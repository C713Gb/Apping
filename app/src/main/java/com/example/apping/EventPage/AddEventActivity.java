package com.example.apping.EventPage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apping.EventPage.Adapter.PostsAdapter;
import com.example.apping.Events.Notifications;
import com.example.apping.Events.Posts;
import com.example.apping.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.Calendar;
import java.util.HashMap;

import static com.example.apping.Notification.AppNotification.CHANNEL_1_ID;

public class AddEventActivity extends AppCompatActivity {

    Uri imageUri;
    String myUrl="";
    StorageTask uploadTask;
    StorageReference storageReference;

    ImageView close, image_added;
    Button post;
    EditText title, description, organisers;
    TextView date, location;

    Calendar c;
    DatePickerDialog dpd;

    String lat,lng;

    private NotificationManagerCompat notificationManagerCompat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);


        init();
        getLatLng();

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddEventActivity.this, EventPageActivity.class));
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        image_added.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setAspectRatio(1,1)
                        .start(AddEventActivity.this);
            }
        });


        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker();
            }
        });
    }

    private void getLatLng() {
        Intent intent = getIntent();
        lat = intent.getExtras().getString("Latitude:");
        lng = intent.getExtras().getString("Longitude:");
        location.setText("Location: "+lat+","+lng);
    }

    private void addLocation() {
        startActivity(new Intent(AddEventActivity.this, LocationActivity.class));
    }

    private void datePicker() {

        c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);


        dpd = new DatePickerDialog(AddEventActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                date.setText(dayOfMonth + "/"+ (month+1) +"/"+ year);
            }
        }, day, month, year);

        dpd.show();

    }



    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();

            image_added.setImageURI(imageUri);

            } else {

                startActivity(new Intent(AddEventActivity.this, EventPageActivity.class));
                finish();
            }

    }

    private void uploadImage() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");
        progressDialog.show();

        if (imageUri!= null){
            final StorageReference filereference = storageReference.child(System.currentTimeMillis()
            +"."+getFileExtension(imageUri));

            uploadTask = filereference.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()){
                        throw task.getException();
                    }

                    return filereference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()){
                        Uri downloadUri = task.getResult();
                        myUrl = downloadUri.toString();

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");

                        String postId = reference.push().getKey();

                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("postid", postId);
                        hashMap.put("postImage", myUrl);
                        hashMap.put("title", title.getText().toString());
                        hashMap.put("description", description.getText().toString());
                        hashMap.put("organisers", organisers.getText().toString());
                        hashMap.put("eventDate", date.getText().toString());
                        hashMap.put("eventLocationLatitude", lat);
                        hashMap.put("eventLocationLongitude", lng);
                        hashMap.put("publisher", FirebaseAuth.getInstance().getCurrentUser().getUid());

                        reference.child(postId).setValue(hashMap);

                        progressDialog.dismiss();
                        startActivity(new Intent(AddEventActivity.this, EventPageActivity.class));


                        addNotifications(organisers.getText().toString(), date.getText().toString(), postId);

                        launchNotification();

                        finish();
                    }else {
                        Toast.makeText(AddEventActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddEventActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            Toast.makeText(this, "No Image Selected!", Toast.LENGTH_SHORT).show();
        }
    }

    private void launchNotification() {
        Notification notification = new NotificationCompat.Builder(AddEventActivity.this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("A New event has been posted")
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setCategory(NotificationCompat.CATEGORY_EVENT)
                .build();

        notificationManagerCompat.notify(1, notification);

    }

    private void init() {
        close = findViewById(R.id.addEventClose);
        image_added = findViewById(R.id.image_added);
        post = findViewById(R.id.post_event);
        title = findViewById(R.id.titleEditText);
        description = findViewById(R.id.descriptionEditText);
        organisers = findViewById(R.id.organisersEditText);
        date = findViewById(R.id.dateTextView);
        location = findViewById(R.id.locationTextView);

        storageReference = FirebaseStorage.getInstance().getReference("posts");

        notificationManagerCompat = NotificationManagerCompat.from(this);
    }

    private void addNotifications(String organisers, String date, String postid){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Notifications");

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("postid",postid);
        hashMap.put("text",organisers +" organised an event on "+ date);
        hashMap.put("ispost",true);

        reference.push().setValue(hashMap);
    }


}
