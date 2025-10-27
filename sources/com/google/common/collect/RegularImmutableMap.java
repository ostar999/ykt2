package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import java.util.AbstractMap;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(emulated = true, serializable = true)
/* loaded from: classes4.dex */
final class RegularImmutableMap<K, V> extends ImmutableMap<K, V> {
    private static final int ABSENT = -1;
    static final ImmutableMap<Object, Object> EMPTY = new RegularImmutableMap(null, new Object[0], 0);
    private static final long serialVersionUID = 0;

    @VisibleForTesting
    final transient Object[] alternatingKeysAndValues;
    private final transient int[] hashTable;
    private final transient int size;

    public static class EntrySet<K, V> extends ImmutableSet<Map.Entry<K, V>> {
        private final transient Object[] alternatingKeysAndValues;
        private final transient int keyOffset;
        private final transient ImmutableMap<K, V> map;
        private final transient int size;

        public EntrySet(ImmutableMap<K, V> immutableMap, Object[] objArr, int i2, int i3) {
            this.map = immutableMap;
            this.alternatingKeysAndValues = objArr;
            this.keyOffset = i2;
            this.size = i3;
        }

        @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            Object key = entry.getKey();
            Object value = entry.getValue();
            return value != null && value.equals(this.map.get(key));
        }

        @Override // com.google.common.collect.ImmutableCollection
        public int copyIntoArray(Object[] objArr, int i2) {
            return asList().copyIntoArray(objArr, i2);
        }

        @Override // com.google.common.collect.ImmutableSet
        public ImmutableList<Map.Entry<K, V>> createAsList() {
            return new ImmutableList<Map.Entry<K, V>>() { // from class: com.google.common.collect.RegularImmutableMap.EntrySet.1
                @Override // com.google.common.collect.ImmutableCollection
                public boolean isPartialView() {
                    return true;
                }

                @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
                public int size() {
                    return EntrySet.this.size;
                }

                @Override // java.util.List
                public Map.Entry<K, V> get(int i2) {
                    Preconditions.checkElementIndex(i2, EntrySet.this.size);
                    int i3 = i2 * 2;
                    return new AbstractMap.SimpleImmutableEntry(EntrySet.this.alternatingKeysAndValues[EntrySet.this.keyOffset + i3], EntrySet.this.alternatingKeysAndValues[i3 + (EntrySet.this.keyOffset ^ 1)]);
                }
            };
        }

        @Override // com.google.common.collect.ImmutableCollection
        public boolean isPartialView() {
            return true;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return this.size;
        }

        @Override // com.google.common.collect.ImmutableSet, com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set, java.util.NavigableSet, com.google.common.collect.SortedIterable
        public UnmodifiableIterator<Map.Entry<K, V>> iterator() {
            return asList().iterator();
        }
    }

    public static final class KeySet<K> extends ImmutableSet<K> {
        private final transient ImmutableList<K> list;
        private final transient ImmutableMap<K, ?> map;

        public KeySet(ImmutableMap<K, ?> immutableMap, ImmutableList<K> immutableList) {
            this.map = immutableMap;
            this.list = immutableList;
        }

        @Override // com.google.common.collect.ImmutableSet, com.google.common.collect.ImmutableCollection
        public ImmutableList<K> asList() {
            return this.list;
        }

        @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(@NullableDecl Object obj) {
            return this.map.get(obj) != null;
        }

        @Override // com.google.common.collect.ImmutableCollection
        public int copyIntoArray(Object[] objArr, int i2) {
            return asList().copyIntoArray(objArr, i2);
        }

        @Override // com.google.common.collect.ImmutableCollection
        public boolean isPartialView() {
            return true;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return this.map.size();
        }

        @Override // com.google.common.collect.ImmutableSet, com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set, java.util.NavigableSet, com.google.common.collect.SortedIterable
        public UnmodifiableIterator<K> iterator() {
            return asList().iterator();
        }
    }

    public static final class KeysOrValuesAsList extends ImmutableList<Object> {
        private final transient Object[] alternatingKeysAndValues;
        private final transient int offset;
        private final transient int size;

        public KeysOrValuesAsList(Object[] objArr, int i2, int i3) {
            this.alternatingKeysAndValues = objArr;
            this.offset = i2;
            this.size = i3;
        }

        @Override // java.util.List
        public Object get(int i2) {
            Preconditions.checkElementIndex(i2, this.size);
            return this.alternatingKeysAndValues[(i2 * 2) + this.offset];
        }

        @Override // com.google.common.collect.ImmutableCollection
        public boolean isPartialView() {
            return true;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.size;
        }
    }

    private RegularImmutableMap(int[] iArr, Object[] objArr, int i2) {
        this.hashTable = iArr;
        this.alternatingKeysAndValues = objArr;
        this.size = i2;
    }

    public static <K, V> RegularImmutableMap<K, V> create(int i2, Object[] objArr) {
        if (i2 == 0) {
            return (RegularImmutableMap) EMPTY;
        }
        if (i2 == 1) {
            CollectPreconditions.checkEntryNotNull(objArr[0], objArr[1]);
            return new RegularImmutableMap<>(null, objArr, 1);
        }
        Preconditions.checkPositionIndex(i2, objArr.length >> 1);
        return new RegularImmutableMap<>(createHashTable(objArr, i2, ImmutableSet.chooseTableSize(i2), 0), objArr, i2);
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0034, code lost:
    
        r12[r7] = r5;
        r3 = r3 + 1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int[] createHashTable(java.lang.Object[] r10, int r11, int r12, int r13) {
        /*
            r0 = 1
            if (r11 != r0) goto Le
            r11 = r10[r13]
            r12 = r13 ^ 1
            r10 = r10[r12]
            com.google.common.collect.CollectPreconditions.checkEntryNotNull(r11, r10)
            r10 = 0
            return r10
        Le:
            int r1 = r12 + (-1)
            int[] r12 = new int[r12]
            r2 = -1
            java.util.Arrays.fill(r12, r2)
            r3 = 0
        L17:
            if (r3 >= r11) goto L77
            int r4 = r3 * 2
            int r5 = r4 + r13
            r6 = r10[r5]
            r7 = r13 ^ 1
            int r4 = r4 + r7
            r4 = r10[r4]
            com.google.common.collect.CollectPreconditions.checkEntryNotNull(r6, r4)
            int r7 = r6.hashCode()
            int r7 = com.google.common.collect.Hashing.smear(r7)
        L2f:
            r7 = r7 & r1
            r8 = r12[r7]
            if (r8 != r2) goto L39
            r12[r7] = r5
            int r3 = r3 + 1
            goto L17
        L39:
            r9 = r10[r8]
            boolean r9 = r9.equals(r6)
            if (r9 != 0) goto L44
            int r7 = r7 + 1
            goto L2f
        L44:
            java.lang.IllegalArgumentException r11 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            java.lang.String r13 = "Multiple entries with same key: "
            r12.append(r13)
            r12.append(r6)
            java.lang.String r13 = "="
            r12.append(r13)
            r12.append(r4)
            java.lang.String r1 = " and "
            r12.append(r1)
            r1 = r10[r8]
            r12.append(r1)
            r12.append(r13)
            r13 = r8 ^ 1
            r10 = r10[r13]
            r12.append(r10)
            java.lang.String r10 = r12.toString()
            r11.<init>(r10)
            throw r11
        L77:
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.RegularImmutableMap.createHashTable(java.lang.Object[], int, int, int):int[]");
    }

    @Override // com.google.common.collect.ImmutableMap
    public ImmutableSet<Map.Entry<K, V>> createEntrySet() {
        return new EntrySet(this, this.alternatingKeysAndValues, 0, this.size);
    }

    @Override // com.google.common.collect.ImmutableMap
    public ImmutableSet<K> createKeySet() {
        return new KeySet(this, new KeysOrValuesAsList(this.alternatingKeysAndValues, 0, this.size));
    }

    @Override // com.google.common.collect.ImmutableMap
    public ImmutableCollection<V> createValues() {
        return new KeysOrValuesAsList(this.alternatingKeysAndValues, 1, this.size);
    }

    @Override // com.google.common.collect.ImmutableMap, java.util.Map
    @NullableDecl
    public V get(@NullableDecl Object obj) {
        return (V) get(this.hashTable, this.alternatingKeysAndValues, this.size, 0, obj);
    }

    @Override // com.google.common.collect.ImmutableMap
    public boolean isPartialView() {
        return false;
    }

    @Override // java.util.Map
    public int size() {
        return this.size;
    }

    public static Object get(@NullableDecl int[] iArr, @NullableDecl Object[] objArr, int i2, int i3, @NullableDecl Object obj) {
        if (obj == null) {
            return null;
        }
        if (i2 == 1) {
            if (objArr[i3].equals(obj)) {
                return objArr[i3 ^ 1];
            }
            return null;
        }
        if (iArr == null) {
            return null;
        }
        int length = iArr.length - 1;
        int iSmear = Hashing.smear(obj.hashCode());
        while (true) {
            int i4 = iSmear & length;
            int i5 = iArr[i4];
            if (i5 == -1) {
                return null;
            }
            if (objArr[i5].equals(obj)) {
                return objArr[i5 ^ 1];
            }
            iSmear = i4 + 1;
        }
    }
}
