package com.huawei.hms.hatool;

import android.text.TextUtils;
import com.huawei.secure.android.common.encrypt.keystore.aes.AesGcmKS;
import com.huawei.secure.android.common.encrypt.utils.EncryptUtil;

/* loaded from: classes4.dex */
public class d0 {

    /* renamed from: c, reason: collision with root package name */
    public static d0 f7715c;

    /* renamed from: a, reason: collision with root package name */
    public String f7716a;

    /* renamed from: b, reason: collision with root package name */
    public String f7717b;

    public static d0 f() {
        if (f7715c == null) {
            g();
        }
        return f7715c;
    }

    public static synchronized void g() {
        if (f7715c == null) {
            f7715c = new d0();
        }
    }

    public String a() {
        if (TextUtils.isEmpty(this.f7716a)) {
            this.f7716a = c();
        }
        return this.f7716a;
    }

    public final String a(String str) {
        String strDecrypt = e() ? AesGcmKS.decrypt("analytics_keystore", str) : "";
        if (TextUtils.isEmpty(strDecrypt)) {
            y.c("hmsSdk", "deCrypt work key first");
            strDecrypt = d.a(str, d());
            if (TextUtils.isEmpty(strDecrypt)) {
                strDecrypt = EncryptUtil.generateSecureRandomStr(16);
                c(b(strDecrypt));
                if (e()) {
                    c0.d();
                }
            } else if (e()) {
                c(b(strDecrypt));
                c0.d();
            }
        }
        return strDecrypt;
    }

    public final String b(String str) {
        return e() ? AesGcmKS.encrypt("analytics_keystore", str) : d.b(str, d());
    }

    public void b() {
        String strGenerateSecureRandomStr = EncryptUtil.generateSecureRandomStr(16);
        if (c(b(strGenerateSecureRandomStr))) {
            this.f7716a = strGenerateSecureRandomStr;
        }
    }

    public final String c() {
        String strA = g0.a(b.i(), "Privacy_MY", "PrivacyData", "");
        if (!TextUtils.isEmpty(strA)) {
            return a(strA);
        }
        String strGenerateSecureRandomStr = EncryptUtil.generateSecureRandomStr(16);
        c(b(strGenerateSecureRandomStr));
        return strGenerateSecureRandomStr;
    }

    public final boolean c(String str) {
        y.c("hmsSdk", "refresh sp aes key");
        if (TextUtils.isEmpty(str)) {
            y.c("hmsSdk", "refreshLocalKey(): encrypted key is empty");
            return false;
        }
        g0.b(b.i(), "Privacy_MY", "PrivacyData", str);
        g0.b(b.i(), "Privacy_MY", "flashKeyTime", System.currentTimeMillis());
        return true;
    }

    public final String d() {
        if (TextUtils.isEmpty(this.f7717b)) {
            this.f7717b = new c0().a();
        }
        return this.f7717b;
    }

    public final boolean e() {
        return true;
    }
}
