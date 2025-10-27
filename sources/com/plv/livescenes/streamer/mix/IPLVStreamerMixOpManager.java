package com.plv.livescenes.streamer.mix;

import com.plv.linkmic.repository.PLVLinkMicHttpRequestException;
import java.util.List;

/* loaded from: classes5.dex */
public interface IPLVStreamerMixOpManager {

    public static class EncodeParam {
        public int audioBitrate;
        public int audioChannels;
        public int audioCodec;
        public int audioSampleRate;
        public int backgroundColor;
        public int videoBitrate;
        public int videoFramerate;
        public int videoGop;
        public int videoHeight;
        public int videoWidth;

        public String toString() {
            return "EncodeParam{audioSampleRate=" + this.audioSampleRate + ", audioBitrate=" + this.audioBitrate + ", audioChannels=" + this.audioChannels + ", videoWidth=" + this.videoWidth + ", videoHeight=" + this.videoHeight + ", videoBitrate=" + this.videoBitrate + ", videoFramerate=" + this.videoFramerate + ", videoGop=" + this.videoGop + ", backgroundColor=" + this.backgroundColor + ", audioCodec=" + this.audioCodec + '}';
        }
    }

    public static class MixUser {
        public boolean hidden = false;
        public int mixInputType;
        public int renderMode;
        public int streamType;
        public String userId;
    }

    public interface OnMixActionListener {
        void onFail(PLVLinkMicHttpRequestException pLVLinkMicHttpRequestException);

        void onSuccess();
    }

    void destroy();

    void init(String str, String str2, int i2, String str3, boolean z2, EncodeParam encodeParam, String str4);

    void setLiveTranscodingEnable(boolean z2);

    void setMixLayoutType(int i2);

    void startMix(MixUser mixUser, OnMixActionListener onMixActionListener);

    void stopMix(OnMixActionListener onMixActionListener);

    void updateMixUser(List<MixUser> list);

    void updateSEIFrameTimeStamp(String str);

    void updateVideoEncodeParam(int i2, int i3, int i4);
}
