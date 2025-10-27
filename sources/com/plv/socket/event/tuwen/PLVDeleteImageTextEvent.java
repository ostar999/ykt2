package com.plv.socket.event.tuwen;

import cn.hutool.core.text.CharPool;

/* loaded from: classes5.dex */
public class PLVDeleteImageTextEvent {
    private String EVENT;
    private DataBean data;

    public static class DataBean {
        private String channelId;
        private String id;
        private int total;

        public String getChannelId() {
            return this.channelId;
        }

        public String getId() {
            return this.id;
        }

        public int getTotal() {
            return this.total;
        }

        public void setChannelId(String str) {
            this.channelId = str;
        }

        public void setId(String str) {
            this.id = str;
        }

        public void setTotal(int i2) {
            this.total = i2;
        }

        public String toString() {
            return "DataBean{total=" + this.total + ", id='" + this.id + CharPool.SINGLE_QUOTE + ", channelId='" + this.channelId + CharPool.SINGLE_QUOTE + '}';
        }
    }

    public DataBean getData() {
        return this.data;
    }

    public String getEVENT() {
        return this.EVENT;
    }

    public void setData(DataBean dataBean) {
        this.data = dataBean;
    }

    public void setEVENT(String str) {
        this.EVENT = str;
    }

    public String toString() {
        return "PLVDeleteImageTextEvent{EVENT='" + this.EVENT + CharPool.SINGLE_QUOTE + ", data=" + this.data + '}';
    }
}
