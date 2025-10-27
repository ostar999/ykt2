package com.unity3d.splash.services.core.request;

import android.os.ConditionVariable;
import com.google.android.exoplayer2.audio.SilenceSkippingAudioProcessor;
import com.unity3d.splash.services.core.log.DeviceLog;
import com.unity3d.splash.services.core.request.WebRequest;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import org.apache.http.HttpHeaders;

/* loaded from: classes6.dex */
public class WebRequestThread {
    private static int _corePoolSize = 1;
    private static long _keepAliveTime = 1000;
    private static int _maximumPoolSize = 1;
    private static CancelableThreadPoolExecutor _pool = null;
    private static LinkedBlockingQueue _queue = null;
    private static boolean _ready = false;
    private static final Object _readyLock = new Object();

    public static synchronized void cancel() {
        CancelableThreadPoolExecutor cancelableThreadPoolExecutor = _pool;
        if (cancelableThreadPoolExecutor != null) {
            cancelableThreadPoolExecutor.cancel();
            Iterator it = _queue.iterator();
            while (it.hasNext()) {
                Runnable runnable = (Runnable) it.next();
                if (runnable instanceof WebRequestRunnable) {
                    ((WebRequestRunnable) runnable).setCancelStatus(true);
                }
            }
            _queue.clear();
            _pool.purge();
        }
    }

    private static synchronized void init() {
        _queue = new LinkedBlockingQueue();
        CancelableThreadPoolExecutor cancelableThreadPoolExecutor = new CancelableThreadPoolExecutor(_corePoolSize, _maximumPoolSize, _keepAliveTime, TimeUnit.MILLISECONDS, _queue);
        _pool = cancelableThreadPoolExecutor;
        cancelableThreadPoolExecutor.prestartAllCoreThreads();
        _queue.add(new Runnable() { // from class: com.unity3d.splash.services.core.request.WebRequestThread.1
            @Override // java.lang.Runnable
            public final void run() {
                boolean unused = WebRequestThread._ready = true;
                synchronized (WebRequestThread._readyLock) {
                    WebRequestThread._readyLock.notify();
                }
            }
        });
        while (!_ready) {
            try {
                Object obj = _readyLock;
                synchronized (obj) {
                    obj.wait();
                }
            } catch (InterruptedException unused) {
                DeviceLog.debug("Couldn't synchronize thread");
                return;
            }
        }
    }

    public static synchronized void request(String str, WebRequest.RequestType requestType, Map map, Integer num, Integer num2, IWebRequestListener iWebRequestListener) {
        request(str, requestType, map, null, num, num2, iWebRequestListener);
    }

    public static synchronized void request(String str, WebRequest.RequestType requestType, Map map, String str2, Integer num, Integer num2, IWebRequestListener iWebRequestListener) {
        if (!_ready) {
            init();
        }
        if (str != null && str.length() >= 3) {
            _queue.add(new WebRequestRunnable(str, requestType.name(), str2, num.intValue(), num2.intValue(), map, iWebRequestListener));
            return;
        }
        iWebRequestListener.onFailed(str, "Request is NULL or too short");
    }

    public static synchronized void reset() {
        cancel();
        CancelableThreadPoolExecutor cancelableThreadPoolExecutor = _pool;
        if (cancelableThreadPoolExecutor != null) {
            cancelableThreadPoolExecutor.shutdown();
            try {
                _pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            } catch (InterruptedException unused) {
            }
            _queue.clear();
            _pool = null;
            _queue = null;
            _ready = false;
        }
    }

    public static synchronized boolean resolve(final String str, final IResolveHostListener iResolveHostListener) {
        if (str != null) {
            if (str.length() >= 3) {
                new Thread(new Runnable() { // from class: com.unity3d.splash.services.core.request.WebRequestThread.2
                    @Override // java.lang.Runnable
                    public final void run() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
                        Thread thread;
                        Exception e2;
                        final ConditionVariable conditionVariable = new ConditionVariable();
                        try {
                            thread = new Thread(new Runnable() { // from class: com.unity3d.splash.services.core.request.WebRequestThread.2.1
                                @Override // java.lang.Runnable
                                public void run() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
                                    try {
                                        String hostAddress = InetAddress.getByName(str).getHostAddress();
                                        AnonymousClass2 anonymousClass2 = AnonymousClass2.this;
                                        iResolveHostListener.onResolve(str, hostAddress);
                                    } catch (UnknownHostException e3) {
                                        DeviceLog.exception("Unknown host", e3);
                                        AnonymousClass2 anonymousClass22 = AnonymousClass2.this;
                                        iResolveHostListener.onFailed(str, ResolveHostError.UNKNOWN_HOST, e3.getMessage());
                                    }
                                    conditionVariable.open();
                                }
                            });
                            try {
                                thread.start();
                            } catch (Exception e3) {
                                e2 = e3;
                                DeviceLog.exception("Exception while resolving host", e2);
                                iResolveHostListener.onFailed(str, ResolveHostError.UNEXPECTED_EXCEPTION, e2.getMessage());
                                if (conditionVariable.block(SilenceSkippingAudioProcessor.DEFAULT_PADDING_SILENCE_US)) {
                                    return;
                                } else {
                                    return;
                                }
                            }
                        } catch (Exception e4) {
                            thread = null;
                            e2 = e4;
                        }
                        if (conditionVariable.block(SilenceSkippingAudioProcessor.DEFAULT_PADDING_SILENCE_US) || thread == null) {
                            return;
                        }
                        thread.interrupt();
                        iResolveHostListener.onFailed(str, ResolveHostError.TIMEOUT, HttpHeaders.TIMEOUT);
                    }
                }).start();
                return true;
            }
        }
        iResolveHostListener.onFailed(str, ResolveHostError.INVALID_HOST, "Host is NULL");
        return false;
    }

    public static synchronized void setConcurrentRequestCount(int i2) {
        _corePoolSize = i2;
        _maximumPoolSize = i2;
        CancelableThreadPoolExecutor cancelableThreadPoolExecutor = _pool;
        if (cancelableThreadPoolExecutor != null) {
            cancelableThreadPoolExecutor.setCorePoolSize(i2);
            _pool.setMaximumPoolSize(_maximumPoolSize);
        }
    }

    public static synchronized void setKeepAliveTime(long j2) {
        _keepAliveTime = j2;
        CancelableThreadPoolExecutor cancelableThreadPoolExecutor = _pool;
        if (cancelableThreadPoolExecutor != null) {
            cancelableThreadPoolExecutor.setKeepAliveTime(j2, TimeUnit.MILLISECONDS);
        }
    }

    public static synchronized void setMaximumPoolSize(int i2) {
        _maximumPoolSize = i2;
        CancelableThreadPoolExecutor cancelableThreadPoolExecutor = _pool;
        if (cancelableThreadPoolExecutor != null) {
            cancelableThreadPoolExecutor.setMaximumPoolSize(i2);
        }
    }
}
