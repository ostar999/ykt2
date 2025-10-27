package com.xiaomi.push;

import android.os.Looper;

/* loaded from: classes6.dex */
public class ao {
    public static void a() {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            throw new RuntimeException("can't do this on ui thread");
        }
    }

    public static void a(boolean z2) {
        if (Looper.getMainLooper().getThread() == Thread.currentThread() && z2) {
            throw new RuntimeException("can't do this on ui thread when debug_switch:" + z2);
        }
        if (Looper.getMainLooper().getThread() != Thread.currentThread() || z2) {
            return;
        }
        com.xiaomi.channel.commonutils.logger.b.m117a("can't do this on ui thread when debug_switch:" + z2);
    }
}
