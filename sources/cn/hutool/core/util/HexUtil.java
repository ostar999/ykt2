package cn.hutool.core.util;

import cn.hutool.core.codec.Base16Codec;
import cn.hutool.core.text.CharPool;
import cn.hutool.core.text.CharSequenceUtil;
import com.github.liuyueyi.quick.transfer.dictionary.DictionaryFactory;
import java.awt.Color;
import java.math.BigInteger;
import java.nio.charset.Charset;

/* loaded from: classes.dex */
public class HexUtil {
    public static void appendHex(StringBuilder sb, byte b3, boolean z2) {
        (z2 ? Base16Codec.CODEC_LOWER : Base16Codec.CODEC_UPPER).appendHex(sb, b3);
    }

    public static Color decodeColor(String str) {
        return Color.decode(str);
    }

    public static byte[] decodeHex(String str) {
        return decodeHex((CharSequence) str);
    }

    public static String decodeHexStr(String str) {
        return decodeHexStr(str, CharsetUtil.CHARSET_UTF_8);
    }

    public static String encodeColor(Color color) {
        return encodeColor(color, DictionaryFactory.SHARP);
    }

    public static char[] encodeHex(byte[] bArr) {
        return encodeHex(bArr, true);
    }

    public static String encodeHexStr(byte[] bArr) {
        return encodeHexStr(bArr, true);
    }

    public static String format(String str) {
        int length = str.length();
        StringBuilder sbBuilder = StrUtil.builder((length / 2) + length);
        sbBuilder.append(str.charAt(0));
        sbBuilder.append(str.charAt(1));
        for (int i2 = 2; i2 < length - 1; i2 += 2) {
            sbBuilder.append(' ');
            sbBuilder.append(str.charAt(i2));
            sbBuilder.append(str.charAt(i2 + 1));
        }
        return sbBuilder.toString();
    }

    public static int hexToInt(String str) {
        return Integer.parseInt(str, 16);
    }

    public static long hexToLong(String str) {
        return Long.parseLong(str, 16);
    }

    public static boolean isHexNumber(String str) {
        if (CharSequenceUtil.startWith(str, CharPool.DASHED)) {
            return false;
        }
        try {
            new BigInteger(str.substring((str.startsWith("0x", 0) || str.startsWith("0X", 0)) ? 2 : str.startsWith(DictionaryFactory.SHARP, 0) ? 1 : 0), 16);
            return true;
        } catch (NumberFormatException unused) {
            return false;
        }
    }

    public static BigInteger toBigInteger(String str) {
        if (str == null) {
            return null;
        }
        return new BigInteger(str, 16);
    }

    public static String toHex(int i2) {
        return Integer.toHexString(i2);
    }

    public static String toUnicodeHex(int i2) {
        StringBuilder sb = new StringBuilder(6);
        sb.append("\\u");
        String hex = toHex(i2);
        int length = hex.length();
        if (length < 4) {
            sb.append((CharSequence) "0000", 0, 4 - length);
        }
        sb.append(hex);
        return sb.toString();
    }

    public static byte[] decodeHex(char[] cArr) {
        return decodeHex(String.valueOf(cArr));
    }

    public static String decodeHexStr(String str, Charset charset) {
        return CharSequenceUtil.isEmpty(str) ? str : StrUtil.str(decodeHex(str), charset);
    }

    public static String encodeColor(Color color, String str) {
        StringBuilder sb = new StringBuilder(str);
        String hexString = Integer.toHexString(color.getRed());
        if (1 == hexString.length()) {
            sb.append('0');
        }
        sb.append(hexString);
        String hexString2 = Integer.toHexString(color.getGreen());
        if (1 == hexString2.length()) {
            sb.append('0');
        }
        sb.append(hexString2);
        String hexString3 = Integer.toHexString(color.getBlue());
        if (1 == hexString3.length()) {
            sb.append('0');
        }
        sb.append(hexString3);
        return sb.toString();
    }

    public static char[] encodeHex(String str, Charset charset) {
        return encodeHex(CharSequenceUtil.bytes(str, charset), true);
    }

    public static String encodeHexStr(String str, Charset charset) {
        return encodeHexStr(CharSequenceUtil.bytes(str, charset), true);
    }

    public static String toHex(long j2) {
        return Long.toHexString(j2);
    }

    public static byte[] decodeHex(CharSequence charSequence) {
        return Base16Codec.CODEC_LOWER.decode(charSequence);
    }

    public static char[] encodeHex(byte[] bArr, boolean z2) {
        return (z2 ? Base16Codec.CODEC_LOWER : Base16Codec.CODEC_UPPER).encode(bArr);
    }

    public static String encodeHexStr(String str) {
        return encodeHexStr(str, CharsetUtil.CHARSET_UTF_8);
    }

    public static String decodeHexStr(char[] cArr, Charset charset) {
        return StrUtil.str(decodeHex(cArr), charset);
    }

    public static String encodeHexStr(byte[] bArr, boolean z2) {
        return new String(encodeHex(bArr, z2));
    }

    public static String toUnicodeHex(char c3) {
        return Base16Codec.CODEC_LOWER.toUnicodeHex(c3);
    }
}
