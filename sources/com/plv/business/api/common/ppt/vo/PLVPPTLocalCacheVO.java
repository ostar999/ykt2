package com.plv.business.api.common.ppt.vo;

import cn.hutool.core.text.CharPool;

/* loaded from: classes4.dex */
public class PLVPPTLocalCacheVO {
    private String pptDataPath;
    private String pptHtmlFilePath;
    private String videoId;
    private String videoPoolId;

    public String getPptDataPath() {
        return this.pptDataPath;
    }

    public String getPptHtmlFilePath() {
        return this.pptHtmlFilePath;
    }

    public String getVideoId() {
        return this.videoId;
    }

    public String getVideoPoolId() {
        return this.videoPoolId;
    }

    public PLVPPTLocalCacheVO setPptDataPath(String str) {
        this.pptDataPath = str;
        return this;
    }

    public PLVPPTLocalCacheVO setPptHtmlFilePath(String str) {
        this.pptHtmlFilePath = str;
        return this;
    }

    public PLVPPTLocalCacheVO setVideoId(String str) {
        this.videoId = str;
        return this;
    }

    public PLVPPTLocalCacheVO setVideoPoolId(String str) {
        this.videoPoolId = str;
        return this;
    }

    public String toString() {
        return "PLVPPTLocalCacheVO{pptHtmlFilePath='" + this.pptHtmlFilePath + CharPool.SINGLE_QUOTE + ", pptDataPath='" + this.pptDataPath + CharPool.SINGLE_QUOTE + ", videoPoolId='" + this.videoPoolId + CharPool.SINGLE_QUOTE + ", videoId='" + this.videoId + CharPool.SINGLE_QUOTE + '}';
    }
}
