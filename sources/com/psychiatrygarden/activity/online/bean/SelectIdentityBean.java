package com.psychiatrygarden.activity.online.bean;

import java.io.Serializable;
import java.util.List;

/* loaded from: classes5.dex */
public class SelectIdentityBean implements Serializable {
    private String code;
    private List<DataBean> data;
    private String message;
    private String server_time;

    public static class DataBean implements Serializable {
        private List<ChildrenBeanX> children;
        private String describe;
        private String label;

        public static class ChildrenBeanX implements Serializable {
            private String am_pm;
            private String app_id;
            private String app_mark;
            private String app_type;
            private List<ChildrenBean> children;
            private String describe;
            private String label;

            public static class ChildrenBean implements Serializable {
                private String is_search = "0";
                private String key;
                private String label;
                private String module_type;

                public String getIs_search() {
                    return this.is_search;
                }

                public String getKey() {
                    return this.key;
                }

                public String getLabel() {
                    return this.label;
                }

                public String getModule_type() {
                    return this.module_type;
                }

                public void setIs_search(String is_search) {
                    this.is_search = is_search;
                }

                public void setKey(String key) {
                    this.key = key;
                }

                public void setLabel(String label) {
                    this.label = label;
                }

                public void setModule_type(String module_type) {
                    this.module_type = module_type;
                }
            }

            public String getAm_pm() {
                return this.am_pm;
            }

            public String getApp_id() {
                return this.app_id;
            }

            public String getApp_mark() {
                return this.app_mark;
            }

            public String getApp_type() {
                return this.app_type;
            }

            public List<ChildrenBean> getChildren() {
                return this.children;
            }

            public String getDescribe() {
                return this.describe;
            }

            public String getLabel() {
                return this.label;
            }

            public void setAm_pm(String am_pm) {
                this.am_pm = am_pm;
            }

            public void setApp_id(String app_id) {
                this.app_id = app_id;
            }

            public void setApp_mark(String app_mark) {
                this.app_mark = app_mark;
            }

            public void setApp_type(String app_type) {
                this.app_type = app_type;
            }

            public void setChildren(List<ChildrenBean> children) {
                this.children = children;
            }

            public void setDescribe(String describe) {
                this.describe = describe;
            }

            public void setLabel(String label) {
                this.label = label;
            }
        }

        public List<ChildrenBeanX> getChildren() {
            return this.children;
        }

        public String getDescribe() {
            return this.describe;
        }

        public String getLabel() {
            return this.label;
        }

        public void setChildren(List<ChildrenBeanX> children) {
            this.children = children;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }

        public void setLabel(String label) {
            this.label = label;
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
