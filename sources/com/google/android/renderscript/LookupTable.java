package com.google.android.renderscript;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\u000e\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001a\u0010\f\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\bR\u001a\u0010\u000f\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\b¨\u0006\u0012"}, d2 = {"Lcom/google/android/renderscript/LookupTable;", "", "()V", "alpha", "", "getAlpha", "()[B", "setAlpha", "([B)V", "blue", "getBlue", "setBlue", "green", "getGreen", "setGreen", "red", "getRed", "setRed", "renderscript-toolkit_release"}, k = 1, mv = {1, 4, 2})
/* loaded from: classes3.dex */
public final class LookupTable {

    @NotNull
    private byte[] alpha;

    @NotNull
    private byte[] blue;

    @NotNull
    private byte[] green;

    @NotNull
    private byte[] red;

    public LookupTable() {
        byte[] bArr = new byte[256];
        for (int i2 = 0; i2 < 256; i2++) {
            bArr[i2] = (byte) i2;
        }
        this.red = bArr;
        byte[] bArr2 = new byte[256];
        for (int i3 = 0; i3 < 256; i3++) {
            bArr2[i3] = (byte) i3;
        }
        this.green = bArr2;
        byte[] bArr3 = new byte[256];
        for (int i4 = 0; i4 < 256; i4++) {
            bArr3[i4] = (byte) i4;
        }
        this.blue = bArr3;
        byte[] bArr4 = new byte[256];
        for (int i5 = 0; i5 < 256; i5++) {
            bArr4[i5] = (byte) i5;
        }
        this.alpha = bArr4;
    }

    @NotNull
    public final byte[] getAlpha() {
        return this.alpha;
    }

    @NotNull
    public final byte[] getBlue() {
        return this.blue;
    }

    @NotNull
    public final byte[] getGreen() {
        return this.green;
    }

    @NotNull
    public final byte[] getRed() {
        return this.red;
    }

    public final void setAlpha(@NotNull byte[] bArr) {
        Intrinsics.checkNotNullParameter(bArr, "<set-?>");
        this.alpha = bArr;
    }

    public final void setBlue(@NotNull byte[] bArr) {
        Intrinsics.checkNotNullParameter(bArr, "<set-?>");
        this.blue = bArr;
    }

    public final void setGreen(@NotNull byte[] bArr) {
        Intrinsics.checkNotNullParameter(bArr, "<set-?>");
        this.green = bArr;
    }

    public final void setRed(@NotNull byte[] bArr) {
        Intrinsics.checkNotNullParameter(bArr, "<set-?>");
        this.red = bArr;
    }
}
