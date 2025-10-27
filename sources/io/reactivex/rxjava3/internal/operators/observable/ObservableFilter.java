package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.internal.observers.BasicFuseableObserver;

/* loaded from: classes8.dex */
public final class ObservableFilter<T> extends AbstractObservableWithUpstream<T, T> {
    final Predicate<? super T> predicate;

    public static final class FilterObserver<T> extends BasicFuseableObserver<T, T> {
        final Predicate<? super T> filter;

        public FilterObserver(Observer<? super T> actual, Predicate<? super T> filter) {
            super(actual);
            this.filter = filter;
        }

        @Override // io.reactivex.rxjava3.core.Observer
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

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        @Nullable
        public T poll() throws Throwable {
            T tPoll;
            do {
                tPoll = this.qd.poll();
                if (tPoll == null) {
                    break;
                }
            } while (!this.filter.test(tPoll));
            return tPoll;
        }

        @Override // io.reactivex.rxjava3.operators.QueueFuseable
        public int requestFusion(int mode) {
            return transitiveBoundaryFusion(mode);
        }
    }

    public ObservableFilter(ObservableSource<T> source, Predicate<? super T> predicate) {
        super(source);
        this.predicate = predicate;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super T> observer) {
        this.source.subscribe(new FilterObserver(observer, this.predicate));
    }
}
