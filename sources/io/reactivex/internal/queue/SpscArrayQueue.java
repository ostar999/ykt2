package io.reactivex.internal.queue;

import io.reactivex.annotations.Nullable;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.util.Pow2;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReferenceArray;

/* loaded from: classes8.dex */
public final class SpscArrayQueue<E> extends AtomicReferenceArray<E> implements SimplePlainQueue<E> {
    private static final Integer MAX_LOOK_AHEAD_STEP = Integer.getInteger("jctools.spsc.max.lookahead.step", 4096);
    private static final long serialVersionUID = -1296597691183856449L;
    final AtomicLong consumerIndex;
    final int lookAheadStep;
    final int mask;
    final AtomicLong producerIndex;
    long producerLookAhead;

    public SpscArrayQueue(int i2) {
        super(Pow2.roundToPowerOfTwo(i2));
        this.mask = length() - 1;
        this.producerIndex = new AtomicLong();
        this.consumerIndex = new AtomicLong();
        this.lookAheadStep = Math.min(i2 / 4, MAX_LOOK_AHEAD_STEP.intValue());
    }

    public int calcElementOffset(long j2) {
        return ((int) j2) & this.mask;
    }

    public int calcElementOffset(long j2, int i2) {
        return ((int) j2) & i2;
    }

    @Override // io.reactivex.internal.fuseable.SimpleQueue
    public void clear() {
        while (true) {
            if (poll() == null && isEmpty()) {
                return;
            }
        }
    }

    @Override // io.reactivex.internal.fuseable.SimpleQueue
    public boolean isEmpty() {
        return this.producerIndex.get() == this.consumerIndex.get();
    }

    public E lvElement(int i2) {
        return get(i2);
    }

    @Override // io.reactivex.internal.fuseable.SimpleQueue
    public boolean offer(E e2) {
        if (e2 == null) {
            throw new NullPointerException("Null is not a valid element");
        }
        int i2 = this.mask;
        long j2 = this.producerIndex.get();
        int iCalcElementOffset = calcElementOffset(j2, i2);
        if (j2 >= this.producerLookAhead) {
            long j3 = this.lookAheadStep + j2;
            if (lvElement(calcElementOffset(j3, i2)) == null) {
                this.producerLookAhead = j3;
            } else if (lvElement(iCalcElementOffset) != null) {
                return false;
            }
        }
        soElement(iCalcElementOffset, e2);
        soProducerIndex(j2 + 1);
        return true;
    }

    @Override // io.reactivex.internal.fuseable.SimplePlainQueue, io.reactivex.internal.fuseable.SimpleQueue
    @Nullable
    public E poll() {
        long j2 = this.consumerIndex.get();
        int iCalcElementOffset = calcElementOffset(j2);
        E eLvElement = lvElement(iCalcElementOffset);
        if (eLvElement == null) {
            return null;
        }
        soConsumerIndex(j2 + 1);
        soElement(iCalcElementOffset, null);
        return eLvElement;
    }

    public void soConsumerIndex(long j2) {
        this.consumerIndex.lazySet(j2);
    }

    public void soElement(int i2, E e2) {
        lazySet(i2, e2);
    }

    public void soProducerIndex(long j2) {
        this.producerIndex.lazySet(j2);
    }

    @Override // io.reactivex.internal.fuseable.SimpleQueue
    public boolean offer(E e2, E e3) {
        return offer(e2) && offer(e3);
    }
}
