package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.Nullable;
import io.reactivex.internal.observers.BasicIntQueueDisposable;

/* loaded from: classes8.dex */
public final class ObservableRangeLong extends Observable<Long> {
    private final long count;
    private final long start;

    public static final class RangeDisposable extends BasicIntQueueDisposable<Long> {
        private static final long serialVersionUID = 396518478098735504L;
        final Observer<? super Long> downstream;
        final long end;
        boolean fused;
        long index;

        public RangeDisposable(Observer<? super Long> observer, long j2, long j3) {
            this.downstream = observer;
            this.index = j2;
            this.end = j3;
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        public void clear() {
            this.index = this.end;
            lazySet(1);
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            set(1);
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return get() != 0;
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        public boolean isEmpty() {
            return this.index == this.end;
        }

        @Override // io.reactivex.internal.fuseable.QueueFuseable
        public int requestFusion(int i2) {
            if ((i2 & 1) == 0) {
                return 0;
            }
            this.fused = true;
            return 1;
        }

        public void run() {
            if (this.fused) {
                return;
            }
            Observer<? super Long> observer = this.downstream;
            long j2 = this.end;
            for (long j3 = this.index; j3 != j2 && get() == 0; j3++) {
                observer.onNext(Long.valueOf(j3));
            }
            if (get() == 0) {
                lazySet(1);
                observer.onComplete();
            }
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        @Nullable
        public Long poll() throws Exception {
            long j2 = this.index;
            if (j2 != this.end) {
                this.index = 1 + j2;
                return Long.valueOf(j2);
            }
            lazySet(1);
            return null;
        }
    }

    public ObservableRangeLong(long j2, long j3) {
        this.start = j2;
        this.count = j3;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super Long> observer) {
        long j2 = this.start;
        RangeDisposable rangeDisposable = new RangeDisposable(observer, j2, j2 + this.count);
        observer.onSubscribe(rangeDisposable);
        rangeDisposable.run();
    }
}
