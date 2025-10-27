package com.google.android.gms.internal.icing;

/* loaded from: classes3.dex */
final class zzfo {
    private static final zzfm zzng = zzct();
    private static final zzfm zznh = new zzfp();

    public static zzfm zzcr() {
        return zzng;
    }

    public static zzfm zzcs() {
        return zznh;
    }

    private static zzfm zzct() {
        try {
            return (zzfm) Class.forName("com.google.protobuf.NewInstanceSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception unused) {
            return null;
        }
    }
}
