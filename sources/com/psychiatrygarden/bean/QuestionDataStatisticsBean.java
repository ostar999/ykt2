package com.psychiatrygarden.bean;

/* loaded from: classes5.dex */
public class QuestionDataStatisticsBean {
    private Integer all_right_count;
    private Integer all_wrong_count;
    private Long collection;
    private Integer comment_count;
    private String is_comment;
    private String is_praise;
    private Long question_id;
    private Integer right_count;
    private Integer wrong_count;
    private Long year_num;

    public QuestionDataStatisticsBean() {
        this.all_right_count = 0;
        this.all_wrong_count = 0;
        this.comment_count = 0;
        this.right_count = 0;
        this.wrong_count = 0;
        this.year_num = 0L;
        this.collection = 0L;
        this.is_praise = "0";
        this.is_comment = "0";
    }

    public Integer getAll_right_count() {
        return this.all_right_count;
    }

    public Integer getAll_wrong_count() {
        return this.all_wrong_count;
    }

    public Long getCollection() {
        return this.collection;
    }

    public Integer getComment_count() {
        return this.comment_count;
    }

    public String getIs_comment() {
        return this.is_comment;
    }

    public String getIs_praise() {
        return this.is_praise;
    }

    public Long getQuestion_id() {
        return this.question_id;
    }

    public Integer getRight_count() {
        return this.right_count;
    }

    public Integer getWrong_count() {
        return this.wrong_count;
    }

    public Long getYear_num() {
        return this.year_num;
    }

    public void setAll_right_count(Integer all_right_count) {
        this.all_right_count = all_right_count;
    }

    public void setAll_wrong_count(Integer all_wrong_count) {
        this.all_wrong_count = all_wrong_count;
    }

    public void setCollection(Long collection) {
        this.collection = collection;
    }

    public void setComment_count(Integer comment_count) {
        this.comment_count = comment_count;
    }

    public void setIs_comment(String is_comment) {
        this.is_comment = is_comment;
    }

    public void setIs_praise(String is_praise) {
        this.is_praise = is_praise;
    }

    public void setQuestion_id(Long question_id) {
        this.question_id = question_id;
    }

    public void setRight_count(Integer right_count) {
        this.right_count = right_count;
    }

    public void setWrong_count(Integer wrong_count) {
        this.wrong_count = wrong_count;
    }

    public void setYear_num(Long year_num) {
        this.year_num = year_num;
    }

    public QuestionDataStatisticsBean(Long question_id) {
        this.all_right_count = 0;
        this.all_wrong_count = 0;
        this.comment_count = 0;
        this.right_count = 0;
        this.wrong_count = 0;
        this.year_num = 0L;
        this.collection = 0L;
        this.is_praise = "0";
        this.is_comment = "0";
        this.question_id = question_id;
    }

    public QuestionDataStatisticsBean(Long question_id, Integer all_right_count, Integer all_wrong_count, Integer comment_count, Integer right_count, Integer wrong_count, Long year_num, Long collection, String is_comment, String is_praise) {
        this.all_right_count = 0;
        this.all_wrong_count = 0;
        this.comment_count = 0;
        this.right_count = 0;
        this.wrong_count = 0;
        this.year_num = 0L;
        this.question_id = question_id;
        this.all_right_count = all_right_count;
        this.all_wrong_count = all_wrong_count;
        this.comment_count = comment_count;
        this.right_count = right_count;
        this.wrong_count = wrong_count;
        this.year_num = year_num;
        this.collection = collection;
        this.is_comment = is_comment;
        this.is_praise = is_praise;
    }
}
