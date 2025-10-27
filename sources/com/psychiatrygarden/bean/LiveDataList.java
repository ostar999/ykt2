package com.psychiatrygarden.bean;

import com.aliyun.auth.common.AliyunVodHttpCommon;
import com.umeng.analytics.pro.d;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\bG\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001Bë\u0001\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u000f\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0010\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0011\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0012\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0013\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0014\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0015\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0016\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0017\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0018\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0019\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u001aJ\u000b\u00102\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u00103\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u00104\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u00105\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u00106\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u00107\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u00108\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u00109\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010:\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010;\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010<\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010=\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010>\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010?\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010@\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010A\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010B\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010C\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010D\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010E\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010F\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010G\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010H\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u009d\u0002\u0010I\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010J\u001a\u00020K2\b\u0010L\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010M\u001a\u00020NHÖ\u0001J\t\u0010O\u001a\u00020\u0003HÖ\u0001R\u0013\u0010\u0014\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0013\u0010\u0015\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001cR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001cR\u0013\u0010\u0018\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u001cR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b \u0010\u001cR\u0013\u0010\n\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001cR\u0013\u0010\u0012\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u001cR\u0013\u0010\u0017\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b#\u0010\u001cR\u0013\u0010\u0019\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b$\u0010\u001cR\u0013\u0010\u0016\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u001cR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b%\u0010\u001cR\u0013\u0010\u000f\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b&\u0010\u001cR\u0013\u0010\f\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b'\u0010\u001cR\u0013\u0010\u000b\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b(\u0010\u001cR\u0013\u0010\t\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b)\u0010\u001cR\u0013\u0010\u0011\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b*\u0010\u001cR\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b+\u0010\u001cR\u0013\u0010\u0010\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b,\u0010\u001cR\u0013\u0010\b\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b-\u0010\u001cR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b.\u0010\u001cR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b/\u0010\u001cR\u0013\u0010\u0013\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b0\u0010\u001cR\u0013\u0010\r\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b1\u0010\u001c¨\u0006P"}, d2 = {"Lcom/psychiatrygarden/bean/LiveDataList;", "", "course_id", "", "live_id", "title", "thumb", "currency", "teacher_id", "start_live_time", "end_live_time", "room_id", "number_watched", "video_id", "status", "live_status", "teacher", d.f22694p, d.f22695q, "user_id", "app_id", "app_secret", "is_permission", "head_img", AliyunVodHttpCommon.ImageType.IMAGETYPE_COVER, "id", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getApp_id", "()Ljava/lang/String;", "getApp_secret", "getCourse_id", "getCover", "getCurrency", "getEnd_live_time", "getEnd_time", "getHead_img", "getId", "getLive_id", "getLive_status", "getNumber_watched", "getRoom_id", "getStart_live_time", "getStart_time", "getStatus", "getTeacher", "getTeacher_id", "getThumb", "getTitle", "getUser_id", "getVideo_id", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component20", "component21", "component22", "component23", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "", "toString", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final /* data */ class LiveDataList {

    @Nullable
    private final String app_id;

    @Nullable
    private final String app_secret;

    @Nullable
    private final String course_id;

    @Nullable
    private final String cover;

    @Nullable
    private final String currency;

    @Nullable
    private final String end_live_time;

    @Nullable
    private final String end_time;

    @Nullable
    private final String head_img;

    @Nullable
    private final String id;

    @Nullable
    private final String is_permission;

    @Nullable
    private final String live_id;

    @Nullable
    private final String live_status;

    @Nullable
    private final String number_watched;

    @Nullable
    private final String room_id;

    @Nullable
    private final String start_live_time;

    @Nullable
    private final String start_time;

    @Nullable
    private final String status;

    @Nullable
    private final String teacher;

    @Nullable
    private final String teacher_id;

    @Nullable
    private final String thumb;

    @Nullable
    private final String title;

    @Nullable
    private final String user_id;

    @Nullable
    private final String video_id;

    public LiveDataList(@Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, @Nullable String str5, @Nullable String str6, @Nullable String str7, @Nullable String str8, @Nullable String str9, @Nullable String str10, @Nullable String str11, @Nullable String str12, @Nullable String str13, @Nullable String str14, @Nullable String str15, @Nullable String str16, @Nullable String str17, @Nullable String str18, @Nullable String str19, @Nullable String str20, @Nullable String str21, @Nullable String str22, @Nullable String str23) {
        this.course_id = str;
        this.live_id = str2;
        this.title = str3;
        this.thumb = str4;
        this.currency = str5;
        this.teacher_id = str6;
        this.start_live_time = str7;
        this.end_live_time = str8;
        this.room_id = str9;
        this.number_watched = str10;
        this.video_id = str11;
        this.status = str12;
        this.live_status = str13;
        this.teacher = str14;
        this.start_time = str15;
        this.end_time = str16;
        this.user_id = str17;
        this.app_id = str18;
        this.app_secret = str19;
        this.is_permission = str20;
        this.head_img = str21;
        this.cover = str22;
        this.id = str23;
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final String getCourse_id() {
        return this.course_id;
    }

    @Nullable
    /* renamed from: component10, reason: from getter */
    public final String getNumber_watched() {
        return this.number_watched;
    }

    @Nullable
    /* renamed from: component11, reason: from getter */
    public final String getVideo_id() {
        return this.video_id;
    }

    @Nullable
    /* renamed from: component12, reason: from getter */
    public final String getStatus() {
        return this.status;
    }

    @Nullable
    /* renamed from: component13, reason: from getter */
    public final String getLive_status() {
        return this.live_status;
    }

    @Nullable
    /* renamed from: component14, reason: from getter */
    public final String getTeacher() {
        return this.teacher;
    }

    @Nullable
    /* renamed from: component15, reason: from getter */
    public final String getStart_time() {
        return this.start_time;
    }

    @Nullable
    /* renamed from: component16, reason: from getter */
    public final String getEnd_time() {
        return this.end_time;
    }

    @Nullable
    /* renamed from: component17, reason: from getter */
    public final String getUser_id() {
        return this.user_id;
    }

    @Nullable
    /* renamed from: component18, reason: from getter */
    public final String getApp_id() {
        return this.app_id;
    }

    @Nullable
    /* renamed from: component19, reason: from getter */
    public final String getApp_secret() {
        return this.app_secret;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getLive_id() {
        return this.live_id;
    }

    @Nullable
    /* renamed from: component20, reason: from getter */
    public final String getIs_permission() {
        return this.is_permission;
    }

    @Nullable
    /* renamed from: component21, reason: from getter */
    public final String getHead_img() {
        return this.head_img;
    }

    @Nullable
    /* renamed from: component22, reason: from getter */
    public final String getCover() {
        return this.cover;
    }

    @Nullable
    /* renamed from: component23, reason: from getter */
    public final String getId() {
        return this.id;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final String getTitle() {
        return this.title;
    }

    @Nullable
    /* renamed from: component4, reason: from getter */
    public final String getThumb() {
        return this.thumb;
    }

    @Nullable
    /* renamed from: component5, reason: from getter */
    public final String getCurrency() {
        return this.currency;
    }

    @Nullable
    /* renamed from: component6, reason: from getter */
    public final String getTeacher_id() {
        return this.teacher_id;
    }

    @Nullable
    /* renamed from: component7, reason: from getter */
    public final String getStart_live_time() {
        return this.start_live_time;
    }

    @Nullable
    /* renamed from: component8, reason: from getter */
    public final String getEnd_live_time() {
        return this.end_live_time;
    }

    @Nullable
    /* renamed from: component9, reason: from getter */
    public final String getRoom_id() {
        return this.room_id;
    }

    @NotNull
    public final LiveDataList copy(@Nullable String course_id, @Nullable String live_id, @Nullable String title, @Nullable String thumb, @Nullable String currency, @Nullable String teacher_id, @Nullable String start_live_time, @Nullable String end_live_time, @Nullable String room_id, @Nullable String number_watched, @Nullable String video_id, @Nullable String status, @Nullable String live_status, @Nullable String teacher, @Nullable String start_time, @Nullable String end_time, @Nullable String user_id, @Nullable String app_id, @Nullable String app_secret, @Nullable String is_permission, @Nullable String head_img, @Nullable String cover, @Nullable String id) {
        return new LiveDataList(course_id, live_id, title, thumb, currency, teacher_id, start_live_time, end_live_time, room_id, number_watched, video_id, status, live_status, teacher, start_time, end_time, user_id, app_id, app_secret, is_permission, head_img, cover, id);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof LiveDataList)) {
            return false;
        }
        LiveDataList liveDataList = (LiveDataList) other;
        return Intrinsics.areEqual(this.course_id, liveDataList.course_id) && Intrinsics.areEqual(this.live_id, liveDataList.live_id) && Intrinsics.areEqual(this.title, liveDataList.title) && Intrinsics.areEqual(this.thumb, liveDataList.thumb) && Intrinsics.areEqual(this.currency, liveDataList.currency) && Intrinsics.areEqual(this.teacher_id, liveDataList.teacher_id) && Intrinsics.areEqual(this.start_live_time, liveDataList.start_live_time) && Intrinsics.areEqual(this.end_live_time, liveDataList.end_live_time) && Intrinsics.areEqual(this.room_id, liveDataList.room_id) && Intrinsics.areEqual(this.number_watched, liveDataList.number_watched) && Intrinsics.areEqual(this.video_id, liveDataList.video_id) && Intrinsics.areEqual(this.status, liveDataList.status) && Intrinsics.areEqual(this.live_status, liveDataList.live_status) && Intrinsics.areEqual(this.teacher, liveDataList.teacher) && Intrinsics.areEqual(this.start_time, liveDataList.start_time) && Intrinsics.areEqual(this.end_time, liveDataList.end_time) && Intrinsics.areEqual(this.user_id, liveDataList.user_id) && Intrinsics.areEqual(this.app_id, liveDataList.app_id) && Intrinsics.areEqual(this.app_secret, liveDataList.app_secret) && Intrinsics.areEqual(this.is_permission, liveDataList.is_permission) && Intrinsics.areEqual(this.head_img, liveDataList.head_img) && Intrinsics.areEqual(this.cover, liveDataList.cover) && Intrinsics.areEqual(this.id, liveDataList.id);
    }

    @Nullable
    public final String getApp_id() {
        return this.app_id;
    }

    @Nullable
    public final String getApp_secret() {
        return this.app_secret;
    }

    @Nullable
    public final String getCourse_id() {
        return this.course_id;
    }

    @Nullable
    public final String getCover() {
        return this.cover;
    }

    @Nullable
    public final String getCurrency() {
        return this.currency;
    }

    @Nullable
    public final String getEnd_live_time() {
        return this.end_live_time;
    }

    @Nullable
    public final String getEnd_time() {
        return this.end_time;
    }

    @Nullable
    public final String getHead_img() {
        return this.head_img;
    }

    @Nullable
    public final String getId() {
        return this.id;
    }

    @Nullable
    public final String getLive_id() {
        return this.live_id;
    }

    @Nullable
    public final String getLive_status() {
        return this.live_status;
    }

    @Nullable
    public final String getNumber_watched() {
        return this.number_watched;
    }

    @Nullable
    public final String getRoom_id() {
        return this.room_id;
    }

    @Nullable
    public final String getStart_live_time() {
        return this.start_live_time;
    }

    @Nullable
    public final String getStart_time() {
        return this.start_time;
    }

    @Nullable
    public final String getStatus() {
        return this.status;
    }

    @Nullable
    public final String getTeacher() {
        return this.teacher;
    }

    @Nullable
    public final String getTeacher_id() {
        return this.teacher_id;
    }

    @Nullable
    public final String getThumb() {
        return this.thumb;
    }

    @Nullable
    public final String getTitle() {
        return this.title;
    }

    @Nullable
    public final String getUser_id() {
        return this.user_id;
    }

    @Nullable
    public final String getVideo_id() {
        return this.video_id;
    }

    public int hashCode() {
        String str = this.course_id;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.live_id;
        int iHashCode2 = (iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.title;
        int iHashCode3 = (iHashCode2 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.thumb;
        int iHashCode4 = (iHashCode3 + (str4 == null ? 0 : str4.hashCode())) * 31;
        String str5 = this.currency;
        int iHashCode5 = (iHashCode4 + (str5 == null ? 0 : str5.hashCode())) * 31;
        String str6 = this.teacher_id;
        int iHashCode6 = (iHashCode5 + (str6 == null ? 0 : str6.hashCode())) * 31;
        String str7 = this.start_live_time;
        int iHashCode7 = (iHashCode6 + (str7 == null ? 0 : str7.hashCode())) * 31;
        String str8 = this.end_live_time;
        int iHashCode8 = (iHashCode7 + (str8 == null ? 0 : str8.hashCode())) * 31;
        String str9 = this.room_id;
        int iHashCode9 = (iHashCode8 + (str9 == null ? 0 : str9.hashCode())) * 31;
        String str10 = this.number_watched;
        int iHashCode10 = (iHashCode9 + (str10 == null ? 0 : str10.hashCode())) * 31;
        String str11 = this.video_id;
        int iHashCode11 = (iHashCode10 + (str11 == null ? 0 : str11.hashCode())) * 31;
        String str12 = this.status;
        int iHashCode12 = (iHashCode11 + (str12 == null ? 0 : str12.hashCode())) * 31;
        String str13 = this.live_status;
        int iHashCode13 = (iHashCode12 + (str13 == null ? 0 : str13.hashCode())) * 31;
        String str14 = this.teacher;
        int iHashCode14 = (iHashCode13 + (str14 == null ? 0 : str14.hashCode())) * 31;
        String str15 = this.start_time;
        int iHashCode15 = (iHashCode14 + (str15 == null ? 0 : str15.hashCode())) * 31;
        String str16 = this.end_time;
        int iHashCode16 = (iHashCode15 + (str16 == null ? 0 : str16.hashCode())) * 31;
        String str17 = this.user_id;
        int iHashCode17 = (iHashCode16 + (str17 == null ? 0 : str17.hashCode())) * 31;
        String str18 = this.app_id;
        int iHashCode18 = (iHashCode17 + (str18 == null ? 0 : str18.hashCode())) * 31;
        String str19 = this.app_secret;
        int iHashCode19 = (iHashCode18 + (str19 == null ? 0 : str19.hashCode())) * 31;
        String str20 = this.is_permission;
        int iHashCode20 = (iHashCode19 + (str20 == null ? 0 : str20.hashCode())) * 31;
        String str21 = this.head_img;
        int iHashCode21 = (iHashCode20 + (str21 == null ? 0 : str21.hashCode())) * 31;
        String str22 = this.cover;
        int iHashCode22 = (iHashCode21 + (str22 == null ? 0 : str22.hashCode())) * 31;
        String str23 = this.id;
        return iHashCode22 + (str23 != null ? str23.hashCode() : 0);
    }

    @Nullable
    public final String is_permission() {
        return this.is_permission;
    }

    @NotNull
    public String toString() {
        return "LiveDataList(course_id=" + this.course_id + ", live_id=" + this.live_id + ", title=" + this.title + ", thumb=" + this.thumb + ", currency=" + this.currency + ", teacher_id=" + this.teacher_id + ", start_live_time=" + this.start_live_time + ", end_live_time=" + this.end_live_time + ", room_id=" + this.room_id + ", number_watched=" + this.number_watched + ", video_id=" + this.video_id + ", status=" + this.status + ", live_status=" + this.live_status + ", teacher=" + this.teacher + ", start_time=" + this.start_time + ", end_time=" + this.end_time + ", user_id=" + this.user_id + ", app_id=" + this.app_id + ", app_secret=" + this.app_secret + ", is_permission=" + this.is_permission + ", head_img=" + this.head_img + ", cover=" + this.cover + ", id=" + this.id + ')';
    }
}
