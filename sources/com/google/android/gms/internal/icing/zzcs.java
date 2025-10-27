package com.google.android.gms.internal.icing;

/* loaded from: classes3.dex */
final class zzcs {
    private static final Class<?> zzgg = zzo("libcore.io.Memory");
    private static final boolean zzgh;

    static {
        zzgh = zzo("org.robolectric.Robolectric") != null;
    }

    public static boolean zzal() {
        return (zzgg == null || zzgh) ? false : true;
    }

    public static Class<?> zzam() {
        return zzgg;
    }

    private static <T> Class<T> zzo(String str) {
        try {
            return (Class<T>) Class.forName(str);
        } catch (Throwable unused) {
            return null;
        }
    }
}
