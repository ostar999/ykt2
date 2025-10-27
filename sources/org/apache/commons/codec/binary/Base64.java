package org.apache.commons.codec.binary;

import cn.hutool.core.text.StrPool;
import com.google.common.base.Ascii;
import java.math.BigInteger;
import okio.Utf8;
import org.apache.commons.compress.archivers.tar.TarConstants;
import org.eclipse.jetty.http.HttpTokens;

/* loaded from: classes9.dex */
public class Base64 extends BaseNCodec {
    private static final int BITS_PER_ENCODED_BYTE = 6;
    private static final int BYTES_PER_ENCODED_BLOCK = 4;
    private static final int BYTES_PER_UNENCODED_BLOCK = 3;
    private static final int MASK_6BITS = 63;
    private int bitWorkArea;
    private final int decodeSize;
    private final byte[] decodeTable;
    private final int encodeSize;
    private final byte[] encodeTable;
    private final byte[] lineSeparator;
    static final byte[] CHUNK_SEPARATOR = {13, 10};
    private static final byte[] STANDARD_ENCODE_TABLE = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, TarConstants.LF_GNUTYPE_LONGLINK, TarConstants.LF_GNUTYPE_LONGNAME, 77, 78, 79, 80, 81, 82, TarConstants.LF_GNUTYPE_SPARSE, 84, 85, 86, 87, TarConstants.LF_PAX_EXTENDED_HEADER_UC, 89, 90, 97, 98, 99, 100, 101, 102, TarConstants.LF_PAX_GLOBAL_EXTENDED_HEADER, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, TarConstants.LF_PAX_EXTENDED_HEADER_LC, 121, 122, TarConstants.LF_NORMAL, TarConstants.LF_LINK, TarConstants.LF_SYMLINK, TarConstants.LF_CHR, TarConstants.LF_BLK, TarConstants.LF_DIR, TarConstants.LF_FIFO, TarConstants.LF_CONTIG, 56, 57, 43, 47};
    private static final byte[] URL_SAFE_ENCODE_TABLE = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, TarConstants.LF_GNUTYPE_LONGLINK, TarConstants.LF_GNUTYPE_LONGNAME, 77, 78, 79, 80, 81, 82, TarConstants.LF_GNUTYPE_SPARSE, 84, 85, 86, 87, TarConstants.LF_PAX_EXTENDED_HEADER_UC, 89, 90, 97, 98, 99, 100, 101, 102, TarConstants.LF_PAX_GLOBAL_EXTENDED_HEADER, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, TarConstants.LF_PAX_EXTENDED_HEADER_LC, 121, 122, TarConstants.LF_NORMAL, TarConstants.LF_LINK, TarConstants.LF_SYMLINK, TarConstants.LF_CHR, TarConstants.LF_BLK, TarConstants.LF_DIR, TarConstants.LF_FIFO, TarConstants.LF_CONTIG, 56, 57, 45, 95};
    private static final byte[] DECODE_TABLE = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, 62, -1, Utf8.REPLACEMENT_BYTE, TarConstants.LF_BLK, TarConstants.LF_DIR, TarConstants.LF_FIFO, TarConstants.LF_CONTIG, 56, 57, HttpTokens.COLON, HttpTokens.SEMI_COLON, 60, kotlin.io.encoding.Base64.padSymbol, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, Ascii.DC2, 19, Ascii.DC4, 21, 22, 23, Ascii.CAN, Ascii.EM, -1, -1, -1, -1, Utf8.REPLACEMENT_BYTE, -1, Ascii.SUB, Ascii.ESC, Ascii.FS, Ascii.GS, Ascii.RS, Ascii.US, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, TarConstants.LF_NORMAL, TarConstants.LF_LINK, TarConstants.LF_SYMLINK, TarConstants.LF_CHR};

    public Base64() {
        this(0);
    }

    public static byte[] decodeBase64(String str) {
        return new Base64().decode(str);
    }

    public static BigInteger decodeInteger(byte[] bArr) {
        return new BigInteger(1, decodeBase64(bArr));
    }

    public static byte[] encodeBase64(byte[] bArr) {
        return encodeBase64(bArr, false);
    }

    public static byte[] encodeBase64Chunked(byte[] bArr) {
        return encodeBase64(bArr, true);
    }

    public static String encodeBase64String(byte[] bArr) {
        return StringUtils.newStringUtf8(encodeBase64(bArr, false));
    }

    public static byte[] encodeBase64URLSafe(byte[] bArr) {
        return encodeBase64(bArr, false, true);
    }

    public static String encodeBase64URLSafeString(byte[] bArr) {
        return StringUtils.newStringUtf8(encodeBase64(bArr, false, true));
    }

    public static byte[] encodeInteger(BigInteger bigInteger) {
        if (bigInteger != null) {
            return encodeBase64(toIntegerBytes(bigInteger), false);
        }
        throw new NullPointerException("encodeInteger called with null parameter");
    }

    public static boolean isArrayByteBase64(byte[] bArr) {
        return isBase64(bArr);
    }

    public static boolean isBase64(byte b3) {
        if (b3 != 61) {
            if (b3 >= 0) {
                byte[] bArr = DECODE_TABLE;
                if (b3 >= bArr.length || bArr[b3] == -1) {
                }
            }
            return false;
        }
        return true;
    }

    public static byte[] toIntegerBytes(BigInteger bigInteger) {
        int iBitLength = ((bigInteger.bitLength() + 7) >> 3) << 3;
        byte[] byteArray = bigInteger.toByteArray();
        int i2 = 1;
        if (bigInteger.bitLength() % 8 != 0 && (bigInteger.bitLength() / 8) + 1 == iBitLength / 8) {
            return byteArray;
        }
        int length = byteArray.length;
        if (bigInteger.bitLength() % 8 == 0) {
            length--;
        } else {
            i2 = 0;
        }
        int i3 = iBitLength / 8;
        int i4 = i3 - length;
        byte[] bArr = new byte[i3];
        System.arraycopy(byteArray, i2, bArr, i4, length);
        return bArr;
    }

    @Override // org.apache.commons.codec.binary.BaseNCodec
    public void decode(byte[] bArr, int i2, int i3) {
        byte b3;
        if (this.eof) {
            return;
        }
        if (i3 < 0) {
            this.eof = true;
        }
        int i4 = 0;
        while (true) {
            if (i4 >= i3) {
                break;
            }
            ensureBufferSize(this.decodeSize);
            int i5 = i2 + 1;
            byte b4 = bArr[i2];
            if (b4 == 61) {
                this.eof = true;
                break;
            }
            if (b4 >= 0) {
                byte[] bArr2 = DECODE_TABLE;
                if (b4 < bArr2.length && (b3 = bArr2[b4]) >= 0) {
                    int i6 = (this.modulus + 1) % 4;
                    this.modulus = i6;
                    int i7 = (this.bitWorkArea << 6) + b3;
                    this.bitWorkArea = i7;
                    if (i6 == 0) {
                        byte[] bArr3 = this.buffer;
                        int i8 = this.pos;
                        int i9 = i8 + 1;
                        bArr3[i8] = (byte) ((i7 >> 16) & 255);
                        int i10 = i9 + 1;
                        bArr3[i9] = (byte) ((i7 >> 8) & 255);
                        this.pos = i10 + 1;
                        bArr3[i10] = (byte) (i7 & 255);
                    }
                }
            }
            i4++;
            i2 = i5;
        }
        if (!this.eof || this.modulus == 0) {
            return;
        }
        ensureBufferSize(this.decodeSize);
        int i11 = this.modulus;
        if (i11 == 2) {
            int i12 = this.bitWorkArea >> 4;
            this.bitWorkArea = i12;
            byte[] bArr4 = this.buffer;
            int i13 = this.pos;
            this.pos = i13 + 1;
            bArr4[i13] = (byte) (i12 & 255);
            return;
        }
        if (i11 != 3) {
            return;
        }
        int i14 = this.bitWorkArea >> 2;
        this.bitWorkArea = i14;
        byte[] bArr5 = this.buffer;
        int i15 = this.pos;
        int i16 = i15 + 1;
        bArr5[i15] = (byte) ((i14 >> 8) & 255);
        this.pos = i16 + 1;
        bArr5[i16] = (byte) (i14 & 255);
    }

    @Override // org.apache.commons.codec.binary.BaseNCodec
    public void encode(byte[] bArr, int i2, int i3) {
        if (this.eof) {
            return;
        }
        if (i3 >= 0) {
            int i4 = 0;
            while (i4 < i3) {
                ensureBufferSize(this.encodeSize);
                int i5 = (this.modulus + 1) % 3;
                this.modulus = i5;
                int i6 = i2 + 1;
                int i7 = bArr[i2];
                if (i7 < 0) {
                    i7 += 256;
                }
                int i8 = (this.bitWorkArea << 8) + i7;
                this.bitWorkArea = i8;
                if (i5 == 0) {
                    byte[] bArr2 = this.buffer;
                    int i9 = this.pos;
                    int i10 = i9 + 1;
                    byte[] bArr3 = this.encodeTable;
                    bArr2[i9] = bArr3[(i8 >> 18) & 63];
                    int i11 = i10 + 1;
                    bArr2[i10] = bArr3[(i8 >> 12) & 63];
                    int i12 = i11 + 1;
                    bArr2[i11] = bArr3[(i8 >> 6) & 63];
                    int i13 = i12 + 1;
                    this.pos = i13;
                    bArr2[i12] = bArr3[i8 & 63];
                    int i14 = this.currentLinePos + 4;
                    this.currentLinePos = i14;
                    int i15 = this.lineLength;
                    if (i15 > 0 && i15 <= i14) {
                        byte[] bArr4 = this.lineSeparator;
                        System.arraycopy(bArr4, 0, bArr2, i13, bArr4.length);
                        this.pos += this.lineSeparator.length;
                        this.currentLinePos = 0;
                    }
                }
                i4++;
                i2 = i6;
            }
            return;
        }
        this.eof = true;
        if (this.modulus == 0 && this.lineLength == 0) {
            return;
        }
        ensureBufferSize(this.encodeSize);
        int i16 = this.pos;
        int i17 = this.modulus;
        if (i17 == 1) {
            byte[] bArr5 = this.buffer;
            int i18 = i16 + 1;
            byte[] bArr6 = this.encodeTable;
            int i19 = this.bitWorkArea;
            bArr5[i16] = bArr6[(i19 >> 2) & 63];
            int i20 = i18 + 1;
            this.pos = i20;
            bArr5[i18] = bArr6[(i19 << 4) & 63];
            if (bArr6 == STANDARD_ENCODE_TABLE) {
                int i21 = i20 + 1;
                bArr5[i20] = kotlin.io.encoding.Base64.padSymbol;
                this.pos = i21 + 1;
                bArr5[i21] = kotlin.io.encoding.Base64.padSymbol;
            }
        } else if (i17 == 2) {
            byte[] bArr7 = this.buffer;
            int i22 = i16 + 1;
            byte[] bArr8 = this.encodeTable;
            int i23 = this.bitWorkArea;
            bArr7[i16] = bArr8[(i23 >> 10) & 63];
            int i24 = i22 + 1;
            bArr7[i22] = bArr8[(i23 >> 4) & 63];
            int i25 = i24 + 1;
            this.pos = i25;
            bArr7[i24] = bArr8[(i23 << 2) & 63];
            if (bArr8 == STANDARD_ENCODE_TABLE) {
                this.pos = i25 + 1;
                bArr7[i25] = kotlin.io.encoding.Base64.padSymbol;
            }
        }
        int i26 = this.currentLinePos;
        int i27 = this.pos;
        int i28 = i26 + (i27 - i16);
        this.currentLinePos = i28;
        if (this.lineLength <= 0 || i28 <= 0) {
            return;
        }
        byte[] bArr9 = this.lineSeparator;
        System.arraycopy(bArr9, 0, this.buffer, i27, bArr9.length);
        this.pos += this.lineSeparator.length;
    }

    @Override // org.apache.commons.codec.binary.BaseNCodec
    public boolean isInAlphabet(byte b3) {
        if (b3 >= 0) {
            byte[] bArr = this.decodeTable;
            if (b3 < bArr.length && bArr[b3] != -1) {
                return true;
            }
        }
        return false;
    }

    public boolean isUrlSafe() {
        return this.encodeTable == URL_SAFE_ENCODE_TABLE;
    }

    public Base64(boolean z2) {
        this(76, CHUNK_SEPARATOR, z2);
    }

    public static byte[] decodeBase64(byte[] bArr) {
        return new Base64().decode(bArr);
    }

    public static byte[] encodeBase64(byte[] bArr, boolean z2) {
        return encodeBase64(bArr, z2, false);
    }

    public static boolean isBase64(String str) {
        return isBase64(StringUtils.getBytesUtf8(str));
    }

    public Base64(int i2) {
        this(i2, CHUNK_SEPARATOR);
    }

    public static byte[] encodeBase64(byte[] bArr, boolean z2, boolean z3) {
        return encodeBase64(bArr, z2, z3, Integer.MAX_VALUE);
    }

    public static boolean isBase64(byte[] bArr) {
        for (int i2 = 0; i2 < bArr.length; i2++) {
            if (!isBase64(bArr[i2]) && !BaseNCodec.isWhiteSpace(bArr[i2])) {
                return false;
            }
        }
        return true;
    }

    public Base64(int i2, byte[] bArr) {
        this(i2, bArr, false);
    }

    public static byte[] encodeBase64(byte[] bArr, boolean z2, boolean z3, int i2) {
        if (bArr == null || bArr.length == 0) {
            return bArr;
        }
        Base64 base64 = z2 ? new Base64(z3) : new Base64(0, CHUNK_SEPARATOR, z3);
        long encodedLength = base64.getEncodedLength(bArr);
        if (encodedLength <= i2) {
            return base64.encode(bArr);
        }
        throw new IllegalArgumentException("Input array too big, the output array would be bigger (" + encodedLength + ") than the specified maximum size of " + i2);
    }

    public Base64(int i2, byte[] bArr, boolean z2) {
        super(3, 4, i2, bArr == null ? 0 : bArr.length);
        this.decodeTable = DECODE_TABLE;
        if (bArr != null) {
            if (containsAlphabetOrPad(bArr)) {
                throw new IllegalArgumentException("lineSeparator must not contain base64 characters: [" + StringUtils.newStringUtf8(bArr) + StrPool.BRACKET_END);
            }
            if (i2 > 0) {
                this.encodeSize = bArr.length + 4;
                byte[] bArr2 = new byte[bArr.length];
                this.lineSeparator = bArr2;
                System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
            } else {
                this.encodeSize = 4;
                this.lineSeparator = null;
            }
        } else {
            this.encodeSize = 4;
            this.lineSeparator = null;
        }
        this.decodeSize = this.encodeSize - 1;
        this.encodeTable = z2 ? URL_SAFE_ENCODE_TABLE : STANDARD_ENCODE_TABLE;
    }
}
