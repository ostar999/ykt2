package com.plv.livescenes.model;

import cn.hutool.core.text.CharPool;
import com.plv.socket.user.PLVSocketUserBean;
import java.util.List;

/* loaded from: classes5.dex */
public class PLVKickUsersVO {
    private int code;
    private List<PLVSocketUserBean> data;
    private String message;
    private String status;

    public int getCode() {
        return this.code;
    }

    public List<PLVSocketUserBean> getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public String getStatus() {
        return this.status;
    }

    public void setCode(int i2) {
        this.code = i2;
    }

    public void setData(List<PLVSocketUserBean> list) {
        this.data = list;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public void setStatus(String str) {
        this.status = str;
    }

    public String toString() {
        return "PLVKickUsersVO{status='" + this.status + CharPool.SINGLE_QUOTE + ", message='" + this.message + CharPool.SINGLE_QUOTE + ", code=" + this.code + ", data=" + this.data + '}';
    }
}
