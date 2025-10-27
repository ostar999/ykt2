package com.tencent.open.b;

/* loaded from: classes6.dex */
public class f {
    public static int a(String str) {
        int iA;
        if (com.tencent.open.utils.f.a() == null || (iA = com.tencent.open.utils.g.a(com.tencent.open.utils.f.a(), str).a("Common_BusinessReportFrequency")) == 0) {
            return 100;
        }
        return iA;
    }

    public static int a() {
        int iA = com.tencent.open.utils.g.a(com.tencent.open.utils.f.a(), (String) null).a("Common_HttpRetryCount");
        if (iA == 0) {
            return 2;
        }
        return iA;
    }
}
