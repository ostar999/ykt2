package com.plv.foundationsdk.net;

import android.content.Context;
import com.plv.foundationsdk.PLVUAClient;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.utils.PLVAppUtils;
import com.plv.foundationsdk.utils.PLVNetworkUtils;
import com.plv.foundationsdk.utils.PLVStethoDecoupler;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import org.jetbrains.annotations.NotNull;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/* loaded from: classes4.dex */
public class PLVRetrofitHelper {
    private static final String TAG = "PLVRetrofitHelper";
    private static OkHttpClient baseOkHttpClient;
    private static OkHttpClient offlineCacheOkHttpClient;
    private static OkHttpClient progressOkHttpClient;
    private static OkHttpClient progressOkHttpClientWithLog;
    private static OkHttpClient retryOkHttpClient;
    private static OkHttpClient userAgentOkHttpClient;
    private static Context mContext = PLVAppUtils.getApp();
    private static Map<RequestBody, WeakReference<PLVRfProgressListener>> progressListenerMap = new HashMap();

    public static class NetCacheInterceptor implements Interceptor {
        private NetCacheInterceptor() {
        }

        @Override // okhttp3.Interceptor
        public Response intercept(Interceptor.Chain chain) throws IOException {
            return chain.proceed(chain.request()).newBuilder().header("Cache-Control", "public, max-age=0").removeHeader("Pragma").build();
        }
    }

    public static class OfflineCacheInterceptor implements Interceptor {
        private OfflineCacheInterceptor() {
        }

        @Override // okhttp3.Interceptor
        public Response intercept(Interceptor.Chain chain) throws IOException {
            Request request = chain.request();
            if (!PLVNetworkUtils.isAvailable(PLVRetrofitHelper.mContext)) {
                request = request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=3600").build();
            }
            return chain.proceed(request);
        }
    }

    public static class ProgressInterceptor implements Interceptor {
        @Override // okhttp3.Interceptor
        public Response intercept(Interceptor.Chain chain) throws IOException {
            Request request = chain.request();
            if (request.body() == null) {
                return chain.proceed(request);
            }
            return chain.proceed(request.newBuilder().method(request.method(), new PLVCountingRequestBody(request.body(), (WeakReference) PLVRetrofitHelper.progressListenerMap.get(request.body()))).build());
        }
    }

    public static class RetryInterceptor implements Interceptor {
        private long interval;
        private int maxRetry;
        private int retryNum = 0;

        public RetryInterceptor(int i2, long j2) {
            this.maxRetry = i2;
            this.interval = j2;
        }

        @Override // okhttp3.Interceptor
        public Response intercept(Interceptor.Chain chain) throws InterruptedException, IOException {
            int i2;
            Request request = chain.request();
            Response responseProceed = chain.proceed(request);
            while (!responseProceed.isSuccessful() && (i2 = this.retryNum) < this.maxRetry) {
                this.retryNum = i2 + 1;
                try {
                    Thread.sleep(this.interval);
                } catch (InterruptedException e2) {
                    PLVCommonLog.e(PLVRetrofitHelper.TAG, e2.getMessage());
                    Thread.currentThread().interrupt();
                }
                responseProceed = chain.proceed(request);
            }
            return responseProceed;
        }
    }

    public static class UserAgentInterceptor implements Interceptor {
        @Override // okhttp3.Interceptor
        public Response intercept(Interceptor.Chain chain) throws IOException {
            return chain.proceed(chain.request().newBuilder().removeHeader("User-Agent").addHeader("User-Agent", PLVUAClient.getUserAgent()).build());
        }
    }

    public static OkHttpClient.Builder baseOkHttpBuilder() {
        return primalOkHttpBuilder(HttpLoggingInterceptor.Level.BODY);
    }

    public static OkHttpClient baseOkHttpClient() {
        if (baseOkHttpClient == null) {
            baseOkHttpClient = baseOkHttpBuilder().build();
        }
        return baseOkHttpClient;
    }

    public static Retrofit.Builder baseRetrofitBuilder(String str) {
        return baseRetrofitBuilder(str, baseOkHttpClient());
    }

    public static void clearProgressListener() {
        progressListenerMap.clear();
    }

    public static <T> T createApi(Class<T> cls, String str) {
        return (T) createApi(cls, str, baseOkHttpClient());
    }

    private static OkHttpClient offlineCacheOkHttpClient() {
        if (offlineCacheOkHttpClient == null) {
            offlineCacheOkHttpClient = baseOkHttpBuilder().addInterceptor(new OfflineCacheInterceptor()).build();
        }
        return offlineCacheOkHttpClient;
    }

    public static OkHttpClient.Builder primalOkHttpBuilder(HttpLoggingInterceptor.Level level) {
        Cache cache = new Cache(new File(mContext.getCacheDir(), "HttpCache"), 31457280L);
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() { // from class: com.plv.foundationsdk.net.PLVRetrofitHelper.1
            @Override // okhttp3.logging.HttpLoggingInterceptor.Logger
            public void log(@NotNull String str) {
                PLVCommonLog.d("OkHttp", str);
            }
        });
        httpLoggingInterceptor.setLevel(level);
        OkHttpClient.Builder builderRetryOnConnectionFailure = new OkHttpClient.Builder().dns(PLVOkHttpDns.getInstance()).cache(cache).addInterceptor(httpLoggingInterceptor).addNetworkInterceptor(PLVStethoDecoupler.createStethoInterceptor()).retryOnConnectionFailure(true);
        TimeUnit timeUnit = TimeUnit.SECONDS;
        return builderRetryOnConnectionFailure.connectTimeout(15L, timeUnit).writeTimeout(10L, timeUnit).readTimeout(10L, timeUnit);
    }

    public static OkHttpClient progressOkhttpClient(RequestBody requestBody, PLVRfProgressListener pLVRfProgressListener) {
        progressListenerMap.put(requestBody, new WeakReference<>(pLVRfProgressListener));
        if (progressOkHttpClient == null) {
            progressOkHttpClient = primalOkHttpBuilder(HttpLoggingInterceptor.Level.NONE).addInterceptor(new ProgressInterceptor()).addInterceptor(new UserAgentInterceptor()).build();
        }
        return progressOkHttpClient;
    }

    public static OkHttpClient progressOkhttpClientWithLog(RequestBody requestBody, PLVRfProgressListener pLVRfProgressListener) {
        progressListenerMap.put(requestBody, new WeakReference<>(pLVRfProgressListener));
        if (progressOkHttpClientWithLog == null) {
            progressOkHttpClientWithLog = baseOkHttpBuilder().addInterceptor(new ProgressInterceptor()).addInterceptor(new UserAgentInterceptor()).build();
        }
        return progressOkHttpClientWithLog;
    }

    public static void removeProgressListener(RequestBody requestBody) {
        progressListenerMap.remove(requestBody);
    }

    private static OkHttpClient retryOkHttpClient(int i2, long j2) {
        if (retryOkHttpClient == null) {
            retryOkHttpClient = baseOkHttpBuilder().addInterceptor(new RetryInterceptor(i2, j2)).build();
        }
        return retryOkHttpClient;
    }

    public static OkHttpClient userAgentOkHttpClient() {
        if (userAgentOkHttpClient == null) {
            userAgentOkHttpClient = baseOkHttpBuilder().addInterceptor(new UserAgentInterceptor()).build();
        }
        return userAgentOkHttpClient;
    }

    public static Retrofit.Builder baseRetrofitBuilder(String str, OkHttpClient okHttpClient) {
        Retrofit.Builder builderBaseUrl = new Retrofit.Builder().baseUrl(str);
        if (okHttpClient == null) {
            okHttpClient = baseOkHttpClient();
        }
        return builderBaseUrl.client(okHttpClient).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create());
    }

    public static <T> T createApi(Class<T> cls, String str, OkHttpClient okHttpClient) {
        Retrofit.Builder builderBaseUrl = new Retrofit.Builder().baseUrl(str);
        if (okHttpClient == null) {
            okHttpClient = baseOkHttpClient();
        }
        return (T) builderBaseUrl.client(okHttpClient).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create()).build().create(cls);
    }

    public static <T> T createApi(Class<T> cls, Retrofit.Builder builder) {
        return (T) builder.build().create(cls);
    }
}
