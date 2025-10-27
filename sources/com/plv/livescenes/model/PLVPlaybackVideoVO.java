package com.plv.livescenes.model;

import cn.hutool.core.text.CharPool;
import com.google.gson.annotations.SerializedName;
import com.plv.business.model.PLVBaseVO;

/* loaded from: classes5.dex */
public class PLVPlaybackVideoVO implements PLVBaseVO {
    private Integer code;
    private Object data;
    private boolean encryption;
    private String message;
    private String status;

    public static class Data {
        private String asDefault;
        private String channelId;
        private String channelSessionId;

        @SerializedName("class")
        private String classX;
        private Long createdTime;
        private String duration;
        private Integer errorCount;
        private String fileId;
        private String fileUrl;
        private String firstImage;
        private Integer height;
        private String lang;
        private Long lastModified;
        private String listType;
        private String liveType;
        private String myBr;
        private String offlineJSUrl;
        private Integer ordertime;
        private String origin;
        private String originSessionId;
        private Integer pageMode;
        private String playbackCacheEnabled;
        private Integer rank;
        private Integer seed;
        private String startTime;
        private String status;
        private String title;
        private String url;
        private String userId;
        private VideoCache videoCache;
        private String videoId;
        private String videoPoolId;
        private Integer width;

        public static class VideoCache {
            private String channelId;
            private String createdTime;
            private String lastModified;
            private String liveType;
            private String pptJsonUrl;
            private String status;
            private String userId;
            private String videoId;
            private String videoPoolId;
            private Long videoSize;
            private String videoUrl;
            private Long zipSize;
            private String zipUrl;

            public String getChannelId() {
                return this.channelId;
            }

            public String getCreatedTime() {
                return this.createdTime;
            }

            public String getLastModified() {
                return this.lastModified;
            }

            public String getLiveType() {
                return this.liveType;
            }

            public String getPptJsonUrl() {
                return this.pptJsonUrl;
            }

            public String getStatus() {
                return this.status;
            }

            public String getUserId() {
                return this.userId;
            }

            public String getVideoId() {
                return this.videoId;
            }

            public String getVideoPoolId() {
                return this.videoPoolId;
            }

            public Long getVideoSize() {
                return this.videoSize;
            }

            public String getVideoUrl() {
                return this.videoUrl;
            }

            public Long getZipSize() {
                return this.zipSize;
            }

            public String getZipUrl() {
                return this.zipUrl;
            }

            public void setChannelId(String str) {
                this.channelId = str;
            }

            public void setCreatedTime(String str) {
                this.createdTime = str;
            }

            public void setLastModified(String str) {
                this.lastModified = str;
            }

            public void setLiveType(String str) {
                this.liveType = str;
            }

            public void setPptJsonUrl(String str) {
                this.pptJsonUrl = str;
            }

            public void setStatus(String str) {
                this.status = str;
            }

            public void setUserId(String str) {
                this.userId = str;
            }

            public void setVideoId(String str) {
                this.videoId = str;
            }

            public void setVideoPoolId(String str) {
                this.videoPoolId = str;
            }

            public void setVideoSize(long j2) {
                this.videoSize = Long.valueOf(j2);
            }

            public void setVideoUrl(String str) {
                this.videoUrl = str;
            }

            public void setZipSize(long j2) {
                this.zipSize = Long.valueOf(j2);
            }

            public void setZipUrl(String str) {
                this.zipUrl = str;
            }

            public String toString() {
                return "VideoCache{videoId='" + this.videoId + CharPool.SINGLE_QUOTE + ", videoPoolId='" + this.videoPoolId + CharPool.SINGLE_QUOTE + ", userId='" + this.userId + CharPool.SINGLE_QUOTE + ", channelId='" + this.channelId + CharPool.SINGLE_QUOTE + ", pptJsonUrl='" + this.pptJsonUrl + CharPool.SINGLE_QUOTE + ", videoUrl='" + this.videoUrl + CharPool.SINGLE_QUOTE + ", videoSize=" + this.videoSize + ", zipUrl='" + this.zipUrl + CharPool.SINGLE_QUOTE + ", zipSize=" + this.zipSize + ", liveType='" + this.liveType + CharPool.SINGLE_QUOTE + ", status='" + this.status + CharPool.SINGLE_QUOTE + ", createdTime='" + this.createdTime + CharPool.SINGLE_QUOTE + ", lastModified='" + this.lastModified + CharPool.SINGLE_QUOTE + '}';
            }
        }

        public String getAsDefault() {
            return this.asDefault;
        }

        public String getChannelId() {
            return this.channelId;
        }

        public String getChannelSessionId() {
            return this.channelSessionId;
        }

        public String getClassX() {
            return this.classX;
        }

        public Long getCreatedTime() {
            return this.createdTime;
        }

        public String getDuration() {
            return this.duration;
        }

        public Integer getErrorCount() {
            return this.errorCount;
        }

        public String getFileId() {
            return this.fileId;
        }

        public String getFileUrl() {
            return this.fileUrl;
        }

        public String getFirstImage() {
            return this.firstImage;
        }

        public Integer getHeight() {
            return this.height;
        }

        public String getLang() {
            return this.lang;
        }

        public Long getLastModified() {
            return this.lastModified;
        }

        public String getListType() {
            return this.listType;
        }

        public String getLiveType() {
            return this.liveType;
        }

        public String getMyBr() {
            return this.myBr;
        }

        public String getOfflineJSUrl() {
            return this.offlineJSUrl;
        }

        public Integer getOrdertime() {
            return this.ordertime;
        }

        public String getOrigin() {
            return this.origin;
        }

        public String getOriginSessionId() {
            return this.originSessionId;
        }

        public Integer getPageMode() {
            return this.pageMode;
        }

        public String getPlaybackCacheEnabled() {
            return "N";
        }

        public Integer getRank() {
            return this.rank;
        }

        public Integer getSeed() {
            return this.seed;
        }

        public String getStartTime() {
            return this.startTime;
        }

        public String getStatus() {
            return this.status;
        }

        public String getTitle() {
            return this.title;
        }

        public String getUrl() {
            return this.url;
        }

        public String getUserId() {
            return this.userId;
        }

        public VideoCache getVideoCache() {
            return this.videoCache;
        }

        public String getVideoId() {
            return this.videoId;
        }

        public String getVideoPoolId() {
            return this.videoPoolId;
        }

        public Integer getWidth() {
            return this.width;
        }

        public boolean isPlaybackCacheEnable() {
            VideoCache videoCache;
            return "Y".equals(getPlaybackCacheEnabled()) && (videoCache = this.videoCache) != null && "Y".equals(videoCache.getStatus());
        }

        public void setAsDefault(String str) {
            this.asDefault = str;
        }

        public void setChannelId(String str) {
            this.channelId = str;
        }

        public void setChannelSessionId(String str) {
            this.channelSessionId = str;
        }

        public void setClassX(String str) {
            this.classX = str;
        }

        public void setCreatedTime(Long l2) {
            this.createdTime = l2;
        }

        public void setDuration(String str) {
            this.duration = str;
        }

        public void setErrorCount(Integer num) {
            this.errorCount = num;
        }

        public void setFileId(String str) {
            this.fileId = str;
        }

        public void setFileUrl(String str) {
            this.fileUrl = str;
        }

        public void setFirstImage(String str) {
            this.firstImage = str;
        }

        public void setHeight(Integer num) {
            this.height = num;
        }

        public void setLang(String str) {
            this.lang = str;
        }

        public void setLastModified(Long l2) {
            this.lastModified = l2;
        }

        public void setListType(String str) {
            this.listType = str;
        }

        public void setLiveType(String str) {
            this.liveType = str;
        }

        public void setMyBr(String str) {
            this.myBr = str;
        }

        public void setOfflineJSUrl(String str) {
            this.offlineJSUrl = str;
        }

        public void setOrdertime(Integer num) {
            this.ordertime = num;
        }

        public void setOrigin(String str) {
            this.origin = str;
        }

        public void setOriginSessionId(String str) {
            this.originSessionId = str;
        }

        public void setPageMode(Integer num) {
            this.pageMode = num;
        }

        public void setPlaybackCacheEnabled(String str) {
            this.playbackCacheEnabled = str;
        }

        public void setRank(Integer num) {
            this.rank = num;
        }

        public void setSeed(Integer num) {
            this.seed = num;
        }

        public void setStartTime(String str) {
            this.startTime = str;
        }

        public void setStatus(String str) {
            this.status = str;
        }

        public void setTitle(String str) {
            this.title = str;
        }

        public void setUrl(String str) {
            this.url = str;
        }

        public void setUserId(String str) {
            this.userId = str;
        }

        public void setVideoCache(VideoCache videoCache) {
            this.videoCache = videoCache;
        }

        public void setVideoId(String str) {
            this.videoId = str;
        }

        public void setVideoPoolId(String str) {
            this.videoPoolId = str;
        }

        public void setWidth(Integer num) {
            this.width = num;
        }

        public String toString() {
            return "Data{offlineJSUrl='" + this.offlineJSUrl + CharPool.SINGLE_QUOTE + ", seed=" + this.seed + ", liveType='" + this.liveType + CharPool.SINGLE_QUOTE + ", origin='" + this.origin + CharPool.SINGLE_QUOTE + ", videoId='" + this.videoId + CharPool.SINGLE_QUOTE + ", title='" + this.title + CharPool.SINGLE_QUOTE + ", listType='" + this.listType + CharPool.SINGLE_QUOTE + ", asDefault='" + this.asDefault + CharPool.SINGLE_QUOTE + ", duration='" + this.duration + CharPool.SINGLE_QUOTE + ", channelSessionId='" + this.channelSessionId + CharPool.SINGLE_QUOTE + ", createdTime=" + this.createdTime + ", rank=" + this.rank + ", fileUrl='" + this.fileUrl + CharPool.SINGLE_QUOTE + ", startTime='" + this.startTime + CharPool.SINGLE_QUOTE + ", lang='" + this.lang + CharPool.SINGLE_QUOTE + ", classX='" + this.classX + CharPool.SINGLE_QUOTE + ", channelId=" + this.channelId + ", errorCount=" + this.errorCount + ", height=" + this.height + ", playbackCacheEnabled='" + this.playbackCacheEnabled + CharPool.SINGLE_QUOTE + ", pageMode=" + this.pageMode + ", ordertime=" + this.ordertime + ", originSessionId='" + this.originSessionId + CharPool.SINGLE_QUOTE + ", userId='" + this.userId + CharPool.SINGLE_QUOTE + ", url='" + this.url + CharPool.SINGLE_QUOTE + ", myBr='" + this.myBr + CharPool.SINGLE_QUOTE + ", videoPoolId='" + this.videoPoolId + CharPool.SINGLE_QUOTE + ", width=" + this.width + ", videoCache=" + this.videoCache + ", firstImage='" + this.firstImage + CharPool.SINGLE_QUOTE + ", lastModified=" + this.lastModified + ", fileId='" + this.fileId + CharPool.SINGLE_QUOTE + ", status='" + this.status + CharPool.SINGLE_QUOTE + '}';
        }
    }

    public Integer getCode() {
        return this.code;
    }

    public Data getData() {
        return (Data) this.data;
    }

    public Object getDataObj() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public String getStatus() {
        return this.status;
    }

    public boolean isEncryption() {
        return this.encryption;
    }

    public void setCode(Integer num) {
        this.code = num;
    }

    public void setData(Object obj) {
        this.data = obj;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public void setStatus(String str) {
        this.status = str;
    }

    public String toString() {
        return "PLVPlaybackVideoVO{code=" + this.code + ", status='" + this.status + CharPool.SINGLE_QUOTE + ", message='" + this.message + CharPool.SINGLE_QUOTE + ", data=" + this.data + '}';
    }
}
