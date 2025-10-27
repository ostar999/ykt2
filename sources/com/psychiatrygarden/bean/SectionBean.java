package com.psychiatrygarden.bean;

/* loaded from: classes5.dex */
public class SectionBean {
    private Long chapter_id;
    private String chapter_parent_id;
    private String isxueshuo;
    private String iszhuanshuo;
    private String school_year;
    private Long sort;
    private String title;

    public SectionBean() {
    }

    public Long getChapter_id() {
        return this.chapter_id;
    }

    public String getChapter_parent_id() {
        return this.chapter_parent_id;
    }

    public String getIsxueshuo() {
        return this.isxueshuo;
    }

    public String getIszhuanshuo() {
        return this.iszhuanshuo;
    }

    public String getSchool_year() {
        return this.school_year;
    }

    public Long getSort() {
        return this.sort;
    }

    public String getTitle() {
        return this.title;
    }

    public void setChapter_id(Long chapter_id) {
        this.chapter_id = chapter_id;
    }

    public void setChapter_parent_id(String chapter_parent_id) {
        this.chapter_parent_id = chapter_parent_id;
    }

    public void setIsxueshuo(String isxueshuo) {
        this.isxueshuo = isxueshuo;
    }

    public void setIszhuanshuo(String iszhuanshuo) {
        this.iszhuanshuo = iszhuanshuo;
    }

    public SectionBean setSchool_year(String school_year) {
        this.school_year = school_year;
        return this;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public SectionBean(Long chapter_id) {
        this.chapter_id = chapter_id;
    }

    public SectionBean(Long chapter_id, String chapter_parent_id, String title, Long sort, String isxueshuo, String iszhuanshuo, String school_year) {
        this.chapter_id = chapter_id;
        this.chapter_parent_id = chapter_parent_id;
        this.title = title;
        this.sort = sort;
        this.isxueshuo = isxueshuo;
        this.iszhuanshuo = iszhuanshuo;
        this.school_year = school_year;
    }
}
