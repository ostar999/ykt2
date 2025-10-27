package com.psychiatrygarden.bean;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0018\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001BK\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\nJ\u000b\u0010\u0013\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0014\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0015\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0016\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0017\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0018\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0019\u001a\u0004\u0018\u00010\u0003HÆ\u0003J]\u0010\u001a\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001e\u001a\u00020\u001fHÖ\u0001J\t\u0010 \u001a\u00020\u0003HÖ\u0001R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0013\u0010\t\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\fR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\fR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\fR\u0013\u0010\b\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\fR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\f¨\u0006!"}, d2 = {"Lcom/psychiatrygarden/bean/CourseMaterialsBean;", "", "title", "", "url", "download_times", "size_info", "size_byte", "type", "id", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getDownload_times", "()Ljava/lang/String;", "getId", "getSize_byte", "getSize_info", "getTitle", "getType", "getUrl", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "", "other", "hashCode", "", "toString", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final /* data */ class CourseMaterialsBean {

    @Nullable
    private final String download_times;

    @Nullable
    private final String id;

    @Nullable
    private final String size_byte;

    @Nullable
    private final String size_info;

    @Nullable
    private final String title;

    @Nullable
    private final String type;

    @Nullable
    private final String url;

    public CourseMaterialsBean(@Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, @Nullable String str5, @Nullable String str6, @Nullable String str7) {
        this.title = str;
        this.url = str2;
        this.download_times = str3;
        this.size_info = str4;
        this.size_byte = str5;
        this.type = str6;
        this.id = str7;
    }

    public static /* synthetic */ CourseMaterialsBean copy$default(CourseMaterialsBean courseMaterialsBean, String str, String str2, String str3, String str4, String str5, String str6, String str7, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = courseMaterialsBean.title;
        }
        if ((i2 & 2) != 0) {
            str2 = courseMaterialsBean.url;
        }
        String str8 = str2;
        if ((i2 & 4) != 0) {
            str3 = courseMaterialsBean.download_times;
        }
        String str9 = str3;
        if ((i2 & 8) != 0) {
            str4 = courseMaterialsBean.size_info;
        }
        String str10 = str4;
        if ((i2 & 16) != 0) {
            str5 = courseMaterialsBean.size_byte;
        }
        String str11 = str5;
        if ((i2 & 32) != 0) {
            str6 = courseMaterialsBean.type;
        }
        String str12 = str6;
        if ((i2 & 64) != 0) {
            str7 = courseMaterialsBean.id;
        }
        return courseMaterialsBean.copy(str, str8, str9, str10, str11, str12, str7);
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final String getTitle() {
        return this.title;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getUrl() {
        return this.url;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final String getDownload_times() {
        return this.download_times;
    }

    @Nullable
    /* renamed from: component4, reason: from getter */
    public final String getSize_info() {
        return this.size_info;
    }

    @Nullable
    /* renamed from: component5, reason: from getter */
    public final String getSize_byte() {
        return this.size_byte;
    }

    @Nullable
    /* renamed from: component6, reason: from getter */
    public final String getType() {
        return this.type;
    }

    @Nullable
    /* renamed from: component7, reason: from getter */
    public final String getId() {
        return this.id;
    }

    @NotNull
    public final CourseMaterialsBean copy(@Nullable String title, @Nullable String url, @Nullable String download_times, @Nullable String size_info, @Nullable String size_byte, @Nullable String type, @Nullable String id) {
        return new CourseMaterialsBean(title, url, download_times, size_info, size_byte, type, id);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CourseMaterialsBean)) {
            return false;
        }
        CourseMaterialsBean courseMaterialsBean = (CourseMaterialsBean) other;
        return Intrinsics.areEqual(this.title, courseMaterialsBean.title) && Intrinsics.areEqual(this.url, courseMaterialsBean.url) && Intrinsics.areEqual(this.download_times, courseMaterialsBean.download_times) && Intrinsics.areEqual(this.size_info, courseMaterialsBean.size_info) && Intrinsics.areEqual(this.size_byte, courseMaterialsBean.size_byte) && Intrinsics.areEqual(this.type, courseMaterialsBean.type) && Intrinsics.areEqual(this.id, courseMaterialsBean.id);
    }

    @Nullable
    public final String getDownload_times() {
        return this.download_times;
    }

    @Nullable
    public final String getId() {
        return this.id;
    }

    @Nullable
    public final String getSize_byte() {
        return this.size_byte;
    }

    @Nullable
    public final String getSize_info() {
        return this.size_info;
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
    public final String getUrl() {
        return this.url;
    }

    public int hashCode() {
        String str = this.title;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.url;
        int iHashCode2 = (iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.download_times;
        int iHashCode3 = (iHashCode2 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.size_info;
        int iHashCode4 = (iHashCode3 + (str4 == null ? 0 : str4.hashCode())) * 31;
        String str5 = this.size_byte;
        int iHashCode5 = (iHashCode4 + (str5 == null ? 0 : str5.hashCode())) * 31;
        String str6 = this.type;
        int iHashCode6 = (iHashCode5 + (str6 == null ? 0 : str6.hashCode())) * 31;
        String str7 = this.id;
        return iHashCode6 + (str7 != null ? str7.hashCode() : 0);
    }

    @NotNull
    public String toString() {
        return "CourseMaterialsBean(title=" + this.title + ", url=" + this.url + ", download_times=" + this.download_times + ", size_info=" + this.size_info + ", size_byte=" + this.size_byte + ", type=" + this.type + ", id=" + this.id + ')';
    }
}
