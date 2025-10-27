package io.reactivex.rxjava3.subjects;

import androidx.camera.view.j;
import io.reactivex.rxjava3.annotations.CheckReturnValue;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes8.dex */
public final class PublishSubject<T> extends Subject<T> {
    Throwable error;
    final AtomicReference<PublishDisposable<T>[]> subscribers = new AtomicReference<>(EMPTY);
    static final PublishDisposable[] TERMINATED = new PublishDisposable[0];
    static final PublishDisposable[] EMPTY = new PublishDisposable[0];

    public static final class PublishDisposable<T> extends AtomicBoolean implements Disposable {
        private static final long serialVersionUID = 3562861878281475070L;
        final Observer<? super T> downstream;
        final PublishSubject<T> parent;

        public PublishDisposable(Observer<? super T> actual, PublishSubject<T> parent) {
            this.downstream = actual;
            this.parent = parent;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            if (compareAndSet(false, true)) {
                this.parent.remove(this);
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return get();
        }

        public void onComplete() {
            if (get()) {
                return;
            }
            this.downstream.onComplete();
        }

        public void onError(Throwable t2) {
            if (get()) {
                RxJavaPlugins.onError(t2);
            } else {
                this.downstream.onError(t2);
            }
        }

        public void onNext(T t2) {
            if (get()) {
                return;
            }
            this.downstream.onNext(t2);
        }
    }

    @CheckReturnValue
    @NonNull
    public static <T> PublishSubject<T> create() {
        return new PublishSubject<>();
    }

    public boolean add(PublishDisposable<T> ps) {
        PublishDisposable<T>[] publishDisposableArr;
        PublishDisposable[] publishDisposableArr2;
        do {
            publishDisposableArr = this.subscribers.get();
            if (publishDisposableArr == TERMINATED) {
                return false;
            }
            int length = publishDisposableArr.length;
            publishDisposableArr2 = new PublishDisposable[length + 1];
            System.arraycopy(publishDisposableArr, 0, publishDisposableArr2, 0, length);
            publishDisposableArr2[length] = ps;
        } while (!j.a(this.subscribers, publishDisposableArr, publishDisposableArr2));
        return true;
    }

    @Override // io.reactivex.rxjava3.subjects.Subject
    @CheckReturnValue
    @Nullable
    public Throwable getThrowable() {
        if (this.subscribers.get() == TERMINATED) {
            return this.error;
        }
        return null;
    }

    @Override // io.reactivex.rxjava3.subjects.Subject
    @CheckReturnValue
    public boolean hasComplete() {
        return this.subscribers.get() == TERMINATED && this.error == null;
    }

    @Override // io.reactivex.rxjava3.subjects.Subject
    @CheckReturnValue
    public boolean hasObservers() {
        return this.subscribers.get().length != 0;
    }

    @Override // io.reactivex.rxjava3.subjects.Subject
    @CheckReturnValue
    public boolean hasThrowable() {
        return this.subscribers.get() == TERMINATED && this.error != null;
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onComplete() {
        PublishDisposable<T>[] publishDisposableArr = this.subscribers.get();
        PublishDisposable<T>[] publishDisposableArr2 = TERMINATED;
        if (publishDisposableArr == publishDisposableArr2) {
            return;
        }
        for (PublishDisposable<T> publishDisposable : this.subscribers.getAndSet(publishDisposableArr2)) {
            publishDisposable.onComplete();
        }
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onError(Throwable t2) {
        ExceptionHelper.nullCheck(t2, "onError called with a null Throwable.");
        PublishDisposable<T>[] publishDisposableArr = this.subscribers.get();
        PublishDisposable<T>[] publishDisposableArr2 = TERMINATED;
        if (publishDisposableArr == publishDisposableArr2) {
            RxJavaPlugins.onError(t2);
            return;
        }
        this.error = t2;
        for (PublishDisposable<T> publishDisposable : this.subscribers.getAndSet(publishDisposableArr2)) {
            publishDisposable.onError(t2);
        }
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onNext(T t2) {
        ExceptionHelper.nullCheck(t2, "onNext called with a null value.");
        for (PublishDisposable<T> publishDisposable : this.subscribers.get()) {
            publishDisposable.onNext(t2);
        }
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onSubscribe(Disposable d3) {
        if (this.subscribers.get() == TERMINATED) {
            d3.dispose();
        }
    }

    public void remove(PublishDisposable<T> ps) {
        PublishDisposable<T>[] publishDisposableArr;
        PublishDisposable[] publishDisposableArr2;
        do {
            publishDisposableArr = this.subscribers.get();
            if (publishDisposableArr == TERMINATED || publishDisposableArr == EMPTY) {
                return;
            }
            int length = publishDisposableArr.length;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    i2 = -1;
                    break;
                } else if (publishDisposableArr[i2] == ps) {
                    break;
                } else {
                    i2++;
                }
            }
            if (i2 < 0) {
                return;
            }
            if (length == 1) {
                publishDisposableArr2 = EMPTY;
            } else {
                PublishDisposable[] publishDisposableArr3 = new PublishDisposable[length - 1];
                System.arraycopy(publishDisposableArr, 0, publishDisposableArr3, 0, i2);
                System.arraycopy(publishDisposableArr, i2 + 1, publishDisposableArr3, i2, (length - i2) - 1);
                publishDisposableArr2 = publishDisposableArr3;
            }
        } while (!j.a(this.subscribers, publishDisposableArr, publishDisposableArr2));
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super T> t2) {
        PublishDisposable<T> publishDisposable = new PublishDisposable<>(t2, this);
        t2.onSubscribe(publishDisposable);
        if (add(publishDisposable)) {
            if (publishDisposable.isDisposed()) {
                remove(publishDisposable);
            }
        } else {
            Throwable th = this.error;
            if (th != null) {
                t2.onError(th);
            } else {
                t2.onComplete();
            }
        }
    }
}
