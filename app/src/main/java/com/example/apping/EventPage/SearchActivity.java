package com.example.apping.EventPage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.apping.EventPage.Adapter.SearchAdapterClass;
import com.example.apping.Events.Search;
import com.example.apping.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    DatabaseReference reference;
    ArrayList<Search> list;
    RecyclerView recyclerView;
    SearchView searchView;
    Context c;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        reference = FirebaseDatabase.getInstance().getReference().child("Posts");
        recyclerView = findViewById(R.id.rv);
        searchView = findViewById(R.id.search_view);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (reference!=null){
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){
                        list = new ArrayList<>();
                        for (DataSnapshot ds: dataSnapshot.getChildren()){
                            list.add(ds.getValue(Search.class));
                        }

                        SearchAdapterClass searchAdapterClass = new SearchAdapterClass(list);
                        recyclerView.setAdapter(searchAdapterClass);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(SearchActivity.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }
        if (searchView!=null){
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    search(newText);
                    return true;
                }
            });
        }
    }

    private void search(String s) {
        ArrayList<Search> myList = new ArrayList<>();
        for (Search object: list){
            if (object.getTitle().toLowerCase().contains(s.toLowerCase())){
                myList.add(object);
            }
        }

        SearchAdapterClass searchAdapterClass = new SearchAdapterClass(myList);
        recyclerView.setAdapter(searchAdapterClass);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(SearchActivity.this, EventPageActivity.class));
        finish();
    }
}



