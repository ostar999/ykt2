package com.alibaba.sdk.android.httpdns.i;

import android.util.Log;
import java.util.Random;

/* loaded from: classes2.dex */
public class a {

    /* renamed from: r, reason: collision with root package name */
    private String f2809r;

    /* renamed from: com.alibaba.sdk.android.httpdns.i.a$a, reason: collision with other inner class name */
    public static final class C0022a {

        /* renamed from: a, reason: collision with root package name */
        private static final a f2810a = new a();
    }

    private a() {
        try {
            Random random = new Random();
            char[] cArr = new char[12];
            for (int i2 = 0; i2 < 12; i2++) {
                cArr[i2] = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".charAt(random.nextInt(62));
            }
            this.f2809r = new String(cArr);
        } catch (Exception e2) {
            Log.d("SessionTrackMgr", e2.getMessage(), e2);
        }
    }

    public static a a() {
        return C0022a.f2810a;
    }

    public String getSessionId() {
        return this.f2809r;
    }
}
