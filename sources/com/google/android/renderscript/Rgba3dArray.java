package com.google.android.renderscript;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(bv = {1, 0, 3}, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\b\n\u0002\b\u000f\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005¢\u0006\u0002\u0010\bJ!\u0010\u000f\u001a\u00020\u00032\u0006\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u00020\u00052\u0006\u0010\u0012\u001a\u00020\u0005H\u0086\u0002J \u0010\u0013\u001a\u00020\u00052\u0006\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u00020\u00052\u0006\u0010\u0012\u001a\u00020\u0005H\u0002J)\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u00020\u00052\u0006\u0010\u0012\u001a\u00020\u00052\u0006\u0010\u0016\u001a\u00020\u0003H\u0086\u0002R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0011\u0010\u0007\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0017"}, d2 = {"Lcom/google/android/renderscript/Rgba3dArray;", "", "values", "", "sizeX", "", "sizeY", "sizeZ", "([BIII)V", "getSizeX", "()I", "getSizeY", "getSizeZ", "getValues", "()[B", "get", "x", "y", "z", "indexOfVector", "set", "", "value", "renderscript-toolkit_release"}, k = 1, mv = {1, 4, 2})
/* loaded from: classes3.dex */
public final class Rgba3dArray {
    private final int sizeX;
    private final int sizeY;
    private final int sizeZ;

    @NotNull
    private final byte[] values;

    public Rgba3dArray(@NotNull byte[] values, int i2, int i3, int i4) {
        Intrinsics.checkNotNullParameter(values, "values");
        this.values = values;
        this.sizeX = i2;
        this.sizeY = i3;
        this.sizeZ = i4;
        if (!(values.length >= ((i2 * i3) * i4) * 4)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
    }

    private final int indexOfVector(int x2, int y2, int z2) {
        int i2 = this.sizeX;
        if (!(x2 >= 0 && i2 > x2)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        int i3 = this.sizeY;
        if (!(y2 >= 0 && i3 > y2)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        if (z2 >= 0 && this.sizeZ > z2) {
            return ((((z2 * i3) + y2) * i2) + x2) * 4;
        }
        throw new IllegalArgumentException("Failed requirement.".toString());
    }

    @NotNull
    public final byte[] get(int x2, int y2, int z2) {
        int iIndexOfVector = indexOfVector(x2, y2, z2);
        byte[] bArr = new byte[4];
        for (int i2 = 0; i2 < 4; i2++) {
            bArr[i2] = this.values[iIndexOfVector + i2];
        }
        return bArr;
    }

    public final int getSizeX() {
        return this.sizeX;
    }

    public final int getSizeY() {
        return this.sizeY;
    }

    public final int getSizeZ() {
        return this.sizeZ;
    }

    @NotNull
    public final byte[] getValues() {
        return this.values;
    }

    public final void set(int x2, int y2, int z2, @NotNull byte[] value) {
        Intrinsics.checkNotNullParameter(value, "value");
        if (!(value.length == 4)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        int iIndexOfVector = indexOfVector(x2, y2, z2);
        for (int i2 = 0; i2 <= 3; i2++) {
            this.values[iIndexOfVector + i2] = value[i2];
        }
    }
}
