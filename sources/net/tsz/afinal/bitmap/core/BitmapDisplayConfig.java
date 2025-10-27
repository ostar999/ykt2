package net.tsz.afinal.bitmap.core;

import android.graphics.Bitmap;
import android.view.animation.Animation;

/* loaded from: classes9.dex */
public class BitmapDisplayConfig {
    private Animation animation;
    private int animationType;
    private int bitmapHeight;
    private int bitmapWidth;
    private Bitmap loadfailBitmap;
    private Bitmap loadingBitmap;

    public class AnimationType {
        public static final int fadeIn = 1;
        public static final int userDefined = 0;

        public AnimationType() {
        }
    }

    public Animation getAnimation() {
        return this.animation;
    }

    public int getAnimationType() {
        return this.animationType;
    }

    public int getBitmapHeight() {
        return this.bitmapHeight;
    }

    public int getBitmapWidth() {
        return this.bitmapWidth;
    }

    public Bitmap getLoadfailBitmap() {
        return this.loadfailBitmap;
    }

    public Bitmap getLoadingBitmap() {
        return this.loadingBitmap;
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
    }

    public void setAnimationType(int i2) {
        this.animationType = i2;
    }

    public void setBitmapHeight(int i2) {
        this.bitmapHeight = i2;
    }

    public void setBitmapWidth(int i2) {
        this.bitmapWidth = i2;
    }

    public void setLoadfailBitmap(Bitmap bitmap) {
        this.loadfailBitmap = bitmap;
    }

    public void setLoadingBitmap(Bitmap bitmap) {
        this.loadingBitmap = bitmap;
    }
}
