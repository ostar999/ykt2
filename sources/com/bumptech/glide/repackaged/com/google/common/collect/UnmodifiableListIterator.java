package com.bumptech.glide.repackaged.com.google.common.collect;

import java.util.ListIterator;

/* loaded from: classes2.dex */
public abstract class UnmodifiableListIterator<E> extends UnmodifiableIterator<E> implements ListIterator<E> {
    @Override // java.util.ListIterator
    @Deprecated
    public final void add(E e2) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.ListIterator
    @Deprecated
    public final void set(E e2) {
        throw new UnsupportedOperationException();
    }
}
