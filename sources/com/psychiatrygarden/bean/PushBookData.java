package com.psychiatrygarden.bean;

import java.io.Serializable;

/* loaded from: classes5.dex */
public class PushBookData implements Serializable {
    private String author;
    private String describe;
    private String grade;
    private String id;
    private boolean isSelected = false;
    private String rate;
    private String thumbnail;
    private String title;

    public String getAuthor() {
        return this.author;
    }

    public String getDescribe() {
        return this.describe;
    }

    public String getGrade() {
        return this.grade;
    }

    public String getId() {
        return this.id;
    }

    public String getRate() {
        return this.rate;
    }

    public String getThumbnail() {
        return this.thumbnail;
    }

    public String getTitle() {
        return this.title;
    }

    public boolean isSelected() {
        return this.isSelected;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
