package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Queue;

@GwtCompatible
/* loaded from: classes4.dex */
class ConsumingQueueIterator<T> extends AbstractIterator<T> {
    private final Queue<T> queue;

    public ConsumingQueueIterator(T... tArr) {
        ArrayDeque arrayDeque = new ArrayDeque(tArr.length);
        this.queue = arrayDeque;
        Collections.addAll(arrayDeque, tArr);
    }

    @Override // com.google.common.collect.AbstractIterator
    public T computeNext() {
        return this.queue.isEmpty() ? endOfData() : this.queue.remove();
    }

    public ConsumingQueueIterator(Queue<T> queue) {
        this.queue = (Queue) Preconditions.checkNotNull(queue);
    }
}
