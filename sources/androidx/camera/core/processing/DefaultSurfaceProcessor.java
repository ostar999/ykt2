package androidx.camera.core.processing;

import android.graphics.SurfaceTexture;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.Surface;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.annotation.VisibleForTesting;
import androidx.annotation.WorkerThread;
import androidx.camera.core.SurfaceOutput;
import androidx.camera.core.SurfaceRequest;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.core.util.Consumer;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

@RequiresApi(21)
/* loaded from: classes.dex */
public class DefaultSurfaceProcessor implements SurfaceProcessorInternal, SurfaceTexture.OnFrameAvailableListener {
    private final Executor mGlExecutor;

    @VisibleForTesting
    final Handler mGlHandler;
    private final OpenGlRenderer mGlRenderer;

    @VisibleForTesting
    final HandlerThread mGlThread;
    private int mInputSurfaceCount;
    private final AtomicBoolean mIsReleased;
    final Map<SurfaceOutput, Surface> mOutputSurfaces;
    private final float[] mSurfaceOutputMatrix;
    private final float[] mTextureMatrix;

    public DefaultSurfaceProcessor() {
        this(ShaderProvider.DEFAULT);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @WorkerThread
    public void checkReadyToRelease() {
        if (this.mIsReleased.get() && this.mInputSurfaceCount == 0) {
            Iterator<SurfaceOutput> it = this.mOutputSurfaces.keySet().iterator();
            while (it.hasNext()) {
                it.next().close();
            }
            this.mOutputSurfaces.clear();
            this.mGlRenderer.release();
            this.mGlThread.quit();
        }
    }

    private void initGlRenderer(@NonNull final ShaderProvider shaderProvider) throws ExecutionException, InterruptedException {
        try {
            CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.core.processing.d
                @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
                public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                    return this.f1624a.lambda$initGlRenderer$5(shaderProvider, completer);
                }
            }).get();
        } catch (InterruptedException | ExecutionException e2) {
            e = e2;
            if (e instanceof ExecutionException) {
                e = e.getCause();
            }
            if (!(e instanceof RuntimeException)) {
                throw new IllegalStateException("Failed to create DefaultSurfaceProcessor", e);
            }
            throw ((RuntimeException) e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initGlRenderer$4(ShaderProvider shaderProvider, CallbackToFutureAdapter.Completer completer) {
        try {
            this.mGlRenderer.init(shaderProvider);
            completer.set(null);
        } catch (RuntimeException e2) {
            completer.setException(e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Object lambda$initGlRenderer$5(final ShaderProvider shaderProvider, final CallbackToFutureAdapter.Completer completer) throws Exception {
        this.mGlExecutor.execute(new Runnable() { // from class: androidx.camera.core.processing.a
            @Override // java.lang.Runnable
            public final void run() {
                this.f1617c.lambda$initGlRenderer$4(shaderProvider, completer);
            }
        });
        return "Init GlRenderer";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onInputSurface$0(SurfaceTexture surfaceTexture, Surface surface, SurfaceRequest.Result result) {
        surfaceTexture.setOnFrameAvailableListener(null);
        surfaceTexture.release();
        surface.release();
        this.mInputSurfaceCount--;
        checkReadyToRelease();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onInputSurface$1(SurfaceRequest surfaceRequest) throws ExecutionException, InterruptedException {
        this.mInputSurfaceCount++;
        final SurfaceTexture surfaceTexture = new SurfaceTexture(this.mGlRenderer.getTextureName());
        surfaceTexture.setDefaultBufferSize(surfaceRequest.getResolution().getWidth(), surfaceRequest.getResolution().getHeight());
        final Surface surface = new Surface(surfaceTexture);
        surfaceRequest.provideSurface(surface, this.mGlExecutor, new Consumer() { // from class: androidx.camera.core.processing.g
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                this.f1629c.lambda$onInputSurface$0(surfaceTexture, surface, (SurfaceRequest.Result) obj);
            }
        });
        surfaceTexture.setOnFrameAvailableListener(this, this.mGlHandler);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onOutputSurface$2(SurfaceOutput surfaceOutput, SurfaceOutput.Event event) {
        surfaceOutput.close();
        this.mOutputSurfaces.remove(surfaceOutput);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onOutputSurface$3(final SurfaceOutput surfaceOutput) {
        this.mOutputSurfaces.put(surfaceOutput, surfaceOutput.getSurface(this.mGlExecutor, new Consumer() { // from class: androidx.camera.core.processing.f
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                this.f1627c.lambda$onOutputSurface$2(surfaceOutput, (SurfaceOutput.Event) obj);
            }
        }));
    }

    @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
    public void onFrameAvailable(@NonNull SurfaceTexture surfaceTexture) {
        if (this.mIsReleased.get()) {
            return;
        }
        surfaceTexture.updateTexImage();
        surfaceTexture.getTransformMatrix(this.mTextureMatrix);
        for (Map.Entry<SurfaceOutput, Surface> entry : this.mOutputSurfaces.entrySet()) {
            Surface value = entry.getValue();
            SurfaceOutput key = entry.getKey();
            this.mGlRenderer.setOutputSurface(value);
            key.updateTransformMatrix(this.mSurfaceOutputMatrix, this.mTextureMatrix);
            this.mGlRenderer.render(surfaceTexture.getTimestamp(), this.mSurfaceOutputMatrix);
        }
    }

    @Override // androidx.camera.core.SurfaceProcessor
    public void onInputSurface(@NonNull final SurfaceRequest surfaceRequest) {
        if (this.mIsReleased.get()) {
            surfaceRequest.willNotProvideSurface();
        } else {
            this.mGlExecutor.execute(new Runnable() { // from class: androidx.camera.core.processing.c
                @Override // java.lang.Runnable
                public final void run() throws ExecutionException, InterruptedException {
                    this.f1622c.lambda$onInputSurface$1(surfaceRequest);
                }
            });
        }
    }

    @Override // androidx.camera.core.SurfaceProcessor
    public void onOutputSurface(@NonNull final SurfaceOutput surfaceOutput) {
        if (this.mIsReleased.get()) {
            surfaceOutput.close();
        } else {
            this.mGlExecutor.execute(new Runnable() { // from class: androidx.camera.core.processing.b
                @Override // java.lang.Runnable
                public final void run() {
                    this.f1620c.lambda$onOutputSurface$3(surfaceOutput);
                }
            });
        }
    }

    @Override // androidx.camera.core.processing.SurfaceProcessorInternal
    public void release() {
        if (this.mIsReleased.getAndSet(true)) {
            return;
        }
        this.mGlExecutor.execute(new Runnable() { // from class: androidx.camera.core.processing.e
            @Override // java.lang.Runnable
            public final void run() {
                this.f1626c.checkReadyToRelease();
            }
        });
    }

    public DefaultSurfaceProcessor(@NonNull ShaderProvider shaderProvider) throws ExecutionException, InterruptedException {
        this.mIsReleased = new AtomicBoolean(false);
        this.mTextureMatrix = new float[16];
        this.mSurfaceOutputMatrix = new float[16];
        this.mOutputSurfaces = new LinkedHashMap();
        this.mInputSurfaceCount = 0;
        HandlerThread handlerThread = new HandlerThread("GL Thread");
        this.mGlThread = handlerThread;
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        this.mGlHandler = handler;
        this.mGlExecutor = CameraXExecutors.newHandlerExecutor(handler);
        this.mGlRenderer = new OpenGlRenderer();
        try {
            initGlRenderer(shaderProvider);
        } catch (RuntimeException e2) {
            release();
            throw e2;
        }
    }
}
