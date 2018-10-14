/*
Assignment HW#5
WebView.java
Jarrod Norris, Andrew Schlesinger
 */
package com.example.gameon.hw05;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class ViewWebPages extends AppCompatActivity {

    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_web_pages);

        String test = getIntent().getExtras().getString("urlToNewsArticle");

        ConnectionCheck cc = new ConnectionCheck(this);
        Boolean connected = cc.isConn();

        if ( connected ) {
            if ( test != null ) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                LayoutInflater inflater = this.getLayoutInflater();

                builder.setTitle("Loading").setView(inflater.inflate(R.layout.activity_loading, null));

                WebView wv = findViewById(R.id.webView);
                wv.setWebViewClient(new WebClient());
                WebSettings set = wv.getSettings();
                set.setJavaScriptEnabled(true);
                set.setBuiltInZoomControls(true);
                wv.loadUrl(test);


                this.dialog = builder.create();
                this.dialog.show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "No internet connection", (Toast.LENGTH_LONG * 100)).show();
        }
    }



    class WebClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        public void onPageFinished(WebView view, String url) {
            dialog.dismiss();
        }
    }
}
