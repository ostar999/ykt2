package org.bouncycastle.crypto.engines;

import com.google.common.base.Ascii;
import com.google.common.primitives.SignedBytes;
import com.yikaobang.yixue.R2;
import java.lang.reflect.Array;
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
public class AESLightEngine implements BlockCipher {
    private static final int BLOCK_SIZE = 16;

    /* renamed from: m1, reason: collision with root package name */
    private static final int f27827m1 = -2139062144;
    private static final int m2 = 2139062143;
    private static final int m3 = 27;
    private int C0;
    private int C1;
    private int C2;
    private int C3;
    private int ROUNDS;
    private int[][] WorkingKey = null;
    private boolean forEncryption;
    private static final byte[] S = {99, 124, 119, 123, -14, 107, 111, -59, TarConstants.LF_NORMAL, 1, TarConstants.LF_PAX_GLOBAL_EXTENDED_HEADER, 43, -2, -41, -85, 118, -54, -126, -55, 125, -6, 89, 71, -16, -83, -44, -94, -81, -100, -92, 114, -64, -73, -3, -109, 38, TarConstants.LF_FIFO, Utf8.REPLACEMENT_BYTE, -9, -52, TarConstants.LF_BLK, -91, -27, -15, 113, -40, TarConstants.LF_LINK, 21, 4, -57, 35, -61, Ascii.CAN, -106, 5, -102, 7, Ascii.DC2, -128, -30, -21, 39, -78, 117, 9, -125, 44, Ascii.SUB, Ascii.ESC, 110, 90, -96, 82, HttpTokens.SEMI_COLON, -42, -77, 41, -29, 47, -124, TarConstants.LF_GNUTYPE_SPARSE, -47, 0, -19, 32, -4, -79, 91, 106, -53, -66, 57, 74, TarConstants.LF_GNUTYPE_LONGNAME, TarConstants.LF_PAX_EXTENDED_HEADER_UC, -49, -48, -17, -86, -5, 67, 77, TarConstants.LF_CHR, -123, 69, -7, 2, 127, 80, 60, -97, -88, 81, -93, SignedBytes.MAX_POWER_OF_TWO, -113, -110, -99, 56, -11, PSSSigner.TRAILER_IMPLICIT, -74, -38, 33, 16, -1, -13, -46, -51, 12, 19, -20, 95, -105, 68, 23, -60, -89, 126, Base64.padSymbol, 100, 93, Ascii.EM, 115, 96, -127, 79, -36, 34, 42, -112, -120, 70, -18, -72, Ascii.DC4, -34, 94, 11, -37, -32, TarConstants.LF_SYMLINK, HttpTokens.COLON, 10, 73, 6, 36, 92, -62, -45, -84, 98, -111, -107, -28, 121, -25, -56, TarConstants.LF_CONTIG, 109, -115, -43, 78, -87, 108, 86, -12, -22, 101, 122, -82, 8, -70, TarConstants.LF_PAX_EXTENDED_HEADER_LC, 37, 46, Ascii.FS, -90, -76, -58, -24, -35, 116, Ascii.US, TarConstants.LF_GNUTYPE_LONGLINK, -67, -117, -118, 112, 62, -75, 102, 72, 3, -10, 14, 97, TarConstants.LF_DIR, 87, -71, -122, -63, Ascii.GS, -98, -31, -8, -104, 17, 105, -39, -114, -108, -101, Ascii.RS, -121, -23, -50, 85, 40, -33, -116, -95, -119, 13, -65, -26, 66, 104, 65, -103, 45, 15, -80, 84, -69, 22};
    private static final byte[] Si = {82, 9, 106, -43, TarConstants.LF_NORMAL, TarConstants.LF_FIFO, -91, 56, -65, SignedBytes.MAX_POWER_OF_TWO, -93, -98, -127, -13, -41, -5, 124, -29, 57, -126, -101, 47, -1, -121, TarConstants.LF_BLK, -114, 67, 68, -60, -34, -23, -53, 84, 123, -108, TarConstants.LF_SYMLINK, -90, -62, 35, Base64.padSymbol, -18, TarConstants.LF_GNUTYPE_LONGNAME, -107, 11, 66, -6, -61, 78, 8, 46, -95, 102, 40, -39, 36, -78, 118, 91, -94, 73, 109, -117, -47, 37, 114, -8, -10, 100, -122, 104, -104, 22, -44, -92, 92, -52, 93, 101, -74, -110, 108, 112, 72, 80, -3, -19, -71, -38, 94, 21, 70, 87, -89, -115, -99, -124, -112, -40, -85, 0, -116, PSSSigner.TRAILER_IMPLICIT, -45, 10, -9, -28, TarConstants.LF_PAX_EXTENDED_HEADER_UC, 5, -72, -77, 69, 6, -48, 44, Ascii.RS, -113, -54, Utf8.REPLACEMENT_BYTE, 15, 2, -63, -81, -67, 3, 1, 19, -118, 107, HttpTokens.COLON, -111, 17, 65, 79, TarConstants.LF_PAX_GLOBAL_EXTENDED_HEADER, -36, -22, -105, -14, -49, -50, -16, -76, -26, 115, -106, -84, 116, 34, -25, -83, TarConstants.LF_DIR, -123, -30, -7, TarConstants.LF_CONTIG, -24, Ascii.FS, 117, -33, 110, 71, -15, Ascii.SUB, 113, Ascii.GS, 41, -59, -119, 111, -73, 98, 14, -86, Ascii.CAN, -66, Ascii.ESC, -4, 86, 62, TarConstants.LF_GNUTYPE_LONGLINK, -58, -46, 121, 32, -102, -37, -64, -2, TarConstants.LF_PAX_EXTENDED_HEADER_LC, -51, 90, -12, Ascii.US, -35, -88, TarConstants.LF_CHR, -120, 7, -57, TarConstants.LF_LINK, -79, Ascii.DC2, 16, 89, 39, -128, -20, 95, 96, 81, 127, -87, Ascii.EM, -75, 74, 13, 45, -27, 122, -97, -109, -55, -100, -17, -96, -32, HttpTokens.SEMI_COLON, 77, -82, 42, -11, -80, -56, -21, -69, 60, -125, TarConstants.LF_GNUTYPE_SPARSE, -103, 97, 23, 43, 4, 126, -70, 119, -42, 38, -31, 105, Ascii.DC4, 99, 85, 33, 12, 125};
    private static final int[] rcon = {1, 2, 4, 8, 16, 32, 64, 128, 27, 54, 108, 216, R2.anim.widget_zoom_in, 77, 154, 47, 94, 188, 99, 198, 151, 53, 106, 212, 179, 125, 250, 239, R2.array.ease_pdf_file_suffix, 145};

    private int FFmulX(int i2) {
        return (((i2 & f27827m1) >>> 7) * 27) ^ ((m2 & i2) << 1);
    }

    private void decryptBlock(int[][] iArr) {
        int i2 = this.C0;
        int i3 = this.ROUNDS;
        int[] iArr2 = iArr[i3];
        this.C0 = i2 ^ iArr2[0];
        this.C1 ^= iArr2[1];
        this.C2 ^= iArr2[2];
        this.C3 ^= iArr2[3];
        int i4 = i3 - 1;
        while (true) {
            byte[] bArr = Si;
            int i5 = this.C0 & 255;
            if (i4 <= 1) {
                int iInv_mcol = inv_mcol((((bArr[i5] & 255) ^ ((bArr[(this.C3 >> 8) & 255] & 255) << 8)) ^ ((bArr[(this.C2 >> 16) & 255] & 255) << 16)) ^ (bArr[(this.C1 >> 24) & 255] << Ascii.CAN)) ^ iArr[i4][0];
                int iInv_mcol2 = inv_mcol((((bArr[this.C1 & 255] & 255) ^ ((bArr[(this.C0 >> 8) & 255] & 255) << 8)) ^ ((bArr[(this.C3 >> 16) & 255] & 255) << 16)) ^ (bArr[(this.C2 >> 24) & 255] << Ascii.CAN)) ^ iArr[i4][1];
                int iInv_mcol3 = inv_mcol((((bArr[this.C2 & 255] & 255) ^ ((bArr[(this.C1 >> 8) & 255] & 255) << 8)) ^ ((bArr[(this.C0 >> 16) & 255] & 255) << 16)) ^ (bArr[(this.C3 >> 24) & 255] << Ascii.CAN)) ^ iArr[i4][2];
                int iInv_mcol4 = iArr[i4][3] ^ inv_mcol((((bArr[this.C3 & 255] & 255) ^ ((bArr[(this.C2 >> 8) & 255] & 255) << 8)) ^ ((bArr[(this.C1 >> 16) & 255] & 255) << 16)) ^ (bArr[(this.C0 >> 24) & 255] << Ascii.CAN));
                int i6 = (((bArr[iInv_mcol & 255] & 255) ^ ((bArr[(iInv_mcol4 >> 8) & 255] & 255) << 8)) ^ ((bArr[(iInv_mcol3 >> 16) & 255] & 255) << 16)) ^ (bArr[(iInv_mcol2 >> 24) & 255] << Ascii.CAN);
                int[] iArr3 = iArr[0];
                this.C0 = iArr3[0] ^ i6;
                this.C1 = ((((bArr[iInv_mcol2 & 255] & 255) ^ ((bArr[(iInv_mcol >> 8) & 255] & 255) << 8)) ^ ((bArr[(iInv_mcol4 >> 16) & 255] & 255) << 16)) ^ (bArr[(iInv_mcol3 >> 24) & 255] << Ascii.CAN)) ^ iArr3[1];
                this.C2 = ((((bArr[iInv_mcol3 & 255] & 255) ^ ((bArr[(iInv_mcol2 >> 8) & 255] & 255) << 8)) ^ ((bArr[(iInv_mcol >> 16) & 255] & 255) << 16)) ^ (bArr[(iInv_mcol4 >> 24) & 255] << Ascii.CAN)) ^ iArr3[2];
                this.C3 = iArr3[3] ^ ((bArr[(iInv_mcol >> 24) & 255] << Ascii.CAN) ^ (((bArr[iInv_mcol4 & 255] & 255) ^ ((bArr[(iInv_mcol3 >> 8) & 255] & 255) << 8)) ^ ((bArr[(iInv_mcol2 >> 16) & 255] & 255) << 16)));
                return;
            }
            int iInv_mcol5 = inv_mcol((((bArr[i5] & 255) ^ ((bArr[(this.C3 >> 8) & 255] & 255) << 8)) ^ ((bArr[(this.C2 >> 16) & 255] & 255) << 16)) ^ (bArr[(this.C1 >> 24) & 255] << Ascii.CAN)) ^ iArr[i4][0];
            int iInv_mcol6 = inv_mcol((((bArr[this.C1 & 255] & 255) ^ ((bArr[(this.C0 >> 8) & 255] & 255) << 8)) ^ ((bArr[(this.C3 >> 16) & 255] & 255) << 16)) ^ (bArr[(this.C2 >> 24) & 255] << Ascii.CAN)) ^ iArr[i4][1];
            int iInv_mcol7 = inv_mcol((((bArr[this.C2 & 255] & 255) ^ ((bArr[(this.C1 >> 8) & 255] & 255) << 8)) ^ ((bArr[(this.C0 >> 16) & 255] & 255) << 16)) ^ (bArr[(this.C3 >> 24) & 255] << Ascii.CAN)) ^ iArr[i4][2];
            int i7 = i4 - 1;
            int iInv_mcol8 = iArr[i4][3] ^ inv_mcol((((bArr[this.C3 & 255] & 255) ^ ((bArr[(this.C2 >> 8) & 255] & 255) << 8)) ^ ((bArr[(this.C1 >> 16) & 255] & 255) << 16)) ^ (bArr[(this.C0 >> 24) & 255] << Ascii.CAN));
            this.C0 = inv_mcol((((bArr[iInv_mcol5 & 255] & 255) ^ ((bArr[(iInv_mcol8 >> 8) & 255] & 255) << 8)) ^ ((bArr[(iInv_mcol7 >> 16) & 255] & 255) << 16)) ^ (bArr[(iInv_mcol6 >> 24) & 255] << Ascii.CAN)) ^ iArr[i7][0];
            this.C1 = inv_mcol((((bArr[iInv_mcol6 & 255] & 255) ^ ((bArr[(iInv_mcol5 >> 8) & 255] & 255) << 8)) ^ ((bArr[(iInv_mcol8 >> 16) & 255] & 255) << 16)) ^ (bArr[(iInv_mcol7 >> 24) & 255] << Ascii.CAN)) ^ iArr[i7][1];
            this.C2 = inv_mcol((((bArr[iInv_mcol7 & 255] & 255) ^ ((bArr[(iInv_mcol6 >> 8) & 255] & 255) << 8)) ^ ((bArr[(iInv_mcol5 >> 16) & 255] & 255) << 16)) ^ (bArr[(iInv_mcol8 >> 24) & 255] << Ascii.CAN)) ^ iArr[i7][2];
            int iInv_mcol9 = inv_mcol((bArr[(iInv_mcol5 >> 24) & 255] << Ascii.CAN) ^ (((bArr[iInv_mcol8 & 255] & 255) ^ ((bArr[(iInv_mcol7 >> 8) & 255] & 255) << 8)) ^ ((bArr[(iInv_mcol6 >> 16) & 255] & 255) << 16)));
            i4 = i7 - 1;
            this.C3 = iInv_mcol9 ^ iArr[i7][3];
        }
    }

    private void encryptBlock(int[][] iArr) {
        int i2 = this.C0;
        int[] iArr2 = iArr[0];
        this.C0 = i2 ^ iArr2[0];
        this.C1 ^= iArr2[1];
        this.C2 ^= iArr2[2];
        this.C3 ^= iArr2[3];
        int i3 = 1;
        while (i3 < this.ROUNDS - 1) {
            byte[] bArr = S;
            int iMcol = mcol((((bArr[this.C0 & 255] & 255) ^ ((bArr[(this.C1 >> 8) & 255] & 255) << 8)) ^ ((bArr[(this.C2 >> 16) & 255] & 255) << 16)) ^ (bArr[(this.C3 >> 24) & 255] << Ascii.CAN)) ^ iArr[i3][0];
            int iMcol2 = mcol((((bArr[this.C1 & 255] & 255) ^ ((bArr[(this.C2 >> 8) & 255] & 255) << 8)) ^ ((bArr[(this.C3 >> 16) & 255] & 255) << 16)) ^ (bArr[(this.C0 >> 24) & 255] << Ascii.CAN)) ^ iArr[i3][1];
            int iMcol3 = mcol((((bArr[this.C2 & 255] & 255) ^ ((bArr[(this.C3 >> 8) & 255] & 255) << 8)) ^ ((bArr[(this.C0 >> 16) & 255] & 255) << 16)) ^ (bArr[(this.C1 >> 24) & 255] << Ascii.CAN)) ^ iArr[i3][2];
            int i4 = i3 + 1;
            int iMcol4 = iArr[i3][3] ^ mcol((((bArr[this.C3 & 255] & 255) ^ ((bArr[(this.C0 >> 8) & 255] & 255) << 8)) ^ ((bArr[(this.C1 >> 16) & 255] & 255) << 16)) ^ (bArr[(this.C2 >> 24) & 255] << Ascii.CAN));
            this.C0 = mcol((((bArr[iMcol & 255] & 255) ^ ((bArr[(iMcol2 >> 8) & 255] & 255) << 8)) ^ ((bArr[(iMcol3 >> 16) & 255] & 255) << 16)) ^ (bArr[(iMcol4 >> 24) & 255] << Ascii.CAN)) ^ iArr[i4][0];
            this.C1 = mcol((((bArr[iMcol2 & 255] & 255) ^ ((bArr[(iMcol3 >> 8) & 255] & 255) << 8)) ^ ((bArr[(iMcol4 >> 16) & 255] & 255) << 16)) ^ (bArr[(iMcol >> 24) & 255] << Ascii.CAN)) ^ iArr[i4][1];
            this.C2 = mcol((((bArr[iMcol3 & 255] & 255) ^ ((bArr[(iMcol4 >> 8) & 255] & 255) << 8)) ^ ((bArr[(iMcol >> 16) & 255] & 255) << 16)) ^ (bArr[(iMcol2 >> 24) & 255] << Ascii.CAN)) ^ iArr[i4][2];
            this.C3 = mcol((((bArr[iMcol4 & 255] & 255) ^ ((bArr[(iMcol >> 8) & 255] & 255) << 8)) ^ ((bArr[(iMcol2 >> 16) & 255] & 255) << 16)) ^ (bArr[(iMcol3 >> 24) & 255] << Ascii.CAN)) ^ iArr[i4][3];
            i3 = i4 + 1;
        }
        byte[] bArr2 = S;
        int iMcol5 = mcol((((bArr2[this.C0 & 255] & 255) ^ ((bArr2[(this.C1 >> 8) & 255] & 255) << 8)) ^ ((bArr2[(this.C2 >> 16) & 255] & 255) << 16)) ^ (bArr2[(this.C3 >> 24) & 255] << Ascii.CAN)) ^ iArr[i3][0];
        int iMcol6 = mcol((((bArr2[this.C1 & 255] & 255) ^ ((bArr2[(this.C2 >> 8) & 255] & 255) << 8)) ^ ((bArr2[(this.C3 >> 16) & 255] & 255) << 16)) ^ (bArr2[(this.C0 >> 24) & 255] << Ascii.CAN)) ^ iArr[i3][1];
        int iMcol7 = mcol((((bArr2[this.C2 & 255] & 255) ^ ((bArr2[(this.C3 >> 8) & 255] & 255) << 8)) ^ ((bArr2[(this.C0 >> 16) & 255] & 255) << 16)) ^ (bArr2[(this.C1 >> 24) & 255] << Ascii.CAN)) ^ iArr[i3][2];
        int i5 = i3 + 1;
        int iMcol8 = iArr[i3][3] ^ mcol((((bArr2[this.C3 & 255] & 255) ^ ((bArr2[(this.C0 >> 8) & 255] & 255) << 8)) ^ ((bArr2[(this.C1 >> 16) & 255] & 255) << 16)) ^ (bArr2[(this.C2 >> 24) & 255] << Ascii.CAN));
        int i6 = (((bArr2[iMcol5 & 255] & 255) ^ ((bArr2[(iMcol6 >> 8) & 255] & 255) << 8)) ^ ((bArr2[(iMcol7 >> 16) & 255] & 255) << 16)) ^ (bArr2[(iMcol8 >> 24) & 255] << Ascii.CAN);
        int[] iArr3 = iArr[i5];
        this.C0 = iArr3[0] ^ i6;
        this.C1 = ((((bArr2[iMcol6 & 255] & 255) ^ ((bArr2[(iMcol7 >> 8) & 255] & 255) << 8)) ^ ((bArr2[(iMcol8 >> 16) & 255] & 255) << 16)) ^ (bArr2[(iMcol5 >> 24) & 255] << Ascii.CAN)) ^ iArr3[1];
        this.C2 = ((((bArr2[iMcol7 & 255] & 255) ^ ((bArr2[(iMcol8 >> 8) & 255] & 255) << 8)) ^ ((bArr2[(iMcol5 >> 16) & 255] & 255) << 16)) ^ (bArr2[(iMcol6 >> 24) & 255] << Ascii.CAN)) ^ iArr3[2];
        this.C3 = iArr3[3] ^ ((((bArr2[iMcol8 & 255] & 255) ^ ((bArr2[(iMcol5 >> 8) & 255] & 255) << 8)) ^ ((bArr2[(iMcol6 >> 16) & 255] & 255) << 16)) ^ (bArr2[(iMcol7 >> 24) & 255] << Ascii.CAN));
    }

    private int[][] generateWorkingKey(byte[] bArr, boolean z2) {
        int length = bArr.length / 4;
        if ((length != 4 && length != 6 && length != 8) || length * 4 != bArr.length) {
            throw new IllegalArgumentException("Key length not 128/192/256 bits.");
        }
        int i2 = length + 6;
        this.ROUNDS = i2;
        int[][] iArr = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, i2 + 1, 4);
        int i3 = 0;
        int i4 = 0;
        while (i3 < bArr.length) {
            iArr[i4 >> 2][i4 & 3] = (bArr[i3] & 255) | ((bArr[i3 + 1] & 255) << 8) | ((bArr[i3 + 2] & 255) << 16) | (bArr[i3 + 3] << Ascii.CAN);
            i3 += 4;
            i4++;
        }
        int i5 = (this.ROUNDS + 1) << 2;
        for (int i6 = length; i6 < i5; i6++) {
            int i7 = i6 - 1;
            int iSubWord = iArr[i7 >> 2][i7 & 3];
            int i8 = i6 % length;
            if (i8 == 0) {
                iSubWord = subWord(shift(iSubWord, 8)) ^ rcon[(i6 / length) - 1];
            } else if (length > 6 && i8 == 4) {
                iSubWord = subWord(iSubWord);
            }
            int i9 = i6 - length;
            iArr[i6 >> 2][i6 & 3] = iSubWord ^ iArr[i9 >> 2][i9 & 3];
        }
        if (!z2) {
            for (int i10 = 1; i10 < this.ROUNDS; i10++) {
                for (int i11 = 0; i11 < 4; i11++) {
                    int[] iArr2 = iArr[i10];
                    iArr2[i11] = inv_mcol(iArr2[i11]);
                }
            }
        }
        return iArr;
    }

    private int inv_mcol(int i2) {
        int iFFmulX = FFmulX(i2);
        int iFFmulX2 = FFmulX(iFFmulX);
        int iFFmulX3 = FFmulX(iFFmulX2);
        int i3 = i2 ^ iFFmulX3;
        return shift(i3, 24) ^ ((shift(iFFmulX ^ i3, 8) ^ (iFFmulX3 ^ (iFFmulX ^ iFFmulX2))) ^ shift(iFFmulX2 ^ i3, 16));
    }

    private int mcol(int i2) {
        int iFFmulX = FFmulX(i2);
        return shift(i2, 24) ^ ((iFFmulX ^ shift(i2 ^ iFFmulX, 8)) ^ shift(i2, 16));
    }

    private void packBlock(byte[] bArr, int i2) {
        int i3 = i2 + 1;
        int i4 = this.C0;
        bArr[i2] = (byte) i4;
        int i5 = i3 + 1;
        bArr[i3] = (byte) (i4 >> 8);
        int i6 = i5 + 1;
        bArr[i5] = (byte) (i4 >> 16);
        int i7 = i6 + 1;
        bArr[i6] = (byte) (i4 >> 24);
        int i8 = i7 + 1;
        int i9 = this.C1;
        bArr[i7] = (byte) i9;
        int i10 = i8 + 1;
        bArr[i8] = (byte) (i9 >> 8);
        int i11 = i10 + 1;
        bArr[i10] = (byte) (i9 >> 16);
        int i12 = i11 + 1;
        bArr[i11] = (byte) (i9 >> 24);
        int i13 = i12 + 1;
        int i14 = this.C2;
        bArr[i12] = (byte) i14;
        int i15 = i13 + 1;
        bArr[i13] = (byte) (i14 >> 8);
        int i16 = i15 + 1;
        bArr[i15] = (byte) (i14 >> 16);
        int i17 = i16 + 1;
        bArr[i16] = (byte) (i14 >> 24);
        int i18 = i17 + 1;
        int i19 = this.C3;
        bArr[i17] = (byte) i19;
        int i20 = i18 + 1;
        bArr[i18] = (byte) (i19 >> 8);
        bArr[i20] = (byte) (i19 >> 16);
        bArr[i20 + 1] = (byte) (i19 >> 24);
    }

    private int shift(int i2, int i3) {
        return (i2 << (-i3)) | (i2 >>> i3);
    }

    private int subWord(int i2) {
        byte[] bArr = S;
        return (bArr[(i2 >> 24) & 255] << Ascii.CAN) | (bArr[i2 & 255] & 255) | ((bArr[(i2 >> 8) & 255] & 255) << 8) | ((bArr[(i2 >> 16) & 255] & 255) << 16);
    }

    private void unpackBlock(byte[] bArr, int i2) {
        int i3 = i2 + 1;
        int i4 = i3 + 1;
        int i5 = (bArr[i2] & 255) | ((bArr[i3] & 255) << 8);
        int i6 = i4 + 1;
        int i7 = i5 | ((bArr[i4] & 255) << 16);
        int i8 = i6 + 1;
        this.C0 = i7 | (bArr[i6] << Ascii.CAN);
        int i9 = i8 + 1;
        int i10 = bArr[i8] & 255;
        int i11 = i9 + 1;
        int i12 = ((bArr[i9] & 255) << 8) | i10;
        int i13 = i11 + 1;
        int i14 = i12 | ((bArr[i11] & 255) << 16);
        int i15 = i13 + 1;
        this.C1 = i14 | (bArr[i13] << Ascii.CAN);
        int i16 = i15 + 1;
        int i17 = bArr[i15] & 255;
        int i18 = i16 + 1;
        int i19 = ((bArr[i16] & 255) << 8) | i17;
        int i20 = i18 + 1;
        int i21 = i19 | ((bArr[i18] & 255) << 16);
        int i22 = i20 + 1;
        this.C2 = i21 | (bArr[i20] << Ascii.CAN);
        int i23 = i22 + 1;
        int i24 = bArr[i22] & 255;
        int i25 = i23 + 1;
        int i26 = ((bArr[i23] & 255) << 8) | i24;
        int i27 = i26 | ((bArr[i25] & 255) << 16);
        this.C3 = (bArr[i25 + 1] << Ascii.CAN) | i27;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public String getAlgorithmName() {
        return "AES";
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int getBlockSize() {
        return 16;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void init(boolean z2, CipherParameters cipherParameters) {
        if (cipherParameters instanceof KeyParameter) {
            this.WorkingKey = generateWorkingKey(((KeyParameter) cipherParameters).getKey(), z2);
            this.forEncryption = z2;
        } else {
            throw new IllegalArgumentException("invalid parameter passed to AES init - " + cipherParameters.getClass().getName());
        }
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int processBlock(byte[] bArr, int i2, byte[] bArr2, int i3) {
        if (this.WorkingKey == null) {
            throw new IllegalStateException("AES engine not initialised");
        }
        if (i2 + 16 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        }
        if (i3 + 16 > bArr2.length) {
            throw new DataLengthException("output buffer too short");
        }
        boolean z2 = this.forEncryption;
        unpackBlock(bArr, i2);
        int[][] iArr = this.WorkingKey;
        if (z2) {
            encryptBlock(iArr);
        } else {
            decryptBlock(iArr);
        }
        packBlock(bArr2, i3);
        return 16;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void reset() {
    }
}
