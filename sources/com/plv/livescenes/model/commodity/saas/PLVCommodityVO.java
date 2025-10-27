package com.plv.livescenes.model.commodity.saas;

import com.plv.socket.event.commodity.PLVProductContentBean;
import java.util.List;

/* loaded from: classes5.dex */
public class PLVCommodityVO {
    private int code;
    private DataBean data;
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
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public String getStatus() {
        return this.status;
    }

    public void setCode(int i2) {
        this.code = i2;
    }

    public void setData(DataBean dataBean) {
        this.data = dataBean;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public void setStatus(String str) {
        this.status = str;
    }
}
