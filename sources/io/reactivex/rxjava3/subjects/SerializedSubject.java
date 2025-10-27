package io.reactivex.rxjava3.subjects;

import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.util.AppendOnlyLinkedArrayList;
import io.reactivex.rxjava3.internal.util.NotificationLite;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;

/* loaded from: classes8.dex */
final class SerializedSubject<T> extends Subject<T> implements AppendOnlyLinkedArrayList.NonThrowingPredicate<Object> {
    final Subject<T> actual;
    volatile boolean done;
    boolean emitting;
    AppendOnlyLinkedArrayList<Object> queue;

    public SerializedSubject(final Subject<T> actual) {
        this.actual = actual;
    }

    public void emitLoop() {
        AppendOnlyLinkedArrayList<Object> appendOnlyLinkedArrayList;
        while (true) {
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

    @Override // io.reactivex.rxjava3.subjects.Subject
    @Nullable
    public Throwable getThrowable() {
        return this.actual.getThrowable();
    }

    @Override // io.reactivex.rxjava3.subjects.Subject
    public boolean hasComplete() {
        return this.actual.hasComplete();
    }

    @Override // io.reactivex.rxjava3.subjects.Subject
    public boolean hasObservers() {
        return this.actual.hasObservers();
    }

    @Override // io.reactivex.rxjava3.subjects.Subject
    public boolean hasThrowable() {
        return this.actual.hasThrowable();
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
            this.done = true;
            if (!this.emitting) {
                this.emitting = true;
                this.actual.onComplete();
                return;
            }
            AppendOnlyLinkedArrayList<Object> appendOnlyLinkedArrayList = this.queue;
            if (appendOnlyLinkedArrayList == null) {
                appendOnlyLinkedArrayList = new AppendOnlyLinkedArrayList<>(4);
                this.queue = appendOnlyLinkedArrayList;
            }
            appendOnlyLinkedArrayList.add(NotificationLite.complete());
        }
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onError(Throwable t2) {
        if (this.done) {
            RxJavaPlugins.onError(t2);
            return;
        }
        synchronized (this) {
            boolean z2 = true;
            if (!this.done) {
                this.done = true;
                if (this.emitting) {
                    AppendOnlyLinkedArrayList<Object> appendOnlyLinkedArrayList = this.queue;
                    if (appendOnlyLinkedArrayList == null) {
                        appendOnlyLinkedArrayList = new AppendOnlyLinkedArrayList<>(4);
                        this.queue = appendOnlyLinkedArrayList;
                    }
                    appendOnlyLinkedArrayList.setFirst(NotificationLite.error(t2));
                    return;
                }
                this.emitting = true;
                z2 = false;
            }
            if (z2) {
                RxJavaPlugins.onError(t2);
            } else {
                this.actual.onError(t2);
            }
        }
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onNext(T t2) {
        if (this.done) {
            return;
        }
        synchronized (this) {
            if (this.done) {
                return;
            }
            if (!this.emitting) {
                this.emitting = true;
                this.actual.onNext(t2);
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
    public void onSubscribe(Disposable d3) {
        boolean z2 = true;
        if (!this.done) {
            synchronized (this) {
                if (!this.done) {
                    if (this.emitting) {
                        AppendOnlyLinkedArrayList<Object> appendOnlyLinkedArrayList = this.queue;
                        if (appendOnlyLinkedArrayList == null) {
                            appendOnlyLinkedArrayList = new AppendOnlyLinkedArrayList<>(4);
                            this.queue = appendOnlyLinkedArrayList;
                        }
                        appendOnlyLinkedArrayList.add(NotificationLite.disposable(d3));
                        return;
                    }
                    this.emitting = true;
                    z2 = false;
                }
            }
        }
        if (z2) {
            d3.dispose();
        } else {
            this.actual.onSubscribe(d3);
            emitLoop();
        }
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super T> observer) {
        this.actual.subscribe(observer);
    }

    @Override // io.reactivex.rxjava3.internal.util.AppendOnlyLinkedArrayList.NonThrowingPredicate, io.reactivex.rxjava3.functions.Predicate
    public boolean test(Object o2) {
        return NotificationLite.acceptFull(o2, this.actual);
    }
}
