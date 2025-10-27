package io.agora.live;

import android.content.Context;
import android.view.SurfaceView;
import io.agora.live.impl.LiveEngineImpl;
import io.agora.rtc.RtcEngine;

/* loaded from: classes8.dex */
public abstract class LiveEngine {
    private static LiveEngineImpl gLiveEngineImpl;

    public static LiveEngine createLiveEngine(Context context, String appId, LiveEngineHandler handler) {
        if (context == null) {
            return null;
        }
        LiveEngineImpl liveEngineImpl = gLiveEngineImpl;
        if (liveEngineImpl == null) {
            gLiveEngineImpl = new LiveEngineImpl(context, appId, handler);
        } else {
            liveEngineImpl.reinitialize(context, appId, handler);
        }
        return gLiveEngineImpl;
    }

    public static synchronized void destroy() {
        LiveEngineImpl liveEngineImpl = gLiveEngineImpl;
        if (liveEngineImpl == null) {
            return;
        }
        liveEngineImpl.doDestroy();
        gLiveEngineImpl = null;
        RtcEngine.destroy();
    }

    public static String getMediaEngineVersion() {
        return RtcEngine.getMediaEngineVersion();
    }

    public static String getSdkVersion() {
        return RtcEngine.getSdkVersion();
    }

    public abstract LiveChannelConfig getLiveChannelConfig();

    public abstract RtcEngine getRtcEngine();

    public abstract int joinChannel(String channelId, String token, LiveChannelConfig channelConfig, int uid);

    public abstract int leaveChannel();

    public abstract int renewToken(String token);

    public abstract void setPublisher(LivePublisher publisher);

    public abstract void setSubscriber(LiveSubscriber subscriber);

    public abstract int startPreview(SurfaceView view, int renderMode);

    public abstract int stopPreview();
}
