package com.psychiatrygarden.activity.courselist.bean;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class CurriculumLiveBean implements Serializable {

    @SerializedName("code")
    private String code;

    @SerializedName("data")
    private List<DataDTO> data = new ArrayList();

    @SerializedName("message")
    private String message;

    @SerializedName("server_time")
    private String serverTime;

    public static class DataDTO implements Serializable {
        private String cover;
        private int status;

        @SerializedName("id")
        private String id = "";

        @SerializedName("title")
        private String title = "";
        private String teacher = "";

        @SerializedName("live_time_str")
        private String liveTimeStr = "";

        @SerializedName("room_id")
        private String roomId = "";

        @SerializedName("user_id")
        private String userId = "";

        @SerializedName("app_id")
        private String appId = "";

        @SerializedName("app_secret")
        private String appSecret = "";

        @SerializedName("live_status")
        private String liveStatus = "";

        @SerializedName("vid")
        private String vid = "";

        @SerializedName("start_live_time")
        private long startLiveTime = 0;
        private String process = "0";
        private String see = "0";
        private String duration = "0";
        private String is_free_watch = "0";
        private int isSelected = 0;
        private String size = "0";

        public String getAppId() {
            return this.appId;
        }

        public String getAppSecret() {
            return this.appSecret;
        }

        public String getCover() {
            return this.cover;
        }

        public String getDuration() {
            return this.duration;
        }

        public String getId() {
            return this.id;
        }

        public int getIsSelected() {
            return this.isSelected;
        }

        public String getIs_free_watch() {
            return this.is_free_watch;
        }

        public String getLiveStatus() {
            return this.liveStatus;
        }

        public String getLiveTimeStr() {
            return this.liveTimeStr;
        }

        public String getProcess() {
            return this.process;
        }

        public String getRoomId() {
            return this.roomId;
        }

        public String getSee() {
            return this.see;
        }

        public String getSize() {
            return this.size;
        }

        public long getStartLiveTime() {
            return this.startLiveTime;
        }

        public int getStatus() {
            return this.status;
        }

        public String getTeacher() {
            return this.teacher;
        }

        public String getTitle() {
            return this.title;
        }

        public String getUserId() {
            return this.userId;
        }

        public String getVid() {
            return this.vid;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public void setAppSecret(String appSecret) {
            this.appSecret = appSecret;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setIsSelected(int isSelected) {
            this.isSelected = isSelected;
        }

        public void setIs_free_watch(String is_free_watch) {
            this.is_free_watch = is_free_watch;
        }

        public void setLiveStatus(String liveStatus) {
            this.liveStatus = liveStatus;
        }

        public void setLiveTimeStr(String liveTimeStr) {
            this.liveTimeStr = liveTimeStr;
        }

        public void setProcess(String process) {
            this.process = process;
        }

        public void setRoomId(String roomId) {
            this.roomId = roomId;
        }

        public void setSee(String see) {
            this.see = see;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public void setStartLiveTime(long startLiveTime) {
            this.startLiveTime = startLiveTime;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public void setTeacher(String teacher) {
            this.teacher = teacher;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setUserId(String userId) {
            this.userId = userId;
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
