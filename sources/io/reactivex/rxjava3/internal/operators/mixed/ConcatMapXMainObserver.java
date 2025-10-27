package io.reactivex.rxjava3.internal.operators.mixed;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.internal.util.ErrorMode;
import io.reactivex.rxjava3.operators.QueueDisposable;
import io.reactivex.rxjava3.operators.SimpleQueue;
import io.reactivex.rxjava3.operators.SpscLinkedArrayQueue;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes8.dex */
public abstract class ConcatMapXMainObserver<T> extends AtomicInteger implements Observer<T>, Disposable {
    private static final long serialVersionUID = -3214213361171757852L;
    volatile boolean disposed;
    volatile boolean done;
    final ErrorMode errorMode;
    final AtomicThrowable errors = new AtomicThrowable();
    final int prefetch;
    SimpleQueue<T> queue;
    Disposable upstream;

    public ConcatMapXMainObserver(int prefetch, ErrorMode errorMode) {
        this.errorMode = errorMode;
        this.prefetch = prefetch;
    }

    public void clearValue() {
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public final void dispose() {
        this.disposed = true;
        this.upstream.dispose();
        disposeInner();
        this.errors.tryTerminateAndReport();
        if (getAndIncrement() == 0) {
            this.queue.clear();
            clearValue();
        }
    }

    public abstract void disposeInner();

    public abstract void drain();

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public final boolean isDisposed() {
        return this.disposed;
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public final void onComplete() {
        this.done = true;
        drain();
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public final void onError(Throwable t2) {
        if (this.errors.tryAddThrowableOrReport(t2)) {
            if (this.errorMode == ErrorMode.IMMEDIATE) {
                disposeInner();
            }
            this.done = true;
            drain();
        }
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public final void onNext(T t2) {
        if (t2 != null) {
            this.queue.offer(t2);
        }
        drain();
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public final void onSubscribe(Disposable d3) {
        if (DisposableHelper.validate(this.upstream, d3)) {
            this.upstream = d3;
            if (d3 instanceof QueueDisposable) {
                QueueDisposable queueDisposable = (QueueDisposable) d3;
                int iRequestFusion = queueDisposable.requestFusion(7);
                if (iRequestFusion == 1) {
                    this.queue = queueDisposable;
                    this.done = true;
                    onSubscribeDownstream();
                    drain();
                    return;
                }
                if (iRequestFusion == 2) {
                    this.queue = queueDisposable;
                    onSubscribeDownstream();
                    return;
                }
            }
            this.queue = new SpscLinkedArrayQueue(this.prefetch);
            onSubscribeDownstream();
        }
    }

    public abstract void onSubscribeDownstream();
}
