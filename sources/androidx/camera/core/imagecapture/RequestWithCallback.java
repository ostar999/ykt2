package androidx.camera.core.imagecapture;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.impl.utils.Threads;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.core.util.Preconditions;
import com.google.common.util.concurrent.ListenableFuture;

@RequiresApi(api = 21)
/* loaded from: classes.dex */
class RequestWithCallback implements TakePictureCallback {
    private CallbackToFutureAdapter.Completer<Void> mCaptureCompleter;
    private final TakePictureRequest mTakePictureRequest;
    private boolean mIsComplete = false;
    private boolean mIsAborted = false;
    private final ListenableFuture<Void> mCaptureFuture = CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.core.imagecapture.h
        @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
        public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
            return this.f1531a.lambda$new$0(completer);
        }
    });

    public RequestWithCallback(@NonNull TakePictureRequest takePictureRequest) {
        this.mTakePictureRequest = takePictureRequest;
    }

    private void checkOnImageCaptured() {
        Preconditions.checkState(this.mCaptureFuture.isDone(), "onImageCaptured() must be called before onFinalResult()");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Object lambda$new$0(CallbackToFutureAdapter.Completer completer) throws Exception {
        this.mCaptureCompleter = completer;
        return "CaptureCompleteFuture";
    }

    private void markComplete() {
        Preconditions.checkState(!this.mIsComplete, "The callback can only complete once.");
        this.mIsComplete = true;
    }

    @MainThread
    private void onFailure(@NonNull ImageCaptureException imageCaptureException) {
        Threads.checkMainThread();
        this.mTakePictureRequest.onError(imageCaptureException);
    }

    @MainThread
    public void abort(@NonNull ImageCaptureException imageCaptureException) {
        Threads.checkMainThread();
        this.mIsAborted = true;
        this.mCaptureCompleter.set(null);
        onFailure(imageCaptureException);
    }

    @NonNull
    @MainThread
    public ListenableFuture<Void> getCaptureFuture() {
        Threads.checkMainThread();
        return this.mCaptureFuture;
    }

    @Override // androidx.camera.core.imagecapture.TakePictureCallback
    public boolean isAborted() {
        return this.mIsAborted;
    }

    @Override // androidx.camera.core.imagecapture.TakePictureCallback
    @MainThread
    public void onCaptureFailure(@NonNull ImageCaptureException imageCaptureException) {
        Threads.checkMainThread();
        if (this.mIsAborted) {
            return;
        }
        markComplete();
        this.mCaptureCompleter.set(null);
        onFailure(imageCaptureException);
    }

    @Override // androidx.camera.core.imagecapture.TakePictureCallback
    @MainThread
    public void onFinalResult(@NonNull ImageCapture.OutputFileResults outputFileResults) {
        Threads.checkMainThread();
        if (this.mIsAborted) {
            return;
        }
        checkOnImageCaptured();
        markComplete();
        this.mTakePictureRequest.onResult(outputFileResults);
    }

    @Override // androidx.camera.core.imagecapture.TakePictureCallback
    @MainThread
    public void onImageCaptured() {
        Threads.checkMainThread();
        if (this.mIsAborted) {
            return;
        }
        this.mCaptureCompleter.set(null);
    }

    @Override // androidx.camera.core.imagecapture.TakePictureCallback
    @MainThread
    public void onProcessFailure(@NonNull ImageCaptureException imageCaptureException) {
        Threads.checkMainThread();
        if (this.mIsAborted) {
            return;
        }
        checkOnImageCaptured();
        markComplete();
        onFailure(imageCaptureException);
    }

    @Override // androidx.camera.core.imagecapture.TakePictureCallback
    @MainThread
    public void onFinalResult(@NonNull ImageProxy imageProxy) {
        Threads.checkMainThread();
        if (this.mIsAborted) {
            return;
        }
        checkOnImageCaptured();
        markComplete();
        this.mTakePictureRequest.onResult(imageProxy);
    }
}
