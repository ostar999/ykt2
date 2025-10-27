package com.meizu.cloud.pushsdk.b.c;

import java.nio.charset.Charset;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes4.dex */
public class g {

    /* renamed from: a, reason: collision with root package name */
    private static final Pattern f9106a = Pattern.compile("([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)/([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)");

    /* renamed from: b, reason: collision with root package name */
    private static final Pattern f9107b = Pattern.compile(";\\s*(?:([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)=(?:([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)|\"([^\"]*)\"))?");

    /* renamed from: c, reason: collision with root package name */
    private final String f9108c;

    /* renamed from: d, reason: collision with root package name */
    private final String f9109d;

    /* renamed from: e, reason: collision with root package name */
    private final String f9110e;

    /* renamed from: f, reason: collision with root package name */
    private final String f9111f;

    private g(String str, String str2, String str3, String str4) {
        this.f9108c = str;
        this.f9109d = str2;
        this.f9110e = str3;
        this.f9111f = str4;
    }

    public static g a(String str) {
        Matcher matcher = f9106a.matcher(str);
        if (!matcher.lookingAt()) {
            return null;
        }
        String strGroup = matcher.group(1);
        Locale locale = Locale.US;
        String lowerCase = strGroup.toLowerCase(locale);
        String lowerCase2 = matcher.group(2).toLowerCase(locale);
        Matcher matcher2 = f9107b.matcher(str);
        String str2 = null;
        for (int iEnd = matcher.end(); iEnd < str.length(); iEnd = matcher2.end()) {
            matcher2.region(iEnd, str.length());
            if (!matcher2.lookingAt()) {
                return null;
            }
            String strGroup2 = matcher2.group(1);
            if (strGroup2 != null && strGroup2.equalsIgnoreCase("charset")) {
                String strGroup3 = matcher2.group(2) != null ? matcher2.group(2) : matcher2.group(3);
                if (str2 != null && !strGroup3.equalsIgnoreCase(str2)) {
                    throw new IllegalArgumentException("Multiple different charsets: " + str);
                }
                str2 = strGroup3;
            }
        }
        return new g(str, lowerCase, lowerCase2, str2);
    }

    public String a() {
        return this.f9109d;
    }

    public Charset b() {
        String str = this.f9111f;
        if (str != null) {
            return Charset.forName(str);
        }
        return null;
    }

    public boolean equals(Object obj) {
        return (obj instanceof g) && ((g) obj).f9108c.equals(this.f9108c);
    }

    public int hashCode() {
        return this.f9108c.hashCode();
    }

    public String toString() {
        return this.f9108c;
    }
}
