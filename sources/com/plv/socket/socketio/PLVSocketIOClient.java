package com.plv.socket.socketio;

import com.plv.socket.user.PLVAuthorizationBean;

/* loaded from: classes5.dex */
public class PLVSocketIOClient {
    private static volatile PLVSocketIOClient socketIOClient;
    private String accountAppId;
    private String accountAppSecret;
    private String accountUserId;
    private String actor;
    private PLVAuthorizationBean authorization;
    private String avatarUrl;
    private String channelId;
    private String courseCode;
    private boolean isTeacherType;
    private long lessonId;
    private String micId;
    private String nickName;
    private String param4;
    private String param5;
    private String sdkVersion;
    private String socketUserId;
    private String token;
    private String userType;

    private PLVSocketIOClient() {
    }

    public static PLVSocketIOClient getInstance() {
        if (socketIOClient == null) {
            synchronized (PLVSocketIOClient.class) {
                if (socketIOClient == null) {
                    socketIOClient = new PLVSocketIOClient();
                }
            }
        }
        return socketIOClient;
    }

    public String getAccountAppId() {
        return this.accountAppId;
    }

    public String getAccountAppSecret() {
        return this.accountAppSecret;
    }

    public String getAccountUserId() {
        return this.accountUserId;
    }

    public String getActor() {
        return this.actor;
    }

    public PLVAuthorizationBean getAuthorization() {
        return this.authorization;
    }

    public String getAvatarUrl() {
        return this.avatarUrl;
    }

    public String getChannelId() {
        return this.channelId;
    }

    public String getCourseCode() {
        return this.courseCode;
    }

    public long getLessonId() {
        return this.lessonId;
    }

    public String getMicId() {
        return this.micId;
    }

    public String getNickName() {
        return this.nickName;
    }

    public String getParam4() {
        return this.param4;
    }

    public String getParam5() {
        return this.param5;
    }

    public String getSdkVersion() {
        return this.sdkVersion;
    }

    public String getSocketUserId() {
        return this.socketUserId;
    }

    public String getToken() {
        return this.token;
    }

    public String getUserType() {
        return this.userType;
    }

    public boolean isTeacherType() {
        return this.isTeacherType;
    }

    public PLVSocketIOClient setAccountAppId(String str) {
        this.accountAppId = str;
        return this;
    }

    public PLVSocketIOClient setAccountAppSecret(String str) {
        this.accountAppSecret = str;
        return this;
    }

    public PLVSocketIOClient setAccountUserId(String str) {
        this.accountUserId = str;
        return this;
    }

    public PLVSocketIOClient setActor(String str) {
        this.actor = str;
        return this;
    }

    public PLVSocketIOClient setAuthorization(PLVAuthorizationBean pLVAuthorizationBean) {
        this.authorization = pLVAuthorizationBean;
        return this;
    }

    public PLVSocketIOClient setAvatarUrl(String str) {
        this.avatarUrl = str;
        return this;
    }

    public PLVSocketIOClient setChannelId(String str) {
        this.channelId = str;
        return this;
    }

    public PLVSocketIOClient setMicId(String str) {
        this.micId = str;
        return this;
    }

    public PLVSocketIOClient setNickName(String str) {
        this.nickName = str;
        return this;
    }

    public PLVSocketIOClient setParam4(String str) {
        this.param4 = str;
        return this;
    }

    public PLVSocketIOClient setParam5(String str) {
        this.param5 = str;
        return this;
    }

    public PLVSocketIOClient setSdkVersion(String str) {
        this.sdkVersion = str;
        return this;
    }

    public PLVSocketIOClient setSocketUserId(String str) {
        this.socketUserId = str;
        return this;
    }

    public PLVSocketIOClient setUserType(String str) {
        this.userType = str;
        return this;
    }

    public PLVSocketIOClient setupHiClassConfig(String str, boolean z2, String str2, long j2) {
        this.token = str;
        this.isTeacherType = z2;
        this.courseCode = str2;
        this.lessonId = j2;
        return this;
    }
}
