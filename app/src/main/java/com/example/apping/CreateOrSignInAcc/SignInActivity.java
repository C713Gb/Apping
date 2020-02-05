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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInActivity extends AppCompatActivity {

    EditText s_emailEditTxt, s_passwordEditTxt;
    Button signbtn;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        init();

        signbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog pd = new ProgressDialog(SignInActivity.this);
                pd.setMessage("Please wait...");
                pd.show();

                String str_email = s_emailEditTxt.getText().toString();
                String str_password = s_passwordEditTxt.getText().toString();

                if (TextUtils.isEmpty(str_email)||TextUtils.isEmpty(str_password)){
                    Toast.makeText(SignInActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }else {

                    auth.signInWithEmailAndPassword(str_email, str_password)
                            .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                                                .child("Users").child(auth.getCurrentUser().getUid());

                                        reference.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                pd.dismiss();
                                                Intent intent = new Intent(SignInActivity.this, EventPageActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(intent);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                                pd.dismiss();
                                            }
                                        });
                                    }else {
                                        pd.dismiss();
                                        Toast.makeText(SignInActivity.this, "Authentication Failed!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }


            }
        });
    }

    private void init() {
        s_emailEditTxt = findViewById(R.id.s_emailEditText);
        s_passwordEditTxt = findViewById(R.id.s_passwordEditText);
        signbtn = findViewById(R.id.signbtn);
        auth = FirebaseAuth.getInstance();
    }
}
