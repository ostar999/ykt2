package com.mobile.auth.a;

import android.content.Context;
import android.text.TextUtils;
import com.mobile.auth.b.e;
import com.mobile.auth.c.k;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public final class a {

    /* renamed from: a, reason: collision with root package name */
    public static c f9562a = null;

    /* renamed from: b, reason: collision with root package name */
    public static int f9563b = 0;

    /* renamed from: c, reason: collision with root package name */
    public static int f9564c = 0;

    /* renamed from: d, reason: collision with root package name */
    public static int f9565d = 0;

    /* renamed from: e, reason: collision with root package name */
    private static final String f9566e = "a";

    public static void a(int i2, int i3, int i4, c cVar) {
        try {
            f9564c = i2;
            f9565d = i3;
            f9563b = i4;
            f9562a = cVar;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static void a(Context context, String str, String str2, b bVar) {
        try {
            a(f9566e, "called requestPreAuthCode()   appId：" + str + ",appSecret:" + str2);
            a(context, str, str2, "qhx", bVar);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    private static void a(Context context, String str, String str2, String str3, b bVar) {
        try {
            if (bVar == null) {
                f9562a = null;
                return;
            }
            if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
                if (!k.b(context)) {
                    bVar.a("{\"result\":80003,\"msg\":\"网络无连接\"}");
                    f9562a = null;
                    return;
                } else if (k.c(context)) {
                    new com.mobile.auth.c.b().a(context, str, str2, str3, bVar);
                    return;
                } else if (k.d(context)) {
                    new com.mobile.auth.c.b().b(context, str, str2, str3, bVar);
                    return;
                } else {
                    bVar.a("{\"result\":80004,\"msg\":\"移动网络未开启\"}");
                    f9562a = null;
                    return;
                }
            }
            bVar.a("{\"result\":80106,\"msg\":\"请求参数异常\"}");
            f9562a = null;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static void a(String str, String str2) {
        try {
            if (f9562a != null) {
                try {
                    f9562a.a("CT_" + str, str2);
                } catch (Throwable unused) {
                }
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static void a(String str, String str2, Throwable th) {
        try {
            if (f9562a != null) {
                try {
                    f9562a.a("CT_" + str, str2, th);
                } catch (Throwable unused) {
                }
            }
        } catch (Throwable th2) {
            try {
                ExceptionProcessor.processException(th2);
            } catch (Throwable th3) {
                ExceptionProcessor.processException(th3);
            }
        }
    }

    public static void b(Context context, String str, String str2, b bVar) {
        if (bVar != null) {
            try {
                try {
                    JSONObject jSONObject = new JSONObject(str);
                    jSONObject.put("reqId", str2);
                    bVar.a(jSONObject.toString());
                    a(f9566e, "callback result : " + jSONObject.toString());
                } catch (Throwable th) {
                    try {
                        ExceptionProcessor.processException(th);
                        return;
                    } catch (Throwable th2) {
                        ExceptionProcessor.processException(th2);
                        return;
                    }
                }
            } catch (Exception unused) {
                bVar.a(str);
                a(f9566e, "Exception callback result : " + str);
            }
            f9562a = null;
            e.a(context, str2);
        }
    }
}
