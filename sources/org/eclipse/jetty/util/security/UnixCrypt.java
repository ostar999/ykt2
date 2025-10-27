package org.eclipse.jetty.util.security;

import com.google.common.base.Ascii;
import com.google.common.primitives.SignedBytes;
import com.yikaobang.yixue.R2;
import java.lang.reflect.Array;
import kotlin.io.encoding.Base64;
import net.lingala.zip4j.util.InternalZipConstants;
import okio.Utf8;
import org.apache.commons.compress.archivers.tar.TarConstants;
import org.eclipse.jetty.http.HttpTokens;

/* loaded from: classes9.dex */
public class UnixCrypt {
    private static final long[][] CF6464;
    private static final long[][] IE3264;
    private static final long[][] PC1ROT;
    private static final long[][][] PC2ROT;
    private static final long[][] SPE;
    private static final byte[] IP = {HttpTokens.COLON, TarConstants.LF_SYMLINK, 42, 34, Ascii.SUB, Ascii.DC2, 10, 2, 60, TarConstants.LF_BLK, 44, 36, Ascii.FS, Ascii.DC4, 12, 4, 62, TarConstants.LF_FIFO, 46, 38, Ascii.RS, 22, 14, 6, SignedBytes.MAX_POWER_OF_TWO, 56, TarConstants.LF_NORMAL, 40, 32, Ascii.CAN, 16, 8, 57, TarConstants.LF_LINK, 41, 33, Ascii.EM, 17, 9, 1, HttpTokens.SEMI_COLON, TarConstants.LF_CHR, 43, 35, Ascii.ESC, 19, 11, 3, Base64.padSymbol, TarConstants.LF_DIR, 45, 37, Ascii.GS, 21, 13, 5, Utf8.REPLACEMENT_BYTE, TarConstants.LF_CONTIG, 47, 39, Ascii.US, 23, 15, 7};
    private static final byte[] ExpandTr = {32, 1, 2, 3, 4, 5, 4, 5, 6, 7, 8, 9, 8, 9, 10, 11, 12, 13, 12, 13, 14, 15, 16, 17, 16, 17, Ascii.DC2, 19, Ascii.DC4, 21, Ascii.DC4, 21, 22, 23, Ascii.CAN, Ascii.EM, Ascii.CAN, Ascii.EM, Ascii.SUB, Ascii.ESC, Ascii.FS, Ascii.GS, Ascii.FS, Ascii.GS, Ascii.RS, Ascii.US, 32, 1};
    private static final byte[] PC1 = {57, TarConstants.LF_LINK, 41, 33, Ascii.EM, 17, 9, 1, HttpTokens.COLON, TarConstants.LF_SYMLINK, 42, 34, Ascii.SUB, Ascii.DC2, 10, 2, HttpTokens.SEMI_COLON, TarConstants.LF_CHR, 43, 35, Ascii.ESC, 19, 11, 3, 60, TarConstants.LF_BLK, 44, 36, Utf8.REPLACEMENT_BYTE, TarConstants.LF_CONTIG, 47, 39, Ascii.US, 23, 15, 7, 62, TarConstants.LF_FIFO, 46, 38, Ascii.RS, 22, 14, 6, Base64.padSymbol, TarConstants.LF_DIR, 45, 37, Ascii.GS, 21, 13, 5, Ascii.FS, Ascii.DC4, 12, 4};
    private static final byte[] Rotates = {1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1};
    private static final byte[] PC2 = {9, Ascii.DC2, 14, 17, 11, Ascii.CAN, 1, 5, 22, Ascii.EM, 3, Ascii.FS, 15, 6, 21, 10, 35, 38, 23, 19, 12, 4, Ascii.SUB, 8, 43, TarConstants.LF_FIFO, 16, 7, Ascii.ESC, Ascii.DC4, 13, 2, 0, 0, 41, TarConstants.LF_BLK, Ascii.US, 37, 47, TarConstants.LF_CONTIG, 0, 0, Ascii.RS, 40, TarConstants.LF_CHR, 45, 33, TarConstants.LF_NORMAL, 0, 0, 44, TarConstants.LF_LINK, 39, 56, 34, TarConstants.LF_DIR, 0, 0, 46, 42, TarConstants.LF_SYMLINK, 36, Ascii.GS, 32};
    private static final byte[][] S = {new byte[]{14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7, 0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8, 4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0, 15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}, new byte[]{15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10, 3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5, 0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15, 13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}, new byte[]{10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8, 13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1, 13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7, 1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}, new byte[]{7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15, 13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9, 10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4, 3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14}, new byte[]{2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9, 14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6, 4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14, 11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3}, new byte[]{12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11, 10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8, 9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6, 4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13}, new byte[]{4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1, 13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6, 1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2, 6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12}, new byte[]{13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7, 1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2, 7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8, 2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}};
    private static final byte[] P32Tr = {16, 7, Ascii.DC4, 21, Ascii.GS, 12, Ascii.FS, 17, 1, 15, 23, Ascii.SUB, 5, Ascii.DC2, Ascii.US, 10, 2, 8, Ascii.CAN, 14, 32, Ascii.ESC, 3, 9, 19, 13, Ascii.RS, 6, 22, 11, 4, Ascii.EM};
    private static final byte[] CIFP = {1, 2, 3, 4, 17, Ascii.DC2, 19, Ascii.DC4, 5, 6, 7, 8, 21, 22, 23, Ascii.CAN, 9, 10, 11, 12, Ascii.EM, Ascii.SUB, Ascii.ESC, Ascii.FS, 13, 14, 15, 16, Ascii.GS, Ascii.RS, Ascii.US, 32, 33, 34, 35, 36, TarConstants.LF_LINK, TarConstants.LF_SYMLINK, TarConstants.LF_CHR, TarConstants.LF_BLK, 37, 38, 39, 40, TarConstants.LF_DIR, TarConstants.LF_FIFO, TarConstants.LF_CONTIG, 56, 41, 42, 43, 44, 57, HttpTokens.COLON, HttpTokens.SEMI_COLON, 60, 45, 46, 47, TarConstants.LF_NORMAL, Base64.padSymbol, 62, Utf8.REPLACEMENT_BYTE, SignedBytes.MAX_POWER_OF_TWO};
    private static final byte[] ITOA64 = {46, 47, TarConstants.LF_NORMAL, TarConstants.LF_LINK, TarConstants.LF_SYMLINK, TarConstants.LF_CHR, TarConstants.LF_BLK, TarConstants.LF_DIR, TarConstants.LF_FIFO, TarConstants.LF_CONTIG, 56, 57, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, TarConstants.LF_GNUTYPE_LONGLINK, TarConstants.LF_GNUTYPE_LONGNAME, 77, 78, 79, 80, 81, 82, TarConstants.LF_GNUTYPE_SPARSE, 84, 85, 86, 87, TarConstants.LF_PAX_EXTENDED_HEADER_UC, 89, 90, 97, 98, 99, 100, 101, 102, TarConstants.LF_PAX_GLOBAL_EXTENDED_HEADER, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, TarConstants.LF_PAX_EXTENDED_HEADER_LC, 121, 122};
    private static final byte[] A64TOI = new byte[128];

    static {
        int i2 = 64;
        int i3 = 8;
        int i4 = 2;
        int i5 = 3;
        int i6 = 32;
        Class cls = Long.TYPE;
        PC1ROT = (long[][]) Array.newInstance((Class<?>) cls, 16, 16);
        PC2ROT = (long[][][]) Array.newInstance((Class<?>) cls, 2, 16, 16);
        IE3264 = (long[][]) Array.newInstance((Class<?>) cls, 8, 16);
        SPE = (long[][]) Array.newInstance((Class<?>) cls, 8, 64);
        CF6464 = (long[][]) Array.newInstance((Class<?>) cls, 16, 16);
        byte[] bArr = new byte[64];
        byte[] bArr2 = new byte[64];
        for (int i7 = 0; i7 < 64; i7++) {
            A64TOI[ITOA64[i7]] = (byte) i7;
        }
        for (int i8 = 0; i8 < 64; i8++) {
            bArr[i8] = 0;
        }
        for (int i9 = 0; i9 < 64; i9++) {
            byte b3 = PC2[i9];
            if (b3 != 0) {
                byte b4 = Rotates[0];
                int i10 = b3 + (b4 - 1);
                if (i10 % 28 < b4) {
                    i10 -= 28;
                }
                int i11 = PC1[i10];
                if (i11 > 0) {
                    int i12 = i11 - 1;
                    i11 = ((i12 | 7) - (i12 & 7)) + 1;
                }
                bArr[i9] = (byte) i11;
            }
        }
        init_perm(PC1ROT, bArr, 8);
        for (int i13 = 0; i13 < 2; i13++) {
            for (int i14 = 0; i14 < 64; i14++) {
                bArr2[i14] = 0;
                bArr[i14] = 0;
            }
            for (int i15 = 0; i15 < 64; i15++) {
                byte b5 = PC2[i15];
                if (b5 != 0) {
                    bArr2[b5 - 1] = (byte) (i15 + 1);
                }
            }
            for (int i16 = 0; i16 < 64; i16++) {
                byte b6 = PC2[i16];
                if (b6 != 0) {
                    int i17 = b6 + i13;
                    if (i17 % 28 <= i13) {
                        i17 -= 28;
                    }
                    bArr[i16] = bArr2[i17];
                }
            }
            init_perm(PC2ROT[i13], bArr, 8);
        }
        for (int i18 = 0; i18 < 8; i18++) {
            int i19 = 0;
            while (i19 < 8) {
                int i20 = i19 < 2 ? 0 : IP[ExpandTr[((i18 * 6) + i19) - 2] - 1];
                if (i20 > 32) {
                    i20 -= 32;
                } else if (i20 > 0) {
                    i20--;
                }
                if (i20 > 0) {
                    int i21 = i20 - 1;
                    i20 = ((i21 | 7) - (i21 & 7)) + 1;
                }
                bArr[(i18 * 8) + i19] = (byte) i20;
                i19++;
            }
        }
        init_perm(IE3264, bArr, 8);
        int i22 = 0;
        while (i22 < 64) {
            int i23 = IP[CIFP[i22] - 1];
            if (i23 > 0) {
                int i24 = i23 - 1;
                i23 = ((i24 | 7) - (i24 & 7)) + 1;
            }
            i22++;
            bArr[i23 - 1] = (byte) i22;
        }
        init_perm(CF6464, bArr, 8);
        for (int i25 = 0; i25 < 48; i25++) {
            bArr[i25] = P32Tr[ExpandTr[i25] - 1];
        }
        int i26 = 0;
        while (i26 < i3) {
            int i27 = 0;
            while (i27 < i2) {
                byte b7 = S[i26][(((i27 >> 0) & 1) << 5) | (((i27 >> 1) & 1) << i5) | (((i27 >> 2) & 1) << i4) | (((i27 >> 3) & 1) << 1) | (((i27 >> 4) & 1) << 0) | (((i27 >> 5) & 1) << 4)];
                int i28 = (((b7 >> 0) & 1) << i5) | (((b7 >> 3) & 1) << 0) | (((b7 >> 2) & 1) << 1) | (((b7 >> 1) & 1) << i4);
                for (int i29 = 0; i29 < i6; i29++) {
                    bArr2[i29] = 0;
                }
                for (int i30 = 0; i30 < 4; i30++) {
                    bArr2[(i26 * 4) + i30] = (byte) ((i28 >> i30) & 1);
                }
                int i31 = 24;
                long j2 = 0;
                while (true) {
                    i31--;
                    if (i31 >= 0) {
                        j2 = (bArr2[bArr[i31] - 1] << i6) | (j2 << 1) | bArr2[bArr[i31 + 24] - 1];
                        i6 = 32;
                    }
                }
                SPE[i26][i27] = to_six_bit(j2);
                i27++;
                i2 = 64;
                i6 = 32;
                i4 = 2;
                i5 = 3;
            }
            i26++;
            i2 = 64;
            i3 = 8;
            i6 = 32;
            i4 = 2;
            i5 = 3;
        }
    }

    private UnixCrypt() {
    }

    public static String crypt(String str, String str2) {
        byte[] bArr = new byte[13];
        if (str == null || str2 == null) {
            return "*";
        }
        int length = str.length();
        int i2 = 0;
        long jCharAt = 0;
        while (i2 < 8) {
            jCharAt = (jCharAt << 8) | (i2 < length ? str.charAt(i2) * 2 : 0);
            i2++;
        }
        long[] jArrDes_setkey = des_setkey(jCharAt);
        int i3 = 0;
        int i4 = 2;
        while (true) {
            i4--;
            if (i4 < 0) {
                break;
            }
            char cCharAt = i4 < str2.length() ? str2.charAt(i4) : '.';
            bArr[i4] = (byte) cCharAt;
            i3 = (i3 << 6) | (A64TOI[cCharAt] & 255);
        }
        long jDes_cipher = des_cipher(0L, i3, 25, jArrDes_setkey);
        int i5 = 12;
        bArr[12] = ITOA64[(((int) jDes_cipher) << 2) & 63];
        char c3 = 4;
        while (true) {
            jDes_cipher >>= c3;
            i5--;
            if (i5 < 2) {
                return new String(bArr, 0, 13);
            }
            bArr[i5] = ITOA64[((int) jDes_cipher) & 63];
            c3 = 6;
        }
    }

    private static long des_cipher(long j2, int i2, int i3, long[] jArr) {
        int i4 = to_six_bit(i2);
        long j3 = j2 & 6148914691236517205L;
        char c3 = 1;
        long j4 = (j2 & (-6148914694099828736L)) | ((j2 >> 1) & 1431655765);
        char c4 = ' ';
        long j5 = InternalZipConstants.ZIP_64_LIMIT;
        int i5 = (int) (((((j3 << 32) | (j3 << 1)) & (-4294967296L)) | ((j4 | (j4 >> 32)) & InternalZipConstants.ZIP_64_LIMIT)) >> 32);
        long[][] jArr2 = IE3264;
        long jPerm3264 = perm3264(i5, jArr2);
        long j6 = jPerm3264;
        long jPerm32642 = perm3264((int) (jPerm3264 & (-1)), jArr2);
        int i6 = i3;
        while (true) {
            i6--;
            if (i6 < 0) {
                return perm6464((((jPerm32642 & (-1)) << 1) & 4042322160L) | (252645135 & (jPerm32642 >> 35)) | ((((j6 >> 35) & 252645135) | (((j6 & (-1)) << 1) & 4042322160L)) << 32), CF6464);
            }
            char c5 = 0;
            int i7 = 0;
            while (i7 < 8) {
                int i8 = i7 << 1;
                long j7 = i4;
                long j8 = ((jPerm32642 >> c4) ^ jPerm32642) & j7 & j5;
                long j9 = ((j8 | (j8 << c4)) ^ jPerm32642) ^ jArr[i8];
                long[][] jArr3 = SPE;
                long[] jArr4 = jArr3[c5];
                long j10 = jArr4[(int) ((j9 >> 58) & 63)];
                long[] jArr5 = jArr3[c3];
                long j11 = j10 ^ jArr5[(int) ((j9 >> 50) & 63)];
                long[] jArr6 = jArr3[2];
                long j12 = jArr6[(int) ((j9 >> 42) & 63)] ^ j11;
                long[] jArr7 = jArr3[3];
                long j13 = j6;
                long j14 = j12 ^ jArr7[(int) ((j9 >> 34) & 63)];
                long[] jArr8 = jArr3[4];
                int i9 = i7;
                long j15 = j14 ^ jArr8[(int) ((j9 >> 26) & 63)];
                long[] jArr9 = jArr3[5];
                long j16 = j15 ^ jArr9[(int) ((j9 >> 18) & 63)];
                long[] jArr10 = jArr3[6];
                long j17 = j16 ^ jArr10[(int) ((j9 >> 10) & 63)];
                long[] jArr11 = jArr3[7];
                long j18 = j13 ^ (j17 ^ jArr11[(int) ((j9 >> 2) & 63)]);
                long j19 = jArr[i8 + 1];
                long j20 = ((j18 >> 32) ^ j18) & j7 & InternalZipConstants.ZIP_64_LIMIT;
                long j21 = ((j20 | (j20 << 32)) ^ j18) ^ j19;
                jPerm32642 ^= ((((((jArr5[(int) ((j21 >> 50) & 63)] ^ jArr4[(int) ((j21 >> 58) & 63)]) ^ jArr6[(int) ((j21 >> 42) & 63)]) ^ jArr7[(int) ((j21 >> 34) & 63)]) ^ jArr8[(int) ((j21 >> 26) & 63)]) ^ jArr9[(int) ((j21 >> 18) & 63)]) ^ jArr10[(int) ((j21 >> 10) & 63)]) ^ jArr11[(int) ((j21 >> 2) & 63)];
                i7 = i9 + 1;
                j6 = j18;
                j5 = 4294967295L;
                c5 = 0;
                c3 = 1;
                c4 = ' ';
            }
            long j22 = j6 ^ jPerm32642;
            jPerm32642 ^= j22;
            j6 = j22 ^ jPerm32642;
            c3 = 1;
            c4 = ' ';
        }
    }

    private static long[] des_setkey(long j2) {
        long jPerm6464 = perm6464(j2, PC1ROT);
        long[] jArr = new long[16];
        jArr[0] = jPerm6464 & (-217020518463700993L);
        for (int i2 = 1; i2 < 16; i2++) {
            jArr[i2] = jPerm6464;
            jPerm6464 = perm6464(jPerm6464, PC2ROT[Rotates[i2] - 1]);
            jArr[i2] = jPerm6464 & (-217020518463700993L);
        }
        return jArr;
    }

    private static void init_perm(long[][] jArr, byte[] bArr, int i2) {
        for (int i3 = 0; i3 < i2 * 8; i3++) {
            int i4 = bArr[i3] - 1;
            if (i4 >= 0) {
                int i5 = i4 >> 2;
                int i6 = 1 << (i4 & 3);
                for (int i7 = 0; i7 < 16; i7++) {
                    int i8 = (i3 & 7) + ((7 - (i3 >> 3)) << 3);
                    if ((i7 & i6) != 0) {
                        long[] jArr2 = jArr[i5];
                        jArr2[i7] = jArr2[i7] | (1 << i8);
                    }
                }
            }
        }
    }

    public static void main(String[] strArr) {
        if (strArr.length != 2) {
            System.err.println("Usage - java org.eclipse.util.UnixCrypt <key> <salt>");
            System.exit(1);
        }
        System.err.println("Crypt=" + crypt(strArr[0], strArr[1]));
    }

    private static long perm3264(int i2, long[][] jArr) {
        long j2 = 0;
        int i3 = 4;
        while (true) {
            i3--;
            if (i3 < 0) {
                return j2;
            }
            int i4 = i2 & 255;
            i2 >>= 8;
            int i5 = i3 << 1;
            j2 = j2 | jArr[i5][i4 & 15] | jArr[i5 + 1][i4 >> 4];
        }
    }

    private static long perm6464(long j2, long[][] jArr) {
        long j3 = 0;
        int i2 = 8;
        while (true) {
            i2--;
            if (i2 < 0) {
                return j3;
            }
            int i3 = (int) (255 & j2);
            j2 >>= 8;
            int i4 = i2 << 1;
            j3 = j3 | jArr[i4][i3 & 15] | jArr[i4 + 1][i3 >> 4];
        }
    }

    private static int to_six_bit(int i2) {
        return ((i2 >> 16) & R2.attr.actionModeShareDrawable) | ((i2 << 26) & (-67108864)) | ((i2 << 12) & 16515072) | ((i2 >> 2) & 64512);
    }

    private static long to_six_bit(long j2) {
        return ((j2 >> 16) & 1082331758844L) | ((j2 << 26) & (-288230371923853312L)) | ((j2 << 12) & 70931694147600384L) | ((j2 >> 2) & 277076930264064L);
    }
}
