package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.internal.observers.BasicFuseableObserver;

/* loaded from: classes8.dex */
public final class ObservableDoAfterNext<T> extends AbstractObservableWithUpstream<T, T> {
    final Consumer<? super T> onAfterNext;

    public static final class DoAfterObserver<T> extends BasicFuseableObserver<T, T> {
        final Consumer<? super T> onAfterNext;

        public DoAfterObserver(Observer<? super T> actual, Consumer<? super T> onAfterNext) {
            super(actual);
            this.onAfterNext = onAfterNext;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t2) {
            this.downstream.onNext(t2);
            if (this.sourceMode == 0) {
                try {
                    this.onAfterNext.accept(t2);
                } catch (Throwable th) {
                    fail(th);
                }
            }
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        @Nullable
        public T poll() throws Throwable {
            T tPoll = this.qd.poll();
            if (tPoll != null) {
                this.onAfterNext.accept(tPoll);
            }
            return tPoll;
        }

        @Override // io.reactivex.rxjava3.operators.QueueFuseable
        public int requestFusion(int mode) {
            return transitiveBoundaryFusion(mode);
        }
    }

    public ObservableDoAfterNext(ObservableSource<T> source, Consumer<? super T> onAfterNext) {
        super(source);
        this.onAfterNext = onAfterNext;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super T> observer) {
        this.source.subscribe(new DoAfterObserver(observer, this.onAfterNext));
    }
}
