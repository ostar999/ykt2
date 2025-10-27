package com.psychiatrygarden.bean;

/* loaded from: classes5.dex */
public class NewHomeTabBean {
    private boolean select;
    private String title;

    public NewHomeTabBean(String title, boolean select) {
        this.title = title;
        this.select = select;
    }

    public String getTitle() {
        return this.title;
    }

    public boolean isSelect() {
        return this.select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
