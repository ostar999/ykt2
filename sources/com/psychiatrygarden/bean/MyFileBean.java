package com.psychiatrygarden.bean;

import java.util.List;

/* loaded from: classes5.dex */
public class MyFileBean {
    private String code;
    private List<FileDataBean> data;
    private String message;

    public static class FileDataBean {
        private String article_id;
        private String desc;
        private String enclosure_id;
        private String icon;
        private String size;
        private String title;
        private String type;
        private String type_name;
        private String url;

        public String getArticle_id() {
            return this.article_id;
        }

        public String getDesc() {
            return this.desc;
        }

        public String getEnclosure_id() {
            return this.enclosure_id;
        }

        public String getIcon() {
            return this.icon;
        }

        public String getSize() {
            return this.size;
        }

        public String getTitle() {
            return this.title;
        }

        public String getType() {
            return this.type;
        }

        public String getType_name() {
            return this.type_name;
        }

        public String getUrl() {
            return this.url;
        }

        public void setArticle_id(String article_id) {
            this.article_id = article_id;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public void setEnclosure_id(String enclosure_id) {
            this.enclosure_id = enclosure_id;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public String getCode() {
        return this.code;
    }

    public List<FileDataBean> getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(List<FileDataBean> data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
