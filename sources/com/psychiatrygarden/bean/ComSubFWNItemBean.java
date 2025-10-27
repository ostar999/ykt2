package com.psychiatrygarden.bean;

import java.io.Serializable;

/* loaded from: classes5.dex */
public class ComSubFWNItemBean implements Serializable {
    private boolean IsLisnxi;
    private String chapter_id;
    private long[] long_questionitems;
    private String mEventStr;
    private String titleStr;

    public ComSubFWNItemBean(String mEventStr, long[] long_questionitems, String titleStr, String chapter_id, boolean isLisnxi) {
        this.mEventStr = mEventStr;
        this.long_questionitems = long_questionitems;
        this.titleStr = titleStr;
        this.chapter_id = chapter_id;
        this.IsLisnxi = isLisnxi;
    }

    public String getChapter_id() {
        return this.chapter_id;
    }

    public long[] getLong_questionitems() {
        return this.long_questionitems;
    }

    public String getTitleStr() {
        return this.titleStr;
    }

    public String getmEventStr() {
        return this.mEventStr;
    }

    public boolean isLisnxi() {
        return this.IsLisnxi;
    }

    public void setChapter_id(String chapter_id) {
        this.chapter_id = chapter_id;
    }

    public void setLisnxi(boolean lisnxi) {
        this.IsLisnxi = lisnxi;
    }

    public void setLong_questionitems(long[] long_questionitems) {
        this.long_questionitems = long_questionitems;
    }

    public void setTitleStr(String titleStr) {
        this.titleStr = titleStr;
    }

    public void setmEventStr(String mEventStr) {
        this.mEventStr = mEventStr;
    }
}
