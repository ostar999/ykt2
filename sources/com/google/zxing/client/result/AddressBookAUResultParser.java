package com.google.zxing.client.result;

import com.google.zxing.Result;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public final class AddressBookAUResultParser extends ResultParser {
    private static String[] matchMultipleValuePrefix(String str, int i2, String str2, boolean z2) {
        ArrayList arrayList = null;
        for (int i3 = 1; i3 <= i2; i3++) {
            String strMatchSinglePrefixedField = ResultParser.matchSinglePrefixedField(str + i3 + ':', str2, '\r', z2);
            if (strMatchSinglePrefixedField == null) {
                break;
            }
            if (arrayList == null) {
                arrayList = new ArrayList(i2);
            }
            arrayList.add(strMatchSinglePrefixedField);
        }
        if (arrayList == null) {
            return null;
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    @Override // com.google.zxing.client.result.ResultParser
    public AddressBookParsedResult parse(Result result) {
        String massagedText = ResultParser.getMassagedText(result);
        if (!massagedText.contains("MEMORY") || !massagedText.contains("\r\n")) {
            return null;
        }
        String strMatchSinglePrefixedField = ResultParser.matchSinglePrefixedField("NAME1:", massagedText, '\r', true);
        String strMatchSinglePrefixedField2 = ResultParser.matchSinglePrefixedField("NAME2:", massagedText, '\r', true);
        String[] strArrMatchMultipleValuePrefix = matchMultipleValuePrefix("TEL", 3, massagedText, true);
        String[] strArrMatchMultipleValuePrefix2 = matchMultipleValuePrefix("MAIL", 3, massagedText, true);
        String strMatchSinglePrefixedField3 = ResultParser.matchSinglePrefixedField("MEMORY:", massagedText, '\r', false);
        String strMatchSinglePrefixedField4 = ResultParser.matchSinglePrefixedField("ADD:", massagedText, '\r', true);
        return new AddressBookParsedResult(ResultParser.maybeWrap(strMatchSinglePrefixedField), null, strMatchSinglePrefixedField2, strArrMatchMultipleValuePrefix, null, strArrMatchMultipleValuePrefix2, null, null, strMatchSinglePrefixedField3, strMatchSinglePrefixedField4 != null ? new String[]{strMatchSinglePrefixedField4} : null, null, null, null, null, null, null);
    }
}
