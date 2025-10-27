package com.catchpig.download.callback;

import com.catchpig.download.entity.DownloadProgress;
import java.util.List;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0010\u000e\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\u0010\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u0006H&J\u0010\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH&J\b\u0010\n\u001a\u00020\u0003H&J\u0016\u0010\u000b\u001a\u00020\u00032\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rH&¨\u0006\u000f"}, d2 = {"Lcom/catchpig/download/callback/MultiDownloadCallback;", "", "onComplete", "", "onError", "t", "", "onProgress", "downloadProgress", "Lcom/catchpig/download/entity/DownloadProgress;", "onStart", "onSuccess", "paths", "", "", "download_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public interface MultiDownloadCallback {
    void onComplete();

    void onError(@NotNull Throwable t2);

    void onProgress(@NotNull DownloadProgress downloadProgress);

    void onStart();

    void onSuccess(@NotNull List<String> paths);
}
