package com.google.zxing.client.result;

import com.google.zxing.Result;
import com.tencent.smtt.sdk.WebView;

/* loaded from: classes4.dex */
public final class TelResultParser extends ResultParser {
    @Override // com.google.zxing.client.result.ResultParser
    public TelParsedResult parse(Result result) {
        String str;
        String massagedText = ResultParser.getMassagedText(result);
        if (!massagedText.startsWith(WebView.SCHEME_TEL) && !massagedText.startsWith("TEL:")) {
            return null;
        }
        if (massagedText.startsWith("TEL:")) {
            str = WebView.SCHEME_TEL + massagedText.substring(4);
        } else {
            str = massagedText;
        }
        int iIndexOf = massagedText.indexOf(63, 4);
        return new TelParsedResult(iIndexOf < 0 ? massagedText.substring(4) : massagedText.substring(4, iIndexOf), str, null);
    }
}
