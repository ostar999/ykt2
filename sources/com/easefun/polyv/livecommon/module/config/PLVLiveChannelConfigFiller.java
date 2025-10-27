package com.easefun.polyv.livecommon.module.config;

import com.plv.livescenes.config.PLVLiveChannelType;
import com.plv.livescenes.playback.video.PLVPlaybackListType;
import com.plv.socket.user.PLVSocketUserConstant;

/* loaded from: classes3.dex */
public class PLVLiveChannelConfigFiller {
    private static PLVLiveChannelConfig channelConfig = new PLVLiveChannelConfig();

    public static PLVLiveChannelConfig generateNewChannelConfig() {
        return new PLVLiveChannelConfig(channelConfig);
    }

    public static void setChannelType(PLVLiveChannelType channelType) {
        channelConfig.setChannelType(channelType);
    }

    public static void setColinMicType(String colinMicType) {
        channelConfig.setColinMicType(colinMicType);
    }

    public static void setHiClassConfig(String token, long lessonId, String courseCode) {
        channelConfig.setHiClassConfig(token, lessonId, courseCode);
    }

    public static void setIsLive(boolean isLive) {
        channelConfig.setIsLive(isLive);
    }

    public static void setLiveStreamingWhenLogin(boolean liveStreamingWhenLogin) {
        channelConfig.setLiveStreamingWhenLogin(liveStreamingWhenLogin);
    }

    public static void setupAccount(String userId, String appId, String appSecret) {
        channelConfig.setupAccount(userId, appId, appSecret);
    }

    public static void setupChannelId(String channelId) {
        channelConfig.setupChannelId(channelId);
    }

    public static void setupChannelName(String channelName) {
        channelConfig.setupChannelName(channelName);
    }

    public static void setupUser(String viewerId, String viewerName) {
        channelConfig.setupUser(viewerId, viewerName, PLVSocketUserConstant.STUDENT_AVATAR_URL, "slice");
    }

    public static void setupVid(String vid) {
        channelConfig.setupVid(vid);
    }

    public static void setupVideoListType(PLVPlaybackListType videoListType) {
        channelConfig.setupVideoListType(videoListType);
    }

    public static void setupUser(String viewerId, String viewerName, String viewerAvatar) {
        channelConfig.setupUser(viewerId, viewerName, viewerAvatar, "slice");
    }

    public static void setupUser(String viewerId, String viewerName, String viewerAvatar, String viewerType) {
        channelConfig.setupUser(viewerId, viewerName, viewerAvatar, viewerType);
    }

    public static void setupUser(String viewerId, String viewerName, String viewerAvatar, String viewerType, String actor) {
        channelConfig.setupUser(viewerId, viewerName, viewerAvatar, viewerType, actor);
    }

    public static void setupUser(String viewerId, String viewerName, String viewerAvatar, String viewerType, String actor, String param4, String param5) {
        channelConfig.setupUser(viewerId, viewerName, viewerAvatar, viewerType, actor, param4, param5);
    }
}
