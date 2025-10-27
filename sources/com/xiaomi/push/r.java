package com.xiaomi.push;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes6.dex */
public class r {

    /* renamed from: a, reason: collision with root package name */
    private static volatile r f25535a;

    /* renamed from: a, reason: collision with other field name */
    private Context f943a;

    /* renamed from: a, reason: collision with other field name */
    private Handler f944a = new Handler(Looper.getMainLooper());

    /* renamed from: a, reason: collision with other field name */
    private Map<String, Map<String, String>> f945a = new HashMap();

    private r(Context context) {
        this.f943a = context;
    }

    public static r a(Context context) {
        if (f25535a == null) {
            synchronized (r.class) {
                if (f25535a == null) {
                    f25535a = new r(context);
                }
            }
        }
        return f25535a;
    }

    private synchronized String a(String str, String str2) {
        if (this.f945a != null && !TextUtils.isEmpty(str)) {
            if (!TextUtils.isEmpty(str2)) {
                try {
                    Map<String, String> map = this.f945a.get(str);
                    if (map == null) {
                        return "";
                    }
                    return map.get(str2);
                } catch (Throwable unused) {
                    return "";
                }
            }
        }
        return "";
    }

    private synchronized void b(String str, String str2, String str3) {
        if (this.f945a == null) {
            this.f945a = new HashMap();
        }
        Map<String, String> map = this.f945a.get(str);
        if (map == null) {
            map = new HashMap<>();
        }
        map.put(str2, str3);
        this.f945a.put(str, map);
    }

    public synchronized String a(String str, String str2, String str3) {
        String strA = a(str, str2);
        if (!TextUtils.isEmpty(strA)) {
            return strA;
        }
        return this.f943a.getSharedPreferences(str, 4).getString(str2, str3);
    }

    /* renamed from: a, reason: collision with other method in class */
    public synchronized void m682a(String str, String str2, String str3) {
        b(str, str2, str3);
        this.f944a.post(new s(this, str, str2, str3));
    }
}
