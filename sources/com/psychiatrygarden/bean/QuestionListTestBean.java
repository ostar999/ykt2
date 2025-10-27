package com.psychiatrygarden.bean;

import java.io.Serializable;
import java.util.List;

/* loaded from: classes5.dex */
public class QuestionListTestBean implements Serializable {
    private List<String> listQuestions;
    private String questionTitle;
    private String unit;

    public List<String> getListQuestions() {
        return this.listQuestions;
    }

    public String getQuestionTitle() {
        return this.questionTitle;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setListQuestions(List<String> listQuestions) {
        this.listQuestions = listQuestions;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
