package com.google.android.gms.internal.icing;

import java.util.Iterator;

/* loaded from: classes3.dex */
final class zzgt implements Iterator<String> {
    private final /* synthetic */ zzgr zzoj;
    private Iterator<String> zzpf;

    public zzgt(zzgr zzgrVar) {
        this.zzoj = zzgrVar;
        this.zzpf = zzgrVar.zzok.iterator();
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.zzpf.hasNext();
    }

    @Override // java.util.Iterator
    public final /* synthetic */ String next() {
        return this.zzpf.next();
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException();
    }
}
