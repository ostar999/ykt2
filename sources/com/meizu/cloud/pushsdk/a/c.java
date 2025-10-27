package com.meizu.cloud.pushsdk.a;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Point;
import android.location.Location;
import android.os.Build;
import android.text.TextUtils;
import android.view.Display;
import android.view.WindowManager;
import cn.hutool.core.text.StrPool;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.c.f.e;
import com.meizu.cloud.pushsdk.util.MzSystemUtils;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    public static final String f8962a = "c";

    /* renamed from: b, reason: collision with root package name */
    private HashMap<String, String> f8963b;

    /* renamed from: c, reason: collision with root package name */
    private HashMap<String, String> f8964c;

    /* renamed from: d, reason: collision with root package name */
    private HashMap<String, Object> f8965d;

    /* renamed from: e, reason: collision with root package name */
    private HashMap<String, Object> f8966e;

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        private Context f8967a = null;

        public a a(Context context) {
            this.f8967a = context;
            return this;
        }

        public c a() {
            return new c(this);
        }
    }

    private c(a aVar) throws NoSuchMethodException, SecurityException {
        this.f8963b = new HashMap<>();
        this.f8964c = new HashMap<>();
        this.f8965d = new HashMap<>();
        this.f8966e = new HashMap<>();
        e();
        if (aVar.f8967a != null) {
            a(aVar.f8967a);
        }
        DebugLogger.i(f8962a, "Subject created successfully.");
    }

    private void a(String str, Object obj) {
        if ((TextUtils.isEmpty(str) || obj == null) && (!(obj instanceof String) || ((String) obj).isEmpty())) {
            return;
        }
        this.f8965d.put(str, obj);
    }

    private void a(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return;
        }
        this.f8963b.put(str, str2);
    }

    private void b(String str, Object obj) {
        if ((TextUtils.isEmpty(str) || obj == null) && (!(obj instanceof String) || ((String) obj).isEmpty())) {
            return;
        }
        this.f8966e.put(str, obj);
    }

    private void b(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return;
        }
        this.f8964c.put(str, str2);
    }

    private void c(Context context) {
        a(b.f8953r, (Object) context.getPackageName());
        a(b.f8954s, (Object) MzSystemUtils.getAppVersionName(context));
        a(b.f8955t, Integer.valueOf(MzSystemUtils.getAppVersionCode(context)));
        a(b.f8956u, MzSystemUtils.getInstalledPackage(context));
        a(b.f8951p, Integer.valueOf(!TextUtils.isEmpty(MzSystemUtils.findReceiver(context, "com.meizu.ups.push.intent.MESSAGE", context.getPackageName())) ? 1 : 0));
    }

    private void d(Context context) {
        Location locationC = e.c(context);
        if (locationC == null) {
            DebugLogger.e(f8962a, "Location information not available.");
        } else {
            b(b.B, Double.valueOf(locationC.getLongitude()));
            b(b.C, Double.valueOf(locationC.getAltitude()));
        }
    }

    private void e() {
        b(b.f8942g, Build.BRAND);
        b(b.f8943h, Build.MODEL);
        b(b.f8945j, Build.VERSION.RELEASE);
        b(b.f8946k, Build.DISPLAY);
        b(b.f8948m, MzSystemUtils.getCurrentLanguage());
    }

    private void e(Context context) {
        a(b.f8937b, MzSystemUtils.getDeviceId(context));
        a(b.f8938c, MzSystemUtils.getSubscribeId(context));
        a(b.f8940e, MzSystemUtils.getLineNumber(context));
        b(b.f8949n, MzSystemUtils.getOperator(context));
    }

    private void f(Context context) {
        b(b.f8944i, com.meizu.cloud.pushsdk.base.c.b(context));
        b(b.f8961z, (Object) MzSystemUtils.getNetWorkType(context));
        b(b.A, (Object) MzSystemUtils.getBSSID(context));
    }

    public Map<String, String> a() {
        return this.f8963b;
    }

    public void a(int i2, int i3) {
        this.f8964c.put(b.f8947l, Integer.toString(i2) + StrPool.DOT + Integer.toString(i3));
    }

    public void a(Context context) throws NoSuchMethodException, SecurityException {
        d(context);
        e(context);
        b(context);
        f(context);
        c(context);
    }

    public Map<String, String> b() {
        return this.f8964c;
    }

    @TargetApi(19)
    public void b(Context context) throws NoSuchMethodException, SecurityException {
        Display defaultDisplay = null;
        try {
            defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
            Point point = new Point();
            Display.class.getMethod("getSize", Point.class);
            defaultDisplay.getSize(point);
            a(point.x, point.y);
        } catch (Exception unused) {
            String str = f8962a;
            DebugLogger.e(str, "Display.getSize isn't available on older devices.");
            if (defaultDisplay != null) {
                a(defaultDisplay.getWidth(), defaultDisplay.getHeight());
            } else {
                DebugLogger.e(str, "error get display");
            }
        }
    }

    public Map<String, Object> c() {
        return this.f8965d;
    }

    public Map<String, Object> d() {
        return this.f8966e;
    }
}
