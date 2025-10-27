package com.psychiatrygarden.bean;

/* loaded from: classes5.dex */
public class QuestioBean {
    private String code;
    private QuestionInfoBean data;
    private String message;

    public static class LiveAbout {
        private LiveInfo live_info;
        private LiveIni live_ini;
        private String live_status;
        private String live_tips;
        private String show_live_button;

        public LiveInfo getLive_info() {
            return this.live_info;
        }

        public LiveIni getLive_ini() {
            return this.live_ini;
        }

        public String getLive_status() {
            return this.live_status;
        }

        public String getLive_tips() {
            return this.live_tips;
        }

        public String getShow_live_button() {
            return this.show_live_button;
        }

        public void setLive_info(LiveInfo live_info) {
            this.live_info = live_info;
        }

        public void setLive_ini(LiveIni live_ini) {
            this.live_ini = live_ini;
        }

        public void setLive_status(String live_status) {
            this.live_status = live_status;
        }

        public void setLive_tips(String live_tips) {
            this.live_tips = live_tips;
        }

        public void setShow_live_button(String show_live_button) {
            this.show_live_button = show_live_button;
        }
    }

    public static class LiveInfo {
        private String live_id;
        private String room_id;
        private String start_live_time;
        private String vid;
        private String video_id;

        public String getLive_id() {
            return this.live_id;
        }

        public String getRoom_id() {
            return this.room_id;
        }

        public String getStart_live_time() {
            return this.start_live_time;
        }

        public String getVid() {
            return this.vid;
        }

        public String getVideo_id() {
            return this.video_id;
        }

        public void setLive_id(String live_id) {
            this.live_id = live_id;
        }

        public void setRoom_id(String room_id) {
            this.room_id = room_id;
        }

        public void setStart_live_time(String start_live_time) {
            this.start_live_time = start_live_time;
        }

        public void setVid(String vid) {
            this.vid = vid;
        }

        public void setVideo_id(String video_id) {
            this.video_id = video_id;
        }
    }

    public static class LiveIni {
        private String polyv_app_id;
        private String polyv_app_secret;
        private String polyv_user_id;

        public String getPolyv_app_id() {
            return this.polyv_app_id;
        }

        public String getPolyv_app_secret() {
            return this.polyv_app_secret;
        }

        public String getPolyv_user_id() {
            return this.polyv_user_id;
        }

        public void setPolyv_app_id(String polyv_app_id) {
            this.polyv_app_id = polyv_app_id;
        }

        public void setPolyv_app_secret(String polyv_app_secret) {
            this.polyv_app_secret = polyv_app_secret;
        }

        public void setPolyv_user_id(String polyv_user_id) {
            this.polyv_user_id = polyv_user_id;
        }
    }

    public static class QuestionInfoBean {
        private int comment_count;
        private String is_collected;
        private int is_comment;
        private int is_praise;
        private LiveAbout live_about;

        public int getComment_count() {
            return this.comment_count;
        }

        public String getIs_collected() {
            return this.is_collected;
        }

        public int getIs_comment() {
            return this.is_comment;
        }

        public int getIs_praise() {
            return this.is_praise;
        }

        public LiveAbout getLive_about() {
            return this.live_about;
        }

        public void setComment_count(int comment_count) {
            this.comment_count = comment_count;
        }

        public void setIs_collected(String is_collected) {
            this.is_collected = is_collected;
        }

        public void setIs_comment(int is_comment) {
            this.is_comment = is_comment;
        }

        public void setIs_praise(int is_praise) {
            this.is_praise = is_praise;
        }

        public void setLive_about(LiveAbout live_about) {
            this.live_about = live_about;
        }
    }

    public String getCode() {
        return this.code;
    }

    public QuestionInfoBean getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(QuestionInfoBean data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
