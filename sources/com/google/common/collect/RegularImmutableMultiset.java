package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.Multiset;
import com.google.common.primitives.Ints;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.Serializable;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(emulated = true, serializable = true)
/* loaded from: classes4.dex */
class RegularImmutableMultiset<E> extends ImmutableMultiset<E> {
    static final RegularImmutableMultiset<Object> EMPTY = new RegularImmutableMultiset<>(ObjectCountHashMap.create());
    final transient ObjectCountHashMap<E> contents;

    @LazyInit
    private transient ImmutableSet<E> elementSet;
    private final transient int size;

    public final class ElementSet extends IndexedImmutableSet<E> {
        private ElementSet() {
        }

        @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(@NullableDecl Object obj) {
            return RegularImmutableMultiset.this.contains(obj);
        }

        @Override // com.google.common.collect.IndexedImmutableSet
        public E get(int i2) {
            return RegularImmutableMultiset.this.contents.getKey(i2);
        }

        @Override // com.google.common.collect.ImmutableCollection
        public boolean isPartialView() {
            return true;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return RegularImmutableMultiset.this.contents.size();
        }
    }

    @GwtIncompatible
    public static class SerializedForm implements Serializable {
        private static final long serialVersionUID = 0;
        final int[] counts;
        final Object[] elements;

        public SerializedForm(Multiset<?> multiset) {
            int size = multiset.entrySet().size();
            this.elements = new Object[size];
            this.counts = new int[size];
            int i2 = 0;
            for (Multiset.Entry<?> entry : multiset.entrySet()) {
                this.elements[i2] = entry.getElement();
                this.counts[i2] = entry.getCount();
                i2++;
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public Object readResolve() {
            ImmutableMultiset.Builder builder = new ImmutableMultiset.Builder(this.elements.length);
            int i2 = 0;
            while (true) {
                Object[] objArr = this.elements;
                if (i2 >= objArr.length) {
                    return builder.build();
                }
                builder.addCopies(objArr[i2], this.counts[i2]);
                i2++;
            }
        }
    }

    public RegularImmutableMultiset(ObjectCountHashMap<E> objectCountHashMap) {
        this.contents = objectCountHashMap;
        long value = 0;
        for (int i2 = 0; i2 < objectCountHashMap.size(); i2++) {
            value += objectCountHashMap.getValue(i2);
        }
        this.size = Ints.saturatedCast(value);
    }

    @Override // com.google.common.collect.Multiset
    public int count(@NullableDecl Object obj) {
        return this.contents.get(obj);
    }

    @Override // com.google.common.collect.ImmutableMultiset
    public Multiset.Entry<E> getEntry(int i2) {
        return this.contents.getEntry(i2);
    }

    @Override // com.google.common.collect.ImmutableCollection
    public boolean isPartialView() {
        return false;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, com.google.common.collect.Multiset
    public int size() {
        return this.size;
    }

    @Override // com.google.common.collect.ImmutableMultiset, com.google.common.collect.ImmutableCollection
    @GwtIncompatible
    public Object writeReplace() {
        return new SerializedForm(this);
    }

    @Override // com.google.common.collect.ImmutableMultiset, com.google.common.collect.Multiset
    public ImmutableSet<E> elementSet() {
        ImmutableSet<E> immutableSet = this.elementSet;
        if (immutableSet != null) {
            return immutableSet;
        }
        ElementSet elementSet = new ElementSet();
        this.elementSet = elementSet;
        return elementSet;
    }
}
