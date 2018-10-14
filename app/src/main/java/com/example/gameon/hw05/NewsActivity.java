package com.example.gameon.hw05;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity implements GetNewsArticlesAsync.PNews{

    ArrayList<News> headlines = null;
    AlertDialog dialog = null;
    private int progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        Sources source = (Sources) getIntent().getSerializableExtra("source");
        Log.d("ohmy", "Did we get the url in newsActivity " + source.getUrl());


        if ( isConn() ) {
            if ( headlines == null ) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                LayoutInflater inflater = this.getLayoutInflater();

                builder.setTitle("Loading").setView(inflater.inflate(R.layout.activity_loading, null));

                new GetNewsArticlesAsync(NewsActivity.this).execute(source.getSourceId());

                this.dialog = builder.create();
                this.dialog.show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "No internet connection", (Toast.LENGTH_LONG * 100)).show();
        }



    }

    public void listNews() {
        ListView lv2 = findViewById(R.id.listView2);
        ArrayAdapter<News> ad2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, this.headlines);
        lv2.setAdapter(ad2);
    }

    @Override
    public void passNews(ArrayList<News> headlines) {
        Log.d("ohmy", "This is headlines " + headlines);
        this.headlines = headlines;
        if ( this.headlines != null ) {
            listNews();
        }
    }

    @Override
    public void progress(int progress) {
        Log.d("ohmy", "This is progress " + progress);
        this.progress = progress;
        if ( progress == 1 ) {
            this.dialog.hide();
        }
    }

    private boolean isConn() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();

        if (ni == null || !ni.isConnected() || (ni.getType() != ConnectivityManager.TYPE_WIFI && ni.getType() != ConnectivityManager.TYPE_MOBILE)) {
            return false;
        }
        return true;
    }
}
