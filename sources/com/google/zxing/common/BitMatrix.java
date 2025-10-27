package com.google.zxing.common;

import java.util.Arrays;

/* loaded from: classes4.dex */
public final class BitMatrix implements Cloneable {
    private final int[] bits;
    private final int height;
    private final int rowSize;
    private final int width;

    public BitMatrix(int i2) {
        this(i2, i2);
    }

    public void clear() {
        int length = this.bits.length;
        for (int i2 = 0; i2 < length; i2++) {
            this.bits[i2] = 0;
        }
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof BitMatrix)) {
            return false;
        }
        BitMatrix bitMatrix = (BitMatrix) obj;
        return this.width == bitMatrix.width && this.height == bitMatrix.height && this.rowSize == bitMatrix.rowSize && Arrays.equals(this.bits, bitMatrix.bits);
    }

    public void flip(int i2, int i3) {
        int i4 = (i3 * this.rowSize) + (i2 >> 5);
        int[] iArr = this.bits;
        iArr[i4] = (1 << (i2 & 31)) ^ iArr[i4];
    }

    public boolean get(int i2, int i3) {
        return ((this.bits[(i3 * this.rowSize) + (i2 >> 5)] >>> (i2 & 31)) & 1) != 0;
    }

    public int[] getBottomRightOnBit() {
        int length = this.bits.length - 1;
        while (length >= 0 && this.bits[length] == 0) {
            length--;
        }
        if (length < 0) {
            return null;
        }
        int i2 = this.rowSize;
        int i3 = length / i2;
        int i4 = (length % i2) << 5;
        int i5 = 31;
        while ((this.bits[length] >>> i5) == 0) {
            i5--;
        }
        return new int[]{i4 + i5, i3};
    }

    public int[] getEnclosingRectangle() {
        int i2 = this.width;
        int i3 = this.height;
        int i4 = -1;
        int i5 = -1;
        for (int i6 = 0; i6 < this.height; i6++) {
            int i7 = 0;
            while (true) {
                int i8 = this.rowSize;
                if (i7 < i8) {
                    int i9 = this.bits[(i8 * i6) + i7];
                    if (i9 != 0) {
                        if (i6 < i3) {
                            i3 = i6;
                        }
                        if (i6 > i5) {
                            i5 = i6;
                        }
                        int i10 = i7 * 32;
                        if (i10 < i2) {
                            int i11 = 0;
                            while ((i9 << (31 - i11)) == 0) {
                                i11++;
                            }
                            int i12 = i11 + i10;
                            if (i12 < i2) {
                                i2 = i12;
                            }
                        }
                        if (i10 + 31 > i4) {
                            int i13 = 31;
                            while ((i9 >>> i13) == 0) {
                                i13--;
                            }
                            int i14 = i10 + i13;
                            if (i14 > i4) {
                                i4 = i14;
                            }
                        }
                    }
                    i7++;
                }
            }
        }
        int i15 = i4 - i2;
        int i16 = i5 - i3;
        if (i15 < 0 || i16 < 0) {
            return null;
        }
        return new int[]{i2, i3, i15, i16};
    }

    public int getHeight() {
        return this.height;
    }

    public BitArray getRow(int i2, BitArray bitArray) {
        if (bitArray == null || bitArray.getSize() < this.width) {
            bitArray = new BitArray(this.width);
        } else {
            bitArray.clear();
        }
        int i3 = i2 * this.rowSize;
        for (int i4 = 0; i4 < this.rowSize; i4++) {
            bitArray.setBulk(i4 << 5, this.bits[i3 + i4]);
        }
        return bitArray;
    }

    public int[] getTopLeftOnBit() {
        int[] iArr;
        int i2 = 0;
        while (true) {
            iArr = this.bits;
            if (i2 >= iArr.length || iArr[i2] != 0) {
                break;
            }
            i2++;
        }
        if (i2 == iArr.length) {
            return null;
        }
        int i3 = this.rowSize;
        int i4 = i2 / i3;
        int i5 = (i2 % i3) << 5;
        int i6 = iArr[i2];
        int i7 = 0;
        while ((i6 << (31 - i7)) == 0) {
            i7++;
        }
        return new int[]{i5 + i7, i4};
    }

    public int getWidth() {
        return this.width;
    }

    public int hashCode() {
        int i2 = this.width;
        return (((((((i2 * 31) + i2) * 31) + this.height) * 31) + this.rowSize) * 31) + Arrays.hashCode(this.bits);
    }

    public void rotate180() {
        int width = getWidth();
        int height = getHeight();
        BitArray bitArray = new BitArray(width);
        BitArray bitArray2 = new BitArray(width);
        for (int i2 = 0; i2 < (height + 1) / 2; i2++) {
            bitArray = getRow(i2, bitArray);
            int i3 = (height - 1) - i2;
            bitArray2 = getRow(i3, bitArray2);
            bitArray.reverse();
            bitArray2.reverse();
            setRow(i2, bitArray2);
            setRow(i3, bitArray);
        }
    }

    public void set(int i2, int i3) {
        int i4 = (i3 * this.rowSize) + (i2 >> 5);
        int[] iArr = this.bits;
        iArr[i4] = (1 << (i2 & 31)) | iArr[i4];
    }

    public void setRegion(int i2, int i3, int i4, int i5) {
        if (i3 < 0 || i2 < 0) {
            throw new IllegalArgumentException("Left and top must be nonnegative");
        }
        if (i5 < 1 || i4 < 1) {
            throw new IllegalArgumentException("Height and width must be at least 1");
        }
        int i6 = i4 + i2;
        int i7 = i5 + i3;
        if (i7 > this.height || i6 > this.width) {
            throw new IllegalArgumentException("The region must fit inside the matrix");
        }
        while (i3 < i7) {
            int i8 = this.rowSize * i3;
            for (int i9 = i2; i9 < i6; i9++) {
                int[] iArr = this.bits;
                int i10 = (i9 >> 5) + i8;
                iArr[i10] = iArr[i10] | (1 << (i9 & 31));
            }
            i3++;
        }
    }

    public void setRow(int i2, BitArray bitArray) {
        int[] bitArray2 = bitArray.getBitArray();
        int[] iArr = this.bits;
        int i3 = this.rowSize;
        System.arraycopy(bitArray2, 0, iArr, i2 * i3, i3);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(this.height * (this.width + 1));
        for (int i2 = 0; i2 < this.height; i2++) {
            for (int i3 = 0; i3 < this.width; i3++) {
                sb.append(get(i3, i2) ? "X " : "  ");
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    public BitMatrix(int i2, int i3) {
        if (i2 < 1 || i3 < 1) {
            throw new IllegalArgumentException("Both dimensions must be greater than 0");
        }
        this.width = i2;
        this.height = i3;
        int i4 = (i2 + 31) >> 5;
        this.rowSize = i4;
        this.bits = new int[i4 * i3];
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public BitMatrix m93clone() {
        return new BitMatrix(this.width, this.height, this.rowSize, (int[]) this.bits.clone());
    }

    private BitMatrix(int i2, int i3, int i4, int[] iArr) {
        this.width = i2;
        this.height = i3;
        this.rowSize = i4;
        this.bits = iArr;
    }
}
