package com.google.zxing.client.result;

import com.google.zxing.Result;

/* loaded from: classes4.dex */
public final class WifiResultParser extends ResultParser {
    @Override // com.google.zxing.client.result.ResultParser
    public WifiParsedResult parse(Result result) {
        String strMatchSinglePrefixedField;
        String massagedText = ResultParser.getMassagedText(result);
        if (!massagedText.startsWith("WIFI:") || (strMatchSinglePrefixedField = ResultParser.matchSinglePrefixedField("S:", massagedText, ';', false)) == null || strMatchSinglePrefixedField.isEmpty()) {
            return null;
        }
        String strMatchSinglePrefixedField2 = ResultParser.matchSinglePrefixedField("P:", massagedText, ';', false);
        String strMatchSinglePrefixedField3 = ResultParser.matchSinglePrefixedField("T:", massagedText, ';', false);
        if (strMatchSinglePrefixedField3 == null) {
            strMatchSinglePrefixedField3 = "nopass";
        }
        return new WifiParsedResult(strMatchSinglePrefixedField3, strMatchSinglePrefixedField, strMatchSinglePrefixedField2, Boolean.parseBoolean(ResultParser.matchSinglePrefixedField("H:", massagedText, ';', false)));
    }
}
