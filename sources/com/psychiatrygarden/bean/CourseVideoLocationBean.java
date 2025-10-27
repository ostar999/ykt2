package com.psychiatrygarden.bean;

/* loaded from: classes5.dex */
public class CourseVideoLocationBean {
    public String id;
    public String is_have;
    public String is_select;
    public Long media_id;
    public String parent_id;
    public Long sort;
    public String title;
    public String video_count;
    public boolean isSelect = false;
    public boolean isAllSelect = false;

    public CourseVideoLocationBean() {
    }

    public String getId() {
        return this.id;
    }

    public String getIs_have() {
        return this.is_have;
    }

    public String getIs_select() {
        return this.is_select;
    }

    public Long getMedia_id() {
        return this.media_id;
    }

    public String getParent_id() {
        return this.parent_id;
    }

    public Long getSort() {
        return this.sort;
    }

    public String getTitle() {
        return this.title;
    }

    public String getVideo_count() {
        return this.video_count;
    }

    public boolean isAllSelect() {
        return this.isAllSelect;
    }

    public boolean isSelect() {
        return this.isSelect;
    }

    public void setAllSelect(boolean allSelect) {
        this.isAllSelect = allSelect;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setIs_have(String is_have) {
        this.is_have = is_have;
    }

    public void setIs_select(String is_select) {
        this.is_select = is_select;
    }

    public void setMedia_id(Long media_id) {
        this.media_id = media_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public void setSelect(boolean select) {
        this.isSelect = select;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setVideo_count(String video_count) {
        this.video_count = video_count;
    }

    public CourseVideoLocationBean(Long media_id) {
        this.media_id = media_id;
    }

    public CourseVideoLocationBean(String id, String title, String parent_id, String video_count, Long sort, String is_select, String is_have) {
        this.id = id;
        this.title = title;
        this.parent_id = parent_id;
        this.video_count = video_count;
        this.sort = sort;
        this.is_select = is_select;
        this.is_have = is_have;
    }
}
