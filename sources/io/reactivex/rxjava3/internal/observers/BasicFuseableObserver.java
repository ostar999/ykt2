package io.reactivex.rxjava3.internal.observers;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.operators.QueueDisposable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;

/* loaded from: classes8.dex */
public abstract class BasicFuseableObserver<T, R> implements Observer<T>, QueueDisposable<R> {
    protected boolean done;
    protected final Observer<? super R> downstream;
    protected QueueDisposable<T> qd;
    protected int sourceMode;
    protected Disposable upstream;

    public BasicFuseableObserver(Observer<? super R> downstream) {
        this.downstream = downstream;
    }

    public void afterDownstream() {
    }

    public boolean beforeDownstream() {
        return true;
    }

    @Override // io.reactivex.rxjava3.operators.SimpleQueue
    public void clear() {
        this.qd.clear();
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public void dispose() {
        this.upstream.dispose();
    }

    public final void fail(Throwable t2) {
        Exceptions.throwIfFatal(t2);
        this.upstream.dispose();
        onError(t2);
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public boolean isDisposed() {
        return this.upstream.isDisposed();
    }

    @Override // io.reactivex.rxjava3.operators.SimpleQueue
    public boolean isEmpty() {
        return this.qd.isEmpty();
    }

    @Override // io.reactivex.rxjava3.operators.SimpleQueue
    public final boolean offer(R e2) {
        throw new UnsupportedOperationException("Should not be called!");
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onComplete() {
        if (this.done) {
            return;
        }
        this.done = true;
        this.downstream.onComplete();
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onError(Throwable t2) {
        if (this.done) {
            RxJavaPlugins.onError(t2);
        } else {
            this.done = true;
            this.downstream.onError(t2);
        }
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public final void onSubscribe(Disposable d3) {
        if (DisposableHelper.validate(this.upstream, d3)) {
            this.upstream = d3;
            if (d3 instanceof QueueDisposable) {
                this.qd = (QueueDisposable) d3;
            }
            if (beforeDownstream()) {
                this.downstream.onSubscribe(this);
                afterDownstream();
            }
        }
    }

    public final int transitiveBoundaryFusion(int mode) {
        QueueDisposable<T> queueDisposable = this.qd;
        if (queueDisposable == null || (mode & 4) != 0) {
            return 0;
        }
        int iRequestFusion = queueDisposable.requestFusion(mode);
        if (iRequestFusion != 0) {
            this.sourceMode = iRequestFusion;
        }
        return iRequestFusion;
    }

    @Override // io.reactivex.rxjava3.operators.SimpleQueue
    public final boolean offer(R v12, R v2) {
        throw new UnsupportedOperationException("Should not be called!");
    }
}
