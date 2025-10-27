package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.internal.subscriptions.BasicQueueSubscription;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.operators.ConditionalSubscriber;
import java.util.Objects;
import org.reactivestreams.Subscriber;

/* loaded from: classes8.dex */
public final class FlowableFromArray<T> extends Flowable<T> {
    final T[] array;

    public static final class ArrayConditionalSubscription<T> extends BaseArraySubscription<T> {
        private static final long serialVersionUID = 2587302975077663557L;
        final ConditionalSubscriber<? super T> downstream;

        public ArrayConditionalSubscription(ConditionalSubscriber<? super T> actual, T[] array) {
            super(array);
            this.downstream = actual;
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableFromArray.BaseArraySubscription
        public void fastPath() {
            T[] tArr = this.array;
            int length = tArr.length;
            ConditionalSubscriber<? super T> conditionalSubscriber = this.downstream;
            for (int i2 = this.index; i2 != length; i2++) {
                if (this.cancelled) {
                    return;
                }
                T t2 = tArr[i2];
                if (t2 == null) {
                    conditionalSubscriber.onError(new NullPointerException("The element at index " + i2 + " is null"));
                    return;
                }
                conditionalSubscriber.tryOnNext(t2);
            }
            if (this.cancelled) {
                return;
            }
            conditionalSubscriber.onComplete();
        }

        /* JADX WARN: Code restructure failed: missing block: B:25:0x0056, code lost:
        
            r10.index = r2;
            r11 = addAndGet(-r6);
         */
        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableFromArray.BaseArraySubscription
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void slowPath(long r11) {
            /*
                r10 = this;
                T[] r0 = r10.array
                int r1 = r0.length
                int r2 = r10.index
                io.reactivex.rxjava3.operators.ConditionalSubscriber<? super T> r3 = r10.downstream
                r4 = 0
            L9:
                r6 = r4
            La:
                int r8 = (r6 > r11 ? 1 : (r6 == r11 ? 0 : -1))
                if (r8 == 0) goto L44
                if (r2 == r1) goto L44
                boolean r8 = r10.cancelled
                if (r8 == 0) goto L15
                return
            L15:
                r8 = r0[r2]
                if (r8 != 0) goto L38
                java.lang.NullPointerException r11 = new java.lang.NullPointerException
                java.lang.StringBuilder r12 = new java.lang.StringBuilder
                r12.<init>()
                java.lang.String r0 = "The element at index "
                r12.append(r0)
                r12.append(r2)
                java.lang.String r0 = " is null"
                r12.append(r0)
                java.lang.String r12 = r12.toString()
                r11.<init>(r12)
                r3.onError(r11)
                return
            L38:
                boolean r8 = r3.tryOnNext(r8)
                if (r8 == 0) goto L41
                r8 = 1
                long r6 = r6 + r8
            L41:
                int r2 = r2 + 1
                goto La
            L44:
                if (r2 != r1) goto L4e
                boolean r11 = r10.cancelled
                if (r11 != 0) goto L4d
                r3.onComplete()
            L4d:
                return
            L4e:
                long r11 = r10.get()
                int r8 = (r6 > r11 ? 1 : (r6 == r11 ? 0 : -1))
                if (r8 != 0) goto La
                r10.index = r2
                long r11 = -r6
                long r11 = r10.addAndGet(r11)
                int r6 = (r11 > r4 ? 1 : (r11 == r4 ? 0 : -1))
                if (r6 != 0) goto L9
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.rxjava3.internal.operators.flowable.FlowableFromArray.ArrayConditionalSubscription.slowPath(long):void");
        }
    }

    public static final class ArraySubscription<T> extends BaseArraySubscription<T> {
        private static final long serialVersionUID = 2587302975077663557L;
        final Subscriber<? super T> downstream;

        public ArraySubscription(Subscriber<? super T> actual, T[] array) {
            super(array);
            this.downstream = actual;
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableFromArray.BaseArraySubscription
        public void fastPath() {
            T[] tArr = this.array;
            int length = tArr.length;
            Subscriber<? super T> subscriber = this.downstream;
            for (int i2 = this.index; i2 != length; i2++) {
                if (this.cancelled) {
                    return;
                }
                T t2 = tArr[i2];
                if (t2 == null) {
                    subscriber.onError(new NullPointerException("The element at index " + i2 + " is null"));
                    return;
                }
                subscriber.onNext(t2);
            }
            if (this.cancelled) {
                return;
            }
            subscriber.onComplete();
        }

        /* JADX WARN: Code restructure failed: missing block: B:22:0x0053, code lost:
        
            r10.index = r2;
            r11 = addAndGet(-r6);
         */
        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableFromArray.BaseArraySubscription
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void slowPath(long r11) {
            /*
                r10 = this;
                T[] r0 = r10.array
                int r1 = r0.length
                int r2 = r10.index
                org.reactivestreams.Subscriber<? super T> r3 = r10.downstream
                r4 = 0
            L9:
                r6 = r4
            La:
                int r8 = (r6 > r11 ? 1 : (r6 == r11 ? 0 : -1))
                if (r8 == 0) goto L41
                if (r2 == r1) goto L41
                boolean r8 = r10.cancelled
                if (r8 == 0) goto L15
                return
            L15:
                r8 = r0[r2]
                if (r8 != 0) goto L38
                java.lang.NullPointerException r11 = new java.lang.NullPointerException
                java.lang.StringBuilder r12 = new java.lang.StringBuilder
                r12.<init>()
                java.lang.String r0 = "The element at index "
                r12.append(r0)
                r12.append(r2)
                java.lang.String r0 = " is null"
                r12.append(r0)
                java.lang.String r12 = r12.toString()
                r11.<init>(r12)
                r3.onError(r11)
                return
            L38:
                r3.onNext(r8)
                r8 = 1
                long r6 = r6 + r8
                int r2 = r2 + 1
                goto La
            L41:
                if (r2 != r1) goto L4b
                boolean r11 = r10.cancelled
                if (r11 != 0) goto L4a
                r3.onComplete()
            L4a:
                return
            L4b:
                long r11 = r10.get()
                int r8 = (r6 > r11 ? 1 : (r6 == r11 ? 0 : -1))
                if (r8 != 0) goto La
                r10.index = r2
                long r11 = -r6
                long r11 = r10.addAndGet(r11)
                int r6 = (r11 > r4 ? 1 : (r11 == r4 ? 0 : -1))
                if (r6 != 0) goto L9
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.rxjava3.internal.operators.flowable.FlowableFromArray.ArraySubscription.slowPath(long):void");
        }
    }

    public static abstract class BaseArraySubscription<T> extends BasicQueueSubscription<T> {
        private static final long serialVersionUID = -2252972430506210021L;
        final T[] array;
        volatile boolean cancelled;
        int index;

        public BaseArraySubscription(T[] array) {
            this.array = array;
        }

        @Override // org.reactivestreams.Subscription
        public final void cancel() {
            this.cancelled = true;
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        public final void clear() {
            this.index = this.array.length;
        }

        public abstract void fastPath();

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        public final boolean isEmpty() {
            return this.index == this.array.length;
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        @Nullable
        public final T poll() {
            int i2 = this.index;
            T[] tArr = this.array;
            if (i2 == tArr.length) {
                return null;
            }
            this.index = i2 + 1;
            T t2 = tArr[i2];
            Objects.requireNonNull(t2, "array element is null");
            return t2;
        }

        @Override // org.reactivestreams.Subscription
        public final void request(long n2) {
            if (SubscriptionHelper.validate(n2) && BackpressureHelper.add(this, n2) == 0) {
                if (n2 == Long.MAX_VALUE) {
                    fastPath();
                } else {
                    slowPath(n2);
                }
            }
        }

        @Override // io.reactivex.rxjava3.operators.QueueFuseable
        public final int requestFusion(int mode) {
            return mode & 1;
        }

        public abstract void slowPath(long r2);
    }

    public FlowableFromArray(T[] array) {
        this.array = array;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super T> s2) {
        if (s2 instanceof ConditionalSubscriber) {
            s2.onSubscribe(new ArrayConditionalSubscription((ConditionalSubscriber) s2, this.array));
        } else {
            s2.onSubscribe(new ArraySubscription(s2, this.array));
        }
    }
}
