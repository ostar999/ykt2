package com.psychiatrygarden.bean;

/* loaded from: classes5.dex */
public class AnsweredQuestionBean {
    private String answer;
    private String answer_day;
    private String answer_month;
    private String answer_year;
    private String chapter_id;
    private String chapter_parent_id;
    private String is_practice;
    private String is_right;
    private String isgaopinkaodian;
    private String isxueshuo;
    private String iszhuanshuo;
    private String media_url;
    private String number;
    private String parent_name;
    private Long part_id;
    private Long part_num_am;
    private Long part_num_pm;
    private Long part_parent_id;
    private Long question_id;
    private String right_answer;
    private String type;
    private String unit;
    private String year;
    private Long year_num;

    public AnsweredQuestionBean() {
        this.is_right = "0";
    }

    public String getAnswer() {
        return this.answer;
    }

    public String getAnswer_day() {
        return this.answer_day;
    }

    public String getAnswer_month() {
        return this.answer_month;
    }

    public String getAnswer_year() {
        return this.answer_year;
    }

    public String getChapter_id() {
        return this.chapter_id;
    }

    public String getChapter_parent_id() {
        return this.chapter_parent_id;
    }

    public String getIs_practice() {
        return this.is_practice;
    }

    public String getIs_right() {
        return this.is_right;
    }

    public String getIsgaopinkaodian() {
        return this.isgaopinkaodian;
    }

    public String getIsxueshuo() {
        return this.isxueshuo;
    }

    public String getIszhuanshuo() {
        return this.iszhuanshuo;
    }

    public String getMedia_url() {
        return this.media_url;
    }

    public String getNumber() {
        return this.number;
    }

    public String getParent_name() {
        return this.parent_name;
    }

    public Long getPart_id() {
        return this.part_id;
    }

    public Long getPart_num_am() {
        return this.part_num_am;
    }

    public Long getPart_num_pm() {
        return this.part_num_pm;
    }

    public Long getPart_parent_id() {
        return this.part_parent_id;
    }

    public Long getQuestion_id() {
        return this.question_id;
    }

    public String getRight_answer() {
        return this.right_answer;
    }

    public String getType() {
        return this.type;
    }

    public String getUnit() {
        return this.unit;
    }

    public String getYear() {
        return this.year;
    }

    public Long getYear_num() {
        return this.year_num;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setAnswer_day(String answer_day) {
        this.answer_day = answer_day;
    }

    public void setAnswer_month(String answer_month) {
        this.answer_month = answer_month;
    }

    public void setAnswer_year(String answer_year) {
        this.answer_year = answer_year;
    }

    public void setChapter_id(String chapter_id) {
        this.chapter_id = chapter_id;
    }

    public void setChapter_parent_id(String chapter_parent_id) {
        this.chapter_parent_id = chapter_parent_id;
    }

    public void setIs_practice(String is_practice) {
        this.is_practice = is_practice;
    }

    public void setIs_right(String is_right) {
        this.is_right = is_right;
    }

    public void setIsgaopinkaodian(String isgaopinkaodian) {
        this.isgaopinkaodian = isgaopinkaodian;
    }

    public void setIsxueshuo(String isxueshuo) {
        this.isxueshuo = isxueshuo;
    }

    public void setIszhuanshuo(String iszhuanshuo) {
        this.iszhuanshuo = iszhuanshuo;
    }

    public void setMedia_url(String media_url) {
        this.media_url = media_url;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setParent_name(String parent_name) {
        this.parent_name = parent_name;
    }

    public void setPart_id(Long part_id) {
        this.part_id = part_id;
    }

    public void setPart_num_am(Long part_num_am) {
        this.part_num_am = part_num_am;
    }

    public void setPart_num_pm(Long part_num_pm) {
        this.part_num_pm = part_num_pm;
    }

    public void setPart_parent_id(Long part_parent_id) {
        this.part_parent_id = part_parent_id;
    }

    public void setQuestion_id(Long question_id) {
        this.question_id = question_id;
    }

    public void setRight_answer(String right_answer) {
        this.right_answer = right_answer;
    }

    public AnsweredQuestionBean setType(String type) {
        this.type = type;
        return this;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setYear_num(Long year_num) {
        this.year_num = year_num;
    }

    public AnsweredQuestionBean(Long question_id) {
        this.is_right = "0";
        this.question_id = question_id;
    }

    public AnsweredQuestionBean(Long question_id, String answer, String chapter_parent_id, String chapter_id, String is_right, String right_answer, String number, Long year_num, String parent_name, String media_url, String year, String isxueshuo, String iszhuanshuo, String isgaopinkaodian, String answer_year, String answer_month, String answer_day, String is_practice, String unit, Long part_id, Long part_parent_id, Long part_num_am, Long part_num_pm, String type) {
        this.question_id = question_id;
        this.answer = answer;
        this.chapter_parent_id = chapter_parent_id;
        this.chapter_id = chapter_id;
        this.is_right = is_right;
        this.right_answer = right_answer;
        this.number = number;
        this.year_num = year_num;
        this.parent_name = parent_name;
        this.media_url = media_url;
        this.year = year;
        this.isxueshuo = isxueshuo;
        this.iszhuanshuo = iszhuanshuo;
        this.isgaopinkaodian = isgaopinkaodian;
        this.answer_year = answer_year;
        this.answer_month = answer_month;
        this.answer_day = answer_day;
        this.is_practice = is_practice;
        this.unit = unit;
        this.part_id = part_id;
        this.part_parent_id = part_parent_id;
        this.part_num_am = part_num_am;
        this.part_num_pm = part_num_pm;
        this.type = type;
    }
}
