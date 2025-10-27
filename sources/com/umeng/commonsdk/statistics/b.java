package com.umeng.commonsdk.statistics;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import cn.hutool.core.text.StrPool;
import com.aliyun.vod.log.struct.AliyunLogKey;
import com.caverock.androidsvg.SVGParser;
import com.hjq.permissions.Permission;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.pro.am;
import com.umeng.analytics.pro.be;
import com.umeng.analytics.pro.d;
import com.umeng.commonsdk.config.FieldManager;
import com.umeng.commonsdk.framework.UMEnvelopeBuild;
import com.umeng.commonsdk.framework.UMFrUtils;
import com.umeng.commonsdk.internal.crash.UMCrashManager;
import com.umeng.commonsdk.statistics.common.DataHelper;
import com.umeng.commonsdk.statistics.common.DeviceConfig;
import com.umeng.commonsdk.statistics.common.ULog;
import com.umeng.commonsdk.statistics.idtracking.Envelope;
import com.umeng.commonsdk.statistics.idtracking.ImprintHandler;
import com.umeng.commonsdk.statistics.idtracking.e;
import com.umeng.commonsdk.statistics.internal.PreferenceWrapper;
import com.umeng.commonsdk.utils.UMUtils;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    public static String f23287a = null;

    /* renamed from: b, reason: collision with root package name */
    public static String f23288b = "";

    /* renamed from: c, reason: collision with root package name */
    private static final String f23289c = "EnvelopeManager";

    /* renamed from: d, reason: collision with root package name */
    private static final String f23290d = "debug.umeng.umTaskId";

    /* renamed from: e, reason: collision with root package name */
    private static final String f23291e = "debug.umeng.umCaseId";

    /* renamed from: f, reason: collision with root package name */
    private static final String f23292f = "empty";

    /* renamed from: g, reason: collision with root package name */
    private static String f23293g = "";

    /* renamed from: h, reason: collision with root package name */
    private static String f23294h = "";

    /* renamed from: i, reason: collision with root package name */
    private static String f23295i;

    /* renamed from: j, reason: collision with root package name */
    private static Map<String, String> f23296j;

    /* renamed from: l, reason: collision with root package name */
    private static boolean f23297l;

    /* renamed from: k, reason: collision with root package name */
    private int f23298k = 0;

    static {
        HashMap map = new HashMap();
        f23296j = map;
        map.put("header", "#h");
        f23296j.put("sdk_type", "#sdt");
        f23296j.put(am.Q, "#ac");
        f23296j.put("device_model", "#dm");
        f23296j.put("umid", "#umid");
        f23296j.put("os", "os");
        f23296j.put("language", "#lang");
        f23296j.put(am.ai, "#dt");
        f23296j.put("resolution", "#rl");
        f23296j.put(am.H, "#dmf");
        f23296j.put(am.J, "#dn");
        f23296j.put("platform_version", "#pv");
        f23296j.put("font_size_setting", "#fss");
        f23296j.put("os_version", "#ov");
        f23296j.put(am.I, "#did");
        f23296j.put("platform_sdk_version", "#psv");
        f23296j.put(am.F, "#db");
        f23296j.put("appkey", "#ak");
        f23296j.put(am.Y, "#itr");
        f23296j.put("id_type", "#it");
        f23296j.put(AliyunLogKey.KEY_UUID, "#ud");
        f23296j.put("device_id", "#dd");
        f23296j.put(am.X, "#imp");
        f23296j.put("sdk_version", "#sv");
        f23296j.put("st", "#st");
        f23296j.put("analytics", "#a");
        f23296j.put("package_name", "#pkg");
        f23296j.put(am.f22453p, "#sig");
        f23296j.put(am.f22454q, "#sis1");
        f23296j.put(am.f22455r, "#sis");
        f23296j.put("app_version", "#av");
        f23296j.put("version_code", "#vc");
        f23296j.put(am.f22459v, "#imd");
        f23296j.put(am.B, "#mnc");
        f23296j.put(am.E, "#boa");
        f23296j.put(am.G, "#mant");
        f23296j.put(am.M, "#tz");
        f23296j.put(am.O, "#ct");
        f23296j.put(am.P, "#car");
        f23296j.put("display_name", "#disn");
        f23296j.put(am.T, "#nt");
        f23296j.put(am.f22439b, "#cv");
        f23296j.put(am.f22441d, "#mv");
        f23296j.put(am.f22440c, "#cot");
        f23296j.put(am.f22442e, "#mod");
        f23296j.put(am.aj, "#al");
        f23296j.put("session_id", "#sid");
        f23296j.put(am.S, "#ip");
        f23296j.put(am.U, "#sre");
        f23296j.put(am.V, "#fre");
        f23296j.put(am.W, "#ret");
        f23296j.put("channel", "#chn");
        f23296j.put("wrapper_type", "#wt");
        f23296j.put("wrapper_version", "#wv");
        f23296j.put(am.aU, "#tsv");
        f23296j.put(am.aV, "#rps");
        f23296j.put(am.aY, "#mov");
        f23296j.put(d.f22687i, "#vt");
        f23296j.put("secret", "#sec");
        f23296j.put(d.ah, "#prv");
        f23296j.put(d.f22690l, "#$prv");
        f23296j.put(d.f22691m, "#uda");
        f23296j.put(am.f22438a, "#tok");
        f23296j.put(am.aM, "#iv");
        f23296j.put(am.R, "#ast");
        f23296j.put("backstate", "#bst");
        f23296j.put("zdata_ver", "#zv");
        f23296j.put("zdata_req_ts", "#zrt");
        f23296j.put("app_b_v", "#bv");
        f23296j.put("zdata", "#zta");
        f23296j.put(am.ap, "#mt");
        f23296j.put(am.am, "#zsv");
        f23296j.put(am.ao, "#oos");
    }

    public static String a(String str) {
        return f23296j.containsKey(str) ? f23296j.get(str) : str;
    }

    private static boolean b() {
        f23293g = UMUtils.getSystemProperty(f23290d, "");
        f23294h = UMUtils.getSystemProperty(f23291e, "");
        return (!TextUtils.isEmpty(f23293g) && !f23292f.equals(f23293g)) && (!TextUtils.isEmpty(f23294h) && !f23292f.equals(f23294h));
    }

    public static void a() {
        if (f23295i != null) {
            f23295i = null;
            e.a();
        }
    }

    private static int[] b(Context context) {
        int[] iArr = new int[3];
        try {
            SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences(com.umeng.commonsdk.internal.c.f23196a, 0);
            if (sharedPreferences != null) {
                iArr[0] = sharedPreferences.getInt(com.umeng.commonsdk.internal.c.f23197b, 0);
                iArr[1] = sharedPreferences.getInt(com.umeng.commonsdk.internal.c.f23198c, 0);
                iArr[2] = sharedPreferences.getInt("policyGrantResult", 0);
            }
        } catch (Throwable unused) {
        }
        return iArr;
    }

    public static long a(Context context) {
        long j2 = DataHelper.ENVELOPE_ENTITY_RAW_LENGTH_MAX - DataHelper.ENVELOPE_EXTRA_LENGTH;
        if (ULog.DEBUG) {
            Log.i(f23289c, "free size is " + j2);
        }
        return j2;
    }

    private JSONObject a(int i2, JSONObject jSONObject) throws JSONException {
        if (jSONObject != null) {
            try {
                jSONObject.put("exception", i2);
            } catch (Exception unused) {
            }
            return jSONObject;
        }
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.put("exception", i2);
        } catch (Exception unused2) {
        }
        return jSONObject2;
    }

    public JSONObject a(Context context, JSONObject jSONObject, JSONObject jSONObject2, String str, String str2, String str3) throws JSONException {
        JSONObject jSONObject3;
        String str4;
        boolean z2;
        String str5;
        Envelope envelope;
        JSONObject jSONObjectOptJSONObject;
        if (ULog.DEBUG && jSONObject != null && jSONObject2 != null) {
            Log.i(f23289c, "headerJSONObject size is " + jSONObject.toString().getBytes().length);
            Log.i(f23289c, "bodyJSONObject size is " + jSONObject2.toString().getBytes().length);
        }
        if (context != null && jSONObject2 != null) {
            try {
                if (jSONObject2.has("analytics") && (jSONObjectOptJSONObject = jSONObject2.optJSONObject("analytics")) != null && jSONObjectOptJSONObject.has(d.f22692n)) {
                    str4 = str2;
                    z2 = true;
                } else {
                    str4 = str2;
                    z2 = false;
                }
                JSONObject jSONObjectA = a(context, str4, z2);
                if (jSONObjectA != null && jSONObject != null) {
                    jSONObjectA = a(jSONObjectA, jSONObject);
                }
                JSONObject jSONObject4 = jSONObjectA;
                if (jSONObject4 != null) {
                    Iterator<String> itKeys = jSONObject2.keys();
                    while (itKeys.hasNext()) {
                        String next = itKeys.next();
                        if (next != null && (next instanceof String)) {
                            String str6 = next;
                            if (jSONObject2.opt(str6) != null) {
                                try {
                                    jSONObject4.put(a(str6), jSONObject2.opt(str6));
                                } catch (Exception unused) {
                                }
                            }
                        }
                    }
                }
                if (TextUtils.isEmpty(str2)) {
                    str4 = am.aG;
                }
                String str7 = TextUtils.isEmpty(str3) ? "1.0.0" : str3;
                if (jSONObject4 != null) {
                    String strSubstring = str4 + "==" + str7 + "&=";
                    if (TextUtils.isEmpty(strSubstring)) {
                        return a(101, jSONObject4);
                    }
                    if (strSubstring.endsWith("&=")) {
                        strSubstring = strSubstring.substring(0, strSubstring.length() - 2);
                    }
                    str5 = strSubstring;
                } else {
                    str5 = null;
                }
                if (jSONObject4 != null) {
                    try {
                        e eVarA = e.a(context);
                        if (eVarA != null) {
                            eVarA.b();
                            String strEncodeToString = Base64.encodeToString(new be().a(eVarA.c()), 0);
                            if (!TextUtils.isEmpty(strEncodeToString)) {
                                JSONObject jSONObject5 = jSONObject4.getJSONObject(a("header"));
                                jSONObject5.put(a(am.Y), strEncodeToString);
                                jSONObject4.put(a("header"), jSONObject5);
                            }
                        }
                    } catch (Exception unused2) {
                    }
                }
                if (jSONObject4 != null && DataHelper.largeThanMaxSize(jSONObject4.toString().getBytes().length, DataHelper.ENVELOPE_ENTITY_RAW_LENGTH_MAX)) {
                    SharedPreferences sharedPreferences = PreferenceWrapper.getDefault(context);
                    if (sharedPreferences != null) {
                        sharedPreferences.edit().putInt("serial", sharedPreferences.getInt("serial", 1) + 1).commit();
                    }
                    return a(113, jSONObject4);
                }
                if (jSONObject4 != null) {
                    Envelope envelopeA = a(context, jSONObject4.toString().getBytes());
                    if (envelopeA == null) {
                        return a(111, jSONObject4);
                    }
                    envelope = envelopeA;
                } else {
                    envelope = null;
                }
                if (envelope != null && DataHelper.largeThanMaxSize(envelope.toBinary().length, DataHelper.ENVELOPE_LENGTH_MAX)) {
                    return a(114, jSONObject4);
                }
                int iA = a(context, envelope, str5, jSONObject4 != null ? jSONObject4.optJSONObject(a("header")).optString(a("app_version")) : null, str);
                if (iA != 0) {
                    return a(iA, jSONObject4);
                }
                if (ULog.DEBUG) {
                    Log.i(f23289c, "constructHeader size is " + jSONObject4.toString().getBytes().length);
                }
                if (!str5.startsWith("z") && !str5.startsWith(am.aC) && !str5.startsWith("t") && !str5.startsWith(am.av) && !com.umeng.commonsdk.stateless.b.a()) {
                    new com.umeng.commonsdk.stateless.b(context);
                    com.umeng.commonsdk.stateless.b.b();
                }
                return jSONObject4;
            } catch (Throwable th) {
                UMCrashManager.reportCrash(context, th);
                if (jSONObject != null) {
                    try {
                        JSONObject jSONObject6 = new JSONObject();
                        try {
                            jSONObject6.put("header", jSONObject);
                        } catch (JSONException unused3) {
                        } catch (Exception e2) {
                            e = e2;
                            jSONObject3 = jSONObject6;
                            UMCrashManager.reportCrash(context, e);
                            return a(110, jSONObject3);
                        }
                        jSONObject3 = jSONObject6;
                    } catch (Exception e3) {
                        e = e3;
                        jSONObject3 = null;
                    }
                } else {
                    jSONObject3 = null;
                }
                if (jSONObject3 == null) {
                    try {
                        jSONObject3 = new JSONObject();
                    } catch (Exception e4) {
                        e = e4;
                        UMCrashManager.reportCrash(context, e);
                        return a(110, jSONObject3);
                    }
                }
                Iterator<String> itKeys2 = jSONObject2.keys();
                while (itKeys2.hasNext()) {
                    String next2 = itKeys2.next();
                    if (next2 != null && (next2 instanceof String)) {
                        String str8 = next2;
                        if (jSONObject2.opt(str8) != null) {
                            try {
                                jSONObject3.put(str8, jSONObject2.opt(str8));
                            } catch (Exception unused4) {
                            }
                        }
                    }
                }
                return a(110, jSONObject3);
            }
        }
        return a(110, (JSONObject) null);
    }

    public JSONObject a(Context context, JSONObject jSONObject, JSONObject jSONObject2, String str) {
        Envelope envelope;
        try {
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put(a("header"), new JSONObject());
            try {
                if (b()) {
                    jSONObject.put("umTaskId", f23293g);
                    jSONObject.put("umCaseId", f23294h);
                }
            } catch (Throwable unused) {
            }
            if (jSONObject != null) {
                jSONObject3 = a(jSONObject3, jSONObject);
            }
            if (jSONObject3 != null && jSONObject2 != null) {
                Iterator<String> itKeys = jSONObject2.keys();
                while (itKeys.hasNext()) {
                    String next = itKeys.next();
                    if (next != null && (next instanceof String)) {
                        String str2 = next;
                        if (jSONObject2.opt(str2) != null) {
                            try {
                                jSONObject3.put(str2, jSONObject2.opt(str2));
                            } catch (Exception unused2) {
                            }
                        }
                    }
                }
            }
            if (jSONObject3 != null && DataHelper.largeThanMaxSize(jSONObject3.toString().getBytes().length, DataHelper.ENVELOPE_ENTITY_RAW_LENGTH_MAX)) {
                SharedPreferences sharedPreferences = PreferenceWrapper.getDefault(context);
                if (sharedPreferences != null) {
                    sharedPreferences.edit().putInt("serial", sharedPreferences.getInt("serial", 1) + 1).commit();
                }
                return a(113, jSONObject3);
            }
            if (jSONObject3 != null) {
                Envelope envelopeA = a(context, jSONObject3.toString().getBytes());
                if (envelopeA == null) {
                    return a(111, jSONObject3);
                }
                envelope = envelopeA;
            } else {
                envelope = null;
            }
            if (envelope != null && DataHelper.largeThanMaxSize(envelope.toBinary().length, DataHelper.ENVELOPE_LENGTH_MAX)) {
                return a(114, jSONObject3);
            }
            int iA = a(context, envelope, "z==1.2.0", jSONObject3 != null ? jSONObject3.optJSONObject(a("header")).optString(a("app_version")) : null, str);
            if (iA != 0) {
                return a(iA, jSONObject3);
            }
            if (ULog.DEBUG) {
                Log.i(f23289c, "constructHeader size is " + jSONObject3.toString().getBytes().length);
            }
            return jSONObject3;
        } catch (Throwable th) {
            UMCrashManager.reportCrash(context, th);
            return a(110, new JSONObject());
        }
    }

    private static JSONObject a(Context context, String str, boolean z2) {
        SharedPreferences sharedPreferences;
        JSONObject jSONObject;
        try {
            sharedPreferences = PreferenceWrapper.getDefault(context);
            if (!TextUtils.isEmpty(f23295i)) {
                try {
                    jSONObject = new JSONObject(f23295i);
                } catch (Exception unused) {
                    jSONObject = null;
                }
            } else {
                UMUtils.saveSDKComponent();
                jSONObject = new JSONObject();
                jSONObject.put(a(am.f22453p), DeviceConfig.getAppMD5Signature(context));
                jSONObject.put(a(am.f22454q), DeviceConfig.getAppSHA1Key(context));
                jSONObject.put(a(am.f22455r), DeviceConfig.getAppHashKey(context));
                jSONObject.put(a("app_version"), DeviceConfig.getAppVersionName(context));
                jSONObject.put(a("version_code"), Integer.parseInt(DeviceConfig.getAppVersionCode(context)));
                jSONObject.put(a(am.f22459v), DeviceConfig.getDeviceIdUmengMD5(context));
                jSONObject.put(a(am.f22460w), DeviceConfig.getCPU());
                String mccmnc = DeviceConfig.getMCCMNC(context);
                if (!TextUtils.isEmpty(mccmnc)) {
                    jSONObject.put(a(am.B), mccmnc);
                    f23288b = mccmnc;
                } else {
                    jSONObject.put(a(am.B), "");
                }
                if (FieldManager.allow(com.umeng.commonsdk.utils.b.I)) {
                    String subOSName = DeviceConfig.getSubOSName(context);
                    if (!TextUtils.isEmpty(subOSName)) {
                        jSONObject.put(a(am.K), subOSName);
                    }
                    String subOSVersion = DeviceConfig.getSubOSVersion(context);
                    if (!TextUtils.isEmpty(subOSVersion)) {
                        jSONObject.put(a(am.L), subOSVersion);
                    }
                }
                String deviceType = DeviceConfig.getDeviceType(context);
                if (!TextUtils.isEmpty(deviceType)) {
                    jSONObject.put(a(am.ai), deviceType);
                }
                jSONObject.put(a("package_name"), DeviceConfig.getPackageName(context));
                jSONObject.put(a("sdk_type"), "Android");
                jSONObject.put(a("device_id"), DeviceConfig.getDeviceId(context));
                jSONObject.put(a("device_model"), Build.MODEL);
                jSONObject.put(a(am.E), Build.BOARD);
                jSONObject.put(a(am.F), Build.BRAND);
                jSONObject.put(a(am.G), Build.TIME);
                jSONObject.put(a(am.H), Build.MANUFACTURER);
                jSONObject.put(a(am.I), Build.ID);
                jSONObject.put(a(am.J), Build.DEVICE);
                jSONObject.put(a("os_version"), Build.VERSION.RELEASE);
                jSONObject.put(a("os"), "Android");
                int[] resolutionArray = DeviceConfig.getResolutionArray(context);
                if (resolutionArray != null) {
                    jSONObject.put(a("resolution"), resolutionArray[1] + "*" + resolutionArray[0]);
                }
                jSONObject.put(a(am.A), DeviceConfig.getMac(context));
                jSONObject.put(a(am.M), DeviceConfig.getTimeZone(context));
                String[] localeInfo = DeviceConfig.getLocaleInfo(context);
                jSONObject.put(a(am.O), localeInfo[0]);
                jSONObject.put(a("language"), localeInfo[1]);
                jSONObject.put(a(am.P), DeviceConfig.getNetworkOperatorName(context));
                jSONObject.put(a("display_name"), DeviceConfig.getAppName(context));
                String[] networkAccessMode = DeviceConfig.getNetworkAccessMode(context);
                if ("Wi-Fi".equals(networkAccessMode[0])) {
                    jSONObject.put(a(am.Q), "wifi");
                } else if ("2G/3G".equals(networkAccessMode[0])) {
                    jSONObject.put(a(am.Q), "2G/3G");
                } else {
                    jSONObject.put(a(am.Q), "unknow");
                }
                if (!"".equals(networkAccessMode[1])) {
                    jSONObject.put(a(am.R), networkAccessMode[1]);
                }
                if (DeviceConfig.isHarmony(context)) {
                    jSONObject.put(a(am.ao), "harmony");
                } else {
                    jSONObject.put(a(am.ao), "Android");
                }
                jSONObject.put(a(am.T), DeviceConfig.getNetworkType(context));
                jSONObject.put(a(am.f22439b), "9.4.7");
                jSONObject.put(a(am.f22440c), SdkVersion.SDK_TYPE);
                jSONObject.put(a(am.f22441d), "1");
                if (!TextUtils.isEmpty(f23287a)) {
                    jSONObject.put(a(am.f22442e), f23287a);
                }
                jSONObject.put(a(am.aj), Build.VERSION.SDK_INT);
                if (!TextUtils.isEmpty(UMUtils.VALUE_REC_VERSION_NAME)) {
                    jSONObject.put(a(am.af), UMUtils.VALUE_REC_VERSION_NAME);
                }
                try {
                    String uUIDForZid = UMUtils.getUUIDForZid(context);
                    if (TextUtils.isEmpty(uUIDForZid)) {
                        UMUtils.setUUIDForZid(context);
                        uUIDForZid = UMUtils.getUUIDForZid(context);
                    }
                    jSONObject.put(a("session_id"), uUIDForZid);
                } catch (Throwable unused2) {
                }
                f23295i = jSONObject.toString();
            }
        } catch (Throwable th) {
            UMCrashManager.reportCrash(context, th);
        }
        if (jSONObject == null) {
            return null;
        }
        try {
            jSONObject.put(a(am.ak), UMUtils.getOaidRequiredTime(context));
        } catch (Exception unused3) {
        }
        try {
            jSONObject.put(a(am.U), sharedPreferences.getInt("successful_request", 0));
            jSONObject.put(a(am.V), sharedPreferences.getInt(am.V, 0));
            jSONObject.put(a(am.W), sharedPreferences.getInt("last_request_spent_ms", 0));
            String zid = UMUtils.getZid(context);
            if (!TextUtils.isEmpty(zid)) {
                jSONObject.put(a(am.al), zid);
            }
            if (!TextUtils.isEmpty(UMUtils.VALUE_ASMS_VERSION)) {
                jSONObject.put(a(am.am), UMUtils.VALUE_ASMS_VERSION);
            }
        } catch (Exception unused4) {
        }
        jSONObject.put(a("channel"), UMUtils.getChannel(context));
        jSONObject.put(a("appkey"), UMUtils.getAppkey(context));
        try {
            String deviceToken = UMUtils.getDeviceToken(context);
            if (!TextUtils.isEmpty(deviceToken)) {
                jSONObject.put(a(am.f22438a), deviceToken);
            }
        } catch (Exception e2) {
            UMCrashManager.reportCrash(context, e2);
        }
        try {
            String strImprintProperty = UMEnvelopeBuild.imprintProperty(context, "umid", null);
            if (!TextUtils.isEmpty(strImprintProperty)) {
                jSONObject.put(a("umid"), strImprintProperty);
            }
        } catch (Exception e3) {
            UMCrashManager.reportCrash(context, e3);
        }
        try {
            jSONObject.put(a("wrapper_type"), a.f23284a);
            jSONObject.put(a("wrapper_version"), a.f23285b);
        } catch (Exception unused5) {
        }
        try {
            int targetSdkVersion = UMUtils.getTargetSdkVersion(context);
            boolean zCheckPermission = UMUtils.checkPermission(context, Permission.READ_PHONE_STATE);
            jSONObject.put(a(am.aU), targetSdkVersion);
            if (zCheckPermission) {
                jSONObject.put(a(am.aV), "yes");
            } else {
                jSONObject.put(a(am.aV), SVGParser.XML_STYLESHEET_ATTR_ALTERNATE_NO);
            }
        } catch (Throwable unused6) {
        }
        try {
            if (b()) {
                jSONObject.put("umTaskId", f23293g);
                jSONObject.put("umCaseId", f23294h);
            }
        } catch (Throwable unused7) {
        }
        if (("t".equals(str) || am.av.equals(str)) && z2) {
            try {
                int[] iArrB = b(context);
                jSONObject.put(a(am.bo), String.valueOf(iArrB[0]) + String.valueOf(iArrB[1]) + String.valueOf(iArrB[2]));
            } catch (Throwable unused8) {
            }
        }
        try {
            Map<String, String> moduleTags = TagHelper.getModuleTags();
            if (moduleTags != null && moduleTags.size() > 0) {
                JSONObject jSONObject2 = new JSONObject();
                for (Map.Entry<String, String> entry : moduleTags.entrySet()) {
                    jSONObject2.put(entry.getKey(), entry.getValue());
                }
                jSONObject.put(a(am.ap), jSONObject2);
            }
        } catch (Throwable unused9) {
        }
        try {
            String realTimeDebugKey = AnalyticsConfig.getRealTimeDebugKey();
            if (!TextUtils.isEmpty(realTimeDebugKey)) {
                jSONObject.put(a(am.bn), realTimeDebugKey);
            }
        } catch (Throwable unused10) {
        }
        try {
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put(a(am.aZ), com.umeng.commonsdk.internal.a.f23171e);
            if (!TextUtils.isEmpty(UMUtils.VALUE_ANALYTICS_VERSION)) {
                jSONObject3.put(a(am.ba), UMUtils.VALUE_ANALYTICS_VERSION);
            }
            if (!TextUtils.isEmpty(UMUtils.VALUE_GAME_VERSION)) {
                jSONObject3.put(a(am.bb), UMUtils.VALUE_GAME_VERSION);
            }
            if (!TextUtils.isEmpty(UMUtils.VALUE_PUSH_VERSION)) {
                jSONObject3.put(a(am.bc), UMUtils.VALUE_PUSH_VERSION);
            }
            if (!TextUtils.isEmpty(UMUtils.VALUE_SHARE_VERSION)) {
                jSONObject3.put(a(am.bd), UMUtils.VALUE_SHARE_VERSION);
            }
            if (!TextUtils.isEmpty(UMUtils.VALUE_APM_VERSION)) {
                jSONObject3.put(a(am.be), UMUtils.VALUE_APM_VERSION);
            }
            if (!TextUtils.isEmpty(UMUtils.VALUE_VERIFY_VERSION)) {
                jSONObject3.put(a(am.bf), UMUtils.VALUE_VERIFY_VERSION);
            }
            if (!TextUtils.isEmpty(UMUtils.VALUE_SMS_VERSION)) {
                jSONObject3.put(a(am.bg), UMUtils.VALUE_SMS_VERSION);
            }
            if (!TextUtils.isEmpty(UMUtils.VALUE_REC_VERSION_NAME)) {
                jSONObject3.put(a(am.bh), UMUtils.VALUE_REC_VERSION_NAME);
            }
            if (!TextUtils.isEmpty(UMUtils.VALUE_VISUAL_VERSION)) {
                jSONObject3.put(a(am.bi), UMUtils.VALUE_VISUAL_VERSION);
            }
            if (!TextUtils.isEmpty(UMUtils.VALUE_ASMS_VERSION)) {
                jSONObject3.put(a(am.bj), UMUtils.VALUE_ASMS_VERSION);
            }
            if (!TextUtils.isEmpty(UMUtils.VALUE_LINK_VERSION)) {
                jSONObject3.put(a(am.bk), UMUtils.VALUE_LINK_VERSION);
            }
            if (!TextUtils.isEmpty(UMUtils.VALUE_ABTEST_VERSION)) {
                jSONObject3.put(a(am.bl), UMUtils.VALUE_ABTEST_VERSION);
            }
            jSONObject.put(a(am.aY), jSONObject3);
        } catch (Throwable unused11) {
        }
        try {
            String apmFlag = UMUtils.getApmFlag();
            if (!TextUtils.isEmpty(apmFlag)) {
                jSONObject.put(a(am.bm), apmFlag);
            }
        } catch (Throwable unused12) {
        }
        byte[] bArrA = ImprintHandler.getImprintService(context).a();
        if (bArrA != null && bArrA.length > 0) {
            try {
                jSONObject.put(a(am.X), Base64.encodeToString(bArrA, 0));
            } catch (JSONException e4) {
                UMCrashManager.reportCrash(context, e4);
            }
        }
        if (jSONObject.length() > 0) {
            return new JSONObject().put(a("header"), jSONObject);
        }
        return null;
    }

    private JSONObject a(JSONObject jSONObject, JSONObject jSONObject2) throws JSONException {
        if (jSONObject != null && jSONObject2 != null && jSONObject.opt(a("header")) != null && (jSONObject.opt(a("header")) instanceof JSONObject)) {
            JSONObject jSONObject3 = (JSONObject) jSONObject.opt(a("header"));
            Iterator<String> itKeys = jSONObject2.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                if (next != null && (next instanceof String)) {
                    String str = next;
                    if (jSONObject2.opt(str) != null) {
                        try {
                            jSONObject3.put(str, jSONObject2.opt(str));
                            if (str.equals(a(d.f22687i)) && (jSONObject2.opt(str) instanceof Integer)) {
                                this.f23298k = ((Integer) jSONObject2.opt(str)).intValue();
                            }
                        } catch (Exception unused) {
                        }
                    }
                }
            }
        }
        return jSONObject;
    }

    private Envelope a(Context context, byte[] bArr) {
        String strImprintProperty = UMEnvelopeBuild.imprintProperty(context, "codex", null);
        int iIntValue = -1;
        try {
            if (!TextUtils.isEmpty(strImprintProperty)) {
                iIntValue = Integer.valueOf(strImprintProperty).intValue();
            }
        } catch (NumberFormatException e2) {
            UMCrashManager.reportCrash(context, e2);
        }
        if (iIntValue == 0) {
            return Envelope.genEnvelope(context, UMUtils.getAppkey(context), bArr);
        }
        if (iIntValue == 1) {
            return Envelope.genEncryptEnvelope(context, UMUtils.getAppkey(context), bArr);
        }
        if (f23297l) {
            return Envelope.genEncryptEnvelope(context, UMUtils.getAppkey(context), bArr);
        }
        return Envelope.genEnvelope(context, UMUtils.getAppkey(context), bArr);
    }

    private int a(Context context, Envelope envelope, String str, String str2, String str3) {
        if (context == null || envelope == null || TextUtils.isEmpty(str)) {
            return 101;
        }
        if (TextUtils.isEmpty(str2)) {
            str2 = DeviceConfig.getAppVersionName(context);
        }
        String strB = com.umeng.commonsdk.stateless.d.b(str3);
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("&&");
        sb.append(str2);
        sb.append(StrPool.UNDERLINE);
        sb.append(System.currentTimeMillis());
        sb.append(StrPool.UNDERLINE);
        sb.append(strB);
        sb.append(".log");
        byte[] binary = envelope.toBinary();
        if (!str.startsWith("z") && !str.startsWith(am.aC) && !str.startsWith(am.av) && !str.startsWith("t")) {
            return com.umeng.commonsdk.stateless.d.a(context, com.umeng.commonsdk.stateless.a.f23258f, sb.toString(), binary);
        }
        return UMFrUtils.saveEnvelopeFile(context, sb.toString(), binary);
    }

    public static void a(boolean z2) {
        f23297l = z2;
    }
}
