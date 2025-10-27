package com.plv.socket.net.model;

import cn.hutool.core.text.CharPool;
import com.plv.foundationsdk.model.PLVFoundationVO;

/* loaded from: classes5.dex */
public class PLVChatTokenVO implements PLVFoundationVO {
    private String chatToken;
    private int code;
    private String data;
    private String message;
    private String status;

    public String getChatToken() {
        return this.chatToken;
    }

    public int getCode() {
        return this.code;
    }

    public String getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public String getStatus() {
        return this.status;
    }

    public void setChatToken(String str) {
        this.chatToken = str;
    }

    public void setCode(int i2) {
        this.code = i2;
    }

    public void setData(String str) {
        this.data = str;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public void setStatus(String str) {
        this.status = str;
    }

    public String toString() {
        return "PLVChatTokenVO{code=" + this.code + ", status='" + this.status + CharPool.SINGLE_QUOTE + ", message='" + this.message + CharPool.SINGLE_QUOTE + ", data='" + this.data + CharPool.SINGLE_QUOTE + '}';
    }
}
