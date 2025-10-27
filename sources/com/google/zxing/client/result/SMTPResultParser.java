package com.google.zxing.client.result;

import com.google.zxing.Result;

/* loaded from: classes4.dex */
public final class SMTPResultParser extends ResultParser {
    @Override // com.google.zxing.client.result.ResultParser
    public EmailAddressParsedResult parse(Result result) {
        String str;
        String massagedText = ResultParser.getMassagedText(result);
        String strSubstring = null;
        if (!massagedText.startsWith("smtp:") && !massagedText.startsWith("SMTP:")) {
            return null;
        }
        String strSubstring2 = massagedText.substring(5);
        int iIndexOf = strSubstring2.indexOf(58);
        if (iIndexOf >= 0) {
            String strSubstring3 = strSubstring2.substring(iIndexOf + 1);
            strSubstring2 = strSubstring2.substring(0, iIndexOf);
            int iIndexOf2 = strSubstring3.indexOf(58);
            if (iIndexOf2 >= 0) {
                String strSubstring4 = strSubstring3.substring(iIndexOf2 + 1);
                strSubstring = strSubstring3.substring(0, iIndexOf2);
                str = strSubstring4;
            } else {
                str = null;
                strSubstring = strSubstring3;
            }
        } else {
            str = null;
        }
        return new EmailAddressParsedResult(strSubstring2, strSubstring, str, "mailto:" + strSubstring2);
    }
}
