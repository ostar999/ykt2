package net.tsz.afinal.bitmap.display;

import android.R;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import net.tsz.afinal.bitmap.core.BitmapDisplayConfig;

/* loaded from: classes9.dex */
public class SimpleDisplayer implements Displayer {
    private void animationDisplay(View view, Bitmap bitmap, Animation animation) {
        animation.setStartTime(AnimationUtils.currentAnimationTimeMillis());
        if (view instanceof ImageView) {
            ((ImageView) view).setImageBitmap(bitmap);
        } else {
            view.setBackgroundDrawable(new BitmapDrawable(bitmap));
        }
        view.startAnimation(animation);
    }

    private void fadeInDisplay(View view, Bitmap bitmap) {
        TransitionDrawable transitionDrawable = new TransitionDrawable(new Drawable[]{new ColorDrawable(R.color.transparent), new BitmapDrawable(view.getResources(), bitmap)});
        if (view instanceof ImageView) {
            ((ImageView) view).setImageDrawable(transitionDrawable);
        } else {
            view.setBackgroundDrawable(transitionDrawable);
        }
        transitionDrawable.startTransition(300);
    }

    @Override // net.tsz.afinal.bitmap.display.Displayer
    public void loadCompletedisplay(View view, Bitmap bitmap, BitmapDisplayConfig bitmapDisplayConfig) {
        int animationType = bitmapDisplayConfig.getAnimationType();
        if (animationType == 0) {
            animationDisplay(view, bitmap, bitmapDisplayConfig.getAnimation());
        } else {
            if (animationType != 1) {
                return;
            }
            fadeInDisplay(view, bitmap);
        }
    }

    @Override // net.tsz.afinal.bitmap.display.Displayer
    public void loadFailDisplay(View view, Bitmap bitmap) {
        if (view instanceof ImageView) {
            ((ImageView) view).setImageBitmap(bitmap);
        } else {
            view.setBackgroundDrawable(new BitmapDrawable(bitmap));
        }
    }
}
