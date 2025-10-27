package com.google.android.gms.internal.icing;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

/* loaded from: classes3.dex */
public final class zzgr extends AbstractList<String> implements zzeo, RandomAccess {
    private final zzeo zzok;

    public zzgr(zzeo zzeoVar) {
        this.zzok = zzeoVar;
    }

    @Override // java.util.AbstractList, java.util.List
    public final /* synthetic */ Object get(int i2) {
        return (String) this.zzok.get(i2);
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.List
    public final Iterator<String> iterator() {
        return new zzgt(this);
    }

    @Override // java.util.AbstractList, java.util.List
    public final ListIterator<String> listIterator(int i2) {
        return new zzgq(this, i2);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.zzok.size();
    }

    @Override // com.google.android.gms.internal.icing.zzeo
    public final Object zzad(int i2) {
        return this.zzok.zzad(i2);
    }

    @Override // com.google.android.gms.internal.icing.zzeo
    public final void zzc(zzct zzctVar) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.android.gms.internal.icing.zzeo
    public final List<?> zzcd() {
        return this.zzok.zzcd();
    }

    @Override // com.google.android.gms.internal.icing.zzeo
    public final zzeo zzce() {
        return this;
    }
}
