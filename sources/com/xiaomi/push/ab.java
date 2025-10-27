package com.xiaomi.push;

/* loaded from: classes6.dex */
public class ab {

    /* renamed from: a, reason: collision with root package name */
    private static int f24587a;

    /* renamed from: a, reason: collision with other field name */
    public static final String f178a;

    /* renamed from: a, reason: collision with other field name */
    public static final boolean f179a;

    /* renamed from: b, reason: collision with root package name */
    public static final boolean f24588b;

    /* renamed from: c, reason: collision with root package name */
    public static final boolean f24589c;

    /* renamed from: d, reason: collision with root package name */
    public static final boolean f24590d;

    /* renamed from: e, reason: collision with root package name */
    public static boolean f24591e;

    /* renamed from: f, reason: collision with root package name */
    public static final boolean f24592f;

    /* renamed from: g, reason: collision with root package name */
    public static final boolean f24593g;

    static {
        int i2;
        String str = ae.f180a ? "ONEBOX" : "@SHIP.TO.2A2FE0D7@";
        f178a = str;
        boolean zContains = str.contains("2A2FE0D7");
        f179a = zContains;
        f24588b = zContains || "DEBUG".equalsIgnoreCase(str);
        f24589c = "LOGABLE".equalsIgnoreCase(str);
        f24590d = str.contains("YY");
        f24591e = str.equalsIgnoreCase("TEST");
        f24592f = "BETA".equalsIgnoreCase(str);
        f24593g = str.startsWith("RC");
        f24587a = 1;
        if (str.equalsIgnoreCase("SANDBOX")) {
            i2 = 2;
        } else {
            if (!str.equalsIgnoreCase("ONEBOX")) {
                f24587a = 1;
                return;
            }
            i2 = 3;
        }
        f24587a = i2;
    }

    public static int a() {
        return f24587a;
    }

    public static void a(int i2) {
        f24587a = i2;
    }

    /* renamed from: a, reason: collision with other method in class */
    public static boolean m193a() {
        return f24587a == 2;
    }

    public static boolean b() {
        return f24587a == 3;
    }
}
