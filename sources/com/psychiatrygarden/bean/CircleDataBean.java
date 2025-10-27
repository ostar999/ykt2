package com.psychiatrygarden.bean;

import java.io.Serializable;
import java.util.List;

/* loaded from: classes5.dex */
public class CircleDataBean implements Serializable {
    private String code;
    private DataBean data;
    private String fixedCount = "0";
    private String message;
    private String server_time;

    public static class DataBean implements Serializable {
        private List<DefaultBean> _default;
        private List<ListBean> _list;

        public static class DefaultBean {
            private String app_id;
            private String ctime;
            private String id;
            private String initials;
            private String is_del;
            private String pid;
            private String title;
            private String utime;
            private String sort = "0";
            private String is_default = "0";

            public String getApp_id() {
                return this.app_id;
            }

            public String getCtime() {
                return this.ctime;
            }

            public String getId() {
                return this.id;
            }

            public String getInitials() {
                return this.initials;
            }

            public String getIs_default() {
                return this.is_default;
            }

            public String getIs_del() {
                return this.is_del;
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

            public String getUtime() {
                return this.utime;
            }

            public void setApp_id(String app_id) {
                this.app_id = app_id;
            }

            public void setCtime(String ctime) {
                this.ctime = ctime;
            }

            public void setId(String id) {
                this.id = id;
            }

            public void setInitials(String initials) {
                this.initials = initials;
            }

            public void setIs_default(String is_default) {
                this.is_default = is_default;
            }

            public void setIs_del(String is_del) {
                this.is_del = is_del;
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

            public void setUtime(String utime) {
                this.utime = utime;
            }
        }

        public static class ListBean {
            private String app_id;
            private String ctime;
            private String id;
            private String initials;
            private String is_default;
            private String is_del;
            private String pid;
            private String sort;
            private String title;
            private String utime;

            public String getApp_id() {
                return this.app_id;
            }

            public String getCtime() {
                return this.ctime;
            }

            public String getId() {
                return this.id;
            }

            public String getInitials() {
                return this.initials;
            }

            public String getIs_default() {
                return this.is_default;
            }

            public String getIs_del() {
                return this.is_del;
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

            public String getUtime() {
                return this.utime;
            }

            public void setApp_id(String app_id) {
                this.app_id = app_id;
            }

            public void setCtime(String ctime) {
                this.ctime = ctime;
            }

            public void setId(String id) {
                this.id = id;
            }

            public void setInitials(String initials) {
                this.initials = initials;
            }

            public void setIs_default(String is_default) {
                this.is_default = is_default;
            }

            public void setIs_del(String is_del) {
                this.is_del = is_del;
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

            public void setUtime(String utime) {
                this.utime = utime;
            }
        }

        public List<DefaultBean> get_default() {
            return this._default;
        }

        public List<ListBean> get_list() {
            return this._list;
        }

        public void set_default(List<DefaultBean> _default) {
            this._default = _default;
        }

        public void set_list(List<ListBean> _list) {
            this._list = _list;
        }
    }

    public String getCode() {
        return this.code;
    }

    public DataBean getData() {
        return this.data;
    }

    public String getFixedCount() {
        return this.fixedCount;
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

    public void setData(DataBean data) {
        this.data = data;
    }

    public void setFixedCount(String fixedCount) {
        this.fixedCount = fixedCount;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setServer_time(String server_time) {
        this.server_time = server_time;
    }
}
