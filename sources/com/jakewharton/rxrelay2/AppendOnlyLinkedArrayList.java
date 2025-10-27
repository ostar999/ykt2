package com.jakewharton.rxrelay2;

import io.reactivex.functions.Predicate;

/* loaded from: classes4.dex */
class AppendOnlyLinkedArrayList<T> {
    private final int capacity;
    private final Object[] head;
    private int offset;
    private Object[] tail;

    public interface NonThrowingPredicate<T> extends Predicate<T> {
        @Override // io.reactivex.functions.Predicate
        boolean test(T t2);
    }

    public AppendOnlyLinkedArrayList(int i2) {
        this.capacity = i2;
        Object[] objArr = new Object[i2 + 1];
        this.head = objArr;
        this.tail = objArr;
    }

    public boolean accept(Relay<? super T> relay) {
        Object[] objArr = this.head;
        int i2 = this.capacity;
        while (true) {
            if (objArr == null) {
                return false;
            }
            for (int i3 = 0; i3 < i2; i3++) {
                Object obj = objArr[i3];
                if (obj == null) {
                    break;
                }
                relay.accept(obj);
            }
            objArr = (Object[]) objArr[i2];
        }
    }

    public void add(T t2) {
        int i2 = this.capacity;
        int i3 = this.offset;
        if (i3 == i2) {
            Object[] objArr = new Object[i2 + 1];
            this.tail[i2] = objArr;
            this.tail = objArr;
            i3 = 0;
        }
        this.tail[i3] = t2;
        this.offset = i3 + 1;
    }

    public void forEachWhile(NonThrowingPredicate<? super T> nonThrowingPredicate) {
        int i2;
        int i3 = this.capacity;
        for (Object[] objArr = this.head; objArr != null; objArr = (Object[]) objArr[i3]) {
            while (i2 < i3) {
                Object obj = objArr[i2];
                i2 = (obj == null || nonThrowingPredicate.test(obj)) ? 0 : i2 + 1;
            }
        }
    }
}
