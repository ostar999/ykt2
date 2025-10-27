package cn.hutool.core.codec;

import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.CharSequenceUtil;
import java.util.List;

/* loaded from: classes.dex */
public class PunyCode {
    private static final int BASE = 36;
    private static final int DAMP = 700;
    private static final char DELIMITER = '-';
    private static final int INITIAL_BIAS = 72;
    private static final int INITIAL_N = 128;
    public static final String PUNY_CODE_PREFIX = "xn--";
    private static final int SKEW = 38;
    private static final int TMAX = 26;
    private static final int TMIN = 1;

    private static int adapt(int i2, int i3, boolean z2) {
        int i4 = z2 ? i2 / 700 : i2 / 2;
        int i5 = i4 + (i4 / i3);
        int i6 = 0;
        while (i5 > 455) {
            i5 /= 35;
            i6 += 36;
        }
        return i6 + ((i5 * 36) / (i5 + 38));
    }

    private static int codepoint2digit(int i2) throws UtilException {
        int i3 = i2 - 48;
        if (i3 < 10) {
            return i3 + 26;
        }
        int i4 = i2 - 97;
        if (i4 < 26) {
            return i4;
        }
        throw new UtilException("BAD_INPUT");
    }

    public static String decode(String str) throws UtilException, IllegalArgumentException {
        int i2;
        Assert.notNull(str, "input must not be null!", new Object[0]);
        String strRemovePrefixIgnoreCase = CharSequenceUtil.removePrefixIgnoreCase(str, PUNY_CODE_PREFIX);
        StringBuilder sb = new StringBuilder();
        int iLastIndexOf = strRemovePrefixIgnoreCase.lastIndexOf(45);
        if (iLastIndexOf > 0) {
            for (int i3 = 0; i3 < iLastIndexOf; i3++) {
                char cCharAt = strRemovePrefixIgnoreCase.charAt(i3);
                if (isBasic(cCharAt)) {
                    sb.append(cCharAt);
                }
            }
            i2 = iLastIndexOf + 1;
        } else {
            i2 = 0;
        }
        int length = strRemovePrefixIgnoreCase.length();
        int length2 = 128;
        int iAdapt = 72;
        int i4 = 0;
        while (i2 < length) {
            int i5 = 36;
            int i6 = 1;
            int i7 = i4;
            while (i2 != length) {
                int i8 = i2 + 1;
                int iCodepoint2digit = codepoint2digit(strRemovePrefixIgnoreCase.charAt(i2));
                if (iCodepoint2digit > (Integer.MAX_VALUE - i7) / i6) {
                    throw new UtilException("OVERFLOW");
                }
                i7 += iCodepoint2digit * i6;
                int i9 = i5 <= iAdapt ? 1 : i5 >= iAdapt + 26 ? 26 : i5 - iAdapt;
                if (iCodepoint2digit < i9) {
                    iAdapt = adapt(i7 - i4, sb.length() + 1, i4 == 0);
                    if (i7 / (sb.length() + 1) > Integer.MAX_VALUE - length2) {
                        throw new UtilException("OVERFLOW");
                    }
                    length2 += i7 / (sb.length() + 1);
                    int length3 = i7 % (sb.length() + 1);
                    sb.insert(length3, (char) length2);
                    i4 = length3 + 1;
                    i2 = i8;
                } else {
                    i6 *= 36 - i9;
                    i5 += 36;
                    i2 = i8;
                }
            }
            throw new UtilException("BAD_INPUT");
        }
        return sb.toString();
    }

    public static String decodeDomain(String str) throws UtilException, IllegalArgumentException {
        Assert.notNull(str, "domain must not be null!", new Object[0]);
        List<String> listSplit = CharSequenceUtil.split((CharSequence) str, '.');
        StringBuilder sb = new StringBuilder((str.length() / 4) + 1);
        for (String strDecode : listSplit) {
            if (sb.length() != 0) {
                sb.append('.');
            }
            if (CharSequenceUtil.startWithIgnoreEquals(strDecode, PUNY_CODE_PREFIX)) {
                strDecode = decode(strDecode);
            }
            sb.append(strDecode);
        }
        return sb.toString();
    }

    private static int digit2codepoint(int i2) throws UtilException {
        Assert.checkBetween(i2, 0, 35);
        if (i2 < 26) {
            return i2 + 97;
        }
        if (i2 < 36) {
            return (i2 - 26) + 48;
        }
        throw new UtilException("BAD_INPUT");
    }

    public static String encode(CharSequence charSequence) throws UtilException {
        return encode(charSequence, false);
    }

    public static String encodeDomain(String str) throws UtilException, IllegalArgumentException {
        Assert.notNull(str, "domain must not be null!", new Object[0]);
        List<String> listSplit = CharSequenceUtil.split((CharSequence) str, '.');
        StringBuilder sb = new StringBuilder(str.length() * 4);
        for (String str2 : listSplit) {
            if (sb.length() != 0) {
                sb.append('.');
            }
            sb.append(encode(str2, true));
        }
        return sb.toString();
    }

    private static boolean isBasic(char c3) {
        return c3 < 128;
    }

    public static String encode(CharSequence charSequence, boolean z2) throws UtilException, IllegalArgumentException {
        Assert.notNull(charSequence, "input must not be null!", new Object[0]);
        StringBuilder sb = new StringBuilder();
        int length = charSequence.length();
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            char cCharAt = charSequence.charAt(i3);
            if (isBasic(cCharAt)) {
                sb.append(cCharAt);
                i2++;
            }
        }
        if (i2 > 0) {
            if (i2 == length) {
                return sb.toString();
            }
            sb.append('-');
        }
        int i4 = 128;
        int i5 = 72;
        int i6 = 0;
        int i7 = i2;
        while (i7 < length) {
            char c3 = 65535;
            for (int i8 = 0; i8 < length; i8++) {
                char cCharAt2 = charSequence.charAt(i8);
                if (cCharAt2 >= i4 && cCharAt2 < c3) {
                    c3 = cCharAt2;
                }
            }
            int i9 = c3 - i4;
            int i10 = i7 + 1;
            if (i9 > (Integer.MAX_VALUE - i6) / i10) {
                throw new UtilException("OVERFLOW");
            }
            int i11 = i6 + (i9 * i10);
            for (int i12 = 0; i12 < length; i12++) {
                char cCharAt3 = charSequence.charAt(i12);
                if (cCharAt3 < c3 && (i11 = i11 + 1) == 0) {
                    throw new UtilException("OVERFLOW");
                }
                if (cCharAt3 == c3) {
                    int i13 = 36;
                    int i14 = i11;
                    while (true) {
                        int i15 = i13 <= i5 ? 1 : i13 >= i5 + 26 ? 26 : i13 - i5;
                        if (i14 < i15) {
                            break;
                        }
                        int i16 = i14 - i15;
                        int i17 = 36 - i15;
                        sb.append((char) digit2codepoint(i15 + (i16 % i17)));
                        i14 = i16 / i17;
                        i13 += 36;
                    }
                    sb.append((char) digit2codepoint(i14));
                    int i18 = i7 + 1;
                    int iAdapt = adapt(i11, i18, i7 == i2);
                    i11 = 0;
                    i7 = i18;
                    i5 = iAdapt;
                }
            }
            i6 = i11 + 1;
            i4 = c3 + 1;
        }
        if (z2) {
            sb.insert(0, PUNY_CODE_PREFIX);
        }
        return sb.toString();
    }
}
