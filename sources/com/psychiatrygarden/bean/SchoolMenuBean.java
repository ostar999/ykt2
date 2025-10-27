package com.psychiatrygarden.bean;

import java.util.List;

/* loaded from: classes5.dex */
public class SchoolMenuBean {
    private String code;
    private List<SchoolMenuData> data;
    private String message;

    public class SchoolMenuData {
        private String icon;
        private String id;
        private String label;
        private String name;
        private String push_type;

        public SchoolMenuData() {
        }

        public String getIcon() {
            return this.icon;
        }

        public String getId() {
            return this.id;
        }

        public String getLabel() {
            return this.label;
        }

        public String getName() {
            return this.name;
        }

        public String getPush_type() {
            return this.push_type;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPush_type(String push_type) {
            this.push_type = push_type;
        }
    }

    public String getCode() {
        return this.code;
    }

    public List<SchoolMenuData> getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(List<SchoolMenuData> data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
