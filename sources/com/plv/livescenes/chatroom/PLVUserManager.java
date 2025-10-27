package com.plv.livescenes.chatroom;

/* loaded from: classes4.dex */
public class PLVUserManager {
    private static final PLVUserManager ourInstance = new PLVUserManager();
    private String avatar;
    private String nickName;
    private String roomId;
    private String userId;

    private PLVUserManager() {
    }

    public static PLVUserManager getInstance() {
        return ourInstance;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public String getNickName() {
        return this.nickName;
    }

    public String getRoomId() {
        return this.roomId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void initUserInfo(String str, String str2, String str3, String str4) {
        this.avatar = str4;
        this.nickName = str;
        this.roomId = str3;
        this.userId = str2;
    }

    public void setAvatar(String str) {
        this.avatar = str;
    }

    public void setNickName(String str) {
        this.nickName = str;
    }

    public void setRoomId(String str) {
        this.roomId = str;
    }

    public void setUserId(String str) {
        this.userId = str;
    }
}
