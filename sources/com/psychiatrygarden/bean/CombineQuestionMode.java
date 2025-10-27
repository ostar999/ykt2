package com.psychiatrygarden.bean;

/* loaded from: classes5.dex */
public enum CombineQuestionMode {
    PRACTISE("练习模式", 1),
    TEST("测试模式", 2);

    private String title;
    private int type;

    CombineQuestionMode(String title, int type) {
        this.title = title;
        this.type = type;
    }

    public String getTitle() {
        return this.title;
    }

    public int getType() {
        return this.type;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(int type) {
        this.type = type;
    }
}
