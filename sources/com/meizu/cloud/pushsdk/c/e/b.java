package com.meizu.cloud.pushsdk.c.e;

import android.content.Context;
import android.location.Location;
import android.os.Build;
import com.aliyun.vod.log.struct.AliyunLogKey;
import com.meizu.cloud.pushsdk.c.f.e;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    private static String f9396a = "b";

    /* renamed from: b, reason: collision with root package name */
    private HashMap<String, String> f9397b;

    /* renamed from: c, reason: collision with root package name */
    private HashMap<String, Object> f9398c;

    /* renamed from: d, reason: collision with root package name */
    private HashMap<String, String> f9399d;

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        private Context f9400a = null;

        public a a(Context context) {
            this.f9400a = context;
            return this;
        }

        public b a() {
            return new b(this);
        }
    }

    private b(a aVar) {
        this.f9397b = new HashMap<>();
        this.f9398c = new HashMap<>();
        this.f9399d = new HashMap<>();
        d();
        e();
        f();
        g();
        if (aVar.f9400a != null) {
            a(aVar.f9400a);
        }
        com.meizu.cloud.pushsdk.c.f.c.c(f9396a, "Subject created successfully.", new Object[0]);
    }

    private void a(String str, Object obj) {
        if ((str == null || obj == null || str.isEmpty()) && (!(obj instanceof String) || ((String) obj).isEmpty())) {
            return;
        }
        this.f9398c.put(str, obj);
    }

    private void a(String str, String str2) {
        if (str == null || str2 == null || str.isEmpty() || str2.isEmpty()) {
            return;
        }
        this.f9399d.put(str, str2);
    }

    private void d() {
        a("ot", "android-" + Build.VERSION.RELEASE);
    }

    private void e() {
        a(AliyunLogKey.KEY_OSVERSION, Build.DISPLAY);
    }

    private void f() {
        a(AliyunLogKey.KEY_DEVICE_MODEL, Build.MODEL);
    }

    private void g() {
        a("df", Build.MANUFACTURER);
    }

    public Map<String, Object> a() {
        return this.f9398c;
    }

    public void a(Context context) {
        b(context);
        c(context);
    }

    public Map<String, String> b() {
        return this.f9399d;
    }

    public void b(Context context) {
        Location locationC = e.c(context);
        if (locationC == null) {
            com.meizu.cloud.pushsdk.c.f.c.a(f9396a, "Location information not available.", new Object[0]);
            return;
        }
        a("la", Double.valueOf(locationC.getLatitude()));
        a("lt", Double.valueOf(locationC.getLongitude()));
        a("al", Double.valueOf(locationC.getAltitude()));
        a("lla", Float.valueOf(locationC.getAccuracy()));
        a("speed", Float.valueOf(locationC.getSpeed()));
        a("br", Float.valueOf(locationC.getBearing()));
    }

    public Map<String, String> c() {
        return this.f9397b;
    }

    public void c(Context context) {
        String strB = e.b(context);
        if (strB != null) {
            a(AliyunLogKey.KEY_CARRIER, strB);
        }
    }
}
