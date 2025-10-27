package com.mobile.auth.e;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.text.TextUtils;
import com.google.android.exoplayer2.source.rtsp.RtspMediaSource;
import com.hjq.permissions.Permission;
import com.mobile.auth.l.g;
import com.mobile.auth.l.h;
import com.mobile.auth.l.j;
import com.mobile.auth.l.k;
import com.mobile.auth.l.m;
import com.mobile.auth.l.n;
import com.mobile.auth.l.o;
import com.mobile.auth.l.q;
import com.mobile.auth.l.r;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class e {

    /* renamed from: f, reason: collision with root package name */
    @SuppressLint({"StaticFieldLeak"})
    private static e f9722f;

    /* renamed from: a, reason: collision with root package name */
    protected final c f9723a;

    /* renamed from: b, reason: collision with root package name */
    protected final Context f9724b;

    /* renamed from: d, reason: collision with root package name */
    protected final Handler f9726d;

    /* renamed from: e, reason: collision with root package name */
    protected String f9727e;

    /* renamed from: c, reason: collision with root package name */
    protected long f9725c = RtspMediaSource.DEFAULT_TIMEOUT_MS;

    /* renamed from: g, reason: collision with root package name */
    private final Object f9728g = new Object();

    public class a implements Runnable {

        /* renamed from: b, reason: collision with root package name */
        private final com.cmic.sso.sdk.a f9746b;

        public a(com.cmic.sso.sdk.a aVar) {
            this.f9746b = aVar;
        }

        @Override // java.lang.Runnable
        public void run() {
            JSONObject jSONObjectA = (r.a(e.this.f9724b).a() || !this.f9746b.b("doNetworkSwitch", false)) ? f.a("200023", "登录超时") : f.a("102508", "数据网络切换失败");
            e.this.a(jSONObjectA.optString("resultCode", "200023"), jSONObjectA.optString("resultString", "登录超时"), this.f9746b, jSONObjectA);
        }
    }

    public e(Context context) {
        Context applicationContext = context.getApplicationContext();
        this.f9724b = applicationContext;
        this.f9726d = new Handler(applicationContext.getMainLooper());
        this.f9723a = c.a(applicationContext);
        r.a(applicationContext);
        k.a(applicationContext);
        j.a(applicationContext);
        n.a(new n.a() { // from class: com.mobile.auth.e.e.1
            @Override // com.mobile.auth.l.n.a
            public void a() {
                String strB = k.b("AID", "");
                com.mobile.auth.l.c.b("AuthnHelperCore", "aid = " + strB);
                if (TextUtils.isEmpty(strB)) {
                    e.this.a();
                }
                com.mobile.auth.l.c.b("AuthnHelperCore", com.mobile.auth.l.b.a(e.this.f9724b, true) ? "生成androidkeystore成功" : "生成androidkeystore失败");
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        String str = "%" + q.b();
        com.mobile.auth.l.c.b("AuthnHelperCore", "generate aid = " + str);
        k.a("AID", str);
    }

    private void a(final Context context, final String str, final com.cmic.sso.sdk.a aVar) {
        n.a(new n.a() { // from class: com.mobile.auth.e.e.5
            @Override // com.mobile.auth.l.n.a
            public void a() {
                if ("200023".equals(str)) {
                    SystemClock.sleep(RtspMediaSource.DEFAULT_TIMEOUT_MS);
                }
                new com.cmic.sso.sdk.d.d().a(context, str, aVar);
            }
        });
    }

    public static void a(boolean z2) {
        com.mobile.auth.l.c.a(z2);
    }

    public static e b(Context context) {
        if (f9722f == null) {
            synchronized (e.class) {
                if (f9722f == null) {
                    f9722f = new e(context);
                }
            }
        }
        return f9722f;
    }

    public com.cmic.sso.sdk.a a(b bVar) {
        com.cmic.sso.sdk.a aVar = new com.cmic.sso.sdk.a(64);
        String strC = q.c();
        aVar.a(new com.cmic.sso.sdk.d.b());
        aVar.a("traceId", strC);
        com.mobile.auth.l.c.a("traceId", strC);
        if (bVar != null) {
            com.mobile.auth.l.e.a(strC, bVar);
        }
        return aVar;
    }

    public void a(long j2) {
        this.f9725c = j2;
    }

    public void a(com.cmic.sso.sdk.a aVar) {
        final a aVar2 = new a(aVar);
        this.f9726d.postDelayed(aVar2, this.f9725c);
        this.f9723a.a(aVar, new d() { // from class: com.mobile.auth.e.e.3
            @Override // com.mobile.auth.e.d
            public void a(String str, String str2, com.cmic.sso.sdk.a aVar3, JSONObject jSONObject) {
                e.this.f9726d.removeCallbacks(aVar2);
                e.this.a(str, str2, aVar3, jSONObject);
            }
        });
    }

    public void a(String str, String str2, com.cmic.sso.sdk.a aVar, JSONObject jSONObject) {
        try {
            String strB = aVar.b("traceId");
            final int iB = aVar.b("SDKRequestCode", -1);
            if (com.mobile.auth.l.e.a(strB)) {
                return;
            }
            synchronized (this) {
                final b bVarC = com.mobile.auth.l.e.c(strB);
                if (jSONObject == null || !jSONObject.optBoolean("keepListener", false)) {
                    com.mobile.auth.l.e.b(strB);
                }
                if (bVarC == null) {
                    return;
                }
                aVar.a("systemEndTime", SystemClock.elapsedRealtime());
                aVar.a("endtime", o.a());
                int iC = aVar.c("logintype");
                if (jSONObject == null) {
                    jSONObject = f.a(str, str2);
                }
                final JSONObject jSONObjectA = iC == 3 ? f.a(str, aVar, jSONObject) : f.a(str, str2, aVar, jSONObject);
                jSONObjectA.put("scripExpiresIn", String.valueOf(h.a()));
                this.f9726d.post(new Runnable() { // from class: com.mobile.auth.e.e.4
                    @Override // java.lang.Runnable
                    public void run() {
                        bVarC.a(iB, jSONObjectA);
                    }
                });
                com.mobile.auth.d.c.a(this.f9724b).a(aVar);
                if (!aVar.b().j() && !q.a(aVar.b())) {
                    a(this.f9724b, str, aVar);
                }
                if (com.mobile.auth.l.e.a()) {
                    r.a(this.f9724b).b();
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void a(final String str, final String str2, final b bVar) {
        final com.cmic.sso.sdk.a aVarA = a(bVar);
        n.a(new n.a(this.f9724b, aVarA) { // from class: com.mobile.auth.e.e.2
            @Override // com.mobile.auth.l.n.a
            public void a() {
                if (e.this.a(aVarA, str, str2, "mobileAuth", 0, bVar)) {
                    e.this.a(aVarA);
                }
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0143  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean a(com.cmic.sso.sdk.a r8, java.lang.String r9, java.lang.String r10, java.lang.String r11, int r12, com.mobile.auth.e.b r13) throws java.lang.IllegalAccessException, java.lang.IllegalArgumentException, java.lang.reflect.InvocationTargetException {
        /*
            Method dump skipped, instructions count: 530
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mobile.auth.e.e.a(com.cmic.sso.sdk.a, java.lang.String, java.lang.String, java.lang.String, int, com.mobile.auth.e.b):boolean");
    }

    public JSONObject c(Context context) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            try {
                boolean zA = m.a(this.f9724b);
                com.mobile.auth.f.a.a().a(context, g.a(context, Permission.READ_PHONE_STATE), zA);
                String strA = j.a().a((String) null);
                int iA = m.a(context, zA);
                jSONObject.put("operatortype", strA);
                jSONObject.put("networktype", iA + "");
                com.mobile.auth.l.c.b("AuthnHelperCore", "网络类型: " + iA);
                com.mobile.auth.l.c.b("AuthnHelperCore", "运营商类型: " + strA);
                return jSONObject;
            } catch (JSONException e2) {
                e2.printStackTrace();
                return jSONObject;
            }
        } catch (Exception unused) {
            jSONObject.put("errorDes", "发生未知错误");
            return jSONObject;
        }
    }

    public void d() {
        try {
            h.a(true, true);
            com.mobile.auth.l.c.b("AuthnHelperCore", "删除scrip");
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
