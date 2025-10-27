package com.plv.socket.event.login;

import cn.hutool.core.text.CharPool;
import com.plv.socket.event.PLVMessageBaseEvent;

/* loaded from: classes5.dex */
public class PLVLoginRefuseEvent extends PLVMessageBaseEvent {
    public static final String EVENT = "LOGIN_REFUSE";
    private DataBean data;

    public static class DataBean {
        private String referField;
        private String type;

        public String getReferField() {
            return this.referField;
        }

        public String getType() {
            return this.type;
        }

        public void setReferField(String str) {
            this.referField = str;
        }

        public void setType(String str) {
            this.type = str;
        }

        public String toString() {
            return "DataBean{referField='" + this.referField + CharPool.SINGLE_QUOTE + ", type='" + this.type + CharPool.SINGLE_QUOTE + '}';
        }
    }

    public DataBean getData() {
        return this.data;
    }

    @Override // com.plv.socket.event.PLVBaseEvent
    public String getEVENT() {
        return "LOGIN_REFUSE";
    }

    public void setData(DataBean dataBean) {
        this.data = dataBean;
    }

    public String toString() {
        return "PLVLoginRefuseEvent{EVENT='LOGIN_REFUSE', data=" + this.data + '}';
    }
}
