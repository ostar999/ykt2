package com.psychiatrygarden.activity.forum.bean;

import java.io.Serializable;
import java.util.List;

/* loaded from: classes5.dex */
public class SearchOptionBean implements Serializable {
    public String code;
    public SearchBean data;
    public String message;

    public static class SearchBean implements Serializable {
        public List<SearchChildBean> group;
        public List<SearchChildBean> sort;
        public List<SearchChildBean> type;

        public List<SearchChildBean> getGroup() {
            return this.group;
        }

        public List<SearchChildBean> getSort() {
            return this.sort;
        }

        public List<SearchChildBean> getType() {
            return this.type;
        }

        public void setGroup(List<SearchChildBean> group) {
            this.group = group;
        }

        public void setSort(List<SearchChildBean> sort) {
            this.sort = sort;
        }

        public void setType(List<SearchChildBean> type) {
            this.type = type;
        }
    }

    public static class SearchChildBean implements Serializable {
        public String label;
        public String value;

        public String getLabel() {
            return this.label;
        }

        public String getValue() {
            return this.value;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public String getCode() {
        return this.code;
    }

    public SearchBean getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(SearchBean data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
