package com.psychiatrygarden.activity.courselist.bean;

import java.io.Serializable;

/* loaded from: classes5.dex */
public class CurricuTheRestBean implements Serializable {
    public String id = "";
    public String vid = "";
    public String see = "0";

    public String getId() {
        return this.id;
    }

    public String getSee() {
        return this.see;
    }

    public String getVid() {
        return this.vid;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSee(String see) {
        this.see = see;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }
}
