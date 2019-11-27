package com.example.android.meetingscheduler_sahilsuhag;

import java.util.ArrayList;

public class Meeting implements Comparable<Meeting>{


    private String description="";

    private String startTime;
    private String endTime;

    ArrayList<String> attendees;

    public Meeting(String desc, String strtime, String endTime,ArrayList<String> attendes){
        description=desc;
        startTime=strtime;
        this.endTime=endTime;
        attendees=attendes;
    }

    public String getDescription() {
        return description;
    }

    public String getStartTime(){
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }
    public String getAttendees() {
        StringBuilder att=new StringBuilder("");
        for (int i = 0; i < attendees.size();i++) {
           att = att.append(";  "+attendees.get(i));
        }
        return att.toString();

    }

    public int compareTo(Meeting o){
            return startTime.compareTo(o.startTime);
    }
}
