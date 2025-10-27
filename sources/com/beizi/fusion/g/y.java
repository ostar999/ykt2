package com.beizi.fusion.g;

import android.os.Handler;
import android.os.Looper;

/* loaded from: classes2.dex */
public class y {

    /* renamed from: a, reason: collision with root package name */
    private static final Handler f5274a = new Handler(Looper.getMainLooper());

    public static void a(Runnable runnable, long j2) {
        f5274a.postDelayed(runnable, j2);
    }
}
