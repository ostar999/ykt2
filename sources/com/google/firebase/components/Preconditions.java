package com.google.firebase.components;

/* loaded from: classes4.dex */
public final class Preconditions {
    public static void checkArgument(boolean z2, String str) {
        if (!z2) {
            throw new IllegalArgumentException(str);
        }
    }

    public static <T> T checkNotNull(T t2) {
        t2.getClass();
        return t2;
    }

    public static void checkState(boolean z2, String str) {
        if (!z2) {
            throw new IllegalStateException(str);
        }
    }

    public static <T> T checkNotNull(T t2, String str) {
        if (t2 != null) {
            return t2;
        }
        throw new NullPointerException(str);
    }
}
