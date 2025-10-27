package com.psychiatrygarden.activity.purchase.beans;

import java.io.Serializable;
import java.util.List;

/* loaded from: classes5.dex */
public class AreaGetPCData implements Serializable {
    private String code;
    private List<DataBean> data;
    private String message;
    private String server_time;

    public static class DataBean implements Serializable {
        private String area_id;
        private String level;
        private String pid;
        private List<SubBean> sub;
        private String title;

        public static class SubBean implements Serializable {
            private String area_id;
            private String level;
            private String pid;
            private String sort;
            private String title;

            public String getArea_id() {
                return this.area_id;
            }

            public String getLevel() {
                return this.level;
            }

            public String getPid() {
                return this.pid;
            }

            public String getSort() {
                return this.sort;
            }

            public String getTitle() {
                return this.title;
            }

            public void setArea_id(String area_id) {
                this.area_id = area_id;
            }

            public void setLevel(String level) {
                this.level = level;
            }

            public void setPid(String pid) {
                this.pid = pid;
            }

            public void setSort(String sort) {
                this.sort = sort;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }

        public String getArea_id() {
            return this.area_id;
        }

        public String getLevel() {
            return this.level;
        }

        public String getPid() {
            return this.pid;
        }

        public List<SubBean> getSub() {
            return this.sub;
        }

        public String getTitle() {
            return this.title;
        }

        public void setArea_id(String area_id) {
            this.area_id = area_id;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public void setSub(List<SubBean> sub) {
            this.sub = sub;
        }

        public void setTitle(String title) {
            this.title = title;
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
