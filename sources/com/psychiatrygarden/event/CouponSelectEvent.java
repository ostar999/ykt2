package com.psychiatrygarden.event;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\n\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000b\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\r\u001a\u00020\u00032\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001J\t\u0010\u0011\u001a\u00020\u0005HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\u0012"}, d2 = {"Lcom/psychiatrygarden/event/CouponSelectEvent;", "", "isCoupon", "", "selectId", "", "(ZLjava/lang/String;)V", "()Z", "getSelectId", "()Ljava/lang/String;", "component1", "component2", "copy", "equals", "other", "hashCode", "", "toString", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final /* data */ class CouponSelectEvent {
    private final boolean isCoupon;

    @NotNull
    private final String selectId;

    public CouponSelectEvent(boolean z2, @NotNull String selectId) {
        Intrinsics.checkNotNullParameter(selectId, "selectId");
        this.isCoupon = z2;
        this.selectId = selectId;
    }

    public static /* synthetic */ CouponSelectEvent copy$default(CouponSelectEvent couponSelectEvent, boolean z2, String str, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            z2 = couponSelectEvent.isCoupon;
        }
        if ((i2 & 2) != 0) {
            str = couponSelectEvent.selectId;
        }
        return couponSelectEvent.copy(z2, str);
    }

    /* renamed from: component1, reason: from getter */
    public final boolean getIsCoupon() {
        return this.isCoupon;
    }

    @NotNull
    /* renamed from: component2, reason: from getter */
    public final String getSelectId() {
        return this.selectId;
    }

    @NotNull
    public final CouponSelectEvent copy(boolean isCoupon, @NotNull String selectId) {
        Intrinsics.checkNotNullParameter(selectId, "selectId");
        return new CouponSelectEvent(isCoupon, selectId);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CouponSelectEvent)) {
            return false;
        }
        CouponSelectEvent couponSelectEvent = (CouponSelectEvent) other;
        return this.isCoupon == couponSelectEvent.isCoupon && Intrinsics.areEqual(this.selectId, couponSelectEvent.selectId);
    }

    @NotNull
    public final String getSelectId() {
        return this.selectId;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [int] */
    /* JADX WARN: Type inference failed for: r0v4 */
    /* JADX WARN: Type inference failed for: r0v5 */
    public int hashCode() {
        boolean z2 = this.isCoupon;
        ?? r02 = z2;
        if (z2) {
            r02 = 1;
        }
        return (r02 * 31) + this.selectId.hashCode();
    }

    public final boolean isCoupon() {
        return this.isCoupon;
    }

    @NotNull
    public String toString() {
        return "CouponSelectEvent(isCoupon=" + this.isCoupon + ", selectId=" + this.selectId + ')';
    }
}
