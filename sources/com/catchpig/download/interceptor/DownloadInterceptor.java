package com.catchpig.download.interceptor;

import com.catchpig.download.callback.DownloadProgressListener;
import com.catchpig.download.response.DownloadResponseBody;
import io.reactivex.rxjava3.subscribers.ResourceSubscriber;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u0006J\b\u0010\u000b\u001a\u00020\bH\u0002J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016R\u001c\u0010\u0003\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lcom/catchpig/download/interceptor/DownloadInterceptor;", "Lokhttp3/Interceptor;", "()V", "downloadProgressListenerMap", "", "", "Lcom/catchpig/download/callback/DownloadProgressListener;", "addProgressListener", "", "key", "downloadProgressListener", "clearInvalidProgressListener", "intercept", "Lokhttp3/Response;", "chain", "Lokhttp3/Interceptor$Chain;", "download_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nDownloadInterceptor.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DownloadInterceptor.kt\ncom/catchpig/download/interceptor/DownloadInterceptor\n+ 2 Iterators.kt\nkotlin/collections/CollectionsKt__IteratorsKt\n*L\n1#1,56:1\n32#2,2:57\n*S KotlinDebug\n*F\n+ 1 DownloadInterceptor.kt\ncom/catchpig/download/interceptor/DownloadInterceptor\n*L\n28#1:57,2\n*E\n"})
/* loaded from: classes2.dex */
public final class DownloadInterceptor implements Interceptor {

    @NotNull
    private Map<String, DownloadProgressListener> downloadProgressListenerMap = new LinkedHashMap();

    private final void clearInvalidProgressListener() {
        Iterator<Map.Entry<String, DownloadProgressListener>> it = this.downloadProgressListenerMap.entrySet().iterator();
        while (it.hasNext()) {
            Object obj = (DownloadProgressListener) it.next().getValue();
            if (obj == null) {
                it.remove();
            } else if ((obj instanceof ResourceSubscriber) && ((ResourceSubscriber) obj).isDisposed()) {
                it.remove();
            }
        }
    }

    public final void addProgressListener(@NotNull String key, @NotNull DownloadProgressListener downloadProgressListener) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(downloadProgressListener, "downloadProgressListener");
        this.downloadProgressListenerMap.put(key, downloadProgressListener);
        clearInvalidProgressListener();
    }

    @Override // okhttp3.Interceptor
    @NotNull
    public Response intercept(@NotNull Interceptor.Chain chain) throws IOException {
        Intrinsics.checkNotNullParameter(chain, "chain");
        Request request = chain.request();
        Response responseProceed = chain.proceed(request);
        String string = request.url().url().toString();
        Intrinsics.checkNotNullExpressionValue(string, "request.url.toUrl().toString()");
        DownloadProgressListener downloadProgressListener = this.downloadProgressListenerMap.get(string);
        Response.Builder builderNewBuilder = responseProceed.newBuilder();
        ResponseBody responseBodyBody = responseProceed.body();
        Intrinsics.checkNotNull(responseBodyBody);
        return builderNewBuilder.body(new DownloadResponseBody(responseBodyBody, downloadProgressListener)).build();
    }
}
