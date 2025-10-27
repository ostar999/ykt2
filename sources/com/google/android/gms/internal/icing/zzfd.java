package com.google.android.gms.internal.icing;

import java.util.Iterator;
import java.util.Map;

/* loaded from: classes3.dex */
final class zzfd implements zzfa {
    @Override // com.google.android.gms.internal.icing.zzfa
    public final Object zzb(Object obj, Object obj2) {
        zzfb zzfbVarZzcj = (zzfb) obj;
        zzfb zzfbVar = (zzfb) obj2;
        if (!zzfbVar.isEmpty()) {
            if (!zzfbVarZzcj.isMutable()) {
                zzfbVarZzcj = zzfbVarZzcj.zzcj();
            }
            zzfbVarZzcj.zza(zzfbVar);
        }
        return zzfbVarZzcj;
    }

    @Override // com.google.android.gms.internal.icing.zzfa
    public final Map<?, ?> zzi(Object obj) {
        return (zzfb) obj;
    }

    @Override // com.google.android.gms.internal.icing.zzfa
    public final Object zzj(Object obj) {
        ((zzfb) obj).zzai();
        return obj;
    }

    @Override // com.google.android.gms.internal.icing.zzfa
    public final zzey<?, ?> zzk(Object obj) {
        throw new NoSuchMethodError();
    }

    @Override // com.google.android.gms.internal.icing.zzfa
    public final int zzb(int i2, Object obj, Object obj2) {
        zzfb zzfbVar = (zzfb) obj;
        if (zzfbVar.isEmpty()) {
            return 0;
        }
        Iterator it = zzfbVar.entrySet().iterator();
        if (!it.hasNext()) {
            return 0;
        }
        Map.Entry entry = (Map.Entry) it.next();
        entry.getKey();
        entry.getValue();
        throw new NoSuchMethodError();
    }
}
