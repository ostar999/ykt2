package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.MapMaker;
import com.google.common.math.IntMath;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Beta
@GwtIncompatible
/* loaded from: classes4.dex */
public abstract class Striped<L> {
    private static final int ALL_SET = -1;
    private static final int LARGE_LAZY_CUTOFF = 1024;
    private static final Supplier<ReadWriteLock> READ_WRITE_LOCK_SUPPLIER = new Supplier<ReadWriteLock>() { // from class: com.google.common.util.concurrent.Striped.5
        @Override // com.google.common.base.Supplier
        public ReadWriteLock get() {
            return new ReentrantReadWriteLock();
        }
    };
    private static final Supplier<ReadWriteLock> WEAK_SAFE_READ_WRITE_LOCK_SUPPLIER = new Supplier<ReadWriteLock>() { // from class: com.google.common.util.concurrent.Striped.6
        @Override // com.google.common.base.Supplier
        public ReadWriteLock get() {
            return new WeakSafeReadWriteLock();
        }
    };

    public static class CompactStriped<L> extends PowerOfTwoStriped<L> {
        private final Object[] array;

        @Override // com.google.common.util.concurrent.Striped
        public L getAt(int i2) {
            return (L) this.array[i2];
        }

        @Override // com.google.common.util.concurrent.Striped
        public int size() {
            return this.array.length;
        }

        private CompactStriped(int i2, Supplier<L> supplier) {
            super(i2);
            int i3 = 0;
            Preconditions.checkArgument(i2 <= 1073741824, "Stripes must be <= 2^30)");
            this.array = new Object[this.mask + 1];
            while (true) {
                Object[] objArr = this.array;
                if (i3 >= objArr.length) {
                    return;
                }
                objArr[i3] = supplier.get();
                i3++;
            }
        }
    }

    @VisibleForTesting
    public static class LargeLazyStriped<L> extends PowerOfTwoStriped<L> {
        final ConcurrentMap<Integer, L> locks;
        final int size;
        final Supplier<L> supplier;

        public LargeLazyStriped(int i2, Supplier<L> supplier) {
            super(i2);
            int i3 = this.mask;
            this.size = i3 == -1 ? Integer.MAX_VALUE : i3 + 1;
            this.supplier = supplier;
            this.locks = new MapMaker().weakValues().makeMap();
        }

        @Override // com.google.common.util.concurrent.Striped
        public L getAt(int i2) {
            if (this.size != Integer.MAX_VALUE) {
                Preconditions.checkElementIndex(i2, size());
            }
            L l2 = this.locks.get(Integer.valueOf(i2));
            if (l2 != null) {
                return l2;
            }
            L l3 = this.supplier.get();
            return (L) MoreObjects.firstNonNull(this.locks.putIfAbsent(Integer.valueOf(i2), l3), l3);
        }

        @Override // com.google.common.util.concurrent.Striped
        public int size() {
            return this.size;
        }
    }

    public static class PaddedLock extends ReentrantLock {
        long unused1;
        long unused2;
        long unused3;

        public PaddedLock() {
            super(false);
        }
    }

    public static class PaddedSemaphore extends Semaphore {
        long unused1;
        long unused2;
        long unused3;

        public PaddedSemaphore(int i2) {
            super(i2, false);
        }
    }

    public static abstract class PowerOfTwoStriped<L> extends Striped<L> {
        final int mask;

        public PowerOfTwoStriped(int i2) {
            super();
            Preconditions.checkArgument(i2 > 0, "Stripes must be positive");
            this.mask = i2 > 1073741824 ? -1 : Striped.ceilToPowerOfTwo(i2) - 1;
        }

        @Override // com.google.common.util.concurrent.Striped
        public final L get(Object obj) {
            return getAt(indexFor(obj));
        }

        @Override // com.google.common.util.concurrent.Striped
        public final int indexFor(Object obj) {
            return Striped.smear(obj.hashCode()) & this.mask;
        }
    }

    @VisibleForTesting
    public static class SmallLazyStriped<L> extends PowerOfTwoStriped<L> {
        final AtomicReferenceArray<ArrayReference<? extends L>> locks;
        final ReferenceQueue<L> queue;
        final int size;
        final Supplier<L> supplier;

        public static final class ArrayReference<L> extends WeakReference<L> {
            final int index;

            public ArrayReference(L l2, int i2, ReferenceQueue<L> referenceQueue) {
                super(l2, referenceQueue);
                this.index = i2;
            }
        }

        public SmallLazyStriped(int i2, Supplier<L> supplier) {
            super(i2);
            this.queue = new ReferenceQueue<>();
            int i3 = this.mask;
            int i4 = i3 == -1 ? Integer.MAX_VALUE : i3 + 1;
            this.size = i4;
            this.locks = new AtomicReferenceArray<>(i4);
            this.supplier = supplier;
        }

        private void drainQueue() {
            while (true) {
                Reference<? extends L> referencePoll = this.queue.poll();
                if (referencePoll == null) {
                    return;
                }
                ArrayReference arrayReference = (ArrayReference) referencePoll;
                b.a(this.locks, arrayReference.index, arrayReference, null);
            }
        }

        @Override // com.google.common.util.concurrent.Striped
        public L getAt(int i2) {
            if (this.size != Integer.MAX_VALUE) {
                Preconditions.checkElementIndex(i2, size());
            }
            ArrayReference<? extends L> arrayReference = this.locks.get(i2);
            L l2 = arrayReference == null ? null : arrayReference.get();
            if (l2 != null) {
                return l2;
            }
            L l3 = this.supplier.get();
            ArrayReference arrayReference2 = new ArrayReference(l3, i2, this.queue);
            while (!b.a(this.locks, i2, arrayReference, arrayReference2)) {
                arrayReference = this.locks.get(i2);
                L l4 = arrayReference == null ? null : arrayReference.get();
                if (l4 != null) {
                    return l4;
                }
            }
            drainQueue();
            return l3;
        }

        @Override // com.google.common.util.concurrent.Striped
        public int size() {
            return this.size;
        }
    }

    public static final class WeakSafeCondition extends ForwardingCondition {
        private final Condition delegate;
        private final WeakSafeReadWriteLock strongReference;

        public WeakSafeCondition(Condition condition, WeakSafeReadWriteLock weakSafeReadWriteLock) {
            this.delegate = condition;
            this.strongReference = weakSafeReadWriteLock;
        }

        @Override // com.google.common.util.concurrent.ForwardingCondition
        public Condition delegate() {
            return this.delegate;
        }
    }

    public static final class WeakSafeLock extends ForwardingLock {
        private final Lock delegate;
        private final WeakSafeReadWriteLock strongReference;

        public WeakSafeLock(Lock lock, WeakSafeReadWriteLock weakSafeReadWriteLock) {
            this.delegate = lock;
            this.strongReference = weakSafeReadWriteLock;
        }

        @Override // com.google.common.util.concurrent.ForwardingLock
        public Lock delegate() {
            return this.delegate;
        }

        @Override // com.google.common.util.concurrent.ForwardingLock, java.util.concurrent.locks.Lock
        public Condition newCondition() {
            return new WeakSafeCondition(this.delegate.newCondition(), this.strongReference);
        }
    }

    public static final class WeakSafeReadWriteLock implements ReadWriteLock {
        private final ReadWriteLock delegate = new ReentrantReadWriteLock();

        @Override // java.util.concurrent.locks.ReadWriteLock
        public Lock readLock() {
            return new WeakSafeLock(this.delegate.readLock(), this);
        }

        @Override // java.util.concurrent.locks.ReadWriteLock
        public Lock writeLock() {
            return new WeakSafeLock(this.delegate.writeLock(), this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int ceilToPowerOfTwo(int i2) {
        return 1 << IntMath.log2(i2, RoundingMode.CEILING);
    }

    public static <L> Striped<L> custom(int i2, Supplier<L> supplier) {
        return new CompactStriped(i2, supplier);
    }

    private static <L> Striped<L> lazy(int i2, Supplier<L> supplier) {
        return i2 < 1024 ? new SmallLazyStriped(i2, supplier) : new LargeLazyStriped(i2, supplier);
    }

    public static Striped<Lock> lazyWeakLock(int i2) {
        return lazy(i2, new Supplier<Lock>() { // from class: com.google.common.util.concurrent.Striped.2
            @Override // com.google.common.base.Supplier
            public Lock get() {
                return new ReentrantLock(false);
            }
        });
    }

    public static Striped<ReadWriteLock> lazyWeakReadWriteLock(int i2) {
        return lazy(i2, WEAK_SAFE_READ_WRITE_LOCK_SUPPLIER);
    }

    public static Striped<Semaphore> lazyWeakSemaphore(int i2, final int i3) {
        return lazy(i2, new Supplier<Semaphore>() { // from class: com.google.common.util.concurrent.Striped.4
            @Override // com.google.common.base.Supplier
            public Semaphore get() {
                return new Semaphore(i3, false);
            }
        });
    }

    public static Striped<Lock> lock(int i2) {
        return custom(i2, new Supplier<Lock>() { // from class: com.google.common.util.concurrent.Striped.1
            @Override // com.google.common.base.Supplier
            public Lock get() {
                return new PaddedLock();
            }
        });
    }

    public static Striped<ReadWriteLock> readWriteLock(int i2) {
        return custom(i2, READ_WRITE_LOCK_SUPPLIER);
    }

    public static Striped<Semaphore> semaphore(int i2, final int i3) {
        return custom(i2, new Supplier<Semaphore>() { // from class: com.google.common.util.concurrent.Striped.3
            @Override // com.google.common.base.Supplier
            public Semaphore get() {
                return new PaddedSemaphore(i3);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int smear(int i2) {
        int i3 = i2 ^ ((i2 >>> 20) ^ (i2 >>> 12));
        return (i3 >>> 4) ^ ((i3 >>> 7) ^ i3);
    }

    public Iterable<L> bulkGet(Iterable<?> iterable) {
        Object[] array = Iterables.toArray(iterable, Object.class);
        if (array.length == 0) {
            return ImmutableList.of();
        }
        int[] iArr = new int[array.length];
        for (int i2 = 0; i2 < array.length; i2++) {
            iArr[i2] = indexFor(array[i2]);
        }
        Arrays.sort(iArr);
        int i3 = iArr[0];
        array[0] = getAt(i3);
        for (int i4 = 1; i4 < array.length; i4++) {
            int i5 = iArr[i4];
            if (i5 == i3) {
                array[i4] = array[i4 - 1];
            } else {
                array[i4] = getAt(i5);
                i3 = i5;
            }
        }
        return Collections.unmodifiableList(Arrays.asList(array));
    }

    public abstract L get(Object obj);

    public abstract L getAt(int i2);

    public abstract int indexFor(Object obj);

    public abstract int size();

    private Striped() {
    }
}
