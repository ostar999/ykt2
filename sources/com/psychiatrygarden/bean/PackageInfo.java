package com.psychiatrygarden.bean;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0012\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B7\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\bJ\u000b\u0010\u000f\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0010\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0011\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0012\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0013\u001a\u0004\u0018\u00010\u0003HÆ\u0003JE\u0010\u0014\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0018\u001a\u00020\u0019HÖ\u0001J\t\u0010\u001a\u001a\u00020\u0003HÖ\u0001R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\nR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\nR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\n¨\u0006\u001b"}, d2 = {"Lcom/psychiatrygarden/bean/PackageInfo;", "", "title", "", "type", "id", "start_live_time", "end_live_time", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getEnd_live_time", "()Ljava/lang/String;", "getId", "getStart_live_time", "getTitle", "getType", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "hashCode", "", "toString", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final /* data */ class PackageInfo {

    @Nullable
    private final String end_live_time;

    @Nullable
    private final String id;

    @Nullable
    private final String start_live_time;

    @Nullable
    private final String title;

    @Nullable
    private final String type;

    public PackageInfo(@Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, @Nullable String str5) {
        this.title = str;
        this.type = str2;
        this.id = str3;
        this.start_live_time = str4;
        this.end_live_time = str5;
    }

    public static /* synthetic */ PackageInfo copy$default(PackageInfo packageInfo, String str, String str2, String str3, String str4, String str5, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = packageInfo.title;
        }
        if ((i2 & 2) != 0) {
            str2 = packageInfo.type;
        }
        String str6 = str2;
        if ((i2 & 4) != 0) {
            str3 = packageInfo.id;
        }
        String str7 = str3;
        if ((i2 & 8) != 0) {
            str4 = packageInfo.start_live_time;
        }
        String str8 = str4;
        if ((i2 & 16) != 0) {
            str5 = packageInfo.end_live_time;
        }
        return packageInfo.copy(str, str6, str7, str8, str5);
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final String getTitle() {
        return this.title;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getType() {
        return this.type;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final String getId() {
        return this.id;
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

    @NotNull
    public final PackageInfo copy(@Nullable String title, @Nullable String type, @Nullable String id, @Nullable String start_live_time, @Nullable String end_live_time) {
        return new PackageInfo(title, type, id, start_live_time, end_live_time);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PackageInfo)) {
            return false;
        }
        PackageInfo packageInfo = (PackageInfo) other;
        return Intrinsics.areEqual(this.title, packageInfo.title) && Intrinsics.areEqual(this.type, packageInfo.type) && Intrinsics.areEqual(this.id, packageInfo.id) && Intrinsics.areEqual(this.start_live_time, packageInfo.start_live_time) && Intrinsics.areEqual(this.end_live_time, packageInfo.end_live_time);
    }

    @Nullable
    public final String getEnd_live_time() {
        return this.end_live_time;
    }

    @Nullable
    public final String getId() {
        return this.id;
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

    public int hashCode() {
        String str = this.title;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.type;
        int iHashCode2 = (iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.id;
        int iHashCode3 = (iHashCode2 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.start_live_time;
        int iHashCode4 = (iHashCode3 + (str4 == null ? 0 : str4.hashCode())) * 31;
        String str5 = this.end_live_time;
        return iHashCode4 + (str5 != null ? str5.hashCode() : 0);
    }

    @NotNull
    public String toString() {
        return "PackageInfo(title=" + this.title + ", type=" + this.type + ", id=" + this.id + ", start_live_time=" + this.start_live_time + ", end_live_time=" + this.end_live_time + ')';
    }
}
