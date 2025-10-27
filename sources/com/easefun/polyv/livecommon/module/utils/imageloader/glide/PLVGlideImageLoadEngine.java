package com.easefun.polyv.livecommon.module.utils.imageloader.glide;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.target.ViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.signature.ObjectKey;
import com.easefun.polyv.livecommon.module.utils.imageloader.IPLVImageLoadEngine;
import com.easefun.polyv.livecommon.module.utils.imageloader.PLVAbsProgressListener;
import com.easefun.polyv.livecommon.module.utils.imageloader.glide.progress.PLVMyProgressManager;
import com.easefun.polyv.livecommon.module.utils.imageloader.glide.progress.PLVOnProgressListener;
import com.plv.foundationsdk.log.PLVCommonLog;
import java.io.File;
import java.util.concurrent.ExecutionException;

/* loaded from: classes3.dex */
public class PLVGlideImageLoadEngine implements IPLVImageLoadEngine {
    private static final String TAG = "PLVGlideImageLoadEngine";

    @Override // com.easefun.polyv.livecommon.module.utils.imageloader.IPLVImageLoadEngine
    public Drawable getImageAsDrawable(Context context, String url) {
        try {
            return Glide.with(context).load(url).submit(Integer.MIN_VALUE, Integer.MIN_VALUE).get();
        } catch (InterruptedException e2) {
            e2.printStackTrace();
            return null;
        } catch (ExecutionException e3) {
            e3.printStackTrace();
            return null;
        }
    }

    @Override // com.easefun.polyv.livecommon.module.utils.imageloader.IPLVImageLoadEngine
    public void loadImage(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url).into(imageView);
    }

    @Override // com.easefun.polyv.livecommon.module.utils.imageloader.IPLVImageLoadEngine
    public void loadImageNoDiskCache(Context context, String url, @DrawableRes int placeHolder, @DrawableRes int error, ImageView imageView) {
        Glide.with(context).load(url).apply((BaseRequestOptions<?>) new RequestOptions().placeholder(placeHolder).error(error).diskCacheStrategy(DiskCacheStrategy.NONE)).into(imageView);
    }

    @Override // com.easefun.polyv.livecommon.module.utils.imageloader.IPLVImageLoadEngine
    @WorkerThread
    public File saveImageAsFile(Context context, String url) throws ExecutionException, InterruptedException {
        return Glide.with(context).asFile().load(url).submit().get();
    }

    @Override // com.easefun.polyv.livecommon.module.utils.imageloader.IPLVImageLoadEngine
    public void loadImage(Context context, int resId, ImageView imageView) {
        Glide.with(context).load(Integer.valueOf(resId)).into(imageView);
    }

    @Override // com.easefun.polyv.livecommon.module.utils.imageloader.IPLVImageLoadEngine
    public void loadImage(Context context, final String moduleTag, @NonNull final Object urlTag, @DrawableRes int errorRes, @NonNull PLVAbsProgressListener listener, final ImageView view) {
        final String url = listener.getUrl();
        PLVCommonLog.i(TAG, "moduleTag：" + moduleTag + "**urlTag：" + urlTag + "**url：" + url);
        PLVMyProgressManager.addListener(moduleTag, urlTag, new PLVOnProgressListener(listener.getUrl()) { // from class: com.easefun.polyv.livecommon.module.utils.imageloader.glide.PLVGlideImageLoadEngine.1
            @Override // com.easefun.polyv.livecommon.module.utils.imageloader.glide.progress.PLVOnProgressListener
            public void onProgress(String url2, boolean isComplete, int percentage, long bytesRead, long totalBytes) {
                PLVCommonLog.i(PLVGlideImageLoadEngine.TAG, "onProgress url：" + url2 + "**" + isComplete + "**" + percentage + "**" + urlTag);
                if (getTransListener() != null) {
                    getTransListener().onProgress(url2, isComplete, percentage, bytesRead, totalBytes);
                }
            }

            @Override // com.easefun.polyv.livecommon.module.utils.imageloader.glide.progress.PLVOnProgressListener
            public void onStart(String url2) {
                PLVCommonLog.i(PLVGlideImageLoadEngine.TAG, "onStart url：" + url2 + "**" + urlTag);
                if (getTransListener() != null) {
                    getTransListener().onStart(url2);
                }
            }
        }.transListener(listener));
        Glide.with(context).load(listener.getUrl()).apply((BaseRequestOptions<?>) new RequestOptions().fitCenter().error(errorRes).signature(new ObjectKey(urlTag))).listener(new RequestListener<Drawable>() { // from class: com.easefun.polyv.livecommon.module.utils.imageloader.glide.PLVGlideImageLoadEngine.2
            @Override // com.bumptech.glide.request.RequestListener
            public boolean onLoadFailed(@Nullable GlideException e2, Object model, Target<Drawable> target, boolean isFirstResource) {
                StringBuilder sb = new StringBuilder();
                sb.append("onLoadFailed url：");
                sb.append(url);
                sb.append("**");
                sb.append(urlTag);
                sb.append("**");
                sb.append(e2 != null ? e2.getMessage() : "");
                PLVCommonLog.i(PLVGlideImageLoadEngine.TAG, sb.toString());
                PLVOnProgressListener progressListener = PLVMyProgressManager.getProgressListener(moduleTag, urlTag);
                if (progressListener instanceof PLVAbsProgressListener) {
                    ((PLVAbsProgressListener) progressListener).onFailed(e2, model);
                }
                PLVMyProgressManager.removeListener(moduleTag, urlTag);
                return false;
            }

            @Override // com.bumptech.glide.request.RequestListener
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                PLVCommonLog.i(PLVGlideImageLoadEngine.TAG, "onResourceReady1 url：" + url + "**" + urlTag + "**" + view.getWidth() + "**" + view.getHeight());
                PLVOnProgressListener progressListener = PLVMyProgressManager.getProgressListener(moduleTag, urlTag);
                if (progressListener == null) {
                    return false;
                }
                progressListener.onProgress(progressListener.getUrl(), true, 100, 0L, 0L);
                return false;
            }
        }).into((RequestBuilder<Drawable>) new ViewTarget<ImageView, Drawable>(view) { // from class: com.easefun.polyv.livecommon.module.utils.imageloader.glide.PLVGlideImageLoadEngine.3
            @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.manager.LifecycleListener
            public void onDestroy() {
                PLVCommonLog.i(PLVGlideImageLoadEngine.TAG, "onDestroy url：" + url + "**" + urlTag);
                PLVMyProgressManager.removeListener(moduleTag, urlTag);
            }

            @Override // com.bumptech.glide.request.target.Target
            public /* bridge */ /* synthetic */ void onResourceReady(@NonNull Object resource, @Nullable Transition transition) {
                onResourceReady((Drawable) resource, (Transition<? super Drawable>) transition);
            }

            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                PLVCommonLog.i(PLVGlideImageLoadEngine.TAG, "onResourceReady2 url：" + url + "**" + urlTag + "**" + resource.getIntrinsicWidth() + "**" + resource.getIntrinsicHeight());
                PLVOnProgressListener progressListener = PLVMyProgressManager.getProgressListener(moduleTag, urlTag);
                if (progressListener instanceof PLVAbsProgressListener) {
                    ((PLVAbsProgressListener) progressListener).onResourceReady(resource);
                }
                PLVMyProgressManager.removeListener(moduleTag, urlTag);
                if (resource instanceof GifDrawable) {
                    ((GifDrawable) resource).start();
                }
            }
        }.waitForLayout());
    }

    @Override // com.easefun.polyv.livecommon.module.utils.imageloader.IPLVImageLoadEngine
    public File saveImageAsFile(Context context, String url, Object urlTag) throws ExecutionException, InterruptedException {
        return Glide.with(context).asFile().load(url).apply((BaseRequestOptions<?>) new RequestOptions().signature(new ObjectKey(urlTag))).submit().get();
    }

    @Override // com.easefun.polyv.livecommon.module.utils.imageloader.IPLVImageLoadEngine
    public void loadImage(Context context, String url, int placeHolder, int error, ImageView imageView) {
        Glide.with(context).load(url).apply((BaseRequestOptions<?>) new RequestOptions().placeholder(placeHolder).error(error)).into(imageView);
    }

    @Override // com.easefun.polyv.livecommon.module.utils.imageloader.IPLVImageLoadEngine
    public void loadImage(Context context, String url, int placeHolder, int error, ImageView imageView, int radius) {
        Glide.with(context).load(url).apply((BaseRequestOptions<?>) new RequestOptions().placeholder(placeHolder).error(error).transform(new RoundedCorners(radius))).into(imageView);
    }
}
