package com.psychiatrygarden.bean;

import androidx.annotation.NonNull;
import java.io.Serializable;
import java.util.List;

/* loaded from: classes5.dex */
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

        public void setChange(boolean change) {
            this.isChange = change;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public void setCutXuni(int cutXuni) {
            this.cutXuni = cutXuni;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public void setUser_label(String user_label) {
            this.user_label = user_label;
        }

        public void setUser_label_Xuni(String user_label_Xuni) {
            this.user_label_Xuni = user_label_Xuni;
        }

        public int sort(DataBean o2) {
            if (this.count > o2.getCount()) {
                return -1;
            }
            return this.count < o2.getCount() ? 1 : 0;
        }

        @Override // java.lang.Comparable
        public int compareTo(@NonNull DataBean o2) {
            return sort(o2);
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

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setServer_time(String server_time) {
        this.server_time = server_time;
    }
}
