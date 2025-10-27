package com.psychiatrygarden.widget.glideUtil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.ImageView;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.psychiatrygarden.widget.glideUtil.progress.OnProgressListener;
import com.psychiatrygarden.widget.glideUtil.transformation.CircleTransformation;
import com.psychiatrygarden.widget.glideUtil.transformation.RadiusTransformation;

@SuppressLint({"CheckResult", "AppCompatCustomView"})
/* loaded from: classes6.dex */
public class GlideImageView extends ImageView {
    private boolean enableState;
    private GlideImageLoader imageLoader;
    private float pressedAlpha;
    private float unableAlpha;

    public GlideImageView(Context context) {
        this(context, null);
    }

    private void init() {
        this.imageLoader = GlideImageLoader.create(this);
    }

    public GlideImageView apply(RequestOptions options) {
        getImageLoader().getGlideRequest().apply((BaseRequestOptions<?>) options);
        return this;
    }

    public GlideImageView centerCrop() {
        getImageLoader().getGlideRequest().centerCrop();
        return this;
    }

    public GlideImageView diskCacheStrategy(@NonNull DiskCacheStrategy strategy) {
        getImageLoader().getGlideRequest().diskCacheStrategy(strategy);
        return this;
    }

    public GlideImageView dontAnimate() {
        getImageLoader().getGlideRequest().dontTransform();
        return this;
    }

    public GlideImageView dontTransform() {
        getImageLoader().getGlideRequest().dontTransform();
        return this;
    }

    @Override // android.widget.ImageView, android.view.View
    public void drawableStateChanged() {
        super.drawableStateChanged();
        if (this.enableState) {
            if (isPressed()) {
                setAlpha(this.pressedAlpha);
            } else if (isEnabled()) {
                setAlpha(1.0f);
            } else {
                setAlpha(this.unableAlpha);
            }
        }
    }

    public GlideImageView enableState(boolean enableState) {
        this.enableState = enableState;
        return this;
    }

    public GlideImageView error(@DrawableRes int resId) {
        getImageLoader().getGlideRequest().error(resId);
        return this;
    }

    public GlideImageView fallback(@DrawableRes int resId) {
        getImageLoader().getGlideRequest().fallback(resId);
        return this;
    }

    public GlideImageView fitCenter() {
        getImageLoader().getGlideRequest().fitCenter();
        return this;
    }

    public GlideImageLoader getImageLoader() {
        if (this.imageLoader == null) {
            this.imageLoader = GlideImageLoader.create(this);
        }
        return this.imageLoader;
    }

    public void load(String url) {
        load(url, 0);
    }

    public void loadCircle(String url) {
        loadCircle(url, 0);
    }

    public void loadDrawable(@DrawableRes int resId) {
        loadDrawable(resId, 0);
    }

    public GlideImageView placeholder(@DrawableRes int resId) {
        getImageLoader().getGlideRequest().placeholder(resId);
        return this;
    }

    public GlideImageView pressedAlpha(float pressedAlpha) {
        this.pressedAlpha = pressedAlpha;
        return this;
    }

    public GlideImageView unableAlpha(float unableAlpha) {
        this.unableAlpha = unableAlpha;
        return this;
    }

    public GlideImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public void load(String url, @DrawableRes int placeholder) {
        load(url, placeholder, 0);
    }

    public void loadCircle(String url, @DrawableRes int placeholder) {
        loadCircle(url, placeholder, null);
    }

    public void loadDrawable(@DrawableRes int resId, @DrawableRes int placeholder) {
        loadDrawable(resId, placeholder, null);
    }

    public GlideImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.enableState = false;
        this.pressedAlpha = 0.4f;
        this.unableAlpha = 0.3f;
        if (isInEditMode()) {
            return;
        }
        init();
    }

    public void load(String url, @DrawableRes int placeholder, int radius) {
        load(url, placeholder, radius, (OnProgressListener) null);
    }

    public void loadCircle(String url, @DrawableRes int placeholder, OnProgressListener onProgressListener) {
        load(url, placeholder, new CircleTransformation(), onProgressListener);
    }

    public void loadDrawable(@DrawableRes int resId, @DrawableRes int placeholder, @NonNull Transformation<Bitmap> transformation) {
        getImageLoader().load(resId, placeholder, transformation);
    }

    public void load(String url, @DrawableRes int placeholder, OnProgressListener onProgressListener) {
        load(url, placeholder, (Transformation<Bitmap>) null, onProgressListener);
    }

    public void load(String url, @DrawableRes int placeholder, int radius, OnProgressListener onProgressListener) {
        load(url, placeholder, new RadiusTransformation(getContext(), radius), onProgressListener);
    }

    public void load(Object obj, @DrawableRes int placeholder, Transformation<Bitmap> transformation) {
        getImageLoader().loadImage(obj, placeholder, transformation);
    }

    public void load(Object obj, @DrawableRes int placeholder, Transformation<Bitmap> transformation, OnProgressListener onProgressListener) {
        getImageLoader().listener(obj, onProgressListener).loadImage(obj, placeholder, transformation);
    }
}
