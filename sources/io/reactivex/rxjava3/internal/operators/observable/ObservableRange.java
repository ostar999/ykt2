package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.internal.observers.BasicIntQueueDisposable;

/* loaded from: classes8.dex */
public final class ObservableRange extends Observable<Integer> {
    private final long end;
    private final int start;

    public static final class RangeDisposable extends BasicIntQueueDisposable<Integer> {
        private static final long serialVersionUID = 396518478098735504L;
        final Observer<? super Integer> downstream;
        final long end;
        boolean fused;
        long index;

        public RangeDisposable(Observer<? super Integer> actual, long start, long end) {
            this.downstream = actual;
            this.index = start;
            this.end = end;
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        public void clear() {
            this.index = this.end;
            lazySet(1);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            set(1);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return get() != 0;
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        public boolean isEmpty() {
            return this.index == this.end;
        }

        @Override // io.reactivex.rxjava3.operators.QueueFuseable
        public int requestFusion(int mode) {
            if ((mode & 1) == 0) {
                return 0;
            }
            this.fused = true;
            return 1;
        }

        public void run() {
            if (this.fused) {
                return;
            }
            Observer<? super Integer> observer = this.downstream;
            long j2 = this.end;
            for (long j3 = this.index; j3 != j2 && get() == 0; j3++) {
                observer.onNext(Integer.valueOf((int) j3));
            }
            if (get() == 0) {
                lazySet(1);
                observer.onComplete();
            }
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        @Nullable
        public Integer poll() {
            long j2 = this.index;
            if (j2 != this.end) {
                this.index = 1 + j2;
                return Integer.valueOf((int) j2);
            }
            lazySet(1);
            return null;
        }
    }

    public ObservableRange(int start, int count) {
        this.start = start;
        this.end = start + count;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super Integer> o2) {
        RangeDisposable rangeDisposable = new RangeDisposable(o2, this.start, this.end);
        o2.onSubscribe(rangeDisposable);
        rangeDisposable.run();
    }
}
