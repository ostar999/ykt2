package io.agora.rtc.video;

import android.view.SurfaceView;

/* loaded from: classes8.dex */
public class VideoCanvas {

    @Deprecated
    public static final int RENDER_MODE_ADAPTIVE = 3;
    public static final int RENDER_MODE_FIT = 2;
    public static final int RENDER_MODE_HIDDEN = 1;
    public String channelId;
    public int renderMode;
    public int uid;
    public SurfaceView view;

    public VideoCanvas(SurfaceView view) {
        this.view = view;
        this.renderMode = 1;
    }

    public VideoCanvas(SurfaceView view, int renderMode, int uid) {
        this.view = view;
        this.renderMode = renderMode;
        this.uid = uid;
    }

    public VideoCanvas(SurfaceView view, int renderMode, String channelId, int uid) {
        this.view = view;
        this.renderMode = renderMode;
        this.channelId = channelId;
        this.uid = uid;
    }
}
