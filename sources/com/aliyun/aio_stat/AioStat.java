package com.aliyun.aio_stat;

import android.content.Context;

/* loaded from: classes2.dex */
public class AioStat {
    public static boolean a(Context context) {
        a.a(context);
        return nInit();
    }

    private static native boolean nInit();

    public static native void nSetApplicationContext(Context context);
}
