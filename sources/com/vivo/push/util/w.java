package com.vivo.push.util;

import android.content.Context;

/* loaded from: classes6.dex */
public final class w extends b {

    /* renamed from: b, reason: collision with root package name */
    private static w f24473b;

    public static synchronized w b() {
        if (f24473b == null) {
            f24473b = new w();
        }
        return f24473b;
    }

    public final synchronized void a(Context context) {
        if (this.f24435a == null) {
            this.f24435a = context;
            a(context, "com.vivo.push_preferences");
        }
    }

    public final byte[] c() {
        byte[] bArrC = c(b("com.vivo.push.secure_cache_iv", ""));
        return (bArrC == null || bArrC.length <= 0) ? new byte[]{34, 32, 33, 37, 33, 34, 32, 33, 33, 33, 34, 41, 35, 32, 32, 32} : bArrC;
    }

    public final byte[] d() {
        byte[] bArrC = c(b("com.vivo.push.secure_cache_key", ""));
        return (bArrC == null || bArrC.length <= 0) ? new byte[]{33, 34, 35, 36, 37, 38, 39, 40, 41, 32, 38, 37, 36, 35, 34, 33} : bArrC;
    }

    private static byte[] c(String str) {
        int length;
        byte[] bArr = null;
        try {
            String[] strArrSplit = str.split(",");
            if (strArrSplit.length > 0) {
                bArr = new byte[strArrSplit.length];
                length = strArrSplit.length;
            } else {
                length = 0;
            }
            for (int i2 = 0; i2 < length; i2++) {
                bArr[i2] = Byte.parseByte(strArrSplit[i2].trim());
            }
        } catch (Exception e2) {
            p.a("SharePreferenceManager", "getCodeBytes error:" + e2.getMessage());
        }
        return bArr;
    }
}
