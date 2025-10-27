package com.psychiatrygarden.bean;

import android.text.TextUtils;
import cn.hutool.core.lang.RegexPool;
import com.aliyun.auth.common.AliyunVodHttpCommon;
import com.google.gson.annotations.SerializedName;
import java.math.BigDecimal;
import java.math.RoundingMode;

/* loaded from: classes5.dex */
public class VideoTopBasicInfo {
    private String activity_id;
    private String chapter_id;
    private String chapter_pid;
    private String chapter_ptitle;
    private String chapter_title;
    private String comment_count;

    @SerializedName("course_title")
    private String courseTitle;
    private String course_id;

    @SerializedName(alternate = {AliyunVodHttpCommon.ImageType.IMAGETYPE_COVER}, value = "course_cover")
    private String cover;

    @SerializedName("current_see")
    private String current_duration;
    private String duration;
    private String finish_count;
    private String free_watch;
    private String handout_num;
    private String isEnd;
    private String is_collection;
    private String is_end;
    private String is_feedback;
    private String is_note;
    private String is_permission;
    private String note_count;
    private String obj_id;
    private String progress;
    private String see;
    private String teacher_name;
    private String title;
    private String type;
    private String vid;
    private String video_count;
    private String video_id;

    private boolean isNumber(String str) {
        return !TextUtils.isEmpty(str) && str.matches(RegexPool.NUMBERS);
    }

    public String getActivity_id() {
        return this.activity_id;
    }

    public String getChapter_id() {
        return this.chapter_id;
    }

    public String getChapter_pid() {
        return this.chapter_pid;
    }

    public String getChapter_ptitle() {
        return this.chapter_ptitle;
    }

    public String getChapter_title() {
        return this.chapter_title;
    }

    public int getCommentCount() {
        if (TextUtils.isEmpty(this.comment_count) || !this.comment_count.matches(RegexPool.NUMBERS)) {
            return 0;
        }
        return Integer.parseInt(this.comment_count);
    }

    public String getComment_count() {
        return this.comment_count;
    }

    public float getCourseProgress() throws NumberFormatException {
        if (isNumber(getVideo_count()) && isNumber(getFinish_count())) {
            int i2 = Integer.parseInt(getVideo_count());
            int i3 = Integer.parseInt(getFinish_count());
            if (i2 != 0 && i3 != 0) {
                return BigDecimal.valueOf((i3 * 1.0f) / i2).multiply(new BigDecimal("100")).setScale(2, RoundingMode.HALF_UP).floatValue();
            }
        }
        return 0.0f;
    }

    public String getCourseTitle() {
        return this.courseTitle;
    }

    public String getCourse_id() {
        return this.course_id;
    }

    public String getCover() {
        return this.cover;
    }

    public String getCurrent_duration() {
        return TextUtils.isEmpty(this.current_duration) ? "0" : this.current_duration;
    }

    public String getDuration() {
        return this.duration;
    }

    public String getFinish_count() {
        return this.finish_count;
    }

    public String getFree_watch() {
        return this.free_watch;
    }

    public String getHandout_num() {
        return this.handout_num;
    }

    public String getIsEnd() {
        return this.isEnd;
    }

    public String getIs_collection() {
        return this.is_collection;
    }

    public String getIs_end() {
        return this.is_end;
    }

    public String getIs_feedback() {
        return this.is_feedback;
    }

    public String getIs_note() {
        return this.is_note;
    }

    public String getIs_permission() {
        return this.is_permission;
    }

    public int getNoteCount() {
        if (TextUtils.isEmpty(this.note_count) || !this.note_count.matches(RegexPool.NUMBERS)) {
            return 0;
        }
        return Integer.parseInt(this.note_count);
    }

    public String getNote_count() {
        return this.note_count;
    }

    public String getObj_id() {
        return this.obj_id;
    }

    public String getProgress() {
        return this.progress;
    }

    public String getSee() {
        return this.see;
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

    public String getVid() {
        return this.vid;
    }

    public String getVideo_count() {
        return this.video_count;
    }

    public String getVideo_id() {
        return this.video_id;
    }

    public void setActivity_id(String activity_id) {
        this.activity_id = activity_id;
    }

    public void setChapter_id(String chapter_id) {
        this.chapter_id = chapter_id;
    }

    public void setChapter_pid(String chapter_pid) {
        this.chapter_pid = chapter_pid;
    }

    public void setChapter_ptitle(String chapter_ptitle) {
        this.chapter_ptitle = chapter_ptitle;
    }

    public void setChapter_title(String chapter_title) {
        this.chapter_title = chapter_title;
    }

    public void setComment_count(String comment_count) {
        this.comment_count = comment_count;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setCurrent_duration(String current_duration) {
        this.current_duration = current_duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setFinish_count(String finish_count) {
        this.finish_count = finish_count;
    }

    public void setFree_watch(String free_watch) {
        this.free_watch = free_watch;
    }

    public void setHandout_num(String handout_num) {
        this.handout_num = handout_num;
    }

    public void setIsEnd(String isEnd) {
        this.isEnd = isEnd;
    }

    public void setIs_collection(String is_collection) {
        this.is_collection = is_collection;
    }

    public void setIs_end(String is_end) {
        this.is_end = is_end;
    }

    public void setIs_feedback(String is_feedback) {
        this.is_feedback = is_feedback;
    }

    public void setIs_note(String is_note) {
        this.is_note = is_note;
    }

    public void setIs_permission(String is_permission) {
        this.is_permission = is_permission;
    }

    public void setNote_count(String note_count) {
        this.note_count = note_count;
    }

    public void setObj_id(String obj_id) {
        this.obj_id = obj_id;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public void setSee(String see) {
        this.see = see;
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

    public void setVid(String vid) {
        this.vid = vid;
    }

    public void setVideo_count(String video_count) {
        this.video_count = video_count;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }
}
