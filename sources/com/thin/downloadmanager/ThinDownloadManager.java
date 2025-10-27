package com.thin.downloadmanager;

import android.os.Handler;
import java.security.InvalidParameterException;

/* loaded from: classes6.dex */
public class ThinDownloadManager implements DownloadManager {
    private DownloadRequestQueue mRequestQueue;

    public ThinDownloadManager() {
        DownloadRequestQueue downloadRequestQueue = new DownloadRequestQueue();
        this.mRequestQueue = downloadRequestQueue;
        downloadRequestQueue.start();
    }

    @Override // com.thin.downloadmanager.DownloadManager
    public int add(DownloadRequest downloadRequest) throws IllegalArgumentException {
        if (downloadRequest != null) {
            return this.mRequestQueue.add(downloadRequest);
        }
        throw new IllegalArgumentException("DownloadRequest cannot be null");
    }

    @Override // com.thin.downloadmanager.DownloadManager
    public int cancel(int i2) {
        return this.mRequestQueue.cancel(i2);
    }

    @Override // com.thin.downloadmanager.DownloadManager
    public void cancelAll() {
        this.mRequestQueue.cancelAll();
    }

    @Override // com.thin.downloadmanager.DownloadManager
    public int query(int i2) {
        return this.mRequestQueue.query(i2);
    }

    @Override // com.thin.downloadmanager.DownloadManager
    public void release() {
        DownloadRequestQueue downloadRequestQueue = this.mRequestQueue;
        if (downloadRequestQueue != null) {
            downloadRequestQueue.release();
            this.mRequestQueue = null;
        }
    }

    public ThinDownloadManager(Handler handler) throws InvalidParameterException {
        DownloadRequestQueue downloadRequestQueue = new DownloadRequestQueue(handler);
        this.mRequestQueue = downloadRequestQueue;
        downloadRequestQueue.start();
    }

    public ThinDownloadManager(int i2) {
        DownloadRequestQueue downloadRequestQueue = new DownloadRequestQueue(i2);
        this.mRequestQueue = downloadRequestQueue;
        downloadRequestQueue.start();
    }
}
