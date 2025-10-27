package com.ucloudrtclib.sdkengine.define;

import android.content.Context;
import android.util.AttributeSet;
import core.renderer.CoreSurfaceViewRenderer;
import org.wrtca.api.RendererCommon;

/* loaded from: classes6.dex */
public class UCloudRtcRenderView extends CoreSurfaceViewRenderer implements RendererCommon.RendererEvents {
    public UCloudRtcRenderView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public UCloudRtcRenderView(Context context) {
        super(context);
    }
}
