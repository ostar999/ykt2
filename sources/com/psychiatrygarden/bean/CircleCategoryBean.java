package com.psychiatrygarden.bean;

/* loaded from: classes5.dex */
public class CircleCategoryBean {
    private String cover;
    private String description;
    private Long id;
    private Boolean isCollect;
    private String name;
    private String parent_cate;
    private String province_id;
    private String school_id;
    private Long sort;
    private String status;
    private String today_topic_num;

    public CircleCategoryBean() {
    }

    public String getCover() {
        return this.cover;
    }

    public String getDescription() {
        return this.description;
    }

    public Long getId() {
        return this.id;
    }

    public Boolean getIsCollect() {
        return this.isCollect;
    }

    public String getName() {
        return this.name;
    }

    public String getParent_cate() {
        return this.parent_cate;
    }

    public String getProvince_id() {
        return this.province_id;
    }

    public String getSchool_id() {
        return this.school_id;
    }

    public Long getSort() {
        return this.sort;
    }

    public String getStatus() {
        return this.status;
    }

    public String getToday_topic_num() {
        return this.today_topic_num;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setIsCollect(Boolean isCollect) {
        this.isCollect = isCollect;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParent_cate(String parent_cate) {
        this.parent_cate = parent_cate;
    }

    public void setProvince_id(String province_id) {
        this.province_id = province_id;
    }

    public void setSchool_id(String school_id) {
        this.school_id = school_id;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setToday_topic_num(String today_topic_num) {
        this.today_topic_num = today_topic_num;
    }

    public CircleCategoryBean(Long id) {
        this.id = id;
    }

    public CircleCategoryBean(Long id, String cover, String description, String name, String parent_cate, String province_id, String school_id, Long sort, String status, String today_topic_num, Boolean isCollect) {
        this.id = id;
        this.cover = cover;
        this.description = description;
        this.name = name;
        this.parent_cate = parent_cate;
        this.province_id = province_id;
        this.school_id = school_id;
        this.sort = sort;
        this.status = status;
        this.today_topic_num = today_topic_num;
        this.isCollect = isCollect;
    }
}
