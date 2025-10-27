package com.psychiatrygarden.widget.glideUtil.progress;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import androidx.annotation.CheckResult;
import androidx.annotation.DrawableRes;
import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.TransitionOptions;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.Option;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestListener;
import java.io.File;
import java.net.URL;
import java.util.List;

/* loaded from: classes6.dex */
public class GlideRequest<TranscodeType> extends RequestBuilder<TranscodeType> implements Cloneable {
    public GlideRequest(@NonNull Class<TranscodeType> transcodeClass, @NonNull RequestBuilder<?> other) {
        super(transcodeClass, other);
    }

    @Override // com.bumptech.glide.RequestBuilder, com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public /* bridge */ /* synthetic */ RequestBuilder apply(@NonNull BaseRequestOptions options) {
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
    @CheckResult
    @Deprecated
    public /* bridge */ /* synthetic */ BaseRequestOptions transforms(@NonNull Transformation[] transformations) {
        return transforms((Transformation<Bitmap>[]) transformations);
    }

    public GlideRequest(@NonNull Glide glide, @NonNull RequestManager requestManager, @NonNull Class<TranscodeType> transcodeClass, @NonNull Context context) {
        super(glide, requestManager, transcodeClass, context);
    }

    @Override // com.bumptech.glide.RequestBuilder
    @NonNull
    @CheckResult
    public GlideRequest<TranscodeType> addListener(@Nullable RequestListener<TranscodeType> listener) {
        return (GlideRequest) super.addListener((RequestListener) listener);
    }

    @Override // com.bumptech.glide.RequestBuilder, com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public /* bridge */ /* synthetic */ BaseRequestOptions apply(@NonNull BaseRequestOptions options) {
        return apply((BaseRequestOptions<?>) options);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    public GlideRequest<TranscodeType> autoClone() {
        return (GlideRequest) super.autoClone();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideRequest<TranscodeType> centerCrop() {
        return (GlideRequest) super.centerCrop();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideRequest<TranscodeType> centerInside() {
        return (GlideRequest) super.centerInside();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideRequest<TranscodeType> circleCrop() {
        return (GlideRequest) super.circleCrop();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideRequest<TranscodeType> decode(@NonNull Class<?> clazz) {
        return (GlideRequest) super.decode(clazz);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideRequest<TranscodeType> disallowHardwareConfig() {
        return (GlideRequest) super.disallowHardwareConfig();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideRequest<TranscodeType> diskCacheStrategy(@NonNull DiskCacheStrategy strategy) {
        return (GlideRequest) super.diskCacheStrategy(strategy);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideRequest<TranscodeType> dontAnimate() {
        return (GlideRequest) super.dontAnimate();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideRequest<TranscodeType> dontTransform() {
        return (GlideRequest) super.dontTransform();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideRequest<TranscodeType> downsample(@NonNull DownsampleStrategy strategy) {
        return (GlideRequest) super.downsample(strategy);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideRequest<TranscodeType> encodeFormat(@NonNull Bitmap.CompressFormat format) {
        return (GlideRequest) super.encodeFormat(format);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideRequest<TranscodeType> encodeQuality(@IntRange(from = 0, to = 100) int value) {
        return (GlideRequest) super.encodeQuality(value);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideRequest<TranscodeType> fitCenter() {
        return (GlideRequest) super.fitCenter();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideRequest<TranscodeType> format(@NonNull DecodeFormat format) {
        return (GlideRequest) super.format(format);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideRequest<TranscodeType> frame(@IntRange(from = 0) long value) {
        return (GlideRequest) super.frame(value);
    }

    @Override // com.bumptech.glide.RequestBuilder
    @NonNull
    @CheckResult
    public GlideRequest<File> getDownloadOnlyRequest() {
        return new GlideRequest(File.class, this).apply((BaseRequestOptions<?>) RequestBuilder.DOWNLOAD_ONLY_OPTIONS);
    }

    @Override // com.bumptech.glide.RequestBuilder
    @NonNull
    @CheckResult
    public GlideRequest<TranscodeType> listener(@Nullable RequestListener<TranscodeType> listener) {
        return (GlideRequest) super.listener((RequestListener) listener);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    public GlideRequest<TranscodeType> lock() {
        return (GlideRequest) super.lock();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideRequest<TranscodeType> onlyRetrieveFromCache(boolean flag) {
        return (GlideRequest) super.onlyRetrieveFromCache(flag);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideRequest<TranscodeType> optionalCenterCrop() {
        return (GlideRequest) super.optionalCenterCrop();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideRequest<TranscodeType> optionalCenterInside() {
        return (GlideRequest) super.optionalCenterInside();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideRequest<TranscodeType> optionalCircleCrop() {
        return (GlideRequest) super.optionalCircleCrop();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideRequest<TranscodeType> optionalFitCenter() {
        return (GlideRequest) super.optionalFitCenter();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideRequest<TranscodeType> priority(@NonNull Priority priority) {
        return (GlideRequest) super.priority(priority);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public <Y> GlideRequest<TranscodeType> set(@NonNull Option<Y> option, @NonNull Y y2) {
        return (GlideRequest) super.set((Option<Option<Y>>) option, (Option<Y>) y2);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideRequest<TranscodeType> signature(@NonNull Key key) {
        return (GlideRequest) super.signature(key);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideRequest<TranscodeType> sizeMultiplier(@FloatRange(from = 0.0d, to = 1.0d) float value) {
        return (GlideRequest) super.sizeMultiplier(value);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideRequest<TranscodeType> skipMemoryCache(boolean skip) {
        return (GlideRequest) super.skipMemoryCache(skip);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideRequest<TranscodeType> theme(@Nullable Resources.Theme theme) {
        return (GlideRequest) super.theme(theme);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideRequest<TranscodeType> timeout(@IntRange(from = 0) int value) {
        return (GlideRequest) super.timeout(value);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @Deprecated
    @CheckResult
    public GlideRequest<TranscodeType> transforms(@NonNull Transformation<Bitmap>... transformations) {
        return (GlideRequest) super.transforms(transformations);
    }

    @Override // com.bumptech.glide.RequestBuilder
    @NonNull
    @CheckResult
    public GlideRequest<TranscodeType> transition(@NonNull TransitionOptions<?, ? super TranscodeType> options) {
        return (GlideRequest) super.transition((TransitionOptions) options);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideRequest<TranscodeType> useAnimationPool(boolean flag) {
        return (GlideRequest) super.useAnimationPool(flag);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideRequest<TranscodeType> useUnlimitedSourceGeneratorsPool(boolean flag) {
        return (GlideRequest) super.useUnlimitedSourceGeneratorsPool(flag);
    }

    @Override // com.bumptech.glide.RequestBuilder, com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideRequest<TranscodeType> apply(@NonNull BaseRequestOptions<?> options) {
        return (GlideRequest) super.apply(options);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideRequest<TranscodeType> fallback(@Nullable Drawable drawable) {
        return (GlideRequest) super.fallback(drawable);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideRequest<TranscodeType> optionalTransform(@NonNull Transformation<Bitmap> transformation) {
        return (GlideRequest) super.optionalTransform(transformation);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideRequest<TranscodeType> override(int width, int height) {
        return (GlideRequest) super.override(width, height);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideRequest<TranscodeType> placeholder(@Nullable Drawable drawable) {
        return (GlideRequest) super.placeholder(drawable);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public /* bridge */ /* synthetic */ BaseRequestOptions transform(@NonNull Transformation[] transformations) {
        return transform((Transformation<Bitmap>[]) transformations);
    }

    @Override // com.bumptech.glide.RequestBuilder, com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    /* renamed from: clone */
    public GlideRequest<TranscodeType> mo69clone() {
        return (GlideRequest) super.mo69clone();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideRequest<TranscodeType> fallback(@DrawableRes int id) {
        return (GlideRequest) super.fallback(id);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public <Y> GlideRequest<TranscodeType> optionalTransform(@NonNull Class<Y> clazz, @NonNull Transformation<Y> transformation) {
        return (GlideRequest) super.optionalTransform((Class) clazz, (Transformation) transformation);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideRequest<TranscodeType> override(int size) {
        return (GlideRequest) super.override(size);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideRequest<TranscodeType> placeholder(@DrawableRes int id) {
        return (GlideRequest) super.placeholder(id);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideRequest<TranscodeType> transform(@NonNull Transformation<Bitmap> transformation) {
        return (GlideRequest) super.transform(transformation);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideRequest<TranscodeType> error(@Nullable Drawable drawable) {
        return (GlideRequest) super.error(drawable);
    }

    @Override // com.bumptech.glide.RequestBuilder
    @NonNull
    @CheckResult
    public GlideRequest<TranscodeType> thumbnail(@Nullable RequestBuilder<TranscodeType> builder) {
        return (GlideRequest) super.thumbnail((RequestBuilder) builder);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideRequest<TranscodeType> transform(@NonNull Transformation<Bitmap>... transformations) {
        return (GlideRequest) super.transform(transformations);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public GlideRequest<TranscodeType> error(@DrawableRes int id) {
        return (GlideRequest) super.error(id);
    }

    @Override // com.bumptech.glide.RequestBuilder
    @NonNull
    @SafeVarargs
    @CheckResult
    public final GlideRequest<TranscodeType> thumbnail(@Nullable RequestBuilder<TranscodeType>... builders) {
        return (GlideRequest) super.thumbnail((RequestBuilder[]) builders);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public <Y> GlideRequest<TranscodeType> transform(@NonNull Class<Y> clazz, @NonNull Transformation<Y> transformation) {
        return (GlideRequest) super.transform((Class) clazz, (Transformation) transformation);
    }

    @Override // com.bumptech.glide.RequestBuilder
    @NonNull
    public GlideRequest<TranscodeType> error(@Nullable RequestBuilder<TranscodeType> builder) {
        return (GlideRequest) super.error((RequestBuilder) builder);
    }

    @Override // com.bumptech.glide.RequestBuilder
    @NonNull
    @CheckResult
    public GlideRequest<TranscodeType> thumbnail(@Nullable List<RequestBuilder<TranscodeType>> list) {
        return (GlideRequest) super.thumbnail((List) list);
    }

    @Override // com.bumptech.glide.RequestBuilder
    @NonNull
    @CheckResult
    public GlideRequest<TranscodeType> error(Object o2) {
        return (GlideRequest) super.error(o2);
    }

    @Override // com.bumptech.glide.RequestBuilder
    @NonNull
    @CheckResult
    public GlideRequest<TranscodeType> thumbnail(float sizeMultiplier) {
        return (GlideRequest) super.thumbnail(sizeMultiplier);
    }

    @Override // com.bumptech.glide.RequestBuilder, com.bumptech.glide.ModelTypes
    @NonNull
    @CheckResult
    public GlideRequest<TranscodeType> load(@Nullable Object o2) {
        return (GlideRequest) super.load(o2);
    }

    @Override // com.bumptech.glide.RequestBuilder, com.bumptech.glide.ModelTypes
    @NonNull
    @CheckResult
    public GlideRequest<TranscodeType> load(@Nullable Bitmap bitmap) {
        return (GlideRequest) super.load(bitmap);
    }

    @Override // com.bumptech.glide.RequestBuilder, com.bumptech.glide.ModelTypes
    @NonNull
    @CheckResult
    public GlideRequest<TranscodeType> load(@Nullable Drawable drawable) {
        return (GlideRequest) super.load(drawable);
    }

    @Override // com.bumptech.glide.RequestBuilder, com.bumptech.glide.ModelTypes
    @NonNull
    @CheckResult
    public GlideRequest<TranscodeType> load(@Nullable String string) {
        return (GlideRequest) super.load(string);
    }

    @Override // com.bumptech.glide.RequestBuilder, com.bumptech.glide.ModelTypes
    @NonNull
    @CheckResult
    public GlideRequest<TranscodeType> load(@Nullable Uri uri) {
        return (GlideRequest) super.load(uri);
    }

    @Override // com.bumptech.glide.RequestBuilder, com.bumptech.glide.ModelTypes
    @NonNull
    @CheckResult
    public GlideRequest<TranscodeType> load(@Nullable File file) {
        return (GlideRequest) super.load(file);
    }

    @Override // com.bumptech.glide.RequestBuilder, com.bumptech.glide.ModelTypes
    @NonNull
    @CheckResult
    public GlideRequest<TranscodeType> load(@Nullable @DrawableRes @RawRes Integer id) {
        return (GlideRequest) super.load(id);
    }

    @Override // com.bumptech.glide.RequestBuilder, com.bumptech.glide.ModelTypes
    @CheckResult
    @Deprecated
    public GlideRequest<TranscodeType> load(@Nullable URL url) {
        return (GlideRequest) super.load(url);
    }

    @Override // com.bumptech.glide.RequestBuilder, com.bumptech.glide.ModelTypes
    @NonNull
    @CheckResult
    public GlideRequest<TranscodeType> load(@Nullable byte[] bytes) {
        return (GlideRequest) super.load(bytes);
    }
}
