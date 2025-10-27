package retrofit2.adapter.rxjava3;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.CompositeException;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/* loaded from: classes9.dex */
final class CallEnqueueObservable<T> extends Observable<Response<T>> {
    private final Call<T> originalCall;

    public static final class CallCallback<T> implements Disposable, Callback<T> {
        private final Call<?> call;
        private volatile boolean disposed;
        private final Observer<? super Response<T>> observer;
        boolean terminated = false;

        public CallCallback(Call<?> call, Observer<? super Response<T>> observer) {
            this.call = call;
            this.observer = observer;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.disposed = true;
            this.call.cancel();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.disposed;
        }

        @Override // retrofit2.Callback
        public void onFailure(Call<T> call, Throwable th) {
            if (call.isCanceled()) {
                return;
            }
            try {
                this.observer.onError(th);
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                RxJavaPlugins.onError(new CompositeException(th, th2));
            }
        }

        @Override // retrofit2.Callback
        public void onResponse(Call<T> call, Response<T> response) {
            if (this.disposed) {
                return;
            }
            try {
                this.observer.onNext(response);
                if (this.disposed) {
                    return;
                }
                this.terminated = true;
                this.observer.onComplete();
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                if (this.terminated) {
                    RxJavaPlugins.onError(th);
                    return;
                }
                if (this.disposed) {
                    return;
                }
                try {
                    this.observer.onError(th);
                } catch (Throwable th2) {
                    Exceptions.throwIfFatal(th2);
                    RxJavaPlugins.onError(new CompositeException(th, th2));
                }
            }
        }
    }

    public CallEnqueueObservable(Call<T> call) {
        this.originalCall = call;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super Response<T>> observer) {
        Call<T> callClone = this.originalCall.clone();
        CallCallback callCallback = new CallCallback(callClone, observer);
        observer.onSubscribe(callCallback);
        if (callCallback.isDisposed()) {
            return;
        }
        callClone.enqueue(callCallback);
    }
}
