package com.psychiatrygarden.activity.purchase.beans;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class TryAndSeeBean implements Serializable {

    @SerializedName("code")
    private String code;

    @SerializedName("data")
    private List<DataDTO> data = new ArrayList();

    @SerializedName("message")
    private String message;

    @SerializedName("server_time")
    private String serverTime;

    public static class DataDTO implements Serializable {

        @SerializedName("id")
        private String id = "";

        @SerializedName("title")
        private String title = "";

        @SerializedName("vid")
        private String vid = "";

        public String getId() {
            return this.id;
        }

        public String getTitle() {
            return this.title;
        }

        public String getVid() {
            return this.vid;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setVid(String vid) {
            this.vid = vid;
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
