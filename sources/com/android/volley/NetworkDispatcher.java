package com.android.volley;

import android.net.TrafficStats;
import android.os.Process;
import java.util.concurrent.BlockingQueue;

/* loaded from: classes2.dex */
public class NetworkDispatcher extends Thread {
    private final Cache mCache;
    private final ResponseDelivery mDelivery;
    private final Network mNetwork;
    private final BlockingQueue<Request> mQueue;
    private volatile boolean mQuit = false;

    public NetworkDispatcher(BlockingQueue<Request> blockingQueue, Network network, Cache cache, ResponseDelivery responseDelivery) {
        this.mQueue = blockingQueue;
        this.mNetwork = network;
        this.mCache = cache;
        this.mDelivery = responseDelivery;
    }

    private void parseAndDeliverNetworkError(Request<?> request, VolleyError volleyError) {
        this.mDelivery.postError(request, request.parseNetworkError(volleyError));
    }

    public void quit() {
        this.mQuit = true;
        interrupt();
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() throws InterruptedException, SecurityException, IllegalArgumentException {
        Process.setThreadPriority(10);
        while (true) {
            try {
                Request requestTake = this.mQueue.take();
                try {
                    requestTake.addMarker("network-queue-take");
                    if (requestTake.isCanceled()) {
                        requestTake.finish("network-discard-cancelled");
                    } else {
                        TrafficStats.setThreadStatsTag(requestTake.getTrafficStatsTag());
                        NetworkResponse networkResponsePerformRequest = this.mNetwork.performRequest(requestTake);
                        requestTake.addMarker("network-http-complete");
                        if (networkResponsePerformRequest.notModified && requestTake.hasHadResponseDelivered()) {
                            requestTake.finish("not-modified");
                        } else {
                            Response<?> networkResponse = requestTake.parseNetworkResponse(networkResponsePerformRequest);
                            requestTake.addMarker("network-parse-complete");
                            if (requestTake.shouldCache() && networkResponse.cacheEntry != null) {
                                this.mCache.put(requestTake.getCacheKey(), networkResponse.cacheEntry);
                                requestTake.addMarker("network-cache-written");
                            }
                            requestTake.markDelivered();
                            this.mDelivery.postResponse(requestTake, networkResponse);
                        }
                    }
                } catch (VolleyError e2) {
                    parseAndDeliverNetworkError(requestTake, e2);
                } catch (Exception e3) {
                    VolleyLog.e(e3, "Unhandled exception %s", e3.toString());
                    this.mDelivery.postError(requestTake, new VolleyError(e3));
                }
            } catch (InterruptedException unused) {
                if (this.mQuit) {
                    return;
                }
            }
        }
    }
}
