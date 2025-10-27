package androidx.camera.core.processing;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Size;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.camera.core.SurfaceOutput;
import androidx.camera.core.SurfaceRequest;
import androidx.camera.core.impl.CameraInternal;
import androidx.camera.core.impl.utils.Threads;
import androidx.camera.core.impl.utils.TransformUtils;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.impl.utils.futures.FutureCallback;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.core.util.Preconditions;
import java.util.Collections;
import java.util.Iterator;

@RequiresApi(api = 21)
/* loaded from: classes.dex */
public class SurfaceProcessorNode implements Node<SurfaceEdge, SurfaceEdge> {

    @NonNull
    final CameraInternal mCameraInternal;
    private final SurfaceOutput.GlTransformOptions mGlTransformOptions;

    @Nullable
    private SurfaceEdge mInputEdge;

    @Nullable
    private SurfaceEdge mOutputEdge;

    @NonNull
    final SurfaceProcessorInternal mSurfaceProcessor;

    /* renamed from: androidx.camera.core.processing.SurfaceProcessorNode$2, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$androidx$camera$core$SurfaceOutput$GlTransformOptions;

        static {
            int[] iArr = new int[SurfaceOutput.GlTransformOptions.values().length];
            $SwitchMap$androidx$camera$core$SurfaceOutput$GlTransformOptions = iArr;
            try {
                iArr[SurfaceOutput.GlTransformOptions.APPLY_CROP_ROTATE_AND_MIRRORING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$androidx$camera$core$SurfaceOutput$GlTransformOptions[SurfaceOutput.GlTransformOptions.USE_SURFACE_TEXTURE_TRANSFORM.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public SurfaceProcessorNode(@NonNull CameraInternal cameraInternal, @NonNull SurfaceOutput.GlTransformOptions glTransformOptions, @NonNull SurfaceProcessorInternal surfaceProcessorInternal) {
        this.mCameraInternal = cameraInternal;
        this.mGlTransformOptions = glTransformOptions;
        this.mSurfaceProcessor = surfaceProcessorInternal;
    }

    @NonNull
    private SettableSurface createOutputSurface(@NonNull SettableSurface settableSurface) {
        int i2 = AnonymousClass2.$SwitchMap$androidx$camera$core$SurfaceOutput$GlTransformOptions[this.mGlTransformOptions.ordinal()];
        if (i2 != 1) {
            if (i2 == 2) {
                return new SettableSurface(settableSurface.getTargets(), settableSurface.getSize(), settableSurface.getFormat(), settableSurface.getSensorToBufferTransform(), false, settableSurface.getCropRect(), settableSurface.getRotationDegrees(), settableSurface.getMirroring());
            }
            throw new AssertionError("Unknown GlTransformOptions: " + this.mGlTransformOptions);
        }
        Size size = settableSurface.getSize();
        Rect cropRect = settableSurface.getCropRect();
        int rotationDegrees = settableSurface.getRotationDegrees();
        boolean mirroring = settableSurface.getMirroring();
        Size size2 = TransformUtils.is90or270(rotationDegrees) ? new Size(cropRect.height(), cropRect.width()) : TransformUtils.rectToSize(cropRect);
        Matrix matrix = new Matrix(settableSurface.getSensorToBufferTransform());
        matrix.postConcat(TransformUtils.getRectToRect(TransformUtils.sizeToRectF(size), new RectF(cropRect), rotationDegrees, mirroring));
        return new SettableSurface(settableSurface.getTargets(), size2, settableSurface.getFormat(), matrix, false, TransformUtils.sizeToRect(size2), 0, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$release$1() {
        SurfaceEdge surfaceEdge = this.mOutputEdge;
        if (surfaceEdge != null) {
            Iterator<SettableSurface> it = surfaceEdge.getSurfaces().iterator();
            while (it.hasNext()) {
                it.next().close();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$setupSurfaceUpdatePipeline$0(SurfaceOutput surfaceOutput, SettableSurface settableSurface, SettableSurface settableSurface2, SurfaceRequest.TransformationInfo transformationInfo) {
        int rotationDegrees = transformationInfo.getRotationDegrees() - surfaceOutput.getRotationDegrees();
        if (settableSurface.getMirroring()) {
            rotationDegrees = -rotationDegrees;
        }
        settableSurface2.setRotationDegrees(TransformUtils.within360(rotationDegrees));
    }

    private void sendSurfacesToProcessorWhenReady(@NonNull final SettableSurface settableSurface, @NonNull final SettableSurface settableSurface2) {
        final SurfaceRequest surfaceRequestCreateSurfaceRequest = settableSurface.createSurfaceRequest(this.mCameraInternal);
        Futures.addCallback(settableSurface2.createSurfaceOutputFuture(this.mGlTransformOptions, settableSurface.getSize(), settableSurface.getCropRect(), settableSurface.getRotationDegrees(), settableSurface.getMirroring()), new FutureCallback<SurfaceOutput>() { // from class: androidx.camera.core.processing.SurfaceProcessorNode.1
            @Override // androidx.camera.core.impl.utils.futures.FutureCallback
            public void onFailure(@NonNull Throwable th) {
                surfaceRequestCreateSurfaceRequest.willNotProvideSurface();
            }

            @Override // androidx.camera.core.impl.utils.futures.FutureCallback
            public void onSuccess(@Nullable SurfaceOutput surfaceOutput) {
                Preconditions.checkNotNull(surfaceOutput);
                SurfaceProcessorNode.this.mSurfaceProcessor.onOutputSurface(surfaceOutput);
                SurfaceProcessorNode.this.mSurfaceProcessor.onInputSurface(surfaceRequestCreateSurfaceRequest);
                SurfaceProcessorNode.this.setupSurfaceUpdatePipeline(settableSurface, surfaceRequestCreateSurfaceRequest, settableSurface2, surfaceOutput);
            }
        }, CameraXExecutors.mainThreadExecutor());
    }

    @Override // androidx.camera.core.processing.Node
    public void release() {
        this.mSurfaceProcessor.release();
        CameraXExecutors.mainThreadExecutor().execute(new Runnable() { // from class: androidx.camera.core.processing.s
            @Override // java.lang.Runnable
            public final void run() {
                this.f1654c.lambda$release$1();
            }
        });
    }

    public void setupSurfaceUpdatePipeline(@NonNull final SettableSurface settableSurface, @NonNull SurfaceRequest surfaceRequest, @NonNull final SettableSurface settableSurface2, @NonNull final SurfaceOutput surfaceOutput) {
        surfaceRequest.setTransformationInfoListener(CameraXExecutors.mainThreadExecutor(), new SurfaceRequest.TransformationInfoListener() { // from class: androidx.camera.core.processing.r
            @Override // androidx.camera.core.SurfaceRequest.TransformationInfoListener
            public final void onTransformationInfoUpdate(SurfaceRequest.TransformationInfo transformationInfo) {
                SurfaceProcessorNode.lambda$setupSurfaceUpdatePipeline$0(surfaceOutput, settableSurface, settableSurface2, transformationInfo);
            }
        });
    }

    @Override // androidx.camera.core.processing.Node
    @NonNull
    @MainThread
    public SurfaceEdge transform(@NonNull SurfaceEdge surfaceEdge) {
        Threads.checkMainThread();
        Preconditions.checkArgument(surfaceEdge.getSurfaces().size() == 1, "Multiple input stream not supported yet.");
        this.mInputEdge = surfaceEdge;
        SettableSurface settableSurface = surfaceEdge.getSurfaces().get(0);
        SettableSurface settableSurfaceCreateOutputSurface = createOutputSurface(settableSurface);
        sendSurfacesToProcessorWhenReady(settableSurface, settableSurfaceCreateOutputSurface);
        SurfaceEdge surfaceEdgeCreate = SurfaceEdge.create(Collections.singletonList(settableSurfaceCreateOutputSurface));
        this.mOutputEdge = surfaceEdgeCreate;
        return surfaceEdgeCreate;
    }
}
