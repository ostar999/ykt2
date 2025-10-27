package com.aliyun.svideo.common.okhttp.interceptor;

import android.util.Log;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/* loaded from: classes2.dex */
public class LoggingIntcepetor implements Interceptor {
    private final String TAG = getClass().getSimpleName();

    @Override // okhttp3.Interceptor
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        long jNanoTime = System.nanoTime();
        Log.d(this.TAG, "Sending request: " + request.url() + "\n" + request.headers());
        Response responseProceed = chain.proceed(request);
        long jNanoTime2 = System.nanoTime();
        Log.d(this.TAG, "Received response for " + responseProceed.request().url() + " in " + ((jNanoTime2 - jNanoTime) / 1000000.0d) + "ms\n" + responseProceed.headers());
        return responseProceed;
    }
}
