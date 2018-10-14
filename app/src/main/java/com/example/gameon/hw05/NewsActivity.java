/*
Assignment HW#5
NewsActivity.java
Jarrod Norris, Andrew Schlesinger
 */

package com.example.gameon.hw05;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity implements GetNewsArticlesAsync.PNews{

    ArrayList<News> headlines = null;
    AlertDialog dialog = null;
    Sources source;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        source = (Sources) getIntent().getSerializableExtra("source");
        Log.d("ohmy", "Did we get the url in newsActivity " + source.getUrl());
        ConnectionCheck cc = new ConnectionCheck(this);
        Boolean connected = cc.isConn();

        if ( connected ) {
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

        TextView title = findViewById(R.id.news_title);
        title.setText(source.getSourceName());
        ListView lv2 = findViewById(R.id.listView2);
        NewsAdapter na = new NewsAdapter(this,R.layout.news_display, this.headlines);
        lv2.setAdapter(na);

        lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), ViewWebPages.class);
                intent.putExtra("urlToNewsArticle", headlines.get(position).getUrl());
                startActivity(intent);
            }
        });
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
        if ( progress == 100 ) {
            this.dialog.dismiss();
        }
    }
}
