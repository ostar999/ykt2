package io.reactivex.rxjava3.processors;

import androidx.camera.view.j;
import io.reactivex.rxjava3.annotations.CheckReturnValue;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.exceptions.MissingBackpressureException;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.AppendOnlyLinkedArrayList;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.internal.util.NotificationLite;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class BehaviorProcessor<T> extends FlowableProcessor<T> {
    long index;
    final ReadWriteLock lock;
    final Lock readLock;
    final AtomicReference<BehaviorSubscription<T>[]> subscribers;
    final AtomicReference<Throwable> terminalEvent;
    final AtomicReference<Object> value;
    final Lock writeLock;
    static final Object[] EMPTY_ARRAY = new Object[0];
    static final BehaviorSubscription[] EMPTY = new BehaviorSubscription[0];
    static final BehaviorSubscription[] TERMINATED = new BehaviorSubscription[0];

    public static final class BehaviorSubscription<T> extends AtomicLong implements Subscription, AppendOnlyLinkedArrayList.NonThrowingPredicate<Object> {
        private static final long serialVersionUID = 3293175281126227086L;
        volatile boolean cancelled;
        final Subscriber<? super T> downstream;
        boolean emitting;
        boolean fastPath;
        long index;
        boolean next;
        AppendOnlyLinkedArrayList<Object> queue;
        final BehaviorProcessor<T> state;

        public BehaviorSubscription(Subscriber<? super T> actual, BehaviorProcessor<T> state) {
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

        public void emitFirst() {
            if (this.cancelled) {
                return;
            }
            synchronized (this) {
                if (this.cancelled) {
                    return;
                }
                if (this.next) {
                    return;
                }
                BehaviorProcessor<T> behaviorProcessor = this.state;
                Lock lock = behaviorProcessor.readLock;
                lock.lock();
                this.index = behaviorProcessor.index;
                Object obj = behaviorProcessor.value.get();
                lock.unlock();
                this.emitting = obj != null;
                this.next = true;
                if (obj == null || test(obj)) {
                    return;
                }
                emitLoop();
            }
        }

        public void emitLoop() {
            AppendOnlyLinkedArrayList<Object> appendOnlyLinkedArrayList;
            while (!this.cancelled) {
                synchronized (this) {
                    appendOnlyLinkedArrayList = this.queue;
                    if (appendOnlyLinkedArrayList == null) {
                        this.emitting = false;
                        return;
                    }
                    this.queue = null;
                }
                appendOnlyLinkedArrayList.forEachWhile(this);
            }
        }

        public void emitNext(Object value, long stateIndex) {
            if (this.cancelled) {
                return;
            }
            if (!this.fastPath) {
                synchronized (this) {
                    if (this.cancelled) {
                        return;
                    }
                    if (this.index == stateIndex) {
                        return;
                    }
                    if (this.emitting) {
                        AppendOnlyLinkedArrayList<Object> appendOnlyLinkedArrayList = this.queue;
                        if (appendOnlyLinkedArrayList == null) {
                            appendOnlyLinkedArrayList = new AppendOnlyLinkedArrayList<>(4);
                            this.queue = appendOnlyLinkedArrayList;
                        }
                        appendOnlyLinkedArrayList.add(value);
                        return;
                    }
                    this.next = true;
                    this.fastPath = true;
                }
            }
            test(value);
        }

        public boolean isFull() {
            return get() == 0;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long n2) {
            if (SubscriptionHelper.validate(n2)) {
                BackpressureHelper.add(this, n2);
            }
        }

        @Override // io.reactivex.rxjava3.internal.util.AppendOnlyLinkedArrayList.NonThrowingPredicate, io.reactivex.rxjava3.functions.Predicate
        public boolean test(Object obj) {
            if (this.cancelled) {
                return true;
            }
            if (NotificationLite.isComplete(obj)) {
                this.downstream.onComplete();
                return true;
            }
            if (NotificationLite.isError(obj)) {
                this.downstream.onError(NotificationLite.getError(obj));
                return true;
            }
            long j2 = get();
            if (j2 == 0) {
                cancel();
                this.downstream.onError(new MissingBackpressureException("Could not deliver value due to lack of requests"));
                return true;
            }
            this.downstream.onNext((Object) NotificationLite.getValue(obj));
            if (j2 == Long.MAX_VALUE) {
                return false;
            }
            decrementAndGet();
            return false;
        }
    }

    public BehaviorProcessor() {
        this.value = new AtomicReference<>();
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        this.lock = reentrantReadWriteLock;
        this.readLock = reentrantReadWriteLock.readLock();
        this.writeLock = reentrantReadWriteLock.writeLock();
        this.subscribers = new AtomicReference<>(EMPTY);
        this.terminalEvent = new AtomicReference<>();
    }

    @CheckReturnValue
    @NonNull
    public static <T> BehaviorProcessor<T> create() {
        return new BehaviorProcessor<>();
    }

    @CheckReturnValue
    @NonNull
    public static <T> BehaviorProcessor<T> createDefault(T defaultValue) {
        Objects.requireNonNull(defaultValue, "defaultValue is null");
        return new BehaviorProcessor<>(defaultValue);
    }

    public boolean add(BehaviorSubscription<T> rs) {
        BehaviorSubscription<T>[] behaviorSubscriptionArr;
        BehaviorSubscription[] behaviorSubscriptionArr2;
        do {
            behaviorSubscriptionArr = this.subscribers.get();
            if (behaviorSubscriptionArr == TERMINATED) {
                return false;
            }
            int length = behaviorSubscriptionArr.length;
            behaviorSubscriptionArr2 = new BehaviorSubscription[length + 1];
            System.arraycopy(behaviorSubscriptionArr, 0, behaviorSubscriptionArr2, 0, length);
            behaviorSubscriptionArr2[length] = rs;
        } while (!j.a(this.subscribers, behaviorSubscriptionArr, behaviorSubscriptionArr2));
        return true;
    }

    @Override // io.reactivex.rxjava3.processors.FlowableProcessor
    @CheckReturnValue
    @Nullable
    public Throwable getThrowable() {
        Object obj = this.value.get();
        if (NotificationLite.isError(obj)) {
            return NotificationLite.getError(obj);
        }
        return null;
    }

    @CheckReturnValue
    @Nullable
    public T getValue() {
        Object obj = this.value.get();
        if (NotificationLite.isComplete(obj) || NotificationLite.isError(obj)) {
            return null;
        }
        return (T) NotificationLite.getValue(obj);
    }

    @Override // io.reactivex.rxjava3.processors.FlowableProcessor
    @CheckReturnValue
    public boolean hasComplete() {
        return NotificationLite.isComplete(this.value.get());
    }

    @Override // io.reactivex.rxjava3.processors.FlowableProcessor
    @CheckReturnValue
    public boolean hasSubscribers() {
        return this.subscribers.get().length != 0;
    }

    @Override // io.reactivex.rxjava3.processors.FlowableProcessor
    @CheckReturnValue
    public boolean hasThrowable() {
        return NotificationLite.isError(this.value.get());
    }

    @CheckReturnValue
    public boolean hasValue() {
        Object obj = this.value.get();
        return (obj == null || NotificationLite.isComplete(obj) || NotificationLite.isError(obj)) ? false : true;
    }

    @CheckReturnValue
    public boolean offer(@NonNull T t2) {
        ExceptionHelper.nullCheck(t2, "offer called with a null value.");
        BehaviorSubscription<T>[] behaviorSubscriptionArr = this.subscribers.get();
        for (BehaviorSubscription<T> behaviorSubscription : behaviorSubscriptionArr) {
            if (behaviorSubscription.isFull()) {
                return false;
            }
        }
        Object next = NotificationLite.next(t2);
        setCurrent(next);
        for (BehaviorSubscription<T> behaviorSubscription2 : behaviorSubscriptionArr) {
            behaviorSubscription2.emitNext(next, this.index);
        }
        return true;
    }

    @Override // org.reactivestreams.Subscriber
    public void onComplete() {
        if (j.a(this.terminalEvent, null, ExceptionHelper.TERMINATED)) {
            Object objComplete = NotificationLite.complete();
            for (BehaviorSubscription<T> behaviorSubscription : terminate(objComplete)) {
                behaviorSubscription.emitNext(objComplete, this.index);
            }
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onError(@NonNull Throwable t2) {
        ExceptionHelper.nullCheck(t2, "onError called with a null Throwable.");
        if (!j.a(this.terminalEvent, null, t2)) {
            RxJavaPlugins.onError(t2);
            return;
        }
        Object objError = NotificationLite.error(t2);
        for (BehaviorSubscription<T> behaviorSubscription : terminate(objError)) {
            behaviorSubscription.emitNext(objError, this.index);
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onNext(@NonNull T t2) {
        ExceptionHelper.nullCheck(t2, "onNext called with a null value.");
        if (this.terminalEvent.get() != null) {
            return;
        }
        Object next = NotificationLite.next(t2);
        setCurrent(next);
        for (BehaviorSubscription<T> behaviorSubscription : this.subscribers.get()) {
            behaviorSubscription.emitNext(next, this.index);
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onSubscribe(@NonNull Subscription s2) {
        if (this.terminalEvent.get() != null) {
            s2.cancel();
        } else {
            s2.request(Long.MAX_VALUE);
        }
    }

    public void remove(BehaviorSubscription<T> rs) {
        BehaviorSubscription<T>[] behaviorSubscriptionArr;
        BehaviorSubscription[] behaviorSubscriptionArr2;
        do {
            behaviorSubscriptionArr = this.subscribers.get();
            int length = behaviorSubscriptionArr.length;
            if (length == 0) {
                return;
            }
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    i2 = -1;
                    break;
                } else if (behaviorSubscriptionArr[i2] == rs) {
                    break;
                } else {
                    i2++;
                }
            }
            if (i2 < 0) {
                return;
            }
            if (length == 1) {
                behaviorSubscriptionArr2 = EMPTY;
            } else {
                BehaviorSubscription[] behaviorSubscriptionArr3 = new BehaviorSubscription[length - 1];
                System.arraycopy(behaviorSubscriptionArr, 0, behaviorSubscriptionArr3, 0, i2);
                System.arraycopy(behaviorSubscriptionArr, i2 + 1, behaviorSubscriptionArr3, i2, (length - i2) - 1);
                behaviorSubscriptionArr2 = behaviorSubscriptionArr3;
            }
        } while (!j.a(this.subscribers, behaviorSubscriptionArr, behaviorSubscriptionArr2));
    }

    public void setCurrent(Object o2) {
        Lock lock = this.writeLock;
        lock.lock();
        this.index++;
        this.value.lazySet(o2);
        lock.unlock();
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(@NonNull Subscriber<? super T> s2) {
        BehaviorSubscription<T> behaviorSubscription = new BehaviorSubscription<>(s2, this);
        s2.onSubscribe(behaviorSubscription);
        if (add(behaviorSubscription)) {
            if (behaviorSubscription.cancelled) {
                remove(behaviorSubscription);
                return;
            } else {
                behaviorSubscription.emitFirst();
                return;
            }
        }
        Throwable th = this.terminalEvent.get();
        if (th == ExceptionHelper.TERMINATED) {
            s2.onComplete();
        } else {
            s2.onError(th);
        }
    }

    @CheckReturnValue
    public int subscriberCount() {
        return this.subscribers.get().length;
    }

    public BehaviorSubscription<T>[] terminate(Object terminalValue) {
        setCurrent(terminalValue);
        return this.subscribers.getAndSet(TERMINATED);
    }

    public BehaviorProcessor(T defaultValue) {
        this();
        this.value.lazySet(defaultValue);
    }
}
