package com.psychiatrygarden.bean;

/* loaded from: classes5.dex */
public enum CombineQuestionSource {
    ALL("全部", 0),
    WRONG("错题", 1),
    COLLECTION("收藏", 2),
    NOT_DO("未做的", 3);

    private String title;
    private int type;

    CombineQuestionSource(String title, int type) {
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
