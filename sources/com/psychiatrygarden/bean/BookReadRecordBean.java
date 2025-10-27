package com.psychiatrygarden.bean;

import com.google.gson.annotations.SerializedName;
import com.umeng.analytics.pro.d;

/* loaded from: classes5.dex */
public class BookReadRecordBean {

    @SerializedName("book_id")
    private String bookId;
    private String bookName;
    private String chapterName;
    private String date;

    @SerializedName(d.f22695q)
    private String endTime;
    private int state;

    public String getBookName() {
        return this.bookName;
    }

    public String getChapterName() {
        return this.chapterName;
    }

    public String getDate() {
        return this.date;
    }

    public int getState() {
        return this.state;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setState(int state) {
        this.state = state;
    }
}
