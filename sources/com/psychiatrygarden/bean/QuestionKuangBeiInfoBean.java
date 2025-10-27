package com.psychiatrygarden.bean;

/* loaded from: classes5.dex */
public class QuestionKuangBeiInfoBean {
    private String book;
    private String chapter_id;
    private String chapter_parent_id;
    private String level;
    private Long number;
    private String page;
    private Long recite_id;
    private String source;
    private String title;
    private String title_title;

    public QuestionKuangBeiInfoBean() {
    }

    public String getBook() {
        return this.book;
    }

    public String getChapter_id() {
        return this.chapter_id;
    }

    public String getChapter_parent_id() {
        return this.chapter_parent_id;
    }

    public String getLevel() {
        return this.level;
    }

    public Long getNumber() {
        return this.number;
    }

    public String getPage() {
        return this.page;
    }

    public Long getRecite_id() {
        return this.recite_id;
    }

    public String getSource() {
        return this.source;
    }

    public String getTitle() {
        return this.title;
    }

    public String getTitle_title() {
        return this.title_title;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public void setChapter_id(String chapter_id) {
        this.chapter_id = chapter_id;
    }

    public void setChapter_parent_id(String chapter_parent_id) {
        this.chapter_parent_id = chapter_parent_id;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public void setRecite_id(Long recite_id) {
        this.recite_id = recite_id;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTitle_title(String title_title) {
        this.title_title = title_title;
    }

    public QuestionKuangBeiInfoBean(Long recite_id) {
        this.recite_id = recite_id;
    }

    public QuestionKuangBeiInfoBean(Long recite_id, String title, String title_title, String level, String book, String page, String chapter_id, String chapter_parent_id, String source, Long number) {
        this.recite_id = recite_id;
        this.title = title;
        this.title_title = title_title;
        this.level = level;
        this.book = book;
        this.page = page;
        this.chapter_id = chapter_id;
        this.chapter_parent_id = chapter_parent_id;
        this.source = source;
        this.number = number;
    }
}
