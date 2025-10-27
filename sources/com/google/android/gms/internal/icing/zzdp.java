package com.google.android.gms.internal.icing;

/* loaded from: classes3.dex */
final class zzdp {
    private static final zzdn<?> zzhg = new zzdq();
    private static final zzdn<?> zzhh = zzba();

    private static zzdn<?> zzba() {
        try {
            return (zzdn) Class.forName("com.google.protobuf.ExtensionSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception unused) {
            return null;
        }
    }

    public static zzdn<?> zzbb() {
        return zzhg;
    }

    public static zzdn<?> zzbc() {
        zzdn<?> zzdnVar = zzhh;
        if (zzdnVar != null) {
            return zzdnVar;
        }
        throw new IllegalStateException("Protobuf runtime is not correctly loaded.");
    }
}
