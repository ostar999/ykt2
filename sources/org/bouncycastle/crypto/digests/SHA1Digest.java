package org.bouncycastle.crypto.digests;

import com.google.common.base.Ascii;
import org.bouncycastle.crypto.util.Pack;

/* loaded from: classes9.dex */
public class SHA1Digest extends GeneralDigest {
    private static final int DIGEST_LENGTH = 20;
    private static final int Y1 = 1518500249;
    private static final int Y2 = 1859775393;
    private static final int Y3 = -1894007588;
    private static final int Y4 = -899497514;
    private int H1;
    private int H2;
    private int H3;
    private int H4;
    private int H5;
    private int[] X;
    private int xOff;

    public SHA1Digest() {
        this.X = new int[80];
        reset();
    }

    public SHA1Digest(SHA1Digest sHA1Digest) {
        super(sHA1Digest);
        int[] iArr = new int[80];
        this.X = iArr;
        this.H1 = sHA1Digest.H1;
        this.H2 = sHA1Digest.H2;
        this.H3 = sHA1Digest.H3;
        this.H4 = sHA1Digest.H4;
        this.H5 = sHA1Digest.H5;
        int[] iArr2 = sHA1Digest.X;
        System.arraycopy(iArr2, 0, iArr, 0, iArr2.length);
        this.xOff = sHA1Digest.xOff;
    }

    private int f(int i2, int i3, int i4) {
        return ((~i2) & i4) | (i3 & i2);
    }

    private int g(int i2, int i3, int i4) {
        return (i2 & i4) | (i2 & i3) | (i3 & i4);
    }

    private int h(int i2, int i3, int i4) {
        return (i2 ^ i3) ^ i4;
    }

    @Override // org.bouncycastle.crypto.Digest
    public int doFinal(byte[] bArr, int i2) {
        finish();
        Pack.intToBigEndian(this.H1, bArr, i2);
        Pack.intToBigEndian(this.H2, bArr, i2 + 4);
        Pack.intToBigEndian(this.H3, bArr, i2 + 8);
        Pack.intToBigEndian(this.H4, bArr, i2 + 12);
        Pack.intToBigEndian(this.H5, bArr, i2 + 16);
        reset();
        return 20;
    }

    @Override // org.bouncycastle.crypto.Digest
    public String getAlgorithmName() {
        return "SHA-1";
    }

    @Override // org.bouncycastle.crypto.Digest
    public int getDigestSize() {
        return 20;
    }

    @Override // org.bouncycastle.crypto.digests.GeneralDigest
    public void processBlock() {
        for (int i2 = 16; i2 < 80; i2++) {
            int[] iArr = this.X;
            int i3 = ((iArr[i2 - 3] ^ iArr[i2 - 8]) ^ iArr[i2 - 14]) ^ iArr[i2 - 16];
            iArr[i2] = (i3 >>> 31) | (i3 << 1);
        }
        int iH = this.H1;
        int iH2 = this.H2;
        int i4 = this.H3;
        int i5 = this.H4;
        int i6 = this.H5;
        int i7 = 0;
        int i8 = 0;
        while (i7 < 4) {
            int i9 = i8 + 1;
            int iF = i6 + ((iH << 5) | (iH >>> 27)) + f(iH2, i4, i5) + this.X[i8] + Y1;
            int i10 = (iH2 >>> 2) | (iH2 << 30);
            int i11 = i9 + 1;
            int iF2 = i5 + ((iF << 5) | (iF >>> 27)) + f(iH, i10, i4) + this.X[i9] + Y1;
            int i12 = (iH >>> 2) | (iH << 30);
            int i13 = i11 + 1;
            int iF3 = i4 + ((iF2 << 5) | (iF2 >>> 27)) + f(iF, i12, i10) + this.X[i11] + Y1;
            i6 = (iF >>> 2) | (iF << 30);
            int i14 = i13 + 1;
            iH2 = i10 + ((iF3 << 5) | (iF3 >>> 27)) + f(iF2, i6, i12) + this.X[i13] + Y1;
            i5 = (iF2 >>> 2) | (iF2 << 30);
            iH = i12 + ((iH2 << 5) | (iH2 >>> 27)) + f(iF3, i5, i6) + this.X[i14] + Y1;
            i4 = (iF3 >>> 2) | (iF3 << 30);
            i7++;
            i8 = i14 + 1;
        }
        int i15 = 0;
        while (i15 < 4) {
            int i16 = i8 + 1;
            int iH3 = i6 + ((iH << 5) | (iH >>> 27)) + h(iH2, i4, i5) + this.X[i8] + Y2;
            int i17 = (iH2 >>> 2) | (iH2 << 30);
            int i18 = i16 + 1;
            int iH4 = i5 + ((iH3 << 5) | (iH3 >>> 27)) + h(iH, i17, i4) + this.X[i16] + Y2;
            int i19 = (iH >>> 2) | (iH << 30);
            int i20 = i18 + 1;
            int iH5 = i4 + ((iH4 << 5) | (iH4 >>> 27)) + h(iH3, i19, i17) + this.X[i18] + Y2;
            i6 = (iH3 >>> 2) | (iH3 << 30);
            int i21 = i20 + 1;
            iH2 = i17 + ((iH5 << 5) | (iH5 >>> 27)) + h(iH4, i6, i19) + this.X[i20] + Y2;
            i5 = (iH4 >>> 2) | (iH4 << 30);
            iH = i19 + ((iH2 << 5) | (iH2 >>> 27)) + h(iH5, i5, i6) + this.X[i21] + Y2;
            i4 = (iH5 >>> 2) | (iH5 << 30);
            i15++;
            i8 = i21 + 1;
        }
        int i22 = 0;
        while (i22 < 4) {
            int i23 = i8 + 1;
            int iG = i6 + ((iH << 5) | (iH >>> 27)) + g(iH2, i4, i5) + this.X[i8] + Y3;
            int i24 = (iH2 >>> 2) | (iH2 << 30);
            int i25 = i23 + 1;
            int iG2 = i5 + ((iG << 5) | (iG >>> 27)) + g(iH, i24, i4) + this.X[i23] + Y3;
            int i26 = (iH >>> 2) | (iH << 30);
            int i27 = i25 + 1;
            int iG3 = i4 + ((iG2 << 5) | (iG2 >>> 27)) + g(iG, i26, i24) + this.X[i25] + Y3;
            i6 = (iG >>> 2) | (iG << 30);
            int i28 = i27 + 1;
            iH2 = i24 + ((iG3 << 5) | (iG3 >>> 27)) + g(iG2, i6, i26) + this.X[i27] + Y3;
            i5 = (iG2 >>> 2) | (iG2 << 30);
            iH = i26 + ((iH2 << 5) | (iH2 >>> 27)) + g(iG3, i5, i6) + this.X[i28] + Y3;
            i4 = (iG3 >>> 2) | (iG3 << 30);
            i22++;
            i8 = i28 + 1;
        }
        int i29 = 0;
        while (i29 <= 3) {
            int i30 = i8 + 1;
            int iH6 = i6 + ((iH << 5) | (iH >>> 27)) + h(iH2, i4, i5) + this.X[i8] + Y4;
            int i31 = (iH2 >>> 2) | (iH2 << 30);
            int i32 = i30 + 1;
            int iH7 = i5 + ((iH6 << 5) | (iH6 >>> 27)) + h(iH, i31, i4) + this.X[i30] + Y4;
            int i33 = (iH >>> 2) | (iH << 30);
            int i34 = i32 + 1;
            int iH8 = i4 + ((iH7 << 5) | (iH7 >>> 27)) + h(iH6, i33, i31) + this.X[i32] + Y4;
            i6 = (iH6 >>> 2) | (iH6 << 30);
            int i35 = i34 + 1;
            iH2 = i31 + ((iH8 << 5) | (iH8 >>> 27)) + h(iH7, i6, i33) + this.X[i34] + Y4;
            i5 = (iH7 >>> 2) | (iH7 << 30);
            iH = i33 + ((iH2 << 5) | (iH2 >>> 27)) + h(iH8, i5, i6) + this.X[i35] + Y4;
            i4 = (iH8 >>> 2) | (iH8 << 30);
            i29++;
            i8 = i35 + 1;
        }
        this.H1 += iH;
        this.H2 += iH2;
        this.H3 += i4;
        this.H4 += i5;
        this.H5 += i6;
        this.xOff = 0;
        for (int i36 = 0; i36 < 16; i36++) {
            this.X[i36] = 0;
        }
    }

    @Override // org.bouncycastle.crypto.digests.GeneralDigest
    public void processLength(long j2) {
        if (this.xOff > 14) {
            processBlock();
        }
        int[] iArr = this.X;
        iArr[14] = (int) (j2 >>> 32);
        iArr[15] = (int) (j2 & (-1));
    }

    @Override // org.bouncycastle.crypto.digests.GeneralDigest
    public void processWord(byte[] bArr, int i2) {
        int i3 = bArr[i2] << Ascii.CAN;
        int i4 = i2 + 1;
        int i5 = i3 | ((bArr[i4] & 255) << 16);
        int i6 = i4 + 1;
        int i7 = (bArr[i6 + 1] & 255) | i5 | ((bArr[i6] & 255) << 8);
        int[] iArr = this.X;
        int i8 = this.xOff;
        iArr[i8] = i7;
        int i9 = i8 + 1;
        this.xOff = i9;
        if (i9 == 16) {
            processBlock();
        }
    }

    @Override // org.bouncycastle.crypto.digests.GeneralDigest, org.bouncycastle.crypto.Digest
    public void reset() {
        super.reset();
        this.H1 = 1732584193;
        this.H2 = -271733879;
        this.H3 = -1732584194;
        this.H4 = 271733878;
        this.H5 = -1009589776;
        this.xOff = 0;
        int i2 = 0;
        while (true) {
            int[] iArr = this.X;
            if (i2 == iArr.length) {
                return;
            }
            iArr[i2] = 0;
            i2++;
        }
    }
}
