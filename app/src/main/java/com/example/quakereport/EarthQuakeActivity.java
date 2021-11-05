package com.example.quakereport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class EarthQuakeActivity extends AppCompatActivity {
    private static final String url = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&limit=10";
    private ArrayList<EarthQuake> mEarthquakes;
    private EarthQuakeAdapter mAdapter;
    private ListView mListView;
    private TextView mEmptyView;
    private ProgressBar mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mListView = findViewById(R.id.myList);
        mEmptyView = findViewById(R.id.empty_text_view);
        mProgress = findViewById(R.id.loading_indicator);
        mListView.setEmptyView(mEmptyView);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mEarthquakes = QueryUtils.extractEarthquakes(response);

                mAdapter = new EarthQuakeAdapter(getApplicationContext(), mEarthquakes);
                mAdapter.notifyDataSetChanged();


                mProgress.setVisibility(View.GONE);

                mEmptyView.setText("no earthquakes found");


                if (mEarthquakes != null && !mEarthquakes.isEmpty()) {
                    mListView.setAdapter(mAdapter);
                }


            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue mRequest = Volley.newRequestQueue(this);
        mRequest.add(stringRequest);


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                EarthQuake currentEarthquake = mAdapter.getItem(i);
                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri earthquakeUri = Uri.parse(currentEarthquake.getUrl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });
    }
}