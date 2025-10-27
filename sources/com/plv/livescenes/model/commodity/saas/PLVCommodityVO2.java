package com.plv.livescenes.model.commodity.saas;

import com.plv.socket.event.commodity.PLVProductContentBean;
import java.util.List;

/* loaded from: classes5.dex */
public class PLVCommodityVO2 {
    private int code;
    private Object data;
    private boolean encryption;
    private String message;
    private String status;

    public static class DataBean {
        private List<PLVProductContentBean> content;
        private int total;

        public List<PLVProductContentBean> getContent() {
            return this.content;
        }

        public int getTotal() {
            return this.total;
        }

        public void setContent(List<PLVProductContentBean> list) {
            this.content = list;
        }

        public void setTotal(int i2) {
            this.total = i2;
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
}
