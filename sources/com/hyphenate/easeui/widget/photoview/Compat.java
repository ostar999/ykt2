package com.hyphenate.easeui.widget.photoview;

import android.view.View;

/* loaded from: classes4.dex */
class Compat {
    private static final int SIXTY_FPS_INTERVAL = 16;

    public static void postOnAnimation(View view, Runnable runnable) {
        SDK16.postOnAnimation(view, runnable);
    }
}
