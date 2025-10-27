package com.google.android.exoplayer2.offline;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.offline.Downloader;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.upstream.cache.CacheWriter;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.PriorityTaskManager;
import com.google.android.exoplayer2.util.RunnableFutureTask;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public final class ProgressiveDownloader implements Downloader {
    private final CacheWriter cacheWriter;
    private final CacheDataSource dataSource;
    private final DataSpec dataSpec;
    private volatile RunnableFutureTask<Void, IOException> downloadRunnable;
    private final Executor executor;
    private volatile boolean isCanceled;

    @Nullable
    private final PriorityTaskManager priorityTaskManager;

    @Nullable
    private Downloader.ProgressListener progressListener;

    public ProgressiveDownloader(MediaItem mediaItem, CacheDataSource.Factory factory) {
        this(mediaItem, factory, new a());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onProgress(long j2, long j3, long j4) {
        Downloader.ProgressListener progressListener = this.progressListener;
        if (progressListener == null) {
            return;
        }
        progressListener.onProgress(j2, j3, (j2 == -1 || j2 == 0) ? -1.0f : (j3 * 100.0f) / j2);
    }

    @Override // com.google.android.exoplayer2.offline.Downloader
    public void cancel() {
        this.isCanceled = true;
        RunnableFutureTask<Void, IOException> runnableFutureTask = this.downloadRunnable;
        if (runnableFutureTask != null) {
            runnableFutureTask.cancel(true);
        }
    }

    @Override // com.google.android.exoplayer2.offline.Downloader
    public void download(@Nullable Downloader.ProgressListener progressListener) throws InterruptedException, IOException {
        this.progressListener = progressListener;
        this.downloadRunnable = new RunnableFutureTask<Void, IOException>() { // from class: com.google.android.exoplayer2.offline.ProgressiveDownloader.1
            @Override // com.google.android.exoplayer2.util.RunnableFutureTask
            public void cancelWork() {
                ProgressiveDownloader.this.cacheWriter.cancel();
            }

            @Override // com.google.android.exoplayer2.util.RunnableFutureTask
            public Void doWork() throws IOException {
                ProgressiveDownloader.this.cacheWriter.cache();
                return null;
            }
        };
        PriorityTaskManager priorityTaskManager = this.priorityTaskManager;
        if (priorityTaskManager != null) {
            priorityTaskManager.add(-1000);
        }
        boolean z2 = false;
        while (!z2) {
            try {
                if (this.isCanceled) {
                    break;
                }
                PriorityTaskManager priorityTaskManager2 = this.priorityTaskManager;
                if (priorityTaskManager2 != null) {
                    priorityTaskManager2.proceed(-1000);
                }
                this.executor.execute(this.downloadRunnable);
                try {
                    this.downloadRunnable.get();
                    z2 = true;
                } catch (ExecutionException e2) {
                    Throwable th = (Throwable) Assertions.checkNotNull(e2.getCause());
                    if (!(th instanceof PriorityTaskManager.PriorityTooLowException)) {
                        if (th instanceof IOException) {
                            throw ((IOException) th);
                        }
                        Util.sneakyThrow(th);
                    }
                }
            } finally {
                this.downloadRunnable.blockUntilFinished();
                PriorityTaskManager priorityTaskManager3 = this.priorityTaskManager;
                if (priorityTaskManager3 != null) {
                    priorityTaskManager3.remove(-1000);
                }
            }
        }
    }

    @Override // com.google.android.exoplayer2.offline.Downloader
    public void remove() {
        this.dataSource.getCache().removeResource(this.dataSource.getCacheKeyFactory().buildCacheKey(this.dataSpec));
    }

    public ProgressiveDownloader(MediaItem mediaItem, CacheDataSource.Factory factory, Executor executor) {
        this.executor = (Executor) Assertions.checkNotNull(executor);
        Assertions.checkNotNull(mediaItem.localConfiguration);
        DataSpec dataSpecBuild = new DataSpec.Builder().setUri(mediaItem.localConfiguration.uri).setKey(mediaItem.localConfiguration.customCacheKey).setFlags(4).build();
        this.dataSpec = dataSpecBuild;
        CacheDataSource cacheDataSourceCreateDataSourceForDownloading = factory.createDataSourceForDownloading();
        this.dataSource = cacheDataSourceCreateDataSourceForDownloading;
        this.cacheWriter = new CacheWriter(cacheDataSourceCreateDataSourceForDownloading, dataSpecBuild, null, new CacheWriter.ProgressListener() { // from class: com.google.android.exoplayer2.offline.p
            @Override // com.google.android.exoplayer2.upstream.cache.CacheWriter.ProgressListener
            public final void onProgress(long j2, long j3, long j4) {
                this.f6829a.onProgress(j2, j3, j4);
            }
        });
        this.priorityTaskManager = factory.getUpstreamPriorityTaskManager();
    }
}
