package com.hyphenate.chat;

import com.aliyun.vod.log.core.AliyunLogCommon;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;

/* loaded from: classes4.dex */
public class EMUserInfo {
    private String avatarUrl;
    private String birth;
    private String email;
    private String ext;
    private int gender = 0;
    private String nickname;
    private String phoneNumber;
    private String signature;
    private String userId;

    public enum EMUserInfoType {
        NICKNAME(0, "nickname"),
        AVATAR_URL(1, "avatarurl"),
        EMAIL(2, "mail"),
        PHONE(3, AliyunLogCommon.TERMINAL_TYPE),
        GENDER(4, "gender"),
        SIGN(5, "sign"),
        BIRTH(6, "birth"),
        EXT(100, SocializeProtocolConstants.PROTOCOL_KEY_EXTEND);

        private String desc;
        private int value;

        EMUserInfoType(int i2, String str) {
            this.value = i2;
            this.desc = str;
        }

        public String getDesc() {
            return this.desc;
        }

        public int getValue() {
            return this.value;
        }

        public void setDesc(String str) {
            this.desc = str;
        }

        public void setValue(int i2) {
            this.value = i2;
        }
    }

    public String getAvatarUrl() {
        return this.avatarUrl;
    }

    public String getBirth() {
        return this.birth;
    }

    public String getEmail() {
        return this.email;
    }

    public String getExt() {
        return this.ext;
    }

    public int getGender() {
        return this.gender;
    }

    @Deprecated
    public String getNickName() {
        return getNickname();
    }

    public String getNickname() {
        return this.nickname;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public String getSignature() {
        return this.signature;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setAvatarUrl(String str) {
        this.avatarUrl = str;
    }

    public void setBirth(String str) {
        this.birth = str;
    }

    public void setEmail(String str) {
        this.email = str;
    }

    public void setExt(String str) {
        this.ext = str;
    }

    public void setGender(int i2) {
        this.gender = i2;
    }

    @Deprecated
    public void setNickName(String str) {
        setNickname(str);
    }

    public void setNickname(String str) {
        this.nickname = str;
    }

    public void setPhoneNumber(String str) {
        this.phoneNumber = str;
    }

    public void setSignature(String str) {
        this.signature = str;
    }

    public void setUserId(String str) {
        this.userId = str;
    }
}
