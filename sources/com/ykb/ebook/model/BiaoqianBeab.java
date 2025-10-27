package com.ykb.ebook.model;

import androidx.annotation.NonNull;
import java.io.Serializable;
import java.util.List;

/* loaded from: classes7.dex */
public class BiaoqianBeab implements Serializable {
    private String code;
    private List<DataBean> data;
    private String message;
    private String server_time;

    public static class DataBean implements Comparable<DataBean>, Serializable {
        private String color;
        private String id;
        private String label;
        private int count = 0;
        private String user_label = "0";
        private int cutXuni = 0;
        private String user_label_Xuni = "0";
        private boolean isChange = false;

        public String getColor() {
            return this.color;
        }

        public int getCount() {
            return this.count;
        }

        public int getCutXuni() {
            return this.cutXuni;
        }

        public String getId() {
            return this.id;
        }

        public String getLabel() {
            return this.label;
        }

        public String getUser_label() {
            return this.user_label;
        }

        public String getUser_label_Xuni() {
            return this.user_label_Xuni;
        }

        public boolean isChange() {
            return this.isChange;
        }

        public void setChange(boolean z2) {
            this.isChange = z2;
        }

        public void setColor(String str) {
            this.color = str;
        }

        public void setCount(int i2) {
            this.count = i2;
        }

        public void setCutXuni(int i2) {
            this.cutXuni = i2;
        }

        public void setId(String str) {
            this.id = str;
        }

        public void setLabel(String str) {
            this.label = str;
        }

        public void setUser_label(String str) {
            this.user_label = str;
        }

        public void setUser_label_Xuni(String str) {
            this.user_label_Xuni = str;
        }

        public int sort(DataBean dataBean) {
            if (this.count > dataBean.getCount()) {
                return -1;
            }
            return this.count < dataBean.getCount() ? 1 : 0;
        }

        @Override // java.lang.Comparable
        public int compareTo(@NonNull DataBean dataBean) {
            return sort(dataBean);
        }
    }

    public String getCode() {
        return this.code;
    }

    public List<DataBean> getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public String getServer_time() {
        return this.server_time;
    }

    public void setCode(String str) {
        this.code = str;
    }

    public void setData(List<DataBean> list) {
        this.data = list;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public void setServer_time(String str) {
        this.server_time = str;
    }
}
