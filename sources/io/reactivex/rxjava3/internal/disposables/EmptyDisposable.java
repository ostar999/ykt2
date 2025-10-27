package io.reactivex.rxjava3.internal.disposables;

import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.operators.QueueDisposable;

/* loaded from: classes8.dex */
public enum EmptyDisposable implements QueueDisposable<Object> {
    INSTANCE,
    NEVER;

    public static void complete(Observer<?> observer) {
        observer.onSubscribe(INSTANCE);
        observer.onComplete();
    }

    public static void error(Throwable e2, Observer<?> observer) {
        observer.onSubscribe(INSTANCE);
        observer.onError(e2);
    }

    @Override // io.reactivex.rxjava3.operators.SimpleQueue
    public void clear() {
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public void dispose() {
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public boolean isDisposed() {
        return this == INSTANCE;
    }

    @Override // io.reactivex.rxjava3.operators.SimpleQueue
    public boolean isEmpty() {
        return true;
    }

    @Override // io.reactivex.rxjava3.operators.SimpleQueue
    public boolean offer(Object value) {
        throw new UnsupportedOperationException("Should not be called!");
    }

    @Override // io.reactivex.rxjava3.operators.SimpleQueue
    @Nullable
    public Object poll() {
        return null;
    }

    @Override // io.reactivex.rxjava3.operators.QueueFuseable
    public int requestFusion(int mode) {
        return mode & 2;
    }

    @Override // io.reactivex.rxjava3.operators.SimpleQueue
    public boolean offer(Object v12, Object v2) {
        throw new UnsupportedOperationException("Should not be called!");
    }

    public static void complete(MaybeObserver<?> observer) {
        observer.onSubscribe(INSTANCE);
        observer.onComplete();
    }

    public static void error(Throwable e2, CompletableObserver observer) {
        observer.onSubscribe(INSTANCE);
        observer.onError(e2);
    }

    public static void complete(CompletableObserver observer) {
        observer.onSubscribe(INSTANCE);
        observer.onComplete();
    }

    public static void error(Throwable e2, SingleObserver<?> observer) {
        observer.onSubscribe(INSTANCE);
        observer.onError(e2);
    }

    public static void error(Throwable e2, MaybeObserver<?> observer) {
        observer.onSubscribe(INSTANCE);
        observer.onError(e2);
    }
}
