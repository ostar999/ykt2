package com.alibaba.sdk.android.vod.upload.internal;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.vod.upload.model.OSSConfig;
import com.alibaba.sdk.android.vod.upload.model.UploadFileInfo;
import java.io.FileNotFoundException;

/* loaded from: classes2.dex */
public interface OSSUploader {
    void cancel();

    void init(OSSConfig oSSConfig, OSSUploadListener oSSUploadListener);

    void pause();

    void resume();

    void setOSSClientConfiguration(ClientConfiguration clientConfiguration);

    void setRecordUploadProgressEnabled(boolean z2);

    void start(UploadFileInfo uploadFileInfo) throws FileNotFoundException;
}
