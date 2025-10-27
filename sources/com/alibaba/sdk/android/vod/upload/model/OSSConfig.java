package com.alibaba.sdk.android.vod.upload.model;

import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationToken;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;

/* loaded from: classes2.dex */
public class OSSConfig {
    private String accessKeyId;
    private String accessKeyIdToVOD;
    private String accessKeySecret;
    private String accessKeySecretToVOD;
    private String expireTime;
    private String expireTimeToVOD;
    private long partSize;
    private String secrityToken;
    private String secrityTokenToVOD;
    private String uploadAddress;
    private String videoId;

    public String getAccessKeyId() {
        return this.accessKeyId;
    }

    public String getAccessKeyIdToVOD() {
        return this.accessKeyIdToVOD;
    }

    public String getAccessKeySecret() {
        return this.accessKeySecret;
    }

    public String getAccessKeySecretToVOD() {
        return this.accessKeySecretToVOD;
    }

    public String getExpireTime() {
        return this.expireTime;
    }

    public String getExpireTimeToVOD() {
        return this.expireTimeToVOD;
    }

    public long getPartSize() {
        return this.partSize;
    }

    public OSSCredentialProvider getProvider() {
        return (this.secrityToken == null || this.expireTime == null) ? new OSSPlainTextAKSKCredentialProvider(this.accessKeyId, this.accessKeySecret) : new OSSFederationCredentialProvider() { // from class: com.alibaba.sdk.android.vod.upload.model.OSSConfig.1
            @Override // com.alibaba.sdk.android.oss.common.auth.OSSFederationCredentialProvider, com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider
            public OSSFederationToken getFederationToken() {
                return new OSSFederationToken(OSSConfig.this.accessKeyId, OSSConfig.this.accessKeySecret, OSSConfig.this.secrityToken, OSSConfig.this.expireTime);
            }
        };
    }

    public String getSecrityToken() {
        return this.secrityToken;
    }

    public String getSecrityTokenToVOD() {
        return this.secrityTokenToVOD;
    }

    public String getUploadAddress() {
        return this.uploadAddress;
    }

    public String getVideoId() {
        return this.videoId;
    }

    public void setAccessKeyId(String str) {
        this.accessKeyId = str;
    }

    public void setAccessKeyIdToVOD(String str) {
        this.accessKeyIdToVOD = str;
    }

    public void setAccessKeySecret(String str) {
        this.accessKeySecret = str;
    }

    public void setAccessKeySecretToVOD(String str) {
        this.accessKeySecretToVOD = str;
    }

    public void setExpireTime(String str) {
        this.expireTime = str;
    }

    public void setExpireTimeToVOD(String str) {
        this.expireTimeToVOD = str;
    }

    public void setPartSize(long j2) {
        this.partSize = j2;
    }

    public void setSecrityToken(String str) {
        this.secrityToken = str;
    }

    public void setSecrityTokenToVOD(String str) {
        this.secrityTokenToVOD = str;
    }

    public void setUploadAddress(String str) {
        this.uploadAddress = str;
    }

    public void setVideoId(String str) {
        this.videoId = str;
    }
}
