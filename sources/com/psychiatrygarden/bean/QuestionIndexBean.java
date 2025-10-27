package com.psychiatrygarden.bean;

/* loaded from: classes5.dex */
public class QuestionIndexBean {
    private String questionId;
    private boolean select;
    private String title;

    public QuestionIndexBean(String title, String questionId, boolean select) {
        this.title = title;
        this.questionId = questionId;
        this.select = select;
    }

    public String getQuestionId() {
        return this.questionId;
    }

    public String getTitle() {
        return this.title;
    }

    public boolean isSelect() {
        return this.select;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
