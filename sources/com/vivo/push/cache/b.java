package com.vivo.push.cache;

import android.content.Context;
import com.vivo.push.util.p;
import java.lang.reflect.Method;

/* loaded from: classes6.dex */
public final class b {

    /* renamed from: a, reason: collision with root package name */
    private static volatile b f24297a;

    /* renamed from: b, reason: collision with root package name */
    private d f24298b;

    private b() {
    }

    public static synchronized b a() {
        if (f24297a == null) {
            f24297a = new b();
        }
        return f24297a;
    }

    public final d a(Context context) {
        d dVar = this.f24298b;
        if (dVar != null) {
            return dVar;
        }
        try {
            Method method = ClientConfigManagerImpl.class.getMethod("getInstance", Context.class);
            p.d("ConfigManagerFactory", "createConfig success is ".concat("com.vivo.push.cache.ClientConfigManagerImpl"));
            d dVar2 = (d) method.invoke(null, context);
            this.f24298b = dVar2;
            return dVar2;
        } catch (Exception e2) {
            e2.printStackTrace();
            p.b("ConfigManagerFactory", "createConfig error", e2);
            return null;
        }
    }
}
