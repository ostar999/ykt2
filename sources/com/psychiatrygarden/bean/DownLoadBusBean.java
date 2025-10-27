package com.psychiatrygarden.bean;

import com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadMediaInfo;
import java.io.Serializable;

/* loaded from: classes5.dex */
public class DownLoadBusBean implements Serializable {
    public AliyunDownloadMediaInfo aliyunDownloadMediaInfo;
    public String downStatus;
    public String errorMessage;

    /* renamed from: i, reason: collision with root package name */
    public int f15297i;
    public String requestId;

    public DownLoadBusBean() {
    }

    public DownLoadBusBean(String downStatus) {
        this.downStatus = downStatus;
    }

    public DownLoadBusBean(AliyunDownloadMediaInfo aliyunDownloadMediaInfo, String downStatus) {
        this.aliyunDownloadMediaInfo = aliyunDownloadMediaInfo;
        this.downStatus = downStatus;
    }

    public DownLoadBusBean(AliyunDownloadMediaInfo aliyunDownloadMediaInfo, String downStatus, int i2, String errorMessage, String requestId) {
        this.aliyunDownloadMediaInfo = aliyunDownloadMediaInfo;
        this.downStatus = downStatus;
        this.f15297i = i2;
        this.errorMessage = errorMessage;
        this.requestId = requestId;
    }

    public DownLoadBusBean(AliyunDownloadMediaInfo aliyunDownloadMediaInfo, String downStatus, int i2) {
        this.aliyunDownloadMediaInfo = aliyunDownloadMediaInfo;
        this.downStatus = downStatus;
        this.f15297i = i2;
    }
}
