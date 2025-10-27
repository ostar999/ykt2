package com.google.android.gms.internal.icing;

import java.util.ListIterator;

/* loaded from: classes3.dex */
final class zzgq implements ListIterator<String> {
    private ListIterator<String> zzoh;
    private final /* synthetic */ int zzoi;
    private final /* synthetic */ zzgr zzoj;

    public zzgq(zzgr zzgrVar, int i2) {
        this.zzoj = zzgrVar;
        this.zzoi = i2;
        this.zzoh = zzgrVar.zzok.listIterator(i2);
    }

    @Override // java.util.ListIterator
    public final /* synthetic */ void add(String str) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public final boolean hasNext() {
        return this.zzoh.hasNext();
    }

    @Override // java.util.ListIterator
    public final boolean hasPrevious() {
        return this.zzoh.hasPrevious();
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public final /* synthetic */ Object next() {
        return this.zzoh.next();
    }

    @Override // java.util.ListIterator
    public final int nextIndex() {
        return this.zzoh.nextIndex();
    }

    @Override // java.util.ListIterator
    public final /* synthetic */ String previous() {
        return this.zzoh.previous();
    }

    @Override // java.util.ListIterator
    public final int previousIndex() {
        return this.zzoh.previousIndex();
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.ListIterator
    public final /* synthetic */ void set(String str) {
        throw new UnsupportedOperationException();
    }
}
