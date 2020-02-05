package com.example.apping.EventPage.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.apping.EventPage.AddEventActivity;
import com.example.apping.EventPage.LocationActivity;
import com.example.apping.R;

public class AddEventFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_event, container, false);
        Intent intent = new Intent(getActivity(), LocationActivity.class);
        startActivity(intent);
        return view;
    }
}
