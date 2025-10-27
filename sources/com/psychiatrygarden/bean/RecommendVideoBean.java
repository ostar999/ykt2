package com.psychiatrygarden.bean;

import cn.hutool.core.text.StrPool;

/* loaded from: classes5.dex */
public class RecommendVideoBean {
    private String cover;
    private String current_see;
    private String duration;
    private String id;
    private String is_end;
    private boolean lastSee;
    private String see;
    private String size;
    private String teacher_id;
    private String teacher_name;
    private String title;
    private String vid;
    private String video_id;

    public String getCover() {
        return this.cover;
    }

    public String getCurrent_see() {
        String str = this.current_see;
        if (str == null || !str.contains(StrPool.DOT)) {
            return this.current_see;
        }
        String str2 = this.current_see;
        return str2.substring(0, str2.lastIndexOf(StrPool.DOT));
    }

    public String getDuration() {
        String str = this.duration;
        if (str == null || !str.contains(StrPool.DOT)) {
            return this.duration;
        }
        String str2 = this.duration;
        return str2.substring(0, str2.lastIndexOf(StrPool.DOT));
    }

    public String getId() {
        return this.id;
    }

    public String getIs_end() {
        return this.is_end;
    }

    public String getSee() {
        String str = this.see;
        if (str == null || !str.contains(StrPool.DOT)) {
            return this.see;
        }
        String str2 = this.see;
        return str2.substring(0, str2.lastIndexOf(StrPool.DOT));
    }

    public String getSize() {
        return this.size;
    }

    public String getTeacher_id() {
        return this.teacher_id;
    }

    public String getTeacher_name() {
        return this.teacher_name;
    }

    public String getTitle() {
        return this.title;
    }

    public String getVid() {
        return this.vid;
    }

    public String getVideo_id() {
        return this.video_id;
    }

    public boolean isLastSee() {
        return this.lastSee;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setCurrent_see(String current_see) {
        this.current_see = current_see;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setIs_end(String is_end) {
        this.is_end = is_end;
    }

    public void setLastSee(boolean lastSee) {
        this.lastSee = lastSee;
    }

    public void setSee(String see) {
        this.see = see;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setTeacher_id(String teacher_id) {
        this.teacher_id = teacher_id;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }
}
