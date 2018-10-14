/*
Assignment HW#5
ConnectionCheck.java
Jarrod Norris, Andrew Schlesinger
 */

package com.example.gameon.hw05;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;

public  class ConnectionCheck extends AppCompatActivity {

    Context context;

    public ConnectionCheck(Context context) {
        this.context = context;
    }

    public boolean isConn() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();

        if (ni == null || !ni.isConnected() || (ni.getType() != ConnectivityManager.TYPE_WIFI && ni.getType() != ConnectivityManager.TYPE_MOBILE)) {
            return false;
        }
        return true;
    }
}
