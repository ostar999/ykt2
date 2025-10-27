package com.plv.livescenes.linkmic.vo;

import cn.hutool.core.text.CharPool;

/* loaded from: classes4.dex */
public class PLVLinkMicEngineParam {
    private String channelId;
    private String groupId;
    private String nickName;
    private String viewerId;
    private String viewerType;

    public String getChannelId() {
        return this.channelId;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public String getNickName() {
        return this.nickName;
    }

    public String getViewerId() {
        return this.viewerId;
    }

    public String getViewerType() {
        return this.viewerType;
    }

    public PLVLinkMicEngineParam setChannelId(String str) {
        this.channelId = str;
        return this;
    }

    public PLVLinkMicEngineParam setGroupId(String str) {
        this.groupId = str;
        return this;
    }

    public PLVLinkMicEngineParam setNickName(String str) {
        this.nickName = str;
        return this;
    }

    public PLVLinkMicEngineParam setViewerId(String str) {
        this.viewerId = str;
        return this;
    }

    public PLVLinkMicEngineParam setViewerType(String str) {
        this.viewerType = str;
        return this;
    }

    public String toString() {
        return "PLVLinkMicEngineParam{channelId='" + this.channelId + CharPool.SINGLE_QUOTE + ", groupId='" + this.groupId + CharPool.SINGLE_QUOTE + ", viewerType='" + this.viewerType + CharPool.SINGLE_QUOTE + ", viewerId='" + this.viewerId + CharPool.SINGLE_QUOTE + ", nickName='" + this.nickName + CharPool.SINGLE_QUOTE + '}';
    }
}
