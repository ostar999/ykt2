package com.airbnb.lottie.network;

import android.util.Pair;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieCompositionFactory;
import com.airbnb.lottie.LottieResult;
import com.airbnb.lottie.utils.Logger;
import com.psychiatrygarden.utils.MimeTypes;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipInputStream;

/* loaded from: classes2.dex */
public class NetworkFetcher {

    @NonNull
    private final LottieNetworkFetcher fetcher;

    @NonNull
    private final NetworkCache networkCache;

    public NetworkFetcher(@NonNull NetworkCache networkCache, @NonNull LottieNetworkFetcher lottieNetworkFetcher) {
        this.networkCache = networkCache;
        this.fetcher = lottieNetworkFetcher;
    }

    @Nullable
    @WorkerThread
    private LottieComposition fetchFromCache(@NonNull String str, @Nullable String str2) {
        Pair<FileExtension, InputStream> pairFetch;
        if (str2 == null || (pairFetch = this.networkCache.fetch(str)) == null) {
            return null;
        }
        FileExtension fileExtension = (FileExtension) pairFetch.first;
        InputStream inputStream = (InputStream) pairFetch.second;
        LottieResult<LottieComposition> lottieResultFromZipStreamSync = fileExtension == FileExtension.ZIP ? LottieCompositionFactory.fromZipStreamSync(new ZipInputStream(inputStream), str) : LottieCompositionFactory.fromJsonInputStreamSync(inputStream, str);
        if (lottieResultFromZipStreamSync.getValue() != null) {
            return lottieResultFromZipStreamSync.getValue();
        }
        return null;
    }

    @NonNull
    @WorkerThread
    private LottieResult<LottieComposition> fetchFromNetwork(@NonNull String str, @Nullable String str2) throws IOException {
        Logger.debug("Fetching " + str);
        Closeable closeable = null;
        try {
            try {
                LottieFetchResult lottieFetchResultFetchSync = this.fetcher.fetchSync(str);
                if (!lottieFetchResultFetchSync.isSuccessful()) {
                    LottieResult<LottieComposition> lottieResult = new LottieResult<>(new IllegalArgumentException(lottieFetchResultFetchSync.error()));
                    try {
                        lottieFetchResultFetchSync.close();
                    } catch (IOException e2) {
                        Logger.warning("LottieFetchResult close failed ", e2);
                    }
                    return lottieResult;
                }
                LottieResult<LottieComposition> lottieResultFromInputStream = fromInputStream(str, lottieFetchResultFetchSync.bodyByteStream(), lottieFetchResultFetchSync.contentType(), str2);
                StringBuilder sb = new StringBuilder();
                sb.append("Completed fetch from network. Success: ");
                sb.append(lottieResultFromInputStream.getValue() != null);
                Logger.debug(sb.toString());
                try {
                    lottieFetchResultFetchSync.close();
                } catch (IOException e3) {
                    Logger.warning("LottieFetchResult close failed ", e3);
                }
                return lottieResultFromInputStream;
            } catch (Exception e4) {
                LottieResult<LottieComposition> lottieResult2 = new LottieResult<>(e4);
                if (0 != 0) {
                    try {
                        closeable.close();
                    } catch (IOException e5) {
                        Logger.warning("LottieFetchResult close failed ", e5);
                    }
                }
                return lottieResult2;
            }
        } catch (Throwable th) {
            if (0 != 0) {
                try {
                    closeable.close();
                } catch (IOException e6) {
                    Logger.warning("LottieFetchResult close failed ", e6);
                }
            }
            throw th;
        }
    }

    @NonNull
    private LottieResult<LottieComposition> fromInputStream(@NonNull String str, @NonNull InputStream inputStream, @Nullable String str2, @Nullable String str3) throws IOException {
        FileExtension fileExtension;
        LottieResult<LottieComposition> lottieResultFromZipStream;
        if (str2 == null) {
            str2 = MimeTypes.APP_JSON;
        }
        if (str2.contains(MimeTypes.APP_ZIP) || str.split("\\?")[0].endsWith(".lottie")) {
            Logger.debug("Handling zip response.");
            fileExtension = FileExtension.ZIP;
            lottieResultFromZipStream = fromZipStream(str, inputStream, str3);
        } else {
            Logger.debug("Received json response.");
            fileExtension = FileExtension.JSON;
            lottieResultFromZipStream = fromJsonStream(str, inputStream, str3);
        }
        if (str3 != null && lottieResultFromZipStream.getValue() != null) {
            this.networkCache.renameTempFile(str, fileExtension);
        }
        return lottieResultFromZipStream;
    }

    @NonNull
    private LottieResult<LottieComposition> fromJsonStream(@NonNull String str, @NonNull InputStream inputStream, @Nullable String str2) throws IOException {
        return str2 == null ? LottieCompositionFactory.fromJsonInputStreamSync(inputStream, null) : LottieCompositionFactory.fromJsonInputStreamSync(new FileInputStream(new File(this.networkCache.writeTempCacheFile(str, inputStream, FileExtension.JSON).getAbsolutePath())), str);
    }

    @NonNull
    private LottieResult<LottieComposition> fromZipStream(@NonNull String str, @NonNull InputStream inputStream, @Nullable String str2) throws IOException {
        return str2 == null ? LottieCompositionFactory.fromZipStreamSync(new ZipInputStream(inputStream), null) : LottieCompositionFactory.fromZipStreamSync(new ZipInputStream(new FileInputStream(this.networkCache.writeTempCacheFile(str, inputStream, FileExtension.ZIP))), str);
    }

    @NonNull
    @WorkerThread
    public LottieResult<LottieComposition> fetchSync(@NonNull String str, @Nullable String str2) {
        LottieComposition lottieCompositionFetchFromCache = fetchFromCache(str, str2);
        if (lottieCompositionFetchFromCache != null) {
            return new LottieResult<>(lottieCompositionFetchFromCache);
        }
        Logger.debug("Animation for " + str + " not found in cache. Fetching from network.");
        return fetchFromNetwork(str, str2);
    }
}
