package com.plv.livescenes.model;

import cn.hutool.core.text.CharPool;
import java.util.List;

/* loaded from: classes5.dex */
public class PLVChatFunctionSwitchVO {
    public static final String ENABLE_Y = "Y";
    public static final String ENAVLE_N = "N";
    public static final String TYPE_SEND_FLOWERS_ENABLED = "sendFlowersEnabled";
    public static final String TYPE_VIEWER_SEND_IMG_ENABLED = "viewerSendImgEnabled";
    public static final String TYPE_WELCOME = "welcome";
    private int code;
    private Object data;
    private boolean encryption;
    private String message;
    private String status;

    public static class DataBean {
        private String enabled;
        private String type;

        public String getEnabled() {
            return this.enabled;
        }

        public String getType() {
            return this.type;
        }

        public boolean isEnabled() {
            return "Y".equals(this.enabled);
        }

        public void setEnabled(String str) {
            this.enabled = str;
        }

        public void setType(String str) {
            this.type = str;
        }

        public String toString() {
            return "DataBean{type='" + this.type + CharPool.SINGLE_QUOTE + ", enabled='" + this.enabled + CharPool.SINGLE_QUOTE + '}';
        }
    }

    public int getCode() {
        return this.code;
    }

    public List<DataBean> getData() {
        return (List) this.data;
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
        return "PLVChatFunctionSwitchVO{code=" + this.code + ", status='" + this.status + CharPool.SINGLE_QUOTE + ", message='" + this.message + CharPool.SINGLE_QUOTE + ", data=" + this.data + '}';
    }
}
