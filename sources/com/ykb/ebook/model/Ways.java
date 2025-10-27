package com.ykb.ebook.model;

import androidx.annotation.Keep;
import com.google.gson.annotations.SerializedName;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.plv.foundationsdk.rx.PLVRxEncryptDataFunction;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Keep
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0016\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\t\u0010\u0018\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0019\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001a\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001b\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001c\u001a\u00020\bHÆ\u0003J;\u0010\u001d\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\bHÆ\u0001J\u0013\u0010\u001e\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010!\u001a\u00020\"HÖ\u0001J\t\u0010#\u001a\u00020\u0003HÖ\u0001R\u001e\u0010\u0007\u001a\u00020\b8\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001e\u0010\u0005\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001e\u0010\u0002\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u000f\"\u0004\b\u0013\u0010\u0011R\u001e\u0010\u0004\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u000f\"\u0004\b\u0015\u0010\u0011R\u001e\u0010\u0006\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u000f\"\u0004\b\u0017\u0010\u0011¨\u0006$"}, d2 = {"Lcom/ykb/ebook/model/Ways;", "", RemoteMessageConst.Notification.ICON, "", "title", "description", "way", "data", "Lcom/ykb/ebook/model/WayData;", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/ykb/ebook/model/WayData;)V", "getData", "()Lcom/ykb/ebook/model/WayData;", PLVRxEncryptDataFunction.SET_DATA_METHOD, "(Lcom/ykb/ebook/model/WayData;)V", "getDescription", "()Ljava/lang/String;", "setDescription", "(Ljava/lang/String;)V", "getIcon", "setIcon", "getTitle", "setTitle", "getWay", "setWay", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "hashCode", "", "toString", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final /* data */ class Ways {

    @SerializedName("data")
    @NotNull
    private WayData data;

    @SerializedName("description")
    @NotNull
    private String description;

    @SerializedName(RemoteMessageConst.Notification.ICON)
    @NotNull
    private String icon;

    @SerializedName("title")
    @NotNull
    private String title;

    @SerializedName("way")
    @NotNull
    private String way;

    public Ways(@NotNull String icon, @NotNull String title, @NotNull String description, @NotNull String way, @NotNull WayData data) {
        Intrinsics.checkNotNullParameter(icon, "icon");
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(description, "description");
        Intrinsics.checkNotNullParameter(way, "way");
        Intrinsics.checkNotNullParameter(data, "data");
        this.icon = icon;
        this.title = title;
        this.description = description;
        this.way = way;
        this.data = data;
    }

    public static /* synthetic */ Ways copy$default(Ways ways, String str, String str2, String str3, String str4, WayData wayData, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = ways.icon;
        }
        if ((i2 & 2) != 0) {
            str2 = ways.title;
        }
        String str5 = str2;
        if ((i2 & 4) != 0) {
            str3 = ways.description;
        }
        String str6 = str3;
        if ((i2 & 8) != 0) {
            str4 = ways.way;
        }
        String str7 = str4;
        if ((i2 & 16) != 0) {
            wayData = ways.data;
        }
        return ways.copy(str, str5, str6, str7, wayData);
    }

    @NotNull
    /* renamed from: component1, reason: from getter */
    public final String getIcon() {
        return this.icon;
    }

    @NotNull
    /* renamed from: component2, reason: from getter */
    public final String getTitle() {
        return this.title;
    }

    @NotNull
    /* renamed from: component3, reason: from getter */
    public final String getDescription() {
        return this.description;
    }

    @NotNull
    /* renamed from: component4, reason: from getter */
    public final String getWay() {
        return this.way;
    }

    @NotNull
    /* renamed from: component5, reason: from getter */
    public final WayData getData() {
        return this.data;
    }

    @NotNull
    public final Ways copy(@NotNull String icon, @NotNull String title, @NotNull String description, @NotNull String way, @NotNull WayData data) {
        Intrinsics.checkNotNullParameter(icon, "icon");
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(description, "description");
        Intrinsics.checkNotNullParameter(way, "way");
        Intrinsics.checkNotNullParameter(data, "data");
        return new Ways(icon, title, description, way, data);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Ways)) {
            return false;
        }
        Ways ways = (Ways) other;
        return Intrinsics.areEqual(this.icon, ways.icon) && Intrinsics.areEqual(this.title, ways.title) && Intrinsics.areEqual(this.description, ways.description) && Intrinsics.areEqual(this.way, ways.way) && Intrinsics.areEqual(this.data, ways.data);
    }

    @NotNull
    public final WayData getData() {
        return this.data;
    }

    @NotNull
    public final String getDescription() {
        return this.description;
    }

    @NotNull
    public final String getIcon() {
        return this.icon;
    }

    @NotNull
    public final String getTitle() {
        return this.title;
    }

    @NotNull
    public final String getWay() {
        return this.way;
    }

    public int hashCode() {
        return (((((((this.icon.hashCode() * 31) + this.title.hashCode()) * 31) + this.description.hashCode()) * 31) + this.way.hashCode()) * 31) + this.data.hashCode();
    }

    public final void setData(@NotNull WayData wayData) {
        Intrinsics.checkNotNullParameter(wayData, "<set-?>");
        this.data = wayData;
    }

    public final void setDescription(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.description = str;
    }

    public final void setIcon(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.icon = str;
    }

    public final void setTitle(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.title = str;
    }

    public final void setWay(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.way = str;
    }

    @NotNull
    public String toString() {
        return "Ways(icon=" + this.icon + ", title=" + this.title + ", description=" + this.description + ", way=" + this.way + ", data=" + this.data + ')';
    }
}
