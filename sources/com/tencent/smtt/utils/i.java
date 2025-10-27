package com.tencent.smtt.utils;

import java.security.KeyFactory;
import java.security.Provider;
import java.security.Security;
import java.security.spec.X509EncodedKeySpec;
import java.util.Random;
import javax.crypto.Cipher;

/* loaded from: classes6.dex */
public class i {

    /* renamed from: a, reason: collision with root package name */
    private static final char[] f21552a = "0123456789abcdef".toCharArray();

    /* renamed from: b, reason: collision with root package name */
    private static i f21553b;

    /* renamed from: c, reason: collision with root package name */
    private String f21554c;

    /* renamed from: d, reason: collision with root package name */
    private String f21555d;

    /* renamed from: e, reason: collision with root package name */
    private String f21556e;

    private i() {
        int iNextInt = new Random().nextInt(89999999) + 10000000;
        int iNextInt2 = new Random().nextInt(89999999) + 10000000;
        this.f21556e = String.valueOf(iNextInt);
        this.f21554c = this.f21556e + String.valueOf(iNextInt2);
    }

    public static synchronized i a() {
        if (f21553b == null) {
            f21553b = new i();
        }
        return f21553b;
    }

    private String b(byte[] bArr) {
        char[] cArr = new char[bArr.length * 2];
        for (int i2 = 0; i2 < bArr.length; i2++) {
            int i3 = bArr[i2] & 255;
            int i4 = i2 * 2;
            char[] cArr2 = f21552a;
            cArr[i4] = cArr2[i3 >>> 4];
            cArr[i4 + 1] = cArr2[i3 & 15];
        }
        return new String(cArr);
    }

    public byte[] a(byte[] bArr) throws Exception {
        return com.tencent.smtt.sdk.stat.a.a(this.f21556e.getBytes(), bArr, 1);
    }

    public void b() throws Exception {
        Security.addProvider((Provider) Class.forName("com.android.org.bouncycastle.jce.provider.BouncyCastleProvider", true, ClassLoader.getSystemClassLoader()).newInstance());
    }

    public String c() throws Exception {
        Cipher cipher;
        if (this.f21555d == null) {
            byte[] bytes = this.f21554c.getBytes();
            try {
                try {
                    cipher = Cipher.getInstance("RSA/ECB/NoPadding");
                } catch (Exception unused) {
                    b();
                    cipher = Cipher.getInstance("RSA/ECB/NoPadding");
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                cipher = null;
            }
            cipher.init(1, KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(android.util.Base64.decode("MCwwDQYJKoZIhvcNAQEBBQADGwAwGAIRAMRB/Q0hTCD+XtnQhpQJefUCAwEAAQ==".getBytes(), 0))));
            this.f21555d = b(cipher.doFinal(bytes));
        }
        return this.f21555d;
    }
}
