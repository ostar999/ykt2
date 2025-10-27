package org.apache.http.client.utils;

import cn.hutool.core.text.StrPool;
import java.util.StringTokenizer;
import org.apache.http.annotation.Immutable;

@Immutable
/* loaded from: classes9.dex */
public class Rfc3492Idn implements Idn {
    private static final String ACE_PREFIX = "xn--";
    private static final int base = 36;
    private static final int damp = 700;
    private static final char delimiter = '-';
    private static final int initial_bias = 72;
    private static final int initial_n = 128;
    private static final int skew = 38;
    private static final int tmax = 26;
    private static final int tmin = 1;

    private int adapt(int i2, int i3, boolean z2) {
        int i4 = z2 ? i2 / 700 : i2 / 2;
        int i5 = i4 + (i4 / i3);
        int i6 = 0;
        while (i5 > 455) {
            i5 /= 35;
            i6 += 36;
        }
        return i6 + ((i5 * 36) / (i5 + 38));
    }

    private int digit(char c3) {
        if (c3 >= 'A' && c3 <= 'Z') {
            return c3 - 'A';
        }
        if (c3 >= 'a' && c3 <= 'z') {
            return c3 - 'a';
        }
        if (c3 >= '0' && c3 <= '9') {
            return (c3 - '0') + 26;
        }
        throw new IllegalArgumentException("illegal digit: " + c3);
    }

    public String decode(String str) {
        StringBuilder sb = new StringBuilder(str.length());
        int iLastIndexOf = str.lastIndexOf(45);
        int length = 128;
        int iAdapt = 72;
        if (iLastIndexOf != -1) {
            sb.append(str.subSequence(0, iLastIndexOf));
            str = str.substring(iLastIndexOf + 1);
        }
        int i2 = 0;
        while (str.length() > 0) {
            int i3 = 36;
            int i4 = i2;
            int i5 = 1;
            while (str.length() != 0) {
                char cCharAt = str.charAt(0);
                str = str.substring(1);
                int iDigit = digit(cCharAt);
                i4 += iDigit * i5;
                int i6 = i3 <= iAdapt + 1 ? 1 : i3 >= iAdapt + 26 ? 26 : i3 - iAdapt;
                if (iDigit < i6) {
                    break;
                }
                i5 *= 36 - i6;
                i3 += 36;
            }
            iAdapt = adapt(i4 - i2, sb.length() + 1, i2 == 0);
            length += i4 / (sb.length() + 1);
            int length2 = i4 % (sb.length() + 1);
            sb.insert(length2, (char) length);
            i2 = length2 + 1;
        }
        return sb.toString();
    }

    @Override // org.apache.http.client.utils.Idn
    public String toUnicode(String str) {
        StringBuilder sb = new StringBuilder(str.length());
        StringTokenizer stringTokenizer = new StringTokenizer(str, StrPool.DOT);
        while (stringTokenizer.hasMoreTokens()) {
            String strNextToken = stringTokenizer.nextToken();
            if (sb.length() > 0) {
                sb.append('.');
            }
            if (strNextToken.startsWith("xn--")) {
                strNextToken = decode(strNextToken.substring(4));
            }
            sb.append(strNextToken);
        }
        return sb.toString();
    }
}
