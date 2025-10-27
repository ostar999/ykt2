package com.plv.rtc.urtc.model;

import cn.hutool.core.text.CharPool;

/* loaded from: classes5.dex */
public class URTCSdkAuthInfo {
    String mAppId = "";
    String mRoomid = "";
    String mUid = "";
    String mToken = "";

    public String getAppId() {
        return this.mAppId;
    }

    public String getRoomId() {
        return this.mRoomid;
    }

    public String getToken() {
        return this.mToken;
    }

    public String getUId() {
        return this.mUid;
    }

    public void setAppId(String str) {
        this.mAppId = str;
    }

    public void setRoomId(String str) {
        this.mRoomid = str;
    }

    public void setToken(String str) {
        this.mToken = str;
    }

    public void setUId(String str) {
        this.mUid = str;
    }

    public String toString() {
        return "URTCSdkAuthInfo{mAppId='" + this.mAppId + CharPool.SINGLE_QUOTE + ", mRoomid='" + this.mRoomid + CharPool.SINGLE_QUOTE + ", mUid='" + this.mUid + CharPool.SINGLE_QUOTE + ", mToken='" + this.mToken + CharPool.SINGLE_QUOTE + '}';
    }
}
