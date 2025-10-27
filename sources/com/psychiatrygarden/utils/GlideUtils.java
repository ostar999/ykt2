package com.psychiatrygarden.utils;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.Headers;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.BaseRequestOptions;
import com.psychiatrygarden.ProjectApp;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class GlideUtils {
    public static GlideUrl generateUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        return new GlideUrl(url, url.startsWith("http") ? new LazyHeaders.Builder().addHeader("Referer", "android.yikaobang.com.cn").build() : Headers.DEFAULT);
    }

    public static void loadImage(Context mContext, @NonNull String path, @NonNull ImageView mImageView) {
        Glide.with(mContext).load((Object) generateUrl(path)).placeholder(new ColorDrawable(ContextCompat.getColor(mImageView.getContext(), SkinManager.getCurrentSkinType(ProjectApp.instance()) == 0 ? R.color.fourth_line_backgroup_color : R.color.bg_backgroud_night))).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).skipMemoryCache(false).into(mImageView);
    }

    public static void loadImageCache(Context mContext, @NonNull String path, @NonNull ImageView mImageView) {
        Glide.with(mContext).load((Object) generateUrl(path)).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).skipMemoryCache(false).skipMemoryCache(true).into(mImageView);
    }

    public static void loadImageDef(Context mContext, @NonNull String path, @NonNull ImageView mImageView) {
        Glide.with(mContext).load((Object) generateUrl(path)).placeholder(R.mipmap.ic_order_default).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).skipMemoryCache(false).into(mImageView);
    }

    public static void loadImageLoding(Context mContext, @NonNull String path, @NonNull ImageView mImageView, int lodingImage, int errorImageView) {
        Glide.with(mContext).load((Object) generateUrl(path)).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).skipMemoryCache(false).placeholder(lodingImage).error(errorImageView).into(mImageView);
    }

    public static void loadImageLodingSize(Context mContext, @NonNull String path, int width, int height, @NonNull ImageView mImageView, int lodingImage, int errorImageView) {
        Glide.with(mContext).load((Object) generateUrl(path)).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).skipMemoryCache(false).override(width, height).placeholder(lodingImage).error(errorImageView).into(mImageView);
    }

    public static void loadImagePriority(Context mContext, @NonNull String path, @NonNull ImageView mImageView, @NonNull Priority priority) {
        Glide.with(mContext).load((Object) generateUrl(path)).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).skipMemoryCache(false).priority(priority).into(mImageView);
    }

    public static void loadImageSize(Context mContext, @NonNull String path, int width, int height, @NonNull ImageView mImageView) {
        Glide.with(mContext).load((Object) generateUrl(path)).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).skipMemoryCache(false).override(width, height).into(mImageView);
    }

    public static void loadImageWithPlaceHolder(Context mContext, @NonNull String path, @NonNull ImageView mImageView, int placeHolderResId) {
        Glide.with(mContext).load((Object) generateUrl(path)).placeholder(placeHolderResId).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).skipMemoryCache(false).into(mImageView);
    }

    public static void loadImage(Context mContext, @NonNull String path, @NonNull ImageView mImageView, int placeHolderResId, int errorResId) {
        Glide.with(mContext).load((Object) generateUrl(path)).placeholder(new ColorDrawable(ContextCompat.getColor(mImageView.getContext(), SkinManager.getCurrentSkinType(ProjectApp.instance()) == 0 ? R.color.fourth_line_backgroup_color : R.color.bg_backgroud_night))).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).skipMemoryCache(false).error(errorResId).into(mImageView);
    }

    public static void loadImage(Context mContext, @NonNull String path, @NonNull ImageView mImageView, @NonNull BaseRequestOptions<?> requestOptions) {
        Glide.with(mContext).load((Object) generateUrl(path)).apply(requestOptions).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).skipMemoryCache(false).into(mImageView);
    }
}
