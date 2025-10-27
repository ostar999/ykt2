package com.aliyun.player.aliyunplayerbase.bean;

/* loaded from: classes2.dex */
public class AliyunPlayAuth {
    private int code;
    private PlayAuthBean data;
    private String message;

    public class PlayAuthBean {
        private String playAuth;
        private VideoMetaBean videoMeta;

        public class VideoMetaBean {
            private String coverURL;
            private String duration;
            private String status;
            private String title;
            private String videoId;

            public VideoMetaBean() {
            }

            public String getCoverURL() {
                return this.coverURL;
            }

            public String getDuration() {
                return this.duration;
            }

            public String getStatus() {
                return this.status;
            }

            public String getTitle() {
                return this.title;
            }

            public String getVideoId() {
                return this.videoId;
            }

            public void setCoverURL(String str) {
                this.coverURL = str;
            }

            public void setDuration(String str) {
                this.duration = str;
            }

            public void setStatus(String str) {
                this.status = str;
            }

            public void setTitle(String str) {
                this.title = str;
            }

            public void setVideoId(String str) {
                this.videoId = str;
            }
        }

        public PlayAuthBean() {
        }

        public String getPlayAuth() {
            return this.playAuth;
        }

        public VideoMetaBean getVideoMeta() {
            return this.videoMeta;
        }

        public void setPlayAuth(String str) {
            this.playAuth = str;
        }

        public void setVideoMeta(VideoMetaBean videoMetaBean) {
            this.videoMeta = videoMetaBean;
        }
    }

    public int getCode() {
        return this.code;
    }

    public PlayAuthBean getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setCode(int i2) {
        this.code = i2;
    }

    public void setData(PlayAuthBean playAuthBean) {
        this.data = playAuthBean;
    }

    public void setMessage(String str) {
        this.message = str;
    }
}
