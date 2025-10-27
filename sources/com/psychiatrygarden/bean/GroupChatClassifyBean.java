package com.psychiatrygarden.bean;

import java.util.List;

/* loaded from: classes5.dex */
public class GroupChatClassifyBean {
    private String code;
    private List<DataDTO> data;
    private String message;
    private String server_time;

    public static class DataDTO {
        private String id;
        private String is_select = "0";
        private String name;

        public String getId() {
            return this.id;
        }

        public String getIs_select() {
            return this.is_select;
        }

        public String getName() {
            return this.name;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setIs_select(String is_select) {
            this.is_select = is_select;
        }

        public void setName(String name) {
            this.name = name;
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
