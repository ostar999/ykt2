package com.psychiatrygarden.activity.courselist.bean;

import java.io.Serializable;

/* loaded from: classes5.dex */
public class ViedeoStatusChangeBean implements Serializable {
    public String c_id;
    public String id;
    public String vid;
    public String note = "0";
    public String collect = "0";
    public String issee = "0";
    public String watch_permission = "0";

    public String getC_id() {
        return this.c_id;
    }

    public String getCollect() {
        return this.collect;
    }

    public String getId() {
        return this.id;
    }

    public String getIssee() {
        return this.issee;
    }

    public String getNote() {
        return this.note;
    }

    public String getVid() {
        return this.vid;
    }

    public String getWatch_permission() {
        return this.watch_permission;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    public void setCollect(String collect) {
        this.collect = collect;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setIssee(String issee) {
        this.issee = issee;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public void setWatch_permission(String watch_permission) {
        this.watch_permission = watch_permission;
    }
}
