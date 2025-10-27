package com.psychiatrygarden.bean;

/* loaded from: classes5.dex */
public class QuestionOptionBean {
    private String img;
    private String isanswer;
    private String key;
    private Long question_id;
    private String type;
    private String value;

    public QuestionOptionBean() {
        this.type = "0";
    }

    public String getImg() {
        return this.img;
    }

    public String getIsanswer() {
        return this.isanswer;
    }

    public String getKey() {
        return this.key;
    }

    public Long getQuestion_id() {
        return this.question_id;
    }

    public String getType() {
        return this.type;
    }

    public String getValue() {
        return this.value;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setIsanswer(String isanswer) {
        this.isanswer = isanswer;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setQuestion_id(Long question_id) {
        this.question_id = question_id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public QuestionOptionBean(Long question_id, String img, String value, String key, String type, String isanswer) {
        this.question_id = question_id;
        this.img = img;
        this.value = value;
        this.key = key;
        this.type = type;
        this.isanswer = isanswer;
    }
}
