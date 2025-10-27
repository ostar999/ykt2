package com.umeng.commonsdk.vchannel;

/* loaded from: classes6.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    public static String f23544a = "https://pslog.umeng.com";

    /* renamed from: b, reason: collision with root package name */
    public static String f23545b = "https://pslog.umeng.com/";

    /* renamed from: c, reason: collision with root package name */
    public static String f23546c = "explog";

    /* renamed from: d, reason: collision with root package name */
    public static final String f23547d = "analytics";

    /* renamed from: e, reason: collision with root package name */
    public static final String f23548e = "ekv";

    /* renamed from: f, reason: collision with root package name */
    public static final String f23549f = "id";

    /* renamed from: g, reason: collision with root package name */
    public static final String f23550g = "ts";

    /* renamed from: h, reason: collision with root package name */
    public static final String f23551h = "ds";

    /* renamed from: i, reason: collision with root package name */
    public static final String f23552i = "pn";

    /* renamed from: j, reason: collision with root package name */
    public static String f23553j = "";

    static {
        String str = "SUB" + System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(String.format("%0" + (32 - str.length()) + "d", 0));
        f23553j = sb.toString();
    }
}
