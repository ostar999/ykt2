package com.github.chrisbanes.photoview;

import android.annotation.TargetApi;
import android.view.View;

/* loaded from: classes3.dex */
class Compat {
    private static final int SIXTY_FPS_INTERVAL = 16;

    public static void postOnAnimation(View view, Runnable runnable) {
        postOnAnimationJellyBean(view, runnable);
    }

    @TargetApi(16)
    private static void postOnAnimationJellyBean(View view, Runnable runnable) {
        view.postOnAnimation(runnable);
    }
}
