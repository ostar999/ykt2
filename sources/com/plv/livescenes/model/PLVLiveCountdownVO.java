package com.plv.livescenes.model;

import cn.hutool.core.text.CharPool;
import com.plv.thirdpart.blankj.utilcode.util.TimeUtils;

/* loaded from: classes5.dex */
public class PLVLiveCountdownVO {
    public static final String DISABLED = "N";
    public static final String ENABLED = "Y";
    private int code;
    private Object data;
    private boolean encryption;
    private String message;
    private String status;

    public static class DataBean {
        private String bookingEnabled;
        private String countEnabled;
        private String countTips;
        private String startTime;

        public String getBookingEnabled() {
            return this.bookingEnabled;
        }

        public String getCountEnabled() {
            return this.countEnabled;
        }

        public String getCountTips() {
            return this.countTips;
        }

        public String getStartTime() {
            return this.startTime;
        }

        public void setBookingEnabled(String str) {
            this.bookingEnabled = str;
        }

        public void setCountEnabled(String str) {
            this.countEnabled = str;
        }

        public void setCountTips(String str) {
            this.countTips = str;
        }

        public void setStartTime(String str) {
            this.startTime = str;
        }

        public String toString() {
            return "DataBean{bookingEnabled='" + this.bookingEnabled + CharPool.SINGLE_QUOTE + ", countTips='" + this.countTips + CharPool.SINGLE_QUOTE + ", countEnabled='" + this.countEnabled + CharPool.SINGLE_QUOTE + ", startTime='" + this.startTime + CharPool.SINGLE_QUOTE + '}';
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

    public long getStartTime() {
        if (this.data != null) {
            return TimeUtils.string2Millis(getData().startTime);
        }
        return -1L;
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
        return "PLVLiveCountdownVO{code=" + this.code + ", status='" + this.status + CharPool.SINGLE_QUOTE + ", message='" + this.message + CharPool.SINGLE_QUOTE + ", data=" + this.data + '}';
    }
}
