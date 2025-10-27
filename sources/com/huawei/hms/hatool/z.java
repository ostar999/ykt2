package com.huawei.hms.hatool;

import android.util.Log;

/* loaded from: classes4.dex */
public class z {

    /* renamed from: a, reason: collision with root package name */
    public boolean f7891a = false;

    /* renamed from: b, reason: collision with root package name */
    public int f7892b = 4;

    public static String a() {
        return "FormalHASDK_2.2.0.313" + k1.a();
    }

    public void a(int i2) {
        Log.i("FormalHASDK", System.lineSeparator() + "======================================= " + System.lineSeparator() + a() + "" + System.lineSeparator() + "=======================================");
        this.f7892b = i2;
        this.f7891a = true;
    }

    public void a(int i2, String str, String str2) {
        if (i2 == 3) {
            Log.d(str, str2);
            return;
        }
        if (i2 == 5) {
            Log.w(str, str2);
        } else if (i2 != 6) {
            Log.i(str, str2);
        } else {
            Log.e(str, str2);
        }
    }

    public void b(int i2, String str, String str2) {
        a(i2, "FormalHASDK", str + "=> " + str2);
    }

    public boolean b(int i2) {
        return this.f7891a && i2 >= this.f7892b;
    }
}
