package com.huawei.hms.hatool;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/* loaded from: classes4.dex */
public final class i {

    /* renamed from: b, reason: collision with root package name */
    public static Map<String, m> f7743b = new HashMap();

    /* renamed from: c, reason: collision with root package name */
    public static i f7744c;

    /* renamed from: a, reason: collision with root package name */
    public l f7745a = new l();

    public static i c() {
        if (f7744c == null) {
            d();
        }
        return f7744c;
    }

    public static synchronized void d() {
        if (f7744c == null) {
            f7744c = new i();
        }
    }

    public m a(String str) {
        return f7743b.get(str);
    }

    public Set<String> a() {
        return f7743b.keySet();
    }

    public void a(String str, m mVar) {
        f7743b.put(str, mVar);
    }

    public l b() {
        return this.f7745a;
    }
}
