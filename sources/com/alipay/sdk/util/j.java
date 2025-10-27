package com.alipay.sdk.util;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public final class j {

    /* renamed from: a, reason: collision with root package name */
    public static final String f3383a = "resultStatus";

    /* renamed from: b, reason: collision with root package name */
    public static final String f3384b = "memo";

    /* renamed from: c, reason: collision with root package name */
    public static final String f3385c = "result";

    private static Map<String, String> a() {
        com.alipay.sdk.app.j jVarA = com.alipay.sdk.app.j.a(com.alipay.sdk.app.j.CANCELED.f3105h);
        HashMap map = new HashMap();
        map.put(f3383a, Integer.toString(jVarA.f3105h));
        map.put(f3384b, jVarA.f3106i);
        map.put("result", "");
        return map;
    }

    private static Map<String, String> b(String str) {
        String[] strArrSplit = str.split(h.f3376b);
        HashMap map = new HashMap();
        for (String str2 : strArrSplit) {
            String strSubstring = str2.substring(0, str2.indexOf("={"));
            String str3 = strSubstring + "={";
            map.put(strSubstring, str2.substring(str2.indexOf(str3) + str3.length(), str2.lastIndexOf("}")));
        }
        return map;
    }

    private static String a(String str, String str2) {
        String str3 = str2 + "={";
        return str.substring(str.indexOf(str3) + str3.length(), str.lastIndexOf("}"));
    }

    public static Map<String, String> a(String str) {
        com.alipay.sdk.app.j jVarA = com.alipay.sdk.app.j.a(com.alipay.sdk.app.j.CANCELED.f3105h);
        HashMap map = new HashMap();
        map.put(f3383a, Integer.toString(jVarA.f3105h));
        map.put(f3384b, jVarA.f3106i);
        map.put("result", "");
        try {
            return b(str);
        } catch (Throwable th) {
            com.alipay.sdk.app.statistic.a.a(com.alipay.sdk.app.statistic.c.f3112b, com.alipay.sdk.app.statistic.c.f3116f, th);
            return map;
        }
    }
}
