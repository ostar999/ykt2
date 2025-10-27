package com.aliyun.svideo.common.utils.image;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* loaded from: classes2.dex */
public abstract class AbstractImageLoader {
    public abstract void clear(@NonNull Context context, @NonNull ImageView imageView);

    public abstract <T> void into(@NonNull View view, @NonNull AbstractImageLoaderTarget<T> abstractImageLoaderTarget);

    public abstract void into(@NonNull ImageView imageView);

    public abstract <R> AbstractImageLoader listener(@NonNull ImageLoaderRequestListener<R> imageLoaderRequestListener);

    public abstract AbstractImageLoader loadImage(@NonNull Context context, @NonNull int i2);

    public abstract AbstractImageLoader loadImage(@NonNull Context context, @DrawableRes int i2, @Nullable ImageLoaderOptions imageLoaderOptions);

    public abstract AbstractImageLoader loadImage(@NonNull Context context, @NonNull String str);

    public abstract AbstractImageLoader loadImage(@NonNull Context context, @NonNull String str, @NonNull ImageLoaderOptions imageLoaderOptions);
}
