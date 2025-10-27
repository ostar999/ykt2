package io.agora.rtc.video;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/* loaded from: classes8.dex */
public class ViERenderer {
    private static SurfaceHolder g_localRenderer;

    public static SurfaceView CreateLocalRenderer(Context context) {
        return new SurfaceView(context);
    }

    public static SurfaceHolder GetLocalRenderer() {
        return g_localRenderer;
    }

    public static void setLocalView(SurfaceView local, int top2, int left, int width, int height) {
        if (local == null) {
            g_localRenderer = null;
        } else {
            g_localRenderer = local.getHolder();
        }
    }
}
