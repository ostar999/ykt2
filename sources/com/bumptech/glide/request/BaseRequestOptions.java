package com.bumptech.glide.request;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.Option;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.stream.HttpGlideUrlLoader;
import com.bumptech.glide.load.resource.bitmap.BitmapEncoder;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy;
import com.bumptech.glide.load.resource.bitmap.Downsampler;
import com.bumptech.glide.load.resource.bitmap.DrawableTransformation;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.bitmap.VideoDecoder;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.load.resource.gif.GifDrawableTransformation;
import com.bumptech.glide.load.resource.gif.GifOptions;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.signature.EmptySignature;
import com.bumptech.glide.util.CachedHashCodeArrayMap;
import com.bumptech.glide.util.Preconditions;
import com.bumptech.glide.util.Util;
import java.util.Map;

/* loaded from: classes2.dex */
public abstract class BaseRequestOptions<T extends BaseRequestOptions<T>> implements Cloneable {
    private static final int DISK_CACHE_STRATEGY = 4;
    private static final int ERROR_ID = 32;
    private static final int ERROR_PLACEHOLDER = 16;
    private static final int FALLBACK = 8192;
    private static final int FALLBACK_ID = 16384;
    private static final int IS_CACHEABLE = 256;
    private static final int ONLY_RETRIEVE_FROM_CACHE = 524288;
    private static final int OVERRIDE = 512;
    private static final int PLACEHOLDER = 64;
    private static final int PLACEHOLDER_ID = 128;
    private static final int PRIORITY = 8;
    private static final int RESOURCE_CLASS = 4096;
    private static final int SIGNATURE = 1024;
    private static final int SIZE_MULTIPLIER = 2;
    private static final int THEME = 32768;
    private static final int TRANSFORMATION = 2048;
    private static final int TRANSFORMATION_ALLOWED = 65536;
    private static final int TRANSFORMATION_REQUIRED = 131072;
    private static final int UNSET = -1;
    private static final int USE_ANIMATION_POOL = 1048576;
    private static final int USE_UNLIMITED_SOURCE_GENERATORS_POOL = 262144;
    private int errorId;

    @Nullable
    private Drawable errorPlaceholder;

    @Nullable
    private Drawable fallbackDrawable;
    private int fallbackId;
    private int fields;
    private boolean isAutoCloneEnabled;
    private boolean isLocked;
    private boolean isTransformationRequired;
    private boolean onlyRetrieveFromCache;

    @Nullable
    private Drawable placeholderDrawable;
    private int placeholderId;

    @Nullable
    private Resources.Theme theme;
    private boolean useAnimationPool;
    private boolean useUnlimitedSourceGeneratorsPool;
    private float sizeMultiplier = 1.0f;

    @NonNull
    private DiskCacheStrategy diskCacheStrategy = DiskCacheStrategy.AUTOMATIC;

    @NonNull
    private Priority priority = Priority.NORMAL;
    private boolean isCacheable = true;
    private int overrideHeight = -1;
    private int overrideWidth = -1;

    @NonNull
    private Key signature = EmptySignature.obtain();
    private boolean isTransformationAllowed = true;

    @NonNull
    private Options options = new Options();

    @NonNull
    private Map<Class<?>, Transformation<?>> transformations = new CachedHashCodeArrayMap();

    @NonNull
    private Class<?> resourceClass = Object.class;
    private boolean isScaleOnlyOrNoTransform = true;

    private boolean isSet(int i2) {
        return isSet(this.fields, i2);
    }

    private static boolean isSet(int i2, int i3) {
        return (i2 & i3) != 0;
    }

    @NonNull
    private T optionalScaleOnlyTransform(@NonNull DownsampleStrategy downsampleStrategy, @NonNull Transformation<Bitmap> transformation) {
        return (T) scaleOnlyTransform(downsampleStrategy, transformation, false);
    }

    @NonNull
    private T scaleOnlyTransform(@NonNull DownsampleStrategy downsampleStrategy, @NonNull Transformation<Bitmap> transformation) {
        return (T) scaleOnlyTransform(downsampleStrategy, transformation, true);
    }

    private T self() {
        return this;
    }

    @NonNull
    @CheckResult
    public T apply(@NonNull BaseRequestOptions<?> baseRequestOptions) {
        if (this.isAutoCloneEnabled) {
            return (T) mo69clone().apply(baseRequestOptions);
        }
        if (isSet(baseRequestOptions.fields, 2)) {
            this.sizeMultiplier = baseRequestOptions.sizeMultiplier;
        }
        if (isSet(baseRequestOptions.fields, 262144)) {
            this.useUnlimitedSourceGeneratorsPool = baseRequestOptions.useUnlimitedSourceGeneratorsPool;
        }
        if (isSet(baseRequestOptions.fields, 1048576)) {
            this.useAnimationPool = baseRequestOptions.useAnimationPool;
        }
        if (isSet(baseRequestOptions.fields, 4)) {
            this.diskCacheStrategy = baseRequestOptions.diskCacheStrategy;
        }
        if (isSet(baseRequestOptions.fields, 8)) {
            this.priority = baseRequestOptions.priority;
        }
        if (isSet(baseRequestOptions.fields, 16)) {
            this.errorPlaceholder = baseRequestOptions.errorPlaceholder;
            this.errorId = 0;
            this.fields &= -33;
        }
        if (isSet(baseRequestOptions.fields, 32)) {
            this.errorId = baseRequestOptions.errorId;
            this.errorPlaceholder = null;
            this.fields &= -17;
        }
        if (isSet(baseRequestOptions.fields, 64)) {
            this.placeholderDrawable = baseRequestOptions.placeholderDrawable;
            this.placeholderId = 0;
            this.fields &= -129;
        }
        if (isSet(baseRequestOptions.fields, 128)) {
            this.placeholderId = baseRequestOptions.placeholderId;
            this.placeholderDrawable = null;
            this.fields &= -65;
        }
        if (isSet(baseRequestOptions.fields, 256)) {
            this.isCacheable = baseRequestOptions.isCacheable;
        }
        if (isSet(baseRequestOptions.fields, 512)) {
            this.overrideWidth = baseRequestOptions.overrideWidth;
            this.overrideHeight = baseRequestOptions.overrideHeight;
        }
        if (isSet(baseRequestOptions.fields, 1024)) {
            this.signature = baseRequestOptions.signature;
        }
        if (isSet(baseRequestOptions.fields, 4096)) {
            this.resourceClass = baseRequestOptions.resourceClass;
        }
        if (isSet(baseRequestOptions.fields, 8192)) {
            this.fallbackDrawable = baseRequestOptions.fallbackDrawable;
            this.fallbackId = 0;
            this.fields &= -16385;
        }
        if (isSet(baseRequestOptions.fields, 16384)) {
            this.fallbackId = baseRequestOptions.fallbackId;
            this.fallbackDrawable = null;
            this.fields &= -8193;
        }
        if (isSet(baseRequestOptions.fields, 32768)) {
            this.theme = baseRequestOptions.theme;
        }
        if (isSet(baseRequestOptions.fields, 65536)) {
            this.isTransformationAllowed = baseRequestOptions.isTransformationAllowed;
        }
        if (isSet(baseRequestOptions.fields, 131072)) {
            this.isTransformationRequired = baseRequestOptions.isTransformationRequired;
        }
        if (isSet(baseRequestOptions.fields, 2048)) {
            this.transformations.putAll(baseRequestOptions.transformations);
            this.isScaleOnlyOrNoTransform = baseRequestOptions.isScaleOnlyOrNoTransform;
        }
        if (isSet(baseRequestOptions.fields, 524288)) {
            this.onlyRetrieveFromCache = baseRequestOptions.onlyRetrieveFromCache;
        }
        if (!this.isTransformationAllowed) {
            this.transformations.clear();
            int i2 = this.fields & (-2049);
            this.isTransformationRequired = false;
            this.fields = i2 & (-131073);
            this.isScaleOnlyOrNoTransform = true;
        }
        this.fields |= baseRequestOptions.fields;
        this.options.putAll(baseRequestOptions.options);
        return (T) selfOrThrowIfLocked();
    }

    @NonNull
    public T autoClone() {
        if (this.isLocked && !this.isAutoCloneEnabled) {
            throw new IllegalStateException("You cannot auto lock an already locked options object, try clone() first");
        }
        this.isAutoCloneEnabled = true;
        return (T) lock();
    }

    @NonNull
    @CheckResult
    public T centerCrop() {
        return (T) transform(DownsampleStrategy.CENTER_OUTSIDE, new CenterCrop());
    }

    @NonNull
    @CheckResult
    public T centerInside() {
        return (T) scaleOnlyTransform(DownsampleStrategy.CENTER_INSIDE, new CenterInside());
    }

    @NonNull
    @CheckResult
    public T circleCrop() {
        return (T) transform(DownsampleStrategy.CENTER_INSIDE, new CircleCrop());
    }

    @NonNull
    @CheckResult
    public T decode(@NonNull Class<?> cls) {
        if (this.isAutoCloneEnabled) {
            return (T) mo69clone().decode(cls);
        }
        this.resourceClass = (Class) Preconditions.checkNotNull(cls);
        this.fields |= 4096;
        return (T) selfOrThrowIfLocked();
    }

    @NonNull
    @CheckResult
    public T disallowHardwareConfig() {
        return (T) set(Downsampler.ALLOW_HARDWARE_CONFIG, Boolean.FALSE);
    }

    @NonNull
    @CheckResult
    public T diskCacheStrategy(@NonNull DiskCacheStrategy diskCacheStrategy) {
        if (this.isAutoCloneEnabled) {
            return (T) mo69clone().diskCacheStrategy(diskCacheStrategy);
        }
        this.diskCacheStrategy = (DiskCacheStrategy) Preconditions.checkNotNull(diskCacheStrategy);
        this.fields |= 4;
        return (T) selfOrThrowIfLocked();
    }

    @NonNull
    @CheckResult
    public T dontAnimate() {
        return (T) set(GifOptions.DISABLE_ANIMATION, Boolean.TRUE);
    }

    @NonNull
    @CheckResult
    public T dontTransform() {
        if (this.isAutoCloneEnabled) {
            return (T) mo69clone().dontTransform();
        }
        this.transformations.clear();
        int i2 = this.fields & (-2049);
        this.isTransformationRequired = false;
        this.isTransformationAllowed = false;
        this.fields = (i2 & (-131073)) | 65536;
        this.isScaleOnlyOrNoTransform = true;
        return (T) selfOrThrowIfLocked();
    }

    @NonNull
    @CheckResult
    public T downsample(@NonNull DownsampleStrategy downsampleStrategy) {
        return (T) set(DownsampleStrategy.OPTION, Preconditions.checkNotNull(downsampleStrategy));
    }

    @NonNull
    @CheckResult
    public T encodeFormat(@NonNull Bitmap.CompressFormat compressFormat) {
        return (T) set(BitmapEncoder.COMPRESSION_FORMAT, Preconditions.checkNotNull(compressFormat));
    }

    @NonNull
    @CheckResult
    public T encodeQuality(@IntRange(from = 0, to = 100) int i2) {
        return (T) set(BitmapEncoder.COMPRESSION_QUALITY, Integer.valueOf(i2));
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof BaseRequestOptions)) {
            return false;
        }
        BaseRequestOptions baseRequestOptions = (BaseRequestOptions) obj;
        return Float.compare(baseRequestOptions.sizeMultiplier, this.sizeMultiplier) == 0 && this.errorId == baseRequestOptions.errorId && Util.bothNullOrEqual(this.errorPlaceholder, baseRequestOptions.errorPlaceholder) && this.placeholderId == baseRequestOptions.placeholderId && Util.bothNullOrEqual(this.placeholderDrawable, baseRequestOptions.placeholderDrawable) && this.fallbackId == baseRequestOptions.fallbackId && Util.bothNullOrEqual(this.fallbackDrawable, baseRequestOptions.fallbackDrawable) && this.isCacheable == baseRequestOptions.isCacheable && this.overrideHeight == baseRequestOptions.overrideHeight && this.overrideWidth == baseRequestOptions.overrideWidth && this.isTransformationRequired == baseRequestOptions.isTransformationRequired && this.isTransformationAllowed == baseRequestOptions.isTransformationAllowed && this.useUnlimitedSourceGeneratorsPool == baseRequestOptions.useUnlimitedSourceGeneratorsPool && this.onlyRetrieveFromCache == baseRequestOptions.onlyRetrieveFromCache && this.diskCacheStrategy.equals(baseRequestOptions.diskCacheStrategy) && this.priority == baseRequestOptions.priority && this.options.equals(baseRequestOptions.options) && this.transformations.equals(baseRequestOptions.transformations) && this.resourceClass.equals(baseRequestOptions.resourceClass) && Util.bothNullOrEqual(this.signature, baseRequestOptions.signature) && Util.bothNullOrEqual(this.theme, baseRequestOptions.theme);
    }

    @NonNull
    @CheckResult
    public T error(@Nullable Drawable drawable) {
        if (this.isAutoCloneEnabled) {
            return (T) mo69clone().error(drawable);
        }
        this.errorPlaceholder = drawable;
        int i2 = this.fields | 16;
        this.errorId = 0;
        this.fields = i2 & (-33);
        return (T) selfOrThrowIfLocked();
    }

    @NonNull
    @CheckResult
    public T fallback(@Nullable Drawable drawable) {
        if (this.isAutoCloneEnabled) {
            return (T) mo69clone().fallback(drawable);
        }
        this.fallbackDrawable = drawable;
        int i2 = this.fields | 8192;
        this.fallbackId = 0;
        this.fields = i2 & (-16385);
        return (T) selfOrThrowIfLocked();
    }

    @NonNull
    @CheckResult
    public T fitCenter() {
        return (T) scaleOnlyTransform(DownsampleStrategy.FIT_CENTER, new FitCenter());
    }

    @NonNull
    @CheckResult
    public T format(@NonNull DecodeFormat decodeFormat) {
        Preconditions.checkNotNull(decodeFormat);
        return (T) set(Downsampler.DECODE_FORMAT, decodeFormat).set(GifOptions.DECODE_FORMAT, decodeFormat);
    }

    @NonNull
    @CheckResult
    public T frame(@IntRange(from = 0) long j2) {
        return (T) set(VideoDecoder.TARGET_FRAME, Long.valueOf(j2));
    }

    @NonNull
    public final DiskCacheStrategy getDiskCacheStrategy() {
        return this.diskCacheStrategy;
    }

    public final int getErrorId() {
        return this.errorId;
    }

    @Nullable
    public final Drawable getErrorPlaceholder() {
        return this.errorPlaceholder;
    }

    @Nullable
    public final Drawable getFallbackDrawable() {
        return this.fallbackDrawable;
    }

    public final int getFallbackId() {
        return this.fallbackId;
    }

    public final boolean getOnlyRetrieveFromCache() {
        return this.onlyRetrieveFromCache;
    }

    @NonNull
    public final Options getOptions() {
        return this.options;
    }

    public final int getOverrideHeight() {
        return this.overrideHeight;
    }

    public final int getOverrideWidth() {
        return this.overrideWidth;
    }

    @Nullable
    public final Drawable getPlaceholderDrawable() {
        return this.placeholderDrawable;
    }

    public final int getPlaceholderId() {
        return this.placeholderId;
    }

    @NonNull
    public final Priority getPriority() {
        return this.priority;
    }

    @NonNull
    public final Class<?> getResourceClass() {
        return this.resourceClass;
    }

    @NonNull
    public final Key getSignature() {
        return this.signature;
    }

    public final float getSizeMultiplier() {
        return this.sizeMultiplier;
    }

    @Nullable
    public final Resources.Theme getTheme() {
        return this.theme;
    }

    @NonNull
    public final Map<Class<?>, Transformation<?>> getTransformations() {
        return this.transformations;
    }

    public final boolean getUseAnimationPool() {
        return this.useAnimationPool;
    }

    public final boolean getUseUnlimitedSourceGeneratorsPool() {
        return this.useUnlimitedSourceGeneratorsPool;
    }

    public int hashCode() {
        return Util.hashCode(this.theme, Util.hashCode(this.signature, Util.hashCode(this.resourceClass, Util.hashCode(this.transformations, Util.hashCode(this.options, Util.hashCode(this.priority, Util.hashCode(this.diskCacheStrategy, Util.hashCode(this.onlyRetrieveFromCache, Util.hashCode(this.useUnlimitedSourceGeneratorsPool, Util.hashCode(this.isTransformationAllowed, Util.hashCode(this.isTransformationRequired, Util.hashCode(this.overrideWidth, Util.hashCode(this.overrideHeight, Util.hashCode(this.isCacheable, Util.hashCode(this.fallbackDrawable, Util.hashCode(this.fallbackId, Util.hashCode(this.placeholderDrawable, Util.hashCode(this.placeholderId, Util.hashCode(this.errorPlaceholder, Util.hashCode(this.errorId, Util.hashCode(this.sizeMultiplier)))))))))))))))))))));
    }

    public final boolean isAutoCloneEnabled() {
        return this.isAutoCloneEnabled;
    }

    public final boolean isDiskCacheStrategySet() {
        return isSet(4);
    }

    public final boolean isLocked() {
        return this.isLocked;
    }

    public final boolean isMemoryCacheable() {
        return this.isCacheable;
    }

    public final boolean isPrioritySet() {
        return isSet(8);
    }

    public boolean isScaleOnlyOrNoTransform() {
        return this.isScaleOnlyOrNoTransform;
    }

    public final boolean isSkipMemoryCacheSet() {
        return isSet(256);
    }

    public final boolean isTransformationAllowed() {
        return this.isTransformationAllowed;
    }

    public final boolean isTransformationRequired() {
        return this.isTransformationRequired;
    }

    public final boolean isTransformationSet() {
        return isSet(2048);
    }

    public final boolean isValidOverride() {
        return Util.isValidDimensions(this.overrideWidth, this.overrideHeight);
    }

    @NonNull
    public T lock() {
        this.isLocked = true;
        return (T) self();
    }

    @NonNull
    @CheckResult
    public T onlyRetrieveFromCache(boolean z2) {
        if (this.isAutoCloneEnabled) {
            return (T) mo69clone().onlyRetrieveFromCache(z2);
        }
        this.onlyRetrieveFromCache = z2;
        this.fields |= 524288;
        return (T) selfOrThrowIfLocked();
    }

    @NonNull
    @CheckResult
    public T optionalCenterCrop() {
        return (T) optionalTransform(DownsampleStrategy.CENTER_OUTSIDE, new CenterCrop());
    }

    @NonNull
    @CheckResult
    public T optionalCenterInside() {
        return (T) optionalScaleOnlyTransform(DownsampleStrategy.CENTER_INSIDE, new CenterInside());
    }

    @NonNull
    @CheckResult
    public T optionalCircleCrop() {
        return (T) optionalTransform(DownsampleStrategy.CENTER_OUTSIDE, new CircleCrop());
    }

    @NonNull
    @CheckResult
    public T optionalFitCenter() {
        return (T) optionalScaleOnlyTransform(DownsampleStrategy.FIT_CENTER, new FitCenter());
    }

    @NonNull
    public final T optionalTransform(@NonNull DownsampleStrategy downsampleStrategy, @NonNull Transformation<Bitmap> transformation) {
        if (this.isAutoCloneEnabled) {
            return (T) mo69clone().optionalTransform(downsampleStrategy, transformation);
        }
        downsample(downsampleStrategy);
        return (T) transform(transformation, false);
    }

    @NonNull
    @CheckResult
    public T override(int i2, int i3) {
        if (this.isAutoCloneEnabled) {
            return (T) mo69clone().override(i2, i3);
        }
        this.overrideWidth = i2;
        this.overrideHeight = i3;
        this.fields |= 512;
        return (T) selfOrThrowIfLocked();
    }

    @NonNull
    @CheckResult
    public T placeholder(@Nullable Drawable drawable) {
        if (this.isAutoCloneEnabled) {
            return (T) mo69clone().placeholder(drawable);
        }
        this.placeholderDrawable = drawable;
        int i2 = this.fields | 64;
        this.placeholderId = 0;
        this.fields = i2 & (-129);
        return (T) selfOrThrowIfLocked();
    }

    @NonNull
    @CheckResult
    public T priority(@NonNull Priority priority) {
        if (this.isAutoCloneEnabled) {
            return (T) mo69clone().priority(priority);
        }
        this.priority = (Priority) Preconditions.checkNotNull(priority);
        this.fields |= 8;
        return (T) selfOrThrowIfLocked();
    }

    @NonNull
    public final T selfOrThrowIfLocked() {
        if (this.isLocked) {
            throw new IllegalStateException("You cannot modify locked T, consider clone()");
        }
        return (T) self();
    }

    @NonNull
    @CheckResult
    public <Y> T set(@NonNull Option<Y> option, @NonNull Y y2) {
        if (this.isAutoCloneEnabled) {
            return (T) mo69clone().set(option, y2);
        }
        Preconditions.checkNotNull(option);
        Preconditions.checkNotNull(y2);
        this.options.set(option, y2);
        return (T) selfOrThrowIfLocked();
    }

    @NonNull
    @CheckResult
    public T signature(@NonNull Key key) {
        if (this.isAutoCloneEnabled) {
            return (T) mo69clone().signature(key);
        }
        this.signature = (Key) Preconditions.checkNotNull(key);
        this.fields |= 1024;
        return (T) selfOrThrowIfLocked();
    }

    @NonNull
    @CheckResult
    public T sizeMultiplier(@FloatRange(from = 0.0d, to = 1.0d) float f2) {
        if (this.isAutoCloneEnabled) {
            return (T) mo69clone().sizeMultiplier(f2);
        }
        if (f2 < 0.0f || f2 > 1.0f) {
            throw new IllegalArgumentException("sizeMultiplier must be between 0 and 1");
        }
        this.sizeMultiplier = f2;
        this.fields |= 2;
        return (T) selfOrThrowIfLocked();
    }

    @NonNull
    @CheckResult
    public T skipMemoryCache(boolean z2) {
        if (this.isAutoCloneEnabled) {
            return (T) mo69clone().skipMemoryCache(true);
        }
        this.isCacheable = !z2;
        this.fields |= 256;
        return (T) selfOrThrowIfLocked();
    }

    @NonNull
    @CheckResult
    public T theme(@Nullable Resources.Theme theme) {
        if (this.isAutoCloneEnabled) {
            return (T) mo69clone().theme(theme);
        }
        this.theme = theme;
        this.fields |= 32768;
        return (T) selfOrThrowIfLocked();
    }

    @NonNull
    @CheckResult
    public T timeout(@IntRange(from = 0) int i2) {
        return (T) set(HttpGlideUrlLoader.TIMEOUT, Integer.valueOf(i2));
    }

    @NonNull
    @CheckResult
    public final T transform(@NonNull DownsampleStrategy downsampleStrategy, @NonNull Transformation<Bitmap> transformation) {
        if (this.isAutoCloneEnabled) {
            return (T) mo69clone().transform(downsampleStrategy, transformation);
        }
        downsample(downsampleStrategy);
        return (T) transform(transformation);
    }

    @NonNull
    @CheckResult
    @Deprecated
    public T transforms(@NonNull Transformation<Bitmap>... transformationArr) {
        return (T) transform((Transformation<Bitmap>) new MultiTransformation(transformationArr), true);
    }

    @NonNull
    @CheckResult
    public T useAnimationPool(boolean z2) {
        if (this.isAutoCloneEnabled) {
            return (T) mo69clone().useAnimationPool(z2);
        }
        this.useAnimationPool = z2;
        this.fields |= 1048576;
        return (T) selfOrThrowIfLocked();
    }

    @NonNull
    @CheckResult
    public T useUnlimitedSourceGeneratorsPool(boolean z2) {
        if (this.isAutoCloneEnabled) {
            return (T) mo69clone().useUnlimitedSourceGeneratorsPool(z2);
        }
        this.useUnlimitedSourceGeneratorsPool = z2;
        this.fields |= 262144;
        return (T) selfOrThrowIfLocked();
    }

    @NonNull
    private T scaleOnlyTransform(@NonNull DownsampleStrategy downsampleStrategy, @NonNull Transformation<Bitmap> transformation, boolean z2) {
        T t2 = z2 ? (T) transform(downsampleStrategy, transformation) : (T) optionalTransform(downsampleStrategy, transformation);
        t2.isScaleOnlyOrNoTransform = true;
        return t2;
    }

    @Override // 
    @CheckResult
    /* renamed from: clone */
    public T mo69clone() {
        try {
            T t2 = (T) super.clone();
            Options options = new Options();
            t2.options = options;
            options.putAll(this.options);
            CachedHashCodeArrayMap cachedHashCodeArrayMap = new CachedHashCodeArrayMap();
            t2.transformations = cachedHashCodeArrayMap;
            cachedHashCodeArrayMap.putAll(this.transformations);
            t2.isLocked = false;
            t2.isAutoCloneEnabled = false;
            return t2;
        } catch (CloneNotSupportedException e2) {
            throw new RuntimeException(e2);
        }
    }

    @NonNull
    @CheckResult
    public T optionalTransform(@NonNull Transformation<Bitmap> transformation) {
        return (T) transform(transformation, false);
    }

    @NonNull
    @CheckResult
    public T transform(@NonNull Transformation<Bitmap> transformation) {
        return (T) transform(transformation, true);
    }

    @NonNull
    @CheckResult
    public <Y> T optionalTransform(@NonNull Class<Y> cls, @NonNull Transformation<Y> transformation) {
        return (T) transform(cls, transformation, false);
    }

    @NonNull
    @CheckResult
    public T transform(@NonNull Transformation<Bitmap>... transformationArr) {
        if (transformationArr.length > 1) {
            return (T) transform((Transformation<Bitmap>) new MultiTransformation(transformationArr), true);
        }
        if (transformationArr.length == 1) {
            return (T) transform(transformationArr[0]);
        }
        return (T) selfOrThrowIfLocked();
    }

    @NonNull
    @CheckResult
    public T override(int i2) {
        return (T) override(i2, i2);
    }

    @NonNull
    @CheckResult
    public T error(@DrawableRes int i2) {
        if (this.isAutoCloneEnabled) {
            return (T) mo69clone().error(i2);
        }
        this.errorId = i2;
        int i3 = this.fields | 32;
        this.errorPlaceholder = null;
        this.fields = i3 & (-17);
        return (T) selfOrThrowIfLocked();
    }

    @NonNull
    @CheckResult
    public T fallback(@DrawableRes int i2) {
        if (this.isAutoCloneEnabled) {
            return (T) mo69clone().fallback(i2);
        }
        this.fallbackId = i2;
        int i3 = this.fields | 16384;
        this.fallbackDrawable = null;
        this.fields = i3 & (-8193);
        return (T) selfOrThrowIfLocked();
    }

    @NonNull
    @CheckResult
    public T placeholder(@DrawableRes int i2) {
        if (this.isAutoCloneEnabled) {
            return (T) mo69clone().placeholder(i2);
        }
        this.placeholderId = i2;
        int i3 = this.fields | 128;
        this.placeholderDrawable = null;
        this.fields = i3 & (-65);
        return (T) selfOrThrowIfLocked();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @NonNull
    public T transform(@NonNull Transformation<Bitmap> transformation, boolean z2) {
        if (this.isAutoCloneEnabled) {
            return (T) mo69clone().transform(transformation, z2);
        }
        DrawableTransformation drawableTransformation = new DrawableTransformation(transformation, z2);
        transform(Bitmap.class, transformation, z2);
        transform(Drawable.class, drawableTransformation, z2);
        transform(BitmapDrawable.class, drawableTransformation.asBitmapDrawable(), z2);
        transform(GifDrawable.class, new GifDrawableTransformation(transformation), z2);
        return (T) selfOrThrowIfLocked();
    }

    @NonNull
    public <Y> T transform(@NonNull Class<Y> cls, @NonNull Transformation<Y> transformation, boolean z2) {
        if (this.isAutoCloneEnabled) {
            return (T) mo69clone().transform(cls, transformation, z2);
        }
        Preconditions.checkNotNull(cls);
        Preconditions.checkNotNull(transformation);
        this.transformations.put(cls, transformation);
        int i2 = this.fields | 2048;
        this.isTransformationAllowed = true;
        int i3 = i2 | 65536;
        this.fields = i3;
        this.isScaleOnlyOrNoTransform = false;
        if (z2) {
            this.fields = i3 | 131072;
            this.isTransformationRequired = true;
        }
        return (T) selfOrThrowIfLocked();
    }

    @NonNull
    @CheckResult
    public <Y> T transform(@NonNull Class<Y> cls, @NonNull Transformation<Y> transformation) {
        return (T) transform(cls, transformation, true);
    }
}
