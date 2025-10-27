package com.beizi.fusion.g;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class an {

    /* renamed from: a, reason: collision with root package name */
    private static volatile an f5154a;

    /* renamed from: b, reason: collision with root package name */
    private Map<String, Long> f5155b = new HashMap();

    public static an a() {
        if (f5154a == null) {
            synchronized (an.class) {
                if (f5154a == null) {
                    f5154a = new an();
                }
            }
        }
        return f5154a;
    }

    public long b(String str) {
        if (this.f5155b.containsKey(str)) {
            return this.f5155b.get(str).longValue();
        }
        return 0L;
    }

    public void a(String str, long j2) {
        this.f5155b.put(str, Long.valueOf(j2));
    }

    public void a(String str) {
        if (this.f5155b.containsKey(str)) {
            this.f5155b.remove(str);
        }
    }
}
