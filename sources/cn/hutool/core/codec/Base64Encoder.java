package cn.hutool.core.codec;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import java.nio.charset.Charset;
import org.apache.commons.compress.archivers.tar.TarConstants;

/* loaded from: classes.dex */
public class Base64Encoder {
    private static final Charset DEFAULT_CHARSET = CharsetUtil.CHARSET_UTF_8;
    private static final byte[] STANDARD_ENCODE_TABLE = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, TarConstants.LF_GNUTYPE_LONGLINK, TarConstants.LF_GNUTYPE_LONGNAME, 77, 78, 79, 80, 81, 82, TarConstants.LF_GNUTYPE_SPARSE, 84, 85, 86, 87, TarConstants.LF_PAX_EXTENDED_HEADER_UC, 89, 90, 97, 98, 99, 100, 101, 102, TarConstants.LF_PAX_GLOBAL_EXTENDED_HEADER, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, TarConstants.LF_PAX_EXTENDED_HEADER_LC, 121, 122, TarConstants.LF_NORMAL, TarConstants.LF_LINK, TarConstants.LF_SYMLINK, TarConstants.LF_CHR, TarConstants.LF_BLK, TarConstants.LF_DIR, TarConstants.LF_FIFO, TarConstants.LF_CONTIG, 56, 57, 43, 47};
    private static final byte[] URL_SAFE_ENCODE_TABLE = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, TarConstants.LF_GNUTYPE_LONGLINK, TarConstants.LF_GNUTYPE_LONGNAME, 77, 78, 79, 80, 81, 82, TarConstants.LF_GNUTYPE_SPARSE, 84, 85, 86, 87, TarConstants.LF_PAX_EXTENDED_HEADER_UC, 89, 90, 97, 98, 99, 100, 101, 102, TarConstants.LF_PAX_GLOBAL_EXTENDED_HEADER, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, TarConstants.LF_PAX_EXTENDED_HEADER_LC, 121, 122, TarConstants.LF_NORMAL, TarConstants.LF_LINK, TarConstants.LF_SYMLINK, TarConstants.LF_CHR, TarConstants.LF_BLK, TarConstants.LF_DIR, TarConstants.LF_FIFO, TarConstants.LF_CONTIG, 56, 57, 45, 95};

    public static byte[] encode(byte[] bArr, boolean z2) {
        return encode(bArr, z2, false);
    }

    public static String encodeStr(byte[] bArr, boolean z2, boolean z3) {
        return StrUtil.str(encode(bArr, z2, z3), DEFAULT_CHARSET);
    }

    public static byte[] encodeUrlSafe(byte[] bArr, boolean z2) {
        return encode(bArr, z2, true);
    }

    public static String encode(CharSequence charSequence) {
        return encode(charSequence, DEFAULT_CHARSET);
    }

    public static String encodeUrlSafe(CharSequence charSequence) {
        return encodeUrlSafe(charSequence, DEFAULT_CHARSET);
    }

    public static String encode(CharSequence charSequence, Charset charset) {
        return encode(CharSequenceUtil.bytes(charSequence, charset));
    }

    public static String encodeUrlSafe(CharSequence charSequence, Charset charset) {
        return encodeUrlSafe(CharSequenceUtil.bytes(charSequence, charset));
    }

    public static String encode(byte[] bArr) {
        return StrUtil.str(encode(bArr, false), DEFAULT_CHARSET);
    }

    public static String encodeUrlSafe(byte[] bArr) {
        return StrUtil.str(encodeUrlSafe(bArr, false), DEFAULT_CHARSET);
    }

    public static byte[] encode(byte[] bArr, boolean z2, boolean z3) {
        if (bArr == null) {
            return null;
        }
        int length = bArr.length;
        if (length == 0) {
            return new byte[0];
        }
        int i2 = (length / 3) * 3;
        int i3 = length - 1;
        int i4 = ((i3 / 3) + 1) << 2;
        int i5 = i4 + (z2 ? ((i4 - 1) / 76) << 1 : 0);
        byte[] bArr2 = new byte[i5];
        byte[] bArr3 = z3 ? URL_SAFE_ENCODE_TABLE : STANDARD_ENCODE_TABLE;
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        while (i6 < i2) {
            int i9 = i6 + 1;
            int i10 = i9 + 1;
            int i11 = ((bArr[i6] & 255) << 16) | ((bArr[i9] & 255) << 8);
            int i12 = i10 + 1;
            int i13 = i11 | (bArr[i10] & 255);
            int i14 = i7 + 1;
            bArr2[i7] = bArr3[(i13 >>> 18) & 63];
            int i15 = i14 + 1;
            bArr2[i14] = bArr3[(i13 >>> 12) & 63];
            int i16 = i15 + 1;
            bArr2[i15] = bArr3[(i13 >>> 6) & 63];
            i7 = i16 + 1;
            bArr2[i16] = bArr3[i13 & 63];
            if (z2 && (i8 = i8 + 1) == 19 && i7 < i5 - 2) {
                int i17 = i7 + 1;
                bArr2[i7] = 13;
                i7 = i17 + 1;
                bArr2[i17] = 10;
                i8 = 0;
            }
            i6 = i12;
        }
        int i18 = length - i2;
        if (i18 > 0) {
            int i19 = (i18 == 2 ? (bArr[i3] & 255) << 2 : 0) | ((bArr[i2] & 255) << 10);
            bArr2[i5 - 4] = bArr3[i19 >> 12];
            bArr2[i5 - 3] = bArr3[(i19 >>> 6) & 63];
            if (z3) {
                int i20 = i5 - 2;
                if (2 == i18) {
                    bArr2[i20] = bArr3[i19 & 63];
                    i20++;
                }
                byte[] bArr4 = new byte[i20];
                System.arraycopy(bArr2, 0, bArr4, 0, i20);
                return bArr4;
            }
            bArr2[i5 - 2] = i18 == 2 ? bArr3[i19 & 63] : (byte) 61;
            bArr2[i5 - 1] = kotlin.io.encoding.Base64.padSymbol;
        }
        return bArr2;
    }
}
