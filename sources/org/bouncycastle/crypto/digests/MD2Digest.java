package org.bouncycastle.crypto.digests;

import com.google.common.base.Ascii;
import com.google.common.primitives.SignedBytes;
import kotlin.io.encoding.Base64;
import okio.Utf8;
import org.apache.commons.compress.archivers.tar.TarConstants;
import org.bouncycastle.crypto.ExtendedDigest;
import org.bouncycastle.crypto.signers.PSSSigner;
import org.eclipse.jetty.http.HttpTokens;

/* loaded from: classes9.dex */
public class MD2Digest implements ExtendedDigest {
    private static final int DIGEST_LENGTH = 16;
    private static final byte[] S = {41, 46, 67, -55, -94, -40, 124, 1, Base64.padSymbol, TarConstants.LF_FIFO, 84, -95, -20, -16, 6, 19, 98, -89, 5, -13, -64, -57, 115, -116, -104, -109, 43, -39, PSSSigner.TRAILER_IMPLICIT, TarConstants.LF_GNUTYPE_LONGNAME, -126, -54, Ascii.RS, -101, 87, 60, -3, -44, -32, 22, TarConstants.LF_PAX_GLOBAL_EXTENDED_HEADER, 66, 111, Ascii.CAN, -118, 23, -27, Ascii.DC2, -66, 78, -60, -42, -38, -98, -34, 73, -96, -5, -11, -114, -69, 47, -18, 122, -87, 104, 121, -111, 21, -78, 7, Utf8.REPLACEMENT_BYTE, -108, -62, 16, -119, 11, 34, 95, 33, -128, 127, 93, -102, 90, -112, TarConstants.LF_SYMLINK, 39, TarConstants.LF_DIR, 62, -52, -25, -65, -9, -105, 3, -1, Ascii.EM, TarConstants.LF_NORMAL, -77, 72, -91, -75, -47, -41, 94, -110, 42, -84, 86, -86, -58, 79, -72, 56, -46, -106, -92, 125, -74, 118, -4, 107, -30, -100, 116, 4, -15, 69, -99, 112, 89, 100, 113, -121, 32, -122, 91, -49, 101, -26, 45, -88, 2, Ascii.ESC, 96, 37, -83, -82, -80, -71, -10, Ascii.FS, 70, 97, 105, TarConstants.LF_BLK, SignedBytes.MAX_POWER_OF_TWO, 126, 15, 85, 71, -93, 35, -35, 81, -81, HttpTokens.COLON, -61, 92, -7, -50, -70, -59, -22, 38, 44, TarConstants.LF_GNUTYPE_SPARSE, 13, 110, -123, 40, -124, 9, -45, -33, -51, -12, 65, -127, 77, 82, 106, -36, TarConstants.LF_CONTIG, -56, 108, -63, -85, -6, 36, -31, 123, 8, 12, -67, -79, 74, TarConstants.LF_PAX_EXTENDED_HEADER_LC, -120, -107, -117, -29, 99, -24, 109, -23, -53, -43, -2, HttpTokens.SEMI_COLON, 0, Ascii.GS, 57, -14, -17, -73, 14, 102, TarConstants.LF_PAX_EXTENDED_HEADER_UC, -48, -28, -90, 119, 114, -8, -21, 117, TarConstants.LF_GNUTYPE_LONGLINK, 10, TarConstants.LF_LINK, 68, 80, -76, -113, -19, Ascii.US, Ascii.SUB, -37, -103, -115, TarConstants.LF_CHR, -97, 17, -125, Ascii.DC4};
    private byte[] C;
    private int COff;
    private byte[] M;
    private byte[] X;
    private int mOff;
    private int xOff;

    public MD2Digest() {
        this.X = new byte[48];
        this.M = new byte[16];
        this.C = new byte[16];
        reset();
    }

    public MD2Digest(MD2Digest mD2Digest) {
        byte[] bArr = new byte[48];
        this.X = bArr;
        this.M = new byte[16];
        this.C = new byte[16];
        byte[] bArr2 = mD2Digest.X;
        System.arraycopy(bArr2, 0, bArr, 0, bArr2.length);
        this.xOff = mD2Digest.xOff;
        byte[] bArr3 = mD2Digest.M;
        System.arraycopy(bArr3, 0, this.M, 0, bArr3.length);
        this.mOff = mD2Digest.mOff;
        byte[] bArr4 = mD2Digest.C;
        System.arraycopy(bArr4, 0, this.C, 0, bArr4.length);
        this.COff = mD2Digest.COff;
    }

    @Override // org.bouncycastle.crypto.Digest
    public int doFinal(byte[] bArr, int i2) {
        int length = this.M.length;
        int i3 = this.mOff;
        byte b3 = (byte) (length - i3);
        while (true) {
            byte[] bArr2 = this.M;
            if (i3 >= bArr2.length) {
                processCheckSum(bArr2);
                processBlock(this.M);
                processBlock(this.C);
                System.arraycopy(this.X, this.xOff, bArr, i2, 16);
                reset();
                return 16;
            }
            bArr2[i3] = b3;
            i3++;
        }
    }

    @Override // org.bouncycastle.crypto.Digest
    public String getAlgorithmName() {
        return "MD2";
    }

    @Override // org.bouncycastle.crypto.ExtendedDigest
    public int getByteLength() {
        return 16;
    }

    @Override // org.bouncycastle.crypto.Digest
    public int getDigestSize() {
        return 16;
    }

    public void processBlock(byte[] bArr) {
        for (int i2 = 0; i2 < 16; i2++) {
            byte[] bArr2 = this.X;
            bArr2[i2 + 16] = bArr[i2];
            bArr2[i2 + 32] = (byte) (bArr[i2] ^ bArr2[i2]);
        }
        int i3 = 0;
        for (int i4 = 0; i4 < 18; i4++) {
            for (int i5 = 0; i5 < 48; i5++) {
                byte[] bArr3 = this.X;
                byte b3 = (byte) (S[i3] ^ bArr3[i5]);
                bArr3[i5] = b3;
                i3 = b3 & 255;
            }
            i3 = (i3 + i4) % 256;
        }
    }

    public void processCheckSum(byte[] bArr) {
        byte b3 = this.C[15];
        for (int i2 = 0; i2 < 16; i2++) {
            byte[] bArr2 = this.C;
            b3 = (byte) (S[(b3 ^ bArr[i2]) & 255] ^ bArr2[i2]);
            bArr2[i2] = b3;
        }
    }

    @Override // org.bouncycastle.crypto.Digest
    public void reset() {
        this.xOff = 0;
        int i2 = 0;
        while (true) {
            byte[] bArr = this.X;
            if (i2 == bArr.length) {
                break;
            }
            bArr[i2] = 0;
            i2++;
        }
        this.mOff = 0;
        int i3 = 0;
        while (true) {
            byte[] bArr2 = this.M;
            if (i3 == bArr2.length) {
                break;
            }
            bArr2[i3] = 0;
            i3++;
        }
        this.COff = 0;
        int i4 = 0;
        while (true) {
            byte[] bArr3 = this.C;
            if (i4 == bArr3.length) {
                return;
            }
            bArr3[i4] = 0;
            i4++;
        }
    }

    @Override // org.bouncycastle.crypto.Digest
    public void update(byte b3) {
        byte[] bArr = this.M;
        int i2 = this.mOff;
        int i3 = i2 + 1;
        this.mOff = i3;
        bArr[i2] = b3;
        if (i3 == 16) {
            processCheckSum(bArr);
            processBlock(this.M);
            this.mOff = 0;
        }
    }

    @Override // org.bouncycastle.crypto.Digest
    public void update(byte[] bArr, int i2, int i3) {
        while (this.mOff != 0 && i3 > 0) {
            update(bArr[i2]);
            i2++;
            i3--;
        }
        while (i3 > 16) {
            System.arraycopy(bArr, i2, this.M, 0, 16);
            processCheckSum(this.M);
            processBlock(this.M);
            i3 -= 16;
            i2 += 16;
        }
        while (i3 > 0) {
            update(bArr[i2]);
            i2++;
            i3--;
        }
    }
}
