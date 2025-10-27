package androidx.camera.core.imagecapture;

import android.util.Size;
import android.view.Surface;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.annotation.VisibleForTesting;
import androidx.camera.core.ForwardingImageProxy;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.MetadataImageReader;
import androidx.camera.core.SafeCloseImageReaderProxy;
import androidx.camera.core.impl.CameraCaptureCallback;
import androidx.camera.core.impl.DeferrableSurface;
import androidx.camera.core.impl.ImageReaderProxy;
import androidx.camera.core.impl.ImmediateSurface;
import androidx.camera.core.impl.utils.Threads;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.processing.Edge;
import androidx.camera.core.processing.Node;
import androidx.core.util.Consumer;
import androidx.core.util.Preconditions;
import com.google.auto.value.AutoValue;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

@RequiresApi(api = 21)
/* loaded from: classes.dex */
class CaptureNode implements Node<In, Out> {

    @VisibleForTesting
    static final int MAX_IMAGES = 4;
    private In mInputEdge;
    private Out mOutputEdge;
    SafeCloseImageReaderProxy mSafeCloseImageReaderProxy;

    @NonNull
    private final Set<Integer> mPendingStageIds = new HashSet();
    private final Set<ImageProxy> mPendingImages = new HashSet();
    private ProcessingRequest mCurrentRequest = null;

    @AutoValue
    public static abstract class In {
        private CameraCaptureCallback mCameraCaptureCallback;
        private DeferrableSurface mSurface;

        @NonNull
        public static In of(Size size, int i2) {
            return new AutoValue_CaptureNode_In(size, i2, new Edge());
        }

        public void closeSurface() {
            this.mSurface.close();
        }

        public CameraCaptureCallback getCameraCaptureCallback() {
            return this.mCameraCaptureCallback;
        }

        public abstract int getFormat();

        @NonNull
        public abstract Edge<ProcessingRequest> getRequestEdge();

        public abstract Size getSize();

        @NonNull
        public DeferrableSurface getSurface() {
            return this.mSurface;
        }

        public void setCameraCaptureCallback(@NonNull CameraCaptureCallback cameraCaptureCallback) {
            this.mCameraCaptureCallback = cameraCaptureCallback;
        }

        public void setSurface(@NonNull Surface surface) {
            Preconditions.checkState(this.mSurface == null, "The surface is already set.");
            this.mSurface = new ImmediateSurface(surface);
        }
    }

    @AutoValue
    public static abstract class Out {
        public static Out of(int i2) {
            return new AutoValue_CaptureNode_Out(new Edge(), new Edge(), i2);
        }

        public abstract int getFormat();

        public abstract Edge<ImageProxy> getImageEdge();

        public abstract Edge<ProcessingRequest> getRequestEdge();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$transform$0(ImageReaderProxy imageReaderProxy) {
        ImageProxy imageProxyAcquireNextImage = imageReaderProxy.acquireNextImage();
        Objects.requireNonNull(imageProxyAcquireNextImage);
        onImageProxyAvailable(imageProxyAcquireNextImage);
    }

    private void matchAndPropagateImage(@NonNull ImageProxy imageProxy) {
        Object tag = imageProxy.getImageInfo().getTagBundle().getTag(this.mCurrentRequest.getTagBundleKey());
        Objects.requireNonNull(tag);
        int iIntValue = ((Integer) tag).intValue();
        Preconditions.checkState(this.mPendingStageIds.contains(Integer.valueOf(iIntValue)), "Received an unexpected stage id" + iIntValue);
        this.mPendingStageIds.remove(Integer.valueOf(iIntValue));
        if (this.mPendingStageIds.isEmpty()) {
            this.mCurrentRequest.onImageCaptured();
            this.mCurrentRequest = null;
        }
        this.mOutputEdge.getImageEdge().accept(imageProxy);
    }

    @MainThread
    public int getCapacity() {
        Threads.checkMainThread();
        Preconditions.checkState(this.mSafeCloseImageReaderProxy != null, "The ImageReader is not initialized.");
        return this.mSafeCloseImageReaderProxy.getCapacity();
    }

    @NonNull
    @VisibleForTesting
    public In getInputEdge() {
        return this.mInputEdge;
    }

    @MainThread
    @VisibleForTesting
    public void onImageProxyAvailable(@NonNull ImageProxy imageProxy) {
        Threads.checkMainThread();
        if (this.mCurrentRequest == null) {
            this.mPendingImages.add(imageProxy);
        } else {
            matchAndPropagateImage(imageProxy);
        }
    }

    @MainThread
    @VisibleForTesting
    public void onRequestAvailable(@NonNull ProcessingRequest processingRequest) {
        Threads.checkMainThread();
        boolean z2 = true;
        Preconditions.checkState(getCapacity() > 0, "Too many acquire images. Close image to be able to process next.");
        if (this.mCurrentRequest != null && !this.mPendingStageIds.isEmpty()) {
            z2 = false;
        }
        Preconditions.checkState(z2, "The previous request is not complete");
        this.mCurrentRequest = processingRequest;
        this.mPendingStageIds.addAll(processingRequest.getStageIds());
        this.mOutputEdge.getRequestEdge().accept(processingRequest);
        Iterator<ImageProxy> it = this.mPendingImages.iterator();
        while (it.hasNext()) {
            matchAndPropagateImage(it.next());
        }
        this.mPendingImages.clear();
    }

    @Override // androidx.camera.core.processing.Node
    @MainThread
    public void release() {
        Threads.checkMainThread();
        SafeCloseImageReaderProxy safeCloseImageReaderProxy = this.mSafeCloseImageReaderProxy;
        if (safeCloseImageReaderProxy != null) {
            safeCloseImageReaderProxy.safeClose();
        }
        In in = this.mInputEdge;
        if (in != null) {
            in.closeSurface();
        }
    }

    @MainThread
    public void setOnImageCloseListener(ForwardingImageProxy.OnImageCloseListener onImageCloseListener) {
        Threads.checkMainThread();
        Preconditions.checkState(this.mSafeCloseImageReaderProxy != null, "The ImageReader is not initialized.");
        this.mSafeCloseImageReaderProxy.setOnImageCloseListener(onImageCloseListener);
    }

    @Override // androidx.camera.core.processing.Node
    @NonNull
    public Out transform(@NonNull In in) {
        this.mInputEdge = in;
        Size size = in.getSize();
        MetadataImageReader metadataImageReader = new MetadataImageReader(size.getWidth(), size.getHeight(), in.getFormat(), 4);
        this.mSafeCloseImageReaderProxy = new SafeCloseImageReaderProxy(metadataImageReader);
        in.setCameraCaptureCallback(metadataImageReader.getCameraCaptureCallback());
        Surface surface = metadataImageReader.getSurface();
        Objects.requireNonNull(surface);
        in.setSurface(surface);
        metadataImageReader.setOnImageAvailableListener(new ImageReaderProxy.OnImageAvailableListener() { // from class: androidx.camera.core.imagecapture.a
            @Override // androidx.camera.core.impl.ImageReaderProxy.OnImageAvailableListener
            public final void onImageAvailable(ImageReaderProxy imageReaderProxy) {
                this.f1520a.lambda$transform$0(imageReaderProxy);
            }
        }, CameraXExecutors.mainThreadExecutor());
        in.getRequestEdge().setListener(new Consumer() { // from class: androidx.camera.core.imagecapture.b
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                this.f1521c.onRequestAvailable((ProcessingRequest) obj);
            }
        });
        Out outOf = Out.of(in.getFormat());
        this.mOutputEdge = outOf;
        return outOf;
    }
}
