package com.google.android.gms.internal.icing;

/* loaded from: classes3.dex */
final class zzfc {
    private static final zzfa zzml = zzcn();
    private static final zzfa zzmm = new zzfd();

    public static zzfa zzcl() {
        return zzml;
    }

    public static zzfa zzcm() {
        return zzmm;
    }

    private static zzfa zzcn() {
        try {
            return (zzfa) Class.forName("com.google.protobuf.MapFieldSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception unused) {
            return null;
        }
    }
}
