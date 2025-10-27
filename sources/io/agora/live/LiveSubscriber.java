package io.agora.live;

import android.util.Log;
import android.view.SurfaceView;
import io.agora.rtc.RtcEngine;
import io.agora.rtc.video.VideoCanvas;
import java.lang.ref.WeakReference;

/* loaded from: classes8.dex */
public class LiveSubscriber {
    private static final String TAG = "LiveSubscriber";
    private final LiveSubscriberHandler mHandler;
    private WeakReference<LiveEngine> mLiveEngine;

    public LiveSubscriber(LiveEngine engine, LiveSubscriberHandler handler) {
        WeakReference<LiveEngine> weakReference = new WeakReference<>(engine);
        this.mLiveEngine = weakReference;
        this.mHandler = handler;
        LiveEngine liveEngine = weakReference.get();
        if (liveEngine != null) {
            liveEngine.setSubscriber(this);
        }
    }

    public LiveSubscriberHandler getLiveSubscriberHandler() {
        return this.mHandler;
    }

    public void subscribe(int uid, int mediaType, SurfaceView view, int renderMode, int videoType) {
        boolean z2;
        LiveEngine liveEngine = this.mLiveEngine.get();
        if (liveEngine == null) {
            Log.e(TAG, "LiveEngine is null, none subscribing...");
            return;
        }
        RtcEngine rtcEngine = liveEngine.getRtcEngine();
        rtcEngine.setupRemoteVideo(new VideoCanvas(view, renderMode, uid));
        rtcEngine.setRemoteVideoStreamType(uid, videoType);
        rtcEngine.setRemoteRenderMode(uid, renderMode);
        boolean z3 = false;
        if (mediaType == 0) {
            z2 = false;
        } else if (mediaType == 1) {
            z2 = false;
            z3 = true;
        } else if (mediaType != 2) {
            z2 = true;
            z3 = true;
        } else {
            z2 = true;
        }
        rtcEngine.muteRemoteAudioStream(uid, !z3);
        rtcEngine.muteRemoteVideoStream(uid, !z2);
    }

    public void unsubscribe(int uid) {
        LiveEngine liveEngine = this.mLiveEngine.get();
        if (liveEngine == null) {
            Log.e(TAG, "LiveEngine is null while unsubscribing");
            return;
        }
        RtcEngine rtcEngine = liveEngine.getRtcEngine();
        rtcEngine.setupRemoteVideo(new VideoCanvas(null, 1, uid));
        rtcEngine.muteRemoteAudioStream(uid, true);
        rtcEngine.muteRemoteVideoStream(uid, true);
    }
}
