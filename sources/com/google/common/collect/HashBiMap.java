package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.j2objc.annotations.RetainedWith;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
/* loaded from: classes4.dex */
public final class HashBiMap<K, V> extends AbstractMap<K, V> implements BiMap<K, V>, Serializable {
    private static final int ABSENT = -1;
    private static final int ENDPOINT = -2;
    private transient Set<Map.Entry<K, V>> entrySet;

    @NullableDecl
    private transient int firstInInsertionOrder;
    private transient int[] hashTableKToV;
    private transient int[] hashTableVToK;

    @RetainedWith
    @MonotonicNonNullDecl
    private transient BiMap<V, K> inverse;
    private transient Set<K> keySet;
    transient K[] keys;

    @NullableDecl
    private transient int lastInInsertionOrder;
    transient int modCount;
    private transient int[] nextInBucketKToV;
    private transient int[] nextInBucketVToK;
    private transient int[] nextInInsertionOrder;
    private transient int[] prevInInsertionOrder;
    transient int size;
    private transient Set<V> valueSet;
    transient V[] values;

    public final class EntryForKey extends AbstractMapEntry<K, V> {
        int index;

        @NullableDecl
        final K key;

        public EntryForKey(int i2) {
            this.key = HashBiMap.this.keys[i2];
            this.index = i2;
        }

        @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
        public K getKey() {
            return this.key;
        }

        @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
        @NullableDecl
        public V getValue() {
            updateIndex();
            int i2 = this.index;
            if (i2 == -1) {
                return null;
            }
            return HashBiMap.this.values[i2];
        }

        @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
        public V setValue(V v2) {
            updateIndex();
            int i2 = this.index;
            if (i2 == -1) {
                return (V) HashBiMap.this.put(this.key, v2);
            }
            V v3 = HashBiMap.this.values[i2];
            if (Objects.equal(v3, v2)) {
                return v2;
            }
            HashBiMap.this.replaceValueInEntry(this.index, v2, false);
            return v3;
        }

        public void updateIndex() {
            int i2 = this.index;
            if (i2 != -1) {
                HashBiMap hashBiMap = HashBiMap.this;
                if (i2 <= hashBiMap.size && Objects.equal(hashBiMap.keys[i2], this.key)) {
                    return;
                }
            }
            this.index = HashBiMap.this.findEntryByKey(this.key);
        }
    }

    public static final class EntryForValue<K, V> extends AbstractMapEntry<V, K> {
        final HashBiMap<K, V> biMap;
        int index;
        final V value;

        public EntryForValue(HashBiMap<K, V> hashBiMap, int i2) {
            this.biMap = hashBiMap;
            this.value = hashBiMap.values[i2];
            this.index = i2;
        }

        private void updateIndex() {
            int i2 = this.index;
            if (i2 != -1) {
                HashBiMap<K, V> hashBiMap = this.biMap;
                if (i2 <= hashBiMap.size && Objects.equal(this.value, hashBiMap.values[i2])) {
                    return;
                }
            }
            this.index = this.biMap.findEntryByValue(this.value);
        }

        @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
        public V getKey() {
            return this.value;
        }

        @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
        public K getValue() {
            updateIndex();
            int i2 = this.index;
            if (i2 == -1) {
                return null;
            }
            return this.biMap.keys[i2];
        }

        @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
        public K setValue(K k2) {
            updateIndex();
            int i2 = this.index;
            if (i2 == -1) {
                return this.biMap.putInverse(this.value, k2, false);
            }
            K k3 = this.biMap.keys[i2];
            if (Objects.equal(k3, k2)) {
                return k2;
            }
            this.biMap.replaceKeyInEntry(this.index, k2, false);
            return k3;
        }
    }

    public final class EntrySet extends View<K, V, Map.Entry<K, V>> {
        public EntrySet() {
            super(HashBiMap.this);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(@NullableDecl Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            Object key = entry.getKey();
            Object value = entry.getValue();
            int iFindEntryByKey = HashBiMap.this.findEntryByKey(key);
            return iFindEntryByKey != -1 && Objects.equal(value, HashBiMap.this.values[iFindEntryByKey]);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        @CanIgnoreReturnValue
        public boolean remove(@NullableDecl Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            Object key = entry.getKey();
            Object value = entry.getValue();
            int iSmearedHash = Hashing.smearedHash(key);
            int iFindEntryByKey = HashBiMap.this.findEntryByKey(key, iSmearedHash);
            if (iFindEntryByKey == -1 || !Objects.equal(value, HashBiMap.this.values[iFindEntryByKey])) {
                return false;
            }
            HashBiMap.this.removeEntryKeyHashKnown(iFindEntryByKey, iSmearedHash);
            return true;
        }

        @Override // com.google.common.collect.HashBiMap.View
        public Map.Entry<K, V> forEntry(int i2) {
            return new EntryForKey(i2);
        }
    }

    public static class Inverse<K, V> extends AbstractMap<V, K> implements BiMap<V, K>, Serializable {
        private final HashBiMap<K, V> forward;
        private transient Set<Map.Entry<V, K>> inverseEntrySet;

        public Inverse(HashBiMap<K, V> hashBiMap) {
            this.forward = hashBiMap;
        }

        @GwtIncompatible("serialization")
        private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
            objectInputStream.defaultReadObject();
            ((HashBiMap) this.forward).inverse = this;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public void clear() {
            this.forward.clear();
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean containsKey(@NullableDecl Object obj) {
            return this.forward.containsValue(obj);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean containsValue(@NullableDecl Object obj) {
            return this.forward.containsKey(obj);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public Set<Map.Entry<V, K>> entrySet() {
            Set<Map.Entry<V, K>> set = this.inverseEntrySet;
            if (set != null) {
                return set;
            }
            InverseEntrySet inverseEntrySet = new InverseEntrySet(this.forward);
            this.inverseEntrySet = inverseEntrySet;
            return inverseEntrySet;
        }

        @Override // com.google.common.collect.BiMap
        @CanIgnoreReturnValue
        @NullableDecl
        public K forcePut(@NullableDecl V v2, @NullableDecl K k2) {
            return this.forward.putInverse(v2, k2, true);
        }

        @Override // java.util.AbstractMap, java.util.Map
        @NullableDecl
        public K get(@NullableDecl Object obj) {
            return this.forward.getInverse(obj);
        }

        @Override // com.google.common.collect.BiMap
        public BiMap<K, V> inverse() {
            return this.forward;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public Set<V> keySet() {
            return this.forward.values();
        }

        @Override // java.util.AbstractMap, java.util.Map, com.google.common.collect.BiMap
        @CanIgnoreReturnValue
        @NullableDecl
        public K put(@NullableDecl V v2, @NullableDecl K k2) {
            return this.forward.putInverse(v2, k2, false);
        }

        @Override // java.util.AbstractMap, java.util.Map
        @CanIgnoreReturnValue
        @NullableDecl
        public K remove(@NullableDecl Object obj) {
            return this.forward.removeInverse(obj);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public int size() {
            return this.forward.size;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public Set<K> values() {
            return this.forward.keySet();
        }
    }

    public static class InverseEntrySet<K, V> extends View<K, V, Map.Entry<V, K>> {
        public InverseEntrySet(HashBiMap<K, V> hashBiMap) {
            super(hashBiMap);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(@NullableDecl Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            Object key = entry.getKey();
            Object value = entry.getValue();
            int iFindEntryByValue = this.biMap.findEntryByValue(key);
            return iFindEntryByValue != -1 && Objects.equal(this.biMap.keys[iFindEntryByValue], value);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            Object key = entry.getKey();
            Object value = entry.getValue();
            int iSmearedHash = Hashing.smearedHash(key);
            int iFindEntryByValue = this.biMap.findEntryByValue(key, iSmearedHash);
            if (iFindEntryByValue == -1 || !Objects.equal(this.biMap.keys[iFindEntryByValue], value)) {
                return false;
            }
            this.biMap.removeEntryValueHashKnown(iFindEntryByValue, iSmearedHash);
            return true;
        }

        @Override // com.google.common.collect.HashBiMap.View
        public Map.Entry<V, K> forEntry(int i2) {
            return new EntryForValue(this.biMap, i2);
        }
    }

    public final class KeySet extends View<K, V, K> {
        public KeySet() {
            super(HashBiMap.this);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(@NullableDecl Object obj) {
            return HashBiMap.this.containsKey(obj);
        }

        @Override // com.google.common.collect.HashBiMap.View
        public K forEntry(int i2) {
            return HashBiMap.this.keys[i2];
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(@NullableDecl Object obj) {
            int iSmearedHash = Hashing.smearedHash(obj);
            int iFindEntryByKey = HashBiMap.this.findEntryByKey(obj, iSmearedHash);
            if (iFindEntryByKey == -1) {
                return false;
            }
            HashBiMap.this.removeEntryKeyHashKnown(iFindEntryByKey, iSmearedHash);
            return true;
        }
    }

    public final class ValueSet extends View<K, V, V> {
        public ValueSet() {
            super(HashBiMap.this);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(@NullableDecl Object obj) {
            return HashBiMap.this.containsValue(obj);
        }

        @Override // com.google.common.collect.HashBiMap.View
        public V forEntry(int i2) {
            return HashBiMap.this.values[i2];
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(@NullableDecl Object obj) {
            int iSmearedHash = Hashing.smearedHash(obj);
            int iFindEntryByValue = HashBiMap.this.findEntryByValue(obj, iSmearedHash);
            if (iFindEntryByValue == -1) {
                return false;
            }
            HashBiMap.this.removeEntryValueHashKnown(iFindEntryByValue, iSmearedHash);
            return true;
        }
    }

    public static abstract class View<K, V, T> extends AbstractSet<T> {
        final HashBiMap<K, V> biMap;

        public View(HashBiMap<K, V> hashBiMap) {
            this.biMap = hashBiMap;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            this.biMap.clear();
        }

        public abstract T forEntry(int i2);

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<T> iterator() {
            return new Iterator<T>() { // from class: com.google.common.collect.HashBiMap.View.1
                private int expectedModCount;
                private int index;
                private int indexToRemove = -1;
                private int remaining;

                {
                    this.index = ((HashBiMap) View.this.biMap).firstInInsertionOrder;
                    HashBiMap<K, V> hashBiMap = View.this.biMap;
                    this.expectedModCount = hashBiMap.modCount;
                    this.remaining = hashBiMap.size;
                }

                private void checkForComodification() {
                    if (View.this.biMap.modCount != this.expectedModCount) {
                        throw new ConcurrentModificationException();
                    }
                }

                @Override // java.util.Iterator
                public boolean hasNext() {
                    checkForComodification();
                    return this.index != -2 && this.remaining > 0;
                }

                @Override // java.util.Iterator
                public T next() {
                    if (!hasNext()) {
                        throw new NoSuchElementException();
                    }
                    T t2 = (T) View.this.forEntry(this.index);
                    this.indexToRemove = this.index;
                    this.index = ((HashBiMap) View.this.biMap).nextInInsertionOrder[this.index];
                    this.remaining--;
                    return t2;
                }

                @Override // java.util.Iterator
                public void remove() {
                    checkForComodification();
                    CollectPreconditions.checkRemove(this.indexToRemove != -1);
                    View.this.biMap.removeEntry(this.indexToRemove);
                    int i2 = this.index;
                    HashBiMap<K, V> hashBiMap = View.this.biMap;
                    if (i2 == hashBiMap.size) {
                        this.index = this.indexToRemove;
                    }
                    this.indexToRemove = -1;
                    this.expectedModCount = hashBiMap.modCount;
                }
            };
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return this.biMap.size;
        }
    }

    private HashBiMap(int i2) {
        init(i2);
    }

    private int bucket(int i2) {
        return i2 & (this.hashTableKToV.length - 1);
    }

    public static <K, V> HashBiMap<K, V> create() {
        return create(16);
    }

    private static int[] createFilledWithAbsent(int i2) {
        int[] iArr = new int[i2];
        Arrays.fill(iArr, -1);
        return iArr;
    }

    private void deleteFromTableKToV(int i2, int i3) {
        Preconditions.checkArgument(i2 != -1);
        int iBucket = bucket(i3);
        int[] iArr = this.hashTableKToV;
        int i4 = iArr[iBucket];
        if (i4 == i2) {
            int[] iArr2 = this.nextInBucketKToV;
            iArr[iBucket] = iArr2[i2];
            iArr2[i2] = -1;
            return;
        }
        int i5 = this.nextInBucketKToV[i4];
        while (true) {
            int i6 = i4;
            i4 = i5;
            if (i4 == -1) {
                throw new AssertionError("Expected to find entry with key " + this.keys[i2]);
            }
            if (i4 == i2) {
                int[] iArr3 = this.nextInBucketKToV;
                iArr3[i6] = iArr3[i2];
                iArr3[i2] = -1;
                return;
            }
            i5 = this.nextInBucketKToV[i4];
        }
    }

    private void deleteFromTableVToK(int i2, int i3) {
        Preconditions.checkArgument(i2 != -1);
        int iBucket = bucket(i3);
        int[] iArr = this.hashTableVToK;
        int i4 = iArr[iBucket];
        if (i4 == i2) {
            int[] iArr2 = this.nextInBucketVToK;
            iArr[iBucket] = iArr2[i2];
            iArr2[i2] = -1;
            return;
        }
        int i5 = this.nextInBucketVToK[i4];
        while (true) {
            int i6 = i4;
            i4 = i5;
            if (i4 == -1) {
                throw new AssertionError("Expected to find entry with value " + this.values[i2]);
            }
            if (i4 == i2) {
                int[] iArr3 = this.nextInBucketVToK;
                iArr3[i6] = iArr3[i2];
                iArr3[i2] = -1;
                return;
            }
            i5 = this.nextInBucketVToK[i4];
        }
    }

    private void ensureCapacity(int i2) {
        int[] iArr = this.nextInBucketKToV;
        if (iArr.length < i2) {
            int iExpandedCapacity = ImmutableCollection.Builder.expandedCapacity(iArr.length, i2);
            this.keys = (K[]) Arrays.copyOf(this.keys, iExpandedCapacity);
            this.values = (V[]) Arrays.copyOf(this.values, iExpandedCapacity);
            this.nextInBucketKToV = expandAndFillWithAbsent(this.nextInBucketKToV, iExpandedCapacity);
            this.nextInBucketVToK = expandAndFillWithAbsent(this.nextInBucketVToK, iExpandedCapacity);
            this.prevInInsertionOrder = expandAndFillWithAbsent(this.prevInInsertionOrder, iExpandedCapacity);
            this.nextInInsertionOrder = expandAndFillWithAbsent(this.nextInInsertionOrder, iExpandedCapacity);
        }
        if (this.hashTableKToV.length < i2) {
            int iClosedTableSize = Hashing.closedTableSize(i2, 1.0d);
            this.hashTableKToV = createFilledWithAbsent(iClosedTableSize);
            this.hashTableVToK = createFilledWithAbsent(iClosedTableSize);
            for (int i3 = 0; i3 < this.size; i3++) {
                int iBucket = bucket(Hashing.smearedHash(this.keys[i3]));
                int[] iArr2 = this.nextInBucketKToV;
                int[] iArr3 = this.hashTableKToV;
                iArr2[i3] = iArr3[iBucket];
                iArr3[iBucket] = i3;
                int iBucket2 = bucket(Hashing.smearedHash(this.values[i3]));
                int[] iArr4 = this.nextInBucketVToK;
                int[] iArr5 = this.hashTableVToK;
                iArr4[i3] = iArr5[iBucket2];
                iArr5[iBucket2] = i3;
            }
        }
    }

    private static int[] expandAndFillWithAbsent(int[] iArr, int i2) {
        int length = iArr.length;
        int[] iArrCopyOf = Arrays.copyOf(iArr, i2);
        Arrays.fill(iArrCopyOf, length, i2, -1);
        return iArrCopyOf;
    }

    private void insertIntoTableKToV(int i2, int i3) {
        Preconditions.checkArgument(i2 != -1);
        int iBucket = bucket(i3);
        int[] iArr = this.nextInBucketKToV;
        int[] iArr2 = this.hashTableKToV;
        iArr[i2] = iArr2[iBucket];
        iArr2[iBucket] = i2;
    }

    private void insertIntoTableVToK(int i2, int i3) {
        Preconditions.checkArgument(i2 != -1);
        int iBucket = bucket(i3);
        int[] iArr = this.nextInBucketVToK;
        int[] iArr2 = this.hashTableVToK;
        iArr[i2] = iArr2[iBucket];
        iArr2[iBucket] = i2;
    }

    private void moveEntryToIndex(int i2, int i3) {
        int i4;
        int i5;
        if (i2 == i3) {
            return;
        }
        int i6 = this.prevInInsertionOrder[i2];
        int i7 = this.nextInInsertionOrder[i2];
        setSucceeds(i6, i3);
        setSucceeds(i3, i7);
        K[] kArr = this.keys;
        K k2 = kArr[i2];
        V[] vArr = this.values;
        V v2 = vArr[i2];
        kArr[i3] = k2;
        vArr[i3] = v2;
        int iBucket = bucket(Hashing.smearedHash(k2));
        int[] iArr = this.hashTableKToV;
        int i8 = iArr[iBucket];
        if (i8 == i2) {
            iArr[iBucket] = i3;
        } else {
            int i9 = this.nextInBucketKToV[i8];
            while (true) {
                i4 = i8;
                i8 = i9;
                if (i8 == i2) {
                    break;
                } else {
                    i9 = this.nextInBucketKToV[i8];
                }
            }
            this.nextInBucketKToV[i4] = i3;
        }
        int[] iArr2 = this.nextInBucketKToV;
        iArr2[i3] = iArr2[i2];
        iArr2[i2] = -1;
        int iBucket2 = bucket(Hashing.smearedHash(v2));
        int[] iArr3 = this.hashTableVToK;
        int i10 = iArr3[iBucket2];
        if (i10 == i2) {
            iArr3[iBucket2] = i3;
        } else {
            int i11 = this.nextInBucketVToK[i10];
            while (true) {
                i5 = i10;
                i10 = i11;
                if (i10 == i2) {
                    break;
                } else {
                    i11 = this.nextInBucketVToK[i10];
                }
            }
            this.nextInBucketVToK[i5] = i3;
        }
        int[] iArr4 = this.nextInBucketVToK;
        iArr4[i3] = iArr4[i2];
        iArr4[i2] = -1;
    }

    @GwtIncompatible
    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        int count = Serialization.readCount(objectInputStream);
        init(16);
        Serialization.populateMap(this, objectInputStream, count);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void replaceKeyInEntry(int i2, @NullableDecl K k2, boolean z2) {
        int i3;
        Preconditions.checkArgument(i2 != -1);
        int iSmearedHash = Hashing.smearedHash(k2);
        int iFindEntryByKey = findEntryByKey(k2, iSmearedHash);
        int i4 = this.lastInInsertionOrder;
        if (iFindEntryByKey == -1) {
            i3 = -2;
        } else {
            if (!z2) {
                throw new IllegalArgumentException("Key already present in map: " + k2);
            }
            i4 = this.prevInInsertionOrder[iFindEntryByKey];
            i3 = this.nextInInsertionOrder[iFindEntryByKey];
            removeEntryKeyHashKnown(iFindEntryByKey, iSmearedHash);
            if (i2 == this.size) {
                i2 = iFindEntryByKey;
            }
        }
        if (i4 == i2) {
            i4 = this.prevInInsertionOrder[i2];
        } else if (i4 == this.size) {
            i4 = iFindEntryByKey;
        }
        if (i3 == i2) {
            iFindEntryByKey = this.nextInInsertionOrder[i2];
        } else if (i3 != this.size) {
            iFindEntryByKey = i3;
        }
        setSucceeds(this.prevInInsertionOrder[i2], this.nextInInsertionOrder[i2]);
        deleteFromTableKToV(i2, Hashing.smearedHash(this.keys[i2]));
        this.keys[i2] = k2;
        insertIntoTableKToV(i2, Hashing.smearedHash(k2));
        setSucceeds(i4, i2);
        setSucceeds(i2, iFindEntryByKey);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void replaceValueInEntry(int i2, @NullableDecl V v2, boolean z2) {
        Preconditions.checkArgument(i2 != -1);
        int iSmearedHash = Hashing.smearedHash(v2);
        int iFindEntryByValue = findEntryByValue(v2, iSmearedHash);
        if (iFindEntryByValue != -1) {
            if (!z2) {
                throw new IllegalArgumentException("Value already present in map: " + v2);
            }
            removeEntryValueHashKnown(iFindEntryByValue, iSmearedHash);
            if (i2 == this.size) {
                i2 = iFindEntryByValue;
            }
        }
        deleteFromTableVToK(i2, Hashing.smearedHash(this.values[i2]));
        this.values[i2] = v2;
        insertIntoTableVToK(i2, iSmearedHash);
    }

    private void setSucceeds(int i2, int i3) {
        if (i2 == -2) {
            this.firstInInsertionOrder = i3;
        } else {
            this.nextInInsertionOrder[i2] = i3;
        }
        if (i3 == -2) {
            this.lastInInsertionOrder = i2;
        } else {
            this.prevInInsertionOrder[i3] = i2;
        }
    }

    @GwtIncompatible
    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        Serialization.writeMap(this, objectOutputStream);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void clear() {
        Arrays.fill(this.keys, 0, this.size, (Object) null);
        Arrays.fill(this.values, 0, this.size, (Object) null);
        Arrays.fill(this.hashTableKToV, -1);
        Arrays.fill(this.hashTableVToK, -1);
        Arrays.fill(this.nextInBucketKToV, 0, this.size, -1);
        Arrays.fill(this.nextInBucketVToK, 0, this.size, -1);
        Arrays.fill(this.prevInInsertionOrder, 0, this.size, -1);
        Arrays.fill(this.nextInInsertionOrder, 0, this.size, -1);
        this.size = 0;
        this.firstInInsertionOrder = -2;
        this.lastInInsertionOrder = -2;
        this.modCount++;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsKey(@NullableDecl Object obj) {
        return findEntryByKey(obj) != -1;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsValue(@NullableDecl Object obj) {
        return findEntryByValue(obj) != -1;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> set = this.entrySet;
        if (set != null) {
            return set;
        }
        EntrySet entrySet = new EntrySet();
        this.entrySet = entrySet;
        return entrySet;
    }

    public int findEntry(@NullableDecl Object obj, int i2, int[] iArr, int[] iArr2, Object[] objArr) {
        int i3 = iArr[bucket(i2)];
        while (i3 != -1) {
            if (Objects.equal(objArr[i3], obj)) {
                return i3;
            }
            i3 = iArr2[i3];
        }
        return -1;
    }

    public int findEntryByKey(@NullableDecl Object obj) {
        return findEntryByKey(obj, Hashing.smearedHash(obj));
    }

    public int findEntryByValue(@NullableDecl Object obj) {
        return findEntryByValue(obj, Hashing.smearedHash(obj));
    }

    @Override // com.google.common.collect.BiMap
    @CanIgnoreReturnValue
    @NullableDecl
    public V forcePut(@NullableDecl K k2, @NullableDecl V v2) {
        return put(k2, v2, true);
    }

    @Override // java.util.AbstractMap, java.util.Map
    @NullableDecl
    public V get(@NullableDecl Object obj) {
        int iFindEntryByKey = findEntryByKey(obj);
        if (iFindEntryByKey == -1) {
            return null;
        }
        return this.values[iFindEntryByKey];
    }

    @NullableDecl
    public K getInverse(@NullableDecl Object obj) {
        int iFindEntryByValue = findEntryByValue(obj);
        if (iFindEntryByValue == -1) {
            return null;
        }
        return this.keys[iFindEntryByValue];
    }

    public void init(int i2) {
        CollectPreconditions.checkNonnegative(i2, "expectedSize");
        int iClosedTableSize = Hashing.closedTableSize(i2, 1.0d);
        this.size = 0;
        this.keys = (K[]) new Object[i2];
        this.values = (V[]) new Object[i2];
        this.hashTableKToV = createFilledWithAbsent(iClosedTableSize);
        this.hashTableVToK = createFilledWithAbsent(iClosedTableSize);
        this.nextInBucketKToV = createFilledWithAbsent(i2);
        this.nextInBucketVToK = createFilledWithAbsent(i2);
        this.firstInInsertionOrder = -2;
        this.lastInInsertionOrder = -2;
        this.prevInInsertionOrder = createFilledWithAbsent(i2);
        this.nextInInsertionOrder = createFilledWithAbsent(i2);
    }

    @Override // com.google.common.collect.BiMap
    public BiMap<V, K> inverse() {
        BiMap<V, K> biMap = this.inverse;
        if (biMap != null) {
            return biMap;
        }
        Inverse inverse = new Inverse(this);
        this.inverse = inverse;
        return inverse;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<K> keySet() {
        Set<K> set = this.keySet;
        if (set != null) {
            return set;
        }
        KeySet keySet = new KeySet();
        this.keySet = keySet;
        return keySet;
    }

    @Override // java.util.AbstractMap, java.util.Map, com.google.common.collect.BiMap
    @CanIgnoreReturnValue
    public V put(@NullableDecl K k2, @NullableDecl V v2) {
        return put(k2, v2, false);
    }

    @NullableDecl
    public K putInverse(@NullableDecl V v2, @NullableDecl K k2, boolean z2) {
        int iSmearedHash = Hashing.smearedHash(v2);
        int iFindEntryByValue = findEntryByValue(v2, iSmearedHash);
        if (iFindEntryByValue != -1) {
            K k3 = this.keys[iFindEntryByValue];
            if (Objects.equal(k3, k2)) {
                return k2;
            }
            replaceKeyInEntry(iFindEntryByValue, k2, z2);
            return k3;
        }
        int i2 = this.lastInInsertionOrder;
        int iSmearedHash2 = Hashing.smearedHash(k2);
        int iFindEntryByKey = findEntryByKey(k2, iSmearedHash2);
        if (!z2) {
            Preconditions.checkArgument(iFindEntryByKey == -1, "Key already present: %s", k2);
        } else if (iFindEntryByKey != -1) {
            i2 = this.prevInInsertionOrder[iFindEntryByKey];
            removeEntryKeyHashKnown(iFindEntryByKey, iSmearedHash2);
        }
        ensureCapacity(this.size + 1);
        K[] kArr = this.keys;
        int i3 = this.size;
        kArr[i3] = k2;
        this.values[i3] = v2;
        insertIntoTableKToV(i3, iSmearedHash2);
        insertIntoTableVToK(this.size, iSmearedHash);
        int i4 = i2 == -2 ? this.firstInInsertionOrder : this.nextInInsertionOrder[i2];
        setSucceeds(i2, this.size);
        setSucceeds(this.size, i4);
        this.size++;
        this.modCount++;
        return null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    @CanIgnoreReturnValue
    @NullableDecl
    public V remove(@NullableDecl Object obj) {
        int iSmearedHash = Hashing.smearedHash(obj);
        int iFindEntryByKey = findEntryByKey(obj, iSmearedHash);
        if (iFindEntryByKey == -1) {
            return null;
        }
        V v2 = this.values[iFindEntryByKey];
        removeEntryKeyHashKnown(iFindEntryByKey, iSmearedHash);
        return v2;
    }

    public void removeEntry(int i2) {
        removeEntryKeyHashKnown(i2, Hashing.smearedHash(this.keys[i2]));
    }

    public void removeEntryKeyHashKnown(int i2, int i3) {
        removeEntry(i2, i3, Hashing.smearedHash(this.values[i2]));
    }

    public void removeEntryValueHashKnown(int i2, int i3) {
        removeEntry(i2, Hashing.smearedHash(this.keys[i2]), i3);
    }

    @NullableDecl
    public K removeInverse(@NullableDecl Object obj) {
        int iSmearedHash = Hashing.smearedHash(obj);
        int iFindEntryByValue = findEntryByValue(obj, iSmearedHash);
        if (iFindEntryByValue == -1) {
            return null;
        }
        K k2 = this.keys[iFindEntryByValue];
        removeEntryValueHashKnown(iFindEntryByValue, iSmearedHash);
        return k2;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public int size() {
        return this.size;
    }

    public static <K, V> HashBiMap<K, V> create(int i2) {
        return new HashBiMap<>(i2);
    }

    private void removeEntry(int i2, int i3, int i4) {
        Preconditions.checkArgument(i2 != -1);
        deleteFromTableKToV(i2, i3);
        deleteFromTableVToK(i2, i4);
        setSucceeds(this.prevInInsertionOrder[i2], this.nextInInsertionOrder[i2]);
        moveEntryToIndex(this.size - 1, i2);
        K[] kArr = this.keys;
        int i5 = this.size;
        kArr[i5 - 1] = null;
        this.values[i5 - 1] = null;
        this.size = i5 - 1;
        this.modCount++;
    }

    public int findEntryByKey(@NullableDecl Object obj, int i2) {
        return findEntry(obj, i2, this.hashTableKToV, this.nextInBucketKToV, this.keys);
    }

    public int findEntryByValue(@NullableDecl Object obj, int i2) {
        return findEntry(obj, i2, this.hashTableVToK, this.nextInBucketVToK, this.values);
    }

    @NullableDecl
    public V put(@NullableDecl K k2, @NullableDecl V v2, boolean z2) {
        int iSmearedHash = Hashing.smearedHash(k2);
        int iFindEntryByKey = findEntryByKey(k2, iSmearedHash);
        if (iFindEntryByKey != -1) {
            V v3 = this.values[iFindEntryByKey];
            if (Objects.equal(v3, v2)) {
                return v2;
            }
            replaceValueInEntry(iFindEntryByKey, v2, z2);
            return v3;
        }
        int iSmearedHash2 = Hashing.smearedHash(v2);
        int iFindEntryByValue = findEntryByValue(v2, iSmearedHash2);
        if (!z2) {
            Preconditions.checkArgument(iFindEntryByValue == -1, "Value already present: %s", v2);
        } else if (iFindEntryByValue != -1) {
            removeEntryValueHashKnown(iFindEntryByValue, iSmearedHash2);
        }
        ensureCapacity(this.size + 1);
        K[] kArr = this.keys;
        int i2 = this.size;
        kArr[i2] = k2;
        this.values[i2] = v2;
        insertIntoTableKToV(i2, iSmearedHash);
        insertIntoTableVToK(this.size, iSmearedHash2);
        setSucceeds(this.lastInInsertionOrder, this.size);
        setSucceeds(this.size, -2);
        this.size++;
        this.modCount++;
        return null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<V> values() {
        Set<V> set = this.valueSet;
        if (set != null) {
            return set;
        }
        ValueSet valueSet = new ValueSet();
        this.valueSet = valueSet;
        return valueSet;
    }

    public static <K, V> HashBiMap<K, V> create(Map<? extends K, ? extends V> map) {
        HashBiMap<K, V> hashBiMapCreate = create(map.size());
        hashBiMapCreate.putAll(map);
        return hashBiMapCreate;
    }
}
