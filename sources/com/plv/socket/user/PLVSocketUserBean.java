package com.plv.socket.user;

import cn.hutool.core.text.CharPool;
import com.plv.foundationsdk.model.PLVFoundationVO;
import com.plv.socket.event.PLVEventHelper;

/* loaded from: classes5.dex */
public class PLVSocketUserBean implements PLVFoundationVO {
    private String actor;
    private PLVAuthorizationBean authorization;
    private boolean banned;
    private String channelId;
    private PLVClassStatusBean classStatus;
    private String clientIp;
    private String kickRefer;
    private String nick;
    private String pic;
    private String roomId;
    private String uid;
    private String userId;
    private String userSource;
    private String userType;

    private boolean isBannedWithClassStatus() {
        PLVClassStatusBean pLVClassStatusBean = this.classStatus;
        return pLVClassStatusBean != null && pLVClassStatusBean.getBanned() == 1;
    }

    public String getActor() {
        return PLVEventHelper.fixActor(this.actor, this.userType);
    }

    public PLVAuthorizationBean getAuthorization() {
        return this.authorization;
    }

    public String getChannelId() {
        return this.channelId;
    }

    public PLVClassStatusBean getClassStatus() {
        return this.classStatus;
    }

    public String getClientIp() {
        return this.clientIp;
    }

    public String getKickRefer() {
        return this.kickRefer;
    }

    public String getNick() {
        return this.nick;
    }

    public String getPic() {
        return PLVEventHelper.fixChatPic(this.pic);
    }

    public String getRoomId() {
        return this.roomId;
    }

    public String getUid() {
        return this.uid;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getUserSource() {
        return this.userSource;
    }

    public String getUserType() {
        return this.userType;
    }

    public boolean isBanned() {
        return this.classStatus != null ? isBannedWithClassStatus() : this.banned;
    }

    public boolean isGuest() {
        return "guest".equals(this.userType);
    }

    public boolean isTeacher() {
        return "teacher".equals(this.userType);
    }

    public void setActor(String str) {
        this.actor = str;
    }

    public void setAuthorization(PLVAuthorizationBean pLVAuthorizationBean) {
        this.authorization = pLVAuthorizationBean;
    }

    public void setBanned(boolean z2) {
        this.banned = z2;
        PLVClassStatusBean pLVClassStatusBean = this.classStatus;
        if (pLVClassStatusBean != null) {
            pLVClassStatusBean.setBanned(z2 ? 1 : 0);
        }
    }

    public void setChannelId(String str) {
        this.channelId = str;
    }

    public void setClassStatus(PLVClassStatusBean pLVClassStatusBean) {
        this.classStatus = pLVClassStatusBean;
    }

    public void setClientIp(String str) {
        this.clientIp = str;
    }

    public void setKickRefer(String str) {
        this.kickRefer = str;
    }

    public void setNick(String str) {
        this.nick = str;
    }

    public void setPic(String str) {
        this.pic = str;
    }

    public void setRoomId(String str) {
        this.roomId = str;
    }

    public void setUid(String str) {
        this.uid = str;
    }

    public void setUserId(String str) {
        this.userId = str;
    }

    public void setUserSource(String str) {
        this.userSource = str;
    }

    public void setUserType(String str) {
        this.userType = str;
    }

    public String toString() {
        return "PLVSocketUserBean{authorization=" + this.authorization + ", actor='" + this.actor + CharPool.SINGLE_QUOTE + ", banned=" + this.banned + ", channelId='" + this.channelId + CharPool.SINGLE_QUOTE + ", clientIp='" + this.clientIp + CharPool.SINGLE_QUOTE + ", nick='" + this.nick + CharPool.SINGLE_QUOTE + ", pic='" + this.pic + CharPool.SINGLE_QUOTE + ", roomId='" + this.roomId + CharPool.SINGLE_QUOTE + ", uid='" + this.uid + CharPool.SINGLE_QUOTE + ", userId='" + this.userId + CharPool.SINGLE_QUOTE + ", userSource='" + this.userSource + CharPool.SINGLE_QUOTE + ", userType='" + this.userType + CharPool.SINGLE_QUOTE + '}';
    }
}
