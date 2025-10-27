package com.thin.downloadmanager;

import android.os.Handler;
import android.os.Looper;
import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes6.dex */
public class DownloadRequestQueue {
    private CallBackDelivery mDelivery;
    private DownloadDispatcher[] mDownloadDispatchers;
    private Set<DownloadRequest> mCurrentRequests = new HashSet();
    private PriorityBlockingQueue<DownloadRequest> mDownloadQueue = new PriorityBlockingQueue<>();
    private AtomicInteger mSequenceGenerator = new AtomicInteger();

    public class CallBackDelivery {
        private final Executor mCallBackExecutor;

        public CallBackDelivery(final Handler handler) {
            this.mCallBackExecutor = new Executor() { // from class: com.thin.downloadmanager.DownloadRequestQueue.CallBackDelivery.1
                @Override // java.util.concurrent.Executor
                public void execute(Runnable runnable) {
                    handler.post(runnable);
                }
            };
        }

        public void postDownloadComplete(final DownloadRequest downloadRequest) {
            this.mCallBackExecutor.execute(new Runnable() { // from class: com.thin.downloadmanager.DownloadRequestQueue.CallBackDelivery.2
                @Override // java.lang.Runnable
                public void run() {
                    if (downloadRequest.getDownloadListener() != null) {
                        downloadRequest.getDownloadListener().onDownloadComplete(downloadRequest.getDownloadId());
                    }
                    if (downloadRequest.getStatusListener() != null) {
                        downloadRequest.getStatusListener().onDownloadComplete(downloadRequest);
                    }
                }
            });
        }

        public void postDownloadFailed(final DownloadRequest downloadRequest, final int i2, final String str) {
            this.mCallBackExecutor.execute(new Runnable() { // from class: com.thin.downloadmanager.DownloadRequestQueue.CallBackDelivery.3
                @Override // java.lang.Runnable
                public void run() {
                    if (downloadRequest.getDownloadListener() != null) {
                        downloadRequest.getDownloadListener().onDownloadFailed(downloadRequest.getDownloadId(), i2, str);
                    }
                    if (downloadRequest.getStatusListener() != null) {
                        downloadRequest.getStatusListener().onDownloadFailed(downloadRequest, i2, str);
                    }
                }
            });
        }

        public void postProgressUpdate(final DownloadRequest downloadRequest, final long j2, final long j3, final int i2) {
            this.mCallBackExecutor.execute(new Runnable() { // from class: com.thin.downloadmanager.DownloadRequestQueue.CallBackDelivery.4
                @Override // java.lang.Runnable
                public void run() {
                    if (downloadRequest.getDownloadListener() != null) {
                        downloadRequest.getDownloadListener().onProgress(downloadRequest.getDownloadId(), j2, j3, i2);
                    }
                    if (downloadRequest.getStatusListener() != null) {
                        downloadRequest.getStatusListener().onProgress(downloadRequest, j2, j3, i2);
                    }
                }
            });
        }
    }

    public DownloadRequestQueue() {
        initialize(new Handler(Looper.getMainLooper()));
    }

    private int getDownloadId() {
        return this.mSequenceGenerator.incrementAndGet();
    }

    private void initialize(Handler handler) {
        this.mDownloadDispatchers = new DownloadDispatcher[Runtime.getRuntime().availableProcessors()];
        this.mDelivery = new CallBackDelivery(handler);
    }

    private void stop() {
        int i2 = 0;
        while (true) {
            DownloadDispatcher[] downloadDispatcherArr = this.mDownloadDispatchers;
            if (i2 >= downloadDispatcherArr.length) {
                return;
            }
            DownloadDispatcher downloadDispatcher = downloadDispatcherArr[i2];
            if (downloadDispatcher != null) {
                downloadDispatcher.quit();
            }
            i2++;
        }
    }

    public int add(DownloadRequest downloadRequest) {
        int downloadId = getDownloadId();
        downloadRequest.setDownloadRequestQueue(this);
        synchronized (this.mCurrentRequests) {
            this.mCurrentRequests.add(downloadRequest);
        }
        downloadRequest.setDownloadId(downloadId);
        this.mDownloadQueue.add(downloadRequest);
        return downloadId;
    }

    public int cancel(int i2) {
        synchronized (this.mCurrentRequests) {
            for (DownloadRequest downloadRequest : this.mCurrentRequests) {
                if (downloadRequest.getDownloadId() == i2) {
                    downloadRequest.cancel();
                    return 1;
                }
            }
            return 0;
        }
    }

    public void cancelAll() {
        synchronized (this.mCurrentRequests) {
            Iterator<DownloadRequest> it = this.mCurrentRequests.iterator();
            while (it.hasNext()) {
                it.next().cancel();
            }
            this.mCurrentRequests.clear();
        }
    }

    public void finish(DownloadRequest downloadRequest) {
        Set<DownloadRequest> set = this.mCurrentRequests;
        if (set != null) {
            synchronized (set) {
                this.mCurrentRequests.remove(downloadRequest);
            }
        }
    }

    public int query(int i2) {
        synchronized (this.mCurrentRequests) {
            for (DownloadRequest downloadRequest : this.mCurrentRequests) {
                if (downloadRequest.getDownloadId() == i2) {
                    return downloadRequest.getDownloadState();
                }
            }
            return 64;
        }
    }

    public void release() {
        Set<DownloadRequest> set = this.mCurrentRequests;
        if (set != null) {
            synchronized (set) {
                this.mCurrentRequests.clear();
                this.mCurrentRequests = null;
            }
        }
        if (this.mDownloadQueue != null) {
            this.mDownloadQueue = null;
        }
        if (this.mDownloadDispatchers == null) {
            return;
        }
        stop();
        int i2 = 0;
        while (true) {
            DownloadDispatcher[] downloadDispatcherArr = this.mDownloadDispatchers;
            if (i2 >= downloadDispatcherArr.length) {
                this.mDownloadDispatchers = null;
                return;
            } else {
                downloadDispatcherArr[i2] = null;
                i2++;
            }
        }
    }

    public void start() {
        stop();
        for (int i2 = 0; i2 < this.mDownloadDispatchers.length; i2++) {
            DownloadDispatcher downloadDispatcher = new DownloadDispatcher(this.mDownloadQueue, this.mDelivery);
            this.mDownloadDispatchers[i2] = downloadDispatcher;
            downloadDispatcher.start();
        }
    }

    public DownloadRequestQueue(int i2) {
        initialize(new Handler(Looper.getMainLooper()));
    }

    public DownloadRequestQueue(Handler handler) throws InvalidParameterException {
        if (handler != null) {
            initialize(handler);
            return;
        }
        throw new InvalidParameterException("callbackHandler must not be null");
    }
}
