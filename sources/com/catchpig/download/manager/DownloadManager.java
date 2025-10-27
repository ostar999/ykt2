package com.catchpig.download.manager;

import android.os.Environment;
import android.util.ArrayMap;
import cn.hutool.core.text.StrPool;
import com.catchpig.download.api.DownloadService;
import com.catchpig.download.callback.DownloadProgressListener;
import com.catchpig.download.interceptor.DownloadInterceptor;
import com.catchpig.utils.ext.FileExtKt;
import com.catchpig.utils.manager.ContextManager;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0016\u0018\u0000 \r2\u00020\u0001:\u0001\rB\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0002J\u0010\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0006H\u0004J\u0018\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0006H\u0004¨\u0006\u000e"}, d2 = {"Lcom/catchpig/download/manager/DownloadManager;", "", "()V", "clearFiles", "", "getDownloadFilePath", "", "localFileName", "downloadUrl", "writeCache", "responseBody", "Lokhttp3/ResponseBody;", "fileName", "Companion", "download_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public class DownloadManager {
    private static final long CONNECT_TIMEOUT = 5;
    private static final long READ_TIMEOUT = 10;

    @Nullable
    private static String downloadPath;

    @Nullable
    private static OkHttpClient okHttpClient;

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private static Map<String, DownloadService> downloadServiceMap = new ArrayMap();

    @NotNull
    private static final Lazy<HttpLoggingInterceptor> logInterceptor$delegate = LazyKt__LazyJVMKt.lazy(new Function0<HttpLoggingInterceptor>() { // from class: com.catchpig.download.manager.DownloadManager$Companion$logInterceptor$2
        @Override // kotlin.jvm.functions.Function0
        @NotNull
        public final HttpLoggingInterceptor invoke() {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(null, 1, null);
            httpLoggingInterceptor.level(HttpLoggingInterceptor.Level.HEADERS);
            return httpLoggingInterceptor;
        }
    });

    @NotNull
    private static final Lazy<DownloadInterceptor> downloadInterceptor$delegate = LazyKt__LazyJVMKt.lazy(new Function0<DownloadInterceptor>() { // from class: com.catchpig.download.manager.DownloadManager$Companion$downloadInterceptor$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        @NotNull
        public final DownloadInterceptor invoke() {
            return new DownloadInterceptor();
        }
    });

    @Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0018\u001a\u00020\u00102\u0006\u0010\u0019\u001a\u00020\rH\u0002J\u0018\u0010\u001a\u001a\u00020\u00102\u0006\u0010\u0019\u001a\u00020\r2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\b\u0010\u001d\u001a\u00020\u0017H\u0002J\u0010\u0010\u001e\u001a\u00020\u00102\u0006\u0010\u0019\u001a\u00020\rH\u0002J\u001e\u0010\u001f\u001a\u00020\u00102\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#2\u0006\u0010\u001b\u001a\u00020\u001cJ\u000e\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020\rR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u001b\u0010\u0006\u001a\u00020\u00078BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\tR\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00100\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\u0011\u001a\u00020\u00128BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0015\u0010\u000b\u001a\u0004\b\u0013\u0010\u0014R\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u0017X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006'"}, d2 = {"Lcom/catchpig/download/manager/DownloadManager$Companion;", "", "()V", "CONNECT_TIMEOUT", "", "READ_TIMEOUT", "downloadInterceptor", "Lcom/catchpig/download/interceptor/DownloadInterceptor;", "getDownloadInterceptor", "()Lcom/catchpig/download/interceptor/DownloadInterceptor;", "downloadInterceptor$delegate", "Lkotlin/Lazy;", "downloadPath", "", "downloadServiceMap", "", "Lcom/catchpig/download/api/DownloadService;", "logInterceptor", "Lokhttp3/logging/HttpLoggingInterceptor;", "getLogInterceptor", "()Lokhttp3/logging/HttpLoggingInterceptor;", "logInterceptor$delegate", "okHttpClient", "Lokhttp3/OkHttpClient;", "getCoroutinesDownloadService", "baseUrl", "getDownloadService", "rxJava", "", "getOkHttpClient", "getRxjavaDownloadService", "initDownloadService", "url", "Ljava/net/URL;", "downloadProgressListener", "Lcom/catchpig/download/callback/DownloadProgressListener;", "setDownloadPath", "", "path", "download_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private final DownloadService getCoroutinesDownloadService(String baseUrl) throws SecurityException {
            Object objCreate = new Retrofit.Builder().baseUrl(baseUrl).client(getOkHttpClient()).addConverterFactory(GsonConverterFactory.create()).build().create(DownloadService.class);
            Intrinsics.checkNotNullExpressionValue(objCreate, "Builder()\n              …nloadService::class.java)");
            return (DownloadService) objCreate;
        }

        private final DownloadInterceptor getDownloadInterceptor() {
            return (DownloadInterceptor) DownloadManager.downloadInterceptor$delegate.getValue();
        }

        private final DownloadService getDownloadService(String baseUrl, boolean rxJava) {
            return rxJava ? getRxjavaDownloadService(baseUrl) : getCoroutinesDownloadService(baseUrl);
        }

        private final HttpLoggingInterceptor getLogInterceptor() {
            return (HttpLoggingInterceptor) DownloadManager.logInterceptor$delegate.getValue();
        }

        private final OkHttpClient getOkHttpClient() {
            OkHttpClient unused = DownloadManager.okHttpClient;
            OkHttpClient.Builder timeout = new OkHttpClient.Builder().connectTimeout(5L, TimeUnit.SECONDS).readTimeout(DownloadManager.READ_TIMEOUT, TimeUnit.MINUTES);
            Companion companion = DownloadManager.INSTANCE;
            return timeout.addInterceptor(companion.getLogInterceptor()).addInterceptor(companion.getDownloadInterceptor()).build();
        }

        private final DownloadService getRxjavaDownloadService(String baseUrl) throws SecurityException {
            Object objCreate = new Retrofit.Builder().baseUrl(baseUrl).addCallAdapterFactory(RxJava3CallAdapterFactory.create()).client(getOkHttpClient()).addConverterFactory(GsonConverterFactory.create()).build().create(DownloadService.class);
            Intrinsics.checkNotNullExpressionValue(objCreate, "Builder()\n              …nloadService::class.java)");
            return (DownloadService) objCreate;
        }

        @NotNull
        public final DownloadService initDownloadService(@NotNull URL url, @NotNull DownloadProgressListener downloadProgressListener, boolean rxJava) {
            Intrinsics.checkNotNullParameter(url, "url");
            Intrinsics.checkNotNullParameter(downloadProgressListener, "downloadProgressListener");
            String str = url.getProtocol() + "://" + url.getHost() + "/";
            DownloadService downloadService = DownloadManager.INSTANCE.getDownloadService(str, rxJava);
            DownloadManager.downloadServiceMap.put(str, downloadService);
            DownloadInterceptor downloadInterceptor = getDownloadInterceptor();
            String string = url.toString();
            Intrinsics.checkNotNullExpressionValue(string, "url.toString()");
            downloadInterceptor.addProgressListener(string, downloadProgressListener);
            return downloadService;
        }

        public final void setDownloadPath(@NotNull String path) {
            Intrinsics.checkNotNullParameter(path, "path");
            DownloadManager.downloadPath = path;
        }
    }

    private final String getDownloadFilePath() {
        String absolutePath;
        String str = downloadPath;
        if (str != null) {
            return str;
        }
        if (Intrinsics.areEqual("mounted", Environment.getExternalStorageState())) {
            File externalCacheDir = ContextManager.INSTANCE.getInstance().getContext().getExternalCacheDir();
            Intrinsics.checkNotNull(externalCacheDir);
            absolutePath = externalCacheDir.getAbsolutePath();
        } else {
            absolutePath = ContextManager.INSTANCE.getInstance().getContext().getCacheDir().getAbsolutePath();
        }
        return absolutePath + "/download";
    }

    public final void clearFiles() {
        FileExtKt.deleteAll(new File(getDownloadFilePath()));
    }

    @NotNull
    public final String localFileName(@NotNull String downloadUrl) {
        Intrinsics.checkNotNullParameter(downloadUrl, "downloadUrl");
        String strReplace$default = StringsKt__StringsJVMKt.replace$default(StringsKt__StringsJVMKt.replace$default(downloadUrl, "/", "", false, 4, (Object) null), StrPool.BACKSLASH, "", false, 4, (Object) null);
        return getDownloadFilePath() + "/" + strReplace$default;
    }

    @NotNull
    public final String writeCache(@NotNull ResponseBody responseBody, @NotNull String fileName) throws IOException {
        Intrinsics.checkNotNullParameter(responseBody, "responseBody");
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        File file = new File(fileName);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        } else if (file.exists()) {
            if (file.length() == responseBody.getContentLength()) {
                return fileName;
            }
            file.delete();
        }
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rwd");
        FileChannel channel = randomAccessFile.getChannel();
        MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0L, responseBody.getContentLength());
        byte[] bArr = new byte[8192];
        while (true) {
            int i2 = responseBody.byteStream().read(bArr);
            if (i2 == -1) {
                responseBody.byteStream().close();
                channel.close();
                randomAccessFile.close();
                return fileName;
            }
            map.put(bArr, 0, i2);
        }
    }
}
