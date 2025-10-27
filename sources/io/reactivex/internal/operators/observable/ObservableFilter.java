package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.annotations.Nullable;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.observers.BasicFuseableObserver;

/* loaded from: classes8.dex */
public final class ObservableFilter<T> extends AbstractObservableWithUpstream<T, T> {
    final Predicate<? super T> predicate;

    public static final class FilterObserver<T> extends BasicFuseableObserver<T, T> {
        final Predicate<? super T> filter;

        public FilterObserver(Observer<? super T> observer, Predicate<? super T> predicate) {
            super(observer);
            this.filter = predicate;
        }

        @Override // io.reactivex.Observer
        public void onNext(T t2) {
            if (this.sourceMode != 0) {
                this.downstream.onNext(null);
                return;
            }
            try {
                if (this.filter.test(t2)) {
                    this.downstream.onNext(t2);
                }
            } catch (Throwable th) {
                fail(th);
            }
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        @Nullable
        public T poll() throws Exception {
            T tPoll;
            do {
                tPoll = this.qd.poll();
                if (tPoll == null) {
                    break;
                }
            } while (!this.filter.test(tPoll));
            return tPoll;
        }

        @Override // io.reactivex.internal.fuseable.QueueFuseable
        public int requestFusion(int i2) {
            return transitiveBoundaryFusion(i2);
        }
    }

    public ObservableFilter(ObservableSource<T> observableSource, Predicate<? super T> predicate) {
        super(observableSource);
        this.predicate = predicate;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super T> observer) {
        this.source.subscribe(new FilterObserver(observer, this.predicate));
    }
}
