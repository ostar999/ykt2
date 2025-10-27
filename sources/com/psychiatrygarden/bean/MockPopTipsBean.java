package com.psychiatrygarden.bean;

import java.util.List;

/* loaded from: classes5.dex */
public class MockPopTipsBean {
    private String code;
    private List<MockPopTipsDataBean> data;
    private String message;

    public static class MockPopTipsDataBean {
        private String desc;
        private String part;
        private String sort;
        private List<String> type;

        public String getDesc() {
            return this.desc;
        }

        public String getPart() {
            return this.part;
        }

        public String getSort() {
            return this.sort;
        }

        public List<String> getType() {
            return this.type;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public void setPart(String part) {
            this.part = part;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public void setType(List<String> type) {
            this.type = type;
        }
    }

    public String getCode() {
        return this.code;
    }

    public List<MockPopTipsDataBean> getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(List<MockPopTipsDataBean> data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
