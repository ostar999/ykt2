package androidx.camera.core;

import android.os.Handler;
import android.os.Looper;
import android.util.Size;
import android.view.Surface;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.arch.core.util.Function;
import androidx.camera.core.impl.CameraCaptureCallback;
import androidx.camera.core.impl.CaptureProcessor;
import androidx.camera.core.impl.CaptureStage;
import androidx.camera.core.impl.DeferrableSurface;
import androidx.camera.core.impl.ImageReaderProxy;
import androidx.camera.core.impl.SingleImageProxyBundle;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.impl.utils.futures.FutureCallback;
import androidx.camera.core.impl.utils.futures.FutureChain;
import androidx.camera.core.impl.utils.futures.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.ScheduledExecutorService;

@RequiresApi(21)
/* loaded from: classes.dex */
final class ProcessingSurface extends DeferrableSurface {
    private static final int MAX_IMAGES = 2;
    private static final String TAG = "ProcessingSurfaceTextur";
    private final CameraCaptureCallback mCameraCaptureCallback;

    @NonNull
    @GuardedBy("mLock")
    final CaptureProcessor mCaptureProcessor;
    final CaptureStage mCaptureStage;
    private final Handler mImageReaderHandler;
    private final MetadataImageReader mInputImageReader;
    private final Surface mInputSurface;
    final Object mLock;
    private final DeferrableSurface mOutputDeferrableSurface;

    @GuardedBy("mLock")
    boolean mReleased;

    @NonNull
    private final Size mResolution;
    private String mTagBundleKey;
    private final ImageReaderProxy.OnImageAvailableListener mTransformedListener;

    public ProcessingSurface(int i2, int i3, int i4, @Nullable Handler handler, @NonNull CaptureStage captureStage, @NonNull CaptureProcessor captureProcessor, @NonNull DeferrableSurface deferrableSurface, @NonNull String str) {
        super(new Size(i2, i3), i4);
        this.mLock = new Object();
        ImageReaderProxy.OnImageAvailableListener onImageAvailableListener = new ImageReaderProxy.OnImageAvailableListener() { // from class: androidx.camera.core.i1
            @Override // androidx.camera.core.impl.ImageReaderProxy.OnImageAvailableListener
            public final void onImageAvailable(ImageReaderProxy imageReaderProxy) {
                this.f1519a.lambda$new$0(imageReaderProxy);
            }
        };
        this.mTransformedListener = onImageAvailableListener;
        this.mReleased = false;
        Size size = new Size(i2, i3);
        this.mResolution = size;
        if (handler != null) {
            this.mImageReaderHandler = handler;
        } else {
            Looper looperMyLooper = Looper.myLooper();
            if (looperMyLooper == null) {
                throw new IllegalStateException("Creating a ProcessingSurface requires a non-null Handler, or be created  on a thread with a Looper.");
            }
            this.mImageReaderHandler = new Handler(looperMyLooper);
        }
        ScheduledExecutorService scheduledExecutorServiceNewHandlerExecutor = CameraXExecutors.newHandlerExecutor(this.mImageReaderHandler);
        MetadataImageReader metadataImageReader = new MetadataImageReader(i2, i3, i4, 2);
        this.mInputImageReader = metadataImageReader;
        metadataImageReader.setOnImageAvailableListener(onImageAvailableListener, scheduledExecutorServiceNewHandlerExecutor);
        this.mInputSurface = metadataImageReader.getSurface();
        this.mCameraCaptureCallback = metadataImageReader.getCameraCaptureCallback();
        this.mCaptureProcessor = captureProcessor;
        captureProcessor.onResolutionUpdate(size);
        this.mCaptureStage = captureStage;
        this.mOutputDeferrableSurface = deferrableSurface;
        this.mTagBundleKey = str;
        Futures.addCallback(deferrableSurface.getSurface(), new FutureCallback<Surface>() { // from class: androidx.camera.core.ProcessingSurface.1
            @Override // androidx.camera.core.impl.utils.futures.FutureCallback
            public void onFailure(@NonNull Throwable th) {
                Logger.e(ProcessingSurface.TAG, "Failed to extract Listenable<Surface>.", th);
            }

            @Override // androidx.camera.core.impl.utils.futures.FutureCallback
            public void onSuccess(@Nullable Surface surface) {
                synchronized (ProcessingSurface.this.mLock) {
                    ProcessingSurface.this.mCaptureProcessor.onOutputSurface(surface, 1);
                }
            }
        }, CameraXExecutors.directExecutor());
        getTerminationFuture().addListener(new Runnable() { // from class: androidx.camera.core.j1
            @Override // java.lang.Runnable
            public final void run() {
                this.f1589c.release();
            }
        }, CameraXExecutors.directExecutor());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(ImageReaderProxy imageReaderProxy) {
        synchronized (this.mLock) {
            imageIncoming(imageReaderProxy);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Surface lambda$provideSurface$1(Surface surface) {
        return this.mInputSurface;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void release() {
        synchronized (this.mLock) {
            if (this.mReleased) {
                return;
            }
            this.mInputImageReader.clearOnImageAvailableListener();
            this.mInputImageReader.close();
            this.mInputSurface.release();
            this.mOutputDeferrableSurface.close();
            this.mReleased = true;
        }
    }

    @Nullable
    public CameraCaptureCallback getCameraCaptureCallback() {
        CameraCaptureCallback cameraCaptureCallback;
        synchronized (this.mLock) {
            if (this.mReleased) {
                throw new IllegalStateException("ProcessingSurface already released!");
            }
            cameraCaptureCallback = this.mCameraCaptureCallback;
        }
        return cameraCaptureCallback;
    }

    @GuardedBy("mLock")
    public void imageIncoming(ImageReaderProxy imageReaderProxy) {
        ImageProxy imageProxyAcquireNextImage;
        if (this.mReleased) {
            return;
        }
        try {
            imageProxyAcquireNextImage = imageReaderProxy.acquireNextImage();
        } catch (IllegalStateException e2) {
            Logger.e(TAG, "Failed to acquire next image.", e2);
            imageProxyAcquireNextImage = null;
        }
        if (imageProxyAcquireNextImage == null) {
            return;
        }
        ImageInfo imageInfo = imageProxyAcquireNextImage.getImageInfo();
        if (imageInfo == null) {
            imageProxyAcquireNextImage.close();
            return;
        }
        Integer num = (Integer) imageInfo.getTagBundle().getTag(this.mTagBundleKey);
        if (num == null) {
            imageProxyAcquireNextImage.close();
            return;
        }
        if (this.mCaptureStage.getId() != num.intValue()) {
            Logger.w(TAG, "ImageProxyBundle does not contain this id: " + num);
            imageProxyAcquireNextImage.close();
            return;
        }
        SingleImageProxyBundle singleImageProxyBundle = new SingleImageProxyBundle(imageProxyAcquireNextImage, this.mTagBundleKey);
        try {
            incrementUseCount();
            this.mCaptureProcessor.process(singleImageProxyBundle);
            singleImageProxyBundle.close();
            decrementUseCount();
        } catch (DeferrableSurface.SurfaceClosedException unused) {
            Logger.d(TAG, "The ProcessingSurface has been closed. Don't process the incoming image.");
            singleImageProxyBundle.close();
        }
    }

    @Override // androidx.camera.core.impl.DeferrableSurface
    @NonNull
    public ListenableFuture<Surface> provideSurface() {
        return FutureChain.from(this.mOutputDeferrableSurface.getSurface()).transform(new Function() { // from class: androidx.camera.core.h1
            @Override // androidx.arch.core.util.Function
            public final Object apply(Object obj) {
                return this.f1512a.lambda$provideSurface$1((Surface) obj);
            }
        }, CameraXExecutors.directExecutor());
    }
}
