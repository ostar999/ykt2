package com.alipay.sdk.app.statistic;

import android.content.Context;
import android.text.TextUtils;

/* loaded from: classes2.dex */
public final class a {

    /* renamed from: a, reason: collision with root package name */
    public static final String f3107a = "alipay_cashier_statistic_record";

    /* renamed from: b, reason: collision with root package name */
    private static c f3108b;

    public static void a(Context context) {
        if (f3108b != null) {
            return;
        }
        f3108b = new c(context);
    }

    private static void b(Context context, String str) {
        new Thread(new b(context, str)).start();
    }

    public static synchronized void a(Context context, String str) {
        String strReplace;
        String strReplace2;
        String str2;
        c cVar = f3108b;
        if (cVar == null) {
            return;
        }
        if (TextUtils.isEmpty(cVar.Q)) {
            str2 = "";
        } else {
            String[] strArrSplit = str.split("&");
            if (strArrSplit != null) {
                strReplace = null;
                strReplace2 = null;
                for (String str3 : strArrSplit) {
                    String[] strArrSplit2 = str3.split("=");
                    if (strArrSplit2 != null && strArrSplit2.length == 2) {
                        if (strArrSplit2[0].equalsIgnoreCase(c.F)) {
                            strArrSplit2[1].replace("\"", "");
                        } else if (strArrSplit2[0].equalsIgnoreCase(c.G)) {
                            strReplace = strArrSplit2[1].replace("\"", "");
                        } else if (strArrSplit2[0].equalsIgnoreCase(c.H)) {
                            strReplace2 = strArrSplit2[1].replace("\"", "");
                        }
                    }
                }
            } else {
                strReplace = null;
                strReplace2 = null;
            }
            String strA = c.a(strReplace2);
            String strA2 = c.a(strReplace);
            String str4 = String.format("%s,%s,-,%s,-,-,-", strA, strA2, c.a(strA2));
            cVar.J = str4;
            str2 = String.format("[(%s),(%s),(%s),(%s),(%s),(%s),(%s),(%s),(%s),(%s)]", cVar.I, str4, cVar.K, cVar.L, cVar.M, cVar.N, cVar.O, cVar.P, cVar.Q, cVar.R);
        }
        new Thread(new b(context, str2)).start();
        f3108b = null;
    }

    public static void a(String str, Throwable th) {
        if (f3108b == null) {
            return;
        }
        th.getClass();
        f3108b.a(str, th.getClass().getSimpleName(), th);
    }

    private static void a(String str, String str2, Throwable th, String str3) {
        c cVar = f3108b;
        if (cVar == null) {
            return;
        }
        cVar.a(str, str2, c.a(th), str3);
    }

    public static void a(String str, String str2, Throwable th) {
        c cVar = f3108b;
        if (cVar == null) {
            return;
        }
        cVar.a(str, str2, th);
    }

    public static void a(String str, String str2, String str3) {
        c cVar = f3108b;
        if (cVar == null) {
            return;
        }
        cVar.a(str, str2, str3);
    }
}
