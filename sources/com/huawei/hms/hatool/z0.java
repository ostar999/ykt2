package com.huawei.hms.hatool;

import android.content.Context;
import android.text.TextUtils;
import android.util.Pair;
import com.aliyun.vod.log.struct.AliyunLogKey;
import com.huawei.secure.android.common.encrypt.hash.SHA;
import java.util.UUID;

/* loaded from: classes4.dex */
public class z0 {

    /* renamed from: b, reason: collision with root package name */
    public static z0 f7893b;

    /* renamed from: a, reason: collision with root package name */
    public Context f7894a;

    public static class a extends a1 {

        /* renamed from: a, reason: collision with root package name */
        public String f7895a;

        /* renamed from: b, reason: collision with root package name */
        public String f7896b;

        public a(String str, String str2) {
            this.f7895a = str;
            this.f7896b = str2;
        }

        @Override // com.huawei.hms.hatool.a1
        public String a() {
            return com.huawei.hms.hatool.a.d(this.f7895a, this.f7896b);
        }

        @Override // com.huawei.hms.hatool.a1
        public String a(String str) {
            return SHA.sha256Encrypt(str);
        }

        @Override // com.huawei.hms.hatool.a1
        public String b() {
            return com.huawei.hms.hatool.a.g(this.f7895a, this.f7896b);
        }

        @Override // com.huawei.hms.hatool.a1
        public String c() {
            return com.huawei.hms.hatool.a.j(this.f7895a, this.f7896b);
        }

        @Override // com.huawei.hms.hatool.a1
        public int d() {
            return (com.huawei.hms.hatool.a.k(this.f7895a, this.f7896b) ? 4 : 0) | 0 | (com.huawei.hms.hatool.a.e(this.f7895a, this.f7896b) ? 2 : 0) | (com.huawei.hms.hatool.a.h(this.f7895a, this.f7896b) ? 1 : 0);
        }
    }

    public static z0 a() {
        z0 z0Var;
        synchronized (z0.class) {
            if (f7893b == null) {
                f7893b = new z0();
            }
            z0Var = f7893b;
        }
        return z0Var;
    }

    public String a(String str, String str2) {
        return g.a(this.f7894a, str, str2);
    }

    public String a(boolean z2) {
        if (!z2) {
            return "";
        }
        String strE = b.e();
        if (TextUtils.isEmpty(strE)) {
            strE = g0.a(this.f7894a, "global_v2", AliyunLogKey.KEY_UUID, "");
            if (TextUtils.isEmpty(strE)) {
                strE = UUID.randomUUID().toString().replace("-", "");
                g0.b(this.f7894a, "global_v2", AliyunLogKey.KEY_UUID, strE);
            }
            b.h(strE);
        }
        return strE;
    }

    public void a(Context context) {
        if (this.f7894a == null) {
            this.f7894a = context;
        }
    }

    public String b(String str, String str2) {
        return g.b(this.f7894a, str, str2);
    }

    public x0 c(String str, String str2) {
        return new a(str, str2).a(this.f7894a);
    }

    public String d(String str, String str2) {
        return c1.b(str, str2);
    }

    public Pair<String, String> e(String str, String str2) {
        if (!com.huawei.hms.hatool.a.f(str, str2)) {
            return new Pair<>("", "");
        }
        String strP = i.c().b().p();
        String strQ = i.c().b().q();
        if (!TextUtils.isEmpty(strP) && !TextUtils.isEmpty(strQ)) {
            return new Pair<>(strP, strQ);
        }
        Pair<String, String> pairE = b1.e(this.f7894a);
        i.c().b().k((String) pairE.first);
        i.c().b().l((String) pairE.second);
        return pairE;
    }

    public String f(String str, String str2) {
        return c1.a(str, str2);
    }
}
