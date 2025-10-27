package com.plv.socket.event.chat;

import cn.hutool.core.text.CharPool;
import com.plv.socket.event.PLVMessageBaseEvent;
import com.plv.socket.user.PLVSocketUserBean;
import java.util.List;

/* loaded from: classes5.dex */
public class PLVUnshieldEvent extends PLVMessageBaseEvent {
    public static final String EVENT = "UNSHIELD";
    private DataBean data;
    private List<PLVSocketUserBean> userIds;

    public static class DataBean {
        private String banType;
        private String userId;

        public String getBanType() {
            return this.banType;
        }

        public String getUserId() {
            return this.userId;
        }

        public void setBanType(String str) {
            this.banType = str;
        }

        public void setUserId(String str) {
            this.userId = str;
        }

        public String toString() {
            return "DataBean{banType='" + this.banType + CharPool.SINGLE_QUOTE + ", userId='" + this.userId + CharPool.SINGLE_QUOTE + '}';
        }
    }

    public DataBean getData() {
        return this.data;
    }

    @Override // com.plv.socket.event.PLVBaseEvent
    public String getEVENT() {
        return "UNSHIELD";
    }

    public List<PLVSocketUserBean> getUserIds() {
        return this.userIds;
    }

    public void setData(DataBean dataBean) {
        this.data = dataBean;
    }

    public void setUserIds(List<PLVSocketUserBean> list) {
        this.userIds = list;
    }

    public String toString() {
        return "PLVUnshieldEvent{EVENT='UNSHIELD', data=" + this.data + ", userIds=" + this.userIds + '}';
    }
}
