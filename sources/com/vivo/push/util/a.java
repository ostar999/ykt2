package com.vivo.push.util;

import android.content.Context;
import android.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes6.dex */
public class a {

    /* renamed from: c, reason: collision with root package name */
    private static volatile a f24432c;

    /* renamed from: a, reason: collision with root package name */
    private byte[] f24433a;

    /* renamed from: b, reason: collision with root package name */
    private byte[] f24434b;

    private a(Context context) {
        w.b().a(ContextDelegate.getContext(context));
        w wVarB = w.b();
        this.f24433a = wVarB.c();
        this.f24434b = wVarB.d();
    }

    public static a a(Context context) {
        if (f24432c == null) {
            synchronized (a.class) {
                if (f24432c == null) {
                    f24432c = new a(context.getApplicationContext());
                }
            }
        }
        return f24432c;
    }

    public final String b(String str) throws Exception {
        return new String(f.a(f.a(a()), f.a(b()), Base64.decode(str, 2)), "utf-8");
    }

    private byte[] b() {
        byte[] bArr = this.f24434b;
        return (bArr == null || bArr.length <= 0) ? w.b().d() : bArr;
    }

    public final String a(String str) throws Exception {
        String strA = f.a(a());
        String strA2 = f.a(b());
        byte[] bytes = str.getBytes("utf-8");
        SecretKeySpec secretKeySpec = new SecretKeySpec(strA2.getBytes("utf-8"), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(1, secretKeySpec, new IvParameterSpec(strA.getBytes("utf-8")));
        return Base64.encodeToString(cipher.doFinal(bytes), 2);
    }

    private byte[] a() {
        byte[] bArr = this.f24433a;
        return (bArr == null || bArr.length <= 0) ? w.b().c() : bArr;
    }
}
