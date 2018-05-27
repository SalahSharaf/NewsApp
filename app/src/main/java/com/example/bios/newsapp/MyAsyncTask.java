package com.example.bios.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MyAsyncTask extends AsyncTaskLoader<List<News>> {

    String URL;

    public MyAsyncTask(Context context, String URL) {
        super(context);
        this.URL = URL;
    }

    @Override
    public List<News> loadInBackground() {
        HttpURLConnection connection;
        List<News> news = new ArrayList<>();
        InputStream stream;
        try {
            URL url = new URL(URL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);
            connection.connect();
            stream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder jsonObject = new StringBuilder();
            String s = "";
            while ((s = reader.readLine()) != null) {
                jsonObject.append(s);
            }
            news = extractJSON(jsonObject.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return news;
    }

    private List<News> extractJSON(String s) throws JSONException {
        List<News> news = new ArrayList<>();
        JSONObject root = new JSONObject(s);
        JSONArray array = root.getJSONArray("results");
        for (int i = 0; i < array.length(); i++) {
            JSONObject result = array.getJSONObject(i);
            String type = result.getString("type");
            String sectionName = result.getString("sectionName");
            String Date = result.getString("webPublicationDate");
            String webTitle = result.getString("webTitle");
            String url = result.getString("webUrl");
            news.add(new News(type, sectionName, webTitle, url, Date));
        }
        return news;
    }


}

