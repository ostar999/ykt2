package io.agora.live.impl;

import android.content.Context;
import android.text.TextUtils;
import android.view.SurfaceView;
import io.agora.live.LiveChannelConfig;
import io.agora.live.LiveEngine;
import io.agora.live.LiveEngineHandler;
import io.agora.live.LivePublisher;
import io.agora.live.LiveStats;
import io.agora.live.LiveSubscriber;
import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.RtcEngine;
import io.agora.rtc.internal.Logging;
import io.agora.rtc.video.VideoCanvas;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes8.dex */
public class LiveEngineImpl extends LiveEngine {
    private static final String[] ENCRYPTION_MODE = {"", "aes-128-xts", "aes-256-xts", "aes-128-ecb"};
    private static final int REMOTE_AUDIO_MUTED = 1;
    private static final int REMOTE_VIDEO_MUTED = 2;
    private static final String TAG = "LiveEngineImpl";
    private LiveChannelConfig mChannelConfig;
    private LivePublisher mPublisher;
    private RtcEngine mRtcEngine;
    private LiveSubscriber mSubscriber;

    public static final class RtcEngineHandler extends IRtcEngineEventHandler {
        private static final RtcEngineHandler gRtcHandler = new RtcEngineHandler();
        private LiveEngineImpl mLiveEngineImpl;
        private LiveEngineHandler mLiveHandler;
        private Map<Integer, Integer> remoteStreamMediaStats = new HashMap();

        private RtcEngineHandler() {
        }

        public static RtcEngineHandler get() {
            return gRtcHandler;
        }

        private int getMediaTypeByStat(int stat) {
            int[] iArr = {3, 2, 1, 0};
            if (stat < 0 || stat > 3) {
                return 0;
            }
            return iArr[stat];
        }

        @Override // io.agora.rtc.IRtcEngineEventHandler
        public void onConnectionInterrupted() {
            LiveEngineHandler liveEngineHandler = this.mLiveHandler;
            if (liveEngineHandler != null) {
                liveEngineHandler.onConnectionInterrupted();
            }
        }

        @Override // io.agora.rtc.IRtcEngineEventHandler
        public void onConnectionLost() {
            LiveEngineHandler liveEngineHandler = this.mLiveHandler;
            if (liveEngineHandler != null) {
                liveEngineHandler.onConnectionLost();
            }
        }

        @Override // io.agora.rtc.IRtcEngineEventHandler
        public void onError(int err) {
            LiveEngineHandler liveEngineHandler = this.mLiveHandler;
            if (liveEngineHandler != null) {
                liveEngineHandler.onError(err);
            }
        }

        @Override // io.agora.rtc.IRtcEngineEventHandler
        public void onFirstRemoteVideoDecoded(int uid, int width, int height, int elapsed) {
            LiveEngineImpl liveEngineImpl = this.mLiveEngineImpl;
            if (liveEngineImpl == null || liveEngineImpl.mSubscriber == null) {
                return;
            }
            this.mLiveEngineImpl.mSubscriber.getLiveSubscriberHandler().onFirstRemoteVideoDecoded(uid, width, height, elapsed);
        }

        @Override // io.agora.rtc.IRtcEngineEventHandler
        public void onJoinChannelSuccess(String channel, int uid, int elapsed) {
            LiveEngineHandler liveEngineHandler = this.mLiveHandler;
            if (liveEngineHandler != null) {
                liveEngineHandler.onJoinChannel(channel, uid, elapsed);
            }
        }

        @Override // io.agora.rtc.IRtcEngineEventHandler
        public void onLeaveChannel(IRtcEngineEventHandler.RtcStats stats) {
            LiveEngineHandler liveEngineHandler = this.mLiveHandler;
            if (liveEngineHandler != null) {
                liveEngineHandler.onLeaveChannel();
            }
        }

        @Override // io.agora.rtc.IRtcEngineEventHandler
        public void onNetworkQuality(int uid, int txQuality, int rxQuality) {
            LiveEngineHandler liveEngineHandler = this.mLiveHandler;
            if (liveEngineHandler != null) {
                liveEngineHandler.onNetworkQuality(uid, txQuality, rxQuality);
            }
        }

        @Override // io.agora.rtc.IRtcEngineEventHandler
        public void onRejoinChannelSuccess(String channel, int uid, int elapsed) {
            LiveEngineHandler liveEngineHandler = this.mLiveHandler;
            if (liveEngineHandler != null) {
                liveEngineHandler.onRejoinChannel(channel, uid, elapsed);
            }
        }

        @Override // io.agora.rtc.IRtcEngineEventHandler
        public void onRequestToken() {
            LiveEngineHandler liveEngineHandler = this.mLiveHandler;
            if (liveEngineHandler != null) {
                liveEngineHandler.onRequestToken();
            }
        }

        @Override // io.agora.rtc.IRtcEngineEventHandler
        public void onRtcStats(IRtcEngineEventHandler.RtcStats stats) {
            LiveEngineHandler liveEngineHandler = this.mLiveHandler;
            if (liveEngineHandler != null) {
                liveEngineHandler.onReportLiveStats(new LiveStats(stats));
            }
        }

        @Override // io.agora.rtc.IRtcEngineEventHandler
        public void onStreamInjectedStatus(String url, int uid, int status) {
            LiveEngineImpl liveEngineImpl = this.mLiveEngineImpl;
            if (liveEngineImpl == null || liveEngineImpl.mPublisher == null) {
                return;
            }
            this.mLiveEngineImpl.mPublisher.getLivePublisherHandler().onStreamInjectedStatus(url, uid, status);
        }

        @Override // io.agora.rtc.IRtcEngineEventHandler
        public void onStreamPublished(String url, int error) {
            LiveEngineImpl liveEngineImpl = this.mLiveEngineImpl;
            if (liveEngineImpl == null || liveEngineImpl.mPublisher == null) {
                return;
            }
            if (error == 0) {
                this.mLiveEngineImpl.mPublisher.getLivePublisherHandler().onStreamUrlPublished(url);
            } else {
                this.mLiveEngineImpl.mPublisher.getLivePublisherHandler().onPublishStreamUrlFailed(url, error);
            }
        }

        @Override // io.agora.rtc.IRtcEngineEventHandler
        public void onStreamUnpublished(String url) {
            LiveEngineImpl liveEngineImpl = this.mLiveEngineImpl;
            if (liveEngineImpl == null || liveEngineImpl.mPublisher == null) {
                return;
            }
            this.mLiveEngineImpl.mPublisher.getLivePublisherHandler().onStreamUrlUnpublished(url);
        }

        @Override // io.agora.rtc.IRtcEngineEventHandler
        public void onTokenPrivilegeWillExpire(String token) {
            LiveEngineHandler liveEngineHandler = this.mLiveHandler;
            if (liveEngineHandler != null) {
                liveEngineHandler.onTokenPrivilegeWillExpire(token);
            }
        }

        @Override // io.agora.rtc.IRtcEngineEventHandler
        public void onTranscodingUpdated() {
            LiveEngineImpl liveEngineImpl = this.mLiveEngineImpl;
            if (liveEngineImpl == null || liveEngineImpl.mPublisher == null) {
                return;
            }
            this.mLiveEngineImpl.mPublisher.getLivePublisherHandler().onPublisherTranscodingUpdated(this.mLiveEngineImpl.mPublisher);
        }

        @Override // io.agora.rtc.IRtcEngineEventHandler
        public void onUserJoined(int uid, int elapsed) {
            LiveEngineImpl liveEngineImpl = this.mLiveEngineImpl;
            if (liveEngineImpl == null || liveEngineImpl.mSubscriber == null) {
                return;
            }
            this.mLiveEngineImpl.getRtcEngine().muteRemoteAudioStream(uid, true);
            this.mLiveEngineImpl.getRtcEngine().muteRemoteVideoStream(uid, true);
            this.remoteStreamMediaStats.put(Integer.valueOf(uid), 0);
            this.mLiveEngineImpl.mSubscriber.getLiveSubscriberHandler().publishedByHost(uid, getMediaTypeByStat(0));
        }

        @Override // io.agora.rtc.IRtcEngineEventHandler
        public void onUserMuteAudio(int uid, boolean muted) {
            LiveEngineImpl liveEngineImpl = this.mLiveEngineImpl;
            if (liveEngineImpl == null || liveEngineImpl.mSubscriber == null) {
                return;
            }
            Integer num = this.remoteStreamMediaStats.get(Integer.valueOf(uid));
            int iIntValue = num != null ? num.intValue() : 0;
            int i2 = muted ? iIntValue | 1 : iIntValue & (-2);
            int mediaTypeByStat = getMediaTypeByStat(i2);
            if (num == null) {
                this.mLiveEngineImpl.mSubscriber.getLiveSubscriberHandler().publishedByHost(uid, mediaTypeByStat);
            } else {
                this.mLiveEngineImpl.mSubscriber.getLiveSubscriberHandler().onStreamTypeChanged(mediaTypeByStat, uid);
            }
            this.remoteStreamMediaStats.put(Integer.valueOf(uid), Integer.valueOf(i2));
        }

        @Override // io.agora.rtc.IRtcEngineEventHandler
        public void onUserMuteVideo(int uid, boolean muted) {
            LiveEngineImpl liveEngineImpl = this.mLiveEngineImpl;
            if (liveEngineImpl == null || liveEngineImpl.mSubscriber == null) {
                return;
            }
            Integer num = this.remoteStreamMediaStats.get(Integer.valueOf(uid));
            int iIntValue = num != null ? num.intValue() : 0;
            int i2 = muted ? iIntValue | 2 : iIntValue & (-3);
            int mediaTypeByStat = getMediaTypeByStat(i2);
            if (num == null) {
                this.mLiveEngineImpl.mSubscriber.getLiveSubscriberHandler().publishedByHost(uid, mediaTypeByStat);
            } else {
                this.mLiveEngineImpl.mSubscriber.getLiveSubscriberHandler().onStreamTypeChanged(mediaTypeByStat, uid);
            }
            this.remoteStreamMediaStats.put(Integer.valueOf(uid), Integer.valueOf(i2));
        }

        @Override // io.agora.rtc.IRtcEngineEventHandler
        public void onUserOffline(int uid, int reason) {
            LiveEngineImpl liveEngineImpl = this.mLiveEngineImpl;
            if (liveEngineImpl == null || liveEngineImpl.mSubscriber == null) {
                return;
            }
            this.remoteStreamMediaStats.remove(Integer.valueOf(uid));
            this.mLiveEngineImpl.mSubscriber.getLiveSubscriberHandler().unpublishedByHost(uid);
        }

        @Override // io.agora.rtc.IRtcEngineEventHandler
        public void onVideoSizeChanged(int uid, int width, int height, int rotation) {
            LiveEngineImpl liveEngineImpl = this.mLiveEngineImpl;
            if (liveEngineImpl == null || liveEngineImpl.mSubscriber == null) {
                return;
            }
            this.mLiveEngineImpl.mSubscriber.getLiveSubscriberHandler().onVideoSizeChanged(uid, width, height, rotation);
        }

        @Override // io.agora.rtc.IRtcEngineEventHandler
        public void onWarning(int warn) {
            LiveEngineHandler liveEngineHandler = this.mLiveHandler;
            if (liveEngineHandler != null) {
                liveEngineHandler.onWarning(warn);
            }
        }

        public void setLiveHandler(LiveEngineHandler handler) {
            this.mLiveHandler = handler;
        }

        public RtcEngineHandler setObjects(LiveEngineImpl liveEngineImpl) {
            this.mLiveEngineImpl = liveEngineImpl;
            return gRtcHandler;
        }
    }

    public LiveEngineImpl(Context context, String appId, LiveEngineHandler handler) {
        try {
            RtcEngineHandler.get().setObjects(this).setLiveHandler(handler);
            this.mRtcEngine = RtcEngine.create(context, appId, RtcEngineHandler.get());
        } catch (Exception e2) {
            RtcEngineHandler.get().setObjects(null).setLiveHandler(null);
            Logging.e(TAG, "failed to create AgoraLiveEngine", e2);
        }
    }

    public void doDestroy() {
        RtcEngineHandler.get().setObjects(null).setLiveHandler(null);
    }

    @Override // io.agora.live.LiveEngine
    public LiveChannelConfig getLiveChannelConfig() {
        return this.mChannelConfig;
    }

    @Override // io.agora.live.LiveEngine
    public RtcEngine getRtcEngine() {
        return this.mRtcEngine;
    }

    @Override // io.agora.live.LiveEngine
    public int joinChannel(String channelId, String token, LiveChannelConfig channelConfig, int uid) {
        this.mRtcEngine.setChannelProfile(1);
        this.mChannelConfig = channelConfig;
        if (channelConfig.videoEnabled) {
            this.mRtcEngine.enableVideo();
            this.mRtcEngine.enableDualStreamMode(true);
        } else {
            this.mRtcEngine.disableVideo();
        }
        this.mRtcEngine.setClientRole(2);
        this.mRtcEngine.setEncryptionMode("");
        this.mRtcEngine.setEncryptionSecret(null);
        return this.mRtcEngine.joinChannel(token, channelId, null, uid);
    }

    @Override // io.agora.live.LiveEngine
    public int leaveChannel() {
        LivePublisher livePublisher = this.mPublisher;
        if (livePublisher != null) {
            livePublisher.unpublish();
        }
        return this.mRtcEngine.leaveChannel();
    }

    public void reinitialize(Context context, String appId, LiveEngineHandler handler) {
        RtcEngineHandler.get().setObjects(this).setLiveHandler(handler);
    }

    @Override // io.agora.live.LiveEngine
    public int renewToken(String token) {
        if (TextUtils.isEmpty(token)) {
            return 2;
        }
        return this.mRtcEngine.renewToken(token);
    }

    @Override // io.agora.live.LiveEngine
    public void setPublisher(LivePublisher publisher) {
        this.mPublisher = publisher;
    }

    @Override // io.agora.live.LiveEngine
    public void setSubscriber(LiveSubscriber subscriber) {
        this.mSubscriber = subscriber;
    }

    @Override // io.agora.live.LiveEngine
    public int startPreview(SurfaceView view, int renderMode) {
        this.mRtcEngine.setupLocalVideo(new VideoCanvas(view, renderMode, 0));
        return this.mRtcEngine.startPreview();
    }

    @Override // io.agora.live.LiveEngine
    public int stopPreview() {
        this.mRtcEngine.setupLocalVideo(new VideoCanvas(null));
        return this.mRtcEngine.stopPreview();
    }
}
