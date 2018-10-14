/*
Assignment HW#5
GetNewsArticlesAsync.java
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

public class GetNewsArticlesAsync extends AsyncTask<String, Integer, ArrayList<News>> {


    private ArrayList<News> headlines = new ArrayList<>();
    PNews pNews;

    public GetNewsArticlesAsync(PNews pNews) {
        this.pNews = pNews;
    }

    @Override
    protected ArrayList<News> doInBackground(String... strings) {
        HttpURLConnection huc;
        InputStream is;
        BufferedReader br;
        JSONArray arts;
        JSONObject json;
        int arrLength = 0;

        String inputUrl = "https://newsapi.org/v2/top-headlines?sources=" + strings[0] + "&apiKey=f2b4fe68a0654cf0a04467cf8ae0927d";

        try {
            URL url = new URL(inputUrl);
            huc = (HttpURLConnection) url.openConnection();
            is = huc.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            String line = "";
            line = br.readLine();
            json = new JSONObject(line);
            arts =  json.getJSONArray("articles");
            arrLength = arts.length();
            double prog = 0.0;

            for ( int i = 0; i < arrLength; i++ ) {
                News news = new News();
                JSONObject hLN = arts.getJSONObject(i);
                news.setAuthor(hLN.getString("author"));
                news.setTitle(hLN.getString("title"));
                news.setDescription(hLN.getString("description"));
                news.setUrl(hLN.getString("url"));
                news.setUrlToImage(hLN.getString("urlToImage"));
                news.setPublishedAt(hLN.getString("publishedAt"));
                news.setContent(hLN.getString("content"));
                headlines.add(news);
                prog = ((i +1) / (double)arrLength) * 100;
                publishProgress((int)prog);
            }

            return headlines;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<News> news) {
        pNews.passNews(news);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        pNews.progress(values[0]);
    }

    public static interface PNews {
        public void passNews(ArrayList<News> headlines);
        public void progress(int progress);
    }
}
