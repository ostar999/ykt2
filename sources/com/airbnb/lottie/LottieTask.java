package com.airbnb.lottie;

import android.os.Handler;
import android.os.Looper;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import com.airbnb.lottie.utils.Logger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/* loaded from: classes.dex */
public class LottieTask<T> {
    public static Executor EXECUTOR = Executors.newCachedThreadPool();
    private final Set<LottieListener<Throwable>> failureListeners;
    private final Handler handler;

    @Nullable
    private volatile LottieResult<T> result;
    private final Set<LottieListener<T>> successListeners;

    public class LottieFutureTask extends FutureTask<LottieResult<T>> {
        public LottieFutureTask(Callable<LottieResult<T>> callable) {
            super(callable);
        }

        @Override // java.util.concurrent.FutureTask
        public void done() {
            if (isCancelled()) {
                return;
            }
            try {
                LottieTask.this.setResult(get());
            } catch (InterruptedException | ExecutionException e2) {
                LottieTask.this.setResult(new LottieResult(e2));
            }
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public LottieTask(Callable<LottieResult<T>> callable) {
        this(callable, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void notifyFailureListeners(Throwable th) {
        ArrayList arrayList = new ArrayList(this.failureListeners);
        if (arrayList.isEmpty()) {
            Logger.warning("Lottie encountered an error but no failure listener was added:", th);
            return;
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((LottieListener) it.next()).onResult(th);
        }
    }

    private void notifyListeners() {
        this.handler.post(new Runnable() { // from class: com.airbnb.lottie.LottieTask.1
            @Override // java.lang.Runnable
            public void run() {
                if (LottieTask.this.result == null) {
                    return;
                }
                LottieResult lottieResult = LottieTask.this.result;
                if (lottieResult.getValue() != null) {
                    LottieTask.this.notifySuccessListeners(lottieResult.getValue());
                } else {
                    LottieTask.this.notifyFailureListeners(lottieResult.getException());
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void notifySuccessListeners(T t2) {
        Iterator it = new ArrayList(this.successListeners).iterator();
        while (it.hasNext()) {
            ((LottieListener) it.next()).onResult(t2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setResult(@Nullable LottieResult<T> lottieResult) {
        if (this.result != null) {
            throw new IllegalStateException("A task may only be set once.");
        }
        this.result = lottieResult;
        notifyListeners();
    }

    public synchronized LottieTask<T> addFailureListener(LottieListener<Throwable> lottieListener) {
        if (this.result != null && this.result.getException() != null) {
            lottieListener.onResult(this.result.getException());
        }
        this.failureListeners.add(lottieListener);
        return this;
    }

    public synchronized LottieTask<T> addListener(LottieListener<T> lottieListener) {
        if (this.result != null && this.result.getValue() != null) {
            lottieListener.onResult(this.result.getValue());
        }
        this.successListeners.add(lottieListener);
        return this;
    }

    public synchronized LottieTask<T> removeFailureListener(LottieListener<Throwable> lottieListener) {
        this.failureListeners.remove(lottieListener);
        return this;
    }

    public synchronized LottieTask<T> removeListener(LottieListener<T> lottieListener) {
        this.successListeners.remove(lottieListener);
        return this;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public LottieTask(Callable<LottieResult<T>> callable, boolean z2) {
        this.successListeners = new LinkedHashSet(1);
        this.failureListeners = new LinkedHashSet(1);
        this.handler = new Handler(Looper.getMainLooper());
        this.result = null;
        if (!z2) {
            EXECUTOR.execute(new LottieFutureTask(callable));
            return;
        }
        try {
            setResult(callable.call());
        } catch (Throwable th) {
            setResult(new LottieResult<>(th));
        }
    }
}
