package com.alipay.security.mobile.module.c;

import android.content.Context;
import java.util.HashMap;

/* loaded from: classes2.dex */
public class a {
    public static String a(Context context, String str, String str2) {
        String strA;
        synchronized (a.class) {
            String strB = null;
            if (context != null) {
                if (!com.alipay.security.mobile.module.a.a.a(str) && !com.alipay.security.mobile.module.a.a.a(str2)) {
                    try {
                        strA = e.a(context, str, str2, "");
                    } catch (Throwable unused) {
                    }
                    if (com.alipay.security.mobile.module.a.a.a(strA)) {
                        return null;
                    }
                    strB = com.alipay.security.mobile.module.a.a.c.b(com.alipay.security.mobile.module.a.a.c.a(), strA);
                    return strB;
                }
            }
            return null;
        }
    }

    public static void a(Context context, String str, String str2, String str3) {
        synchronized (a.class) {
            if (com.alipay.security.mobile.module.a.a.a(str) || com.alipay.security.mobile.module.a.a.a(str2) || context == null) {
                return;
            }
            try {
                String strA = com.alipay.security.mobile.module.a.a.c.a(com.alipay.security.mobile.module.a.a.c.a(), str3);
                HashMap map = new HashMap();
                map.put(str2, strA);
                e.a(context, str, map);
            } catch (Throwable unused) {
            }
        }
    }
}
