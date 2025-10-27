package com.psychiatrygarden.bean;

import java.io.Serializable;

/* loaded from: classes5.dex */
public class PhyicBean implements Serializable {
    public int donecount;
    public String nameevent;
    public int rightcount;
    public int worngcount;

    public int getDonecount() {
        return this.donecount;
    }

    public String getNameevent() {
        return this.nameevent;
    }

    public int getRightcount() {
        return this.rightcount;
    }

    public int getWorngcount() {
        return this.worngcount;
    }

    public void setDonecount(int donecount) {
        this.donecount = donecount;
    }

    public void setNameevent(String nameevent) {
        this.nameevent = nameevent;
    }

    public void setRightcount(int rightcount) {
        this.rightcount = rightcount;
    }

    public void setWorngcount(int worngcount) {
        this.worngcount = worngcount;
    }
}
