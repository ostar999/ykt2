package com.ali.security;

import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes2.dex */
public class MinosSecurityLoad_bbc1bffae6ebd3190382c8ec0f7941ad {
    private static AtomicBoolean isLoadLibrary = new AtomicBoolean(false);

    public static void SLoad(String str) {
        if (isLoadLibrary.compareAndSet(false, true)) {
            System.loadLibrary(str);
            isLoadLibrary.set(false);
        }
    }
}
