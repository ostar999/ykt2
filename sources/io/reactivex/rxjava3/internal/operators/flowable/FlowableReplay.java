package io.reactivex.rxjava3.internal.operators.flowable;

import androidx.camera.view.j;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.flowables.ConnectableFlowable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.fuseable.HasUpstreamPublisher;
import io.reactivex.rxjava3.internal.subscribers.SubscriberResourceWrapper;
import io.reactivex.rxjava3.internal.subscriptions.EmptySubscription;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.internal.util.NotificationLite;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.schedulers.Timed;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableReplay<T> extends ConnectableFlowable<T> implements HasUpstreamPublisher<T> {
    static final Supplier DEFAULT_UNBOUNDED_FACTORY = new DefaultUnboundedFactory();
    final Supplier<? extends ReplayBuffer<T>> bufferFactory;
    final AtomicReference<ReplaySubscriber<T>> current;
    final Publisher<T> onSubscribe;
    final Flowable<T> source;

    public static abstract class BoundedReplayBuffer<T> extends AtomicReference<Node> implements ReplayBuffer<T> {
        private static final long serialVersionUID = 2346567790059478686L;
        final boolean eagerTruncate;
        long index;
        int size;
        Node tail;

        public BoundedReplayBuffer(boolean eagerTruncate) {
            this.eagerTruncate = eagerTruncate;
            Node node = new Node(null, 0L);
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

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableReplay.ReplayBuffer
        public final void complete() {
            Object objEnterTransform = enterTransform(NotificationLite.complete(), true);
            long j2 = this.index + 1;
            this.index = j2;
            addLast(new Node(objEnterTransform, j2));
            truncateFinal();
        }

        public Object enterTransform(Object value, boolean terminal) {
            return value;
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableReplay.ReplayBuffer
        public final void error(Throwable e2) {
            Object objEnterTransform = enterTransform(NotificationLite.error(e2), true);
            long j2 = this.index + 1;
            this.index = j2;
            addLast(new Node(objEnterTransform, j2));
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

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableReplay.ReplayBuffer
        public final void next(T value) {
            Object objEnterTransform = enterTransform(NotificationLite.next(value), false);
            long j2 = this.index + 1;
            this.index = j2;
            addLast(new Node(objEnterTransform, j2));
            truncate();
        }

        public final void removeFirst() {
            Node node = get().get();
            if (node == null) {
                throw new IllegalStateException("Empty list!");
            }
            this.size--;
            setFirst(node);
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

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableReplay.ReplayBuffer
        public final void replay(InnerSubscription<T> output) {
            synchronized (output) {
                if (output.emitting) {
                    output.missed = true;
                    return;
                }
                output.emitting = true;
                while (true) {
                    long j2 = output.get();
                    boolean z2 = j2 == Long.MAX_VALUE;
                    Node head = (Node) output.index();
                    if (head == null) {
                        head = getHead();
                        output.index = head;
                        BackpressureHelper.add(output.totalRequested, head.index);
                    }
                    long j3 = 0;
                    while (j2 != 0) {
                        if (!output.isDisposed()) {
                            Node node = head.get();
                            if (node == null) {
                                break;
                            }
                            Object objLeaveTransform = leaveTransform(node.value);
                            try {
                                if (NotificationLite.accept(objLeaveTransform, output.child)) {
                                    output.index = null;
                                    return;
                                } else {
                                    j3++;
                                    j2--;
                                    head = node;
                                }
                            } catch (Throwable th) {
                                Exceptions.throwIfFatal(th);
                                output.index = null;
                                output.dispose();
                                if (NotificationLite.isError(objLeaveTransform) || NotificationLite.isComplete(objLeaveTransform)) {
                                    RxJavaPlugins.onError(th);
                                    return;
                                } else {
                                    output.child.onError(th);
                                    return;
                                }
                            }
                        } else {
                            output.index = null;
                            return;
                        }
                    }
                    if (j2 == 0 && output.isDisposed()) {
                        output.index = null;
                        return;
                    }
                    if (j3 != 0) {
                        output.index = head;
                        if (!z2) {
                            output.produced(j3);
                        }
                    }
                    synchronized (output) {
                        if (!output.missed) {
                            output.emitting = false;
                            return;
                        }
                        output.missed = false;
                    }
                }
            }
        }

        public final void setFirst(Node n2) {
            if (this.eagerTruncate) {
                Node node = new Node(null, n2.index);
                node.lazySet(n2.get());
                n2 = node;
            }
            set(n2);
        }

        public final void trimHead() {
            Node node = get();
            if (node.value != null) {
                Node node2 = new Node(null, 0L);
                node2.lazySet(node.get());
                set(node2);
            }
        }

        public abstract void truncate();

        public void truncateFinal() {
            trimHead();
        }
    }

    public static final class DefaultUnboundedFactory implements Supplier<Object> {
        @Override // io.reactivex.rxjava3.functions.Supplier
        public Object get() {
            return new UnboundedReplayBuffer(16);
        }
    }

    public static final class InnerSubscription<T> extends AtomicLong implements Subscription, Disposable {
        static final long CANCELLED = Long.MIN_VALUE;
        private static final long serialVersionUID = -4453897557930727610L;
        final Subscriber<? super T> child;
        boolean emitting;
        Object index;
        boolean missed;
        final ReplaySubscriber<T> parent;
        final AtomicLong totalRequested = new AtomicLong();

        public InnerSubscription(ReplaySubscriber<T> parent, Subscriber<? super T> child) {
            this.parent = parent;
            this.child = child;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            dispose();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            if (getAndSet(Long.MIN_VALUE) != Long.MIN_VALUE) {
                this.parent.remove(this);
                this.parent.manageRequests();
                this.index = null;
            }
        }

        public <U> U index() {
            return (U) this.index;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return get() == Long.MIN_VALUE;
        }

        public long produced(long n2) {
            return BackpressureHelper.producedCancel(this, n2);
        }

        @Override // org.reactivestreams.Subscription
        public void request(long n2) {
            if (!SubscriptionHelper.validate(n2) || BackpressureHelper.addCancel(this, n2) == Long.MIN_VALUE) {
                return;
            }
            BackpressureHelper.add(this.totalRequested, n2);
            this.parent.manageRequests();
            this.parent.buffer.replay(this);
        }
    }

    public static final class MulticastFlowable<R, U> extends Flowable<R> {
        private final Supplier<? extends ConnectableFlowable<U>> connectableFactory;
        private final Function<? super Flowable<U>, ? extends Publisher<R>> selector;

        public final class DisposableConsumer implements Consumer<Disposable> {
            private final SubscriberResourceWrapper<R> srw;

            public DisposableConsumer(SubscriberResourceWrapper<R> srw) {
                this.srw = srw;
            }

            @Override // io.reactivex.rxjava3.functions.Consumer
            public void accept(Disposable r2) {
                this.srw.setResource(r2);
            }
        }

        public MulticastFlowable(Supplier<? extends ConnectableFlowable<U>> connectableFactory, Function<? super Flowable<U>, ? extends Publisher<R>> selector) {
            this.connectableFactory = connectableFactory;
            this.selector = selector;
        }

        @Override // io.reactivex.rxjava3.core.Flowable
        public void subscribeActual(Subscriber<? super R> child) {
            try {
                ConnectableFlowable connectableFlowable = (ConnectableFlowable) ExceptionHelper.nullCheck(this.connectableFactory.get(), "The connectableFactory returned a null ConnectableFlowable.");
                try {
                    Publisher publisher = (Publisher) ExceptionHelper.nullCheck(this.selector.apply(connectableFlowable), "The selector returned a null Publisher.");
                    SubscriberResourceWrapper subscriberResourceWrapper = new SubscriberResourceWrapper(child);
                    publisher.subscribe(subscriberResourceWrapper);
                    connectableFlowable.connect(new DisposableConsumer(subscriberResourceWrapper));
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    EmptySubscription.error(th, child);
                }
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                EmptySubscription.error(th2, child);
            }
        }
    }

    public static final class Node extends AtomicReference<Node> {
        private static final long serialVersionUID = 245354315435971818L;
        final long index;
        final Object value;

        public Node(Object value, long index) {
            this.value = value;
            this.index = index;
        }
    }

    public interface ReplayBuffer<T> {
        void complete();

        void error(Throwable e2);

        void next(T value);

        void replay(InnerSubscription<T> output);
    }

    public static final class ReplayBufferSupplier<T> implements Supplier<ReplayBuffer<T>> {
        final int bufferSize;
        final boolean eagerTruncate;

        public ReplayBufferSupplier(int bufferSize, boolean eagerTruncate) {
            this.bufferSize = bufferSize;
            this.eagerTruncate = eagerTruncate;
        }

        @Override // io.reactivex.rxjava3.functions.Supplier
        public ReplayBuffer<T> get() {
            return new SizeBoundReplayBuffer(this.bufferSize, this.eagerTruncate);
        }
    }

    public static final class ReplayPublisher<T> implements Publisher<T> {
        private final Supplier<? extends ReplayBuffer<T>> bufferFactory;
        private final AtomicReference<ReplaySubscriber<T>> curr;

        public ReplayPublisher(AtomicReference<ReplaySubscriber<T>> curr, Supplier<? extends ReplayBuffer<T>> bufferFactory) {
            this.curr = curr;
            this.bufferFactory = bufferFactory;
        }

        @Override // org.reactivestreams.Publisher
        public void subscribe(Subscriber<? super T> child) {
            ReplaySubscriber<T> replaySubscriber;
            while (true) {
                replaySubscriber = this.curr.get();
                if (replaySubscriber != null) {
                    break;
                }
                try {
                    ReplaySubscriber<T> replaySubscriber2 = new ReplaySubscriber<>(this.bufferFactory.get(), this.curr);
                    if (j.a(this.curr, null, replaySubscriber2)) {
                        replaySubscriber = replaySubscriber2;
                        break;
                    }
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    EmptySubscription.error(th, child);
                    return;
                }
            }
            InnerSubscription<T> innerSubscription = new InnerSubscription<>(replaySubscriber, child);
            child.onSubscribe(innerSubscription);
            replaySubscriber.add(innerSubscription);
            if (innerSubscription.isDisposed()) {
                replaySubscriber.remove(innerSubscription);
            } else {
                replaySubscriber.manageRequests();
                replaySubscriber.buffer.replay(innerSubscription);
            }
        }
    }

    public static final class ReplaySubscriber<T> extends AtomicReference<Subscription> implements FlowableSubscriber<T>, Disposable {
        static final InnerSubscription[] EMPTY = new InnerSubscription[0];
        static final InnerSubscription[] TERMINATED = new InnerSubscription[0];
        private static final long serialVersionUID = 7224554242710036740L;
        final ReplayBuffer<T> buffer;
        final AtomicReference<ReplaySubscriber<T>> current;
        boolean done;
        long requestedFromUpstream;
        final AtomicInteger management = new AtomicInteger();
        final AtomicReference<InnerSubscription<T>[]> subscribers = new AtomicReference<>(EMPTY);
        final AtomicBoolean shouldConnect = new AtomicBoolean();

        public ReplaySubscriber(ReplayBuffer<T> buffer, AtomicReference<ReplaySubscriber<T>> current) {
            this.buffer = buffer;
            this.current = current;
        }

        public boolean add(InnerSubscription<T> producer) {
            InnerSubscription<T>[] innerSubscriptionArr;
            InnerSubscription[] innerSubscriptionArr2;
            do {
                innerSubscriptionArr = this.subscribers.get();
                if (innerSubscriptionArr == TERMINATED) {
                    return false;
                }
                int length = innerSubscriptionArr.length;
                innerSubscriptionArr2 = new InnerSubscription[length + 1];
                System.arraycopy(innerSubscriptionArr, 0, innerSubscriptionArr2, 0, length);
                innerSubscriptionArr2[length] = producer;
            } while (!j.a(this.subscribers, innerSubscriptionArr, innerSubscriptionArr2));
            return true;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.subscribers.set(TERMINATED);
            j.a(this.current, this, null);
            SubscriptionHelper.cancel(this);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.subscribers.get() == TERMINATED;
        }

        public void manageRequests() {
            AtomicInteger atomicInteger = this.management;
            if (atomicInteger.getAndIncrement() != 0) {
                return;
            }
            int iAddAndGet = 1;
            while (!isDisposed()) {
                Subscription subscription = get();
                if (subscription != null) {
                    long j2 = this.requestedFromUpstream;
                    long jMax = j2;
                    for (InnerSubscription<T> innerSubscription : this.subscribers.get()) {
                        jMax = Math.max(jMax, innerSubscription.totalRequested.get());
                    }
                    long j3 = jMax - j2;
                    if (j3 != 0) {
                        this.requestedFromUpstream = jMax;
                        subscription.request(j3);
                    }
                }
                iAddAndGet = atomicInteger.addAndGet(-iAddAndGet);
                if (iAddAndGet == 0) {
                    return;
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.buffer.complete();
            for (InnerSubscription<T> innerSubscription : this.subscribers.getAndSet(TERMINATED)) {
                this.buffer.replay(innerSubscription);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable e2) {
            if (this.done) {
                RxJavaPlugins.onError(e2);
                return;
            }
            this.done = true;
            this.buffer.error(e2);
            for (InnerSubscription<T> innerSubscription : this.subscribers.getAndSet(TERMINATED)) {
                this.buffer.replay(innerSubscription);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            if (this.done) {
                return;
            }
            this.buffer.next(t2);
            for (InnerSubscription<T> innerSubscription : this.subscribers.get()) {
                this.buffer.replay(innerSubscription);
            }
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription p2) {
            if (SubscriptionHelper.setOnce(this, p2)) {
                manageRequests();
                for (InnerSubscription<T> innerSubscription : this.subscribers.get()) {
                    this.buffer.replay(innerSubscription);
                }
            }
        }

        public void remove(InnerSubscription<T> p2) {
            InnerSubscription<T>[] innerSubscriptionArr;
            InnerSubscription[] innerSubscriptionArr2;
            do {
                innerSubscriptionArr = this.subscribers.get();
                int length = innerSubscriptionArr.length;
                if (length == 0) {
                    return;
                }
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        i2 = -1;
                        break;
                    } else if (innerSubscriptionArr[i2].equals(p2)) {
                        break;
                    } else {
                        i2++;
                    }
                }
                if (i2 < 0) {
                    return;
                }
                if (length == 1) {
                    innerSubscriptionArr2 = EMPTY;
                } else {
                    InnerSubscription[] innerSubscriptionArr3 = new InnerSubscription[length - 1];
                    System.arraycopy(innerSubscriptionArr, 0, innerSubscriptionArr3, 0, i2);
                    System.arraycopy(innerSubscriptionArr, i2 + 1, innerSubscriptionArr3, i2, (length - i2) - 1);
                    innerSubscriptionArr2 = innerSubscriptionArr3;
                }
            } while (!j.a(this.subscribers, innerSubscriptionArr, innerSubscriptionArr2));
        }
    }

    public static final class ScheduledReplayBufferSupplier<T> implements Supplier<ReplayBuffer<T>> {
        private final int bufferSize;
        final boolean eagerTruncate;
        private final long maxAge;
        private final Scheduler scheduler;
        private final TimeUnit unit;

        public ScheduledReplayBufferSupplier(int bufferSize, long maxAge, TimeUnit unit, Scheduler scheduler, boolean eagerTruncate) {
            this.bufferSize = bufferSize;
            this.maxAge = maxAge;
            this.unit = unit;
            this.scheduler = scheduler;
            this.eagerTruncate = eagerTruncate;
        }

        @Override // io.reactivex.rxjava3.functions.Supplier
        public ReplayBuffer<T> get() {
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

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableReplay.BoundedReplayBuffer
        public Object enterTransform(Object value, boolean terminal) {
            return new Timed(value, terminal ? Long.MAX_VALUE : this.scheduler.now(this.unit), this.unit);
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableReplay.BoundedReplayBuffer
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

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableReplay.BoundedReplayBuffer
        public Object leaveTransform(Object value) {
            return ((Timed) value).value();
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableReplay.BoundedReplayBuffer
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

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableReplay.BoundedReplayBuffer
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

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableReplay.BoundedReplayBuffer
        public void truncate() {
            if (this.size > this.limit) {
                removeFirst();
            }
        }
    }

    public static final class UnboundedReplayBuffer<T> extends ArrayList<Object> implements ReplayBuffer<T> {
        private static final long serialVersionUID = 7063189396499112664L;
        volatile int size;

        public UnboundedReplayBuffer(int capacityHint) {
            super(capacityHint);
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableReplay.ReplayBuffer
        public void complete() {
            add(NotificationLite.complete());
            this.size++;
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableReplay.ReplayBuffer
        public void error(Throwable e2) {
            add(NotificationLite.error(e2));
            this.size++;
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableReplay.ReplayBuffer
        public void next(T value) {
            add(NotificationLite.next(value));
            this.size++;
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableReplay.ReplayBuffer
        public void replay(InnerSubscription<T> output) {
            synchronized (output) {
                if (output.emitting) {
                    output.missed = true;
                    return;
                }
                output.emitting = true;
                Subscriber<? super T> subscriber = output.child;
                while (!output.isDisposed()) {
                    int i2 = this.size;
                    Integer num = (Integer) output.index();
                    int iIntValue = num != null ? num.intValue() : 0;
                    long j2 = output.get();
                    long j3 = j2;
                    long j4 = 0;
                    while (j3 != 0 && iIntValue < i2) {
                        Object obj = get(iIntValue);
                        try {
                            if (NotificationLite.accept(obj, subscriber) || output.isDisposed()) {
                                return;
                            }
                            iIntValue++;
                            j3--;
                            j4++;
                        } catch (Throwable th) {
                            Exceptions.throwIfFatal(th);
                            output.dispose();
                            if (NotificationLite.isError(obj) || NotificationLite.isComplete(obj)) {
                                RxJavaPlugins.onError(th);
                                return;
                            } else {
                                subscriber.onError(th);
                                return;
                            }
                        }
                    }
                    if (j4 != 0) {
                        output.index = Integer.valueOf(iIntValue);
                        if (j2 != Long.MAX_VALUE) {
                            output.produced(j4);
                        }
                    }
                    synchronized (output) {
                        if (!output.missed) {
                            output.emitting = false;
                            return;
                        }
                        output.missed = false;
                    }
                }
            }
        }
    }

    private FlowableReplay(Publisher<T> onSubscribe, Flowable<T> source, final AtomicReference<ReplaySubscriber<T>> current, final Supplier<? extends ReplayBuffer<T>> bufferFactory) {
        this.onSubscribe = onSubscribe;
        this.source = source;
        this.current = current;
        this.bufferFactory = bufferFactory;
    }

    public static <T> ConnectableFlowable<T> create(Flowable<T> source, final int bufferSize, boolean eagerTruncate) {
        return bufferSize == Integer.MAX_VALUE ? createFrom(source) : create(source, new ReplayBufferSupplier(bufferSize, eagerTruncate));
    }

    public static <T> ConnectableFlowable<T> createFrom(Flowable<? extends T> source) {
        return create(source, DEFAULT_UNBOUNDED_FACTORY);
    }

    public static <U, R> Flowable<R> multicastSelector(final Supplier<? extends ConnectableFlowable<U>> connectableFactory, final Function<? super Flowable<U>, ? extends Publisher<R>> selector) {
        return new MulticastFlowable(connectableFactory, selector);
    }

    @Override // io.reactivex.rxjava3.flowables.ConnectableFlowable
    public void connect(Consumer<? super Disposable> connection) {
        ReplaySubscriber<T> replaySubscriber;
        while (true) {
            replaySubscriber = this.current.get();
            if (replaySubscriber != null && !replaySubscriber.isDisposed()) {
                break;
            }
            try {
                ReplaySubscriber<T> replaySubscriber2 = new ReplaySubscriber<>(this.bufferFactory.get(), this.current);
                if (j.a(this.current, replaySubscriber, replaySubscriber2)) {
                    replaySubscriber = replaySubscriber2;
                    break;
                }
            } finally {
                Exceptions.throwIfFatal(th);
                RuntimeException runtimeExceptionWrapOrThrow = ExceptionHelper.wrapOrThrow(th);
            }
        }
        boolean z2 = !replaySubscriber.shouldConnect.get() && replaySubscriber.shouldConnect.compareAndSet(false, true);
        try {
            connection.accept(replaySubscriber);
            if (z2) {
                this.source.subscribe((FlowableSubscriber) replaySubscriber);
            }
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            if (z2) {
                replaySubscriber.shouldConnect.compareAndSet(true, false);
            }
            throw ExceptionHelper.wrapOrThrow(th);
        }
    }

    @Override // io.reactivex.rxjava3.flowables.ConnectableFlowable
    public void reset() {
        ReplaySubscriber<T> replaySubscriber = this.current.get();
        if (replaySubscriber == null || !replaySubscriber.isDisposed()) {
            return;
        }
        j.a(this.current, replaySubscriber, null);
    }

    @Override // io.reactivex.rxjava3.internal.fuseable.HasUpstreamPublisher
    public Publisher<T> source() {
        return this.source;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super T> s2) {
        this.onSubscribe.subscribe(s2);
    }

    public static <T> ConnectableFlowable<T> create(Flowable<T> source, long maxAge, TimeUnit unit, Scheduler scheduler, boolean eagerTruncate) {
        return create(source, maxAge, unit, scheduler, Integer.MAX_VALUE, eagerTruncate);
    }

    public static <T> ConnectableFlowable<T> create(Flowable<T> source, final long maxAge, final TimeUnit unit, final Scheduler scheduler, final int bufferSize, boolean eagerTruncate) {
        return create(source, new ScheduledReplayBufferSupplier(bufferSize, maxAge, unit, scheduler, eagerTruncate));
    }

    public static <T> ConnectableFlowable<T> create(Flowable<T> source, final Supplier<? extends ReplayBuffer<T>> bufferFactory) {
        AtomicReference atomicReference = new AtomicReference();
        return RxJavaPlugins.onAssembly((ConnectableFlowable) new FlowableReplay(new ReplayPublisher(atomicReference, bufferFactory), source, atomicReference, bufferFactory));
    }
}
