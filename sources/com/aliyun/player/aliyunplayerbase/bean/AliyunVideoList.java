package com.aliyun.player.aliyunplayerbase.bean;

import java.util.List;

/* loaded from: classes2.dex */
public class AliyunVideoList {
    private int code;
    private VideoList data;
    private String message;

    public class VideoList {
        private List<VideoListItem> playInfoList;
        private VideoBase videoBase;

        public class VideoBase {
            private String coverURL;
            private String title;
            private String videoId;

            public VideoBase() {
            }

            public String getCoverURL() {
                return this.coverURL;
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

            public void setTitle(String str) {
                this.title = str;
            }

            public void setVideoId(String str) {
                this.videoId = str;
            }
        }

        public class VideoListItem {
            private String coverURL;
            private String definition;
            private String duration;
            private int encrypt;
            private String format;
            private String playURL;
            private String specification;
            private String title;

            public VideoListItem() {
            }

            public String getCoverURL() {
                return this.coverURL;
            }

            public String getDefinition() {
                return this.definition;
            }

            public String getDuration() {
                return this.duration;
            }

            public int getEncrypt() {
                return this.encrypt;
            }

            public String getFormat() {
                return this.format;
            }

            public String getPlayURL() {
                return this.playURL;
            }

            public String getSpecification() {
                return this.specification;
            }

            public String getTitle() {
                return this.title;
            }

            public void setCoverURL(String str) {
                this.coverURL = str;
            }

            public void setDefinition(String str) {
                this.definition = str;
            }

            public void setDuration(String str) {
                this.duration = str;
            }

            public void setEncrypt(int i2) {
                this.encrypt = i2;
            }

            public void setFormat(String str) {
                this.format = str;
            }

            public void setPlayURL(String str) {
                this.playURL = str;
            }

            public void setSpecification(String str) {
                this.specification = str;
            }

            public void setTitle(String str) {
                this.title = str;
            }
        }

        public VideoList() {
        }

        public List<VideoListItem> getPlayInfoList() {
            return this.playInfoList;
        }

        public VideoBase getVideoBase() {
            return this.videoBase;
        }

        public void setPlayInfoList(List<VideoListItem> list) {
            this.playInfoList = list;
        }

        public void setVideoBase(VideoBase videoBase) {
            this.videoBase = videoBase;
        }
    }

    public int getCode() {
        return this.code;
    }

    public VideoList getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setCode(int i2) {
        this.code = i2;
    }

    public void setData(VideoList videoList) {
        this.data = videoList;
    }

    public void setMessage(String str) {
        this.message = str;
    }
}
