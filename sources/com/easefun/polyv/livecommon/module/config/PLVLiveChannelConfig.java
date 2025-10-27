package com.easefun.polyv.livecommon.module.config;

import android.text.TextUtils;
import com.easefun.polyv.livecommon.module.utils.PLVSystemUtils;
import com.plv.livescenes.config.PLVLiveChannelType;
import com.plv.livescenes.playback.video.PLVPlaybackListType;
import com.plv.socket.user.PLVSocketUserConstant;
import com.plv.thirdpart.blankj.utilcode.util.Utils;

/* loaded from: classes3.dex */
public class PLVLiveChannelConfig {
    private Account account;
    private String channelId;
    private String channelName;
    private PLVLiveChannelType channelType;
    private String colinMicType;
    private HiClassConfig hiClassConfig;
    private boolean isLive;
    private boolean isLiveStreamingWhenLogin;
    private boolean isSkipAutoLinkMic;
    private User user;
    private String vid;
    private PLVPlaybackListType videoListType;

    public static class Account {
        private String appId;
        private String appSecret;
        private String userId;

        public Account() {
        }

        public String getAppId() {
            return this.appId;
        }

        public String getAppSecret() {
            return this.appSecret;
        }

        public String getUserId() {
            return this.userId;
        }

        public Account(Account old) {
            this.userId = old.getUserId();
            this.appId = old.getAppId();
            this.appSecret = old.getAppSecret();
        }
    }

    public static class HiClassConfig {
        private String courseCode;
        private long lessonId;
        private String token;

        public HiClassConfig() {
        }

        public String getCourseCode() {
            return this.courseCode;
        }

        public long getLessonId() {
            return this.lessonId;
        }

        public String getToken() {
            return this.token;
        }

        public void setCourseCode(String courseCode) {
            this.courseCode = courseCode;
        }

        public void setLessonId(long lessonId) {
            this.lessonId = lessonId;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public HiClassConfig(HiClassConfig old) {
            this.token = old.token;
            this.lessonId = old.lessonId;
            this.courseCode = old.courseCode;
        }
    }

    public static class User {
        private String actor;
        private String param4;
        private String param5;
        private String viewerAvatar;
        private String viewerId;
        private String viewerName;
        private String viewerType;

        public User() {
        }

        public Object clone() throws CloneNotSupportedException {
            return super.clone();
        }

        public String getActor() {
            return this.actor;
        }

        public String getParam4() {
            return this.param4;
        }

        public String getParam5() {
            return this.param5;
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

        public String getViewerType() {
            return this.viewerType;
        }

        public User(User old) {
            this.viewerId = old.viewerId;
            this.viewerName = old.viewerName;
            this.viewerAvatar = old.viewerAvatar;
            this.viewerType = old.viewerType;
            this.actor = old.actor;
            this.param4 = old.param4;
            this.param5 = old.param5;
        }
    }

    public PLVLiveChannelConfig() {
        this.isSkipAutoLinkMic = false;
        this.isLiveStreamingWhenLogin = false;
        this.account = new Account();
        this.user = new User();
        this.hiClassConfig = new HiClassConfig();
    }

    public Account getAccount() {
        return this.account;
    }

    public String getChannelId() {
        return this.channelId;
    }

    public String getChannelName() {
        return this.channelName;
    }

    public PLVLiveChannelType getChannelType() {
        return this.channelType;
    }

    public HiClassConfig getHiClassConfig() {
        return this.hiClassConfig;
    }

    public User getUser() {
        return this.user;
    }

    public String getVid() {
        return this.vid;
    }

    public PLVPlaybackListType getVideoListType() {
        return this.videoListType;
    }

    public boolean isAloneChannelType() {
        return this.channelType == PLVLiveChannelType.ALONE;
    }

    public boolean isAutoLinkToGuest() {
        return TextUtils.isEmpty(this.colinMicType) || ("auto".equals(this.colinMicType) && !this.isSkipAutoLinkMic);
    }

    public boolean isLive() {
        return this.isLive;
    }

    public boolean isLiveStreamingWhenLogin() {
        return this.isLiveStreamingWhenLogin;
    }

    public boolean isPPTChannelType() {
        return this.channelType == PLVLiveChannelType.PPT;
    }

    public void setChannelType(PLVLiveChannelType channelType) {
        this.channelType = channelType;
    }

    public void setColinMicType(String colinMicType) {
        this.colinMicType = colinMicType;
    }

    public void setHiClassConfig(String token, long lessonId, String courseCode) {
        this.hiClassConfig.token = token;
        this.hiClassConfig.lessonId = lessonId;
        this.hiClassConfig.courseCode = courseCode;
    }

    public void setIsLive(boolean isLive) {
        this.isLive = isLive;
    }

    public void setLiveStreamingWhenLogin(boolean liveStreamingWhenLogin) {
        this.isLiveStreamingWhenLogin = liveStreamingWhenLogin;
    }

    public void setSkipAutoLinkMic(boolean skipAutoLinkMic) {
        this.isSkipAutoLinkMic = skipAutoLinkMic;
    }

    public void setupAccount(String userId, String appId, String appSecret) {
        this.account.userId = userId;
        this.account.appId = appId;
        this.account.appSecret = appSecret;
    }

    public void setupChannelId(String channelId) {
        this.channelId = channelId;
    }

    public void setupChannelName(String channelName) {
        this.channelName = channelName;
    }

    public void setupUser(String viewerId, String viewerName, String viewerAvatar, String viewerType) {
        setupUser(viewerId, viewerName, viewerAvatar, viewerType, null);
    }

    public void setupVid(String vid) {
        this.vid = vid;
    }

    public void setupVideoListType(PLVPlaybackListType videoListType) {
        this.videoListType = videoListType;
    }

    public void setupUser(String viewerId, String viewerName, String viewerAvatar, String viewerType, String actor) {
        setupUser(viewerId, viewerName, viewerAvatar, viewerType, actor, "", "");
    }

    public void setupUser(String viewerId, String viewerName, String viewerAvatar, String viewerType, String actor, String param4, String param5) {
        User user = this.user;
        if (TextUtils.isEmpty(viewerId)) {
            viewerId = PLVSystemUtils.getAndroidId(Utils.getApp()) + "";
        }
        user.viewerId = viewerId;
        User user2 = this.user;
        if (TextUtils.isEmpty(viewerName)) {
            viewerName = "观众" + PLVSystemUtils.getAndroidId(Utils.getApp());
        }
        user2.viewerName = viewerName;
        User user3 = this.user;
        if (TextUtils.isEmpty(viewerAvatar)) {
            viewerAvatar = PLVSocketUserConstant.STUDENT_AVATAR_URL;
        }
        user3.viewerAvatar = viewerAvatar;
        User user4 = this.user;
        if (TextUtils.isEmpty(viewerType)) {
            viewerType = "student";
        }
        user4.viewerType = viewerType;
        this.user.actor = actor;
        this.user.param4 = param4;
        this.user.param5 = param5;
    }

    public PLVLiveChannelConfig(PLVLiveChannelConfig old) {
        this.isSkipAutoLinkMic = false;
        this.isLiveStreamingWhenLogin = false;
        this.account = new Account(old.account);
        this.user = new User(old.user);
        this.channelId = old.channelId;
        this.vid = old.vid;
        this.videoListType = old.videoListType;
        this.isLive = old.isLive;
        this.channelType = old.channelType;
        this.channelName = old.channelName;
        this.colinMicType = old.colinMicType;
        this.hiClassConfig = new HiClassConfig(old.hiClassConfig);
        this.isLiveStreamingWhenLogin = old.isLiveStreamingWhenLogin;
    }
}
