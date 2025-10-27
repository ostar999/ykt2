package com.psychiatrygarden.bean;

import java.io.Serializable;

/* loaded from: classes5.dex */
public class ComYearFWNItemBean implements Serializable {
    private long[] long_questionitems;
    private String mEventStr;
    private String titleStr;
    private String unit;
    private String year;

    public ComYearFWNItemBean(String year, String unit, String mEventStr, long[] long_questionitems, String titleStr) {
        this.year = year;
        this.unit = unit;
        this.mEventStr = mEventStr;
        this.long_questionitems = long_questionitems;
        this.titleStr = titleStr;
    }

    public long[] getLong_questionitems() {
        return this.long_questionitems;
    }

    public String getTitleStr() {
        return this.titleStr;
    }

    public String getUnit() {
        return this.unit;
    }

    public String getYear() {
        return this.year;
    }

    public String getmEventStr() {
        return this.mEventStr;
    }

    public void setLong_questionitems(long[] long_questionitems) {
        this.long_questionitems = long_questionitems;
    }

    public void setTitleStr(String titleStr) {
        this.titleStr = titleStr;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setmEventStr(String mEventStr) {
        this.mEventStr = mEventStr;
    }
}
