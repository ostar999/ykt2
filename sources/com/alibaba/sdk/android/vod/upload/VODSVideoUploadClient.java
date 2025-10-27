package com.alibaba.sdk.android.vod.upload;

import com.alibaba.sdk.android.vod.upload.session.VodSessionCreateInfo;

/* loaded from: classes2.dex */
public interface VODSVideoUploadClient {
    void cancel();

    void init();

    void pause();

    void refreshSTSToken(String str, String str2, String str3, String str4);

    void release();

    void resume();

    void setAppVersion(String str);

    void setRecordUploadProgressEnabled(boolean z2);

    void setRegion(String str);

    void uploadWithVideoAndImg(VodSessionCreateInfo vodSessionCreateInfo, VODSVideoUploadCallback vODSVideoUploadCallback);
}
