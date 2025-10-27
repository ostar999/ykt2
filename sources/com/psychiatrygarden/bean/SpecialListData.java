package com.psychiatrygarden.bean;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B-\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\u0002\u0010\tJ\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0010J\u000b\u0010\u0013\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u0010\u0014\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u0010\u0015\u001a\u0004\u0018\u00010\bHÆ\u0003J>\u0010\u0016\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\bHÆ\u0001¢\u0006\u0002\u0010\u0017J\u0013\u0010\u0018\u001a\u00020\u00032\b\u0010\u0019\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001a\u001a\u00020\u001bHÖ\u0001J\t\u0010\u001c\u001a\u00020\u0005HÖ\u0001R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000bR\u0015\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\u0011\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u001d"}, d2 = {"Lcom/psychiatrygarden/bean/SpecialListData;", "", "success", "", "message", "", "code", "data", "Lcom/psychiatrygarden/bean/SpecialListInnerData;", "(Ljava/lang/Boolean;Ljava/lang/String;Ljava/lang/String;Lcom/psychiatrygarden/bean/SpecialListInnerData;)V", "getCode", "()Ljava/lang/String;", "getData", "()Lcom/psychiatrygarden/bean/SpecialListInnerData;", "getMessage", "getSuccess", "()Ljava/lang/Boolean;", "Ljava/lang/Boolean;", "component1", "component2", "component3", "component4", "copy", "(Ljava/lang/Boolean;Ljava/lang/String;Ljava/lang/String;Lcom/psychiatrygarden/bean/SpecialListInnerData;)Lcom/psychiatrygarden/bean/SpecialListData;", "equals", "other", "hashCode", "", "toString", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final /* data */ class SpecialListData {

    @Nullable
    private final String code;

    @Nullable
    private final SpecialListInnerData data;

    @Nullable
    private final String message;

    @Nullable
    private final Boolean success;

    public SpecialListData(@Nullable Boolean bool, @Nullable String str, @Nullable String str2, @Nullable SpecialListInnerData specialListInnerData) {
        this.success = bool;
        this.message = str;
        this.code = str2;
        this.data = specialListInnerData;
    }

    public static /* synthetic */ SpecialListData copy$default(SpecialListData specialListData, Boolean bool, String str, String str2, SpecialListInnerData specialListInnerData, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            bool = specialListData.success;
        }
        if ((i2 & 2) != 0) {
            str = specialListData.message;
        }
        if ((i2 & 4) != 0) {
            str2 = specialListData.code;
        }
        if ((i2 & 8) != 0) {
            specialListInnerData = specialListData.data;
        }
        return specialListData.copy(bool, str, str2, specialListInnerData);
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final Boolean getSuccess() {
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
    public final SpecialListInnerData getData() {
        return this.data;
    }

    @NotNull
    public final SpecialListData copy(@Nullable Boolean success, @Nullable String message, @Nullable String code, @Nullable SpecialListInnerData data) {
        return new SpecialListData(success, message, code, data);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SpecialListData)) {
            return false;
        }
        SpecialListData specialListData = (SpecialListData) other;
        return Intrinsics.areEqual(this.success, specialListData.success) && Intrinsics.areEqual(this.message, specialListData.message) && Intrinsics.areEqual(this.code, specialListData.code) && Intrinsics.areEqual(this.data, specialListData.data);
    }

    @Nullable
    public final String getCode() {
        return this.code;
    }

    @Nullable
    public final SpecialListInnerData getData() {
        return this.data;
    }

    @Nullable
    public final String getMessage() {
        return this.message;
    }

    @Nullable
    public final Boolean getSuccess() {
        return this.success;
    }

    public int hashCode() {
        Boolean bool = this.success;
        int iHashCode = (bool == null ? 0 : bool.hashCode()) * 31;
        String str = this.message;
        int iHashCode2 = (iHashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.code;
        int iHashCode3 = (iHashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        SpecialListInnerData specialListInnerData = this.data;
        return iHashCode3 + (specialListInnerData != null ? specialListInnerData.hashCode() : 0);
    }

    @NotNull
    public String toString() {
        return "SpecialListData(success=" + this.success + ", message=" + this.message + ", code=" + this.code + ", data=" + this.data + ')';
    }
}
