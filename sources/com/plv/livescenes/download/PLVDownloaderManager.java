package com.plv.livescenes.download;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.livescenes.download.IPLVDownloader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes4.dex */
public class PLVDownloaderManager {
    private static final int DEFAULT_DOWNLOAD_THREAD_COUNT = 1;
    private static volatile PLVDownloaderManager INSTANCE = null;
    private static final String TAG = "PLVDownloaderManager";
    private final ExecutorService downloadExecutor;
    private final int downloadThreadCount;
    private final ConcurrentHashMap<String, PLVDownloader> downloaders = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Future> downloadersTaskFutures = new ConcurrentHashMap<>();

    private PLVDownloaderManager(int i2) {
        this.downloadThreadCount = i2;
        this.downloadExecutor = Executors.newFixedThreadPool(i2, new ThreadFactory() { // from class: com.plv.livescenes.download.PLVDownloaderManager.1
            private final AtomicInteger mCount = new AtomicInteger(1);

            @Override // java.util.concurrent.ThreadFactory
            public Thread newThread(@NonNull Runnable runnable) {
                PLVCommonLog.d(PLVDownloaderManager.TAG, "create download thread");
                return new Thread(runnable, "download #" + this.mCount.getAndIncrement());
            }
        });
    }

    private void clearDownloading() {
        synchronized (PLVDownloaderManager.class) {
            this.downloadersTaskFutures.clear();
            this.downloaders.clear();
        }
    }

    private void createDownloadTask(final PLVDownloader pLVDownloader, String str) {
        if (this.downloadersTaskFutures.containsKey(str)) {
            this.downloadersTaskFutures.remove(str).cancel(true);
        }
        this.downloadersTaskFutures.put(str, this.downloadExecutor.submit(new Runnable() { // from class: com.plv.livescenes.download.PLVDownloaderManager.3
            @Override // java.lang.Runnable
            public void run() throws Throwable {
                PLVCommonLog.d(PLVDownloaderManager.TAG, "start download task");
                pLVDownloader.start();
            }
        }));
    }

    @Nullable
    private PLVDownloader getDownloaderSelf(IPLVDownloader.Builder builder) {
        return this.downloaders.get(builder.createDownloadKey());
    }

    public static PLVDownloaderManager getInstance() {
        if (INSTANCE == null) {
            synchronized (PLVDownloaderManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PLVDownloaderManager(1);
                }
            }
        }
        return INSTANCE;
    }

    public PLVDownloader addDownloader(IPLVDownloader.Builder builder) {
        String strCreateDownloadKey = builder.createDownloadKey();
        if (this.downloaders.containsKey(strCreateDownloadKey)) {
            return this.downloaders.get(strCreateDownloadKey);
        }
        PLVDownloader pLVDownloader = new PLVDownloader(builder);
        this.downloaders.put(strCreateDownloadKey, pLVDownloader);
        return pLVDownloader;
    }

    @Nullable
    public PLVDownloader clearDownloader(IPLVDownloader.Builder builder) {
        PLVDownloader downloaderSelf = getDownloaderSelf(builder);
        if (downloaderSelf != null) {
            downloaderSelf.stop();
        }
        Future future = this.downloadersTaskFutures.get(builder.createDownloadKey());
        if (future != null) {
            future.cancel(true);
        }
        return downloaderSelf;
    }

    @Nullable
    public PLVDownloader getDownloader(IPLVDownloader.Builder builder) {
        return this.downloaders.get(builder.createDownloadKey());
    }

    public void releaseDownload() {
        stopAll();
        clearDownloading();
    }

    public PLVDownloader removeDownloader(IPLVDownloader.Builder builder) {
        String strCreateDownloadKey = builder.createDownloadKey();
        Future futureRemove = this.downloadersTaskFutures.remove(strCreateDownloadKey);
        if (futureRemove != null) {
            futureRemove.cancel(true);
        }
        PLVDownloader pLVDownloaderRemove = this.downloaders.remove(strCreateDownloadKey);
        if (pLVDownloaderRemove != null && pLVDownloaderRemove.isDownloading()) {
            pLVDownloaderRemove.stop();
        }
        return pLVDownloaderRemove;
    }

    public void startAll() {
        if (this.downloaders.isEmpty()) {
            return;
        }
        for (final Map.Entry<String, PLVDownloader> entry : this.downloaders.entrySet()) {
            Future futureRemove = this.downloadersTaskFutures.remove(entry.getKey());
            if (futureRemove != null) {
                futureRemove.cancel(true);
            }
            this.downloadersTaskFutures.put(entry.getKey(), this.downloadExecutor.submit(new Runnable() { // from class: com.plv.livescenes.download.PLVDownloaderManager.2
                @Override // java.lang.Runnable
                public void run() throws Throwable {
                    ((PLVDownloader) entry.getValue()).start();
                }
            }));
        }
    }

    public void startDownload(PLVDownloader pLVDownloader) {
        if (pLVDownloader.isDownloading()) {
            return;
        }
        createDownloadTask(pLVDownloader, pLVDownloader.getKey());
    }

    public void stopAll() {
        if (this.downloaders.isEmpty()) {
            return;
        }
        Iterator<Map.Entry<String, PLVDownloader>> it = this.downloaders.entrySet().iterator();
        while (it.hasNext()) {
            it.next().getValue().stop();
        }
        ArrayList arrayList = new ArrayList(this.downloadersTaskFutures.values());
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            ((Future) it2.next()).cancel(true);
        }
        arrayList.clear();
        this.downloadersTaskFutures.clear();
    }

    @Nullable
    public PLVDownloader getDownloader(String str) {
        return this.downloaders.get(str);
    }

    public PLVDownloader removeDownloader(String str) {
        Future futureRemove = this.downloadersTaskFutures.remove(str);
        if (futureRemove != null) {
            futureRemove.cancel(true);
        }
        PLVDownloader pLVDownloaderRemove = this.downloaders.remove(str);
        if (pLVDownloaderRemove != null && pLVDownloaderRemove.isDownloading()) {
            pLVDownloaderRemove.stop();
        }
        return pLVDownloaderRemove;
    }
}
