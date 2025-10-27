package com.plv.rtc.b;

import com.plv.rtc.transcode.IPLVARTCTranscoding;
import com.plv.rtc.transcode.PLVARTCImage;
import com.plv.rtc.transcode.PLVARTCTranscodingUser;
import io.agora.rtc.live.LiveTranscoding;
import io.agora.rtc.video.AgoraImage;

/* loaded from: classes5.dex */
public class b implements IPLVARTCTranscoding {

    /* renamed from: a, reason: collision with root package name */
    private LiveTranscoding f10893a = new LiveTranscoding();

    public LiveTranscoding a() {
        return this.f10893a;
    }

    @Override // com.plv.rtc.transcode.IPLVARTCTranscoding
    public int addUser(PLVARTCTranscodingUser pLVARTCTranscodingUser) {
        LiveTranscoding.TranscodingUser transcodingUser = new LiveTranscoding.TranscodingUser();
        transcodingUser.alpha = pLVARTCTranscodingUser.alpha;
        transcodingUser.audioChannel = pLVARTCTranscodingUser.audioChannel;
        transcodingUser.height = pLVARTCTranscodingUser.height;
        transcodingUser.width = pLVARTCTranscodingUser.width;
        transcodingUser.uid = pLVARTCTranscodingUser.uid;
        transcodingUser.f27142x = pLVARTCTranscodingUser.f10897x;
        transcodingUser.f27143y = pLVARTCTranscodingUser.f10898y;
        transcodingUser.zOrder = pLVARTCTranscodingUser.zOrder;
        return this.f10893a.addUser(transcodingUser);
    }

    @Override // com.plv.rtc.transcode.IPLVARTCTranscoding
    public int getHeight() {
        return this.f10893a.height;
    }

    @Override // com.plv.rtc.transcode.IPLVARTCTranscoding
    public int getWidth() {
        return this.f10893a.width;
    }

    @Override // com.plv.rtc.transcode.IPLVARTCTranscoding
    public void removeUser(int i2) {
        this.f10893a.removeUser(i2);
    }

    @Override // com.plv.rtc.transcode.IPLVARTCTranscoding
    public void setAudioBitrate(int i2) {
        this.f10893a.audioBitrate = i2;
    }

    @Override // com.plv.rtc.transcode.IPLVARTCTranscoding
    public void setAudioChannels(int i2) {
        this.f10893a.audioChannels = i2;
    }

    @Override // com.plv.rtc.transcode.IPLVARTCTranscoding
    public void setAudioSampleRate(int i2) {
        if (i2 == 32000) {
            this.f10893a.audioSampleRate = LiveTranscoding.AudioSampleRateType.TYPE_32000;
        } else if (i2 == 44100) {
            this.f10893a.audioSampleRate = LiveTranscoding.AudioSampleRateType.TYPE_44100;
        } else {
            if (i2 != 48000) {
                return;
            }
            this.f10893a.audioSampleRate = LiveTranscoding.AudioSampleRateType.TYPE_48000;
        }
    }

    @Override // com.plv.rtc.transcode.IPLVARTCTranscoding
    public void setBackgroundImage(PLVARTCImage pLVARTCImage) {
        AgoraImage agoraImage = new AgoraImage();
        agoraImage.height = pLVARTCImage.height;
        agoraImage.width = pLVARTCImage.width;
        agoraImage.url = pLVARTCImage.url;
        agoraImage.f27145x = pLVARTCImage.f10895x;
        agoraImage.f27146y = pLVARTCImage.f10896y;
        this.f10893a.backgroundImage = agoraImage;
    }

    @Override // com.plv.rtc.transcode.IPLVARTCTranscoding
    public void setHeight(int i2) {
        this.f10893a.height = i2;
    }

    @Override // com.plv.rtc.transcode.IPLVARTCTranscoding
    public void setLowLatency(boolean z2) {
        this.f10893a.lowLatency = z2;
    }

    @Override // com.plv.rtc.transcode.IPLVARTCTranscoding
    public void setUserConfigExtraInfo(String str) {
        this.f10893a.userConfigExtraInfo = str;
    }

    @Override // com.plv.rtc.transcode.IPLVARTCTranscoding
    public void setVideoBitrate(int i2) {
        this.f10893a.videoBitrate = i2;
    }

    @Override // com.plv.rtc.transcode.IPLVARTCTranscoding
    public void setVideoCodecProfile(int i2) {
        if (i2 == 66) {
            this.f10893a.videoCodecProfile = LiveTranscoding.VideoCodecProfileType.BASELINE;
        } else if (i2 == 77) {
            this.f10893a.videoCodecProfile = LiveTranscoding.VideoCodecProfileType.MAIN;
        } else {
            if (i2 != 100) {
                return;
            }
            this.f10893a.videoCodecProfile = LiveTranscoding.VideoCodecProfileType.HIGH;
        }
    }

    @Override // com.plv.rtc.transcode.IPLVARTCTranscoding
    public void setVideoFramerate(int i2) {
        this.f10893a.videoFramerate = i2;
    }

    @Override // com.plv.rtc.transcode.IPLVARTCTranscoding
    public void setVideoGop(int i2) {
        this.f10893a.videoGop = i2;
    }

    @Override // com.plv.rtc.transcode.IPLVARTCTranscoding
    public void setWaterMark(PLVARTCImage pLVARTCImage) {
        AgoraImage agoraImage = new AgoraImage();
        agoraImage.height = pLVARTCImage.height;
        agoraImage.width = pLVARTCImage.width;
        agoraImage.url = pLVARTCImage.url;
        agoraImage.f27145x = pLVARTCImage.f10895x;
        agoraImage.f27146y = pLVARTCImage.f10896y;
        this.f10893a.watermark = agoraImage;
    }

    @Override // com.plv.rtc.transcode.IPLVARTCTranscoding
    public void setWidth(int i2) {
        this.f10893a.width = i2;
    }
}
