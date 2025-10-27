package com.azhon.appupdate.listener;

import com.aliyun.player.alivcplayerexpand.util.database.DatabaseManager;
import com.aliyun.vod.log.struct.AliyunLogKey;
import java.io.File;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0003\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\u0010\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u0006H&J\u0018\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\tH&J\u0010\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\rH&J\b\u0010\u000e\u001a\u00020\u0003H&¨\u0006\u000f"}, d2 = {"Lcom/azhon/appupdate/listener/OnDownloadListener;", "", "cancel", "", "done", "apk", "Ljava/io/File;", DatabaseManager.DOWNLOADING, "max", "", "progress", "error", AliyunLogKey.KEY_EVENT, "", "start", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public interface OnDownloadListener {
    void cancel();

    void done(@NotNull File apk);

    void downloading(int max, int progress);

    void error(@NotNull Throwable e2);

    void start();
}
