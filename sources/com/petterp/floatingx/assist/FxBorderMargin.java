package com.petterp.floatingx.assist;

import com.meizu.cloud.pushsdk.notification.model.NotifyType;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0014\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B-\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003¢\u0006\u0002\u0010\u0007J\t\u0010\u0012\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J1\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001a\u001a\u00020\u001bHÖ\u0001J\t\u0010\u001c\u001a\u00020\u001dHÖ\u0001R\u001a\u0010\u0005\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\t\"\u0004\b\r\u0010\u000bR\u001a\u0010\u0006\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\t\"\u0004\b\u000f\u0010\u000bR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\t\"\u0004\b\u0011\u0010\u000b¨\u0006\u001e"}, d2 = {"Lcom/petterp/floatingx/assist/FxBorderMargin;", "", "t", "", NotifyType.LIGHTS, "b", "r", "(FFFF)V", "getB", "()F", "setB", "(F)V", "getL", "setL", "getR", "setR", "getT", "setT", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "", "toString", "", "floatingx_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes4.dex */
public final /* data */ class FxBorderMargin {
    private float b;
    private float l;
    private float r;
    private float t;

    public FxBorderMargin() {
        this(0.0f, 0.0f, 0.0f, 0.0f, 15, null);
    }

    public FxBorderMargin(float f2, float f3, float f4, float f5) {
        this.t = f2;
        this.l = f3;
        this.b = f4;
        this.r = f5;
    }

    public static /* synthetic */ FxBorderMargin copy$default(FxBorderMargin fxBorderMargin, float f2, float f3, float f4, float f5, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            f2 = fxBorderMargin.t;
        }
        if ((i2 & 2) != 0) {
            f3 = fxBorderMargin.l;
        }
        if ((i2 & 4) != 0) {
            f4 = fxBorderMargin.b;
        }
        if ((i2 & 8) != 0) {
            f5 = fxBorderMargin.r;
        }
        return fxBorderMargin.copy(f2, f3, f4, f5);
    }

    /* renamed from: component1, reason: from getter */
    public final float getT() {
        return this.t;
    }

    /* renamed from: component2, reason: from getter */
    public final float getL() {
        return this.l;
    }

    /* renamed from: component3, reason: from getter */
    public final float getB() {
        return this.b;
    }

    /* renamed from: component4, reason: from getter */
    public final float getR() {
        return this.r;
    }

    @NotNull
    public final FxBorderMargin copy(float t2, float l2, float b3, float r2) {
        return new FxBorderMargin(t2, l2, b3, r2);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof FxBorderMargin)) {
            return false;
        }
        FxBorderMargin fxBorderMargin = (FxBorderMargin) other;
        return Intrinsics.areEqual((Object) Float.valueOf(this.t), (Object) Float.valueOf(fxBorderMargin.t)) && Intrinsics.areEqual((Object) Float.valueOf(this.l), (Object) Float.valueOf(fxBorderMargin.l)) && Intrinsics.areEqual((Object) Float.valueOf(this.b), (Object) Float.valueOf(fxBorderMargin.b)) && Intrinsics.areEqual((Object) Float.valueOf(this.r), (Object) Float.valueOf(fxBorderMargin.r));
    }

    public final float getB() {
        return this.b;
    }

    public final float getL() {
        return this.l;
    }

    public final float getR() {
        return this.r;
    }

    public final float getT() {
        return this.t;
    }

    public int hashCode() {
        return (((((Float.floatToIntBits(this.t) * 31) + Float.floatToIntBits(this.l)) * 31) + Float.floatToIntBits(this.b)) * 31) + Float.floatToIntBits(this.r);
    }

    public final void setB(float f2) {
        this.b = f2;
    }

    public final void setL(float f2) {
        this.l = f2;
    }

    public final void setR(float f2) {
        this.r = f2;
    }

    public final void setT(float f2) {
        this.t = f2;
    }

    @NotNull
    public String toString() {
        return "FxBorderMargin(t=" + this.t + ", l=" + this.l + ", b=" + this.b + ", r=" + this.r + ')';
    }

    public /* synthetic */ FxBorderMargin(float f2, float f3, float f4, float f5, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 0.0f : f2, (i2 & 2) != 0 ? 0.0f : f3, (i2 & 4) != 0 ? 0.0f : f4, (i2 & 8) != 0 ? 0.0f : f5);
    }
}
