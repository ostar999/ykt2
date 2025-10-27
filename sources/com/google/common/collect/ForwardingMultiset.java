package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Objects;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
/* loaded from: classes4.dex */
public abstract class ForwardingMultiset<E> extends ForwardingCollection<E> implements Multiset<E> {

    @Beta
    public class StandardElementSet extends Multisets.ElementSet<E> {
        public StandardElementSet() {
        }

        @Override // com.google.common.collect.Multisets.ElementSet, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<E> iterator() {
            return Multisets.elementIterator(multiset().entrySet().iterator());
        }

        @Override // com.google.common.collect.Multisets.ElementSet
        public Multiset<E> multiset() {
            return ForwardingMultiset.this;
        }
    }

    @Override // com.google.common.collect.Multiset
    @CanIgnoreReturnValue
    public int add(E e2, int i2) {
        return delegate().add(e2, i2);
    }

    @Override // com.google.common.collect.Multiset
    public int count(Object obj) {
        return delegate().count(obj);
    }

    @Override // com.google.common.collect.ForwardingCollection, com.google.common.collect.ForwardingObject
    public abstract Multiset<E> delegate();

    public Set<E> elementSet() {
        return delegate().elementSet();
    }

    public Set<Multiset.Entry<E>> entrySet() {
        return delegate().entrySet();
    }

    @Override // java.util.Collection, com.google.common.collect.Multiset
    public boolean equals(@NullableDecl Object obj) {
        return obj == this || delegate().equals(obj);
    }

    @Override // java.util.Collection, com.google.common.collect.Multiset
    public int hashCode() {
        return delegate().hashCode();
    }

    @Override // com.google.common.collect.Multiset
    @CanIgnoreReturnValue
    public int remove(Object obj, int i2) {
        return delegate().remove(obj, i2);
    }

    @Override // com.google.common.collect.Multiset
    @CanIgnoreReturnValue
    public int setCount(E e2, int i2) {
        return delegate().setCount(e2, i2);
    }

    public boolean standardAdd(E e2) {
        add(e2, 1);
        return true;
    }

    @Override // com.google.common.collect.ForwardingCollection
    @Beta
    public boolean standardAddAll(Collection<? extends E> collection) {
        return Multisets.addAllImpl(this, collection);
    }

    @Override // com.google.common.collect.ForwardingCollection
    public void standardClear() {
        Iterators.clear(entrySet().iterator());
    }

    @Override // com.google.common.collect.ForwardingCollection
    public boolean standardContains(@NullableDecl Object obj) {
        return count(obj) > 0;
    }

    @Beta
    public int standardCount(@NullableDecl Object obj) {
        for (Multiset.Entry<E> entry : entrySet()) {
            if (Objects.equal(entry.getElement(), obj)) {
                return entry.getCount();
            }
        }
        return 0;
    }

    public boolean standardEquals(@NullableDecl Object obj) {
        return Multisets.equalsImpl(this, obj);
    }

    public int standardHashCode() {
        return entrySet().hashCode();
    }

    public Iterator<E> standardIterator() {
        return Multisets.iteratorImpl(this);
    }

    @Override // com.google.common.collect.ForwardingCollection
    public boolean standardRemove(Object obj) {
        return remove(obj, 1) > 0;
    }

    @Override // com.google.common.collect.ForwardingCollection
    public boolean standardRemoveAll(Collection<?> collection) {
        return Multisets.removeAllImpl(this, collection);
    }

    @Override // com.google.common.collect.ForwardingCollection
    public boolean standardRetainAll(Collection<?> collection) {
        return Multisets.retainAllImpl(this, collection);
    }

    public int standardSetCount(E e2, int i2) {
        return Multisets.setCountImpl(this, e2, i2);
    }

    public int standardSize() {
        return Multisets.linearTimeSizeImpl(this);
    }

    @Override // com.google.common.collect.ForwardingCollection
    public String standardToString() {
        return entrySet().toString();
    }

    @Override // com.google.common.collect.Multiset
    @CanIgnoreReturnValue
    public boolean setCount(E e2, int i2, int i3) {
        return delegate().setCount(e2, i2, i3);
    }

    public boolean standardSetCount(E e2, int i2, int i3) {
        return Multisets.setCountImpl(this, e2, i2, i3);
    }
}
