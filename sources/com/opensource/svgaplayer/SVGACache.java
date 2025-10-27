package com.opensource.svgaplayer;

import android.content.Context;
import com.arialyy.aria.core.inf.IOptionConstant;
import com.luck.picture.lib.config.PictureMimeType;
import com.opensource.svgaplayer.utils.log.LogUtils;
import com.umeng.analytics.pro.d;
import java.io.File;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(bv = {1, 0, 3}, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001 B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0004J\u000e\u0010\r\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u0004J\u000e\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u0011J\u000e\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u0004J\u000e\u0010\u0013\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u0004J\u0006\u0010\u0014\u001a\u00020\u0015J\u0015\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u0004H\u0000¢\u0006\u0002\b\u0018J\u000e\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u000e\u001a\u00020\u0004J\u0006\u0010\u001b\u001a\u00020\u001aJ\u0006\u0010\u001c\u001a\u00020\u001aJ\u0010\u0010\u001d\u001a\u00020\u00152\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fJ\u0018\u0010\u001d\u001a\u00020\u00152\b\u0010\u001e\u001a\u0004\u0018\u00010\u001f2\u0006\u0010\b\u001a\u00020\tR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\u00020\u00048BX\u0082\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006!"}, d2 = {"Lcom/opensource/svgaplayer/SVGACache;", "", "()V", "TAG", "", IOptionConstant.cacheDir, "getCacheDir", "()Ljava/lang/String;", "type", "Lcom/opensource/svgaplayer/SVGACache$Type;", "buildAudioFile", "Ljava/io/File;", "audio", "buildCacheDir", "cacheKey", "buildCacheKey", "url", "Ljava/net/URL;", "str", "buildSvgaFile", "clearCache", "", "clearDir", "path", "clearDir$com_opensource_svgaplayer", "isCached", "", "isDefaultCache", "isInitialized", "onCreate", d.R, "Landroid/content/Context;", "Type", BuildConfig.LIBRARY_PACKAGE_NAME}, k = 1, mv = {1, 1, 15})
/* loaded from: classes4.dex */
public final class SVGACache {
    private static final String TAG = "SVGACache";
    public static final SVGACache INSTANCE = new SVGACache();
    private static Type type = Type.DEFAULT;
    private static String cacheDir = "/";

    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004¨\u0006\u0005"}, d2 = {"Lcom/opensource/svgaplayer/SVGACache$Type;", "", "(Ljava/lang/String;I)V", "DEFAULT", "FILE", BuildConfig.LIBRARY_PACKAGE_NAME}, k = 1, mv = {1, 1, 15})
    public enum Type {
        DEFAULT,
        FILE
    }

    private SVGACache() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String getCacheDir() {
        if (!Intrinsics.areEqual(cacheDir, "/")) {
            File file = new File(cacheDir);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
        return cacheDir;
    }

    @NotNull
    public final File buildAudioFile(@NotNull String audio) {
        Intrinsics.checkParameterIsNotNull(audio, "audio");
        return new File(getCacheDir() + audio + PictureMimeType.MP3);
    }

    @NotNull
    public final File buildCacheDir(@NotNull String cacheKey) {
        Intrinsics.checkParameterIsNotNull(cacheKey, "cacheKey");
        return new File(getCacheDir() + cacheKey + '/');
    }

    @NotNull
    public final String buildCacheKey(@NotNull String str) throws NoSuchAlgorithmException {
        Intrinsics.checkParameterIsNotNull(str, "str");
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        Charset charsetForName = Charset.forName("UTF-8");
        Intrinsics.checkExpressionValueIsNotNull(charsetForName, "Charset.forName(charsetName)");
        byte[] bytes = str.getBytes(charsetForName);
        Intrinsics.checkExpressionValueIsNotNull(bytes, "(this as java.lang.String).getBytes(charset)");
        messageDigest.update(bytes);
        String string = "";
        for (byte b3 : messageDigest.digest()) {
            StringBuilder sb = new StringBuilder();
            sb.append(string);
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            String str2 = String.format("%02x", Arrays.copyOf(new Object[]{Byte.valueOf(b3)}, 1));
            Intrinsics.checkExpressionValueIsNotNull(str2, "java.lang.String.format(format, *args)");
            sb.append(str2);
            string = sb.toString();
        }
        return string;
    }

    @NotNull
    public final File buildSvgaFile(@NotNull String cacheKey) {
        Intrinsics.checkParameterIsNotNull(cacheKey, "cacheKey");
        return new File(getCacheDir() + cacheKey + ".svga");
    }

    public final void clearCache() {
        if (isInitialized()) {
            SVGAParser.INSTANCE.getThreadPoolExecutor$com_opensource_svgaplayer().execute(new Runnable() { // from class: com.opensource.svgaplayer.SVGACache.clearCache.1
                @Override // java.lang.Runnable
                public final void run() {
                    SVGACache sVGACache = SVGACache.INSTANCE;
                    sVGACache.clearDir$com_opensource_svgaplayer(sVGACache.getCacheDir());
                    LogUtils.INSTANCE.info(SVGACache.TAG, "Clear svga cache done!");
                }
            });
        } else {
            LogUtils.INSTANCE.error(TAG, "SVGACache is not init!");
        }
    }

    public final void clearDir$com_opensource_svgaplayer(@NotNull String path) {
        File[] fileArrListFiles;
        Intrinsics.checkParameterIsNotNull(path, "path");
        try {
            File file = new File(path);
            if (!file.exists()) {
                file = null;
            }
            if (file == null || (fileArrListFiles = file.listFiles()) == null) {
                return;
            }
            for (File file2 : fileArrListFiles) {
                if (file2.exists()) {
                    Intrinsics.checkExpressionValueIsNotNull(file2, "file");
                    if (file2.isDirectory()) {
                        SVGACache sVGACache = INSTANCE;
                        String absolutePath = file2.getAbsolutePath();
                        Intrinsics.checkExpressionValueIsNotNull(absolutePath, "file.absolutePath");
                        sVGACache.clearDir$com_opensource_svgaplayer(absolutePath);
                    }
                    file2.delete();
                }
            }
        } catch (Exception e2) {
            LogUtils.INSTANCE.error(TAG, "Clear svga cache path: " + path + " fail", e2);
        }
    }

    public final boolean isCached(@NotNull String cacheKey) {
        Intrinsics.checkParameterIsNotNull(cacheKey, "cacheKey");
        return (isDefaultCache() ? buildCacheDir(cacheKey) : buildSvgaFile(cacheKey)).exists();
    }

    public final boolean isDefaultCache() {
        return type == Type.DEFAULT;
    }

    public final boolean isInitialized() {
        return (Intrinsics.areEqual("/", getCacheDir()) ^ true) && new File(getCacheDir()).exists();
    }

    public final void onCreate(@Nullable Context context) {
        onCreate(context, Type.DEFAULT);
    }

    public final void onCreate(@Nullable Context context, @NotNull Type type2) {
        Intrinsics.checkParameterIsNotNull(type2, "type");
        if (isInitialized() || context == null) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        File cacheDir2 = context.getCacheDir();
        Intrinsics.checkExpressionValueIsNotNull(cacheDir2, "context.cacheDir");
        sb.append(cacheDir2.getAbsolutePath());
        sb.append("/svga/");
        cacheDir = sb.toString();
        File file = new File(getCacheDir());
        if (!(!file.exists())) {
            file = null;
        }
        if (file != null) {
            file.mkdirs();
        }
        type = type2;
    }

    @NotNull
    public final String buildCacheKey(@NotNull URL url) {
        Intrinsics.checkParameterIsNotNull(url, "url");
        String string = url.toString();
        Intrinsics.checkExpressionValueIsNotNull(string, "url.toString()");
        return buildCacheKey(string);
    }
}
