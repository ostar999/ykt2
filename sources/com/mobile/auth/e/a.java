package com.mobile.auth.e;

import android.content.Context;
import android.content.Intent;
import com.cmic.sso.sdk.view.a;
import com.mobile.auth.e.e;
import com.mobile.auth.l.n;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class a extends e {

    /* renamed from: f, reason: collision with root package name */
    private static a f9706f;

    /* renamed from: g, reason: collision with root package name */
    private com.cmic.sso.sdk.view.a f9707g;

    /* renamed from: h, reason: collision with root package name */
    private com.cmic.sso.sdk.view.e f9708h;

    private a(Context context) {
        super(context);
        this.f9708h = null;
    }

    public static a a(Context context) {
        if (f9706f == null) {
            synchronized (a.class) {
                if (f9706f == null) {
                    f9706f = new a(context);
                }
            }
        }
        return f9706f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(Context context, com.cmic.sso.sdk.a aVar) {
        String strB = aVar.b("traceId");
        Intent intent = new Intent();
        intent.putExtra("traceId", strB);
        com.mobile.auth.l.e.a(aVar.b("traceId"), aVar);
        intent.setClassName(context, "com.cmic.sso.sdk.view.LoginAuthActivity");
        intent.setFlags(268435456);
        context.startActivity(intent);
    }

    public com.cmic.sso.sdk.view.a a() {
        if (this.f9707g == null) {
            this.f9707g = new a.C0105a().a();
        }
        return this.f9707g;
    }

    @Override // com.mobile.auth.e.e
    public void a(com.cmic.sso.sdk.a aVar) {
        final e.a aVar2 = new e.a(aVar);
        this.f9726d.postDelayed(aVar2, this.f9725c);
        this.f9723a.a(aVar, new d() { // from class: com.mobile.auth.e.a.2
            @Override // com.mobile.auth.e.d
            public void a(String str, String str2, com.cmic.sso.sdk.a aVar3, JSONObject jSONObject) {
                com.mobile.auth.l.c.b("onBusinessComplete", "onBusinessComplete");
                a.this.f9726d.removeCallbacks(aVar2);
                if (!"103000".equals(str) || com.mobile.auth.l.e.a(aVar3.b("traceId"))) {
                    a.this.a(str, str2, aVar3, jSONObject);
                } else {
                    a.b(a.this.f9724b, aVar3);
                }
            }
        });
    }

    @Override // com.mobile.auth.e.e
    public void a(String str, String str2, b bVar) {
        a(str, str2, bVar, -1);
    }

    public void a(final String str, final String str2, final b bVar, int i2) {
        final com.cmic.sso.sdk.a aVarA = a(bVar);
        aVarA.a("SDKRequestCode", i2);
        n.a(new n.a(this.f9724b, aVarA) { // from class: com.mobile.auth.e.a.1
            @Override // com.mobile.auth.l.n.a
            public void a() {
                if (a.this.a(aVarA, str, str2, "mobileAuth", 0, bVar)) {
                    a.super.a(aVarA);
                }
            }
        });
    }

    public void a(String str, JSONObject jSONObject) {
        com.cmic.sso.sdk.view.e eVar = this.f9708h;
        if (eVar != null) {
            eVar.a(str, jSONObject);
        }
    }

    public void b() {
        try {
            if (com.cmic.sso.sdk.view.f.a().b() != null) {
                com.cmic.sso.sdk.view.f.a().b().a();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            com.mobile.auth.l.c.a("AuthnHelper", "关闭授权页失败");
        }
    }

    public long c() {
        return this.f9725c;
    }
}
