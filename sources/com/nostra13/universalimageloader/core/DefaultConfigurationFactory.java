package com.nostra13.universalimageloader.core;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.nostra13.universalimageloader.cache.disc.DiskCache;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.impl.ext.LruDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.FileNameGenerator;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.MemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.assist.deque.LIFOLinkedBlockingDeque;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.decode.ImageDecoder;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;
import com.nostra13.universalimageloader.utils.L;
import com.nostra13.universalimageloader.utils.StorageUtils;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes4.dex */
public class DefaultConfigurationFactory {

    public static class DefaultThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final String namePrefix;
        private final int threadPriority;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final ThreadGroup group = Thread.currentThread().getThreadGroup();

        public DefaultThreadFactory(int i2, String str) {
            this.threadPriority = i2;
            this.namePrefix = str + poolNumber.getAndIncrement() + "-thread-";
        }

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(this.group, runnable, this.namePrefix + this.threadNumber.getAndIncrement(), 0L);
            if (thread.isDaemon()) {
                thread.setDaemon(false);
            }
            thread.setPriority(this.threadPriority);
            return thread;
        }
    }

    public static BitmapDisplayer createBitmapDisplayer() {
        return new SimpleBitmapDisplayer();
    }

    public static DiskCache createDiskCache(Context context, FileNameGenerator fileNameGenerator, long j2, int i2) {
        File fileCreateReserveDiskCacheDir = createReserveDiskCacheDir(context);
        if (j2 > 0 || i2 > 0) {
            try {
                return new LruDiskCache(StorageUtils.getIndividualCacheDirectory(context), fileCreateReserveDiskCacheDir, fileNameGenerator, j2, i2);
            } catch (IOException e2) {
                L.e(e2);
            }
        }
        return new UnlimitedDiskCache(StorageUtils.getCacheDirectory(context), fileCreateReserveDiskCacheDir, fileNameGenerator);
    }

    public static Executor createExecutor(int i2, int i3, QueueProcessingType queueProcessingType) {
        return new ThreadPoolExecutor(i2, i2, 0L, TimeUnit.MILLISECONDS, (BlockingQueue<Runnable>) (queueProcessingType == QueueProcessingType.LIFO ? new LIFOLinkedBlockingDeque() : new LinkedBlockingQueue()), createThreadFactory(i3, "uil-pool-"));
    }

    public static FileNameGenerator createFileNameGenerator() {
        return new HashCodeFileNameGenerator();
    }

    public static ImageDecoder createImageDecoder(boolean z2) {
        return new BaseImageDecoder(z2);
    }

    public static ImageDownloader createImageDownloader(Context context) {
        return new BaseImageDownloader(context);
    }

    public static MemoryCache createMemoryCache(Context context, int i2) {
        if (i2 == 0) {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME);
            int memoryClass = activityManager.getMemoryClass();
            if (hasHoneycomb() && isLargeHeap(context)) {
                memoryClass = getLargeMemoryClass(activityManager);
            }
            i2 = (memoryClass * 1048576) / 8;
        }
        return new LruMemoryCache(i2);
    }

    private static File createReserveDiskCacheDir(Context context) {
        File cacheDirectory = StorageUtils.getCacheDirectory(context, false);
        File file = new File(cacheDirectory, "uil-images");
        return (file.exists() || file.mkdir()) ? file : cacheDirectory;
    }

    public static Executor createTaskDistributor() {
        return Executors.newCachedThreadPool(createThreadFactory(5, "uil-pool-d-"));
    }

    private static ThreadFactory createThreadFactory(int i2, String str) {
        return new DefaultThreadFactory(i2, str);
    }

    @TargetApi(11)
    private static int getLargeMemoryClass(ActivityManager activityManager) {
        return activityManager.getLargeMemoryClass();
    }

    private static boolean hasHoneycomb() {
        return true;
    }

    @TargetApi(11)
    private static boolean isLargeHeap(Context context) {
        return (context.getApplicationInfo().flags & 1048576) != 0;
    }
}
