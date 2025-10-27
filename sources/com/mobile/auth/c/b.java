package com.mobile.auth.c;

import android.content.Context;
import android.net.Network;
import android.text.TextUtils;
import com.mobile.auth.c.j;
import com.mobile.auth.c.r;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public final class b {

    /* renamed from: a, reason: collision with root package name */
    private static final String f9603a = "b";

    /* renamed from: com.mobile.auth.c.b$3, reason: invalid class name */
    public class AnonymousClass3 extends r.a {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ Context f9622a;

        /* renamed from: b, reason: collision with root package name */
        final /* synthetic */ String f9623b;

        /* renamed from: c, reason: collision with root package name */
        final /* synthetic */ String f9624c;

        /* renamed from: d, reason: collision with root package name */
        final /* synthetic */ String f9625d;

        /* renamed from: e, reason: collision with root package name */
        final /* synthetic */ String f9626e;

        /* renamed from: f, reason: collision with root package name */
        final /* synthetic */ String f9627f;

        /* renamed from: g, reason: collision with root package name */
        final /* synthetic */ com.mobile.auth.a.b f9628g;

        public AnonymousClass3(Context context, String str, String str2, String str3, String str4, String str5, com.mobile.auth.a.b bVar) {
            this.f9622a = context;
            this.f9623b = str;
            this.f9624c = str2;
            this.f9625d = str3;
            this.f9626e = str4;
            this.f9627f = str5;
            this.f9628g = bVar;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                if (new j().a(this.f9622a, "https://id6.me/auth/preauth.do")) {
                    if (a()) {
                        return;
                    }
                    String strA = b.a(b.this, this.f9622a, this.f9623b, this.f9624c, this.f9625d, null, this.f9626e, this.f9627f);
                    if (a()) {
                    } else {
                        com.mobile.auth.a.a.b(this.f9622a, strA, this.f9627f, this.f9628g);
                    }
                } else if (a()) {
                } else {
                    com.mobile.auth.a.a.b(this.f9622a, o.a(80800, "WIFI切换超时"), this.f9627f, this.f9628g);
                }
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }
    }

    public static /* synthetic */ String a() {
        try {
            return f9603a;
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

    public static String a(Context context, String str, String str2, Network network) {
        try {
            return c(context, i.a(context, str, network), str2, network);
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

    private String a(Context context, String str, String str2, String str3, Network network, String str4, String str5) {
        try {
            new n();
            String strB = b();
            String strA = l.a(context, str, str2, str3, strB);
            String str6 = f9603a;
            com.mobile.auth.a.a.a(str6, "request params : " + strA);
            n nVarA = i.a(context, k.j(context), strA, network, str4, str5, true);
            if (nVarA.f9667d) {
                nVarA = i.a(context, nVarA.f9668e.equals("2") ? "https://card.e.189.cn/auth/preauth.do" : "https://id6.me/auth/preauth.do", strA, network, str4, str5, false);
            }
            com.mobile.auth.a.a.a(str6, "request result : " + nVarA.f9665b);
            String strB2 = b(context, nVarA.f9665b, strB, network);
            nVarA.f9665b = strB2;
            if (TextUtils.isEmpty(strB2)) {
                nVarA.f9665b = "{\"result\":80001,\"msg\":\"请求异常\"}";
                return "{\"result\":80001,\"msg\":\"请求异常\"}";
            }
            com.mobile.auth.b.e.a(str5, nVarA.f9665b, strA);
            return nVarA.f9665b;
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

    private static String a(Context context, List<String> list, String str, Network network) {
        String strA;
        JSONObject jSONObject;
        for (int i2 = 0; i2 < list.size(); i2++) {
            try {
                try {
                    list.get(i2);
                    TextUtils.isEmpty(list.get(i2));
                    strA = a(context, list.get(i2), str, network);
                    try {
                        jSONObject = !TextUtils.isEmpty(strA) ? new JSONObject(strA) : null;
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
                if (jSONObject != null && jSONObject.getInt("result") == 0) {
                    return strA;
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
        return null;
    }

    public static /* synthetic */ String a(b bVar, Context context, String str, String str2, String str3, Network network, String str4, String str5) {
        try {
            return bVar.a(context, str, str2, str3, network, str4, str5);
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

    public static String a(String str, String str2) {
        try {
            return f.b(str, str2);
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

    private void a(final Context context, final String str, final r.a aVar, final int i2, final com.mobile.auth.a.b bVar) {
        try {
            final Future futureB = r.a().b(aVar);
            r.a().a(new Runnable() { // from class: com.mobile.auth.c.b.4
                @Override // java.lang.Runnable
                public void run() {
                    Future future;
                    Future future2;
                    try {
                        try {
                            futureB.get(i2, TimeUnit.MILLISECONDS);
                            future2 = futureB;
                        } catch (Throwable th) {
                            try {
                                aVar.a(true);
                                if (th instanceof TimeoutException) {
                                    com.mobile.auth.b.e.a(str, "{\"result\":80000,\"msg\":\"请求超时\"}", "");
                                    com.mobile.auth.b.e.a(str).h("submitOnTimeoutInterrupted()");
                                    com.mobile.auth.a.a.b(context, "{\"result\":80000,\"msg\":\"请求超时\"}", str, bVar);
                                } else {
                                    com.mobile.auth.b.e.a(str, "{\"result\":80001,\"msg\":\"请求异常\"}", "");
                                    com.mobile.auth.b.e.a(str).h("submitOnTimeoutInterrupted other exception : " + th.getMessage());
                                    com.mobile.auth.a.a.a(b.a(), "submitOnTimeoutInterrupted other exception", th);
                                    com.mobile.auth.a.a.b(context, "{\"result\":80001,\"msg\":\"请求异常\"}", str, bVar);
                                }
                                Future future3 = futureB;
                                if (future3 == null || future3.isDone()) {
                                    return;
                                } else {
                                    future = futureB;
                                }
                            } catch (Throwable th2) {
                                Future future4 = futureB;
                                if (future4 != null && !future4.isDone()) {
                                    futureB.cancel(true);
                                }
                                throw th2;
                            }
                        }
                        if (future2 == null || future2.isDone()) {
                            return;
                        }
                        future = futureB;
                        future.cancel(true);
                    } catch (Throwable th3) {
                        try {
                            ExceptionProcessor.processException(th3);
                        } catch (Throwable th4) {
                            ExceptionProcessor.processException(th4);
                        }
                    }
                }
            });
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    private String b() {
        try {
            String string = UUID.randomUUID().toString();
            if (TextUtils.isEmpty(string)) {
                return "";
            }
            String strReplace = string.replace("-", "");
            return strReplace.length() >= 16 ? strReplace.substring(0, 16) : strReplace;
        } catch (Throwable th) {
            try {
                com.mobile.auth.a.a.a(f9603a, "generateAesKey error", th);
                return "";
            } catch (Throwable th2) {
                try {
                    ExceptionProcessor.processException(th2);
                    return null;
                } catch (Throwable th3) {
                    ExceptionProcessor.processException(th3);
                    return null;
                }
            }
        }
    }

    private String b(Context context, String str, String str2, Network network) {
        try {
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            try {
                JSONObject jSONObject = new JSONObject(str);
                int iOptInt = jSONObject.optInt("result");
                String strOptString = jSONObject.optString("data");
                if ((iOptInt == 0 || iOptInt == 30002) && !TextUtils.isEmpty(strOptString)) {
                    String strA = a(strOptString, str2);
                    if (!TextUtils.isEmpty(strA)) {
                        try {
                            jSONObject.put("data", new JSONObject(strA));
                        } catch (JSONException e2) {
                            e2.printStackTrace();
                            jSONObject.put("data", strA);
                        }
                        if (iOptInt != 30002) {
                            return jSONObject.toString();
                        }
                        JSONObject jSONObject2 = (JSONObject) jSONObject.opt("data");
                        ArrayList arrayList = new ArrayList();
                        JSONArray jSONArrayOptJSONArray = jSONObject2.optJSONArray("urls");
                        if (jSONArrayOptJSONArray != null) {
                            for (int i2 = 0; i2 < jSONArrayOptJSONArray.length(); i2++) {
                                arrayList.add(jSONArrayOptJSONArray.getString(i2));
                            }
                        }
                        if (arrayList.isEmpty()) {
                            return null;
                        }
                        return a(context, arrayList, str2, network);
                    }
                }
                return jSONObject.toString();
            } catch (Throwable th) {
                com.mobile.auth.a.a.a(f9603a, "decryptResult error", th);
                return null;
            }
        } catch (Throwable th2) {
            try {
                ExceptionProcessor.processException(th2);
                return null;
            } catch (Throwable th3) {
                ExceptionProcessor.processException(th3);
                return null;
            }
        }
    }

    private static String c(Context context, String str, String str2, Network network) {
        try {
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            try {
                JSONObject jSONObject = new JSONObject(str);
                int iOptInt = jSONObject.optInt("result");
                String strOptString = jSONObject.optString("data");
                if (iOptInt == 0 && !TextUtils.isEmpty(strOptString)) {
                    String strA = a(strOptString, str2);
                    if (!TextUtils.isEmpty(strA)) {
                        try {
                            jSONObject.put("data", new JSONObject(strA));
                        } catch (JSONException e2) {
                            e2.printStackTrace();
                            jSONObject.put("data", strA);
                        }
                    }
                }
                return jSONObject.toString();
            } catch (Throwable th) {
                com.mobile.auth.a.a.a(f9603a, "decryptResult error", th);
                return null;
            }
        } catch (Throwable th2) {
            try {
                ExceptionProcessor.processException(th2);
                return null;
            } catch (Throwable th3) {
                ExceptionProcessor.processException(th3);
                return null;
            }
        }
    }

    public void a(final Context context, final String str, final String str2, final String str3, final com.mobile.auth.a.b bVar) {
        try {
            int i2 = com.mobile.auth.a.a.f9563b;
            if (i2 <= 0) {
                i2 = 10000;
            }
            final String strA = e.a();
            final String strA2 = e.a(context);
            com.mobile.auth.b.e.a(strA).a(str).b(strA2).d("preauth").c(k.f(context)).i(context.getPackageName());
            a(context, strA, new r.a() { // from class: com.mobile.auth.c.b.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        String strA3 = b.a(b.this, context, str, str2, str3, null, strA2, strA);
                        if (a()) {
                            return;
                        }
                        com.mobile.auth.a.a.b(context, strA3, strA, bVar);
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }
            }, i2, bVar);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void b(final Context context, final String str, final String str2, final String str3, final com.mobile.auth.a.b bVar) {
        try {
            int i2 = com.mobile.auth.a.a.f9563b;
            if (i2 <= 0) {
                i2 = 10000;
            }
            final String strA = e.a();
            final String strA2 = e.a(context);
            com.mobile.auth.b.e.a(strA).a(str).b(strA2).d("preauth").c(k.f(context)).i(context.getPackageName());
            j jVar = new j();
            jVar.a(context, new j.a() { // from class: com.mobile.auth.c.b.2

                /* renamed from: i, reason: collision with root package name */
                private boolean f9620i = false;

                /* renamed from: j, reason: collision with root package name */
                private boolean f9621j = false;

                @Override // com.mobile.auth.c.j.a
                public synchronized void a() {
                    try {
                        this.f9620i = true;
                        if (!this.f9621j) {
                            com.mobile.auth.b.e.a(strA, "{\"result\":80000,\"msg\":\"请求超时\"}", "");
                            com.mobile.auth.a.a.b(context, "{\"result\":80000,\"msg\":\"请求超时\"}", strA, bVar);
                        }
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }

                @Override // com.mobile.auth.c.j.a
                public synchronized void a(int i3, String str4, long j2) {
                    try {
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                    if (!this.f9620i && !this.f9621j) {
                        this.f9621j = true;
                        com.mobile.auth.b.e.a(strA).h("switchToMobile_L  onFail()  expendTime : " + j2).a(i3).f(str4).b(j2);
                        com.mobile.auth.a.a.b(context, o.a(i3, str4), strA, bVar);
                        com.mobile.auth.a.a.a(b.a(), "Switching network failed (L), errorMsg :" + str4 + " , expendTime ：" + j2);
                    }
                }

                @Override // com.mobile.auth.c.j.a
                public void a(Network network, long j2) {
                    try {
                        com.mobile.auth.a.a.a(b.a(), "Switching network successfully (L) , expendTime ：" + j2);
                        if (!this.f9620i && !this.f9621j) {
                            com.mobile.auth.b.e.a(strA).b(j2);
                            String strA3 = b.a(b.this, context, str, str2, str3, network, strA2, strA);
                            synchronized (this) {
                                if (!this.f9620i && !this.f9621j) {
                                    this.f9621j = true;
                                    com.mobile.auth.a.a.b(context, strA3, strA, bVar);
                                }
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
            });
            jVar.a(i2);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }
}
