package com.psychiatrygarden.bean;

import com.plv.foundationsdk.rx.PLVRxEncryptDataFunction;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u001b\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B?\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\nJ\t\u0010\u001b\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u001c\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u0010\u001d\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u0010\u001e\u001a\u0004\u0018\u00010\bHÆ\u0003J\u000b\u0010\u001f\u001a\u0004\u0018\u00010\u0005HÆ\u0003JC\u0010 \u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0005HÆ\u0001J\u0013\u0010!\u001a\u00020\u00032\b\u0010\"\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010#\u001a\u00020$HÖ\u0001J\t\u0010%\u001a\u00020\u0005HÖ\u0001R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\f\"\u0004\b\u0014\u0010\u000eR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\f\"\u0004\b\u0016\u0010\u000eR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001a¨\u0006&"}, d2 = {"Lcom/psychiatrygarden/bean/GetPermissionBean;", "", "success", "", "message", "", "code", "data", "Lcom/psychiatrygarden/bean/GetPermissionBeanData;", "server_time", "(ZLjava/lang/String;Ljava/lang/String;Lcom/psychiatrygarden/bean/GetPermissionBeanData;Ljava/lang/String;)V", "getCode", "()Ljava/lang/String;", "setCode", "(Ljava/lang/String;)V", "getData", "()Lcom/psychiatrygarden/bean/GetPermissionBeanData;", PLVRxEncryptDataFunction.SET_DATA_METHOD, "(Lcom/psychiatrygarden/bean/GetPermissionBeanData;)V", "getMessage", "setMessage", "getServer_time", "setServer_time", "getSuccess", "()Z", "setSuccess", "(Z)V", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "other", "hashCode", "", "toString", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final /* data */ class GetPermissionBean {

    @Nullable
    private String code;

    @Nullable
    private GetPermissionBeanData data;

    @Nullable
    private String message;

    @Nullable
    private String server_time;
    private boolean success;

    public GetPermissionBean() {
        this(false, null, null, null, null, 31, null);
    }

    public GetPermissionBean(boolean z2, @Nullable String str, @Nullable String str2, @Nullable GetPermissionBeanData getPermissionBeanData, @Nullable String str3) {
        this.success = z2;
        this.message = str;
        this.code = str2;
        this.data = getPermissionBeanData;
        this.server_time = str3;
    }

    public static /* synthetic */ GetPermissionBean copy$default(GetPermissionBean getPermissionBean, boolean z2, String str, String str2, GetPermissionBeanData getPermissionBeanData, String str3, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            z2 = getPermissionBean.success;
        }
        if ((i2 & 2) != 0) {
            str = getPermissionBean.message;
        }
        String str4 = str;
        if ((i2 & 4) != 0) {
            str2 = getPermissionBean.code;
        }
        String str5 = str2;
        if ((i2 & 8) != 0) {
            getPermissionBeanData = getPermissionBean.data;
        }
        GetPermissionBeanData getPermissionBeanData2 = getPermissionBeanData;
        if ((i2 & 16) != 0) {
            str3 = getPermissionBean.server_time;
        }
        return getPermissionBean.copy(z2, str4, str5, getPermissionBeanData2, str3);
    }

    /* renamed from: component1, reason: from getter */
    public final boolean getSuccess() {
        return this.success;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getMessage() {
        return this.message;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final String getCode() {
        return this.code;
    }

    @Nullable
    /* renamed from: component4, reason: from getter */
    public final GetPermissionBeanData getData() {
        return this.data;
    }

    @Nullable
    /* renamed from: component5, reason: from getter */
    public final String getServer_time() {
        return this.server_time;
    }

    @NotNull
    public final GetPermissionBean copy(boolean success, @Nullable String message, @Nullable String code, @Nullable GetPermissionBeanData data, @Nullable String server_time) {
        return new GetPermissionBean(success, message, code, data, server_time);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof GetPermissionBean)) {
            return false;
        }
        GetPermissionBean getPermissionBean = (GetPermissionBean) other;
        return this.success == getPermissionBean.success && Intrinsics.areEqual(this.message, getPermissionBean.message) && Intrinsics.areEqual(this.code, getPermissionBean.code) && Intrinsics.areEqual(this.data, getPermissionBean.data) && Intrinsics.areEqual(this.server_time, getPermissionBean.server_time);
    }

    @Nullable
    public final String getCode() {
        return this.code;
    }

    @Nullable
    public final GetPermissionBeanData getData() {
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

    public final boolean getSuccess() {
        return this.success;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [int] */
    /* JADX WARN: Type inference failed for: r0v10 */
    /* JADX WARN: Type inference failed for: r0v11 */
    public int hashCode() {
        boolean z2 = this.success;
        ?? r02 = z2;
        if (z2) {
            r02 = 1;
        }
        int i2 = r02 * 31;
        String str = this.message;
        int iHashCode = (i2 + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.code;
        int iHashCode2 = (iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        GetPermissionBeanData getPermissionBeanData = this.data;
        int iHashCode3 = (iHashCode2 + (getPermissionBeanData == null ? 0 : getPermissionBeanData.hashCode())) * 31;
        String str3 = this.server_time;
        return iHashCode3 + (str3 != null ? str3.hashCode() : 0);
    }

    public final void setCode(@Nullable String str) {
        this.code = str;
    }

    public final void setData(@Nullable GetPermissionBeanData getPermissionBeanData) {
        this.data = getPermissionBeanData;
    }

    public final void setMessage(@Nullable String str) {
        this.message = str;
    }

    public final void setServer_time(@Nullable String str) {
        this.server_time = str;
    }

    public final void setSuccess(boolean z2) {
        this.success = z2;
    }

    @NotNull
    public String toString() {
        return "GetPermissionBean(success=" + this.success + ", message=" + this.message + ", code=" + this.code + ", data=" + this.data + ", server_time=" + this.server_time + ')';
    }

    public /* synthetic */ GetPermissionBean(boolean z2, String str, String str2, GetPermissionBeanData getPermissionBeanData, String str3, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? false : z2, (i2 & 2) != 0 ? null : str, (i2 & 4) != 0 ? null : str2, (i2 & 8) != 0 ? null : getPermissionBeanData, (i2 & 16) == 0 ? str3 : null);
    }
}
