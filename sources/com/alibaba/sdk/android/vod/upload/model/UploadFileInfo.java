package com.alibaba.sdk.android.vod.upload.model;

import com.alibaba.sdk.android.vod.upload.common.UploadStateType;
import com.alibaba.sdk.android.vod.upload.common.utils.StringUtil;

/* loaded from: classes2.dex */
public class UploadFileInfo {
    public static final int UPLOAD_FILE_TYPE_IMAGE = 0;
    public static final int UPLOAD_FILE_TYPE_VIDEO = 1;
    private String bucket;
    private String endpoint;
    private String filePath;
    private int fileType = 1;
    private String object;
    private UploadStateType status;
    private VodInfo vodInfo;

    public boolean equals(UploadFileInfo uploadFileInfo) {
        return (uploadFileInfo == null || StringUtil.isEmpty(uploadFileInfo.filePath) || !uploadFileInfo.filePath.equals(this.filePath) || StringUtil.isEmpty(uploadFileInfo.endpoint) || !uploadFileInfo.endpoint.equals(this.endpoint) || StringUtil.isEmpty(uploadFileInfo.bucket) || !uploadFileInfo.bucket.equals(this.bucket) || StringUtil.isEmpty(uploadFileInfo.object) || !uploadFileInfo.object.equals(this.object) || StringUtil.isEmpty(uploadFileInfo.object) || !uploadFileInfo.object.equals(this.object)) ? false : true;
    }

    public String getBucket() {
        return this.bucket;
    }

    public String getEndpoint() {
        return this.endpoint;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public int getFileType() {
        return this.fileType;
    }

    public String getObject() {
        return this.object;
    }

    public UploadStateType getStatus() {
        return this.status;
    }

    public VodInfo getVodInfo() {
        return this.vodInfo;
    }

    public void setBucket(String str) {
        this.bucket = str;
    }

    public void setEndpoint(String str) {
        this.endpoint = str;
    }

    public void setFilePath(String str) {
        this.filePath = str;
    }

    public void setFileType(int i2) {
        this.fileType = i2;
    }

    public void setObject(String str) {
        this.object = str;
    }

    public void setStatus(UploadStateType uploadStateType) {
        this.status = uploadStateType;
    }

    public void setVodInfo(VodInfo vodInfo) {
        this.vodInfo = vodInfo;
    }
}
