package io.reactivex.internal.queue;

import io.reactivex.annotations.Nullable;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes8.dex */
public final class MpscLinkedQueue<T> implements SimplePlainQueue<T> {
    private final AtomicReference<LinkedQueueNode<T>> producerNode = new AtomicReference<>();
    private final AtomicReference<LinkedQueueNode<T>> consumerNode = new AtomicReference<>();

    public static final class LinkedQueueNode<E> extends AtomicReference<LinkedQueueNode<E>> {
        private static final long serialVersionUID = 2404266111789071508L;
        private E value;

        public LinkedQueueNode() {
        }

        public E getAndNullValue() {
            E eLpValue = lpValue();
            spValue(null);
            return eLpValue;
        }

        public E lpValue() {
            return this.value;
        }

        public LinkedQueueNode<E> lvNext() {
            return get();
        }

        public void soNext(LinkedQueueNode<E> linkedQueueNode) {
            lazySet(linkedQueueNode);
        }

        public void spValue(E e2) {
            this.value = e2;
        }

        public LinkedQueueNode(E e2) {
            spValue(e2);
        }
    }

    public MpscLinkedQueue() {
        LinkedQueueNode<T> linkedQueueNode = new LinkedQueueNode<>();
        spConsumerNode(linkedQueueNode);
        xchgProducerNode(linkedQueueNode);
    }

    @Override // io.reactivex.internal.fuseable.SimpleQueue
    public void clear() {
        while (poll() != null && !isEmpty()) {
        }
    }

    @Override // io.reactivex.internal.fuseable.SimpleQueue
    public boolean isEmpty() {
        return lvConsumerNode() == lvProducerNode();
    }

    public LinkedQueueNode<T> lpConsumerNode() {
        return this.consumerNode.get();
    }

    public LinkedQueueNode<T> lvConsumerNode() {
        return this.consumerNode.get();
    }

    public LinkedQueueNode<T> lvProducerNode() {
        return this.producerNode.get();
    }

    @Override // io.reactivex.internal.fuseable.SimpleQueue
    public boolean offer(T t2) {
        if (t2 == null) {
            throw new NullPointerException("Null is not a valid element");
        }
        LinkedQueueNode<T> linkedQueueNode = new LinkedQueueNode<>(t2);
        xchgProducerNode(linkedQueueNode).soNext(linkedQueueNode);
        return true;
    }

    @Override // io.reactivex.internal.fuseable.SimplePlainQueue, io.reactivex.internal.fuseable.SimpleQueue
    @Nullable
    public T poll() {
        LinkedQueueNode<T> linkedQueueNodeLvNext;
        LinkedQueueNode<T> linkedQueueNodeLpConsumerNode = lpConsumerNode();
        LinkedQueueNode<T> linkedQueueNodeLvNext2 = linkedQueueNodeLpConsumerNode.lvNext();
        if (linkedQueueNodeLvNext2 != null) {
            T andNullValue = linkedQueueNodeLvNext2.getAndNullValue();
            spConsumerNode(linkedQueueNodeLvNext2);
            return andNullValue;
        }
        if (linkedQueueNodeLpConsumerNode == lvProducerNode()) {
            return null;
        }
        do {
            linkedQueueNodeLvNext = linkedQueueNodeLpConsumerNode.lvNext();
        } while (linkedQueueNodeLvNext == null);
        T andNullValue2 = linkedQueueNodeLvNext.getAndNullValue();
        spConsumerNode(linkedQueueNodeLvNext);
        return andNullValue2;
    }

    public void spConsumerNode(LinkedQueueNode<T> linkedQueueNode) {
        this.consumerNode.lazySet(linkedQueueNode);
    }

    public LinkedQueueNode<T> xchgProducerNode(LinkedQueueNode<T> linkedQueueNode) {
        return this.producerNode.getAndSet(linkedQueueNode);
    }

    @Override // io.reactivex.internal.fuseable.SimpleQueue
    public boolean offer(T t2, T t3) {
        offer(t2);
        offer(t3);
        return true;
    }
}
