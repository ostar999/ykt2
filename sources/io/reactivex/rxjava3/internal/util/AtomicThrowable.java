package io.reactivex.rxjava3.internal.util;

import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Emitter;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;

/* loaded from: classes8.dex */
public final class AtomicThrowable extends AtomicReference<Throwable> {
    private static final long serialVersionUID = 3949248817947090603L;

    public boolean isTerminated() {
        return get() == ExceptionHelper.TERMINATED;
    }

    public Throwable terminate() {
        return ExceptionHelper.terminate(this);
    }

    public boolean tryAddThrowable(Throwable t2) {
        return ExceptionHelper.addThrowable(this, t2);
    }

    public boolean tryAddThrowableOrReport(Throwable t2) {
        if (tryAddThrowable(t2)) {
            return true;
        }
        RxJavaPlugins.onError(t2);
        return false;
    }

    public void tryTerminateAndReport() {
        Throwable thTerminate = terminate();
        if (thTerminate == null || thTerminate == ExceptionHelper.TERMINATED) {
            return;
        }
        RxJavaPlugins.onError(thTerminate);
    }

    public void tryTerminateConsumer(Subscriber<?> consumer) {
        Throwable thTerminate = terminate();
        if (thTerminate == null) {
            consumer.onComplete();
        } else if (thTerminate != ExceptionHelper.TERMINATED) {
            consumer.onError(thTerminate);
        }
    }

    public void tryTerminateConsumer(Observer<?> consumer) {
        Throwable thTerminate = terminate();
        if (thTerminate == null) {
            consumer.onComplete();
        } else if (thTerminate != ExceptionHelper.TERMINATED) {
            consumer.onError(thTerminate);
        }
    }

    public void tryTerminateConsumer(MaybeObserver<?> consumer) {
        Throwable thTerminate = terminate();
        if (thTerminate == null) {
            consumer.onComplete();
        } else if (thTerminate != ExceptionHelper.TERMINATED) {
            consumer.onError(thTerminate);
        }
    }

    public void tryTerminateConsumer(SingleObserver<?> consumer) {
        Throwable thTerminate = terminate();
        if (thTerminate == null || thTerminate == ExceptionHelper.TERMINATED) {
            return;
        }
        consumer.onError(thTerminate);
    }

    public void tryTerminateConsumer(CompletableObserver consumer) {
        Throwable thTerminate = terminate();
        if (thTerminate == null) {
            consumer.onComplete();
        } else if (thTerminate != ExceptionHelper.TERMINATED) {
            consumer.onError(thTerminate);
        }
    }

    public void tryTerminateConsumer(Emitter<?> consumer) {
        Throwable thTerminate = terminate();
        if (thTerminate == null) {
            consumer.onComplete();
        } else if (thTerminate != ExceptionHelper.TERMINATED) {
            consumer.onError(thTerminate);
        }
    }
}
