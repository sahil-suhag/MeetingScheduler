package com.example.android.meetingscheduler_sahilsuhag;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ScheduleFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle bundle){
        final View rootView = inflater.inflate(R.layout.submit_fragment,vg,false);

        final Calendar myCalendar = Calendar.getInstance();


        final Button back = rootView.findViewById(R.id.back);
        final Button setMeet = rootView.findViewById(R.id.meetingDate);
        final Button startTime= rootView.findViewById(R.id.meetingStartTime);
        final Button endTime = rootView.findViewById(R.id.meetingEndTime);
        final Button submit = rootView.findViewById(R.id.submit);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd/MM/yy"; //In which you need put here
                SimpleDateFormat format1 = new SimpleDateFormat(myFormat, Locale.US);
                SimpleDateFormat format2 =new SimpleDateFormat("dd/MM/yyyy");


                setMeet.setText("Meeting at : "+format1.format(myCalendar.getTime()));

            }

        };


        setMeet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();


            }
        });

        startTime.setOnClickListener(new View.OnClickListener() {

            int mHour = myCalendar.get(Calendar.HOUR_OF_DAY);
            int mMinute = myCalendar.get(Calendar.MINUTE);
            SimpleDateFormat Startformat= new SimpleDateFormat("HH:mm:ss");

            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                startTime.setText("Start Time : "+Startformat.format( new Date(myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DATE),hourOfDay,minute)));
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();




            }
        });

        endTime.setOnClickListener(new View.OnClickListener() {
            int mHour = myCalendar.get(Calendar.HOUR_OF_DAY);
            int mMinute = myCalendar.get(Calendar.MINUTE);
            SimpleDateFormat endformat= new SimpleDateFormat("HH:mm:ss");

            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                endTime.setText("Start Time : "+endformat.format( new Date(myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DATE),hourOfDay,minute)));
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();




            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(),"Meeting Scheduled", Toast.LENGTH_SHORT).show();

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment dailyFrag = new DailyFragment();
                FragmentTransaction trans = getFragmentManager().beginTransaction();
                trans.replace(R.id.root2,dailyFrag);
                trans.addToBackStack(null);
                trans.commit();
                LinearLayout meetRoot = rootView.findViewById(R.id.root1);
                meetRoot.setVisibility(View.INVISIBLE);
            }
        });

        return rootView;

    }

}
