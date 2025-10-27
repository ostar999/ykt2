package com.huawei.hms.framework.network.grs.local.model;

import android.text.TextUtils;
import com.huawei.hms.framework.common.Logger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes4.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    private String f7706a;

    /* renamed from: b, reason: collision with root package name */
    private final Map<String, d> f7707b = new ConcurrentHashMap(16);

    /* renamed from: c, reason: collision with root package name */
    private List<b> f7708c = new ArrayList(16);

    public d a(String str) {
        if (!TextUtils.isEmpty(str)) {
            return this.f7707b.get(str);
        }
        Logger.w("Service", "In servings.getServing(String groupId), the groupId is Empty or null");
        return null;
    }

    public List<b> a() {
        return this.f7708c;
    }

    public void a(String str, d dVar) {
        if (TextUtils.isEmpty(str) || dVar == null) {
            return;
        }
        this.f7707b.put(str, dVar);
    }

    public void a(List<b> list) {
        this.f7708c = list;
    }

    public String b() {
        return this.f7706a;
    }

    public void b(String str) {
    }

    public void c(String str) {
        this.f7706a = str;
    }
}
