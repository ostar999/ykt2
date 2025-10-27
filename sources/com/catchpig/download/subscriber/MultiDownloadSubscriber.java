package com.catchpig.download.subscriber;

import android.os.Handler;
import android.os.Looper;
import com.catchpig.download.callback.DownloadProgressListener;
import com.catchpig.download.callback.MultiDownloadCallback;
import com.catchpig.download.entity.DownloadProgress;
import com.catchpig.download.subscriber.MultiDownloadSubscriber;
import com.catchpig.utils.ext.LogExtKt;
import com.plv.socket.event.linkmic.PLVUpdateMicSiteEvent;
import io.reactivex.rxjava3.subscribers.ResourceSubscriber;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0000\u0018\u0000 \u001b2\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003:\u0001\u001bB\u0015\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\b\u0010\u000e\u001a\u00020\u000fH\u0016J\u0012\u0010\u0010\u001a\u00020\u000f2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0016J\u0010\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0002H\u0016J\b\u0010\u0014\u001a\u00020\u000fH\u0016J \u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0019\u001a\u00020\u001aH\u0016R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u000b\u001a\u0012\u0012\u0004\u0012\u00020\u00020\fj\b\u0012\u0004\u0012\u00020\u0002`\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001c"}, d2 = {"Lcom/catchpig/download/subscriber/MultiDownloadSubscriber;", "Lio/reactivex/rxjava3/subscribers/ResourceSubscriber;", "", "Lcom/catchpig/download/callback/DownloadProgressListener;", "totalCount", "", "multiDownloadCallback", "Lcom/catchpig/download/callback/MultiDownloadCallback;", "(ILcom/catchpig/download/callback/MultiDownloadCallback;)V", "handler", "Landroid/os/Handler;", "paths", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "onComplete", "", "onError", "t", "", "onNext", "onStart", PLVUpdateMicSiteEvent.EVENT_NAME, "read", "", "count", "done", "", "Companion", "download_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class MultiDownloadSubscriber extends ResourceSubscriber<String> implements DownloadProgressListener {

    @NotNull
    private static final String TAG = "MultiDownloadSubscriber";

    @NotNull
    private final Handler handler;

    @NotNull
    private final MultiDownloadCallback multiDownloadCallback;

    @NotNull
    private ArrayList<String> paths;
    private final int totalCount;

    public MultiDownloadSubscriber(int i2, @NotNull MultiDownloadCallback multiDownloadCallback) {
        Intrinsics.checkNotNullParameter(multiDownloadCallback, "multiDownloadCallback");
        this.totalCount = i2;
        this.multiDownloadCallback = multiDownloadCallback;
        this.handler = new Handler(Looper.getMainLooper());
        this.paths = new ArrayList<>();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void update$lambda$1(MultiDownloadSubscriber this$0, boolean z2, long j2, long j3) {
        int i2;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        int size = this$0.paths.size() + 1;
        DownloadProgress downloadProgress = (z2 && size == (i2 = this$0.totalCount)) ? new DownloadProgress(j2, j3, i2, i2) : new DownloadProgress(j2, j3, size, this$0.totalCount);
        LogExtKt.logd("update->read:" + j2 + ",count:" + j3 + ",done:" + z2 + ",completeCount:" + size + ",totalCount:" + this$0.totalCount, TAG);
        this$0.multiDownloadCallback.onProgress(downloadProgress);
    }

    @Override // org.reactivestreams.Subscriber
    public void onComplete() {
        LogExtKt.logd("onComplete(" + this.paths + ")", TAG);
        this.multiDownloadCallback.onSuccess(this.paths);
        this.multiDownloadCallback.onComplete();
    }

    @Override // org.reactivestreams.Subscriber
    public void onError(@Nullable Throwable t2) {
        if (t2 != null) {
            if (t2 instanceof Exception) {
                ((Exception) t2).printStackTrace();
            }
            LogExtKt.loge("onError(" + t2 + ")", TAG);
            this.multiDownloadCallback.onError(t2);
        }
    }

    @Override // io.reactivex.rxjava3.subscribers.ResourceSubscriber
    public void onStart() {
        super.onStart();
        LogExtKt.logd("onStart", TAG);
        this.multiDownloadCallback.onStart();
    }

    @Override // com.catchpig.download.callback.DownloadProgressListener
    public void update(final long read, final long count, final boolean done) {
        this.handler.post(new Runnable() { // from class: p0.b
            @Override // java.lang.Runnable
            public final void run() {
                MultiDownloadSubscriber.update$lambda$1(this.f28153c, done, read, count);
            }
        });
    }

    @Override // org.reactivestreams.Subscriber
    public void onNext(@NotNull String t2) {
        Intrinsics.checkNotNullParameter(t2, "t");
        LogExtKt.logd("onNext(" + t2 + ")", TAG);
        this.paths.add(t2);
    }
}
