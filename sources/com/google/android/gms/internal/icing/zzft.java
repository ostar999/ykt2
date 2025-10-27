package com.google.android.gms.internal.icing;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* loaded from: classes3.dex */
final class zzft {
    private static final zzft zznk = new zzft();
    private final ConcurrentMap<Class<?>, zzfu<?>> zznm = new ConcurrentHashMap();
    private final zzfx zznl = new zzeu();

    private zzft() {
    }

    public static zzft zzcv() {
        return zznk;
    }

    public final <T> zzfu<T> zze(Class<T> cls) {
        zzeb.zza(cls, "messageType");
        zzfu<T> zzfuVar = (zzfu) this.zznm.get(cls);
        if (zzfuVar != null) {
            return zzfuVar;
        }
        zzfu<T> zzfuVarZzd = this.zznl.zzd(cls);
        zzeb.zza(cls, "messageType");
        zzeb.zza(zzfuVarZzd, "schema");
        zzfu<T> zzfuVar2 = (zzfu) this.zznm.putIfAbsent(cls, zzfuVarZzd);
        return zzfuVar2 != null ? zzfuVar2 : zzfuVarZzd;
    }

    public final <T> zzfu<T> zzo(T t2) {
        return zze(t2.getClass());
    }
}
