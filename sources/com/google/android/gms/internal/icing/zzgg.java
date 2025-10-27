package com.google.android.gms.internal.icing;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes3.dex */
class zzgg extends AbstractSet<Map.Entry<Object, Object>> {
    private final /* synthetic */ zzfz zznx;

    private zzgg(zzfz zzfzVar) {
        this.zznx = zzfzVar;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public /* synthetic */ boolean add(Object obj) {
        Map.Entry entry = (Map.Entry) obj;
        if (contains(entry)) {
            return false;
        }
        this.zznx.zza((zzfz) entry.getKey(), (Comparable) entry.getValue());
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public void clear() {
        this.zznx.clear();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean contains(Object obj) {
        Map.Entry entry = (Map.Entry) obj;
        Object obj2 = this.zznx.get(entry.getKey());
        Object value = entry.getValue();
        if (obj2 != value) {
            return obj2 != null && obj2.equals(value);
        }
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public Iterator<Map.Entry<Object, Object>> iterator() {
        return new zzgh(this.zznx, null);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean remove(Object obj) {
        Map.Entry entry = (Map.Entry) obj;
        if (!contains(entry)) {
            return false;
        }
        this.zznx.remove(entry.getKey());
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public int size() {
        return this.zznx.size();
    }

    public /* synthetic */ zzgg(zzfz zzfzVar, zzfy zzfyVar) {
        this(zzfzVar);
    }
}
