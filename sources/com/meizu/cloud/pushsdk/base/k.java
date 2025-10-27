package com.meizu.cloud.pushsdk.base;

/* loaded from: classes4.dex */
public class k {
    /* JADX WARN: Multi-variable type inference failed */
    public static String a(String str) {
        com.meizu.cloud.pushsdk.base.a.d dVarA = com.meizu.cloud.pushsdk.base.a.a.a("android.os.SystemProperties").a("get", String.class).a(str);
        if (dVarA.f9230a) {
            return (String) dVarA.f9231b;
        }
        return null;
    }
}
