package com.umeng.commonsdk.internal;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.umeng.analytics.CoreProtocol;
import com.umeng.analytics.pro.aa;
import com.umeng.analytics.pro.ao;
import com.umeng.analytics.pro.ap;
import com.umeng.analytics.pro.aq;
import com.umeng.commonsdk.UMConfigureImpl;
import com.umeng.commonsdk.config.FieldManager;
import com.umeng.commonsdk.debug.UMRTLog;
import com.umeng.commonsdk.framework.UMEnvelopeBuild;
import com.umeng.commonsdk.framework.UMLogDataProtocol;
import com.umeng.commonsdk.framework.UMWorkDispatch;
import com.umeng.commonsdk.listener.OnGetOaidListener;
import com.umeng.commonsdk.service.UMGlobalContext;
import com.umeng.commonsdk.statistics.UMServerURL;
import com.umeng.commonsdk.statistics.idtracking.h;
import com.umeng.commonsdk.utils.UMUtils;
import com.umeng.commonsdk.utils.onMessageSendListener;
import com.umeng.umzid.ZIDManager;
import java.io.File;
import java.lang.reflect.Method;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class c implements UMLogDataProtocol {

    /* renamed from: a, reason: collision with root package name */
    public static final String f23196a = "um_policy_grant";

    /* renamed from: b, reason: collision with root package name */
    public static final String f23197b = "preInitInvokedFlag";

    /* renamed from: c, reason: collision with root package name */
    public static final String f23198c = "policyGrantInvokedFlag";

    /* renamed from: d, reason: collision with root package name */
    public static final String f23199d = "policyGrantResult";

    /* renamed from: f, reason: collision with root package name */
    private static int f23200f = 1;

    /* renamed from: g, reason: collision with root package name */
    private static final String f23201g = "info";

    /* renamed from: h, reason: collision with root package name */
    private static final String f23202h = "stat";

    /* renamed from: i, reason: collision with root package name */
    private static Class<?> f23203i = null;

    /* renamed from: j, reason: collision with root package name */
    private static Method f23204j = null;

    /* renamed from: k, reason: collision with root package name */
    private static Method f23205k = null;

    /* renamed from: l, reason: collision with root package name */
    private static Method f23206l = null;

    /* renamed from: m, reason: collision with root package name */
    private static boolean f23207m = false;

    /* renamed from: e, reason: collision with root package name */
    private Context f23208e;

    static {
        c();
    }

    public c(Context context) {
        if (context != null) {
            this.f23208e = context.getApplicationContext();
        }
    }

    public static String b() {
        Method method;
        Class<?> cls = f23203i;
        if (cls == null || (method = f23204j) == null || f23206l == null) {
            return "";
        }
        try {
            Object objInvoke = method.invoke(cls, new Object[0]);
            return objInvoke != null ? (String) f23206l.invoke(objInvoke, new Object[0]) : "";
        } catch (Throwable unused) {
            return "";
        }
    }

    private static void c() {
        try {
            f23203i = ZIDManager.class;
            Method declaredMethod = ZIDManager.class.getDeclaredMethod("getInstance", new Class[0]);
            if (declaredMethod != null) {
                f23204j = declaredMethod;
            }
            Method declaredMethod2 = f23203i.getDeclaredMethod("getZID", Context.class);
            if (declaredMethod2 != null) {
                f23205k = declaredMethod2;
            }
            Method declaredMethod3 = f23203i.getDeclaredMethod("getSDKVersion", new Class[0]);
            if (declaredMethod3 != null) {
                f23206l = declaredMethod3;
            }
        } catch (Throwable unused) {
        }
    }

    private void d() {
        ao aoVarA = ao.a(this.f23208e);
        ap apVarA = aoVarA.a(aq.f22518c);
        if (apVarA != null) {
            UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> [有状态]二级缓存记录构建成真正信封。");
            try {
                String str = apVarA.f22510a;
                String str2 = apVarA.f22511b;
                JSONObject jSONObjectA = new com.umeng.commonsdk.statistics.b().a(this.f23208e.getApplicationContext(), new JSONObject(apVarA.f22512c), new JSONObject(apVarA.f22513d), apVarA.f22514e, str2, apVarA.f22515f);
                if (jSONObjectA == null || !jSONObjectA.has("exception")) {
                    UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> [有状态]二级缓存记录构建真正信封 成功! 删除二级缓存记录。");
                } else {
                    UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> [有状态]二级缓存记录构建真正信封 失败。删除二级缓存记录");
                }
                aoVarA.a(aq.f22518c, str);
                aoVarA.b();
            } catch (Throwable unused) {
            }
        }
    }

    private void e() {
        if (f23207m) {
            if (FieldManager.allow(com.umeng.commonsdk.utils.b.G)) {
                return;
            }
            f23207m = false;
        } else if (FieldManager.allow(com.umeng.commonsdk.utils.b.G)) {
            f23207m = true;
            a(this.f23208e, new OnGetOaidListener() { // from class: com.umeng.commonsdk.internal.c.4
                @Override // com.umeng.commonsdk.listener.OnGetOaidListener
                public void onGetOaid(String str) {
                    UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> OAID云控参数更新(不采集->采集)：采集完成");
                    if (TextUtils.isEmpty(str)) {
                        UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> oaid返回null或者空串，不需要 伪冷启动。");
                        return;
                    }
                    try {
                        SharedPreferences sharedPreferences = c.this.f23208e.getSharedPreferences(h.f23379a, 0);
                        if (sharedPreferences != null) {
                            SharedPreferences.Editor editorEdit = sharedPreferences.edit();
                            editorEdit.putString(h.f23380b, str);
                            editorEdit.commit();
                        }
                    } catch (Throwable unused) {
                    }
                    UMWorkDispatch.sendEvent(c.this.f23208e, 32788, b.a(c.this.f23208e).a(), null);
                }
            });
        }
    }

    private void f() {
        if (FieldManager.allow(com.umeng.commonsdk.utils.b.G)) {
            f23207m = true;
            UMConfigureImpl.registerInterruptFlag();
            UMConfigureImpl.init(this.f23208e);
            f23200f++;
            UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> 要读取 oaid，需等待读取结果.");
            UMConfigureImpl.registerMessageSendListener(new onMessageSendListener() { // from class: com.umeng.commonsdk.internal.c.5
                @Override // com.umeng.commonsdk.utils.onMessageSendListener
                public void onMessageSend() {
                    if (c.this.f23208e != null) {
                        UMWorkDispatch.sendEvent(c.this.f23208e, 32790, b.a(c.this.f23208e).a(), null);
                    }
                    UMConfigureImpl.removeMessageSendListener(this);
                }
            });
            b(this.f23208e);
        }
    }

    private void g() {
        if (f23200f <= 0) {
            h();
            d(this.f23208e);
        }
    }

    private void h() {
        UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> 真实构建条件满足，开始构建业务信封。");
        if (UMUtils.isMainProgress(this.f23208e)) {
            e(this.f23208e);
            com.umeng.commonsdk.a.a(this.f23208e);
            Context context = this.f23208e;
            UMWorkDispatch.sendEvent(context, 8208, CoreProtocol.getInstance(context), null);
            Context context2 = this.f23208e;
            UMWorkDispatch.sendEvent(context2, 32785, b.a(context2).a(), null);
        }
    }

    @Override // com.umeng.commonsdk.framework.UMLogDataProtocol
    public void removeCacheData(Object obj) {
    }

    @Override // com.umeng.commonsdk.framework.UMLogDataProtocol
    public JSONObject setupReportData(long j2) {
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:55:0x019e  */
    @Override // com.umeng.commonsdk.framework.UMLogDataProtocol
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void workEvent(java.lang.Object r12, int r13) throws org.json.JSONException, java.lang.IllegalAccessException, java.lang.IllegalArgumentException, java.lang.reflect.InvocationTargetException {
        /*
            Method dump skipped, instructions count: 934
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.commonsdk.internal.c.workEvent(java.lang.Object, int):void");
    }

    public String a() {
        Method method;
        Class<?> cls = f23203i;
        if (cls == null || (method = f23204j) == null || f23205k == null) {
            return "";
        }
        try {
            Object objInvoke = method.invoke(cls, new Object[0]);
            return objInvoke != null ? (String) f23205k.invoke(objInvoke, this.f23208e) : "";
        } catch (Throwable unused) {
            return "";
        }
    }

    private static void b(final Context context) {
        new Thread(new Runnable() { // from class: com.umeng.commonsdk.internal.c.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    SharedPreferences sharedPreferences = context.getSharedPreferences(h.f23379a, 0);
                    long jCurrentTimeMillis = System.currentTimeMillis();
                    String strA = aa.a(context);
                    long jCurrentTimeMillis2 = System.currentTimeMillis();
                    if (!TextUtils.isEmpty(strA) && sharedPreferences != null) {
                        SharedPreferences.Editor editorEdit = sharedPreferences.edit();
                        editorEdit.putString(h.f23381c, (jCurrentTimeMillis2 - jCurrentTimeMillis) + "");
                        editorEdit.commit();
                    }
                    if (sharedPreferences != null) {
                        SharedPreferences.Editor editorEdit2 = sharedPreferences.edit();
                        editorEdit2.putString(h.f23380b, strA);
                        editorEdit2.commit();
                    }
                    UMConfigureImpl.removeInterruptFlag();
                } catch (Throwable unused) {
                }
            }
        }).start();
    }

    private void a(Context context) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(com.umeng.commonsdk.statistics.b.a("appkey"), UMGlobalContext.getInstance(context).getAppkey());
            jSONObject.put(com.umeng.commonsdk.statistics.b.a("app_version"), UMGlobalContext.getInstance(context).getAppVersion());
            jSONObject.put(com.umeng.commonsdk.statistics.b.a("os"), "Android");
            JSONObject jSONObjectBuildZeroEnvelopeWithExtHeader = UMEnvelopeBuild.buildZeroEnvelopeWithExtHeader(context, jSONObject, null, UMServerURL.ZCFG_PATH);
            if (jSONObjectBuildZeroEnvelopeWithExtHeader != null && jSONObjectBuildZeroEnvelopeWithExtHeader.has("exception")) {
                UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> 构建零号报文失败.");
            } else {
                UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> 构建零号报文 成功!!!");
            }
        } catch (Throwable unused) {
        }
    }

    private static void e(Context context) {
        File file = new File(context.getFilesDir().getAbsolutePath() + File.separator + aq.f22527l);
        if (file.exists()) {
            return;
        }
        try {
            file.createNewFile();
        } catch (Throwable unused) {
        }
    }

    private static void c(final Context context) {
        if (FieldManager.allow(com.umeng.commonsdk.utils.b.G)) {
            a(context, new OnGetOaidListener() { // from class: com.umeng.commonsdk.internal.c.3
                @Override // com.umeng.commonsdk.listener.OnGetOaidListener
                public void onGetOaid(String str) {
                    if (TextUtils.isEmpty(str)) {
                        return;
                    }
                    try {
                        SharedPreferences sharedPreferences = context.getSharedPreferences(h.f23379a, 0);
                        if (sharedPreferences == null || sharedPreferences.getString(h.f23380b, "").equalsIgnoreCase(str)) {
                            return;
                        }
                        UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> 更新本地缓存OAID");
                        SharedPreferences.Editor editorEdit = sharedPreferences.edit();
                        editorEdit.putString(h.f23380b, str);
                        editorEdit.commit();
                    } catch (Throwable unused) {
                    }
                }
            });
        }
    }

    private static void a(Context context, final OnGetOaidListener onGetOaidListener) {
        if (context == null) {
            return;
        }
        final Context applicationContext = context.getApplicationContext();
        new Thread(new Runnable() { // from class: com.umeng.commonsdk.internal.c.2
            @Override // java.lang.Runnable
            public void run() {
                String strA = aa.a(applicationContext);
                OnGetOaidListener onGetOaidListener2 = onGetOaidListener;
                if (onGetOaidListener2 != null) {
                    onGetOaidListener2.onGetOaid(strA);
                }
            }
        }).start();
    }

    private static Class<?> a(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException unused) {
            return null;
        }
    }

    private void d(Context context) {
        Object objInvoke;
        Method declaredMethod;
        Context applicationContext = context.getApplicationContext();
        String appkey = UMUtils.getAppkey(context);
        try {
            Class<?> clsA = a("com.umeng.umzid.ZIDManager");
            Method declaredMethod2 = clsA.getDeclaredMethod("getInstance", new Class[0]);
            if (declaredMethod2 == null || (objInvoke = declaredMethod2.invoke(clsA, new Object[0])) == null || (declaredMethod = clsA.getDeclaredMethod("init", Context.class, String.class, a("com.umeng.umzid.IZIDCompletionCallback"))) == null) {
                return;
            }
            declaredMethod.invoke(objInvoke, applicationContext, appkey, null);
        } catch (Throwable unused) {
        }
    }
}
