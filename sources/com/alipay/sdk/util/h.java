package com.alipay.sdk.util;

import android.content.Context;
import android.text.TextUtils;

/* loaded from: classes2.dex */
public final class h {

    /* renamed from: a, reason: collision with root package name */
    public static final String f3375a = "pref_trade_token";

    /* renamed from: b, reason: collision with root package name */
    public static final String f3376b = ";";

    /* renamed from: c, reason: collision with root package name */
    public static final String f3377c = "result={";

    /* renamed from: d, reason: collision with root package name */
    public static final String f3378d = "}";

    /* renamed from: e, reason: collision with root package name */
    public static final String f3379e = "trade_token=\"";

    /* renamed from: f, reason: collision with root package name */
    public static final String f3380f = "\"";

    /* renamed from: g, reason: collision with root package name */
    public static final String f3381g = "trade_token=";

    private static String a(String str) {
        String strSubstring = null;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String[] strArrSplit = str.split(f3376b);
        for (int i2 = 0; i2 < strArrSplit.length; i2++) {
            if (strArrSplit[i2].startsWith(f3377c) && strArrSplit[i2].endsWith("}")) {
                String[] strArrSplit2 = strArrSplit[i2].substring(8, r3.length() - 1).split("&");
                int i3 = 0;
                while (true) {
                    if (i3 >= strArrSplit2.length) {
                        break;
                    }
                    if (strArrSplit2[i3].startsWith(f3379e) && strArrSplit2[i3].endsWith("\"")) {
                        strSubstring = strArrSplit2[i3].substring(13, r1.length() - 1);
                        break;
                    }
                    if (strArrSplit2[i3].startsWith(f3381g)) {
                        strSubstring = strArrSplit2[i3].substring(12);
                        break;
                    }
                    i3++;
                }
            }
        }
        return strSubstring;
    }

    private static String a(Context context) {
        return i.b(context, f3375a, "");
    }

    private static void a(Context context, String str) {
        try {
            String strSubstring = null;
            if (!TextUtils.isEmpty(str)) {
                String[] strArrSplit = str.split(f3376b);
                for (int i2 = 0; i2 < strArrSplit.length; i2++) {
                    if (strArrSplit[i2].startsWith(f3377c) && strArrSplit[i2].endsWith("}")) {
                        String[] strArrSplit2 = strArrSplit[i2].substring(8, r3.length() - 1).split("&");
                        int i3 = 0;
                        while (true) {
                            if (i3 >= strArrSplit2.length) {
                                break;
                            }
                            if (strArrSplit2[i3].startsWith(f3379e) && strArrSplit2[i3].endsWith("\"")) {
                                strSubstring = strArrSplit2[i3].substring(13, r1.length() - 1);
                                break;
                            } else {
                                if (strArrSplit2[i3].startsWith(f3381g)) {
                                    strSubstring = strArrSplit2[i3].substring(12);
                                    break;
                                }
                                i3++;
                            }
                        }
                    }
                }
            }
            if (TextUtils.isEmpty(strSubstring)) {
                return;
            }
            i.a(context, f3375a, strSubstring);
        } catch (Throwable th) {
            com.alipay.sdk.app.statistic.a.a(com.alipay.sdk.app.statistic.c.f3112b, com.alipay.sdk.app.statistic.c.f3135y, th);
        }
    }
}
