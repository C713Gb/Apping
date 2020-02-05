package com.example.apping.CreateOrSignInAcc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.apping.EventPage.EventPageActivity;
import com.example.apping.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeScreenActivity extends AppCompatActivity {
    ViewPager viewPager;
    CustomSwipeAdapter adapter;
    LinearLayout SliderDotsPanel;
    private int dotsCount;
    private ImageView[] dots;
    Button joinNowButton;
    TextView signInButton;
    FirebaseUser firebaseUser;


    @Override
    protected void onStart() {
        super.onStart();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser != null){
            startActivity(new Intent(WelcomeScreenActivity.this, EventPageActivity.class));
            finish();
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);



        joinNowButton = findViewById(R.id.JoinNowButton);
        signInButton = findViewById(R.id.SignInButton);
        viewPager = findViewById(R.id.view_pager);
        adapter = new CustomSwipeAdapter(this);
        SliderDotsPanel = findViewById(R.id.Slider_Dots);
        dotsCount = adapter.getCount();
        dots = new ImageView[dotsCount];

        DisplayDots();

        viewPager.setAdapter(adapter);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(), 4000, 5000);


        joinNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeScreenActivity.this, JoinNowActivity.class);
                startActivity(intent);
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeScreenActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });

    }


    public class MyTimerTask extends TimerTask {

        @Override
        public void run() {

            WelcomeScreenActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (viewPager.getCurrentItem() == 0){
                        viewPager.setCurrentItem(1);
                    }else if (viewPager.getCurrentItem() == 1){
                        viewPager.setCurrentItem(2);
                    }else if (viewPager.getCurrentItem() == 2){
                        viewPager.setCurrentItem(3);
                    }else {
                        viewPager.setCurrentItem(0);
                    }

                }
            });

        }
    }

    private void DisplayDots() {


        for (int i=0; i< dotsCount; i++){
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8,0,8,0);

            SliderDotsPanel.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for (int i=0; i< dotsCount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));
                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


}
