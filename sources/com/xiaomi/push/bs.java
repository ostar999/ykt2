package com.xiaomi.push;

import java.util.Map;

/* loaded from: classes6.dex */
public final class bs {

    /* renamed from: a, reason: collision with root package name */
    private static String f24651a;

    private static String a() {
        String strM249b = bq.m249b();
        String strC = bq.c();
        String strE = bq.e();
        int iA = bq.a();
        int iB = bq.b();
        if (strM249b == null || strM249b.isEmpty() || strC == null || strC.isEmpty()) {
            return null;
        }
        if (iA < 0 || iB < 0) {
            iA = 999;
            iB = 99;
        }
        return String.format("%s__%s__%d__%d__%s", strM249b, strC, Integer.valueOf(iA), Integer.valueOf(iB), strE);
    }

    public static String a(String str, String str2) {
        String strA = br.a();
        String strA2 = a();
        String strA3 = null;
        if (strA2 == null) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer(strA);
        stringBuffer.append("/base/profile");
        stringBuffer.append("/");
        stringBuffer.append("metoknlpsdk");
        stringBuffer.append("/");
        stringBuffer.append(str);
        stringBuffer.append("/");
        stringBuffer.append(strA2);
        stringBuffer.append("__");
        stringBuffer.append(str2);
        String string = stringBuffer.toString();
        Map mapM250a = m250a();
        try {
            strA3 = bn.a(string, mapM250a);
        } catch (Exception unused) {
        }
        mapM250a.clear();
        return strA3;
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0025, code lost:
    
        if (com.xiaomi.push.bs.f24651a == null) goto L14;
     */
    /* renamed from: a, reason: collision with other method in class */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.util.Map m250a() throws java.security.NoSuchAlgorithmException {
        /*
            java.lang.String r0 = a()
            java.util.HashMap r1 = new java.util.HashMap
            r1.<init>()
            java.lang.String r2 = com.xiaomi.push.bs.f24651a
            if (r2 != 0) goto L28
            java.lang.String r2 = com.xiaomi.push.bq.m248a()
            r3 = 0
            if (r2 == 0) goto L27
            boolean r4 = r2.isEmpty()
            if (r4 == 0) goto L1b
            goto L27
        L1b:
            java.lang.String r2 = com.xiaomi.push.bq.a(r2)
            if (r2 == 0) goto L23
            com.xiaomi.push.bs.f24651a = r2
        L23:
            java.lang.String r2 = com.xiaomi.push.bs.f24651a
            if (r2 != 0) goto L28
        L27:
            return r3
        L28:
            java.lang.String r2 = "CCPVER"
            java.lang.String r3 = com.xiaomi.push.bs.f24651a
            r1.put(r2, r3)
            java.lang.String r2 = "CCPINF"
            r1.put(r2, r0)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.bs.m250a():java.util.Map");
    }
}
