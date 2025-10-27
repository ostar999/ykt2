package com.nostra13.universalimageloader.core;

import android.graphics.Bitmap;
import android.os.Handler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.LoadedFrom;
import com.nostra13.universalimageloader.core.assist.ViewScaleType;
import com.nostra13.universalimageloader.core.decode.ImageDecoder;
import com.nostra13.universalimageloader.core.decode.ImageDecodingInfo;
import com.nostra13.universalimageloader.core.download.ImageDownloader;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.utils.IoUtils;
import com.nostra13.universalimageloader.utils.L;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes4.dex */
final class LoadAndDisplayImageTask implements Runnable, IoUtils.CopyListener {
    private static final String ERROR_NO_IMAGE_STREAM = "No stream for image [%s]";
    private static final String ERROR_POST_PROCESSOR_NULL = "Post-processor returned null [%s]";
    private static final String ERROR_PRE_PROCESSOR_NULL = "Pre-processor returned null [%s]";
    private static final String ERROR_PROCESSOR_FOR_DISK_CACHE_NULL = "Bitmap processor for disk cache returned null [%s]";
    private static final String LOG_CACHE_IMAGE_IN_MEMORY = "Cache image in memory [%s]";
    private static final String LOG_CACHE_IMAGE_ON_DISK = "Cache image on disk [%s]";
    private static final String LOG_DELAY_BEFORE_LOADING = "Delay %d ms before loading...  [%s]";
    private static final String LOG_GET_IMAGE_FROM_MEMORY_CACHE_AFTER_WAITING = "...Get cached bitmap from memory after waiting. [%s]";
    private static final String LOG_LOAD_IMAGE_FROM_DISK_CACHE = "Load image from disk cache [%s]";
    private static final String LOG_LOAD_IMAGE_FROM_NETWORK = "Load image from network [%s]";
    private static final String LOG_POSTPROCESS_IMAGE = "PostProcess image before displaying [%s]";
    private static final String LOG_PREPROCESS_IMAGE = "PreProcess image before caching in memory [%s]";
    private static final String LOG_PROCESS_IMAGE_BEFORE_CACHE_ON_DISK = "Process image before cache on disk [%s]";
    private static final String LOG_RESIZE_CACHED_IMAGE_FILE = "Resize image in disk cache [%s]";
    private static final String LOG_RESUME_AFTER_PAUSE = ".. Resume loading [%s]";
    private static final String LOG_START_DISPLAY_IMAGE_TASK = "Start display image task [%s]";
    private static final String LOG_TASK_CANCELLED_IMAGEAWARE_COLLECTED = "ImageAware was collected by GC. Task is cancelled. [%s]";
    private static final String LOG_TASK_CANCELLED_IMAGEAWARE_REUSED = "ImageAware is reused for another image. Task is cancelled. [%s]";
    private static final String LOG_TASK_INTERRUPTED = "Task was interrupted [%s]";
    private static final String LOG_WAITING_FOR_IMAGE_LOADED = "Image already is loading. Waiting... [%s]";
    private static final String LOG_WAITING_FOR_RESUME = "ImageLoader is paused. Waiting...  [%s]";
    private final ImageLoaderConfiguration configuration;
    private final ImageDecoder decoder;
    private final ImageDownloader downloader;
    private final ImageLoaderEngine engine;
    private final Handler handler;
    final ImageAware imageAware;
    private final ImageLoadingInfo imageLoadingInfo;
    final ImageLoadingListener listener;
    private LoadedFrom loadedFrom = LoadedFrom.NETWORK;
    private final String memoryCacheKey;
    private final ImageDownloader networkDeniedDownloader;
    final DisplayImageOptions options;
    final ImageLoadingProgressListener progressListener;
    private final ImageDownloader slowNetworkDownloader;
    private final boolean syncLoading;
    private final ImageSize targetSize;
    final String uri;

    public class TaskCancelledException extends Exception {
        public TaskCancelledException() {
        }
    }

    public LoadAndDisplayImageTask(ImageLoaderEngine imageLoaderEngine, ImageLoadingInfo imageLoadingInfo, Handler handler) {
        this.engine = imageLoaderEngine;
        this.imageLoadingInfo = imageLoadingInfo;
        this.handler = handler;
        ImageLoaderConfiguration imageLoaderConfiguration = imageLoaderEngine.configuration;
        this.configuration = imageLoaderConfiguration;
        this.downloader = imageLoaderConfiguration.downloader;
        this.networkDeniedDownloader = imageLoaderConfiguration.networkDeniedDownloader;
        this.slowNetworkDownloader = imageLoaderConfiguration.slowNetworkDownloader;
        this.decoder = imageLoaderConfiguration.decoder;
        this.uri = imageLoadingInfo.uri;
        this.memoryCacheKey = imageLoadingInfo.memoryCacheKey;
        this.imageAware = imageLoadingInfo.imageAware;
        this.targetSize = imageLoadingInfo.targetSize;
        DisplayImageOptions displayImageOptions = imageLoadingInfo.options;
        this.options = displayImageOptions;
        this.listener = imageLoadingInfo.listener;
        this.progressListener = imageLoadingInfo.progressListener;
        this.syncLoading = displayImageOptions.isSyncLoading();
    }

    private void checkTaskInterrupted() throws TaskCancelledException {
        if (isTaskInterrupted()) {
            throw new TaskCancelledException();
        }
    }

    private void checkTaskNotActual() throws TaskCancelledException {
        checkViewCollected();
        checkViewReused();
    }

    private void checkViewCollected() throws TaskCancelledException {
        if (isViewCollected()) {
            throw new TaskCancelledException();
        }
    }

    private void checkViewReused() throws TaskCancelledException {
        if (isViewReused()) {
            throw new TaskCancelledException();
        }
    }

    private Bitmap decodeImage(String str) throws IOException {
        return this.decoder.decode(new ImageDecodingInfo(this.memoryCacheKey, str, this.uri, this.targetSize, this.imageAware.getScaleType(), getDownloader(), this.options));
    }

    private boolean delayIfNeed() throws InterruptedException {
        if (!this.options.shouldDelayBeforeLoading()) {
            return false;
        }
        L.d(LOG_DELAY_BEFORE_LOADING, Integer.valueOf(this.options.getDelayBeforeLoading()), this.memoryCacheKey);
        try {
            Thread.sleep(this.options.getDelayBeforeLoading());
            return isTaskNotActual();
        } catch (InterruptedException unused) {
            L.e(LOG_TASK_INTERRUPTED, this.memoryCacheKey);
            return true;
        }
    }

    private boolean downloadImage() throws IOException {
        InputStream stream = getDownloader().getStream(this.uri, this.options.getExtraForDownloader());
        if (stream == null) {
            L.e(ERROR_NO_IMAGE_STREAM, this.memoryCacheKey);
            return false;
        }
        try {
            return this.configuration.diskCache.save(this.uri, stream, this);
        } finally {
            IoUtils.closeSilently(stream);
        }
    }

    private void fireCancelEvent() {
        if (this.syncLoading || isTaskInterrupted()) {
            return;
        }
        runTask(new Runnable() { // from class: com.nostra13.universalimageloader.core.LoadAndDisplayImageTask.3
            @Override // java.lang.Runnable
            public void run() {
                LoadAndDisplayImageTask loadAndDisplayImageTask = LoadAndDisplayImageTask.this;
                loadAndDisplayImageTask.listener.onLoadingCancelled(loadAndDisplayImageTask.uri, loadAndDisplayImageTask.imageAware.getWrappedView());
            }
        }, false, this.handler, this.engine);
    }

    private void fireFailEvent(final FailReason.FailType failType, final Throwable th) {
        if (this.syncLoading || isTaskInterrupted() || isTaskNotActual()) {
            return;
        }
        runTask(new Runnable() { // from class: com.nostra13.universalimageloader.core.LoadAndDisplayImageTask.2
            @Override // java.lang.Runnable
            public void run() {
                if (LoadAndDisplayImageTask.this.options.shouldShowImageOnFail()) {
                    LoadAndDisplayImageTask loadAndDisplayImageTask = LoadAndDisplayImageTask.this;
                    loadAndDisplayImageTask.imageAware.setImageDrawable(loadAndDisplayImageTask.options.getImageOnFail(loadAndDisplayImageTask.configuration.resources));
                }
                LoadAndDisplayImageTask loadAndDisplayImageTask2 = LoadAndDisplayImageTask.this;
                loadAndDisplayImageTask2.listener.onLoadingFailed(loadAndDisplayImageTask2.uri, loadAndDisplayImageTask2.imageAware.getWrappedView(), new FailReason(failType, th));
            }
        }, false, this.handler, this.engine);
    }

    private boolean fireProgressEvent(final int i2, final int i3) {
        if (isTaskInterrupted() || isTaskNotActual()) {
            return false;
        }
        if (this.progressListener == null) {
            return true;
        }
        runTask(new Runnable() { // from class: com.nostra13.universalimageloader.core.LoadAndDisplayImageTask.1
            @Override // java.lang.Runnable
            public void run() {
                LoadAndDisplayImageTask loadAndDisplayImageTask = LoadAndDisplayImageTask.this;
                loadAndDisplayImageTask.progressListener.onProgressUpdate(loadAndDisplayImageTask.uri, loadAndDisplayImageTask.imageAware.getWrappedView(), i2, i3);
            }
        }, false, this.handler, this.engine);
        return true;
    }

    private ImageDownloader getDownloader() {
        return this.engine.isNetworkDenied() ? this.networkDeniedDownloader : this.engine.isSlowNetwork() ? this.slowNetworkDownloader : this.downloader;
    }

    private boolean isTaskInterrupted() {
        if (!Thread.interrupted()) {
            return false;
        }
        L.d(LOG_TASK_INTERRUPTED, this.memoryCacheKey);
        return true;
    }

    private boolean isTaskNotActual() {
        return isViewCollected() || isViewReused();
    }

    private boolean isViewCollected() {
        if (!this.imageAware.isCollected()) {
            return false;
        }
        L.d(LOG_TASK_CANCELLED_IMAGEAWARE_COLLECTED, this.memoryCacheKey);
        return true;
    }

    private boolean isViewReused() {
        if (!(!this.memoryCacheKey.equals(this.engine.getLoadingUriForView(this.imageAware)))) {
            return false;
        }
        L.d(LOG_TASK_CANCELLED_IMAGEAWARE_REUSED, this.memoryCacheKey);
        return true;
    }

    private boolean resizeAndSaveImage(int i2, int i3) throws IOException {
        File file = this.configuration.diskCache.get(this.uri);
        if (file == null || !file.exists()) {
            return false;
        }
        Bitmap bitmapDecode = this.decoder.decode(new ImageDecodingInfo(this.memoryCacheKey, ImageDownloader.Scheme.FILE.wrap(file.getAbsolutePath()), this.uri, new ImageSize(i2, i3), ViewScaleType.FIT_INSIDE, getDownloader(), new DisplayImageOptions.Builder().cloneFrom(this.options).imageScaleType(ImageScaleType.IN_SAMPLE_INT).build()));
        if (bitmapDecode != null && this.configuration.processorForDiskCache != null) {
            L.d(LOG_PROCESS_IMAGE_BEFORE_CACHE_ON_DISK, this.memoryCacheKey);
            bitmapDecode = this.configuration.processorForDiskCache.process(bitmapDecode);
            if (bitmapDecode == null) {
                L.e(ERROR_PROCESSOR_FOR_DISK_CACHE_NULL, this.memoryCacheKey);
            }
        }
        if (bitmapDecode == null) {
            return false;
        }
        boolean zSave = this.configuration.diskCache.save(this.uri, bitmapDecode);
        bitmapDecode.recycle();
        return zSave;
    }

    public static void runTask(Runnable runnable, boolean z2, Handler handler, ImageLoaderEngine imageLoaderEngine) {
        if (z2) {
            runnable.run();
        } else if (handler == null) {
            imageLoaderEngine.fireCallback(runnable);
        } else {
            handler.post(runnable);
        }
    }

    private boolean tryCacheImageOnDisk() throws TaskCancelledException {
        L.d(LOG_CACHE_IMAGE_ON_DISK, this.memoryCacheKey);
        try {
            boolean zDownloadImage = downloadImage();
            if (zDownloadImage) {
                ImageLoaderConfiguration imageLoaderConfiguration = this.configuration;
                int i2 = imageLoaderConfiguration.maxImageWidthForDiskCache;
                int i3 = imageLoaderConfiguration.maxImageHeightForDiskCache;
                if (i2 > 0 || i3 > 0) {
                    L.d(LOG_RESIZE_CACHED_IMAGE_FILE, this.memoryCacheKey);
                    resizeAndSaveImage(i2, i3);
                }
            }
            return zDownloadImage;
        } catch (IOException e2) {
            L.e(e2);
            return false;
        }
    }

    private Bitmap tryLoadBitmap() throws TaskCancelledException {
        Bitmap bitmapDecodeImage;
        File file;
        Bitmap bitmap = null;
        try {
            try {
                File file2 = this.configuration.diskCache.get(this.uri);
                if (file2 == null || !file2.exists() || file2.length() <= 0) {
                    bitmapDecodeImage = null;
                } else {
                    L.d(LOG_LOAD_IMAGE_FROM_DISK_CACHE, this.memoryCacheKey);
                    this.loadedFrom = LoadedFrom.DISC_CACHE;
                    checkTaskNotActual();
                    bitmapDecodeImage = decodeImage(ImageDownloader.Scheme.FILE.wrap(file2.getAbsolutePath()));
                }
                if (bitmapDecodeImage != null) {
                    try {
                        if (bitmapDecodeImage.getWidth() > 0 && bitmapDecodeImage.getHeight() > 0) {
                            return bitmapDecodeImage;
                        }
                    } catch (IOException e2) {
                        Bitmap bitmap2 = bitmapDecodeImage;
                        e = e2;
                        bitmap = bitmap2;
                        L.e(e);
                        fireFailEvent(FailReason.FailType.IO_ERROR, e);
                        return bitmap;
                    } catch (IllegalStateException unused) {
                        fireFailEvent(FailReason.FailType.NETWORK_DENIED, null);
                        return bitmapDecodeImage;
                    } catch (OutOfMemoryError e3) {
                        Bitmap bitmap3 = bitmapDecodeImage;
                        e = e3;
                        bitmap = bitmap3;
                        L.e(e);
                        fireFailEvent(FailReason.FailType.OUT_OF_MEMORY, e);
                        return bitmap;
                    } catch (Throwable th) {
                        Bitmap bitmap4 = bitmapDecodeImage;
                        th = th;
                        bitmap = bitmap4;
                        L.e(th);
                        fireFailEvent(FailReason.FailType.UNKNOWN, th);
                        return bitmap;
                    }
                }
                L.d(LOG_LOAD_IMAGE_FROM_NETWORK, this.memoryCacheKey);
                this.loadedFrom = LoadedFrom.NETWORK;
                String strWrap = this.uri;
                if (this.options.isCacheOnDisk() && tryCacheImageOnDisk() && (file = this.configuration.diskCache.get(this.uri)) != null) {
                    strWrap = ImageDownloader.Scheme.FILE.wrap(file.getAbsolutePath());
                }
                checkTaskNotActual();
                bitmapDecodeImage = decodeImage(strWrap);
                if (bitmapDecodeImage != null && bitmapDecodeImage.getWidth() > 0 && bitmapDecodeImage.getHeight() > 0) {
                    return bitmapDecodeImage;
                }
                fireFailEvent(FailReason.FailType.DECODING_ERROR, null);
                return bitmapDecodeImage;
            } catch (IOException e4) {
                e = e4;
            } catch (IllegalStateException unused2) {
                bitmapDecodeImage = null;
            } catch (OutOfMemoryError e5) {
                e = e5;
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (TaskCancelledException e6) {
            throw e6;
        }
    }

    private boolean waitIfPaused() {
        AtomicBoolean pause = this.engine.getPause();
        if (pause.get()) {
            synchronized (this.engine.getPauseLock()) {
                if (pause.get()) {
                    L.d(LOG_WAITING_FOR_RESUME, this.memoryCacheKey);
                    try {
                        this.engine.getPauseLock().wait();
                        L.d(LOG_RESUME_AFTER_PAUSE, this.memoryCacheKey);
                    } catch (InterruptedException unused) {
                        L.e(LOG_TASK_INTERRUPTED, this.memoryCacheKey);
                        return true;
                    }
                }
            }
        }
        return isTaskNotActual();
    }

    public String getLoadingUri() {
        return this.uri;
    }

    @Override // com.nostra13.universalimageloader.utils.IoUtils.CopyListener
    public boolean onBytesCopied(int i2, int i3) {
        return this.syncLoading || fireProgressEvent(i2, i3);
    }

    @Override // java.lang.Runnable
    public void run() {
        if (waitIfPaused() || delayIfNeed()) {
            return;
        }
        ReentrantLock reentrantLock = this.imageLoadingInfo.loadFromUriLock;
        L.d(LOG_START_DISPLAY_IMAGE_TASK, this.memoryCacheKey);
        if (reentrantLock.isLocked()) {
            L.d(LOG_WAITING_FOR_IMAGE_LOADED, this.memoryCacheKey);
        }
        reentrantLock.lock();
        try {
            checkTaskNotActual();
            Bitmap bitmapTryLoadBitmap = this.configuration.memoryCache.get(this.memoryCacheKey);
            if (bitmapTryLoadBitmap == null || bitmapTryLoadBitmap.isRecycled()) {
                bitmapTryLoadBitmap = tryLoadBitmap();
                if (bitmapTryLoadBitmap == null) {
                    return;
                }
                checkTaskNotActual();
                checkTaskInterrupted();
                if (this.options.shouldPreProcess()) {
                    L.d(LOG_PREPROCESS_IMAGE, this.memoryCacheKey);
                    bitmapTryLoadBitmap = this.options.getPreProcessor().process(bitmapTryLoadBitmap);
                    if (bitmapTryLoadBitmap == null) {
                        L.e(ERROR_PRE_PROCESSOR_NULL, this.memoryCacheKey);
                    }
                }
                if (bitmapTryLoadBitmap != null && this.options.isCacheInMemory()) {
                    L.d(LOG_CACHE_IMAGE_IN_MEMORY, this.memoryCacheKey);
                    this.configuration.memoryCache.put(this.memoryCacheKey, bitmapTryLoadBitmap);
                }
            } else {
                this.loadedFrom = LoadedFrom.MEMORY_CACHE;
                L.d(LOG_GET_IMAGE_FROM_MEMORY_CACHE_AFTER_WAITING, this.memoryCacheKey);
            }
            if (bitmapTryLoadBitmap != null && this.options.shouldPostProcess()) {
                L.d(LOG_POSTPROCESS_IMAGE, this.memoryCacheKey);
                bitmapTryLoadBitmap = this.options.getPostProcessor().process(bitmapTryLoadBitmap);
                if (bitmapTryLoadBitmap == null) {
                    L.e(ERROR_POST_PROCESSOR_NULL, this.memoryCacheKey);
                }
            }
            checkTaskNotActual();
            checkTaskInterrupted();
            reentrantLock.unlock();
            runTask(new DisplayBitmapTask(bitmapTryLoadBitmap, this.imageLoadingInfo, this.engine, this.loadedFrom), this.syncLoading, this.handler, this.engine);
        } catch (TaskCancelledException unused) {
            fireCancelEvent();
        } finally {
            reentrantLock.unlock();
        }
    }
}
