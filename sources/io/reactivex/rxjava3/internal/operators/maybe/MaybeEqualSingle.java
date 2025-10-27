package io.reactivex.rxjava3.internal.operators.maybe;

import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.MaybeSource;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.BiPredicate;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes8.dex */
public final class MaybeEqualSingle<T> extends Single<Boolean> {
    final BiPredicate<? super T, ? super T> isEqual;
    final MaybeSource<? extends T> source1;
    final MaybeSource<? extends T> source2;

    public static final class EqualCoordinator<T> extends AtomicInteger implements Disposable {
        final SingleObserver<? super Boolean> downstream;
        final BiPredicate<? super T, ? super T> isEqual;
        final EqualObserver<T> observer1;
        final EqualObserver<T> observer2;

        public EqualCoordinator(SingleObserver<? super Boolean> actual, BiPredicate<? super T, ? super T> isEqual) {
            super(2);
            this.downstream = actual;
            this.isEqual = isEqual;
            this.observer1 = new EqualObserver<>(this);
            this.observer2 = new EqualObserver<>(this);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.observer1.dispose();
            this.observer2.dispose();
        }

        public void done() {
            if (decrementAndGet() == 0) {
                Object obj = this.observer1.value;
                Object obj2 = this.observer2.value;
                if (obj == null || obj2 == null) {
                    this.downstream.onSuccess(Boolean.valueOf(obj == null && obj2 == null));
                    return;
                }
                try {
                    this.downstream.onSuccess(Boolean.valueOf(this.isEqual.test(obj, obj2)));
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.downstream.onError(th);
                }
            }
        }

        public void error(EqualObserver<T> sender, Throwable ex) {
            if (getAndSet(0) <= 0) {
                RxJavaPlugins.onError(ex);
                return;
            }
            EqualObserver<T> equalObserver = this.observer1;
            if (sender == equalObserver) {
                this.observer2.dispose();
            } else {
                equalObserver.dispose();
            }
            this.downstream.onError(ex);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(this.observer1.get());
        }

        public void subscribe(MaybeSource<? extends T> source1, MaybeSource<? extends T> source2) {
            source1.subscribe(this.observer1);
            source2.subscribe(this.observer2);
        }
    }

    public static final class EqualObserver<T> extends AtomicReference<Disposable> implements MaybeObserver<T> {
        private static final long serialVersionUID = -3031974433025990931L;
        final EqualCoordinator<T> parent;
        Object value;

        public EqualObserver(EqualCoordinator<T> parent) {
            this.parent = parent;
        }

        public void dispose() {
            DisposableHelper.dispose(this);
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onComplete() {
            this.parent.done();
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onError(Throwable e2) {
            this.parent.error(this, e2);
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onSubscribe(Disposable d3) {
            DisposableHelper.setOnce(this, d3);
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.SingleObserver
        public void onSuccess(T value) {
            this.value = value;
            this.parent.done();
        }
    }

    public MaybeEqualSingle(MaybeSource<? extends T> source1, MaybeSource<? extends T> source2, BiPredicate<? super T, ? super T> isEqual) {
        this.source1 = source1;
        this.source2 = source2;
        this.isEqual = isEqual;
    }

    @Override // io.reactivex.rxjava3.core.Single
    public void subscribeActual(SingleObserver<? super Boolean> observer) {
        EqualCoordinator equalCoordinator = new EqualCoordinator(observer, this.isEqual);
        observer.onSubscribe(equalCoordinator);
        equalCoordinator.subscribe(this.source1, this.source2);
    }
}
