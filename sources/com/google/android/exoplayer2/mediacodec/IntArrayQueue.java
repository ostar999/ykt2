package com.google.android.exoplayer2.mediacodec;

import java.util.NoSuchElementException;

/* loaded from: classes3.dex */
final class IntArrayQueue {
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private int wrapAroundMask;
    private int headIndex = 0;
    private int tailIndex = -1;
    private int size = 0;
    private int[] data = new int[16];

    public IntArrayQueue() {
        this.wrapAroundMask = r0.length - 1;
    }

    private void doubleArraySize() {
        int[] iArr = this.data;
        int length = iArr.length << 1;
        if (length < 0) {
            throw new IllegalStateException();
        }
        int[] iArr2 = new int[length];
        int length2 = iArr.length;
        int i2 = this.headIndex;
        int i3 = length2 - i2;
        System.arraycopy(iArr, i2, iArr2, 0, i3);
        System.arraycopy(this.data, 0, iArr2, i3, i2);
        this.headIndex = 0;
        this.tailIndex = this.size - 1;
        this.data = iArr2;
        this.wrapAroundMask = iArr2.length - 1;
    }

    public void add(int i2) {
        if (this.size == this.data.length) {
            doubleArraySize();
        }
        int i3 = (this.tailIndex + 1) & this.wrapAroundMask;
        this.tailIndex = i3;
        this.data[i3] = i2;
        this.size++;
    }

    public int capacity() {
        return this.data.length;
    }

    public void clear() {
        this.headIndex = 0;
        this.tailIndex = -1;
        this.size = 0;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int remove() {
        int i2 = this.size;
        if (i2 == 0) {
            throw new NoSuchElementException();
        }
        int[] iArr = this.data;
        int i3 = this.headIndex;
        int i4 = iArr[i3];
        this.headIndex = (i3 + 1) & this.wrapAroundMask;
        this.size = i2 - 1;
        return i4;
    }

    public int size() {
        return this.size;
    }
}
