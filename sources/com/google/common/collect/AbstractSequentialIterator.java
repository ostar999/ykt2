package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.util.NoSuchElementException;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
/* loaded from: classes4.dex */
public abstract class AbstractSequentialIterator<T> extends UnmodifiableIterator<T> {

    @NullableDecl
    private T nextOrNull;

    public AbstractSequentialIterator(@NullableDecl T t2) {
        this.nextOrNull = t2;
    }

    @NullableDecl
    public abstract T computeNext(T t2);

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.nextOrNull != null;
    }

    @Override // java.util.Iterator
    public final T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        try {
            T t2 = this.nextOrNull;
            this.nextOrNull = computeNext(t2);
            return t2;
        } catch (Throwable th) {
            this.nextOrNull = computeNext(this.nextOrNull);
            throw th;
        }
    }
}
