package com.psychiatrygarden.activity.purchase.zoom;

import android.view.View;

/* loaded from: classes5.dex */
public class Compat {
    private static final int SIXTY_FPS_INTERVAL = 16;

    public static void postOnAnimation(View view, Runnable runnable) {
        SDK16.postOnAnimation(view, runnable);
    }
}
