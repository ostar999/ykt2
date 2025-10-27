package com.mobile.auth.gatewayauth.manager;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.mobile.auth.BuildConfig;
import com.mobile.auth.gatewayauth.Constant;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.mobile.auth.gatewayauth.PnsReporter;
import com.mobile.auth.gatewayauth.model.MonitorStruct;
import com.mobile.auth.gatewayauth.model.UStruct;
import com.mobile.auth.gatewayauth.utils.AESUtils;
import com.mobile.auth.gatewayauth.utils.f;
import com.mobile.auth.gatewayauth.utils.security.PackageUtils;
import com.nirvana.tools.logger.storage.LoggerIdManager;
import com.tencent.open.SocialOperation;
import com.umeng.analytics.pro.am;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class d {

    /* renamed from: a, reason: collision with root package name */
    public static String f10219a;

    /* renamed from: b, reason: collision with root package name */
    public static String f10220b;

    /* renamed from: c, reason: collision with root package name */
    public static String f10221c;

    /* renamed from: d, reason: collision with root package name */
    private Context f10222d;

    /* renamed from: h, reason: collision with root package name */
    private String f10226h;

    /* renamed from: i, reason: collision with root package name */
    private String f10227i;

    /* renamed from: j, reason: collision with root package name */
    private String f10228j;

    /* renamed from: o, reason: collision with root package name */
    private Map<String, String> f10233o;

    /* renamed from: p, reason: collision with root package name */
    private Map<String, String> f10234p;

    /* renamed from: q, reason: collision with root package name */
    private Map<String, String> f10235q;

    /* renamed from: r, reason: collision with root package name */
    private Map<String, String> f10236r;

    /* renamed from: s, reason: collision with root package name */
    private com.mobile.auth.o.a f10237s;

    /* renamed from: e, reason: collision with root package name */
    private final String f10223e = "c78623c22e2f6513";

    /* renamed from: f, reason: collision with root package name */
    private String f10224f = UUID.randomUUID().toString();

    /* renamed from: g, reason: collision with root package name */
    private boolean f10225g = true;

    /* renamed from: k, reason: collision with root package name */
    private String f10229k = "";

    /* renamed from: l, reason: collision with root package name */
    private String f10230l = "";

    /* renamed from: m, reason: collision with root package name */
    private com.mobile.auth.n.a f10231m = null;

    /* renamed from: n, reason: collision with root package name */
    private LoggerIdManager f10232n = null;

    public d(Context context) {
        this.f10222d = context.getApplicationContext();
        o();
    }

    private String a(Context context) {
        try {
            try {
                Object objInvoke = Class.forName("com.nirvana.tools.logger.utils.LocalDeviceUtil").getDeclaredMethod("getUmaaId", Context.class).invoke(null, context);
                return objInvoke != null ? objInvoke.toString() : "";
            } catch (Exception e2) {
                e2.printStackTrace();
                return "";
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    private String a(JSONObject jSONObject, String str, String str2, String str3, String str4) {
        try {
            try {
                jSONObject.put(am.aF, new JSONObject(a(this.f10222d, str2, str4)));
                jSONObject.put("action", str);
                jSONObject.put("apiLevel", str3);
                jSONObject.put("osType", "Android");
                Map<String, String> map = this.f10236r;
                if (map != null && !map.isEmpty()) {
                    for (String str5 : this.f10236r.keySet()) {
                        jSONObject.put(str5, this.f10236r.get(str5));
                    }
                }
            } catch (JSONException e2) {
                this.f10237s.e("AssembleMonitorInfoError!", Log.getStackTraceString(e2));
            }
            return jSONObject.toString();
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    private String b(Context context) {
        try {
            try {
                Object objInvoke = Class.forName("com.nirvana.tools.logger.utils.LocalDeviceUtil").getDeclaredMethod("getDeviceId", Context.class).invoke(null, context);
                return objInvoke != null ? objInvoke.toString() : "";
            } catch (Exception e2) {
                e2.printStackTrace();
                return "";
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    private void o() {
        try {
            this.f10237s = com.mobile.auth.o.a.a(this.f10222d);
            this.f10231m = new com.mobile.auth.n.a(a(), this);
            r();
            this.f10232n = new LoggerIdManager(this.f10222d);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    private String p() {
        try {
            return q() ? a(this.f10222d) : "";
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    private boolean q() {
        try {
            Class.forName("com.nirvana.tools.logger.utils.LocalDeviceUtil");
            return true;
        } catch (ClassNotFoundException unused) {
            return false;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return false;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return false;
            }
        }
    }

    private boolean r() {
        return true;
    }

    public com.mobile.auth.o.a a() {
        try {
            return this.f10237s;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public String a(MonitorStruct monitorStruct) {
        try {
            return a(monitorStruct.getVendorKey(), monitorStruct.getAction(), new UStruct(monitorStruct), monitorStruct.getApiLevel(), monitorStruct.getPhoneNumber());
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public String a(String str, String str2, UStruct uStruct, String str3) {
        try {
            return a(str, str2, uStruct, str3, "");
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public String a(String str, String str2, UStruct uStruct, String str3, String str4) {
        String strEncrypt;
        try {
            JSONObject jSONObject = new JSONObject();
            if (!TextUtils.isEmpty(this.f10229k)) {
                uStruct.setEt(this.f10229k);
            }
            try {
                JSONObject json = uStruct.toJson();
                Map<String, String> map = this.f10233o;
                if (map != null && !map.isEmpty()) {
                    for (String str5 : this.f10233o.keySet()) {
                        json.put(str5, this.f10233o.get(str5));
                    }
                }
                jSONObject.put(am.aG, json);
                jSONObject.put(am.aG, uStruct.toJson());
                if (TextUtils.isEmpty(str4)) {
                    Map<String, String> mapB = b(uStruct.getPrivateIp());
                    Map<String, String> map2 = this.f10235q;
                    if (map2 != null && !map2.isEmpty()) {
                        mapB.putAll(this.f10235q);
                    }
                    strEncrypt = AESUtils.encrypt(new JSONObject(mapB).toString(), "c78623c22e2f6513");
                } else {
                    HashMap map3 = new HashMap();
                    map3.put("phoneNumber", str4);
                    map3.putAll(b(uStruct.getPrivateIp()));
                    Map<String, String> map4 = this.f10235q;
                    if (map4 != null && !map4.isEmpty()) {
                        map3.putAll(this.f10235q);
                    }
                    strEncrypt = AESUtils.encrypt(new JSONObject(map3).toString(), "c78623c22e2f6513");
                }
                jSONObject.put("s", strEncrypt);
            } catch (Exception e2) {
                this.f10237s.e("BuildMonitorError!", Log.getStackTraceString(e2));
            }
            return a(jSONObject, str2, str, str3, uStruct.getNetworkType());
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public Map<String, Object> a(Context context, String str, String str2) {
        String str3;
        Map<String, String> map;
        try {
            HashMap map2 = new HashMap();
            map2.put("createTime", Long.valueOf(System.currentTimeMillis()));
            map2.put("osVersion", f.b());
            map2.put("deviceName", f.c());
            map2.put("deviceBrand", f.a());
            map2.put("packageName", PackageUtils.getPackageName(context));
            map2.put("appVersion", PackageUtils.getVersionName(context));
            map2.put(SocialOperation.GAME_SIGNATURE, PackageUtils.getSign(context));
            if (Constant.VENDOR_CUCC.equals(str)) {
                map2.put("vendorKey", Constant.VENDOR_CUXZ);
            } else {
                map2.put("vendorKey", str);
            }
            map2.put(com.heytap.mcssdk.constant.b.C, BuildConfig.VERSION_NAME);
            map2.put("networkType", str2);
            map2.put("monitorVersion", "2.1");
            map2.put("utdid", l());
            map2.put("um_aaid", m());
            map2.put("uniqueId", k());
            map2.put("traceId", this.f10224f);
            map2.put("archiveName", BuildConfig.FLAVOR);
            if (!Constant.VENDOR_CMCC.equals(str)) {
                if (!Constant.VENDOR_CUCC.equals(str)) {
                    if (Constant.VENDOR_CTCC.equals(str)) {
                        str3 = BuildConfig.CTCC_SDK_VERSION;
                    } else if (!Constant.VENDOR_CUXZ.equals(str)) {
                        str3 = "";
                    }
                }
                map2.put("carrierSdkVersion", BuildConfig.CUZX_SDK_VERSION);
                map = this.f10234p;
                if (map != null && !map.isEmpty()) {
                    map2.putAll(this.f10234p);
                }
                return map2;
            }
            str3 = BuildConfig.CMCC_SDK_VERSION;
            map2.put("carrierSdkVersion", str3);
            map = this.f10234p;
            if (map != null) {
                map2.putAll(this.f10234p);
            }
            return map2;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public void a(e eVar) {
        try {
            this.f10231m.a(eVar);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void a(String str) {
        try {
            this.f10230l = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public PnsReporter b() {
        try {
            return this.f10231m;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public String b(String str, String str2, UStruct uStruct, String str3) {
        String strEncrypt;
        try {
            JSONObject jSONObject = new JSONObject();
            if (!TextUtils.isEmpty(this.f10229k)) {
                uStruct.setEt(this.f10229k);
            }
            try {
                JSONObject json = uStruct.toJson();
                Map<String, String> map = this.f10233o;
                if (map != null && !map.isEmpty()) {
                    for (String str4 : this.f10233o.keySet()) {
                        json.put(str4, this.f10233o.get(str4));
                    }
                }
                jSONObject.put(am.aG, json);
                Map<String, String> map2 = this.f10235q;
                if (map2 == null || map2.isEmpty()) {
                    strEncrypt = "";
                } else {
                    Map<? extends String, ? extends String> map3 = this.f10235q;
                    map3.putAll(map3);
                    strEncrypt = AESUtils.encrypt(new JSONObject(this.f10235q).toString(), "c78623c22e2f6513");
                }
                jSONObject.put("s", strEncrypt);
            } catch (Exception e2) {
                this.f10237s.e("BuildMonitorNoSError!", Log.getStackTraceString(e2));
            }
            return a(jSONObject, str2, str, str3, uStruct.getNetworkType());
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public Map<String, String> b(String str) {
        try {
            HashMap map = new HashMap();
            if (TextUtils.isEmpty(str)) {
                map.put("innerIP", str);
            }
            map.put("sceneCode", this.f10230l);
            return map;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public synchronized String c() {
        try {
            if (TextUtils.isEmpty(this.f10226h)) {
                return f();
            }
            return this.f10226h;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public void c(String str) {
        if (str != null) {
            try {
                this.f10229k = str;
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }
    }

    public synchronized String d() {
        try {
            if (TextUtils.isEmpty(this.f10227i)) {
                return g();
            }
            return this.f10227i;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public synchronized String e() {
        try {
            if (TextUtils.isEmpty(this.f10228j)) {
                return j();
            }
            return this.f10228j;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public synchronized String f() {
        String string;
        try {
            string = UUID.randomUUID().toString();
            this.f10226h = string;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
        return string;
    }

    public synchronized String g() {
        String string;
        try {
            string = UUID.randomUUID().toString();
            this.f10227i = string;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
        return string;
    }

    public synchronized void h() {
        try {
            this.f10226h = null;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public synchronized void i() {
        try {
            this.f10227i = null;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public synchronized String j() {
        String string;
        try {
            string = UUID.randomUUID().toString();
            this.f10228j = string;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
        return string;
    }

    public String k() {
        LoggerIdManager loggerIdManager;
        try {
            if (f10219a == null && (loggerIdManager = this.f10232n) != null) {
                f10219a = loggerIdManager.getUniqueId();
            }
            return f10219a;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public String l() {
        try {
            if (!this.f10225g) {
                return null;
            }
            if (f10220b == null && q()) {
                f10220b = b(this.f10222d);
            }
            return f10220b;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public String m() {
        try {
            if (f10221c == null) {
                f10221c = p();
            }
            return f10221c;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public void n() {
        try {
            this.f10225g = false;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }
}
