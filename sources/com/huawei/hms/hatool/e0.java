package com.huawei.hms.hatool;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public final class e0 {

    /* renamed from: b, reason: collision with root package name */
    public static e0 f7719b;

    /* renamed from: a, reason: collision with root package name */
    public volatile Map<String, f0> f7720a = new HashMap();

    public static e0 a() {
        if (f7719b == null) {
            b();
        }
        return f7719b;
    }

    public static synchronized void b() {
        if (f7719b == null) {
            f7719b = new e0();
        }
    }

    public final f0 a(String str) {
        if (!this.f7720a.containsKey(str)) {
            this.f7720a.put(str, new f0());
        }
        return this.f7720a.get(str);
    }

    public f0 a(String str, long j2) {
        f0 f0VarA = a(str);
        f0VarA.a(j2);
        return f0VarA;
    }
}
