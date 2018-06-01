package com.example.bios.newsapp;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {
    public String URL = "https://content.guardianapis.com/search?q=debate&tag=politics/politics&from-date=2014-01-01&api-key=test&show-tags=contributor";
    //public String URL = "https://content.guardianapis.com/search?q=debates&api-key=test";
    public ListView listView;
    public MyListAdapter listAdapter;
    public TextView textView;
    public ProgressBar progressBar;
    SwipeRefreshLayout refreshLayout;
    public boolean no_Internet;
    public static boolean preferenceChanged;
    public static String listValue, monthSelectionValue;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        textView = findViewById(R.id.text);
        progressBar = findViewById(R.id.progress);
        refreshLayout = findViewById(R.id.swipeRefresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo info = connectivityManager.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    no_Internet = false;
                } else {
                    no_Internet = true;
                }
                getLoaderManager().initLoader(0, null, MainActivity.this);
            }
        });
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            getLoaderManager().initLoader(0, null, this);
        } else {
            textView.setText(R.string.no_internet_connection);
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (preferenceChanged) {
            Toast.makeText(this, R.string.preferences_have_been_modified_Swipe_to_refresh, Toast.LENGTH_SHORT).show();
            preferenceChanged = false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.toggle) {
            Intent preference = new Intent(getBaseContext(), MyPreferenceActivity.class);
            startActivity(preference);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        return new MyAsyncTask(getBaseContext(), URL);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {
        progressBar.setVisibility(View.GONE);
        if (!data.isEmpty() && !no_Internet) {
            textView.setText("");
            listView.setVisibility(View.VISIBLE);
        } else if (no_Internet) {
            textView.setText(R.string.no_internet_connection);
            progressBar.setVisibility(View.GONE);
            listView.setVisibility(View.GONE);
        } else {
            textView.setText(R.string.swipe_to_refresh);
        }
        refreshLayout.setRefreshing(false);
        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(this);
        listValue = preference.getString(getString(R.string.liat), getString(R.string.general));
        monthSelectionValue = preference.getString(getString(R.string.monthselection), "1");
        List<News> mydata = new ArrayList<>(data);
        if (listValue.equalsIgnoreCase(getString(R.string.general))) {
            //do nothing with mydata
        } else if (listValue.equalsIgnoreCase(getString(R.string.news))) {
            for (int i = 0; i < mydata.size(); i++) {
                if (!mydata.get(i).getSectionName().equalsIgnoreCase(getString(R.string.news))) {
                    mydata.remove(i);
                }
            }
        } else if (listValue.equalsIgnoreCase(getString(R.string.politics))) {
            for (int i = 0; i < mydata.size(); i++) {
                if (!mydata.get(i).getSectionName().equalsIgnoreCase(getString(R.string.politics))) {
                    mydata.remove(i);
                }
            }
        } else if (listValue.equalsIgnoreCase(getString(R.string.business))) {
            for (int i = 0; i < mydata.size(); i++) {
                if (!mydata.get(i).getSectionName().equalsIgnoreCase(getString(R.string.business))) {
                    mydata.remove(i);
                }
            }
        }
        for (int i = 0; i < mydata.size(); i++) {
            String date = mydata.get(i).getDate();
            String monthS = date.substring(date.indexOf("-") + 1, date.lastIndexOf("-"));
            Integer month = new Integer(monthS);
            if (month < Integer.valueOf(monthSelectionValue)) {
                mydata.remove(i);
            }
        }
        listAdapter = new MyListAdapter(getBaseContext(), 0, mydata);
        listView.setAdapter(listAdapter);

    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        if (listAdapter != null) {
            listAdapter.clear();
        }
    }
}
