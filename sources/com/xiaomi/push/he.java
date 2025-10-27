package com.xiaomi.push;

import java.util.Random;

/* loaded from: classes6.dex */
public class he {

    /* renamed from: a, reason: collision with other field name */
    private static final char[] f518a = "&quot;".toCharArray();

    /* renamed from: b, reason: collision with root package name */
    private static final char[] f25050b = "&apos;".toCharArray();

    /* renamed from: c, reason: collision with root package name */
    private static final char[] f25051c = "&amp;".toCharArray();

    /* renamed from: d, reason: collision with root package name */
    private static final char[] f25052d = "&lt;".toCharArray();

    /* renamed from: e, reason: collision with root package name */
    private static final char[] f25053e = "&gt;".toCharArray();

    /* renamed from: a, reason: collision with root package name */
    private static Random f25049a = new Random();

    /* renamed from: f, reason: collision with root package name */
    private static char[] f25054f = "0123456789abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    public static String a(int i2) {
        if (i2 < 1) {
            return null;
        }
        char[] cArr = new char[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            cArr[i3] = f25054f[f25049a.nextInt(71)];
        }
        return new String(cArr);
    }

    public static String a(String str) {
        if (str == null) {
            return null;
        }
        char[] charArray = str.toCharArray();
        int length = charArray.length;
        StringBuilder sb = new StringBuilder((int) (length * 1.3d));
        int i2 = 0;
        int i3 = 0;
        while (i2 < length) {
            char c3 = charArray[i2];
            if (c3 <= '>') {
                if (c3 == '<') {
                    if (i2 > i3) {
                        sb.append(charArray, i3, i2 - i3);
                    }
                    i3 = i2 + 1;
                    sb.append(f25052d);
                } else if (c3 == '>') {
                    if (i2 > i3) {
                        sb.append(charArray, i3, i2 - i3);
                    }
                    i3 = i2 + 1;
                    sb.append(f25053e);
                } else if (c3 == '&') {
                    if (i2 > i3) {
                        sb.append(charArray, i3, i2 - i3);
                    }
                    int i4 = i2 + 5;
                    if (length <= i4 || charArray[i2 + 1] != '#' || !Character.isDigit(charArray[i2 + 2]) || !Character.isDigit(charArray[i2 + 3]) || !Character.isDigit(charArray[i2 + 4]) || charArray[i4] != ';') {
                        i3 = i2 + 1;
                        sb.append(f25051c);
                    }
                } else if (c3 == '\"') {
                    if (i2 > i3) {
                        sb.append(charArray, i3, i2 - i3);
                    }
                    i3 = i2 + 1;
                    sb.append(f518a);
                } else if (c3 == '\'') {
                    if (i2 > i3) {
                        sb.append(charArray, i3, i2 - i3);
                    }
                    i3 = i2 + 1;
                    sb.append(f25050b);
                }
            }
            i2++;
        }
        if (i3 == 0) {
            return str;
        }
        if (i2 > i3) {
            sb.append(charArray, i3, i2 - i3);
        }
        return sb.toString();
    }

    public static final String a(String str, String str2, String str3) {
        if (str == null) {
            return null;
        }
        int iIndexOf = str.indexOf(str2, 0);
        if (iIndexOf < 0) {
            return str;
        }
        char[] charArray = str.toCharArray();
        char[] charArray2 = str3.toCharArray();
        int length = str2.length();
        StringBuilder sb = new StringBuilder(charArray.length);
        sb.append(charArray, 0, iIndexOf);
        sb.append(charArray2);
        int i2 = iIndexOf + length;
        while (true) {
            int iIndexOf2 = str.indexOf(str2, i2);
            if (iIndexOf2 <= 0) {
                sb.append(charArray, i2, charArray.length - i2);
                return sb.toString();
            }
            sb.append(charArray, i2, iIndexOf2 - i2);
            sb.append(charArray2);
            i2 = iIndexOf2 + length;
        }
    }

    public static String a(byte[] bArr) {
        return String.valueOf(av.a(bArr));
    }

    public static final String b(String str) {
        return a(a(a(a(a(str, "&lt;", "<"), "&gt;", ">"), "&quot;", "\""), "&apos;", "'"), "&amp;", "&");
    }
}
