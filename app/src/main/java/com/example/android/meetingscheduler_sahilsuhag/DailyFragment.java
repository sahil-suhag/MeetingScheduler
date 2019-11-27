package com.example.android.meetingscheduler_sahilsuhag;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DailyFragment extends Fragment {


    private MeetingAdapter mAdapter;
    private static String REQUEST_URL_TEMP ="http://fathomless-shelf-5846.herokuapp.com/api/schedule?date=";
//    private static final String REQUEST_URL ="http://fathomless-shelf-5846.herokuapp.com/api/schedule?date=\"7/8/2015\" ";
//    private static final String REQUEST_URL_2 ="http://fathomless-shelf-5846.herokuapp.com/api/schedule?date=\"7/8/2019\" ";

    public DailyFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state){
        final View rootView = inflater.inflate(R.layout.fragment_layout,container,false);
        ListView listview = (ListView)rootView.findViewById(R.id.list);

        ArrayList<Meeting> meetingsList = new ArrayList<>();

        mAdapter = new MeetingAdapter(getContext(),meetingsList);
        listview.setAdapter(mAdapter);

        final Calendar myCalendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");

        final TextView edittext = rootView.findViewById(R.id.date);
        //dateTv.setText(dateFormat.format(cal.getTime()));
        edittext.setText(dateFormat.format(myCalendar.getTime()));



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
                REQUEST_URL_TEMP = REQUEST_URL_TEMP+"\""+format2.format(myCalendar.getTime())+"\"";
                GetMeetingAsyncTask asyncTask = new GetMeetingAsyncTask();
                asyncTask.execute(REQUEST_URL_TEMP);

                edittext.setText(format1.format(myCalendar.getTime()));

            }

        };




        edittext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//                String myFormat = "dd/MM/yy"; //In which you need put here
//                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
//
//                edittext.setText(sdf.format(myCalendar.getTime()));
            }
        });

        Button next=rootView.findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCalendar.add(Calendar.DATE,1);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yy");
                String varDate = dateFormat.format(myCalendar.getTime());
                REQUEST_URL_TEMP =REQUEST_URL_TEMP+"\""+varDate+"\"";
                edittext.setText(dateFormat2.format(myCalendar.getTime()));


                GetMeetingAsyncTask gmat = new GetMeetingAsyncTask();
                gmat.execute(REQUEST_URL_TEMP);

            }
        });

        Button prev=rootView.findViewById(R.id.prev);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCalendar.add(Calendar.DATE,-1);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yy");

                edittext.setText(dateFormat2.format(myCalendar.getTime()));

                String varDate = dateFormat.format(myCalendar.getTime());
                REQUEST_URL_TEMP =REQUEST_URL_TEMP+"\""+varDate+"\"";

                GetMeetingAsyncTask gmat = new GetMeetingAsyncTask();
                gmat.execute(REQUEST_URL_TEMP);

            }
        });

        Button submit = rootView.findViewById(R.id.schedule);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment scheduleFrag = new ScheduleFragment();
                FragmentTransaction trans = getFragmentManager().beginTransaction();
                trans.replace(R.id.root,scheduleFrag);
                trans.addToBackStack(null);
                trans.commit();
                RelativeLayout meetRoot = rootView.findViewById(R.id.meetFrag);
                meetRoot.setVisibility(View.INVISIBLE);
            }
        });

        SimpleDateFormat format2 =new SimpleDateFormat("dd/MM/yyyy");
        REQUEST_URL_TEMP = REQUEST_URL_TEMP+"\""+format2.format(myCalendar.getTime())+"\"";
        GetMeetingAsyncTask gmat = new GetMeetingAsyncTask();
        gmat.execute(REQUEST_URL_TEMP);

        return rootView;


    }


   private class GetMeetingAsyncTask extends AsyncTask<String, Void, ArrayList<Meeting>> {


        @Override
        protected ArrayList<Meeting> doInBackground(String... urls) {
            // Don't perform the request if there are no URLs, or the first URL is null
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            ArrayList<Meeting> result = ListUtils.fetchData(urls[0]);
            return result;
        }

        /**
         * This method runs on the main UI thread after the background work has been
         * completed. This method receives as input, the return value from the doInBackground()
         * method. First we clear out the adapter, to get rid of earthquake data from a previous
         * query to USGS. Then we update the adapter with the new list of earthquakes,
         * which will trigger the ListView to re-populate its list items.
         */
        @Override
        protected void onPostExecute(ArrayList<Meeting> data) {
            // Clear the adapter of previous earthquake data
            mAdapter.clear();

            // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
            // data set. This will trigger the ListView to update.
            if (data != null && !data.isEmpty()) {
                mAdapter.addAll(data);
            }
        }
    }
}
