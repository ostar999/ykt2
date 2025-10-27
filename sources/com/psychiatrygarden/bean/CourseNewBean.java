package com.psychiatrygarden.bean;

import java.io.Serializable;
import java.util.List;

/* loaded from: classes5.dex */
public class CourseNewBean implements Serializable {
    private String code;
    private List<DataBeanXX> data;
    private String message;
    private String server_time;

    public static class DataBeanXX implements Serializable {
        private String activity_id;
        private String app_id;
        private String count;
        private String cover;
        private List<DataBeanX> data;
        private String have;
        private String id;
        private String is_open;
        private String is_open_link;
        private String parent_id;
        private String see_count;
        private String student_type;
        private String title;
        private String xue_activity_id;

        public static class DataBeanX implements Serializable {
            private String activity_id;
            private String app_id;
            private String count;
            private String cover;
            private List<DataBean> data;
            private String have;
            private String id;
            private String is_open;
            private String is_open_link;
            private String parent_id;
            private String see_count;
            private String student_type;
            private String title;
            private String xue_activity_id;

            public static class DataBean implements Serializable {
                private String activity_id;
                private String app_id;
                private String count;
                private String cover;
                private String have;
                private String id;
                private String is_open;
                private String is_open_link;
                private String parent_id;
                private String see_count;
                private String student_type;
                private String title;
                private String xue_activity_id;
                private String discription_img = "";
                private String lecturer = "";

                public String getActivity_id() {
                    return this.activity_id;
                }

                public String getApp_id() {
                    return this.app_id;
                }

                public String getCount() {
                    return this.count;
                }

                public String getCover() {
                    return this.cover;
                }

                public String getDiscription_img() {
                    return this.discription_img;
                }

                public String getHave() {
                    return this.have;
                }

                public String getId() {
                    return this.id;
                }

                public String getIs_open() {
                    return this.is_open;
                }

                public String getIs_open_link() {
                    return this.is_open_link;
                }

                public String getLecturer() {
                    return this.lecturer;
                }

                public String getParent_id() {
                    return this.parent_id;
                }

                public String getSee_count() {
                    return this.see_count;
                }

                public String getStudent_type() {
                    return this.student_type;
                }

                public String getTitle() {
                    return this.title;
                }

                public String getXue_activity_id() {
                    return this.xue_activity_id;
                }

                public void setActivity_id(String activity_id) {
                    this.activity_id = activity_id;
                }

                public void setApp_id(String app_id) {
                    this.app_id = app_id;
                }

                public void setCount(String count) {
                    this.count = count;
                }

                public void setCover(String cover) {
                    this.cover = cover;
                }

                public void setDiscription_img(String discription_img) {
                    this.discription_img = discription_img;
                }

                public void setHave(String have) {
                    this.have = have;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public void setIs_open(String is_open) {
                    this.is_open = is_open;
                }

                public void setIs_open_link(String is_open_link) {
                    this.is_open_link = is_open_link;
                }

                public void setLecturer(String lecturer) {
                    this.lecturer = lecturer;
                }

                public void setParent_id(String parent_id) {
                    this.parent_id = parent_id;
                }

                public void setSee_count(String see_count) {
                    this.see_count = see_count;
                }

                public void setStudent_type(String student_type) {
                    this.student_type = student_type;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public void setXue_activity_id(String xue_activity_id) {
                    this.xue_activity_id = xue_activity_id;
                }
            }

            public String getActivity_id() {
                return this.activity_id;
            }

            public String getApp_id() {
                return this.app_id;
            }

            public String getCount() {
                return this.count;
            }

            public String getCover() {
                return this.cover;
            }

            public List<DataBean> getData() {
                return this.data;
            }

            public String getHave() {
                return this.have;
            }

            public String getId() {
                return this.id;
            }

            public String getIs_open() {
                return this.is_open;
            }

            public String getIs_open_link() {
                return this.is_open_link;
            }

            public String getParent_id() {
                return this.parent_id;
            }

            public String getSee_count() {
                return this.see_count;
            }

            public String getStudent_type() {
                return this.student_type;
            }

            public String getTitle() {
                return this.title;
            }

            public String getXue_activity_id() {
                return this.xue_activity_id;
            }

            public void setActivity_id(String activity_id) {
                this.activity_id = activity_id;
            }

            public void setApp_id(String app_id) {
                this.app_id = app_id;
            }

            public void setCount(String count) {
                this.count = count;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public void setData(List<DataBean> data) {
                this.data = data;
            }

            public void setHave(String have) {
                this.have = have;
            }

            public void setId(String id) {
                this.id = id;
            }

            public void setIs_open(String is_open) {
                this.is_open = is_open;
            }

            public void setIs_open_link(String is_open_link) {
                this.is_open_link = is_open_link;
            }

            public void setParent_id(String parent_id) {
                this.parent_id = parent_id;
            }

            public void setSee_count(String see_count) {
                this.see_count = see_count;
            }

            public void setStudent_type(String student_type) {
                this.student_type = student_type;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setXue_activity_id(String xue_activity_id) {
                this.xue_activity_id = xue_activity_id;
            }
        }

        public String getActivity_id() {
            return this.activity_id;
        }

        public String getApp_id() {
            return this.app_id;
        }

        public String getCount() {
            return this.count;
        }

        public String getCover() {
            return this.cover;
        }

        public List<DataBeanX> getData() {
            return this.data;
        }

        public String getHave() {
            return this.have;
        }

        public String getId() {
            return this.id;
        }

        public String getIs_open() {
            return this.is_open;
        }

        public String getIs_open_link() {
            return this.is_open_link;
        }

        public String getParent_id() {
            return this.parent_id;
        }

        public String getSee_count() {
            return this.see_count;
        }

        public String getStudent_type() {
            return this.student_type;
        }

        public String getTitle() {
            return this.title;
        }

        public String getXue_activity_id() {
            return this.xue_activity_id;
        }

        public void setActivity_id(String activity_id) {
            this.activity_id = activity_id;
        }

        public void setApp_id(String app_id) {
            this.app_id = app_id;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public void setData(List<DataBeanX> data) {
            this.data = data;
        }

        public void setHave(String have) {
            this.have = have;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setIs_open(String is_open) {
            this.is_open = is_open;
        }

        public void setIs_open_link(String is_open_link) {
            this.is_open_link = is_open_link;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }

        public void setSee_count(String see_count) {
            this.see_count = see_count;
        }

        public void setStudent_type(String student_type) {
            this.student_type = student_type;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setXue_activity_id(String xue_activity_id) {
            this.xue_activity_id = xue_activity_id;
        }
    }

    public String getCode() {
        return this.code;
    }

    public List<DataBeanXX> getData() {
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

    public void setData(List<DataBeanXX> data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setServer_time(String server_time) {
        this.server_time = server_time;
    }
}
