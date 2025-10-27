package io.reactivex.internal.subscribers;

import com.google.android.exoplayer2.C;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public abstract class SinglePostCompleteSubscriber<T, R> extends AtomicLong implements FlowableSubscriber<T>, Subscription {
    static final long COMPLETE_MASK = Long.MIN_VALUE;
    static final long REQUEST_MASK = Long.MAX_VALUE;
    private static final long serialVersionUID = 7917814472626990048L;
    protected final Subscriber<? super R> downstream;
    protected long produced;
    protected Subscription upstream;
    protected R value;

    public SinglePostCompleteSubscriber(Subscriber<? super R> subscriber) {
        this.downstream = subscriber;
    }

    public void cancel() {
        this.upstream.cancel();
    }

    public final void complete(R r2) {
        long j2 = this.produced;
        if (j2 != 0) {
            BackpressureHelper.produced(this, j2);
        }
        while (true) {
            long j3 = get();
            if ((j3 & Long.MIN_VALUE) != 0) {
                onDrop(r2);
                return;
            }
            if ((j3 & Long.MAX_VALUE) != 0) {
                lazySet(C.TIME_UNSET);
                this.downstream.onNext(r2);
                this.downstream.onComplete();
                return;
            } else {
                this.value = r2;
                if (compareAndSet(0L, Long.MIN_VALUE)) {
                    return;
                } else {
                    this.value = null;
                }
            }
        }
    }

    public void onDrop(R r2) {
    }

    @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
    public void onSubscribe(Subscription subscription) {
        if (SubscriptionHelper.validate(this.upstream, subscription)) {
            this.upstream = subscription;
            this.downstream.onSubscribe(this);
        }
    }

    @Override // org.reactivestreams.Subscription
    public final void request(long j2) {
        long j3;
        if (SubscriptionHelper.validate(j2)) {
            do {
                j3 = get();
                if ((j3 & Long.MIN_VALUE) != 0) {
                    if (compareAndSet(Long.MIN_VALUE, C.TIME_UNSET)) {
                        this.downstream.onNext(this.value);
                        this.downstream.onComplete();
                        return;
                    }
                    return;
                }
            } while (!compareAndSet(j3, BackpressureHelper.addCap(j3, j2)));
            this.upstream.request(j2);
        }
    }
}
