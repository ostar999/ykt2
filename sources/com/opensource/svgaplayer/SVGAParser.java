package com.opensource.svgaplayer;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.http.HttpResponseCache;
import android.os.Handler;
import android.os.Looper;
import com.alipay.sdk.authjs.a;
import com.aliyun.vod.log.struct.AliyunLogKey;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.proto.MovieEntity;
import com.opensource.svgaplayer.utils.log.LogUtils;
import com.umeng.analytics.pro.d;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.Inflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlin.text.StringsKt__StringsKt;
import org.eclipse.jetty.http.HttpHeaderValues;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

@Metadata(bv = {1, 0, 3}, d1 = {"\u0000|\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\u0018\u0000 <2\u00020\u0001:\u0004<=>?B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J$\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u0016J$\u0010\u0017\u001a\u00020\u00102\u0006\u0010\u0018\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\b\u0010\u0019\u001a\u0004\u0018\u00010\u0012H\u0002JB\u0010\u001a\u001a\u00020\u00102\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0018\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\b\b\u0002\u0010\u001d\u001a\u00020\u001e2\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u00162\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u0012J.\u0010\u001f\u001a\u00020\u00102\u0006\u0010\u0018\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u00162\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u0012J,\u0010 \u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010!2\u0006\u0010\"\u001a\u00020#2\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u0016J\u0018\u0010$\u001a\u00020\u00102\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020\u0012H\u0002J\u0012\u0010(\u001a\u0004\u0018\u00010)2\u0006\u0010*\u001a\u00020)H\u0002J\u000e\u0010+\u001a\u00020\u00102\u0006\u0010\u0002\u001a\u00020\u0003J$\u0010,\u001a\u00020\u00102\u0006\u0010-\u001a\u00020.2\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\b\u0010\u0019\u001a\u0004\u0018\u00010\u0012H\u0002J(\u0010/\u001a\u00020\u00102\n\u00100\u001a\u000601j\u0002`22\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\b\u0010\u0019\u001a\u0004\u0018\u00010\u0012H\u0002J\u0010\u00103\u001a\u00020\u001e2\u0006\u00104\u001a\u00020)H\u0002J,\u00105\u001a\u00020\u00102\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0018\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\b\b\u0002\u0010\u001d\u001a\u00020\u001eH\u0007J\u001a\u00105\u001a\u00020\u00102\u0006\u0010\"\u001a\u00020#2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0007J\u001a\u00105\u001a\u00020\u00102\u0006\u00106\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0007J\u0012\u00107\u001a\u0004\u0018\u00010)2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\u0016\u00108\u001a\u00020\u00102\u0006\u00109\u001a\u00020\r2\u0006\u0010:\u001a\u00020\rJ\u0018\u0010;\u001a\u00020\u00102\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0018\u001a\u00020\u0012H\u0002R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u0010\u0010\u000b\u001a\u0004\u0018\u00010\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006@"}, d2 = {"Lcom/opensource/svgaplayer/SVGAParser;", "", d.R, "Landroid/content/Context;", "(Landroid/content/Context;)V", "fileDownloader", "Lcom/opensource/svgaplayer/SVGAParser$FileDownloader;", "getFileDownloader", "()Lcom/opensource/svgaplayer/SVGAParser$FileDownloader;", "setFileDownloader", "(Lcom/opensource/svgaplayer/SVGAParser$FileDownloader;)V", "mContext", "mFrameHeight", "", "mFrameWidth", "decodeFromAssets", "", "name", "", a.f3170c, "Lcom/opensource/svgaplayer/SVGAParser$ParseCompletion;", "playCallback", "Lcom/opensource/svgaplayer/SVGAParser$PlayCallback;", "decodeFromCacheKey", "cacheKey", "alias", "decodeFromInputStream", "inputStream", "Ljava/io/InputStream;", "closeInputStream", "", "decodeFromSVGAFileCacheKey", "decodeFromURL", "Lkotlin/Function0;", "url", "Ljava/net/URL;", "ensureUnzipSafety", "outputFile", "Ljava/io/File;", "dstDirPath", "inflate", "", "byteArray", "init", "invokeCompleteCallback", "videoItem", "Lcom/opensource/svgaplayer/SVGAVideoEntity;", "invokeErrorCallback", AliyunLogKey.KEY_EVENT, "Ljava/lang/Exception;", "Lkotlin/Exception;", "isZipFile", HttpHeaderValues.BYTES, "parse", "assetsName", "readAsBytes", "setFrameSize", "frameWidth", "frameHeight", "unzip", "Companion", "FileDownloader", "ParseCompletion", "PlayCallback", BuildConfig.LIBRARY_PACKAGE_NAME}, k = 1, mv = {1, 1, 15})
/* loaded from: classes4.dex */
public final class SVGAParser {
    private static final String TAG = "SVGAParser";

    @NotNull
    private FileDownloader fileDownloader;
    private Context mContext;
    private volatile int mFrameHeight;
    private volatile int mFrameWidth;

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final AtomicInteger threadNum = new AtomicInteger(0);
    private static SVGAParser mShareParser = new SVGAParser(null);
    private static ExecutorService threadPoolExecutor = Executors.newCachedThreadPool(new ThreadFactory() { // from class: com.opensource.svgaplayer.SVGAParser$Companion$threadPoolExecutor$1
        @Override // java.util.concurrent.ThreadFactory
        @NotNull
        public final Thread newThread(Runnable runnable) {
            return new Thread(runnable, "SVGAParser-Thread-" + SVGAParser.threadNum.getAndIncrement());
        }
    });

    @Metadata(bv = {1, 0, 3}, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013J\u0006\u0010\u0014\u001a\u00020\u0006R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\"\u0010\t\u001a\n \u000b*\u0004\u0018\u00010\n0\nX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000f¨\u0006\u0015"}, d2 = {"Lcom/opensource/svgaplayer/SVGAParser$Companion;", "", "()V", "TAG", "", "mShareParser", "Lcom/opensource/svgaplayer/SVGAParser;", "threadNum", "Ljava/util/concurrent/atomic/AtomicInteger;", "threadPoolExecutor", "Ljava/util/concurrent/ExecutorService;", "kotlin.jvm.PlatformType", "getThreadPoolExecutor$com_opensource_svgaplayer", "()Ljava/util/concurrent/ExecutorService;", "setThreadPoolExecutor$com_opensource_svgaplayer", "(Ljava/util/concurrent/ExecutorService;)V", "setThreadPoolExecutor", "", "executor", "Ljava/util/concurrent/ThreadPoolExecutor;", "shareParser", BuildConfig.LIBRARY_PACKAGE_NAME}, k = 1, mv = {1, 1, 15})
    public static final class Companion {
        private Companion() {
        }

        public final ExecutorService getThreadPoolExecutor$com_opensource_svgaplayer() {
            return SVGAParser.threadPoolExecutor;
        }

        public final void setThreadPoolExecutor(@NotNull ThreadPoolExecutor executor) {
            Intrinsics.checkParameterIsNotNull(executor, "executor");
            setThreadPoolExecutor$com_opensource_svgaplayer(executor);
        }

        public final void setThreadPoolExecutor$com_opensource_svgaplayer(ExecutorService executorService) {
            SVGAParser.threadPoolExecutor = executorService;
        }

        @NotNull
        public final SVGAParser shareParser() {
            return SVGAParser.mShareParser;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J`\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\f\u001a\u00020\r2!\u0010\u000e\u001a\u001d\u0012\u0013\u0012\u00110\u0010¢\u0006\f\b\u0011\u0012\b\b\u0012\u0012\u0004\b\b(\u0013\u0012\u0004\u0012\u00020\u000b0\u000f2%\u0010\u0014\u001a!\u0012\u0017\u0012\u00150\u0015j\u0002`\u0016¢\u0006\f\b\u0011\u0012\b\b\u0012\u0012\u0004\b\b(\u0017\u0012\u0004\u0012\u00020\u000b0\u000fH\u0016R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\u0018"}, d2 = {"Lcom/opensource/svgaplayer/SVGAParser$FileDownloader;", "", "()V", "noCache", "", "getNoCache", "()Z", "setNoCache", "(Z)V", "resume", "Lkotlin/Function0;", "", "url", "Ljava/net/URL;", "complete", "Lkotlin/Function1;", "Ljava/io/InputStream;", "Lkotlin/ParameterName;", "name", "inputStream", "failure", "Ljava/lang/Exception;", "Lkotlin/Exception;", AliyunLogKey.KEY_EVENT, BuildConfig.LIBRARY_PACKAGE_NAME}, k = 1, mv = {1, 1, 15})
    public static class FileDownloader {
        private boolean noCache;

        public final boolean getNoCache() {
            return this.noCache;
        }

        @NotNull
        public Function0<Unit> resume(@NotNull final URL url, @NotNull final Function1<? super InputStream, Unit> complete, @NotNull final Function1<? super Exception, Unit> failure) {
            Intrinsics.checkParameterIsNotNull(url, "url");
            Intrinsics.checkParameterIsNotNull(complete, "complete");
            Intrinsics.checkParameterIsNotNull(failure, "failure");
            final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
            booleanRef.element = false;
            Function0<Unit> function0 = new Function0<Unit>() { // from class: com.opensource.svgaplayer.SVGAParser$FileDownloader$resume$cancelBlock$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    booleanRef.element = true;
                }
            };
            SVGAParser.INSTANCE.getThreadPoolExecutor$com_opensource_svgaplayer().execute(new Runnable() { // from class: com.opensource.svgaplayer.SVGAParser$FileDownloader$resume$1
                @Override // java.lang.Runnable
                public final void run() throws IOException {
                    try {
                        LogUtils logUtils = LogUtils.INSTANCE;
                        logUtils.info("SVGAParser", "================ svga file download start ================");
                        if (HttpResponseCache.getInstalled() == null && !this.this$0.getNoCache()) {
                            logUtils.error("SVGAParser", "SVGAParser can not handle cache before install HttpResponseCache. see https://github.com/yyued/SVGAPlayer-Android#cache");
                            logUtils.error("SVGAParser", "在配置 HttpResponseCache 前 SVGAParser 无法缓存. 查看 https://github.com/yyued/SVGAPlayer-Android#cache ");
                        }
                        URLConnection uRLConnectionOpenConnection = url.openConnection();
                        if (!(uRLConnectionOpenConnection instanceof HttpURLConnection)) {
                            uRLConnectionOpenConnection = null;
                        }
                        HttpURLConnection httpURLConnection = (HttpURLConnection) uRLConnectionOpenConnection;
                        if (httpURLConnection == null) {
                            return;
                        }
                        httpURLConnection.setConnectTimeout(20000);
                        httpURLConnection.setRequestMethod("GET");
                        httpURLConnection.setRequestProperty("Connection", "close");
                        httpURLConnection.connect();
                        InputStream inputStream = httpURLConnection.getInputStream();
                        try {
                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            try {
                                byte[] bArr = new byte[4096];
                                while (true) {
                                    if (booleanRef.element) {
                                        LogUtils.INSTANCE.warn("SVGAParser", "================ svga file download canceled ================");
                                        break;
                                    }
                                    int i2 = inputStream.read(bArr, 0, 4096);
                                    if (i2 == -1) {
                                        break;
                                    } else {
                                        byteArrayOutputStream.write(bArr, 0, i2);
                                    }
                                }
                                if (booleanRef.element) {
                                    LogUtils.INSTANCE.warn("SVGAParser", "================ svga file download canceled ================");
                                    CloseableKt.closeFinally(byteArrayOutputStream, null);
                                    CloseableKt.closeFinally(inputStream, null);
                                    return;
                                }
                                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
                                try {
                                    LogUtils.INSTANCE.info("SVGAParser", "================ svga file download complete ================");
                                    complete.invoke(byteArrayInputStream);
                                    Unit unit = Unit.INSTANCE;
                                    CloseableKt.closeFinally(byteArrayInputStream, null);
                                    CloseableKt.closeFinally(byteArrayOutputStream, null);
                                    CloseableKt.closeFinally(inputStream, null);
                                } finally {
                                }
                            } finally {
                            }
                        } finally {
                        }
                    } catch (Exception e2) {
                        LogUtils logUtils2 = LogUtils.INSTANCE;
                        logUtils2.error("SVGAParser", "================ svga file download fail ================");
                        logUtils2.error("SVGAParser", "error: " + e2.getMessage());
                        e2.printStackTrace();
                        failure.invoke(e2);
                    }
                }
            });
            return function0;
        }

        public final void setNoCache(boolean z2) {
            this.noCache = z2;
        }
    }

    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\b\u0010\u0006\u001a\u00020\u0003H&¨\u0006\u0007"}, d2 = {"Lcom/opensource/svgaplayer/SVGAParser$ParseCompletion;", "", "onComplete", "", "videoItem", "Lcom/opensource/svgaplayer/SVGAVideoEntity;", "onError", BuildConfig.LIBRARY_PACKAGE_NAME}, k = 1, mv = {1, 1, 15})
    public interface ParseCompletion {
        void onComplete(@NotNull SVGAVideoEntity videoItem);

        void onError();
    }

    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H&¨\u0006\u0007"}, d2 = {"Lcom/opensource/svgaplayer/SVGAParser$PlayCallback;", "", "onPlay", "", "file", "", "Ljava/io/File;", BuildConfig.LIBRARY_PACKAGE_NAME}, k = 1, mv = {1, 1, 15})
    public interface PlayCallback {
        void onPlay(@NotNull List<? extends File> file);
    }

    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"<anonymous>", "", "run"}, k = 3, mv = {1, 1, 15})
    /* renamed from: com.opensource.svgaplayer.SVGAParser$decodeFromInputStream$1, reason: invalid class name and case insensitive filesystem */
    public static final class RunnableC05531 implements Runnable {
        final /* synthetic */ String $alias;
        final /* synthetic */ String $cacheKey;
        final /* synthetic */ ParseCompletion $callback;
        final /* synthetic */ boolean $closeInputStream;
        final /* synthetic */ InputStream $inputStream;
        final /* synthetic */ PlayCallback $playCallback;

        public RunnableC05531(InputStream inputStream, String str, ParseCompletion parseCompletion, String str2, PlayCallback playCallback, boolean z2) {
            this.$inputStream = inputStream;
            this.$cacheKey = str;
            this.$callback = parseCompletion;
            this.$alias = str2;
            this.$playCallback = playCallback;
            this.$closeInputStream = z2;
        }

        @Override // java.lang.Runnable
        public final void run() throws IOException {
            LogUtils logUtils;
            String str;
            StringBuilder sb;
            try {
                try {
                    final byte[] asBytes = SVGAParser.this.readAsBytes(this.$inputStream);
                    if (asBytes == null) {
                        SVGAParser.this.invokeErrorCallback(new Exception("readAsBytes(inputStream) cause exception"), this.$callback, this.$alias);
                    } else if (SVGAParser.this.isZipFile(asBytes)) {
                        LogUtils logUtils2 = LogUtils.INSTANCE;
                        logUtils2.info(SVGAParser.TAG, "decode from zip file");
                        SVGACache sVGACache = SVGACache.INSTANCE;
                        if (!sVGACache.buildCacheDir(this.$cacheKey).exists() || SVGAParserKt.isUnzipping) {
                            synchronized (Integer.valueOf(SVGAParserKt.fileLock)) {
                                if (!sVGACache.buildCacheDir(this.$cacheKey).exists()) {
                                    SVGAParserKt.isUnzipping = true;
                                    logUtils2.info(SVGAParser.TAG, "no cached, prepare to unzip");
                                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(asBytes);
                                    try {
                                        SVGAParser.this.unzip(byteArrayInputStream, this.$cacheKey);
                                        SVGAParserKt.isUnzipping = false;
                                        logUtils2.info(SVGAParser.TAG, "unzip success");
                                        Unit unit = Unit.INSTANCE;
                                        CloseableKt.closeFinally(byteArrayInputStream, null);
                                    } finally {
                                    }
                                }
                                Unit unit2 = Unit.INSTANCE;
                            }
                        }
                        SVGAParser.this.decodeFromCacheKey(this.$cacheKey, this.$callback, this.$alias);
                    } else {
                        if (!SVGACache.INSTANCE.isDefaultCache()) {
                            SVGAParser.INSTANCE.getThreadPoolExecutor$com_opensource_svgaplayer().execute(new Runnable() { // from class: com.opensource.svgaplayer.SVGAParser$decodeFromInputStream$1$$special$$inlined$let$lambda$2
                                @Override // java.lang.Runnable
                                public final void run() throws IOException {
                                    File fileBuildSvgaFile = SVGACache.INSTANCE.buildSvgaFile(this.$cacheKey);
                                    try {
                                        File file = fileBuildSvgaFile.exists() ^ true ? fileBuildSvgaFile : null;
                                        if (file != null) {
                                            file.createNewFile();
                                        }
                                        new FileOutputStream(fileBuildSvgaFile).write(asBytes);
                                        Unit unit3 = Unit.INSTANCE;
                                    } catch (Exception e2) {
                                        LogUtils.INSTANCE.error("SVGAParser", "create cache file fail.", e2);
                                        fileBuildSvgaFile.delete();
                                    }
                                }
                            });
                        }
                        LogUtils logUtils3 = LogUtils.INSTANCE;
                        logUtils3.info(SVGAParser.TAG, "inflate start");
                        byte[] bArrInflate = SVGAParser.this.inflate(asBytes);
                        if (bArrInflate != null) {
                            logUtils3.info(SVGAParser.TAG, "inflate complete");
                            MovieEntity movieEntityDecode = MovieEntity.ADAPTER.decode(bArrInflate);
                            Intrinsics.checkExpressionValueIsNotNull(movieEntityDecode, "MovieEntity.ADAPTER.decode(it)");
                            final SVGAVideoEntity sVGAVideoEntity = new SVGAVideoEntity(movieEntityDecode, new File(this.$cacheKey), SVGAParser.this.mFrameWidth, SVGAParser.this.mFrameHeight);
                            logUtils3.info(SVGAParser.TAG, "SVGAVideoEntity prepare start");
                            sVGAVideoEntity.prepare$com_opensource_svgaplayer(new Function0<Unit>() { // from class: com.opensource.svgaplayer.SVGAParser$decodeFromInputStream$1$$special$$inlined$let$lambda$3
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(0);
                                }

                                @Override // kotlin.jvm.functions.Function0
                                public /* bridge */ /* synthetic */ Unit invoke() {
                                    invoke2();
                                    return Unit.INSTANCE;
                                }

                                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                                public final void invoke2() {
                                    LogUtils.INSTANCE.info("SVGAParser", "SVGAVideoEntity prepare success");
                                    SVGAParser.RunnableC05531 runnableC05531 = this;
                                    SVGAParser.this.invokeCompleteCallback(sVGAVideoEntity, runnableC05531.$callback, runnableC05531.$alias);
                                }
                            }, this.$playCallback);
                        } else {
                            SVGAParser.this.invokeErrorCallback(new Exception("inflate(bytes) cause exception"), this.$callback, this.$alias);
                        }
                    }
                    if (this.$closeInputStream) {
                        this.$inputStream.close();
                    }
                    logUtils = LogUtils.INSTANCE;
                    str = SVGAParser.TAG;
                    sb = new StringBuilder();
                } catch (Exception e2) {
                    SVGAParser.this.invokeErrorCallback(e2, this.$callback, this.$alias);
                    if (this.$closeInputStream) {
                        this.$inputStream.close();
                    }
                    logUtils = LogUtils.INSTANCE;
                    str = SVGAParser.TAG;
                    sb = new StringBuilder();
                }
                sb.append("================ decode ");
                sb.append(this.$alias);
                sb.append(" from input stream end ================");
                logUtils.info(str, sb.toString());
            } catch (Throwable th) {
                if (this.$closeInputStream) {
                    this.$inputStream.close();
                }
                LogUtils.INSTANCE.info(SVGAParser.TAG, "================ decode " + this.$alias + " from input stream end ================");
                throw th;
            }
        }
    }

    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"<anonymous>", "", "run"}, k = 3, mv = {1, 1, 15})
    /* renamed from: com.opensource.svgaplayer.SVGAParser$decodeFromSVGAFileCacheKey$1, reason: invalid class name and case insensitive filesystem */
    public static final class RunnableC05541 implements Runnable {
        final /* synthetic */ String $alias;
        final /* synthetic */ String $cacheKey;
        final /* synthetic */ ParseCompletion $callback;
        final /* synthetic */ PlayCallback $playCallback;

        public RunnableC05541(String str, String str2, ParseCompletion parseCompletion, PlayCallback playCallback) {
            this.$alias = str;
            this.$cacheKey = str2;
            this.$callback = parseCompletion;
            this.$playCallback = playCallback;
        }

        @Override // java.lang.Runnable
        public final void run() {
            LogUtils logUtils;
            StringBuilder sb;
            try {
                try {
                    logUtils = LogUtils.INSTANCE;
                    logUtils.info(SVGAParser.TAG, "================ decode " + this.$alias + " from svga cachel file to entity ================");
                    FileInputStream fileInputStream = new FileInputStream(SVGACache.INSTANCE.buildSvgaFile(this.$cacheKey));
                    try {
                        byte[] asBytes = SVGAParser.this.readAsBytes(fileInputStream);
                        if (asBytes == null) {
                            SVGAParser.this.invokeErrorCallback(new Exception("readAsBytes(inputStream) cause exception"), this.$callback, this.$alias);
                        } else if (SVGAParser.this.isZipFile(asBytes)) {
                            SVGAParser.this.decodeFromCacheKey(this.$cacheKey, this.$callback, this.$alias);
                        } else {
                            logUtils.info(SVGAParser.TAG, "inflate start");
                            byte[] bArrInflate = SVGAParser.this.inflate(asBytes);
                            if (bArrInflate != null) {
                                logUtils.info(SVGAParser.TAG, "inflate complete");
                                MovieEntity movieEntityDecode = MovieEntity.ADAPTER.decode(bArrInflate);
                                Intrinsics.checkExpressionValueIsNotNull(movieEntityDecode, "MovieEntity.ADAPTER.decode(it)");
                                final SVGAVideoEntity sVGAVideoEntity = new SVGAVideoEntity(movieEntityDecode, new File(this.$cacheKey), SVGAParser.this.mFrameWidth, SVGAParser.this.mFrameHeight);
                                logUtils.info(SVGAParser.TAG, "SVGAVideoEntity prepare start");
                                sVGAVideoEntity.prepare$com_opensource_svgaplayer(new Function0<Unit>() { // from class: com.opensource.svgaplayer.SVGAParser$decodeFromSVGAFileCacheKey$1$$special$$inlined$use$lambda$1
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(0);
                                    }

                                    @Override // kotlin.jvm.functions.Function0
                                    public /* bridge */ /* synthetic */ Unit invoke() {
                                        invoke2();
                                        return Unit.INSTANCE;
                                    }

                                    /* renamed from: invoke, reason: avoid collision after fix types in other method */
                                    public final void invoke2() {
                                        LogUtils.INSTANCE.info("SVGAParser", "SVGAVideoEntity prepare success");
                                        SVGAParser.RunnableC05541 runnableC05541 = this;
                                        SVGAParser.this.invokeCompleteCallback(sVGAVideoEntity, runnableC05541.$callback, runnableC05541.$alias);
                                    }
                                }, this.$playCallback);
                            } else {
                                SVGAParser.this.invokeErrorCallback(new Exception("inflate(bytes) cause exception"), this.$callback, this.$alias);
                            }
                        }
                        Unit unit = Unit.INSTANCE;
                        CloseableKt.closeFinally(fileInputStream, null);
                        sb = new StringBuilder();
                    } finally {
                    }
                } catch (Exception e2) {
                    SVGAParser.this.invokeErrorCallback(e2, this.$callback, this.$alias);
                    logUtils = LogUtils.INSTANCE;
                    sb = new StringBuilder();
                }
                sb.append("================ decode ");
                sb.append(this.$alias);
                sb.append(" from svga cachel file to entity end ================");
                logUtils.info(SVGAParser.TAG, sb.toString());
            } catch (Throwable th) {
                LogUtils.INSTANCE.info(SVGAParser.TAG, "================ decode " + this.$alias + " from svga cachel file to entity end ================");
                throw th;
            }
        }
    }

    public SVGAParser(@Nullable Context context) {
        this.mContext = context != null ? context.getApplicationContext() : null;
        SVGACache.INSTANCE.onCreate(context);
        this.fileDownloader = new FileDownloader();
    }

    public static /* synthetic */ void decodeFromAssets$default(SVGAParser sVGAParser, String str, ParseCompletion parseCompletion, PlayCallback playCallback, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            playCallback = null;
        }
        sVGAParser.decodeFromAssets(str, parseCompletion, playCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void decodeFromCacheKey(String cacheKey, ParseCompletion callback, String alias) throws Exception {
        FileInputStream fileInputStream;
        LogUtils logUtils = LogUtils.INSTANCE;
        logUtils.info(TAG, "================ decode " + alias + " from cache ================");
        StringBuilder sb = new StringBuilder();
        sb.append("decodeFromCacheKey called with cacheKey : ");
        sb.append(cacheKey);
        logUtils.debug(TAG, sb.toString());
        if (this.mContext == null) {
            logUtils.error(TAG, "在配置 SVGAParser context 前, 无法解析 SVGA 文件。");
            return;
        }
        try {
            File fileBuildCacheDir = SVGACache.INSTANCE.buildCacheDir(cacheKey);
            File file = new File(fileBuildCacheDir, "movie.binary");
            if (!file.isFile()) {
                file = null;
            }
            if (file != null) {
                try {
                    logUtils.info(TAG, "binary change to entity");
                    fileInputStream = new FileInputStream(file);
                    try {
                        logUtils.info(TAG, "binary change to entity success");
                        MovieEntity movieEntityDecode = MovieEntity.ADAPTER.decode(fileInputStream);
                        Intrinsics.checkExpressionValueIsNotNull(movieEntityDecode, "MovieEntity.ADAPTER.decode(it)");
                        invokeCompleteCallback(new SVGAVideoEntity(movieEntityDecode, fileBuildCacheDir, this.mFrameWidth, this.mFrameHeight), callback, alias);
                        Unit unit = Unit.INSTANCE;
                        CloseableKt.closeFinally(fileInputStream, null);
                    } finally {
                    }
                } catch (Exception e2) {
                    LogUtils.INSTANCE.error(TAG, "binary change to entity fail", e2);
                    fileBuildCacheDir.delete();
                    file.delete();
                    throw e2;
                }
            }
            File file2 = new File(fileBuildCacheDir, "movie.spec");
            if (!file2.isFile()) {
                file2 = null;
            }
            if (file2 == null) {
                return;
            }
            try {
                logUtils.info(TAG, "spec change to entity");
                fileInputStream = new FileInputStream(file2);
                try {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    try {
                        byte[] bArr = new byte[2048];
                        while (true) {
                            int i2 = fileInputStream.read(bArr, 0, 2048);
                            if (i2 == -1) {
                                JSONObject jSONObject = new JSONObject(byteArrayOutputStream.toString());
                                LogUtils.INSTANCE.info(TAG, "spec change to entity success");
                                invokeCompleteCallback(new SVGAVideoEntity(jSONObject, fileBuildCacheDir, this.mFrameWidth, this.mFrameHeight), callback, alias);
                                Unit unit2 = Unit.INSTANCE;
                                CloseableKt.closeFinally(byteArrayOutputStream, null);
                                CloseableKt.closeFinally(fileInputStream, null);
                                return;
                            }
                            byteArrayOutputStream.write(bArr, 0, i2);
                        }
                    } finally {
                    }
                } finally {
                    try {
                        throw th;
                    } finally {
                    }
                }
            } catch (Exception e3) {
                LogUtils.INSTANCE.error(TAG, alias + " movie.spec change to entity fail", e3);
                fileBuildCacheDir.delete();
                file2.delete();
                throw e3;
            }
        } catch (Exception e4) {
            invokeErrorCallback(e4, callback, alias);
        }
    }

    public static /* synthetic */ void decodeFromInputStream$default(SVGAParser sVGAParser, InputStream inputStream, String str, ParseCompletion parseCompletion, boolean z2, PlayCallback playCallback, String str2, int i2, Object obj) {
        if ((i2 & 8) != 0) {
            z2 = false;
        }
        sVGAParser.decodeFromInputStream(inputStream, str, parseCompletion, z2, (i2 & 16) != 0 ? null : playCallback, (i2 & 32) != 0 ? null : str2);
    }

    public static /* synthetic */ void decodeFromSVGAFileCacheKey$default(SVGAParser sVGAParser, String str, ParseCompletion parseCompletion, PlayCallback playCallback, String str2, int i2, Object obj) {
        if ((i2 & 8) != 0) {
            str2 = null;
        }
        sVGAParser.decodeFromSVGAFileCacheKey(str, parseCompletion, playCallback, str2);
    }

    public static /* synthetic */ Function0 decodeFromURL$default(SVGAParser sVGAParser, URL url, ParseCompletion parseCompletion, PlayCallback playCallback, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            playCallback = null;
        }
        return sVGAParser.decodeFromURL(url, parseCompletion, playCallback);
    }

    private final void ensureUnzipSafety(File outputFile, String dstDirPath) throws IOException {
        String dstDirCanonicalPath = new File(dstDirPath).getCanonicalPath();
        String outputFileCanonicalPath = outputFile.getCanonicalPath();
        Intrinsics.checkExpressionValueIsNotNull(outputFileCanonicalPath, "outputFileCanonicalPath");
        Intrinsics.checkExpressionValueIsNotNull(dstDirCanonicalPath, "dstDirCanonicalPath");
        if (StringsKt__StringsJVMKt.startsWith$default(outputFileCanonicalPath, dstDirCanonicalPath, false, 2, null)) {
            return;
        }
        throw new IOException("Found Zip Path Traversal Vulnerability with " + dstDirCanonicalPath);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final byte[] inflate(byte[] byteArray) {
        Inflater inflater = new Inflater();
        inflater.setInput(byteArray, 0, byteArray.length);
        byte[] bArr = new byte[2048];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while (true) {
            try {
                int iInflate = inflater.inflate(bArr, 0, 2048);
                if (iInflate <= 0) {
                    inflater.end();
                    byte[] byteArray2 = byteArrayOutputStream.toByteArray();
                    CloseableKt.closeFinally(byteArrayOutputStream, null);
                    return byteArray2;
                }
                byteArrayOutputStream.write(bArr, 0, iInflate);
            } finally {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void invokeCompleteCallback(final SVGAVideoEntity videoItem, final ParseCompletion callback, final String alias) {
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.opensource.svgaplayer.SVGAParser.invokeCompleteCallback.1
            @Override // java.lang.Runnable
            public final void run() {
                LogUtils.INSTANCE.info(SVGAParser.TAG, "================ " + alias + " parser complete ================");
                ParseCompletion parseCompletion = callback;
                if (parseCompletion != null) {
                    parseCompletion.onComplete(videoItem);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void invokeErrorCallback(Exception e2, final ParseCompletion callback, String alias) {
        e2.printStackTrace();
        LogUtils logUtils = LogUtils.INSTANCE;
        logUtils.error(TAG, "================ " + alias + " parser error ================");
        StringBuilder sb = new StringBuilder();
        sb.append(alias);
        sb.append(" parse error");
        logUtils.error(TAG, sb.toString(), e2);
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.opensource.svgaplayer.SVGAParser.invokeErrorCallback.1
            @Override // java.lang.Runnable
            public final void run() {
                ParseCompletion parseCompletion = callback;
                if (parseCompletion != null) {
                    parseCompletion.onError();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isZipFile(byte[] bytes) {
        return bytes.length > 4 && bytes[0] == 80 && bytes[1] == 75 && bytes[2] == 3 && bytes[3] == 4;
    }

    public static /* synthetic */ void parse$default(SVGAParser sVGAParser, InputStream inputStream, String str, ParseCompletion parseCompletion, boolean z2, int i2, Object obj) {
        if ((i2 & 8) != 0) {
            z2 = false;
        }
        sVGAParser.parse(inputStream, str, parseCompletion, z2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final byte[] readAsBytes(InputStream inputStream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            byte[] bArr = new byte[2048];
            while (true) {
                int i2 = inputStream.read(bArr, 0, 2048);
                if (i2 <= 0) {
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    CloseableKt.closeFinally(byteArrayOutputStream, null);
                    return byteArray;
                }
                byteArrayOutputStream.write(bArr, 0, i2);
            }
        } finally {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void unzip(InputStream inputStream, String cacheKey) throws Exception {
        LogUtils.INSTANCE.info(TAG, "================ unzip prepare ================");
        File fileBuildCacheDir = SVGACache.INSTANCE.buildCacheDir(cacheKey);
        fileBuildCacheDir.mkdirs();
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            try {
                ZipInputStream zipInputStream = new ZipInputStream(bufferedInputStream);
                while (true) {
                    try {
                        ZipEntry nextEntry = zipInputStream.getNextEntry();
                        if (nextEntry == null) {
                            Unit unit = Unit.INSTANCE;
                            CloseableKt.closeFinally(zipInputStream, null);
                            CloseableKt.closeFinally(bufferedInputStream, null);
                            return;
                        }
                        String name = nextEntry.getName();
                        Intrinsics.checkExpressionValueIsNotNull(name, "zipItem.name");
                        if (!StringsKt__StringsKt.contains$default((CharSequence) name, (CharSequence) "../", false, 2, (Object) null)) {
                            String name2 = nextEntry.getName();
                            Intrinsics.checkExpressionValueIsNotNull(name2, "zipItem.name");
                            if (!StringsKt__StringsKt.contains$default((CharSequence) name2, (CharSequence) "/", false, 2, (Object) null)) {
                                File file = new File(fileBuildCacheDir, nextEntry.getName());
                                String absolutePath = fileBuildCacheDir.getAbsolutePath();
                                Intrinsics.checkExpressionValueIsNotNull(absolutePath, "cacheDir.absolutePath");
                                ensureUnzipSafety(file, absolutePath);
                                FileOutputStream fileOutputStream = new FileOutputStream(file);
                                try {
                                    byte[] bArr = new byte[2048];
                                    while (true) {
                                        int i2 = zipInputStream.read(bArr);
                                        if (i2 <= 0) {
                                            break;
                                        } else {
                                            fileOutputStream.write(bArr, 0, i2);
                                        }
                                    }
                                    Unit unit2 = Unit.INSTANCE;
                                    CloseableKt.closeFinally(fileOutputStream, null);
                                    LogUtils.INSTANCE.error(TAG, "================ unzip complete ================");
                                    zipInputStream.closeEntry();
                                } finally {
                                }
                            }
                        }
                    } finally {
                    }
                }
            } finally {
            }
        } catch (Exception e2) {
            LogUtils logUtils = LogUtils.INSTANCE;
            logUtils.error(TAG, "================ unzip error ================");
            logUtils.error(TAG, "error", e2);
            SVGACache sVGACache = SVGACache.INSTANCE;
            String absolutePath2 = fileBuildCacheDir.getAbsolutePath();
            Intrinsics.checkExpressionValueIsNotNull(absolutePath2, "cacheDir.absolutePath");
            sVGACache.clearDir$com_opensource_svgaplayer(absolutePath2);
            fileBuildCacheDir.delete();
            throw e2;
        }
    }

    public final void decodeFromAssets(@NotNull final String name, @Nullable final ParseCompletion callback, @Nullable final PlayCallback playCallback) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        if (this.mContext == null) {
            LogUtils.INSTANCE.error(TAG, "在配置 SVGAParser context 前, 无法解析 SVGA 文件。");
            return;
        }
        LogUtils.INSTANCE.info(TAG, "================ decode " + name + " from assets ================");
        threadPoolExecutor.execute(new Runnable() { // from class: com.opensource.svgaplayer.SVGAParser.decodeFromAssets.1
            @Override // java.lang.Runnable
            public final void run() {
                AssetManager assets;
                InputStream inputStreamOpen;
                try {
                    Context context = SVGAParser.this.mContext;
                    if (context == null || (assets = context.getAssets()) == null || (inputStreamOpen = assets.open(name)) == null) {
                        return;
                    }
                    SVGAParser.this.decodeFromInputStream(inputStreamOpen, SVGACache.INSTANCE.buildCacheKey("file:///assets/" + name), callback, true, playCallback, name);
                } catch (Exception e2) {
                    SVGAParser.this.invokeErrorCallback(e2, callback, name);
                }
            }
        });
    }

    public final void decodeFromInputStream(@NotNull InputStream inputStream, @NotNull String cacheKey, @Nullable ParseCompletion callback, boolean closeInputStream, @Nullable PlayCallback playCallback, @Nullable String alias) {
        Intrinsics.checkParameterIsNotNull(inputStream, "inputStream");
        Intrinsics.checkParameterIsNotNull(cacheKey, "cacheKey");
        if (this.mContext == null) {
            LogUtils.INSTANCE.error(TAG, "在配置 SVGAParser context 前, 无法解析 SVGA 文件。");
            return;
        }
        LogUtils.INSTANCE.info(TAG, "================ decode " + alias + " from input stream ================");
        threadPoolExecutor.execute(new RunnableC05531(inputStream, cacheKey, callback, alias, playCallback, closeInputStream));
    }

    public final void decodeFromSVGAFileCacheKey(@NotNull String cacheKey, @Nullable ParseCompletion callback, @Nullable PlayCallback playCallback, @Nullable String alias) {
        Intrinsics.checkParameterIsNotNull(cacheKey, "cacheKey");
        threadPoolExecutor.execute(new RunnableC05541(alias, cacheKey, callback, playCallback));
    }

    @Nullable
    public final Function0<Unit> decodeFromURL(@NotNull final URL url, @Nullable final ParseCompletion callback, @Nullable final PlayCallback playCallback) {
        Intrinsics.checkParameterIsNotNull(url, "url");
        if (this.mContext == null) {
            LogUtils.INSTANCE.error(TAG, "在配置 SVGAParser context 前, 无法解析 SVGA 文件。");
            return null;
        }
        final String string = url.toString();
        Intrinsics.checkExpressionValueIsNotNull(string, "url.toString()");
        LogUtils logUtils = LogUtils.INSTANCE;
        logUtils.info(TAG, "================ decode from url: " + string + " ================");
        SVGACache sVGACache = SVGACache.INSTANCE;
        final String strBuildCacheKey = sVGACache.buildCacheKey(url);
        if (!sVGACache.isCached(strBuildCacheKey)) {
            logUtils.info(TAG, "no cached, prepare to download");
            return this.fileDownloader.resume(url, new Function1<InputStream, Unit>() { // from class: com.opensource.svgaplayer.SVGAParser.decodeFromURL.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(InputStream inputStream) {
                    invoke2(inputStream);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(@NotNull InputStream it) {
                    Intrinsics.checkParameterIsNotNull(it, "it");
                    SVGAParser.this.decodeFromInputStream(it, strBuildCacheKey, callback, false, playCallback, string);
                }
            }, new Function1<Exception, Unit>() { // from class: com.opensource.svgaplayer.SVGAParser.decodeFromURL.3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Exception exc) {
                    invoke2(exc);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(@NotNull Exception it) {
                    Intrinsics.checkParameterIsNotNull(it, "it");
                    LogUtils.INSTANCE.error(SVGAParser.TAG, "================ svga file: " + url + " download fail ================");
                    SVGAParser.this.invokeErrorCallback(it, callback, string);
                }
            });
        }
        logUtils.info(TAG, "this url cached");
        threadPoolExecutor.execute(new Runnable() { // from class: com.opensource.svgaplayer.SVGAParser.decodeFromURL.1
            @Override // java.lang.Runnable
            public final void run() throws Exception {
                if (SVGACache.INSTANCE.isDefaultCache()) {
                    SVGAParser.this.decodeFromCacheKey(strBuildCacheKey, callback, string);
                } else {
                    SVGAParser.this.decodeFromSVGAFileCacheKey(strBuildCacheKey, callback, playCallback, string);
                }
            }
        });
        return null;
    }

    @NotNull
    public final FileDownloader getFileDownloader() {
        return this.fileDownloader;
    }

    public final void init(@NotNull Context context) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        Context applicationContext = context.getApplicationContext();
        this.mContext = applicationContext;
        SVGACache.INSTANCE.onCreate(applicationContext);
    }

    @Deprecated(message = "This method has been deprecated from 2.4.0.", replaceWith = @ReplaceWith(expression = "this.decodeFromAssets(assetsName, callback)", imports = {}))
    public final void parse(@NotNull String assetsName, @Nullable ParseCompletion callback) {
        Intrinsics.checkParameterIsNotNull(assetsName, "assetsName");
        decodeFromAssets(assetsName, callback, null);
    }

    public final void setFileDownloader(@NotNull FileDownloader fileDownloader) {
        Intrinsics.checkParameterIsNotNull(fileDownloader, "<set-?>");
        this.fileDownloader = fileDownloader;
    }

    public final void setFrameSize(int frameWidth, int frameHeight) {
        this.mFrameWidth = frameWidth;
        this.mFrameHeight = frameHeight;
    }

    @Deprecated(message = "This method has been deprecated from 2.4.0.", replaceWith = @ReplaceWith(expression = "this.decodeFromURL(url, callback)", imports = {}))
    public final void parse(@NotNull URL url, @Nullable ParseCompletion callback) {
        Intrinsics.checkParameterIsNotNull(url, "url");
        decodeFromURL(url, callback, null);
    }

    @Deprecated(message = "This method has been deprecated from 2.4.0.", replaceWith = @ReplaceWith(expression = "this.decodeFromInputStream(inputStream, cacheKey, callback, closeInputStream)", imports = {}))
    public final void parse(@NotNull InputStream inputStream, @NotNull String cacheKey, @Nullable ParseCompletion callback, boolean closeInputStream) {
        Intrinsics.checkParameterIsNotNull(inputStream, "inputStream");
        Intrinsics.checkParameterIsNotNull(cacheKey, "cacheKey");
        decodeFromInputStream$default(this, inputStream, cacheKey, callback, closeInputStream, null, null, 32, null);
    }
}
