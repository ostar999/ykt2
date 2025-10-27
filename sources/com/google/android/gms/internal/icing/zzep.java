package com.google.android.gms.internal.icing;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;

/* loaded from: classes3.dex */
public final class zzep extends zzcp<String> implements zzeo, RandomAccess {
    private static final zzep zzlx;
    private static final zzeo zzly;
    private final List<Object> zzlz;

    static {
        zzep zzepVar = new zzep();
        zzlx = zzepVar;
        zzepVar.zzai();
        zzly = zzepVar;
    }

    public zzep() {
        this(10);
    }

    private static String zzh(Object obj) {
        return obj instanceof String ? (String) obj : obj instanceof zzct ? ((zzct) obj).zzan() : zzeb.zze((byte[]) obj);
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractList, java.util.List
    public final /* synthetic */ void add(int i2, Object obj) {
        zzaj();
        this.zzlz.add(i2, (String) obj);
        ((AbstractList) this).modCount++;
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean addAll(Collection<? extends String> collection) {
        return addAll(size(), collection);
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final void clear() {
        zzaj();
        this.zzlz.clear();
        ((AbstractList) this).modCount++;
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractList, java.util.Collection, java.util.List
    public final /* bridge */ /* synthetic */ boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override // java.util.AbstractList, java.util.List
    public final /* synthetic */ Object get(int i2) {
        Object obj = this.zzlz.get(i2);
        if (obj instanceof String) {
            return (String) obj;
        }
        if (obj instanceof zzct) {
            zzct zzctVar = (zzct) obj;
            String strZzan = zzctVar.zzan();
            if (zzctVar.zzao()) {
                this.zzlz.set(i2, strZzan);
            }
            return strZzan;
        }
        byte[] bArr = (byte[]) obj;
        String strZze = zzeb.zze(bArr);
        if (zzeb.zzd(bArr)) {
            this.zzlz.set(i2, strZze);
        }
        return strZze;
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractList, java.util.Collection, java.util.List
    public final /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final /* bridge */ /* synthetic */ boolean remove(Object obj) {
        return super.remove(obj);
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final /* bridge */ /* synthetic */ boolean removeAll(Collection collection) {
        return super.removeAll(collection);
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final /* bridge */ /* synthetic */ boolean retainAll(Collection collection) {
        return super.retainAll(collection);
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractList, java.util.List
    public final /* synthetic */ Object set(int i2, Object obj) {
        zzaj();
        return zzh(this.zzlz.set(i2, (String) obj));
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.zzlz.size();
    }

    @Override // com.google.android.gms.internal.icing.zzeo
    public final Object zzad(int i2) {
        return this.zzlz.get(i2);
    }

    @Override // com.google.android.gms.internal.icing.zzcp, com.google.android.gms.internal.icing.zzee
    public final /* bridge */ /* synthetic */ boolean zzah() {
        return super.zzah();
    }

    @Override // com.google.android.gms.internal.icing.zzeo
    public final void zzc(zzct zzctVar) {
        zzaj();
        this.zzlz.add(zzctVar);
        ((AbstractList) this).modCount++;
    }

    @Override // com.google.android.gms.internal.icing.zzeo
    public final List<?> zzcd() {
        return Collections.unmodifiableList(this.zzlz);
    }

    @Override // com.google.android.gms.internal.icing.zzeo
    public final zzeo zzce() {
        return zzah() ? new zzgr(this) : this;
    }

    @Override // com.google.android.gms.internal.icing.zzee
    public final /* synthetic */ zzee zzj(int i2) {
        if (i2 < size()) {
            throw new IllegalArgumentException();
        }
        ArrayList arrayList = new ArrayList(i2);
        arrayList.addAll(this.zzlz);
        return new zzep((ArrayList<Object>) arrayList);
    }

    public zzep(int i2) {
        this((ArrayList<Object>) new ArrayList(i2));
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractList, java.util.List
    public final boolean addAll(int i2, Collection<? extends String> collection) {
        zzaj();
        if (collection instanceof zzeo) {
            collection = ((zzeo) collection).zzcd();
        }
        boolean zAddAll = this.zzlz.addAll(i2, collection);
        ((AbstractList) this).modCount++;
        return zAddAll;
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractList, java.util.List
    public final /* synthetic */ Object remove(int i2) {
        zzaj();
        Object objRemove = this.zzlz.remove(i2);
        ((AbstractList) this).modCount++;
        return zzh(objRemove);
    }

    private zzep(ArrayList<Object> arrayList) {
        this.zzlz = arrayList;
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final /* bridge */ /* synthetic */ boolean add(Object obj) {
        return super.add(obj);
    }
}
