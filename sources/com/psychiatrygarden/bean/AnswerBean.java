package com.psychiatrygarden.bean;

import java.io.Serializable;

/* loaded from: classes5.dex */
public class AnswerBean implements Serializable {
    private String answer;
    private String duration;
    private String is_right;
    private String knowledge_id;
    private String question_id;
    private String subject_id;

    public String getAnswer() {
        return this.answer;
    }

    public String getDuration() {
        return this.duration;
    }

    public String getIs_right() {
        return this.is_right;
    }

    public String getKnowledge_id() {
        return this.knowledge_id;
    }

    public String getQuestion_id() {
        return this.question_id;
    }

    public String getSubject_id() {
        return this.subject_id;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setIs_right(String is_right) {
        this.is_right = is_right;
    }

    public void setKnowledge_id(String knowledge_id) {
        this.knowledge_id = knowledge_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }
}
