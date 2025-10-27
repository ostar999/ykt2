package com.ykb.ebook.network;

import com.ykb.ebook.common.ConstantKt;
import com.ykb.ebook.util.Log;
import java.net.Proxy;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.jetbrains.annotations.NotNull;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Metadata(d1 = {"\u0000\"\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\b\u0010\b\u001a\u00020\tH\u0002\u001a\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002\"\u001b\u0010\u0000\u001a\u00020\u00018FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0002\u0010\u0003\"\u000e\u0010\u0006\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"retrofit", "Lretrofit2/Retrofit;", "getRetrofit", "()Lretrofit2/Retrofit;", "retrofit$delegate", "Lkotlin/Lazy;", "tag", "", "getOkHttpClient", "Lokhttp3/OkHttpClient;", "getSSLFactory", "Ljavax/net/ssl/SSLSocketFactory;", "x509TrustManager", "Ljavax/net/ssl/X509TrustManager;", "ebook_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class RetrofitClientKt {

    @NotNull
    private static final Lazy retrofit$delegate = LazyKt__LazyJVMKt.lazy(new Function0<Retrofit>() { // from class: com.ykb.ebook.network.RetrofitClientKt$retrofit$2
        @Override // kotlin.jvm.functions.Function0
        public final Retrofit invoke() {
            return new Retrofit.Builder().baseUrl(ConstantKt.getBASE_URL()).addConverterFactory(GsonConverterFactory.create()).client(RetrofitClientKt.getOkHttpClient()).build();
        }
    });

    @NotNull
    public static final String tag = "Retrofit";

    /* JADX INFO: Access modifiers changed from: private */
    public static final OkHttpClient getOkHttpClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new CustomInterceptor());
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        CacheControlInterceptor cacheControlInterceptor = new CacheControlInterceptor();
        SignInterceptor signInterceptor = new SignInterceptor();
        MineX509 mineX509 = new MineX509();
        OkHttpClient.Builder builderSslSocketFactory = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).addInterceptor(cacheControlInterceptor).addInterceptor(signInterceptor).sslSocketFactory(getSSLFactory(mineX509), mineX509);
        TimeUnit timeUnit = TimeUnit.SECONDS;
        OkHttpClient.Builder timeout = builderSslSocketFactory.connectTimeout(15L, timeUnit).readTimeout(15L, timeUnit);
        if (!ConstantKt.getIS_DEBUG()) {
            timeout.proxy(Proxy.NO_PROXY);
        }
        return timeout.build();
    }

    @NotNull
    public static final Retrofit getRetrofit() {
        Object value = retrofit$delegate.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "<get-retrofit>(...)");
        return (Retrofit) value;
    }

    private static final SSLSocketFactory getSSLFactory(X509TrustManager x509TrustManager) throws NoSuchAlgorithmException, KeyManagementException {
        TrustManager[] trustManagerArr = {x509TrustManager};
        try {
            SSLContext sSLContext = SSLContext.getInstance("SSL");
            sSLContext.init(null, trustManagerArr, new SecureRandom());
            SSLSocketFactory socketFactory = sSLContext.getSocketFactory();
            Intrinsics.checkNotNullExpressionValue(socketFactory, "{\n        val sslContext…ntext.socketFactory\n    }");
            return socketFactory;
        } catch (KeyManagementException e2) {
            Log log = Log.INSTANCE;
            String message = e2.getMessage();
            log.logE(tag, message != null ? message : "未知错误");
            return new MineFactory();
        } catch (NoSuchAlgorithmException e3) {
            Log log2 = Log.INSTANCE;
            String message2 = e3.getMessage();
            log2.logE(tag, message2 != null ? message2 : "未知错误");
            return new MineFactory();
        }
    }
}
