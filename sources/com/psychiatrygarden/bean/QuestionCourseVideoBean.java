package com.psychiatrygarden.bean;

/* loaded from: classes5.dex */
public class QuestionCourseVideoBean {
    private Long media_id;
    private String media_img;
    private String media_url;
    private Long question_id;

    public QuestionCourseVideoBean() {
    }

    public Long getMedia_id() {
        return this.media_id;
    }

    public String getMedia_img() {
        return this.media_img;
    }

    public String getMedia_url() {
        return this.media_url;
    }

    public Long getQuestion_id() {
        return this.question_id;
    }

    public void setMedia_id(Long media_id) {
        this.media_id = media_id;
    }

    public void setMedia_img(String media_img) {
        this.media_img = media_img;
    }

    public void setMedia_url(String media_url) {
        this.media_url = media_url;
    }

    public void setQuestion_id(Long question_id) {
        this.question_id = question_id;
    }

    public QuestionCourseVideoBean(Long media_id) {
        this.media_id = media_id;
    }

    public QuestionCourseVideoBean(Long media_id, Long question_id, String media_url, String media_img) {
        this.media_id = media_id;
        this.question_id = question_id;
        this.media_url = media_url;
        this.media_img = media_img;
    }
}
