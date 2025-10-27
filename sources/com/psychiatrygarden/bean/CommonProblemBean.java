package com.psychiatrygarden.bean;

/* loaded from: classes5.dex */
public class CommonProblemBean {
    private String content;
    private String title;

    public CommonProblemBean(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    public String getTitle() {
        return this.title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
