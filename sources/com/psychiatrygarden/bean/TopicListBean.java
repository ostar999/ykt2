package com.psychiatrygarden.bean;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

/* loaded from: classes5.dex */
public class TopicListBean implements Serializable {

    @SerializedName("code")
    private String code;

    @SerializedName("data")
    private List<DataDTO> data;

    @SerializedName("message")
    private String message;

    @SerializedName("server_time")
    private String serverTime;

    public static class DataDTO implements Serializable {

        @SerializedName("id")
        private String id = "";
        private int is_default = 0;

        @SerializedName("name")
        private String name;

        public String getId() {
            return this.id;
        }

        public int getIs_default() {
            return this.is_default;
        }

        public String getName() {
            return this.name;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setIs_default(int is_default) {
            this.is_default = is_default;
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

    public String getServerTime() {
        return this.serverTime;
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

    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }
}
