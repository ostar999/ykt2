package com.psychiatrygarden.bean;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b!\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001Bi\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\rJ\u000b\u0010\u0019\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u001a\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u001b\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u001c\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u001d\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u001e\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u001f\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010 \u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010!\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\"\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u0081\u0001\u0010#\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010$\u001a\u00020%2\b\u0010&\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010'\u001a\u00020(HÖ\u0001J\t\u0010)\u001a\u00020\u0003HÖ\u0001R\u0013\u0010\n\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0013\u0010\u000b\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000fR\u0013\u0010\b\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000fR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000fR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000fR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u000fR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u000fR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u000fR\u0013\u0010\f\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u000fR\u0013\u0010\t\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u000f¨\u0006*"}, d2 = {"Lcom/psychiatrygarden/bean/LiveInfo;", "", "live_id", "", "title", "room_id", "start_live_time", "end_live_time", "currency", "user_id", "app_id", "app_secret", "type", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getApp_id", "()Ljava/lang/String;", "getApp_secret", "getCurrency", "getEnd_live_time", "getLive_id", "getRoom_id", "getStart_live_time", "getTitle", "getType", "getUser_id", "component1", "component10", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "", "toString", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final /* data */ class LiveInfo {

    @Nullable
    private final String app_id;

    @Nullable
    private final String app_secret;

    @Nullable
    private final String currency;

    @Nullable
    private final String end_live_time;

    @Nullable
    private final String live_id;

    @Nullable
    private final String room_id;

    @Nullable
    private final String start_live_time;

    @Nullable
    private final String title;

    @Nullable
    private final String type;

    @Nullable
    private final String user_id;

    public LiveInfo(@Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, @Nullable String str5, @Nullable String str6, @Nullable String str7, @Nullable String str8, @Nullable String str9, @Nullable String str10) {
        this.live_id = str;
        this.title = str2;
        this.room_id = str3;
        this.start_live_time = str4;
        this.end_live_time = str5;
        this.currency = str6;
        this.user_id = str7;
        this.app_id = str8;
        this.app_secret = str9;
        this.type = str10;
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final String getLive_id() {
        return this.live_id;
    }

    @Nullable
    /* renamed from: component10, reason: from getter */
    public final String getType() {
        return this.type;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getTitle() {
        return this.title;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final String getRoom_id() {
        return this.room_id;
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
    public final String getCurrency() {
        return this.currency;
    }

    @Nullable
    /* renamed from: component7, reason: from getter */
    public final String getUser_id() {
        return this.user_id;
    }

    @Nullable
    /* renamed from: component8, reason: from getter */
    public final String getApp_id() {
        return this.app_id;
    }

    @Nullable
    /* renamed from: component9, reason: from getter */
    public final String getApp_secret() {
        return this.app_secret;
    }

    @NotNull
    public final LiveInfo copy(@Nullable String live_id, @Nullable String title, @Nullable String room_id, @Nullable String start_live_time, @Nullable String end_live_time, @Nullable String currency, @Nullable String user_id, @Nullable String app_id, @Nullable String app_secret, @Nullable String type) {
        return new LiveInfo(live_id, title, room_id, start_live_time, end_live_time, currency, user_id, app_id, app_secret, type);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof LiveInfo)) {
            return false;
        }
        LiveInfo liveInfo = (LiveInfo) other;
        return Intrinsics.areEqual(this.live_id, liveInfo.live_id) && Intrinsics.areEqual(this.title, liveInfo.title) && Intrinsics.areEqual(this.room_id, liveInfo.room_id) && Intrinsics.areEqual(this.start_live_time, liveInfo.start_live_time) && Intrinsics.areEqual(this.end_live_time, liveInfo.end_live_time) && Intrinsics.areEqual(this.currency, liveInfo.currency) && Intrinsics.areEqual(this.user_id, liveInfo.user_id) && Intrinsics.areEqual(this.app_id, liveInfo.app_id) && Intrinsics.areEqual(this.app_secret, liveInfo.app_secret) && Intrinsics.areEqual(this.type, liveInfo.type);
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
    public final String getCurrency() {
        return this.currency;
    }

    @Nullable
    public final String getEnd_live_time() {
        return this.end_live_time;
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
    public final String getStart_live_time() {
        return this.start_live_time;
    }

    @Nullable
    public final String getTitle() {
        return this.title;
    }

    @Nullable
    public final String getType() {
        return this.type;
    }

    @Nullable
    public final String getUser_id() {
        return this.user_id;
    }

    public int hashCode() {
        String str = this.live_id;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.title;
        int iHashCode2 = (iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.room_id;
        int iHashCode3 = (iHashCode2 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.start_live_time;
        int iHashCode4 = (iHashCode3 + (str4 == null ? 0 : str4.hashCode())) * 31;
        String str5 = this.end_live_time;
        int iHashCode5 = (iHashCode4 + (str5 == null ? 0 : str5.hashCode())) * 31;
        String str6 = this.currency;
        int iHashCode6 = (iHashCode5 + (str6 == null ? 0 : str6.hashCode())) * 31;
        String str7 = this.user_id;
        int iHashCode7 = (iHashCode6 + (str7 == null ? 0 : str7.hashCode())) * 31;
        String str8 = this.app_id;
        int iHashCode8 = (iHashCode7 + (str8 == null ? 0 : str8.hashCode())) * 31;
        String str9 = this.app_secret;
        int iHashCode9 = (iHashCode8 + (str9 == null ? 0 : str9.hashCode())) * 31;
        String str10 = this.type;
        return iHashCode9 + (str10 != null ? str10.hashCode() : 0);
    }

    @NotNull
    public String toString() {
        return "LiveInfo(live_id=" + this.live_id + ", title=" + this.title + ", room_id=" + this.room_id + ", start_live_time=" + this.start_live_time + ", end_live_time=" + this.end_live_time + ", currency=" + this.currency + ", user_id=" + this.user_id + ", app_id=" + this.app_id + ", app_secret=" + this.app_secret + ", type=" + this.type + ')';
    }
}
