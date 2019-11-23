package com.example.android.meetingscheduler_sahilsuhag;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class CustomPagerAdapter extends FragmentPagerAdapter {
    LayoutInflater lf;
    String [] title = {"Prev","Date","Next"};
    public CustomPagerAdapter (FragmentManager fm){
        super(fm);

    }


    public int getCount(){
        return 1;
    }



    public Fragment getItem(int position){

        return new DailyFragment();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }


}
