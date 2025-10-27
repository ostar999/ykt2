package com.google.zxing.client.result;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.yikaobang.yixue.R2;
import java.util.regex.Pattern;

/* loaded from: classes4.dex */
public final class VINResultParser extends ResultParser {
    private static final Pattern IOQ = Pattern.compile("[IOQ]");
    private static final Pattern AZ09 = Pattern.compile("[A-Z0-9]{17}");

    private static char checkChar(int i2) {
        if (i2 < 10) {
            return (char) (i2 + 48);
        }
        if (i2 == 10) {
            return 'X';
        }
        throw new IllegalArgumentException();
    }

    private static boolean checkChecksum(CharSequence charSequence) {
        int i2 = 0;
        int iVinPositionWeight = 0;
        while (i2 < charSequence.length()) {
            int i3 = i2 + 1;
            iVinPositionWeight += vinPositionWeight(i3) * vinCharValue(charSequence.charAt(i2));
            i2 = i3;
        }
        return charSequence.charAt(8) == checkChar(iVinPositionWeight % 11);
    }

    private static String countryCode(CharSequence charSequence) {
        char cCharAt = charSequence.charAt(0);
        char cCharAt2 = charSequence.charAt(1);
        if (cCharAt == '9') {
            if (cCharAt2 >= 'A' && cCharAt2 <= 'E') {
                return "BR";
            }
            if (cCharAt2 < '3' || cCharAt2 > '9') {
                return null;
            }
            return "BR";
        }
        if (cCharAt == 'S') {
            if (cCharAt2 >= 'A' && cCharAt2 <= 'M') {
                return "UK";
            }
            if (cCharAt2 < 'N' || cCharAt2 > 'T') {
                return null;
            }
            return "DE";
        }
        if (cCharAt == 'Z') {
            if (cCharAt2 < 'A' || cCharAt2 > 'R') {
                return null;
            }
            return "IT";
        }
        switch (cCharAt) {
            case '1':
            case '4':
            case '5':
                return "US";
            case '2':
                return "CA";
            case '3':
                if (cCharAt2 < 'A' || cCharAt2 > 'W') {
                    return null;
                }
                return "MX";
            default:
                switch (cCharAt) {
                    case 'J':
                        if (cCharAt2 < 'A' || cCharAt2 > 'T') {
                            return null;
                        }
                        return "JP";
                    case 'K':
                        if (cCharAt2 < 'L' || cCharAt2 > 'R') {
                            return null;
                        }
                        return "KO";
                    case 'L':
                        return "CN";
                    case 'M':
                        if (cCharAt2 < 'A' || cCharAt2 > 'E') {
                            return null;
                        }
                        return "IN";
                    default:
                        switch (cCharAt) {
                            case 'V':
                                if (cCharAt2 >= 'F' && cCharAt2 <= 'R') {
                                    return "FR";
                                }
                                if (cCharAt2 < 'S' || cCharAt2 > 'W') {
                                    return null;
                                }
                                return "ES";
                            case 'W':
                                return "DE";
                            case 'X':
                                if (cCharAt2 == '0') {
                                    return "RU";
                                }
                                if (cCharAt2 < '3' || cCharAt2 > '9') {
                                    return null;
                                }
                                return "RU";
                            default:
                                return null;
                        }
                }
        }
    }

    private static int modelYear(char c3) {
        if (c3 >= 'E' && c3 <= 'H') {
            return (c3 - 'E') + R2.attr.icon_record_video;
        }
        if (c3 >= 'J' && c3 <= 'N') {
            return (c3 - 'J') + R2.attr.icon_search;
        }
        if (c3 == 'P') {
            return R2.attr.icon_selected_top;
        }
        if (c3 >= 'R' && c3 <= 'T') {
            return (c3 - 'R') + R2.attr.icon_share_new;
        }
        if (c3 >= 'V' && c3 <= 'Y') {
            return (c3 - 'V') + R2.attr.icon_src_normal;
        }
        if (c3 >= '1' && c3 <= '9') {
            return (c3 - '1') + 2001;
        }
        if (c3 < 'A' || c3 > 'D') {
            throw new IllegalArgumentException();
        }
        return (c3 - 'A') + 2010;
    }

    private static int vinCharValue(char c3) {
        if (c3 >= 'A' && c3 <= 'I') {
            return (c3 - 'A') + 1;
        }
        if (c3 >= 'J' && c3 <= 'R') {
            return (c3 - 'J') + 1;
        }
        if (c3 >= 'S' && c3 <= 'Z') {
            return (c3 - 'S') + 2;
        }
        if (c3 < '0' || c3 > '9') {
            throw new IllegalArgumentException();
        }
        return c3 - '0';
    }

    private static int vinPositionWeight(int i2) {
        if (i2 >= 1 && i2 <= 7) {
            return 9 - i2;
        }
        if (i2 == 8) {
            return 10;
        }
        if (i2 == 9) {
            return 0;
        }
        if (i2 < 10 || i2 > 17) {
            throw new IllegalArgumentException();
        }
        return 19 - i2;
    }

    @Override // com.google.zxing.client.result.ResultParser
    public VINParsedResult parse(Result result) {
        if (result.getBarcodeFormat() != BarcodeFormat.CODE_39) {
            return null;
        }
        String strTrim = IOQ.matcher(result.getText()).replaceAll("").trim();
        if (!AZ09.matcher(strTrim).matches()) {
            return null;
        }
        try {
            if (!checkChecksum(strTrim)) {
                return null;
            }
            String strSubstring = strTrim.substring(0, 3);
            return new VINParsedResult(strTrim, strSubstring, strTrim.substring(3, 9), strTrim.substring(9, 17), countryCode(strSubstring), strTrim.substring(3, 8), modelYear(strTrim.charAt(9)), strTrim.charAt(10), strTrim.substring(11));
        } catch (IllegalArgumentException unused) {
            return null;
        }
    }
}
