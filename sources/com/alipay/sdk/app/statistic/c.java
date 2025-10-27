package com.alipay.sdk.app.statistic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import cn.hutool.core.text.StrPool;
import com.xiaomi.mipush.sdk.Constants;
import java.text.SimpleDateFormat;
import java.util.Date;

/* loaded from: classes2.dex */
public final class c {
    public static final String A = "BindWaitTimeoutEx";
    public static final String B = "CheckClientExistEx";
    public static final String C = "CheckClientSignEx";
    public static final String D = "GetInstalledAppEx";
    public static final String E = "GetInstalledAppEx";
    public static final String F = "partner";
    public static final String G = "out_trade_no";
    public static final String H = "trade_no";

    /* renamed from: a, reason: collision with root package name */
    public static final String f3111a = "net";

    /* renamed from: b, reason: collision with root package name */
    public static final String f3112b = "biz";

    /* renamed from: c, reason: collision with root package name */
    public static final String f3113c = "cp";

    /* renamed from: d, reason: collision with root package name */
    public static final String f3114d = "auth";

    /* renamed from: e, reason: collision with root package name */
    public static final String f3115e = "third";

    /* renamed from: f, reason: collision with root package name */
    public static final String f3116f = "FormatResultEx";

    /* renamed from: g, reason: collision with root package name */
    public static final String f3117g = "GetApdidEx";

    /* renamed from: h, reason: collision with root package name */
    public static final String f3118h = "GetApdidNull";

    /* renamed from: i, reason: collision with root package name */
    public static final String f3119i = "GetApdidTimeout";

    /* renamed from: j, reason: collision with root package name */
    public static final String f3120j = "GetUtdidEx";

    /* renamed from: k, reason: collision with root package name */
    public static final String f3121k = "GetPackageInfoEx";

    /* renamed from: l, reason: collision with root package name */
    public static final String f3122l = "NotIncludeSignatures";

    /* renamed from: m, reason: collision with root package name */
    public static final String f3123m = "GetInstalledPackagesEx";

    /* renamed from: n, reason: collision with root package name */
    public static final String f3124n = "GetPublicKeyFromSignEx";

    /* renamed from: o, reason: collision with root package name */
    public static final String f3125o = "H5PayNetworkError";

    /* renamed from: p, reason: collision with root package name */
    public static final String f3126p = "H5AuthNetworkError";

    /* renamed from: q, reason: collision with root package name */
    public static final String f3127q = "SSLError";

    /* renamed from: r, reason: collision with root package name */
    public static final String f3128r = "H5PayDataAnalysisError";

    /* renamed from: s, reason: collision with root package name */
    public static final String f3129s = "H5AuthDataAnalysisError";

    /* renamed from: t, reason: collision with root package name */
    public static final String f3130t = "PublicKeyUnmatch";

    /* renamed from: u, reason: collision with root package name */
    public static final String f3131u = "ClientBindFailed";

    /* renamed from: v, reason: collision with root package name */
    public static final String f3132v = "TriDesEncryptError";

    /* renamed from: w, reason: collision with root package name */
    public static final String f3133w = "TriDesDecryptError";

    /* renamed from: x, reason: collision with root package name */
    public static final String f3134x = "ClientBindException";

    /* renamed from: y, reason: collision with root package name */
    public static final String f3135y = "SaveTradeTokenError";

    /* renamed from: z, reason: collision with root package name */
    public static final String f3136z = "ClientBindServiceFailed";
    String I;
    String J;
    String K;
    String L;
    String M;
    String N;
    String O;
    String P;
    String Q = "";
    String R;

    public c(Context context) {
        context = context != null ? context.getApplicationContext() : context;
        this.I = String.format("123456789,%s", new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").format(new Date()));
        this.K = a(context);
        this.L = String.format("android,3,%s,%s,com.alipay.mcpay,5.0,-,-,-", a(com.alipay.sdk.cons.a.f3200f), a(com.alipay.sdk.cons.a.f3201g));
        this.M = String.format("%s,%s,-,-,-", a(com.alipay.sdk.tid.b.a().f3338a), a(com.alipay.sdk.sys.b.a().c()));
        this.N = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,-", a(com.alipay.sdk.util.a.d(context)), "android", a(Build.VERSION.RELEASE), a(Build.MODEL), "-", a(com.alipay.sdk.util.a.a(context).a()), a(com.alipay.sdk.util.a.b(context).f3363p), "gw", a(com.alipay.sdk.util.a.a(context).b()));
        this.O = "-";
        this.P = "-";
        this.R = "-";
    }

    private boolean a() {
        return TextUtils.isEmpty(this.Q);
    }

    @SuppressLint({"SimpleDateFormat"})
    private static String b() {
        return String.format("123456789,%s", new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").format(new Date()));
    }

    private static String c(String str) {
        String str2;
        String[] strArrSplit = str.split("&");
        String strReplace = null;
        if (strArrSplit != null) {
            String strReplace2 = null;
            for (String str3 : strArrSplit) {
                String[] strArrSplit2 = str3.split("=");
                if (strArrSplit2 != null && strArrSplit2.length == 2) {
                    if (strArrSplit2[0].equalsIgnoreCase(F)) {
                        strArrSplit2[1].replace("\"", "");
                    } else if (strArrSplit2[0].equalsIgnoreCase(G)) {
                        strReplace = strArrSplit2[1].replace("\"", "");
                    } else if (strArrSplit2[0].equalsIgnoreCase(H)) {
                        strReplace2 = strArrSplit2[1].replace("\"", "");
                    }
                }
            }
            str2 = strReplace;
            strReplace = strReplace2;
        } else {
            str2 = null;
        }
        String strA = a(strReplace);
        String strA2 = a(str2);
        return String.format("%s,%s,-,%s,-,-,-", strA, strA2, a(strA2));
    }

    private static String d() {
        return String.format("%s,%s,-,-,-", a(com.alipay.sdk.tid.b.a().f3338a), a(com.alipay.sdk.sys.b.a().c()));
    }

    public final void a(String str, String str2, Throwable th) {
        a(str, str2, a(th));
    }

    private void a(String str, String str2, Throwable th, String str3) {
        a(str, str2, a(th), str3);
    }

    private static String b(Context context) {
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,-", a(com.alipay.sdk.util.a.d(context)), "android", a(Build.VERSION.RELEASE), a(Build.MODEL), "-", a(com.alipay.sdk.util.a.a(context).a()), a(com.alipay.sdk.util.a.b(context).f3363p), "gw", a(com.alipay.sdk.util.a.a(context).b()));
    }

    public final void a(String str, String str2, String str3, String str4) {
        String str5 = "";
        if (!TextUtils.isEmpty(this.Q)) {
            str5 = "^";
        }
        this.Q += (str5 + String.format("%s,%s,%s,%s", str, str2, a(str3), str4));
    }

    public final void a(String str, String str2, String str3) {
        a(str, str2, str3, "-");
    }

    public static String a(String str) {
        return TextUtils.isEmpty(str) ? "" : str.replace(StrPool.BRACKET_START, "【").replace(StrPool.BRACKET_END, "】").replace("(", "（").replace(")", "）").replace(",", "，").replace("-", "=").replace("^", Constants.WAVE_SEPARATOR);
    }

    private String b(String str) {
        String str2;
        if (TextUtils.isEmpty(this.Q)) {
            return "";
        }
        String[] strArrSplit = str.split("&");
        String strReplace = null;
        if (strArrSplit != null) {
            String strReplace2 = null;
            for (String str3 : strArrSplit) {
                String[] strArrSplit2 = str3.split("=");
                if (strArrSplit2 != null && strArrSplit2.length == 2) {
                    if (strArrSplit2[0].equalsIgnoreCase(F)) {
                        strArrSplit2[1].replace("\"", "");
                    } else if (strArrSplit2[0].equalsIgnoreCase(G)) {
                        strReplace = strArrSplit2[1].replace("\"", "");
                    } else if (strArrSplit2[0].equalsIgnoreCase(H)) {
                        strReplace2 = strArrSplit2[1].replace("\"", "");
                    }
                }
            }
            str2 = strReplace;
            strReplace = strReplace2;
        } else {
            str2 = null;
        }
        String strA = a(strReplace);
        String strA2 = a(str2);
        String str4 = String.format("%s,%s,-,%s,-,-,-", strA, strA2, a(strA2));
        this.J = str4;
        return String.format("[(%s),(%s),(%s),(%s),(%s),(%s),(%s),(%s),(%s),(%s)]", this.I, str4, this.K, this.L, this.M, this.N, this.O, this.P, this.Q, this.R);
    }

    public static String a(Throwable th) {
        if (th == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        try {
            stringBuffer.append(th.getClass().getName());
            stringBuffer.append(":");
            stringBuffer.append(th.getMessage());
            stringBuffer.append(" 》 ");
            StackTraceElement[] stackTrace = th.getStackTrace();
            if (stackTrace != null) {
                for (StackTraceElement stackTraceElement : stackTrace) {
                    stringBuffer.append(stackTraceElement.toString() + " 》 ");
                }
            }
        } catch (Throwable unused) {
        }
        return stringBuffer.toString();
    }

    private static String c() {
        return String.format("android,3,%s,%s,com.alipay.mcpay,5.0,-,-,-", a(com.alipay.sdk.cons.a.f3200f), a(com.alipay.sdk.cons.a.f3201g));
    }

    private static String a(Context context) {
        String packageName;
        String str;
        String str2 = "-";
        if (context != null) {
            try {
                Context applicationContext = context.getApplicationContext();
                packageName = applicationContext.getPackageName();
                try {
                    str2 = applicationContext.getPackageManager().getPackageInfo(packageName, 0).versionName;
                } catch (Throwable unused) {
                }
            } catch (Throwable unused2) {
                packageName = "-";
            }
            str = str2;
            str2 = packageName;
        } else {
            str = "-";
        }
        return String.format("%s,%s,-,-,-", str2, str);
    }
}
