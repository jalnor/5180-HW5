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

public class GetSourcesAsync extends AsyncTask<String, Integer, String> {

    JSONArray headlines = new JSONArray();

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    @Override
    protected String doInBackground(String... strings) {

        HttpURLConnection huc = null;
        JSONObject j;
        //URLConnection huc2 = null;
        try {
            URL url = new URL(strings[0]);
            huc = (HttpURLConnection) url.openConnection();
            InputStream is = huc.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = "";
            line = br.readLine();
            j = new JSONObject(line);
            //headlines = j.getJSONArray("articles");
            Log.d("message", "The json is " + j);
            is.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }



    public static interface pData {
        public void passData();
    }
}
