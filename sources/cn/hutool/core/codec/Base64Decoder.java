package cn.hutool.core.codec;

import cn.hutool.core.lang.mutable.MutableInt;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.PrimitiveArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.base.Ascii;
import java.nio.charset.Charset;
import okio.Utf8;
import org.apache.commons.compress.archivers.tar.TarConstants;
import org.eclipse.jetty.http.HttpTokens;

/* loaded from: classes.dex */
public class Base64Decoder {
    private static final byte PADDING = -2;
    private static final Charset DEFAULT_CHARSET = CharsetUtil.CHARSET_UTF_8;
    private static final byte[] DECODE_TABLE = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, 62, -1, Utf8.REPLACEMENT_BYTE, TarConstants.LF_BLK, TarConstants.LF_DIR, TarConstants.LF_FIFO, TarConstants.LF_CONTIG, 56, 57, HttpTokens.COLON, HttpTokens.SEMI_COLON, 60, kotlin.io.encoding.Base64.padSymbol, -1, -1, -1, -2, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, Ascii.DC2, 19, Ascii.DC4, 21, 22, 23, Ascii.CAN, Ascii.EM, -1, -1, -1, -1, Utf8.REPLACEMENT_BYTE, -1, Ascii.SUB, Ascii.ESC, Ascii.FS, Ascii.GS, Ascii.RS, Ascii.US, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, TarConstants.LF_NORMAL, TarConstants.LF_LINK, TarConstants.LF_SYMLINK, TarConstants.LF_CHR};

    public static byte[] decode(CharSequence charSequence) {
        return decode(CharSequenceUtil.bytes(charSequence, DEFAULT_CHARSET));
    }

    public static String decodeStr(CharSequence charSequence) {
        return decodeStr(charSequence, DEFAULT_CHARSET);
    }

    private static byte getNextValidDecodeByte(byte[] bArr, MutableInt mutableInt, int i2) {
        byte b3;
        while (mutableInt.intValue() <= i2) {
            byte b4 = bArr[mutableInt.intValue()];
            mutableInt.increment();
            if (b4 > -1 && (b3 = DECODE_TABLE[b4]) > -1) {
                return b3;
            }
        }
        return (byte) -2;
    }

    public static boolean isBase64Code(byte b3) {
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

    public static byte[] decode(byte[] bArr) {
        return PrimitiveArrayUtil.isEmpty(bArr) ? bArr : decode(bArr, 0, bArr.length);
    }

    public static String decodeStr(CharSequence charSequence, Charset charset) {
        return StrUtil.str(decode(charSequence), charset);
    }

    public static byte[] decode(byte[] bArr, int i2, int i3) {
        if (PrimitiveArrayUtil.isEmpty(bArr)) {
            return bArr;
        }
        MutableInt mutableInt = new MutableInt(i2);
        int i4 = (i2 + i3) - 1;
        int i5 = (i3 * 3) / 4;
        byte[] bArr2 = new byte[i5];
        int i6 = 0;
        while (mutableInt.intValue() <= i4) {
            byte nextValidDecodeByte = getNextValidDecodeByte(bArr, mutableInt, i4);
            byte nextValidDecodeByte2 = getNextValidDecodeByte(bArr, mutableInt, i4);
            byte nextValidDecodeByte3 = getNextValidDecodeByte(bArr, mutableInt, i4);
            byte nextValidDecodeByte4 = getNextValidDecodeByte(bArr, mutableInt, i4);
            if (-2 != nextValidDecodeByte2) {
                bArr2[i6] = (byte) ((nextValidDecodeByte << 2) | (nextValidDecodeByte2 >>> 4));
                i6++;
            }
            if (-2 != nextValidDecodeByte3) {
                bArr2[i6] = (byte) (((nextValidDecodeByte2 & 15) << 4) | (nextValidDecodeByte3 >>> 2));
                i6++;
            }
            if (-2 != nextValidDecodeByte4) {
                bArr2[i6] = (byte) (((nextValidDecodeByte3 & 3) << 6) | nextValidDecodeByte4);
                i6++;
            }
        }
        return i6 == i5 ? bArr2 : (byte[]) ArrayUtil.copy(bArr2, new byte[i6], i6);
    }
}
