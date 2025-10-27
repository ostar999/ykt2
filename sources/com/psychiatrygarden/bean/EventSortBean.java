package com.psychiatrygarden.bean;

import java.io.Serializable;

/* loaded from: classes5.dex */
public class EventSortBean implements Serializable {
    public String mEveStr;
    public String sortType;

    public EventSortBean(String mEveStr, String sortType) {
        this.mEveStr = mEveStr;
        this.sortType = sortType;
    }

    public String getSortType() {
        return this.sortType;
    }

    public String getmEveStr() {
        return this.mEveStr;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public void setmEveStr(String mEveStr) {
        this.mEveStr = mEveStr;
    }
}
