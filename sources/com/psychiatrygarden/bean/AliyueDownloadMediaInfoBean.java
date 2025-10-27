package com.psychiatrygarden.bean;

import com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadMediaInfo;

/* loaded from: classes5.dex */
public class AliyueDownloadMediaInfoBean {
    public AliyunDownloadMediaInfo aliyunDownloadMediaInfo;
    public boolean isSelect = false;
    public boolean isAllSelect = false;

    public AliyunDownloadMediaInfo getAliyunDownloadMediaInfo() {
        return this.aliyunDownloadMediaInfo;
    }

    public boolean isAllSelect() {
        return this.isAllSelect;
    }

    public boolean isSelect() {
        return this.isSelect;
    }

    public void setAliyunDownloadMediaInfo(AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
        this.aliyunDownloadMediaInfo = aliyunDownloadMediaInfo;
    }

    public void setAllSelect(boolean allSelect) {
        this.isAllSelect = allSelect;
    }

    public void setSelect(boolean select) {
        this.isSelect = select;
    }
}
