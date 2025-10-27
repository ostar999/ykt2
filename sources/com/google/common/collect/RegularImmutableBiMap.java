package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.RegularImmutableMap;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(emulated = true, serializable = true)
/* loaded from: classes4.dex */
final class RegularImmutableBiMap<K, V> extends ImmutableBiMap<K, V> {
    static final RegularImmutableBiMap<Object, Object> EMPTY = new RegularImmutableBiMap<>();

    @VisibleForTesting
    final transient Object[] alternatingKeysAndValues;
    private final transient RegularImmutableBiMap<V, K> inverse;
    private final transient int[] keyHashTable;
    private final transient int keyOffset;
    private final transient int size;

    /* JADX WARN: Multi-variable type inference failed */
    private RegularImmutableBiMap() {
        this.keyHashTable = null;
        this.alternatingKeysAndValues = new Object[0];
        this.keyOffset = 0;
        this.size = 0;
        this.inverse = this;
    }

    @Override // com.google.common.collect.ImmutableMap
    public ImmutableSet<Map.Entry<K, V>> createEntrySet() {
        return new RegularImmutableMap.EntrySet(this, this.alternatingKeysAndValues, this.keyOffset, this.size);
    }

    @Override // com.google.common.collect.ImmutableMap
    public ImmutableSet<K> createKeySet() {
        return new RegularImmutableMap.KeySet(this, new RegularImmutableMap.KeysOrValuesAsList(this.alternatingKeysAndValues, this.keyOffset, this.size));
    }

    @Override // com.google.common.collect.ImmutableMap, java.util.Map
    public V get(@NullableDecl Object obj) {
        return (V) RegularImmutableMap.get(this.keyHashTable, this.alternatingKeysAndValues, this.size, this.keyOffset, obj);
    }

    @Override // com.google.common.collect.ImmutableMap
    public boolean isPartialView() {
        return false;
    }

    @Override // java.util.Map
    public int size() {
        return this.size;
    }

    @Override // com.google.common.collect.ImmutableBiMap, com.google.common.collect.BiMap
    public ImmutableBiMap<V, K> inverse() {
        return this.inverse;
    }

    public RegularImmutableBiMap(Object[] objArr, int i2) {
        this.alternatingKeysAndValues = objArr;
        this.size = i2;
        this.keyOffset = 0;
        int iChooseTableSize = i2 >= 2 ? ImmutableSet.chooseTableSize(i2) : 0;
        this.keyHashTable = RegularImmutableMap.createHashTable(objArr, i2, iChooseTableSize, 0);
        this.inverse = new RegularImmutableBiMap<>(RegularImmutableMap.createHashTable(objArr, i2, iChooseTableSize, 1), objArr, i2, this);
    }

    private RegularImmutableBiMap(int[] iArr, Object[] objArr, int i2, RegularImmutableBiMap<V, K> regularImmutableBiMap) {
        this.keyHashTable = iArr;
        this.alternatingKeysAndValues = objArr;
        this.keyOffset = 1;
        this.size = i2;
        this.inverse = regularImmutableBiMap;
    }
}
