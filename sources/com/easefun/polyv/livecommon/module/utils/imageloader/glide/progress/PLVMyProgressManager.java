package com.easefun.polyv.livecommon.module.utils.imageloader.glide.progress;

import android.text.TextUtils;
import com.easefun.polyv.livecommon.module.utils.imageloader.glide.progress.PLVProgressResponseBody;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/* loaded from: classes3.dex */
public class PLVMyProgressManager {
    private static OkHttpClient okHttpClient;
    private static ConcurrentHashMap<String, ConcurrentHashMap<Object, PLVOnProgressListener>> listenersMap = new ConcurrentHashMap<>();
    private static final PLVProgressResponseBody.InternalProgressListener LISTENER = new PLVProgressResponseBody.InternalProgressListener() { // from class: com.easefun.polyv.livecommon.module.utils.imageloader.glide.progress.PLVMyProgressManager.1
        @Override // com.easefun.polyv.livecommon.module.utils.imageloader.glide.progress.PLVProgressResponseBody.InternalProgressListener
        public void onProgress(String url, long bytesRead, long totalBytes) {
            List<PLVOnProgressListener> progressListener = PLVMyProgressManager.getProgressListener(url);
            if (progressListener != null) {
                int i2 = (int) (((bytesRead * 1.0f) / totalBytes) * 100.0f);
                boolean z2 = i2 >= 100;
                if (z2) {
                    return;
                }
                Iterator<PLVOnProgressListener> it = progressListener.iterator();
                while (it.hasNext()) {
                    it.next().onProgress(url, z2, i2, bytesRead, totalBytes);
                }
            }
        }
    };

    private PLVMyProgressManager() {
    }

    public static void addListener(String moduleTag, Object urlTag, PLVOnProgressListener listener) {
        if (listener == null || TextUtils.isEmpty(listener.getUrl())) {
            return;
        }
        ConcurrentHashMap<Object, PLVOnProgressListener> progressListenerList = getProgressListenerList(moduleTag);
        if (progressListenerList == null) {
            ConcurrentHashMap<Object, PLVOnProgressListener> concurrentHashMap = new ConcurrentHashMap<>();
            concurrentHashMap.put(urlTag, listener);
            listenersMap.put(moduleTag, concurrentHashMap);
        } else {
            if (!progressListenerList.containsKey(urlTag)) {
                progressListenerList.put(urlTag, listener);
                return;
            }
            progressListenerList.get(urlTag).transListener(null);
            progressListenerList.remove(urlTag);
            progressListenerList.put(urlTag, listener);
        }
    }

    public static ConcurrentHashMap<String, ConcurrentHashMap<Object, PLVOnProgressListener>> getListenersMap() {
        return listenersMap;
    }

    public static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder().addNetworkInterceptor(new Interceptor() { // from class: com.easefun.polyv.livecommon.module.utils.imageloader.glide.progress.PLVMyProgressManager.2
                @Override // okhttp3.Interceptor
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    Request request = chain.request();
                    final String url = request.url().getUrl();
                    final List<PLVOnProgressListener> progressListener = PLVMyProgressManager.getProgressListener(url);
                    if (progressListener != null) {
                        PLVProgressResponseBody.mainThreadHandler.post(new Runnable() { // from class: com.easefun.polyv.livecommon.module.utils.imageloader.glide.progress.PLVMyProgressManager.2.1
                            @Override // java.lang.Runnable
                            public void run() {
                                Iterator it = progressListener.iterator();
                                while (it.hasNext()) {
                                    ((PLVOnProgressListener) it.next()).onStart(url);
                                }
                            }
                        });
                    }
                    Response responseProceed = chain.proceed(request);
                    return responseProceed.newBuilder().body(new PLVProgressResponseBody(url, PLVMyProgressManager.LISTENER, responseProceed.body())).build();
                }
            }).build();
        }
        return okHttpClient;
    }

    public static List<PLVOnProgressListener> getProgressListener(String url) {
        ArrayList arrayList = new ArrayList();
        if (listenersMap != null && !TextUtils.isEmpty(url)) {
            Iterator<String> it = listenersMap.keySet().iterator();
            while (it.hasNext()) {
                ConcurrentHashMap<Object, PLVOnProgressListener> progressListenerList = getProgressListenerList(it.next());
                if (progressListenerList != null) {
                    for (PLVOnProgressListener pLVOnProgressListener : progressListenerList.values()) {
                        if (url.equals(pLVOnProgressListener.getUrl())) {
                            arrayList.add(pLVOnProgressListener);
                        }
                    }
                }
            }
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return arrayList;
    }

    public static ConcurrentHashMap<Object, PLVOnProgressListener> getProgressListenerList(String moduleTag) {
        ConcurrentHashMap<String, ConcurrentHashMap<Object, PLVOnProgressListener>> concurrentHashMap;
        if (TextUtils.isEmpty(moduleTag) || (concurrentHashMap = listenersMap) == null || concurrentHashMap.size() == 0 || listenersMap.get(moduleTag) == null || listenersMap.get(moduleTag).size() == 0) {
            return null;
        }
        return listenersMap.get(moduleTag);
    }

    public static boolean isContainsListener(String moduleTag, Object urlTag) {
        ConcurrentHashMap<Object, PLVOnProgressListener> progressListenerList = getProgressListenerList(moduleTag);
        if (progressListenerList != null) {
            return progressListenerList.containsKey(urlTag);
        }
        return false;
    }

    public static void removeAllListener() {
        listenersMap.clear();
    }

    public static void removeListener(String moduleTag, Object urlTag) {
        ConcurrentHashMap<Object, PLVOnProgressListener> progressListenerList = getProgressListenerList(moduleTag);
        if (progressListenerList == null || !progressListenerList.containsKey(urlTag)) {
            return;
        }
        progressListenerList.get(urlTag).transListener(null);
        progressListenerList.remove(urlTag);
    }

    public static void removeModuleListener(String moduleTag) {
        ConcurrentHashMap<Object, PLVOnProgressListener> progressListenerList = getProgressListenerList(moduleTag);
        if (progressListenerList != null) {
            Iterator<PLVOnProgressListener> it = progressListenerList.values().iterator();
            while (it.hasNext()) {
                it.next().transListener(null);
            }
            progressListenerList.clear();
        }
    }

    public static PLVOnProgressListener getProgressListener(String moduleTag, Object urlTag) {
        ConcurrentHashMap<Object, PLVOnProgressListener> progressListenerList = getProgressListenerList(moduleTag);
        if (progressListenerList == null || !progressListenerList.containsKey(urlTag)) {
            return null;
        }
        return progressListenerList.get(urlTag).getTransListener();
    }
}
