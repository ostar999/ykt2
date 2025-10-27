package com.squareup.wire;

/* loaded from: classes6.dex */
final class Preconditions {
    private Preconditions() {
    }

    public static void checkNotNull(Object obj, String str) {
        if (obj == null) {
            throw new NullPointerException(str);
        }
    }
}
