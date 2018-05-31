package com.example.bios.newsapp;

import java.util.List;

public class News {
    private String Type,SectionName,WebTitle,WebUrl,Date;
    private List<String> names;

    public News(String type, String sectionName, String webTitle, String webUrl, String date, List<String> Names) {
        Type = type;
        SectionName = sectionName;
        WebTitle = webTitle;
        WebUrl = webUrl;
        this.Date=date;
        this.names=Names;
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getSectionName() {
        return SectionName;
    }

    public void setSectionName(String sectionName) {
        SectionName = sectionName;
    }

    public String getWebTitle() {
        return WebTitle;
    }

    public void setWebTitle(String webTitle) {
        WebTitle = webTitle;
    }

    public String getWebUrl() {
        return WebUrl;
    }

    public void setWebUrl(String webUrl) {
        WebUrl = webUrl;
    }
}
