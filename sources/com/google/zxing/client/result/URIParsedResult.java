package com.google.zxing.client.result;

import com.just.agentweb.DefaultWebClient;
import java.util.regex.Pattern;

/* loaded from: classes4.dex */
public final class URIParsedResult extends ParsedResult {
    private static final Pattern USER_IN_HOST = Pattern.compile(":/*([^/@]+)@[^/]+");
    private final String title;
    private final String uri;

    public URIParsedResult(String str, String str2) {
        super(ParsedResultType.URI);
        this.uri = massageURI(str);
        this.title = str2;
    }

    private static boolean isColonFollowedByPortNumber(String str, int i2) {
        int i3 = i2 + 1;
        int iIndexOf = str.indexOf(47, i3);
        if (iIndexOf < 0) {
            iIndexOf = str.length();
        }
        return ResultParser.isSubstringOfDigits(str, i3, iIndexOf - i3);
    }

    private static String massageURI(String str) {
        String strTrim = str.trim();
        int iIndexOf = strTrim.indexOf(58);
        if (iIndexOf < 0) {
            return DefaultWebClient.HTTP_SCHEME + strTrim;
        }
        if (!isColonFollowedByPortNumber(strTrim, iIndexOf)) {
            return strTrim;
        }
        return DefaultWebClient.HTTP_SCHEME + strTrim;
    }

    @Override // com.google.zxing.client.result.ParsedResult
    public String getDisplayResult() {
        StringBuilder sb = new StringBuilder(30);
        ParsedResult.maybeAppend(this.title, sb);
        ParsedResult.maybeAppend(this.uri, sb);
        return sb.toString();
    }

    public String getTitle() {
        return this.title;
    }

    public String getURI() {
        return this.uri;
    }

    public boolean isPossiblyMaliciousURI() {
        return USER_IN_HOST.matcher(this.uri).find();
    }
}
