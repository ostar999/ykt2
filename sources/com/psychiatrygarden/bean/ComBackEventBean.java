package com.psychiatrygarden.bean;

import java.io.Serializable;

/* loaded from: classes5.dex */
public class ComBackEventBean implements Serializable {
    private boolean IsLisnxi;
    private long[] list_auestion;
    private String tag;
    private String title;

    public ComBackEventBean(String title, boolean IsLisnxi, long[] list_auestion, String tag) {
        this.title = title;
        this.IsLisnxi = IsLisnxi;
        this.list_auestion = list_auestion;
        this.tag = tag;
    }

    public long[] getList_auestion() {
        return this.list_auestion;
    }

    public String getTag() {
        return this.tag;
    }

    public String getTitle() {
        return this.title;
    }

    public boolean isLisnxi() {
        return this.IsLisnxi;
    }

    public void setLisnxi(boolean lisnxi) {
        this.IsLisnxi = lisnxi;
    }

    public void setList_auestion(long[] list_auestion) {
        this.list_auestion = list_auestion;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ComBackEventBean(String title, boolean IsLisnxi, String tag) {
        this.title = title;
        this.IsLisnxi = IsLisnxi;
        this.tag = tag;
    }
}
