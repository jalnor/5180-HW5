/*
Assignment HW#5
MainActivity.java
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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetSourcesAsync.PData {

    AlertDialog dialog = null;
    ArrayList<Sources> srcs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConnectionCheck cc = new ConnectionCheck(this);
        Boolean connected = cc.isConn();

        if ( connected ) {
            if ( srcs == null ) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                LayoutInflater inflater = this.getLayoutInflater();

                builder.setTitle("Loading").setView(inflater.inflate(R.layout.activity_loading, null));
                new GetSourcesAsync(MainActivity.this).execute("https://newsapi.org/v2/sources?apiKey=f2b4fe68a0654cf0a04467cf8ae0927d");

                this.dialog = builder.create();
                this.dialog.show();

            }
        } else {
            Toast.makeText(getApplicationContext(), "No internet connection", (Toast.LENGTH_LONG * 100)).show();
        }

    }

    private void listSources() {
        ListView lv = findViewById(R.id.listView);
        ArrayAdapter<Sources> ad = new ArrayAdapter<Sources>(this, android.R.layout.simple_list_item_1, android.R.id.text1, this.srcs);
        lv.setAdapter(ad);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("ohmy", "This is the clicked item " + srcs.get(position));
                Intent intent = new Intent(getApplicationContext(), NewsActivity.class);
                intent.putExtra("source", srcs.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public void passData(ArrayList<Sources> srcs) {

        this.srcs = srcs;
        Log.d("message2", "Srcs in passdata is " + this.srcs);
        if ( srcs != null ) {
            listSources();

        }


    }

    @Override
    public void progress(int progress) {

        if ( progress == 100 ) {
            this.dialog.dismiss();
        }
        Log.d("message", "This is the progress at the interface" + progress);
    }
}
