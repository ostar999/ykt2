package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.annotations.Nullable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.observers.BasicFuseableObserver;

/* loaded from: classes8.dex */
public final class ObservableDoAfterNext<T> extends AbstractObservableWithUpstream<T, T> {
    final Consumer<? super T> onAfterNext;

    public static final class DoAfterObserver<T> extends BasicFuseableObserver<T, T> {
        final Consumer<? super T> onAfterNext;

        public DoAfterObserver(Observer<? super T> observer, Consumer<? super T> consumer) {
            super(observer);
            this.onAfterNext = consumer;
        }

        @Override // io.reactivex.Observer
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

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        @Nullable
        public T poll() throws Exception {
            T tPoll = this.qd.poll();
            if (tPoll != null) {
                this.onAfterNext.accept(tPoll);
            }
            return tPoll;
        }

        @Override // io.reactivex.internal.fuseable.QueueFuseable
        public int requestFusion(int i2) {
            return transitiveBoundaryFusion(i2);
        }
    }

    public ObservableDoAfterNext(ObservableSource<T> observableSource, Consumer<? super T> consumer) {
        super(observableSource);
        this.onAfterNext = consumer;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super T> observer) {
        this.source.subscribe(new DoAfterObserver(observer, this.onAfterNext));
    }
}
