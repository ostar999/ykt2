package com.aliyun.player.aliyunplayerbase.bean;

import java.util.List;

/* loaded from: classes2.dex */
public class AliyunVideoListBean {
    public static final String STATUS_CENSOR_FAIL = "fail";
    public static final String STATUS_CENSOR_ON = "onCensor";
    public static final String STATUS_CENSOR_SUCCESS = "success";
    public static final String STATUS_CENSOR_WAIT = "check";
    private int code;
    private VideoDataBean data;
    private String message;

    public static class VideoDataBean {
        private int total;
        private List<VideoListBean> videoList;

        public static class VideoListBean {
            private String censorStatus;
            private String coverUrl;
            private String firstFrameUrl;
            protected String id = "";
            private String status;
            private String title;
            private String videoId;

            public String getCensorStatus() {
                return this.censorStatus;
            }

            public String getCoverUrl() {
                return this.coverUrl;
            }

            public String getFirstFrameUrl() {
                return this.firstFrameUrl;
            }

            public int getId() {
                try {
                    return Integer.parseInt(this.id);
                } catch (NumberFormatException e2) {
                    e2.printStackTrace();
                    return 0;
                }
            }

            public VideoSourceType getSourceType() {
                return "success".equals(this.censorStatus) ? VideoSourceType.TYPE_STS : VideoSourceType.TYPE_ERROR_NOT_SHOW;
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

            public void setCensorStatus(String str) {
                this.censorStatus = str;
            }

            public void setCoverUrl(String str) {
                this.coverUrl = str;
            }

            public void setFirstFrameUrl(String str) {
                this.firstFrameUrl = str;
            }

            public void setId(String str) {
                this.id = str;
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

            public void setId(int i2) {
                this.id = i2 + "";
            }
        }

        public int getTotal() {
            return this.total;
        }

        public List<VideoListBean> getVideoList() {
            return this.videoList;
        }

        public void setTotal(int i2) {
            this.total = i2;
        }

        public void setVideoList(List<VideoListBean> list) {
            this.videoList = list;
        }
    }

    public int getCode() {
        return this.code;
    }

    public VideoDataBean getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setCode(int i2) {
        this.code = i2;
    }

    public void setData(VideoDataBean videoDataBean) {
        this.data = videoDataBean;
    }

    public void setMessage(String str) {
        this.message = str;
    }
}
