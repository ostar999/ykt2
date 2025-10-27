package io.reactivex.internal.operators.mixed;

import androidx.camera.view.j;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes8.dex */
public final class ObservableSwitchMapCompletable<T> extends Completable {
    final boolean delayErrors;
    final Function<? super T, ? extends CompletableSource> mapper;
    final Observable<T> source;

    public static final class SwitchMapCompletableObserver<T> implements Observer<T>, Disposable {
        static final SwitchMapInnerObserver INNER_DISPOSED = new SwitchMapInnerObserver(null);
        final boolean delayErrors;
        volatile boolean done;
        final CompletableObserver downstream;
        final AtomicThrowable errors = new AtomicThrowable();
        final AtomicReference<SwitchMapInnerObserver> inner = new AtomicReference<>();
        final Function<? super T, ? extends CompletableSource> mapper;
        Disposable upstream;

        public static final class SwitchMapInnerObserver extends AtomicReference<Disposable> implements CompletableObserver {
            private static final long serialVersionUID = -8003404460084760287L;
            final SwitchMapCompletableObserver<?> parent;

            public SwitchMapInnerObserver(SwitchMapCompletableObserver<?> switchMapCompletableObserver) {
                this.parent = switchMapCompletableObserver;
            }

            public void dispose() {
                DisposableHelper.dispose(this);
            }

            @Override // io.reactivex.CompletableObserver, io.reactivex.MaybeObserver
            public void onComplete() {
                this.parent.innerComplete(this);
            }

            @Override // io.reactivex.CompletableObserver
            public void onError(Throwable th) {
                this.parent.innerError(this, th);
            }

            @Override // io.reactivex.CompletableObserver
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(this, disposable);
            }
        }

        public SwitchMapCompletableObserver(CompletableObserver completableObserver, Function<? super T, ? extends CompletableSource> function, boolean z2) {
            this.downstream = completableObserver;
            this.mapper = function;
            this.delayErrors = z2;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.upstream.dispose();
            disposeInner();
        }

        public void disposeInner() {
            AtomicReference<SwitchMapInnerObserver> atomicReference = this.inner;
            SwitchMapInnerObserver switchMapInnerObserver = INNER_DISPOSED;
            SwitchMapInnerObserver andSet = atomicReference.getAndSet(switchMapInnerObserver);
            if (andSet == null || andSet == switchMapInnerObserver) {
                return;
            }
            andSet.dispose();
        }

        public void innerComplete(SwitchMapInnerObserver switchMapInnerObserver) {
            if (j.a(this.inner, switchMapInnerObserver, null) && this.done) {
                Throwable thTerminate = this.errors.terminate();
                if (thTerminate == null) {
                    this.downstream.onComplete();
                } else {
                    this.downstream.onError(thTerminate);
                }
            }
        }

        public void innerError(SwitchMapInnerObserver switchMapInnerObserver, Throwable th) {
            if (!j.a(this.inner, switchMapInnerObserver, null) || !this.errors.addThrowable(th)) {
                RxJavaPlugins.onError(th);
                return;
            }
            if (this.delayErrors) {
                if (this.done) {
                    this.downstream.onError(this.errors.terminate());
                    return;
                }
                return;
            }
            dispose();
            Throwable thTerminate = this.errors.terminate();
            if (thTerminate != ExceptionHelper.TERMINATED) {
                this.downstream.onError(thTerminate);
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.inner.get() == INNER_DISPOSED;
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            this.done = true;
            if (this.inner.get() == null) {
                Throwable thTerminate = this.errors.terminate();
                if (thTerminate == null) {
                    this.downstream.onComplete();
                } else {
                    this.downstream.onError(thTerminate);
                }
            }
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            if (!this.errors.addThrowable(th)) {
                RxJavaPlugins.onError(th);
                return;
            }
            if (this.delayErrors) {
                onComplete();
                return;
            }
            disposeInner();
            Throwable thTerminate = this.errors.terminate();
            if (thTerminate != ExceptionHelper.TERMINATED) {
                this.downstream.onError(thTerminate);
            }
        }

        @Override // io.reactivex.Observer
        public void onNext(T t2) {
            SwitchMapInnerObserver switchMapInnerObserver;
            try {
                CompletableSource completableSource = (CompletableSource) ObjectHelper.requireNonNull(this.mapper.apply(t2), "The mapper returned a null CompletableSource");
                SwitchMapInnerObserver switchMapInnerObserver2 = new SwitchMapInnerObserver(this);
                do {
                    switchMapInnerObserver = this.inner.get();
                    if (switchMapInnerObserver == INNER_DISPOSED) {
                        return;
                    }
                } while (!j.a(this.inner, switchMapInnerObserver, switchMapInnerObserver2));
                if (switchMapInnerObserver != null) {
                    switchMapInnerObserver.dispose();
                }
                completableSource.subscribe(switchMapInnerObserver2);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.upstream.dispose();
                onError(th);
            }
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                this.downstream.onSubscribe(this);
            }
        }
    }

    public ObservableSwitchMapCompletable(Observable<T> observable, Function<? super T, ? extends CompletableSource> function, boolean z2) {
        this.source = observable;
        this.mapper = function;
        this.delayErrors = z2;
    }

    @Override // io.reactivex.Completable
    public void subscribeActual(CompletableObserver completableObserver) {
        if (ScalarXMapZHelper.tryAsCompletable(this.source, this.mapper, completableObserver)) {
            return;
        }
        this.source.subscribe(new SwitchMapCompletableObserver(completableObserver, this.mapper, this.delayErrors));
    }
}
