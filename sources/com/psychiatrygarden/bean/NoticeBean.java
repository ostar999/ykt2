package com.psychiatrygarden.bean;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0016\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001BA\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0001\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\tJ\u000b\u0010\u0012\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0013\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0014\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0015\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0016\u001a\u0004\u0018\u00010\u0001HÆ\u0003J\u000b\u0010\u0017\u001a\u0004\u0018\u00010\u0003HÆ\u0003JQ\u0010\u0018\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001c\u001a\u00020\u001dHÖ\u0001J\t\u0010\u001e\u001a\u00020\u0003HÖ\u0001R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0001¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000bR\u0013\u0010\b\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000bR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000b¨\u0006\u001f"}, d2 = {"Lcom/psychiatrygarden/bean/NoticeBean;", "", "web_url", "", "img", "times", "second", "target_params", "web_link", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;Ljava/lang/String;)V", "getImg", "()Ljava/lang/String;", "getSecond", "getTarget_params", "()Ljava/lang/Object;", "getTimes", "getWeb_link", "getWeb_url", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "", "other", "hashCode", "", "toString", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final /* data */ class NoticeBean {

    @Nullable
    private final String img;

    @Nullable
    private final String second;

    @Nullable
    private final Object target_params;

    @Nullable
    private final String times;

    @Nullable
    private final String web_link;

    @Nullable
    private final String web_url;

    public NoticeBean(@Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, @Nullable Object obj, @Nullable String str5) {
        this.web_url = str;
        this.img = str2;
        this.times = str3;
        this.second = str4;
        this.target_params = obj;
        this.web_link = str5;
    }

    public static /* synthetic */ NoticeBean copy$default(NoticeBean noticeBean, String str, String str2, String str3, String str4, Object obj, String str5, int i2, Object obj2) {
        if ((i2 & 1) != 0) {
            str = noticeBean.web_url;
        }
        if ((i2 & 2) != 0) {
            str2 = noticeBean.img;
        }
        String str6 = str2;
        if ((i2 & 4) != 0) {
            str3 = noticeBean.times;
        }
        String str7 = str3;
        if ((i2 & 8) != 0) {
            str4 = noticeBean.second;
        }
        String str8 = str4;
        if ((i2 & 16) != 0) {
            obj = noticeBean.target_params;
        }
        Object obj3 = obj;
        if ((i2 & 32) != 0) {
            str5 = noticeBean.web_link;
        }
        return noticeBean.copy(str, str6, str7, str8, obj3, str5);
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final String getWeb_url() {
        return this.web_url;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getImg() {
        return this.img;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final String getTimes() {
        return this.times;
    }

    @Nullable
    /* renamed from: component4, reason: from getter */
    public final String getSecond() {
        return this.second;
    }

    @Nullable
    /* renamed from: component5, reason: from getter */
    public final Object getTarget_params() {
        return this.target_params;
    }

    @Nullable
    /* renamed from: component6, reason: from getter */
    public final String getWeb_link() {
        return this.web_link;
    }

    @NotNull
    public final NoticeBean copy(@Nullable String web_url, @Nullable String img, @Nullable String times, @Nullable String second, @Nullable Object target_params, @Nullable String web_link) {
        return new NoticeBean(web_url, img, times, second, target_params, web_link);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof NoticeBean)) {
            return false;
        }
        NoticeBean noticeBean = (NoticeBean) other;
        return Intrinsics.areEqual(this.web_url, noticeBean.web_url) && Intrinsics.areEqual(this.img, noticeBean.img) && Intrinsics.areEqual(this.times, noticeBean.times) && Intrinsics.areEqual(this.second, noticeBean.second) && Intrinsics.areEqual(this.target_params, noticeBean.target_params) && Intrinsics.areEqual(this.web_link, noticeBean.web_link);
    }

    @Nullable
    public final String getImg() {
        return this.img;
    }

    @Nullable
    public final String getSecond() {
        return this.second;
    }

    @Nullable
    public final Object getTarget_params() {
        return this.target_params;
    }

    @Nullable
    public final String getTimes() {
        return this.times;
    }

    @Nullable
    public final String getWeb_link() {
        return this.web_link;
    }

    @Nullable
    public final String getWeb_url() {
        return this.web_url;
    }

    public int hashCode() {
        String str = this.web_url;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.img;
        int iHashCode2 = (iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.times;
        int iHashCode3 = (iHashCode2 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.second;
        int iHashCode4 = (iHashCode3 + (str4 == null ? 0 : str4.hashCode())) * 31;
        Object obj = this.target_params;
        int iHashCode5 = (iHashCode4 + (obj == null ? 0 : obj.hashCode())) * 31;
        String str5 = this.web_link;
        return iHashCode5 + (str5 != null ? str5.hashCode() : 0);
    }

    @NotNull
    public String toString() {
        return "NoticeBean(web_url=" + this.web_url + ", img=" + this.img + ", times=" + this.times + ", second=" + this.second + ", target_params=" + this.target_params + ", web_link=" + this.web_link + ')';
    }
}
