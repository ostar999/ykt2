package com.huawei.hms.hatool;

import android.content.Context;
import android.util.Pair;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class k0 implements n0 {

    /* renamed from: a, reason: collision with root package name */
    public Context f7790a = b.i();

    /* renamed from: b, reason: collision with root package name */
    public String f7791b;

    /* renamed from: c, reason: collision with root package name */
    public String f7792c;

    /* renamed from: d, reason: collision with root package name */
    public String f7793d;

    public k0(String str, String str2, String str3) {
        this.f7791b = str;
        this.f7792c = str2;
        this.f7793d = str3;
    }

    public final void a(String str, List<q> list) {
        Pair<String, String> pairA = u0.a(str);
        new t(list, (String) pairA.first, (String) pairA.second, this.f7793d).a();
    }

    @Override // java.lang.Runnable
    public void run() {
        Map<String, List<q>> mapA;
        y.c("hmsSdk", "eventReportTask is running");
        boolean zA = q0.a(this.f7790a);
        if (zA) {
            y.c("hmsSdk", "workKey is refresh,begin report all data");
            this.f7792c = "alltype";
        }
        try {
            try {
                mapA = w.a(this.f7790a, this.f7791b, this.f7792c);
            } catch (IllegalArgumentException e2) {
                y.e("hmsSdk", "readEventRecords handData IllegalArgumentException:" + e2.getMessage());
                if ("alltype".equals(this.f7792c)) {
                    g0.a(this.f7790a, "stat_v2_1", new String[0]);
                    g0.a(this.f7790a, "cached_v2_1", new String[0]);
                } else {
                    String strA = u0.a(this.f7791b, this.f7792c);
                    g0.a(this.f7790a, "stat_v2_1", strA);
                    g0.a(this.f7790a, "cached_v2_1", strA);
                }
            } catch (Exception e3) {
                y.e("hmsSdk", "readEventRecords handData Exception:" + e3.getMessage());
                if ("alltype".equals(this.f7792c)) {
                    g0.a(this.f7790a, "stat_v2_1", new String[0]);
                    g0.a(this.f7790a, "cached_v2_1", new String[0]);
                } else {
                    String strA2 = u0.a(this.f7791b, this.f7792c);
                    g0.a(this.f7790a, "stat_v2_1", strA2);
                    g0.a(this.f7790a, "cached_v2_1", strA2);
                }
            }
            if (mapA.size() == 0) {
                y.b("hmsSdk", "no have events to report: tag:%s : type:%s", this.f7791b, this.f7792c);
                if ("alltype".equals(this.f7792c)) {
                    g0.a(this.f7790a, "stat_v2_1", new String[0]);
                    g0.a(this.f7790a, "cached_v2_1", new String[0]);
                    return;
                } else {
                    String strA3 = u0.a(this.f7791b, this.f7792c);
                    g0.a(this.f7790a, "stat_v2_1", strA3);
                    g0.a(this.f7790a, "cached_v2_1", strA3);
                    return;
                }
            }
            for (Map.Entry<String, List<q>> entry : mapA.entrySet()) {
                a(entry.getKey(), entry.getValue());
            }
            if ("alltype".equals(this.f7792c)) {
                g0.a(this.f7790a, "stat_v2_1", new String[0]);
                g0.a(this.f7790a, "cached_v2_1", new String[0]);
            } else {
                String strA4 = u0.a(this.f7791b, this.f7792c);
                g0.a(this.f7790a, "stat_v2_1", strA4);
                g0.a(this.f7790a, "cached_v2_1", strA4);
            }
            if (zA) {
                y.c("hmsSdk", "refresh local key");
                d0.f().b();
            }
        } catch (Throwable th) {
            if ("alltype".equals(this.f7792c)) {
                g0.a(this.f7790a, "stat_v2_1", new String[0]);
                g0.a(this.f7790a, "cached_v2_1", new String[0]);
            } else {
                String strA5 = u0.a(this.f7791b, this.f7792c);
                g0.a(this.f7790a, "stat_v2_1", strA5);
                g0.a(this.f7790a, "cached_v2_1", strA5);
            }
            throw th;
        }
    }
}
