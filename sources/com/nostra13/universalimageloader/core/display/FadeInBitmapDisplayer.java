package com.nostra13.universalimageloader.core.display;

import android.graphics.Bitmap;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.DecelerateInterpolator;
import com.nostra13.universalimageloader.core.assist.LoadedFrom;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;

/* loaded from: classes4.dex */
public class FadeInBitmapDisplayer implements BitmapDisplayer {
    private final boolean animateFromDisk;
    private final boolean animateFromMemory;
    private final boolean animateFromNetwork;
    private final int durationMillis;

    public FadeInBitmapDisplayer(int i2) {
        this(i2, true, true, true);
    }

    public static void animate(View view, int i2) {
        if (view != null) {
            AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
            alphaAnimation.setDuration(i2);
            alphaAnimation.setInterpolator(new DecelerateInterpolator());
            view.startAnimation(alphaAnimation);
        }
    }

    @Override // com.nostra13.universalimageloader.core.display.BitmapDisplayer
    public void display(Bitmap bitmap, ImageAware imageAware, LoadedFrom loadedFrom) {
        imageAware.setImageBitmap(bitmap);
        if ((this.animateFromNetwork && loadedFrom == LoadedFrom.NETWORK) || ((this.animateFromDisk && loadedFrom == LoadedFrom.DISC_CACHE) || (this.animateFromMemory && loadedFrom == LoadedFrom.MEMORY_CACHE))) {
            animate(imageAware.getWrappedView(), this.durationMillis);
        }
    }

    public FadeInBitmapDisplayer(int i2, boolean z2, boolean z3, boolean z4) {
        this.durationMillis = i2;
        this.animateFromNetwork = z2;
        this.animateFromDisk = z3;
        this.animateFromMemory = z4;
    }
}
