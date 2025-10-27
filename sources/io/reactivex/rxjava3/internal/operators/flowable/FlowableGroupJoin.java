package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.exceptions.MissingBackpressureException;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.operators.SimpleQueue;
import io.reactivex.rxjava3.operators.SpscLinkedArrayQueue;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.processors.UnicastProcessor;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableGroupJoin<TLeft, TRight, TLeftEnd, TRightEnd, R> extends AbstractFlowableWithUpstream<TLeft, R> {
    final Function<? super TLeft, ? extends Publisher<TLeftEnd>> leftEnd;
    final Publisher<? extends TRight> other;
    final BiFunction<? super TLeft, ? super Flowable<TRight>, ? extends R> resultSelector;
    final Function<? super TRight, ? extends Publisher<TRightEnd>> rightEnd;

    public static final class GroupJoinSubscription<TLeft, TRight, TLeftEnd, TRightEnd, R> extends AtomicInteger implements Subscription, JoinSupport {
        private static final long serialVersionUID = -6071216598687999801L;
        volatile boolean cancelled;
        final Subscriber<? super R> downstream;
        final Function<? super TLeft, ? extends Publisher<TLeftEnd>> leftEnd;
        int leftIndex;
        final BiFunction<? super TLeft, ? super Flowable<TRight>, ? extends R> resultSelector;
        final Function<? super TRight, ? extends Publisher<TRightEnd>> rightEnd;
        int rightIndex;
        static final Integer LEFT_VALUE = 1;
        static final Integer RIGHT_VALUE = 2;
        static final Integer LEFT_CLOSE = 3;
        static final Integer RIGHT_CLOSE = 4;
        final AtomicLong requested = new AtomicLong();
        final CompositeDisposable disposables = new CompositeDisposable();
        final SpscLinkedArrayQueue<Object> queue = new SpscLinkedArrayQueue<>(Flowable.bufferSize());
        final Map<Integer, UnicastProcessor<TRight>> lefts = new LinkedHashMap();
        final Map<Integer, TRight> rights = new LinkedHashMap();
        final AtomicReference<Throwable> error = new AtomicReference<>();
        final AtomicInteger active = new AtomicInteger(2);

        public GroupJoinSubscription(Subscriber<? super R> actual, Function<? super TLeft, ? extends Publisher<TLeftEnd>> leftEnd, Function<? super TRight, ? extends Publisher<TRightEnd>> rightEnd, BiFunction<? super TLeft, ? super Flowable<TRight>, ? extends R> resultSelector) {
            this.downstream = actual;
            this.leftEnd = leftEnd;
            this.rightEnd = rightEnd;
            this.resultSelector = resultSelector;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            cancelAll();
            if (getAndIncrement() == 0) {
                this.queue.clear();
            }
        }

        public void cancelAll() {
            this.disposables.dispose();
        }

        public void drain() {
            if (getAndIncrement() != 0) {
                return;
            }
            SpscLinkedArrayQueue<Object> spscLinkedArrayQueue = this.queue;
            Subscriber<? super R> subscriber = this.downstream;
            int iAddAndGet = 1;
            while (!this.cancelled) {
                if (this.error.get() != null) {
                    spscLinkedArrayQueue.clear();
                    cancelAll();
                    errorAll(subscriber);
                    return;
                }
                boolean z2 = this.active.get() == 0;
                Integer num = (Integer) spscLinkedArrayQueue.poll();
                boolean z3 = num == null;
                if (z2 && z3) {
                    Iterator<UnicastProcessor<TRight>> it = this.lefts.values().iterator();
                    while (it.hasNext()) {
                        it.next().onComplete();
                    }
                    this.lefts.clear();
                    this.rights.clear();
                    this.disposables.dispose();
                    subscriber.onComplete();
                    return;
                }
                if (z3) {
                    iAddAndGet = addAndGet(-iAddAndGet);
                    if (iAddAndGet == 0) {
                        return;
                    }
                } else {
                    Object objPoll = spscLinkedArrayQueue.poll();
                    if (num == LEFT_VALUE) {
                        UnicastProcessor unicastProcessorCreate = UnicastProcessor.create();
                        int i2 = this.leftIndex;
                        this.leftIndex = i2 + 1;
                        this.lefts.put(Integer.valueOf(i2), unicastProcessorCreate);
                        try {
                            Publisher publisherApply = this.leftEnd.apply(objPoll);
                            Objects.requireNonNull(publisherApply, "The leftEnd returned a null Publisher");
                            Publisher publisher = publisherApply;
                            LeftRightEndSubscriber leftRightEndSubscriber = new LeftRightEndSubscriber(this, true, i2);
                            this.disposables.add(leftRightEndSubscriber);
                            publisher.subscribe(leftRightEndSubscriber);
                            if (this.error.get() != null) {
                                spscLinkedArrayQueue.clear();
                                cancelAll();
                                errorAll(subscriber);
                                return;
                            }
                            try {
                                R rApply = this.resultSelector.apply(objPoll, unicastProcessorCreate);
                                Objects.requireNonNull(rApply, "The resultSelector returned a null value");
                                if (this.requested.get() == 0) {
                                    fail(new MissingBackpressureException("Could not emit value due to lack of requests"), subscriber, spscLinkedArrayQueue);
                                    return;
                                }
                                subscriber.onNext(rApply);
                                BackpressureHelper.produced(this.requested, 1L);
                                Iterator<TRight> it2 = this.rights.values().iterator();
                                while (it2.hasNext()) {
                                    unicastProcessorCreate.onNext(it2.next());
                                }
                            } catch (Throwable th) {
                                fail(th, subscriber, spscLinkedArrayQueue);
                                return;
                            }
                        } catch (Throwable th2) {
                            fail(th2, subscriber, spscLinkedArrayQueue);
                            return;
                        }
                    } else if (num == RIGHT_VALUE) {
                        int i3 = this.rightIndex;
                        this.rightIndex = i3 + 1;
                        this.rights.put(Integer.valueOf(i3), objPoll);
                        try {
                            Publisher publisherApply2 = this.rightEnd.apply(objPoll);
                            Objects.requireNonNull(publisherApply2, "The rightEnd returned a null Publisher");
                            Publisher publisher2 = publisherApply2;
                            LeftRightEndSubscriber leftRightEndSubscriber2 = new LeftRightEndSubscriber(this, false, i3);
                            this.disposables.add(leftRightEndSubscriber2);
                            publisher2.subscribe(leftRightEndSubscriber2);
                            if (this.error.get() != null) {
                                spscLinkedArrayQueue.clear();
                                cancelAll();
                                errorAll(subscriber);
                                return;
                            } else {
                                Iterator<UnicastProcessor<TRight>> it3 = this.lefts.values().iterator();
                                while (it3.hasNext()) {
                                    it3.next().onNext(objPoll);
                                }
                            }
                        } catch (Throwable th3) {
                            fail(th3, subscriber, spscLinkedArrayQueue);
                            return;
                        }
                    } else if (num == LEFT_CLOSE) {
                        LeftRightEndSubscriber leftRightEndSubscriber3 = (LeftRightEndSubscriber) objPoll;
                        UnicastProcessor<TRight> unicastProcessorRemove = this.lefts.remove(Integer.valueOf(leftRightEndSubscriber3.index));
                        this.disposables.remove(leftRightEndSubscriber3);
                        if (unicastProcessorRemove != null) {
                            unicastProcessorRemove.onComplete();
                        }
                    } else {
                        LeftRightEndSubscriber leftRightEndSubscriber4 = (LeftRightEndSubscriber) objPoll;
                        this.rights.remove(Integer.valueOf(leftRightEndSubscriber4.index));
                        this.disposables.remove(leftRightEndSubscriber4);
                    }
                }
            }
            spscLinkedArrayQueue.clear();
        }

        public void errorAll(Subscriber<?> a3) {
            Throwable thTerminate = ExceptionHelper.terminate(this.error);
            Iterator<UnicastProcessor<TRight>> it = this.lefts.values().iterator();
            while (it.hasNext()) {
                it.next().onError(thTerminate);
            }
            this.lefts.clear();
            this.rights.clear();
            a3.onError(thTerminate);
        }

        public void fail(Throwable exc, Subscriber<?> a3, SimpleQueue<?> q2) {
            Exceptions.throwIfFatal(exc);
            ExceptionHelper.addThrowable(this.error, exc);
            q2.clear();
            cancelAll();
            errorAll(a3);
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableGroupJoin.JoinSupport
        public void innerClose(boolean isLeft, LeftRightEndSubscriber index) {
            synchronized (this) {
                this.queue.offer(isLeft ? LEFT_CLOSE : RIGHT_CLOSE, index);
            }
            drain();
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableGroupJoin.JoinSupport
        public void innerCloseError(Throwable ex) {
            if (ExceptionHelper.addThrowable(this.error, ex)) {
                drain();
            } else {
                RxJavaPlugins.onError(ex);
            }
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableGroupJoin.JoinSupport
        public void innerComplete(LeftRightSubscriber sender) {
            this.disposables.delete(sender);
            this.active.decrementAndGet();
            drain();
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableGroupJoin.JoinSupport
        public void innerError(Throwable ex) {
            if (!ExceptionHelper.addThrowable(this.error, ex)) {
                RxJavaPlugins.onError(ex);
            } else {
                this.active.decrementAndGet();
                drain();
            }
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableGroupJoin.JoinSupport
        public void innerValue(boolean isLeft, Object o2) {
            synchronized (this) {
                this.queue.offer(isLeft ? LEFT_VALUE : RIGHT_VALUE, o2);
            }
            drain();
        }

        @Override // org.reactivestreams.Subscription
        public void request(long n2) {
            if (SubscriptionHelper.validate(n2)) {
                BackpressureHelper.add(this.requested, n2);
            }
        }
    }

    public interface JoinSupport {
        void innerClose(boolean isLeft, LeftRightEndSubscriber index);

        void innerCloseError(Throwable ex);

        void innerComplete(LeftRightSubscriber sender);

        void innerError(Throwable ex);

        void innerValue(boolean isLeft, Object o2);
    }

    public static final class LeftRightEndSubscriber extends AtomicReference<Subscription> implements FlowableSubscriber<Object>, Disposable {
        private static final long serialVersionUID = 1883890389173668373L;
        final int index;
        final boolean isLeft;
        final JoinSupport parent;

        public LeftRightEndSubscriber(JoinSupport parent, boolean isLeft, int index) {
            this.parent = parent;
            this.isLeft = isLeft;
            this.index = index;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            SubscriptionHelper.cancel(this);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return get() == SubscriptionHelper.CANCELLED;
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.parent.innerClose(this.isLeft, this);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t2) {
            this.parent.innerCloseError(t2);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object t2) {
            if (SubscriptionHelper.cancel(this)) {
                this.parent.innerClose(this.isLeft, this);
            }
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s2) {
            SubscriptionHelper.setOnce(this, s2, Long.MAX_VALUE);
        }
    }

    public static final class LeftRightSubscriber extends AtomicReference<Subscription> implements FlowableSubscriber<Object>, Disposable {
        private static final long serialVersionUID = 1883890389173668373L;
        final boolean isLeft;
        final JoinSupport parent;

        public LeftRightSubscriber(JoinSupport parent, boolean isLeft) {
            this.parent = parent;
            this.isLeft = isLeft;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            SubscriptionHelper.cancel(this);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return get() == SubscriptionHelper.CANCELLED;
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.parent.innerComplete(this);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t2) {
            this.parent.innerError(t2);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object t2) {
            this.parent.innerValue(this.isLeft, t2);
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s2) {
            SubscriptionHelper.setOnce(this, s2, Long.MAX_VALUE);
        }
    }

    public FlowableGroupJoin(Flowable<TLeft> source, Publisher<? extends TRight> other, Function<? super TLeft, ? extends Publisher<TLeftEnd>> leftEnd, Function<? super TRight, ? extends Publisher<TRightEnd>> rightEnd, BiFunction<? super TLeft, ? super Flowable<TRight>, ? extends R> resultSelector) {
        super(source);
        this.other = other;
        this.leftEnd = leftEnd;
        this.rightEnd = rightEnd;
        this.resultSelector = resultSelector;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super R> s2) {
        GroupJoinSubscription groupJoinSubscription = new GroupJoinSubscription(s2, this.leftEnd, this.rightEnd, this.resultSelector);
        s2.onSubscribe(groupJoinSubscription);
        LeftRightSubscriber leftRightSubscriber = new LeftRightSubscriber(groupJoinSubscription, true);
        groupJoinSubscription.disposables.add(leftRightSubscriber);
        LeftRightSubscriber leftRightSubscriber2 = new LeftRightSubscriber(groupJoinSubscription, false);
        groupJoinSubscription.disposables.add(leftRightSubscriber2);
        this.source.subscribe((FlowableSubscriber) leftRightSubscriber);
        this.other.subscribe(leftRightSubscriber2);
    }
}
