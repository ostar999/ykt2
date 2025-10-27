package com.google.zxing.client.result;

/* loaded from: classes4.dex */
public final class ExpandedProductResultParser extends ResultParser {
    private static String findAIvalue(int i2, String str) {
        if (str.charAt(i2) != '(') {
            return null;
        }
        String strSubstring = str.substring(i2 + 1);
        StringBuilder sb = new StringBuilder();
        for (int i3 = 0; i3 < strSubstring.length(); i3++) {
            char cCharAt = strSubstring.charAt(i3);
            if (cCharAt == ')') {
                return sb.toString();
            }
            if (cCharAt < '0' || cCharAt > '9') {
                return null;
            }
            sb.append(cCharAt);
        }
        return sb.toString();
    }

    private static String findValue(int i2, String str) {
        StringBuilder sb = new StringBuilder();
        String strSubstring = str.substring(i2);
        for (int i3 = 0; i3 < strSubstring.length(); i3++) {
            char cCharAt = strSubstring.charAt(i3);
            if (cCharAt != '(') {
                sb.append(cCharAt);
            } else {
                if (findAIvalue(i3, strSubstring) != null) {
                    break;
                }
                sb.append('(');
            }
        }
        return sb.toString();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:13:0x004f  */
    @Override // com.google.zxing.client.result.ResultParser
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.google.zxing.client.result.ExpandedProductParsedResult parse(com.google.zxing.Result r24) {
        /*
            Method dump skipped, instructions count: 866
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.client.result.ExpandedProductResultParser.parse(com.google.zxing.Result):com.google.zxing.client.result.ExpandedProductParsedResult");
    }
}
