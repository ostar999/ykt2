package com.psychiatrygarden.bean;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u000e\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u000f\u001a\u0004\u0018\u00010\u0006HÆ\u0003J+\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006HÆ\u0001J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001J\t\u0010\u0016\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t¨\u0006\u0017"}, d2 = {"Lcom/psychiatrygarden/bean/CombineQuestionBean;", "", "code", "", "message", "data", "Lcom/psychiatrygarden/bean/CombineQuestionBeanData;", "(Ljava/lang/String;Ljava/lang/String;Lcom/psychiatrygarden/bean/CombineQuestionBeanData;)V", "getCode", "()Ljava/lang/String;", "getData", "()Lcom/psychiatrygarden/bean/CombineQuestionBeanData;", "getMessage", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final /* data */ class CombineQuestionBean {

    @NotNull
    private final String code;

    @Nullable
    private final CombineQuestionBeanData data;

    @Nullable
    private final String message;

    public CombineQuestionBean(@NotNull String code, @Nullable String str, @Nullable CombineQuestionBeanData combineQuestionBeanData) {
        Intrinsics.checkNotNullParameter(code, "code");
        this.code = code;
        this.message = str;
        this.data = combineQuestionBeanData;
    }

    public static /* synthetic */ CombineQuestionBean copy$default(CombineQuestionBean combineQuestionBean, String str, String str2, CombineQuestionBeanData combineQuestionBeanData, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = combineQuestionBean.code;
        }
        if ((i2 & 2) != 0) {
            str2 = combineQuestionBean.message;
        }
        if ((i2 & 4) != 0) {
            combineQuestionBeanData = combineQuestionBean.data;
        }
        return combineQuestionBean.copy(str, str2, combineQuestionBeanData);
    }

    @NotNull
    /* renamed from: component1, reason: from getter */
    public final String getCode() {
        return this.code;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getMessage() {
        return this.message;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final CombineQuestionBeanData getData() {
        return this.data;
    }

    @NotNull
    public final CombineQuestionBean copy(@NotNull String code, @Nullable String message, @Nullable CombineQuestionBeanData data) {
        Intrinsics.checkNotNullParameter(code, "code");
        return new CombineQuestionBean(code, message, data);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CombineQuestionBean)) {
            return false;
        }
        CombineQuestionBean combineQuestionBean = (CombineQuestionBean) other;
        return Intrinsics.areEqual(this.code, combineQuestionBean.code) && Intrinsics.areEqual(this.message, combineQuestionBean.message) && Intrinsics.areEqual(this.data, combineQuestionBean.data);
    }

    @NotNull
    public final String getCode() {
        return this.code;
    }

    @Nullable
    public final CombineQuestionBeanData getData() {
        return this.data;
    }

    @Nullable
    public final String getMessage() {
        return this.message;
    }

    public int hashCode() {
        int iHashCode = this.code.hashCode() * 31;
        String str = this.message;
        int iHashCode2 = (iHashCode + (str == null ? 0 : str.hashCode())) * 31;
        CombineQuestionBeanData combineQuestionBeanData = this.data;
        return iHashCode2 + (combineQuestionBeanData != null ? combineQuestionBeanData.hashCode() : 0);
    }

    @NotNull
    public String toString() {
        return "CombineQuestionBean(code=" + this.code + ", message=" + this.message + ", data=" + this.data + ')';
    }
}
