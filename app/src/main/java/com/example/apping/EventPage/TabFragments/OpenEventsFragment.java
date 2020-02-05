package com.example.apping.EventPage.TabFragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
import android.widget.Toast;

import com.example.apping.EventPage.Adapter.PostsAdapter;
import com.example.apping.Events.Posts;
import com.example.apping.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class OpenEventsFragment extends Fragment {

    private RecyclerView recyclerView;
    private PostsAdapter postsAdapter;
    private List<Posts> postsLists;


    public OpenEventsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_open_events, container, false);

        recyclerView = view.findViewById(R.id.open_event_recycler_view);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        postsLists = new ArrayList<>();
        postsAdapter = new PostsAdapter(getContext(), postsLists);
        recyclerView.setAdapter(postsAdapter);

        readPosts();
        return view;
    }

    private void readPosts() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postsLists.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Posts post = snapshot.getValue(Posts.class);
                    postsLists.add(post);
                }

                postsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
