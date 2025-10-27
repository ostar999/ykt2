package com.plv.livescenes.model;

import com.plv.business.model.PLVBaseVO;

/* loaded from: classes5.dex */
public class PLVTempStorePlaybackVideoVO implements PLVBaseVO {
    private Integer code;
    private Object data;
    private boolean encryption;
    private String message;
    private String status;

    public static class Data {
        private Integer bitrate;
        private String channelId;
        private String channelSessionId;
        private Long createdTime;
        private Integer daysLeft;
        private String duration;
        private String endTime;
        private String fileId;
        private String filename;
        private Long filesize;
        private Integer height;
        private String liveType;
        private String m3u8;
        private String mp4;
        private String origin;
        private String originSessionId;
        private String playbackCacheEnabled;
        private String startTime;
        private String userId;
        private VideoCache videoCache;
        private Integer width;

        public static class VideoCache {
            private String liveType;
            private String offlineJSUrl;
            private String status;
            private Long videoSize;
            private String videoUrl;
            private Long zipSize;
            private String zipUrl;

            public String getLiveType() {
                return this.liveType;
            }

            public String getOfflineJSUrl() {
                return this.offlineJSUrl;
            }

            public String getStatus() {
                return this.status;
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

            public void setLiveType(String str) {
                this.liveType = str;
            }

            public void setOfflineJSUrl(String str) {
                this.offlineJSUrl = str;
            }

            public void setStatus(String str) {
                this.status = str;
            }

            public void setVideoSize(Long l2) {
                this.videoSize = l2;
            }

            public void setVideoUrl(String str) {
                this.videoUrl = str;
            }

            public void setZipSize(Long l2) {
                this.zipSize = l2;
            }

            public void setZipUrl(String str) {
                this.zipUrl = str;
            }
        }

        public Integer getBitrate() {
            return this.bitrate;
        }

        public String getChannelId() {
            return this.channelId;
        }

        public String getChannelSessionId() {
            return this.channelSessionId;
        }

        public Long getCreatedTime() {
            return this.createdTime;
        }

        public Integer getDaysLeft() {
            return this.daysLeft;
        }

        public String getDuration() {
            return this.duration;
        }

        public String getEndTime() {
            return this.endTime;
        }

        public String getFileId() {
            return this.fileId;
        }

        public String getFilename() {
            return this.filename;
        }

        public Long getFilesize() {
            return this.filesize;
        }

        public Integer getHeight() {
            return this.height;
        }

        public String getLiveType() {
            return this.liveType;
        }

        public String getM3u8() {
            return this.m3u8;
        }

        public String getMp4() {
            return this.mp4;
        }

        public String getOrigin() {
            return this.origin;
        }

        public String getOriginSessionId() {
            return this.originSessionId;
        }

        public String getPlaybackCacheEnabled() {
            return this.playbackCacheEnabled;
        }

        public String getStartTime() {
            return this.startTime;
        }

        public String getUserId() {
            return this.userId;
        }

        public VideoCache getVideoCache() {
            return this.videoCache;
        }

        public Integer getWidth() {
            return this.width;
        }

        public boolean isPlaybackCacheEnable() {
            VideoCache videoCache;
            return "Y".equals(getPlaybackCacheEnabled()) && (videoCache = this.videoCache) != null && "Y".equals(videoCache.getStatus());
        }

        public void setBitrate(Integer num) {
            this.bitrate = num;
        }

        public void setChannelId(String str) {
            this.channelId = str;
        }

        public void setChannelSessionId(String str) {
            this.channelSessionId = str;
        }

        public void setCreatedTime(Long l2) {
            this.createdTime = l2;
        }

        public void setDaysLeft(Integer num) {
            this.daysLeft = num;
        }

        public void setDuration(String str) {
            this.duration = str;
        }

        public void setEndTime(String str) {
            this.endTime = str;
        }

        public void setFileId(String str) {
            this.fileId = str;
        }

        public void setFilename(String str) {
            this.filename = str;
        }

        public void setFilesize(Long l2) {
            this.filesize = l2;
        }

        public void setHeight(Integer num) {
            this.height = num;
        }

        public void setLiveType(String str) {
            this.liveType = str;
        }

        public void setM3u8(String str) {
            this.m3u8 = str;
        }

        public void setMp4(String str) {
            this.mp4 = str;
        }

        public void setOrigin(String str) {
            this.origin = str;
        }

        public void setOriginSessionId(String str) {
            this.originSessionId = str;
        }

        public void setPlaybackCacheEnabled(String str) {
            this.playbackCacheEnabled = str;
        }

        public void setStartTime(String str) {
            this.startTime = str;
        }

        public void setUserId(String str) {
            this.userId = str;
        }

        public void setVideoCache(VideoCache videoCache) {
            this.videoCache = videoCache;
        }

        public void setWidth(Integer num) {
            this.width = num;
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
}
