package com.plv.livescenes.model;

import cn.hutool.core.text.CharPool;
import com.plv.business.model.PLVBaseVO;

/* loaded from: classes5.dex */
public class PLVLiveRestrictVO implements PLVBaseVO {
    private boolean canWatch;
    private int channelId;
    private String channelStatus;
    private String errorCode;
    private String sign;
    private long ts;
    private String userId;
    private String userStatus;

    public int getChannelId() {
        return this.channelId;
    }

    public String getChannelStatus() {
        return this.channelStatus;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public String getSign() {
        return this.sign;
    }

    public long getTs() {
        return this.ts;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getUserStatus() {
        return this.userStatus;
    }

    public boolean isCanWatch() {
        return this.canWatch;
    }

    public void setCanWatch(boolean z2) {
        this.canWatch = z2;
    }

    public void setChannelId(int i2) {
        this.channelId = i2;
    }

    public void setChannelStatus(String str) {
        this.channelStatus = str;
    }

    public void setErrorCode(String str) {
        this.errorCode = str;
    }

    public void setSign(String str) {
        this.sign = str;
    }

    public void setTs(long j2) {
        this.ts = j2;
    }

    public void setUserId(String str) {
        this.userId = str;
    }

    public void setUserStatus(String str) {
        this.userStatus = str;
    }

    public String toString() {
        return "PLVLiveRestrictVO{ts=" + this.ts + ", sign='" + this.sign + CharPool.SINGLE_QUOTE + ", userId='" + this.userId + CharPool.SINGLE_QUOTE + ", userStatus='" + this.userStatus + CharPool.SINGLE_QUOTE + ", channelId=" + this.channelId + ", channelStatus='" + this.channelStatus + CharPool.SINGLE_QUOTE + ", canWatch=" + this.canWatch + ", errorCode='" + this.errorCode + CharPool.SINGLE_QUOTE + '}';
    }
}
