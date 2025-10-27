package com.plv.socket.event.sclass;

import cn.hutool.core.text.CharPool;

/* loaded from: classes5.dex */
public class PLVInLiveAckResult {
    public static final String STATUS_END = "end";
    public static final String STATUS_LIVE = "live";
    private int code;
    private DataBean data;
    private String message;
    private String status;

    public static class DataBean {
        private String inLive;

        public String getInLive() {
            return this.inLive;
        }

        public boolean isLive() {
            return PLVInLiveAckResult.STATUS_LIVE.equals(this.inLive);
        }

        public void setInLive(String str) {
            this.inLive = str;
        }

        public String toString() {
            return "DataBean{inLive='" + this.inLive + CharPool.SINGLE_QUOTE + '}';
        }
    }

    public int getCode() {
        return this.code;
    }

    public DataBean getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public String getStatus() {
        return this.status;
    }

    public boolean isSuccess() {
        return this.code == 200;
    }

    public void setCode(int i2) {
        this.code = i2;
    }

    public void setData(DataBean dataBean) {
        this.data = dataBean;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public void setStatus(String str) {
        this.status = str;
    }

    public String toString() {
        return "PLVInLiveAckResult{status='" + this.status + CharPool.SINGLE_QUOTE + ", message='" + this.message + CharPool.SINGLE_QUOTE + ", code=" + this.code + ", data=" + this.data + '}';
    }
}
