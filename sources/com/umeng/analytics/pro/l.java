package com.umeng.analytics.pro;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Process;
import android.text.TextUtils;
import cn.hutool.core.text.StrPool;
import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.pro.e;
import com.umeng.analytics.pro.i;
import com.umeng.analytics.vshelper.PageNameMonitor;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.config.FieldManager;
import com.umeng.commonsdk.debug.UMLog;
import com.umeng.commonsdk.debug.UMRTLog;
import com.umeng.commonsdk.statistics.common.DeviceConfig;
import com.umeng.commonsdk.utils.UMUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class l {

    /* renamed from: a, reason: collision with root package name */
    public static String f22826a;

    /* renamed from: b, reason: collision with root package name */
    boolean f22835b;

    /* renamed from: c, reason: collision with root package name */
    boolean f22836c;

    /* renamed from: f, reason: collision with root package name */
    com.umeng.analytics.vshelper.a f22837f;

    /* renamed from: g, reason: collision with root package name */
    Application.ActivityLifecycleCallbacks f22838g;

    /* renamed from: h, reason: collision with root package name */
    private final Map<String, Long> f22839h;

    /* renamed from: l, reason: collision with root package name */
    private boolean f22840l;

    /* renamed from: m, reason: collision with root package name */
    private int f22841m;

    /* renamed from: n, reason: collision with root package name */
    private int f22842n;

    /* renamed from: i, reason: collision with root package name */
    private static JSONArray f22829i = new JSONArray();

    /* renamed from: j, reason: collision with root package name */
    private static Object f22830j = new Object();

    /* renamed from: k, reason: collision with root package name */
    private static Application f22831k = null;

    /* renamed from: d, reason: collision with root package name */
    static String f22827d = null;

    /* renamed from: e, reason: collision with root package name */
    static int f22828e = -1;

    /* renamed from: o, reason: collision with root package name */
    private static boolean f22832o = true;

    /* renamed from: p, reason: collision with root package name */
    private static Object f22833p = new Object();

    /* renamed from: q, reason: collision with root package name */
    private static ar f22834q = new com.umeng.analytics.vshelper.b();

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        private static final l f22844a = new l();

        private a() {
        }
    }

    public static /* synthetic */ int a(l lVar) {
        int i2 = lVar.f22842n;
        lVar.f22842n = i2 - 1;
        return i2;
    }

    public static /* synthetic */ int b(l lVar) {
        int i2 = lVar.f22841m;
        lVar.f22841m = i2 - 1;
        return i2;
    }

    public static /* synthetic */ int e(l lVar) {
        int i2 = lVar.f22842n;
        lVar.f22842n = i2 + 1;
        return i2;
    }

    public static /* synthetic */ int f(l lVar) {
        int i2 = lVar.f22841m;
        lVar.f22841m = i2 + 1;
        return i2;
    }

    private void g() {
        if (this.f22840l) {
            return;
        }
        this.f22840l = true;
        if (f22831k != null) {
            f22831k.registerActivityLifecycleCallbacks(this.f22838g);
        }
    }

    private l() {
        this.f22839h = new HashMap();
        this.f22840l = false;
        this.f22835b = false;
        this.f22836c = false;
        this.f22841m = 0;
        this.f22842n = 0;
        this.f22837f = PageNameMonitor.getInstance();
        this.f22838g = new Application.ActivityLifecycleCallbacks() { // from class: com.umeng.analytics.pro.l.1
            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityCreated(Activity activity, Bundle bundle) {
                l.f22834q.a(activity, bundle);
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityDestroyed(Activity activity) {
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityPaused(Activity activity) {
                if (FieldManager.allow(com.umeng.commonsdk.utils.b.F)) {
                    UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> onActivityPaused: FirstResumeTrigger enabled.");
                    synchronized (l.f22833p) {
                        if (l.f22832o) {
                            return;
                        }
                    }
                } else {
                    UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> onActivityPaused: FirstResumeTrigger disabled.");
                }
                if (UMConfigure.AUTO_ACTIVITY_PAGE_COLLECTION != MobclickAgent.PageMode.AUTO) {
                    if (UMConfigure.AUTO_ACTIVITY_PAGE_COLLECTION == MobclickAgent.PageMode.MANUAL) {
                        com.umeng.analytics.b.a().i();
                    }
                } else {
                    l.this.c(activity);
                    com.umeng.analytics.b.a().i();
                    l.this.f22835b = false;
                    l.f22834q.d(activity);
                }
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityResumed(Activity activity) {
                if (FieldManager.allow(com.umeng.commonsdk.utils.b.F)) {
                    UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> onActivityResumed: FirstResumeTrigger enabled.");
                    synchronized (l.f22833p) {
                        if (l.f22832o) {
                            boolean unused = l.f22832o = false;
                        }
                    }
                    l.this.a(activity);
                } else {
                    UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> onActivityResumed: FirstResumeTrigger disabled.");
                    l.this.a(activity);
                }
                l.f22834q.c(activity);
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityStarted(Activity activity) {
                if (activity != null) {
                    if (l.this.f22841m <= 0) {
                        if (l.f22827d == null) {
                            l.f22827d = UUID.randomUUID().toString();
                        }
                        if (l.f22828e == -1) {
                            l.f22828e = activity.isTaskRoot() ? 1 : 0;
                        }
                        if (l.f22828e == 0 && UMUtils.isMainProgress(activity)) {
                            HashMap map = new HashMap();
                            map.put("activityName", activity.toString());
                            map.put("pid", Integer.valueOf(Process.myPid()));
                            map.put("isMainProcess", Integer.valueOf(UMUtils.isMainProgress(activity) ? 1 : 0));
                            com.umeng.analytics.b bVarA = com.umeng.analytics.b.a();
                            if (bVarA != null) {
                                bVarA.a((Context) activity, "$$_onUMengEnterForegroundInitError", (Map<String, Object>) map);
                            }
                            l.f22828e = -2;
                            if (UMConfigure.isDebugLog()) {
                                UMLog.mutlInfo(2, j.ar);
                            }
                        } else if (l.f22828e == 1 || !UMUtils.isMainProgress(activity)) {
                            HashMap map2 = new HashMap();
                            map2.put("pairUUID", l.f22827d);
                            map2.put("pid", Integer.valueOf(Process.myPid()));
                            map2.put("isMainProcess", Integer.valueOf(UMUtils.isMainProgress(activity) ? 1 : 0));
                            map2.put("activityName", activity.toString());
                            if (com.umeng.analytics.b.a() != null) {
                                com.umeng.analytics.b.a().a((Context) activity, "$$_onUMengEnterForeground", (Map<String, Object>) map2);
                            }
                        }
                    }
                    if (l.this.f22842n < 0) {
                        l.e(l.this);
                    } else {
                        l.f(l.this);
                    }
                }
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityStopped(Activity activity) {
                MobclickAgent.PageMode pageMode = UMConfigure.AUTO_ACTIVITY_PAGE_COLLECTION;
                MobclickAgent.PageMode pageMode2 = MobclickAgent.PageMode.AUTO;
                if (activity != null) {
                    if (activity.isChangingConfigurations()) {
                        l.a(l.this);
                        return;
                    }
                    l.b(l.this);
                    if (l.this.f22841m <= 0) {
                        if (l.f22828e == 0 && UMUtils.isMainProgress(activity)) {
                            return;
                        }
                        int i2 = l.f22828e;
                        if (i2 == 1 || (i2 == 0 && !UMUtils.isMainProgress(activity))) {
                            HashMap map = new HashMap();
                            map.put("pairUUID", l.f22827d);
                            map.put("reason", "Normal");
                            map.put("pid", Integer.valueOf(Process.myPid()));
                            map.put("isMainProcess", Integer.valueOf(UMUtils.isMainProgress(activity) ? 1 : 0));
                            map.put("activityName", activity.toString());
                            com.umeng.analytics.b bVarA = com.umeng.analytics.b.a();
                            if (bVarA != null) {
                                bVarA.a((Context) activity, "$$_onUMengEnterBackground", (Map<String, Object>) map);
                            }
                            if (l.f22827d != null) {
                                l.f22827d = null;
                            }
                        }
                    }
                }
            }
        };
        synchronized (this) {
            if (f22831k != null) {
                g();
            }
        }
    }

    public void c() {
        c((Activity) null);
        b();
    }

    public void b(Context context) {
        synchronized (f22833p) {
            if (f22832o) {
                f22832o = false;
                Activity globleActivity = DeviceConfig.getGlobleActivity(context);
                if (globleActivity == null) {
                    UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> init触发onResume: 无前台Activity，直接退出。");
                    return;
                }
                UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> init触发onResume: 补救成功，前台Activity名：" + globleActivity.getLocalClassName());
                a(globleActivity);
                return;
            }
            UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> init触发onResume: firstResumeCall = false，直接返回。");
        }
    }

    public static void c(Context context) {
        String string;
        if (context != null) {
            try {
                JSONObject jSONObject = new JSONObject();
                synchronized (f22830j) {
                    string = f22829i.toString();
                    f22829i = new JSONArray();
                }
                if (string.length() > 0) {
                    jSONObject.put(e.d.a.f22750c, new JSONArray(string));
                    i.a(context).a(u.a().c(), jSONObject, i.a.AUTOPAGE);
                }
            } catch (Throwable unused) {
            }
        }
    }

    public boolean a() {
        return this.f22840l;
    }

    public static synchronized l a(Context context) {
        if (f22831k == null && context != null) {
            if (context instanceof Activity) {
                f22831k = ((Activity) context).getApplication();
            } else if (context instanceof Application) {
                f22831k = (Application) context;
            }
        }
        return a.f22844a;
    }

    public static void a(Context context, String str) {
        if (f22828e == 1 && UMUtils.isMainProgress(context)) {
            HashMap map = new HashMap();
            map.put("pairUUID", f22827d);
            map.put("reason", str);
            if (f22827d != null) {
                f22827d = null;
            }
            if (context != null) {
                map.put("pid", Integer.valueOf(Process.myPid()));
                map.put("isMainProcess", Integer.valueOf(UMUtils.isMainProgress(context) ? 1 : 0));
                map.put("Context", context.toString());
                com.umeng.analytics.b.a().a(context, "$$_onUMengEnterBackground", (Map<String, Object>) map);
            }
        }
    }

    public void b() {
        this.f22840l = false;
        if (f22831k != null) {
            f22831k.unregisterActivityLifecycleCallbacks(this.f22838g);
            f22831k = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(Activity activity) {
        long jLongValue;
        long jCurrentTimeMillis;
        try {
            synchronized (this.f22839h) {
                if (f22826a == null && activity != null) {
                    f22826a = activity.getPackageName() + StrPool.DOT + activity.getLocalClassName();
                }
                if (TextUtils.isEmpty(f22826a) || !this.f22839h.containsKey(f22826a)) {
                    jLongValue = 0;
                    jCurrentTimeMillis = 0;
                } else {
                    jLongValue = this.f22839h.get(f22826a).longValue();
                    jCurrentTimeMillis = System.currentTimeMillis() - jLongValue;
                    this.f22839h.remove(f22826a);
                }
            }
            synchronized (f22830j) {
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put(d.f22700v, f22826a);
                    jSONObject.put("duration", jCurrentTimeMillis);
                    jSONObject.put(d.f22702x, jLongValue);
                    jSONObject.put("type", 0);
                    f22829i.put(jSONObject);
                } catch (Throwable unused) {
                }
            }
        } catch (Throwable unused2) {
        }
    }

    private void b(Activity activity) {
        f22826a = activity.getPackageName() + StrPool.DOT + activity.getLocalClassName();
        synchronized (this.f22839h) {
            this.f22839h.put(f22826a, Long.valueOf(System.currentTimeMillis()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Activity activity) {
        if (UMConfigure.AUTO_ACTIVITY_PAGE_COLLECTION != MobclickAgent.PageMode.AUTO) {
            if (UMConfigure.AUTO_ACTIVITY_PAGE_COLLECTION == MobclickAgent.PageMode.MANUAL) {
                synchronized (f22833p) {
                    com.umeng.analytics.b.a().h();
                }
                return;
            }
            return;
        }
        if (activity != null) {
            String str = activity.getPackageName() + StrPool.DOT + activity.getLocalClassName();
            this.f22837f.activityResume(str);
            if (this.f22835b) {
                this.f22835b = false;
                if (!TextUtils.isEmpty(f22826a)) {
                    if (f22826a.equals(str)) {
                        return;
                    }
                    b(activity);
                    synchronized (f22833p) {
                        com.umeng.analytics.b.a().h();
                    }
                    return;
                }
                f22826a = str;
                return;
            }
            b(activity);
            synchronized (f22833p) {
                com.umeng.analytics.b.a().h();
            }
        }
    }
}
