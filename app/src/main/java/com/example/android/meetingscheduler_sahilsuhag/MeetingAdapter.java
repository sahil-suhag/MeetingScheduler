package com.example.android.meetingscheduler_sahilsuhag;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;

public class MeetingAdapter extends ArrayAdapter<Meeting> {

    public MeetingAdapter(Context context, ArrayList<Meeting> list){
        super(context, 0, list);

    }

    @Override
    public View getView(int position, View v, ViewGroup vg){

        Meeting meeting = getItem(position);

        if (v==null){
            v = LayoutInflater.from(getContext()).inflate(R.layout.list_item,vg,false);
        }

        TextView timeTv= v.findViewById(R.id.time);
        String starttime = meeting.getStartTime();
        if (starttime.toCharArray()[0]=='0'){
            char[] temp;
            temp= Arrays.copyOfRange(starttime.toCharArray(),1,starttime.length());
            starttime=String.valueOf(temp);

        }
        timeTv.setText(starttime+" - "+meeting.getEndTime());

        TextView descTv = v.findViewById(R.id.agenda);
        descTv.setText(meeting.getDescription());



        return v;
    }
}
