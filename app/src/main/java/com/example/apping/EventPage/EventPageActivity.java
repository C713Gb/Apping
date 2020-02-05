package com.example.apping.EventPage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.apping.EventPage.Fragments.AddEventFragment;
import com.example.apping.EventPage.Fragments.HomeFragment;
import com.example.apping.EventPage.Fragments.NotificationsFragment;
import com.example.apping.EventPage.Fragments.SearchFragment;
import com.example.apping.EventPage.Fragments.StarredFragment;
import com.example.apping.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class EventPageActivity extends AppCompatActivity {

    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_page);

        init();

        setSupportActionBar(toolbar);

        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;
                    switch (menuItem.getItemId()){
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_search:
                            selectedFragment = new SearchFragment();
                            break;
                        case R.id.nav_add_event:
                            selectedFragment = new AddEventFragment();
                            break;
                        case R.id.nav_starred:
                            selectedFragment = new StarredFragment();
                            break;
                        case R.id.nav_notifications:
                            selectedFragment = new NotificationsFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.topright_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.user_profile:
                Toast.makeText(this, "SendSelected", Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(EventPageActivity.this, ProfileActivity.class);
                //startActivity(intent);
                return true;
            case R.id.send:
                Toast.makeText(this, "Send Selected", Toast.LENGTH_SHORT).show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        finish();
    }
}
