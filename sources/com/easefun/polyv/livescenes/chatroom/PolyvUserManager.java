package com.easefun.polyv.livescenes.chatroom;

import com.plv.livescenes.chatroom.PLVUserManager;

@Deprecated
/* loaded from: classes3.dex */
public class PolyvUserManager {
    private static final PolyvUserManager ourInstance = new PolyvUserManager();

    private PolyvUserManager() {
    }

    public static PolyvUserManager getInstance() {
        return ourInstance;
    }

    public String getAvatar() {
        return PLVUserManager.getInstance().getAvatar();
    }

    public String getNickName() {
        return PLVUserManager.getInstance().getNickName();
    }

    public String getRoomId() {
        return PLVUserManager.getInstance().getRoomId();
    }

    public String getUserId() {
        return PLVUserManager.getInstance().getUserId();
    }

    public void initUserInfo(String str, String str2, String str3, String str4) {
        PLVUserManager.getInstance().initUserInfo(str, str2, str3, str4);
    }

    public void setAvatar(String str) {
        PLVUserManager.getInstance().setAvatar(str);
    }

    public void setNickName(String str) {
        PLVUserManager.getInstance().setNickName(str);
    }

    public void setRoomId(String str) {
        PLVUserManager.getInstance().setRoomId(str);
    }

    public void setUserId(String str) {
        PLVUserManager.getInstance().setUserId(str);
    }
}
