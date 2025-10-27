package com.aliyun.player.alivcplayerexpand.util.download;

import com.aliyun.player.bean.ErrorCode;
import java.util.List;

/* loaded from: classes2.dex */
public interface AliyunDownloadInfoListener {
    void onAdd(AliyunDownloadMediaInfo aliyunDownloadMediaInfo);

    void onCompletion(AliyunDownloadMediaInfo aliyunDownloadMediaInfo);

    void onDelete(AliyunDownloadMediaInfo aliyunDownloadMediaInfo);

    void onDeleteAll();

    void onError(AliyunDownloadMediaInfo aliyunDownloadMediaInfo, ErrorCode errorCode, String str, String str2);

    void onFileProgress(AliyunDownloadMediaInfo aliyunDownloadMediaInfo);

    void onPrepared(List<AliyunDownloadMediaInfo> list);

    void onProgress(AliyunDownloadMediaInfo aliyunDownloadMediaInfo, int i2);

    void onStart(AliyunDownloadMediaInfo aliyunDownloadMediaInfo);

    void onStop(AliyunDownloadMediaInfo aliyunDownloadMediaInfo);

    void onWait(AliyunDownloadMediaInfo aliyunDownloadMediaInfo);
}
