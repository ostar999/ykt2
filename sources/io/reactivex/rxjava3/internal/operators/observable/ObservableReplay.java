package io.reactivex.rxjava3.internal.operators.observable;

import androidx.camera.view.j;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.internal.fuseable.HasUpstreamObservableSource;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.internal.util.NotificationLite;
import io.reactivex.rxjava3.observables.ConnectableObservable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.schedulers.Timed;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes8.dex */
public final class ObservableReplay<T> extends ConnectableObservable<T> implements HasUpstreamObservableSource<T> {
    static final BufferSupplier DEFAULT_UNBOUNDED_FACTORY = new UnBoundedFactory();
    final BufferSupplier<T> bufferFactory;
    final AtomicReference<ReplayObserver<T>> current;
    final ObservableSource<T> onSubscribe;
    final ObservableSource<T> source;

    public static abstract class BoundedReplayBuffer<T> extends AtomicReference<Node> implements ReplayBuffer<T> {
        private static final long serialVersionUID = 2346567790059478686L;
        final boolean eagerTruncate;
        int size;
        Node tail;

        public BoundedReplayBuffer(boolean eagerTruncate) {
            this.eagerTruncate = eagerTruncate;
            Node node = new Node(null);
            this.tail = node;
            set(node);
        }

        public final void addLast(Node n2) {
            this.tail.set(n2);
            this.tail = n2;
            this.size++;
        }

        public final void collect(Collection<? super T> collection) {
            Node head = getHead();
            while (true) {
                head = head.get();
                if (head == null) {
                    return;
                }
                Object objLeaveTransform = leaveTransform(head.value);
                if (NotificationLite.isComplete(objLeaveTransform) || NotificationLite.isError(objLeaveTransform)) {
                    return;
                } else {
                    collection.add((Object) NotificationLite.getValue(objLeaveTransform));
                }
            }
        }

        @Override // io.reactivex.rxjava3.internal.operators.observable.ObservableReplay.ReplayBuffer
        public final void complete() {
            addLast(new Node(enterTransform(NotificationLite.complete())));
            truncateFinal();
        }

        public Object enterTransform(Object value) {
            return value;
        }

        @Override // io.reactivex.rxjava3.internal.operators.observable.ObservableReplay.ReplayBuffer
        public final void error(Throwable e2) {
            addLast(new Node(enterTransform(NotificationLite.error(e2))));
            truncateFinal();
        }

        public Node getHead() {
            return get();
        }

        public boolean hasCompleted() {
            Object obj = this.tail.value;
            return obj != null && NotificationLite.isComplete(leaveTransform(obj));
        }

        public boolean hasError() {
            Object obj = this.tail.value;
            return obj != null && NotificationLite.isError(leaveTransform(obj));
        }

        public Object leaveTransform(Object value) {
            return value;
        }

        @Override // io.reactivex.rxjava3.internal.operators.observable.ObservableReplay.ReplayBuffer
        public final void next(T value) {
            addLast(new Node(enterTransform(NotificationLite.next(value))));
            truncate();
        }

        public final void removeFirst() {
            this.size--;
            setFirst(get().get());
        }

        public final void removeSome(int n2) {
            Node node = get();
            while (n2 > 0) {
                node = node.get();
                n2--;
                this.size--;
            }
            setFirst(node);
            Node node2 = get();
            if (node2.get() == null) {
                this.tail = node2;
            }
        }

        @Override // io.reactivex.rxjava3.internal.operators.observable.ObservableReplay.ReplayBuffer
        public final void replay(InnerDisposable<T> output) {
            if (output.getAndIncrement() != 0) {
                return;
            }
            int iAddAndGet = 1;
            do {
                Node head = (Node) output.index();
                if (head == null) {
                    head = getHead();
                    output.index = head;
                }
                while (!output.isDisposed()) {
                    Node node = head.get();
                    if (node == null) {
                        output.index = head;
                        iAddAndGet = output.addAndGet(-iAddAndGet);
                    } else {
                        if (NotificationLite.accept(leaveTransform(node.value), output.child)) {
                            output.index = null;
                            return;
                        }
                        head = node;
                    }
                }
                output.index = null;
                return;
            } while (iAddAndGet != 0);
        }

        public final void setFirst(Node n2) {
            if (this.eagerTruncate) {
                Node node = new Node(null);
                node.lazySet(n2.get());
                n2 = node;
            }
            set(n2);
        }

        public final void trimHead() {
            Node node = get();
            if (node.value != null) {
                Node node2 = new Node(null);
                node2.lazySet(node.get());
                set(node2);
            }
        }

        public abstract void truncate();

        public void truncateFinal() {
            trimHead();
        }
    }

    public interface BufferSupplier<T> {
        ReplayBuffer<T> call();
    }

    public static final class DisposeConsumer<R> implements Consumer<Disposable> {
        private final ObserverResourceWrapper<R> srw;

        public DisposeConsumer(ObserverResourceWrapper<R> srw) {
            this.srw = srw;
        }

        @Override // io.reactivex.rxjava3.functions.Consumer
        public void accept(Disposable r2) {
            this.srw.setResource(r2);
        }
    }

    public static final class InnerDisposable<T> extends AtomicInteger implements Disposable {
        private static final long serialVersionUID = 2728361546769921047L;
        volatile boolean cancelled;
        final Observer<? super T> child;
        Object index;
        final ReplayObserver<T> parent;

        public InnerDisposable(ReplayObserver<T> parent, Observer<? super T> child) {
            this.parent = parent;
            this.child = child;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            this.parent.remove(this);
            this.index = null;
        }

        public <U> U index() {
            return (U) this.index;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled;
        }
    }

    public static final class MulticastReplay<R, U> extends Observable<R> {
        private final Supplier<? extends ConnectableObservable<U>> connectableFactory;
        private final Function<? super Observable<U>, ? extends ObservableSource<R>> selector;

        public MulticastReplay(Supplier<? extends ConnectableObservable<U>> connectableFactory, Function<? super Observable<U>, ? extends ObservableSource<R>> selector) {
            this.connectableFactory = connectableFactory;
            this.selector = selector;
        }

        @Override // io.reactivex.rxjava3.core.Observable
        public void subscribeActual(Observer<? super R> observer) {
            try {
                ConnectableObservable<U> connectableObservable = this.connectableFactory.get();
                Objects.requireNonNull(connectableObservable, "The connectableFactory returned a null ConnectableObservable");
                ConnectableObservable<U> connectableObservable2 = connectableObservable;
                ObservableSource<R> observableSourceApply = this.selector.apply(connectableObservable2);
                Objects.requireNonNull(observableSourceApply, "The selector returned a null ObservableSource");
                ObservableSource<R> observableSource = observableSourceApply;
                ObserverResourceWrapper observerResourceWrapper = new ObserverResourceWrapper(observer);
                observableSource.subscribe(observerResourceWrapper);
                connectableObservable2.connect(new DisposeConsumer(observerResourceWrapper));
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                EmptyDisposable.error(th, observer);
            }
        }
    }

    public static final class Node extends AtomicReference<Node> {
        private static final long serialVersionUID = 245354315435971818L;
        final Object value;

        public Node(Object value) {
            this.value = value;
        }
    }

    public interface ReplayBuffer<T> {
        void complete();

        void error(Throwable e2);

        void next(T value);

        void replay(InnerDisposable<T> output);
    }

    public static final class ReplayBufferSupplier<T> implements BufferSupplier<T> {
        final int bufferSize;
        final boolean eagerTruncate;

        public ReplayBufferSupplier(int bufferSize, boolean eagerTruncate) {
            this.bufferSize = bufferSize;
            this.eagerTruncate = eagerTruncate;
        }

        @Override // io.reactivex.rxjava3.internal.operators.observable.ObservableReplay.BufferSupplier
        public ReplayBuffer<T> call() {
            return new SizeBoundReplayBuffer(this.bufferSize, this.eagerTruncate);
        }
    }

    public static final class ReplayObserver<T> extends AtomicReference<Disposable> implements Observer<T>, Disposable {
        static final InnerDisposable[] EMPTY = new InnerDisposable[0];
        static final InnerDisposable[] TERMINATED = new InnerDisposable[0];
        private static final long serialVersionUID = -533785617179540163L;
        final ReplayBuffer<T> buffer;
        final AtomicReference<ReplayObserver<T>> current;
        boolean done;
        final AtomicReference<InnerDisposable[]> observers = new AtomicReference<>(EMPTY);
        final AtomicBoolean shouldConnect = new AtomicBoolean();

        public ReplayObserver(ReplayBuffer<T> buffer, AtomicReference<ReplayObserver<T>> current) {
            this.buffer = buffer;
            this.current = current;
        }

        public boolean add(InnerDisposable<T> producer) {
            InnerDisposable[] innerDisposableArr;
            InnerDisposable[] innerDisposableArr2;
            do {
                innerDisposableArr = this.observers.get();
                if (innerDisposableArr == TERMINATED) {
                    return false;
                }
                int length = innerDisposableArr.length;
                innerDisposableArr2 = new InnerDisposable[length + 1];
                System.arraycopy(innerDisposableArr, 0, innerDisposableArr2, 0, length);
                innerDisposableArr2[length] = producer;
            } while (!j.a(this.observers, innerDisposableArr, innerDisposableArr2));
            return true;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.observers.set(TERMINATED);
            j.a(this.current, this, null);
            DisposableHelper.dispose(this);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.observers.get() == TERMINATED;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.buffer.complete();
            replayFinal();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable e2) {
            if (this.done) {
                RxJavaPlugins.onError(e2);
                return;
            }
            this.done = true;
            this.buffer.error(e2);
            replayFinal();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t2) {
            if (this.done) {
                return;
            }
            this.buffer.next(t2);
            replay();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable p2) {
            if (DisposableHelper.setOnce(this, p2)) {
                replay();
            }
        }

        public void remove(InnerDisposable<T> producer) {
            InnerDisposable[] innerDisposableArr;
            InnerDisposable[] innerDisposableArr2;
            do {
                innerDisposableArr = this.observers.get();
                int length = innerDisposableArr.length;
                if (length == 0) {
                    return;
                }
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        i2 = -1;
                        break;
                    } else if (innerDisposableArr[i2].equals(producer)) {
                        break;
                    } else {
                        i2++;
                    }
                }
                if (i2 < 0) {
                    return;
                }
                if (length == 1) {
                    innerDisposableArr2 = EMPTY;
                } else {
                    InnerDisposable[] innerDisposableArr3 = new InnerDisposable[length - 1];
                    System.arraycopy(innerDisposableArr, 0, innerDisposableArr3, 0, i2);
                    System.arraycopy(innerDisposableArr, i2 + 1, innerDisposableArr3, i2, (length - i2) - 1);
                    innerDisposableArr2 = innerDisposableArr3;
                }
            } while (!j.a(this.observers, innerDisposableArr, innerDisposableArr2));
        }

        public void replay() {
            for (InnerDisposable<T> innerDisposable : this.observers.get()) {
                this.buffer.replay(innerDisposable);
            }
        }

        public void replayFinal() {
            for (InnerDisposable<T> innerDisposable : this.observers.getAndSet(TERMINATED)) {
                this.buffer.replay(innerDisposable);
            }
        }
    }

    public static final class ReplaySource<T> implements ObservableSource<T> {
        private final BufferSupplier<T> bufferFactory;
        private final AtomicReference<ReplayObserver<T>> curr;

        public ReplaySource(AtomicReference<ReplayObserver<T>> curr, BufferSupplier<T> bufferFactory) {
            this.curr = curr;
            this.bufferFactory = bufferFactory;
        }

        @Override // io.reactivex.rxjava3.core.ObservableSource
        public void subscribe(Observer<? super T> child) {
            ReplayObserver<T> replayObserver;
            while (true) {
                replayObserver = this.curr.get();
                if (replayObserver != null) {
                    break;
                }
                ReplayObserver<T> replayObserver2 = new ReplayObserver<>(this.bufferFactory.call(), this.curr);
                if (j.a(this.curr, null, replayObserver2)) {
                    replayObserver = replayObserver2;
                    break;
                }
            }
            InnerDisposable<T> innerDisposable = new InnerDisposable<>(replayObserver, child);
            child.onSubscribe(innerDisposable);
            replayObserver.add(innerDisposable);
            if (innerDisposable.isDisposed()) {
                replayObserver.remove(innerDisposable);
            } else {
                replayObserver.buffer.replay(innerDisposable);
            }
        }
    }

    public static final class ScheduledReplaySupplier<T> implements BufferSupplier<T> {
        private final int bufferSize;
        final boolean eagerTruncate;
        private final long maxAge;
        private final Scheduler scheduler;
        private final TimeUnit unit;

        public ScheduledReplaySupplier(int bufferSize, long maxAge, TimeUnit unit, Scheduler scheduler, boolean eagerTruncate) {
            this.bufferSize = bufferSize;
            this.maxAge = maxAge;
            this.unit = unit;
            this.scheduler = scheduler;
            this.eagerTruncate = eagerTruncate;
        }

        @Override // io.reactivex.rxjava3.internal.operators.observable.ObservableReplay.BufferSupplier
        public ReplayBuffer<T> call() {
            return new SizeAndTimeBoundReplayBuffer(this.bufferSize, this.maxAge, this.unit, this.scheduler, this.eagerTruncate);
        }
    }

    public static final class SizeAndTimeBoundReplayBuffer<T> extends BoundedReplayBuffer<T> {
        private static final long serialVersionUID = 3457957419649567404L;
        final int limit;
        final long maxAge;
        final Scheduler scheduler;
        final TimeUnit unit;

        public SizeAndTimeBoundReplayBuffer(int limit, long maxAge, TimeUnit unit, Scheduler scheduler, boolean eagerTruncate) {
            super(eagerTruncate);
            this.scheduler = scheduler;
            this.limit = limit;
            this.maxAge = maxAge;
            this.unit = unit;
        }

        @Override // io.reactivex.rxjava3.internal.operators.observable.ObservableReplay.BoundedReplayBuffer
        public Object enterTransform(Object value) {
            return new Timed(value, this.scheduler.now(this.unit), this.unit);
        }

        @Override // io.reactivex.rxjava3.internal.operators.observable.ObservableReplay.BoundedReplayBuffer
        public Node getHead() {
            Node node;
            long jNow = this.scheduler.now(this.unit) - this.maxAge;
            Node node2 = get();
            Node node3 = node2.get();
            while (true) {
                Node node4 = node3;
                node = node2;
                node2 = node4;
                if (node2 == null) {
                    break;
                }
                Timed timed = (Timed) node2.value;
                if (NotificationLite.isComplete(timed.value()) || NotificationLite.isError(timed.value()) || timed.time() > jNow) {
                    break;
                }
                node3 = node2.get();
            }
            return node;
        }

        @Override // io.reactivex.rxjava3.internal.operators.observable.ObservableReplay.BoundedReplayBuffer
        public Object leaveTransform(Object value) {
            return ((Timed) value).value();
        }

        @Override // io.reactivex.rxjava3.internal.operators.observable.ObservableReplay.BoundedReplayBuffer
        public void truncate() {
            Node node;
            long jNow = this.scheduler.now(this.unit) - this.maxAge;
            Node node2 = get();
            Node node3 = node2.get();
            int i2 = 0;
            while (true) {
                Node node4 = node3;
                node = node2;
                node2 = node4;
                int i3 = this.size;
                if (i3 > 1) {
                    if (i3 <= this.limit) {
                        if (((Timed) node2.value).time() > jNow) {
                            break;
                        }
                        i2++;
                        this.size--;
                        node3 = node2.get();
                    } else {
                        i2++;
                        this.size = i3 - 1;
                        node3 = node2.get();
                    }
                } else {
                    break;
                }
            }
            if (i2 != 0) {
                setFirst(node);
            }
        }

        @Override // io.reactivex.rxjava3.internal.operators.observable.ObservableReplay.BoundedReplayBuffer
        public void truncateFinal() {
            Node node;
            long jNow = this.scheduler.now(this.unit) - this.maxAge;
            Node node2 = get();
            Node node3 = node2.get();
            int i2 = 0;
            while (true) {
                Node node4 = node3;
                node = node2;
                node2 = node4;
                if (this.size <= 1 || ((Timed) node2.value).time() > jNow) {
                    break;
                }
                i2++;
                this.size--;
                node3 = node2.get();
            }
            if (i2 != 0) {
                setFirst(node);
            }
        }
    }

    public static final class SizeBoundReplayBuffer<T> extends BoundedReplayBuffer<T> {
        private static final long serialVersionUID = -5898283885385201806L;
        final int limit;

        public SizeBoundReplayBuffer(int limit, boolean eagerTruncate) {
            super(eagerTruncate);
            this.limit = limit;
        }

        @Override // io.reactivex.rxjava3.internal.operators.observable.ObservableReplay.BoundedReplayBuffer
        public void truncate() {
            if (this.size > this.limit) {
                removeFirst();
            }
        }
    }

    public static final class UnBoundedFactory implements BufferSupplier<Object> {
        @Override // io.reactivex.rxjava3.internal.operators.observable.ObservableReplay.BufferSupplier
        public ReplayBuffer<Object> call() {
            return new UnboundedReplayBuffer(16);
        }
    }

    public static final class UnboundedReplayBuffer<T> extends ArrayList<Object> implements ReplayBuffer<T> {
        private static final long serialVersionUID = 7063189396499112664L;
        volatile int size;

        public UnboundedReplayBuffer(int capacityHint) {
            super(capacityHint);
        }

        @Override // io.reactivex.rxjava3.internal.operators.observable.ObservableReplay.ReplayBuffer
        public void complete() {
            add(NotificationLite.complete());
            this.size++;
        }

        @Override // io.reactivex.rxjava3.internal.operators.observable.ObservableReplay.ReplayBuffer
        public void error(Throwable e2) {
            add(NotificationLite.error(e2));
            this.size++;
        }

        @Override // io.reactivex.rxjava3.internal.operators.observable.ObservableReplay.ReplayBuffer
        public void next(T value) {
            add(NotificationLite.next(value));
            this.size++;
        }

        @Override // io.reactivex.rxjava3.internal.operators.observable.ObservableReplay.ReplayBuffer
        public void replay(InnerDisposable<T> output) {
            if (output.getAndIncrement() != 0) {
                return;
            }
            Observer<? super T> observer = output.child;
            int iAddAndGet = 1;
            while (!output.isDisposed()) {
                int i2 = this.size;
                Integer num = (Integer) output.index();
                int iIntValue = num != null ? num.intValue() : 0;
                while (iIntValue < i2) {
                    if (NotificationLite.accept(get(iIntValue), observer) || output.isDisposed()) {
                        return;
                    } else {
                        iIntValue++;
                    }
                }
                output.index = Integer.valueOf(iIntValue);
                iAddAndGet = output.addAndGet(-iAddAndGet);
                if (iAddAndGet == 0) {
                    return;
                }
            }
        }
    }

    private ObservableReplay(ObservableSource<T> onSubscribe, ObservableSource<T> source, final AtomicReference<ReplayObserver<T>> current, final BufferSupplier<T> bufferFactory) {
        this.onSubscribe = onSubscribe;
        this.source = source;
        this.current = current;
        this.bufferFactory = bufferFactory;
    }

    public static <T> ConnectableObservable<T> create(ObservableSource<T> source, final int bufferSize, boolean eagerTruncate) {
        return bufferSize == Integer.MAX_VALUE ? createFrom(source) : create(source, new ReplayBufferSupplier(bufferSize, eagerTruncate));
    }

    public static <T> ConnectableObservable<T> createFrom(ObservableSource<? extends T> source) {
        return create(source, DEFAULT_UNBOUNDED_FACTORY);
    }

    public static <U, R> Observable<R> multicastSelector(final Supplier<? extends ConnectableObservable<U>> connectableFactory, final Function<? super Observable<U>, ? extends ObservableSource<R>> selector) {
        return RxJavaPlugins.onAssembly(new MulticastReplay(connectableFactory, selector));
    }

    @Override // io.reactivex.rxjava3.observables.ConnectableObservable
    public void connect(Consumer<? super Disposable> connection) {
        ReplayObserver<T> replayObserver;
        while (true) {
            replayObserver = this.current.get();
            if (replayObserver != null && !replayObserver.isDisposed()) {
                break;
            }
            ReplayObserver<T> replayObserver2 = new ReplayObserver<>(this.bufferFactory.call(), this.current);
            if (j.a(this.current, replayObserver, replayObserver2)) {
                replayObserver = replayObserver2;
                break;
            }
        }
        boolean z2 = !replayObserver.shouldConnect.get() && replayObserver.shouldConnect.compareAndSet(false, true);
        try {
            connection.accept(replayObserver);
            if (z2) {
                this.source.subscribe(replayObserver);
            }
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            if (z2) {
                replayObserver.shouldConnect.compareAndSet(true, false);
            }
            Exceptions.throwIfFatal(th);
            throw ExceptionHelper.wrapOrThrow(th);
        }
    }

    @Override // io.reactivex.rxjava3.observables.ConnectableObservable
    public void reset() {
        ReplayObserver<T> replayObserver = this.current.get();
        if (replayObserver == null || !replayObserver.isDisposed()) {
            return;
        }
        j.a(this.current, replayObserver, null);
    }

    @Override // io.reactivex.rxjava3.internal.fuseable.HasUpstreamObservableSource
    public ObservableSource<T> source() {
        return this.source;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super T> observer) {
        this.onSubscribe.subscribe(observer);
    }

    public static <T> ConnectableObservable<T> create(ObservableSource<T> source, long maxAge, TimeUnit unit, Scheduler scheduler, boolean eagerTruncate) {
        return create(source, maxAge, unit, scheduler, Integer.MAX_VALUE, eagerTruncate);
    }

    public static <T> ConnectableObservable<T> create(ObservableSource<T> source, final long maxAge, final TimeUnit unit, final Scheduler scheduler, final int bufferSize, boolean eagerTruncate) {
        return create(source, new ScheduledReplaySupplier(bufferSize, maxAge, unit, scheduler, eagerTruncate));
    }

    public static <T> ConnectableObservable<T> create(ObservableSource<T> source, final BufferSupplier<T> bufferFactory) {
        AtomicReference atomicReference = new AtomicReference();
        return RxJavaPlugins.onAssembly((ConnectableObservable) new ObservableReplay(new ReplaySource(atomicReference, bufferFactory), source, atomicReference, bufferFactory));
    }
}
