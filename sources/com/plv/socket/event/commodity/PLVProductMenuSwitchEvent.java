package com.plv.socket.event.commodity;

import com.plv.socket.event.PLVMessageBaseEvent;

/* loaded from: classes5.dex */
public class PLVProductMenuSwitchEvent extends PLVMessageBaseEvent {
    public static final String EVENT = "PRODUCT_MESSAGE";
    private ContentBean content;
    private String status;

    public static class ContentBean {
        private Object content;
        private String enabled;
        private String menuId;
        private String menuType;
        private String name;
        private int ordered;

        public Object getContent() {
            return this.content;
        }

        public String getEnabled() {
            return this.enabled;
        }

        public String getMenuId() {
            return this.menuId;
        }

        public String getMenuType() {
            return this.menuType;
        }

        public String getName() {
            return this.name;
        }

        public int getOrdered() {
            return this.ordered;
        }

        public boolean isEnabled() {
            return "Y".equals(this.enabled);
        }

        public void setContent(Object obj) {
            this.content = obj;
        }

        public void setEnabled(String str) {
            this.enabled = str;
        }

        public void setMenuId(String str) {
            this.menuId = str;
        }

        public void setMenuType(String str) {
            this.menuType = str;
        }

        public void setName(String str) {
            this.name = str;
        }

        public void setOrdered(int i2) {
            this.ordered = i2;
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

    public void setContent(ContentBean contentBean) {
        this.content = contentBean;
    }

    public void setStatus(String str) {
        this.status = str;
    }
}
