package com.google.android.renderscript;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0007\b\u0016¢\u0006\u0002\u0010\u0002B%\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0004\u0012\u0006\u0010\u0007\u001a\u00020\u0004¢\u0006\u0002\u0010\bJ\t\u0010\u000e\u001a\u00020\u0004HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0004HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0004HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0004HÆ\u0003J1\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u00042\b\b\u0002\u0010\u0006\u001a\u00020\u00042\b\b\u0002\u0010\u0007\u001a\u00020\u0004HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0004HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001R\u0011\u0010\u0005\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\n¨\u0006\u0019"}, d2 = {"Lcom/google/android/renderscript/Range2d;", "", "()V", "startX", "", "endX", "startY", "endY", "(IIII)V", "getEndX", "()I", "getEndY", "getStartX", "getStartY", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "toString", "", "renderscript-toolkit_release"}, k = 1, mv = {1, 4, 2})
/* loaded from: classes3.dex */
public final /* data */ class Range2d {
    private final int endX;
    private final int endY;
    private final int startX;
    private final int startY;

    public Range2d(int i2, int i3, int i4, int i5) {
        this.startX = i2;
        this.endX = i3;
        this.startY = i4;
        this.endY = i5;
    }

    public static /* synthetic */ Range2d copy$default(Range2d range2d, int i2, int i3, int i4, int i5, int i6, Object obj) {
        if ((i6 & 1) != 0) {
            i2 = range2d.startX;
        }
        if ((i6 & 2) != 0) {
            i3 = range2d.endX;
        }
        if ((i6 & 4) != 0) {
            i4 = range2d.startY;
        }
        if ((i6 & 8) != 0) {
            i5 = range2d.endY;
        }
        return range2d.copy(i2, i3, i4, i5);
    }

    /* renamed from: component1, reason: from getter */
    public final int getStartX() {
        return this.startX;
    }

    /* renamed from: component2, reason: from getter */
    public final int getEndX() {
        return this.endX;
    }

    /* renamed from: component3, reason: from getter */
    public final int getStartY() {
        return this.startY;
    }

    /* renamed from: component4, reason: from getter */
    public final int getEndY() {
        return this.endY;
    }

    @NotNull
    public final Range2d copy(int startX, int endX, int startY, int endY) {
        return new Range2d(startX, endX, startY, endY);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Range2d)) {
            return false;
        }
        Range2d range2d = (Range2d) other;
        return this.startX == range2d.startX && this.endX == range2d.endX && this.startY == range2d.startY && this.endY == range2d.endY;
    }

    public final int getEndX() {
        return this.endX;
    }

    public final int getEndY() {
        return this.endY;
    }

    public final int getStartX() {
        return this.startX;
    }

    public final int getStartY() {
        return this.startY;
    }

    public int hashCode() {
        return (((((this.startX * 31) + this.endX) * 31) + this.startY) * 31) + this.endY;
    }

    @NotNull
    public String toString() {
        return "Range2d(startX=" + this.startX + ", endX=" + this.endX + ", startY=" + this.startY + ", endY=" + this.endY + ")";
    }

    public Range2d() {
        this(0, 0, 0, 0);
    }
}
