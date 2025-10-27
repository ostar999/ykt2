package com.psychiatrygarden.bean;

import com.aliyun.auth.common.AliyunVodHttpCommon;
import java.util.List;
import kotlin.Metadata;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b%\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\"\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001c\u0010\n\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\f\"\u0004\b\u0011\u0010\u000eR\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\f\"\u0004\b\u0014\u0010\u000eR\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\f\"\u0004\b\u0017\u0010\u000eR\u001c\u0010\u0018\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\f\"\u0004\b\u001a\u0010\u000eR\u001c\u0010\u001b\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\f\"\u0004\b\u001d\u0010\u000eR\u001c\u0010\u001e\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\f\"\u0004\b \u0010\u000eR\u001c\u0010!\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\f\"\u0004\b#\u0010\u000eR\u001c\u0010$\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010\f\"\u0004\b&\u0010\u000eR\u001c\u0010'\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010\f\"\u0004\b)\u0010\u000e¨\u0006*"}, d2 = {"Lcom/psychiatrygarden/bean/ChooseSchoolTargetSchoolBeanItem;", "", "()V", "attr", "", "", "getAttr", "()Ljava/util/List;", "setAttr", "(Ljava/util/List;)V", "code", "getCode", "()Ljava/lang/String;", "setCode", "(Ljava/lang/String;)V", AliyunVodHttpCommon.ImageType.IMAGETYPE_COVER, "getCover", "setCover", "follow_count", "getFollow_count", "setFollow_count", "location", "getLocation", "setLocation", "major_count", "getMajor_count", "setMajor_count", "recent_7days_follow", "getRecent_7days_follow", "setRecent_7days_follow", "school_id", "getSchool_id", "setSchool_id", "student_count", "getStudent_count", "setStudent_count", "title", "getTitle", "setTitle", "view_count", "getView_count", "setView_count", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class ChooseSchoolTargetSchoolBeanItem {

    @Nullable
    private List<String> attr;

    @Nullable
    private String code;

    @Nullable
    private String cover;

    @Nullable
    private String follow_count;

    @Nullable
    private String location;

    @Nullable
    private String major_count;

    @Nullable
    private String recent_7days_follow;

    @Nullable
    private String school_id;

    @Nullable
    private String student_count;

    @Nullable
    private String title;

    @Nullable
    private String view_count;

    @Nullable
    public final List<String> getAttr() {
        return this.attr;
    }

    @Nullable
    public final String getCode() {
        return this.code;
    }

    @Nullable
    public final String getCover() {
        return this.cover;
    }

    @Nullable
    public final String getFollow_count() {
        return this.follow_count;
    }

    @Nullable
    public final String getLocation() {
        return this.location;
    }

    @Nullable
    public final String getMajor_count() {
        return this.major_count;
    }

    @Nullable
    public final String getRecent_7days_follow() {
        return this.recent_7days_follow;
    }

    @Nullable
    public final String getSchool_id() {
        return this.school_id;
    }

    @Nullable
    public final String getStudent_count() {
        return this.student_count;
    }

    @Nullable
    public final String getTitle() {
        return this.title;
    }

    @Nullable
    public final String getView_count() {
        return this.view_count;
    }

    public final void setAttr(@Nullable List<String> list) {
        this.attr = list;
    }

    public final void setCode(@Nullable String str) {
        this.code = str;
    }

    public final void setCover(@Nullable String str) {
        this.cover = str;
    }

    public final void setFollow_count(@Nullable String str) {
        this.follow_count = str;
    }

    public final void setLocation(@Nullable String str) {
        this.location = str;
    }

    public final void setMajor_count(@Nullable String str) {
        this.major_count = str;
    }

    public final void setRecent_7days_follow(@Nullable String str) {
        this.recent_7days_follow = str;
    }

    public final void setSchool_id(@Nullable String str) {
        this.school_id = str;
    }

    public final void setStudent_count(@Nullable String str) {
        this.student_count = str;
    }

    public final void setTitle(@Nullable String str) {
        this.title = str;
    }

    public final void setView_count(@Nullable String str) {
        this.view_count = str;
    }
}
