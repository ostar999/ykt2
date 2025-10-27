package com.tencent.smtt.utils;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes6.dex */
public class f {

    /* renamed from: b, reason: collision with root package name */
    private static f f21543b;

    /* renamed from: a, reason: collision with root package name */
    private final Map<Integer, String> f21544a;

    private f() {
        HashMap map = new HashMap();
        this.f21544a = map;
        map.put(325, "if NoSuchMethodError, please check the Class in base.apk which is conflict with x5.");
        map.put(326, "your CPU is i686|mips|x86_64? sorry X5 is not support them.");
        map.put(402, "QbSdk.forceSysWebView() has been called! Check it.");
        map.put(404, "try to restart your app.");
    }

    public static f a() {
        if (f21543b == null) {
            f21543b = new f();
        }
        return f21543b;
    }

    public String a(int i2) {
        if (i2 >= 303 && i2 <= 324) {
            return "Core has some problem, try to reinstall the app.";
        }
        try {
            return this.f21544a.get(Integer.valueOf(i2));
        } catch (Exception unused) {
            return "Unexpected load error.";
        }
    }
}
