package com.azhon.appupdate.base;

import com.aliyun.vod.log.core.AliyunLogCommon;
import com.azhon.appupdate.base.bean.DownloadStatus;
import kotlin.Metadata;
import kotlinx.coroutines.flow.Flow;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\u001e\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\tH&J\b\u0010\u000b\u001a\u00020\u0004H&¨\u0006\f"}, d2 = {"Lcom/azhon/appupdate/base/BaseHttpDownloadManager;", "", "()V", "cancel", "", AliyunLogCommon.SubModule.download, "Lkotlinx/coroutines/flow/Flow;", "Lcom/azhon/appupdate/base/bean/DownloadStatus;", "apkUrl", "", "apkName", "release", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public abstract class BaseHttpDownloadManager {
    public abstract void cancel();

    @NotNull
    public abstract Flow<DownloadStatus> download(@NotNull String apkUrl, @NotNull String apkName);

    public abstract void release();
}
