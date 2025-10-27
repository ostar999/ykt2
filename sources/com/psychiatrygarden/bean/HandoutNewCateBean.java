package com.psychiatrygarden.bean;

import java.io.Serializable;
import java.util.List;

/* loaded from: classes5.dex */
public class HandoutNewCateBean implements Serializable {
    private String code;
    private DataBean data;
    private String fixedCount = "0";
    private String message;
    private String server_time;

    public static class DataBean {
        private List<DefaultBean> _default;
        private List<ListBean> _list;

        public static class DefaultBean {
            private String id;
            private String initials;
            private String pid;
            private String pname;
            private String selected;
            private String sort;
            private String title;

            public String getId() {
                return this.id;
            }

            public String getInitials() {
                return this.initials;
            }

            public String getPid() {
                return this.pid;
            }

            public String getPname() {
                return this.pname;
            }

            public String getSelected() {
                return this.selected;
            }

            public String getSort() {
                return this.sort;
            }

            public String getTitle() {
                return this.title;
            }

            public void setId(String id) {
                this.id = id;
            }

            public void setInitials(String initials) {
                this.initials = initials;
            }

            public void setPid(String pid) {
                this.pid = pid;
            }

            public void setPname(String pname) {
                this.pname = pname;
            }

            public void setSelected(String selected) {
                this.selected = selected;
            }

            public void setSort(String sort) {
                this.sort = sort;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }

        public static class ListBean {
            private String p_id;
            private String p_name;
            private List<List<SubBean>> sub;

            public static class SubBean {
                private String id;
                private String initials;
                private String pid;
                private String pname;
                private String selected;
                private String sort;
                private String title;

                public String getId() {
                    return this.id;
                }

                public String getInitials() {
                    return this.initials;
                }

                public String getPid() {
                    return this.pid;
                }

                public String getPname() {
                    return this.pname;
                }

                public String getSelected() {
                    return this.selected;
                }

                public String getSort() {
                    return this.sort;
                }

                public String getTitle() {
                    return this.title;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public void setInitials(String initials) {
                    this.initials = initials;
                }

                public void setPid(String pid) {
                    this.pid = pid;
                }

                public void setPname(String pname) {
                    this.pname = pname;
                }

                public void setSelected(String selected) {
                    this.selected = selected;
                }

                public void setSort(String sort) {
                    this.sort = sort;
                }

                public void setTitle(String title) {
                    this.title = title;
                }
            }

            public String getP_id() {
                return this.p_id;
            }

            public String getP_name() {
                return this.p_name;
            }

            public List<List<SubBean>> getSub() {
                return this.sub;
            }

            public void setP_id(String p_id) {
                this.p_id = p_id;
            }

            public void setP_name(String p_name) {
                this.p_name = p_name;
            }

            public void setSub(List<List<SubBean>> sub) {
                this.sub = sub;
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
