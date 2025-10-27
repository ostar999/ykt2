package com.alibaba.sdk.android.vod.upload;

import com.alibaba.sdk.android.vod.upload.common.VodUploadStateType;
import com.alibaba.sdk.android.vod.upload.model.UploadFileInfo;
import com.alibaba.sdk.android.vod.upload.model.VodInfo;
import com.alibaba.sdk.android.vod.upload.session.VodHttpClientConfig;
import java.util.List;

/* loaded from: classes2.dex */
public interface VODUploadClient {
    void addFile(String str, VodInfo vodInfo);

    void addFile(String str, String str2, String str3, String str4);

    void addFile(String str, String str2, String str3, String str4, VodInfo vodInfo);

    void cancelFile(int i2);

    void clearFiles();

    void deleteFile(int i2);

    VodUploadStateType getStatus();

    void init(VODUploadCallback vODUploadCallback);

    void init(String str, String str2, VODUploadCallback vODUploadCallback);

    void init(String str, String str2, String str3, String str4, VODUploadCallback vODUploadCallback);

    List<UploadFileInfo> listFiles();

    void pause();

    void resume();

    void resumeFile(int i2);

    void resumeWithAuth(String str);

    void resumeWithToken(String str, String str2, String str3, String str4);

    void setAppId(String str);

    void setPartSize(long j2);

    void setRecordUploadProgressEnabled(boolean z2);

    void setRegion(String str);

    void setStorageLocation(String str);

    void setTemplateGroupId(String str);

    void setTranscodeMode(boolean z2);

    void setUploadAuthAndAddress(UploadFileInfo uploadFileInfo, String str, String str2);

    void setVodHttpClientConfig(VodHttpClientConfig vodHttpClientConfig);

    void setWorkflowId(String str);

    void start();

    void stop();
}
