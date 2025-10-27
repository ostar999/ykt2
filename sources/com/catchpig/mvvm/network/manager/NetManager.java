package com.catchpig.mvvm.network.manager;

import androidx.exifinterface.media.ExifInterface;
import com.catchpig.mvvm.entity.ServiceParam;
import com.catchpig.mvvm.ksp.KotlinMvvmCompiler;
import com.catchpig.mvvm.network.factory.SerializationConverterFactory;
import com.catchpig.utils.ext.LogExtKt;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.jetbrains.annotations.NotNull;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;

@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u0000 \u00172\u00020\u0001:\u0002\u0017\u0018B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0007H\u0002J#\u0010\u000e\u001a\u0002H\u000f\"\b\b\u0000\u0010\u000f*\u00020\u00012\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\u000f0\u0011¢\u0006\u0002\u0010\u0012J+\u0010\u0013\u001a\u0002H\u000f\"\b\b\u0000\u0010\u000f*\u00020\u00012\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\u000f0\u00112\u0006\u0010\r\u001a\u00020\u0007¢\u0006\u0002\u0010\u0014J\u000e\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0003\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R*\u0010\u0005\u001a\u001e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00010\u0006j\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u0001`\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lcom/catchpig/mvvm/network/manager/NetManager;", "", "()V", "debug", "", "serviceMap", "Ljava/util/HashMap;", "", "Lkotlin/collections/HashMap;", "getClient", "Lokhttp3/OkHttpClient;", "serviceParam", "Lcom/catchpig/mvvm/entity/ServiceParam;", "baseUrl", "getService", ExifInterface.LATITUDE_SOUTH, "serviceClass", "Ljava/lang/Class;", "(Ljava/lang/Class;)Ljava/lang/Object;", "getServiceBase", "(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Object;", "setDebug", "", "Companion", "NetManagerHolder", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nNetManager.kt\nKotlin\n*S Kotlin\n*F\n+ 1 NetManager.kt\ncom/catchpig/mvvm/network/manager/NetManager\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,115:1\n1855#2,2:116\n1855#2,2:118\n*S KotlinDebug\n*F\n+ 1 NetManager.kt\ncom/catchpig/mvvm/network/manager/NetManager\n*L\n99#1:116,2\n109#1:118,2\n*E\n"})
/* loaded from: classes2.dex */
public final class NetManager {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private static final String TAG = "NetManager";
    private boolean debug;

    @NotNull
    private final HashMap<String, Object> serviceMap;

    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0005\u001a\u00020\u0006R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0007"}, d2 = {"Lcom/catchpig/mvvm/network/manager/NetManager$Companion;", "", "()V", "TAG", "", "getInstance", "Lcom/catchpig/mvvm/network/manager/NetManager;", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final NetManager getInstance() {
            return NetManagerHolder.INSTANCE.getHolder();
        }
    }

    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÂ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lcom/catchpig/mvvm/network/manager/NetManager$NetManagerHolder;", "", "()V", "holder", "Lcom/catchpig/mvvm/network/manager/NetManager;", "getHolder", "()Lcom/catchpig/mvvm/network/manager/NetManager;", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class NetManagerHolder {

        @NotNull
        public static final NetManagerHolder INSTANCE = new NetManagerHolder();

        @NotNull
        private static final NetManager holder = new NetManager(null);

        private NetManagerHolder() {
        }

        @NotNull
        public final NetManager getHolder() {
            return holder;
        }
    }

    private NetManager() {
        this.serviceMap = new HashMap<>();
    }

    public /* synthetic */ NetManager(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private final OkHttpClient getClient(ServiceParam serviceParam, String baseUrl) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        long connectTimeout = serviceParam.getConnectTimeout();
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        OkHttpClient.Builder timeout = builder.connectTimeout(connectTimeout, timeUnit).readTimeout(serviceParam.getReadTimeout(), timeUnit);
        SSLSocketClient sSLSocketClient = SSLSocketClient.INSTANCE;
        timeout.sslSocketFactory(sSLSocketClient.getSsLSocketFactory(), sSLSocketClient.getTrustManager()[0]).hostnameVerifier(sSLSocketClient.getHostnameVerifier());
        if (this.debug) {
            Iterator<T> it = serviceParam.getDebugInterceptors().iterator();
            while (it.hasNext()) {
                timeout = timeout.addInterceptor((Interceptor) it.next());
            }
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() { // from class: com.catchpig.mvvm.network.manager.a
                @Override // okhttp3.logging.HttpLoggingInterceptor.Logger
                public final void log(String str) {
                    NetManager.getClient$lambda$1(str);
                }
            });
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
            timeout = timeout.addInterceptor(httpLoggingInterceptor);
        }
        Iterator<T> it2 = serviceParam.getInterceptors().iterator();
        while (it2.hasNext()) {
            timeout = timeout.addInterceptor((Interceptor) it2.next());
        }
        return timeout.build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void getClient$lambda$1(String it) {
        Intrinsics.checkNotNullParameter(it, "it");
        LogExtKt.logd(it, TAG);
    }

    @NotNull
    public final <S> S getService(@NotNull Class<S> serviceClass) {
        Intrinsics.checkNotNullParameter(serviceClass, "serviceClass");
        String className = serviceClass.getName();
        S s2 = (S) this.serviceMap.get(className);
        if (s2 != null) {
            return s2;
        }
        KotlinMvvmCompiler kotlinMvvmCompiler = KotlinMvvmCompiler.INSTANCE;
        Intrinsics.checkNotNullExpressionValue(className, "className");
        ServiceParam serviceParam = kotlinMvvmCompiler.getServiceParam(className);
        Retrofit.Builder builderAddConverterFactory = new Retrofit.Builder().baseUrl(serviceParam.getBaseUrl()).client(getClient(serviceParam, serviceParam.getBaseUrl())).addConverterFactory(SerializationConverterFactory.INSTANCE.create(className));
        if (serviceParam.getRxJava()) {
            builderAddConverterFactory = builderAddConverterFactory.addCallAdapterFactory(RxJava3CallAdapterFactory.create());
        }
        S s3 = (S) builderAddConverterFactory.build().create(serviceClass);
        HashMap<String, Object> map = this.serviceMap;
        Intrinsics.checkNotNull(s3);
        map.put(className, s3);
        return s3;
    }

    @NotNull
    public final <S> S getServiceBase(@NotNull Class<S> serviceClass, @NotNull String baseUrl) {
        Intrinsics.checkNotNullParameter(serviceClass, "serviceClass");
        Intrinsics.checkNotNullParameter(baseUrl, "baseUrl");
        String className = serviceClass.getName();
        S s2 = (S) this.serviceMap.get(baseUrl);
        if (s2 != null) {
            return s2;
        }
        KotlinMvvmCompiler kotlinMvvmCompiler = KotlinMvvmCompiler.INSTANCE;
        Intrinsics.checkNotNullExpressionValue(className, "className");
        ServiceParam serviceParam = kotlinMvvmCompiler.getServiceParam(className);
        Retrofit.Builder builderAddConverterFactory = new Retrofit.Builder().baseUrl(baseUrl).client(getClient(serviceParam, baseUrl)).addConverterFactory(SerializationConverterFactory.INSTANCE.create(className));
        if (serviceParam.getRxJava()) {
            builderAddConverterFactory = builderAddConverterFactory.addCallAdapterFactory(RxJava3CallAdapterFactory.create());
        }
        S s3 = (S) builderAddConverterFactory.build().create(serviceClass);
        HashMap<String, Object> map = this.serviceMap;
        Intrinsics.checkNotNull(s3);
        map.put(className, s3);
        return s3;
    }

    public final void setDebug(boolean debug) {
        this.debug = debug;
    }
}
