package com.psychiatrygarden.bean;

/* loaded from: classes5.dex */
public class QuestionVideoLocationBean {
    private Long question_id;
    private Long video_location;

    public QuestionVideoLocationBean() {
    }

    public Long getQuestion_id() {
        return this.question_id;
    }

    public Long getVideo_location() {
        return this.video_location;
    }

    public void setQuestion_id(Long question_id) {
        this.question_id = question_id;
    }

    public void setVideo_location(Long video_location) {
        this.video_location = video_location;
    }

    public QuestionVideoLocationBean(Long question_id) {
        this.question_id = question_id;
    }

    public QuestionVideoLocationBean(Long question_id, Long video_location) {
        this.question_id = question_id;
        this.video_location = video_location;
    }
}
