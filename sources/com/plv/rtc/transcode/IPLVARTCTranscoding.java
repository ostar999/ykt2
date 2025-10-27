package com.plv.rtc.transcode;

/* loaded from: classes5.dex */
public interface IPLVARTCTranscoding {
    int addUser(PLVARTCTranscodingUser pLVARTCTranscodingUser);

    int getHeight();

    int getWidth();

    void removeUser(int i2);

    void setAudioBitrate(int i2);

    void setAudioChannels(int i2);

    void setAudioSampleRate(int i2);

    void setBackgroundImage(PLVARTCImage pLVARTCImage);

    void setHeight(int i2);

    void setLowLatency(boolean z2);

    void setUserConfigExtraInfo(String str);

    void setVideoBitrate(int i2);

    void setVideoCodecProfile(int i2);

    void setVideoFramerate(int i2);

    void setVideoGop(int i2);

    void setWaterMark(PLVARTCImage pLVARTCImage);

    void setWidth(int i2);
}
