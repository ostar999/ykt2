package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.VisibleForTesting;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(emulated = true, serializable = true)
/* loaded from: classes4.dex */
final class RegularImmutableSet<E> extends ImmutableSet<E> {
    static final RegularImmutableSet<Object> EMPTY = new RegularImmutableSet<>(new Object[0], 0, null, 0, 0);

    @VisibleForTesting
    final transient Object[] elements;
    private final transient int hashCode;
    private final transient int mask;
    private final transient int size;

    @VisibleForTesting
    final transient Object[] table;

    public RegularImmutableSet(Object[] objArr, int i2, Object[] objArr2, int i3, int i4) {
        this.elements = objArr;
        this.table = objArr2;
        this.mask = i3;
        this.hashCode = i2;
        this.size = i4;
    }

    @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean contains(@NullableDecl Object obj) {
        Object[] objArr = this.table;
        if (obj == null || objArr == null) {
            return false;
        }
        int iSmearedHash = Hashing.smearedHash(obj);
        while (true) {
            int i2 = iSmearedHash & this.mask;
            Object obj2 = objArr[i2];
            if (obj2 == null) {
                return false;
            }
            if (obj2.equals(obj)) {
                return true;
            }
            iSmearedHash = i2 + 1;
        }
    }

    @Override // com.google.common.collect.ImmutableCollection
    public int copyIntoArray(Object[] objArr, int i2) {
        System.arraycopy(this.elements, 0, objArr, i2, this.size);
        return i2 + this.size;
    }

    @Override // com.google.common.collect.ImmutableSet
    public ImmutableList<E> createAsList() {
        return ImmutableList.asImmutableList(this.elements, this.size);
    }

    @Override // com.google.common.collect.ImmutableSet, java.util.Collection, java.util.Set
    public int hashCode() {
        return this.hashCode;
    }

    @Override // com.google.common.collect.ImmutableCollection
    public Object[] internalArray() {
        return this.elements;
    }

    @Override // com.google.common.collect.ImmutableCollection
    public int internalArrayEnd() {
        return this.size;
    }

    @Override // com.google.common.collect.ImmutableCollection
    public int internalArrayStart() {
        return 0;
    }

    @Override // com.google.common.collect.ImmutableSet
    public boolean isHashCodeFast() {
        return true;
    }

    @Override // com.google.common.collect.ImmutableCollection
    public boolean isPartialView() {
        return false;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public int size() {
        return this.size;
    }

    @Override // com.google.common.collect.ImmutableSet, com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set, java.util.NavigableSet, com.google.common.collect.SortedIterable
    public UnmodifiableIterator<E> iterator() {
        return asList().iterator();
    }
}
