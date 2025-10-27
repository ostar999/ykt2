package com.psychiatrygarden.activity.courselist.bean;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.utils.CommonParameter;
import java.io.Serializable;
import java.util.List;

/* loaded from: classes5.dex */
public class CourseVideoListBean implements Serializable {
    private String code;
    private List<DataBean> data;
    private String message;
    private String server_time;

    @Entity(primaryKeys = {"userAndAppId", "vid", "chapter_id", "type"}, tableName = "course_video")
    public static class DataBean implements Serializable {

        @Ignore
        private String akId;

        @Ignore
        private String akSecret;
        private String app_id;

        @NonNull
        private String chapter_id;
        private String comment_count;
        private String count;
        private String description;
        private String duration;
        private String duration_text;

        @NonNull
        private int id;
        private String lecturer;
        private int progress;
        private int sort;

        @Ignore
        private String st;
        private int stutas;
        private String thumb;
        private String title;

        @NonNull
        private String type;

        @NonNull
        private String vid;
        private String vidteaching_id;
        private String watched;

        @NonNull
        public String userAndAppId = UserConfig.getUserId() + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ProjectApp.instance());

        @Ignore
        public String mSavePath = "";
        public String collection = "0";
        public String note = "0";
        public String is_see = "0";
        public String sign_time = "";
        public String free_watch_time = "0";
        public String watch_permission = "0";
        public String expire_str = "";
        public String activity_id = "";

        public String getActivity_id() {
            return this.activity_id;
        }

        public String getAkId() {
            return this.akId;
        }

        public String getAkSecret() {
            return this.akSecret;
        }

        public String getApp_id() {
            return this.app_id;
        }

        public String getChapter_id() {
            return this.chapter_id;
        }

        public String getCollection() {
            return this.collection;
        }

        public String getComment_count() {
            return this.comment_count;
        }

        public String getCount() {
            return this.count;
        }

        public String getDescription() {
            return this.description;
        }

        public String getDuration() {
            return this.duration;
        }

        public String getDuration_text() {
            return this.duration_text;
        }

        public String getExpire_str() {
            return this.expire_str;
        }

        public String getFree_watch_time() {
            return this.free_watch_time;
        }

        public int getId() {
            return this.id;
        }

        public String getIs_see() {
            return this.is_see;
        }

        public String getLecturer() {
            return this.lecturer;
        }

        public String getNote() {
            return this.note;
        }

        public int getProgress() {
            return this.progress;
        }

        public String getSign_time() {
            return this.sign_time;
        }

        public int getSort() {
            return this.sort;
        }

        public String getSt() {
            return this.st;
        }

        public int getStutas() {
            return this.stutas;
        }

        public String getThumb() {
            return this.thumb;
        }

        public String getTitle() {
            return this.title;
        }

        public String getType() {
            return this.type;
        }

        @NonNull
        public String getUserAndAppId() {
            return this.userAndAppId;
        }

        public String getVid() {
            return this.vid;
        }

        public String getVidteaching_id() {
            return this.vidteaching_id;
        }

        public String getWatch_permission() {
            return this.watch_permission;
        }

        public String getWatched() {
            return this.watched;
        }

        public String getmSavePath() {
            return this.mSavePath;
        }

        public void setActivity_id(String activity_id) {
            this.activity_id = activity_id;
        }

        public void setAkId(String akId) {
            this.akId = akId;
        }

        public void setAkSecret(String akSecret) {
            this.akSecret = akSecret;
        }

        public void setApp_id(String app_id) {
            this.app_id = app_id;
        }

        public void setChapter_id(String chapter_id) {
            this.chapter_id = chapter_id;
        }

        public void setCollection(String collection) {
            this.collection = collection;
        }

        public void setComment_count(String comment_count) {
            this.comment_count = comment_count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public void setDuration_text(String duration_text) {
            this.duration_text = duration_text;
        }

        public void setExpire_str(String expire_str) {
            this.expire_str = expire_str;
        }

        public void setFree_watch_time(String free_watch_time) {
            this.free_watch_time = free_watch_time;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setIs_see(String is_see) {
            this.is_see = is_see;
        }

        public void setLecturer(String lecturer) {
            this.lecturer = lecturer;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public void setProgress(int progress) {
            this.progress = progress;
        }

        public void setSign_time(String sign_time) {
            this.sign_time = sign_time;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public void setSt(String st) {
            this.st = st;
        }

        public void setStutas(int stutas) {
            this.stutas = stutas;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setUserAndAppId(@NonNull String userAndAppId) {
            this.userAndAppId = userAndAppId;
        }

        public void setVid(String vid) {
            this.vid = vid;
        }

        public void setVidteaching_id(String vidteaching_id) {
            this.vidteaching_id = vidteaching_id;
        }

        public void setWatch_permission(String watch_permission) {
            this.watch_permission = watch_permission;
        }

        public void setWatched(String watched) {
            this.watched = watched;
        }

        public void setmSavePath(String mSavePath) {
            this.mSavePath = mSavePath;
        }
    }

    public String getCode() {
        return this.code;
    }

    public List<DataBean> getData() {
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

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setServer_time(String server_time) {
        this.server_time = server_time;
    }
}
