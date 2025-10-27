package com.psychiatrygarden.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class ClassReminderBean implements Serializable {
    private ClassKeyInfoBean ak;
    private String code;
    private List<ClassReminderListBean> data = new ArrayList();
    private String message;
    private String server_time;

    public class ClassKeyInfoBean {
        private String akId;
        private String akSecret;

        public ClassKeyInfoBean() {
        }

        public String getAkId() {
            return this.akId;
        }

        public String getAkSecret() {
            return this.akSecret;
        }

        public void setAkId(String akId) {
            this.akId = akId;
        }

        public void setAkSecret(String akSecret) {
            this.akSecret = akSecret;
        }
    }

    public class ClassReminderListBean {
        private String activity_id;
        private String content;
        private String course_id;
        private String course_type;
        private String ctime;
        private String have_chapter;
        private String id;
        private String polyv_app_id;
        private String polyv_app_secret;
        private String polyv_user_id;
        private String room_id;
        private String time_text;
        private String title;
        private String type;
        private String type_text;
        private String user_id;
        private String video_id;

        public ClassReminderListBean() {
        }

        public String getActivity_id() {
            return this.activity_id;
        }

        public String getContent() {
            return this.content;
        }

        public String getCourse_id() {
            return this.course_id;
        }

        public String getCourse_type() {
            return this.course_type;
        }

        public String getCtime() {
            return this.ctime;
        }

        public String getHave_chapter() {
            return this.have_chapter;
        }

        public String getId() {
            return this.id;
        }

        public String getPolyv_app_id() {
            return this.polyv_app_id;
        }

        public String getPolyv_app_secret() {
            return this.polyv_app_secret;
        }

        public String getPolyv_user_id() {
            return this.polyv_user_id;
        }

        public String getRoom_id() {
            return this.room_id;
        }

        public String getTime_text() {
            return this.time_text;
        }

        public String getTitle() {
            return this.title;
        }

        public String getType() {
            return this.type;
        }

        public String getType_text() {
            return this.type_text;
        }

        public String getUser_id() {
            return this.user_id;
        }

        public String getVideo_id() {
            return this.video_id;
        }

        public void setActivity_id(String activity_id) {
            this.activity_id = activity_id;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setCourse_id(String course_id) {
            this.course_id = course_id;
        }

        public void setCourse_type(String course_type) {
            this.course_type = course_type;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public void setHave_chapter(String have_chapter) {
            this.have_chapter = have_chapter;
        }

        public void setId(String id) {
            this.id = id;
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

        public void setRoom_id(String room_id) {
            this.room_id = room_id;
        }

        public void setTime_text(String time_text) {
            this.time_text = time_text;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setType_text(String type_text) {
            this.type_text = type_text;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public void setVideo_id(String video_id) {
            this.video_id = video_id;
        }
    }

    public ClassKeyInfoBean getAk() {
        return this.ak;
    }

    public String getCode() {
        return this.code;
    }

    public List<ClassReminderListBean> getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public String getServer_time() {
        return this.server_time;
    }

    public void setAk(ClassKeyInfoBean ak) {
        this.ak = ak;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(List<ClassReminderListBean> data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setServer_time(String server_time) {
        this.server_time = server_time;
    }
}
