package com.easefun.polyv.livecommon.module.utils.imageloader;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import java.io.File;
import java.util.concurrent.ExecutionException;

/* loaded from: classes3.dex */
public interface IPLVImageLoadEngine {
    Drawable getImageAsDrawable(Context context, String url);

    void loadImage(Context context, @DrawableRes int resId, ImageView imageView);

    void loadImage(Context context, String url, @DrawableRes int placeHolder, @DrawableRes int error, ImageView imageView);

    void loadImage(Context context, String url, @DrawableRes int placeHolder, @DrawableRes int error, ImageView imageView, int radius);

    void loadImage(Context context, String url, ImageView imageView);

    void loadImage(Context context, final String moduleTag, final Object urlTag, @DrawableRes int errorRes, @NonNull final PLVAbsProgressListener listener, final ImageView view);

    void loadImageNoDiskCache(Context context, String url, @DrawableRes int placeHolder, @DrawableRes int error, ImageView imageView);

    @WorkerThread
    File saveImageAsFile(Context context, String url) throws ExecutionException, InterruptedException;

    @WorkerThread
    File saveImageAsFile(Context context, String url, Object urlTag) throws ExecutionException, InterruptedException;
}
