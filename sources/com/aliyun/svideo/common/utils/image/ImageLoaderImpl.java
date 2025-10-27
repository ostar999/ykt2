package com.aliyun.svideo.common.utils.image;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.aliyun.svideo.common.utils.image.GlideRoundedCornersTransform;
import com.aliyun.svideo.common.utils.image.ImageLoaderOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomViewTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

/* loaded from: classes2.dex */
public class ImageLoaderImpl extends AbstractImageLoader {
    private static final String TAG = "ImageLoaderImpl";
    private RequestBuilder mRequestBuilder;

    @SuppressLint({"CheckResult"})
    private <R> void loadGlideOption(Context context, RequestBuilder<R> requestBuilder, ImageLoaderOptions imageLoaderOptions) {
        int i2;
        this.mRequestBuilder = requestBuilder;
        RequestOptions requestOptionsSkipMemoryCacheOf = RequestOptions.skipMemoryCacheOf(imageLoaderOptions.isSkipMemoryCache());
        if (imageLoaderOptions.getHolderDrawable() != null) {
            requestOptionsSkipMemoryCacheOf = requestOptionsSkipMemoryCacheOf.placeholder(imageLoaderOptions.getHolderDrawable());
        }
        if (imageLoaderOptions.getHolderDrawableId() != -1) {
            requestOptionsSkipMemoryCacheOf = requestOptionsSkipMemoryCacheOf.placeholder(imageLoaderOptions.getHolderDrawableId());
        }
        if (imageLoaderOptions.getErrorDrawableId() != -1) {
            requestOptionsSkipMemoryCacheOf = requestOptionsSkipMemoryCacheOf.error(imageLoaderOptions.getErrorDrawableId());
        }
        if (imageLoaderOptions.isCenterCrop()) {
            requestOptionsSkipMemoryCacheOf = requestOptionsSkipMemoryCacheOf.centerCrop();
        }
        if (imageLoaderOptions.isCircle()) {
            requestOptionsSkipMemoryCacheOf = requestOptionsSkipMemoryCacheOf.optionalCircleCrop();
        }
        RequestOptions requestOptionsDiskCacheStrategy = imageLoaderOptions.isSkipDiskCacheCache() ? requestOptionsSkipMemoryCacheOf.diskCacheStrategy(DiskCacheStrategy.NONE) : requestOptionsSkipMemoryCacheOf.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        if (imageLoaderOptions.getThumbnail() != 1.0f) {
            this.mRequestBuilder.thumbnail(imageLoaderOptions.getThumbnail());
        }
        Point overridePoint = imageLoaderOptions.getOverridePoint();
        int i3 = overridePoint.x;
        if (i3 != 0 && (i2 = overridePoint.y) != 0) {
            requestOptionsDiskCacheStrategy = requestOptionsDiskCacheStrategy.override(i3, i2);
        }
        if (imageLoaderOptions.isRoundCorner()) {
            requestOptionsDiskCacheStrategy = requestOptionsDiskCacheStrategy.transform(new GlideRoundedCornersTransform(context, 4.0f, GlideRoundedCornersTransform.CornerType.ALL));
        }
        this.mRequestBuilder.apply((BaseRequestOptions<?>) requestOptionsDiskCacheStrategy);
    }

    private void loadGlideResource(@NonNull Context context, Object obj, @NonNull ImageLoaderOptions imageLoaderOptions) {
        RequestManager requestManagerWith;
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (activity.isDestroyed()) {
                Log.e(TAG, "You cannot start a load for a destroyed activity");
                return;
            }
            requestManagerWith = Glide.with(activity);
        } else {
            requestManagerWith = Glide.with(context);
        }
        if (imageLoaderOptions.isAsBitmap()) {
            RequestBuilder<Bitmap> requestBuilderLoad = requestManagerWith.asBitmap().load(obj instanceof String ? (String) obj : (Integer) obj);
            if (imageLoaderOptions.isCrossFade()) {
                requestBuilderLoad = requestBuilderLoad.transition(new BitmapTransitionOptions().crossFade());
            }
            loadGlideOption(context, requestBuilderLoad, imageLoaderOptions);
            return;
        }
        RequestBuilder<Drawable> requestBuilderLoad2 = requestManagerWith.load(obj instanceof String ? (String) obj : (Integer) obj);
        if (imageLoaderOptions.isCrossFade()) {
            requestBuilderLoad2 = requestBuilderLoad2.transition(new DrawableTransitionOptions().crossFade());
        }
        loadGlideOption(context, requestBuilderLoad2, imageLoaderOptions);
    }

    @Override // com.aliyun.svideo.common.utils.image.AbstractImageLoader
    public void clear(@NonNull Context context, @NonNull ImageView imageView) {
        Glide.with(context).clear(imageView);
    }

    @Override // com.aliyun.svideo.common.utils.image.AbstractImageLoader
    public void into(@NonNull ImageView imageView) {
        this.mRequestBuilder.into(imageView);
    }

    @Override // com.aliyun.svideo.common.utils.image.AbstractImageLoader
    @SuppressLint({"CheckResult"})
    public <R> AbstractImageLoader listener(@NonNull final ImageLoaderRequestListener<R> imageLoaderRequestListener) {
        this.mRequestBuilder.listener(new RequestListener<R>() { // from class: com.aliyun.svideo.common.utils.image.ImageLoaderImpl.1
            @Override // com.bumptech.glide.request.RequestListener
            public boolean onLoadFailed(@Nullable GlideException glideException, Object obj, Target<R> target, boolean z2) {
                imageLoaderRequestListener.onLoadFailed(glideException == null ? "no msg" : glideException.getMessage(), z2);
                return false;
            }

            @Override // com.bumptech.glide.request.RequestListener
            public boolean onResourceReady(R r2, Object obj, Target<R> target, DataSource dataSource, boolean z2) {
                imageLoaderRequestListener.onResourceReady(r2, z2);
                return false;
            }
        });
        return this;
    }

    @Override // com.aliyun.svideo.common.utils.image.AbstractImageLoader
    public AbstractImageLoader loadImage(@NonNull Context context, @NonNull String str) {
        return loadImage(context, str, new ImageLoaderOptions.Builder().build());
    }

    @Override // com.aliyun.svideo.common.utils.image.AbstractImageLoader
    public <T> void into(@NonNull View view, @NonNull final AbstractImageLoaderTarget<T> abstractImageLoaderTarget) {
        this.mRequestBuilder.into((RequestBuilder) new CustomViewTarget<View, T>(view) { // from class: com.aliyun.svideo.common.utils.image.ImageLoaderImpl.2
            @Override // com.bumptech.glide.request.target.Target
            public void onLoadFailed(@Nullable Drawable drawable) {
                abstractImageLoaderTarget.onLoadFailed(drawable);
            }

            @Override // com.bumptech.glide.request.target.CustomViewTarget
            public void onResourceCleared(@Nullable Drawable drawable) {
                abstractImageLoaderTarget.onLoadCleared(drawable);
            }

            @Override // com.bumptech.glide.request.target.Target
            public void onResourceReady(@NonNull T t2, @Nullable Transition<? super T> transition) {
                abstractImageLoaderTarget.onResourceReady(t2);
            }

            @Override // com.bumptech.glide.request.target.CustomViewTarget, com.bumptech.glide.manager.LifecycleListener
            public void onStart() {
                super.onStart();
                abstractImageLoaderTarget.onLoadStarted();
            }
        });
    }

    @Override // com.aliyun.svideo.common.utils.image.AbstractImageLoader
    public AbstractImageLoader loadImage(@NonNull Context context, @NonNull int i2) {
        return loadImage(context, i2, new ImageLoaderOptions.Builder().build());
    }

    @Override // com.aliyun.svideo.common.utils.image.AbstractImageLoader
    public AbstractImageLoader loadImage(@NonNull Context context, @NonNull String str, @NonNull ImageLoaderOptions imageLoaderOptions) {
        loadGlideResource(context, str, imageLoaderOptions);
        return this;
    }

    @Override // com.aliyun.svideo.common.utils.image.AbstractImageLoader
    public AbstractImageLoader loadImage(@NonNull Context context, int i2, @NonNull ImageLoaderOptions imageLoaderOptions) {
        loadGlideResource(context, Integer.valueOf(i2), imageLoaderOptions);
        return this;
    }
}
