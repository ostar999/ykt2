package com.psychiatrygarden.bean;

/* loaded from: classes5.dex */
public class CircleCategoryLableBean {
    private String cate_id;
    private Long id;
    private Boolean isCheck;
    private String name;
    private String parent_cate;

    public CircleCategoryLableBean() {
    }

    public String getCate_id() {
        return this.cate_id;
    }

    public Long getId() {
        return this.id;
    }

    public Boolean getIsCheck() {
        return this.isCheck;
    }

    public String getName() {
        return this.name;
    }

    public String getParent_cate() {
        return this.parent_cate;
    }

    public void setCate_id(String cate_id) {
        this.cate_id = cate_id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setIsCheck(Boolean isCheck) {
        this.isCheck = isCheck;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParent_cate(String parent_cate) {
        this.parent_cate = parent_cate;
    }

    public CircleCategoryLableBean(Long id, String name, String cate_id, String parent_cate, Boolean isCheck) {
        this.id = id;
        this.name = name;
        this.cate_id = cate_id;
        this.parent_cate = parent_cate;
        this.isCheck = isCheck;
    }
}
