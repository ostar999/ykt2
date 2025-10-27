package com.google.android.gms.internal.icing;

import java.lang.Comparable;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/* loaded from: classes3.dex */
class zzfz<K extends Comparable<K>, V> extends AbstractMap<K, V> {
    private boolean zzhl;
    private final int zznr;
    private List<zzge> zzns;
    private Map<K, V> zznt;
    private volatile zzgg zznu;
    private Map<K, V> zznv;
    private volatile zzga zznw;

    private zzfz(int i2) {
        this.zznr = i2;
        this.zzns = Collections.emptyList();
        this.zznt = Collections.emptyMap();
        this.zznv = Collections.emptyMap();
    }

    public static <FieldDescriptorType extends zzdu<FieldDescriptorType>> zzfz<FieldDescriptorType, Object> zzai(int i2) {
        return new zzfy(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final V zzak(int i2) {
        zzdg();
        V v2 = (V) this.zzns.remove(i2).getValue();
        if (!this.zznt.isEmpty()) {
            Iterator<Map.Entry<K, V>> it = zzdh().entrySet().iterator();
            this.zzns.add(new zzge(this, it.next()));
            it.remove();
        }
        return v2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzdg() {
        if (this.zzhl) {
            throw new UnsupportedOperationException();
        }
    }

    private final SortedMap<K, V> zzdh() {
        zzdg();
        if (this.zznt.isEmpty() && !(this.zznt instanceof TreeMap)) {
            TreeMap treeMap = new TreeMap();
            this.zznt = treeMap;
            this.zznv = treeMap.descendingMap();
        }
        return (SortedMap) this.zznt;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void clear() {
        zzdg();
        if (!this.zzns.isEmpty()) {
            this.zzns.clear();
        }
        if (this.zznt.isEmpty()) {
            return;
        }
        this.zznt.clear();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsKey(Object obj) {
        Comparable comparable = (Comparable) obj;
        return zza((zzfz<K, V>) comparable) >= 0 || this.zznt.containsKey(comparable);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<Map.Entry<K, V>> entrySet() {
        if (this.zznu == null) {
            this.zznu = new zzgg(this, null);
        }
        return this.zznu;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzfz)) {
            return super.equals(obj);
        }
        zzfz zzfzVar = (zzfz) obj;
        int size = size();
        if (size != zzfzVar.size()) {
            return false;
        }
        int iZzdd = zzdd();
        if (iZzdd != zzfzVar.zzdd()) {
            return entrySet().equals(zzfzVar.entrySet());
        }
        for (int i2 = 0; i2 < iZzdd; i2++) {
            if (!zzaj(i2).equals(zzfzVar.zzaj(i2))) {
                return false;
            }
        }
        if (iZzdd != size) {
            return this.zznt.equals(zzfzVar.zznt);
        }
        return true;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V get(Object obj) {
        Comparable comparable = (Comparable) obj;
        int iZza = zza((zzfz<K, V>) comparable);
        return iZza >= 0 ? (V) this.zzns.get(iZza).getValue() : this.zznt.get(comparable);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public int hashCode() {
        int iZzdd = zzdd();
        int iHashCode = 0;
        for (int i2 = 0; i2 < iZzdd; i2++) {
            iHashCode += this.zzns.get(i2).hashCode();
        }
        return this.zznt.size() > 0 ? iHashCode + this.zznt.hashCode() : iHashCode;
    }

    public final boolean isImmutable() {
        return this.zzhl;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.AbstractMap, java.util.Map
    public /* synthetic */ Object put(Object obj, Object obj2) {
        return zza((zzfz<K, V>) obj, (Comparable) obj2);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V remove(Object obj) {
        zzdg();
        Comparable comparable = (Comparable) obj;
        int iZza = zza((zzfz<K, V>) comparable);
        if (iZza >= 0) {
            return zzak(iZza);
        }
        if (this.zznt.isEmpty()) {
            return null;
        }
        return this.zznt.remove(comparable);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public int size() {
        return this.zzns.size() + this.zznt.size();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final V zza(K k2, V v2) {
        zzdg();
        int iZza = zza((zzfz<K, V>) k2);
        if (iZza >= 0) {
            return (V) this.zzns.get(iZza).setValue(v2);
        }
        zzdg();
        if (this.zzns.isEmpty() && !(this.zzns instanceof ArrayList)) {
            this.zzns = new ArrayList(this.zznr);
        }
        int i2 = -(iZza + 1);
        if (i2 >= this.zznr) {
            return zzdh().put(k2, v2);
        }
        int size = this.zzns.size();
        int i3 = this.zznr;
        if (size == i3) {
            zzge zzgeVarRemove = this.zzns.remove(i3 - 1);
            zzdh().put((Comparable) zzgeVarRemove.getKey(), zzgeVarRemove.getValue());
        }
        this.zzns.add(i2, new zzge(this, k2, v2));
        return null;
    }

    public final Map.Entry<K, V> zzaj(int i2) {
        return this.zzns.get(i2);
    }

    public final int zzdd() {
        return this.zzns.size();
    }

    public final Iterable<Map.Entry<K, V>> zzde() {
        return this.zznt.isEmpty() ? zzgd.zzdj() : this.zznt.entrySet();
    }

    public final Set<Map.Entry<K, V>> zzdf() {
        if (this.zznw == null) {
            this.zznw = new zzga(this, null);
        }
        return this.zznw;
    }

    public void zzai() {
        if (this.zzhl) {
            return;
        }
        this.zznt = this.zznt.isEmpty() ? Collections.emptyMap() : Collections.unmodifiableMap(this.zznt);
        this.zznv = this.zznv.isEmpty() ? Collections.emptyMap() : Collections.unmodifiableMap(this.zznv);
        this.zzhl = true;
    }

    public /* synthetic */ zzfz(int i2, zzfy zzfyVar) {
        this(i2);
    }

    private final int zza(K k2) {
        int size = this.zzns.size() - 1;
        if (size >= 0) {
            int iCompareTo = k2.compareTo((Comparable) this.zzns.get(size).getKey());
            if (iCompareTo > 0) {
                return -(size + 2);
            }
            if (iCompareTo == 0) {
                return size;
            }
        }
        int i2 = 0;
        while (i2 <= size) {
            int i3 = (i2 + size) / 2;
            int iCompareTo2 = k2.compareTo((Comparable) this.zzns.get(i3).getKey());
            if (iCompareTo2 < 0) {
                size = i3 - 1;
            } else {
                if (iCompareTo2 <= 0) {
                    return i3;
                }
                i2 = i3 + 1;
            }
        }
        return -(i2 + 1);
    }
}
