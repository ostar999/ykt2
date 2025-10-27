package com.android.volley;

import android.os.Process;
import com.android.volley.Cache;
import java.util.concurrent.BlockingQueue;

/* loaded from: classes2.dex */
public class CacheDispatcher extends Thread {
    private static final boolean DEBUG = VolleyLog.DEBUG;
    private final Cache mCache;
    private final BlockingQueue<Request> mCacheQueue;
    private final ResponseDelivery mDelivery;
    private final BlockingQueue<Request> mNetworkQueue;
    private volatile boolean mQuit = false;

    public CacheDispatcher(BlockingQueue<Request> blockingQueue, BlockingQueue<Request> blockingQueue2, Cache cache, ResponseDelivery responseDelivery) {
        this.mCacheQueue = blockingQueue;
        this.mNetworkQueue = blockingQueue2;
        this.mCache = cache;
        this.mDelivery = responseDelivery;
    }

    public void quit() {
        this.mQuit = true;
        interrupt();
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() throws InterruptedException, SecurityException, IllegalArgumentException {
        if (DEBUG) {
            VolleyLog.v("start new dispatcher", new Object[0]);
        }
        Process.setThreadPriority(10);
        this.mCache.initialize();
        while (true) {
            try {
                final Request<?> requestTake = this.mCacheQueue.take();
                requestTake.addMarker("cache-queue-take");
                if (requestTake.isCanceled()) {
                    requestTake.finish("cache-discard-canceled");
                } else {
                    Cache.Entry entry = this.mCache.get(requestTake.getCacheKey());
                    if (entry == null) {
                        requestTake.addMarker("cache-miss");
                        this.mNetworkQueue.put(requestTake);
                    } else if (entry.isExpired()) {
                        requestTake.addMarker("cache-hit-expired");
                        requestTake.setCacheEntry(entry);
                        this.mNetworkQueue.put(requestTake);
                    } else {
                        requestTake.addMarker("cache-hit");
                        Response<?> networkResponse = requestTake.parseNetworkResponse(new NetworkResponse(entry.data, entry.responseHeaders));
                        requestTake.addMarker("cache-hit-parsed");
                        if (entry.refreshNeeded()) {
                            requestTake.addMarker("cache-hit-refresh-needed");
                            requestTake.setCacheEntry(entry);
                            networkResponse.intermediate = true;
                            this.mDelivery.postResponse(requestTake, networkResponse, new Runnable() { // from class: com.android.volley.CacheDispatcher.1
                                @Override // java.lang.Runnable
                                public void run() throws InterruptedException {
                                    try {
                                        CacheDispatcher.this.mNetworkQueue.put(requestTake);
                                    } catch (InterruptedException unused) {
                                    }
                                }
                            });
                        } else {
                            this.mDelivery.postResponse(requestTake, networkResponse);
                        }
                    }
                }
            } catch (InterruptedException unused) {
                if (this.mQuit) {
                    return;
                }
            }
        }
    }
}
