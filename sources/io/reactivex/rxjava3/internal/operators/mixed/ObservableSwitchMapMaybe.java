package io.reactivex.rxjava3.internal.operators.mixed;

import androidx.camera.view.j;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.MaybeSource;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes8.dex */
public final class ObservableSwitchMapMaybe<T, R> extends Observable<R> {
    final boolean delayErrors;
    final Function<? super T, ? extends MaybeSource<? extends R>> mapper;
    final Observable<T> source;

    public static final class SwitchMapMaybeMainObserver<T, R> extends AtomicInteger implements Observer<T>, Disposable {
        static final SwitchMapMaybeObserver<Object> INNER_DISPOSED = new SwitchMapMaybeObserver<>(null);
        private static final long serialVersionUID = -5402190102429853762L;
        volatile boolean cancelled;
        final boolean delayErrors;
        volatile boolean done;
        final Observer<? super R> downstream;
        final AtomicThrowable errors = new AtomicThrowable();
        final AtomicReference<SwitchMapMaybeObserver<R>> inner = new AtomicReference<>();
        final Function<? super T, ? extends MaybeSource<? extends R>> mapper;
        Disposable upstream;

        public static final class SwitchMapMaybeObserver<R> extends AtomicReference<Disposable> implements MaybeObserver<R> {
            private static final long serialVersionUID = 8042919737683345351L;
            volatile R item;
            final SwitchMapMaybeMainObserver<?, R> parent;

            public SwitchMapMaybeObserver(SwitchMapMaybeMainObserver<?, R> parent) {
                this.parent = parent;
            }

            public void dispose() {
                DisposableHelper.dispose(this);
            }

            @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.CompletableObserver
            public void onComplete() {
                this.parent.innerComplete(this);
            }

            @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
            public void onError(Throwable e2) {
                this.parent.innerError(this, e2);
            }

            @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
            public void onSubscribe(Disposable d3) {
                DisposableHelper.setOnce(this, d3);
            }

            @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.SingleObserver
            public void onSuccess(R t2) {
                this.item = t2;
                this.parent.drain();
            }
        }

        public SwitchMapMaybeMainObserver(Observer<? super R> downstream, Function<? super T, ? extends MaybeSource<? extends R>> mapper, boolean delayErrors) {
            this.downstream = downstream;
            this.mapper = mapper;
            this.delayErrors = delayErrors;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.cancelled = true;
            this.upstream.dispose();
            disposeInner();
            this.errors.tryTerminateAndReport();
        }

        public void disposeInner() {
            AtomicReference<SwitchMapMaybeObserver<R>> atomicReference = this.inner;
            SwitchMapMaybeObserver<Object> switchMapMaybeObserver = INNER_DISPOSED;
            SwitchMapMaybeObserver<Object> switchMapMaybeObserver2 = (SwitchMapMaybeObserver) atomicReference.getAndSet(switchMapMaybeObserver);
            if (switchMapMaybeObserver2 == null || switchMapMaybeObserver2 == switchMapMaybeObserver) {
                return;
            }
            switchMapMaybeObserver2.dispose();
        }

        public void drain() {
            if (getAndIncrement() != 0) {
                return;
            }
            Observer<? super R> observer = this.downstream;
            AtomicThrowable atomicThrowable = this.errors;
            AtomicReference<SwitchMapMaybeObserver<R>> atomicReference = this.inner;
            int iAddAndGet = 1;
            while (!this.cancelled) {
                if (atomicThrowable.get() != null && !this.delayErrors) {
                    atomicThrowable.tryTerminateConsumer(observer);
                    return;
                }
                boolean z2 = this.done;
                SwitchMapMaybeObserver<R> switchMapMaybeObserver = atomicReference.get();
                boolean z3 = switchMapMaybeObserver == null;
                if (z2 && z3) {
                    atomicThrowable.tryTerminateConsumer(observer);
                    return;
                } else if (z3 || switchMapMaybeObserver.item == null) {
                    iAddAndGet = addAndGet(-iAddAndGet);
                    if (iAddAndGet == 0) {
                        return;
                    }
                } else {
                    j.a(atomicReference, switchMapMaybeObserver, null);
                    observer.onNext(switchMapMaybeObserver.item);
                }
            }
        }

        public void innerComplete(SwitchMapMaybeObserver<R> sender) {
            if (j.a(this.inner, sender, null)) {
                drain();
            }
        }

        public void innerError(SwitchMapMaybeObserver<R> sender, Throwable ex) {
            if (!j.a(this.inner, sender, null)) {
                RxJavaPlugins.onError(ex);
            } else if (this.errors.tryAddThrowableOrReport(ex)) {
                if (!this.delayErrors) {
                    this.upstream.dispose();
                    disposeInner();
                }
                drain();
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            this.done = true;
            drain();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable t2) {
            if (this.errors.tryAddThrowableOrReport(t2)) {
                if (!this.delayErrors) {
                    disposeInner();
                }
                this.done = true;
                drain();
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t2) {
            SwitchMapMaybeObserver<R> switchMapMaybeObserver;
            SwitchMapMaybeObserver<R> switchMapMaybeObserver2 = this.inner.get();
            if (switchMapMaybeObserver2 != null) {
                switchMapMaybeObserver2.dispose();
            }
            try {
                MaybeSource<? extends R> maybeSourceApply = this.mapper.apply(t2);
                Objects.requireNonNull(maybeSourceApply, "The mapper returned a null MaybeSource");
                MaybeSource<? extends R> maybeSource = maybeSourceApply;
                SwitchMapMaybeObserver switchMapMaybeObserver3 = new SwitchMapMaybeObserver(this);
                do {
                    switchMapMaybeObserver = this.inner.get();
                    if (switchMapMaybeObserver == INNER_DISPOSED) {
                        return;
                    }
                } while (!j.a(this.inner, switchMapMaybeObserver, switchMapMaybeObserver3));
                maybeSource.subscribe(switchMapMaybeObserver3);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.upstream.dispose();
                this.inner.getAndSet(INNER_DISPOSED);
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

    public ObservableSwitchMapMaybe(Observable<T> source, Function<? super T, ? extends MaybeSource<? extends R>> mapper, boolean delayErrors) {
        this.source = source;
        this.mapper = mapper;
        this.delayErrors = delayErrors;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super R> observer) {
        if (ScalarXMapZHelper.tryAsMaybe(this.source, this.mapper, observer)) {
            return;
        }
        this.source.subscribe(new SwitchMapMaybeMainObserver(observer, this.mapper, this.delayErrors));
    }
}
