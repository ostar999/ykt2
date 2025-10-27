package org.apache.commons.codec.binary;

import cn.hutool.core.text.StrPool;
import com.google.common.base.Ascii;
import okio.Utf8;
import org.apache.commons.compress.archivers.tar.TarConstants;

/* loaded from: classes9.dex */
public class Base32 extends BaseNCodec {
    private static final int BITS_PER_ENCODED_BYTE = 5;
    private static final int BYTES_PER_ENCODED_BLOCK = 8;
    private static final int BYTES_PER_UNENCODED_BLOCK = 5;
    private static final byte[] CHUNK_SEPARATOR = {13, 10};
    private static final byte[] DECODE_TABLE = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, Utf8.REPLACEMENT_BYTE, -1, -1, Ascii.SUB, Ascii.ESC, Ascii.FS, Ascii.GS, Ascii.RS, Ascii.US, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, Ascii.DC2, 19, Ascii.DC4, 21, 22, 23, Ascii.CAN, Ascii.EM};
    private static final byte[] ENCODE_TABLE = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, TarConstants.LF_GNUTYPE_LONGLINK, TarConstants.LF_GNUTYPE_LONGNAME, 77, 78, 79, 80, 81, 82, TarConstants.LF_GNUTYPE_SPARSE, 84, 85, 86, 87, TarConstants.LF_PAX_EXTENDED_HEADER_UC, 89, 90, TarConstants.LF_SYMLINK, TarConstants.LF_CHR, TarConstants.LF_BLK, TarConstants.LF_DIR, TarConstants.LF_FIFO, TarConstants.LF_CONTIG};
    private static final byte[] HEX_DECODE_TABLE = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, Utf8.REPLACEMENT_BYTE, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, -1, -1, -1, -1, -1, -1, -1, 10, 11, 12, 13, 14, 15, 16, 17, Ascii.DC2, 19, Ascii.DC4, 21, 22, 23, Ascii.CAN, Ascii.EM, Ascii.SUB, Ascii.ESC, Ascii.FS, Ascii.GS, Ascii.RS, Ascii.US, 32};
    private static final byte[] HEX_ENCODE_TABLE = {TarConstants.LF_NORMAL, TarConstants.LF_LINK, TarConstants.LF_SYMLINK, TarConstants.LF_CHR, TarConstants.LF_BLK, TarConstants.LF_DIR, TarConstants.LF_FIFO, TarConstants.LF_CONTIG, 56, 57, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, TarConstants.LF_GNUTYPE_LONGLINK, TarConstants.LF_GNUTYPE_LONGNAME, 77, 78, 79, 80, 81, 82, TarConstants.LF_GNUTYPE_SPARSE, 84, 85, 86};
    private static final int MASK_5BITS = 31;
    private long bitWorkArea;
    private final int decodeSize;
    private final byte[] decodeTable;
    private final int encodeSize;
    private final byte[] encodeTable;
    private final byte[] lineSeparator;

    public Base32() {
        this(false);
    }

    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v2, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r2v7 */
    @Override // org.apache.commons.codec.binary.BaseNCodec
    public void decode(byte[] bArr, int i2, int i3) {
        byte b3;
        if (this.eof) {
        }
        ?? r2 = 1;
        if (i3 < 0) {
            this.eof = true;
        }
        int i4 = 0;
        int i5 = i2;
        while (true) {
            if (i4 >= i3) {
                break;
            }
            int i6 = i5 + 1;
            byte b4 = bArr[i5];
            if (b4 == 61) {
                this.eof = r2;
                break;
            }
            ensureBufferSize(this.decodeSize);
            if (b4 >= 0) {
                byte[] bArr2 = this.decodeTable;
                if (b4 < bArr2.length && (b3 = bArr2[b4]) >= 0) {
                    int i7 = (this.modulus + r2) % 8;
                    this.modulus = i7;
                    this.bitWorkArea = (this.bitWorkArea << 5) + b3;
                    if (i7 == 0) {
                        byte[] bArr3 = this.buffer;
                        int i8 = this.pos;
                        int i9 = i8 + 1;
                        bArr3[i8] = (byte) ((r12 >> 32) & 255);
                        int i10 = i9 + 1;
                        bArr3[i9] = (byte) ((r12 >> 24) & 255);
                        int i11 = i10 + 1;
                        bArr3[i10] = (byte) ((r12 >> 16) & 255);
                        int i12 = i11 + 1;
                        bArr3[i11] = (byte) ((r12 >> 8) & 255);
                        this.pos = i12 + 1;
                        bArr3[i12] = (byte) (r12 & 255);
                    }
                }
            }
            i4++;
            i5 = i6;
            r2 = 1;
        }
        if (!this.eof || this.modulus < 2) {
            return;
        }
        ensureBufferSize(this.decodeSize);
        switch (this.modulus) {
            case 2:
                byte[] bArr4 = this.buffer;
                int i13 = this.pos;
                this.pos = i13 + 1;
                bArr4[i13] = (byte) ((this.bitWorkArea >> 2) & 255);
                break;
            case 3:
                byte[] bArr5 = this.buffer;
                int i14 = this.pos;
                this.pos = i14 + 1;
                bArr5[i14] = (byte) ((this.bitWorkArea >> 7) & 255);
                break;
            case 4:
                this.bitWorkArea = this.bitWorkArea >> 4;
                byte[] bArr6 = this.buffer;
                int i15 = this.pos;
                int i16 = i15 + 1;
                bArr6[i15] = (byte) ((r1 >> 8) & 255);
                this.pos = i16 + 1;
                bArr6[i16] = (byte) (r1 & 255);
                break;
            case 5:
                this.bitWorkArea = this.bitWorkArea >> 1;
                byte[] bArr7 = this.buffer;
                int i17 = this.pos;
                int i18 = i17 + 1;
                bArr7[i17] = (byte) ((r1 >> 16) & 255);
                int i19 = i18 + 1;
                bArr7[i18] = (byte) ((r1 >> 8) & 255);
                this.pos = i19 + 1;
                bArr7[i19] = (byte) (r1 & 255);
                break;
            case 6:
                this.bitWorkArea = this.bitWorkArea >> 6;
                byte[] bArr8 = this.buffer;
                int i20 = this.pos;
                int i21 = i20 + 1;
                bArr8[i20] = (byte) ((r1 >> 16) & 255);
                int i22 = i21 + 1;
                bArr8[i21] = (byte) ((r1 >> 8) & 255);
                this.pos = i22 + 1;
                bArr8[i22] = (byte) (r1 & 255);
                break;
            case 7:
                this.bitWorkArea = this.bitWorkArea >> 3;
                byte[] bArr9 = this.buffer;
                int i23 = this.pos;
                int i24 = i23 + 1;
                bArr9[i23] = (byte) ((r1 >> 24) & 255);
                int i25 = i24 + 1;
                bArr9[i24] = (byte) ((r1 >> 16) & 255);
                int i26 = i25 + 1;
                bArr9[i25] = (byte) ((r1 >> 8) & 255);
                this.pos = i26 + 1;
                bArr9[i26] = (byte) (r1 & 255);
                break;
        }
    }

    @Override // org.apache.commons.codec.binary.BaseNCodec
    public void encode(byte[] bArr, int i2, int i3) {
        if (this.eof) {
            return;
        }
        if (i3 >= 0) {
            int i4 = i2;
            int i5 = 0;
            while (i5 < i3) {
                ensureBufferSize(this.encodeSize);
                int i6 = (this.modulus + 1) % 5;
                this.modulus = i6;
                int i7 = i4 + 1;
                int i8 = bArr[i4];
                if (i8 < 0) {
                    i8 += 256;
                }
                long j2 = (this.bitWorkArea << 8) + i8;
                this.bitWorkArea = j2;
                if (i6 == 0) {
                    byte[] bArr2 = this.buffer;
                    int i9 = this.pos;
                    int i10 = i9 + 1;
                    byte[] bArr3 = this.encodeTable;
                    bArr2[i9] = bArr3[((int) (j2 >> 35)) & 31];
                    int i11 = i10 + 1;
                    bArr2[i10] = bArr3[((int) (j2 >> 30)) & 31];
                    int i12 = i11 + 1;
                    bArr2[i11] = bArr3[((int) (j2 >> 25)) & 31];
                    int i13 = i12 + 1;
                    bArr2[i12] = bArr3[((int) (j2 >> 20)) & 31];
                    int i14 = i13 + 1;
                    bArr2[i13] = bArr3[((int) (j2 >> 15)) & 31];
                    int i15 = i14 + 1;
                    bArr2[i14] = bArr3[((int) (j2 >> 10)) & 31];
                    int i16 = i15 + 1;
                    bArr2[i15] = bArr3[((int) (j2 >> 5)) & 31];
                    int i17 = i16 + 1;
                    this.pos = i17;
                    bArr2[i16] = bArr3[((int) j2) & 31];
                    int i18 = this.currentLinePos + 8;
                    this.currentLinePos = i18;
                    int i19 = this.lineLength;
                    if (i19 > 0 && i19 <= i18) {
                        byte[] bArr4 = this.lineSeparator;
                        System.arraycopy(bArr4, 0, bArr2, i17, bArr4.length);
                        this.pos += this.lineSeparator.length;
                        this.currentLinePos = 0;
                    }
                }
                i5++;
                i4 = i7;
            }
            return;
        }
        this.eof = true;
        if (this.modulus == 0 && this.lineLength == 0) {
            return;
        }
        ensureBufferSize(this.encodeSize);
        int i20 = this.pos;
        int i21 = this.modulus;
        if (i21 == 1) {
            byte[] bArr5 = this.buffer;
            int i22 = i20 + 1;
            byte[] bArr6 = this.encodeTable;
            long j3 = this.bitWorkArea;
            bArr5[i20] = bArr6[((int) (j3 >> 3)) & 31];
            int i23 = i22 + 1;
            bArr5[i22] = bArr6[((int) (j3 << 2)) & 31];
            int i24 = i23 + 1;
            bArr5[i23] = kotlin.io.encoding.Base64.padSymbol;
            int i25 = i24 + 1;
            bArr5[i24] = kotlin.io.encoding.Base64.padSymbol;
            int i26 = i25 + 1;
            bArr5[i25] = kotlin.io.encoding.Base64.padSymbol;
            int i27 = i26 + 1;
            bArr5[i26] = kotlin.io.encoding.Base64.padSymbol;
            int i28 = i27 + 1;
            bArr5[i27] = kotlin.io.encoding.Base64.padSymbol;
            this.pos = i28 + 1;
            bArr5[i28] = kotlin.io.encoding.Base64.padSymbol;
        } else if (i21 == 2) {
            byte[] bArr7 = this.buffer;
            int i29 = i20 + 1;
            byte[] bArr8 = this.encodeTable;
            long j4 = this.bitWorkArea;
            bArr7[i20] = bArr8[((int) (j4 >> 11)) & 31];
            int i30 = i29 + 1;
            bArr7[i29] = bArr8[((int) (j4 >> 6)) & 31];
            int i31 = i30 + 1;
            bArr7[i30] = bArr8[((int) (j4 >> 1)) & 31];
            int i32 = i31 + 1;
            bArr7[i31] = bArr8[((int) (j4 << 4)) & 31];
            int i33 = i32 + 1;
            bArr7[i32] = kotlin.io.encoding.Base64.padSymbol;
            int i34 = i33 + 1;
            bArr7[i33] = kotlin.io.encoding.Base64.padSymbol;
            int i35 = i34 + 1;
            bArr7[i34] = kotlin.io.encoding.Base64.padSymbol;
            this.pos = i35 + 1;
            bArr7[i35] = kotlin.io.encoding.Base64.padSymbol;
        } else if (i21 == 3) {
            byte[] bArr9 = this.buffer;
            int i36 = i20 + 1;
            byte[] bArr10 = this.encodeTable;
            long j5 = this.bitWorkArea;
            bArr9[i20] = bArr10[((int) (j5 >> 19)) & 31];
            int i37 = i36 + 1;
            bArr9[i36] = bArr10[((int) (j5 >> 14)) & 31];
            int i38 = i37 + 1;
            bArr9[i37] = bArr10[((int) (j5 >> 9)) & 31];
            int i39 = i38 + 1;
            bArr9[i38] = bArr10[((int) (j5 >> 4)) & 31];
            int i40 = i39 + 1;
            bArr9[i39] = bArr10[((int) (j5 << 1)) & 31];
            int i41 = i40 + 1;
            bArr9[i40] = kotlin.io.encoding.Base64.padSymbol;
            int i42 = i41 + 1;
            bArr9[i41] = kotlin.io.encoding.Base64.padSymbol;
            this.pos = i42 + 1;
            bArr9[i42] = kotlin.io.encoding.Base64.padSymbol;
        } else if (i21 == 4) {
            byte[] bArr11 = this.buffer;
            int i43 = i20 + 1;
            byte[] bArr12 = this.encodeTable;
            long j6 = this.bitWorkArea;
            bArr11[i20] = bArr12[((int) (j6 >> 27)) & 31];
            int i44 = i43 + 1;
            bArr11[i43] = bArr12[((int) (j6 >> 22)) & 31];
            int i45 = i44 + 1;
            bArr11[i44] = bArr12[((int) (j6 >> 17)) & 31];
            int i46 = i45 + 1;
            bArr11[i45] = bArr12[((int) (j6 >> 12)) & 31];
            int i47 = i46 + 1;
            bArr11[i46] = bArr12[((int) (j6 >> 7)) & 31];
            int i48 = i47 + 1;
            bArr11[i47] = bArr12[((int) (j6 >> 2)) & 31];
            int i49 = i48 + 1;
            bArr11[i48] = bArr12[((int) (j6 << 3)) & 31];
            this.pos = i49 + 1;
            bArr11[i49] = kotlin.io.encoding.Base64.padSymbol;
        }
        int i50 = this.currentLinePos;
        int i51 = this.pos;
        int i52 = i50 + (i51 - i20);
        this.currentLinePos = i52;
        if (this.lineLength <= 0 || i52 <= 0) {
            return;
        }
        byte[] bArr13 = this.lineSeparator;
        System.arraycopy(bArr13, 0, this.buffer, i51, bArr13.length);
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

    public Base32(boolean z2) {
        this(0, null, z2);
    }

    public Base32(int i2) {
        this(i2, CHUNK_SEPARATOR);
    }

    public Base32(int i2, byte[] bArr) {
        this(i2, bArr, false);
    }

    public Base32(int i2, byte[] bArr, boolean z2) {
        super(5, 8, i2, bArr == null ? 0 : bArr.length);
        if (z2) {
            this.encodeTable = HEX_ENCODE_TABLE;
            this.decodeTable = HEX_DECODE_TABLE;
        } else {
            this.encodeTable = ENCODE_TABLE;
            this.decodeTable = DECODE_TABLE;
        }
        if (i2 <= 0) {
            this.encodeSize = 8;
            this.lineSeparator = null;
        } else if (bArr != null) {
            if (!containsAlphabetOrPad(bArr)) {
                this.encodeSize = bArr.length + 8;
                byte[] bArr2 = new byte[bArr.length];
                this.lineSeparator = bArr2;
                System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
            } else {
                throw new IllegalArgumentException("lineSeparator must not contain Base32 characters: [" + StringUtils.newStringUtf8(bArr) + StrPool.BRACKET_END);
            }
        } else {
            throw new IllegalArgumentException("lineLength " + i2 + " > 0, but lineSeparator is null");
        }
        this.decodeSize = this.encodeSize - 1;
    }
}
