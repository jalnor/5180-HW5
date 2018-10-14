package com.example.gameon.hw05;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.webkit.WebView;

public class ViewWebPages extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_web_pages);

        String test = getIntent().getExtras().getString("urlToNewsArticle");

        WebView wv = findViewById(R.id.webView);
        wv.loadUrl(test);
    }
}
