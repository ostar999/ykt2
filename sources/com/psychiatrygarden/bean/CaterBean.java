package com.psychiatrygarden.bean;

import java.io.Serializable;

/* loaded from: classes5.dex */
public class CaterBean implements Serializable {
    public String count = "0";
    public String id;

    public String getCount() {
        return this.count;
    }

    public String getId() {
        return this.id;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public void setId(String id) {
        this.id = id;
    }
}
