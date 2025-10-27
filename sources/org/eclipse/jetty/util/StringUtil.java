package org.eclipse.jetty.util;

import cn.hutool.core.text.CharPool;
import com.alibaba.fastjson.parser.JSONLexer;
import com.google.common.base.Ascii;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import kotlin.text.Typography;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes9.dex */
public class StringUtil {
    public static final String ALL_INTERFACES = "0.0.0.0";
    public static final String CRLF = "\r\n";
    public static final String __ISO_8859_1 = "ISO-8859-1";
    public static final String __UTF16 = "UTF-16";
    public static final String __UTF8 = "UTF-8";
    public static final String __UTF8Alt = "UTF8";
    private static final Logger LOG = Log.getLogger((Class<?>) StringUtil.class);
    public static final String __LINE_SEPARATOR = System.getProperty("line.separator", "\n");
    public static final Charset __UTF8_CHARSET = Charset.forName("UTF-8");
    public static final Charset __ISO_8859_1_CHARSET = Charset.forName("ISO-8859-1");
    private static char[] lowercases = {0, 1, 2, 3, 4, 5, 6, 7, '\b', '\t', '\n', 11, '\f', '\r', 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, JSONLexer.EOI, 27, 28, 29, 30, 31, ' ', '!', '\"', '#', Typography.dollar, '%', '&', CharPool.SINGLE_QUOTE, '(', ')', '*', '+', ',', CharPool.DASHED, '.', '/', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ':', ';', Typography.less, '=', Typography.greater, '?', '@', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '[', '\\', ']', '^', '_', '`', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '{', '|', '}', '~', Ascii.MAX};

    public static void append(StringBuilder sb, String str, int i2, int i3) {
        synchronized (sb) {
            int i4 = i3 + i2;
            while (i2 < i4) {
                if (i2 >= str.length()) {
                    break;
                }
                sb.append(str.charAt(i2));
                i2++;
            }
        }
    }

    public static void append2digits(StringBuffer stringBuffer, int i2) {
        if (i2 < 100) {
            stringBuffer.append((char) ((i2 / 10) + 48));
            stringBuffer.append((char) ((i2 % 10) + 48));
        }
    }

    public static String asciiToLowerCase(String str) {
        int i2;
        char[] charArray;
        char c3;
        int length = str.length();
        while (true) {
            i2 = length - 1;
            if (length <= 0) {
                charArray = null;
                break;
            }
            char cCharAt = str.charAt(i2);
            if (cCharAt <= 127 && cCharAt != (c3 = lowercases[cCharAt])) {
                charArray = str.toCharArray();
                charArray[i2] = c3;
                break;
            }
            length = i2;
        }
        while (true) {
            int i3 = i2 - 1;
            if (i2 <= 0) {
                break;
            }
            char c4 = charArray[i3];
            if (c4 <= 127) {
                charArray[i3] = lowercases[c4];
            }
            i2 = i3;
        }
        return charArray == null ? str : new String(charArray);
    }

    public static boolean endsWithIgnoreCase(String str, String str2) {
        int length;
        if (str2 == null) {
            return true;
        }
        if (str == null || (length = str.length()) < (length = str2.length())) {
            return false;
        }
        while (true) {
            int i2 = length - 1;
            if (length <= 0) {
                return true;
            }
            int length2 = length2 - 1;
            char cCharAt = str.charAt(length2);
            char cCharAt2 = str2.charAt(i2);
            if (cCharAt != cCharAt2) {
                if (cCharAt <= 127) {
                    cCharAt = lowercases[cCharAt];
                }
                if (cCharAt2 <= 127) {
                    cCharAt2 = lowercases[cCharAt2];
                }
                if (cCharAt != cCharAt2) {
                    return false;
                }
            }
            length = i2;
        }
    }

    public static boolean equals(String str, char[] cArr, int i2, int i3) {
        if (str.length() != i3) {
            return false;
        }
        for (int i4 = 0; i4 < i3; i4++) {
            if (cArr[i2 + i4] != str.charAt(i4)) {
                return false;
            }
        }
        return true;
    }

    public static byte[] getBytes(String str) {
        try {
            return str.getBytes("ISO-8859-1");
        } catch (Exception e2) {
            LOG.warn(e2);
            return str.getBytes();
        }
    }

    public static int indexFrom(String str, String str2) {
        for (int i2 = 0; i2 < str.length(); i2++) {
            if (str2.indexOf(str.charAt(i2)) >= 0) {
                return i2;
            }
        }
        return -1;
    }

    public static boolean isUTF8(String str) {
        return "UTF-8".equalsIgnoreCase(str) || "UTF8".equalsIgnoreCase(str);
    }

    public static String nonNull(String str) {
        return str == null ? "" : str;
    }

    public static String printable(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(str.length());
        for (int i2 = 0; i2 < str.length(); i2++) {
            char cCharAt = str.charAt(i2);
            if (!Character.isISOControl(cCharAt)) {
                sb.append(cCharAt);
            }
        }
        return sb.toString();
    }

    public static String replace(String str, String str2, String str3) {
        int length = 0;
        int iIndexOf = str.indexOf(str2, 0);
        if (iIndexOf == -1) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str.length() + str3.length());
        do {
            sb.append(str.substring(length, iIndexOf));
            sb.append(str3);
            length = str2.length() + iIndexOf;
            iIndexOf = str.indexOf(str2, length);
        } while (iIndexOf != -1);
        if (length < str.length()) {
            sb.append(str.substring(length, str.length()));
        }
        return sb.toString();
    }

    public static String sidBytesToString(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        sb.append("S-");
        sb.append(Byte.toString(bArr[0]));
        sb.append(CharPool.DASHED);
        StringBuilder sb2 = new StringBuilder();
        for (int i2 = 2; i2 <= 7; i2++) {
            sb2.append(Integer.toHexString(bArr[i2] & 255));
        }
        sb.append(Long.parseLong(sb2.toString(), 16));
        byte b3 = bArr[1];
        for (int i3 = 0; i3 < b3; i3++) {
            int i4 = i3 * 4;
            sb2.setLength(0);
            sb2.append(String.format("%02X%02X%02X%02X", Integer.valueOf(bArr[i4 + 11] & 255), Integer.valueOf(bArr[i4 + 10] & 255), Integer.valueOf(bArr[i4 + 9] & 255), Integer.valueOf(bArr[i4 + 8] & 255)));
            sb.append(CharPool.DASHED);
            sb.append(Long.parseLong(sb2.toString(), 16));
        }
        return sb.toString();
    }

    public static byte[] sidStringToBytes(String str) {
        String[] strArrSplit = str.split("-");
        int length = strArrSplit.length - 3;
        byte[] bArr = new byte[(length * 4) + 8];
        int i2 = 0;
        bArr[0] = (byte) Integer.parseInt(strArrSplit[1]);
        bArr[1] = (byte) length;
        int i3 = 2;
        String hexString = Long.toHexString(Long.parseLong(strArrSplit[2]));
        while (hexString.length() < 12) {
            hexString = "0" + hexString;
        }
        while (i2 < hexString.length()) {
            int i4 = i2 + 2;
            bArr[i3] = (byte) Integer.parseInt(hexString.substring(i2, i4), 16);
            i3++;
            i2 = i4;
        }
        for (int i5 = 3; i5 < strArrSplit.length; i5++) {
            String hexString2 = Long.toHexString(Long.parseLong(strArrSplit[i5]));
            while (hexString2.length() < 8) {
                hexString2 = "0" + hexString2;
            }
            int length2 = hexString2.length();
            while (length2 > 0) {
                int i6 = length2 - 2;
                bArr[i3] = (byte) Integer.parseInt(hexString2.substring(i6, length2), 16);
                i3++;
                length2 = i6;
            }
        }
        return bArr;
    }

    public static boolean startsWithIgnoreCase(String str, String str2) {
        if (str2 == null) {
            return true;
        }
        if (str == null || str.length() < str2.length()) {
            return false;
        }
        for (int i2 = 0; i2 < str2.length(); i2++) {
            char cCharAt = str.charAt(i2);
            char cCharAt2 = str2.charAt(i2);
            if (cCharAt != cCharAt2) {
                if (cCharAt <= 127) {
                    cCharAt = lowercases[cCharAt];
                }
                if (cCharAt2 <= 127) {
                    cCharAt2 = lowercases[cCharAt2];
                }
                if (cCharAt != cCharAt2) {
                    return false;
                }
            }
        }
        return true;
    }

    public static String toString(byte[] bArr, int i2, int i3, String str) {
        try {
            return new String(bArr, i2, i3, str);
        } catch (UnsupportedEncodingException e2) {
            throw new IllegalArgumentException(e2);
        }
    }

    public static String toUTF8String(byte[] bArr, int i2, int i3) {
        try {
            return new String(bArr, i2, i3, "UTF-8");
        } catch (UnsupportedEncodingException e2) {
            throw new IllegalArgumentException(e2);
        }
    }

    public static String unquote(String str) {
        return QuotedStringTokenizer.unquote(str);
    }

    public static void append2digits(StringBuilder sb, int i2) {
        if (i2 < 100) {
            sb.append((char) ((i2 / 10) + 48));
            sb.append((char) ((i2 % 10) + 48));
        }
    }

    public static byte[] getBytes(String str, String str2) {
        try {
            return str.getBytes(str2);
        } catch (Exception e2) {
            LOG.warn(e2);
            return str.getBytes();
        }
    }

    public static void append(StringBuilder sb, byte b3, int i2) {
        int i3 = b3 & 255;
        int i4 = ((i3 / i2) % i2) + 48;
        if (i4 > 57) {
            i4 = ((i4 - 48) - 10) + 97;
        }
        sb.append((char) i4);
        int i5 = (i3 % i2) + 48;
        if (i5 > 57) {
            i5 = ((i5 - 48) - 10) + 97;
        }
        sb.append((char) i5);
    }

    public static String printable(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < bArr.length; i2++) {
            char c3 = (char) bArr[i2];
            if (!Character.isWhitespace(c3) && (c3 <= ' ' || c3 >= 127)) {
                sb.append("0x");
                TypeUtil.toHex(bArr[i2], (Appendable) sb);
            } else {
                sb.append(c3);
            }
        }
        return sb.toString();
    }
}
