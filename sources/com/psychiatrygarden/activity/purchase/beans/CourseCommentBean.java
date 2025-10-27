package com.psychiatrygarden.activity.purchase.beans;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/* loaded from: classes5.dex */
public class CourseCommentBean {
    private String avatar;
    private String content;

    @SerializedName(alternate = {"sub_name"}, value = "course_name")
    private String courseName;
    private String course_id;
    private String created_at;
    private String created_date;
    private String ctime;
    private boolean expand;
    private String id;

    @SerializedName(alternate = {"imgs"}, value = "image")
    private List<String> image;
    private String is_vip;
    private String nickname;
    private String obj_id;
    private String school;

    @SerializedName(alternate = {"grade"}, value = "star_level")
    private String star_level;
    private String user_id;
    private String user_identity;
    private String user_identity_color;

    public String getAvatar() {
        return this.avatar;
    }

    public String getContent() {
        return this.content;
    }

    public String getCourseName() {
        return this.courseName;
    }

    public String getCourse_id() {
        return this.course_id;
    }

    public String getCreated_at() {
        return this.created_at;
    }

    public String getCreated_date() {
        return this.created_date;
    }

    public String getCtime() {
        return this.ctime;
    }

    public String getId() {
        return this.id;
    }

    public List<String> getImage() {
        return this.image;
    }

    public String getIs_vip() {
        return this.is_vip;
    }

    public String getNickname() {
        return this.nickname;
    }

    public String getSchool() {
        return this.school;
    }

    public String getStar_level() {
        return this.star_level;
    }

    public String getUser_id() {
        return this.user_id;
    }

    public String getUser_identity() {
        return this.user_identity;
    }

    public String getUser_identity_color() {
        return this.user_identity_color;
    }

    public boolean isExpand() {
        return this.expand;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setImage(List<String> image) {
        this.image = image;
    }

    public void setIs_vip(String is_vip) {
        this.is_vip = is_vip;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public void setStar_level(String star_level) {
        this.star_level = star_level;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setUser_identity(String user_identity) {
        this.user_identity = user_identity;
    }

    public void setUser_identity_color(String user_identity_color) {
        this.user_identity_color = user_identity_color;
    }
}
