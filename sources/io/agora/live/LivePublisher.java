package io.agora.live;

import android.text.TextUtils;
import io.agora.rtc.RtcEngine;
import io.agora.rtc.live.LiveInjectStreamConfig;
import io.agora.rtc.live.LiveTranscoding;
import io.agora.rtc.video.AgoraImage;
import java.lang.ref.WeakReference;

/* loaded from: classes8.dex */
public class LivePublisher {
    private final LivePublisherHandler mHandler;
    private WeakReference<LiveEngine> mLiveEngine;
    private boolean mPublishAudio;
    private boolean mPublishVideo;
    private boolean mPublishing;

    public LivePublisher(LiveEngine engine, LivePublisherHandler handler) {
        WeakReference<LiveEngine> weakReference = new WeakReference<>(engine);
        this.mLiveEngine = weakReference;
        this.mHandler = handler;
        LiveEngine liveEngine = weakReference.get();
        if (liveEngine != null) {
            liveEngine.getRtcEngine().enableDualStreamMode(true);
            liveEngine.setPublisher(this);
        }
        setMediaType(3);
    }

    private void applyPublishingMediaType() {
        LiveEngine liveEngine = this.mLiveEngine.get();
        if (liveEngine == null) {
            return;
        }
        RtcEngine rtcEngine = liveEngine.getRtcEngine();
        if (this.mPublishing) {
            rtcEngine.muteLocalAudioStream(!this.mPublishAudio);
            if (liveEngine.getLiveChannelConfig().videoEnabled) {
                rtcEngine.muteLocalVideoStream(!this.mPublishVideo);
            }
        }
    }

    public int addInjectStreamUrl(String url, LiveInjectStreamConfig config) {
        LiveEngine liveEngine = this.mLiveEngine.get();
        if (liveEngine == null) {
            return 3;
        }
        return liveEngine.getRtcEngine().addInjectStreamUrl(url, config);
    }

    public int addStreamUrl(String url, boolean isTranscodingEnabled) {
        if (TextUtils.isEmpty(url)) {
            return 2;
        }
        LiveEngine liveEngine = this.mLiveEngine.get();
        if (liveEngine == null) {
            return 3;
        }
        return liveEngine.getRtcEngine().addPublishStreamUrl(url, isTranscodingEnabled);
    }

    public void addVideoWatermark(AgoraImage watermark) {
        LiveEngine liveEngine = this.mLiveEngine.get();
        if (liveEngine == null) {
            return;
        }
        liveEngine.getRtcEngine().addVideoWatermark(watermark);
    }

    public void clearVideoWatermarks() {
        LiveEngine liveEngine = this.mLiveEngine.get();
        if (liveEngine == null) {
            return;
        }
        liveEngine.getRtcEngine().clearVideoWatermarks();
    }

    public LivePublisherHandler getLivePublisherHandler() {
        return this.mHandler;
    }

    public void publish() {
        LiveEngine liveEngine = this.mLiveEngine.get();
        if (liveEngine == null) {
            return;
        }
        RtcEngine rtcEngine = liveEngine.getRtcEngine();
        this.mPublishing = true;
        if (liveEngine.getLiveChannelConfig().videoEnabled) {
            rtcEngine.enableLocalVideo(true);
        }
        rtcEngine.setClientRole(1);
        applyPublishingMediaType();
    }

    public int removeInjectStreamUrl(String url) {
        LiveEngine liveEngine = this.mLiveEngine.get();
        if (liveEngine == null) {
            return 3;
        }
        return liveEngine.getRtcEngine().removeInjectStreamUrl(url);
    }

    public int removeStreamUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return 2;
        }
        LiveEngine liveEngine = this.mLiveEngine.get();
        if (liveEngine == null) {
            return 3;
        }
        return liveEngine.getRtcEngine().removePublishStreamUrl(url);
    }

    public int setLiveTranscoding(LiveTranscoding transcoding) {
        LiveEngine liveEngine = this.mLiveEngine.get();
        if (liveEngine == null) {
            return -3;
        }
        return liveEngine.getRtcEngine().setLiveTranscoding(transcoding);
    }

    public void setMediaType(int mediaType) {
        if (mediaType == 0) {
            this.mPublishAudio = false;
            this.mPublishVideo = false;
        } else if (mediaType == 1) {
            this.mPublishAudio = true;
            this.mPublishVideo = false;
        } else if (mediaType == 2) {
            this.mPublishAudio = false;
            this.mPublishVideo = true;
        } else if (mediaType == 3) {
            this.mPublishAudio = true;
            this.mPublishVideo = true;
        }
        applyPublishingMediaType();
    }

    public int setVideoProfile(int width, int height, int frameRate, int bitrate) {
        LiveEngine liveEngine = this.mLiveEngine.get();
        if (liveEngine == null) {
            return -3;
        }
        return liveEngine.getRtcEngine().setVideoProfile(width, height, frameRate, bitrate);
    }

    public void switchCamera() {
        LiveEngine liveEngine = this.mLiveEngine.get();
        if (liveEngine == null) {
            return;
        }
        liveEngine.getRtcEngine().switchCamera();
    }

    public void unpublish() {
        LiveEngine liveEngine = this.mLiveEngine.get();
        if (liveEngine == null) {
            return;
        }
        liveEngine.getRtcEngine().setClientRole(2);
    }
}
