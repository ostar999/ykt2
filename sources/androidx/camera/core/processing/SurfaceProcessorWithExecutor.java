package androidx.camera.core.processing;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.camera.core.SurfaceOutput;
import androidx.camera.core.SurfaceProcessor;
import androidx.camera.core.SurfaceRequest;
import androidx.core.util.Preconditions;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public class SurfaceProcessorWithExecutor implements SurfaceProcessorInternal {

    @NonNull
    private final Executor mExecutor;

    @NonNull
    private final SurfaceProcessor mSurfaceProcessor;

    public SurfaceProcessorWithExecutor(@NonNull SurfaceProcessor surfaceProcessor, @NonNull Executor executor) {
        Preconditions.checkState(!(surfaceProcessor instanceof SurfaceProcessorInternal), "SurfaceProcessorInternal should always be thread safe. Do not wrap.");
        this.mSurfaceProcessor = surfaceProcessor;
        this.mExecutor = executor;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onInputSurface$0(SurfaceRequest surfaceRequest) {
        this.mSurfaceProcessor.onInputSurface(surfaceRequest);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onOutputSurface$1(SurfaceOutput surfaceOutput) {
        this.mSurfaceProcessor.onOutputSurface(surfaceOutput);
    }

    @NonNull
    @VisibleForTesting
    public Executor getExecutor() {
        return this.mExecutor;
    }

    @NonNull
    @VisibleForTesting
    public SurfaceProcessor getProcessor() {
        return this.mSurfaceProcessor;
    }

    @Override // androidx.camera.core.SurfaceProcessor
    public void onInputSurface(@NonNull final SurfaceRequest surfaceRequest) {
        this.mExecutor.execute(new Runnable() { // from class: androidx.camera.core.processing.t
            @Override // java.lang.Runnable
            public final void run() {
                this.f1655c.lambda$onInputSurface$0(surfaceRequest);
            }
        });
    }

    @Override // androidx.camera.core.SurfaceProcessor
    public void onOutputSurface(@NonNull final SurfaceOutput surfaceOutput) {
        this.mExecutor.execute(new Runnable() { // from class: androidx.camera.core.processing.u
            @Override // java.lang.Runnable
            public final void run() {
                this.f1657c.lambda$onOutputSurface$1(surfaceOutput);
            }
        });
    }

    @Override // androidx.camera.core.processing.SurfaceProcessorInternal
    public void release() {
    }
}
