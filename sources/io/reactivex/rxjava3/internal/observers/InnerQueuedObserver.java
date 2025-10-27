package io.reactivex.rxjava3.internal.observers;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.util.QueueDrainHelper;
import io.reactivex.rxjava3.operators.QueueDisposable;
import io.reactivex.rxjava3.operators.SimpleQueue;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes8.dex */
public final class InnerQueuedObserver<T> extends AtomicReference<Disposable> implements Observer<T>, Disposable {
    private static final long serialVersionUID = -5417183359794346637L;
    volatile boolean done;
    int fusionMode;
    final InnerQueuedObserverSupport<T> parent;
    final int prefetch;
    SimpleQueue<T> queue;

    public InnerQueuedObserver(InnerQueuedObserverSupport<T> parent, int prefetch) {
        this.parent = parent;
        this.prefetch = prefetch;
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public void dispose() {
        DisposableHelper.dispose(this);
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public boolean isDisposed() {
        return DisposableHelper.isDisposed(get());
    }

    public boolean isDone() {
        return this.done;
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onComplete() {
        this.parent.innerComplete(this);
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onError(Throwable t2) {
        this.parent.innerError(this, t2);
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onNext(T t2) {
        if (this.fusionMode == 0) {
            this.parent.innerNext(this, t2);
        } else {
            this.parent.drain();
        }
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onSubscribe(Disposable d3) {
        if (DisposableHelper.setOnce(this, d3)) {
            if (d3 instanceof QueueDisposable) {
                QueueDisposable queueDisposable = (QueueDisposable) d3;
                int iRequestFusion = queueDisposable.requestFusion(3);
                if (iRequestFusion == 1) {
                    this.fusionMode = iRequestFusion;
                    this.queue = queueDisposable;
                    this.done = true;
                    this.parent.innerComplete(this);
                    return;
                }
                if (iRequestFusion == 2) {
                    this.fusionMode = iRequestFusion;
                    this.queue = queueDisposable;
                    return;
                }
            }
            this.queue = QueueDrainHelper.createQueue(-this.prefetch);
        }
    }

    public SimpleQueue<T> queue() {
        return this.queue;
    }

    public void setDone() {
        this.done = true;
    }
}
