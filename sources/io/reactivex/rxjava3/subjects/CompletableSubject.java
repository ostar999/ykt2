package io.reactivex.rxjava3.subjects;

import androidx.camera.view.j;
import io.reactivex.rxjava3.annotations.CheckReturnValue;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes8.dex */
public final class CompletableSubject extends Completable implements CompletableObserver {
    static final CompletableDisposable[] EMPTY = new CompletableDisposable[0];
    static final CompletableDisposable[] TERMINATED = new CompletableDisposable[0];
    Throwable error;
    final AtomicBoolean once = new AtomicBoolean();
    final AtomicReference<CompletableDisposable[]> observers = new AtomicReference<>(EMPTY);

    public static final class CompletableDisposable extends AtomicReference<CompletableSubject> implements Disposable {
        private static final long serialVersionUID = -7650903191002190468L;
        final CompletableObserver downstream;

        public CompletableDisposable(CompletableObserver actual, CompletableSubject parent) {
            this.downstream = actual;
            lazySet(parent);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            CompletableSubject andSet = getAndSet(null);
            if (andSet != null) {
                andSet.remove(this);
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return get() == null;
        }
    }

    @CheckReturnValue
    @NonNull
    public static CompletableSubject create() {
        return new CompletableSubject();
    }

    public boolean add(CompletableDisposable inner) {
        CompletableDisposable[] completableDisposableArr;
        CompletableDisposable[] completableDisposableArr2;
        do {
            completableDisposableArr = this.observers.get();
            if (completableDisposableArr == TERMINATED) {
                return false;
            }
            int length = completableDisposableArr.length;
            completableDisposableArr2 = new CompletableDisposable[length + 1];
            System.arraycopy(completableDisposableArr, 0, completableDisposableArr2, 0, length);
            completableDisposableArr2[length] = inner;
        } while (!j.a(this.observers, completableDisposableArr, completableDisposableArr2));
        return true;
    }

    @Nullable
    public Throwable getThrowable() {
        if (this.observers.get() == TERMINATED) {
            return this.error;
        }
        return null;
    }

    public boolean hasComplete() {
        return this.observers.get() == TERMINATED && this.error == null;
    }

    public boolean hasObservers() {
        return this.observers.get().length != 0;
    }

    public boolean hasThrowable() {
        return this.observers.get() == TERMINATED && this.error != null;
    }

    public int observerCount() {
        return this.observers.get().length;
    }

    @Override // io.reactivex.rxjava3.core.CompletableObserver
    public void onComplete() {
        if (this.once.compareAndSet(false, true)) {
            for (CompletableDisposable completableDisposable : this.observers.getAndSet(TERMINATED)) {
                completableDisposable.downstream.onComplete();
            }
        }
    }

    @Override // io.reactivex.rxjava3.core.CompletableObserver
    public void onError(Throwable e2) {
        ExceptionHelper.nullCheck(e2, "onError called with a null Throwable.");
        if (!this.once.compareAndSet(false, true)) {
            RxJavaPlugins.onError(e2);
            return;
        }
        this.error = e2;
        for (CompletableDisposable completableDisposable : this.observers.getAndSet(TERMINATED)) {
            completableDisposable.downstream.onError(e2);
        }
    }

    @Override // io.reactivex.rxjava3.core.CompletableObserver
    public void onSubscribe(Disposable d3) {
        if (this.observers.get() == TERMINATED) {
            d3.dispose();
        }
    }

    public void remove(CompletableDisposable inner) {
        CompletableDisposable[] completableDisposableArr;
        CompletableDisposable[] completableDisposableArr2;
        do {
            completableDisposableArr = this.observers.get();
            int length = completableDisposableArr.length;
            if (length == 0) {
                return;
            }
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    i2 = -1;
                    break;
                } else if (completableDisposableArr[i2] == inner) {
                    break;
                } else {
                    i2++;
                }
            }
            if (i2 < 0) {
                return;
            }
            if (length == 1) {
                completableDisposableArr2 = EMPTY;
            } else {
                CompletableDisposable[] completableDisposableArr3 = new CompletableDisposable[length - 1];
                System.arraycopy(completableDisposableArr, 0, completableDisposableArr3, 0, i2);
                System.arraycopy(completableDisposableArr, i2 + 1, completableDisposableArr3, i2, (length - i2) - 1);
                completableDisposableArr2 = completableDisposableArr3;
            }
        } while (!j.a(this.observers, completableDisposableArr, completableDisposableArr2));
    }

    @Override // io.reactivex.rxjava3.core.Completable
    public void subscribeActual(CompletableObserver observer) {
        CompletableDisposable completableDisposable = new CompletableDisposable(observer, this);
        observer.onSubscribe(completableDisposable);
        if (add(completableDisposable)) {
            if (completableDisposable.isDisposed()) {
                remove(completableDisposable);
            }
        } else {
            Throwable th = this.error;
            if (th != null) {
                observer.onError(th);
            } else {
                observer.onComplete();
            }
        }
    }
}
