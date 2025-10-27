package com.psychiatrygarden.bean;

import com.huawei.hms.push.constant.RemoteMessageConst;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003¢\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J1\u0010\u0011\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t¨\u0006\u0018"}, d2 = {"Lcom/psychiatrygarden/bean/ParentCourseBean;", "", "id", "", "title", "description", RemoteMessageConst.Notification.ICON, "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getDescription", "()Ljava/lang/String;", "getIcon", "getId", "getTitle", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "", "toString", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final /* data */ class ParentCourseBean {

    @NotNull
    private final String description;

    @NotNull
    private final String icon;

    @NotNull
    private final String id;

    @NotNull
    private final String title;

    public ParentCourseBean(@NotNull String id, @NotNull String title, @NotNull String description, @NotNull String icon) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(description, "description");
        Intrinsics.checkNotNullParameter(icon, "icon");
        this.id = id;
        this.title = title;
        this.description = description;
        this.icon = icon;
    }

    public static /* synthetic */ ParentCourseBean copy$default(ParentCourseBean parentCourseBean, String str, String str2, String str3, String str4, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = parentCourseBean.id;
        }
        if ((i2 & 2) != 0) {
            str2 = parentCourseBean.title;
        }
        if ((i2 & 4) != 0) {
            str3 = parentCourseBean.description;
        }
        if ((i2 & 8) != 0) {
            str4 = parentCourseBean.icon;
        }
        return parentCourseBean.copy(str, str2, str3, str4);
    }

    @NotNull
    /* renamed from: component1, reason: from getter */
    public final String getId() {
        return this.id;
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
    public final String getIcon() {
        return this.icon;
    }

    @NotNull
    public final ParentCourseBean copy(@NotNull String id, @NotNull String title, @NotNull String description, @NotNull String icon) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(description, "description");
        Intrinsics.checkNotNullParameter(icon, "icon");
        return new ParentCourseBean(id, title, description, icon);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ParentCourseBean)) {
            return false;
        }
        ParentCourseBean parentCourseBean = (ParentCourseBean) other;
        return Intrinsics.areEqual(this.id, parentCourseBean.id) && Intrinsics.areEqual(this.title, parentCourseBean.title) && Intrinsics.areEqual(this.description, parentCourseBean.description) && Intrinsics.areEqual(this.icon, parentCourseBean.icon);
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
    public final String getId() {
        return this.id;
    }

    @NotNull
    public final String getTitle() {
        return this.title;
    }

    public int hashCode() {
        return (((((this.id.hashCode() * 31) + this.title.hashCode()) * 31) + this.description.hashCode()) * 31) + this.icon.hashCode();
    }

    @NotNull
    public String toString() {
        return "ParentCourseBean(id=" + this.id + ", title=" + this.title + ", description=" + this.description + ", icon=" + this.icon + ')';
    }
}
