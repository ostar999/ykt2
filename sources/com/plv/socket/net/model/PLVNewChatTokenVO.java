package com.plv.socket.net.model;

import cn.hutool.core.text.CharPool;
import com.plv.foundationsdk.model.PLVFoundationVO;

/* loaded from: classes5.dex */
public class PLVNewChatTokenVO implements PLVFoundationVO {
    private int code;
    private Object data;
    private boolean encryption;
    private String message;
    private String status;

    public static class DataBean {
        private String childRoomEnabled;
        private String mediaChannelKey;
        private String roomId;
        private String token;

        public String getChildRoomEnabled() {
            return this.childRoomEnabled;
        }

        public String getMediaChannelKey() {
            return this.mediaChannelKey;
        }

        public String getRoomId() {
            return this.roomId;
        }

        public String getToken() {
            return this.token;
        }

        public void setChildRoomEnabled(String str) {
            this.childRoomEnabled = str;
        }

        public void setMediaChannelKey(String str) {
            this.mediaChannelKey = str;
        }

        public void setRoomId(String str) {
            this.roomId = str;
        }

        public void setToken(String str) {
            this.token = str;
        }

        public String toString() {
            return "DataBean{mediaChannelKey='" + this.mediaChannelKey + CharPool.SINGLE_QUOTE + ", token='" + this.token + CharPool.SINGLE_QUOTE + ", childRoomEnabled='" + this.childRoomEnabled + CharPool.SINGLE_QUOTE + ", roomId='" + this.roomId + CharPool.SINGLE_QUOTE + '}';
        }
    }

    public int getCode() {
        return this.code;
    }

    public DataBean getData() {
        return (DataBean) this.data;
    }

    public Object getDataObj() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public String getStatus() {
        return this.status;
    }

    public boolean isEncryption() {
        return this.encryption;
    }

    public void setCode(int i2) {
        this.code = i2;
    }

    public void setData(Object obj) {
        this.data = obj;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public void setStatus(String str) {
        this.status = str;
    }

    public String toString() {
        return "PLVNewChatTokenVO{code=" + this.code + ", status='" + this.status + CharPool.SINGLE_QUOTE + ", message='" + this.message + CharPool.SINGLE_QUOTE + ", data=" + this.data + '}';
    }
}
