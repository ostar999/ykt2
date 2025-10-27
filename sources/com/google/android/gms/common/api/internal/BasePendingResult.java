package com.google.android.gms.common.api.internal;

import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.util.Pair;
import androidx.annotation.NonNull;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.ResultTransform;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.TransformedResult;
import com.google.android.gms.common.internal.ICancelToken;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.VisibleForTesting;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@KeepForSdk
@KeepName
/* loaded from: classes3.dex */
public abstract class BasePendingResult<R extends Result> extends PendingResult<R> {
    static final ThreadLocal<Boolean> zado = new zao();

    @KeepName
    private zaa mResultGuardian;
    private Status mStatus;
    private R zacl;
    private final Object zadp;
    private final CallbackHandler<R> zadq;
    private final WeakReference<GoogleApiClient> zadr;
    private final CountDownLatch zads;
    private final ArrayList<PendingResult.StatusListener> zadt;
    private ResultCallback<? super R> zadu;
    private final AtomicReference<zacq> zadv;
    private volatile boolean zadw;
    private boolean zadx;
    private boolean zady;
    private ICancelToken zadz;
    private volatile zack<R> zaea;
    private boolean zaeb;

    @VisibleForTesting
    public static class CallbackHandler<R extends Result> extends com.google.android.gms.internal.base.zar {
        public CallbackHandler() {
            this(Looper.getMainLooper());
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i2 = message.what;
            if (i2 != 1) {
                if (i2 == 2) {
                    ((BasePendingResult) message.obj).zab(Status.RESULT_TIMEOUT);
                    return;
                }
                StringBuilder sb = new StringBuilder(45);
                sb.append("Don't know how to handle message: ");
                sb.append(i2);
                Log.wtf("BasePendingResult", sb.toString(), new Exception());
                return;
            }
            Pair pair = (Pair) message.obj;
            ResultCallback resultCallback = (ResultCallback) pair.first;
            Result result = (Result) pair.second;
            try {
                resultCallback.onResult(result);
            } catch (RuntimeException e2) {
                BasePendingResult.zab(result);
                throw e2;
            }
        }

        public final void zaa(ResultCallback<? super R> resultCallback, R r2) {
            sendMessage(obtainMessage(1, new Pair(BasePendingResult.zaa(resultCallback), r2)));
        }

        public CallbackHandler(Looper looper) {
            super(looper);
        }
    }

    public final class zaa {
        private zaa() {
        }

        public final void finalize() throws Throwable {
            BasePendingResult.zab(BasePendingResult.this.zacl);
            super.finalize();
        }

        public /* synthetic */ zaa(BasePendingResult basePendingResult, zao zaoVar) {
            this();
        }
    }

    @Deprecated
    public BasePendingResult() {
        this.zadp = new Object();
        this.zads = new CountDownLatch(1);
        this.zadt = new ArrayList<>();
        this.zadv = new AtomicReference<>();
        this.zaeb = false;
        this.zadq = new CallbackHandler<>(Looper.getMainLooper());
        this.zadr = new WeakReference<>(null);
    }

    private final R get() {
        R r2;
        synchronized (this.zadp) {
            Preconditions.checkState(!this.zadw, "Result has already been consumed.");
            Preconditions.checkState(isReady(), "Result is not ready.");
            r2 = this.zacl;
            this.zacl = null;
            this.zadu = null;
            this.zadw = true;
        }
        zacq andSet = this.zadv.getAndSet(null);
        if (andSet != null) {
            andSet.zab(this);
        }
        return r2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <R extends Result> ResultCallback<R> zaa(ResultCallback<R> resultCallback) {
        return resultCallback;
    }

    @Override // com.google.android.gms.common.api.PendingResult
    public final void addStatusListener(PendingResult.StatusListener statusListener) {
        Preconditions.checkArgument(statusListener != null, "Callback cannot be null.");
        synchronized (this.zadp) {
            if (isReady()) {
                statusListener.onComplete(this.mStatus);
            } else {
                this.zadt.add(statusListener);
            }
        }
    }

    @Override // com.google.android.gms.common.api.PendingResult
    public final R await() throws InterruptedException {
        Preconditions.checkNotMainThread("await must not be called on the UI thread");
        Preconditions.checkState(!this.zadw, "Result has already been consumed");
        Preconditions.checkState(this.zaea == null, "Cannot await if then() has been called.");
        try {
            this.zads.await();
        } catch (InterruptedException unused) {
            zab(Status.RESULT_INTERRUPTED);
        }
        Preconditions.checkState(isReady(), "Result is not ready.");
        return (R) get();
    }

    @Override // com.google.android.gms.common.api.PendingResult
    @KeepForSdk
    public void cancel() {
        synchronized (this.zadp) {
            if (!this.zadx && !this.zadw) {
                ICancelToken iCancelToken = this.zadz;
                if (iCancelToken != null) {
                    try {
                        iCancelToken.cancel();
                    } catch (RemoteException unused) {
                    }
                }
                zab(this.zacl);
                this.zadx = true;
                zaa((BasePendingResult<R>) createFailedResult(Status.RESULT_CANCELED));
            }
        }
    }

    @NonNull
    @KeepForSdk
    public abstract R createFailedResult(Status status);

    @Override // com.google.android.gms.common.api.PendingResult
    public boolean isCanceled() {
        boolean z2;
        synchronized (this.zadp) {
            z2 = this.zadx;
        }
        return z2;
    }

    @KeepForSdk
    public final boolean isReady() {
        return this.zads.getCount() == 0;
    }

    @KeepForSdk
    public final void setCancelToken(ICancelToken iCancelToken) {
        synchronized (this.zadp) {
            this.zadz = iCancelToken;
        }
    }

    @KeepForSdk
    public final void setResult(R r2) {
        synchronized (this.zadp) {
            if (this.zady || this.zadx) {
                zab(r2);
                return;
            }
            isReady();
            boolean z2 = true;
            Preconditions.checkState(!isReady(), "Results have already been set");
            if (this.zadw) {
                z2 = false;
            }
            Preconditions.checkState(z2, "Result has already been consumed");
            zaa((BasePendingResult<R>) r2);
        }
    }

    @Override // com.google.android.gms.common.api.PendingResult
    @KeepForSdk
    public final void setResultCallback(ResultCallback<? super R> resultCallback) {
        synchronized (this.zadp) {
            if (resultCallback == null) {
                this.zadu = null;
                return;
            }
            boolean z2 = true;
            Preconditions.checkState(!this.zadw, "Result has already been consumed.");
            if (this.zaea != null) {
                z2 = false;
            }
            Preconditions.checkState(z2, "Cannot set callbacks if then() has been called.");
            if (isCanceled()) {
                return;
            }
            if (isReady()) {
                this.zadq.zaa(resultCallback, get());
            } else {
                this.zadu = resultCallback;
            }
        }
    }

    @Override // com.google.android.gms.common.api.PendingResult
    public <S extends Result> TransformedResult<S> then(ResultTransform<? super R, ? extends S> resultTransform) {
        TransformedResult<S> transformedResultThen;
        Preconditions.checkState(!this.zadw, "Result has already been consumed.");
        synchronized (this.zadp) {
            Preconditions.checkState(this.zaea == null, "Cannot call then() twice.");
            Preconditions.checkState(this.zadu == null, "Cannot call then() if callbacks are set.");
            Preconditions.checkState(this.zadx ? false : true, "Cannot call then() if result was canceled.");
            this.zaeb = true;
            this.zaea = new zack<>(this.zadr);
            transformedResultThen = this.zaea.then(resultTransform);
            if (isReady()) {
                this.zadq.zaa(this.zaea, get());
            } else {
                this.zadu = this.zaea;
            }
        }
        return transformedResultThen;
    }

    public final void zaa(zacq zacqVar) {
        this.zadv.set(zacqVar);
    }

    public final void zab(Status status) {
        synchronized (this.zadp) {
            if (!isReady()) {
                setResult(createFailedResult(status));
                this.zady = true;
            }
        }
    }

    @Override // com.google.android.gms.common.api.PendingResult
    public final Integer zal() {
        return null;
    }

    public final boolean zaq() {
        boolean zIsCanceled;
        synchronized (this.zadp) {
            if (this.zadr.get() == null || !this.zaeb) {
                cancel();
            }
            zIsCanceled = isCanceled();
        }
        return zIsCanceled;
    }

    public final void zar() {
        this.zaeb = this.zaeb || zado.get().booleanValue();
    }

    private final void zaa(R r2) {
        this.zacl = r2;
        zao zaoVar = null;
        this.zadz = null;
        this.zads.countDown();
        this.mStatus = this.zacl.getStatus();
        if (this.zadx) {
            this.zadu = null;
        } else if (this.zadu != null) {
            this.zadq.removeMessages(2);
            this.zadq.zaa(this.zadu, get());
        } else if (this.zacl instanceof Releasable) {
            this.mResultGuardian = new zaa(this, zaoVar);
        }
        ArrayList<PendingResult.StatusListener> arrayList = this.zadt;
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            PendingResult.StatusListener statusListener = arrayList.get(i2);
            i2++;
            statusListener.onComplete(this.mStatus);
        }
        this.zadt.clear();
    }

    public static void zab(Result result) {
        if (result instanceof Releasable) {
            try {
                ((Releasable) result).release();
            } catch (RuntimeException e2) {
                String strValueOf = String.valueOf(result);
                StringBuilder sb = new StringBuilder(strValueOf.length() + 18);
                sb.append("Unable to release ");
                sb.append(strValueOf);
                Log.w("BasePendingResult", sb.toString(), e2);
            }
        }
    }

    @Override // com.google.android.gms.common.api.PendingResult
    public final R await(long j2, TimeUnit timeUnit) {
        if (j2 > 0) {
            Preconditions.checkNotMainThread("await must not be called on the UI thread when time is greater than zero.");
        }
        Preconditions.checkState(!this.zadw, "Result has already been consumed.");
        Preconditions.checkState(this.zaea == null, "Cannot await if then() has been called.");
        try {
            if (!this.zads.await(j2, timeUnit)) {
                zab(Status.RESULT_TIMEOUT);
            }
        } catch (InterruptedException unused) {
            zab(Status.RESULT_INTERRUPTED);
        }
        Preconditions.checkState(isReady(), "Result is not ready.");
        return (R) get();
    }

    @KeepForSdk
    public BasePendingResult(GoogleApiClient googleApiClient) {
        this.zadp = new Object();
        this.zads = new CountDownLatch(1);
        this.zadt = new ArrayList<>();
        this.zadv = new AtomicReference<>();
        this.zaeb = false;
        this.zadq = new CallbackHandler<>(googleApiClient != null ? googleApiClient.getLooper() : Looper.getMainLooper());
        this.zadr = new WeakReference<>(googleApiClient);
    }

    @Override // com.google.android.gms.common.api.PendingResult
    @KeepForSdk
    public final void setResultCallback(ResultCallback<? super R> resultCallback, long j2, TimeUnit timeUnit) {
        synchronized (this.zadp) {
            if (resultCallback == null) {
                this.zadu = null;
                return;
            }
            boolean z2 = true;
            Preconditions.checkState(!this.zadw, "Result has already been consumed.");
            if (this.zaea != null) {
                z2 = false;
            }
            Preconditions.checkState(z2, "Cannot set callbacks if then() has been called.");
            if (isCanceled()) {
                return;
            }
            if (isReady()) {
                this.zadq.zaa(resultCallback, get());
            } else {
                this.zadu = resultCallback;
                CallbackHandler<R> callbackHandler = this.zadq;
                callbackHandler.sendMessageDelayed(callbackHandler.obtainMessage(2, this), timeUnit.toMillis(j2));
            }
        }
    }

    @KeepForSdk
    @Deprecated
    public BasePendingResult(Looper looper) {
        this.zadp = new Object();
        this.zads = new CountDownLatch(1);
        this.zadt = new ArrayList<>();
        this.zadv = new AtomicReference<>();
        this.zaeb = false;
        this.zadq = new CallbackHandler<>(looper);
        this.zadr = new WeakReference<>(null);
    }

    @VisibleForTesting
    @KeepForSdk
    public BasePendingResult(@NonNull CallbackHandler<R> callbackHandler) {
        this.zadp = new Object();
        this.zads = new CountDownLatch(1);
        this.zadt = new ArrayList<>();
        this.zadv = new AtomicReference<>();
        this.zaeb = false;
        this.zadq = (CallbackHandler) Preconditions.checkNotNull(callbackHandler, "CallbackHandler must not be null");
        this.zadr = new WeakReference<>(null);
    }
}
