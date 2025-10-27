package com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database.entity;

import cn.hutool.core.text.CharPool;
import com.plv.livescenes.config.PLVLiveChannelType;
import com.plv.livescenes.playback.video.PLVPlaybackListType;

/* loaded from: classes3.dex */
public class PLVPlaybackCacheViewerInfoVO {
    private String channelId;
    private PLVLiveChannelType channelType;
    private PLVPlaybackListType playbackListType;
    private String vid;
    private String viewerAvatar;
    private String viewerId;
    private String viewerName;

    public PLVPlaybackCacheViewerInfoVO copy() {
        PLVPlaybackCacheViewerInfoVO pLVPlaybackCacheViewerInfoVO = new PLVPlaybackCacheViewerInfoVO();
        pLVPlaybackCacheViewerInfoVO.setChannelId(this.channelId);
        pLVPlaybackCacheViewerInfoVO.setChannelType(this.channelType);
        pLVPlaybackCacheViewerInfoVO.setVid(this.vid);
        pLVPlaybackCacheViewerInfoVO.setViewerId(this.viewerId);
        pLVPlaybackCacheViewerInfoVO.setViewerName(this.viewerName);
        pLVPlaybackCacheViewerInfoVO.setViewerAvatar(this.viewerAvatar);
        pLVPlaybackCacheViewerInfoVO.setPlaybackListType(this.playbackListType);
        return pLVPlaybackCacheViewerInfoVO;
    }

    public String getChannelId() {
        return this.channelId;
    }

    public PLVLiveChannelType getChannelType() {
        return this.channelType;
    }

    public PLVPlaybackListType getPlaybackListType() {
        return this.playbackListType;
    }

    public String getVid() {
        return this.vid;
    }

    public String getViewerAvatar() {
        return this.viewerAvatar;
    }

    public String getViewerId() {
        return this.viewerId;
    }

    public String getViewerName() {
        return this.viewerName;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public void setChannelType(PLVLiveChannelType channelType) {
        this.channelType = channelType;
    }

    public void setPlaybackListType(PLVPlaybackListType playbackListType) {
        this.playbackListType = playbackListType;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public void setViewerAvatar(String viewerAvatar) {
        this.viewerAvatar = viewerAvatar;
    }

    public void setViewerId(String viewerId) {
        this.viewerId = viewerId;
    }

    public void setViewerName(String viewerName) {
        this.viewerName = viewerName;
    }

    public String toString() {
        return "PLVPlaybackCacheViewerInfoVO{channelId='" + this.channelId + CharPool.SINGLE_QUOTE + ", channelType=" + this.channelType + ", vid='" + this.vid + CharPool.SINGLE_QUOTE + ", viewerId='" + this.viewerId + CharPool.SINGLE_QUOTE + ", viewerName='" + this.viewerName + CharPool.SINGLE_QUOTE + ", viewerAvatar='" + this.viewerAvatar + CharPool.SINGLE_QUOTE + ", playbackListType=" + this.playbackListType + '}';
    }
}
