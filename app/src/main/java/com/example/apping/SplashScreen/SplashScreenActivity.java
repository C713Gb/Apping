package com.example.apping.SplashScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.apping.CreateOrSignInAcc.WelcomeScreenActivity;
import com.example.apping.EventPage.EventPageActivity;
import com.example.apping.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreenActivity extends AppCompatActivity {

    private static int WELCOME_TIMEOUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent welcome = new Intent(SplashScreenActivity.this, WelcomeScreenActivity.class);
                startActivity(welcome);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        }, WELCOME_TIMEOUT);



    }
}
