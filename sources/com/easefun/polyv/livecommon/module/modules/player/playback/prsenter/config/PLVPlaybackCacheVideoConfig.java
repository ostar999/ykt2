package com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.config;

import cn.hutool.core.text.StrPool;
import com.plv.livescenes.config.PLVLiveChannelType;
import com.plv.livescenes.playback.video.PLVPlaybackListType;

/* loaded from: classes3.dex */
public class PLVPlaybackCacheVideoConfig {
    private String channelId;
    private PLVLiveChannelType channelType;
    private PLVPlaybackListType playbackListType;
    private String vid;
    private String videoPoolId;
    private String viewerAvatar;
    private String viewerId;
    private String viewerName;

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

    public String getVideoPoolId() {
        return this.videoPoolId;
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

    public PLVPlaybackCacheVideoConfig setChannelId(String channelId) {
        this.channelId = channelId;
        return this;
    }

    public PLVPlaybackCacheVideoConfig setChannelType(PLVLiveChannelType channelType) {
        this.channelType = channelType;
        return this;
    }

    public PLVPlaybackCacheVideoConfig setPlaybackListType(PLVPlaybackListType playbackListType) {
        this.playbackListType = playbackListType;
        return this;
    }

    public PLVPlaybackCacheVideoConfig setVid(String vid) {
        this.vid = vid;
        return this;
    }

    public PLVPlaybackCacheVideoConfig setVideoPoolId(String videoPoolId) {
        this.videoPoolId = videoPoolId;
        return this;
    }

    public PLVPlaybackCacheVideoConfig setVideoPoolIdByVid(final String vid) {
        if (vid == null) {
            return this;
        }
        if (vid.contains(StrPool.UNDERLINE)) {
            setVideoPoolId(vid.substring(0, vid.lastIndexOf(StrPool.UNDERLINE)));
        } else {
            setVideoPoolId(vid);
        }
        return this;
    }

    public PLVPlaybackCacheVideoConfig setViewerAvatar(String viewerAvatar) {
        this.viewerAvatar = viewerAvatar;
        return this;
    }

    public PLVPlaybackCacheVideoConfig setViewerId(String viewerId) {
        this.viewerId = viewerId;
        return this;
    }

    public PLVPlaybackCacheVideoConfig setViewerName(String viewerName) {
        this.viewerName = viewerName;
        return this;
    }
}
