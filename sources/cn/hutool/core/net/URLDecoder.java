package cn.hutool.core.net;

import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.CharsetUtil;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.nio.charset.Charset;

/* loaded from: classes.dex */
public class URLDecoder implements Serializable {
    private static final byte ESCAPE_CHAR = 37;
    private static final long serialVersionUID = 1;

    public static String decode(String str, Charset charset) {
        return decode(str, charset, true);
    }

    public static String decodeForPath(String str, Charset charset) {
        return decode(str, charset, false);
    }

    private static String decodeSub(String str, int i2, int i3, Charset charset, boolean z2) {
        return new String(decode(str.substring(i2, i3).getBytes(CharsetUtil.CHARSET_ISO_8859_1), z2), charset);
    }

    public static String decode(String str, Charset charset, boolean z2) {
        if (str == null || charset == null) {
            return str;
        }
        int length = str.length();
        if (length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(length / 3);
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            char cCharAt = str.charAt(i3);
            if ('%' != cCharAt && !CharUtil.isHexChar(cCharAt)) {
                if (i3 > i2) {
                    sb.append(decodeSub(str, i2, i3, charset, z2));
                }
                if ('+' == cCharAt && z2) {
                    cCharAt = ' ';
                }
                sb.append(cCharAt);
                i2 = i3 + 1;
            }
        }
        if (i2 < length) {
            sb.append(decodeSub(str, i2, length, charset, z2));
        }
        return sb.toString();
    }

    public static byte[] decode(byte[] bArr) {
        return decode(bArr, true);
    }

    public static byte[] decode(byte[] bArr, boolean z2) {
        int iDigit16;
        int i2;
        int iDigit162;
        if (bArr == null) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(bArr.length);
        int i3 = 0;
        while (i3 < bArr.length) {
            byte b3 = bArr[i3];
            if (b3 == 43) {
                if (z2) {
                    b3 = 32;
                }
                byteArrayOutputStream.write(b3);
            } else if (b3 == 37) {
                int i4 = i3 + 1;
                if (i4 < bArr.length && (iDigit16 = CharUtil.digit16(bArr[i4])) >= 0 && (i2 = i3 + 2) < bArr.length && (iDigit162 = CharUtil.digit16(bArr[i2])) >= 0) {
                    byteArrayOutputStream.write((char) ((iDigit16 << 4) + iDigit162));
                    i3 = i2;
                } else {
                    byteArrayOutputStream.write(b3);
                }
            } else {
                byteArrayOutputStream.write(b3);
            }
            i3++;
        }
        return byteArrayOutputStream.toByteArray();
    }
}
