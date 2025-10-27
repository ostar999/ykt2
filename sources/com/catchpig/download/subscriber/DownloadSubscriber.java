package com.catchpig.download.subscriber;

import android.os.Handler;
import android.os.Looper;
import com.catchpig.download.callback.DownloadCallback;
import com.catchpig.download.callback.DownloadProgressListener;
import com.catchpig.download.entity.DownloadProgress;
import com.catchpig.download.subscriber.DownloadSubscriber;
import com.catchpig.utils.ext.LogExtKt;
import com.plv.socket.event.linkmic.PLVUpdateMicSiteEvent;
import io.reactivex.rxjava3.subscribers.ResourceSubscriber;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0000\u0018\u0000 \u00162\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003:\u0001\u0016B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\t\u001a\u00020\nH\u0016J\u0012\u0010\u000b\u001a\u00020\n2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0016J\u0010\u0010\u000e\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\u0002H\u0016J\b\u0010\u000f\u001a\u00020\nH\u0016J \u0010\u0010\u001a\u00020\n2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u0015H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lcom/catchpig/download/subscriber/DownloadSubscriber;", "Lio/reactivex/rxjava3/subscribers/ResourceSubscriber;", "", "Lcom/catchpig/download/callback/DownloadProgressListener;", "downloadCallback", "Lcom/catchpig/download/callback/DownloadCallback;", "(Lcom/catchpig/download/callback/DownloadCallback;)V", "handler", "Landroid/os/Handler;", "onComplete", "", "onError", "t", "", "onNext", "onStart", PLVUpdateMicSiteEvent.EVENT_NAME, "read", "", "count", "done", "", "Companion", "download_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class DownloadSubscriber extends ResourceSubscriber<String> implements DownloadProgressListener {

    @NotNull
    public static final String TAG = "DownloadSubscriber";

    @NotNull
    private final DownloadCallback downloadCallback;

    @NotNull
    private final Handler handler;

    public DownloadSubscriber(@NotNull DownloadCallback downloadCallback) {
        Intrinsics.checkNotNullParameter(downloadCallback, "downloadCallback");
        this.downloadCallback = downloadCallback;
        this.handler = new Handler(Looper.getMainLooper());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void update$lambda$1(boolean z2, long j2, long j3, DownloadSubscriber this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        DownloadProgress downloadProgress = z2 ? new DownloadProgress(j2, j3, 1, 1) : new DownloadProgress(j2, j3, 0, 1);
        LogExtKt.logd("update(" + j2 + "," + j3 + "," + z2 + ")", TAG);
        this$0.downloadCallback.onProgress(downloadProgress);
    }

    @Override // org.reactivestreams.Subscriber
    public void onComplete() {
        LogExtKt.logd("onComplete", TAG);
        this.downloadCallback.onComplete();
    }

    @Override // org.reactivestreams.Subscriber
    public void onError(@Nullable Throwable t2) {
        if (t2 != null) {
            if (t2 instanceof Exception) {
                ((Exception) t2).printStackTrace();
            }
            LogExtKt.loge("onError(" + t2 + ")", TAG);
            this.downloadCallback.onError(t2);
        }
    }

    @Override // io.reactivex.rxjava3.subscribers.ResourceSubscriber
    public void onStart() {
        super.onStart();
        LogExtKt.logd("onStart", TAG);
        this.downloadCallback.onStart();
    }

    @Override // com.catchpig.download.callback.DownloadProgressListener
    public void update(final long read, final long count, final boolean done) {
        this.handler.post(new Runnable() { // from class: p0.a
            @Override // java.lang.Runnable
            public final void run() {
                DownloadSubscriber.update$lambda$1(done, read, count, this);
            }
        });
    }

    @Override // org.reactivestreams.Subscriber
    public void onNext(@NotNull String t2) {
        Intrinsics.checkNotNullParameter(t2, "t");
        LogExtKt.logd("onNext(" + t2 + ")", TAG);
        this.downloadCallback.onSuccess(t2);
    }
}
