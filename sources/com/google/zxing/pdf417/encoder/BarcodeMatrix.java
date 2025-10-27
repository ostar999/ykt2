package com.google.zxing.pdf417.encoder;

import java.lang.reflect.Array;

/* loaded from: classes4.dex */
public final class BarcodeMatrix {
    private int currentRow;
    private final int height;
    private final BarcodeRow[] matrix;
    private final int width;

    public BarcodeMatrix(int i2, int i3) {
        BarcodeRow[] barcodeRowArr = new BarcodeRow[i2];
        this.matrix = barcodeRowArr;
        int length = barcodeRowArr.length;
        for (int i4 = 0; i4 < length; i4++) {
            this.matrix[i4] = new BarcodeRow(((i3 + 4) * 17) + 1);
        }
        this.width = i3 * 17;
        this.height = i2;
        this.currentRow = -1;
    }

    public BarcodeRow getCurrentRow() {
        return this.matrix[this.currentRow];
    }

    public byte[][] getMatrix() {
        return getScaledMatrix(1, 1);
    }

    public byte[][] getScaledMatrix(int i2, int i3) {
        byte[][] bArr = (byte[][]) Array.newInstance((Class<?>) Byte.TYPE, this.height * i3, this.width * i2);
        int i4 = this.height * i3;
        for (int i5 = 0; i5 < i4; i5++) {
            bArr[(i4 - i5) - 1] = this.matrix[i5 / i3].getScaledRow(i2);
        }
        return bArr;
    }

    public void set(int i2, int i3, byte b3) {
        this.matrix[i3].set(i2, b3);
    }

    public void startRow() {
        this.currentRow++;
    }
}
