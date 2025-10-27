package com.psychiatrygarden.widget.glideUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.psychiatrygarden.widget.glideUtil.progress.GlideRequest;
import com.psychiatrygarden.widget.glideUtil.progress.OnProgressListener;
import com.psychiatrygarden.widget.glideUtil.progress.ProgressManager;
import com.yikaobang.yixue.R;
import java.lang.ref.WeakReference;

/* loaded from: classes6.dex */
public class GlideImageLoader {
    protected static final String ANDROID_RESOURCE = "android.resource://";
    protected static final String FILE = "file://";
    protected static final String SEPARATOR = "/";
    private GlideRequest<Drawable> glideRequest = GlideApp.with(getContext()).asDrawable();
    private WeakReference<ImageView> imageViewWeakReference;
    private String url;

    public class GlideImageViewTarget extends DrawableImageViewTarget {
        public GlideImageViewTarget(ImageView view) {
            super(view);
        }

        @Override // com.bumptech.glide.request.target.ImageViewTarget, com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
        public void onLoadFailed(@Nullable Drawable errorDrawable) {
            OnProgressListener progressListener = ProgressManager.getProgressListener(GlideImageLoader.this.getUrl());
            if (progressListener != null) {
                progressListener.onProgress(true, 100, 0L, 0L);
                ProgressManager.removeListener(GlideImageLoader.this.getUrl());
            }
            super.onLoadFailed(errorDrawable);
        }

        @Override // com.bumptech.glide.request.target.ImageViewTarget, com.bumptech.glide.request.target.ViewTarget, com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
        public void onLoadStarted(Drawable placeholder) {
            super.onLoadStarted(placeholder);
        }

        @Override // com.bumptech.glide.request.target.ImageViewTarget, com.bumptech.glide.request.target.Target
        public /* bridge */ /* synthetic */ void onResourceReady(@NonNull Object resource, @Nullable Transition transition) {
            onResourceReady((Drawable) resource, (Transition<? super Drawable>) transition);
        }

        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
            OnProgressListener progressListener = ProgressManager.getProgressListener(GlideImageLoader.this.getUrl());
            if (progressListener != null) {
                progressListener.onProgress(true, 100, 0L, 0L);
                ProgressManager.removeListener(GlideImageLoader.this.getUrl());
            }
            super.onResourceReady((GlideImageViewTarget) resource, (Transition<? super GlideImageViewTarget>) transition);
        }
    }

    private GlideImageLoader(ImageView imageView) {
        this.imageViewWeakReference = new WeakReference<>(imageView);
    }

    public static GlideImageLoader create(ImageView imageView) {
        return new GlideImageLoader(imageView);
    }

    public Context getContext() {
        if (getImageView() != null) {
            return getImageView().getContext();
        }
        return null;
    }

    public GlideRequest getGlideRequest() {
        if (this.glideRequest == null) {
            this.glideRequest = GlideApp.with(getContext()).asDrawable();
        }
        return this.glideRequest;
    }

    public ImageView getImageView() {
        WeakReference<ImageView> weakReference = this.imageViewWeakReference;
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }

    public String getUrl() {
        return this.url;
    }

    public GlideImageLoader listener(Object obj, OnProgressListener onProgressListener) {
        if (obj instanceof String) {
            this.url = (String) obj;
        }
        ProgressManager.addListener(this.url, onProgressListener);
        return this;
    }

    public GlideImageLoader load(@DrawableRes int resId, @DrawableRes int placeholder, @NonNull Transformation<Bitmap> transformation) {
        return loadImage(resId2Uri(resId), placeholder, transformation);
    }

    public GlideRequest<Drawable> loadImage(Object obj) {
        if (obj instanceof String) {
            this.url = (String) obj;
        }
        return this.glideRequest.load(obj);
    }

    public Uri resId2Uri(@DrawableRes int resId) {
        return Uri.parse(ANDROID_RESOURCE + getContext().getPackageName() + "/" + resId);
    }

    public GlideImageLoader loadImage(Object obj, @DrawableRes int placeholder, Transformation<Bitmap> transformation) {
        GlideUrl glideUrlGenerateUrl = obj instanceof String ? GlideUtils.generateUrl((String) obj) : null;
        if (glideUrlGenerateUrl != null) {
            obj = glideUrlGenerateUrl;
        }
        GlideRequest<Drawable> glideRequestLoadImage = loadImage(obj);
        this.glideRequest = glideRequestLoadImage;
        if (placeholder != 0) {
            this.glideRequest = glideRequestLoadImage.placeholder((Drawable) new ColorDrawable(ContextCompat.getColor(ProjectApp.instance(), SkinManager.getCurrentSkinType(ProjectApp.instance()) == 0 ? R.color.fourth_line_backgroup_color : R.color.bg_backgroud_night)));
        }
        if (transformation != null) {
            this.glideRequest = this.glideRequest.transform(transformation);
        }
        this.glideRequest.into((GlideRequest<Drawable>) new GlideImageViewTarget(getImageView()));
        return this;
    }
}
