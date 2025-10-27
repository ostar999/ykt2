package com.google.android.exoplayer2.offline;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import androidx.annotation.CheckResult;
import androidx.annotation.IntRange;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.database.DatabaseProvider;
import com.google.android.exoplayer2.offline.Downloader;
import com.google.android.exoplayer2.scheduler.Requirements;
import com.google.android.exoplayer2.scheduler.RequirementsWatcher;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public final class DownloadManager {
    public static final int DEFAULT_MAX_PARALLEL_DOWNLOADS = 3;
    public static final int DEFAULT_MIN_RETRY_COUNT = 5;
    public static final Requirements DEFAULT_REQUIREMENTS = new Requirements(1);
    private static final int MSG_ADD_DOWNLOAD = 6;
    private static final int MSG_CONTENT_LENGTH_CHANGED = 10;
    private static final int MSG_DOWNLOAD_UPDATE = 2;
    private static final int MSG_INITIALIZE = 0;
    private static final int MSG_INITIALIZED = 0;
    private static final int MSG_PROCESSED = 1;
    private static final int MSG_RELEASE = 12;
    private static final int MSG_REMOVE_ALL_DOWNLOADS = 8;
    private static final int MSG_REMOVE_DOWNLOAD = 7;
    private static final int MSG_SET_DOWNLOADS_PAUSED = 1;
    private static final int MSG_SET_MAX_PARALLEL_DOWNLOADS = 4;
    private static final int MSG_SET_MIN_RETRY_COUNT = 5;
    private static final int MSG_SET_NOT_MET_REQUIREMENTS = 2;
    private static final int MSG_SET_STOP_REASON = 3;
    private static final int MSG_TASK_STOPPED = 9;
    private static final int MSG_UPDATE_PROGRESS = 11;
    private static final String TAG = "DownloadManager";
    private int activeTaskCount;
    private final Handler applicationHandler;
    private final Context context;
    private final WritableDownloadIndex downloadIndex;
    private List<Download> downloads;
    private boolean downloadsPaused;
    private boolean initialized;
    private final InternalHandler internalHandler;
    private final CopyOnWriteArraySet<Listener> listeners;
    private int maxParallelDownloads;
    private int minRetryCount;
    private int notMetRequirements;
    private int pendingMessages;
    private final RequirementsWatcher.Listener requirementsListener;
    private RequirementsWatcher requirementsWatcher;
    private boolean waitingForRequirements;

    public static final class DownloadUpdate {
        public final Download download;
        public final List<Download> downloads;

        @Nullable
        public final Exception finalException;
        public final boolean isRemove;

        public DownloadUpdate(Download download, boolean z2, List<Download> list, @Nullable Exception exc) {
            this.download = download;
            this.isRemove = z2;
            this.downloads = list;
            this.finalException = exc;
        }
    }

    public interface Listener {
        void onDownloadChanged(DownloadManager downloadManager, Download download, @Nullable Exception exc);

        void onDownloadRemoved(DownloadManager downloadManager, Download download);

        void onDownloadsPausedChanged(DownloadManager downloadManager, boolean z2);

        void onIdle(DownloadManager downloadManager);

        void onInitialized(DownloadManager downloadManager);

        void onRequirementsStateChanged(DownloadManager downloadManager, Requirements requirements, int i2);

        void onWaitingForRequirementsChanged(DownloadManager downloadManager, boolean z2);
    }

    public static class Task extends Thread implements Downloader.ProgressListener {
        private long contentLength;
        private final DownloadProgress downloadProgress;
        private final Downloader downloader;

        @Nullable
        private Exception finalException;

        @Nullable
        private volatile InternalHandler internalHandler;
        private volatile boolean isCanceled;
        private final boolean isRemove;
        private final int minRetryCount;
        private final DownloadRequest request;

        private static int getRetryDelayMillis(int i2) {
            return Math.min((i2 - 1) * 1000, 5000);
        }

        public void cancel(boolean z2) {
            if (z2) {
                this.internalHandler = null;
            }
            if (this.isCanceled) {
                return;
            }
            this.isCanceled = true;
            this.downloader.cancel();
            interrupt();
        }

        @Override // com.google.android.exoplayer2.offline.Downloader.ProgressListener
        public void onProgress(long j2, long j3, float f2) {
            this.downloadProgress.bytesDownloaded = j3;
            this.downloadProgress.percentDownloaded = f2;
            if (j2 != this.contentLength) {
                this.contentLength = j2;
                InternalHandler internalHandler = this.internalHandler;
                if (internalHandler != null) {
                    internalHandler.obtainMessage(10, (int) (j2 >> 32), (int) j2, this).sendToTarget();
                }
            }
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() throws InterruptedException, IOException {
            try {
                if (this.isRemove) {
                    this.downloader.remove();
                } else {
                    long j2 = -1;
                    int i2 = 0;
                    while (!this.isCanceled) {
                        try {
                            this.downloader.download(this);
                            break;
                        } catch (IOException e2) {
                            if (!this.isCanceled) {
                                long j3 = this.downloadProgress.bytesDownloaded;
                                if (j3 != j2) {
                                    i2 = 0;
                                    j2 = j3;
                                }
                                i2++;
                                if (i2 > this.minRetryCount) {
                                    throw e2;
                                }
                                Thread.sleep(getRetryDelayMillis(i2));
                            }
                        }
                    }
                }
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
            } catch (Exception e3) {
                this.finalException = e3;
            }
            InternalHandler internalHandler = this.internalHandler;
            if (internalHandler != null) {
                internalHandler.obtainMessage(9, this).sendToTarget();
            }
        }

        private Task(DownloadRequest downloadRequest, Downloader downloader, DownloadProgress downloadProgress, boolean z2, int i2, InternalHandler internalHandler) {
            this.request = downloadRequest;
            this.downloader = downloader;
            this.downloadProgress = downloadProgress;
            this.isRemove = z2;
            this.minRetryCount = i2;
            this.internalHandler = internalHandler;
            this.contentLength = -1L;
        }
    }

    @Deprecated
    public DownloadManager(Context context, DatabaseProvider databaseProvider, Cache cache, DataSource.Factory factory) {
        this(context, databaseProvider, cache, factory, new a());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean handleMainMessage(Message message) {
        int i2 = message.what;
        if (i2 == 0) {
            onInitialized((List) message.obj);
        } else if (i2 == 1) {
            onMessageProcessed(message.arg1, message.arg2);
        } else {
            if (i2 != 2) {
                throw new IllegalStateException();
            }
            onDownloadUpdate((DownloadUpdate) message.obj);
        }
        return true;
    }

    public static Download mergeRequest(Download download, DownloadRequest downloadRequest, int i2, long j2) {
        int i3;
        int i4 = download.state;
        long j3 = (i4 == 5 || download.isTerminalState()) ? j2 : download.startTimeMs;
        if (i4 == 5 || i4 == 7) {
            i3 = 7;
        } else {
            i3 = i2 != 0 ? 1 : 0;
        }
        return new Download(download.request.copyWithMergedRequest(downloadRequest), i3, j3, j2, -1L, i2, 0);
    }

    private void notifyWaitingForRequirementsChanged() {
        Iterator<Listener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onWaitingForRequirementsChanged(this, this.waitingForRequirements);
        }
    }

    private void onDownloadUpdate(DownloadUpdate downloadUpdate) {
        this.downloads = Collections.unmodifiableList(downloadUpdate.downloads);
        Download download = downloadUpdate.download;
        boolean zUpdateWaitingForRequirements = updateWaitingForRequirements();
        if (downloadUpdate.isRemove) {
            Iterator<Listener> it = this.listeners.iterator();
            while (it.hasNext()) {
                it.next().onDownloadRemoved(this, download);
            }
        } else {
            Iterator<Listener> it2 = this.listeners.iterator();
            while (it2.hasNext()) {
                it2.next().onDownloadChanged(this, download, downloadUpdate.finalException);
            }
        }
        if (zUpdateWaitingForRequirements) {
            notifyWaitingForRequirementsChanged();
        }
    }

    private void onInitialized(List<Download> list) {
        this.initialized = true;
        this.downloads = Collections.unmodifiableList(list);
        boolean zUpdateWaitingForRequirements = updateWaitingForRequirements();
        Iterator<Listener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onInitialized(this);
        }
        if (zUpdateWaitingForRequirements) {
            notifyWaitingForRequirementsChanged();
        }
    }

    private void onMessageProcessed(int i2, int i3) {
        this.pendingMessages -= i2;
        this.activeTaskCount = i3;
        if (isIdle()) {
            Iterator<Listener> it = this.listeners.iterator();
            while (it.hasNext()) {
                it.next().onIdle(this);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onRequirementsStateChanged(RequirementsWatcher requirementsWatcher, int i2) {
        Requirements requirements = requirementsWatcher.getRequirements();
        if (this.notMetRequirements != i2) {
            this.notMetRequirements = i2;
            this.pendingMessages++;
            this.internalHandler.obtainMessage(2, i2, 0).sendToTarget();
        }
        boolean zUpdateWaitingForRequirements = updateWaitingForRequirements();
        Iterator<Listener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onRequirementsStateChanged(this, requirements, i2);
        }
        if (zUpdateWaitingForRequirements) {
            notifyWaitingForRequirementsChanged();
        }
    }

    private void setDownloadsPaused(boolean z2) {
        if (this.downloadsPaused == z2) {
            return;
        }
        this.downloadsPaused = z2;
        this.pendingMessages++;
        this.internalHandler.obtainMessage(1, z2 ? 1 : 0, 0).sendToTarget();
        boolean zUpdateWaitingForRequirements = updateWaitingForRequirements();
        Iterator<Listener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onDownloadsPausedChanged(this, z2);
        }
        if (zUpdateWaitingForRequirements) {
            notifyWaitingForRequirementsChanged();
        }
    }

    private boolean updateWaitingForRequirements() {
        boolean z2;
        if (this.downloadsPaused || this.notMetRequirements == 0) {
            z2 = false;
        } else {
            for (int i2 = 0; i2 < this.downloads.size(); i2++) {
                if (this.downloads.get(i2).state == 0) {
                    z2 = true;
                    break;
                }
            }
            z2 = false;
        }
        boolean z3 = this.waitingForRequirements != z2;
        this.waitingForRequirements = z2;
        return z3;
    }

    public void addDownload(DownloadRequest downloadRequest) {
        addDownload(downloadRequest, 0);
    }

    public void addListener(Listener listener) {
        Assertions.checkNotNull(listener);
        this.listeners.add(listener);
    }

    public Looper getApplicationLooper() {
        return this.applicationHandler.getLooper();
    }

    public List<Download> getCurrentDownloads() {
        return this.downloads;
    }

    public DownloadIndex getDownloadIndex() {
        return this.downloadIndex;
    }

    public boolean getDownloadsPaused() {
        return this.downloadsPaused;
    }

    public int getMaxParallelDownloads() {
        return this.maxParallelDownloads;
    }

    public int getMinRetryCount() {
        return this.minRetryCount;
    }

    public int getNotMetRequirements() {
        return this.notMetRequirements;
    }

    public Requirements getRequirements() {
        return this.requirementsWatcher.getRequirements();
    }

    public boolean isIdle() {
        return this.activeTaskCount == 0 && this.pendingMessages == 0;
    }

    public boolean isInitialized() {
        return this.initialized;
    }

    public boolean isWaitingForRequirements() {
        return this.waitingForRequirements;
    }

    public void pauseDownloads() {
        setDownloadsPaused(true);
    }

    public void release() {
        synchronized (this.internalHandler) {
            InternalHandler internalHandler = this.internalHandler;
            if (internalHandler.released) {
                return;
            }
            internalHandler.sendEmptyMessage(12);
            boolean z2 = false;
            while (true) {
                InternalHandler internalHandler2 = this.internalHandler;
                if (internalHandler2.released) {
                    break;
                }
                try {
                    internalHandler2.wait();
                } catch (InterruptedException unused) {
                    z2 = true;
                }
            }
            if (z2) {
                Thread.currentThread().interrupt();
            }
            this.applicationHandler.removeCallbacksAndMessages(null);
            this.downloads = Collections.emptyList();
            this.pendingMessages = 0;
            this.activeTaskCount = 0;
            this.initialized = false;
            this.notMetRequirements = 0;
            this.waitingForRequirements = false;
        }
    }

    public void removeAllDownloads() {
        this.pendingMessages++;
        this.internalHandler.obtainMessage(8).sendToTarget();
    }

    public void removeDownload(String str) {
        this.pendingMessages++;
        this.internalHandler.obtainMessage(7, str).sendToTarget();
    }

    public void removeListener(Listener listener) {
        this.listeners.remove(listener);
    }

    public void resumeDownloads() {
        setDownloadsPaused(false);
    }

    public void setMaxParallelDownloads(@IntRange(from = 1) int i2) {
        Assertions.checkArgument(i2 > 0);
        if (this.maxParallelDownloads == i2) {
            return;
        }
        this.maxParallelDownloads = i2;
        this.pendingMessages++;
        this.internalHandler.obtainMessage(4, i2, 0).sendToTarget();
    }

    public void setMinRetryCount(int i2) {
        Assertions.checkArgument(i2 >= 0);
        if (this.minRetryCount == i2) {
            return;
        }
        this.minRetryCount = i2;
        this.pendingMessages++;
        this.internalHandler.obtainMessage(5, i2, 0).sendToTarget();
    }

    public void setRequirements(Requirements requirements) {
        if (requirements.equals(this.requirementsWatcher.getRequirements())) {
            return;
        }
        this.requirementsWatcher.stop();
        RequirementsWatcher requirementsWatcher = new RequirementsWatcher(this.context, this.requirementsListener, requirements);
        this.requirementsWatcher = requirementsWatcher;
        onRequirementsStateChanged(this.requirementsWatcher, requirementsWatcher.start());
    }

    public void setStopReason(@Nullable String str, int i2) {
        this.pendingMessages++;
        this.internalHandler.obtainMessage(3, i2, 0, str).sendToTarget();
    }

    public DownloadManager(Context context, DatabaseProvider databaseProvider, Cache cache, DataSource.Factory factory, Executor executor) {
        this(context, new DefaultDownloadIndex(databaseProvider), new DefaultDownloaderFactory(new CacheDataSource.Factory().setCache(cache).setUpstreamDataSourceFactory(factory), executor));
    }

    public void addDownload(DownloadRequest downloadRequest, int i2) {
        this.pendingMessages++;
        this.internalHandler.obtainMessage(6, i2, 0, downloadRequest).sendToTarget();
    }

    public DownloadManager(Context context, WritableDownloadIndex writableDownloadIndex, DownloaderFactory downloaderFactory) {
        this.context = context.getApplicationContext();
        this.downloadIndex = writableDownloadIndex;
        this.maxParallelDownloads = 3;
        this.minRetryCount = 5;
        this.downloadsPaused = true;
        this.downloads = Collections.emptyList();
        this.listeners = new CopyOnWriteArraySet<>();
        Handler handlerCreateHandlerForCurrentOrMainLooper = Util.createHandlerForCurrentOrMainLooper(new Handler.Callback() { // from class: com.google.android.exoplayer2.offline.j
            @Override // android.os.Handler.Callback
            public final boolean handleMessage(Message message) {
                return this.f6824c.handleMainMessage(message);
            }
        });
        this.applicationHandler = handlerCreateHandlerForCurrentOrMainLooper;
        HandlerThread handlerThread = new HandlerThread("ExoPlayer:DownloadManager");
        handlerThread.start();
        InternalHandler internalHandler = new InternalHandler(handlerThread, writableDownloadIndex, downloaderFactory, handlerCreateHandlerForCurrentOrMainLooper, this.maxParallelDownloads, this.minRetryCount, this.downloadsPaused);
        this.internalHandler = internalHandler;
        RequirementsWatcher.Listener listener = new RequirementsWatcher.Listener() { // from class: com.google.android.exoplayer2.offline.k
            @Override // com.google.android.exoplayer2.scheduler.RequirementsWatcher.Listener
            public final void onRequirementsStateChanged(RequirementsWatcher requirementsWatcher, int i2) {
                this.f6825a.onRequirementsStateChanged(requirementsWatcher, i2);
            }
        };
        this.requirementsListener = listener;
        RequirementsWatcher requirementsWatcher = new RequirementsWatcher(context, listener, DEFAULT_REQUIREMENTS);
        this.requirementsWatcher = requirementsWatcher;
        int iStart = requirementsWatcher.start();
        this.notMetRequirements = iStart;
        this.pendingMessages = 1;
        internalHandler.obtainMessage(0, iStart, 0).sendToTarget();
    }

    public static final class InternalHandler extends Handler {
        private static final int UPDATE_PROGRESS_INTERVAL_MS = 5000;
        private int activeDownloadTaskCount;
        private final HashMap<String, Task> activeTasks;
        private final WritableDownloadIndex downloadIndex;
        private final DownloaderFactory downloaderFactory;
        private final ArrayList<Download> downloads;
        private boolean downloadsPaused;
        private final Handler mainHandler;
        private int maxParallelDownloads;
        private int minRetryCount;
        private int notMetRequirements;
        public boolean released;
        private final HandlerThread thread;

        public InternalHandler(HandlerThread handlerThread, WritableDownloadIndex writableDownloadIndex, DownloaderFactory downloaderFactory, Handler handler, int i2, int i3, boolean z2) {
            super(handlerThread.getLooper());
            this.thread = handlerThread;
            this.downloadIndex = writableDownloadIndex;
            this.downloaderFactory = downloaderFactory;
            this.mainHandler = handler;
            this.maxParallelDownloads = i2;
            this.minRetryCount = i3;
            this.downloadsPaused = z2;
            this.downloads = new ArrayList<>();
            this.activeTasks = new HashMap<>();
        }

        private void addDownload(DownloadRequest downloadRequest, int i2) {
            Download download = getDownload(downloadRequest.id, true);
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (download != null) {
                putDownload(DownloadManager.mergeRequest(download, downloadRequest, i2, jCurrentTimeMillis));
            } else {
                putDownload(new Download(downloadRequest, i2 == 0 ? 0 : 1, jCurrentTimeMillis, jCurrentTimeMillis, -1L, i2, 0));
            }
            syncTasks();
        }

        private boolean canDownloadsRun() {
            return !this.downloadsPaused && this.notMetRequirements == 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static int compareStartTimes(Download download, Download download2) {
            return Util.compareLong(download.startTimeMs, download2.startTimeMs);
        }

        private static Download copyDownloadWithState(Download download, int i2, int i3) {
            return new Download(download.request, i2, download.startTimeMs, System.currentTimeMillis(), download.contentLength, i3, 0, download.progress);
        }

        @Nullable
        private Download getDownload(String str, boolean z2) {
            int downloadIndex = getDownloadIndex(str);
            if (downloadIndex != -1) {
                return this.downloads.get(downloadIndex);
            }
            if (!z2) {
                return null;
            }
            try {
                return this.downloadIndex.getDownload(str);
            } catch (IOException e2) {
                String strValueOf = String.valueOf(str);
                Log.e(DownloadManager.TAG, strValueOf.length() != 0 ? "Failed to load download: ".concat(strValueOf) : new String("Failed to load download: "), e2);
                return null;
            }
        }

        private int getDownloadIndex(String str) {
            for (int i2 = 0; i2 < this.downloads.size(); i2++) {
                if (this.downloads.get(i2).request.id.equals(str)) {
                    return i2;
                }
            }
            return -1;
        }

        private void initialize(int i2) throws IOException {
            this.notMetRequirements = i2;
            DownloadCursor downloads = null;
            try {
                try {
                    this.downloadIndex.setDownloadingStatesToQueued();
                    downloads = this.downloadIndex.getDownloads(0, 1, 2, 5, 7);
                    while (downloads.moveToNext()) {
                        this.downloads.add(downloads.getDownload());
                    }
                } catch (IOException e2) {
                    Log.e(DownloadManager.TAG, "Failed to load index.", e2);
                    this.downloads.clear();
                }
                Util.closeQuietly(downloads);
                this.mainHandler.obtainMessage(0, new ArrayList(this.downloads)).sendToTarget();
                syncTasks();
            } catch (Throwable th) {
                Util.closeQuietly(downloads);
                throw th;
            }
        }

        private void onContentLengthChanged(Task task, long j2) {
            Download download = (Download) Assertions.checkNotNull(getDownload(task.request.id, false));
            if (j2 == download.contentLength || j2 == -1) {
                return;
            }
            putDownload(new Download(download.request, download.state, download.startTimeMs, System.currentTimeMillis(), j2, download.stopReason, download.failureReason, download.progress));
        }

        private void onDownloadTaskStopped(Download download, @Nullable Exception exc) {
            Download download2 = new Download(download.request, exc == null ? 3 : 4, download.startTimeMs, System.currentTimeMillis(), download.contentLength, download.stopReason, exc == null ? 0 : 1, download.progress);
            this.downloads.remove(getDownloadIndex(download2.request.id));
            try {
                this.downloadIndex.putDownload(download2);
            } catch (IOException e2) {
                Log.e(DownloadManager.TAG, "Failed to update index.", e2);
            }
            this.mainHandler.obtainMessage(2, new DownloadUpdate(download2, false, new ArrayList(this.downloads), exc)).sendToTarget();
        }

        private void onRemoveTaskStopped(Download download) {
            if (download.state == 7) {
                int i2 = download.stopReason;
                putDownloadWithState(download, i2 == 0 ? 0 : 1, i2);
                syncTasks();
            } else {
                this.downloads.remove(getDownloadIndex(download.request.id));
                try {
                    this.downloadIndex.removeDownload(download.request.id);
                } catch (IOException unused) {
                    Log.e(DownloadManager.TAG, "Failed to remove from database");
                }
                this.mainHandler.obtainMessage(2, new DownloadUpdate(download, true, new ArrayList(this.downloads), null)).sendToTarget();
            }
        }

        private void onTaskStopped(Task task) {
            String str = task.request.id;
            this.activeTasks.remove(str);
            boolean z2 = task.isRemove;
            if (!z2) {
                int i2 = this.activeDownloadTaskCount - 1;
                this.activeDownloadTaskCount = i2;
                if (i2 == 0) {
                    removeMessages(11);
                }
            }
            if (task.isCanceled) {
                syncTasks();
                return;
            }
            Exception exc = task.finalException;
            if (exc != null) {
                String strValueOf = String.valueOf(task.request);
                StringBuilder sb = new StringBuilder(strValueOf.length() + 20);
                sb.append("Task failed: ");
                sb.append(strValueOf);
                sb.append(", ");
                sb.append(z2);
                Log.e(DownloadManager.TAG, sb.toString(), exc);
            }
            Download download = (Download) Assertions.checkNotNull(getDownload(str, false));
            int i3 = download.state;
            if (i3 == 2) {
                Assertions.checkState(!z2);
                onDownloadTaskStopped(download, exc);
            } else {
                if (i3 != 5 && i3 != 7) {
                    throw new IllegalStateException();
                }
                Assertions.checkState(z2);
                onRemoveTaskStopped(download);
            }
            syncTasks();
        }

        private Download putDownload(Download download) {
            int i2 = download.state;
            Assertions.checkState((i2 == 3 || i2 == 4) ? false : true);
            int downloadIndex = getDownloadIndex(download.request.id);
            if (downloadIndex == -1) {
                this.downloads.add(download);
                Collections.sort(this.downloads, new l());
            } else {
                boolean z2 = download.startTimeMs != this.downloads.get(downloadIndex).startTimeMs;
                this.downloads.set(downloadIndex, download);
                if (z2) {
                    Collections.sort(this.downloads, new l());
                }
            }
            try {
                this.downloadIndex.putDownload(download);
            } catch (IOException e2) {
                Log.e(DownloadManager.TAG, "Failed to update index.", e2);
            }
            this.mainHandler.obtainMessage(2, new DownloadUpdate(download, false, new ArrayList(this.downloads), null)).sendToTarget();
            return download;
        }

        private Download putDownloadWithState(Download download, int i2, int i3) {
            Assertions.checkState((i2 == 3 || i2 == 4) ? false : true);
            return putDownload(copyDownloadWithState(download, i2, i3));
        }

        private void release() {
            Iterator<Task> it = this.activeTasks.values().iterator();
            while (it.hasNext()) {
                it.next().cancel(true);
            }
            try {
                this.downloadIndex.setDownloadingStatesToQueued();
            } catch (IOException e2) {
                Log.e(DownloadManager.TAG, "Failed to update index.", e2);
            }
            this.downloads.clear();
            this.thread.quit();
            synchronized (this) {
                this.released = true;
                notifyAll();
            }
        }

        private void removeAllDownloads() {
            ArrayList arrayList = new ArrayList();
            try {
                DownloadCursor downloads = this.downloadIndex.getDownloads(3, 4);
                while (downloads.moveToNext()) {
                    try {
                        arrayList.add(downloads.getDownload());
                    } finally {
                    }
                }
                downloads.close();
            } catch (IOException unused) {
                Log.e(DownloadManager.TAG, "Failed to load downloads.");
            }
            for (int i2 = 0; i2 < this.downloads.size(); i2++) {
                ArrayList<Download> arrayList2 = this.downloads;
                arrayList2.set(i2, copyDownloadWithState(arrayList2.get(i2), 5, 0));
            }
            for (int i3 = 0; i3 < arrayList.size(); i3++) {
                this.downloads.add(copyDownloadWithState((Download) arrayList.get(i3), 5, 0));
            }
            Collections.sort(this.downloads, new l());
            try {
                this.downloadIndex.setStatesToRemoving();
            } catch (IOException e2) {
                Log.e(DownloadManager.TAG, "Failed to update index.", e2);
            }
            ArrayList arrayList3 = new ArrayList(this.downloads);
            for (int i4 = 0; i4 < this.downloads.size(); i4++) {
                this.mainHandler.obtainMessage(2, new DownloadUpdate(this.downloads.get(i4), false, arrayList3, null)).sendToTarget();
            }
            syncTasks();
        }

        private void removeDownload(String str) {
            Download download = getDownload(str, true);
            if (download == null) {
                String strValueOf = String.valueOf(str);
                Log.e(DownloadManager.TAG, strValueOf.length() != 0 ? "Failed to remove nonexistent download: ".concat(strValueOf) : new String("Failed to remove nonexistent download: "));
            } else {
                putDownloadWithState(download, 5, 0);
                syncTasks();
            }
        }

        private void setDownloadsPaused(boolean z2) {
            this.downloadsPaused = z2;
            syncTasks();
        }

        private void setMaxParallelDownloads(int i2) {
            this.maxParallelDownloads = i2;
            syncTasks();
        }

        private void setMinRetryCount(int i2) {
            this.minRetryCount = i2;
        }

        private void setNotMetRequirements(int i2) {
            this.notMetRequirements = i2;
            syncTasks();
        }

        private void setStopReason(@Nullable String str, int i2) {
            if (str == null) {
                for (int i3 = 0; i3 < this.downloads.size(); i3++) {
                    setStopReason(this.downloads.get(i3), i2);
                }
                try {
                    this.downloadIndex.setStopReason(i2);
                } catch (IOException e2) {
                    Log.e(DownloadManager.TAG, "Failed to set manual stop reason", e2);
                }
            } else {
                Download download = getDownload(str, false);
                if (download != null) {
                    setStopReason(download, i2);
                } else {
                    try {
                        this.downloadIndex.setStopReason(str, i2);
                    } catch (IOException e3) {
                        Log.e(DownloadManager.TAG, str.length() != 0 ? "Failed to set manual stop reason: ".concat(str) : new String("Failed to set manual stop reason: "), e3);
                    }
                }
            }
            syncTasks();
        }

        private void syncDownloadingDownload(Task task, Download download, int i2) {
            Assertions.checkState(!task.isRemove);
            if (!canDownloadsRun() || i2 >= this.maxParallelDownloads) {
                putDownloadWithState(download, 0, 0);
                task.cancel(false);
            }
        }

        @Nullable
        @CheckResult
        private Task syncQueuedDownload(@Nullable Task task, Download download) {
            if (task != null) {
                Assertions.checkState(!task.isRemove);
                task.cancel(false);
                return task;
            }
            if (!canDownloadsRun() || this.activeDownloadTaskCount >= this.maxParallelDownloads) {
                return null;
            }
            Download downloadPutDownloadWithState = putDownloadWithState(download, 2, 0);
            Task task2 = new Task(downloadPutDownloadWithState.request, this.downloaderFactory.createDownloader(downloadPutDownloadWithState.request), downloadPutDownloadWithState.progress, false, this.minRetryCount, this);
            this.activeTasks.put(downloadPutDownloadWithState.request.id, task2);
            int i2 = this.activeDownloadTaskCount;
            this.activeDownloadTaskCount = i2 + 1;
            if (i2 == 0) {
                sendEmptyMessageDelayed(11, 5000L);
            }
            task2.start();
            return task2;
        }

        private void syncRemovingDownload(@Nullable Task task, Download download) {
            if (task != null) {
                if (task.isRemove) {
                    return;
                }
                task.cancel(false);
            } else {
                Task task2 = new Task(download.request, this.downloaderFactory.createDownloader(download.request), download.progress, true, this.minRetryCount, this);
                this.activeTasks.put(download.request.id, task2);
                task2.start();
            }
        }

        private void syncStoppedDownload(@Nullable Task task) {
            if (task != null) {
                Assertions.checkState(!task.isRemove);
                task.cancel(false);
            }
        }

        private void syncTasks() {
            int i2 = 0;
            for (int i3 = 0; i3 < this.downloads.size(); i3++) {
                Download download = this.downloads.get(i3);
                Task taskSyncQueuedDownload = this.activeTasks.get(download.request.id);
                int i4 = download.state;
                if (i4 == 0) {
                    taskSyncQueuedDownload = syncQueuedDownload(taskSyncQueuedDownload, download);
                } else if (i4 == 1) {
                    syncStoppedDownload(taskSyncQueuedDownload);
                } else if (i4 == 2) {
                    Assertions.checkNotNull(taskSyncQueuedDownload);
                    syncDownloadingDownload(taskSyncQueuedDownload, download, i2);
                } else {
                    if (i4 != 5 && i4 != 7) {
                        throw new IllegalStateException();
                    }
                    syncRemovingDownload(taskSyncQueuedDownload, download);
                }
                if (taskSyncQueuedDownload != null && !taskSyncQueuedDownload.isRemove) {
                    i2++;
                }
            }
        }

        private void updateProgress() {
            for (int i2 = 0; i2 < this.downloads.size(); i2++) {
                Download download = this.downloads.get(i2);
                if (download.state == 2) {
                    try {
                        this.downloadIndex.putDownload(download);
                    } catch (IOException e2) {
                        Log.e(DownloadManager.TAG, "Failed to update index.", e2);
                    }
                }
            }
            sendEmptyMessageDelayed(11, 5000L);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) throws IOException {
            int i2 = 0;
            switch (message.what) {
                case 0:
                    initialize(message.arg1);
                    i2 = 1;
                    this.mainHandler.obtainMessage(1, i2, this.activeTasks.size()).sendToTarget();
                    return;
                case 1:
                    setDownloadsPaused(message.arg1 != 0);
                    i2 = 1;
                    this.mainHandler.obtainMessage(1, i2, this.activeTasks.size()).sendToTarget();
                    return;
                case 2:
                    setNotMetRequirements(message.arg1);
                    i2 = 1;
                    this.mainHandler.obtainMessage(1, i2, this.activeTasks.size()).sendToTarget();
                    return;
                case 3:
                    setStopReason((String) message.obj, message.arg1);
                    i2 = 1;
                    this.mainHandler.obtainMessage(1, i2, this.activeTasks.size()).sendToTarget();
                    return;
                case 4:
                    setMaxParallelDownloads(message.arg1);
                    i2 = 1;
                    this.mainHandler.obtainMessage(1, i2, this.activeTasks.size()).sendToTarget();
                    return;
                case 5:
                    setMinRetryCount(message.arg1);
                    i2 = 1;
                    this.mainHandler.obtainMessage(1, i2, this.activeTasks.size()).sendToTarget();
                    return;
                case 6:
                    addDownload((DownloadRequest) message.obj, message.arg1);
                    i2 = 1;
                    this.mainHandler.obtainMessage(1, i2, this.activeTasks.size()).sendToTarget();
                    return;
                case 7:
                    removeDownload((String) message.obj);
                    i2 = 1;
                    this.mainHandler.obtainMessage(1, i2, this.activeTasks.size()).sendToTarget();
                    return;
                case 8:
                    removeAllDownloads();
                    i2 = 1;
                    this.mainHandler.obtainMessage(1, i2, this.activeTasks.size()).sendToTarget();
                    return;
                case 9:
                    onTaskStopped((Task) message.obj);
                    this.mainHandler.obtainMessage(1, i2, this.activeTasks.size()).sendToTarget();
                    return;
                case 10:
                    onContentLengthChanged((Task) message.obj, Util.toLong(message.arg1, message.arg2));
                    return;
                case 11:
                    updateProgress();
                    return;
                case 12:
                    release();
                    return;
                default:
                    throw new IllegalStateException();
            }
        }

        private void setStopReason(Download download, int i2) {
            if (i2 == 0) {
                if (download.state == 1) {
                    putDownloadWithState(download, 0, 0);
                }
            } else if (i2 != download.stopReason) {
                int i3 = download.state;
                if (i3 == 0 || i3 == 2) {
                    i3 = 1;
                }
                putDownload(new Download(download.request, i3, download.startTimeMs, System.currentTimeMillis(), download.contentLength, i2, 0, download.progress));
            }
        }
    }
}
