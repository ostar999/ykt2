package com.alipay.sdk.app;

/* loaded from: classes2.dex */
public final class i {

    /* renamed from: a, reason: collision with root package name */
    public static String f3096a;

    private static void a(String str) {
        f3096a = str;
    }

    private static String b() {
        return f3096a;
    }

    private static String c() {
        j jVarA = j.a(j.DOUBLE_REQUEST.f3105h);
        return a(jVarA.f3105h, jVarA.f3106i, "");
    }

    private static String d() {
        j jVarA = j.a(j.PARAMS_ERROR.f3105h);
        return a(jVarA.f3105h, jVarA.f3106i, "");
    }

    public static String a() {
        j jVarA = j.a(j.CANCELED.f3105h);
        return a(jVarA.f3105h, jVarA.f3106i, "");
    }

    public static String a(int i2, String str, String str2) {
        return "resultStatus={" + i2 + "};memo={" + str + "};result={" + str2 + "}";
    }
}
