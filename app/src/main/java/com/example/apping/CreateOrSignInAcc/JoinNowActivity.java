package com.example.apping.CreateOrSignInAcc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.apping.EventPage.EventPageActivity;
import com.example.apping.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class JoinNowActivity extends AppCompatActivity {

    EditText usernameEditText, emailEditText, mobileEditText, passwordEditText;
    Button joinbtn;

    FirebaseAuth auth;
    DatabaseReference reference;
    ProgressDialog pd;

    String str_username, str_email, str_mobile, str_password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_now);

        init();

        joinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd = new ProgressDialog(JoinNowActivity.this);
                pd.setMessage("Please Wait...");
                pd.show();

                str_username = usernameEditText.getText().toString();
                str_email = emailEditText.getText().toString();
                str_mobile = mobileEditText.getText().toString();
                str_password = passwordEditText.getText().toString();

                check();
            }
        });

    }

    private void check() {
        if (TextUtils.isEmpty(str_username) || TextUtils.isEmpty(str_email)||
                TextUtils.isEmpty(str_mobile)|| TextUtils.isEmpty(str_password)){
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
        }else if (str_password.length()<8){
            Toast.makeText(this, "Password should be at least 8 digits", Toast.LENGTH_SHORT).show();
        }else {
            register(str_username, str_password, str_email, str_mobile);
        }
    }

    private void register(final String username, String password, final String email, final String mobile){
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(JoinNowActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){


                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            String userid = firebaseUser.getUid();

                            reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);

                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("id", userid);
                            hashMap.put("username", username.toLowerCase());
                            hashMap.put("mobile", mobile);
                            hashMap.put("email", email);

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        //Toast.makeText(JoinNowActivity.this, "hello it's me", Toast.LENGTH_SHORT).show();
                                        pd.dismiss();
                                        Intent i = new Intent(JoinNowActivity.this, EventPageActivity.class);
                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(i);
                                        finish();
                                    }else {
                                        pd.dismiss();
                                        Toast.makeText(JoinNowActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else {
                            pd.dismiss();
                            Toast.makeText(JoinNowActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void init() {

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        emailEditText = findViewById(R.id.emailEditText);
        mobileEditText = findViewById(R.id.phoneEditText);
        joinbtn = findViewById(R.id.joinbtn);
        auth = FirebaseAuth.getInstance();

    }


}
