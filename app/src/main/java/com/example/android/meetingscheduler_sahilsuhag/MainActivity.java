package com.example.android.meetingscheduler_sahilsuhag;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment dailyFragment = new DailyFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment,dailyFragment);
        transaction.commit();



//        CustomPagerAdapter cpa = new CustomPagerAdapter(getSupportFragmentManager() );
//
//        ViewPager vp =(ViewPager)findViewById(R.id.pager);
//        vp.setAdapter(cpa);




//        TabLayout tl = (TabLayout)findViewById(R.id.sliding_tabs);
//        tl.setupWithViewPager(vp);


    }
}
