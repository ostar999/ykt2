package com.google.common.collect;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtIncompatible
/* loaded from: classes4.dex */
class CompactHashMap<K, V> extends AbstractMap<K, V> implements Serializable {
    static final float DEFAULT_LOAD_FACTOR = 1.0f;
    static final int DEFAULT_SIZE = 3;
    private static final long HASH_MASK = -4294967296L;
    private static final int MAXIMUM_CAPACITY = 1073741824;
    private static final long NEXT_MASK = 4294967295L;
    static final int UNSET = -1;

    @VisibleForTesting
    @MonotonicNonNullDecl
    transient long[] entries;

    @MonotonicNonNullDecl
    private transient Set<Map.Entry<K, V>> entrySetView;

    @MonotonicNonNullDecl
    private transient Set<K> keySetView;

    @VisibleForTesting
    @MonotonicNonNullDecl
    transient Object[] keys;
    transient float loadFactor;
    transient int modCount;
    private transient int size;

    @MonotonicNonNullDecl
    private transient int[] table;
    private transient int threshold;

    @VisibleForTesting
    @MonotonicNonNullDecl
    transient Object[] values;

    @MonotonicNonNullDecl
    private transient Collection<V> valuesView;

    public class EntrySetView extends AbstractSet<Map.Entry<K, V>> {
        public EntrySetView() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            CompactHashMap.this.clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(@NullableDecl Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            int iIndexOf = CompactHashMap.this.indexOf(entry.getKey());
            return iIndexOf != -1 && Objects.equal(CompactHashMap.this.values[iIndexOf], entry.getValue());
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Map.Entry<K, V>> iterator() {
            return CompactHashMap.this.entrySetIterator();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(@NullableDecl Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            int iIndexOf = CompactHashMap.this.indexOf(entry.getKey());
            if (iIndexOf == -1 || !Objects.equal(CompactHashMap.this.values[iIndexOf], entry.getValue())) {
                return false;
            }
            CompactHashMap.this.removeEntry(iIndexOf);
            return true;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return CompactHashMap.this.size;
        }
    }

    public abstract class Itr<T> implements Iterator<T> {
        int currentIndex;
        int expectedModCount;
        int indexToRemove;

        private Itr() {
            this.expectedModCount = CompactHashMap.this.modCount;
            this.currentIndex = CompactHashMap.this.firstEntryIndex();
            this.indexToRemove = -1;
        }

        private void checkForConcurrentModification() {
            if (CompactHashMap.this.modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }

        public abstract T getOutput(int i2);

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.currentIndex >= 0;
        }

        @Override // java.util.Iterator
        public T next() {
            checkForConcurrentModification();
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int i2 = this.currentIndex;
            this.indexToRemove = i2;
            T output = getOutput(i2);
            this.currentIndex = CompactHashMap.this.getSuccessor(this.currentIndex);
            return output;
        }

        @Override // java.util.Iterator
        public void remove() {
            checkForConcurrentModification();
            CollectPreconditions.checkRemove(this.indexToRemove >= 0);
            this.expectedModCount++;
            CompactHashMap.this.removeEntry(this.indexToRemove);
            this.currentIndex = CompactHashMap.this.adjustAfterRemove(this.currentIndex, this.indexToRemove);
            this.indexToRemove = -1;
        }
    }

    public class KeySetView extends AbstractSet<K> {
        public KeySetView() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            CompactHashMap.this.clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            return CompactHashMap.this.containsKey(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<K> iterator() {
            return CompactHashMap.this.keySetIterator();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(@NullableDecl Object obj) {
            int iIndexOf = CompactHashMap.this.indexOf(obj);
            if (iIndexOf == -1) {
                return false;
            }
            CompactHashMap.this.removeEntry(iIndexOf);
            return true;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return CompactHashMap.this.size;
        }
    }

    public final class MapEntry extends AbstractMapEntry<K, V> {

        @NullableDecl
        private final K key;
        private int lastKnownIndex;

        public MapEntry(int i2) {
            this.key = (K) CompactHashMap.this.keys[i2];
            this.lastKnownIndex = i2;
        }

        private void updateLastKnownIndex() {
            int i2 = this.lastKnownIndex;
            if (i2 == -1 || i2 >= CompactHashMap.this.size() || !Objects.equal(this.key, CompactHashMap.this.keys[this.lastKnownIndex])) {
                this.lastKnownIndex = CompactHashMap.this.indexOf(this.key);
            }
        }

        @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
        public K getKey() {
            return this.key;
        }

        @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
        public V getValue() {
            updateLastKnownIndex();
            int i2 = this.lastKnownIndex;
            if (i2 == -1) {
                return null;
            }
            return (V) CompactHashMap.this.values[i2];
        }

        @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
        public V setValue(V v2) {
            updateLastKnownIndex();
            int i2 = this.lastKnownIndex;
            if (i2 == -1) {
                CompactHashMap.this.put(this.key, v2);
                return null;
            }
            Object[] objArr = CompactHashMap.this.values;
            V v3 = (V) objArr[i2];
            objArr[i2] = v2;
            return v3;
        }
    }

    public class ValuesView extends AbstractCollection<V> {
        public ValuesView() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public void clear() {
            CompactHashMap.this.clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public Iterator<V> iterator() {
            return CompactHashMap.this.valuesIterator();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            return CompactHashMap.this.size;
        }
    }

    public CompactHashMap() {
        init(3, 1.0f);
    }

    public static <K, V> CompactHashMap<K, V> create() {
        return new CompactHashMap<>();
    }

    public static <K, V> CompactHashMap<K, V> createWithExpectedSize(int i2) {
        return new CompactHashMap<>(i2);
    }

    private static int getHash(long j2) {
        return (int) (j2 >>> 32);
    }

    private static int getNext(long j2) {
        return (int) j2;
    }

    private int hashTableMask() {
        return this.table.length - 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int indexOf(@NullableDecl Object obj) {
        int iSmearedHash = Hashing.smearedHash(obj);
        int next = this.table[hashTableMask() & iSmearedHash];
        while (next != -1) {
            long j2 = this.entries[next];
            if (getHash(j2) == iSmearedHash && Objects.equal(obj, this.keys[next])) {
                return next;
            }
            next = getNext(j2);
        }
        return -1;
    }

    private static long[] newEntries(int i2) {
        long[] jArr = new long[i2];
        Arrays.fill(jArr, -1L);
        return jArr;
    }

    private static int[] newTable(int i2) {
        int[] iArr = new int[i2];
        Arrays.fill(iArr, -1);
        return iArr;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        init(3, 1.0f);
        int i2 = objectInputStream.readInt();
        while (true) {
            i2--;
            if (i2 < 0) {
                return;
            } else {
                put(objectInputStream.readObject(), objectInputStream.readObject());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @CanIgnoreReturnValue
    public V removeEntry(int i2) {
        return remove(this.keys[i2], getHash(this.entries[i2]));
    }

    private void resizeMeMaybe(int i2) {
        int length = this.entries.length;
        if (i2 > length) {
            int iMax = Math.max(1, length >>> 1) + length;
            if (iMax < 0) {
                iMax = Integer.MAX_VALUE;
            }
            if (iMax != length) {
                resizeEntries(iMax);
            }
        }
    }

    private void resizeTable(int i2) {
        if (this.table.length >= 1073741824) {
            this.threshold = Integer.MAX_VALUE;
            return;
        }
        int i3 = ((int) (i2 * this.loadFactor)) + 1;
        int[] iArrNewTable = newTable(i2);
        long[] jArr = this.entries;
        int length = iArrNewTable.length - 1;
        for (int i4 = 0; i4 < this.size; i4++) {
            int hash = getHash(jArr[i4]);
            int i5 = hash & length;
            int i6 = iArrNewTable[i5];
            iArrNewTable[i5] = i4;
            jArr[i4] = (hash << 32) | (i6 & 4294967295L);
        }
        this.threshold = i3;
        this.table = iArrNewTable;
    }

    private static long swapNext(long j2, int i2) {
        return (j2 & HASH_MASK) | (4294967295L & i2);
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(this.size);
        for (int i2 = 0; i2 < this.size; i2++) {
            objectOutputStream.writeObject(this.keys[i2]);
            objectOutputStream.writeObject(this.values[i2]);
        }
    }

    public void accessEntry(int i2) {
    }

    public int adjustAfterRemove(int i2, int i3) {
        return i2 - 1;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void clear() {
        this.modCount++;
        Arrays.fill(this.keys, 0, this.size, (Object) null);
        Arrays.fill(this.values, 0, this.size, (Object) null);
        Arrays.fill(this.table, -1);
        Arrays.fill(this.entries, -1L);
        this.size = 0;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsKey(@NullableDecl Object obj) {
        return indexOf(obj) != -1;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsValue(@NullableDecl Object obj) {
        for (int i2 = 0; i2 < this.size; i2++) {
            if (Objects.equal(obj, this.values[i2])) {
                return true;
            }
        }
        return false;
    }

    public Set<Map.Entry<K, V>> createEntrySet() {
        return new EntrySetView();
    }

    public Set<K> createKeySet() {
        return new KeySetView();
    }

    public Collection<V> createValues() {
        return new ValuesView();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> set = this.entrySetView;
        if (set != null) {
            return set;
        }
        Set<Map.Entry<K, V>> setCreateEntrySet = createEntrySet();
        this.entrySetView = setCreateEntrySet;
        return setCreateEntrySet;
    }

    public Iterator<Map.Entry<K, V>> entrySetIterator() {
        return new CompactHashMap<K, V>.Itr<Map.Entry<K, V>>() { // from class: com.google.common.collect.CompactHashMap.2
            @Override // com.google.common.collect.CompactHashMap.Itr
            public Map.Entry<K, V> getOutput(int i2) {
                return new MapEntry(i2);
            }
        };
    }

    public int firstEntryIndex() {
        return isEmpty() ? -1 : 0;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V get(@NullableDecl Object obj) {
        int iIndexOf = indexOf(obj);
        accessEntry(iIndexOf);
        if (iIndexOf == -1) {
            return null;
        }
        return (V) this.values[iIndexOf];
    }

    public int getSuccessor(int i2) {
        int i3 = i2 + 1;
        if (i3 < this.size) {
            return i3;
        }
        return -1;
    }

    public void init(int i2, float f2) {
        Preconditions.checkArgument(i2 >= 0, "Initial capacity must be non-negative");
        Preconditions.checkArgument(f2 > 0.0f, "Illegal load factor");
        int iClosedTableSize = Hashing.closedTableSize(i2, f2);
        this.table = newTable(iClosedTableSize);
        this.loadFactor = f2;
        this.keys = new Object[i2];
        this.values = new Object[i2];
        this.entries = newEntries(i2);
        this.threshold = Math.max(1, (int) (iClosedTableSize * f2));
    }

    public void insertEntry(int i2, @NullableDecl K k2, @NullableDecl V v2, int i3) {
        this.entries[i2] = (i3 << 32) | 4294967295L;
        this.keys[i2] = k2;
        this.values[i2] = v2;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<K> keySet() {
        Set<K> set = this.keySetView;
        if (set != null) {
            return set;
        }
        Set<K> setCreateKeySet = createKeySet();
        this.keySetView = setCreateKeySet;
        return setCreateKeySet;
    }

    public Iterator<K> keySetIterator() {
        return new CompactHashMap<K, V>.Itr<K>() { // from class: com.google.common.collect.CompactHashMap.1
            @Override // com.google.common.collect.CompactHashMap.Itr
            public K getOutput(int i2) {
                return (K) CompactHashMap.this.keys[i2];
            }
        };
    }

    public void moveLastEntry(int i2) {
        int size = size() - 1;
        if (i2 >= size) {
            this.keys[i2] = null;
            this.values[i2] = null;
            this.entries[i2] = -1;
            return;
        }
        Object[] objArr = this.keys;
        objArr[i2] = objArr[size];
        Object[] objArr2 = this.values;
        objArr2[i2] = objArr2[size];
        objArr[size] = null;
        objArr2[size] = null;
        long[] jArr = this.entries;
        long j2 = jArr[size];
        jArr[i2] = j2;
        jArr[size] = -1;
        int hash = getHash(j2) & hashTableMask();
        int[] iArr = this.table;
        int i3 = iArr[hash];
        if (i3 == size) {
            iArr[hash] = i2;
            return;
        }
        while (true) {
            long j3 = this.entries[i3];
            int next = getNext(j3);
            if (next == size) {
                this.entries[i3] = swapNext(j3, i2);
                return;
            }
            i3 = next;
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    @CanIgnoreReturnValue
    @NullableDecl
    public V put(@NullableDecl K k2, @NullableDecl V v2) {
        long[] jArr = this.entries;
        Object[] objArr = this.keys;
        Object[] objArr2 = this.values;
        int iSmearedHash = Hashing.smearedHash(k2);
        int iHashTableMask = hashTableMask() & iSmearedHash;
        int i2 = this.size;
        int[] iArr = this.table;
        int i3 = iArr[iHashTableMask];
        if (i3 == -1) {
            iArr[iHashTableMask] = i2;
        } else {
            while (true) {
                long j2 = jArr[i3];
                if (getHash(j2) == iSmearedHash && Objects.equal(k2, objArr[i3])) {
                    V v3 = (V) objArr2[i3];
                    objArr2[i3] = v2;
                    accessEntry(i3);
                    return v3;
                }
                int next = getNext(j2);
                if (next == -1) {
                    jArr[i3] = swapNext(j2, i2);
                    break;
                }
                i3 = next;
            }
        }
        if (i2 == Integer.MAX_VALUE) {
            throw new IllegalStateException("Cannot contain more than Integer.MAX_VALUE elements!");
        }
        int i4 = i2 + 1;
        resizeMeMaybe(i4);
        insertEntry(i2, k2, v2, iSmearedHash);
        this.size = i4;
        if (i2 >= this.threshold) {
            resizeTable(this.table.length * 2);
        }
        this.modCount++;
        return null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    @CanIgnoreReturnValue
    @NullableDecl
    public V remove(@NullableDecl Object obj) {
        return remove(obj, Hashing.smearedHash(obj));
    }

    public void resizeEntries(int i2) {
        this.keys = Arrays.copyOf(this.keys, i2);
        this.values = Arrays.copyOf(this.values, i2);
        long[] jArr = this.entries;
        int length = jArr.length;
        long[] jArrCopyOf = Arrays.copyOf(jArr, i2);
        if (i2 > length) {
            Arrays.fill(jArrCopyOf, length, i2, -1L);
        }
        this.entries = jArrCopyOf;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public int size() {
        return this.size;
    }

    public void trimToSize() {
        int i2 = this.size;
        if (i2 < this.entries.length) {
            resizeEntries(i2);
        }
        int iMax = Math.max(1, Integer.highestOneBit((int) (i2 / this.loadFactor)));
        if (iMax < 1073741824 && i2 / iMax > this.loadFactor) {
            iMax <<= 1;
        }
        if (iMax < this.table.length) {
            resizeTable(iMax);
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Collection<V> values() {
        Collection<V> collection = this.valuesView;
        if (collection != null) {
            return collection;
        }
        Collection<V> collectionCreateValues = createValues();
        this.valuesView = collectionCreateValues;
        return collectionCreateValues;
    }

    public Iterator<V> valuesIterator() {
        return new CompactHashMap<K, V>.Itr<V>() { // from class: com.google.common.collect.CompactHashMap.3
            @Override // com.google.common.collect.CompactHashMap.Itr
            public V getOutput(int i2) {
                return (V) CompactHashMap.this.values[i2];
            }
        };
    }

    @NullableDecl
    private V remove(@NullableDecl Object obj, int i2) {
        int iHashTableMask = hashTableMask() & i2;
        int i3 = this.table[iHashTableMask];
        if (i3 == -1) {
            return null;
        }
        int i4 = -1;
        while (true) {
            if (getHash(this.entries[i3]) == i2 && Objects.equal(obj, this.keys[i3])) {
                V v2 = (V) this.values[i3];
                if (i4 == -1) {
                    this.table[iHashTableMask] = getNext(this.entries[i3]);
                } else {
                    long[] jArr = this.entries;
                    jArr[i4] = swapNext(jArr[i4], getNext(jArr[i3]));
                }
                moveLastEntry(i3);
                this.size--;
                this.modCount++;
                return v2;
            }
            int next = getNext(this.entries[i3]);
            if (next == -1) {
                return null;
            }
            i4 = i3;
            i3 = next;
        }
    }

    public CompactHashMap(int i2) {
        this(i2, 1.0f);
    }

    public CompactHashMap(int i2, float f2) {
        init(i2, f2);
    }
}
