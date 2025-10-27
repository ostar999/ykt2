package com.plv.livescenes.document.model;

/* loaded from: classes4.dex */
public class PLVAssistantInfo {
    private DataBean data;
    private String roomId;

    public static class DataBean {
        private int pageId;
        private String type;

        public int getPageId() {
            return this.pageId;
        }

        public String getType() {
            return this.type;
        }

        public void setPageId(int i2) {
            this.pageId = i2;
        }

        public void setType(String str) {
            this.type = str;
        }
    }

    public DataBean getData() {
        return this.data;
    }

    public String getRoomId() {
        return this.roomId;
    }

    public void setData(DataBean dataBean) {
        this.data = dataBean;
    }

    public void setRoomId(String str) {
        this.roomId = str;
    }
}
