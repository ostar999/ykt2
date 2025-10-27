package com.plv.livescenes.model;

import cn.hutool.core.text.CharPool;

/* loaded from: classes5.dex */
public class PLVPlaybackVO2 {
    private Integer code;
    private Object data;
    private boolean encryption;
    private String message;
    private String status;

    public static class Data {
        private String chatPlaybackEnabled;
        private String enablePlayBack;
        private String hasPlaybackVideo;
        private String hasRecordFile;
        private String playbackOrigin;
        private String playbackType;
        private RecordFile recordFile;
        private String sectionEnabled;
        private TargetPlaybackVideo targetPlaybackVideo;
        private String targetSessionId;
        private String type;
        private String vodSessionId;
        private String vodUrl;
        private String vodUserStatus;

        public static class RecordFile {
            private String channelSessionId;
            private String duration;
            private String fileId;
            private String m3u8;
            private String mp4;
            private String origin;
            private String originSessionId;
            private Integer pageMode;

            public String getChannelSessionId() {
                return this.channelSessionId;
            }

            public String getDuration() {
                return this.duration;
            }

            public String getFileId() {
                return this.fileId;
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

            public Integer getPageMode() {
                return this.pageMode;
            }

            public void setChannelSessionId(String str) {
                this.channelSessionId = str;
            }

            public void setDuration(String str) {
                this.duration = str;
            }

            public void setFileId(String str) {
                this.fileId = str;
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

            public void setPageMode(Integer num) {
                this.pageMode = num;
            }

            public String toString() {
                return "RecordFile{fileId='" + this.fileId + CharPool.SINGLE_QUOTE + ", m3u8='" + this.m3u8 + CharPool.SINGLE_QUOTE + ", mp4='" + this.mp4 + CharPool.SINGLE_QUOTE + ", channelSessionId='" + this.channelSessionId + CharPool.SINGLE_QUOTE + ", duration='" + this.duration + CharPool.SINGLE_QUOTE + ", originSessionId='" + this.originSessionId + CharPool.SINGLE_QUOTE + ", pageMode=" + this.pageMode + ", origin='" + this.origin + CharPool.SINGLE_QUOTE + '}';
            }
        }

        public static class TargetPlaybackVideo {
            private String channelSessionId;
            private String duration;
            private String fileId;
            private String fileUrl;
            private String origin;
            private String originSessionId;
            private Integer pageMode;
            private Integer seed;
            private String userId;
            private String videoId;
            private String videoPoolId;

            public String getChannelSessionId() {
                return this.channelSessionId;
            }

            public String getDuration() {
                return this.duration;
            }

            public String getFileId() {
                return this.fileId;
            }

            public String getFileUrl() {
                return this.fileUrl;
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

            public Integer getSeed() {
                return this.seed;
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

            public void setChannelSessionId(String str) {
                this.channelSessionId = str;
            }

            public void setDuration(String str) {
                this.duration = str;
            }

            public void setFileId(String str) {
                this.fileId = str;
            }

            public void setFileUrl(String str) {
                this.fileUrl = str;
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

            public void setSeed(Integer num) {
                this.seed = num;
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

            public String toString() {
                return "TargetPlaybackVideo{videoId='" + this.videoId + CharPool.SINGLE_QUOTE + ", videoPoolId='" + this.videoPoolId + CharPool.SINGLE_QUOTE + ", fileUrl='" + this.fileUrl + CharPool.SINGLE_QUOTE + ", userId='" + this.userId + CharPool.SINGLE_QUOTE + ", channelSessionId='" + this.channelSessionId + CharPool.SINGLE_QUOTE + ", duration='" + this.duration + CharPool.SINGLE_QUOTE + ", originSessionId='" + this.originSessionId + CharPool.SINGLE_QUOTE + ", seed=" + this.seed + ", pageMode=" + this.pageMode + ", fileId='" + this.fileId + CharPool.SINGLE_QUOTE + ", origin='" + this.origin + CharPool.SINGLE_QUOTE + '}';
            }
        }

        public String getChatPlaybackEnabled() {
            return this.chatPlaybackEnabled;
        }

        public String getEnablePlayBack() {
            return this.enablePlayBack;
        }

        public String getHasPlaybackVideo() {
            return this.hasPlaybackVideo;
        }

        public String getHasRecordFile() {
            return this.hasRecordFile;
        }

        public String getPlaybackOrigin() {
            return this.playbackOrigin;
        }

        public String getPlaybackType() {
            return this.playbackType;
        }

        public RecordFile getRecordFile() {
            return this.recordFile;
        }

        public String getSectionEnabled() {
            return this.sectionEnabled;
        }

        public TargetPlaybackVideo getTargetPlaybackVideo() {
            return this.targetPlaybackVideo;
        }

        public String getTargetSessionId() {
            return this.targetSessionId;
        }

        public String getType() {
            return this.type;
        }

        public String getVodSessionId() {
            return this.vodSessionId;
        }

        public String getVodUrl() {
            return this.vodUrl;
        }

        public String getVodUserStatus() {
            return this.vodUserStatus;
        }

        public void setChatPlaybackEnabled(String str) {
            this.chatPlaybackEnabled = str;
        }

        public void setEnablePlayBack(String str) {
            this.enablePlayBack = str;
        }

        public void setHasPlaybackVideo(String str) {
            this.hasPlaybackVideo = str;
        }

        public void setHasRecordFile(String str) {
            this.hasRecordFile = str;
        }

        public void setPlaybackOrigin(String str) {
            this.playbackOrigin = str;
        }

        public void setPlaybackType(String str) {
            this.playbackType = str;
        }

        public void setRecordFile(RecordFile recordFile) {
            this.recordFile = recordFile;
        }

        public void setSectionEnabled(String str) {
            this.sectionEnabled = str;
        }

        public void setTargetPlaybackVideo(TargetPlaybackVideo targetPlaybackVideo) {
            this.targetPlaybackVideo = targetPlaybackVideo;
        }

        public void setTargetSessionId(String str) {
            this.targetSessionId = str;
        }

        public void setType(String str) {
            this.type = str;
        }

        public void setVodSessionId(String str) {
            this.vodSessionId = str;
        }

        public void setVodUrl(String str) {
            this.vodUrl = str;
        }

        public void setVodUserStatus(String str) {
            this.vodUserStatus = str;
        }

        public String toString() {
            return "Data{hasPlaybackVideo='" + this.hasPlaybackVideo + CharPool.SINGLE_QUOTE + ", hasRecordFile='" + this.hasRecordFile + CharPool.SINGLE_QUOTE + ", enablePlayBack='" + this.enablePlayBack + CharPool.SINGLE_QUOTE + ", playbackOrigin='" + this.playbackOrigin + CharPool.SINGLE_QUOTE + ", vodUserStatus='" + this.vodUserStatus + CharPool.SINGLE_QUOTE + ", sectionEnabled='" + this.sectionEnabled + CharPool.SINGLE_QUOTE + ", recordFile=" + this.recordFile + ", targetPlaybackVideo=" + this.targetPlaybackVideo + ", vodUrl='" + this.vodUrl + CharPool.SINGLE_QUOTE + ", vodSessionId='" + this.vodSessionId + CharPool.SINGLE_QUOTE + ", targetSessionId='" + this.targetSessionId + CharPool.SINGLE_QUOTE + ", type='" + this.type + CharPool.SINGLE_QUOTE + ", playbackType='" + this.playbackType + CharPool.SINGLE_QUOTE + ", chatPlaybackEnabled='" + this.chatPlaybackEnabled + CharPool.SINGLE_QUOTE + '}';
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
        return "PLVPlaybackVO2{code=" + this.code + ", status='" + this.status + CharPool.SINGLE_QUOTE + ", message='" + this.message + CharPool.SINGLE_QUOTE + ", data=" + this.data + '}';
    }
}
