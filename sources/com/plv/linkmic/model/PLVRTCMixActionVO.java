package com.plv.linkmic.model;

import java.util.List;

/* loaded from: classes4.dex */
public class PLVRTCMixActionVO {
    private String action;
    private EncodeParamVO encodeParam;
    private int mixLayoutType;
    private OutputParamVO outputParam;
    private PublishCdnParamVO publishCdnParam;
    private int roomId;
    private List<UserListVO> userList;

    public static class EncodeParamVO {
        private int audioBitrate;
        private int audioChannels;
        private int audioCodec;
        private int audioSampleRate;
        private int backgroundColor;
        private int videoBitrate;
        private int videoFramerate;
        private int videoGop;
        private int videoHeight;
        private int videoWidth;

        public int getAudioBitrate() {
            return this.audioBitrate;
        }

        public int getAudioChannels() {
            return this.audioChannels;
        }

        public int getAudioCodec() {
            return this.audioCodec;
        }

        public int getAudioSampleRate() {
            return this.audioSampleRate;
        }

        public int getBackgroundColor() {
            return this.backgroundColor;
        }

        public int getVideoBitrate() {
            return this.videoBitrate;
        }

        public int getVideoFramerate() {
            return this.videoFramerate;
        }

        public int getVideoGop() {
            return this.videoGop;
        }

        public int getVideoHeight() {
            return this.videoHeight;
        }

        public int getVideoWidth() {
            return this.videoWidth;
        }

        public void setAudioBitrate(int i2) {
            this.audioBitrate = i2;
        }

        public void setAudioChannels(int i2) {
            this.audioChannels = i2;
        }

        public void setAudioCodec(int i2) {
            this.audioCodec = i2;
        }

        public void setAudioSampleRate(int i2) {
            this.audioSampleRate = i2;
        }

        public void setBackgroundColor(int i2) {
            this.backgroundColor = i2;
        }

        public void setVideoBitrate(int i2) {
            this.videoBitrate = i2;
        }

        public void setVideoFramerate(int i2) {
            this.videoFramerate = i2;
        }

        public void setVideoGop(int i2) {
            this.videoGop = i2;
        }

        public void setVideoHeight(int i2) {
            this.videoHeight = i2;
        }

        public void setVideoWidth(int i2) {
            this.videoWidth = i2;
        }
    }

    public static class OutputParamVO {
        private int pureAudioStream;
        private String streamId;

        public int getPureAudioStream() {
            return this.pureAudioStream;
        }

        public String getStreamId() {
            return this.streamId;
        }

        public void setPureAudioStream(int i2) {
            this.pureAudioStream = i2;
        }

        public void setStreamId(String str) {
            this.streamId = str;
        }
    }

    public static class PublishCdnParamVO {
        private String cdnUrl;

        public String getCdnUrl() {
            return this.cdnUrl;
        }

        public void setCdnUrl(String str) {
            this.cdnUrl = str;
        }
    }

    public static class UserListVO {
        private boolean hidden = false;
        private int mixInputType;
        private int renderMode;
        private int streamType;
        private String userId;

        public int getMixInputType() {
            return this.mixInputType;
        }

        public int getRenderMode() {
            return this.renderMode;
        }

        public int getStreamType() {
            return this.streamType;
        }

        public String getUserId() {
            return this.userId;
        }

        public boolean isHidden() {
            return this.hidden;
        }

        public void setHidden(boolean z2) {
            this.hidden = z2;
        }

        public void setMixInputType(int i2) {
            this.mixInputType = i2;
        }

        public void setRenderMode(int i2) {
            this.renderMode = i2;
        }

        public void setStreamType(int i2) {
            this.streamType = i2;
        }

        public void setUserId(String str) {
            this.userId = str;
        }
    }

    public String getAction() {
        return this.action;
    }

    public EncodeParamVO getEncodeParam() {
        return this.encodeParam;
    }

    public int getMixLayoutType() {
        return this.mixLayoutType;
    }

    public OutputParamVO getOutputParam() {
        return this.outputParam;
    }

    public PublishCdnParamVO getPublishCdnParam() {
        return this.publishCdnParam;
    }

    public int getRoomId() {
        return this.roomId;
    }

    public List<UserListVO> getUserList() {
        return this.userList;
    }

    public void setAction(String str) {
        this.action = str;
    }

    public void setEncodeParam(EncodeParamVO encodeParamVO) {
        this.encodeParam = encodeParamVO;
    }

    public void setMixLayoutType(int i2) {
        this.mixLayoutType = i2;
    }

    public void setOutputParam(OutputParamVO outputParamVO) {
        this.outputParam = outputParamVO;
    }

    public void setPublishCdnParam(PublishCdnParamVO publishCdnParamVO) {
        this.publishCdnParam = publishCdnParamVO;
    }

    public void setRoomId(int i2) {
        this.roomId = i2;
    }

    public void setUserList(List<UserListVO> list) {
        this.userList = list;
    }
}
