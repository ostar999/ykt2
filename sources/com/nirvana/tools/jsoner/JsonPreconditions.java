package com.nirvana.tools.jsoner;

/* loaded from: classes4.dex */
public final class JsonPreconditions {
    private JsonPreconditions() {
        throw new UnsupportedOperationException();
    }

    public static void checkArgument(boolean z2) {
        if (!z2) {
            throw new IllegalArgumentException();
        }
    }

    public static <T> T checkNotNull(T t2) {
        t2.getClass();
        return t2;
    }
}
