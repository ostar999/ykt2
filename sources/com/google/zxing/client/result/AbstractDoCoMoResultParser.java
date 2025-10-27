package com.google.zxing.client.result;

/* loaded from: classes4.dex */
abstract class AbstractDoCoMoResultParser extends ResultParser {
    public static String[] matchDoCoMoPrefixedField(String str, String str2, boolean z2) {
        return ResultParser.matchPrefixedField(str, str2, ';', z2);
    }

    public static String matchSingleDoCoMoPrefixedField(String str, String str2, boolean z2) {
        return ResultParser.matchSinglePrefixedField(str, str2, ';', z2);
    }
}
