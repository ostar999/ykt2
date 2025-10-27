package com.ta.utdid2.a.a;

import com.ta.a.e.h;

/* loaded from: classes6.dex */
public class g {
    public static String get(String str, String str2) throws ClassNotFoundException {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            return (String) cls.getMethod("get", String.class, String.class).invoke(cls, str, str2);
        } catch (Exception e2) {
            h.b("", e2, new Object[0]);
            return str2;
        }
    }
}
