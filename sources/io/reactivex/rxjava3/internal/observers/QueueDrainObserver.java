package io.reactivex.rxjava3.internal.observers;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.util.ObservableQueueDrain;
import io.reactivex.rxjava3.internal.util.QueueDrainHelper;
import io.reactivex.rxjava3.operators.SimplePlainQueue;

/* loaded from: classes8.dex */
public abstract class QueueDrainObserver<T, U, V> extends QueueDrainSubscriberPad2 implements Observer<T>, ObservableQueueDrain<U, V> {
    protected volatile boolean cancelled;
    protected volatile boolean done;
    protected final Observer<? super V> downstream;
    protected Throwable error;
    protected final SimplePlainQueue<U> queue;

    public QueueDrainObserver(Observer<? super V> actual, SimplePlainQueue<U> queue) {
        this.downstream = actual;
        this.queue = queue;
    }

    @Override // io.reactivex.rxjava3.internal.util.ObservableQueueDrain
    public void accept(Observer<? super V> a3, U v2) {
    }

    @Override // io.reactivex.rxjava3.internal.util.ObservableQueueDrain
    public final boolean cancelled() {
        return this.cancelled;
    }

    @Override // io.reactivex.rxjava3.internal.util.ObservableQueueDrain
    public final boolean done() {
        return this.done;
    }

    @Override // io.reactivex.rxjava3.internal.util.ObservableQueueDrain
    public final boolean enter() {
        return this.wip.getAndIncrement() == 0;
    }

    @Override // io.reactivex.rxjava3.internal.util.ObservableQueueDrain
    public final Throwable error() {
        return this.error;
    }

    public final void fastPathEmit(U value, boolean delayError, Disposable dispose) {
        Observer<? super V> observer = this.downstream;
        SimplePlainQueue<U> simplePlainQueue = this.queue;
        if (this.wip.get() == 0 && this.wip.compareAndSet(0, 1)) {
            accept(observer, value);
            if (leave(-1) == 0) {
                return;
            }
        } else {
            simplePlainQueue.offer(value);
            if (!enter()) {
                return;
            }
        }
        QueueDrainHelper.drainLoop(simplePlainQueue, observer, delayError, dispose, this);
    }

    public final void fastPathOrderedEmit(U value, boolean delayError, Disposable disposable) {
        Observer<? super V> observer = this.downstream;
        SimplePlainQueue<U> simplePlainQueue = this.queue;
        if (this.wip.get() != 0 || !this.wip.compareAndSet(0, 1)) {
            simplePlainQueue.offer(value);
            if (!enter()) {
                return;
            }
        } else if (simplePlainQueue.isEmpty()) {
            accept(observer, value);
            if (leave(-1) == 0) {
                return;
            }
        } else {
            simplePlainQueue.offer(value);
        }
        QueueDrainHelper.drainLoop(simplePlainQueue, observer, delayError, disposable, this);
    }

    @Override // io.reactivex.rxjava3.internal.util.ObservableQueueDrain
    public final int leave(int m2) {
        return this.wip.addAndGet(m2);
    }
}
