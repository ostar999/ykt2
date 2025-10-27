package com.beizi.ad.internal.c;

/* loaded from: classes2.dex */
public final class k {
    public static <T> T a(T t2) {
        t2.getClass();
        return t2;
    }

    public static void a(Object... objArr) {
        for (Object obj : objArr) {
            obj.getClass();
        }
    }

    public static <T> T a(T t2, String str) {
        if (t2 != null) {
            return t2;
        }
        throw new NullPointerException(str);
    }

    public static void a(boolean z2) {
        if (!z2) {
            throw new IllegalArgumentException();
        }
    }

    public static void a(boolean z2, String str) {
        if (!z2) {
            throw new IllegalArgumentException(str);
        }
    }
}
