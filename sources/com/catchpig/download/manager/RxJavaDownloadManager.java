package com.catchpig.download.manager;

import com.alipay.sdk.authjs.a;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.catchpig.download.api.DownloadService;
import com.catchpig.download.callback.DownloadCallback;
import com.catchpig.download.callback.MultiDownloadCallback;
import com.catchpig.download.entity.DownloadProgress;
import com.catchpig.download.subscriber.DownloadSubscriber;
import com.catchpig.download.subscriber.MultiDownloadSubscriber;
import com.catchpig.utils.ext.RxJavaExtKt;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.io.File;
import java.net.URL;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u001c\n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \"2\u00020\u0001:\u0002\"#B\u0005¢\u0006\u0002\u0010\u0002JX\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062!\u0010\u0007\u001a\u001d\u0012\u0013\u0012\u00110\u0006¢\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b(\u000b\u0012\u0004\u0012\u00020\f0\b2%\b\u0002\u0010\r\u001a\u001f\u0012\u0013\u0012\u00110\u000e¢\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b(\u000f\u0012\u0004\u0012\u00020\f\u0018\u00010\bJ\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u0011JX\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062!\u0010\u0007\u001a\u001d\u0012\u0013\u0012\u00110\u0013¢\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b(\u0014\u0012\u0004\u0012\u00020\f0\b2%\b\u0002\u0010\r\u001a\u001f\u0012\u0013\u0012\u00110\u000e¢\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b(\u000f\u0012\u0004\u0012\u00020\f\u0018\u00010\bJ&\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00060\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u00062\u0006\u0010\u001a\u001a\u00020\u0006H\u0002Jd\u0010\u001b\u001a\u00020\u00042\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00060\u001d2'\u0010\u0007\u001a#\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00020\u00060\u001e¢\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b(\u001f\u0012\u0004\u0012\u00020\f0\b2%\b\u0002\u0010\r\u001a\u001f\u0012\u0013\u0012\u00110\u000e¢\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b(\u000f\u0012\u0004\u0012\u00020\f\u0018\u00010\bJ\u001c\u0010\u001b\u001a\u00020\u00042\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00060\u001d2\u0006\u0010 \u001a\u00020!¨\u0006$"}, d2 = {"Lcom/catchpig/download/manager/RxJavaDownloadManager;", "Lcom/catchpig/download/manager/DownloadManager;", "()V", AliyunLogCommon.SubModule.download, "Lio/reactivex/rxjava3/disposables/Disposable;", "downloadUrl", "", a.f3170c, "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "path", "", "progress", "Lcom/catchpig/download/entity/DownloadProgress;", "downloadProgress", "downloadCallback", "Lcom/catchpig/download/callback/DownloadCallback;", "downloadFile", "Ljava/io/File;", "file", "httpDownload", "Lio/reactivex/rxjava3/core/Flowable;", "downloadService", "Lcom/catchpig/download/api/DownloadService;", "url", "localFilePath", "multiDownload", "downloadUrls", "", "", "paths", "multiDownloadCallback", "Lcom/catchpig/download/callback/MultiDownloadCallback;", "Companion", "RxJavaDownloadManagerHolder", "download_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class RxJavaDownloadManager extends DownloadManager {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0005"}, d2 = {"Lcom/catchpig/download/manager/RxJavaDownloadManager$Companion;", "", "()V", "getInstance", "Lcom/catchpig/download/manager/RxJavaDownloadManager;", "download_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final RxJavaDownloadManager getInstance() {
            return RxJavaDownloadManagerHolder.INSTANCE.getHolder();
        }
    }

    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÂ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lcom/catchpig/download/manager/RxJavaDownloadManager$RxJavaDownloadManagerHolder;", "", "()V", "holder", "Lcom/catchpig/download/manager/RxJavaDownloadManager;", "getHolder", "()Lcom/catchpig/download/manager/RxJavaDownloadManager;", "download_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class RxJavaDownloadManagerHolder {

        @NotNull
        public static final RxJavaDownloadManagerHolder INSTANCE = new RxJavaDownloadManagerHolder();

        @NotNull
        private static final RxJavaDownloadManager holder = new RxJavaDownloadManager();

        private RxJavaDownloadManagerHolder() {
        }

        @NotNull
        public final RxJavaDownloadManager getHolder() {
            return holder;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ Disposable download$default(RxJavaDownloadManager rxJavaDownloadManager, String str, Function1 function1, Function1 function12, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            function12 = null;
        }
        return rxJavaDownloadManager.download(str, function1, function12);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ Disposable downloadFile$default(RxJavaDownloadManager rxJavaDownloadManager, String str, Function1 function1, Function1 function12, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            function12 = null;
        }
        return rxJavaDownloadManager.downloadFile(str, function1, function12);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Flowable<String> httpDownload(DownloadService downloadService, String url, final String localFilePath) {
        Flowable map = downloadService.rxJavaDownload(url).subscribeOn(Schedulers.io()).map(new Function() { // from class: com.catchpig.download.manager.RxJavaDownloadManager.httpDownload.1
            @Override // io.reactivex.rxjava3.functions.Function
            @NotNull
            public final String apply(@NotNull ResponseBody it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return RxJavaDownloadManager.this.writeCache(it, localFilePath);
            }
        });
        Intrinsics.checkNotNullExpressionValue(map, "private fun httpDownload…FilePath)\n        }\n    }");
        return map;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ Disposable multiDownload$default(RxJavaDownloadManager rxJavaDownloadManager, Iterable iterable, Function1 function1, Function1 function12, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            function12 = null;
        }
        return rxJavaDownloadManager.multiDownload(iterable, function1, function12);
    }

    @NotNull
    public final Disposable download(@NotNull String downloadUrl, @NotNull final Function1<? super String, Unit> callback, @Nullable final Function1<? super DownloadProgress, Unit> progress) {
        Intrinsics.checkNotNullParameter(downloadUrl, "downloadUrl");
        Intrinsics.checkNotNullParameter(callback, "callback");
        return download(downloadUrl, new DownloadCallback() { // from class: com.catchpig.download.manager.RxJavaDownloadManager.download.1
            @Override // com.catchpig.download.callback.DownloadCallback
            public void onComplete() {
            }

            @Override // com.catchpig.download.callback.DownloadCallback
            public void onError(@NotNull Throwable t2) {
                Intrinsics.checkNotNullParameter(t2, "t");
            }

            @Override // com.catchpig.download.callback.DownloadCallback
            public void onProgress(@NotNull DownloadProgress downloadProgress) {
                Intrinsics.checkNotNullParameter(downloadProgress, "downloadProgress");
                Function1<DownloadProgress, Unit> function1 = progress;
                if (function1 != null) {
                    function1.invoke(downloadProgress);
                }
            }

            @Override // com.catchpig.download.callback.DownloadCallback
            public void onStart() {
            }

            @Override // com.catchpig.download.callback.DownloadCallback
            public void onSuccess(@NotNull String path) {
                Intrinsics.checkNotNullParameter(path, "path");
                callback.invoke(path);
            }
        });
    }

    @NotNull
    public final Disposable downloadFile(@NotNull String downloadUrl, @NotNull final Function1<? super File, Unit> callback, @Nullable final Function1<? super DownloadProgress, Unit> progress) {
        Intrinsics.checkNotNullParameter(downloadUrl, "downloadUrl");
        Intrinsics.checkNotNullParameter(callback, "callback");
        return download(downloadUrl, new DownloadCallback() { // from class: com.catchpig.download.manager.RxJavaDownloadManager.downloadFile.1
            @Override // com.catchpig.download.callback.DownloadCallback
            public void onComplete() {
            }

            @Override // com.catchpig.download.callback.DownloadCallback
            public void onError(@NotNull Throwable t2) {
                Intrinsics.checkNotNullParameter(t2, "t");
            }

            @Override // com.catchpig.download.callback.DownloadCallback
            public void onProgress(@NotNull DownloadProgress downloadProgress) {
                Intrinsics.checkNotNullParameter(downloadProgress, "downloadProgress");
                Function1<DownloadProgress, Unit> function1 = progress;
                if (function1 != null) {
                    function1.invoke(downloadProgress);
                }
            }

            @Override // com.catchpig.download.callback.DownloadCallback
            public void onStart() {
            }

            @Override // com.catchpig.download.callback.DownloadCallback
            public void onSuccess(@NotNull String path) {
                Intrinsics.checkNotNullParameter(path, "path");
                callback.invoke(new File(path));
            }
        });
    }

    @NotNull
    public final Disposable multiDownload(@NotNull Iterable<String> downloadUrls, @NotNull final Function1<? super List<String>, Unit> callback, @Nullable final Function1<? super DownloadProgress, Unit> progress) {
        Intrinsics.checkNotNullParameter(downloadUrls, "downloadUrls");
        Intrinsics.checkNotNullParameter(callback, "callback");
        return multiDownload(downloadUrls, new MultiDownloadCallback() { // from class: com.catchpig.download.manager.RxJavaDownloadManager.multiDownload.1
            @Override // com.catchpig.download.callback.MultiDownloadCallback
            public void onComplete() {
            }

            @Override // com.catchpig.download.callback.MultiDownloadCallback
            public void onError(@NotNull Throwable t2) {
                Intrinsics.checkNotNullParameter(t2, "t");
            }

            @Override // com.catchpig.download.callback.MultiDownloadCallback
            public void onProgress(@NotNull DownloadProgress downloadProgress) {
                Intrinsics.checkNotNullParameter(downloadProgress, "downloadProgress");
                Function1<DownloadProgress, Unit> function1 = progress;
                if (function1 != null) {
                    function1.invoke(downloadProgress);
                }
            }

            @Override // com.catchpig.download.callback.MultiDownloadCallback
            public void onStart() {
            }

            @Override // com.catchpig.download.callback.MultiDownloadCallback
            public void onSuccess(@NotNull List<String> paths) {
                Intrinsics.checkNotNullParameter(paths, "paths");
                callback.invoke(paths);
            }
        });
    }

    @NotNull
    public final Disposable download(@NotNull String downloadUrl, @NotNull DownloadCallback downloadCallback) {
        Intrinsics.checkNotNullParameter(downloadUrl, "downloadUrl");
        Intrinsics.checkNotNullParameter(downloadCallback, "downloadCallback");
        final DownloadSubscriber downloadSubscriber = new DownloadSubscriber(downloadCallback);
        Flowable flowableFlatMap = Flowable.just(downloadUrl).flatMap(new Function() { // from class: com.catchpig.download.manager.RxJavaDownloadManager.download.2
            @Override // io.reactivex.rxjava3.functions.Function
            @NotNull
            public final Publisher<? extends String> apply(@NotNull String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                String strLocalFileName = RxJavaDownloadManager.this.localFileName(it);
                File file = new File(strLocalFileName);
                URL url = new URL(it);
                if (file.exists()) {
                    long length = file.length();
                    if (length == url.openConnection().getContentLength()) {
                        downloadSubscriber.update(length, length, true);
                        return Flowable.just(strLocalFileName);
                    }
                }
                DownloadService downloadServiceInitDownloadService = DownloadManager.INSTANCE.initDownloadService(url, downloadSubscriber, true);
                RxJavaDownloadManager rxJavaDownloadManager = RxJavaDownloadManager.this;
                String file2 = url.getFile();
                Intrinsics.checkNotNullExpressionValue(file2, "url.file");
                String strSubstring = file2.substring(1);
                Intrinsics.checkNotNullExpressionValue(strSubstring, "this as java.lang.String).substring(startIndex)");
                return rxJavaDownloadManager.httpDownload(downloadServiceInitDownloadService, strSubstring, strLocalFileName);
            }
        });
        Intrinsics.checkNotNullExpressionValue(flowableFlatMap, "fun download(downloadUrl… downloadSubscriber\n    }");
        RxJavaExtKt.io2main(flowableFlatMap).subscribeWith(downloadSubscriber);
        return downloadSubscriber;
    }

    @NotNull
    public final Disposable multiDownload(@NotNull Iterable<String> downloadUrls, @NotNull MultiDownloadCallback multiDownloadCallback) {
        Intrinsics.checkNotNullParameter(downloadUrls, "downloadUrls");
        Intrinsics.checkNotNullParameter(multiDownloadCallback, "multiDownloadCallback");
        final MultiDownloadSubscriber multiDownloadSubscriber = new MultiDownloadSubscriber(CollectionsKt___CollectionsKt.count(downloadUrls), multiDownloadCallback);
        Flowable flowableConcatMap = Flowable.fromIterable(downloadUrls).concatMap(new Function() { // from class: com.catchpig.download.manager.RxJavaDownloadManager.multiDownload.2
            @Override // io.reactivex.rxjava3.functions.Function
            @NotNull
            public final Publisher<? extends String> apply(@NotNull String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                String strLocalFileName = RxJavaDownloadManager.this.localFileName(it);
                File file = new File(strLocalFileName);
                URL url = new URL(it);
                if (file.exists()) {
                    long length = file.length();
                    if (length == url.openConnection().getContentLength()) {
                        multiDownloadSubscriber.update(length, length, true);
                        return Flowable.just(strLocalFileName);
                    }
                }
                DownloadService downloadServiceInitDownloadService = DownloadManager.INSTANCE.initDownloadService(url, multiDownloadSubscriber, true);
                RxJavaDownloadManager rxJavaDownloadManager = RxJavaDownloadManager.this;
                String file2 = url.getFile();
                Intrinsics.checkNotNullExpressionValue(file2, "url.file");
                String strSubstring = file2.substring(1);
                Intrinsics.checkNotNullExpressionValue(strSubstring, "this as java.lang.String).substring(startIndex)");
                return rxJavaDownloadManager.httpDownload(downloadServiceInitDownloadService, strSubstring, strLocalFileName);
            }
        });
        Intrinsics.checkNotNullExpressionValue(flowableConcatMap, "fun multiDownload(\n     …DownloadSubscriber)\n    }");
        Subscriber subscriberSubscribeWith = RxJavaExtKt.io2main(flowableConcatMap).subscribeWith(multiDownloadSubscriber);
        Intrinsics.checkNotNullExpressionValue(subscriberSubscribeWith, "fun multiDownload(\n     …DownloadSubscriber)\n    }");
        return (Disposable) subscriberSubscribeWith;
    }
}
