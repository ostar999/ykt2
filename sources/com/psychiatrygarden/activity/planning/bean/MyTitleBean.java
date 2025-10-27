package com.psychiatrygarden.activity.planning.bean;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class MyTitleBean {
    List<MycalendarBean> list = new ArrayList();
    String title;

    public List<MycalendarBean> getList() {
        return this.list;
    }

    public String getTitle() {
        return this.title;
    }

    public void setList(List<MycalendarBean> list) {
        this.list = list;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
