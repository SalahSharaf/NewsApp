package com.example.bios.newsapp;

public class News {
    private String Type,SectionName,WebTitle,WebUrl,Date;

    public News(String type, String sectionName, String webTitle, String webUrl,String date) {
        Type = type;
        SectionName = sectionName;
        WebTitle = webTitle;
        WebUrl = webUrl;
        this.Date=date;
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
