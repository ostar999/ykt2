package com.squareup.wire;

/* loaded from: classes6.dex */
public final class Wire {
    private Wire() {
    }

    public static <T> T get(T t2, T t3) {
        return t2 != null ? t2 : t3;
    }
}
