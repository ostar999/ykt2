package com.plv.livescenes.playback.vo;

import cn.hutool.core.text.CharPool;
import com.plv.livescenes.playback.video.PLVPlaybackListType;

/* loaded from: classes5.dex */
public class PLVPlaybackLocalCacheVO {
    private String channelId;
    private String channelSessionId;
    private String jsPath;
    private String liveType;
    private String originSessionId;
    private PLVPlaybackListType playbackListType;
    private String pptPath;
    private String videoId;
    private String videoPath;
    private String videoPoolId;

    public String getChannelId() {
        return this.channelId;
    }

    public String getChannelSessionId() {
        return this.channelSessionId;
    }

    public String getJsPath() {
        return this.jsPath;
    }

    public String getLiveType() {
        return this.liveType;
    }

    public String getOriginSessionId() {
        return this.originSessionId;
    }

    public PLVPlaybackListType getPlaybackListType() {
        return this.playbackListType;
    }

    public String getPptPath() {
        return this.pptPath;
    }

    public String getVideoId() {
        return this.videoId;
    }

    public String getVideoPath() {
        return this.videoPath;
    }

    public String getVideoPoolId() {
        return this.videoPoolId;
    }

    public PLVPlaybackLocalCacheVO setChannelId(String str) {
        this.channelId = str;
        return this;
    }

    public PLVPlaybackLocalCacheVO setChannelSessionId(String str) {
        this.channelSessionId = str;
        return this;
    }

    public PLVPlaybackLocalCacheVO setJsPath(String str) {
        this.jsPath = str;
        return this;
    }

    public PLVPlaybackLocalCacheVO setLiveType(String str) {
        this.liveType = str;
        return this;
    }

    public PLVPlaybackLocalCacheVO setOriginSessionId(String str) {
        this.originSessionId = str;
        return this;
    }

    public PLVPlaybackLocalCacheVO setPlaybackListType(PLVPlaybackListType pLVPlaybackListType) {
        this.playbackListType = pLVPlaybackListType;
        return this;
    }

    public PLVPlaybackLocalCacheVO setPptPath(String str) {
        this.pptPath = str;
        return this;
    }

    public PLVPlaybackLocalCacheVO setVideoId(String str) {
        this.videoId = str;
        return this;
    }

    public PLVPlaybackLocalCacheVO setVideoPath(String str) {
        this.videoPath = str;
        return this;
    }

    public PLVPlaybackLocalCacheVO setVideoPoolId(String str) {
        this.videoPoolId = str;
        return this;
    }

    public String toString() {
        return "PLVPlaybackLocalCacheVO{videoPoolId='" + this.videoPoolId + CharPool.SINGLE_QUOTE + ", videoId='" + this.videoId + CharPool.SINGLE_QUOTE + ", channelId='" + this.channelId + CharPool.SINGLE_QUOTE + ", playbackListType=" + this.playbackListType + ", liveType='" + this.liveType + CharPool.SINGLE_QUOTE + ", channelSessionId='" + this.channelSessionId + CharPool.SINGLE_QUOTE + ", originSessionId='" + this.originSessionId + CharPool.SINGLE_QUOTE + ", videoPath='" + this.videoPath + CharPool.SINGLE_QUOTE + ", jsPath='" + this.jsPath + CharPool.SINGLE_QUOTE + ", pptPath='" + this.pptPath + CharPool.SINGLE_QUOTE + '}';
    }
}
