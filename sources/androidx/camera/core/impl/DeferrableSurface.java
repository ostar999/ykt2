package androidx.camera.core.impl;

import android.util.Log;
import android.util.Size;
import android.view.Surface;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.camera.core.Logger;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

@RequiresApi(21)
/* loaded from: classes.dex */
public abstract class DeferrableSurface {

    @GuardedBy("mLock")
    private boolean mClosed;

    @Nullable
    Class<?> mContainerClass;
    private final Object mLock;

    @NonNull
    private final Size mPrescribedSize;
    private final int mPrescribedStreamFormat;

    @GuardedBy("mLock")
    private CallbackToFutureAdapter.Completer<Void> mTerminationCompleter;
    private final ListenableFuture<Void> mTerminationFuture;

    @GuardedBy("mLock")
    private int mUseCount;
    public static final Size SIZE_UNDEFINED = new Size(0, 0);
    private static final String TAG = "DeferrableSurface";
    private static final boolean DEBUG = Logger.isDebugEnabled(TAG);
    private static final AtomicInteger USED_COUNT = new AtomicInteger(0);
    private static final AtomicInteger TOTAL_COUNT = new AtomicInteger(0);

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static final class SurfaceClosedException extends Exception {
        DeferrableSurface mDeferrableSurface;

        public SurfaceClosedException(@NonNull String str, @NonNull DeferrableSurface deferrableSurface) {
            super(str);
            this.mDeferrableSurface = deferrableSurface;
        }

        @NonNull
        public DeferrableSurface getDeferrableSurface() {
            return this.mDeferrableSurface;
        }
    }

    public static final class SurfaceUnavailableException extends Exception {
        public SurfaceUnavailableException(@NonNull String str) {
            super(str);
        }
    }

    public DeferrableSurface() {
        this(SIZE_UNDEFINED, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Object lambda$new$0(CallbackToFutureAdapter.Completer completer) throws Exception {
        synchronized (this.mLock) {
            this.mTerminationCompleter = completer;
        }
        return "DeferrableSurface-termination(" + this + ")";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(String str) throws ExecutionException, InterruptedException {
        try {
            this.mTerminationFuture.get();
            printGlobalDebugCounts("Surface terminated", TOTAL_COUNT.decrementAndGet(), USED_COUNT.get());
        } catch (Exception e2) {
            Logger.e(TAG, "Unexpected surface termination for " + this + "\nStack Trace:\n" + str);
            synchronized (this.mLock) {
                throw new IllegalArgumentException(String.format("DeferrableSurface %s [closed: %b, use_count: %s] terminated with unexpected exception.", this, Boolean.valueOf(this.mClosed), Integer.valueOf(this.mUseCount)), e2);
            }
        }
    }

    private void printGlobalDebugCounts(@NonNull String str, int i2, int i3) {
        if (!DEBUG && Logger.isDebugEnabled(TAG)) {
            Logger.d(TAG, "DeferrableSurface usage statistics may be inaccurate since debug logging was not enabled at static initialization time. App restart may be required to enable accurate usage statistics.");
        }
        Logger.d(TAG, str + "[total_surfaces=" + i2 + ", used_surfaces=" + i3 + "](" + this + "}");
    }

    public void close() {
        CallbackToFutureAdapter.Completer<Void> completer;
        synchronized (this.mLock) {
            if (this.mClosed) {
                completer = null;
            } else {
                this.mClosed = true;
                if (this.mUseCount == 0) {
                    completer = this.mTerminationCompleter;
                    this.mTerminationCompleter = null;
                } else {
                    completer = null;
                }
                if (Logger.isDebugEnabled(TAG)) {
                    Logger.d(TAG, "surface closed,  useCount=" + this.mUseCount + " closed=true " + this);
                }
            }
        }
        if (completer != null) {
            completer.set(null);
        }
    }

    public void decrementUseCount() {
        CallbackToFutureAdapter.Completer<Void> completer;
        synchronized (this.mLock) {
            int i2 = this.mUseCount;
            if (i2 == 0) {
                throw new IllegalStateException("Decrementing use count occurs more times than incrementing");
            }
            int i3 = i2 - 1;
            this.mUseCount = i3;
            if (i3 == 0 && this.mClosed) {
                completer = this.mTerminationCompleter;
                this.mTerminationCompleter = null;
            } else {
                completer = null;
            }
            if (Logger.isDebugEnabled(TAG)) {
                Logger.d(TAG, "use count-1,  useCount=" + this.mUseCount + " closed=" + this.mClosed + " " + this);
                if (this.mUseCount == 0) {
                    printGlobalDebugCounts("Surface no longer in use", TOTAL_COUNT.get(), USED_COUNT.decrementAndGet());
                }
            }
        }
        if (completer != null) {
            completer.set(null);
        }
    }

    @Nullable
    public Class<?> getContainerClass() {
        return this.mContainerClass;
    }

    @NonNull
    public Size getPrescribedSize() {
        return this.mPrescribedSize;
    }

    public int getPrescribedStreamFormat() {
        return this.mPrescribedStreamFormat;
    }

    @NonNull
    public final ListenableFuture<Surface> getSurface() {
        synchronized (this.mLock) {
            if (this.mClosed) {
                return Futures.immediateFailedFuture(new SurfaceClosedException("DeferrableSurface already closed.", this));
            }
            return provideSurface();
        }
    }

    @NonNull
    public ListenableFuture<Void> getTerminationFuture() {
        return Futures.nonCancellationPropagating(this.mTerminationFuture);
    }

    @RestrictTo({RestrictTo.Scope.TESTS})
    public int getUseCount() {
        int i2;
        synchronized (this.mLock) {
            i2 = this.mUseCount;
        }
        return i2;
    }

    public void incrementUseCount() throws SurfaceClosedException {
        synchronized (this.mLock) {
            int i2 = this.mUseCount;
            if (i2 == 0 && this.mClosed) {
                throw new SurfaceClosedException("Cannot begin use on a closed surface.", this);
            }
            this.mUseCount = i2 + 1;
            if (Logger.isDebugEnabled(TAG)) {
                if (this.mUseCount == 1) {
                    printGlobalDebugCounts("New surface in use", TOTAL_COUNT.get(), USED_COUNT.incrementAndGet());
                }
                Logger.d(TAG, "use count+1, useCount=" + this.mUseCount + " " + this);
            }
        }
    }

    @NonNull
    public abstract ListenableFuture<Surface> provideSurface();

    public void setContainerClass(@NonNull Class<?> cls) {
        this.mContainerClass = cls;
    }

    public DeferrableSurface(@NonNull Size size, int i2) {
        this.mLock = new Object();
        this.mUseCount = 0;
        this.mClosed = false;
        this.mPrescribedSize = size;
        this.mPrescribedStreamFormat = i2;
        ListenableFuture<Void> future = CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.core.impl.p
            @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
            public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                return this.f1556a.lambda$new$0(completer);
            }
        });
        this.mTerminationFuture = future;
        if (Logger.isDebugEnabled(TAG)) {
            printGlobalDebugCounts("Surface created", TOTAL_COUNT.incrementAndGet(), USED_COUNT.get());
            final String stackTraceString = Log.getStackTraceString(new Exception());
            future.addListener(new Runnable() { // from class: androidx.camera.core.impl.q
                @Override // java.lang.Runnable
                public final void run() throws ExecutionException, InterruptedException {
                    this.f1557c.lambda$new$1(stackTraceString);
                }
            }, CameraXExecutors.directExecutor());
        }
    }
}
