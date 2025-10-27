package org.apache.http.client.utils;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.annotation.Immutable;
import org.apache.http.message.BasicNameValuePair;

@Immutable
/* loaded from: classes9.dex */
public class URLEncodedUtils {
    public static final String CONTENT_TYPE = "application/x-www-form-urlencoded";
    private static final String NAME_VALUE_SEPARATOR = "=";
    private static final String PARAMETER_SEPARATOR = "&";

    private static String decode(String str, String str2) {
        if (str2 == null) {
            str2 = "ISO-8859-1";
        }
        try {
            return URLDecoder.decode(str, str2);
        } catch (UnsupportedEncodingException e2) {
            throw new IllegalArgumentException(e2);
        }
    }

    private static String encode(String str, String str2) {
        if (str2 == null) {
            str2 = "ISO-8859-1";
        }
        try {
            return URLEncoder.encode(str, str2);
        } catch (UnsupportedEncodingException e2) {
            throw new IllegalArgumentException(e2);
        }
    }

    public static String format(List<? extends NameValuePair> list, String str) {
        StringBuilder sb = new StringBuilder();
        for (NameValuePair nameValuePair : list) {
            String strEncode = encode(nameValuePair.getName(), str);
            String value = nameValuePair.getValue();
            String strEncode2 = value != null ? encode(value, str) : "";
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(strEncode);
            sb.append("=");
            sb.append(strEncode2);
        }
        return sb.toString();
    }

    public static boolean isEncoded(HttpEntity httpEntity) throws ParseException {
        Header contentType = httpEntity.getContentType();
        if (contentType != null) {
            HeaderElement[] elements = contentType.getElements();
            if (elements.length > 0) {
                return elements[0].getName().equalsIgnoreCase("application/x-www-form-urlencoded");
            }
        }
        return false;
    }

    public static List<NameValuePair> parse(URI uri, String str) {
        List<NameValuePair> listEmptyList = Collections.emptyList();
        String rawQuery = uri.getRawQuery();
        if (rawQuery == null || rawQuery.length() <= 0) {
            return listEmptyList;
        }
        ArrayList arrayList = new ArrayList();
        parse(arrayList, new Scanner(rawQuery), str);
        return arrayList;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0028  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.util.List<org.apache.http.NameValuePair> parse(org.apache.http.HttpEntity r5) throws org.apache.http.ParseException, java.io.IOException {
        /*
            java.util.List r0 = java.util.Collections.emptyList()
            org.apache.http.Header r1 = r5.getContentType()
            r2 = 0
            if (r1 == 0) goto L28
            org.apache.http.HeaderElement[] r1 = r1.getElements()
            int r3 = r1.length
            if (r3 <= 0) goto L28
            r3 = 0
            r1 = r1[r3]
            java.lang.String r3 = r1.getName()
            java.lang.String r4 = "charset"
            org.apache.http.NameValuePair r1 = r1.getParameterByName(r4)
            if (r1 == 0) goto L25
            java.lang.String r2 = r1.getValue()
        L25:
            r1 = r2
            r2 = r3
            goto L29
        L28:
            r1 = r2
        L29:
            if (r2 == 0) goto L4e
            java.lang.String r3 = "application/x-www-form-urlencoded"
            boolean r2 = r2.equalsIgnoreCase(r3)
            if (r2 == 0) goto L4e
            java.lang.String r2 = "ASCII"
            java.lang.String r5 = org.apache.http.util.EntityUtils.toString(r5, r2)
            if (r5 == 0) goto L4e
            int r2 = r5.length()
            if (r2 <= 0) goto L4e
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            java.util.Scanner r2 = new java.util.Scanner
            r2.<init>(r5)
            parse(r0, r2, r1)
        L4e:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.http.client.utils.URLEncodedUtils.parse(org.apache.http.HttpEntity):java.util.List");
    }

    public static void parse(List<NameValuePair> list, Scanner scanner, String str) {
        scanner.useDelimiter("&");
        while (scanner.hasNext()) {
            String[] strArrSplit = scanner.next().split("=");
            if (strArrSplit.length != 0 && strArrSplit.length <= 2) {
                list.add(new BasicNameValuePair(decode(strArrSplit[0], str), strArrSplit.length == 2 ? decode(strArrSplit[1], str) : null));
            } else {
                throw new IllegalArgumentException("bad parameter");
            }
        }
    }
}
