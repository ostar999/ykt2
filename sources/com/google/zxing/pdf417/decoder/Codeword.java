package com.google.zxing.pdf417.decoder;

import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;

/* loaded from: classes4.dex */
final class Codeword {
    private static final int BARCODE_ROW_UNKNOWN = -1;
    private final int bucket;
    private final int endX;
    private int rowNumber = -1;
    private final int startX;
    private final int value;

    public Codeword(int i2, int i3, int i4, int i5) {
        this.startX = i2;
        this.endX = i3;
        this.bucket = i4;
        this.value = i5;
    }

    public int getBucket() {
        return this.bucket;
    }

    public int getEndX() {
        return this.endX;
    }

    public int getRowNumber() {
        return this.rowNumber;
    }

    public int getStartX() {
        return this.startX;
    }

    public int getValue() {
        return this.value;
    }

    public int getWidth() {
        return this.endX - this.startX;
    }

    public boolean hasValidRowNumber() {
        return isValidRowNumber(this.rowNumber);
    }

    public boolean isValidRowNumber(int i2) {
        return i2 != -1 && this.bucket == (i2 % 3) * 3;
    }

    public void setRowNumber(int i2) {
        this.rowNumber = i2;
    }

    public void setRowNumberAsRowIndicatorColumn() {
        this.rowNumber = ((this.value / 30) * 3) + (this.bucket / 3);
    }

    public String toString() {
        return this.rowNumber + HiAnalyticsConstant.REPORT_VAL_SEPARATOR + this.value;
    }
}
