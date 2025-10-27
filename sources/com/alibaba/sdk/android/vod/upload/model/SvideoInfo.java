package com.alibaba.sdk.android.vod.upload.model;

import java.util.List;

/* loaded from: classes2.dex */
public class SvideoInfo {
    private Integer cateId;
    private String desc;
    private boolean isProcess = true;
    private boolean isShowWaterMark = false;
    private int priority = 6;
    private List<String> tags;
    private String title;
    private String userData;

    public Integer getCateId() {
        return this.cateId;
    }

    public String getDesc() {
        return this.desc;
    }

    public int getPriority() {
        return this.priority;
    }

    public List<String> getTags() {
        return this.tags;
    }

    public String getTitle() {
        return this.title;
    }

    public String getUserData() {
        return this.userData;
    }

    public boolean isProcess() {
        return this.isProcess;
    }

    public boolean isShowWaterMark() {
        return this.isShowWaterMark;
    }

    public void setCateId(Integer num) {
        this.cateId = num;
    }

    public void setDesc(String str) {
        this.desc = str;
    }

    public void setPriority(int i2) {
        this.priority = i2;
    }

    public void setProcess(boolean z2) {
        this.isProcess = z2;
    }

    public void setShowWaterMark(boolean z2) {
        this.isShowWaterMark = z2;
    }

    public void setTags(List<String> list) {
        this.tags = list;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public void setUserData(String str) {
        this.userData = str;
    }
}
