package com.psychiatrygarden.activity.purchase.beans;

import java.io.Serializable;

/* loaded from: classes5.dex */
public class BaseInfo implements Serializable {
    protected String Id;
    protected boolean isChoosed;
    protected String name;

    public BaseInfo() {
    }

    public String getId() {
        return this.Id;
    }

    public String getName() {
        return this.name;
    }

    public boolean isChoosed() {
        return this.isChoosed;
    }

    public void setChoosed(boolean isChoosed) {
        this.isChoosed = isChoosed;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BaseInfo(String id, String name) {
        this.Id = id;
        this.name = name;
    }
}
