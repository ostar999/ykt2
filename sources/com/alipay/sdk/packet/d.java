package com.alipay.sdk.packet;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.text.TextUtils;
import android.widget.TextView;
import com.alipay.sdk.util.h;
import com.alipay.sdk.util.k;
import com.alipay.sdk.util.l;
import com.weibo.ssosdk.WeiboSsoSdk;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.compress.archivers.tar.TarConstants;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.message.BasicHeader;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public abstract class d {

    /* renamed from: a, reason: collision with root package name */
    public static final String f3285a = "msp-gzip";

    /* renamed from: b, reason: collision with root package name */
    public static final String f3286b = "Msp-Param";

    /* renamed from: c, reason: collision with root package name */
    public static final String f3287c = "Operation-Type";

    /* renamed from: d, reason: collision with root package name */
    public static final String f3288d = "content-type";

    /* renamed from: e, reason: collision with root package name */
    public static final String f3289e = "Version";

    /* renamed from: f, reason: collision with root package name */
    public static final String f3290f = "AppId";

    /* renamed from: g, reason: collision with root package name */
    public static final String f3291g = "des-mode";

    /* renamed from: h, reason: collision with root package name */
    public static final String f3292h = "namespace";

    /* renamed from: i, reason: collision with root package name */
    public static final String f3293i = "api_name";

    /* renamed from: j, reason: collision with root package name */
    public static final String f3294j = "api_version";

    /* renamed from: k, reason: collision with root package name */
    public static final String f3295k = "data";

    /* renamed from: l, reason: collision with root package name */
    public static final String f3296l = "params";

    /* renamed from: m, reason: collision with root package name */
    public static final String f3297m = "public_key";

    /* renamed from: n, reason: collision with root package name */
    public static final String f3298n = "device";

    /* renamed from: o, reason: collision with root package name */
    public static final String f3299o = "action";

    /* renamed from: p, reason: collision with root package name */
    public static final String f3300p = "type";

    /* renamed from: q, reason: collision with root package name */
    public static final String f3301q = "method";

    /* renamed from: t, reason: collision with root package name */
    private static com.alipay.sdk.net.a f3302t;

    /* renamed from: r, reason: collision with root package name */
    protected boolean f3303r = true;

    /* renamed from: s, reason: collision with root package name */
    protected boolean f3304s = true;

    private static com.alipay.sdk.net.a b(Context context, String str) {
        com.alipay.sdk.net.a aVar = f3302t;
        if (aVar == null) {
            f3302t = new com.alipay.sdk.net.a(context, str);
        } else if (!TextUtils.equals(str, aVar.f3276b)) {
            f3302t.f3276b = str;
        }
        return f3302t;
    }

    public List<Header> a(boolean z2, String str) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new BasicHeader(f3285a, String.valueOf(z2)));
        arrayList.add(new BasicHeader(f3287c, "alipay.msp.cashier.dispatch.bytes"));
        arrayList.add(new BasicHeader(f3288d, "application/octet-stream"));
        arrayList.add(new BasicHeader("Version", WeiboSsoSdk.SDK_VERSION_CODE));
        arrayList.add(new BasicHeader("AppId", "TAOBAO"));
        arrayList.add(new BasicHeader(f3286b, a.a(str)));
        arrayList.add(new BasicHeader(f3291g, "CBC"));
        return arrayList;
    }

    public abstract JSONObject a() throws JSONException;

    public String b() {
        return "4.9.0";
    }

    public String c() throws JSONException {
        HashMap map = new HashMap();
        map.put(f3298n, Build.MODEL);
        map.put("namespace", "com.alipay.mobilecashier");
        map.put("api_name", "com.alipay.mcpay");
        map.put(f3294j, b());
        return a((HashMap<String, String>) map, (HashMap<String, String>) new HashMap());
    }

    private static byte[] b(HttpResponse httpResponse) throws Throwable {
        ByteArrayOutputStream byteArrayOutputStream;
        byte[] bArr = new byte[1024];
        InputStream inputStream = null;
        try {
            InputStream content = httpResponse.getEntity().getContent();
            try {
                byteArrayOutputStream = new ByteArrayOutputStream();
                while (true) {
                    try {
                        int i2 = content.read(bArr);
                        if (i2 == -1) {
                            break;
                        }
                        byteArrayOutputStream.write(bArr, 0, i2);
                    } catch (Throwable th) {
                        th = th;
                        inputStream = content;
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (Exception unused) {
                            }
                        }
                        if (byteArrayOutputStream != null) {
                            try {
                                byteArrayOutputStream.close();
                                throw th;
                            } catch (Exception unused2) {
                                throw th;
                            }
                        }
                        throw th;
                    }
                }
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                try {
                    content.close();
                } catch (Exception unused3) {
                }
                try {
                    byteArrayOutputStream.close();
                } catch (Exception unused4) {
                }
                return byteArray;
            } catch (Throwable th2) {
                th = th2;
                byteArrayOutputStream = null;
            }
        } catch (Throwable th3) {
            th = th3;
            byteArrayOutputStream = null;
        }
    }

    public static JSONObject a(String str, String str2) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("type", str);
        jSONObject2.put("method", str2);
        jSONObject.put("action", jSONObject2);
        return jSONObject;
    }

    public String a(String str, JSONObject jSONObject) throws JSONException {
        JSONObject jSONObject2;
        String str2;
        String str3;
        CharSequence charSequence;
        String str4;
        com.alipay.sdk.sys.b bVar;
        JSONObject jSONObject3;
        String str5;
        String str6;
        String strA;
        String strB;
        com.alipay.sdk.sys.b bVarA = com.alipay.sdk.sys.b.a();
        com.alipay.sdk.tid.b bVarA2 = com.alipay.sdk.tid.b.a();
        JSONObject jSONObjectA = com.alipay.sdk.util.b.a(new JSONObject(), jSONObject);
        try {
            jSONObjectA.put(com.alipay.sdk.cons.b.f3217c, bVarA2.f3338a);
            com.alipay.sdk.data.c cVarA = com.alipay.sdk.data.c.a();
            Context context = com.alipay.sdk.sys.b.a().f3333a;
            com.alipay.sdk.util.a aVarA = com.alipay.sdk.util.a.a(context);
            if (TextUtils.isEmpty(cVarA.f3257a)) {
                bVar = bVarA;
                String strB2 = l.b();
                jSONObject3 = jSONObjectA;
                try {
                    String strC = l.c();
                    str5 = com.alipay.sdk.cons.b.f3216b;
                    String strG = l.g(context);
                    str2 = "utdid";
                    String strI = l.i(context);
                    str4 = com.alipay.sdk.cons.b.f3217c;
                    str3 = "wifi";
                    charSequence = " ";
                    cVarA.f3257a = "Msp/15.5.5 (" + strB2 + h.f3376b + strC + h.f3376b + strG + h.f3376b + strI + h.f3376b + l.h(context) + h.f3376b + Float.toString(new TextView(context).getTextSize());
                } catch (Throwable unused) {
                    jSONObject2 = jSONObject3;
                }
            } else {
                str2 = "utdid";
                str3 = "wifi";
                charSequence = " ";
                str4 = com.alipay.sdk.cons.b.f3217c;
                bVar = bVarA;
                jSONObject3 = jSONObjectA;
                str5 = com.alipay.sdk.cons.b.f3216b;
            }
            String str7 = com.alipay.sdk.util.a.b(context).f3363p;
            String strD = l.d();
            String strA2 = aVarA.a();
            String strB3 = aVarA.b();
            Context context2 = com.alipay.sdk.sys.b.a().f3333a;
            SharedPreferences sharedPreferences = context2.getSharedPreferences("virtualImeiAndImsi", 0);
            String string = sharedPreferences.getString("virtual_imsi", null);
            if (TextUtils.isEmpty(string)) {
                if (TextUtils.isEmpty(com.alipay.sdk.tid.b.a().f3338a)) {
                    String strC2 = com.alipay.sdk.sys.b.a().c();
                    if (TextUtils.isEmpty(strC2)) {
                        str6 = strB3;
                        strA = com.alipay.sdk.data.c.b();
                    } else {
                        str6 = strB3;
                        strA = strC2.substring(3, 18);
                    }
                } else {
                    str6 = strB3;
                    strA = com.alipay.sdk.util.a.a(context2).a();
                }
                sharedPreferences.edit().putString("virtual_imsi", strA).commit();
            } else {
                str6 = strB3;
                strA = string;
            }
            Context context3 = com.alipay.sdk.sys.b.a().f3333a;
            SharedPreferences sharedPreferences2 = context3.getSharedPreferences("virtualImeiAndImsi", 0);
            String string2 = sharedPreferences2.getString("virtual_imei", null);
            if (TextUtils.isEmpty(string2)) {
                if (TextUtils.isEmpty(com.alipay.sdk.tid.b.a().f3338a)) {
                    strB = com.alipay.sdk.data.c.b();
                } else {
                    strB = com.alipay.sdk.util.a.a(context3).b();
                }
                string2 = strB;
                sharedPreferences2.edit().putString("virtual_imei", string2).commit();
            }
            cVarA.f3259c = bVarA2.f3339b;
            CharSequence charSequence2 = charSequence;
            String strReplace = Build.MANUFACTURER.replace(h.f3376b, charSequence2);
            String strReplace2 = Build.MODEL.replace(h.f3376b, charSequence2);
            boolean zB = com.alipay.sdk.sys.b.b();
            String str8 = aVarA.f3342a;
            String str9 = str3;
            WifiInfo connectionInfo = ((WifiManager) context.getApplicationContext().getSystemService(str9)).getConnectionInfo();
            String ssid = connectionInfo != null ? connectionInfo.getSSID() : "-1";
            WifiInfo connectionInfo2 = ((WifiManager) context.getApplicationContext().getSystemService(str9)).getConnectionInfo();
            String bssid = connectionInfo2 != null ? connectionInfo2.getBSSID() : TarConstants.VERSION_POSIX;
            StringBuilder sb = new StringBuilder();
            sb.append(cVarA.f3257a);
            sb.append(h.f3376b);
            sb.append(str7);
            sb.append(h.f3376b);
            sb.append(strD);
            sb.append(h.f3376b);
            sb.append("1");
            sb.append(h.f3376b);
            sb.append(strA2);
            sb.append(h.f3376b);
            sb.append(str6);
            sb.append(h.f3376b);
            sb.append(cVarA.f3259c);
            sb.append(h.f3376b);
            sb.append(strReplace);
            sb.append(h.f3376b);
            sb.append(strReplace2);
            sb.append(h.f3376b);
            sb.append(zB);
            sb.append(h.f3376b);
            sb.append(str8);
            sb.append(";-1;-1;");
            sb.append(cVarA.f3258b);
            sb.append(h.f3376b);
            sb.append(strA);
            sb.append(h.f3376b);
            sb.append(string2);
            sb.append(h.f3376b);
            sb.append(ssid);
            sb.append(h.f3376b);
            sb.append(bssid);
            HashMap<String, String> map = new HashMap<>();
            map.put(str4, bVarA2.f3338a);
            String str10 = str2;
            map.put(str10, com.alipay.sdk.sys.b.a().c());
            String strB4 = cVarA.b(context, map);
            if (!TextUtils.isEmpty(strB4)) {
                sb.append(h.f3376b);
                sb.append(strB4);
            }
            sb.append(")");
            jSONObject2 = jSONObject3;
            try {
                jSONObject2.put(str5, sb.toString());
                com.alipay.sdk.sys.b bVar2 = bVar;
                jSONObject2.put(com.alipay.sdk.cons.b.f3219e, l.c(bVar2.f3333a));
                jSONObject2.put(com.alipay.sdk.cons.b.f3220f, l.b(bVar2.f3333a));
                jSONObject2.put(com.alipay.sdk.cons.b.f3218d, str);
                jSONObject2.put(com.alipay.sdk.cons.b.f3222h, com.alipay.sdk.cons.a.f3198d);
                jSONObject2.put(str10, bVar2.c());
                jSONObject2.put(com.alipay.sdk.cons.b.f3224j, bVarA2.f3339b);
                com.alipay.sdk.data.c.a();
                jSONObject2.put(com.alipay.sdk.cons.b.f3225k, com.alipay.sdk.data.c.a(bVar2.f3333a));
            } catch (Throwable unused2) {
            }
        } catch (Throwable unused3) {
            jSONObject2 = jSONObjectA;
        }
        return jSONObject2.toString();
    }

    private static String a(HttpResponse httpResponse, String str) {
        Header[] allHeaders;
        String name;
        if (httpResponse == null || (allHeaders = httpResponse.getAllHeaders()) == null || allHeaders.length <= 0) {
            return null;
        }
        for (Header header : allHeaders) {
            if (header != null && (name = header.getName()) != null && name.equalsIgnoreCase(str)) {
                return header.getValue();
            }
        }
        return null;
    }

    public static String a(HashMap<String, String> map, HashMap<String, String> map2) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            jSONObject2.put(entry.getKey(), entry.getValue());
        }
        JSONObject jSONObject3 = new JSONObject();
        for (Map.Entry<String, String> entry2 : map2.entrySet()) {
            jSONObject3.put(entry2.getKey(), entry2.getValue());
        }
        jSONObject2.put("params", jSONObject3);
        jSONObject.put("data", jSONObject2);
        return jSONObject.toString();
    }

    private static boolean a(String str) throws JSONException {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        try {
            JSONObject jSONObject = new JSONObject(str).getJSONObject("data");
            if (!jSONObject.has("params")) {
                return false;
            }
            String strOptString = jSONObject.getJSONObject("params").optString(f3297m, null);
            if (TextUtils.isEmpty(strOptString)) {
                return false;
            }
            com.alipay.sdk.sys.b.a();
            com.alipay.sdk.data.c.a().a(strOptString);
            return true;
        } catch (JSONException unused) {
            return false;
        }
    }

    private b a(Context context) throws Throwable {
        return a(context, "", k.a(context), true);
    }

    public b a(Context context, String str) throws Throwable {
        return a(context, str, k.a(context), true);
    }

    private b a(Context context, String str, String str2) throws Throwable {
        return a(context, str, str2, true);
    }

    public final b a(Context context, String str, String str2, boolean z2) throws Throwable {
        Header[] allHeaders;
        String name;
        e eVar = new e(this.f3304s);
        c cVarA = eVar.a(new b(c(), a(str, a())), this.f3303r);
        com.alipay.sdk.net.a aVar = f3302t;
        if (aVar == null) {
            f3302t = new com.alipay.sdk.net.a(context, str2);
        } else if (!TextUtils.equals(str2, aVar.f3276b)) {
            f3302t.f3276b = str2;
        }
        HttpResponse httpResponseA = f3302t.a(cVarA.f3284b, a(cVarA.f3283a, str));
        String value = null;
        if (httpResponseA != null && (allHeaders = httpResponseA.getAllHeaders()) != null && allHeaders.length > 0) {
            int length = allHeaders.length;
            int i2 = 0;
            while (true) {
                if (i2 < length) {
                    Header header = allHeaders[i2];
                    if (header != null && (name = header.getName()) != null && name.equalsIgnoreCase(f3285a)) {
                        value = header.getValue();
                        break;
                    }
                    i2++;
                } else {
                    break;
                }
            }
        }
        b bVarA = eVar.a(new c(Boolean.valueOf(value).booleanValue(), b(httpResponseA)));
        return (bVarA != null && a(bVarA.f3281a) && z2) ? a(context, str, str2, false) : bVarA;
    }

    private static boolean a(HttpResponse httpResponse) {
        Header[] allHeaders;
        String name;
        String value = null;
        if (httpResponse != null && (allHeaders = httpResponse.getAllHeaders()) != null && allHeaders.length > 0) {
            int length = allHeaders.length;
            int i2 = 0;
            while (true) {
                if (i2 < length) {
                    Header header = allHeaders[i2];
                    if (header != null && (name = header.getName()) != null && name.equalsIgnoreCase(f3285a)) {
                        value = header.getValue();
                        break;
                    }
                    i2++;
                } else {
                    break;
                }
            }
        }
        return Boolean.valueOf(value).booleanValue();
    }
}
