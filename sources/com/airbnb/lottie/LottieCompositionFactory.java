package com.airbnb.lottie;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;
import androidx.annotation.WorkerThread;
import com.airbnb.lottie.model.LottieCompositionCache;
import com.airbnb.lottie.parser.LottieCompositionMoshiParser;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.utils.Logger;
import com.airbnb.lottie.utils.Utils;
import com.luck.picture.lib.config.PictureMimeType;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import okio.BufferedSource;
import okio.Okio;
import org.apache.commons.compress.archivers.tar.TarConstants;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class LottieCompositionFactory {
    private static final Map<String, LottieTask<LottieComposition>> taskCache = new HashMap();
    private static final byte[] MAGIC = {80, TarConstants.LF_GNUTYPE_LONGLINK, 3, 4};

    private LottieCompositionFactory() {
    }

    private static LottieTask<LottieComposition> cache(@Nullable final String str, Callable<LottieResult<LottieComposition>> callable) {
        final LottieComposition lottieComposition = str == null ? null : LottieCompositionCache.getInstance().get(str);
        if (lottieComposition != null) {
            return new LottieTask<>(new Callable<LottieResult<LottieComposition>>() { // from class: com.airbnb.lottie.LottieCompositionFactory.9
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // java.util.concurrent.Callable
                public LottieResult<LottieComposition> call() {
                    return new LottieResult<>(lottieComposition);
                }
            });
        }
        if (str != null) {
            Map<String, LottieTask<LottieComposition>> map = taskCache;
            if (map.containsKey(str)) {
                return map.get(str);
            }
        }
        LottieTask<LottieComposition> lottieTask = new LottieTask<>(callable);
        if (str != null) {
            lottieTask.addListener(new LottieListener<LottieComposition>() { // from class: com.airbnb.lottie.LottieCompositionFactory.10
                @Override // com.airbnb.lottie.LottieListener
                public void onResult(LottieComposition lottieComposition2) {
                    LottieCompositionFactory.taskCache.remove(str);
                }
            });
            lottieTask.addFailureListener(new LottieListener<Throwable>() { // from class: com.airbnb.lottie.LottieCompositionFactory.11
                @Override // com.airbnb.lottie.LottieListener
                public void onResult(Throwable th) {
                    LottieCompositionFactory.taskCache.remove(str);
                }
            });
            taskCache.put(str, lottieTask);
        }
        return lottieTask;
    }

    public static void clearCache(Context context) {
        taskCache.clear();
        LottieCompositionCache.getInstance().clear();
        L.networkCache(context).clear();
    }

    @Nullable
    private static LottieImageAsset findImageAssetForFileName(LottieComposition lottieComposition, String str) {
        for (LottieImageAsset lottieImageAsset : lottieComposition.getImages().values()) {
            if (lottieImageAsset.getFileName().equals(str)) {
                return lottieImageAsset;
            }
        }
        return null;
    }

    public static LottieTask<LottieComposition> fromAsset(Context context, String str) {
        return fromAsset(context, str, "asset_" + str);
    }

    @WorkerThread
    public static LottieResult<LottieComposition> fromAssetSync(Context context, String str) {
        return fromAssetSync(context, str, "asset_" + str);
    }

    @Deprecated
    public static LottieTask<LottieComposition> fromJson(final JSONObject jSONObject, @Nullable final String str) {
        return cache(str, new Callable<LottieResult<LottieComposition>>() { // from class: com.airbnb.lottie.LottieCompositionFactory.5
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public LottieResult<LottieComposition> call() {
                return LottieCompositionFactory.fromJsonSync(jSONObject, str);
            }
        });
    }

    public static LottieTask<LottieComposition> fromJsonInputStream(final InputStream inputStream, @Nullable final String str) {
        return cache(str, new Callable<LottieResult<LottieComposition>>() { // from class: com.airbnb.lottie.LottieCompositionFactory.4
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public LottieResult<LottieComposition> call() {
                return LottieCompositionFactory.fromJsonInputStreamSync(inputStream, str);
            }
        });
    }

    @WorkerThread
    public static LottieResult<LottieComposition> fromJsonInputStreamSync(InputStream inputStream, @Nullable String str) {
        return fromJsonInputStreamSync(inputStream, str, true);
    }

    public static LottieTask<LottieComposition> fromJsonReader(final JsonReader jsonReader, @Nullable final String str) {
        return cache(str, new Callable<LottieResult<LottieComposition>>() { // from class: com.airbnb.lottie.LottieCompositionFactory.7
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public LottieResult<LottieComposition> call() {
                return LottieCompositionFactory.fromJsonReaderSync(jsonReader, str);
            }
        });
    }

    @WorkerThread
    public static LottieResult<LottieComposition> fromJsonReaderSync(JsonReader jsonReader, @Nullable String str) {
        return fromJsonReaderSyncInternal(jsonReader, str, true);
    }

    private static LottieResult<LottieComposition> fromJsonReaderSyncInternal(JsonReader jsonReader, @Nullable String str, boolean z2) throws IOException {
        try {
            try {
                LottieComposition lottieComposition = LottieCompositionMoshiParser.parse(jsonReader);
                if (str != null) {
                    LottieCompositionCache.getInstance().put(str, lottieComposition);
                }
                LottieResult<LottieComposition> lottieResult = new LottieResult<>(lottieComposition);
                if (z2) {
                    Utils.closeQuietly(jsonReader);
                }
                return lottieResult;
            } catch (Exception e2) {
                LottieResult<LottieComposition> lottieResult2 = new LottieResult<>(e2);
                if (z2) {
                    Utils.closeQuietly(jsonReader);
                }
                return lottieResult2;
            }
        } catch (Throwable th) {
            if (z2) {
                Utils.closeQuietly(jsonReader);
            }
            throw th;
        }
    }

    public static LottieTask<LottieComposition> fromJsonString(final String str, @Nullable final String str2) {
        return cache(str2, new Callable<LottieResult<LottieComposition>>() { // from class: com.airbnb.lottie.LottieCompositionFactory.6
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public LottieResult<LottieComposition> call() {
                return LottieCompositionFactory.fromJsonStringSync(str, str2);
            }
        });
    }

    @WorkerThread
    public static LottieResult<LottieComposition> fromJsonStringSync(String str, @Nullable String str2) {
        return fromJsonReaderSync(JsonReader.of(Okio.buffer(Okio.source(new ByteArrayInputStream(str.getBytes())))), str2);
    }

    @WorkerThread
    @Deprecated
    public static LottieResult<LottieComposition> fromJsonSync(JSONObject jSONObject, @Nullable String str) {
        return fromJsonStringSync(jSONObject.toString(), str);
    }

    public static LottieTask<LottieComposition> fromRawRes(Context context, @RawRes int i2) {
        return fromRawRes(context, i2, rawResCacheKey(context, i2));
    }

    @WorkerThread
    public static LottieResult<LottieComposition> fromRawResSync(Context context, @RawRes int i2) {
        return fromRawResSync(context, i2, rawResCacheKey(context, i2));
    }

    public static LottieTask<LottieComposition> fromUrl(Context context, String str) {
        return fromUrl(context, str, "url_" + str);
    }

    @WorkerThread
    public static LottieResult<LottieComposition> fromUrlSync(Context context, String str) {
        return fromUrlSync(context, str, str);
    }

    public static LottieTask<LottieComposition> fromZipStream(final ZipInputStream zipInputStream, @Nullable final String str) {
        return cache(str, new Callable<LottieResult<LottieComposition>>() { // from class: com.airbnb.lottie.LottieCompositionFactory.8
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public LottieResult<LottieComposition> call() {
                return LottieCompositionFactory.fromZipStreamSync(zipInputStream, str);
            }
        });
    }

    @WorkerThread
    public static LottieResult<LottieComposition> fromZipStreamSync(ZipInputStream zipInputStream, @Nullable String str) throws IOException {
        try {
            return fromZipStreamSyncInternal(zipInputStream, str);
        } finally {
            Utils.closeQuietly(zipInputStream);
        }
    }

    @WorkerThread
    private static LottieResult<LottieComposition> fromZipStreamSyncInternal(ZipInputStream zipInputStream, @Nullable String str) throws IOException {
        HashMap map = new HashMap();
        try {
            ZipEntry nextEntry = zipInputStream.getNextEntry();
            LottieComposition value = null;
            while (nextEntry != null) {
                String name = nextEntry.getName();
                if (name.contains("__MACOSX")) {
                    zipInputStream.closeEntry();
                } else if (nextEntry.getName().equalsIgnoreCase("manifest.json")) {
                    zipInputStream.closeEntry();
                } else if (nextEntry.getName().contains(".json")) {
                    value = fromJsonReaderSyncInternal(JsonReader.of(Okio.buffer(Okio.source(zipInputStream))), null, false).getValue();
                } else if (name.contains(PictureMimeType.PNG) || name.contains(PictureMimeType.WEBP)) {
                    map.put(name.split("/")[r1.length - 1], BitmapFactory.decodeStream(zipInputStream));
                } else {
                    zipInputStream.closeEntry();
                }
                nextEntry = zipInputStream.getNextEntry();
            }
            if (value == null) {
                return new LottieResult<>((Throwable) new IllegalArgumentException("Unable to parse composition"));
            }
            for (Map.Entry entry : map.entrySet()) {
                LottieImageAsset lottieImageAssetFindImageAssetForFileName = findImageAssetForFileName(value, (String) entry.getKey());
                if (lottieImageAssetFindImageAssetForFileName != null) {
                    lottieImageAssetFindImageAssetForFileName.setBitmap(Utils.resizeBitmapIfNeeded((Bitmap) entry.getValue(), lottieImageAssetFindImageAssetForFileName.getWidth(), lottieImageAssetFindImageAssetForFileName.getHeight()));
                }
            }
            for (Map.Entry<String, LottieImageAsset> entry2 : value.getImages().entrySet()) {
                if (entry2.getValue().getBitmap() == null) {
                    return new LottieResult<>((Throwable) new IllegalStateException("There is no image for " + entry2.getValue().getFileName()));
                }
            }
            if (str != null) {
                LottieCompositionCache.getInstance().put(str, value);
            }
            return new LottieResult<>(value);
        } catch (IOException e2) {
            return new LottieResult<>((Throwable) e2);
        }
    }

    private static boolean isNightMode(Context context) {
        return (context.getResources().getConfiguration().uiMode & 48) == 32;
    }

    private static Boolean isZipCompressed(BufferedSource bufferedSource) {
        try {
            BufferedSource bufferedSourcePeek = bufferedSource.peek();
            for (byte b3 : MAGIC) {
                if (bufferedSourcePeek.readByte() != b3) {
                    return Boolean.FALSE;
                }
            }
            bufferedSourcePeek.close();
            return Boolean.TRUE;
        } catch (Exception e2) {
            Logger.error("Failed to check zip file header", e2);
            return Boolean.FALSE;
        }
    }

    private static String rawResCacheKey(Context context, @RawRes int i2) {
        StringBuilder sb = new StringBuilder();
        sb.append("rawRes");
        sb.append(isNightMode(context) ? "_night_" : "_day_");
        sb.append(i2);
        return sb.toString();
    }

    public static void setMaxCacheSize(int i2) {
        LottieCompositionCache.getInstance().resize(i2);
    }

    @WorkerThread
    private static LottieResult<LottieComposition> fromJsonInputStreamSync(InputStream inputStream, @Nullable String str, boolean z2) throws IOException {
        try {
            return fromJsonReaderSync(JsonReader.of(Okio.buffer(Okio.source(inputStream))), str);
        } finally {
            if (z2) {
                Utils.closeQuietly(inputStream);
            }
        }
    }

    public static LottieTask<LottieComposition> fromRawRes(Context context, @RawRes final int i2, @Nullable final String str) {
        final WeakReference weakReference = new WeakReference(context);
        final Context applicationContext = context.getApplicationContext();
        return cache(str, new Callable<LottieResult<LottieComposition>>() { // from class: com.airbnb.lottie.LottieCompositionFactory.3
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public LottieResult<LottieComposition> call() {
                Context context2 = (Context) weakReference.get();
                if (context2 == null) {
                    context2 = applicationContext;
                }
                return LottieCompositionFactory.fromRawResSync(context2, i2, str);
            }
        });
    }

    @WorkerThread
    public static LottieResult<LottieComposition> fromRawResSync(Context context, @RawRes int i2, @Nullable String str) {
        try {
            BufferedSource bufferedSourceBuffer = Okio.buffer(Okio.source(context.getResources().openRawResource(i2)));
            return isZipCompressed(bufferedSourceBuffer).booleanValue() ? fromZipStreamSync(new ZipInputStream(bufferedSourceBuffer.inputStream()), str) : fromJsonInputStreamSync(bufferedSourceBuffer.inputStream(), str);
        } catch (Resources.NotFoundException e2) {
            return new LottieResult<>((Throwable) e2);
        }
    }

    public static LottieTask<LottieComposition> fromUrl(final Context context, final String str, @Nullable final String str2) {
        return cache(str2, new Callable<LottieResult<LottieComposition>>() { // from class: com.airbnb.lottie.LottieCompositionFactory.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public LottieResult<LottieComposition> call() {
                LottieResult<LottieComposition> lottieResultFetchSync = L.networkFetcher(context).fetchSync(str, str2);
                if (str2 != null && lottieResultFetchSync.getValue() != null) {
                    LottieCompositionCache.getInstance().put(str2, lottieResultFetchSync.getValue());
                }
                return lottieResultFetchSync;
            }
        });
    }

    @WorkerThread
    public static LottieResult<LottieComposition> fromUrlSync(Context context, String str, @Nullable String str2) {
        LottieResult<LottieComposition> lottieResultFetchSync = L.networkFetcher(context).fetchSync(str, str2);
        if (str2 != null && lottieResultFetchSync.getValue() != null) {
            LottieCompositionCache.getInstance().put(str2, lottieResultFetchSync.getValue());
        }
        return lottieResultFetchSync;
    }

    public static LottieTask<LottieComposition> fromAsset(Context context, final String str, @Nullable final String str2) {
        final Context applicationContext = context.getApplicationContext();
        return cache(str2, new Callable<LottieResult<LottieComposition>>() { // from class: com.airbnb.lottie.LottieCompositionFactory.2
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public LottieResult<LottieComposition> call() {
                return LottieCompositionFactory.fromAssetSync(applicationContext, str, str2);
            }
        });
    }

    @WorkerThread
    public static LottieResult<LottieComposition> fromAssetSync(Context context, String str, @Nullable String str2) {
        try {
            if (!str.endsWith(".zip") && !str.endsWith(".lottie")) {
                return fromJsonInputStreamSync(context.getAssets().open(str), str2);
            }
            return fromZipStreamSync(new ZipInputStream(context.getAssets().open(str)), str2);
        } catch (IOException e2) {
            return new LottieResult<>((Throwable) e2);
        }
    }
}
