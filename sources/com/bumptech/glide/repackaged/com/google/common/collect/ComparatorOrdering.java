package com.bumptech.glide.repackaged.com.google.common.collect;

import com.bumptech.glide.repackaged.com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.Comparator;

/* loaded from: classes2.dex */
final class ComparatorOrdering<T> extends Ordering<T> implements Serializable {
    final Comparator<T> comparator;

    public ComparatorOrdering(Comparator<T> comparator) {
        this.comparator = (Comparator) Preconditions.checkNotNull(comparator);
    }

    @Override // com.bumptech.glide.repackaged.com.google.common.collect.Ordering, java.util.Comparator
    public int compare(T t2, T t3) {
        return this.comparator.compare(t2, t3);
    }

    @Override // java.util.Comparator
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof ComparatorOrdering) {
            return this.comparator.equals(((ComparatorOrdering) obj).comparator);
        }
        return false;
    }

    public int hashCode() {
        return this.comparator.hashCode();
    }

    public String toString() {
        return this.comparator.toString();
    }
}
