package com.umeng.analytics.pro;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.CoreProtocol;
import com.umeng.analytics.pro.e;
import com.umeng.analytics.pro.i;
import com.umeng.analytics.pro.y;
import com.umeng.analytics.process.UMProcessDBDatasSender;
import com.umeng.commonsdk.config.FieldManager;
import com.umeng.commonsdk.debug.UMRTLog;
import com.umeng.commonsdk.framework.UMWorkDispatch;
import com.umeng.commonsdk.service.UMGlobalContext;
import com.umeng.commonsdk.statistics.AnalyticsConstants;
import com.umeng.commonsdk.statistics.common.MLog;
import com.umeng.commonsdk.statistics.internal.PreferenceWrapper;
import com.umeng.commonsdk.utils.UMUtils;
import java.lang.reflect.Method;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class u implements y.a {

    /* renamed from: a, reason: collision with root package name */
    public static final String f22933a = "session_start_time";

    /* renamed from: b, reason: collision with root package name */
    public static final String f22934b = "session_end_time";

    /* renamed from: c, reason: collision with root package name */
    public static final String f22935c = "session_id";

    /* renamed from: d, reason: collision with root package name */
    public static final String f22936d = "pre_session_id";

    /* renamed from: e, reason: collision with root package name */
    public static final String f22937e = "a_start_time";

    /* renamed from: f, reason: collision with root package name */
    public static final String f22938f = "a_end_time";

    /* renamed from: g, reason: collision with root package name */
    public static final String f22939g = "fg_count";

    /* renamed from: h, reason: collision with root package name */
    private static String f22940h = null;

    /* renamed from: i, reason: collision with root package name */
    private static Context f22941i = null;

    /* renamed from: j, reason: collision with root package name */
    private static boolean f22942j = false;

    /* renamed from: k, reason: collision with root package name */
    private static long f22943k = 0;

    /* renamed from: l, reason: collision with root package name */
    private static boolean f22944l = true;

    /* renamed from: m, reason: collision with root package name */
    private static long f22945m;

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        private static final u f22946a = new u();

        private a() {
        }
    }

    public static u a() {
        return a.f22946a;
    }

    public static void b(Context context) {
        SharedPreferences sharedPreferences = PreferenceWrapper.getDefault(f22941i);
        if (sharedPreferences != null) {
            long j2 = sharedPreferences.getLong(f22939g, 0L);
            SharedPreferences.Editor editorEdit = sharedPreferences.edit();
            if (editorEdit != null) {
                editorEdit.putLong(f22939g, j2 + 1);
                editorEdit.commit();
            }
        }
    }

    private void d(Context context) {
        try {
            SharedPreferences.Editor editorEdit = PreferenceWrapper.getDefault(context).edit();
            editorEdit.putLong(f22939g, 0L);
            editorEdit.commit();
        } catch (Throwable unused) {
        }
    }

    private String e(Context context) {
        if (f22941i == null && context != null) {
            f22941i = context.getApplicationContext();
        }
        String strD = y.a().d(f22941i);
        try {
            f(context);
            o.a(f22941i).d((Object) null);
        } catch (Throwable unused) {
        }
        return strD;
    }

    private void f(Context context) {
        o.a(context).b(context);
        o.a(context).d();
    }

    public void c(Context context, Object obj) {
        try {
            if (f22941i == null && context != null) {
                f22941i = context.getApplicationContext();
            }
            long jLongValue = ((Long) obj).longValue();
            SharedPreferences sharedPreferences = PreferenceWrapper.getDefault(context);
            if (sharedPreferences == null) {
                return;
            }
            if (sharedPreferences.getLong(f22937e, 0L) == 0) {
                MLog.e("onPause called before onResume");
                return;
            }
            SharedPreferences.Editor editorEdit = sharedPreferences.edit();
            UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> onEndSessionInternal: write activity end time = " + jLongValue);
            editorEdit.putLong(f22938f, jLongValue);
            editorEdit.putLong(f22934b, jLongValue);
            editorEdit.commit();
        } catch (Throwable unused) {
        }
    }

    private u() {
        y.a().a(this);
    }

    public static long a(Context context) {
        try {
            return PreferenceWrapper.getDefault(context).getLong(f22939g, 0L);
        } catch (Throwable unused) {
            return 0L;
        }
    }

    public void a(Context context, long j2) {
        SharedPreferences.Editor editorEdit;
        SharedPreferences sharedPreferences = PreferenceWrapper.getDefault(f22941i);
        if (sharedPreferences == null || (editorEdit = sharedPreferences.edit()) == null) {
            return;
        }
        editorEdit.putLong(f22933a, j2);
        editorEdit.commit();
    }

    public void b(Context context, Object obj) {
        long jLongValue;
        try {
            if (f22941i == null) {
                f22941i = UMGlobalContext.getAppContext(context);
            }
            if (obj == null) {
                jLongValue = System.currentTimeMillis();
            } else {
                jLongValue = ((Long) obj).longValue();
            }
            SharedPreferences sharedPreferences = PreferenceWrapper.getDefault(f22941i);
            if (sharedPreferences == null) {
                return;
            }
            f22943k = sharedPreferences.getLong(f22938f, 0L);
            UMRTLog.i(UMRTLog.RTLOG_TAG, "------>>> lastActivityEndTime: " + f22943k);
            String string = sharedPreferences.getString(d.az, "");
            String appVersionName = UMUtils.getAppVersionName(f22941i);
            SharedPreferences.Editor editorEdit = sharedPreferences.edit();
            if (editorEdit == null) {
                return;
            }
            if (!TextUtils.isEmpty(string) && !string.equals(appVersionName)) {
                UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> requestNewInstantSessionIf: version upgrade");
                editorEdit.putLong(f22933a, jLongValue);
                editorEdit.commit();
                o.a(f22941i).a((Object) null, true);
                UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> force generate new session: session id = " + y.a().c(f22941i));
                f22942j = true;
                a(f22941i, jLongValue, true);
                return;
            }
            if (y.a().e(f22941i)) {
                UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> More then 30 sec from last session.");
                f22942j = true;
                editorEdit.putLong(f22933a, jLongValue);
                editorEdit.commit();
                a(f22941i, jLongValue, false);
                return;
            }
            UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> less then 30 sec from last session, do nothing.");
            f22942j = false;
        } catch (Throwable unused) {
        }
    }

    public void a(Context context, Object obj) {
        SharedPreferences.Editor editorEdit;
        try {
            if (f22941i == null && context != null) {
                f22941i = context.getApplicationContext();
            }
            long jLongValue = ((Long) obj).longValue();
            SharedPreferences sharedPreferences = PreferenceWrapper.getDefault(f22941i);
            if (sharedPreferences == null || (editorEdit = sharedPreferences.edit()) == null) {
                return;
            }
            String string = sharedPreferences.getString(d.az, "");
            String appVersionName = UMUtils.getAppVersionName(f22941i);
            if (TextUtils.isEmpty(string)) {
                editorEdit.putInt("versioncode", Integer.parseInt(UMUtils.getAppVersionCode(context)));
                editorEdit.putString(d.az, appVersionName);
                editorEdit.commit();
            } else if (!string.equals(appVersionName)) {
                UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> onStartSessionInternal: upgrade version: " + string + "-> " + appVersionName);
                int i2 = sharedPreferences.getInt("versioncode", 0);
                String string2 = sharedPreferences.getString("pre_date", "");
                String string3 = sharedPreferences.getString("pre_version", "");
                String string4 = sharedPreferences.getString(d.az, "");
                editorEdit.putInt("versioncode", Integer.parseInt(UMUtils.getAppVersionCode(context)));
                editorEdit.putString(d.az, appVersionName);
                editorEdit.putString("vers_date", string2);
                editorEdit.putString("vers_pre_version", string3);
                editorEdit.putString("cur_version", string4);
                editorEdit.putInt("vers_code", i2);
                editorEdit.putString("vers_name", string);
                editorEdit.commit();
                if (f22944l) {
                    f22944l = false;
                }
                if (f22942j) {
                    f22942j = false;
                    b(f22941i, jLongValue, true);
                    b(f22941i, jLongValue);
                    return;
                }
                return;
            }
            if (f22942j) {
                f22942j = false;
                if (f22944l) {
                    f22944l = false;
                }
                f22940h = e(context);
                MLog.d("创建新会话: " + f22940h);
                UMRTLog.i(UMRTLog.RTLOG_TAG, "mSessionChanged flag has been set, Start new session: " + f22940h);
                return;
            }
            f22940h = sharedPreferences.getString("session_id", null);
            editorEdit.putLong(f22937e, jLongValue);
            editorEdit.putLong(f22938f, 0L);
            editorEdit.commit();
            MLog.d("延续上一个会话: " + f22940h);
            UMRTLog.i(UMRTLog.RTLOG_TAG, "Extend current session: " + f22940h);
            if (f22944l) {
                f22944l = false;
                if (FieldManager.allow(com.umeng.commonsdk.utils.b.E)) {
                    Context context2 = f22941i;
                    UMWorkDispatch.sendEventEx(context2, 8213, CoreProtocol.getInstance(context2), null, 0L);
                }
            }
            f(context);
            o.a(f22941i).a(false);
        } catch (Throwable unused) {
        }
    }

    public String c(Context context) {
        try {
            if (f22940h == null) {
                return PreferenceWrapper.getDefault(context).getString("session_id", null);
            }
        } catch (Throwable unused) {
        }
        return f22940h;
    }

    public String c() {
        return c(f22941i);
    }

    public boolean b(Context context, long j2, boolean z2) {
        String strA;
        long j3;
        try {
            SharedPreferences sharedPreferences = PreferenceWrapper.getDefault(context);
            if (sharedPreferences == null || (strA = y.a().a(f22941i)) == null) {
                return false;
            }
            long j4 = sharedPreferences.getLong(f22937e, 0L);
            long j5 = sharedPreferences.getLong(f22938f, 0L);
            if (j4 <= 0 || j5 != 0) {
                return false;
            }
            try {
                if (z2) {
                    j3 = f22943k;
                    if (j3 == 0) {
                        UMRTLog.i(UMRTLog.RTLOG_TAG, "------>>> lastActivityEndTime = 0, In-app upgrade, use currentTime: = " + j2);
                        j3 = j2;
                    } else {
                        UMRTLog.i(UMRTLog.RTLOG_TAG, "------>>> lastActivityEndTime != 0, app upgrade, use lastActivityEndTime: = " + f22943k);
                    }
                    c(f22941i, Long.valueOf(j3));
                } else {
                    c(f22941i, Long.valueOf(j2));
                    j3 = j2;
                }
                JSONObject jSONObject = new JSONObject();
                if (z2) {
                    jSONObject.put(e.d.a.f22754g, j3);
                } else {
                    jSONObject.put(e.d.a.f22754g, j2);
                }
                JSONObject jSONObjectB = com.umeng.analytics.b.a().b();
                if (jSONObjectB != null && jSONObjectB.length() > 0) {
                    jSONObject.put("__sp", jSONObjectB);
                }
                JSONObject jSONObjectC = com.umeng.analytics.b.a().c();
                if (jSONObjectC != null && jSONObjectC.length() > 0) {
                    jSONObject.put("__pp", jSONObjectC);
                }
                if (FieldManager.allow(com.umeng.commonsdk.utils.b.E)) {
                    UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>>*** foregroundCount = " + f22945m);
                    jSONObject.put(e.d.a.f22755h, f22945m);
                    f22945m = 0L;
                } else {
                    jSONObject.put(e.d.a.f22755h, 0L);
                }
                i.a(context).a(strA, jSONObject, i.a.END);
                o.a(f22941i).e();
            } catch (Throwable unused) {
            }
            return true;
        } catch (Throwable unused2) {
            return false;
        }
    }

    public void b(Context context, long j2) {
        if (PreferenceWrapper.getDefault(context) == null) {
            return;
        }
        try {
            o.a(f22941i).c((Object) null);
        } catch (Throwable unused) {
        }
    }

    public String a(Context context, long j2, boolean z2) {
        String strB = y.a().b(context);
        UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> onInstantSessionInternal: current session id = " + strB);
        if (TextUtils.isEmpty(strB)) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("__e", j2);
            JSONObject jSONObjectB = com.umeng.analytics.b.a().b();
            if (jSONObjectB != null && jSONObjectB.length() > 0) {
                jSONObject.put("__sp", jSONObjectB);
            }
            JSONObject jSONObjectC = com.umeng.analytics.b.a().c();
            if (jSONObjectC != null && jSONObjectC.length() > 0) {
                jSONObject.put("__pp", jSONObjectC);
            }
            i.a(context).a(strB, jSONObject, i.a.INSTANTSESSIONBEGIN);
            o.a(context).a(jSONObject, z2);
        } catch (Throwable unused) {
        }
        return strB;
    }

    public String b() {
        return f22940h;
    }

    @Override // com.umeng.analytics.pro.y.a
    public void a(String str, String str2, long j2, long j3, long j4) throws JSONException {
        a(f22941i, str2, j2, j3, j4);
        UMRTLog.i(UMRTLog.RTLOG_TAG, "saveSessionToDB: complete");
        if (AnalyticsConstants.SUB_PROCESS_EVENT) {
            Context context = f22941i;
            UMWorkDispatch.sendEvent(context, UMProcessDBDatasSender.UM_PROCESS_EVENT_KEY, UMProcessDBDatasSender.getInstance(context), Long.valueOf(System.currentTimeMillis()));
        }
    }

    @Override // com.umeng.analytics.pro.y.a
    public void a(String str, long j2, long j3, long j4) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        a(str, j2);
    }

    private void a(Context context, String str, long j2, long j3, long j4) throws JSONException {
        if (TextUtils.isEmpty(f22940h)) {
            f22940h = y.a().a(f22941i);
        }
        if (TextUtils.isEmpty(str) || str.equals(f22940h)) {
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(e.d.a.f22754g, j3);
            jSONObject.put(e.d.a.f22755h, j4);
            JSONObject jSONObjectB = com.umeng.analytics.b.a().b();
            if (jSONObjectB != null && jSONObjectB.length() > 0) {
                jSONObject.put("__sp", jSONObjectB);
            }
            JSONObject jSONObjectC = com.umeng.analytics.b.a().c();
            if (jSONObjectC != null && jSONObjectC.length() > 0) {
                jSONObject.put("__pp", jSONObjectC);
            }
            i.a(context).a(f22940h, jSONObject, i.a.END);
        } catch (Exception unused) {
        }
        try {
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("__e", j2);
            i.a(context).a(str, jSONObject2, i.a.BEGIN);
            if (FieldManager.allow(com.umeng.commonsdk.utils.b.E)) {
                f22945m = j4;
                d(context);
                Context context2 = f22941i;
                UMWorkDispatch.sendEventEx(context2, 8213, CoreProtocol.getInstance(context2), null, 0L);
            }
        } catch (Exception unused2) {
        }
        f22940h = str;
    }

    private void a(String str, long j2) {
        SharedPreferences sharedPreferences = PreferenceWrapper.getDefault(f22941i);
        if (sharedPreferences == null) {
            return;
        }
        long j3 = sharedPreferences.getLong(f22934b, 0L);
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("__ii", str);
            jSONObject.put("__e", j2);
            jSONObject.put(e.d.a.f22754g, j3);
            double[] location = AnalyticsConfig.getLocation();
            if (location != null) {
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put(d.C, location[0]);
                jSONObject2.put(d.D, location[1]);
                jSONObject2.put("ts", System.currentTimeMillis());
                jSONObject.put(e.d.a.f22752e, jSONObject2);
            }
            Class<?> cls = Class.forName("android.net.TrafficStats");
            Class<?> cls2 = Integer.TYPE;
            Method method = cls.getMethod("getUidRxBytes", cls2);
            Method method2 = cls.getMethod("getUidTxBytes", cls2);
            int i2 = f22941i.getApplicationInfo().uid;
            if (i2 == -1) {
                return;
            }
            long jLongValue = ((Long) method.invoke(null, Integer.valueOf(i2))).longValue();
            long jLongValue2 = ((Long) method2.invoke(null, Integer.valueOf(i2))).longValue();
            if (jLongValue > 0 && jLongValue2 > 0) {
                JSONObject jSONObject3 = new JSONObject();
                jSONObject3.put(d.H, jLongValue);
                jSONObject3.put(d.G, jLongValue2);
                jSONObject.put(e.d.a.f22751d, jSONObject3);
            }
            i.a(f22941i).a(str, jSONObject, i.a.NEWSESSION);
            v.a(f22941i);
            l.c(f22941i);
        } catch (Throwable unused) {
        }
    }
}
