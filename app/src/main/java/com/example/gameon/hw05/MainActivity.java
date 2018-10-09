package com.example.gameon.hw05;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;

public class MainActivity extends AppCompatActivity {

    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();

        builder.setTitle("Loading").setView(inflater.inflate(R.layout.activity_loading, null));

        dialog = builder.create();

        if ( isConn() ) {

        }
    }



    private boolean isConn() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();

        if (ni == null || !ni.isConnected() || (ni.getType() != ConnectivityManager.TYPE_WIFI && ni.getType() != ConnectivityManager.TYPE_MOBILE)) {
            return false;
        } else {
            new GetSourcesAsync().execute("https://newsapi.org/v2/top-headlines?sources=<Source_id>&apiKey=f2b4fe68a0654cf0a04467cf8ae0927d");
        }
        return true;
    }
}
