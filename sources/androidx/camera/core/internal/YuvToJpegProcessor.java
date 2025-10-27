package androidx.camera.core.internal;

import android.graphics.Rect;
import android.graphics.YuvImage;
import android.media.Image;
import android.media.ImageWriter;
import android.util.Size;
import android.view.Surface;
import androidx.annotation.GuardedBy;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Logger;
import androidx.camera.core.impl.CaptureProcessor;
import androidx.camera.core.impl.ImageProxyBundle;
import androidx.camera.core.impl.utils.ExifData;
import androidx.camera.core.impl.utils.ExifOutputStream;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.camera.core.internal.compat.ImageWriterCompat;
import androidx.camera.core.internal.utils.ImageUtil;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.core.util.Preconditions;
import com.google.common.util.concurrent.ListenableFuture;
import java.nio.ByteBuffer;
import java.util.List;

@RequiresApi(26)
/* loaded from: classes.dex */
public class YuvToJpegProcessor implements CaptureProcessor {
    private static final String TAG = "YuvToJpegProcessor";
    private static final Rect UNINITIALIZED_RECT = new Rect(0, 0, 0, 0);

    @GuardedBy("mLock")
    CallbackToFutureAdapter.Completer<Void> mCloseCompleter;

    @GuardedBy("mLock")
    private ListenableFuture<Void> mCloseFuture;

    @GuardedBy("mLock")
    private ImageWriter mImageWriter;
    private final int mMaxImages;

    @IntRange(from = 0, to = 100)
    @GuardedBy("mLock")
    private int mQuality;
    private final Object mLock = new Object();

    @GuardedBy("mLock")
    private int mRotationDegrees = 0;

    @GuardedBy("mLock")
    private boolean mClosed = false;

    @GuardedBy("mLock")
    private int mProcessingImages = 0;

    @GuardedBy("mLock")
    private Rect mImageRect = UNINITIALIZED_RECT;

    public YuvToJpegProcessor(@IntRange(from = 0, to = 100) int i2, int i3) {
        this.mQuality = i2;
        this.mMaxImages = i3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Object lambda$getCloseFuture$0(CallbackToFutureAdapter.Completer completer) throws Exception {
        synchronized (this.mLock) {
            this.mCloseCompleter = completer;
        }
        return "YuvToJpegProcessor-close";
    }

    @Override // androidx.camera.core.impl.CaptureProcessor
    public void close() {
        CallbackToFutureAdapter.Completer<Void> completer;
        synchronized (this.mLock) {
            if (this.mClosed) {
                return;
            }
            this.mClosed = true;
            if (this.mProcessingImages != 0 || this.mImageWriter == null) {
                Logger.d(TAG, "close() called while processing. Will close after completion.");
                completer = null;
            } else {
                Logger.d(TAG, "No processing in progress. Closing immediately.");
                this.mImageWriter.close();
                completer = this.mCloseCompleter;
            }
            if (completer != null) {
                completer.set(null);
            }
        }
    }

    @Override // androidx.camera.core.impl.CaptureProcessor
    @NonNull
    public ListenableFuture<Void> getCloseFuture() {
        ListenableFuture<Void> listenableFutureNonCancellationPropagating;
        synchronized (this.mLock) {
            if (this.mClosed && this.mProcessingImages == 0) {
                listenableFutureNonCancellationPropagating = Futures.immediateFuture(null);
            } else {
                if (this.mCloseFuture == null) {
                    this.mCloseFuture = CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.core.internal.g
                        @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
                        public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                            return this.f1581a.lambda$getCloseFuture$0(completer);
                        }
                    });
                }
                listenableFutureNonCancellationPropagating = Futures.nonCancellationPropagating(this.mCloseFuture);
            }
        }
        return listenableFutureNonCancellationPropagating;
    }

    @Override // androidx.camera.core.impl.CaptureProcessor
    public void onOutputSurface(@NonNull Surface surface, int i2) {
        Preconditions.checkState(i2 == 256, "YuvToJpegProcessor only supports JPEG output format.");
        synchronized (this.mLock) {
            if (this.mClosed) {
                Logger.w(TAG, "Cannot set output surface. Processor is closed.");
            } else {
                if (this.mImageWriter != null) {
                    throw new IllegalStateException("Output surface already set.");
                }
                this.mImageWriter = ImageWriterCompat.newInstance(surface, this.mMaxImages, i2);
            }
        }
    }

    @Override // androidx.camera.core.impl.CaptureProcessor
    public void onResolutionUpdate(@NonNull Size size) {
        synchronized (this.mLock) {
            this.mImageRect = new Rect(0, 0, size.getWidth(), size.getHeight());
        }
    }

    @Override // androidx.camera.core.impl.CaptureProcessor
    public void process(@NonNull ImageProxyBundle imageProxyBundle) throws Throwable {
        ImageWriter imageWriter;
        boolean z2;
        Rect rect;
        int i2;
        int i3;
        ImageProxy imageProxy;
        Image imageDequeueInputImage;
        CallbackToFutureAdapter.Completer<Void> completer;
        CallbackToFutureAdapter.Completer<Void> completer2;
        CallbackToFutureAdapter.Completer<Void> completer3;
        List<Integer> captureIds = imageProxyBundle.getCaptureIds();
        boolean z3 = false;
        Preconditions.checkArgument(captureIds.size() == 1, "Processing image bundle have single capture id, but found " + captureIds.size());
        ListenableFuture<ImageProxy> imageProxy2 = imageProxyBundle.getImageProxy(captureIds.get(0).intValue());
        Preconditions.checkArgument(imageProxy2.isDone());
        synchronized (this.mLock) {
            imageWriter = this.mImageWriter;
            z2 = !this.mClosed;
            rect = this.mImageRect;
            if (z2) {
                this.mProcessingImages++;
            }
            i2 = this.mQuality;
            i3 = this.mRotationDegrees;
        }
        try {
            try {
                imageProxy = imageProxy2.get();
                try {
                } catch (Exception e2) {
                    e = e2;
                    imageDequeueInputImage = null;
                } catch (Throwable th) {
                    th = th;
                    imageDequeueInputImage = null;
                }
            } catch (Exception e3) {
                e = e3;
                imageProxy = null;
                imageDequeueInputImage = null;
            } catch (Throwable th2) {
                th = th2;
                imageProxy = null;
                imageDequeueInputImage = null;
            }
            if (!z2) {
                Logger.w(TAG, "Image enqueued for processing on closed processor.");
                imageProxy.close();
                synchronized (this.mLock) {
                    if (z2) {
                        try {
                            int i4 = this.mProcessingImages;
                            this.mProcessingImages = i4 - 1;
                            if (i4 == 0 && this.mClosed) {
                                z3 = true;
                            }
                        } finally {
                        }
                    }
                    completer3 = this.mCloseCompleter;
                }
                if (z3) {
                    imageWriter.close();
                    Logger.d(TAG, "Closed after completion of last image processed.");
                    if (completer3 != null) {
                        completer3.set(null);
                        return;
                    }
                    return;
                }
                return;
            }
            imageDequeueInputImage = imageWriter.dequeueInputImage();
            try {
                ImageProxy imageProxy3 = imageProxy2.get();
                try {
                    Preconditions.checkState(imageProxy3.getFormat() == 35, "Input image is not expected YUV_420_888 image format");
                    YuvImage yuvImage = new YuvImage(ImageUtil.yuv_420_888toNv21(imageProxy3), 17, imageProxy3.getWidth(), imageProxy3.getHeight(), null);
                    ByteBuffer buffer = imageDequeueInputImage.getPlanes()[0].getBuffer();
                    int iPosition = buffer.position();
                    yuvImage.compressToJpeg(rect, i2, new ExifOutputStream(new ByteBufferOutputStream(buffer), ExifData.create(imageProxy3, i3)));
                    imageProxy3.close();
                    try {
                        buffer.limit(buffer.position());
                        buffer.position(iPosition);
                        imageWriter.queueInputImage(imageDequeueInputImage);
                        synchronized (this.mLock) {
                            if (z2) {
                                try {
                                    int i5 = this.mProcessingImages;
                                    this.mProcessingImages = i5 - 1;
                                    if (i5 == 0 && this.mClosed) {
                                        z3 = true;
                                    }
                                } finally {
                                }
                            }
                            completer2 = this.mCloseCompleter;
                        }
                    } catch (Exception e4) {
                        e = e4;
                        imageProxy = null;
                        if (z2) {
                            Logger.e(TAG, "Failed to process YUV -> JPEG", e);
                            imageDequeueInputImage = imageWriter.dequeueInputImage();
                            ByteBuffer buffer2 = imageDequeueInputImage.getPlanes()[0].getBuffer();
                            buffer2.rewind();
                            buffer2.limit(0);
                            imageWriter.queueInputImage(imageDequeueInputImage);
                        }
                        synchronized (this.mLock) {
                            if (z2) {
                                try {
                                    int i6 = this.mProcessingImages;
                                    this.mProcessingImages = i6 - 1;
                                    if (i6 == 0 && this.mClosed) {
                                        z3 = true;
                                    }
                                } finally {
                                }
                            }
                            completer2 = this.mCloseCompleter;
                        }
                        if (imageDequeueInputImage != null) {
                            imageDequeueInputImage.close();
                        }
                        if (imageProxy != null) {
                            imageProxy.close();
                        }
                        if (z3) {
                            imageWriter.close();
                            Logger.d(TAG, "Closed after completion of last image processed.");
                            if (completer2 == null) {
                                return;
                            }
                            completer2.set(null);
                        }
                        return;
                    } catch (Throwable th3) {
                        th = th3;
                        imageProxy = null;
                        synchronized (this.mLock) {
                            if (z2) {
                                try {
                                    int i7 = this.mProcessingImages;
                                    this.mProcessingImages = i7 - 1;
                                    if (i7 == 0 && this.mClosed) {
                                        z3 = true;
                                    }
                                } finally {
                                }
                            }
                            completer = this.mCloseCompleter;
                        }
                        if (imageDequeueInputImage != null) {
                            imageDequeueInputImage.close();
                        }
                        if (imageProxy != null) {
                            imageProxy.close();
                        }
                        if (z3) {
                            imageWriter.close();
                            Logger.d(TAG, "Closed after completion of last image processed.");
                            if (completer != null) {
                                completer.set(null);
                            }
                        }
                        throw th;
                    }
                } catch (Exception e5) {
                    e = e5;
                    imageProxy = imageProxy3;
                } catch (Throwable th4) {
                    th = th4;
                    imageProxy = imageProxy3;
                }
            } catch (Exception e6) {
                e = e6;
            }
            if (z3) {
                imageWriter.close();
                Logger.d(TAG, "Closed after completion of last image processed.");
                if (completer2 == null) {
                    return;
                }
                completer2.set(null);
            }
        } catch (Throwable th5) {
            th = th5;
        }
    }

    public void setJpegQuality(@IntRange(from = 0, to = 100) int i2) {
        synchronized (this.mLock) {
            this.mQuality = i2;
        }
    }

    public void setRotationDegrees(int i2) {
        synchronized (this.mLock) {
            this.mRotationDegrees = i2;
        }
    }
}
