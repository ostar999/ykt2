package com.unity3d.player;

import android.util.Log;

/* loaded from: classes6.dex */
final class f {

    /* renamed from: a, reason: collision with root package name */
    protected static boolean f24093a = false;

    public static void Log(int i2, String str) {
        if (f24093a) {
            return;
        }
        if (i2 == 6) {
            Log.e("Unity", str);
        }
        if (i2 == 5) {
            Log.w("Unity", str);
        }
    }
}
