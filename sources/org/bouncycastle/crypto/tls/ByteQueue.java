package org.bouncycastle.crypto.tls;

/* loaded from: classes9.dex */
public class ByteQueue {
    private static final int INITBUFSIZE = 1024;
    private byte[] databuf = new byte[1024];
    private int skipped = 0;
    private int available = 0;

    public static final int nextTwoPow(int i2) {
        int i3 = i2 | (i2 >> 1);
        int i4 = i3 | (i3 >> 2);
        int i5 = i4 | (i4 >> 4);
        int i6 = i5 | (i5 >> 8);
        return (i6 | (i6 >> 16)) + 1;
    }

    public void addData(byte[] bArr, int i2, int i3) {
        if (this.skipped + this.available + i3 > this.databuf.length) {
            byte[] bArr2 = new byte[nextTwoPow(bArr.length)];
            System.arraycopy(this.databuf, this.skipped, bArr2, 0, this.available);
            this.skipped = 0;
            this.databuf = bArr2;
        }
        System.arraycopy(bArr, i2, this.databuf, this.skipped + this.available, i3);
        this.available += i3;
    }

    public void read(byte[] bArr, int i2, int i3, int i4) {
        if (this.available - i4 < i3) {
            throw new TlsRuntimeException("Not enough data to read");
        }
        if (bArr.length - i2 >= i3) {
            System.arraycopy(this.databuf, this.skipped + i4, bArr, i2, i3);
            return;
        }
        throw new TlsRuntimeException("Buffer size of " + bArr.length + " is too small for a read of " + i3 + " bytes");
    }

    public void removeData(int i2) {
        int i3 = this.available;
        if (i2 > i3) {
            throw new TlsRuntimeException("Cannot remove " + i2 + " bytes, only got " + this.available);
        }
        int i4 = i3 - i2;
        this.available = i4;
        int i5 = this.skipped + i2;
        this.skipped = i5;
        byte[] bArr = this.databuf;
        if (i5 > bArr.length / 2) {
            System.arraycopy(bArr, i5, bArr, 0, i4);
            this.skipped = 0;
        }
    }

    public int size() {
        return this.available;
    }
}
