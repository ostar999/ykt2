package com.psychiatrygarden.bean;

import com.aliyun.player.source.VidSts;

/* loaded from: classes5.dex */
public class CourseFreeBean {
    public String activity_id;
    public String category_id;
    public String chapter_id;
    public String course_id;
    public double duration_see;
    public String durtions;
    public String expire_str;
    public String fileUrl;
    public long free_watch_time;
    public String type;
    public VidSts vidSts;
    public String watch_permission;

    public CourseFreeBean(VidSts vidSts, String activity_id, String expire_str, String watch_permission, long free_watch_time, String chapter_id, String durtions, String course_id, String category_id, double duration_see, String fileUrl, String type) {
        this.vidSts = vidSts;
        this.activity_id = activity_id;
        this.expire_str = expire_str;
        this.watch_permission = watch_permission;
        this.free_watch_time = free_watch_time;
        this.chapter_id = chapter_id;
        this.durtions = durtions;
        this.course_id = course_id;
        this.category_id = category_id;
        this.duration_see = duration_see;
        this.fileUrl = fileUrl;
        this.type = type;
    }
}
