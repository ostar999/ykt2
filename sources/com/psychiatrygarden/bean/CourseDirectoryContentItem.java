package com.psychiatrygarden.bean;

import cn.hutool.core.lang.RegexPool;

/* loaded from: classes5.dex */
public class CourseDirectoryContentItem {
    private String activity_id;
    private String app_id;
    private String app_secret;
    private String chapter_id;
    private String count;
    private String course_cover;
    private String course_id;
    private String course_title;
    private String cover;
    private String current_duration;
    private String current_see;
    private String duration;
    private String end_live_time;
    private String free_watch;
    private String hasPermission;
    private String is_end;
    private String is_hide_teacher;
    private String is_permission;
    private String obj_id;
    private String publish_time;
    private String room_id;
    private String see;
    private String size;
    private String start_live_time;
    private String teacher_name;
    private String title;
    private String type;
    private String user_id;
    private String vid;
    private String video_id;

    public String getActivity_id() {
        return this.activity_id;
    }

    public String getApp_id() {
        return this.app_id;
    }

    public String getApp_secret() {
        return this.app_secret;
    }

    public String getChapter_id() {
        return this.chapter_id;
    }

    public String getCount() {
        return this.count;
    }

    public String getCourse_cover() {
        return this.course_cover;
    }

    public String getCourse_id() {
        return this.course_id;
    }

    public String getCourse_title() {
        return this.course_title;
    }

    public String getCover() {
        return this.cover;
    }

    public String getCurrent_duration() {
        return this.current_duration;
    }

    public String getCurrent_see() {
        return this.current_see;
    }

    public String getDuration() {
        return this.duration;
    }

    public String getEnd_live_time() {
        return this.end_live_time;
    }

    public String getFree_watch() {
        return this.free_watch;
    }

    public String getHasPermission() {
        return this.hasPermission;
    }

    public String getIs_end() {
        return this.is_end;
    }

    public String getIs_hide_teacher() {
        return this.is_hide_teacher;
    }

    public String getIs_permission() {
        return this.is_permission;
    }

    public String getObj_id() {
        return this.obj_id;
    }

    public String getPublish_time() {
        return this.publish_time;
    }

    public String getRoom_id() {
        return this.room_id;
    }

    public String getSee() {
        return this.see;
    }

    public String getSize() {
        return this.size;
    }

    public String getStart_live_time() {
        return this.start_live_time;
    }

    public String getTeacher_name() {
        return this.teacher_name;
    }

    public String getTitle() {
        return this.title;
    }

    public String getType() {
        return this.type;
    }

    public String getUser_id() {
        return this.user_id;
    }

    public String getVid() {
        return this.vid;
    }

    public long getVideoSize() {
        String str = this.size;
        if (str == null || !str.matches(RegexPool.NUMBERS)) {
            return 0L;
        }
        return Long.parseLong(this.size);
    }

    public String getVideo_id() {
        return this.video_id;
    }

    public void setActivity_id(String activity_id) {
        this.activity_id = activity_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public void setApp_secret(String app_secret) {
        this.app_secret = app_secret;
    }

    public void setChapter_id(String chapter_id) {
        this.chapter_id = chapter_id;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public void setCourse_cover(String course_cover) {
        this.course_cover = course_cover;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public void setCourse_title(String course_title) {
        this.course_title = course_title;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setCurrent_duration(String current_duration) {
        this.current_duration = current_duration;
    }

    public void setCurrent_see(String current_see) {
        this.current_see = current_see;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setEnd_live_time(String end_live_time) {
        this.end_live_time = end_live_time;
    }

    public void setFree_watch(String free_watch) {
        this.free_watch = free_watch;
    }

    public void setHasPermission(String hasPermission) {
        this.hasPermission = hasPermission;
    }

    public void setIs_end(String is_end) {
        this.is_end = is_end;
    }

    public void setIs_hide_teacher(String is_hide_teacher) {
        this.is_hide_teacher = is_hide_teacher;
    }

    public void setIs_permission(String is_permission) {
        this.is_permission = is_permission;
    }

    public void setObj_id(String obj_id) {
        this.obj_id = obj_id;
    }

    public void setPublish_time(String publish_time) {
        this.publish_time = publish_time;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public void setSee(String see) {
        this.see = see;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setStart_live_time(String start_live_time) {
        this.start_live_time = start_live_time;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }
}
