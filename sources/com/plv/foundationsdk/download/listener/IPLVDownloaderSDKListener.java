package com.plv.foundationsdk.download.listener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public interface IPLVDownloaderSDKListener {
    void onByte(int i2);

    void onDownloadError(@NonNull String str, int i2, @Nullable ArrayList<String> arrayList, @Nullable ArrayList<String> arrayList2);

    void onDownloadProgress(long j2, long j3);

    void onDownloadSuccess();
}
