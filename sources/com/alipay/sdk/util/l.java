package com.alipay.sdk.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import cn.hutool.crypto.KeyUtil;
import com.alipay.sdk.app.EnvUtils;
import com.github.liuyueyi.quick.transfer.dictionary.DictionaryFactory;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressLint({"SetJavaScriptEnabled", "DefaultLocale"})
/* loaded from: classes2.dex */
public final class l {

    /* renamed from: a, reason: collision with root package name */
    static final String f3387a = "com.alipay.android.app";

    /* renamed from: b, reason: collision with root package name */
    public static final int f3388b = 99;

    /* renamed from: c, reason: collision with root package name */
    public static final int f3389c = 73;

    /* renamed from: d, reason: collision with root package name */
    public static final String[] f3390d = {"10.1.5.1013151", "10.1.5.1013148"};

    /* renamed from: e, reason: collision with root package name */
    private static final String f3391e = "com.eg.android.AlipayGphone";

    /* renamed from: f, reason: collision with root package name */
    private static final String f3392f = "com.eg.android.AlipayGphoneRC";

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        public Signature[] f3393a;

        /* renamed from: b, reason: collision with root package name */
        public int f3394b;

        public final boolean a() {
            Signature[] signatureArr = this.f3393a;
            if (signatureArr == null || signatureArr.length <= 0) {
                return false;
            }
            int i2 = 0;
            while (true) {
                Signature[] signatureArr2 = this.f3393a;
                if (i2 >= signatureArr2.length) {
                    return false;
                }
                String strA = l.a(signatureArr2[i2].toByteArray());
                if (strA != null && !TextUtils.equals(strA, com.alipay.sdk.cons.a.f3202h)) {
                    com.alipay.sdk.app.statistic.a.a(com.alipay.sdk.app.statistic.c.f3112b, com.alipay.sdk.app.statistic.c.f3130t, strA);
                    return true;
                }
                i2++;
            }
        }
    }

    public static String a() {
        return EnvUtils.isSandBox() ? f3392f : f3391e;
    }

    public static String b(String str, String str2, String str3) {
        try {
            int iIndexOf = str3.indexOf(str) + str.length();
            int iIndexOf2 = !TextUtils.isEmpty(str2) ? str3.indexOf(str2, iIndexOf) : 0;
            return iIndexOf2 <= 0 ? str3.substring(iIndexOf) : str3.substring(iIndexOf, iIndexOf2);
        } catch (Throwable unused) {
            return "";
        }
    }

    private static PackageInfo c(Context context, String str) {
        for (PackageInfo packageInfo : context.getPackageManager().getInstalledPackages(192)) {
            if (packageInfo.packageName.equals(str)) {
                return packageInfo;
            }
        }
        return null;
    }

    public static String d() {
        return "-1;-1";
    }

    public static boolean d(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(a(), 128);
            if (packageInfo == null) {
                return false;
            }
            String str = packageInfo.versionName;
            String[] strArr = f3390d;
            if (!TextUtils.equals(str, strArr[0])) {
                if (!TextUtils.equals(str, strArr[1])) {
                    return false;
                }
            }
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }

    public static boolean e(Context context) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(a(), 128);
        } catch (Throwable unused) {
        }
        if (packageInfo == null) {
            return false;
        }
        return packageInfo.versionCode < 99;
    }

    public static String f(Context context) throws IOException {
        return " (" + b() + h.f3376b + c() + h.f3376b + g(context) + ";;" + h(context) + ")(sdk android)";
    }

    public static String g(Context context) {
        return context.getResources().getConfiguration().locale.toString();
    }

    public static String h(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getApplicationContext().getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels + "*" + displayMetrics.heightPixels;
    }

    public static String i(Context context) {
        String strA = k.a(context);
        return strA.substring(0, strA.indexOf("://"));
    }

    public static String j(Context context) {
        String strSubstring = "";
        try {
            String string = "";
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager) context.getApplicationContext().getSystemService(PushConstants.INTENT_ACTIVITY_NAME)).getRunningAppProcesses()) {
                if (runningAppProcessInfo.processName.equals(a())) {
                    string = string + "#M";
                } else {
                    if (runningAppProcessInfo.processName.startsWith(a() + ":")) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(string);
                        sb.append(DictionaryFactory.SHARP);
                        sb.append(runningAppProcessInfo.processName.replace(a() + ":", ""));
                        string = sb.toString();
                    }
                }
            }
            strSubstring = string;
        } catch (Throwable unused) {
        }
        if (strSubstring.length() > 0) {
            strSubstring = strSubstring.substring(1);
        }
        return strSubstring.length() == 0 ? "N" : strSubstring;
    }

    public static String k(Context context) {
        try {
            List<PackageInfo> installedPackages = context.getPackageManager().getInstalledPackages(0);
            StringBuilder sb = new StringBuilder();
            for (int i2 = 0; i2 < installedPackages.size(); i2++) {
                PackageInfo packageInfo = installedPackages.get(i2);
                int i3 = packageInfo.applicationInfo.flags;
                if ((i3 & 1) == 0 && (i3 & 128) == 0) {
                    if (packageInfo.packageName.equals(a())) {
                        sb.append(packageInfo.packageName);
                        sb.append(packageInfo.versionCode);
                        sb.append("-");
                    } else if (!packageInfo.packageName.contains("theme") && !packageInfo.packageName.startsWith("com.google.") && !packageInfo.packageName.startsWith("com.android.")) {
                        sb.append(packageInfo.packageName);
                        sb.append("-");
                    }
                }
            }
            return sb.toString();
        } catch (Throwable th) {
            com.alipay.sdk.app.statistic.a.a(com.alipay.sdk.app.statistic.c.f3112b, "GetInstalledAppEx", th);
            return "";
        }
    }

    private static DisplayMetrics l(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getApplicationContext().getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    public static Map<String, String> a(String str) {
        HashMap map = new HashMap();
        for (String str2 : str.split("&")) {
            int iIndexOf = str2.indexOf("=", 1);
            map.put(str2.substring(0, iIndexOf), URLDecoder.decode(str2.substring(iIndexOf + 1)));
        }
        return map;
    }

    public static String e() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < 24; i2++) {
            int iNextInt = random.nextInt(3);
            if (iNextInt == 0) {
                sb.append(String.valueOf((char) Math.round((Math.random() * 25.0d) + 65.0d)));
            } else if (iNextInt == 1) {
                sb.append(String.valueOf((char) Math.round((Math.random() * 25.0d) + 97.0d)));
            } else if (iNextInt == 2) {
                sb.append(String.valueOf(new Random().nextInt(10)));
            }
        }
        return sb.toString();
    }

    private static PackageInfo b(Context context, String str) throws PackageManager.NameNotFoundException {
        return context.getPackageManager().getPackageInfo(str, 192);
    }

    private static a b(PackageInfo packageInfo) {
        if (packageInfo == null) {
            return null;
        }
        a aVar = new a();
        aVar.f3393a = packageInfo.signatures;
        aVar.f3394b = packageInfo.versionCode;
        return aVar;
    }

    public static boolean c(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(a(), 128);
            if (packageInfo == null) {
                return false;
            }
            return packageInfo.versionCode > 73;
        } catch (Throwable th) {
            com.alipay.sdk.app.statistic.a.a(com.alipay.sdk.app.statistic.c.f3112b, com.alipay.sdk.app.statistic.c.B, th);
            return false;
        }
    }

    public static String a(String str, String str2, String str3) {
        try {
            int iIndexOf = str3.indexOf(str) + str.length();
            if (iIndexOf <= str.length()) {
                return "";
            }
            int iIndexOf2 = !TextUtils.isEmpty(str2) ? str3.indexOf(str2, iIndexOf) : 0;
            if (iIndexOf2 <= 0) {
                return str3.substring(iIndexOf);
            }
            return str3.substring(iIndexOf, iIndexOf2);
        } catch (Throwable unused) {
            return "";
        }
    }

    private static String f() throws IOException {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("/proc/version"), 256);
            try {
                String line = bufferedReader.readLine();
                bufferedReader.close();
                Matcher matcher = Pattern.compile("\\w+\\s+\\w+\\s+([^\\s]+)\\s+\\(([^\\s@]+(?:@[^\\s.]+)?)[^)]*\\)\\s+\\((?:[^(]*\\([^)]*\\))?[^)]*\\)\\s+([^\\s]+)\\s+(?:PREEMPT\\s+)?(.+)").matcher(line);
                if (!matcher.matches() || matcher.groupCount() < 4) {
                    return "Unavailable";
                }
                return matcher.group(1) + "\n" + matcher.group(2) + " " + matcher.group(3) + "\n" + matcher.group(4);
            } catch (Throwable th) {
                bufferedReader.close();
                throw th;
            }
        } catch (IOException unused) {
            return "Unavailable";
        }
    }

    public static boolean b(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(f3387a, 128) != null;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public static String b() {
        return "Android " + Build.VERSION.RELEASE;
    }

    public static String c() throws IOException {
        String strF = f();
        int iIndexOf = strF.indexOf("-");
        if (iIndexOf != -1) {
            strF = strF.substring(0, iIndexOf);
        }
        int iIndexOf2 = strF.indexOf("\n");
        if (iIndexOf2 != -1) {
            strF = strF.substring(0, iIndexOf2);
        }
        return "Linux " + strF;
    }

    public static boolean b(String str) {
        return Pattern.compile("^http(s)?://([a-z0-9_\\-]+\\.)*(alipaydev|alipay|taobao)\\.(com|net)(:\\d+)?(/.*)?$").matcher(str).matches();
    }

    public static String a(byte[] bArr) {
        try {
            String string = ((X509Certificate) CertificateFactory.getInstance(KeyUtil.CERT_TYPE_X509).generateCertificate(new ByteArrayInputStream(bArr))).getPublicKey().toString();
            if (string.indexOf("modulus") != -1) {
                return string.substring(string.indexOf("modulus") + 8, string.lastIndexOf(",")).trim();
            }
            return null;
        } catch (Exception e2) {
            com.alipay.sdk.app.statistic.a.a("auth", com.alipay.sdk.app.statistic.c.f3124n, e2);
            return null;
        }
    }

    @SuppressLint({"InlinedApi"})
    private static boolean c(PackageInfo packageInfo) {
        int i2 = packageInfo.applicationInfo.flags;
        return (i2 & 1) == 0 && (i2 & 128) == 0;
    }

    public static a a(Context context) {
        return a(context, a());
    }

    private static boolean a(PackageInfo packageInfo) {
        String str = "";
        boolean z2 = false;
        if (packageInfo == null) {
            str = "info == null";
        } else {
            Signature[] signatureArr = packageInfo.signatures;
            if (signatureArr == null) {
                str = "info.signatures == null";
            } else if (signatureArr.length <= 0) {
                str = "info.signatures.length <= 0";
            } else {
                z2 = true;
            }
        }
        if (!z2) {
            com.alipay.sdk.app.statistic.a.a("auth", com.alipay.sdk.app.statistic.c.f3122l, str);
        }
        return z2;
    }

    public static WebView a(Activity activity, String str, String str2) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Context applicationContext = activity.getApplicationContext();
        if (!TextUtils.isEmpty(str2)) {
            CookieSyncManager.createInstance(applicationContext).sync();
            CookieManager.getInstance().setCookie(str, str2);
            CookieSyncManager.getInstance().sync();
        }
        LinearLayout linearLayout = new LinearLayout(applicationContext);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
        linearLayout.setOrientation(1);
        activity.setContentView(linearLayout, layoutParams);
        WebView webView = new WebView(applicationContext);
        layoutParams.weight = 1.0f;
        webView.setVisibility(0);
        linearLayout.addView(webView, layoutParams);
        WebSettings settings = webView.getSettings();
        settings.setUserAgentString(settings.getUserAgentString() + f(applicationContext));
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        settings.setSupportMultipleWindows(true);
        settings.setJavaScriptEnabled(true);
        settings.setSavePassword(false);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setMinimumFontSize(settings.getMinimumFontSize() + 8);
        settings.setAllowFileAccess(false);
        settings.setTextSize(WebSettings.TextSize.NORMAL);
        webView.setVerticalScrollbarOverlay(true);
        webView.setDownloadListener(new m(applicationContext));
        try {
            Method method = webView.getSettings().getClass().getMethod("setDomStorageEnabled", Boolean.TYPE);
            if (method != null) {
                method.invoke(webView.getSettings(), Boolean.TRUE);
            }
        } catch (Exception unused) {
        }
        try {
            try {
                webView.removeJavascriptInterface("searchBoxJavaBridge_");
                webView.removeJavascriptInterface("accessibility");
                webView.removeJavascriptInterface("accessibilityTraversal");
            } catch (Throwable unused2) {
                Method method2 = webView.getClass().getMethod("removeJavascriptInterface", new Class[0]);
                if (method2 != null) {
                    method2.invoke(webView, "searchBoxJavaBridge_");
                    method2.invoke(webView, "accessibility");
                    method2.invoke(webView, "accessibilityTraversal");
                }
            }
        } catch (Throwable unused3) {
        }
        webView.getSettings().setCacheMode(2);
        webView.loadUrl(str);
        return webView;
    }

    public static boolean a(WebView webView, String str, Activity activity) throws NumberFormatException {
        String strSubstring;
        if (TextUtils.isEmpty(str)) {
            return true;
        }
        if (!str.toLowerCase().startsWith(com.alipay.sdk.cons.a.f3203i.toLowerCase()) && !str.toLowerCase().startsWith(com.alipay.sdk.cons.a.f3204j.toLowerCase())) {
            if (!TextUtils.equals(str, com.alipay.sdk.cons.a.f3206l) && !TextUtils.equals(str, com.alipay.sdk.cons.a.f3207m)) {
                if (str.startsWith(com.alipay.sdk.cons.a.f3205k)) {
                    try {
                        String strSubstring2 = str.substring(str.indexOf(com.alipay.sdk.cons.a.f3205k) + 24);
                        int i2 = Integer.parseInt(strSubstring2.substring(strSubstring2.lastIndexOf(com.alipay.sdk.cons.a.f3208n) + 10));
                        if (i2 != com.alipay.sdk.app.j.SUCCEEDED.f3105h && i2 != com.alipay.sdk.app.j.PAY_WAITTING.f3105h) {
                            com.alipay.sdk.app.j jVarA = com.alipay.sdk.app.j.a(com.alipay.sdk.app.j.FAILED.f3105h);
                            com.alipay.sdk.app.i.f3096a = com.alipay.sdk.app.i.a(jVarA.f3105h, jVarA.f3106i, "");
                        } else {
                            if (com.alipay.sdk.cons.a.f3212r) {
                                StringBuilder sb = new StringBuilder();
                                String strDecode = URLDecoder.decode(str);
                                String strDecode2 = URLDecoder.decode(strDecode);
                                String str2 = strDecode2.substring(strDecode2.indexOf(com.alipay.sdk.cons.a.f3205k) + 24, strDecode2.lastIndexOf(com.alipay.sdk.cons.a.f3208n)).split(com.alipay.sdk.cons.a.f3210p)[0];
                                int iIndexOf = strDecode.indexOf(com.alipay.sdk.cons.a.f3210p) + 12;
                                sb.append(str2);
                                sb.append(com.alipay.sdk.cons.a.f3210p);
                                sb.append(strDecode.substring(iIndexOf, strDecode.indexOf("&", iIndexOf)));
                                sb.append(strDecode.substring(strDecode.indexOf("&", iIndexOf)));
                                strSubstring = sb.toString();
                            } else {
                                String strDecode3 = URLDecoder.decode(str);
                                strSubstring = strDecode3.substring(strDecode3.indexOf(com.alipay.sdk.cons.a.f3205k) + 24, strDecode3.lastIndexOf(com.alipay.sdk.cons.a.f3208n));
                            }
                            com.alipay.sdk.app.j jVarA2 = com.alipay.sdk.app.j.a(i2);
                            com.alipay.sdk.app.i.f3096a = com.alipay.sdk.app.i.a(jVarA2.f3105h, jVarA2.f3106i, strSubstring);
                        }
                    } catch (Exception unused) {
                        com.alipay.sdk.app.j jVarA3 = com.alipay.sdk.app.j.a(com.alipay.sdk.app.j.PARAMS_ERROR.f3105h);
                        com.alipay.sdk.app.i.f3096a = com.alipay.sdk.app.i.a(jVarA3.f3105h, jVarA3.f3106i, "");
                    }
                    activity.runOnUiThread(new n(activity));
                    return true;
                }
                webView.loadUrl(str);
                return true;
            }
            com.alipay.sdk.app.i.f3096a = com.alipay.sdk.app.i.a();
            activity.finish();
            return true;
        }
        try {
            a aVarA = a(activity);
            if (aVarA != null && !aVarA.a()) {
                if (str.startsWith("intent://platformapi/startapp")) {
                    str = str.replaceFirst("intent://platformapi/startapp\\?", com.alipay.sdk.cons.a.f3203i);
                }
                activity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
            }
        } catch (Throwable unused2) {
        }
        return true;
    }

    private static a a(Context context, String str) {
        boolean zA;
        PackageInfo packageInfoC;
        try {
            packageInfoC = context.getPackageManager().getPackageInfo(str, 192);
            if (!a(packageInfoC)) {
                try {
                    packageInfoC = c(context, str);
                } catch (Throwable th) {
                    com.alipay.sdk.app.statistic.a.a("auth", com.alipay.sdk.app.statistic.c.f3123m, th);
                }
            }
        } catch (Throwable th2) {
            try {
                com.alipay.sdk.app.statistic.a.a("auth", com.alipay.sdk.app.statistic.c.f3121k, th2);
                if (zA) {
                    packageInfoC = null;
                } else {
                    try {
                    } catch (Throwable th3) {
                        packageInfoC = null;
                        if (a(packageInfoC)) {
                            return null;
                        }
                        a aVar = new a();
                        aVar.f3393a = packageInfoC.signatures;
                        aVar.f3394b = packageInfoC.versionCode;
                        return aVar;
                    }
                }
            } finally {
                if (!a((PackageInfo) null)) {
                    try {
                        c(context, str);
                    } catch (Throwable th32) {
                        com.alipay.sdk.app.statistic.a.a("auth", com.alipay.sdk.app.statistic.c.f3123m, th32);
                    }
                }
            }
        }
        if (a(packageInfoC) || packageInfoC == null) {
            return null;
        }
        a aVar2 = new a();
        aVar2.f3393a = packageInfoC.signatures;
        aVar2.f3394b = packageInfoC.versionCode;
        return aVar2;
    }
}
