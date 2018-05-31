package com.example.bios.newsapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class MyListAdapter extends ArrayAdapter<News> implements View.OnClickListener {
    List<News> news;

    public MyListAdapter(@NonNull Context context, int resource, List<News> news) {
        super(context, resource);
        this.news = news;
    }

    @Override
    public int getCount() {
        return news.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listitem_layout, null);
        }
        TextView type, title, date,section,names;
        type = convertView.findViewById(R.id.type);
        title = convertView.findViewById(R.id.title);
        date = convertView.findViewById(R.id.date);
        type.setText(news.get(position).getType());
        title.setText(news.get(position).getWebTitle());
        date.setText(news.get(position).getDate());
        section=convertView.findViewById(R.id.sectionName);
        names=convertView.findViewById(R.id.names);
        names.setText("Written by : ");
        for(String s:news.get(position).getNames()) {
            names.append(s+"\n");
        }
        section.setText(" / "+news.get(position).getSectionName());
        convertView.setOnClickListener(this);
        convertView.setTag(position);
        return convertView;
    }

    @Override
    public void onClick(View v) {
        int index = (int) v.getTag();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(news.get(index).getWebUrl()));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getContext().startActivity(intent);
    }
}
