package com.example.apping.EventPage.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.apping.EventPage.Adapter.SectionPageAdapter;
import com.example.apping.EventPage.TabFragments.MyEventsFragment;
import com.example.apping.EventPage.TabFragments.OpenEventsFragment;
import com.example.apping.EventPage.TabFragments.TodaysEventsFragment;
import com.example.apping.R;
import com.google.android.material.tabs.TabLayout;

public class HomeFragment extends Fragment {

    View myFragment;
    ViewPager homeViewPager;
    TabLayout tabLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.fragment_home, container, false);

        init();

        return myFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setUpHomeViewPager(homeViewPager);
        tabLayout.setupWithViewPager(homeViewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setUpHomeViewPager(ViewPager homeViewPager) {
        SectionPageAdapter adapter = new SectionPageAdapter(getChildFragmentManager());

        adapter.addFragment(new OpenEventsFragment(), "Open Events");
        adapter.addFragment(new MyEventsFragment(), "My Events");
        adapter.addFragment(new TodaysEventsFragment(), "Todays Events");

        homeViewPager.setAdapter(adapter);
    }

    private void init() {
        homeViewPager = myFragment.findViewById(R.id.homeViewPager);
        tabLayout = myFragment.findViewById(R.id.tabLayout);
    }
}
