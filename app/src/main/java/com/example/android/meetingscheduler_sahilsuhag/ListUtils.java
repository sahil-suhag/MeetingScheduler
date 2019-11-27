package com.example.android.meetingscheduler_sahilsuhag;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;

public class ListUtils {

    private static final String LOG_TAG = ListUtils.class.getSimpleName();

    private ListUtils(){

    }

    public static ArrayList<Meeting> fetchData(String Url){
        URL url = createUrl(Url);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        ArrayList<Meeting> meetingList = extractFeaturesFromJson(jsonResponse);
        return meetingList;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    public static String makeHttpRequest(URL url) throws IOException{
        String jsonResponse="";
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the herokuapp JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    public static String readFromStream(InputStream iptstm) throws IOException{
        StringBuilder output = new StringBuilder();
        if (iptstm!=null){
            InputStreamReader inputStreamReader = new InputStreamReader(iptstm, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    public static ArrayList<Meeting> extractFeaturesFromJson(String json){
        ArrayList<Meeting> listToReturn= new ArrayList<>();
        try{
            JSONArray root = new JSONArray(json);
            for(int i =0 ; i<root.length();i++){
                JSONObject meet = root.optJSONObject(i);
                String startTime = meet.optString("start_time");
                if(startTime.length()!=5){
                    startTime ="0"+startTime;
                }
                String endTime = meet.optString("end_time");
                String desc = meet.optString("description");
                JSONArray attendees = meet.optJSONArray("participants");
                ArrayList<String> participants = new ArrayList<>();
                for (int y = 0; y<attendees.length();y++){
                    String name = attendees.getString(i);
                    participants.add(name);
                }
                Meeting meeting = new Meeting(desc,startTime,endTime,participants);
                listToReturn.add(meeting);
            }
        }
        catch(JSONException e){
            e.printStackTrace();
        }
        Collections.sort(listToReturn);
        return listToReturn;
    }

}
