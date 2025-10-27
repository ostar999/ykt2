package io.reactivex.rxjava3.internal.operators.mixed;

import androidx.camera.view.j;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Objects;
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

            public SwitchMapInnerObserver(SwitchMapCompletableObserver<?> parent) {
                this.parent = parent;
            }

            public void dispose() {
                DisposableHelper.dispose(this);
            }

            @Override // io.reactivex.rxjava3.core.CompletableObserver
            public void onComplete() {
                this.parent.innerComplete(this);
            }

            @Override // io.reactivex.rxjava3.core.CompletableObserver
            public void onError(Throwable e2) {
                this.parent.innerError(this, e2);
            }

            @Override // io.reactivex.rxjava3.core.CompletableObserver
            public void onSubscribe(Disposable d3) {
                DisposableHelper.setOnce(this, d3);
            }
        }

        public SwitchMapCompletableObserver(CompletableObserver downstream, Function<? super T, ? extends CompletableSource> mapper, boolean delayErrors) {
            this.downstream = downstream;
            this.mapper = mapper;
            this.delayErrors = delayErrors;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.upstream.dispose();
            disposeInner();
            this.errors.tryTerminateAndReport();
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

        public void innerComplete(SwitchMapInnerObserver sender) {
            if (j.a(this.inner, sender, null) && this.done) {
                this.errors.tryTerminateConsumer(this.downstream);
            }
        }

        public void innerError(SwitchMapInnerObserver sender, Throwable error) {
            if (!j.a(this.inner, sender, null)) {
                RxJavaPlugins.onError(error);
                return;
            }
            if (this.errors.tryAddThrowableOrReport(error)) {
                if (this.delayErrors) {
                    if (this.done) {
                        this.errors.tryTerminateConsumer(this.downstream);
                    }
                } else {
                    this.upstream.dispose();
                    disposeInner();
                    this.errors.tryTerminateConsumer(this.downstream);
                }
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.inner.get() == INNER_DISPOSED;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            this.done = true;
            if (this.inner.get() == null) {
                this.errors.tryTerminateConsumer(this.downstream);
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable t2) {
            if (this.errors.tryAddThrowableOrReport(t2)) {
                if (this.delayErrors) {
                    onComplete();
                } else {
                    disposeInner();
                    this.errors.tryTerminateConsumer(this.downstream);
                }
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t2) {
            SwitchMapInnerObserver switchMapInnerObserver;
            try {
                CompletableSource completableSourceApply = this.mapper.apply(t2);
                Objects.requireNonNull(completableSourceApply, "The mapper returned a null CompletableSource");
                CompletableSource completableSource = completableSourceApply;
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

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable d3) {
            if (DisposableHelper.validate(this.upstream, d3)) {
                this.upstream = d3;
                this.downstream.onSubscribe(this);
            }
        }
    }

    public ObservableSwitchMapCompletable(Observable<T> source, Function<? super T, ? extends CompletableSource> mapper, boolean delayErrors) {
        this.source = source;
        this.mapper = mapper;
        this.delayErrors = delayErrors;
    }

    @Override // io.reactivex.rxjava3.core.Completable
    public void subscribeActual(CompletableObserver observer) {
        if (ScalarXMapZHelper.tryAsCompletable(this.source, this.mapper, observer)) {
            return;
        }
        this.source.subscribe(new SwitchMapCompletableObserver(observer, this.mapper, this.delayErrors));
    }
}
