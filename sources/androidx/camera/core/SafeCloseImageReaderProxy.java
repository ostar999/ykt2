package androidx.camera.core;

import android.view.Surface;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.camera.core.ForwardingImageProxy;
import androidx.camera.core.impl.ImageReaderProxy;
import java.util.concurrent.Executor;

@RequiresApi(21)
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class SafeCloseImageReaderProxy implements ImageReaderProxy {
    private ForwardingImageProxy.OnImageCloseListener mForwardingImageCloseListener;

    @GuardedBy("mLock")
    private final ImageReaderProxy mImageReaderProxy;

    @Nullable
    private final Surface mSurface;
    private final Object mLock = new Object();

    @GuardedBy("mLock")
    private int mOutstandingImages = 0;

    @GuardedBy("mLock")
    private boolean mIsClosed = false;
    private final ForwardingImageProxy.OnImageCloseListener mImageCloseListener = new ForwardingImageProxy.OnImageCloseListener() { // from class: androidx.camera.core.k1
        @Override // androidx.camera.core.ForwardingImageProxy.OnImageCloseListener
        public final void onImageClose(ImageProxy imageProxy) {
            this.f1594a.lambda$new$0(imageProxy);
        }
    };

    public SafeCloseImageReaderProxy(@NonNull ImageReaderProxy imageReaderProxy) {
        this.mImageReaderProxy = imageReaderProxy;
        this.mSurface = imageReaderProxy.getSurface();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(ImageProxy imageProxy) {
        ForwardingImageProxy.OnImageCloseListener onImageCloseListener;
        synchronized (this.mLock) {
            int i2 = this.mOutstandingImages - 1;
            this.mOutstandingImages = i2;
            if (this.mIsClosed && i2 == 0) {
                close();
            }
            onImageCloseListener = this.mForwardingImageCloseListener;
        }
        if (onImageCloseListener != null) {
            onImageCloseListener.onImageClose(imageProxy);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setOnImageAvailableListener$1(ImageReaderProxy.OnImageAvailableListener onImageAvailableListener, ImageReaderProxy imageReaderProxy) {
        onImageAvailableListener.onImageAvailable(this);
    }

    @Nullable
    @GuardedBy("mLock")
    private ImageProxy wrapImageProxy(@Nullable ImageProxy imageProxy) {
        if (imageProxy == null) {
            return null;
        }
        this.mOutstandingImages++;
        SingleCloseImageProxy singleCloseImageProxy = new SingleCloseImageProxy(imageProxy);
        singleCloseImageProxy.addOnImageCloseListener(this.mImageCloseListener);
        return singleCloseImageProxy;
    }

    @Override // androidx.camera.core.impl.ImageReaderProxy
    @Nullable
    public ImageProxy acquireLatestImage() {
        ImageProxy imageProxyWrapImageProxy;
        synchronized (this.mLock) {
            imageProxyWrapImageProxy = wrapImageProxy(this.mImageReaderProxy.acquireLatestImage());
        }
        return imageProxyWrapImageProxy;
    }

    @Override // androidx.camera.core.impl.ImageReaderProxy
    @Nullable
    public ImageProxy acquireNextImage() {
        ImageProxy imageProxyWrapImageProxy;
        synchronized (this.mLock) {
            imageProxyWrapImageProxy = wrapImageProxy(this.mImageReaderProxy.acquireNextImage());
        }
        return imageProxyWrapImageProxy;
    }

    @Override // androidx.camera.core.impl.ImageReaderProxy
    public void clearOnImageAvailableListener() {
        synchronized (this.mLock) {
            this.mImageReaderProxy.clearOnImageAvailableListener();
        }
    }

    @Override // androidx.camera.core.impl.ImageReaderProxy
    public void close() {
        synchronized (this.mLock) {
            Surface surface = this.mSurface;
            if (surface != null) {
                surface.release();
            }
            this.mImageReaderProxy.close();
        }
    }

    public int getCapacity() {
        int maxImages;
        synchronized (this.mLock) {
            maxImages = this.mImageReaderProxy.getMaxImages() - this.mOutstandingImages;
        }
        return maxImages;
    }

    @Override // androidx.camera.core.impl.ImageReaderProxy
    public int getHeight() {
        int height;
        synchronized (this.mLock) {
            height = this.mImageReaderProxy.getHeight();
        }
        return height;
    }

    @Override // androidx.camera.core.impl.ImageReaderProxy
    public int getImageFormat() {
        int imageFormat;
        synchronized (this.mLock) {
            imageFormat = this.mImageReaderProxy.getImageFormat();
        }
        return imageFormat;
    }

    @Override // androidx.camera.core.impl.ImageReaderProxy
    public int getMaxImages() {
        int maxImages;
        synchronized (this.mLock) {
            maxImages = this.mImageReaderProxy.getMaxImages();
        }
        return maxImages;
    }

    @Override // androidx.camera.core.impl.ImageReaderProxy
    @Nullable
    public Surface getSurface() {
        Surface surface;
        synchronized (this.mLock) {
            surface = this.mImageReaderProxy.getSurface();
        }
        return surface;
    }

    @Override // androidx.camera.core.impl.ImageReaderProxy
    public int getWidth() {
        int width;
        synchronized (this.mLock) {
            width = this.mImageReaderProxy.getWidth();
        }
        return width;
    }

    public void safeClose() {
        synchronized (this.mLock) {
            this.mIsClosed = true;
            this.mImageReaderProxy.clearOnImageAvailableListener();
            if (this.mOutstandingImages == 0) {
                close();
            }
        }
    }

    @Override // androidx.camera.core.impl.ImageReaderProxy
    public void setOnImageAvailableListener(@NonNull final ImageReaderProxy.OnImageAvailableListener onImageAvailableListener, @NonNull Executor executor) {
        synchronized (this.mLock) {
            this.mImageReaderProxy.setOnImageAvailableListener(new ImageReaderProxy.OnImageAvailableListener() { // from class: androidx.camera.core.l1
                @Override // androidx.camera.core.impl.ImageReaderProxy.OnImageAvailableListener
                public final void onImageAvailable(ImageReaderProxy imageReaderProxy) {
                    this.f1597a.lambda$setOnImageAvailableListener$1(onImageAvailableListener, imageReaderProxy);
                }
            }, executor);
        }
    }

    public void setOnImageCloseListener(@NonNull ForwardingImageProxy.OnImageCloseListener onImageCloseListener) {
        synchronized (this.mLock) {
            this.mForwardingImageCloseListener = onImageCloseListener;
        }
    }
}
