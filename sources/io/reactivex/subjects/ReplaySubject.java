package io.reactivex.subjects;

import androidx.camera.view.j;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.plugins.RxJavaPlugins;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes8.dex */
public final class ReplaySubject<T> extends Subject<T> {
    final ReplayBuffer<T> buffer;
    boolean done;
    final AtomicReference<ReplayDisposable<T>[]> observers = new AtomicReference<>(EMPTY);
    static final ReplayDisposable[] EMPTY = new ReplayDisposable[0];
    static final ReplayDisposable[] TERMINATED = new ReplayDisposable[0];
    private static final Object[] EMPTY_ARRAY = new Object[0];

    public static final class Node<T> extends AtomicReference<Node<T>> {
        private static final long serialVersionUID = 6404226426336033100L;
        final T value;

        public Node(T t2) {
            this.value = t2;
        }
    }

    public interface ReplayBuffer<T> {
        void add(T t2);

        void addFinal(Object obj);

        boolean compareAndSet(Object obj, Object obj2);

        Object get();

        @Nullable
        T getValue();

        T[] getValues(T[] tArr);

        void replay(ReplayDisposable<T> replayDisposable);

        int size();

        void trimHead();
    }

    public static final class ReplayDisposable<T> extends AtomicInteger implements Disposable {
        private static final long serialVersionUID = 466549804534799122L;
        volatile boolean cancelled;
        final Observer<? super T> downstream;
        Object index;
        final ReplaySubject<T> state;

        public ReplayDisposable(Observer<? super T> observer, ReplaySubject<T> replaySubject) {
            this.downstream = observer;
            this.state = replaySubject;
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
        volatile boolean done;
        volatile TimedNode<Object> head;
        final long maxAge;
        final int maxSize;
        final Scheduler scheduler;
        int size;
        TimedNode<Object> tail;
        final TimeUnit unit;

        public SizeAndTimeBoundReplayBuffer(int i2, long j2, TimeUnit timeUnit, Scheduler scheduler) {
            this.maxSize = ObjectHelper.verifyPositive(i2, "maxSize");
            this.maxAge = ObjectHelper.verifyPositive(j2, "maxAge");
            this.unit = (TimeUnit) ObjectHelper.requireNonNull(timeUnit, "unit is null");
            this.scheduler = (Scheduler) ObjectHelper.requireNonNull(scheduler, "scheduler is null");
            TimedNode<Object> timedNode = new TimedNode<>(null, 0L);
            this.tail = timedNode;
            this.head = timedNode;
        }

        @Override // io.reactivex.subjects.ReplaySubject.ReplayBuffer
        public void add(T t2) {
            TimedNode<Object> timedNode = new TimedNode<>(t2, this.scheduler.now(this.unit));
            TimedNode<Object> timedNode2 = this.tail;
            this.tail = timedNode;
            this.size++;
            timedNode2.set(timedNode);
            trim();
        }

        @Override // io.reactivex.subjects.ReplaySubject.ReplayBuffer
        public void addFinal(Object obj) {
            TimedNode<Object> timedNode = new TimedNode<>(obj, Long.MAX_VALUE);
            TimedNode<Object> timedNode2 = this.tail;
            this.tail = timedNode;
            this.size++;
            timedNode2.lazySet(timedNode);
            trimFinal();
            this.done = true;
        }

        public TimedNode<Object> getHead() {
            TimedNode<Object> timedNode;
            TimedNode<Object> timedNode2 = this.head;
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

        @Override // io.reactivex.subjects.ReplaySubject.ReplayBuffer
        @Nullable
        public T getValue() {
            T t2;
            TimedNode<Object> timedNode = this.head;
            TimedNode<Object> timedNode2 = null;
            while (true) {
                TimedNode<T> timedNode3 = timedNode.get();
                if (timedNode3 == null) {
                    break;
                }
                timedNode2 = timedNode;
                timedNode = timedNode3;
            }
            if (timedNode.time >= this.scheduler.now(this.unit) - this.maxAge && (t2 = (T) timedNode.value) != null) {
                return (NotificationLite.isComplete(t2) || NotificationLite.isError(t2)) ? (T) timedNode2.value : t2;
            }
            return null;
        }

        @Override // io.reactivex.subjects.ReplaySubject.ReplayBuffer
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

        @Override // io.reactivex.subjects.ReplaySubject.ReplayBuffer
        public void replay(ReplayDisposable<T> replayDisposable) {
            if (replayDisposable.getAndIncrement() != 0) {
                return;
            }
            Observer<? super T> observer = replayDisposable.downstream;
            TimedNode<Object> head = (TimedNode) replayDisposable.index;
            if (head == null) {
                head = getHead();
            }
            int iAddAndGet = 1;
            while (!replayDisposable.cancelled) {
                while (!replayDisposable.cancelled) {
                    TimedNode<T> timedNode = head.get();
                    if (timedNode != null) {
                        T t2 = timedNode.value;
                        if (this.done && timedNode.get() == null) {
                            if (NotificationLite.isComplete(t2)) {
                                observer.onComplete();
                            } else {
                                observer.onError(NotificationLite.getError(t2));
                            }
                            replayDisposable.index = null;
                            replayDisposable.cancelled = true;
                            return;
                        }
                        observer.onNext(t2);
                        head = timedNode;
                    } else if (head.get() == null) {
                        replayDisposable.index = head;
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

        @Override // io.reactivex.subjects.ReplaySubject.ReplayBuffer
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
            TimedNode<Object> timedNode = this.head;
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

        public void trimFinal() {
            long jNow = this.scheduler.now(this.unit) - this.maxAge;
            TimedNode<Object> timedNode = this.head;
            while (true) {
                TimedNode<T> timedNode2 = timedNode.get();
                if (timedNode2.get() == null) {
                    if (timedNode.value == null) {
                        this.head = timedNode;
                        return;
                    }
                    TimedNode<Object> timedNode3 = new TimedNode<>(null, 0L);
                    timedNode3.lazySet(timedNode.get());
                    this.head = timedNode3;
                    return;
                }
                if (timedNode2.time > jNow) {
                    if (timedNode.value == null) {
                        this.head = timedNode;
                        return;
                    }
                    TimedNode<Object> timedNode4 = new TimedNode<>(null, 0L);
                    timedNode4.lazySet(timedNode.get());
                    this.head = timedNode4;
                    return;
                }
                timedNode = timedNode2;
            }
        }

        @Override // io.reactivex.subjects.ReplaySubject.ReplayBuffer
        public void trimHead() {
            TimedNode<Object> timedNode = this.head;
            if (timedNode.value != null) {
                TimedNode<Object> timedNode2 = new TimedNode<>(null, 0L);
                timedNode2.lazySet(timedNode.get());
                this.head = timedNode2;
            }
        }

        public int size(TimedNode<Object> timedNode) {
            int i2 = 0;
            while (i2 != Integer.MAX_VALUE) {
                TimedNode<T> timedNode2 = timedNode.get();
                if (timedNode2 == null) {
                    Object obj = timedNode.value;
                    return (NotificationLite.isComplete(obj) || NotificationLite.isError(obj)) ? i2 - 1 : i2;
                }
                i2++;
                timedNode = timedNode2;
            }
            return i2;
        }
    }

    public static final class SizeBoundReplayBuffer<T> extends AtomicReference<Object> implements ReplayBuffer<T> {
        private static final long serialVersionUID = 1107649250281456395L;
        volatile boolean done;
        volatile Node<Object> head;
        final int maxSize;
        int size;
        Node<Object> tail;

        public SizeBoundReplayBuffer(int i2) {
            this.maxSize = ObjectHelper.verifyPositive(i2, "maxSize");
            Node<Object> node = new Node<>(null);
            this.tail = node;
            this.head = node;
        }

        @Override // io.reactivex.subjects.ReplaySubject.ReplayBuffer
        public void add(T t2) {
            Node<Object> node = new Node<>(t2);
            Node<Object> node2 = this.tail;
            this.tail = node;
            this.size++;
            node2.set(node);
            trim();
        }

        @Override // io.reactivex.subjects.ReplaySubject.ReplayBuffer
        public void addFinal(Object obj) {
            Node<Object> node = new Node<>(obj);
            Node<Object> node2 = this.tail;
            this.tail = node;
            this.size++;
            node2.lazySet(node);
            trimHead();
            this.done = true;
        }

        @Override // io.reactivex.subjects.ReplaySubject.ReplayBuffer
        @Nullable
        public T getValue() {
            Node<Object> node = this.head;
            Node<Object> node2 = null;
            while (true) {
                Node<T> node3 = node.get();
                if (node3 == null) {
                    break;
                }
                node2 = node;
                node = node3;
            }
            T t2 = (T) node.value;
            if (t2 == null) {
                return null;
            }
            return (NotificationLite.isComplete(t2) || NotificationLite.isError(t2)) ? (T) node2.value : t2;
        }

        @Override // io.reactivex.subjects.ReplaySubject.ReplayBuffer
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

        @Override // io.reactivex.subjects.ReplaySubject.ReplayBuffer
        public void replay(ReplayDisposable<T> replayDisposable) {
            if (replayDisposable.getAndIncrement() != 0) {
                return;
            }
            Observer<? super T> observer = replayDisposable.downstream;
            Node<Object> node = (Node) replayDisposable.index;
            if (node == null) {
                node = this.head;
            }
            int iAddAndGet = 1;
            while (!replayDisposable.cancelled) {
                Node<T> node2 = node.get();
                if (node2 != null) {
                    T t2 = node2.value;
                    if (this.done && node2.get() == null) {
                        if (NotificationLite.isComplete(t2)) {
                            observer.onComplete();
                        } else {
                            observer.onError(NotificationLite.getError(t2));
                        }
                        replayDisposable.index = null;
                        replayDisposable.cancelled = true;
                        return;
                    }
                    observer.onNext(t2);
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

        @Override // io.reactivex.subjects.ReplaySubject.ReplayBuffer
        public int size() {
            Node<Object> node = this.head;
            int i2 = 0;
            while (i2 != Integer.MAX_VALUE) {
                Node<T> node2 = node.get();
                if (node2 == null) {
                    Object obj = node.value;
                    return (NotificationLite.isComplete(obj) || NotificationLite.isError(obj)) ? i2 - 1 : i2;
                }
                i2++;
                node = node2;
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

        @Override // io.reactivex.subjects.ReplaySubject.ReplayBuffer
        public void trimHead() {
            Node<Object> node = this.head;
            if (node.value != null) {
                Node<Object> node2 = new Node<>(null);
                node2.lazySet(node.get());
                this.head = node2;
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
        final List<Object> buffer;
        volatile boolean done;
        volatile int size;

        public UnboundedReplayBuffer(int i2) {
            this.buffer = new ArrayList(ObjectHelper.verifyPositive(i2, "capacityHint"));
        }

        @Override // io.reactivex.subjects.ReplaySubject.ReplayBuffer
        public void add(T t2) {
            this.buffer.add(t2);
            this.size++;
        }

        @Override // io.reactivex.subjects.ReplaySubject.ReplayBuffer
        public void addFinal(Object obj) {
            this.buffer.add(obj);
            trimHead();
            this.size++;
            this.done = true;
        }

        @Override // io.reactivex.subjects.ReplaySubject.ReplayBuffer
        @Nullable
        public T getValue() {
            int i2 = this.size;
            if (i2 == 0) {
                return null;
            }
            List<Object> list = this.buffer;
            T t2 = (T) list.get(i2 - 1);
            if (!NotificationLite.isComplete(t2) && !NotificationLite.isError(t2)) {
                return t2;
            }
            if (i2 == 1) {
                return null;
            }
            return (T) list.get(i2 - 2);
        }

        @Override // io.reactivex.subjects.ReplaySubject.ReplayBuffer
        public T[] getValues(T[] tArr) {
            int i2 = this.size;
            if (i2 == 0) {
                if (tArr.length != 0) {
                    tArr[0] = null;
                }
                return tArr;
            }
            List<Object> list = this.buffer;
            Object obj = list.get(i2 - 1);
            if ((NotificationLite.isComplete(obj) || NotificationLite.isError(obj)) && i2 - 1 == 0) {
                if (tArr.length != 0) {
                    tArr[0] = null;
                }
                return tArr;
            }
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

        @Override // io.reactivex.subjects.ReplaySubject.ReplayBuffer
        public void replay(ReplayDisposable<T> replayDisposable) {
            int iIntValue;
            int i2;
            if (replayDisposable.getAndIncrement() != 0) {
                return;
            }
            List<Object> list = this.buffer;
            Observer<? super T> observer = replayDisposable.downstream;
            Integer num = (Integer) replayDisposable.index;
            if (num != null) {
                iIntValue = num.intValue();
            } else {
                iIntValue = 0;
                replayDisposable.index = 0;
            }
            int iAddAndGet = 1;
            while (!replayDisposable.cancelled) {
                int i3 = this.size;
                while (i3 != iIntValue) {
                    if (replayDisposable.cancelled) {
                        replayDisposable.index = null;
                        return;
                    }
                    Object obj = list.get(iIntValue);
                    if (this.done && (i2 = iIntValue + 1) == i3 && i2 == (i3 = this.size)) {
                        if (NotificationLite.isComplete(obj)) {
                            observer.onComplete();
                        } else {
                            observer.onError(NotificationLite.getError(obj));
                        }
                        replayDisposable.index = null;
                        replayDisposable.cancelled = true;
                        return;
                    }
                    observer.onNext(obj);
                    iIntValue++;
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

        @Override // io.reactivex.subjects.ReplaySubject.ReplayBuffer
        public int size() {
            int i2 = this.size;
            if (i2 == 0) {
                return 0;
            }
            int i3 = i2 - 1;
            Object obj = this.buffer.get(i3);
            return (NotificationLite.isComplete(obj) || NotificationLite.isError(obj)) ? i3 : i2;
        }

        @Override // io.reactivex.subjects.ReplaySubject.ReplayBuffer
        public void trimHead() {
        }
    }

    public ReplaySubject(ReplayBuffer<T> replayBuffer) {
        this.buffer = replayBuffer;
    }

    @CheckReturnValue
    @NonNull
    public static <T> ReplaySubject<T> create() {
        return new ReplaySubject<>(new UnboundedReplayBuffer(16));
    }

    public static <T> ReplaySubject<T> createUnbounded() {
        return new ReplaySubject<>(new SizeBoundReplayBuffer(Integer.MAX_VALUE));
    }

    @CheckReturnValue
    @NonNull
    public static <T> ReplaySubject<T> createWithSize(int i2) {
        return new ReplaySubject<>(new SizeBoundReplayBuffer(i2));
    }

    @CheckReturnValue
    @NonNull
    public static <T> ReplaySubject<T> createWithTime(long j2, TimeUnit timeUnit, Scheduler scheduler) {
        return new ReplaySubject<>(new SizeAndTimeBoundReplayBuffer(Integer.MAX_VALUE, j2, timeUnit, scheduler));
    }

    @CheckReturnValue
    @NonNull
    public static <T> ReplaySubject<T> createWithTimeAndSize(long j2, TimeUnit timeUnit, Scheduler scheduler, int i2) {
        return new ReplaySubject<>(new SizeAndTimeBoundReplayBuffer(i2, j2, timeUnit, scheduler));
    }

    public boolean add(ReplayDisposable<T> replayDisposable) {
        ReplayDisposable<T>[] replayDisposableArr;
        ReplayDisposable[] replayDisposableArr2;
        do {
            replayDisposableArr = this.observers.get();
            if (replayDisposableArr == TERMINATED) {
                return false;
            }
            int length = replayDisposableArr.length;
            replayDisposableArr2 = new ReplayDisposable[length + 1];
            System.arraycopy(replayDisposableArr, 0, replayDisposableArr2, 0, length);
            replayDisposableArr2[length] = replayDisposable;
        } while (!j.a(this.observers, replayDisposableArr, replayDisposableArr2));
        return true;
    }

    public void cleanupBuffer() {
        this.buffer.trimHead();
    }

    @Override // io.reactivex.subjects.Subject
    @Nullable
    public Throwable getThrowable() {
        Object obj = this.buffer.get();
        if (NotificationLite.isError(obj)) {
            return NotificationLite.getError(obj);
        }
        return null;
    }

    @Nullable
    public T getValue() {
        return this.buffer.getValue();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Object[] getValues() {
        Object[] objArr = EMPTY_ARRAY;
        Object[] values = getValues(objArr);
        return values == objArr ? new Object[0] : values;
    }

    @Override // io.reactivex.subjects.Subject
    public boolean hasComplete() {
        return NotificationLite.isComplete(this.buffer.get());
    }

    @Override // io.reactivex.subjects.Subject
    public boolean hasObservers() {
        return this.observers.get().length != 0;
    }

    @Override // io.reactivex.subjects.Subject
    public boolean hasThrowable() {
        return NotificationLite.isError(this.buffer.get());
    }

    public boolean hasValue() {
        return this.buffer.size() != 0;
    }

    public int observerCount() {
        return this.observers.get().length;
    }

    @Override // io.reactivex.Observer
    public void onComplete() {
        if (this.done) {
            return;
        }
        this.done = true;
        Object objComplete = NotificationLite.complete();
        ReplayBuffer<T> replayBuffer = this.buffer;
        replayBuffer.addFinal(objComplete);
        for (ReplayDisposable<T> replayDisposable : terminate(objComplete)) {
            replayBuffer.replay(replayDisposable);
        }
    }

    @Override // io.reactivex.Observer
    public void onError(Throwable th) {
        ObjectHelper.requireNonNull(th, "onError called with null. Null values are generally not allowed in 2.x operators and sources.");
        if (this.done) {
            RxJavaPlugins.onError(th);
            return;
        }
        this.done = true;
        Object objError = NotificationLite.error(th);
        ReplayBuffer<T> replayBuffer = this.buffer;
        replayBuffer.addFinal(objError);
        for (ReplayDisposable<T> replayDisposable : terminate(objError)) {
            replayBuffer.replay(replayDisposable);
        }
    }

    @Override // io.reactivex.Observer
    public void onNext(T t2) {
        ObjectHelper.requireNonNull(t2, "onNext called with null. Null values are generally not allowed in 2.x operators and sources.");
        if (this.done) {
            return;
        }
        ReplayBuffer<T> replayBuffer = this.buffer;
        replayBuffer.add(t2);
        for (ReplayDisposable<T> replayDisposable : this.observers.get()) {
            replayBuffer.replay(replayDisposable);
        }
    }

    @Override // io.reactivex.Observer
    public void onSubscribe(Disposable disposable) {
        if (this.done) {
            disposable.dispose();
        }
    }

    public void remove(ReplayDisposable<T> replayDisposable) {
        ReplayDisposable<T>[] replayDisposableArr;
        ReplayDisposable[] replayDisposableArr2;
        do {
            replayDisposableArr = this.observers.get();
            if (replayDisposableArr == TERMINATED || replayDisposableArr == EMPTY) {
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

    public ReplayDisposable<T>[] terminate(Object obj) {
        return this.buffer.compareAndSet(null, obj) ? this.observers.getAndSet(TERMINATED) : TERMINATED;
    }

    @CheckReturnValue
    @NonNull
    public static <T> ReplaySubject<T> create(int i2) {
        return new ReplaySubject<>(new UnboundedReplayBuffer(i2));
    }

    public T[] getValues(T[] tArr) {
        return this.buffer.getValues(tArr);
    }
}
