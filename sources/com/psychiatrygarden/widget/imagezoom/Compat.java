package com.psychiatrygarden.widget.imagezoom;

import android.view.View;
import androidx.core.view.MotionEventCompat;

/* loaded from: classes6.dex */
public class Compat {
    private static final int SIXTY_FPS_INTERVAL = 16;

    public static int getPointerIndex(int action) {
        return getPointerIndexHoneyComb(action);
    }

    private static int getPointerIndexEclair(int action) {
        return (action & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8;
    }

    private static int getPointerIndexHoneyComb(int action) {
        return (action & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8;
    }

    public static void postOnAnimation(View view, Runnable runnable) {
        postOnAnimationJellyBean(view, runnable);
    }

    private static void postOnAnimationJellyBean(View view, Runnable runnable) {
        view.postOnAnimation(runnable);
    }
}
