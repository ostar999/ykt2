package com.umeng.analytics;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.pro.d;
import com.umeng.analytics.pro.i;
import com.umeng.analytics.pro.j;
import com.umeng.analytics.pro.k;
import com.umeng.analytics.pro.l;
import com.umeng.analytics.pro.m;
import com.umeng.analytics.pro.n;
import com.umeng.analytics.pro.o;
import com.umeng.analytics.pro.p;
import com.umeng.analytics.pro.s;
import com.umeng.analytics.pro.t;
import com.umeng.analytics.pro.u;
import com.umeng.analytics.pro.v;
import com.umeng.analytics.pro.y;
import com.umeng.common.ISysListener;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.config.FieldManager;
import com.umeng.commonsdk.debug.UMLog;
import com.umeng.commonsdk.debug.UMRTLog;
import com.umeng.commonsdk.framework.UMWorkDispatch;
import com.umeng.commonsdk.service.UMGlobalContext;
import com.umeng.commonsdk.statistics.common.DataHelper;
import com.umeng.commonsdk.statistics.common.HelperUtils;
import com.umeng.commonsdk.statistics.common.MLog;
import com.umeng.commonsdk.statistics.internal.PreferenceWrapper;
import com.umeng.commonsdk.utils.UMUtils;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.microedition.khronos.opengles.GL10;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class b implements n, t {
    private static final String A = "umsp_2";
    private static final String B = "umsp_3";
    private static final String C = "umsp_4";
    private static final String D = "umsp_5";

    /* renamed from: a, reason: collision with root package name */
    private static Context f22363a = null;

    /* renamed from: h, reason: collision with root package name */
    private static final String f22364h = "sp_uapp";

    /* renamed from: i, reason: collision with root package name */
    private static final String f22365i = "prepp_uapp";

    /* renamed from: o, reason: collision with root package name */
    private static final int f22366o = 128;

    /* renamed from: p, reason: collision with root package name */
    private static final int f22367p = 256;

    /* renamed from: q, reason: collision with root package name */
    private static String f22368q = "";

    /* renamed from: r, reason: collision with root package name */
    private static String f22369r = "";

    /* renamed from: s, reason: collision with root package name */
    private static final String f22370s = "ekv_bl";

    /* renamed from: t, reason: collision with root package name */
    private static final String f22371t = "ekv_bl_ver";

    /* renamed from: v, reason: collision with root package name */
    private static final String f22372v = "ekv_wl";

    /* renamed from: w, reason: collision with root package name */
    private static final String f22373w = "ekv_wl_ver";

    /* renamed from: z, reason: collision with root package name */
    private static final String f22374z = "umsp_1";

    /* renamed from: b, reason: collision with root package name */
    private ISysListener f22375b;

    /* renamed from: c, reason: collision with root package name */
    private p f22376c;

    /* renamed from: d, reason: collision with root package name */
    private v f22377d;

    /* renamed from: e, reason: collision with root package name */
    private k f22378e;

    /* renamed from: f, reason: collision with root package name */
    private u f22379f;

    /* renamed from: g, reason: collision with root package name */
    private l f22380g;

    /* renamed from: j, reason: collision with root package name */
    private boolean f22381j;

    /* renamed from: k, reason: collision with root package name */
    private volatile JSONObject f22382k;

    /* renamed from: l, reason: collision with root package name */
    private volatile JSONObject f22383l;

    /* renamed from: m, reason: collision with root package name */
    private volatile JSONObject f22384m;

    /* renamed from: n, reason: collision with root package name */
    private boolean f22385n;

    /* renamed from: u, reason: collision with root package name */
    private com.umeng.analytics.filter.a f22386u;

    /* renamed from: x, reason: collision with root package name */
    private com.umeng.analytics.filter.b f22387x;

    /* renamed from: y, reason: collision with root package name */
    private m f22388y;

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        private static final b f22389a = new b();

        private a() {
        }
    }

    static {
        Context appContext = UMGlobalContext.getAppContext();
        if (appContext != null) {
            f22363a = appContext.getApplicationContext();
        }
    }

    public static b a() {
        return a.f22389a;
    }

    private void i(Context context) {
        try {
            if (context == null) {
                MLog.e("unexpected null context in getNativeSuperProperties");
                return;
            }
            if (f22363a == null) {
                f22363a = context.getApplicationContext();
            }
            SharedPreferences sharedPreferences = PreferenceWrapper.getDefault(context);
            if (this.f22382k == null) {
                this.f22382k = new JSONObject();
            }
            if (this.f22383l == null) {
                this.f22383l = new JSONObject();
            }
            String string = sharedPreferences.getString(f22365i, null);
            if (!TextUtils.isEmpty(string)) {
                try {
                    this.f22384m = new JSONObject(string);
                } catch (JSONException unused) {
                }
            }
            if (this.f22384m == null) {
                this.f22384m = new JSONObject();
            }
        } catch (Throwable unused2) {
        }
    }

    public JSONObject b() {
        return this.f22382k;
    }

    public JSONObject c() {
        return this.f22384m;
    }

    public JSONObject d() {
        return this.f22383l;
    }

    public void e() {
        this.f22383l = null;
    }

    public String f() {
        if (UMUtils.isMainProgress(f22363a)) {
            return f22368q;
        }
        MLog.e("getOnResumedActivityName can not be called in child process");
        return null;
    }

    public String g() {
        if (UMUtils.isMainProgress(f22363a)) {
            return f22369r;
        }
        MLog.e("getOnPausedActivityName can not be called in child process");
        return null;
    }

    public void h() {
        try {
            Context context = f22363a;
            if (context != null) {
                if (!UMUtils.isMainProgress(context)) {
                    MLog.e("onStartSessionInternal can not be called in child process");
                    return;
                }
                long jCurrentTimeMillis = System.currentTimeMillis();
                Context context2 = f22363a;
                UMWorkDispatch.sendEvent(context2, 4352, CoreProtocol.getInstance(context2), Long.valueOf(jCurrentTimeMillis));
                Context context3 = f22363a;
                UMWorkDispatch.sendEvent(context3, 4103, CoreProtocol.getInstance(context3), Long.valueOf(jCurrentTimeMillis));
            }
            ISysListener iSysListener = this.f22375b;
            if (iSysListener != null) {
                iSysListener.onAppResume();
            }
        } catch (Throwable unused) {
        }
    }

    public void j() {
        try {
            Context context = f22363a;
            if (context == null) {
                return;
            }
            if (!UMUtils.isMainProgress(context)) {
                MLog.e("onProfileSignOff can not be called in child process");
                return;
            }
            long jCurrentTimeMillis = System.currentTimeMillis();
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("ts", jCurrentTimeMillis);
            Context context2 = f22363a;
            UMWorkDispatch.sendEvent(context2, 4102, CoreProtocol.getInstance(context2), jSONObject);
            Context context3 = f22363a;
            UMWorkDispatch.sendEvent(context3, 4356, CoreProtocol.getInstance(context3), jSONObject);
        } catch (Throwable th) {
            if (MLog.DEBUG) {
                MLog.e(" Excepthon  in  onProfileSignOff", th);
            }
        }
    }

    public synchronized void k() {
        Context context;
        try {
            context = f22363a;
        } catch (Throwable unused) {
        }
        if (context == null) {
            return;
        }
        if (!UMUtils.isMainProgress(context)) {
            MLog.e("unregisterSuperPropertyByCoreProtocol can not be called in child process");
            return;
        }
        if (this.f22382k != null) {
            SharedPreferences.Editor editorEdit = PreferenceWrapper.getDefault(f22363a).edit();
            editorEdit.putString(f22364h, this.f22382k.toString());
            editorEdit.commit();
        } else {
            this.f22382k = new JSONObject();
        }
    }

    public synchronized JSONObject l() {
        Context context;
        try {
            context = f22363a;
        } catch (Throwable unused) {
        }
        if (context == null) {
            return null;
        }
        if (!UMUtils.isMainProgress(context)) {
            MLog.e("getSuperPropertiesJSONObject can not be called in child process");
            return null;
        }
        if (this.f22382k == null) {
            this.f22382k = new JSONObject();
        }
        return this.f22382k;
    }

    public synchronized void m() {
        try {
            Context context = f22363a;
            if (context != null) {
                if (!UMUtils.isMainProgress(context)) {
                    MLog.e("clearSuperPropertiesByCoreProtocol can not be called in child process");
                } else {
                    SharedPreferences.Editor editorEdit = PreferenceWrapper.getDefault(f22363a).edit();
                    editorEdit.remove(f22364h);
                    editorEdit.commit();
                }
            }
        } catch (Throwable unused) {
        }
    }

    @Override // com.umeng.analytics.pro.n
    public void n() {
        UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> onIntoBackground triggered.");
        if (AnalyticsConfig.enable && FieldManager.b()) {
            if (!FieldManager.allow(com.umeng.commonsdk.utils.b.D)) {
                UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> 退出发送策略: 云控控制字关闭。功能不生效");
            } else {
                if (UMWorkDispatch.eventHasExist(8210)) {
                    return;
                }
                UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> 退出时发送策略 被触发！");
                Context context = f22363a;
                UMWorkDispatch.sendEvent(context, 8210, CoreProtocol.getInstance(context), null);
            }
        }
    }

    private b() {
        this.f22376c = new p();
        this.f22377d = new v();
        this.f22378e = new k();
        this.f22379f = u.a();
        this.f22380g = null;
        this.f22381j = false;
        this.f22382k = null;
        this.f22383l = null;
        this.f22384m = null;
        this.f22385n = false;
        this.f22386u = null;
        this.f22387x = null;
        this.f22388y = null;
        this.f22376c.a(this);
    }

    public void a(Context context) {
        if (context == null) {
            return;
        }
        try {
            if (f22363a == null) {
                f22363a = context.getApplicationContext();
            }
            if (this.f22386u == null) {
                com.umeng.analytics.filter.a aVar = new com.umeng.analytics.filter.a("ekv_bl", "ekv_bl_ver");
                this.f22386u = aVar;
                aVar.register(f22363a);
            }
            if (this.f22387x == null) {
                com.umeng.analytics.filter.b bVar = new com.umeng.analytics.filter.b("ekv_wl", "ekv_wl_ver");
                this.f22387x = bVar;
                bVar.register(f22363a);
            }
            if (UMUtils.isMainProgress(f22363a)) {
                if (!this.f22381j) {
                    this.f22381j = true;
                    i(f22363a);
                }
                synchronized (this) {
                    if (!this.f22385n) {
                        l lVarA = l.a(context);
                        this.f22380g = lVarA;
                        if (lVarA.a()) {
                            this.f22385n = true;
                        }
                        this.f22388y = m.a();
                        try {
                            m.a(context);
                            this.f22388y.a(this);
                        } catch (Throwable unused) {
                        }
                    }
                }
                if (UMConfigure.isDebugLog()) {
                    UMLog.mutlInfo(j.B, 3, "", null, null);
                }
                UMWorkDispatch.registerConnStateObserver(CoreProtocol.getInstance(f22363a));
            }
        } catch (Throwable unused2) {
        }
    }

    public void b(String str) {
        if (!UMUtils.isMainProgress(f22363a)) {
            MLog.e("onPageEnd can not be called in child process");
            return;
        }
        try {
            if (UMConfigure.AUTO_ACTIVITY_PAGE_COLLECTION != MobclickAgent.PageMode.LEGACY_AUTO) {
                this.f22377d.b(str);
            }
        } catch (Throwable unused) {
        }
    }

    public void c(Context context) {
        if (context == null) {
            UMLog.aq(j.f22815p, 0, "\\|");
            return;
        }
        if (UMConfigure.AUTO_ACTIVITY_PAGE_COLLECTION == MobclickAgent.PageMode.AUTO) {
            return;
        }
        if (f22363a == null) {
            f22363a = context.getApplicationContext();
        }
        if (!UMUtils.isMainProgress(f22363a)) {
            MLog.e("onPause can not be called in child process");
            return;
        }
        if (UMConfigure.isDebugLog() && !(context instanceof Activity)) {
            UMLog.aq(j.f22816q, 2, "\\|");
        }
        try {
            if (!this.f22381j || !this.f22385n) {
                a(context);
            }
            if (UMConfigure.AUTO_ACTIVITY_PAGE_COLLECTION != MobclickAgent.PageMode.LEGACY_MANUAL) {
                this.f22378e.b(context.getClass().getName());
            }
            i();
        } catch (Throwable th) {
            if (MLog.DEBUG) {
                MLog.e("Exception occurred in Mobclick.onRause(). ", th);
            }
        }
        if (UMConfigure.isDebugLog() && (context instanceof Activity)) {
            f22369r = context.getClass().getName();
        }
    }

    public void d(Context context) {
        if (context == null) {
            return;
        }
        try {
            if (f22363a == null) {
                f22363a = context.getApplicationContext();
            }
            if (!UMUtils.isMainProgress(f22363a)) {
                MLog.e("onKillProcess can not be called in child process");
                return;
            }
            l lVar = this.f22380g;
            if (lVar != null) {
                lVar.c();
            }
            l.a(context, "onKillProcess");
            k kVar = this.f22378e;
            if (kVar != null) {
                kVar.b();
            }
            v vVar = this.f22377d;
            if (vVar != null) {
                vVar.b();
            }
            Context context2 = f22363a;
            if (context2 != null) {
                u uVar = this.f22379f;
                if (uVar != null) {
                    uVar.c(context2, Long.valueOf(System.currentTimeMillis()));
                }
                o.a(f22363a).d();
                v.a(f22363a);
                if (UMConfigure.AUTO_ACTIVITY_PAGE_COLLECTION == MobclickAgent.PageMode.AUTO) {
                    l.c(f22363a);
                }
                PreferenceWrapper.getDefault(f22363a).edit().commit();
            }
        } catch (Throwable unused) {
        }
    }

    public synchronized Object e(Context context, String str) {
        if (context == null) {
            UMLog.aq(j.ai, 0, "\\|");
            return null;
        }
        if (f22363a == null) {
            f22363a = context.getApplicationContext();
        }
        if (!UMUtils.isMainProgress(f22363a)) {
            MLog.e("getSuperProperty can not be called in child process");
            return null;
        }
        if (TextUtils.isEmpty(str)) {
            UMLog.aq(j.ag, 0, "\\|");
            return null;
        }
        if (!str.equals(f22374z) && !str.equals(A) && !str.equals(B) && !str.equals(C) && !str.equals(D)) {
            MLog.e("please check key or value, must be correct!");
            return null;
        }
        if (this.f22382k == null) {
            this.f22382k = new JSONObject();
        } else if (this.f22382k.has(str)) {
            return this.f22382k.opt(str);
        }
        return null;
    }

    public synchronized void f(Context context) {
        if (context == null) {
            UMLog.aq(j.ah, 0, "\\|");
            return;
        }
        if (f22363a == null) {
            f22363a = context.getApplicationContext();
        }
        if (!UMUtils.isMainProgress(f22363a)) {
            MLog.e("clearSuperProperties can not be called in child process");
            return;
        }
        if (!this.f22381j || !this.f22385n) {
            a(f22363a);
        }
        this.f22382k = new JSONObject();
        Context context2 = f22363a;
        UMWorkDispatch.sendEvent(context2, 8196, CoreProtocol.getInstance(context2), null);
    }

    public synchronized void g(Context context) {
        if (context == null) {
            UMLog.aq(j.ap, 0, "\\|");
            return;
        }
        if (f22363a == null) {
            f22363a = context.getApplicationContext();
        }
        if (!UMUtils.isMainProgress(f22363a)) {
            MLog.e("clearPreProperties can not be called in child process");
            return;
        }
        if (!this.f22381j || !this.f22385n) {
            a(f22363a);
        }
        if (this.f22384m.length() > 0) {
            Context context2 = f22363a;
            UMWorkDispatch.sendEvent(context2, 8201, CoreProtocol.getInstance(context2), null);
        }
        this.f22384m = new JSONObject();
    }

    public void b(Context context) {
        if (context == null) {
            MLog.e("unexpected null context in onResume");
            return;
        }
        if (UMConfigure.AUTO_ACTIVITY_PAGE_COLLECTION == MobclickAgent.PageMode.AUTO) {
            return;
        }
        if (f22363a == null) {
            f22363a = context.getApplicationContext();
        }
        if (!UMUtils.isMainProgress(f22363a)) {
            MLog.e("onResume can not be called in child process");
            return;
        }
        if (UMConfigure.isDebugLog() && !(context instanceof Activity)) {
            UMLog.aq(j.f22814o, 2, "\\|");
        }
        try {
            if (!this.f22381j || !this.f22385n) {
                a(context);
            }
            if (UMConfigure.AUTO_ACTIVITY_PAGE_COLLECTION != MobclickAgent.PageMode.LEGACY_MANUAL) {
                this.f22378e.a(context.getClass().getName());
            }
            h();
            if (UMConfigure.isDebugLog() && (context instanceof Activity)) {
                f22368q = context.getClass().getName();
            }
        } catch (Throwable th) {
            MLog.e("Exception occurred in Mobclick.onResume(). ", th);
        }
    }

    public synchronized JSONObject h(Context context) {
        if (context == null) {
            UMLog.aq(j.aq, 0, "\\|");
            return null;
        }
        if (f22363a == null) {
            f22363a = context.getApplicationContext();
        }
        if (!UMUtils.isMainProgress(f22363a)) {
            MLog.e("getPreProperties can not be called in child process");
            return null;
        }
        if (!this.f22381j || !this.f22385n) {
            a(f22363a);
        }
        if (this.f22384m == null) {
            this.f22384m = new JSONObject();
        }
        JSONObject jSONObject = new JSONObject();
        if (this.f22384m.length() > 0) {
            try {
                jSONObject = new JSONObject(this.f22384m.toString());
            } catch (JSONException unused) {
            }
        }
        return jSONObject;
    }

    public void i() {
        try {
            Context context = f22363a;
            if (context != null) {
                if (!UMUtils.isMainProgress(context)) {
                    MLog.e("onEndSessionInternal can not be called in child process");
                    return;
                }
                Context context2 = f22363a;
                UMWorkDispatch.sendEvent(context2, 4104, CoreProtocol.getInstance(context2), Long.valueOf(System.currentTimeMillis()));
                Context context3 = f22363a;
                UMWorkDispatch.sendEvent(context3, 4100, CoreProtocol.getInstance(context3), null);
                Context context4 = f22363a;
                UMWorkDispatch.sendEvent(context4, 4099, CoreProtocol.getInstance(context4), null);
                Context context5 = f22363a;
                UMWorkDispatch.sendEvent(context5, 4105, CoreProtocol.getInstance(context5), null);
            }
        } catch (Throwable unused) {
        }
        ISysListener iSysListener = this.f22375b;
        if (iSysListener != null) {
            iSysListener.onAppPause();
        }
    }

    public synchronized void f(Context context, String str) {
        if (context == null) {
            UMLog.aq(j.an, 0, "\\|");
            return;
        }
        if (f22363a == null) {
            f22363a = context.getApplicationContext();
        }
        if (!UMUtils.isMainProgress(f22363a)) {
            MLog.e("unregisterPreProperty can not be called in child process");
            return;
        }
        if (!this.f22381j || !this.f22385n) {
            a(f22363a);
        }
        if (this.f22384m == null) {
            this.f22384m = new JSONObject();
        }
        if (str != null && str.length() > 0) {
            if (this.f22384m.has(str)) {
                this.f22384m.remove(str);
                Context context2 = f22363a;
                UMWorkDispatch.sendEvent(context2, 8200, CoreProtocol.getInstance(context2), this.f22384m.toString());
            } else if (UMConfigure.isDebugLog()) {
                UMLog.aq(j.ao, 0, "\\|");
            }
            return;
        }
        MLog.e("please check propertics, property is null!");
    }

    private boolean c(String str) {
        if (this.f22386u.enabled() && this.f22386u.matchHit(str)) {
            return true;
        }
        if (!this.f22387x.enabled()) {
            return false;
        }
        if (!this.f22387x.matchHit(str)) {
            return true;
        }
        UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> white list match! id = " + str);
        return false;
    }

    public synchronized void d(Context context, String str) {
        try {
        } catch (Throwable th) {
            th.printStackTrace();
        }
        if (context == null) {
            UMLog.aq(j.ah, 0, "\\|");
            return;
        }
        if (f22363a == null) {
            f22363a = context.getApplicationContext();
        }
        if (!UMUtils.isMainProgress(f22363a)) {
            MLog.e("unregisterSuperProperty can not be called in child process");
            return;
        }
        if (!this.f22381j || !this.f22385n) {
            a(f22363a);
        }
        if (TextUtils.isEmpty(str)) {
            UMLog.aq(j.ag, 0, "\\|");
            return;
        }
        if (!str.equals(f22374z) && !str.equals(A) && !str.equals(B) && !str.equals(C) && !str.equals(D)) {
            MLog.e("please check key or value, must be correct!");
            return;
        }
        if (this.f22382k == null) {
            this.f22382k = new JSONObject();
        }
        if (this.f22382k.has(str)) {
            this.f22382k.remove(str);
            Context context2 = f22363a;
            UMWorkDispatch.sendEvent(context2, 8197, CoreProtocol.getInstance(context2), str);
        }
    }

    public void b(Context context, String str) {
        try {
            if (context == null) {
                UMLog.aq(j.N, 0, "\\|");
                return;
            }
            if (f22363a == null) {
                f22363a = context.getApplicationContext();
            }
            if (!UMUtils.isMainProgress(f22363a)) {
                MLog.e("onDeepLinkReceived can not be called in child process");
                return;
            }
            if (!this.f22381j || !this.f22385n) {
                a(f22363a);
            }
            if (!TextUtils.isEmpty(str)) {
                HashMap map = new HashMap();
                map.put(d.aE, str);
                a(f22363a, d.aD, (Map<String, Object>) map, -1L, false);
                return;
            }
            UMLog.aq(j.O, 0, "\\|");
        } catch (Throwable th) {
            if (MLog.DEBUG) {
                MLog.e(th);
            }
        }
    }

    public synchronized String e(Context context) {
        if (context == null) {
            UMLog.aq(j.ai, 0, "\\|");
            return null;
        }
        if (f22363a == null) {
            f22363a = context.getApplicationContext();
        }
        if (!UMUtils.isMainProgress(f22363a)) {
            MLog.e("getSuperProperties can not be called in child process");
            return null;
        }
        if (this.f22382k != null) {
            return this.f22382k.toString();
        }
        this.f22382k = new JSONObject();
        return null;
    }

    public void c(Context context, String str) {
        if (context == null) {
            UMLog.aq(j.f22825z, 0, "\\|");
            return;
        }
        if (f22363a == null) {
            f22363a = context.getApplicationContext();
        }
        if (!UMUtils.isMainProgress(f22363a)) {
            MLog.e("setSecret can not be called in child process");
            return;
        }
        if (!this.f22381j || !this.f22385n) {
            a(f22363a);
        }
        AnalyticsConfig.a(f22363a, str);
    }

    public void a(String str) {
        if (!UMUtils.isMainProgress(f22363a)) {
            MLog.e("onPageStart can not be called in child process");
            return;
        }
        try {
            if (UMConfigure.AUTO_ACTIVITY_PAGE_COLLECTION != MobclickAgent.PageMode.LEGACY_AUTO) {
                this.f22377d.a(str);
            }
        } catch (Throwable unused) {
        }
    }

    public void a(ISysListener iSysListener) {
        if (!UMUtils.isMainProgress(f22363a)) {
            MLog.e("setSysListener can not be called in child process");
        } else {
            this.f22375b = iSysListener;
        }
    }

    public void a(Context context, int i2) {
        if (context == null) {
            MLog.e("unexpected null context in setVerticalType");
            return;
        }
        if (f22363a == null) {
            f22363a = context.getApplicationContext();
        }
        if (!UMUtils.isMainProgress(f22363a)) {
            MLog.e("setVerticalType can not be called in child process");
            return;
        }
        if (!this.f22381j || !this.f22385n) {
            a(f22363a);
        }
        AnalyticsConfig.a(f22363a, i2);
    }

    public synchronized void b(Object obj) {
        Context context;
        try {
            context = f22363a;
        } catch (Throwable unused) {
        }
        if (context == null) {
            return;
        }
        if (!UMUtils.isMainProgress(context)) {
            MLog.e("updateNativePrePropertiesByCoreProtocol can not be called in child process");
            return;
        }
        SharedPreferences.Editor editorEdit = PreferenceWrapper.getDefault(f22363a).edit();
        if (obj != null) {
            String str = (String) obj;
            if (editorEdit != null && !TextUtils.isEmpty(str)) {
                editorEdit.putString(f22365i, str).commit();
            }
        } else if (editorEdit != null) {
            editorEdit.remove(f22365i).commit();
        }
    }

    public void a(Context context, String str, HashMap<String, Object> map) {
        if (context == null) {
            return;
        }
        try {
            if (f22363a == null) {
                f22363a = context.getApplicationContext();
            }
            if (!UMUtils.isMainProgress(f22363a)) {
                MLog.e("onGKVEvent can not be called in child process");
                return;
            }
            if (!this.f22381j || !this.f22385n) {
                a(f22363a);
            }
            String string = "";
            if (this.f22382k == null) {
                this.f22382k = new JSONObject();
            } else {
                string = this.f22382k.toString();
            }
            s.a(f22363a).a(str, map, string);
        } catch (Throwable th) {
            if (MLog.DEBUG) {
                MLog.e(th);
            }
        }
    }

    private boolean b(String str, Object obj) {
        int length;
        if (TextUtils.isEmpty(str)) {
            MLog.e("key is " + str + ", please check key, illegal");
            return false;
        }
        try {
            length = str.getBytes("UTF-8").length;
        } catch (UnsupportedEncodingException unused) {
            length = 0;
        }
        if (length > 128) {
            MLog.e("key length is " + length + ", please check key, illegal");
            return false;
        }
        if (obj instanceof String) {
            if (((String) obj).getBytes("UTF-8").length <= 256) {
                return true;
            }
            MLog.e("value length is " + ((String) obj).getBytes("UTF-8").length + ", please check value, illegal");
            return false;
        }
        if ((obj instanceof Integer) || (obj instanceof Long) || (obj instanceof Double) || (obj instanceof Float)) {
            return true;
        }
        MLog.e("value is " + obj + ", please check value, type illegal");
        return false;
    }

    public void a(Context context, String str) {
        if (context == null) {
            UMLog.aq(j.f22822w, 0, "\\|");
            return;
        }
        if (f22363a == null) {
            f22363a = context.getApplicationContext();
        }
        if (!UMUtils.isMainProgress(f22363a)) {
            MLog.e("reportError can not be called in child process");
            return;
        }
        if (TextUtils.isEmpty(str)) {
            if (UMConfigure.isDebugLog()) {
                UMLog.aq(j.f22823x, 0, "\\|");
                return;
            }
            return;
        }
        try {
            if (!this.f22381j || !this.f22385n) {
                a(f22363a);
            }
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("ts", System.currentTimeMillis());
            jSONObject.put(d.Q, 2);
            jSONObject.put(d.R, str);
            jSONObject.put("__ii", this.f22379f.c());
            Context context2 = f22363a;
            UMWorkDispatch.sendEvent(context2, 4106, CoreProtocol.getInstance(context2), jSONObject);
        } catch (Throwable th) {
            if (MLog.DEBUG) {
                MLog.e(th);
            }
        }
    }

    public void a(Context context, Throwable th) {
        if (context != null && th != null) {
            if (f22363a == null) {
                f22363a = context.getApplicationContext();
            }
            if (!UMUtils.isMainProgress(f22363a)) {
                MLog.e("reportError can not be called in child process");
                return;
            }
            try {
                if (!this.f22381j || !this.f22385n) {
                    a(f22363a);
                }
                a(f22363a, DataHelper.convertExceptionToString(th));
                return;
            } catch (Exception e2) {
                if (MLog.DEBUG) {
                    MLog.e(e2);
                    return;
                }
                return;
            }
        }
        UMLog.aq(j.f22824y, 0, "\\|");
    }

    public void a(Context context, String str, String str2, long j2, int i2) {
        if (context == null) {
            return;
        }
        try {
            if (f22363a == null) {
                f22363a = context.getApplicationContext();
            }
            if (!this.f22381j || !this.f22385n) {
                a(f22363a);
            }
            if (c(str)) {
                UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> filter ekv [" + str + "].");
                return;
            }
            String string = "";
            if (this.f22382k == null) {
                this.f22382k = new JSONObject();
            } else {
                string = this.f22382k.toString();
            }
            s.a(f22363a).a(str, str2, j2, i2, string);
        } catch (Throwable th) {
            if (MLog.DEBUG) {
                MLog.e(th);
            }
        }
    }

    public void a(Context context, String str, Map<String, Object> map, long j2) {
        try {
        } catch (Throwable th) {
            if (MLog.DEBUG) {
                MLog.e(th);
            }
        }
        if (!TextUtils.isEmpty(str)) {
            if (Arrays.asList(d.aF).contains(str)) {
                UMLog.aq(j.f22801b, 0, "\\|");
                return;
            }
            if (map.isEmpty()) {
                UMLog.aq(j.f22803d, 0, "\\|");
                return;
            }
            Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                if (Arrays.asList(d.aF).contains(it.next().getKey())) {
                    UMLog.aq(j.f22804e, 0, "\\|");
                    return;
                }
            }
            a(context, str, map, j2, false);
            return;
        }
        UMLog.aq(j.f22802c, 0, "\\|");
    }

    public void a(Context context, String str, Map<String, Object> map) {
        a(context, str, map, -1L, true);
    }

    private void a(Context context, String str, Map<String, Object> map, long j2, boolean z2) {
        try {
            if (context == null) {
                MLog.e("context is null in onEventNoCheck, please check!");
                return;
            }
            if (f22363a == null) {
                f22363a = context.getApplicationContext();
            }
            if (!this.f22381j || !this.f22385n) {
                a(f22363a);
            }
            if (c(str)) {
                UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> filter ekv [" + str + "].");
                return;
            }
            String string = "";
            if (this.f22382k == null) {
                this.f22382k = new JSONObject();
            } else {
                string = this.f22382k.toString();
            }
            s.a(f22363a).a(str, map, j2, string, z2);
        } catch (Throwable th) {
            if (MLog.DEBUG) {
                MLog.e(th);
            }
        }
    }

    @Override // com.umeng.analytics.pro.t
    public void a(Throwable th) throws JSONException {
        try {
            Context context = f22363a;
            if (context == null) {
                return;
            }
            if (!UMUtils.isMainProgress(context)) {
                MLog.e("onAppCrash can not be called in child process");
                return;
            }
            if (AnalyticsConfig.enable) {
                v vVar = this.f22377d;
                if (vVar != null) {
                    vVar.b();
                }
                l.a(f22363a, "onAppCrash");
                k kVar = this.f22378e;
                if (kVar != null) {
                    kVar.b();
                }
                l lVar = this.f22380g;
                if (lVar != null) {
                    lVar.c();
                }
                u uVar = this.f22379f;
                if (uVar != null) {
                    uVar.c(f22363a, Long.valueOf(System.currentTimeMillis()));
                }
                if (th != null) {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("ts", System.currentTimeMillis());
                    jSONObject.put(d.Q, 1);
                    jSONObject.put(d.R, DataHelper.convertExceptionToString(th));
                    i.a(f22363a).a(this.f22379f.c(), jSONObject.toString(), 1);
                }
                o.a(f22363a).d();
                v.a(f22363a);
                if (UMConfigure.AUTO_ACTIVITY_PAGE_COLLECTION == MobclickAgent.PageMode.AUTO) {
                    l.c(f22363a);
                }
                PreferenceWrapper.getDefault(f22363a).edit().commit();
            }
        } catch (Exception e2) {
            if (MLog.DEBUG) {
                MLog.e("Exception in onAppCrash", e2);
            }
        }
    }

    public void a(String str, String str2) {
        try {
            Context context = f22363a;
            if (context == null) {
                return;
            }
            if (!UMUtils.isMainProgress(context)) {
                MLog.e("onProfileSignIn can not be called in child process");
                return;
            }
            long jCurrentTimeMillis = System.currentTimeMillis();
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(d.M, str);
            jSONObject.put("uid", str2);
            jSONObject.put("ts", jCurrentTimeMillis);
            Context context2 = f22363a;
            UMWorkDispatch.sendEvent(context2, 4101, CoreProtocol.getInstance(context2), jSONObject);
            Context context3 = f22363a;
            UMWorkDispatch.sendEvent(context3, 4356, CoreProtocol.getInstance(context3), jSONObject);
        } catch (Throwable th) {
            if (MLog.DEBUG) {
                MLog.e(" Excepthon  in  onProfileSignIn", th);
            }
        }
    }

    public void a(boolean z2) {
        Context context = f22363a;
        if (context == null) {
            return;
        }
        if (!UMUtils.isMainProgress(context)) {
            MLog.e("setCatchUncaughtExceptions can not be called in child process");
        } else {
            if (AnalyticsConfig.CHANGE_CATCH_EXCEPTION_NOTALLOW) {
                return;
            }
            AnalyticsConfig.CATCH_EXCEPTION = z2;
        }
    }

    public void a(GL10 gl10) {
        String[] gpu = UMUtils.getGPU(gl10);
        if (gpu.length == 2) {
            AnalyticsConfig.GPU_VENDER = gpu[0];
            AnalyticsConfig.GPU_RENDERER = gpu[1];
        }
    }

    public void a(double d3, double d4) {
        Context context = f22363a;
        if (context == null) {
            return;
        }
        if (!UMUtils.isMainProgress(context)) {
            MLog.e("setLocation can not be called in child process");
            return;
        }
        if (AnalyticsConfig.f22340a == null) {
            AnalyticsConfig.f22340a = new double[2];
        }
        double[] dArr = AnalyticsConfig.f22340a;
        dArr[0] = d3;
        dArr[1] = d4;
    }

    public void a(Context context, MobclickAgent.EScenarioType eScenarioType) {
        if (context == null) {
            MLog.e("unexpected null context in setScenarioType");
            return;
        }
        if (f22363a == null) {
            f22363a = context.getApplicationContext();
        }
        if (!UMUtils.isMainProgress(f22363a)) {
            MLog.e("setScenarioType can not be called in child process");
            return;
        }
        if (eScenarioType != null) {
            a(f22363a, eScenarioType.toValue());
        }
        if (this.f22381j && this.f22385n) {
            return;
        }
        a(f22363a);
    }

    public void a(long j2) {
        Context context = f22363a;
        if (context == null) {
            return;
        }
        if (!UMUtils.isMainProgress(context)) {
            MLog.e("setSessionContinueMillis can not be called in child process");
        } else {
            AnalyticsConfig.kContinueSessionMillis = j2;
            y.a().a(AnalyticsConfig.kContinueSessionMillis);
        }
    }

    public synchronized void a(Context context, String str, Object obj) {
        int i2 = 0;
        if (context == null) {
            UMLog.aq(j.af, 0, "\\|");
            return;
        }
        if (f22363a == null) {
            f22363a = context.getApplicationContext();
        }
        if (!UMUtils.isMainProgress(f22363a)) {
            MLog.e("registerSuperProperty can not be called in child process");
            return;
        }
        if (!this.f22381j || !this.f22385n) {
            a(f22363a);
        }
        if (!TextUtils.isEmpty(str) && obj != null) {
            if (!str.equals(f22374z) && !str.equals(A) && !str.equals(B) && !str.equals(C) && !str.equals(D)) {
                MLog.e("property name is " + str + ", please check key, must be correct!");
                return;
            }
            if ((obj instanceof String) && !HelperUtils.checkStrLen(obj.toString(), 256)) {
                MLog.e("property value is " + obj + ", please check value, lawless!");
                return;
            }
            try {
                if (this.f22382k == null) {
                    this.f22382k = new JSONObject();
                }
                if (obj.getClass().isArray()) {
                    if (obj instanceof String[]) {
                        String[] strArr = (String[]) obj;
                        if (strArr.length > 10) {
                            MLog.e("please check value, size is " + strArr.length + ", overstep 10!");
                            return;
                        }
                        JSONArray jSONArray = new JSONArray();
                        while (i2 < strArr.length) {
                            String str2 = strArr[i2];
                            if (str2 != null && HelperUtils.checkStrLen(str2, 256)) {
                                jSONArray.put(strArr[i2]);
                                i2++;
                            }
                            MLog.e("please check value, length is " + strArr[i2].length() + ", overlength 256!");
                            return;
                        }
                        this.f22382k.put(str, jSONArray);
                    } else if (obj instanceof long[]) {
                        long[] jArr = (long[]) obj;
                        if (jArr.length > 10) {
                            MLog.e("please check value, size is " + jArr.length + ", overstep 10!");
                            return;
                        }
                        JSONArray jSONArray2 = new JSONArray();
                        while (i2 < jArr.length) {
                            jSONArray2.put(jArr[i2]);
                            i2++;
                        }
                        this.f22382k.put(str, jSONArray2);
                    } else if (obj instanceof int[]) {
                        int[] iArr = (int[]) obj;
                        if (iArr.length > 10) {
                            MLog.e("please check value, size is " + iArr.length + ", overstep 10!");
                            return;
                        }
                        JSONArray jSONArray3 = new JSONArray();
                        while (i2 < iArr.length) {
                            jSONArray3.put(iArr[i2]);
                            i2++;
                        }
                        this.f22382k.put(str, jSONArray3);
                    } else if (obj instanceof float[]) {
                        float[] fArr = (float[]) obj;
                        if (fArr.length > 10) {
                            MLog.e("please check value, size is " + fArr.length + ", overstep 10!");
                            return;
                        }
                        JSONArray jSONArray4 = new JSONArray();
                        while (i2 < fArr.length) {
                            jSONArray4.put(fArr[i2]);
                            i2++;
                        }
                        this.f22382k.put(str, jSONArray4);
                    } else if (obj instanceof double[]) {
                        double[] dArr = (double[]) obj;
                        if (dArr.length > 10) {
                            MLog.e("please check value, size is " + dArr.length + ", overstep 10!");
                            return;
                        }
                        JSONArray jSONArray5 = new JSONArray();
                        while (i2 < dArr.length) {
                            jSONArray5.put(dArr[i2]);
                            i2++;
                        }
                        this.f22382k.put(str, jSONArray5);
                    } else if (obj instanceof short[]) {
                        short[] sArr = (short[]) obj;
                        if (sArr.length > 10) {
                            MLog.e("please check value, size is " + sArr.length + ", overstep 10!");
                            return;
                        }
                        JSONArray jSONArray6 = new JSONArray();
                        while (i2 < sArr.length) {
                            jSONArray6.put((int) sArr[i2]);
                            i2++;
                        }
                        this.f22382k.put(str, jSONArray6);
                    } else {
                        MLog.e("please check value, illegal type!");
                        return;
                    }
                } else {
                    if (!(obj instanceof String) && !(obj instanceof Long) && !(obj instanceof Integer) && !(obj instanceof Float) && !(obj instanceof Double) && !(obj instanceof Short)) {
                        MLog.e("please check value, illegal type!");
                        return;
                    }
                    this.f22382k.put(str, obj);
                }
            } catch (Throwable unused) {
            }
            Context context2 = f22363a;
            UMWorkDispatch.sendEvent(context2, 8195, CoreProtocol.getInstance(context2), this.f22382k.toString());
            return;
        }
        UMLog.aq(j.ag, 0, "\\|");
    }

    private void a(String str, Object obj) {
        try {
            if (this.f22382k == null) {
                this.f22382k = new JSONObject();
            }
            int i2 = 0;
            if (obj.getClass().isArray()) {
                if (obj instanceof String[]) {
                    String[] strArr = (String[]) obj;
                    if (strArr.length > 10) {
                        return;
                    }
                    JSONArray jSONArray = new JSONArray();
                    while (i2 < strArr.length) {
                        String str2 = strArr[i2];
                        if (str2 != null && !HelperUtils.checkStrLen(str2, 256)) {
                            jSONArray.put(strArr[i2]);
                        }
                        i2++;
                    }
                    this.f22382k.put(str, jSONArray);
                    return;
                }
                if (obj instanceof long[]) {
                    long[] jArr = (long[]) obj;
                    JSONArray jSONArray2 = new JSONArray();
                    while (i2 < jArr.length) {
                        jSONArray2.put(jArr[i2]);
                        i2++;
                    }
                    this.f22382k.put(str, jSONArray2);
                    return;
                }
                if (obj instanceof int[]) {
                    int[] iArr = (int[]) obj;
                    JSONArray jSONArray3 = new JSONArray();
                    while (i2 < iArr.length) {
                        jSONArray3.put(iArr[i2]);
                        i2++;
                    }
                    this.f22382k.put(str, jSONArray3);
                    return;
                }
                if (obj instanceof float[]) {
                    float[] fArr = (float[]) obj;
                    JSONArray jSONArray4 = new JSONArray();
                    while (i2 < fArr.length) {
                        jSONArray4.put(fArr[i2]);
                        i2++;
                    }
                    this.f22382k.put(str, jSONArray4);
                    return;
                }
                if (obj instanceof double[]) {
                    double[] dArr = (double[]) obj;
                    JSONArray jSONArray5 = new JSONArray();
                    while (i2 < dArr.length) {
                        jSONArray5.put(dArr[i2]);
                        i2++;
                    }
                    this.f22382k.put(str, jSONArray5);
                    return;
                }
                if (obj instanceof short[]) {
                    short[] sArr = (short[]) obj;
                    JSONArray jSONArray6 = new JSONArray();
                    while (i2 < sArr.length) {
                        jSONArray6.put((int) sArr[i2]);
                        i2++;
                    }
                    this.f22382k.put(str, jSONArray6);
                    return;
                }
                return;
            }
            if (obj instanceof List) {
                List list = (List) obj;
                JSONArray jSONArray7 = new JSONArray();
                while (i2 < list.size()) {
                    Object obj2 = list.get(i2);
                    if ((obj2 instanceof String) || (obj2 instanceof Long) || (obj2 instanceof Integer) || (obj2 instanceof Float) || (obj2 instanceof Double) || (obj2 instanceof Short)) {
                        jSONArray7.put(list.get(i2));
                    }
                    i2++;
                }
                this.f22382k.put(str, jSONArray7);
                return;
            }
            if ((obj instanceof String) || (obj instanceof Long) || (obj instanceof Integer) || (obj instanceof Float) || (obj instanceof Double) || (obj instanceof Short)) {
                this.f22382k.put(str, obj);
            }
        } catch (Throwable unused) {
        }
    }

    public synchronized void a(Object obj) {
        Context context;
        try {
            context = f22363a;
        } catch (Throwable unused) {
        }
        if (context == null) {
            return;
        }
        if (!UMUtils.isMainProgress(context)) {
            MLog.e("registerSuperPropertyByCoreProtocol can not be called in child process");
            return;
        }
        if (obj != null) {
            String str = (String) obj;
            SharedPreferences.Editor editorEdit = PreferenceWrapper.getDefault(f22363a).edit();
            if (editorEdit != null && !TextUtils.isEmpty(str)) {
                editorEdit.putString(f22364h, this.f22382k.toString()).commit();
            }
        }
    }

    public synchronized void a(Context context, List<String> list) {
        try {
        } catch (Throwable th) {
            MLog.e(th);
        }
        if (context == null) {
            UMLog.aq(j.aj, 0, "\\|");
            return;
        }
        if (f22363a == null) {
            f22363a = context.getApplicationContext();
        }
        if (!UMUtils.isMainProgress(f22363a)) {
            MLog.e("setFirstLaunchEvent can not be called in child process");
            return;
        }
        if (!this.f22381j || !this.f22385n) {
            a(f22363a);
        }
        s.a(f22363a).a(list);
    }

    public synchronized void a(Context context, JSONObject jSONObject) {
        JSONObject jSONObject2;
        String string;
        Object obj;
        if (context == null) {
            UMLog.aq(j.al, 0, "\\|");
            return;
        }
        if (f22363a == null) {
            f22363a = context.getApplicationContext();
        }
        if (!UMUtils.isMainProgress(f22363a)) {
            MLog.e("registerPreProperties can not be called in child process");
            return;
        }
        if (!this.f22381j || !this.f22385n) {
            a(f22363a);
        }
        if (this.f22384m == null) {
            this.f22384m = new JSONObject();
        }
        if (jSONObject != null && jSONObject.length() > 0) {
            try {
                jSONObject2 = new JSONObject(this.f22384m.toString());
            } catch (Exception unused) {
                jSONObject2 = null;
            }
            if (jSONObject2 == null) {
                jSONObject2 = new JSONObject();
            }
            Iterator<String> itKeys = jSONObject.keys();
            if (itKeys != null) {
                while (itKeys.hasNext()) {
                    try {
                        string = itKeys.next().toString();
                        obj = jSONObject.get(string);
                    } catch (Exception unused2) {
                    }
                    if (b(string, obj)) {
                        jSONObject2.put(string, obj);
                        if (jSONObject2.length() > 10) {
                            MLog.e("please check propertics, size overlength!");
                            return;
                        }
                        continue;
                    } else {
                        return;
                    }
                }
            }
            this.f22384m = jSONObject2;
            if (this.f22384m.length() > 0) {
                Context context2 = f22363a;
                UMWorkDispatch.sendEvent(context2, 8199, CoreProtocol.getInstance(context2), this.f22384m.toString());
            }
            return;
        }
        UMLog.aq(j.am, 0, "\\|");
    }
}
