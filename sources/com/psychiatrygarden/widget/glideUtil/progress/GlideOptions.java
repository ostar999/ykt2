package com.psychiatrygarden.widget.glideUtil.progress;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import androidx.annotation.CheckResult;
import androidx.annotation.DrawableRes;
import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.Option;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;

/* loaded from: classes6.dex */
public final class GlideOptions extends RequestOptions implements Cloneable {
    private static GlideOptions centerCropTransform2;
    private static GlideOptions centerInsideTransform1;
    private static GlideOptions circleCropTransform3;
    private static GlideOptions fitCenterTransform0;
    private static GlideOptions noAnimation5;
    private static GlideOptions noTransformation4;

    @NonNull
    @CheckResult
    public static GlideOptions bitmapTransform(@NonNull Transformation<Bitmap> transformation) {
        return new GlideOptions().transform(transformation);
    }

    @NonNull
    @CheckResult
    public static GlideOptions centerCropTransform() {
        if (centerCropTransform2 == null) {
            centerCropTransform2 = new GlideOptions().centerCrop().autoClone();
        }
        return centerCropTransform2;
    }

    @NonNull
    @CheckResult
    public static GlideOptions centerInsideTransform() {
        if (centerInsideTransform1 == null) {
            centerInsideTransform1 = new GlideOptions().centerInside().autoClone();
        }
        return centerInsideTransform1;
    }

    @NonNull
    @CheckResult
    public static GlideOptions circleCropTransform() {
        if (circleCropTransform3 == null) {
            circleCropTransform3 = new GlideOptions().circleCrop().autoClone();
        }
        return circleCropTransform3;
    }

    @NonNull
    @CheckResult
    public static GlideOptions decodeTypeOf(@NonNull Class<?> clazz) {
        return new GlideOptions().decode(clazz);
    }

    @NonNull
    @CheckResult
    public static GlideOptions diskCacheStrategyOf(@NonNull DiskCacheStrategy strategy) {
        return new GlideOptions().diskCacheStrategy(strategy);
    }

    @NonNull
    @CheckResult
    public static GlideOptions downsampleOf(@NonNull DownsampleStrategy strategy) {
        return new GlideOptions().downsample(strategy);
    }

    @NonNull
    @CheckResult
    public static GlideOptions encodeFormatOf(@NonNull Bitmap.CompressFormat format) {
        return new GlideOptions().encodeFormat(format);
    }

    @NonNull
    @CheckResult
    public static GlideOptions encodeQualityOf(@IntRange(from = 0, to = 100) int value) {
        return new GlideOptions().encodeQuality(value);
    }

    @NonNull
    @CheckResult
    public static GlideOptions errorOf(@Nullable Drawable drawable) {
        return new GlideOptions().error(drawable);
    }

    @NonNull
    @CheckResult
    public static GlideOptions fitCenterTransform() {
        if (fitCenterTransform0 == null) {
            fitCenterTransform0 = new GlideOptions().fitCenter().autoClone();
        }
        return fitCenterTransform0;
    }

    @NonNull
    @CheckResult
    public static GlideOptions formatOf(@NonNull DecodeFormat format) {
        return new GlideOptions().format(format);
    }

    @NonNull
    @CheckResult
    public static GlideOptions frameOf(@IntRange(from = 0) long value) {
        return new GlideOptions().frame(value);
    }

    @NonNull
    @CheckResult
    public static GlideOptions noAnimation() {
        if (noAnimation5 == null) {
            noAnimation5 = new GlideOptions().dontAnimate().autoClone();
        }
        return noAnimation5;
    }

    @NonNull
    @CheckResult
    public static GlideOptions noTransformation() {
        if (noTransformation4 == null) {
            noTransformation4 = new GlideOptions().dontTransform().autoClone();
        }
        return noTransformation4;
    }

    @NonNull
    @CheckResult
    public static <T> GlideOptions option(@NonNull Option<T> option, @NonNull T t2) {
        return new GlideOptions().set((Option<Option<T>>) option, (Option<T>) t2);
    }

    @NonNull
    @CheckResult
    public static GlideOptions overrideOf(int width, int height) {
        return new GlideOptions().override(width, height);
    }

    @NonNull
    @CheckResult
    public static GlideOptions placeholderOf(@Nullable Drawable drawable) {
        return new GlideOptions().placeholder(drawable);
    }

    @NonNull
    @CheckResult
    public static GlideOptions priorityOf(@NonNull Priority priority) {
        return new GlideOptions().priority(priority);
    }

    @NonNull
    @CheckResult
    public static GlideOptions signatureOf(@NonNull Key key) {
        return new GlideOptions().signature(key);
    }

    @NonNull
    @CheckResult
    public static GlideOptions sizeMultiplierOf(@FloatRange(from = 0.0d, to = 1.0d) float value) {
        return new GlideOptions().sizeMultiplier(value);
    }

    @NonNull
    @CheckResult
    public static GlideOptions skipMemoryCacheOf(boolean skipMemoryCache) {
        return new GlideOptions().skipMemoryCache(skipMemoryCache);
    }

    @NonNull
    @CheckResult
    public static GlideOptions timeoutOf(@IntRange(from = 0) int value) {
        return new GlideOptions().timeout(value);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public /* bridge */ /* synthetic */ BaseRequestOptions apply(@NonNull BaseRequestOptions options) {
        return apply((BaseRequestOptions<?>) options);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public /* bridge */ /* synthetic */ BaseRequestOptions decode(@NonNull Class clazz) {
        return decode((Class<?>) clazz);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public /* bridge */ /* synthetic */ BaseRequestOptions optionalTransform(@NonNull Transformation transformation) {
        return optionalTransform((Transformation<Bitmap>) transformation);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public /* bridge */ /* synthetic */ BaseRequestOptions set(@NonNull Option option, @NonNull Object y2) {
        return set((Option<Option>) option, (Option) y2);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public /* bridge */ /* synthetic */ BaseRequestOptions transform(@NonNull Transformation transformation) {
        return transform((Transformation<Bitmap>) transformation);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @Deprecated
    @SafeVarargs
    @CheckResult
    public /* bridge */ /* synthetic */ BaseRequestOptions transforms(@NonNull Transformation[] transformations) {
        return transforms((Transformation<Bitmap>[]) transformations);
    }

    @NonNull
    @CheckResult
    public static GlideOptions errorOf(@DrawableRes int id) {
        return new GlideOptions().error(id);
    }

    @NonNull
    @CheckResult
    public static GlideOptions overrideOf(int size) {
        return new GlideOptions().override(size);
    }

    @NonNull
    @CheckResult
    public static GlideOptions placeholderOf(@DrawableRes int id) {
        return new GlideOptions().placeholder(id);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideOptions apply(@NonNull BaseRequestOptions<?> options) {
        return (GlideOptions) super.apply(options);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    public GlideOptions autoClone() {
        return (GlideOptions) super.autoClone();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideOptions centerCrop() {
        return (GlideOptions) super.centerCrop();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideOptions centerInside() {
        return (GlideOptions) super.centerInside();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideOptions circleCrop() {
        return (GlideOptions) super.circleCrop();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideOptions decode(@NonNull Class<?> clazz) {
        return (GlideOptions) super.decode(clazz);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideOptions disallowHardwareConfig() {
        return (GlideOptions) super.disallowHardwareConfig();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideOptions diskCacheStrategy(@NonNull DiskCacheStrategy strategy) {
        return (GlideOptions) super.diskCacheStrategy(strategy);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideOptions dontAnimate() {
        return (GlideOptions) super.dontAnimate();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideOptions dontTransform() {
        return (GlideOptions) super.dontTransform();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideOptions downsample(@NonNull DownsampleStrategy strategy) {
        return (GlideOptions) super.downsample(strategy);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideOptions encodeFormat(@NonNull Bitmap.CompressFormat format) {
        return (GlideOptions) super.encodeFormat(format);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideOptions encodeQuality(@IntRange(from = 0, to = 100) int value) {
        return (GlideOptions) super.encodeQuality(value);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideOptions fitCenter() {
        return (GlideOptions) super.fitCenter();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideOptions format(@NonNull DecodeFormat format) {
        return (GlideOptions) super.format(format);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideOptions frame(@IntRange(from = 0) long value) {
        return (GlideOptions) super.frame(value);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    public GlideOptions lock() {
        return (GlideOptions) super.lock();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideOptions onlyRetrieveFromCache(boolean flag) {
        return (GlideOptions) super.onlyRetrieveFromCache(flag);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideOptions optionalCenterCrop() {
        return (GlideOptions) super.optionalCenterCrop();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideOptions optionalCenterInside() {
        return (GlideOptions) super.optionalCenterInside();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideOptions optionalCircleCrop() {
        return (GlideOptions) super.optionalCircleCrop();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideOptions optionalFitCenter() {
        return (GlideOptions) super.optionalFitCenter();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideOptions priority(@NonNull Priority priority) {
        return (GlideOptions) super.priority(priority);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public <Y> GlideOptions set(@NonNull Option<Y> option, @NonNull Y y2) {
        return (GlideOptions) super.set((Option<Option<Y>>) option, (Option<Y>) y2);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideOptions signature(@NonNull Key key) {
        return (GlideOptions) super.signature(key);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideOptions sizeMultiplier(@FloatRange(from = 0.0d, to = 1.0d) float value) {
        return (GlideOptions) super.sizeMultiplier(value);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideOptions skipMemoryCache(boolean skip) {
        return (GlideOptions) super.skipMemoryCache(skip);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideOptions theme(@Nullable Resources.Theme theme) {
        return (GlideOptions) super.theme(theme);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideOptions timeout(@IntRange(from = 0) int value) {
        return (GlideOptions) super.timeout(value);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @Deprecated
    @SafeVarargs
    @CheckResult
    public final GlideOptions transforms(@NonNull Transformation<Bitmap>... transformations) {
        return (GlideOptions) super.transforms(transformations);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideOptions useAnimationPool(boolean flag) {
        return (GlideOptions) super.useAnimationPool(flag);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideOptions useUnlimitedSourceGeneratorsPool(boolean flag) {
        return (GlideOptions) super.useUnlimitedSourceGeneratorsPool(flag);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    /* renamed from: clone */
    public GlideOptions mo69clone() {
        return (GlideOptions) super.mo69clone();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideOptions error(@Nullable Drawable drawable) {
        return (GlideOptions) super.error(drawable);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideOptions fallback(@Nullable Drawable drawable) {
        return (GlideOptions) super.fallback(drawable);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideOptions optionalTransform(@NonNull Transformation<Bitmap> transformation) {
        return (GlideOptions) super.optionalTransform(transformation);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideOptions override(int width, int height) {
        return (GlideOptions) super.override(width, height);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideOptions placeholder(@Nullable Drawable drawable) {
        return (GlideOptions) super.placeholder(drawable);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @SafeVarargs
    @CheckResult
    public /* bridge */ /* synthetic */ BaseRequestOptions transform(@NonNull Transformation[] transformations) {
        return transform((Transformation<Bitmap>[]) transformations);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideOptions error(@DrawableRes int id) {
        return (GlideOptions) super.error(id);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideOptions fallback(@DrawableRes int id) {
        return (GlideOptions) super.fallback(id);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public <Y> GlideOptions optionalTransform(@NonNull Class<Y> clazz, @NonNull Transformation<Y> transformation) {
        return (GlideOptions) super.optionalTransform((Class) clazz, (Transformation) transformation);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideOptions override(int size) {
        return (GlideOptions) super.override(size);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideOptions placeholder(@DrawableRes int id) {
        return (GlideOptions) super.placeholder(id);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideOptions transform(@NonNull Transformation<Bitmap> transformation) {
        return (GlideOptions) super.transform(transformation);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @SafeVarargs
    @CheckResult
    public final GlideOptions transform(@NonNull Transformation<Bitmap>... transformations) {
        return (GlideOptions) super.transform(transformations);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public <Y> GlideOptions transform(@NonNull Class<Y> clazz, @NonNull Transformation<Y> transformation) {
        return (GlideOptions) super.transform((Class) clazz, (Transformation) transformation);
    }
}
