package com.psychiatrygarden.activity.forum.bean;

import com.psychiatrygarden.activity.forum.bean.ForumIndexBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class ForumChildrenBean implements Serializable {
    private String code;
    private List<ForumIndexBean.DataBean.ListBean> data = new ArrayList();
    private String message;
    private String server_time;

    public String getCode() {
        return this.code;
    }

    public List<ForumIndexBean.DataBean.ListBean> getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public String getServer_time() {
        return this.server_time;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(List<ForumIndexBean.DataBean.ListBean> data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setServer_time(String server_time) {
        this.server_time = server_time;
    }
}
