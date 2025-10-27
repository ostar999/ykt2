package com.google.android.gms.internal.icing;

import java.util.Iterator;
import java.util.Map;

/* loaded from: classes3.dex */
final class zzgh implements Iterator<Map.Entry<Object, Object>> {
    private int pos;
    private final /* synthetic */ zzfz zznx;
    private Iterator<Map.Entry<Object, Object>> zzny;
    private boolean zzoc;

    private zzgh(zzfz zzfzVar) {
        this.zznx = zzfzVar;
        this.pos = -1;
    }

    private final Iterator<Map.Entry<Object, Object>> zzdi() {
        if (this.zzny == null) {
            this.zzny = this.zznx.zznt.entrySet().iterator();
        }
        return this.zzny;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.pos + 1 < this.zznx.zzns.size() || (!this.zznx.zznt.isEmpty() && zzdi().hasNext());
    }

    @Override // java.util.Iterator
    public final /* synthetic */ Map.Entry<Object, Object> next() {
        this.zzoc = true;
        int i2 = this.pos + 1;
        this.pos = i2;
        return i2 < this.zznx.zzns.size() ? (Map.Entry) this.zznx.zzns.get(this.pos) : zzdi().next();
    }

    @Override // java.util.Iterator
    public final void remove() {
        if (!this.zzoc) {
            throw new IllegalStateException("remove() was called before next()");
        }
        this.zzoc = false;
        this.zznx.zzdg();
        if (this.pos >= this.zznx.zzns.size()) {
            zzdi().remove();
            return;
        }
        zzfz zzfzVar = this.zznx;
        int i2 = this.pos;
        this.pos = i2 - 1;
        zzfzVar.zzak(i2);
    }

    public /* synthetic */ zzgh(zzfz zzfzVar, zzfy zzfyVar) {
        this(zzfzVar);
    }
}
