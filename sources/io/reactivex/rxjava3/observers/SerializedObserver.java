package io.reactivex.rxjava3.observers;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.util.AppendOnlyLinkedArrayList;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.internal.util.NotificationLite;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;

/* loaded from: classes8.dex */
public final class SerializedObserver<T> implements Observer<T>, Disposable {
    static final int QUEUE_LINK_SIZE = 4;
    final boolean delayError;
    volatile boolean done;
    final Observer<? super T> downstream;
    boolean emitting;
    AppendOnlyLinkedArrayList<Object> queue;
    Disposable upstream;

    public SerializedObserver(@NonNull Observer<? super T> downstream) {
        this(downstream, false);
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public void dispose() {
        this.done = true;
        this.upstream.dispose();
    }

    public void emitLoop() {
        AppendOnlyLinkedArrayList<Object> appendOnlyLinkedArrayList;
        do {
            synchronized (this) {
                appendOnlyLinkedArrayList = this.queue;
                if (appendOnlyLinkedArrayList == null) {
                    this.emitting = false;
                    return;
                }
                this.queue = null;
            }
        } while (!appendOnlyLinkedArrayList.accept(this.downstream));
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public boolean isDisposed() {
        return this.upstream.isDisposed();
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onComplete() {
        if (this.done) {
            return;
        }
        synchronized (this) {
            if (this.done) {
                return;
            }
            if (!this.emitting) {
                this.done = true;
                this.emitting = true;
                this.downstream.onComplete();
            } else {
                AppendOnlyLinkedArrayList<Object> appendOnlyLinkedArrayList = this.queue;
                if (appendOnlyLinkedArrayList == null) {
                    appendOnlyLinkedArrayList = new AppendOnlyLinkedArrayList<>(4);
                    this.queue = appendOnlyLinkedArrayList;
                }
                appendOnlyLinkedArrayList.add(NotificationLite.complete());
            }
        }
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onError(@NonNull Throwable t2) {
        if (this.done) {
            RxJavaPlugins.onError(t2);
            return;
        }
        synchronized (this) {
            boolean z2 = true;
            if (!this.done) {
                if (this.emitting) {
                    this.done = true;
                    AppendOnlyLinkedArrayList<Object> appendOnlyLinkedArrayList = this.queue;
                    if (appendOnlyLinkedArrayList == null) {
                        appendOnlyLinkedArrayList = new AppendOnlyLinkedArrayList<>(4);
                        this.queue = appendOnlyLinkedArrayList;
                    }
                    Object objError = NotificationLite.error(t2);
                    if (this.delayError) {
                        appendOnlyLinkedArrayList.add(objError);
                    } else {
                        appendOnlyLinkedArrayList.setFirst(objError);
                    }
                    return;
                }
                this.done = true;
                this.emitting = true;
                z2 = false;
            }
            if (z2) {
                RxJavaPlugins.onError(t2);
            } else {
                this.downstream.onError(t2);
            }
        }
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onNext(@NonNull T t2) {
        if (this.done) {
            return;
        }
        if (t2 == null) {
            this.upstream.dispose();
            onError(ExceptionHelper.createNullPointerException("onNext called with a null value."));
            return;
        }
        synchronized (this) {
            if (this.done) {
                return;
            }
            if (!this.emitting) {
                this.emitting = true;
                this.downstream.onNext(t2);
                emitLoop();
            } else {
                AppendOnlyLinkedArrayList<Object> appendOnlyLinkedArrayList = this.queue;
                if (appendOnlyLinkedArrayList == null) {
                    appendOnlyLinkedArrayList = new AppendOnlyLinkedArrayList<>(4);
                    this.queue = appendOnlyLinkedArrayList;
                }
                appendOnlyLinkedArrayList.add(NotificationLite.next(t2));
            }
        }
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onSubscribe(@NonNull Disposable d3) {
        if (DisposableHelper.validate(this.upstream, d3)) {
            this.upstream = d3;
            this.downstream.onSubscribe(this);
        }
    }

    public SerializedObserver(@NonNull Observer<? super T> actual, boolean delayError) {
        this.downstream = actual;
        this.delayError = delayError;
    }
}
