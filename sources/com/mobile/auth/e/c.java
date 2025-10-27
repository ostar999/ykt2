package com.mobile.auth.e;

import android.annotation.SuppressLint;
import android.content.Context;
import com.cmic.sso.sdk.b;
import com.mobile.auth.l.l;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import javax.crypto.NoSuchPaddingException;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class c {

    /* renamed from: c, reason: collision with root package name */
    @SuppressLint({"StaticFieldLeak"})
    private static c f9716c;

    /* renamed from: a, reason: collision with root package name */
    private final com.mobile.auth.j.a f9717a = com.mobile.auth.j.a.a();

    /* renamed from: b, reason: collision with root package name */
    private final Context f9718b;

    private c(Context context) {
        this.f9718b = context.getApplicationContext();
    }

    public static c a(Context context) {
        if (f9716c == null) {
            synchronized (c.class) {
                if (f9716c == null) {
                    f9716c = new c(context);
                }
            }
        }
        return f9716c;
    }

    private void a(com.cmic.sso.sdk.a aVar) throws NoSuchAlgorithmException {
        String packageName = this.f9718b.getPackageName();
        String strA = com.mobile.auth.l.d.a(l.a(this.f9718b, packageName));
        aVar.a("apppackage", packageName);
        aVar.a("appsign", strA);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00a2  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00d0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void a(com.cmic.sso.sdk.a r21, com.mobile.auth.e.d r22, java.lang.String r23, java.lang.String r24, org.json.JSONObject r25) throws org.json.JSONException {
        /*
            Method dump skipped, instructions count: 255
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mobile.auth.e.c.a(com.cmic.sso.sdk.a, com.mobile.auth.e.d, java.lang.String, java.lang.String, org.json.JSONObject):void");
    }

    private void b(com.cmic.sso.sdk.a aVar) throws UnsupportedEncodingException {
        byte[] bytes = new byte[0];
        if (aVar.b("use2048PublicKey", false)) {
            com.mobile.auth.l.c.a("AuthnBusiness", "使用2048公钥对应的对称秘钥生成方式");
            bytes = com.mobile.auth.l.a.a();
        } else {
            com.mobile.auth.l.c.a("AuthnBusiness", "使用1024公钥对应的对称秘钥生成方式");
            try {
                bytes = UUID.randomUUID().toString().substring(0, 16).getBytes("utf-8");
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        byte[] bArrA = com.mobile.auth.l.a.a();
        aVar.a(b.a.f6384a, bytes);
        aVar.a(b.a.f6385b, bArrA);
        aVar.a("authType", "3");
    }

    public void a(com.cmic.sso.sdk.a aVar, d dVar) {
        com.mobile.auth.l.c.b("AuthnBusiness", "LoginCheck method start");
        int iC = aVar.c("logintype");
        if (!aVar.b("isCacheScrip", false)) {
            b(aVar, dVar);
            return;
        }
        String strB = aVar.b("securityphone", "");
        if (iC == 3) {
            dVar.a("103000", k.a.f27523u, aVar, f.a(strB));
        } else {
            b(aVar, dVar);
        }
    }

    public void b(final com.cmic.sso.sdk.a aVar, final d dVar) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        String str;
        com.mobile.auth.l.c.b("AuthnBusiness", "getScripAndToken start");
        a(aVar);
        if (!aVar.b("isCacheScrip", false)) {
            b(aVar);
        }
        if (aVar.c("logintype") != 1) {
            str = aVar.c("logintype") == 0 ? "50" : "200";
            this.f9717a.a(aVar, new com.mobile.auth.j.d() { // from class: com.mobile.auth.e.c.1
                @Override // com.mobile.auth.j.d
                public void a(String str2, String str3, JSONObject jSONObject) throws JSONException {
                    c.this.a(aVar, dVar, str2, str3, jSONObject);
                }
            });
        }
        aVar.a("userCapaid", str);
        this.f9717a.a(aVar, new com.mobile.auth.j.d() { // from class: com.mobile.auth.e.c.1
            @Override // com.mobile.auth.j.d
            public void a(String str2, String str3, JSONObject jSONObject) throws JSONException {
                c.this.a(aVar, dVar, str2, str3, jSONObject);
            }
        });
    }
}
