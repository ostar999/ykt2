package com.google.android.exoplayer2.source.dash.manifest;

import java.util.Locale;

/* loaded from: classes3.dex */
public final class UrlTemplate {
    private static final String BANDWIDTH = "Bandwidth";
    private static final int BANDWIDTH_ID = 3;
    private static final String DEFAULT_FORMAT_TAG = "%01d";
    private static final String ESCAPED_DOLLAR = "$$";
    private static final String NUMBER = "Number";
    private static final int NUMBER_ID = 2;
    private static final String REPRESENTATION = "RepresentationID";
    private static final int REPRESENTATION_ID = 1;
    private static final String TIME = "Time";
    private static final int TIME_ID = 4;
    private final int identifierCount;
    private final String[] identifierFormatTags;
    private final int[] identifiers;
    private final String[] urlPieces;

    private UrlTemplate(String[] strArr, int[] iArr, String[] strArr2, int i2) {
        this.urlPieces = strArr;
        this.identifiers = iArr;
        this.identifierFormatTags = strArr2;
        this.identifierCount = i2;
    }

    public static UrlTemplate compile(String str) {
        String[] strArr = new String[5];
        int[] iArr = new int[4];
        String[] strArr2 = new String[4];
        return new UrlTemplate(strArr, iArr, strArr2, parseTemplate(str, strArr, iArr, strArr2));
    }

    private static int parseTemplate(String str, String[] strArr, int[] iArr, String[] strArr2) {
        String strSubstring;
        strArr[0] = "";
        int length = 0;
        int i2 = 0;
        while (length < str.length()) {
            int iIndexOf = str.indexOf("$", length);
            if (iIndexOf == -1) {
                String strValueOf = String.valueOf(strArr[i2]);
                String strValueOf2 = String.valueOf(str.substring(length));
                strArr[i2] = strValueOf2.length() != 0 ? strValueOf.concat(strValueOf2) : new String(strValueOf);
                length = str.length();
            } else if (iIndexOf != length) {
                String strValueOf3 = String.valueOf(strArr[i2]);
                String strValueOf4 = String.valueOf(str.substring(length, iIndexOf));
                strArr[i2] = strValueOf4.length() != 0 ? strValueOf3.concat(strValueOf4) : new String(strValueOf3);
                length = iIndexOf;
            } else if (str.startsWith(ESCAPED_DOLLAR, length)) {
                strArr[i2] = String.valueOf(strArr[i2]).concat("$");
                length += 2;
            } else {
                int i3 = length + 1;
                int iIndexOf2 = str.indexOf("$", i3);
                String strSubstring2 = str.substring(i3, iIndexOf2);
                if (strSubstring2.equals(REPRESENTATION)) {
                    iArr[i2] = 1;
                } else {
                    int iIndexOf3 = strSubstring2.indexOf("%0");
                    if (iIndexOf3 != -1) {
                        strSubstring = strSubstring2.substring(iIndexOf3);
                        if (!strSubstring.endsWith("d") && !strSubstring.endsWith("x")) {
                            strSubstring = strSubstring.concat("d");
                        }
                        strSubstring2 = strSubstring2.substring(0, iIndexOf3);
                    } else {
                        strSubstring = DEFAULT_FORMAT_TAG;
                    }
                    strSubstring2.hashCode();
                    switch (strSubstring2) {
                        case "Number":
                            iArr[i2] = 2;
                            break;
                        case "Time":
                            iArr[i2] = 4;
                            break;
                        case "Bandwidth":
                            iArr[i2] = 3;
                            break;
                        default:
                            throw new IllegalArgumentException(str.length() != 0 ? "Invalid template: ".concat(str) : new String("Invalid template: "));
                    }
                    strArr2[i2] = strSubstring;
                }
                i2++;
                strArr[i2] = "";
                length = iIndexOf2 + 1;
            }
        }
        return i2;
    }

    public String buildUri(String str, long j2, int i2, long j3) {
        StringBuilder sb = new StringBuilder();
        int i3 = 0;
        while (true) {
            int i4 = this.identifierCount;
            if (i3 >= i4) {
                sb.append(this.urlPieces[i4]);
                return sb.toString();
            }
            sb.append(this.urlPieces[i3]);
            int i5 = this.identifiers[i3];
            if (i5 == 1) {
                sb.append(str);
            } else if (i5 == 2) {
                sb.append(String.format(Locale.US, this.identifierFormatTags[i3], Long.valueOf(j2)));
            } else if (i5 == 3) {
                sb.append(String.format(Locale.US, this.identifierFormatTags[i3], Integer.valueOf(i2)));
            } else if (i5 == 4) {
                sb.append(String.format(Locale.US, this.identifierFormatTags[i3], Long.valueOf(j3)));
            }
            i3++;
        }
    }
}
