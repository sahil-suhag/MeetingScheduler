package com.example.android.meetingscheduler_sahilsuhag;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CustomPagerAdapter cpa = new CustomPagerAdapter(getSupportFragmentManager() );

        ViewPager vp =(ViewPager)findViewById(R.id.pager);
        vp.setAdapter(cpa);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd / MM / yy");

        TextView dateTv = (TextView)findViewById(R.id.date);
        dateTv.setText(dateFormat.format(cal.getTime()));

        Button next = (Button)findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

//        TabLayout tl = (TabLayout)findViewById(R.id.sliding_tabs);
//        tl.setupWithViewPager(vp);


    }
}
