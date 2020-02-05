package com.example.apping.EventPage.TabFragmentsPage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.apping.R;

public class OpenEventsActivity extends AppCompatActivity {

    TextView titleActivity, descriptionActivity, organiserActivity, dateActivity, location_txtview;
    public String aTitle, aDescription, aOrganiser, aDate, aLat, aLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_events);

        init();

        getVarIntent();

        setVarText();

        final double la = Double.parseDouble(aLat);
        final double lo = Double.parseDouble(aLng);

        location_txtview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(OpenEventsActivity.this, ""+aLat+","+aLng, Toast.LENGTH_SHORT).show();
                String geoUri = "http://maps.google.com/maps?q=loc:" + la + "," + lo ;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
                //intent.putExtra("Lati:",aLat);
                //intent.putExtra("Longi:",aLng);
                startActivity(intent);
            }
        });

    }

    private void setVarText() {
        titleActivity.setText(aTitle);
        descriptionActivity.setText(aDescription);
        organiserActivity.setText(aOrganiser);
        dateActivity.setText(aDate);
    }

    private void getVarIntent() {
        Intent intent = getIntent();
        aTitle = intent.getExtras().getString("Title:");
        aDescription = intent.getExtras().getString("Description:");
        aOrganiser = intent.getExtras().getString("Organisers:");
        aDate = intent.getExtras().getString("Date:");
        aLat = intent.getExtras().getString("Lat:");
        aLng = intent.getExtras().getString("Lng:");

    }

    private void init() {
        titleActivity = findViewById(R.id.event_title_activity);
        descriptionActivity = findViewById(R.id.event_description_activity);
        organiserActivity = findViewById(R.id.organisers_activity);
        dateActivity = findViewById(R.id.event_date_activity);
        location_txtview = findViewById(R.id.locate_txtview);

    }


}
