package androidx.camera.core.processing;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.Range;
import android.util.Size;
import android.view.Surface;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.camera.core.SurfaceOutput;
import androidx.camera.core.SurfaceRequest;
import androidx.camera.core.impl.CameraInternal;
import androidx.camera.core.impl.DeferrableSurface;
import androidx.camera.core.impl.utils.Threads;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.impl.utils.futures.AsyncFunction;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.core.util.Preconditions;
import com.google.common.util.concurrent.ListenableFuture;

@RequiresApi(api = 21)
/* loaded from: classes.dex */
public class SettableSurface extends DeferrableSurface {
    CallbackToFutureAdapter.Completer<Surface> mCompleter;

    @Nullable
    private SurfaceOutputImpl mConsumerToNotify;
    private final Rect mCropRect;
    private boolean mHasConsumer;
    private final boolean mHasEmbeddedTransform;
    private boolean mHasProvider;
    private final boolean mMirroring;

    @Nullable
    private SurfaceRequest mProviderSurfaceRequest;
    private int mRotationDegrees;
    private final Matrix mSensorToBufferTransform;
    private final ListenableFuture<Surface> mSurfaceFuture;
    private final int mTargets;

    public SettableSurface(int i2, @NonNull final Size size, int i3, @NonNull Matrix matrix, boolean z2, @NonNull Rect rect, int i4, boolean z3) {
        super(size, i3);
        this.mHasProvider = false;
        this.mHasConsumer = false;
        this.mTargets = i2;
        this.mSensorToBufferTransform = matrix;
        this.mHasEmbeddedTransform = z2;
        this.mCropRect = rect;
        this.mRotationDegrees = i4;
        this.mMirroring = z3;
        this.mSurfaceFuture = CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.core.processing.j
            @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
            public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                return this.f1637a.lambda$new$0(size, completer);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$close$3() {
        SurfaceOutputImpl surfaceOutputImpl = this.mConsumerToNotify;
        if (surfaceOutputImpl != null) {
            surfaceOutputImpl.requestClose();
            this.mConsumerToNotify = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ ListenableFuture lambda$createSurfaceOutputFuture$2(SurfaceOutput.GlTransformOptions glTransformOptions, Size size, Rect rect, int i2, boolean z2, Surface surface) throws Exception {
        Preconditions.checkNotNull(surface);
        try {
            incrementUseCount();
            SurfaceOutputImpl surfaceOutputImpl = new SurfaceOutputImpl(surface, getTargets(), getFormat(), getSize(), glTransformOptions, size, rect, i2, z2);
            surfaceOutputImpl.getCloseFuture().addListener(new Runnable() { // from class: androidx.camera.core.processing.k
                @Override // java.lang.Runnable
                public final void run() {
                    this.f1639c.decrementUseCount();
                }
            }, CameraXExecutors.directExecutor());
            this.mConsumerToNotify = surfaceOutputImpl;
            return Futures.immediateFuture(surfaceOutputImpl);
        } catch (DeferrableSurface.SurfaceClosedException e2) {
            return Futures.immediateFailedFuture(e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Object lambda$new$0(Size size, CallbackToFutureAdapter.Completer completer) throws Exception {
        this.mCompleter = completer;
        return "SettableFuture size: " + size + " hashCode: " + hashCode();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$setProvider$1(DeferrableSurface deferrableSurface) {
        deferrableSurface.decrementUseCount();
        deferrableSurface.close();
    }

    @MainThread
    private void notifyTransformationInfoUpdate() {
        SurfaceRequest surfaceRequest = this.mProviderSurfaceRequest;
        if (surfaceRequest != null) {
            surfaceRequest.updateTransformationInfo(SurfaceRequest.TransformationInfo.of(this.mCropRect, this.mRotationDegrees, -1));
        }
    }

    @Override // androidx.camera.core.impl.DeferrableSurface
    public final void close() {
        super.close();
        CameraXExecutors.mainThreadExecutor().execute(new Runnable() { // from class: androidx.camera.core.processing.l
            @Override // java.lang.Runnable
            public final void run() {
                this.f1640c.lambda$close$3();
            }
        });
    }

    @NonNull
    @MainThread
    public ListenableFuture<SurfaceOutput> createSurfaceOutputFuture(@NonNull final SurfaceOutput.GlTransformOptions glTransformOptions, @NonNull final Size size, @NonNull final Rect rect, final int i2, final boolean z2) {
        Threads.checkMainThread();
        Preconditions.checkState(!this.mHasConsumer, "Consumer can only be linked once.");
        this.mHasConsumer = true;
        return Futures.transformAsync(getSurface(), new AsyncFunction() { // from class: androidx.camera.core.processing.n
            @Override // androidx.camera.core.impl.utils.futures.AsyncFunction
            public final ListenableFuture apply(Object obj) {
                return this.f1642a.lambda$createSurfaceOutputFuture$2(glTransformOptions, size, rect, i2, z2, (Surface) obj);
            }
        }, CameraXExecutors.mainThreadExecutor());
    }

    @NonNull
    @MainThread
    public SurfaceRequest createSurfaceRequest(@NonNull CameraInternal cameraInternal) {
        return createSurfaceRequest(cameraInternal, null);
    }

    @NonNull
    public Rect getCropRect() {
        return this.mCropRect;
    }

    public int getFormat() {
        return getPrescribedStreamFormat();
    }

    public boolean getMirroring() {
        return this.mMirroring;
    }

    public int getRotationDegrees() {
        return this.mRotationDegrees;
    }

    @NonNull
    public Matrix getSensorToBufferTransform() {
        return this.mSensorToBufferTransform;
    }

    @NonNull
    public Size getSize() {
        return getPrescribedSize();
    }

    public int getTargets() {
        return this.mTargets;
    }

    public boolean hasEmbeddedTransform() {
        return this.mHasEmbeddedTransform;
    }

    @Override // androidx.camera.core.impl.DeferrableSurface
    @NonNull
    public ListenableFuture<Surface> provideSurface() {
        return this.mSurfaceFuture;
    }

    @MainThread
    public void setProvider(@NonNull ListenableFuture<Surface> listenableFuture) {
        Threads.checkMainThread();
        Preconditions.checkState(!this.mHasProvider, "Provider can only be linked once.");
        this.mHasProvider = true;
        Futures.propagate(listenableFuture, this.mCompleter);
    }

    @MainThread
    public void setRotationDegrees(int i2) {
        Threads.checkMainThread();
        if (this.mRotationDegrees == i2) {
            return;
        }
        this.mRotationDegrees = i2;
        notifyTransformationInfoUpdate();
    }

    @NonNull
    @MainThread
    public SurfaceRequest createSurfaceRequest(@NonNull CameraInternal cameraInternal, @Nullable Range<Integer> range) {
        Threads.checkMainThread();
        SurfaceRequest surfaceRequest = new SurfaceRequest(getSize(), cameraInternal, true, range);
        try {
            setProvider(surfaceRequest.getDeferrableSurface());
            this.mProviderSurfaceRequest = surfaceRequest;
            notifyTransformationInfoUpdate();
            return surfaceRequest;
        } catch (DeferrableSurface.SurfaceClosedException e2) {
            throw new AssertionError("Surface is somehow already closed", e2);
        }
    }

    @MainThread
    public void setProvider(@NonNull final DeferrableSurface deferrableSurface) throws DeferrableSurface.SurfaceClosedException {
        Threads.checkMainThread();
        setProvider(deferrableSurface.getSurface());
        deferrableSurface.incrementUseCount();
        getTerminationFuture().addListener(new Runnable() { // from class: androidx.camera.core.processing.m
            @Override // java.lang.Runnable
            public final void run() {
                SettableSurface.lambda$setProvider$1(deferrableSurface);
            }
        }, CameraXExecutors.directExecutor());
    }
}
