package com.huawei.hms.framework.network.grs.local.model;

import android.text.TextUtils;
import com.huawei.hms.framework.common.Logger;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes4.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private String f7702a;

    /* renamed from: b, reason: collision with root package name */
    private final Map<String, c> f7703b = new ConcurrentHashMap(16);

    public c a(String str) {
        if (!TextUtils.isEmpty(str)) {
            return this.f7703b.get(str);
        }
        Logger.w("ApplicationBean", "In getServing(String serviceName), the serviceName is Empty or null");
        return null;
    }

    public void a() {
        Map<String, c> map = this.f7703b;
        if (map != null) {
            map.clear();
        }
    }

    public void a(long j2) {
    }

    public void a(String str, c cVar) {
        if (TextUtils.isEmpty(str) || cVar == null) {
            return;
        }
        this.f7703b.put(str, cVar);
    }

    public String b() {
        return this.f7702a;
    }

    public void b(String str) {
        this.f7702a = str;
    }
}
