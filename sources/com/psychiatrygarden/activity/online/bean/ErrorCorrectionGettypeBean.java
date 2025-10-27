package com.psychiatrygarden.activity.online.bean;

import java.util.List;

/* loaded from: classes5.dex */
public class ErrorCorrectionGettypeBean {
    private String code;
    private List<DataDTO> data;
    private String message;
    private String server_time;

    public static class DataDTO {
        private String id;
        private String title;

        public String getId() {
            return this.id;
        }

        public String getTitle() {
            return this.title;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public String getCode() {
        return this.code;
    }

    public List<DataDTO> getData() {
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

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setServer_time(String server_time) {
        this.server_time = server_time;
    }
}
