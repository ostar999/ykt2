package com.google.common.collect;

import com.google.common.annotations.GwtIncompatible;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;

@GwtIncompatible
/* loaded from: classes4.dex */
class CompactLinkedHashSet<E> extends CompactHashSet<E> {
    private static final int ENDPOINT = -2;
    private transient int firstEntry;
    private transient int lastEntry;

    @MonotonicNonNullDecl
    private transient int[] predecessor;

    @MonotonicNonNullDecl
    private transient int[] successor;

    public CompactLinkedHashSet() {
    }

    public static <E> CompactLinkedHashSet<E> create() {
        return new CompactLinkedHashSet<>();
    }

    public static <E> CompactLinkedHashSet<E> createWithExpectedSize(int i2) {
        return new CompactLinkedHashSet<>(i2);
    }

    private void succeeds(int i2, int i3) {
        if (i2 == -2) {
            this.firstEntry = i3;
        } else {
            this.successor[i2] = i3;
        }
        if (i3 == -2) {
            this.lastEntry = i2;
        } else {
            this.predecessor[i3] = i2;
        }
    }

    @Override // com.google.common.collect.CompactHashSet
    public int adjustAfterRemove(int i2, int i3) {
        return i2 == size() ? i3 : i2;
    }

    @Override // com.google.common.collect.CompactHashSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public void clear() {
        super.clear();
        this.firstEntry = -2;
        this.lastEntry = -2;
        Arrays.fill(this.predecessor, -1);
        Arrays.fill(this.successor, -1);
    }

    @Override // com.google.common.collect.CompactHashSet
    public int firstEntryIndex() {
        return this.firstEntry;
    }

    @Override // com.google.common.collect.CompactHashSet
    public int getSuccessor(int i2) {
        return this.successor[i2];
    }

    @Override // com.google.common.collect.CompactHashSet
    public void init(int i2, float f2) {
        super.init(i2, f2);
        int[] iArr = new int[i2];
        this.predecessor = iArr;
        this.successor = new int[i2];
        Arrays.fill(iArr, -1);
        Arrays.fill(this.successor, -1);
        this.firstEntry = -2;
        this.lastEntry = -2;
    }

    @Override // com.google.common.collect.CompactHashSet
    public void insertEntry(int i2, E e2, int i3) {
        super.insertEntry(i2, e2, i3);
        succeeds(this.lastEntry, i2);
        succeeds(i2, -2);
    }

    @Override // com.google.common.collect.CompactHashSet
    public void moveEntry(int i2) {
        int size = size() - 1;
        super.moveEntry(i2);
        succeeds(this.predecessor[i2], this.successor[i2]);
        if (size != i2) {
            succeeds(this.predecessor[size], i2);
            succeeds(i2, this.successor[size]);
        }
        this.predecessor[size] = -1;
        this.successor[size] = -1;
    }

    @Override // com.google.common.collect.CompactHashSet
    public void resizeEntries(int i2) {
        super.resizeEntries(i2);
        int[] iArr = this.predecessor;
        int length = iArr.length;
        this.predecessor = Arrays.copyOf(iArr, i2);
        this.successor = Arrays.copyOf(this.successor, i2);
        if (length < i2) {
            Arrays.fill(this.predecessor, length, i2, -1);
            Arrays.fill(this.successor, length, i2, -1);
        }
    }

    @Override // com.google.common.collect.CompactHashSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public Object[] toArray() {
        return ObjectArrays.toArrayImpl(this);
    }

    public CompactLinkedHashSet(int i2) {
        super(i2);
    }

    public static <E> CompactLinkedHashSet<E> create(Collection<? extends E> collection) {
        CompactLinkedHashSet<E> compactLinkedHashSetCreateWithExpectedSize = createWithExpectedSize(collection.size());
        compactLinkedHashSetCreateWithExpectedSize.addAll(collection);
        return compactLinkedHashSetCreateWithExpectedSize;
    }

    @Override // com.google.common.collect.CompactHashSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public <T> T[] toArray(T[] tArr) {
        return (T[]) ObjectArrays.toArrayImpl(this, tArr);
    }

    public static <E> CompactLinkedHashSet<E> create(E... eArr) {
        CompactLinkedHashSet<E> compactLinkedHashSetCreateWithExpectedSize = createWithExpectedSize(eArr.length);
        Collections.addAll(compactLinkedHashSetCreateWithExpectedSize, eArr);
        return compactLinkedHashSetCreateWithExpectedSize;
    }
}
