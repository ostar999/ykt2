package com.google.android.gms.internal.icing;

import java.util.Iterator;
import java.util.Map;

/* loaded from: classes3.dex */
final class zzga extends zzgg {
    private final /* synthetic */ zzfz zznx;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private zzga(zzfz zzfzVar) {
        super(zzfzVar, null);
        this.zznx = zzfzVar;
    }

    @Override // com.google.android.gms.internal.icing.zzgg, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public final Iterator<Map.Entry<Object, Object>> iterator() {
        return new zzgb(this.zznx, null);
    }

    public /* synthetic */ zzga(zzfz zzfzVar, zzfy zzfyVar) {
        this(zzfzVar);
    }
}
