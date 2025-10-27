package io.agora.rtc;

import android.opengl.EGLContext;

/* loaded from: classes8.dex */
public abstract class RtcEngineEx extends RtcEngine {
    public abstract int enableRecap(int interval);

    public abstract int enableTransportQualityIndication(boolean enabled);

    public abstract String getParameters(String parameters);

    public abstract String makeQualityReportUrl(String channel, int listenerUid, int speakerUid, int format);

    public abstract int monitorAudioRouteChange(boolean isMonitoring);

    public abstract int playRecap();

    public abstract int setApiCallMode(int syncCallTimeout);

    public abstract int setAppType(int appType);

    public abstract int setProfile(String profile, boolean merge);

    public abstract int setTextureId(int id, EGLContext eglContext, int width, int height, long ts);

    public abstract int setTextureId(int id, javax.microedition.khronos.egl.EGLContext eglContext, int width, int height, long ts);

    public abstract int updateSharedContext(EGLContext sharedContext);

    public abstract int updateSharedContext(javax.microedition.khronos.egl.EGLContext sharedContext);
}
