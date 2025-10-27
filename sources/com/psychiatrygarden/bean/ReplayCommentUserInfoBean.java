package com.psychiatrygarden.bean;

import java.io.Serializable;

/* loaded from: classes5.dex */
public class ReplayCommentUserInfoBean implements Serializable {
    private String ctime;
    private String is_vip = "";
    private String is_svip = "";
    private String school = "";
    private String user_identity = "";
    private String is_authentication = "0";

    public String getCtime() {
        return this.ctime;
    }

    public String getIs_authentication() {
        return this.is_authentication;
    }

    public String getIs_svip() {
        return this.is_svip;
    }

    public String getIs_vip() {
        return this.is_vip;
    }

    public String getSchool() {
        return this.school;
    }

    public String getUser_identity() {
        return this.user_identity;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public void setIs_authentication(String is_authentication) {
        this.is_authentication = is_authentication;
    }

    public void setIs_svip(String is_svip) {
        this.is_svip = is_svip;
    }

    public void setIs_vip(String is_vip) {
        this.is_vip = is_vip;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public void setUser_identity(String user_identity) {
        this.user_identity = user_identity;
    }
}
