package com.plv.livescenes.model;

import cn.hutool.core.text.CharPool;
import com.plv.business.model.PLVBaseVO;

/* loaded from: classes5.dex */
public class PLVPlaybackVO implements PLVBaseVO {
    private int code;
    private Object data;
    private boolean encryption;
    private String message;
    private String status;

    public static class DataBean {
        private String bitrate;
        private int channelId;
        private String channelSessionId;
        private long createdTime;
        private String duration;
        private String fileId;
        private String fileUrl;
        private String firstImage;
        private long lastModified;
        private String liveType;
        private String mergeInfo;
        private String myBr;
        private String originSessionId;
        private String qid;
        private int rank;
        private int seed;
        private String startTime;
        private String status;
        private String title;
        private String url;
        private String userId;
        private String videoId;
        private String videoPoolId;

        public String getBitrate() {
            return this.bitrate;
        }

        public int getChannelId() {
            return this.channelId;
        }

        public String getChannelSessionId() {
            return this.channelSessionId;
        }

        public long getCreatedTime() {
            return this.createdTime;
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

        public String getFirstImage() {
            return this.firstImage;
        }

        public long getLastModified() {
            return this.lastModified;
        }

        public String getLiveType() {
            return this.liveType;
        }

        public String getMergeInfo() {
            return this.mergeInfo;
        }

        public String getMyBr() {
            return this.myBr;
        }

        public String getOriginSessionId() {
            return this.originSessionId;
        }

        public String getQid() {
            return this.qid;
        }

        public int getRank() {
            return this.rank;
        }

        public int getSeed() {
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

        public String getVideoId() {
            return this.videoId;
        }

        public String getVideoPoolId() {
            return this.videoPoolId;
        }

        public void setBitrate(String str) {
            this.bitrate = str;
        }

        public void setChannelId(int i2) {
            this.channelId = i2;
        }

        public void setChannelSessionId(String str) {
            this.channelSessionId = str;
        }

        public void setCreatedTime(long j2) {
            this.createdTime = j2;
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

        public void setFirstImage(String str) {
            this.firstImage = str;
        }

        public void setLastModified(long j2) {
            this.lastModified = j2;
        }

        public void setLiveType(String str) {
            this.liveType = str;
        }

        public void setMergeInfo(String str) {
            this.mergeInfo = str;
        }

        public void setMyBr(String str) {
            this.myBr = str;
        }

        public void setOriginSessionId(String str) {
            this.originSessionId = str;
        }

        public void setQid(String str) {
            this.qid = str;
        }

        public void setRank(int i2) {
            this.rank = i2;
        }

        public void setSeed(int i2) {
            this.seed = i2;
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

        public void setVideoId(String str) {
            this.videoId = str;
        }

        public void setVideoPoolId(String str) {
            this.videoPoolId = str;
        }

        public String toString() {
            return "DataBean{videoId='" + this.videoId + CharPool.SINGLE_QUOTE + ", videoPoolId='" + this.videoPoolId + CharPool.SINGLE_QUOTE + ", userId='" + this.userId + CharPool.SINGLE_QUOTE + ", channelId=" + this.channelId + ", title='" + this.title + CharPool.SINGLE_QUOTE + ", firstImage='" + this.firstImage + CharPool.SINGLE_QUOTE + ", duration='" + this.duration + CharPool.SINGLE_QUOTE + ", myBr='" + this.myBr + CharPool.SINGLE_QUOTE + ", qid='" + this.qid + CharPool.SINGLE_QUOTE + ", seed=" + this.seed + ", bitrate='" + this.bitrate + CharPool.SINGLE_QUOTE + ", createdTime=" + this.createdTime + ", lastModified=" + this.lastModified + ", rank=" + this.rank + ", url='" + this.url + CharPool.SINGLE_QUOTE + ", channelSessionId='" + this.channelSessionId + CharPool.SINGLE_QUOTE + ", mergeInfo='" + this.mergeInfo + CharPool.SINGLE_QUOTE + ", status='" + this.status + CharPool.SINGLE_QUOTE + ", fileUrl='" + this.fileUrl + CharPool.SINGLE_QUOTE + ", fileId='" + this.fileId + CharPool.SINGLE_QUOTE + ", startTime='" + this.startTime + CharPool.SINGLE_QUOTE + ", liveType='" + this.liveType + CharPool.SINGLE_QUOTE + ", originSessionId='" + this.originSessionId + CharPool.SINGLE_QUOTE + '}';
        }
    }

    public int getCode() {
        return this.code;
    }

    public DataBean getData() {
        return (DataBean) this.data;
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

    public void setCode(int i2) {
        this.code = i2;
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
        return "PLVPlaybackVO{code=" + this.code + ", status='" + this.status + CharPool.SINGLE_QUOTE + ", message='" + this.message + CharPool.SINGLE_QUOTE + ", data=" + this.data + '}';
    }
}
