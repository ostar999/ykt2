package com.alibaba.sdk.android.vod.upload;

import com.alibaba.sdk.android.vod.upload.model.UploadFileInfo;
import com.alibaba.sdk.android.vod.upload.model.VodUploadResult;

/* loaded from: classes2.dex */
public abstract class VODUploadCallback {
    public void onUploadFailed(UploadFileInfo uploadFileInfo, String str, String str2) {
    }

    public void onUploadProgress(UploadFileInfo uploadFileInfo, long j2, long j3) {
    }

    public void onUploadRetry(String str, String str2) {
    }

    public void onUploadRetryResume() {
    }

    public void onUploadStarted(UploadFileInfo uploadFileInfo) {
    }

    @Deprecated
    public void onUploadSucceed(UploadFileInfo uploadFileInfo) {
    }

    public void onUploadSucceed(UploadFileInfo uploadFileInfo, VodUploadResult vodUploadResult) {
    }

    public void onUploadTokenExpired() {
    }
}
