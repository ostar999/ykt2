package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.functions.BiPredicate;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.observers.BasicFuseableObserver;

/* loaded from: classes8.dex */
public final class ObservableDistinctUntilChanged<T, K> extends AbstractObservableWithUpstream<T, T> {
    final BiPredicate<? super K, ? super K> comparer;
    final Function<? super T, K> keySelector;

    public static final class DistinctUntilChangedObserver<T, K> extends BasicFuseableObserver<T, T> {
        final BiPredicate<? super K, ? super K> comparer;
        boolean hasValue;
        final Function<? super T, K> keySelector;
        K last;

        public DistinctUntilChangedObserver(Observer<? super T> actual, Function<? super T, K> keySelector, BiPredicate<? super K, ? super K> comparer) {
            super(actual);
            this.keySelector = keySelector;
            this.comparer = comparer;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t2) {
            if (this.done) {
                return;
            }
            if (this.sourceMode != 0) {
                this.downstream.onNext(t2);
                return;
            }
            try {
                K kApply = this.keySelector.apply(t2);
                if (this.hasValue) {
                    boolean zTest = this.comparer.test(this.last, kApply);
                    this.last = kApply;
                    if (zTest) {
                        return;
                    }
                } else {
                    this.hasValue = true;
                    this.last = kApply;
                }
                this.downstream.onNext(t2);
            } catch (Throwable th) {
                fail(th);
            }
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        @Nullable
        public T poll() throws Throwable {
            while (true) {
                T tPoll = this.qd.poll();
                if (tPoll == null) {
                    return null;
                }
                K kApply = this.keySelector.apply(tPoll);
                if (!this.hasValue) {
                    this.hasValue = true;
                    this.last = kApply;
                    return tPoll;
                }
                if (!this.comparer.test(this.last, kApply)) {
                    this.last = kApply;
                    return tPoll;
                }
                this.last = kApply;
            }
        }

        @Override // io.reactivex.rxjava3.operators.QueueFuseable
        public int requestFusion(int mode) {
            return transitiveBoundaryFusion(mode);
        }
    }

    public ObservableDistinctUntilChanged(ObservableSource<T> source, Function<? super T, K> keySelector, BiPredicate<? super K, ? super K> comparer) {
        super(source);
        this.keySelector = keySelector;
        this.comparer = comparer;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super T> observer) {
        this.source.subscribe(new DistinctUntilChangedObserver(observer, this.keySelector, this.comparer));
    }
}
