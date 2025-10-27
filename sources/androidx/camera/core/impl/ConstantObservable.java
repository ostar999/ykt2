package androidx.camera.core.impl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.camera.core.impl.Observable;
import androidx.camera.core.impl.utils.futures.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

@RequiresApi(21)
/* loaded from: classes.dex */
public final class ConstantObservable<T> implements Observable<T> {
    private static final ConstantObservable<Object> NULL_OBSERVABLE = new ConstantObservable<>(null);
    private static final String TAG = "ConstantObservable";
    private final ListenableFuture<T> mValueFuture;

    private ConstantObservable(@Nullable T t2) {
        this.mValueFuture = Futures.immediateFuture(t2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addObserver$0(Observable.Observer observer) {
        try {
            observer.onNewData(this.mValueFuture.get());
        } catch (InterruptedException | ExecutionException e2) {
            observer.onError(e2);
        }
    }

    @NonNull
    public static <U> Observable<U> withValue(@Nullable U u2) {
        return u2 == null ? NULL_OBSERVABLE : new ConstantObservable(u2);
    }

    @Override // androidx.camera.core.impl.Observable
    public void addObserver(@NonNull Executor executor, @NonNull final Observable.Observer<? super T> observer) {
        this.mValueFuture.addListener(new Runnable() { // from class: androidx.camera.core.impl.o
            @Override // java.lang.Runnable
            public final void run() {
                this.f1554c.lambda$addObserver$0(observer);
            }
        }, executor);
    }

    @Override // androidx.camera.core.impl.Observable
    @NonNull
    public ListenableFuture<T> fetchData() {
        return this.mValueFuture;
    }

    @Override // androidx.camera.core.impl.Observable
    public void removeObserver(@NonNull Observable.Observer<? super T> observer) {
    }
}
