package com.cmic.sso.sdk.d;

import android.text.TextUtils;
import com.mobile.auth.l.o;
import java.util.HashMap;

/* loaded from: classes3.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private static C0104a<String, String> f6390a = new C0104a<>();

    /* renamed from: com.cmic.sso.sdk.d.a$a, reason: collision with other inner class name */
    public static class C0104a<K, V> extends HashMap<K, V> {
        private C0104a() {
        }
    }

    public static void a(String str) {
        try {
            String str2 = f6390a.get(str);
            f6390a.put(str, String.valueOf((!TextUtils.isEmpty(str2) ? Integer.parseInt(str2) : 0) + 1));
            f6390a.put(str + "Time", o.a());
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static void a(String str, String str2) {
        f6390a.put(str, str2);
    }
}
