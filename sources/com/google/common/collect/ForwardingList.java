package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
/* loaded from: classes4.dex */
public abstract class ForwardingList<E> extends ForwardingCollection<E> implements List<E> {
    public void add(int i2, E e2) {
        delegate().add(i2, e2);
    }

    @CanIgnoreReturnValue
    public boolean addAll(int i2, Collection<? extends E> collection) {
        return delegate().addAll(i2, collection);
    }

    @Override // com.google.common.collect.ForwardingCollection, com.google.common.collect.ForwardingObject
    public abstract List<E> delegate();

    @Override // java.util.Collection, java.util.List
    public boolean equals(@NullableDecl Object obj) {
        return obj == this || delegate().equals(obj);
    }

    @Override // java.util.List
    public E get(int i2) {
        return delegate().get(i2);
    }

    @Override // java.util.Collection, java.util.List
    public int hashCode() {
        return delegate().hashCode();
    }

    @Override // java.util.List
    public int indexOf(Object obj) {
        return delegate().indexOf(obj);
    }

    @Override // java.util.List
    public int lastIndexOf(Object obj) {
        return delegate().lastIndexOf(obj);
    }

    @Override // java.util.List
    public ListIterator<E> listIterator() {
        return delegate().listIterator();
    }

    @Override // java.util.List
    @CanIgnoreReturnValue
    public E remove(int i2) {
        return delegate().remove(i2);
    }

    @Override // java.util.List
    @CanIgnoreReturnValue
    public E set(int i2, E e2) {
        return delegate().set(i2, e2);
    }

    public boolean standardAdd(E e2) {
        add(size(), e2);
        return true;
    }

    public boolean standardAddAll(int i2, Iterable<? extends E> iterable) {
        return Lists.addAllImpl(this, i2, iterable);
    }

    @Beta
    public boolean standardEquals(@NullableDecl Object obj) {
        return Lists.equalsImpl(this, obj);
    }

    @Beta
    public int standardHashCode() {
        return Lists.hashCodeImpl(this);
    }

    public int standardIndexOf(@NullableDecl Object obj) {
        return Lists.indexOfImpl(this, obj);
    }

    public Iterator<E> standardIterator() {
        return listIterator();
    }

    public int standardLastIndexOf(@NullableDecl Object obj) {
        return Lists.lastIndexOfImpl(this, obj);
    }

    public ListIterator<E> standardListIterator() {
        return listIterator(0);
    }

    @Beta
    public List<E> standardSubList(int i2, int i3) {
        return Lists.subListImpl(this, i2, i3);
    }

    @Override // java.util.List
    public List<E> subList(int i2, int i3) {
        return delegate().subList(i2, i3);
    }

    @Override // java.util.List
    public ListIterator<E> listIterator(int i2) {
        return delegate().listIterator(i2);
    }

    @Beta
    public ListIterator<E> standardListIterator(int i2) {
        return Lists.listIteratorImpl(this, i2);
    }
}
