package com.psychiatrygarden.bean;

import com.aliyun.auth.common.AliyunVodHttpCommon;
import com.aliyun.player.alivcplayerexpand.util.database.DatabaseManager;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0016\n\u0002\u0010\b\n\u0002\b3\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0086\b\u0018\u00002\u00020\u0001Bé\u0001\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u000f\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0010\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0011\u001a\u00020\u0003\u0012\b\u0010\u0012\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0013\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0014\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0015\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0016\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0017\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0018\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0019\u001a\u00020\u001a¢\u0006\u0002\u0010\u001bJ\u000b\u00105\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u00106\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u00107\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u00108\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u00109\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010:\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u0010;\u001a\u00020\u0003HÆ\u0003J\u000b\u0010<\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010=\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010>\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010?\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010@\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010A\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010B\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010C\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u0010D\u001a\u00020\u001aHÆ\u0003J\u000b\u0010E\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010F\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010G\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010H\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010I\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010J\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010K\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u0099\u0002\u0010L\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0011\u001a\u00020\u00032\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0019\u001a\u00020\u001aHÆ\u0001J\u0013\u0010M\u001a\u00020N2\b\u0010O\u001a\u0004\u0018\u00010PHÖ\u0003J\t\u0010Q\u001a\u00020\u001aHÖ\u0001J\t\u0010R\u001a\u00020\u0003HÖ\u0001R\u0013\u0010\u000f\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0013\u0010\u0010\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001dR\u0011\u0010\u0011\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u001dR\u0013\u0010\u0017\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b \u0010\u001dR\u0013\u0010\u000b\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001dR\u0013\u0010\u0013\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u001dR\u0013\u0010\u0014\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b#\u0010\u001dR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b$\u0010\u001dR\u0013\u0010\u0015\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u001dR\u0013\u0010\u0018\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u001dR\u001a\u0010\u0019\u001a\u00020\u001aX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010&\"\u0004\b'\u0010(R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b)\u0010\u001dR\u0013\u0010\t\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b*\u0010\u001dR\u0013\u0010\u0016\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b+\u0010\u001dR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b,\u0010\u001dR\u0013\u0010\f\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b-\u0010\u001dR\u0013\u0010\r\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b.\u0010\u001dR\u0013\u0010\b\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b/\u0010\u001dR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b0\u0010\u001dR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b1\u0010\u001dR\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b2\u0010\u001dR\u0013\u0010\u0012\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b3\u0010\u001dR\u0013\u0010\n\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b4\u0010\u001d¨\u0006S"}, d2 = {"Lcom/psychiatrygarden/bean/LiveBannerBean;", "Lcom/chad/library/adapter/base/entity/MultiItemEntity;", "live_id", "", "title", "thumb", "start_live_time", "end_live_time", "teacher_id", "room_id", "video_id", "currency", "teacher", "teacher_avatar", "user_id", "app_id", "app_secret", "course_id", "vid", "current_duration", "duration", "is_end", DatabaseManager.SIZE, AliyunVodHttpCommon.ImageType.IMAGETYPE_COVER, "is_permission", "itemType", "", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;I)V", "getApp_id", "()Ljava/lang/String;", "getApp_secret", "getCourse_id", "getCover", "getCurrency", "getCurrent_duration", "getDuration", "getEnd_live_time", "getItemType", "()I", "setItemType", "(I)V", "getLive_id", "getRoom_id", "getSize", "getStart_live_time", "getTeacher", "getTeacher_avatar", "getTeacher_id", "getThumb", "getTitle", "getUser_id", "getVid", "getVideo_id", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component20", "component21", "component22", "component23", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "", "hashCode", "toString", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final /* data */ class LiveBannerBean implements MultiItemEntity {

    @Nullable
    private final String app_id;

    @Nullable
    private final String app_secret;

    @NotNull
    private final String course_id;

    @Nullable
    private final String cover;

    @Nullable
    private final String currency;

    @Nullable
    private final String current_duration;

    @Nullable
    private final String duration;

    @Nullable
    private final String end_live_time;

    @Nullable
    private final String is_end;

    @Nullable
    private final String is_permission;
    private int itemType;

    @Nullable
    private final String live_id;

    @Nullable
    private final String room_id;

    @Nullable
    private final String size;

    @Nullable
    private final String start_live_time;

    @Nullable
    private final String teacher;

    @Nullable
    private final String teacher_avatar;

    @Nullable
    private final String teacher_id;

    @Nullable
    private final String thumb;

    @Nullable
    private final String title;

    @Nullable
    private final String user_id;

    @Nullable
    private final String vid;

    @Nullable
    private final String video_id;

    public LiveBannerBean(@Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, @Nullable String str5, @Nullable String str6, @Nullable String str7, @Nullable String str8, @Nullable String str9, @Nullable String str10, @Nullable String str11, @Nullable String str12, @Nullable String str13, @Nullable String str14, @NotNull String course_id, @Nullable String str15, @Nullable String str16, @Nullable String str17, @Nullable String str18, @Nullable String str19, @Nullable String str20, @Nullable String str21, int i2) {
        Intrinsics.checkNotNullParameter(course_id, "course_id");
        this.live_id = str;
        this.title = str2;
        this.thumb = str3;
        this.start_live_time = str4;
        this.end_live_time = str5;
        this.teacher_id = str6;
        this.room_id = str7;
        this.video_id = str8;
        this.currency = str9;
        this.teacher = str10;
        this.teacher_avatar = str11;
        this.user_id = str12;
        this.app_id = str13;
        this.app_secret = str14;
        this.course_id = course_id;
        this.vid = str15;
        this.current_duration = str16;
        this.duration = str17;
        this.is_end = str18;
        this.size = str19;
        this.cover = str20;
        this.is_permission = str21;
        this.itemType = i2;
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final String getLive_id() {
        return this.live_id;
    }

    @Nullable
    /* renamed from: component10, reason: from getter */
    public final String getTeacher() {
        return this.teacher;
    }

    @Nullable
    /* renamed from: component11, reason: from getter */
    public final String getTeacher_avatar() {
        return this.teacher_avatar;
    }

    @Nullable
    /* renamed from: component12, reason: from getter */
    public final String getUser_id() {
        return this.user_id;
    }

    @Nullable
    /* renamed from: component13, reason: from getter */
    public final String getApp_id() {
        return this.app_id;
    }

    @Nullable
    /* renamed from: component14, reason: from getter */
    public final String getApp_secret() {
        return this.app_secret;
    }

    @NotNull
    /* renamed from: component15, reason: from getter */
    public final String getCourse_id() {
        return this.course_id;
    }

    @Nullable
    /* renamed from: component16, reason: from getter */
    public final String getVid() {
        return this.vid;
    }

    @Nullable
    /* renamed from: component17, reason: from getter */
    public final String getCurrent_duration() {
        return this.current_duration;
    }

    @Nullable
    /* renamed from: component18, reason: from getter */
    public final String getDuration() {
        return this.duration;
    }

    @Nullable
    /* renamed from: component19, reason: from getter */
    public final String getIs_end() {
        return this.is_end;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getTitle() {
        return this.title;
    }

    @Nullable
    /* renamed from: component20, reason: from getter */
    public final String getSize() {
        return this.size;
    }

    @Nullable
    /* renamed from: component21, reason: from getter */
    public final String getCover() {
        return this.cover;
    }

    @Nullable
    /* renamed from: component22, reason: from getter */
    public final String getIs_permission() {
        return this.is_permission;
    }

    public final int component23() {
        return getItemType();
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final String getThumb() {
        return this.thumb;
    }

    @Nullable
    /* renamed from: component4, reason: from getter */
    public final String getStart_live_time() {
        return this.start_live_time;
    }

    @Nullable
    /* renamed from: component5, reason: from getter */
    public final String getEnd_live_time() {
        return this.end_live_time;
    }

    @Nullable
    /* renamed from: component6, reason: from getter */
    public final String getTeacher_id() {
        return this.teacher_id;
    }

    @Nullable
    /* renamed from: component7, reason: from getter */
    public final String getRoom_id() {
        return this.room_id;
    }

    @Nullable
    /* renamed from: component8, reason: from getter */
    public final String getVideo_id() {
        return this.video_id;
    }

    @Nullable
    /* renamed from: component9, reason: from getter */
    public final String getCurrency() {
        return this.currency;
    }

    @NotNull
    public final LiveBannerBean copy(@Nullable String live_id, @Nullable String title, @Nullable String thumb, @Nullable String start_live_time, @Nullable String end_live_time, @Nullable String teacher_id, @Nullable String room_id, @Nullable String video_id, @Nullable String currency, @Nullable String teacher, @Nullable String teacher_avatar, @Nullable String user_id, @Nullable String app_id, @Nullable String app_secret, @NotNull String course_id, @Nullable String vid, @Nullable String current_duration, @Nullable String duration, @Nullable String is_end, @Nullable String size, @Nullable String cover, @Nullable String is_permission, int itemType) {
        Intrinsics.checkNotNullParameter(course_id, "course_id");
        return new LiveBannerBean(live_id, title, thumb, start_live_time, end_live_time, teacher_id, room_id, video_id, currency, teacher, teacher_avatar, user_id, app_id, app_secret, course_id, vid, current_duration, duration, is_end, size, cover, is_permission, itemType);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof LiveBannerBean)) {
            return false;
        }
        LiveBannerBean liveBannerBean = (LiveBannerBean) other;
        return Intrinsics.areEqual(this.live_id, liveBannerBean.live_id) && Intrinsics.areEqual(this.title, liveBannerBean.title) && Intrinsics.areEqual(this.thumb, liveBannerBean.thumb) && Intrinsics.areEqual(this.start_live_time, liveBannerBean.start_live_time) && Intrinsics.areEqual(this.end_live_time, liveBannerBean.end_live_time) && Intrinsics.areEqual(this.teacher_id, liveBannerBean.teacher_id) && Intrinsics.areEqual(this.room_id, liveBannerBean.room_id) && Intrinsics.areEqual(this.video_id, liveBannerBean.video_id) && Intrinsics.areEqual(this.currency, liveBannerBean.currency) && Intrinsics.areEqual(this.teacher, liveBannerBean.teacher) && Intrinsics.areEqual(this.teacher_avatar, liveBannerBean.teacher_avatar) && Intrinsics.areEqual(this.user_id, liveBannerBean.user_id) && Intrinsics.areEqual(this.app_id, liveBannerBean.app_id) && Intrinsics.areEqual(this.app_secret, liveBannerBean.app_secret) && Intrinsics.areEqual(this.course_id, liveBannerBean.course_id) && Intrinsics.areEqual(this.vid, liveBannerBean.vid) && Intrinsics.areEqual(this.current_duration, liveBannerBean.current_duration) && Intrinsics.areEqual(this.duration, liveBannerBean.duration) && Intrinsics.areEqual(this.is_end, liveBannerBean.is_end) && Intrinsics.areEqual(this.size, liveBannerBean.size) && Intrinsics.areEqual(this.cover, liveBannerBean.cover) && Intrinsics.areEqual(this.is_permission, liveBannerBean.is_permission) && getItemType() == liveBannerBean.getItemType();
    }

    @Nullable
    public final String getApp_id() {
        return this.app_id;
    }

    @Nullable
    public final String getApp_secret() {
        return this.app_secret;
    }

    @NotNull
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
    public final String getCurrent_duration() {
        return this.current_duration;
    }

    @Nullable
    public final String getDuration() {
        return this.duration;
    }

    @Nullable
    public final String getEnd_live_time() {
        return this.end_live_time;
    }

    @Override // com.chad.library.adapter.base.entity.MultiItemEntity
    public int getItemType() {
        return this.itemType;
    }

    @Nullable
    public final String getLive_id() {
        return this.live_id;
    }

    @Nullable
    public final String getRoom_id() {
        return this.room_id;
    }

    @Nullable
    public final String getSize() {
        return this.size;
    }

    @Nullable
    public final String getStart_live_time() {
        return this.start_live_time;
    }

    @Nullable
    public final String getTeacher() {
        return this.teacher;
    }

    @Nullable
    public final String getTeacher_avatar() {
        return this.teacher_avatar;
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
    public final String getVid() {
        return this.vid;
    }

    @Nullable
    public final String getVideo_id() {
        return this.video_id;
    }

    public int hashCode() {
        String str = this.live_id;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.title;
        int iHashCode2 = (iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.thumb;
        int iHashCode3 = (iHashCode2 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.start_live_time;
        int iHashCode4 = (iHashCode3 + (str4 == null ? 0 : str4.hashCode())) * 31;
        String str5 = this.end_live_time;
        int iHashCode5 = (iHashCode4 + (str5 == null ? 0 : str5.hashCode())) * 31;
        String str6 = this.teacher_id;
        int iHashCode6 = (iHashCode5 + (str6 == null ? 0 : str6.hashCode())) * 31;
        String str7 = this.room_id;
        int iHashCode7 = (iHashCode6 + (str7 == null ? 0 : str7.hashCode())) * 31;
        String str8 = this.video_id;
        int iHashCode8 = (iHashCode7 + (str8 == null ? 0 : str8.hashCode())) * 31;
        String str9 = this.currency;
        int iHashCode9 = (iHashCode8 + (str9 == null ? 0 : str9.hashCode())) * 31;
        String str10 = this.teacher;
        int iHashCode10 = (iHashCode9 + (str10 == null ? 0 : str10.hashCode())) * 31;
        String str11 = this.teacher_avatar;
        int iHashCode11 = (iHashCode10 + (str11 == null ? 0 : str11.hashCode())) * 31;
        String str12 = this.user_id;
        int iHashCode12 = (iHashCode11 + (str12 == null ? 0 : str12.hashCode())) * 31;
        String str13 = this.app_id;
        int iHashCode13 = (iHashCode12 + (str13 == null ? 0 : str13.hashCode())) * 31;
        String str14 = this.app_secret;
        int iHashCode14 = (((iHashCode13 + (str14 == null ? 0 : str14.hashCode())) * 31) + this.course_id.hashCode()) * 31;
        String str15 = this.vid;
        int iHashCode15 = (iHashCode14 + (str15 == null ? 0 : str15.hashCode())) * 31;
        String str16 = this.current_duration;
        int iHashCode16 = (iHashCode15 + (str16 == null ? 0 : str16.hashCode())) * 31;
        String str17 = this.duration;
        int iHashCode17 = (iHashCode16 + (str17 == null ? 0 : str17.hashCode())) * 31;
        String str18 = this.is_end;
        int iHashCode18 = (iHashCode17 + (str18 == null ? 0 : str18.hashCode())) * 31;
        String str19 = this.size;
        int iHashCode19 = (iHashCode18 + (str19 == null ? 0 : str19.hashCode())) * 31;
        String str20 = this.cover;
        int iHashCode20 = (iHashCode19 + (str20 == null ? 0 : str20.hashCode())) * 31;
        String str21 = this.is_permission;
        return ((iHashCode20 + (str21 != null ? str21.hashCode() : 0)) * 31) + getItemType();
    }

    @Nullable
    public final String is_end() {
        return this.is_end;
    }

    @Nullable
    public final String is_permission() {
        return this.is_permission;
    }

    public void setItemType(int i2) {
        this.itemType = i2;
    }

    @NotNull
    public String toString() {
        return "LiveBannerBean(live_id=" + this.live_id + ", title=" + this.title + ", thumb=" + this.thumb + ", start_live_time=" + this.start_live_time + ", end_live_time=" + this.end_live_time + ", teacher_id=" + this.teacher_id + ", room_id=" + this.room_id + ", video_id=" + this.video_id + ", currency=" + this.currency + ", teacher=" + this.teacher + ", teacher_avatar=" + this.teacher_avatar + ", user_id=" + this.user_id + ", app_id=" + this.app_id + ", app_secret=" + this.app_secret + ", course_id=" + this.course_id + ", vid=" + this.vid + ", current_duration=" + this.current_duration + ", duration=" + this.duration + ", is_end=" + this.is_end + ", size=" + this.size + ", cover=" + this.cover + ", is_permission=" + this.is_permission + ", itemType=" + getItemType() + ')';
    }

    public /* synthetic */ LiveBannerBean(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13, String str14, String str15, String str16, String str17, String str18, String str19, String str20, String str21, String str22, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, str3, str4, str5, str6, str7, str8, str9, str10, str11, str12, str13, str14, str15, str16, str17, str18, str19, str20, str21, str22, (i3 & 4194304) != 0 ? 0 : i2);
    }
}
