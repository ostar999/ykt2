package com.azhon.appupdate.listener;

import com.aliyun.player.alivcplayerexpand.util.database.DatabaseManager;
import com.aliyun.vod.log.struct.AliyunLogKey;
import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0003\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\u0010\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0018\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0016J\u0010\u0010\f\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u0004H\u0016¨\u0006\u0010"}, d2 = {"Lcom/azhon/appupdate/listener/OnDownloadListenerAdapter;", "Lcom/azhon/appupdate/listener/OnDownloadListener;", "()V", "cancel", "", "done", "apk", "Ljava/io/File;", DatabaseManager.DOWNLOADING, "max", "", "progress", "error", AliyunLogKey.KEY_EVENT, "", "start", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public abstract class OnDownloadListenerAdapter implements OnDownloadListener {
    @Override // com.azhon.appupdate.listener.OnDownloadListener
    public void cancel() {
    }

    @Override // com.azhon.appupdate.listener.OnDownloadListener
    public void done(@NotNull File apk) {
        Intrinsics.checkNotNullParameter(apk, "apk");
    }

    @Override // com.azhon.appupdate.listener.OnDownloadListener
    public void downloading(int max, int progress) {
    }

    @Override // com.azhon.appupdate.listener.OnDownloadListener
    public void error(@NotNull Throwable e2) {
        Intrinsics.checkNotNullParameter(e2, "e");
    }

    @Override // com.azhon.appupdate.listener.OnDownloadListener
    public void start() {
    }
}
