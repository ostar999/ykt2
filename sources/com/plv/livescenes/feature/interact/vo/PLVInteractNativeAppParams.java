package com.plv.livescenes.feature.interact.vo;

/* loaded from: classes4.dex */
public class PLVInteractNativeAppParams {
    private String appId;
    private String appSecret;
    private ChannelInfoDTO channelInfo;
    private String sessionId;
    private String token;
    private UserInfoDTO userInfo;

    public static class ChannelInfoDTO {
        private String channelId;
        private String roomId;

        public String getChannelId() {
            return this.channelId;
        }

        public String getRoomId() {
            return this.roomId;
        }

        public ChannelInfoDTO setChannelId(String str) {
            this.channelId = str;
            return this;
        }

        public ChannelInfoDTO setRoomId(String str) {
            this.roomId = str;
            return this;
        }
    }

    public static class UserInfoDTO {
        private String nick;
        private String pic;
        private String userId;

        public String getNick() {
            return this.nick;
        }

        public String getPic() {
            return this.pic;
        }

        public String getUserId() {
            return this.userId;
        }

        public UserInfoDTO setNick(String str) {
            this.nick = str;
            return this;
        }

        public UserInfoDTO setPic(String str) {
            this.pic = str;
            return this;
        }

        public UserInfoDTO setUserId(String str) {
            this.userId = str;
            return this;
        }
    }

    public String getAppId() {
        return this.appId;
    }

    public String getAppSecret() {
        return this.appSecret;
    }

    public ChannelInfoDTO getChannelInfo() {
        return this.channelInfo;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public String getToken() {
        return this.token;
    }

    public UserInfoDTO getUserInfo() {
        return this.userInfo;
    }

    public PLVInteractNativeAppParams setAppId(String str) {
        this.appId = str;
        return this;
    }

    public PLVInteractNativeAppParams setAppSecret(String str) {
        this.appSecret = str;
        return this;
    }

    public PLVInteractNativeAppParams setChannelInfo(ChannelInfoDTO channelInfoDTO) {
        this.channelInfo = channelInfoDTO;
        return this;
    }

    public PLVInteractNativeAppParams setSessionId(String str) {
        this.sessionId = str;
        return this;
    }

    public PLVInteractNativeAppParams setToken(String str) {
        this.token = str;
        return this;
    }

    public PLVInteractNativeAppParams setUserInfo(UserInfoDTO userInfoDTO) {
        this.userInfo = userInfoDTO;
        return this;
    }
}
