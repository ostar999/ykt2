package com.psychiatrygarden.bean;

import com.psychiatrygarden.bean.RedEnvelopeCouponsBean;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B)\u0012\u000e\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0006¢\u0006\u0002\u0010\bJ\u0011\u0010\u000e\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0006HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0006HÆ\u0003J/\u0010\u0011\u001a\u00020\u00002\u0010\b\u0002\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u0006HÆ\u0001J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0006HÖ\u0001R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0019\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0007\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\n¨\u0006\u0018"}, d2 = {"Lcom/psychiatrygarden/bean/CouponData;", "", "list", "", "Lcom/psychiatrygarden/bean/RedEnvelopeCouponsBean$RedEnvelopeCouponsDataItem;", "available_count", "", "not_available_count", "(Ljava/util/List;Ljava/lang/String;Ljava/lang/String;)V", "getAvailable_count", "()Ljava/lang/String;", "getList", "()Ljava/util/List;", "getNot_available_count", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final /* data */ class CouponData {

    @NotNull
    private final String available_count;

    @Nullable
    private final List<RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem> list;

    @NotNull
    private final String not_available_count;

    /* JADX WARN: Multi-variable type inference failed */
    public CouponData(@Nullable List<? extends RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem> list, @NotNull String available_count, @NotNull String not_available_count) {
        Intrinsics.checkNotNullParameter(available_count, "available_count");
        Intrinsics.checkNotNullParameter(not_available_count, "not_available_count");
        this.list = list;
        this.available_count = available_count;
        this.not_available_count = not_available_count;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ CouponData copy$default(CouponData couponData, List list, String str, String str2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            list = couponData.list;
        }
        if ((i2 & 2) != 0) {
            str = couponData.available_count;
        }
        if ((i2 & 4) != 0) {
            str2 = couponData.not_available_count;
        }
        return couponData.copy(list, str, str2);
    }

    @Nullable
    public final List<RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem> component1() {
        return this.list;
    }

    @NotNull
    /* renamed from: component2, reason: from getter */
    public final String getAvailable_count() {
        return this.available_count;
    }

    @NotNull
    /* renamed from: component3, reason: from getter */
    public final String getNot_available_count() {
        return this.not_available_count;
    }

    @NotNull
    public final CouponData copy(@Nullable List<? extends RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem> list, @NotNull String available_count, @NotNull String not_available_count) {
        Intrinsics.checkNotNullParameter(available_count, "available_count");
        Intrinsics.checkNotNullParameter(not_available_count, "not_available_count");
        return new CouponData(list, available_count, not_available_count);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CouponData)) {
            return false;
        }
        CouponData couponData = (CouponData) other;
        return Intrinsics.areEqual(this.list, couponData.list) && Intrinsics.areEqual(this.available_count, couponData.available_count) && Intrinsics.areEqual(this.not_available_count, couponData.not_available_count);
    }

    @NotNull
    public final String getAvailable_count() {
        return this.available_count;
    }

    @Nullable
    public final List<RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem> getList() {
        return this.list;
    }

    @NotNull
    public final String getNot_available_count() {
        return this.not_available_count;
    }

    public int hashCode() {
        List<RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem> list = this.list;
        return ((((list == null ? 0 : list.hashCode()) * 31) + this.available_count.hashCode()) * 31) + this.not_available_count.hashCode();
    }

    @NotNull
    public String toString() {
        return "CouponData(list=" + this.list + ", available_count=" + this.available_count + ", not_available_count=" + this.not_available_count + ')';
    }

    public /* synthetic */ CouponData(List list, String str, String str2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(list, (i2 & 2) != 0 ? "0" : str, (i2 & 4) != 0 ? "0" : str2);
    }
}
