package com.alipay.sdk.sys;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import com.alipay.sdk.util.l;
import java.io.UnsupportedEncodingException;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class a {

    /* renamed from: a, reason: collision with root package name */
    public static final String f3316a = "\"&";

    /* renamed from: b, reason: collision with root package name */
    public static final String f3317b = "&";

    /* renamed from: c, reason: collision with root package name */
    public static final String f3318c = "bizcontext=\"";

    /* renamed from: d, reason: collision with root package name */
    public static final String f3319d = "bizcontext=";

    /* renamed from: e, reason: collision with root package name */
    public static final String f3320e = "\"";

    /* renamed from: f, reason: collision with root package name */
    public static final String f3321f = "appkey";

    /* renamed from: g, reason: collision with root package name */
    public static final String f3322g = "ty";

    /* renamed from: h, reason: collision with root package name */
    public static final String f3323h = "sv";

    /* renamed from: i, reason: collision with root package name */
    public static final String f3324i = "an";

    /* renamed from: j, reason: collision with root package name */
    public static final String f3325j = "setting";

    /* renamed from: k, reason: collision with root package name */
    public static final String f3326k = "av";

    /* renamed from: l, reason: collision with root package name */
    public static final String f3327l = "sdk_start_time";

    /* renamed from: m, reason: collision with root package name */
    public static final String f3328m = "UTF-8";

    /* renamed from: n, reason: collision with root package name */
    private String f3329n;

    /* renamed from: o, reason: collision with root package name */
    private String f3330o;

    /* renamed from: p, reason: collision with root package name */
    private Context f3331p;

    public a(Context context) throws PackageManager.NameNotFoundException {
        this.f3329n = "";
        this.f3330o = "";
        this.f3331p = null;
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            this.f3329n = packageInfo.versionName;
            this.f3330o = packageInfo.packageName;
            this.f3331p = context.getApplicationContext();
        } catch (Exception unused) {
        }
    }

    private static boolean b(String str) {
        return !str.contains(f3316a);
    }

    private String c(String str) {
        try {
            String strA = a(str, "&", f3319d);
            if (TextUtils.isEmpty(strA)) {
                str = str + "&" + b(f3319d, "");
            } else {
                int iIndexOf = str.indexOf(strA);
                str = str.substring(0, iIndexOf) + b(strA, f3319d, "") + str.substring(iIndexOf + strA.length());
            }
        } catch (Throwable unused) {
        }
        return str;
    }

    private String d(String str) {
        try {
            String strA = a(str, f3316a, f3318c);
            if (TextUtils.isEmpty(strA)) {
                return str + "&" + b(f3318c, "\"");
            }
            if (!strA.endsWith("\"")) {
                strA = strA + "\"";
            }
            int iIndexOf = str.indexOf(strA);
            return str.substring(0, iIndexOf) + b(strA, f3318c, "\"") + str.substring(iIndexOf + strA.length());
        } catch (Throwable unused) {
            return str;
        }
    }

    public final String a(String str) {
        return (TextUtils.isEmpty(str) || str.startsWith("new_external_info==")) ? str : str.contains(f3316a) ^ true ? c(str) : d(str);
    }

    private String b(String str, String str2) throws JSONException, UnsupportedEncodingException {
        return str + a("", "") + str2;
    }

    private String b(String str, String str2, String str3) throws JSONException, UnsupportedEncodingException {
        String strSubstring = str.substring(str2.length());
        JSONObject jSONObject = new JSONObject(strSubstring.substring(0, strSubstring.length() - str3.length()));
        if (!jSONObject.has("appkey")) {
            jSONObject.put("appkey", com.alipay.sdk.cons.a.f3198d);
        }
        if (!jSONObject.has(f3322g)) {
            jSONObject.put(f3322g, "and_lite");
        }
        if (!jSONObject.has(f3323h)) {
            jSONObject.put(f3323h, com.alipay.sdk.cons.a.f3201g);
        }
        if (!jSONObject.has(f3324i) && (!this.f3330o.contains(f3325j) || !l.e(this.f3331p))) {
            jSONObject.put(f3324i, this.f3330o);
        }
        if (!jSONObject.has("av")) {
            jSONObject.put("av", this.f3329n);
        }
        if (!jSONObject.has(f3327l)) {
            jSONObject.put(f3327l, System.currentTimeMillis());
        }
        return str2 + jSONObject.toString() + str3;
    }

    private static String a(String str, String str2, String str3) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String[] strArrSplit = str.split(str2);
        for (int i2 = 0; i2 < strArrSplit.length; i2++) {
            if (!TextUtils.isEmpty(strArrSplit[i2]) && strArrSplit[i2].startsWith(str3)) {
                return strArrSplit[i2];
            }
        }
        return null;
    }

    public final String a(String str, String str2) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("appkey", com.alipay.sdk.cons.a.f3198d);
            jSONObject.put(f3322g, "and_lite");
            jSONObject.put(f3323h, com.alipay.sdk.cons.a.f3201g);
            if (!this.f3330o.contains(f3325j) || !l.e(this.f3331p)) {
                jSONObject.put(f3324i, this.f3330o);
            }
            jSONObject.put("av", this.f3329n);
            jSONObject.put(f3327l, System.currentTimeMillis());
            if (!TextUtils.isEmpty(str)) {
                jSONObject.put(str, str2);
            }
            return jSONObject.toString();
        } catch (Throwable unused) {
            return "";
        }
    }
}
