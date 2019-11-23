package com.example.android.meetingscheduler_sahilsuhag;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class DailyFragment extends Fragment {

    private MeetingAdapter mAdapter;
    private static final String REQUEST_URL ="http://fathomless-shelf-5846.herokuapp.com/api/schedule?date=\"7/8/2015\" ";

    public DailyFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state){
        View rootView = inflater.inflate(R.layout.fragment_layout,container,false);
        ListView listview = (ListView)rootView.findViewById(R.id.list);

        ArrayList<Meeting> meetingsList = new ArrayList<>();

        mAdapter = new MeetingAdapter(getContext(),meetingsList);
        listview.setAdapter(mAdapter);

        GetMeetingAsyncTask gmat = new GetMeetingAsyncTask();
        gmat.execute(REQUEST_URL);

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
