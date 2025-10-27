package org.bouncycastle.crypto.engines;

import com.google.common.base.Ascii;
import com.google.common.primitives.SignedBytes;
import kotlin.io.encoding.Base64;
import okio.Utf8;
import org.apache.commons.compress.archivers.tar.TarConstants;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.signers.PSSSigner;
import org.eclipse.jetty.http.HttpTokens;

/* loaded from: classes9.dex */
public final class TwofishEngine implements BlockCipher {
    private static final int BLOCK_SIZE = 16;
    private static final int GF256_FDBK = 361;
    private static final int GF256_FDBK_2 = 180;
    private static final int GF256_FDBK_4 = 90;
    private static final int INPUT_WHITEN = 0;
    private static final int MAX_KEY_BITS = 256;
    private static final int MAX_ROUNDS = 16;
    private static final int OUTPUT_WHITEN = 4;
    private static final byte[][] P = {new byte[]{-87, TarConstants.LF_PAX_GLOBAL_EXTENDED_HEADER, -77, -24, 4, -3, -93, 118, -102, -110, -128, TarConstants.LF_PAX_EXTENDED_HEADER_LC, -28, -35, -47, 56, 13, -58, TarConstants.LF_DIR, -104, Ascii.CAN, -9, -20, 108, 67, 117, TarConstants.LF_CONTIG, 38, -6, 19, -108, 72, -14, -48, -117, TarConstants.LF_NORMAL, -124, 84, -33, 35, Ascii.EM, 91, Base64.padSymbol, 89, -13, -82, -94, -126, 99, 1, -125, 46, -39, 81, -101, 124, -90, -21, -91, -66, 22, 12, -29, 97, -64, -116, HttpTokens.COLON, -11, 115, 44, 37, 11, -69, 78, -119, 107, TarConstants.LF_GNUTYPE_SPARSE, 106, -76, -15, -31, -26, -67, 69, -30, -12, -74, 102, -52, -107, 3, 86, -44, Ascii.FS, Ascii.RS, -41, -5, -61, -114, -75, -23, -49, -65, -70, -22, 119, 57, -81, TarConstants.LF_CHR, -55, 98, 113, -127, 121, 9, -83, 36, -51, -7, -40, -27, -59, -71, 77, 68, 8, -122, -25, -95, Ascii.GS, -86, -19, 6, 112, -78, -46, 65, 123, -96, 17, TarConstants.LF_LINK, -62, 39, -112, 32, -10, 96, -1, -106, 92, -79, -85, -98, -100, 82, Ascii.ESC, 95, -109, 10, -17, -111, -123, 73, -18, 45, 79, -113, HttpTokens.SEMI_COLON, 71, -121, 109, 70, -42, 62, 105, 100, 42, -50, -53, 47, -4, -105, 5, 122, -84, 127, -43, Ascii.SUB, TarConstants.LF_GNUTYPE_LONGLINK, 14, -89, 90, 40, Ascii.DC4, Utf8.REPLACEMENT_BYTE, 41, -120, 60, TarConstants.LF_GNUTYPE_LONGNAME, 2, -72, -38, -80, 23, 85, Ascii.US, -118, 125, 87, -57, -115, 116, -73, -60, -97, 114, 126, 21, 34, Ascii.DC2, TarConstants.LF_PAX_EXTENDED_HEADER_UC, 7, -103, TarConstants.LF_BLK, 110, 80, -34, 104, 101, PSSSigner.TRAILER_IMPLICIT, -37, -8, -56, -88, 43, SignedBytes.MAX_POWER_OF_TWO, -36, -2, TarConstants.LF_SYMLINK, -92, -54, 16, 33, -16, -45, 93, 15, 0, 111, -99, TarConstants.LF_FIFO, 66, 74, 94, -63, -32}, new byte[]{117, -13, -58, -12, -37, 123, -5, -56, 74, -45, -26, 107, 69, 125, -24, TarConstants.LF_GNUTYPE_LONGLINK, -42, TarConstants.LF_SYMLINK, -40, -3, TarConstants.LF_CONTIG, 113, -15, -31, TarConstants.LF_NORMAL, 15, -8, Ascii.ESC, -121, -6, 6, Utf8.REPLACEMENT_BYTE, 94, -70, -82, 91, -118, 0, PSSSigner.TRAILER_IMPLICIT, -99, 109, -63, -79, 14, -128, 93, -46, -43, -96, -124, 7, Ascii.DC4, -75, -112, 44, -93, -78, 115, TarConstants.LF_GNUTYPE_LONGNAME, 84, -110, 116, TarConstants.LF_FIFO, 81, 56, -80, -67, 90, -4, 96, 98, -106, 108, 66, -9, 16, 124, 40, 39, -116, 19, -107, -100, -57, 36, 70, HttpTokens.SEMI_COLON, 112, -54, -29, -123, -53, 17, -48, -109, -72, -90, -125, 32, -1, -97, 119, -61, -52, 3, 111, 8, -65, SignedBytes.MAX_POWER_OF_TWO, -25, 43, -30, 121, 12, -86, -126, 65, HttpTokens.COLON, -22, -71, -28, -102, -92, -105, 126, -38, 122, 23, 102, -108, -95, Ascii.GS, Base64.padSymbol, -16, -34, -77, 11, 114, -89, Ascii.FS, -17, -47, TarConstants.LF_GNUTYPE_SPARSE, 62, -113, TarConstants.LF_CHR, 38, 95, -20, 118, 42, 73, -127, -120, -18, 33, -60, Ascii.SUB, -21, -39, -59, 57, -103, -51, -83, TarConstants.LF_LINK, -117, 1, Ascii.CAN, 35, -35, Ascii.US, 78, 45, -7, 72, 79, -14, 101, -114, TarConstants.LF_PAX_EXTENDED_HEADER_LC, 92, TarConstants.LF_PAX_EXTENDED_HEADER_UC, Ascii.EM, -115, -27, -104, 87, TarConstants.LF_PAX_GLOBAL_EXTENDED_HEADER, 127, 5, 100, -81, 99, -74, -2, -11, -73, 60, -91, -50, -23, 104, 68, -32, 77, 67, 105, 41, 46, -84, 21, 89, -88, 10, -98, 110, 71, -33, TarConstants.LF_BLK, TarConstants.LF_DIR, 106, -49, -36, 34, -55, -64, -101, -119, -44, -19, -85, Ascii.DC2, -94, 13, 82, -69, 2, 47, -87, -41, 97, Ascii.RS, -76, 80, 4, -10, -62, 22, 37, -122, 86, 85, 9, -66, -111}};
    private static final int P_00 = 1;
    private static final int P_01 = 0;
    private static final int P_02 = 0;
    private static final int P_03 = 1;
    private static final int P_04 = 1;
    private static final int P_10 = 0;
    private static final int P_11 = 0;
    private static final int P_12 = 1;
    private static final int P_13 = 1;
    private static final int P_14 = 0;
    private static final int P_20 = 1;
    private static final int P_21 = 1;
    private static final int P_22 = 0;
    private static final int P_23 = 0;
    private static final int P_24 = 0;
    private static final int P_30 = 0;
    private static final int P_31 = 1;
    private static final int P_32 = 1;
    private static final int P_33 = 0;
    private static final int P_34 = 1;
    private static final int ROUNDS = 16;
    private static final int ROUND_SUBKEYS = 8;
    private static final int RS_GF_FDBK = 333;
    private static final int SK_BUMP = 16843009;
    private static final int SK_ROTL = 9;
    private static final int SK_STEP = 33686018;
    private static final int TOTAL_SUBKEYS = 40;
    private int[] gSBox;
    private int[] gSubKeys;
    private boolean encrypting = false;
    private int[] gMDS0 = new int[256];
    private int[] gMDS1 = new int[256];
    private int[] gMDS2 = new int[256];
    private int[] gMDS3 = new int[256];
    private int k64Cnt = 0;
    private byte[] workingKey = null;

    public TwofishEngine() {
        int[] iArr = new int[2];
        int[] iArr2 = new int[2];
        int[] iArr3 = new int[2];
        for (int i2 = 0; i2 < 256; i2++) {
            byte[][] bArr = P;
            int i3 = bArr[0][i2] & 255;
            iArr[0] = i3;
            iArr2[0] = Mx_X(i3) & 255;
            iArr3[0] = Mx_Y(i3) & 255;
            int i4 = bArr[1][i2] & 255;
            iArr[1] = i4;
            iArr2[1] = Mx_X(i4) & 255;
            int iMx_Y = Mx_Y(i4) & 255;
            iArr3[1] = iMx_Y;
            this.gMDS0[i2] = (iMx_Y << 24) | iArr[1] | (iArr2[1] << 8) | (iMx_Y << 16);
            int[] iArr4 = this.gMDS1;
            int i5 = iArr3[0];
            iArr4[i2] = i5 | (i5 << 8) | (iArr2[0] << 16) | (iArr[0] << 24);
            int[] iArr5 = this.gMDS2;
            int i6 = iArr2[1];
            int i7 = iArr3[1];
            iArr5[i2] = (iArr[1] << 16) | i6 | (i7 << 8) | (i7 << 24);
            int[] iArr6 = this.gMDS3;
            int i8 = iArr2[0];
            iArr6[i2] = (i8 << 24) | (iArr[0] << 8) | i8 | (iArr3[0] << 16);
        }
    }

    private void Bits32ToBytes(int i2, byte[] bArr, int i3) {
        bArr[i3] = (byte) i2;
        bArr[i3 + 1] = (byte) (i2 >> 8);
        bArr[i3 + 2] = (byte) (i2 >> 16);
        bArr[i3 + 3] = (byte) (i2 >> 24);
    }

    private int BytesTo32Bits(byte[] bArr, int i2) {
        return ((bArr[i2 + 3] & 255) << 24) | (bArr[i2] & 255) | ((bArr[i2 + 1] & 255) << 8) | ((bArr[i2 + 2] & 255) << 16);
    }

    private int F32(int i2, int[] iArr) {
        int i3;
        int i4;
        int iB0 = b0(i2);
        int iB1 = b1(i2);
        int iB2 = b2(i2);
        int iB3 = b3(i2);
        int i5 = iArr[0];
        int i6 = iArr[1];
        int i7 = iArr[2];
        int i8 = iArr[3];
        int i9 = this.k64Cnt & 3;
        if (i9 != 0) {
            if (i9 == 1) {
                int[] iArr2 = this.gMDS0;
                byte[][] bArr = P;
                i3 = (iArr2[(bArr[0][iB0] & 255) ^ b0(i5)] ^ this.gMDS1[(bArr[0][iB1] & 255) ^ b1(i5)]) ^ this.gMDS2[(bArr[1][iB2] & 255) ^ b2(i5)];
                i4 = this.gMDS3[(bArr[1][iB3] & 255) ^ b3(i5)];
                return i3 ^ i4;
            }
            if (i9 != 2) {
                if (i9 != 3) {
                    return 0;
                }
            }
            int[] iArr3 = this.gMDS0;
            byte[][] bArr2 = P;
            byte[] bArr3 = bArr2[0];
            i3 = (iArr3[(bArr3[(bArr3[iB0] & 255) ^ b0(i6)] & 255) ^ b0(i5)] ^ this.gMDS1[(bArr2[0][(bArr2[1][iB1] & 255) ^ b1(i6)] & 255) ^ b1(i5)]) ^ this.gMDS2[(bArr2[1][(bArr2[0][iB2] & 255) ^ b2(i6)] & 255) ^ b2(i5)];
            int[] iArr4 = this.gMDS3;
            byte[] bArr4 = bArr2[1];
            i4 = iArr4[(bArr4[(bArr4[iB3] & 255) ^ b3(i6)] & 255) ^ b3(i5)];
            return i3 ^ i4;
        }
        byte[][] bArr5 = P;
        iB0 = (bArr5[1][iB0] & 255) ^ b0(i8);
        iB1 = (bArr5[0][iB1] & 255) ^ b1(i8);
        iB2 = (bArr5[0][iB2] & 255) ^ b2(i8);
        iB3 = (bArr5[1][iB3] & 255) ^ b3(i8);
        byte[][] bArr6 = P;
        iB0 = (bArr6[1][iB0] & 255) ^ b0(i7);
        iB1 = (bArr6[1][iB1] & 255) ^ b1(i7);
        iB2 = (bArr6[0][iB2] & 255) ^ b2(i7);
        iB3 = (bArr6[0][iB3] & 255) ^ b3(i7);
        int[] iArr32 = this.gMDS0;
        byte[][] bArr22 = P;
        byte[] bArr32 = bArr22[0];
        i3 = (iArr32[(bArr32[(bArr32[iB0] & 255) ^ b0(i6)] & 255) ^ b0(i5)] ^ this.gMDS1[(bArr22[0][(bArr22[1][iB1] & 255) ^ b1(i6)] & 255) ^ b1(i5)]) ^ this.gMDS2[(bArr22[1][(bArr22[0][iB2] & 255) ^ b2(i6)] & 255) ^ b2(i5)];
        int[] iArr42 = this.gMDS3;
        byte[] bArr42 = bArr22[1];
        i4 = iArr42[(bArr42[(bArr42[iB3] & 255) ^ b3(i6)] & 255) ^ b3(i5)];
        return i3 ^ i4;
    }

    private int Fe32_0(int i2) {
        int[] iArr = this.gSBox;
        return iArr[(((i2 >>> 24) & 255) * 2) + 513] ^ ((iArr[((i2 & 255) * 2) + 0] ^ iArr[(((i2 >>> 8) & 255) * 2) + 1]) ^ iArr[(((i2 >>> 16) & 255) * 2) + 512]);
    }

    private int Fe32_3(int i2) {
        int[] iArr = this.gSBox;
        return iArr[(((i2 >>> 16) & 255) * 2) + 513] ^ ((iArr[(((i2 >>> 24) & 255) * 2) + 0] ^ iArr[((i2 & 255) * 2) + 1]) ^ iArr[(((i2 >>> 8) & 255) * 2) + 512]);
    }

    private int LFSR1(int i2) {
        return ((i2 & 1) != 0 ? 180 : 0) ^ (i2 >> 1);
    }

    private int LFSR2(int i2) {
        return ((i2 >> 2) ^ ((i2 & 2) != 0 ? 180 : 0)) ^ ((i2 & 1) != 0 ? 90 : 0);
    }

    private int Mx_X(int i2) {
        return i2 ^ LFSR2(i2);
    }

    private int Mx_Y(int i2) {
        return LFSR2(i2) ^ (LFSR1(i2) ^ i2);
    }

    private int RS_MDS_Encode(int i2, int i3) {
        for (int i4 = 0; i4 < 4; i4++) {
            i3 = RS_rem(i3);
        }
        int iRS_rem = i2 ^ i3;
        for (int i5 = 0; i5 < 4; i5++) {
            iRS_rem = RS_rem(iRS_rem);
        }
        return iRS_rem;
    }

    private int RS_rem(int i2) {
        int i3 = (i2 >>> 24) & 255;
        int i4 = ((i3 << 1) ^ ((i3 & 128) != 0 ? 333 : 0)) & 255;
        int i5 = ((i3 >>> 1) ^ ((i3 & 1) != 0 ? 166 : 0)) ^ i4;
        return ((((i2 << 8) ^ (i5 << 24)) ^ (i4 << 16)) ^ (i5 << 8)) ^ i3;
    }

    private int b0(int i2) {
        return i2 & 255;
    }

    private int b1(int i2) {
        return (i2 >>> 8) & 255;
    }

    private int b2(int i2) {
        return (i2 >>> 16) & 255;
    }

    private int b3(int i2) {
        return (i2 >>> 24) & 255;
    }

    private void decryptBlock(byte[] bArr, int i2, byte[] bArr2, int i3) {
        int iBytesTo32Bits = BytesTo32Bits(bArr, i2) ^ this.gSubKeys[4];
        int iBytesTo32Bits2 = BytesTo32Bits(bArr, i2 + 4) ^ this.gSubKeys[5];
        int iBytesTo32Bits3 = BytesTo32Bits(bArr, i2 + 8) ^ this.gSubKeys[6];
        int iBytesTo32Bits4 = BytesTo32Bits(bArr, i2 + 12) ^ this.gSubKeys[7];
        int i4 = 39;
        int i5 = 0;
        while (i5 < 16) {
            int iFe32_0 = Fe32_0(iBytesTo32Bits);
            int iFe32_3 = Fe32_3(iBytesTo32Bits2);
            int[] iArr = this.gSubKeys;
            int i6 = i4 - 1;
            int i7 = iBytesTo32Bits4 ^ (((iFe32_3 * 2) + iFe32_0) + iArr[i4]);
            int i8 = (iBytesTo32Bits3 << 1) | (iBytesTo32Bits3 >>> 31);
            int i9 = i6 - 1;
            int i10 = i8 ^ ((iFe32_0 + iFe32_3) + iArr[i6]);
            iBytesTo32Bits4 = (i7 << 31) | (i7 >>> 1);
            int iFe32_02 = Fe32_0(i10);
            int iFe32_32 = Fe32_3(iBytesTo32Bits4);
            int[] iArr2 = this.gSubKeys;
            int i11 = i9 - 1;
            int i12 = iBytesTo32Bits2 ^ (((iFe32_32 * 2) + iFe32_02) + iArr2[i9]);
            iBytesTo32Bits = ((iBytesTo32Bits >>> 31) | (iBytesTo32Bits << 1)) ^ ((iFe32_02 + iFe32_32) + iArr2[i11]);
            iBytesTo32Bits2 = (i12 << 31) | (i12 >>> 1);
            i5 += 2;
            iBytesTo32Bits3 = i10;
            i4 = i11 - 1;
        }
        Bits32ToBytes(this.gSubKeys[0] ^ iBytesTo32Bits3, bArr2, i3);
        Bits32ToBytes(iBytesTo32Bits4 ^ this.gSubKeys[1], bArr2, i3 + 4);
        Bits32ToBytes(this.gSubKeys[2] ^ iBytesTo32Bits, bArr2, i3 + 8);
        Bits32ToBytes(this.gSubKeys[3] ^ iBytesTo32Bits2, bArr2, i3 + 12);
    }

    private void encryptBlock(byte[] bArr, int i2, byte[] bArr2, int i3) {
        int i4 = 0;
        int iBytesTo32Bits = BytesTo32Bits(bArr, i2) ^ this.gSubKeys[0];
        int iBytesTo32Bits2 = BytesTo32Bits(bArr, i2 + 4) ^ this.gSubKeys[1];
        int iBytesTo32Bits3 = BytesTo32Bits(bArr, i2 + 8) ^ this.gSubKeys[2];
        int iBytesTo32Bits4 = BytesTo32Bits(bArr, i2 + 12) ^ this.gSubKeys[3];
        int i5 = 8;
        while (i4 < 16) {
            int iFe32_0 = Fe32_0(iBytesTo32Bits);
            int iFe32_3 = Fe32_3(iBytesTo32Bits2);
            int[] iArr = this.gSubKeys;
            int i6 = i5 + 1;
            int i7 = iBytesTo32Bits3 ^ ((iFe32_0 + iFe32_3) + iArr[i5]);
            iBytesTo32Bits3 = (i7 >>> 1) | (i7 << 31);
            int i8 = (iBytesTo32Bits4 >>> 31) | (iBytesTo32Bits4 << 1);
            int i9 = i6 + 1;
            iBytesTo32Bits4 = i8 ^ ((iFe32_0 + (iFe32_3 * 2)) + iArr[i6]);
            int iFe32_02 = Fe32_0(iBytesTo32Bits3);
            int iFe32_32 = Fe32_3(iBytesTo32Bits4);
            int[] iArr2 = this.gSubKeys;
            int i10 = i9 + 1;
            int i11 = iBytesTo32Bits ^ ((iFe32_02 + iFe32_32) + iArr2[i9]);
            iBytesTo32Bits = (i11 >>> 1) | (i11 << 31);
            i4 += 2;
            iBytesTo32Bits2 = ((iBytesTo32Bits2 << 1) | (iBytesTo32Bits2 >>> 31)) ^ ((iFe32_02 + (iFe32_32 * 2)) + iArr2[i10]);
            i5 = i10 + 1;
        }
        Bits32ToBytes(this.gSubKeys[4] ^ iBytesTo32Bits3, bArr2, i3);
        Bits32ToBytes(iBytesTo32Bits4 ^ this.gSubKeys[5], bArr2, i3 + 4);
        Bits32ToBytes(this.gSubKeys[6] ^ iBytesTo32Bits, bArr2, i3 + 8);
        Bits32ToBytes(this.gSubKeys[7] ^ iBytesTo32Bits2, bArr2, i3 + 12);
    }

    private void setKey(byte[] bArr) {
        int iB0;
        int iB1;
        int iB2;
        int iB3;
        int iB22;
        int iB12;
        int iB02;
        int iB32;
        int[] iArr = new int[4];
        int[] iArr2 = new int[4];
        int[] iArr3 = new int[4];
        this.gSubKeys = new int[40];
        int i2 = this.k64Cnt;
        if (i2 < 1) {
            throw new IllegalArgumentException("Key size less than 64 bits");
        }
        if (i2 > 4) {
            throw new IllegalArgumentException("Key size larger than 256 bits");
        }
        for (int i3 = 0; i3 < this.k64Cnt; i3++) {
            int i4 = i3 * 8;
            iArr[i3] = BytesTo32Bits(bArr, i4);
            int iBytesTo32Bits = BytesTo32Bits(bArr, i4 + 4);
            iArr2[i3] = iBytesTo32Bits;
            iArr3[(this.k64Cnt - 1) - i3] = RS_MDS_Encode(iArr[i3], iBytesTo32Bits);
        }
        for (int i5 = 0; i5 < 20; i5++) {
            int i6 = SK_STEP * i5;
            int iF32 = F32(i6, iArr);
            int iF322 = F32(i6 + 16843009, iArr2);
            int i7 = (iF322 >>> 24) | (iF322 << 8);
            int i8 = iF32 + i7;
            int[] iArr4 = this.gSubKeys;
            int i9 = i5 * 2;
            iArr4[i9] = i8;
            int i10 = i8 + i7;
            iArr4[i9 + 1] = (i10 << 9) | (i10 >>> 23);
        }
        int i11 = iArr3[0];
        int i12 = iArr3[1];
        int i13 = 2;
        int i14 = iArr3[2];
        int i15 = iArr3[3];
        this.gSBox = new int[1024];
        int i16 = 0;
        while (i16 < 256) {
            int i17 = this.k64Cnt & 3;
            if (i17 != 0) {
                if (i17 == 1) {
                    int[] iArr5 = this.gSBox;
                    int i18 = i16 * 2;
                    int[] iArr6 = this.gMDS0;
                    byte[][] bArr2 = P;
                    iArr5[i18] = iArr6[(bArr2[0][i16] & 255) ^ b0(i11)];
                    this.gSBox[i18 + 1] = this.gMDS1[(bArr2[0][i16] & 255) ^ b1(i11)];
                    this.gSBox[i18 + 512] = this.gMDS2[(bArr2[1][i16] & 255) ^ b2(i11)];
                    this.gSBox[i18 + 513] = this.gMDS3[(bArr2[1][i16] & 255) ^ b3(i11)];
                } else if (i17 == i13) {
                    iB32 = i16;
                    iB02 = iB32;
                    iB12 = iB02;
                    iB22 = iB12;
                    int[] iArr7 = this.gSBox;
                    int i19 = i16 * 2;
                    int[] iArr8 = this.gMDS0;
                    byte[][] bArr3 = P;
                    byte[] bArr4 = bArr3[0];
                    iArr7[i19] = iArr8[(bArr4[(bArr4[iB02] & 255) ^ b0(i12)] & 255) ^ b0(i11)];
                    this.gSBox[i19 + 1] = this.gMDS1[(bArr3[0][(bArr3[1][iB12] & 255) ^ b1(i12)] & 255) ^ b1(i11)];
                    this.gSBox[i19 + 512] = this.gMDS2[(bArr3[1][(bArr3[0][iB22] & 255) ^ b2(i12)] & 255) ^ b2(i11)];
                    int[] iArr9 = this.gMDS3;
                    byte[] bArr5 = bArr3[1];
                    this.gSBox[i19 + 513] = iArr9[(bArr5[(bArr5[iB32] & 255) ^ b3(i12)] & 255) ^ b3(i11)];
                } else if (i17 == 3) {
                    iB3 = i16;
                    iB0 = iB3;
                    iB1 = iB0;
                    iB2 = iB1;
                }
                i16++;
                i13 = 2;
            } else {
                byte[][] bArr6 = P;
                iB0 = (bArr6[1][i16] & 255) ^ b0(i15);
                iB1 = (bArr6[0][i16] & 255) ^ b1(i15);
                iB2 = (bArr6[0][i16] & 255) ^ b2(i15);
                iB3 = (bArr6[1][i16] & 255) ^ b3(i15);
            }
            byte[][] bArr7 = P;
            iB02 = (bArr7[1][iB0] & 255) ^ b0(i14);
            iB12 = (bArr7[1][iB1] & 255) ^ b1(i14);
            iB22 = (bArr7[0][iB2] & 255) ^ b2(i14);
            iB32 = (bArr7[0][iB3] & 255) ^ b3(i14);
            int[] iArr72 = this.gSBox;
            int i192 = i16 * 2;
            int[] iArr82 = this.gMDS0;
            byte[][] bArr32 = P;
            byte[] bArr42 = bArr32[0];
            iArr72[i192] = iArr82[(bArr42[(bArr42[iB02] & 255) ^ b0(i12)] & 255) ^ b0(i11)];
            this.gSBox[i192 + 1] = this.gMDS1[(bArr32[0][(bArr32[1][iB12] & 255) ^ b1(i12)] & 255) ^ b1(i11)];
            this.gSBox[i192 + 512] = this.gMDS2[(bArr32[1][(bArr32[0][iB22] & 255) ^ b2(i12)] & 255) ^ b2(i11)];
            int[] iArr92 = this.gMDS3;
            byte[] bArr52 = bArr32[1];
            this.gSBox[i192 + 513] = iArr92[(bArr52[(bArr52[iB32] & 255) ^ b3(i12)] & 255) ^ b3(i11)];
            i16++;
            i13 = 2;
        }
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public String getAlgorithmName() {
        return "Twofish";
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int getBlockSize() {
        return 16;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void init(boolean z2, CipherParameters cipherParameters) {
        if (!(cipherParameters instanceof KeyParameter)) {
            throw new IllegalArgumentException("invalid parameter passed to Twofish init - " + cipherParameters.getClass().getName());
        }
        this.encrypting = z2;
        byte[] key = ((KeyParameter) cipherParameters).getKey();
        this.workingKey = key;
        this.k64Cnt = key.length / 8;
        setKey(key);
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int processBlock(byte[] bArr, int i2, byte[] bArr2, int i3) {
        if (this.workingKey == null) {
            throw new IllegalStateException("Twofish not initialised");
        }
        if (i2 + 16 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        }
        if (i3 + 16 > bArr2.length) {
            throw new DataLengthException("output buffer too short");
        }
        if (this.encrypting) {
            encryptBlock(bArr, i2, bArr2, i3);
            return 16;
        }
        decryptBlock(bArr, i2, bArr2, i3);
        return 16;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void reset() {
        byte[] bArr = this.workingKey;
        if (bArr != null) {
            setKey(bArr);
        }
    }
}
