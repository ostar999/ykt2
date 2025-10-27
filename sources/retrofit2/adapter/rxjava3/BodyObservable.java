package retrofit2.adapter.rxjava3;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.CompositeException;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import retrofit2.Response;

/* loaded from: classes9.dex */
final class BodyObservable<T> extends Observable<T> {
    private final Observable<Response<T>> upstream;

    public static class BodyObserver<R> implements Observer<Response<R>> {
        private final Observer<? super R> observer;
        private boolean terminated;

        public BodyObserver(Observer<? super R> observer) {
            this.observer = observer;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            if (this.terminated) {
                return;
            }
            this.observer.onComplete();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable th) {
            if (!this.terminated) {
                this.observer.onError(th);
                return;
            }
            AssertionError assertionError = new AssertionError("This should never happen! Report as a bug with the full stacktrace.");
            assertionError.initCause(th);
            RxJavaPlugins.onError(assertionError);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable disposable) {
            this.observer.onSubscribe(disposable);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(Response<R> response) {
            if (response.isSuccessful()) {
                this.observer.onNext(response.body());
                return;
            }
            this.terminated = true;
            HttpException httpException = new HttpException(response);
            try {
                this.observer.onError(httpException);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                RxJavaPlugins.onError(new CompositeException(httpException, th));
            }
        }
    }

    public BodyObservable(Observable<Response<T>> observable) {
        this.upstream = observable;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super T> observer) {
        this.upstream.subscribe(new BodyObserver(observer));
    }
}
