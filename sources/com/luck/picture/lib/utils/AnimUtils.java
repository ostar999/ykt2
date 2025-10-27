package com.luck.picture.lib.utils;

import android.animation.ObjectAnimator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import androidx.constraintlayout.motion.widget.Key;

/* loaded from: classes4.dex */
public class AnimUtils {
    public static final int DURATION = 250;

    public static void rotateArrow(ImageView imageView, boolean z2) {
        float f2;
        float f3 = 180.0f;
        if (z2) {
            f3 = 0.0f;
            f2 = 180.0f;
        } else {
            f2 = 360.0f;
        }
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(imageView, Key.ROTATION, f3, f2);
        objectAnimatorOfFloat.setDuration(250L);
        objectAnimatorOfFloat.setInterpolator(new LinearInterpolator());
        objectAnimatorOfFloat.start();
    }
}
