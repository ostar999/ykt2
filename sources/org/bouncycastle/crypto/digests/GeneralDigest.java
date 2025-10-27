package org.bouncycastle.crypto.digests;

import org.bouncycastle.crypto.ExtendedDigest;

/* loaded from: classes9.dex */
public abstract class GeneralDigest implements ExtendedDigest {
    private static final int BYTE_LENGTH = 64;
    private long byteCount;
    private byte[] xBuf;
    private int xBufOff;

    public GeneralDigest() {
        this.xBuf = new byte[4];
        this.xBufOff = 0;
    }

    public GeneralDigest(GeneralDigest generalDigest) {
        byte[] bArr = new byte[generalDigest.xBuf.length];
        this.xBuf = bArr;
        byte[] bArr2 = generalDigest.xBuf;
        System.arraycopy(bArr2, 0, bArr, 0, bArr2.length);
        this.xBufOff = generalDigest.xBufOff;
        this.byteCount = generalDigest.byteCount;
    }

    public void finish() {
        long j2 = this.byteCount << 3;
        byte b3 = -128;
        while (true) {
            update(b3);
            if (this.xBufOff == 0) {
                processLength(j2);
                processBlock();
                return;
            }
            b3 = 0;
        }
    }

    @Override // org.bouncycastle.crypto.ExtendedDigest
    public int getByteLength() {
        return 64;
    }

    public abstract void processBlock();

    public abstract void processLength(long j2);

    public abstract void processWord(byte[] bArr, int i2);

    @Override // org.bouncycastle.crypto.Digest
    public void reset() {
        this.byteCount = 0L;
        this.xBufOff = 0;
        int i2 = 0;
        while (true) {
            byte[] bArr = this.xBuf;
            if (i2 >= bArr.length) {
                return;
            }
            bArr[i2] = 0;
            i2++;
        }
    }

    @Override // org.bouncycastle.crypto.Digest
    public void update(byte b3) {
        byte[] bArr = this.xBuf;
        int i2 = this.xBufOff;
        int i3 = i2 + 1;
        this.xBufOff = i3;
        bArr[i2] = b3;
        if (i3 == bArr.length) {
            processWord(bArr, 0);
            this.xBufOff = 0;
        }
        this.byteCount++;
    }

    @Override // org.bouncycastle.crypto.Digest
    public void update(byte[] bArr, int i2, int i3) {
        while (this.xBufOff != 0 && i3 > 0) {
            update(bArr[i2]);
            i2++;
            i3--;
        }
        while (i3 > this.xBuf.length) {
            processWord(bArr, i2);
            byte[] bArr2 = this.xBuf;
            i2 += bArr2.length;
            i3 -= bArr2.length;
            this.byteCount += bArr2.length;
        }
        while (i3 > 0) {
            update(bArr[i2]);
            i2++;
            i3--;
        }
    }
}
