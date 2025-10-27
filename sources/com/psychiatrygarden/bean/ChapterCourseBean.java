package com.psychiatrygarden.bean;

import java.io.Serializable;
import java.util.List;

/* loaded from: classes5.dex */
public class ChapterCourseBean implements Serializable {
    private String code;
    private List<DataBean> data;
    private String message;
    private String server_time;

    public static class DataBean implements Serializable {
        private List<ChapterBean> chapter;
        private String id;
        private String number_video;
        private String title;

        public static class ChapterBean implements Serializable {
            private String id;
            private String number_video;
            private String title;

            public String getId() {
                return this.id;
            }

            public String getNumber_video() {
                return this.number_video;
            }

            public String getTitle() {
                return this.title;
            }

            public void setId(String id) {
                this.id = id;
            }

            public void setNumber_video(String number_video) {
                this.number_video = number_video;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }

        public List<ChapterBean> getChapter() {
            return this.chapter;
        }

        public String getId() {
            return this.id;
        }

        public String getNumber_video() {
            return this.number_video;
        }

        public String getTitle() {
            return this.title;
        }

        public void setChapter(List<ChapterBean> chapter) {
            this.chapter = chapter;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setNumber_video(String number_video) {
            this.number_video = number_video;
        }

        public void setTitle(String title) {
            this.title = title;
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
