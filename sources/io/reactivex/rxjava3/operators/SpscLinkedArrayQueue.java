package io.reactivex.rxjava3.operators;

import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.internal.util.Pow2;
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

    public SpscLinkedArrayQueue(final int bufferSize) {
        int iRoundToPowerOfTwo = Pow2.roundToPowerOfTwo(Math.max(8, bufferSize));
        int i2 = iRoundToPowerOfTwo - 1;
        AtomicReferenceArray<Object> atomicReferenceArray = new AtomicReferenceArray<>(iRoundToPowerOfTwo + 1);
        this.producerBuffer = atomicReferenceArray;
        this.producerMask = i2;
        adjustLookAheadStep(iRoundToPowerOfTwo);
        this.consumerBuffer = atomicReferenceArray;
        this.consumerMask = i2;
        this.producerLookAhead = i2 - 1;
        soProducerIndex(0L);
    }

    private void adjustLookAheadStep(int capacity) {
        this.producerLookAheadStep = Math.min(capacity / 4, MAX_LOOK_AHEAD_STEP);
    }

    private static int calcDirectOffset(int index) {
        return index;
    }

    private static int calcWrappedOffset(long index, int mask) {
        return calcDirectOffset(((int) index) & mask);
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

    private static Object lvElement(AtomicReferenceArray<Object> buffer, int offset) {
        return buffer.get(offset);
    }

    private AtomicReferenceArray<Object> lvNextBufferAndUnlink(AtomicReferenceArray<Object> curr, int nextIndex) {
        int iCalcDirectOffset = calcDirectOffset(nextIndex);
        AtomicReferenceArray<Object> atomicReferenceArray = (AtomicReferenceArray) lvElement(curr, iCalcDirectOffset);
        soElement(curr, iCalcDirectOffset, null);
        return atomicReferenceArray;
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

    private void resize(final AtomicReferenceArray<Object> oldBuffer, final long currIndex, final int offset, final T e2, final long mask) {
        AtomicReferenceArray<Object> atomicReferenceArray = new AtomicReferenceArray<>(oldBuffer.length());
        this.producerBuffer = atomicReferenceArray;
        this.producerLookAhead = (mask + currIndex) - 1;
        soElement(atomicReferenceArray, offset, e2);
        soNext(oldBuffer, atomicReferenceArray);
        soElement(oldBuffer, offset, HAS_NEXT);
        soProducerIndex(currIndex + 1);
    }

    private void soConsumerIndex(long v2) {
        this.consumerIndex.lazySet(v2);
    }

    private static void soElement(AtomicReferenceArray<Object> buffer, int offset, Object e2) {
        buffer.lazySet(offset, e2);
    }

    private void soNext(AtomicReferenceArray<Object> curr, AtomicReferenceArray<Object> next) {
        soElement(curr, calcDirectOffset(curr.length() - 1), next);
    }

    private void soProducerIndex(long v2) {
        this.producerIndex.lazySet(v2);
    }

    private boolean writeToQueue(final AtomicReferenceArray<Object> buffer, final T e2, final long index, final int offset) {
        soElement(buffer, offset, e2);
        soProducerIndex(index + 1);
        return true;
    }

    @Override // io.reactivex.rxjava3.operators.SimpleQueue
    public void clear() {
        while (true) {
            if (poll() == null && isEmpty()) {
                return;
            }
        }
    }

    @Override // io.reactivex.rxjava3.operators.SimpleQueue
    public boolean isEmpty() {
        return lvProducerIndex() == lvConsumerIndex();
    }

    @Override // io.reactivex.rxjava3.operators.SimpleQueue
    public boolean offer(final T e2) {
        if (e2 == null) {
            throw new NullPointerException("Null is not a valid element");
        }
        AtomicReferenceArray<Object> atomicReferenceArray = this.producerBuffer;
        long jLpProducerIndex = lpProducerIndex();
        int i2 = this.producerMask;
        int iCalcWrappedOffset = calcWrappedOffset(jLpProducerIndex, i2);
        if (jLpProducerIndex < this.producerLookAhead) {
            return writeToQueue(atomicReferenceArray, e2, jLpProducerIndex, iCalcWrappedOffset);
        }
        long j2 = this.producerLookAheadStep + jLpProducerIndex;
        if (lvElement(atomicReferenceArray, calcWrappedOffset(j2, i2)) == null) {
            this.producerLookAhead = j2 - 1;
            return writeToQueue(atomicReferenceArray, e2, jLpProducerIndex, iCalcWrappedOffset);
        }
        if (lvElement(atomicReferenceArray, calcWrappedOffset(1 + jLpProducerIndex, i2)) == null) {
            return writeToQueue(atomicReferenceArray, e2, jLpProducerIndex, iCalcWrappedOffset);
        }
        resize(atomicReferenceArray, jLpProducerIndex, iCalcWrappedOffset, e2, i2);
        return true;
    }

    @Nullable
    public T peek() {
        AtomicReferenceArray<Object> atomicReferenceArray = this.consumerBuffer;
        long jLpConsumerIndex = lpConsumerIndex();
        int i2 = this.consumerMask;
        T t2 = (T) lvElement(atomicReferenceArray, calcWrappedOffset(jLpConsumerIndex, i2));
        return t2 == HAS_NEXT ? newBufferPeek(lvNextBufferAndUnlink(atomicReferenceArray, i2 + 1), jLpConsumerIndex, i2) : t2;
    }

    @Override // io.reactivex.rxjava3.operators.SimplePlainQueue, io.reactivex.rxjava3.operators.SimpleQueue
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

    @Override // io.reactivex.rxjava3.operators.SimpleQueue
    public boolean offer(T first, T second) {
        AtomicReferenceArray<Object> atomicReferenceArray = this.producerBuffer;
        long jLvProducerIndex = lvProducerIndex();
        int i2 = this.producerMask;
        long j2 = 2 + jLvProducerIndex;
        if (lvElement(atomicReferenceArray, calcWrappedOffset(j2, i2)) == null) {
            int iCalcWrappedOffset = calcWrappedOffset(jLvProducerIndex, i2);
            soElement(atomicReferenceArray, iCalcWrappedOffset + 1, second);
            soElement(atomicReferenceArray, iCalcWrappedOffset, first);
            soProducerIndex(j2);
            return true;
        }
        AtomicReferenceArray<Object> atomicReferenceArray2 = new AtomicReferenceArray<>(atomicReferenceArray.length());
        this.producerBuffer = atomicReferenceArray2;
        int iCalcWrappedOffset2 = calcWrappedOffset(jLvProducerIndex, i2);
        soElement(atomicReferenceArray2, iCalcWrappedOffset2 + 1, second);
        soElement(atomicReferenceArray2, iCalcWrappedOffset2, first);
        soNext(atomicReferenceArray, atomicReferenceArray2);
        soElement(atomicReferenceArray, iCalcWrappedOffset2, HAS_NEXT);
        soProducerIndex(j2);
        return true;
    }
}
