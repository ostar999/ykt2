package com.psychiatrygarden.bean;

import java.util.List;

/* loaded from: classes5.dex */
public class MaterialTab {
    private String code;
    private List<MaterialTabData> data;
    private String message;

    public static class MaterialTabData {
        private String app_id;
        private String app_name;
        private String download_count;
        private String file_count;
        private String icon;
        private boolean select;

        public String getApp_name() {
            return this.app_name;
        }

        public String getDownload_count() {
            return this.download_count;
        }

        public String getFile_count() {
            return this.file_count;
        }

        public String getIcon() {
            return this.icon;
        }

        public String getId() {
            return this.app_id;
        }

        public boolean isSelect() {
            return this.select;
        }

        public void setApp_name(String app_name) {
            this.app_name = app_name;
        }

        public void setDownload_count(String download_count) {
            this.download_count = download_count;
        }

        public void setFile_count(String file_count) {
            this.file_count = file_count;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public void setId(String id) {
            this.app_id = id;
        }

        public void setSelect(boolean select) {
            this.select = select;
        }
    }

    public String getCode() {
        return this.code;
    }

    public List<MaterialTabData> getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(List<MaterialTabData> data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
