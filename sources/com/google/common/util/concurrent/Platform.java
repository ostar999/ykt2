package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(emulated = true)
/* loaded from: classes4.dex */
final class Platform {
    private Platform() {
    }

    public static boolean isInstanceOfThrowableClass(@NullableDecl Throwable th, Class<? extends Throwable> cls) {
        return cls.isInstance(th);
    }
}
