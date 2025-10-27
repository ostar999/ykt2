package com.psychiatrygarden.bean;

import java.io.Serializable;

/* loaded from: classes5.dex */
public class OnlineServiceBean implements Serializable {
    public String cs_name = "";
    public String cs_type = "";
    public String contact = "";
    public String wechat_corpid = "";
    public String cs_desc = "";

    public String getContact() {
        return this.contact;
    }

    public String getCs_desc() {
        return this.cs_desc;
    }

    public String getCs_name() {
        return this.cs_name;
    }

    public String getCs_type() {
        return this.cs_type;
    }

    public String getWechat_corpid() {
        return this.wechat_corpid;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setCs_desc(String cs_desc) {
        this.cs_desc = cs_desc;
    }

    public void setCs_name(String cs_name) {
        this.cs_name = cs_name;
    }

    public void setCs_type(String cs_type) {
        this.cs_type = cs_type;
    }

    public void setWechat_corpid(String wechat_corpid) {
        this.wechat_corpid = wechat_corpid;
    }
}
