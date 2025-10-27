package com.easefun.polyv.livecommon.ui.widget.blurview;

import android.graphics.Canvas;

/* loaded from: classes3.dex */
interface BlurController extends BlurViewFacade {
    public static final float DEFAULT_BLUR_RADIUS = 16.0f;
    public static final float DEFAULT_SCALE_FACTOR = 8.0f;

    void destroy();

    boolean draw(Canvas canvas);

    void updateBlurViewSize();
}
