package com.jakewharton.rxrelay2;

import androidx.camera.view.j;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes4.dex */
public final class ReplayRelay<T> extends Relay<T> {
    static final ReplayDisposable[] EMPTY = new ReplayDisposable[0];
    private static final Object[] EMPTY_ARRAY = new Object[0];
    final ReplayBuffer<T> buffer;
    final AtomicReference<ReplayDisposable<T>[]> observers = new AtomicReference<>(EMPTY);

    public static final class Node<T> extends AtomicReference<Node<T>> {
        private static final long serialVersionUID = 6404226426336033100L;
        final T value;

        public Node(T t2) {
            this.value = t2;
        }
    }

    public interface ReplayBuffer<T> {
        void add(T t2);

        T getValue();

        T[] getValues(T[] tArr);

        void replay(ReplayDisposable<T> replayDisposable);

        int size();
    }

    public static final class ReplayDisposable<T> extends AtomicInteger implements Disposable {
        private static final long serialVersionUID = 466549804534799122L;
        final Observer<? super T> actual;
        volatile boolean cancelled;
        Object index;
        final ReplayRelay<T> state;

        public ReplayDisposable(Observer<? super T> observer, ReplayRelay<T> replayRelay) {
            this.actual = observer;
            this.state = replayRelay;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            this.state.remove(this);
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled;
        }
    }

    public static final class SizeAndTimeBoundReplayBuffer<T> extends AtomicReference<Object> implements ReplayBuffer<T> {
        private static final long serialVersionUID = -8056260896137901749L;
        volatile TimedNode<T> head;
        final long maxAge;
        final int maxSize;
        final Scheduler scheduler;
        int size;
        TimedNode<T> tail;
        final TimeUnit unit;

        public SizeAndTimeBoundReplayBuffer(int i2, long j2, TimeUnit timeUnit, Scheduler scheduler) {
            if (i2 <= 0) {
                throw new IllegalArgumentException("maxSize > 0 required but it was " + i2);
            }
            if (j2 <= 0) {
                throw new IllegalArgumentException("maxAge > 0 required but it was " + j2);
            }
            if (timeUnit == null) {
                throw new NullPointerException("unit == null");
            }
            if (scheduler == null) {
                throw new NullPointerException("scheduler == null");
            }
            this.maxSize = i2;
            this.maxAge = j2;
            this.unit = timeUnit;
            this.scheduler = scheduler;
            TimedNode<T> timedNode = new TimedNode<>(null, 0L);
            this.tail = timedNode;
            this.head = timedNode;
        }

        @Override // com.jakewharton.rxrelay2.ReplayRelay.ReplayBuffer
        public void add(T t2) {
            TimedNode<T> timedNode = new TimedNode<>(t2, this.scheduler.now(this.unit));
            TimedNode<T> timedNode2 = this.tail;
            this.tail = timedNode;
            this.size++;
            timedNode2.set(timedNode);
            trim();
        }

        @Override // com.jakewharton.rxrelay2.ReplayRelay.ReplayBuffer
        public T getValue() {
            TimedNode<T> timedNode = this.head;
            while (true) {
                TimedNode<T> timedNode2 = timedNode.get();
                if (timedNode2 == null) {
                    return timedNode.value;
                }
                timedNode = timedNode2;
            }
        }

        @Override // com.jakewharton.rxrelay2.ReplayRelay.ReplayBuffer
        public T[] getValues(T[] tArr) {
            TimedNode<T> timedNode = this.head;
            int size = size();
            if (size != 0) {
                if (tArr.length < size) {
                    tArr = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), size));
                }
                for (int i2 = 0; i2 != size; i2++) {
                    timedNode = timedNode.get();
                    tArr[i2] = timedNode.value;
                }
                if (tArr.length > size) {
                    tArr[size] = null;
                }
            } else if (tArr.length != 0) {
                tArr[0] = null;
            }
            return tArr;
        }

        @Override // com.jakewharton.rxrelay2.ReplayRelay.ReplayBuffer
        public void replay(ReplayDisposable<T> replayDisposable) {
            if (replayDisposable.getAndIncrement() != 0) {
                return;
            }
            Observer<? super T> observer = replayDisposable.actual;
            TimedNode<T> timedNode = (TimedNode) replayDisposable.index;
            int iAddAndGet = 1;
            if (timedNode == null) {
                timedNode = this.head;
                long jNow = this.scheduler.now(this.unit) - this.maxAge;
                TimedNode<T> timedNode2 = timedNode.get();
                while (timedNode2 != null && timedNode2.time <= jNow) {
                    TimedNode<T> timedNode3 = timedNode2;
                    timedNode2 = timedNode2.get();
                    timedNode = timedNode3;
                }
            }
            while (!replayDisposable.cancelled) {
                while (!replayDisposable.cancelled) {
                    TimedNode<T> timedNode4 = timedNode.get();
                    if (timedNode4 != null) {
                        observer.onNext(timedNode4.value);
                        timedNode = timedNode4;
                    } else if (timedNode.get() == null) {
                        replayDisposable.index = timedNode;
                        iAddAndGet = replayDisposable.addAndGet(-iAddAndGet);
                        if (iAddAndGet == 0) {
                            return;
                        }
                    }
                }
                replayDisposable.index = null;
                return;
            }
            replayDisposable.index = null;
        }

        @Override // com.jakewharton.rxrelay2.ReplayRelay.ReplayBuffer
        public int size() {
            TimedNode<T> timedNode = this.head;
            int i2 = 0;
            while (i2 != Integer.MAX_VALUE && (timedNode = timedNode.get()) != null) {
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
            long jNow = this.scheduler.now(this.unit) - this.maxAge;
            TimedNode<T> timedNode = this.head;
            while (true) {
                TimedNode<T> timedNode2 = timedNode.get();
                if (timedNode2 == null) {
                    this.head = timedNode;
                    return;
                } else {
                    if (timedNode2.time > jNow) {
                        this.head = timedNode;
                        return;
                    }
                    timedNode = timedNode2;
                }
            }
        }
    }

    public static final class SizeBoundReplayBuffer<T> extends AtomicReference<Object> implements ReplayBuffer<T> {
        private static final long serialVersionUID = 1107649250281456395L;
        volatile Node<T> head;
        final int maxSize;
        int size;
        Node<T> tail;

        public SizeBoundReplayBuffer(int i2) {
            if (i2 <= 0) {
                throw new IllegalArgumentException("maxSize > 0 required but it was " + i2);
            }
            this.maxSize = i2;
            Node<T> node = new Node<>(null);
            this.tail = node;
            this.head = node;
        }

        @Override // com.jakewharton.rxrelay2.ReplayRelay.ReplayBuffer
        public void add(T t2) {
            Node<T> node = new Node<>(t2);
            Node<T> node2 = this.tail;
            this.tail = node;
            this.size++;
            node2.set(node);
            trim();
        }

        @Override // com.jakewharton.rxrelay2.ReplayRelay.ReplayBuffer
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

        @Override // com.jakewharton.rxrelay2.ReplayRelay.ReplayBuffer
        public T[] getValues(T[] tArr) {
            Node<T> node = this.head;
            int size = size();
            if (size != 0) {
                if (tArr.length < size) {
                    tArr = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), size));
                }
                for (int i2 = 0; i2 != size; i2++) {
                    node = node.get();
                    tArr[i2] = node.value;
                }
                if (tArr.length > size) {
                    tArr[size] = null;
                }
            } else if (tArr.length != 0) {
                tArr[0] = null;
            }
            return tArr;
        }

        @Override // com.jakewharton.rxrelay2.ReplayRelay.ReplayBuffer
        public void replay(ReplayDisposable<T> replayDisposable) {
            if (replayDisposable.getAndIncrement() != 0) {
                return;
            }
            Observer<? super T> observer = replayDisposable.actual;
            Node<T> node = (Node) replayDisposable.index;
            int iAddAndGet = 1;
            if (node == null) {
                node = this.head;
            }
            while (!replayDisposable.cancelled) {
                Node<T> node2 = node.get();
                if (node2 != null) {
                    observer.onNext(node2.value);
                    node = node2;
                } else if (node.get() != null) {
                    continue;
                } else {
                    replayDisposable.index = node;
                    iAddAndGet = replayDisposable.addAndGet(-iAddAndGet);
                    if (iAddAndGet == 0) {
                        return;
                    }
                }
            }
            replayDisposable.index = null;
        }

        @Override // com.jakewharton.rxrelay2.ReplayRelay.ReplayBuffer
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
    }

    public static final class TimedNode<T> extends AtomicReference<TimedNode<T>> {
        private static final long serialVersionUID = 6404226426336033100L;
        final long time;
        final T value;

        public TimedNode(T t2, long j2) {
            this.value = t2;
            this.time = j2;
        }
    }

    public static final class UnboundedReplayBuffer<T> extends AtomicReference<Object> implements ReplayBuffer<T> {
        private static final long serialVersionUID = -733876083048047795L;
        final List<T> buffer;
        volatile int size;

        public UnboundedReplayBuffer(int i2) {
            if (i2 <= 0) {
                throw new IllegalArgumentException("capacityHint <= 0");
            }
            this.buffer = new ArrayList(i2);
        }

        @Override // com.jakewharton.rxrelay2.ReplayRelay.ReplayBuffer
        public void add(T t2) {
            this.buffer.add(t2);
            this.size++;
        }

        @Override // com.jakewharton.rxrelay2.ReplayRelay.ReplayBuffer
        public T getValue() {
            int i2 = this.size;
            if (i2 != 0) {
                return this.buffer.get(i2 - 1);
            }
            return null;
        }

        @Override // com.jakewharton.rxrelay2.ReplayRelay.ReplayBuffer
        public T[] getValues(T[] tArr) {
            int i2 = this.size;
            if (i2 == 0) {
                if (tArr.length != 0) {
                    tArr[0] = null;
                }
                return tArr;
            }
            if (tArr.length < i2) {
                tArr = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), i2));
            }
            List<T> list = this.buffer;
            for (int i3 = 0; i3 < i2; i3++) {
                tArr[i3] = list.get(i3);
            }
            if (tArr.length > i2) {
                tArr[i2] = null;
            }
            return tArr;
        }

        @Override // com.jakewharton.rxrelay2.ReplayRelay.ReplayBuffer
        public void replay(ReplayDisposable<T> replayDisposable) {
            int iIntValue;
            if (replayDisposable.getAndIncrement() != 0) {
                return;
            }
            List<T> list = this.buffer;
            Observer<? super T> observer = replayDisposable.actual;
            Integer num = (Integer) replayDisposable.index;
            int iAddAndGet = 1;
            if (num != null) {
                iIntValue = num.intValue();
            } else {
                iIntValue = 0;
                replayDisposable.index = 0;
            }
            while (!replayDisposable.cancelled) {
                int i2 = this.size;
                while (i2 != iIntValue) {
                    if (replayDisposable.cancelled) {
                        replayDisposable.index = null;
                        return;
                    } else {
                        observer.onNext(list.get(iIntValue));
                        iIntValue++;
                    }
                }
                if (iIntValue == this.size) {
                    replayDisposable.index = Integer.valueOf(iIntValue);
                    iAddAndGet = replayDisposable.addAndGet(-iAddAndGet);
                    if (iAddAndGet == 0) {
                        return;
                    }
                }
            }
            replayDisposable.index = null;
        }

        @Override // com.jakewharton.rxrelay2.ReplayRelay.ReplayBuffer
        public int size() {
            int i2 = this.size;
            if (i2 != 0) {
                return i2;
            }
            return 0;
        }
    }

    public ReplayRelay(ReplayBuffer<T> replayBuffer) {
        this.buffer = replayBuffer;
    }

    public static <T> ReplayRelay<T> create() {
        return new ReplayRelay<>(new UnboundedReplayBuffer(16));
    }

    public static <T> ReplayRelay<T> createUnbounded() {
        return new ReplayRelay<>(new SizeBoundReplayBuffer(Integer.MAX_VALUE));
    }

    public static <T> ReplayRelay<T> createWithSize(int i2) {
        return new ReplayRelay<>(new SizeBoundReplayBuffer(i2));
    }

    public static <T> ReplayRelay<T> createWithTime(long j2, TimeUnit timeUnit, Scheduler scheduler) {
        return new ReplayRelay<>(new SizeAndTimeBoundReplayBuffer(Integer.MAX_VALUE, j2, timeUnit, scheduler));
    }

    public static <T> ReplayRelay<T> createWithTimeAndSize(long j2, TimeUnit timeUnit, Scheduler scheduler, int i2) {
        return new ReplayRelay<>(new SizeAndTimeBoundReplayBuffer(i2, j2, timeUnit, scheduler));
    }

    @Override // com.jakewharton.rxrelay2.Relay, io.reactivex.functions.Consumer
    public void accept(T t2) {
        if (t2 == null) {
            throw new NullPointerException("value == null");
        }
        ReplayBuffer<T> replayBuffer = this.buffer;
        replayBuffer.add(t2);
        for (ReplayDisposable<T> replayDisposable : this.observers.get()) {
            replayBuffer.replay(replayDisposable);
        }
    }

    public boolean add(ReplayDisposable<T> replayDisposable) {
        ReplayDisposable<T>[] replayDisposableArr;
        ReplayDisposable[] replayDisposableArr2;
        do {
            replayDisposableArr = this.observers.get();
            int length = replayDisposableArr.length;
            replayDisposableArr2 = new ReplayDisposable[length + 1];
            System.arraycopy(replayDisposableArr, 0, replayDisposableArr2, 0, length);
            replayDisposableArr2[length] = replayDisposable;
        } while (!j.a(this.observers, replayDisposableArr, replayDisposableArr2));
        return true;
    }

    public T getValue() {
        return this.buffer.getValue();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Object[] getValues() {
        Object[] objArr = EMPTY_ARRAY;
        Object[] values = getValues(objArr);
        return values == objArr ? new Object[0] : values;
    }

    @Override // com.jakewharton.rxrelay2.Relay
    public boolean hasObservers() {
        return this.observers.get().length != 0;
    }

    public boolean hasValue() {
        return this.buffer.size() != 0;
    }

    public int observerCount() {
        return this.observers.get().length;
    }

    public void remove(ReplayDisposable<T> replayDisposable) {
        ReplayDisposable<T>[] replayDisposableArr;
        ReplayDisposable[] replayDisposableArr2;
        do {
            replayDisposableArr = this.observers.get();
            if (replayDisposableArr == EMPTY) {
                return;
            }
            int length = replayDisposableArr.length;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    i2 = -1;
                    break;
                } else if (replayDisposableArr[i2] == replayDisposable) {
                    break;
                } else {
                    i2++;
                }
            }
            if (i2 < 0) {
                return;
            }
            if (length == 1) {
                replayDisposableArr2 = EMPTY;
            } else {
                ReplayDisposable[] replayDisposableArr3 = new ReplayDisposable[length - 1];
                System.arraycopy(replayDisposableArr, 0, replayDisposableArr3, 0, i2);
                System.arraycopy(replayDisposableArr, i2 + 1, replayDisposableArr3, i2, (length - i2) - 1);
                replayDisposableArr2 = replayDisposableArr3;
            }
        } while (!j.a(this.observers, replayDisposableArr, replayDisposableArr2));
    }

    public int size() {
        return this.buffer.size();
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super T> observer) {
        ReplayDisposable<T> replayDisposable = new ReplayDisposable<>(observer, this);
        observer.onSubscribe(replayDisposable);
        if (replayDisposable.cancelled) {
            return;
        }
        if (add(replayDisposable) && replayDisposable.cancelled) {
            remove(replayDisposable);
        } else {
            this.buffer.replay(replayDisposable);
        }
    }

    public static <T> ReplayRelay<T> create(int i2) {
        return new ReplayRelay<>(new UnboundedReplayBuffer(i2));
    }

    public T[] getValues(T[] tArr) {
        return this.buffer.getValues(tArr);
    }
}
