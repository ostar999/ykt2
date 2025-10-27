package com.koushikdutta.urlimageviewhelper;

import java.lang.ref.SoftReference;
import java.util.Hashtable;

/* loaded from: classes4.dex */
public class SoftReferenceHashTable<K, V> {
    Hashtable<K, SoftReference<V>> mTable = new Hashtable<>();

    public V get(K k2) {
        SoftReference<V> softReference = this.mTable.get(k2);
        if (softReference == null) {
            return null;
        }
        V v2 = softReference.get();
        if (v2 == null) {
            this.mTable.remove(k2);
        }
        return v2;
    }

    public V put(K k2, V v2) {
        SoftReference<V> softReferencePut = this.mTable.put(k2, new SoftReference<>(v2));
        if (softReferencePut == null) {
            return null;
        }
        return softReferencePut.get();
    }

    public V remove(K k2) {
        SoftReference<V> softReferenceRemove = this.mTable.remove(k2);
        if (softReferenceRemove == null) {
            return null;
        }
        return softReferenceRemove.get();
    }
}
