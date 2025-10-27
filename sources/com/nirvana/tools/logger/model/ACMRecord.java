package com.nirvana.tools.logger.model;

/* loaded from: classes4.dex */
public class ACMRecord {
    public static final int FLAG_ALL = -1;
    public static final int UPLOAD_FLAG_FAILED = 1;
    public static final int UPLOAD_FLAG_NONE = 0;
    public static final int UPLOAD_FLAG_SUCCEED = 2;
    public static final int UPLOAD_STRATEGY_ALL_NETWORK = 2;
    public static final int UPLOAD_STRATEGY_ONLY_WIFI = 1;
    private String content;
    private long id;
    private long timestamp = System.currentTimeMillis();
    private int strategy = 2;
    private int uploadCount = 0;
    private int uploadFlag = 0;

    public String getContent() {
        return this.content;
    }

    public long getId() {
        return this.id;
    }

    public int getStrategy() {
        return this.strategy;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public int getUploadCount() {
        return this.uploadCount;
    }

    public int getUploadFlag() {
        return this.uploadFlag;
    }

    public void setContent(String str) {
        this.content = str;
    }

    public void setId(long j2) {
        this.id = j2;
    }

    public void setStrategy(int i2) {
        this.strategy = i2;
    }

    public void setTimestamp(long j2) {
        this.timestamp = j2;
    }

    public void setUploadCount(int i2) {
        this.uploadCount = i2;
    }

    public void setUploadFlag(int i2) {
        this.uploadFlag = i2;
    }
}
