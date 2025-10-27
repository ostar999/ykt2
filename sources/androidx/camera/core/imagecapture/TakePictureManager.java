package androidx.camera.core.imagecapture;

import android.util.Log;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.VisibleForTesting;
import androidx.camera.core.ForwardingImageProxy;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.impl.utils.Threads;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.impl.utils.futures.FutureCallback;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.core.util.Pair;
import androidx.core.util.Preconditions;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.Objects;

@RequiresApi(api = 21)
/* loaded from: classes.dex */
public class TakePictureManager implements ForwardingImageProxy.OnImageCloseListener {
    private static final String TAG = "TakePictureManager";
    final ImageCaptureControl mImageCaptureControl;
    final ImagePipeline mImagePipeline;

    @Nullable
    @VisibleForTesting
    RequestWithCallback mInFlightRequest;

    @VisibleForTesting
    final Deque<TakePictureRequest> mNewRequests = new ArrayDeque();
    boolean mPaused = false;

    @MainThread
    public TakePictureManager(@NonNull ImageCaptureControl imageCaptureControl, @NonNull ImagePipeline imagePipeline) {
        Threads.checkMainThread();
        this.mImageCaptureControl = imageCaptureControl;
        this.mImagePipeline = imagePipeline;
        imagePipeline.setOnImageCloseListener(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$issueNextRequest$0(ProcessingRequest processingRequest) {
        this.mImagePipeline.postProcess(processingRequest);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$trackCurrentRequest$1() {
        this.mInFlightRequest = null;
        issueNextRequest();
    }

    @MainThread
    private void submitCameraRequest(@NonNull final CameraRequest cameraRequest, @NonNull final Runnable runnable) {
        Threads.checkMainThread();
        this.mImageCaptureControl.lockFlashMode();
        Futures.addCallback(this.mImageCaptureControl.submitStillCaptureRequests(cameraRequest.getCaptureConfigs()), new FutureCallback<Void>() { // from class: androidx.camera.core.imagecapture.TakePictureManager.1
            @Override // androidx.camera.core.impl.utils.futures.FutureCallback
            public void onFailure(@NonNull Throwable th) {
                if (th instanceof ImageCaptureException) {
                    cameraRequest.onCaptureFailure((ImageCaptureException) th);
                } else {
                    cameraRequest.onCaptureFailure(new ImageCaptureException(2, "Failed to submit capture request", th));
                }
                TakePictureManager.this.mImageCaptureControl.unlockFlashMode();
            }

            @Override // androidx.camera.core.impl.utils.futures.FutureCallback
            public void onSuccess(@Nullable Void r12) {
                runnable.run();
                TakePictureManager.this.mImageCaptureControl.unlockFlashMode();
            }
        }, CameraXExecutors.mainThreadExecutor());
    }

    private void trackCurrentRequest(@NonNull RequestWithCallback requestWithCallback) {
        Preconditions.checkState(!hasInFlightRequest());
        this.mInFlightRequest = requestWithCallback;
        requestWithCallback.getCaptureFuture().addListener(new Runnable() { // from class: androidx.camera.core.imagecapture.l
            @Override // java.lang.Runnable
            public final void run() {
                this.f1536c.lambda$trackCurrentRequest$1();
            }
        }, CameraXExecutors.directExecutor());
    }

    @MainThread
    public void abortRequests() {
        Threads.checkMainThread();
        ImageCaptureException imageCaptureException = new ImageCaptureException(3, "Camera is closed.", null);
        Iterator<TakePictureRequest> it = this.mNewRequests.iterator();
        while (it.hasNext()) {
            it.next().onError(imageCaptureException);
        }
        this.mNewRequests.clear();
        RequestWithCallback requestWithCallback = this.mInFlightRequest;
        if (requestWithCallback != null) {
            requestWithCallback.abort(imageCaptureException);
        }
    }

    @VisibleForTesting
    public boolean hasInFlightRequest() {
        return this.mInFlightRequest != null;
    }

    @MainThread
    public void issueNextRequest() {
        Threads.checkMainThread();
        Log.d(TAG, "Issue the next TakePictureRequest.");
        if (hasInFlightRequest()) {
            Log.d(TAG, "There is already a request in-flight.");
            return;
        }
        if (this.mPaused) {
            Log.d(TAG, "The class is paused.");
            return;
        }
        if (this.mImagePipeline.getCapacity() == 0) {
            Log.d(TAG, "Too many acquire images. Close image to be able to process next.");
            return;
        }
        TakePictureRequest takePictureRequestPoll = this.mNewRequests.poll();
        if (takePictureRequestPoll == null) {
            Log.d(TAG, "No new request.");
            return;
        }
        RequestWithCallback requestWithCallback = new RequestWithCallback(takePictureRequestPoll);
        trackCurrentRequest(requestWithCallback);
        Pair<CameraRequest, ProcessingRequest> pairCreateRequests = this.mImagePipeline.createRequests(takePictureRequestPoll, requestWithCallback);
        CameraRequest cameraRequest = pairCreateRequests.first;
        Objects.requireNonNull(cameraRequest);
        final ProcessingRequest processingRequest = pairCreateRequests.second;
        Objects.requireNonNull(processingRequest);
        submitCameraRequest(cameraRequest, new Runnable() { // from class: androidx.camera.core.imagecapture.k
            @Override // java.lang.Runnable
            public final void run() {
                this.f1534c.lambda$issueNextRequest$0(processingRequest);
            }
        });
    }

    @MainThread
    public void offerRequest(@NonNull TakePictureRequest takePictureRequest) {
        Threads.checkMainThread();
        this.mNewRequests.offer(takePictureRequest);
        issueNextRequest();
    }

    @Override // androidx.camera.core.ForwardingImageProxy.OnImageCloseListener
    public void onImageClose(@NonNull ImageProxy imageProxy) {
        CameraXExecutors.mainThreadExecutor().execute(new Runnable() { // from class: androidx.camera.core.imagecapture.m
            @Override // java.lang.Runnable
            public final void run() {
                this.f1537c.issueNextRequest();
            }
        });
    }

    @MainThread
    public void pause() {
        Threads.checkMainThread();
        this.mPaused = true;
    }

    @MainThread
    public void resume() {
        Threads.checkMainThread();
        this.mPaused = false;
        issueNextRequest();
    }
}
