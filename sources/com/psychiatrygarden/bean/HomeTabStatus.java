package com.psychiatrygarden.bean;

import java.io.Serializable;

/* loaded from: classes5.dex */
public class HomeTabStatus implements Serializable {
    public String mEvestr;
    public int position;

    public HomeTabStatus(String mEvestr, int position) {
        this.mEvestr = mEvestr;
        this.position = position;
    }
}
