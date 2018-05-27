package com.example.bios.newsapp;

import android.app.LoaderManager;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {
    public String URL = "https://content.guardianapis.com/search?q=debate&tag=politics/politics&from-date=2014-01-01&api-key=test";
    public ListView listView;
    public MyListAdapter listAdapter;
    public TextView textView;
    public ProgressBar progressBar;
    SwipeRefreshLayout refreshLayout;
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
                getLoaderManager().initLoader(0,null,MainActivity.this);
                textView.setText("");
            }
        });
        ConnectivityManager connectivityManager= (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info=connectivityManager.getActiveNetworkInfo();
        if(info!=null&&info.isConnected()){
            getLoaderManager().initLoader(0,null,this);
        }else {
            textView.setText(R.string.no_internet);
        }
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        return new MyAsyncTask(getBaseContext(),URL);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {
        if(data==null){
            textView.setText("No Items To Display, swipe to refresh");
        }else {
            textView.setVisibility(View.GONE);
        }
        listAdapter = new MyListAdapter(getBaseContext(), 0, data);
        listView.setAdapter(listAdapter);
        progressBar.setVisibility(View.GONE);
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        listAdapter.clear();
    }
}
