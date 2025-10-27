package com.psychiatrygarden.bean;

/* loaded from: classes5.dex */
public class NotesBeanBei {
    private Long cate_num;
    private String chapter_id;
    private String chapter_parent_id;
    private String content;
    private String is_practice;
    private String isgaopinkaodian;
    private String isxueshuo;
    private String iszhuanshuo;
    private String media_url;
    private Long question_id;
    private String unit;
    private String year;
    private Long year_num;

    public NotesBeanBei() {
    }

    public Long getCate_num() {
        return this.cate_num;
    }

    public String getChapter_id() {
        return this.chapter_id;
    }

    public String getChapter_parent_id() {
        return this.chapter_parent_id;
    }

    public String getContent() {
        return this.content;
    }

    public String getIs_practice() {
        return this.is_practice;
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

    public Long getQuestion_id() {
        return this.question_id;
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

    public void setCate_num(Long cate_num) {
        this.cate_num = cate_num;
    }

    public void setChapter_id(String chapter_id) {
        this.chapter_id = chapter_id;
    }

    public void setChapter_parent_id(String chapter_parent_id) {
        this.chapter_parent_id = chapter_parent_id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setIs_practice(String is_practice) {
        this.is_practice = is_practice;
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

    public void setQuestion_id(Long question_id) {
        this.question_id = question_id;
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

    public NotesBeanBei(Long question_id) {
        this.question_id = question_id;
    }

    public NotesBeanBei(Long question_id, String chapter_parent_id, String chapter_id, String year, Long cate_num, Long year_num, String content, String media_url, String isxueshuo, String iszhuanshuo, String isgaopinkaodian, String is_practice, String unit) {
        this.question_id = question_id;
        this.chapter_parent_id = chapter_parent_id;
        this.chapter_id = chapter_id;
        this.year = year;
        this.cate_num = cate_num;
        this.year_num = year_num;
        this.content = content;
        this.media_url = media_url;
        this.isxueshuo = isxueshuo;
        this.iszhuanshuo = iszhuanshuo;
        this.isgaopinkaodian = isgaopinkaodian;
        this.is_practice = is_practice;
        this.unit = unit;
    }
}
