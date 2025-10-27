package com.psychiatrygarden.bean;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B-\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\bJ\u000b\u0010\u000f\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0010\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u0010\u0011\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0012\u001a\u0004\u0018\u00010\u0003HÆ\u0003J9\u0010\u0013\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001J\t\u0010\u0019\u001a\u00020\u0003HÖ\u0001R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\nR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\n¨\u0006\u001a"}, d2 = {"Lcom/psychiatrygarden/bean/AdBean;", "", "code", "", "data", "Lcom/psychiatrygarden/bean/AdDataBean;", "message", "server_time", "(Ljava/lang/String;Lcom/psychiatrygarden/bean/AdDataBean;Ljava/lang/String;Ljava/lang/String;)V", "getCode", "()Ljava/lang/String;", "getData", "()Lcom/psychiatrygarden/bean/AdDataBean;", "getMessage", "getServer_time", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "", "toString", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final /* data */ class AdBean {

    @Nullable
    private final String code;

    @Nullable
    private final AdDataBean data;

    @Nullable
    private final String message;

    @Nullable
    private final String server_time;

    public AdBean(@Nullable String str, @Nullable AdDataBean adDataBean, @Nullable String str2, @Nullable String str3) {
        this.code = str;
        this.data = adDataBean;
        this.message = str2;
        this.server_time = str3;
    }

    public static /* synthetic */ AdBean copy$default(AdBean adBean, String str, AdDataBean adDataBean, String str2, String str3, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = adBean.code;
        }
        if ((i2 & 2) != 0) {
            adDataBean = adBean.data;
        }
        if ((i2 & 4) != 0) {
            str2 = adBean.message;
        }
        if ((i2 & 8) != 0) {
            str3 = adBean.server_time;
        }
        return adBean.copy(str, adDataBean, str2, str3);
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final String getCode() {
        return this.code;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final AdDataBean getData() {
        return this.data;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final String getMessage() {
        return this.message;
    }

    @Nullable
    /* renamed from: component4, reason: from getter */
    public final String getServer_time() {
        return this.server_time;
    }

    @NotNull
    public final AdBean copy(@Nullable String code, @Nullable AdDataBean data, @Nullable String message, @Nullable String server_time) {
        return new AdBean(code, data, message, server_time);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AdBean)) {
            return false;
        }
        AdBean adBean = (AdBean) other;
        return Intrinsics.areEqual(this.code, adBean.code) && Intrinsics.areEqual(this.data, adBean.data) && Intrinsics.areEqual(this.message, adBean.message) && Intrinsics.areEqual(this.server_time, adBean.server_time);
    }

    @Nullable
    public final String getCode() {
        return this.code;
    }

    @Nullable
    public final AdDataBean getData() {
        return this.data;
    }

    @Nullable
    public final String getMessage() {
        return this.message;
    }

    @Nullable
    public final String getServer_time() {
        return this.server_time;
    }

    public int hashCode() {
        String str = this.code;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        AdDataBean adDataBean = this.data;
        int iHashCode2 = (iHashCode + (adDataBean == null ? 0 : adDataBean.hashCode())) * 31;
        String str2 = this.message;
        int iHashCode3 = (iHashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.server_time;
        return iHashCode3 + (str3 != null ? str3.hashCode() : 0);
    }

    @NotNull
    public String toString() {
        return "AdBean(code=" + this.code + ", data=" + this.data + ", message=" + this.message + ", server_time=" + this.server_time + ')';
    }
}
