package com.psychiatrygarden.bean;

/* loaded from: classes5.dex */
public class QuestionCombineTabItem {
    private boolean select;
    private String title;

    public QuestionCombineTabItem(boolean select, String title) {
        this.select = select;
        this.title = title;
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
