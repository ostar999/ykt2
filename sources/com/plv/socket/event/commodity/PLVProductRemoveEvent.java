package com.plv.socket.event.commodity;

import com.plv.socket.event.PLVMessageBaseEvent;

/* loaded from: classes5.dex */
public class PLVProductRemoveEvent extends PLVMessageBaseEvent {
    public static final String EVENT = "PRODUCT_MESSAGE";
    private ContentBean content;
    private String status;

    public static class ContentBean {
        private int productId;
        private int rank;
        private int status;

        public int getProductId() {
            return this.productId;
        }

        public int getRank() {
            return this.rank;
        }

        public int getStatus() {
            return this.status;
        }

        public boolean isPullOffShelvesStatus() {
            return !isPutOnShelvesStatus();
        }

        public boolean isPutOnShelvesStatus() {
            return 1 == this.status;
        }

        public void setProductId(int i2) {
            this.productId = i2;
        }

        public void setRank(int i2) {
            this.rank = i2;
        }

        public void setStatus(int i2) {
            this.status = i2;
        }
    }

    public ContentBean getContent() {
        return this.content;
    }

    @Override // com.plv.socket.event.PLVBaseEvent
    public String getEVENT() {
        return "PRODUCT_MESSAGE";
    }

    public String getStatus() {
        return this.status;
    }

    public boolean isExpurgate() {
        return "3".equals(this.status);
    }

    public boolean isPullOffShelves() {
        return "2".equals(this.status);
    }

    public void setContent(ContentBean contentBean) {
        this.content = contentBean;
    }

    public void setStatus(String str) {
        this.status = str;
    }
}
