package com.plv.livescenes.feature.pointreward.vo;

import cn.hutool.core.text.CharPool;

/* loaded from: classes4.dex */
public class PLVDonateGoodResponseVO {
    private Integer code;
    private Object data;
    private boolean encryption;
    private String requestId;
    private String status;
    private Boolean success;

    public static class Data {
        private Boolean freeDonate;

        public Boolean getFreeDonate() {
            return this.freeDonate;
        }

        public void setFreeDonate(Boolean bool) {
            this.freeDonate = bool;
        }

        public String toString() {
            return "Data{freeDonate=" + this.freeDonate + '}';
        }
    }

    public Integer getCode() {
        return this.code;
    }

    public Data getData() {
        return (Data) this.data;
    }

    public Object getDataObj() {
        return this.data;
    }

    public String getRequestId() {
        return this.requestId;
    }

    public String getStatus() {
        return this.status;
    }

    public Boolean getSuccess() {
        return this.success;
    }

    public boolean isEncryption() {
        return this.encryption;
    }

    public void setCode(Integer num) {
        this.code = num;
    }

    public void setData(Object obj) {
        this.data = obj;
    }

    public void setRequestId(String str) {
        this.requestId = str;
    }

    public void setStatus(String str) {
        this.status = str;
    }

    public void setSuccess(Boolean bool) {
        this.success = bool;
    }

    public String toString() {
        return "PLVDonateGoodResponseVO{code=" + this.code + ", status='" + this.status + CharPool.SINGLE_QUOTE + ", requestId='" + this.requestId + CharPool.SINGLE_QUOTE + ", data=" + this.data + ", success=" + this.success + '}';
    }
}
