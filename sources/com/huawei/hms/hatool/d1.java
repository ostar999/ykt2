package com.huawei.hms.hatool;

import android.util.Pair;
import com.huawei.secure.android.common.encrypt.hash.SHA;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class d1 extends v {

    public static /* synthetic */ class a {

        /* renamed from: a, reason: collision with root package name */
        public static final /* synthetic */ int[] f7718a;

        static {
            int[] iArr = new int[y0.values().length];
            f7718a = iArr;
            try {
                iArr[y0.SN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f7718a[y0.IMEI.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f7718a[y0.UDID.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public static e1 a(String str, String str2) {
        e1 e1VarA = v.a(str, str2);
        x0 x0VarC = z0.a().c(str, str2);
        e1VarA.g(z0.a().a(c.c(str, str2)));
        e1VarA.f(c.o(str, str2));
        e1VarA.c(z0.a().f(str, str2));
        int i2 = a.f7718a[x0VarC.a().ordinal()];
        if (i2 == 1) {
            e1VarA.d(x0VarC.b());
        } else if (i2 == 2) {
            e1VarA.b(x0VarC.b());
        } else if (i2 == 3) {
            e1VarA.e(x0VarC.b());
        }
        return e1VarA;
    }

    public static f1 a(String str, String str2, String str3, String str4) {
        f1 f1VarA = v.a(str, str2, str3, str4);
        String strA = z0.a().a(c.c(str2, str3));
        long jCurrentTimeMillis = System.currentTimeMillis();
        String strSha256Encrypt = SHA.sha256Encrypt(b.f() + strA + jCurrentTimeMillis);
        f1VarA.f(String.valueOf(jCurrentTimeMillis));
        f1VarA.g(strSha256Encrypt);
        return f1VarA;
    }

    public static g1 a(String str, String str2, String str3) {
        g1 g1VarA = v.a(str, str2, str3);
        Pair<String, String> pairE = z0.a().e(str2, str);
        g1VarA.f((String) pairE.first);
        g1VarA.g((String) pairE.second);
        g1VarA.h(f.b());
        g1VarA.d(z0.a().d(str2, str));
        return g1VarA;
    }

    public static C0549r a(List<q> list, String str, String str2, String str3, String str4) {
        y.c("hmsSdk", "generate UploadData");
        C0549r c0549rB = v.b(str, str2);
        if (c0549rB == null) {
            return null;
        }
        c0549rB.a(a(x.d().a(), str, str2, str3));
        c0549rB.a(a(str, str2));
        c0549rB.a(a(str2, str, str4));
        c0549rB.a(c.g(str, str2));
        c0549rB.a(list);
        return c0549rB;
    }

    public static Map<String, String> b(String str, String str2, String str3) {
        Map<String, String> mapC = v.c(str, str3);
        Map<String, String> mapI = c.i(str, str2);
        if (mapI == null) {
            return mapC;
        }
        mapC.putAll(mapI);
        return mapC;
    }
}
