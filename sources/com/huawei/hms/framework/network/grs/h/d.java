package com.huawei.hms.framework.network.grs.h;

import android.os.SystemClock;
import com.huawei.hms.framework.common.Logger;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes4.dex */
public class d {

    /* renamed from: a, reason: collision with root package name */
    private static final Map<String, a> f7697a = new ConcurrentHashMap(16);

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        private final long f7698a;

        /* renamed from: b, reason: collision with root package name */
        private final long f7699b;

        public a(long j2, long j3) {
            this.f7698a = j2;
            this.f7699b = j3;
        }

        public boolean a() {
            return SystemClock.elapsedRealtime() - this.f7699b <= this.f7698a;
        }
    }

    public static a a(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("map size of get is before:");
        Map<String, a> map = f7697a;
        sb.append(map.size());
        Logger.v("RequestUtil", sb.toString());
        a aVar = map.get(str);
        Logger.v("RequestUtil", "map size of get is after:" + map.size());
        return aVar;
    }

    public static void a(String str, a aVar) {
        StringBuilder sb = new StringBuilder();
        sb.append("map size of put is before:");
        Map<String, a> map = f7697a;
        sb.append(map.size());
        Logger.v("RequestUtil", sb.toString());
        map.put(str, aVar);
        Logger.v("RequestUtil", "map size of put is after:" + map.size());
    }
}
