package com.psychiatrygarden.bean;

/* loaded from: classes5.dex */
public class TestDataBean {
    private String groupName;
    private String text;

    public TestDataBean(String text, String groupName) {
        this.text = text;
        this.groupName = groupName;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public String getText() {
        return this.text;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setText(String text) {
        this.text = text;
    }
}
