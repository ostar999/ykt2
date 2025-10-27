package androidx.camera.core.processing;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.camera.core.CameraEffect;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProcessor;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.core.util.Preconditions;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

@RequiresApi(api = 21)
/* loaded from: classes.dex */
public class InternalImageProcessor {

    @NonNull
    private final Executor mExecutor;

    @NonNull
    private final ImageProcessor mImageProcessor;

    public InternalImageProcessor(@NonNull CameraEffect cameraEffect) {
        Preconditions.checkArgument(cameraEffect.getTargets() == 4);
        this.mExecutor = cameraEffect.getProcessorExecutor();
        ImageProcessor imageProcessor = cameraEffect.getImageProcessor();
        Objects.requireNonNull(imageProcessor);
        this.mImageProcessor = imageProcessor;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$safeProcess$0(CallbackToFutureAdapter.Completer completer, ImageProcessor.Request request) {
        try {
            completer.set(this.mImageProcessor.process(request));
        } catch (Exception e2) {
            completer.setException(e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Object lambda$safeProcess$1(final ImageProcessor.Request request, final CallbackToFutureAdapter.Completer completer) throws Exception {
        this.mExecutor.execute(new Runnable() { // from class: androidx.camera.core.processing.h
            @Override // java.lang.Runnable
            public final void run() {
                this.f1632c.lambda$safeProcess$0(completer, request);
            }
        });
        return "InternalImageProcessor#process " + request.hashCode();
    }

    @NonNull
    public ImageProcessor.Response safeProcess(@NonNull final ImageProcessor.Request request) throws ImageCaptureException {
        try {
            return (ImageProcessor.Response) CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.core.processing.i
                @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
                public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                    return this.f1635a.lambda$safeProcess$1(request, completer);
                }
            }).get();
        } catch (InterruptedException | ExecutionException e2) {
            e = e2;
            if (e.getCause() != null) {
                e = e.getCause();
            }
            throw new ImageCaptureException(0, "Failed to invoke ImageProcessor.", e);
        }
    }
}
