package org.bouncycastle.crypto.digests;

import com.google.common.base.Ascii;
import org.bouncycastle.crypto.util.Pack;

/* loaded from: classes9.dex */
public class SHA256Digest extends GeneralDigest {
    private static final int DIGEST_LENGTH = 32;
    static final int[] K = {1116352408, 1899447441, -1245643825, -373957723, 961987163, 1508970993, -1841331548, -1424204075, -670586216, 310598401, 607225278, 1426881987, 1925078388, -2132889090, -1680079193, -1046744716, -459576895, -272742522, 264347078, 604807628, 770255983, 1249150122, 1555081692, 1996064986, -1740746414, -1473132947, -1341970488, -1084653625, -958395405, -710438585, 113926993, 338241895, 666307205, 773529912, 1294757372, 1396182291, 1695183700, 1986661051, -2117940946, -1838011259, -1564481375, -1474664885, -1035236496, -949202525, -778901479, -694614492, -200395387, 275423344, 430227734, 506948616, 659060556, 883997877, 958139571, 1322822218, 1537002063, 1747873779, 1955562222, 2024104815, -2067236844, -1933114872, -1866530822, -1538233109, -1090935817, -965641998};
    private int H1;
    private int H2;
    private int H3;
    private int H4;
    private int H5;
    private int H6;
    private int H7;
    private int H8;
    private int[] X;
    private int xOff;

    public SHA256Digest() {
        this.X = new int[64];
        reset();
    }

    public SHA256Digest(SHA256Digest sHA256Digest) {
        super(sHA256Digest);
        int[] iArr = new int[64];
        this.X = iArr;
        this.H1 = sHA256Digest.H1;
        this.H2 = sHA256Digest.H2;
        this.H3 = sHA256Digest.H3;
        this.H4 = sHA256Digest.H4;
        this.H5 = sHA256Digest.H5;
        this.H6 = sHA256Digest.H6;
        this.H7 = sHA256Digest.H7;
        this.H8 = sHA256Digest.H8;
        int[] iArr2 = sHA256Digest.X;
        System.arraycopy(iArr2, 0, iArr, 0, iArr2.length);
        this.xOff = sHA256Digest.xOff;
    }

    private int Ch(int i2, int i3, int i4) {
        return ((~i2) & i4) ^ (i3 & i2);
    }

    private int Maj(int i2, int i3, int i4) {
        return ((i2 & i4) ^ (i2 & i3)) ^ (i3 & i4);
    }

    private int Sum0(int i2) {
        return ((i2 << 10) | (i2 >>> 22)) ^ (((i2 >>> 2) | (i2 << 30)) ^ ((i2 >>> 13) | (i2 << 19)));
    }

    private int Sum1(int i2) {
        return ((i2 << 7) | (i2 >>> 25)) ^ (((i2 >>> 6) | (i2 << 26)) ^ ((i2 >>> 11) | (i2 << 21)));
    }

    private int Theta0(int i2) {
        return (i2 >>> 3) ^ (((i2 >>> 7) | (i2 << 25)) ^ ((i2 >>> 18) | (i2 << 14)));
    }

    private int Theta1(int i2) {
        return (i2 >>> 10) ^ (((i2 >>> 17) | (i2 << 15)) ^ ((i2 >>> 19) | (i2 << 13)));
    }

    @Override // org.bouncycastle.crypto.Digest
    public int doFinal(byte[] bArr, int i2) {
        finish();
        Pack.intToBigEndian(this.H1, bArr, i2);
        Pack.intToBigEndian(this.H2, bArr, i2 + 4);
        Pack.intToBigEndian(this.H3, bArr, i2 + 8);
        Pack.intToBigEndian(this.H4, bArr, i2 + 12);
        Pack.intToBigEndian(this.H5, bArr, i2 + 16);
        Pack.intToBigEndian(this.H6, bArr, i2 + 20);
        Pack.intToBigEndian(this.H7, bArr, i2 + 24);
        Pack.intToBigEndian(this.H8, bArr, i2 + 28);
        reset();
        return 32;
    }

    @Override // org.bouncycastle.crypto.Digest
    public String getAlgorithmName() {
        return "SHA-256";
    }

    @Override // org.bouncycastle.crypto.Digest
    public int getDigestSize() {
        return 32;
    }

    @Override // org.bouncycastle.crypto.digests.GeneralDigest
    public void processBlock() {
        for (int i2 = 16; i2 <= 63; i2++) {
            int[] iArr = this.X;
            int iTheta1 = Theta1(iArr[i2 - 2]);
            int[] iArr2 = this.X;
            iArr[i2] = iTheta1 + iArr2[i2 - 7] + Theta0(iArr2[i2 - 15]) + this.X[i2 - 16];
        }
        int iSum0 = this.H1;
        int iSum02 = this.H2;
        int iSum03 = this.H3;
        int iSum04 = this.H4;
        int i3 = this.H5;
        int i4 = this.H6;
        int i5 = this.H7;
        int i6 = this.H8;
        int i7 = 0;
        for (int i8 = 0; i8 < 8; i8++) {
            int iSum1 = Sum1(i3) + Ch(i3, i4, i5);
            int[] iArr3 = K;
            int i9 = i6 + iSum1 + iArr3[i7] + this.X[i7];
            int i10 = iSum04 + i9;
            int iSum05 = i9 + Sum0(iSum0) + Maj(iSum0, iSum02, iSum03);
            int i11 = i7 + 1;
            int iSum12 = i5 + Sum1(i10) + Ch(i10, i3, i4) + iArr3[i11] + this.X[i11];
            int i12 = iSum03 + iSum12;
            int iSum06 = iSum12 + Sum0(iSum05) + Maj(iSum05, iSum0, iSum02);
            int i13 = i11 + 1;
            int iSum13 = i4 + Sum1(i12) + Ch(i12, i10, i3) + iArr3[i13] + this.X[i13];
            int i14 = iSum02 + iSum13;
            int iSum07 = iSum13 + Sum0(iSum06) + Maj(iSum06, iSum05, iSum0);
            int i15 = i13 + 1;
            int iSum14 = i3 + Sum1(i14) + Ch(i14, i12, i10) + iArr3[i15] + this.X[i15];
            int i16 = iSum0 + iSum14;
            int iSum08 = iSum14 + Sum0(iSum07) + Maj(iSum07, iSum06, iSum05);
            int i17 = i15 + 1;
            int iSum15 = i10 + Sum1(i16) + Ch(i16, i14, i12) + iArr3[i17] + this.X[i17];
            i6 = iSum05 + iSum15;
            iSum04 = iSum15 + Sum0(iSum08) + Maj(iSum08, iSum07, iSum06);
            int i18 = i17 + 1;
            int iSum16 = i12 + Sum1(i6) + Ch(i6, i16, i14) + iArr3[i18] + this.X[i18];
            i5 = iSum06 + iSum16;
            iSum03 = iSum16 + Sum0(iSum04) + Maj(iSum04, iSum08, iSum07);
            int i19 = i18 + 1;
            int iSum17 = i14 + Sum1(i5) + Ch(i5, i6, i16) + iArr3[i19] + this.X[i19];
            i4 = iSum07 + iSum17;
            iSum02 = iSum17 + Sum0(iSum03) + Maj(iSum03, iSum04, iSum08);
            int i20 = i19 + 1;
            int iSum18 = i16 + Sum1(i4) + Ch(i4, i5, i6) + iArr3[i20] + this.X[i20];
            i3 = iSum08 + iSum18;
            iSum0 = iSum18 + Sum0(iSum02) + Maj(iSum02, iSum03, iSum04);
            i7 = i20 + 1;
        }
        this.H1 += iSum0;
        this.H2 += iSum02;
        this.H3 += iSum03;
        this.H4 += iSum04;
        this.H5 += i3;
        this.H6 += i4;
        this.H7 += i5;
        this.H8 += i6;
        this.xOff = 0;
        for (int i21 = 0; i21 < 16; i21++) {
            this.X[i21] = 0;
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
        this.H1 = 1779033703;
        this.H2 = -1150833019;
        this.H3 = 1013904242;
        this.H4 = -1521486534;
        this.H5 = 1359893119;
        this.H6 = -1694144372;
        this.H7 = 528734635;
        this.H8 = 1541459225;
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
