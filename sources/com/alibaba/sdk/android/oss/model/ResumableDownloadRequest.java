package com.alibaba.sdk.android.oss.model;

import android.support.v4.media.session.PlaybackStateCompat;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import java.util.Map;

/* loaded from: classes2.dex */
public class ResumableDownloadRequest extends OSSRequest {
    private String bucketName;
    private String checkPointFilePath;
    private String downloadToFilePath;
    private Boolean enableCheckPoint;
    private String objectKey;
    private long partSize;
    private OSSProgressCallback progressListener;
    private Range range;
    private Map<String, String> requestHeader;

    public ResumableDownloadRequest(String str, String str2, String str3) {
        this.enableCheckPoint = Boolean.FALSE;
        this.partSize = PlaybackStateCompat.ACTION_SET_REPEAT_MODE;
        this.bucketName = str;
        this.objectKey = str2;
        this.downloadToFilePath = str3;
    }

    public String getBucketName() {
        return this.bucketName;
    }

    public String getCheckPointFilePath() {
        return this.checkPointFilePath;
    }

    public String getDownloadToFilePath() {
        return this.downloadToFilePath;
    }

    public Boolean getEnableCheckPoint() {
        return this.enableCheckPoint;
    }

    public String getObjectKey() {
        return this.objectKey;
    }

    public long getPartSize() {
        return this.partSize;
    }

    public OSSProgressCallback getProgressListener() {
        return this.progressListener;
    }

    public Range getRange() {
        return this.range;
    }

    public Map<String, String> getRequestHeader() {
        return this.requestHeader;
    }

    public String getTempFilePath() {
        return this.downloadToFilePath + ".tmp";
    }

    public void setBucketName(String str) {
        this.bucketName = str;
    }

    public void setCheckPointFilePath(String str) {
        this.checkPointFilePath = str;
    }

    public void setDownloadToFilePath(String str) {
        this.downloadToFilePath = str;
    }

    public void setEnableCheckPoint(Boolean bool) {
        this.enableCheckPoint = bool;
    }

    public void setObjectKey(String str) {
        this.objectKey = str;
    }

    public void setPartSize(long j2) {
        this.partSize = j2;
    }

    public void setProgressListener(OSSProgressCallback oSSProgressCallback) {
        this.progressListener = oSSProgressCallback;
    }

    public void setRange(Range range) {
        this.range = range;
    }

    public void setRequestHeader(Map<String, String> map) {
        this.requestHeader = map;
    }

    public ResumableDownloadRequest(String str, String str2, String str3, String str4) {
        this.partSize = PlaybackStateCompat.ACTION_SET_REPEAT_MODE;
        this.bucketName = str;
        this.objectKey = str2;
        this.downloadToFilePath = str3;
        this.enableCheckPoint = Boolean.TRUE;
        this.checkPointFilePath = str4;
    }
}
