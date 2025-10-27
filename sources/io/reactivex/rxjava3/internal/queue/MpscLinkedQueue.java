package io.reactivex.rxjava3.internal.queue;

import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.operators.SimplePlainQueue;
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

        public void soNext(LinkedQueueNode<E> n2) {
            lazySet(n2);
        }

        public void spValue(E newValue) {
            this.value = newValue;
        }

        public LinkedQueueNode(E val) {
            spValue(val);
        }
    }

    public MpscLinkedQueue() {
        LinkedQueueNode<T> linkedQueueNode = new LinkedQueueNode<>();
        spConsumerNode(linkedQueueNode);
        xchgProducerNode(linkedQueueNode);
    }

    @Override // io.reactivex.rxjava3.operators.SimpleQueue
    public void clear() {
        while (poll() != null && !isEmpty()) {
        }
    }

    @Override // io.reactivex.rxjava3.operators.SimpleQueue
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

    @Override // io.reactivex.rxjava3.operators.SimpleQueue
    public boolean offer(final T e2) {
        if (e2 == null) {
            throw new NullPointerException("Null is not a valid element");
        }
        LinkedQueueNode<T> linkedQueueNode = new LinkedQueueNode<>(e2);
        xchgProducerNode(linkedQueueNode).soNext(linkedQueueNode);
        return true;
    }

    @Override // io.reactivex.rxjava3.operators.SimplePlainQueue, io.reactivex.rxjava3.operators.SimpleQueue
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

    public void spConsumerNode(LinkedQueueNode<T> node) {
        this.consumerNode.lazySet(node);
    }

    public LinkedQueueNode<T> xchgProducerNode(LinkedQueueNode<T> node) {
        return this.producerNode.getAndSet(node);
    }

    @Override // io.reactivex.rxjava3.operators.SimpleQueue
    public boolean offer(T v12, T v2) {
        offer(v12);
        offer(v2);
        return true;
    }
}
