package com.tencent.open.log;

import com.tencent.connect.common.Constants;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.io.File;

/* loaded from: classes6.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    public static int f20621a = 60;

    /* renamed from: b, reason: collision with root package name */
    public static int f20622b = 60;

    /* renamed from: c, reason: collision with root package name */
    public static String f20623c = "OpenSDK.Client.File.Tracer";

    /* renamed from: d, reason: collision with root package name */
    public static String f20624d;

    /* renamed from: e, reason: collision with root package name */
    public static String f20625e;

    /* renamed from: f, reason: collision with root package name */
    public static long f20626f;

    /* renamed from: g, reason: collision with root package name */
    public static int f20627g;

    /* renamed from: h, reason: collision with root package name */
    public static int f20628h;

    /* renamed from: i, reason: collision with root package name */
    public static int f20629i;

    /* renamed from: j, reason: collision with root package name */
    public static String f20630j;

    /* renamed from: k, reason: collision with root package name */
    public static String f20631k;

    /* renamed from: l, reason: collision with root package name */
    public static String f20632l;

    /* renamed from: m, reason: collision with root package name */
    public static int f20633m;

    /* renamed from: n, reason: collision with root package name */
    public static long f20634n;

    /* renamed from: o, reason: collision with root package name */
    public static String f20635o;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append("Tencent");
        String str = File.separator;
        sb.append(str);
        sb.append("msflogs");
        sb.append(str);
        sb.append("com");
        sb.append(str);
        sb.append(SocializeProtocolConstants.PROTOCOL_KEY_TENCENT);
        sb.append(str);
        sb.append("mobileqq");
        sb.append(str);
        f20624d = sb.toString();
        f20625e = ".log";
        f20626f = 8388608L;
        f20627g = 262144;
        f20628h = 1024;
        f20629i = 10000;
        f20630j = "debug.file.blockcount";
        f20631k = "debug.file.keepperiod";
        f20632l = "debug.file.tracelevel";
        f20633m = 24;
        f20634n = 604800000L;
        f20635o = Constants.APP_SPECIFIC_ROOT + str + "logs";
    }
}
