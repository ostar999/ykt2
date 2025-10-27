package com.psychiatrygarden.bean;

import com.aliyun.auth.common.AliyunVodHttpCommon;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B#\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0006J\u000b\u0010\u000b\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\f\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\r\u001a\u0004\u0018\u00010\u0003HÆ\u0003J-\u0010\u000e\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0003HÖ\u0001R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\bR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\b¨\u0006\u0015"}, d2 = {"Lcom/psychiatrygarden/bean/SpecialItemBean;", "", "id", "", "title", AliyunVodHttpCommon.ImageType.IMAGETYPE_COVER, "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getCover", "()Ljava/lang/String;", "getId", "getTitle", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final /* data */ class SpecialItemBean {

    @Nullable
    private final String cover;

    @Nullable
    private final String id;

    @Nullable
    private final String title;

    public SpecialItemBean(@Nullable String str, @Nullable String str2, @Nullable String str3) {
        this.id = str;
        this.title = str2;
        this.cover = str3;
    }

    public static /* synthetic */ SpecialItemBean copy$default(SpecialItemBean specialItemBean, String str, String str2, String str3, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = specialItemBean.id;
        }
        if ((i2 & 2) != 0) {
            str2 = specialItemBean.title;
        }
        if ((i2 & 4) != 0) {
            str3 = specialItemBean.cover;
        }
        return specialItemBean.copy(str, str2, str3);
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final String getId() {
        return this.id;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getTitle() {
        return this.title;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final String getCover() {
        return this.cover;
    }

    @NotNull
    public final SpecialItemBean copy(@Nullable String id, @Nullable String title, @Nullable String cover) {
        return new SpecialItemBean(id, title, cover);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SpecialItemBean)) {
            return false;
        }
        SpecialItemBean specialItemBean = (SpecialItemBean) other;
        return Intrinsics.areEqual(this.id, specialItemBean.id) && Intrinsics.areEqual(this.title, specialItemBean.title) && Intrinsics.areEqual(this.cover, specialItemBean.cover);
    }

    @Nullable
    public final String getCover() {
        return this.cover;
    }

    @Nullable
    public final String getId() {
        return this.id;
    }

    @Nullable
    public final String getTitle() {
        return this.title;
    }

    public int hashCode() {
        String str = this.id;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.title;
        int iHashCode2 = (iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.cover;
        return iHashCode2 + (str3 != null ? str3.hashCode() : 0);
    }

    @NotNull
    public String toString() {
        return "SpecialItemBean(id=" + this.id + ", title=" + this.title + ", cover=" + this.cover + ')';
    }
}
