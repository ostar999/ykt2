package com.huawei.hms.framework.network.grs.e;

import android.content.Context;
import android.text.TextUtils;
import cn.hutool.core.lang.RegexPool;
import com.huawei.hms.framework.common.Logger;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.huawei.hms.framework.network.grs.GrsBaseInfo;
import com.huawei.hms.framework.network.grs.g.d;
import com.huawei.hms.framework.network.grs.g.h;
import com.huawei.hms.framework.network.grs.h.e;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes4.dex */
public class a {

    /* renamed from: f, reason: collision with root package name */
    private static final String f7595f = "a";

    /* renamed from: a, reason: collision with root package name */
    private final Map<String, Map<String, Map<String, String>>> f7596a = new ConcurrentHashMap(16);

    /* renamed from: b, reason: collision with root package name */
    private final Map<String, Long> f7597b = new ConcurrentHashMap(16);

    /* renamed from: c, reason: collision with root package name */
    private final c f7598c;

    /* renamed from: d, reason: collision with root package name */
    private final c f7599d;

    /* renamed from: e, reason: collision with root package name */
    private final h f7600e;

    public a(c cVar, c cVar2, h hVar) {
        this.f7599d = cVar2;
        this.f7598c = cVar;
        this.f7600e = hVar;
        hVar.a(this);
    }

    private void a(GrsBaseInfo grsBaseInfo, b bVar, Context context, String str) {
        Long l2 = this.f7597b.get(grsBaseInfo.getGrsParasKey(true, true, context));
        if (e.a(l2)) {
            bVar.a(2);
            return;
        }
        if (e.a(l2, 300000L)) {
            this.f7600e.a(new com.huawei.hms.framework.network.grs.g.k.c(grsBaseInfo, context), null, str, this.f7599d);
        }
        bVar.a(1);
    }

    private void a(GrsBaseInfo grsBaseInfo, String str, Context context) {
        if (e.a(this.f7597b.get(str), 300000L)) {
            this.f7600e.a(new com.huawei.hms.framework.network.grs.g.k.c(grsBaseInfo, context), null, null, this.f7599d);
        }
    }

    public c a() {
        return this.f7598c;
    }

    public Map<String, String> a(GrsBaseInfo grsBaseInfo, String str, b bVar, Context context) {
        Map<String, Map<String, String>> map = this.f7596a.get(grsBaseInfo.getGrsParasKey(true, true, context));
        if (map == null || map.isEmpty()) {
            return new HashMap();
        }
        a(grsBaseInfo, bVar, context, str);
        return map.get(str);
    }

    public void a(GrsBaseInfo grsBaseInfo, Context context) {
        String grsParasKey = grsBaseInfo.getGrsParasKey(true, true, context);
        this.f7598c.b(grsParasKey + CrashHianalyticsData.TIME, "0");
        this.f7597b.remove(grsParasKey + CrashHianalyticsData.TIME);
        this.f7596a.remove(grsParasKey);
        this.f7600e.a(grsParasKey);
    }

    public void a(GrsBaseInfo grsBaseInfo, d dVar, Context context, com.huawei.hms.framework.network.grs.g.k.c cVar) {
        if (dVar.f() == 2) {
            Logger.w(f7595f, "update cache from server failed");
            return;
        }
        if (cVar.d().size() != 0) {
            this.f7598c.b("geoipCountryCode", dVar.j());
            this.f7598c.b("geoipCountryCodetime", dVar.a());
            return;
        }
        String grsParasKey = grsBaseInfo.getGrsParasKey(true, true, context);
        if (dVar.m()) {
            this.f7596a.put(grsParasKey, com.huawei.hms.framework.network.grs.a.a(this.f7598c.a(grsParasKey, "")));
        } else {
            this.f7598c.b(grsParasKey, dVar.j());
            this.f7596a.put(grsParasKey, com.huawei.hms.framework.network.grs.a.a(dVar.j()));
        }
        if (!TextUtils.isEmpty(dVar.e())) {
            this.f7598c.b(grsParasKey + "ETag", dVar.e());
        }
        this.f7598c.b(grsParasKey + CrashHianalyticsData.TIME, dVar.a());
        this.f7597b.put(grsParasKey, Long.valueOf(Long.parseLong(dVar.a())));
    }

    public h b() {
        return this.f7600e;
    }

    public void b(GrsBaseInfo grsBaseInfo, Context context) {
        String grsParasKey = grsBaseInfo.getGrsParasKey(true, true, context);
        String strA = this.f7598c.a(grsParasKey, "");
        String strA2 = this.f7598c.a(grsParasKey + CrashHianalyticsData.TIME, "0");
        long j2 = 0;
        if (!TextUtils.isEmpty(strA2) && strA2.matches(RegexPool.NUMBERS)) {
            try {
                j2 = Long.parseLong(strA2);
            } catch (NumberFormatException e2) {
                Logger.w(f7595f, "convert urlParamKey from String to Long catch NumberFormatException.", e2);
            }
        }
        this.f7596a.put(grsParasKey, com.huawei.hms.framework.network.grs.a.a(strA));
        this.f7597b.put(grsParasKey, Long.valueOf(j2));
        a(grsBaseInfo, grsParasKey, context);
    }

    public c c() {
        return this.f7599d;
    }
}
