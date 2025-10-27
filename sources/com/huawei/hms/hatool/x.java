package com.huawei.hms.hatool;

import com.huawei.secure.android.common.encrypt.utils.EncryptUtil;

/* loaded from: classes4.dex */
public class x {

    /* renamed from: b, reason: collision with root package name */
    public static x f7877b = new x();

    /* renamed from: a, reason: collision with root package name */
    public a f7878a = new a();

    public class a {

        /* renamed from: a, reason: collision with root package name */
        public String f7879a;

        /* renamed from: b, reason: collision with root package name */
        public String f7880b;

        /* renamed from: c, reason: collision with root package name */
        public long f7881c = 0;

        public a() {
        }

        public void a(long j2) {
            x.this.f7878a.f7881c = j2;
        }

        public void a(String str) {
            x.this.f7878a.f7880b = str;
        }

        public void b(String str) {
            x.this.f7878a.f7879a = str;
        }
    }

    public static x d() {
        return f7877b;
    }

    public String a() {
        return this.f7878a.f7880b;
    }

    public void a(String str, String str2) {
        long jB = b();
        String strB = r0.b(str, str2);
        if (strB == null || strB.isEmpty()) {
            y.e("WorkKeyHandler", "get rsa pubkey config error");
            return;
        }
        if (jB == 0) {
            jB = System.currentTimeMillis();
        } else if (System.currentTimeMillis() - jB <= com.heytap.mcssdk.constant.a.f7143g) {
            return;
        }
        String strGenerateSecureRandomStr = EncryptUtil.generateSecureRandomStr(16);
        String strA = e.a(strB, strGenerateSecureRandomStr);
        this.f7878a.a(jB);
        this.f7878a.b(strGenerateSecureRandomStr);
        this.f7878a.a(strA);
    }

    public long b() {
        return this.f7878a.f7881c;
    }

    public String c() {
        return this.f7878a.f7879a;
    }
}
