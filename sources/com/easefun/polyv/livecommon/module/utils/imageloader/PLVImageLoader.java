package com.easefun.polyv.livecommon.module.utils.imageloader;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import com.easefun.polyv.livecommon.module.utils.imageloader.glide.PLVGlideImageLoadEngine;
import java.io.File;
import java.util.concurrent.ExecutionException;

/* loaded from: classes3.dex */
public class PLVImageLoader {
    private static volatile PLVImageLoader instance;
    private IPLVImageLoadEngine loadEngine = new PLVGlideImageLoadEngine();

    private PLVImageLoader() {
    }

    public static PLVImageLoader getInstance() {
        if (instance == null) {
            synchronized (PLVImageLoader.class) {
                if (instance == null) {
                    instance = new PLVImageLoader();
                }
            }
        }
        return instance;
    }

    @WorkerThread
    public Drawable getImageAsDrawable(Context context, String url) {
        return this.loadEngine.getImageAsDrawable(context, url);
    }

    public void loadImage(String url, ImageView imageView) {
        this.loadEngine.loadImage(imageView.getContext(), url, imageView);
    }

    public void loadImageNoDiskCache(Context context, String url, @DrawableRes int placeHolder, @DrawableRes int error, ImageView imageView) {
        this.loadEngine.loadImageNoDiskCache(context, url, placeHolder, error, imageView);
    }

    @WorkerThread
    public File saveImageAsFile(Context context, String url) throws ExecutionException, InterruptedException {
        return this.loadEngine.saveImageAsFile(context, url);
    }

    public void loadImage(Context context, String url, ImageView imageView) {
        this.loadEngine.loadImage(context, url, imageView);
    }

    @WorkerThread
    public File saveImageAsFile(Context context, String url, Object urlTag) throws ExecutionException, InterruptedException {
        return this.loadEngine.saveImageAsFile(context, url, urlTag);
    }

    public void loadImage(Context context, @DrawableRes int resId, ImageView imageView) {
        this.loadEngine.loadImage(context, resId, imageView);
    }

    public void loadImage(Context context, final String moduleTag, @NonNull final Object urlTag, @DrawableRes int errorRes, @NonNull final PLVAbsProgressListener listener, final ImageView view) {
        this.loadEngine.loadImage(context, moduleTag, urlTag, errorRes, listener, view);
    }

    public void loadImage(Context context, String url, @DrawableRes int placeHolder, @DrawableRes int error, ImageView imageView) {
        this.loadEngine.loadImage(context, url, placeHolder, error, imageView);
    }

    public void loadImage(Context context, String url, @DrawableRes int placeHolder, @DrawableRes int error, ImageView imageView, int radius) {
        this.loadEngine.loadImage(context, url, placeHolder, error, imageView, radius);
    }
}
