package com.plv.livescenes.chatroom.send.img;

import cn.hutool.core.text.CharPool;
import java.util.List;

/* loaded from: classes4.dex */
public class PLVSendChatImgEvent {
    private String EVENT;
    private String accountId;
    private String roomId;
    private String sessionId;
    private String source;
    private List<ValueBean> values;

    public static class ValueBean {
        private String id;
        private SizeBean size;
        private String status;
        private String type;
        private String uploadImgUrl;

        public static class SizeBean {
            private double height;
            private double width;

            public double getHeight() {
                return this.height;
            }

            public double getWidth() {
                return this.width;
            }

            public void setHeight(double d3) {
                this.height = d3;
            }

            public void setWidth(double d3) {
                this.width = d3;
            }

            public String toString() {
                return "SizeBean{height=" + this.height + ", width=" + this.width + '}';
            }
        }

        public String getId() {
            return this.id;
        }

        public SizeBean getSize() {
            return this.size;
        }

        public String getStatus() {
            return this.status;
        }

        public String getType() {
            return this.type;
        }

        public String getUploadImgUrl() {
            return this.uploadImgUrl;
        }

        public void setId(String str) {
            this.id = str;
        }

        public void setSize(SizeBean sizeBean) {
            this.size = sizeBean;
        }

        public void setStatus(String str) {
            this.status = str;
        }

        public void setType(String str) {
            this.type = str;
        }

        public void setUploadImgUrl(String str) {
            this.uploadImgUrl = str;
        }

        public String toString() {
            return "ValueBean{uploadImgUrl='" + this.uploadImgUrl + CharPool.SINGLE_QUOTE + ", type='" + this.type + CharPool.SINGLE_QUOTE + ", status='" + this.status + CharPool.SINGLE_QUOTE + ", id='" + this.id + CharPool.SINGLE_QUOTE + ", size=" + this.size + '}';
        }
    }

    public String getAccountId() {
        return this.accountId;
    }

    public String getEVENT() {
        return this.EVENT;
    }

    public String getRoomId() {
        return this.roomId;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public String getSource() {
        return this.source;
    }

    public List<ValueBean> getValues() {
        return this.values;
    }

    public void setAccountId(String str) {
        this.accountId = str;
    }

    public void setEVENT(String str) {
        this.EVENT = str;
    }

    public void setRoomId(String str) {
        this.roomId = str;
    }

    public void setSessionId(String str) {
        this.sessionId = str;
    }

    public void setSource(String str) {
        this.source = str;
    }

    public void setValues(List<ValueBean> list) {
        this.values = list;
    }

    public String toString() {
        return "PLVSendChatImgEvent{EVENT='" + this.EVENT + CharPool.SINGLE_QUOTE + ", roomId='" + this.roomId + CharPool.SINGLE_QUOTE + ", accountId='" + this.accountId + CharPool.SINGLE_QUOTE + ", sessionId='" + this.sessionId + CharPool.SINGLE_QUOTE + ", values=" + this.values + '}';
    }
}
