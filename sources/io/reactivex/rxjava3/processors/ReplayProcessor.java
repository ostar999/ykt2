package io.reactivex.rxjava3.processors;

import androidx.camera.view.j;
import io.reactivex.rxjava3.annotations.CheckReturnValue;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.internal.functions.ObjectHelper;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class ReplayProcessor<T> extends FlowableProcessor<T> {
    final ReplayBuffer<T> buffer;
    boolean done;
    final AtomicReference<ReplaySubscription<T>[]> subscribers = new AtomicReference<>(EMPTY);
    private static final Object[] EMPTY_ARRAY = new Object[0];
    static final ReplaySubscription[] EMPTY = new ReplaySubscription[0];
    static final ReplaySubscription[] TERMINATED = new ReplaySubscription[0];

    public static final class Node<T> extends AtomicReference<Node<T>> {
        private static final long serialVersionUID = 6404226426336033100L;
        final T value;

        public Node(T value) {
            this.value = value;
        }
    }

    public interface ReplayBuffer<T> {
        void complete();

        void error(Throwable ex);

        Throwable getError();

        @Nullable
        T getValue();

        T[] getValues(T[] array);

        boolean isDone();

        void next(T value);

        void replay(ReplaySubscription<T> rs);

        int size();

        void trimHead();
    }

    public static final class ReplaySubscription<T> extends AtomicInteger implements Subscription {
        private static final long serialVersionUID = 466549804534799122L;
        volatile boolean cancelled;
        final Subscriber<? super T> downstream;
        long emitted;
        Object index;
        final AtomicLong requested = new AtomicLong();
        final ReplayProcessor<T> state;

        public ReplaySubscription(Subscriber<? super T> actual, ReplayProcessor<T> state) {
            this.downstream = actual;
            this.state = state;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            this.state.remove(this);
        }

        @Override // org.reactivestreams.Subscription
        public void request(long n2) {
            if (SubscriptionHelper.validate(n2)) {
                BackpressureHelper.add(this.requested, n2);
                this.state.buffer.replay(this);
            }
        }
    }

    public static final class SizeAndTimeBoundReplayBuffer<T> implements ReplayBuffer<T> {
        volatile boolean done;
        Throwable error;
        volatile TimedNode<T> head;
        final long maxAge;
        final int maxSize;
        final Scheduler scheduler;
        int size;
        TimedNode<T> tail;
        final TimeUnit unit;

        public SizeAndTimeBoundReplayBuffer(int maxSize, long maxAge, TimeUnit unit, Scheduler scheduler) {
            this.maxSize = maxSize;
            this.maxAge = maxAge;
            this.unit = unit;
            this.scheduler = scheduler;
            TimedNode<T> timedNode = new TimedNode<>(null, 0L);
            this.tail = timedNode;
            this.head = timedNode;
        }

        @Override // io.reactivex.rxjava3.processors.ReplayProcessor.ReplayBuffer
        public void complete() {
            trimFinal();
            this.done = true;
        }

        @Override // io.reactivex.rxjava3.processors.ReplayProcessor.ReplayBuffer
        public void error(Throwable ex) {
            trimFinal();
            this.error = ex;
            this.done = true;
        }

        @Override // io.reactivex.rxjava3.processors.ReplayProcessor.ReplayBuffer
        public Throwable getError() {
            return this.error;
        }

        public TimedNode<T> getHead() {
            TimedNode<T> timedNode;
            TimedNode<T> timedNode2 = this.head;
            long jNow = this.scheduler.now(this.unit) - this.maxAge;
            TimedNode<T> timedNode3 = timedNode2.get();
            while (true) {
                TimedNode<T> timedNode4 = timedNode3;
                timedNode = timedNode2;
                timedNode2 = timedNode4;
                if (timedNode2 == null || timedNode2.time > jNow) {
                    break;
                }
                timedNode3 = timedNode2.get();
            }
            return timedNode;
        }

        @Override // io.reactivex.rxjava3.processors.ReplayProcessor.ReplayBuffer
        @Nullable
        public T getValue() {
            TimedNode<T> timedNode = this.head;
            while (true) {
                TimedNode<T> timedNode2 = timedNode.get();
                if (timedNode2 == null) {
                    break;
                }
                timedNode = timedNode2;
            }
            if (timedNode.time < this.scheduler.now(this.unit) - this.maxAge) {
                return null;
            }
            return timedNode.value;
        }

        @Override // io.reactivex.rxjava3.processors.ReplayProcessor.ReplayBuffer
        public T[] getValues(T[] tArr) {
            TimedNode<T> head = getHead();
            int size = size(head);
            if (size != 0) {
                if (tArr.length < size) {
                    tArr = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), size));
                }
                for (int i2 = 0; i2 != size; i2++) {
                    head = head.get();
                    tArr[i2] = head.value;
                }
                if (tArr.length > size) {
                    tArr[size] = null;
                }
            } else if (tArr.length != 0) {
                tArr[0] = null;
            }
            return tArr;
        }

        @Override // io.reactivex.rxjava3.processors.ReplayProcessor.ReplayBuffer
        public boolean isDone() {
            return this.done;
        }

        @Override // io.reactivex.rxjava3.processors.ReplayProcessor.ReplayBuffer
        public void next(T value) {
            TimedNode<T> timedNode = new TimedNode<>(value, this.scheduler.now(this.unit));
            TimedNode<T> timedNode2 = this.tail;
            this.tail = timedNode;
            this.size++;
            timedNode2.set(timedNode);
            trim();
        }

        @Override // io.reactivex.rxjava3.processors.ReplayProcessor.ReplayBuffer
        public void replay(ReplaySubscription<T> replaySubscription) {
            if (replaySubscription.getAndIncrement() != 0) {
                return;
            }
            Subscriber<? super T> subscriber = replaySubscription.downstream;
            TimedNode<T> head = (TimedNode) replaySubscription.index;
            if (head == null) {
                head = getHead();
            }
            long j2 = replaySubscription.emitted;
            int iAddAndGet = 1;
            do {
                long j3 = replaySubscription.requested.get();
                while (j2 != j3) {
                    if (replaySubscription.cancelled) {
                        replaySubscription.index = null;
                        return;
                    }
                    boolean z2 = this.done;
                    TimedNode<T> timedNode = head.get();
                    boolean z3 = timedNode == null;
                    if (z2 && z3) {
                        replaySubscription.index = null;
                        replaySubscription.cancelled = true;
                        Throwable th = this.error;
                        if (th == null) {
                            subscriber.onComplete();
                            return;
                        } else {
                            subscriber.onError(th);
                            return;
                        }
                    }
                    if (z3) {
                        break;
                    }
                    subscriber.onNext(timedNode.value);
                    j2++;
                    head = timedNode;
                }
                if (j2 == j3) {
                    if (replaySubscription.cancelled) {
                        replaySubscription.index = null;
                        return;
                    }
                    if (this.done && head.get() == null) {
                        replaySubscription.index = null;
                        replaySubscription.cancelled = true;
                        Throwable th2 = this.error;
                        if (th2 == null) {
                            subscriber.onComplete();
                            return;
                        } else {
                            subscriber.onError(th2);
                            return;
                        }
                    }
                }
                replaySubscription.index = head;
                replaySubscription.emitted = j2;
                iAddAndGet = replaySubscription.addAndGet(-iAddAndGet);
            } while (iAddAndGet != 0);
        }

        @Override // io.reactivex.rxjava3.processors.ReplayProcessor.ReplayBuffer
        public int size() {
            return size(getHead());
        }

        public void trim() {
            int i2 = this.size;
            if (i2 > this.maxSize) {
                this.size = i2 - 1;
                this.head = this.head.get();
            }
            long jNow = this.scheduler.now(this.unit) - this.maxAge;
            TimedNode<T> timedNode = this.head;
            while (this.size > 1) {
                TimedNode<T> timedNode2 = timedNode.get();
                if (timedNode2.time > jNow) {
                    this.head = timedNode;
                    return;
                } else {
                    this.size--;
                    timedNode = timedNode2;
                }
            }
            this.head = timedNode;
        }

        public void trimFinal() {
            long jNow = this.scheduler.now(this.unit) - this.maxAge;
            TimedNode<T> timedNode = this.head;
            while (true) {
                TimedNode<T> timedNode2 = timedNode.get();
                if (timedNode2 == null) {
                    if (timedNode.value != null) {
                        this.head = new TimedNode<>(null, 0L);
                        return;
                    } else {
                        this.head = timedNode;
                        return;
                    }
                }
                if (timedNode2.time > jNow) {
                    if (timedNode.value == null) {
                        this.head = timedNode;
                        return;
                    }
                    TimedNode<T> timedNode3 = new TimedNode<>(null, 0L);
                    timedNode3.lazySet(timedNode.get());
                    this.head = timedNode3;
                    return;
                }
                timedNode = timedNode2;
            }
        }

        @Override // io.reactivex.rxjava3.processors.ReplayProcessor.ReplayBuffer
        public void trimHead() {
            if (this.head.value != null) {
                TimedNode<T> timedNode = new TimedNode<>(null, 0L);
                timedNode.lazySet(this.head.get());
                this.head = timedNode;
            }
        }

        public int size(TimedNode<T> h2) {
            int i2 = 0;
            while (i2 != Integer.MAX_VALUE && (h2 = h2.get()) != null) {
                i2++;
            }
            return i2;
        }
    }

    public static final class SizeBoundReplayBuffer<T> implements ReplayBuffer<T> {
        volatile boolean done;
        Throwable error;
        volatile Node<T> head;
        final int maxSize;
        int size;
        Node<T> tail;

        public SizeBoundReplayBuffer(int maxSize) {
            this.maxSize = maxSize;
            Node<T> node = new Node<>(null);
            this.tail = node;
            this.head = node;
        }

        @Override // io.reactivex.rxjava3.processors.ReplayProcessor.ReplayBuffer
        public void complete() {
            trimHead();
            this.done = true;
        }

        @Override // io.reactivex.rxjava3.processors.ReplayProcessor.ReplayBuffer
        public void error(Throwable ex) {
            this.error = ex;
            trimHead();
            this.done = true;
        }

        @Override // io.reactivex.rxjava3.processors.ReplayProcessor.ReplayBuffer
        public Throwable getError() {
            return this.error;
        }

        @Override // io.reactivex.rxjava3.processors.ReplayProcessor.ReplayBuffer
        public T getValue() {
            Node<T> node = this.head;
            while (true) {
                Node<T> node2 = node.get();
                if (node2 == null) {
                    return node.value;
                }
                node = node2;
            }
        }

        @Override // io.reactivex.rxjava3.processors.ReplayProcessor.ReplayBuffer
        public T[] getValues(T[] tArr) {
            Node<T> node = this.head;
            Node<T> node2 = node;
            int i2 = 0;
            while (true) {
                node2 = node2.get();
                if (node2 == null) {
                    break;
                }
                i2++;
            }
            if (tArr.length < i2) {
                tArr = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), i2));
            }
            for (int i3 = 0; i3 < i2; i3++) {
                node = node.get();
                tArr[i3] = node.value;
            }
            if (tArr.length > i2) {
                tArr[i2] = null;
            }
            return tArr;
        }

        @Override // io.reactivex.rxjava3.processors.ReplayProcessor.ReplayBuffer
        public boolean isDone() {
            return this.done;
        }

        @Override // io.reactivex.rxjava3.processors.ReplayProcessor.ReplayBuffer
        public void next(T value) {
            Node<T> node = new Node<>(value);
            Node<T> node2 = this.tail;
            this.tail = node;
            this.size++;
            node2.set(node);
            trim();
        }

        @Override // io.reactivex.rxjava3.processors.ReplayProcessor.ReplayBuffer
        public void replay(ReplaySubscription<T> replaySubscription) {
            if (replaySubscription.getAndIncrement() != 0) {
                return;
            }
            Subscriber<? super T> subscriber = replaySubscription.downstream;
            Node<T> node = (Node) replaySubscription.index;
            if (node == null) {
                node = this.head;
            }
            long j2 = replaySubscription.emitted;
            int iAddAndGet = 1;
            do {
                long j3 = replaySubscription.requested.get();
                while (j2 != j3) {
                    if (replaySubscription.cancelled) {
                        replaySubscription.index = null;
                        return;
                    }
                    boolean z2 = this.done;
                    Node<T> node2 = node.get();
                    boolean z3 = node2 == null;
                    if (z2 && z3) {
                        replaySubscription.index = null;
                        replaySubscription.cancelled = true;
                        Throwable th = this.error;
                        if (th == null) {
                            subscriber.onComplete();
                            return;
                        } else {
                            subscriber.onError(th);
                            return;
                        }
                    }
                    if (z3) {
                        break;
                    }
                    subscriber.onNext(node2.value);
                    j2++;
                    node = node2;
                }
                if (j2 == j3) {
                    if (replaySubscription.cancelled) {
                        replaySubscription.index = null;
                        return;
                    }
                    if (this.done && node.get() == null) {
                        replaySubscription.index = null;
                        replaySubscription.cancelled = true;
                        Throwable th2 = this.error;
                        if (th2 == null) {
                            subscriber.onComplete();
                            return;
                        } else {
                            subscriber.onError(th2);
                            return;
                        }
                    }
                }
                replaySubscription.index = node;
                replaySubscription.emitted = j2;
                iAddAndGet = replaySubscription.addAndGet(-iAddAndGet);
            } while (iAddAndGet != 0);
        }

        @Override // io.reactivex.rxjava3.processors.ReplayProcessor.ReplayBuffer
        public int size() {
            Node<T> node = this.head;
            int i2 = 0;
            while (i2 != Integer.MAX_VALUE && (node = node.get()) != null) {
                i2++;
            }
            return i2;
        }

        public void trim() {
            int i2 = this.size;
            if (i2 > this.maxSize) {
                this.size = i2 - 1;
                this.head = this.head.get();
            }
        }

        @Override // io.reactivex.rxjava3.processors.ReplayProcessor.ReplayBuffer
        public void trimHead() {
            if (this.head.value != null) {
                Node<T> node = new Node<>(null);
                node.lazySet(this.head.get());
                this.head = node;
            }
        }
    }

    public static final class TimedNode<T> extends AtomicReference<TimedNode<T>> {
        private static final long serialVersionUID = 6404226426336033100L;
        final long time;
        final T value;

        public TimedNode(T value, long time) {
            this.value = value;
            this.time = time;
        }
    }

    public static final class UnboundedReplayBuffer<T> implements ReplayBuffer<T> {
        final List<T> buffer;
        volatile boolean done;
        Throwable error;
        volatile int size;

        public UnboundedReplayBuffer(int capacityHint) {
            this.buffer = new ArrayList(capacityHint);
        }

        @Override // io.reactivex.rxjava3.processors.ReplayProcessor.ReplayBuffer
        public void complete() {
            this.done = true;
        }

        @Override // io.reactivex.rxjava3.processors.ReplayProcessor.ReplayBuffer
        public void error(Throwable ex) {
            this.error = ex;
            this.done = true;
        }

        @Override // io.reactivex.rxjava3.processors.ReplayProcessor.ReplayBuffer
        public Throwable getError() {
            return this.error;
        }

        @Override // io.reactivex.rxjava3.processors.ReplayProcessor.ReplayBuffer
        @Nullable
        public T getValue() {
            int i2 = this.size;
            if (i2 == 0) {
                return null;
            }
            return this.buffer.get(i2 - 1);
        }

        @Override // io.reactivex.rxjava3.processors.ReplayProcessor.ReplayBuffer
        public T[] getValues(T[] tArr) {
            int i2 = this.size;
            if (i2 == 0) {
                if (tArr.length != 0) {
                    tArr[0] = null;
                }
                return tArr;
            }
            List<T> list = this.buffer;
            if (tArr.length < i2) {
                tArr = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), i2));
            }
            for (int i3 = 0; i3 < i2; i3++) {
                tArr[i3] = list.get(i3);
            }
            if (tArr.length > i2) {
                tArr[i2] = null;
            }
            return tArr;
        }

        @Override // io.reactivex.rxjava3.processors.ReplayProcessor.ReplayBuffer
        public boolean isDone() {
            return this.done;
        }

        @Override // io.reactivex.rxjava3.processors.ReplayProcessor.ReplayBuffer
        public void next(T value) {
            this.buffer.add(value);
            this.size++;
        }

        @Override // io.reactivex.rxjava3.processors.ReplayProcessor.ReplayBuffer
        public void replay(ReplaySubscription<T> rs) {
            int iIntValue;
            if (rs.getAndIncrement() != 0) {
                return;
            }
            List<T> list = this.buffer;
            Subscriber<? super T> subscriber = rs.downstream;
            Integer num = (Integer) rs.index;
            if (num != null) {
                iIntValue = num.intValue();
            } else {
                iIntValue = 0;
                rs.index = 0;
            }
            long j2 = rs.emitted;
            int iAddAndGet = 1;
            do {
                long j3 = rs.requested.get();
                while (j2 != j3) {
                    if (rs.cancelled) {
                        rs.index = null;
                        return;
                    }
                    boolean z2 = this.done;
                    int i2 = this.size;
                    if (z2 && iIntValue == i2) {
                        rs.index = null;
                        rs.cancelled = true;
                        Throwable th = this.error;
                        if (th == null) {
                            subscriber.onComplete();
                            return;
                        } else {
                            subscriber.onError(th);
                            return;
                        }
                    }
                    if (iIntValue == i2) {
                        break;
                    }
                    subscriber.onNext(list.get(iIntValue));
                    iIntValue++;
                    j2++;
                }
                if (j2 == j3) {
                    if (rs.cancelled) {
                        rs.index = null;
                        return;
                    }
                    boolean z3 = this.done;
                    int i3 = this.size;
                    if (z3 && iIntValue == i3) {
                        rs.index = null;
                        rs.cancelled = true;
                        Throwable th2 = this.error;
                        if (th2 == null) {
                            subscriber.onComplete();
                            return;
                        } else {
                            subscriber.onError(th2);
                            return;
                        }
                    }
                }
                rs.index = Integer.valueOf(iIntValue);
                rs.emitted = j2;
                iAddAndGet = rs.addAndGet(-iAddAndGet);
            } while (iAddAndGet != 0);
        }

        @Override // io.reactivex.rxjava3.processors.ReplayProcessor.ReplayBuffer
        public int size() {
            return this.size;
        }

        @Override // io.reactivex.rxjava3.processors.ReplayProcessor.ReplayBuffer
        public void trimHead() {
        }
    }

    public ReplayProcessor(ReplayBuffer<T> buffer) {
        this.buffer = buffer;
    }

    @CheckReturnValue
    @NonNull
    public static <T> ReplayProcessor<T> create() {
        return new ReplayProcessor<>(new UnboundedReplayBuffer(16));
    }

    @CheckReturnValue
    public static <T> ReplayProcessor<T> createUnbounded() {
        return new ReplayProcessor<>(new SizeBoundReplayBuffer(Integer.MAX_VALUE));
    }

    @CheckReturnValue
    @NonNull
    public static <T> ReplayProcessor<T> createWithSize(int maxSize) {
        ObjectHelper.verifyPositive(maxSize, "maxSize");
        return new ReplayProcessor<>(new SizeBoundReplayBuffer(maxSize));
    }

    @CheckReturnValue
    @NonNull
    public static <T> ReplayProcessor<T> createWithTime(long maxAge, @NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        ObjectHelper.verifyPositive(maxAge, "maxAge");
        Objects.requireNonNull(unit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return new ReplayProcessor<>(new SizeAndTimeBoundReplayBuffer(Integer.MAX_VALUE, maxAge, unit, scheduler));
    }

    @CheckReturnValue
    @NonNull
    public static <T> ReplayProcessor<T> createWithTimeAndSize(long maxAge, @NonNull TimeUnit unit, @NonNull Scheduler scheduler, int maxSize) {
        ObjectHelper.verifyPositive(maxSize, "maxSize");
        ObjectHelper.verifyPositive(maxAge, "maxAge");
        Objects.requireNonNull(unit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return new ReplayProcessor<>(new SizeAndTimeBoundReplayBuffer(maxSize, maxAge, unit, scheduler));
    }

    public boolean add(ReplaySubscription<T> rs) {
        ReplaySubscription<T>[] replaySubscriptionArr;
        ReplaySubscription[] replaySubscriptionArr2;
        do {
            replaySubscriptionArr = this.subscribers.get();
            if (replaySubscriptionArr == TERMINATED) {
                return false;
            }
            int length = replaySubscriptionArr.length;
            replaySubscriptionArr2 = new ReplaySubscription[length + 1];
            System.arraycopy(replaySubscriptionArr, 0, replaySubscriptionArr2, 0, length);
            replaySubscriptionArr2[length] = rs;
        } while (!j.a(this.subscribers, replaySubscriptionArr, replaySubscriptionArr2));
        return true;
    }

    public void cleanupBuffer() {
        this.buffer.trimHead();
    }

    @Override // io.reactivex.rxjava3.processors.FlowableProcessor
    @CheckReturnValue
    @Nullable
    public Throwable getThrowable() {
        ReplayBuffer<T> replayBuffer = this.buffer;
        if (replayBuffer.isDone()) {
            return replayBuffer.getError();
        }
        return null;
    }

    @CheckReturnValue
    public T getValue() {
        return this.buffer.getValue();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @CheckReturnValue
    public Object[] getValues() {
        Object[] objArr = EMPTY_ARRAY;
        Object[] values = getValues(objArr);
        return values == objArr ? new Object[0] : values;
    }

    @Override // io.reactivex.rxjava3.processors.FlowableProcessor
    @CheckReturnValue
    public boolean hasComplete() {
        ReplayBuffer<T> replayBuffer = this.buffer;
        return replayBuffer.isDone() && replayBuffer.getError() == null;
    }

    @Override // io.reactivex.rxjava3.processors.FlowableProcessor
    @CheckReturnValue
    public boolean hasSubscribers() {
        return this.subscribers.get().length != 0;
    }

    @Override // io.reactivex.rxjava3.processors.FlowableProcessor
    @CheckReturnValue
    public boolean hasThrowable() {
        ReplayBuffer<T> replayBuffer = this.buffer;
        return replayBuffer.isDone() && replayBuffer.getError() != null;
    }

    @CheckReturnValue
    public boolean hasValue() {
        return this.buffer.size() != 0;
    }

    @Override // org.reactivestreams.Subscriber
    public void onComplete() {
        if (this.done) {
            return;
        }
        this.done = true;
        ReplayBuffer<T> replayBuffer = this.buffer;
        replayBuffer.complete();
        for (ReplaySubscription<T> replaySubscription : this.subscribers.getAndSet(TERMINATED)) {
            replayBuffer.replay(replaySubscription);
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onError(Throwable t2) {
        ExceptionHelper.nullCheck(t2, "onError called with a null Throwable.");
        if (this.done) {
            RxJavaPlugins.onError(t2);
            return;
        }
        this.done = true;
        ReplayBuffer<T> replayBuffer = this.buffer;
        replayBuffer.error(t2);
        for (ReplaySubscription<T> replaySubscription : this.subscribers.getAndSet(TERMINATED)) {
            replayBuffer.replay(replaySubscription);
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onNext(T t2) {
        ExceptionHelper.nullCheck(t2, "onNext called with a null value.");
        if (this.done) {
            return;
        }
        ReplayBuffer<T> replayBuffer = this.buffer;
        replayBuffer.next(t2);
        for (ReplaySubscription<T> replaySubscription : this.subscribers.get()) {
            replayBuffer.replay(replaySubscription);
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onSubscribe(Subscription s2) {
        if (this.done) {
            s2.cancel();
        } else {
            s2.request(Long.MAX_VALUE);
        }
    }

    public void remove(ReplaySubscription<T> rs) {
        ReplaySubscription<T>[] replaySubscriptionArr;
        ReplaySubscription[] replaySubscriptionArr2;
        do {
            replaySubscriptionArr = this.subscribers.get();
            if (replaySubscriptionArr == TERMINATED || replaySubscriptionArr == EMPTY) {
                return;
            }
            int length = replaySubscriptionArr.length;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    i2 = -1;
                    break;
                } else if (replaySubscriptionArr[i2] == rs) {
                    break;
                } else {
                    i2++;
                }
            }
            if (i2 < 0) {
                return;
            }
            if (length == 1) {
                replaySubscriptionArr2 = EMPTY;
            } else {
                ReplaySubscription[] replaySubscriptionArr3 = new ReplaySubscription[length - 1];
                System.arraycopy(replaySubscriptionArr, 0, replaySubscriptionArr3, 0, i2);
                System.arraycopy(replaySubscriptionArr, i2 + 1, replaySubscriptionArr3, i2, (length - i2) - 1);
                replaySubscriptionArr2 = replaySubscriptionArr3;
            }
        } while (!j.a(this.subscribers, replaySubscriptionArr, replaySubscriptionArr2));
    }

    @CheckReturnValue
    public int size() {
        return this.buffer.size();
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super T> s2) {
        ReplaySubscription<T> replaySubscription = new ReplaySubscription<>(s2, this);
        s2.onSubscribe(replaySubscription);
        if (add(replaySubscription) && replaySubscription.cancelled) {
            remove(replaySubscription);
        } else {
            this.buffer.replay(replaySubscription);
        }
    }

    @CheckReturnValue
    public int subscriberCount() {
        return this.subscribers.get().length;
    }

    @CheckReturnValue
    @NonNull
    public static <T> ReplayProcessor<T> create(int capacityHint) {
        ObjectHelper.verifyPositive(capacityHint, "capacityHint");
        return new ReplayProcessor<>(new UnboundedReplayBuffer(capacityHint));
    }

    @CheckReturnValue
    public T[] getValues(T[] array) {
        return this.buffer.getValues(array);
    }
}
