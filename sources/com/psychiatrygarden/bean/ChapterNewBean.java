package com.psychiatrygarden.bean;

import java.io.Serializable;
import java.util.List;

/* loaded from: classes5.dex */
public class ChapterNewBean implements Serializable {
    private String code;
    private List<DataBean> data;
    private String message;
    private String server_time;

    public static class DataBean implements Serializable {
        private String chapter_id;
        private List<ChildrenBean> children;
        private String collection_id;
        private String done;
        private String have;
        private String parent_id;
        private String title;
        private String total;
        private String right_count = "0";
        private String error_count = "0";

        public static class ChildrenBean implements Serializable {
            private String chapter_id;
            private String collection_id;
            private String have;
            private String parent_id;
            private String title;
            private String total = "0";
            private String done = "0";
            private String right_count = "0";
            private String error_count = "0";

            public String getChapter_id() {
                return this.chapter_id;
            }

            public String getCollection_id() {
                return this.collection_id;
            }

            public String getDone() {
                return this.done;
            }

            public String getError_count() {
                return this.error_count;
            }

            public String getHave() {
                return this.have;
            }

            public String getParent_id() {
                return this.parent_id;
            }

            public String getRight_count() {
                return this.right_count;
            }

            public String getTitle() {
                return this.title;
            }

            public String getTotal() {
                return this.total;
            }

            public void setChapter_id(String chapter_id) {
                this.chapter_id = chapter_id;
            }

            public void setCollection_id(String collection_id) {
                this.collection_id = collection_id;
            }

            public void setDone(String done) {
                this.done = done;
            }

            public void setError_count(String error_count) {
                this.error_count = error_count;
            }

            public void setHave(String have) {
                this.have = have;
            }

            public void setParent_id(String parent_id) {
                this.parent_id = parent_id;
            }

            public void setRight_count(String right_count) {
                this.right_count = right_count;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setTotal(String total) {
                this.total = total;
            }
        }

        public String getChapter_id() {
            return this.chapter_id;
        }

        public List<ChildrenBean> getChildren() {
            return this.children;
        }

        public String getCollection_id() {
            return this.collection_id;
        }

        public String getDone() {
            return this.done;
        }

        public String getError_count() {
            return this.error_count;
        }

        public String getHave() {
            return this.have;
        }

        public String getParent_id() {
            return this.parent_id;
        }

        public String getRight_count() {
            return this.right_count;
        }

        public String getTitle() {
            return this.title;
        }

        public String getTotal() {
            return this.total;
        }

        public void setChapter_id(String chapter_id) {
            this.chapter_id = chapter_id;
        }

        public void setChildren(List<ChildrenBean> children) {
            this.children = children;
        }

        public void setCollection_id(String collection_id) {
            this.collection_id = collection_id;
        }

        public void setDone(String done) {
            this.done = done;
        }

        public void setError_count(String error_count) {
            this.error_count = error_count;
        }

        public void setHave(String have) {
            this.have = have;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }

        public void setRight_count(String right_count) {
            this.right_count = right_count;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setTotal(String total) {
            this.total = total;
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
