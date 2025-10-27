package com.google.android.gms.internal.icing;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
final class zzgb implements Iterator<Map.Entry<Object, Object>> {
    private int pos;
    private final /* synthetic */ zzfz zznx;
    private Iterator<Map.Entry<Object, Object>> zzny;

    private zzgb(zzfz zzfzVar) {
        this.zznx = zzfzVar;
        this.pos = zzfzVar.zzns.size();
    }

    private final Iterator<Map.Entry<Object, Object>> zzdi() {
        if (this.zzny == null) {
            this.zzny = this.zznx.zznv.entrySet().iterator();
        }
        return this.zzny;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        int i2 = this.pos;
        return (i2 > 0 && i2 <= this.zznx.zzns.size()) || zzdi().hasNext();
    }

    @Override // java.util.Iterator
    public final /* synthetic */ Map.Entry<Object, Object> next() {
        if (zzdi().hasNext()) {
            return zzdi().next();
        }
        List list = this.zznx.zzns;
        int i2 = this.pos - 1;
        this.pos = i2;
        return (Map.Entry) list.get(i2);
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException();
    }

    public /* synthetic */ zzgb(zzfz zzfzVar, zzfy zzfyVar) {
        this(zzfzVar);
    }
}
