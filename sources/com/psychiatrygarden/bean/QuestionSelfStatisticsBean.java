package com.psychiatrygarden.bean;

/* loaded from: classes5.dex */
public class QuestionSelfStatisticsBean {
    private Long question_id;
    private Integer selfanswernum;
    private Integer selfwrongnum;

    public QuestionSelfStatisticsBean() {
    }

    public Long getQuestion_id() {
        return this.question_id;
    }

    public Integer getSelfanswernum() {
        return this.selfanswernum;
    }

    public Integer getSelfwrongnum() {
        return this.selfwrongnum;
    }

    public void setQuestion_id(Long question_id) {
        this.question_id = question_id;
    }

    public void setSelfanswernum(Integer selfanswernum) {
        this.selfanswernum = selfanswernum;
    }

    public void setSelfwrongnum(Integer selfwrongnum) {
        this.selfwrongnum = selfwrongnum;
    }

    public QuestionSelfStatisticsBean(Long question_id) {
        this.question_id = question_id;
    }

    public QuestionSelfStatisticsBean(Long question_id, Integer selfanswernum, Integer selfwrongnum) {
        this.question_id = question_id;
        this.selfanswernum = selfanswernum;
        this.selfwrongnum = selfwrongnum;
    }
}
