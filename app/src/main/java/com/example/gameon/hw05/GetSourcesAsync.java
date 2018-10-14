/*
Assignment HW#5
GetSourcesAsync.java
Jarrod Norris, Andrew Schlesinger
 */
package com.example.gameon.hw05;

import android.os.AsyncTask;
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
import java.util.ArrayList;

public class GetSourcesAsync extends AsyncTask<String, Integer, ArrayList<Sources>> {

    JSONArray newsSources = new JSONArray();
    ArrayList<Sources> srcs = new ArrayList<>();
    int progress = 0;

    PData pData;
    public GetSourcesAsync(PData pData) {
        this.pData = pData;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        pData.progress(values[0]);
    }

    @Override
    protected void onPostExecute(ArrayList<Sources> sources) {
        Log.d("Damnit", "This is in the new onpostexec " + sources.get(0).getSourceName());
        pData.passData(sources);
    }

    @Override
    protected ArrayList<Sources> doInBackground(String... strings) {

        HttpURLConnection huc = null;
        InputStream is = null;
        BufferedReader br = null;
        JSONObject j;
        int arrLength = 1;
        try {
            URL url = new URL(strings[0]);
            huc = (HttpURLConnection) url.openConnection();
            is = huc.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            String line = "";
            line = br.readLine();
            j = new JSONObject(line);
            newsSources = j.getJSONArray("sources");
            arrLength = newsSources.length();
            Log.d("message", "The value of arrLength is " + arrLength);
            double prog = 0;
            for ( int i = 0; i < arrLength; i++ ) {
                JSONObject src = newsSources.getJSONObject(i);
                Sources source = new Sources();
                source.setSourceId(src.getString("id"));
                source.setSourceName(src.getString("name"));
                source.setDescription(src.getString("description"));
                source.setUrl(src.getString("url"));
                source.setCategory(src.getString("category"));
                source.setLanguage(src.getString("language"));
                source.setCountry(src.getString("country"));
                srcs.add(source);
                Log.d("Damnit", "This is bullshit " + i + ", " + arrLength + " dividing them " + i/arrLength + " But dividing real numbers 1/4 " + ((int)1 / (int)4) );
                prog = ( (i + 1) / (double) arrLength ) * 100;
                publishProgress((int)prog);
                Log.d("message", "doInBackground: " + srcs.get(i).getSourceName() + " progress is " + prog);
            }

            Log.d("Damnit", "doInBackground: " + srcs.get(15).getSourceName());

            return srcs;


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            try {
                if ( is != null && br != null ) {
                    is.close();
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }



    public static interface PData {
        public void passData(ArrayList<Sources> data);
        public void progress(int p);
    }
}
