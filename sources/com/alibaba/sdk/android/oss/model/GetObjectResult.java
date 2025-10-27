package com.alibaba.sdk.android.oss.model;

import com.alibaba.sdk.android.oss.internal.CheckCRC64DownloadInputStream;
import java.io.InputStream;

/* loaded from: classes2.dex */
public class GetObjectResult extends OSSResult {
    private long contentLength;
    private ObjectMetadata metadata = new ObjectMetadata();
    private InputStream objectContent;

    @Override // com.alibaba.sdk.android.oss.model.OSSResult
    public Long getClientCRC() {
        InputStream inputStream = this.objectContent;
        return (inputStream == null || !(inputStream instanceof CheckCRC64DownloadInputStream)) ? super.getClientCRC() : Long.valueOf(((CheckCRC64DownloadInputStream) inputStream).getClientCRC64());
    }

    public long getContentLength() {
        return this.contentLength;
    }

    public ObjectMetadata getMetadata() {
        return this.metadata;
    }

    public InputStream getObjectContent() {
        return this.objectContent;
    }

    public void setContentLength(long j2) {
        this.contentLength = j2;
    }

    public void setMetadata(ObjectMetadata objectMetadata) {
        this.metadata = objectMetadata;
    }

    public void setObjectContent(InputStream inputStream) {
        this.objectContent = inputStream;
    }
}
