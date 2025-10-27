package com.tencent.rtmp.sharp.jni;

/* loaded from: classes6.dex */
public class RingBuffer {
    private final int DEFAULT_SIZE;
    public int c_totalSize;
    public boolean m_isEmpty;
    public byte[] m_pBuf;
    public int m_read;
    public int m_write;

    public RingBuffer() {
        this.DEFAULT_SIZE = 1000;
        this.c_totalSize = 1000;
        this.m_isEmpty = true;
        this.m_read = 0;
        this.m_write = 0;
        this.m_pBuf = new byte[1000];
    }

    public void Clear() {
        this.m_write = 0;
        this.m_read = 0;
        this.m_isEmpty = true;
    }

    public boolean Pop(byte[] bArr, int i2) {
        if (this.m_pBuf == null || RemainRead() < i2 || i2 <= 0) {
            return false;
        }
        int i3 = this.c_totalSize;
        int i4 = this.m_read;
        if (i3 - i4 >= i2) {
            System.arraycopy(this.m_pBuf, i4, bArr, 0, i2);
        } else {
            System.arraycopy(this.m_pBuf, i4, bArr, 0, i3 - i4);
            byte[] bArr2 = this.m_pBuf;
            int i5 = this.c_totalSize;
            int i6 = this.m_read;
            System.arraycopy(bArr2, 0, bArr, i5 - i6, i2 - (i5 - i6));
        }
        int i7 = (this.m_read + i2) % this.c_totalSize;
        this.m_read = i7;
        this.m_isEmpty = i7 == this.m_write;
        return true;
    }

    public void Push(byte[] bArr, int i2) {
        if (this.m_pBuf != null && RemainWrite() >= i2) {
            int i3 = this.c_totalSize;
            int i4 = this.m_write;
            if (i3 - i4 >= i2) {
                System.arraycopy(bArr, 0, this.m_pBuf, i4, i2);
            } else {
                System.arraycopy(bArr, 0, this.m_pBuf, i4, i3 - i4);
                int i5 = this.c_totalSize;
                int i6 = this.m_write;
                System.arraycopy(bArr, i5 - i6, this.m_pBuf, 0, i2 - (i5 - i6));
            }
            this.m_write = (this.m_write + i2) % this.c_totalSize;
            this.m_isEmpty = false;
        }
    }

    public int RemainRead() {
        int i2 = this.m_write;
        int i3 = this.m_read;
        if (i2 < i3) {
            return (this.c_totalSize - i3) + i2;
        }
        if (i2 > i3) {
            return i2 - i3;
        }
        if (this.m_isEmpty) {
            return 0;
        }
        return this.c_totalSize;
    }

    public int RemainWrite() {
        return this.c_totalSize - RemainRead();
    }

    public RingBuffer(int i2) {
        this.DEFAULT_SIZE = 1000;
        this.c_totalSize = i2;
        this.m_isEmpty = true;
        this.m_read = 0;
        this.m_write = 0;
        this.m_pBuf = new byte[i2];
    }
}
