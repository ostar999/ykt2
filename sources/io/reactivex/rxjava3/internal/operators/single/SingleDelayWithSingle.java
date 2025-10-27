package io.reactivex.rxjava3.internal.operators.single;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.observers.ResumeSingleObserver;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes8.dex */
public final class SingleDelayWithSingle<T, U> extends Single<T> {
    final SingleSource<U> other;
    final SingleSource<T> source;

    public static final class OtherObserver<T, U> extends AtomicReference<Disposable> implements SingleObserver<U>, Disposable {
        private static final long serialVersionUID = -8565274649390031272L;
        final SingleObserver<? super T> downstream;
        final SingleSource<T> source;

        public OtherObserver(SingleObserver<? super T> actual, SingleSource<T> source) {
            this.downstream = actual;
            this.source = source;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(get());
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onError(Throwable e2) {
            this.downstream.onError(e2);
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onSubscribe(Disposable d3) {
            if (DisposableHelper.setOnce(this, d3)) {
                this.downstream.onSubscribe(this);
            }
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onSuccess(U value) {
            this.source.subscribe(new ResumeSingleObserver(this, this.downstream));
        }
    }

    public SingleDelayWithSingle(SingleSource<T> source, SingleSource<U> other) {
        this.source = source;
        this.other = other;
    }

    @Override // io.reactivex.rxjava3.core.Single
    public void subscribeActual(SingleObserver<? super T> observer) {
        this.other.subscribe(new OtherObserver(observer, this.source));
    }
}
