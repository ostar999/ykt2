package com.psychiatrygarden.bean;

/* loaded from: classes5.dex */
public class CourseDirectoryTreeBean {
    private CourseDirectoryContentItem contentItem;
    private String id;
    private CourseDirectoryItemData item;
    private boolean select = false;
    private String title;

    public CourseDirectoryTreeBean(CourseDirectoryContentItem contentItem, CourseDirectoryItemData item) {
        this.contentItem = contentItem;
        this.item = item;
    }

    public CourseDirectoryContentItem getContentItem() {
        return this.contentItem;
    }

    public String getId() {
        return this.id;
    }

    public CourseDirectoryItemData getItem() {
        return this.item;
    }

    public String getTitle() {
        return this.title;
    }

    public boolean isSelect() {
        return this.select;
    }

    public void setContentItem(CourseDirectoryContentItem contentItem) {
        this.contentItem = contentItem;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setItem(CourseDirectoryItemData item) {
        this.item = item;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public CourseDirectoryTreeBean(String title, String id) {
        this.title = title;
        this.id = id;
    }
}
