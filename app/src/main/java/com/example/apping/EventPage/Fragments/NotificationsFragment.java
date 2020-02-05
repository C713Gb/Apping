package com.example.apping.EventPage.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apping.EventPage.Adapter.NotificationsAdapter;
import com.example.apping.Events.Notifications;
import com.example.apping.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NotificationsFragment extends Fragment {

    private NotificationsAdapter notificationsAdapter;
    private List<Notifications> notificationsList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        //Intent intent = new Intent(getActivity(), NotificationsActivity.class);
        //startActivity(intent);


        RecyclerView recyclerView = view.findViewById(R.id.notification_recyclerView);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        //linearLayoutManager.setReverseLayout(true);
        //linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        notificationsList = new ArrayList<>();
        notificationsAdapter = new NotificationsAdapter(getContext(), notificationsList);
        recyclerView.setAdapter(notificationsAdapter);

        readNotifications();

        /*
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Notifications");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot: dataSnapshot.getChildren()){

                    HashMap k1 = (HashMap) snapshot.getValue();
                    Object k = k1.get("ispost");

                    if (k.toString() == "true")
                        readNotifications();

                    //Toast.makeText(NotificationsActivity.this, ""+(k.toString() == "true"), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

         */

        return view;
    }

    private void readNotifications() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Notifications");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                notificationsList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Notifications notifications = snapshot.getValue(Notifications.class);
                    notificationsList.add(notifications);
                }
                Collections.reverse(notificationsList);
                notificationsAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
