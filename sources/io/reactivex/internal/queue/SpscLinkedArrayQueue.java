package io.reactivex.internal.queue;

import io.reactivex.annotations.Nullable;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.util.Pow2;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReferenceArray;

/* loaded from: classes8.dex */
public final class SpscLinkedArrayQueue<T> implements SimplePlainQueue<T> {
    AtomicReferenceArray<Object> consumerBuffer;
    final int consumerMask;
    AtomicReferenceArray<Object> producerBuffer;
    long producerLookAhead;
    int producerLookAheadStep;
    final int producerMask;
    static final int MAX_LOOK_AHEAD_STEP = Integer.getInteger("jctools.spsc.max.lookahead.step", 4096).intValue();
    private static final Object HAS_NEXT = new Object();
    final AtomicLong producerIndex = new AtomicLong();
    final AtomicLong consumerIndex = new AtomicLong();

    public SpscLinkedArrayQueue(int i2) {
        int iRoundToPowerOfTwo = Pow2.roundToPowerOfTwo(Math.max(8, i2));
        int i3 = iRoundToPowerOfTwo - 1;
        AtomicReferenceArray<Object> atomicReferenceArray = new AtomicReferenceArray<>(iRoundToPowerOfTwo + 1);
        this.producerBuffer = atomicReferenceArray;
        this.producerMask = i3;
        adjustLookAheadStep(iRoundToPowerOfTwo);
        this.consumerBuffer = atomicReferenceArray;
        this.consumerMask = i3;
        this.producerLookAhead = i3 - 1;
        soProducerIndex(0L);
    }

    private void adjustLookAheadStep(int i2) {
        this.producerLookAheadStep = Math.min(i2 / 4, MAX_LOOK_AHEAD_STEP);
    }

    private static int calcDirectOffset(int i2) {
        return i2;
    }

    private static int calcWrappedOffset(long j2, int i2) {
        return calcDirectOffset(((int) j2) & i2);
    }

    private long lpConsumerIndex() {
        return this.consumerIndex.get();
    }

    private long lpProducerIndex() {
        return this.producerIndex.get();
    }

    private long lvConsumerIndex() {
        return this.consumerIndex.get();
    }

    private static <E> Object lvElement(AtomicReferenceArray<Object> atomicReferenceArray, int i2) {
        return atomicReferenceArray.get(i2);
    }

    private AtomicReferenceArray<Object> lvNextBufferAndUnlink(AtomicReferenceArray<Object> atomicReferenceArray, int i2) {
        int iCalcDirectOffset = calcDirectOffset(i2);
        AtomicReferenceArray<Object> atomicReferenceArray2 = (AtomicReferenceArray) lvElement(atomicReferenceArray, iCalcDirectOffset);
        soElement(atomicReferenceArray, iCalcDirectOffset, null);
        return atomicReferenceArray2;
    }

    private long lvProducerIndex() {
        return this.producerIndex.get();
    }

    private T newBufferPeek(AtomicReferenceArray<Object> atomicReferenceArray, long j2, int i2) {
        this.consumerBuffer = atomicReferenceArray;
        return (T) lvElement(atomicReferenceArray, calcWrappedOffset(j2, i2));
    }

    private T newBufferPoll(AtomicReferenceArray<Object> atomicReferenceArray, long j2, int i2) {
        this.consumerBuffer = atomicReferenceArray;
        int iCalcWrappedOffset = calcWrappedOffset(j2, i2);
        T t2 = (T) lvElement(atomicReferenceArray, iCalcWrappedOffset);
        if (t2 != null) {
            soElement(atomicReferenceArray, iCalcWrappedOffset, null);
            soConsumerIndex(j2 + 1);
        }
        return t2;
    }

    private void resize(AtomicReferenceArray<Object> atomicReferenceArray, long j2, int i2, T t2, long j3) {
        AtomicReferenceArray<Object> atomicReferenceArray2 = new AtomicReferenceArray<>(atomicReferenceArray.length());
        this.producerBuffer = atomicReferenceArray2;
        this.producerLookAhead = (j3 + j2) - 1;
        soElement(atomicReferenceArray2, i2, t2);
        soNext(atomicReferenceArray, atomicReferenceArray2);
        soElement(atomicReferenceArray, i2, HAS_NEXT);
        soProducerIndex(j2 + 1);
    }

    private void soConsumerIndex(long j2) {
        this.consumerIndex.lazySet(j2);
    }

    private static void soElement(AtomicReferenceArray<Object> atomicReferenceArray, int i2, Object obj) {
        atomicReferenceArray.lazySet(i2, obj);
    }

    private void soNext(AtomicReferenceArray<Object> atomicReferenceArray, AtomicReferenceArray<Object> atomicReferenceArray2) {
        soElement(atomicReferenceArray, calcDirectOffset(atomicReferenceArray.length() - 1), atomicReferenceArray2);
    }

    private void soProducerIndex(long j2) {
        this.producerIndex.lazySet(j2);
    }

    private boolean writeToQueue(AtomicReferenceArray<Object> atomicReferenceArray, T t2, long j2, int i2) {
        soElement(atomicReferenceArray, i2, t2);
        soProducerIndex(j2 + 1);
        return true;
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
        return lvProducerIndex() == lvConsumerIndex();
    }

    @Override // io.reactivex.internal.fuseable.SimpleQueue
    public boolean offer(T t2) {
        if (t2 == null) {
            throw new NullPointerException("Null is not a valid element");
        }
        AtomicReferenceArray<Object> atomicReferenceArray = this.producerBuffer;
        long jLpProducerIndex = lpProducerIndex();
        int i2 = this.producerMask;
        int iCalcWrappedOffset = calcWrappedOffset(jLpProducerIndex, i2);
        if (jLpProducerIndex < this.producerLookAhead) {
            return writeToQueue(atomicReferenceArray, t2, jLpProducerIndex, iCalcWrappedOffset);
        }
        long j2 = this.producerLookAheadStep + jLpProducerIndex;
        if (lvElement(atomicReferenceArray, calcWrappedOffset(j2, i2)) == null) {
            this.producerLookAhead = j2 - 1;
            return writeToQueue(atomicReferenceArray, t2, jLpProducerIndex, iCalcWrappedOffset);
        }
        if (lvElement(atomicReferenceArray, calcWrappedOffset(1 + jLpProducerIndex, i2)) == null) {
            return writeToQueue(atomicReferenceArray, t2, jLpProducerIndex, iCalcWrappedOffset);
        }
        resize(atomicReferenceArray, jLpProducerIndex, iCalcWrappedOffset, t2, i2);
        return true;
    }

    public T peek() {
        AtomicReferenceArray<Object> atomicReferenceArray = this.consumerBuffer;
        long jLpConsumerIndex = lpConsumerIndex();
        int i2 = this.consumerMask;
        T t2 = (T) lvElement(atomicReferenceArray, calcWrappedOffset(jLpConsumerIndex, i2));
        return t2 == HAS_NEXT ? newBufferPeek(lvNextBufferAndUnlink(atomicReferenceArray, i2 + 1), jLpConsumerIndex, i2) : t2;
    }

    @Override // io.reactivex.internal.fuseable.SimplePlainQueue, io.reactivex.internal.fuseable.SimpleQueue
    @Nullable
    public T poll() {
        AtomicReferenceArray<Object> atomicReferenceArray = this.consumerBuffer;
        long jLpConsumerIndex = lpConsumerIndex();
        int i2 = this.consumerMask;
        int iCalcWrappedOffset = calcWrappedOffset(jLpConsumerIndex, i2);
        T t2 = (T) lvElement(atomicReferenceArray, iCalcWrappedOffset);
        boolean z2 = t2 == HAS_NEXT;
        if (t2 == null || z2) {
            if (z2) {
                return newBufferPoll(lvNextBufferAndUnlink(atomicReferenceArray, i2 + 1), jLpConsumerIndex, i2);
            }
            return null;
        }
        soElement(atomicReferenceArray, iCalcWrappedOffset, null);
        soConsumerIndex(jLpConsumerIndex + 1);
        return t2;
    }

    public int size() {
        long jLvConsumerIndex = lvConsumerIndex();
        while (true) {
            long jLvProducerIndex = lvProducerIndex();
            long jLvConsumerIndex2 = lvConsumerIndex();
            if (jLvConsumerIndex == jLvConsumerIndex2) {
                return (int) (jLvProducerIndex - jLvConsumerIndex2);
            }
            jLvConsumerIndex = jLvConsumerIndex2;
        }
    }

    @Override // io.reactivex.internal.fuseable.SimpleQueue
    public boolean offer(T t2, T t3) {
        AtomicReferenceArray<Object> atomicReferenceArray = this.producerBuffer;
        long jLvProducerIndex = lvProducerIndex();
        int i2 = this.producerMask;
        long j2 = 2 + jLvProducerIndex;
        if (lvElement(atomicReferenceArray, calcWrappedOffset(j2, i2)) == null) {
            int iCalcWrappedOffset = calcWrappedOffset(jLvProducerIndex, i2);
            soElement(atomicReferenceArray, iCalcWrappedOffset + 1, t3);
            soElement(atomicReferenceArray, iCalcWrappedOffset, t2);
            soProducerIndex(j2);
            return true;
        }
        AtomicReferenceArray<Object> atomicReferenceArray2 = new AtomicReferenceArray<>(atomicReferenceArray.length());
        this.producerBuffer = atomicReferenceArray2;
        int iCalcWrappedOffset2 = calcWrappedOffset(jLvProducerIndex, i2);
        soElement(atomicReferenceArray2, iCalcWrappedOffset2 + 1, t3);
        soElement(atomicReferenceArray2, iCalcWrappedOffset2, t2);
        soNext(atomicReferenceArray, atomicReferenceArray2);
        soElement(atomicReferenceArray, iCalcWrappedOffset2, HAS_NEXT);
        soProducerIndex(j2);
        return true;
    }
}
