package com.psychiatrygarden.bean;

/* loaded from: classes5.dex */
public class SectionPartBean {
    private String am;
    private String parent_id;
    private Long part_id;
    private String pm;
    private Long sort;
    private String title;

    public SectionPartBean() {
    }

    public String getAm() {
        return this.am;
    }

    public String getParent_id() {
        return this.parent_id;
    }

    public Long getPart_id() {
        return this.part_id;
    }

    public String getPm() {
        return this.pm;
    }

    public Long getSort() {
        return this.sort;
    }

    public String getTitle() {
        return this.title;
    }

    public void setAm(String am) {
        this.am = am;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public void setPart_id(Long part_id) {
        this.part_id = part_id;
    }

    public void setPm(String pm) {
        this.pm = pm;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public SectionPartBean(Long part_id, String parent_id, String title, Long sort, String am, String pm) {
        this.part_id = part_id;
        this.parent_id = parent_id;
        this.title = title;
        this.sort = sort;
        this.am = am;
        this.pm = pm;
    }
}
