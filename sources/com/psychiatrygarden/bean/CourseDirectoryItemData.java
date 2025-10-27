package com.psychiatrygarden.bean;

import java.util.List;

/* loaded from: classes5.dex */
public class CourseDirectoryItemData {
    private String chapter_id;
    private List<CourseDirectoryItemData> children;
    private List<CourseDirectoryContentItem> content;
    private String course_id;
    private String pid;
    private String publish_time;
    private String sort;
    private String title;

    public String getChapter_id() {
        return this.chapter_id;
    }

    public List<CourseDirectoryItemData> getChildren() {
        return this.children;
    }

    public List<CourseDirectoryContentItem> getContent() {
        return this.content;
    }

    public String getCourse_id() {
        return this.course_id;
    }

    public String getPid() {
        return this.pid;
    }

    public String getPublish_time() {
        return this.publish_time;
    }

    public String getSort() {
        return this.sort;
    }

    public String getTitle() {
        return this.title;
    }

    public void setChapter_id(String chapter_id) {
        this.chapter_id = chapter_id;
    }

    public void setChildren(List<CourseDirectoryItemData> children) {
        this.children = children;
    }

    public void setContent(List<CourseDirectoryContentItem> content) {
        this.content = content;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public void setPublish_time(String publish_time) {
        this.publish_time = publish_time;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
