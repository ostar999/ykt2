package com.airbnb.lottie;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import cn.hutool.core.text.StrPool;
import com.airbnb.lottie.manager.FontAssetManager;
import com.airbnb.lottie.manager.ImageAssetManager;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.model.Marker;
import com.airbnb.lottie.model.layer.CompositionLayer;
import com.airbnb.lottie.parser.LayerParser;
import com.airbnb.lottie.utils.Logger;
import com.airbnb.lottie.utils.LottieValueAnimator;
import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.value.LottieFrameInfo;
import com.airbnb.lottie.value.LottieValueCallback;
import com.airbnb.lottie.value.SimpleLottieValueCallback;
import com.easefun.polyv.mediasdk.player.IjkMediaMeta;
import com.yikaobang.yixue.R2;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* loaded from: classes.dex */
public class LottieDrawable extends Drawable implements Drawable.Callback, Animatable {
    public static final int INFINITE = -1;
    public static final int RESTART = 1;
    public static final int REVERSE = 2;
    private static final String TAG = "LottieDrawable";
    private int alpha;
    private final LottieValueAnimator animator;
    private final Set<ColorFilterData> colorFilterData;
    private LottieComposition composition;

    @Nullable
    private CompositionLayer compositionLayer;
    private boolean enableMergePaths;

    @Nullable
    FontAssetDelegate fontAssetDelegate;

    @Nullable
    private FontAssetManager fontAssetManager;

    @Nullable
    private ImageAssetDelegate imageAssetDelegate;

    @Nullable
    private ImageAssetManager imageAssetManager;

    @Nullable
    private String imageAssetsFolder;
    private boolean isApplyingOpacityToLayersEnabled;
    private boolean isDirty;
    private boolean isExtraScaleEnabled;
    private final ArrayList<LazyCompositionTask> lazyCompositionTasks;
    private final Matrix matrix = new Matrix();
    private boolean outlineMasksAndMattes;
    private boolean performanceTrackingEnabled;
    private final ValueAnimator.AnimatorUpdateListener progressUpdateListener;
    private boolean safeMode;
    private float scale;

    @Nullable
    private ImageView.ScaleType scaleType;
    private boolean systemAnimationsEnabled;

    @Nullable
    TextDelegate textDelegate;

    public static class ColorFilterData {

        @Nullable
        final ColorFilter colorFilter;

        @Nullable
        final String contentName;
        final String layerName;

        public ColorFilterData(@Nullable String str, @Nullable String str2, @Nullable ColorFilter colorFilter) {
            this.layerName = str;
            this.contentName = str2;
            this.colorFilter = colorFilter;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ColorFilterData)) {
                return false;
            }
            ColorFilterData colorFilterData = (ColorFilterData) obj;
            return hashCode() == colorFilterData.hashCode() && this.colorFilter == colorFilterData.colorFilter;
        }

        public int hashCode() {
            String str = this.layerName;
            int iHashCode = str != null ? R2.attr.bl_checkable_gradient_endColor * str.hashCode() : 17;
            String str2 = this.contentName;
            return str2 != null ? iHashCode * 31 * str2.hashCode() : iHashCode;
        }
    }

    public interface LazyCompositionTask {
        void run(LottieComposition lottieComposition);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface RepeatMode {
    }

    public LottieDrawable() {
        LottieValueAnimator lottieValueAnimator = new LottieValueAnimator();
        this.animator = lottieValueAnimator;
        this.scale = 1.0f;
        this.systemAnimationsEnabled = true;
        this.safeMode = false;
        this.colorFilterData = new HashSet();
        this.lazyCompositionTasks = new ArrayList<>();
        ValueAnimator.AnimatorUpdateListener animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.airbnb.lottie.LottieDrawable.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                if (LottieDrawable.this.compositionLayer != null) {
                    LottieDrawable.this.compositionLayer.setProgress(LottieDrawable.this.animator.getAnimatedValueAbsolute());
                }
            }
        };
        this.progressUpdateListener = animatorUpdateListener;
        this.alpha = 255;
        this.isExtraScaleEnabled = true;
        this.isDirty = false;
        lottieValueAnimator.addUpdateListener(animatorUpdateListener);
    }

    private void buildCompositionLayer() {
        CompositionLayer compositionLayer = new CompositionLayer(this, LayerParser.parse(this.composition), this.composition.getLayers(), this.composition);
        this.compositionLayer = compositionLayer;
        if (this.outlineMasksAndMattes) {
            compositionLayer.setOutlineMasksAndMattes(true);
        }
    }

    private void drawInternal(@NonNull Canvas canvas) {
        if (ImageView.ScaleType.FIT_XY == this.scaleType) {
            drawWithNewAspectRatio(canvas);
        } else {
            drawWithOriginalAspectRatio(canvas);
        }
    }

    private void drawWithNewAspectRatio(Canvas canvas) {
        float f2;
        if (this.compositionLayer == null) {
            return;
        }
        Rect bounds = getBounds();
        float fWidth = bounds.width() / this.composition.getBounds().width();
        float fHeight = bounds.height() / this.composition.getBounds().height();
        int iSave = -1;
        if (this.isExtraScaleEnabled) {
            float fMin = Math.min(fWidth, fHeight);
            if (fMin < 1.0f) {
                f2 = 1.0f / fMin;
                fWidth /= f2;
                fHeight /= f2;
            } else {
                f2 = 1.0f;
            }
            if (f2 > 1.0f) {
                iSave = canvas.save();
                float fWidth2 = bounds.width() / 2.0f;
                float fHeight2 = bounds.height() / 2.0f;
                float f3 = fWidth2 * fMin;
                float f4 = fMin * fHeight2;
                canvas.translate(fWidth2 - f3, fHeight2 - f4);
                canvas.scale(f2, f2, f3, f4);
            }
        }
        this.matrix.reset();
        this.matrix.preScale(fWidth, fHeight);
        this.compositionLayer.draw(canvas, this.matrix, this.alpha);
        if (iSave > 0) {
            canvas.restoreToCount(iSave);
        }
    }

    private void drawWithOriginalAspectRatio(Canvas canvas) {
        float f2;
        int iSave;
        if (this.compositionLayer == null) {
            return;
        }
        float f3 = this.scale;
        float maxScale = getMaxScale(canvas);
        if (f3 > maxScale) {
            f2 = this.scale / maxScale;
        } else {
            maxScale = f3;
            f2 = 1.0f;
        }
        if (f2 > 1.0f) {
            iSave = canvas.save();
            float fWidth = this.composition.getBounds().width() / 2.0f;
            float fHeight = this.composition.getBounds().height() / 2.0f;
            float f4 = fWidth * maxScale;
            float f5 = fHeight * maxScale;
            canvas.translate((getScale() * fWidth) - f4, (getScale() * fHeight) - f5);
            canvas.scale(f2, f2, f4, f5);
        } else {
            iSave = -1;
        }
        this.matrix.reset();
        this.matrix.preScale(maxScale, maxScale);
        this.compositionLayer.draw(canvas, this.matrix, this.alpha);
        if (iSave > 0) {
            canvas.restoreToCount(iSave);
        }
    }

    @Nullable
    private Context getContext() {
        Drawable.Callback callback = getCallback();
        if (callback != null && (callback instanceof View)) {
            return ((View) callback).getContext();
        }
        return null;
    }

    private FontAssetManager getFontAssetManager() {
        if (getCallback() == null) {
            return null;
        }
        if (this.fontAssetManager == null) {
            this.fontAssetManager = new FontAssetManager(getCallback(), this.fontAssetDelegate);
        }
        return this.fontAssetManager;
    }

    private ImageAssetManager getImageAssetManager() {
        if (getCallback() == null) {
            return null;
        }
        ImageAssetManager imageAssetManager = this.imageAssetManager;
        if (imageAssetManager != null && !imageAssetManager.hasSameContext(getContext())) {
            this.imageAssetManager = null;
        }
        if (this.imageAssetManager == null) {
            this.imageAssetManager = new ImageAssetManager(getCallback(), this.imageAssetsFolder, this.imageAssetDelegate, this.composition.getImages());
        }
        return this.imageAssetManager;
    }

    private float getMaxScale(@NonNull Canvas canvas) {
        return Math.min(canvas.getWidth() / this.composition.getBounds().width(), canvas.getHeight() / this.composition.getBounds().height());
    }

    private void updateBounds() {
        if (this.composition == null) {
            return;
        }
        float scale = getScale();
        setBounds(0, 0, (int) (this.composition.getBounds().width() * scale), (int) (this.composition.getBounds().height() * scale));
    }

    public void addAnimatorListener(Animator.AnimatorListener animatorListener) {
        this.animator.addListener(animatorListener);
    }

    @RequiresApi(api = 19)
    public void addAnimatorPauseListener(Animator.AnimatorPauseListener animatorPauseListener) {
        this.animator.addPauseListener(animatorPauseListener);
    }

    public void addAnimatorUpdateListener(ValueAnimator.AnimatorUpdateListener animatorUpdateListener) {
        this.animator.addUpdateListener(animatorUpdateListener);
    }

    public <T> void addValueCallback(final KeyPath keyPath, final T t2, final LottieValueCallback<T> lottieValueCallback) {
        CompositionLayer compositionLayer = this.compositionLayer;
        if (compositionLayer == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() { // from class: com.airbnb.lottie.LottieDrawable.16
                @Override // com.airbnb.lottie.LottieDrawable.LazyCompositionTask
                public void run(LottieComposition lottieComposition) {
                    LottieDrawable.this.addValueCallback(keyPath, (KeyPath) t2, (LottieValueCallback<KeyPath>) lottieValueCallback);
                }
            });
            return;
        }
        boolean zIsEmpty = true;
        if (keyPath == KeyPath.COMPOSITION) {
            compositionLayer.addValueCallback(t2, lottieValueCallback);
        } else if (keyPath.getResolvedElement() != null) {
            keyPath.getResolvedElement().addValueCallback(t2, lottieValueCallback);
        } else {
            List<KeyPath> listResolveKeyPath = resolveKeyPath(keyPath);
            for (int i2 = 0; i2 < listResolveKeyPath.size(); i2++) {
                listResolveKeyPath.get(i2).getResolvedElement().addValueCallback(t2, lottieValueCallback);
            }
            zIsEmpty = true ^ listResolveKeyPath.isEmpty();
        }
        if (zIsEmpty) {
            invalidateSelf();
            if (t2 == LottieProperty.TIME_REMAP) {
                setProgress(getProgress());
            }
        }
    }

    public void cancelAnimation() {
        this.lazyCompositionTasks.clear();
        this.animator.cancel();
    }

    public void clearComposition() {
        if (this.animator.isRunning()) {
            this.animator.cancel();
        }
        this.composition = null;
        this.compositionLayer = null;
        this.imageAssetManager = null;
        this.animator.clearComposition();
        invalidateSelf();
    }

    public void disableExtraScaleModeInFitXY() {
        this.isExtraScaleEnabled = false;
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(@NonNull Canvas canvas) {
        this.isDirty = false;
        L.beginSection("Drawable#draw");
        if (this.safeMode) {
            try {
                drawInternal(canvas);
            } catch (Throwable th) {
                Logger.error("Lottie crashed in draw!", th);
            }
        } else {
            drawInternal(canvas);
        }
        L.endSection("Drawable#draw");
    }

    public boolean enableMergePathsForKitKatAndAbove() {
        return this.enableMergePaths;
    }

    @MainThread
    public void endAnimation() {
        this.lazyCompositionTasks.clear();
        this.animator.endAnimation();
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.alpha;
    }

    public LottieComposition getComposition() {
        return this.composition;
    }

    public int getFrame() {
        return (int) this.animator.getFrame();
    }

    @Nullable
    public Bitmap getImageAsset(String str) {
        ImageAssetManager imageAssetManager = getImageAssetManager();
        if (imageAssetManager != null) {
            return imageAssetManager.bitmapForId(str);
        }
        return null;
    }

    @Nullable
    public String getImageAssetsFolder() {
        return this.imageAssetsFolder;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        if (this.composition == null) {
            return -1;
        }
        return (int) (r0.getBounds().height() * getScale());
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        if (this.composition == null) {
            return -1;
        }
        return (int) (r0.getBounds().width() * getScale());
    }

    public float getMaxFrame() {
        return this.animator.getMaxFrame();
    }

    public float getMinFrame() {
        return this.animator.getMinFrame();
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    @Nullable
    public PerformanceTracker getPerformanceTracker() {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition != null) {
            return lottieComposition.getPerformanceTracker();
        }
        return null;
    }

    @FloatRange(from = 0.0d, to = 1.0d)
    public float getProgress() {
        return this.animator.getAnimatedValueAbsolute();
    }

    public int getRepeatCount() {
        return this.animator.getRepeatCount();
    }

    public int getRepeatMode() {
        return this.animator.getRepeatMode();
    }

    public float getScale() {
        return this.scale;
    }

    public float getSpeed() {
        return this.animator.getSpeed();
    }

    @Nullable
    public TextDelegate getTextDelegate() {
        return this.textDelegate;
    }

    @Nullable
    public Typeface getTypeface(String str, String str2) {
        FontAssetManager fontAssetManager = getFontAssetManager();
        if (fontAssetManager != null) {
            return fontAssetManager.getTypeface(str, str2);
        }
        return null;
    }

    public boolean hasMasks() {
        CompositionLayer compositionLayer = this.compositionLayer;
        return compositionLayer != null && compositionLayer.hasMasks();
    }

    public boolean hasMatte() {
        CompositionLayer compositionLayer = this.compositionLayer;
        return compositionLayer != null && compositionLayer.hasMatte();
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void invalidateDrawable(@NonNull Drawable drawable) {
        Drawable.Callback callback = getCallback();
        if (callback == null) {
            return;
        }
        callback.invalidateDrawable(this);
    }

    @Override // android.graphics.drawable.Drawable
    public void invalidateSelf() {
        if (this.isDirty) {
            return;
        }
        this.isDirty = true;
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.invalidateDrawable(this);
        }
    }

    public boolean isAnimating() {
        LottieValueAnimator lottieValueAnimator = this.animator;
        if (lottieValueAnimator == null) {
            return false;
        }
        return lottieValueAnimator.isRunning();
    }

    public boolean isApplyingOpacityToLayersEnabled() {
        return this.isApplyingOpacityToLayersEnabled;
    }

    public boolean isLooping() {
        return this.animator.getRepeatCount() == -1;
    }

    public boolean isMergePathsEnabledForKitKatAndAbove() {
        return this.enableMergePaths;
    }

    @Override // android.graphics.drawable.Animatable
    public boolean isRunning() {
        return isAnimating();
    }

    @Deprecated
    public void loop(boolean z2) {
        this.animator.setRepeatCount(z2 ? -1 : 0);
    }

    public void pauseAnimation() {
        this.lazyCompositionTasks.clear();
        this.animator.pauseAnimation();
    }

    @MainThread
    public void playAnimation() {
        if (this.compositionLayer == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() { // from class: com.airbnb.lottie.LottieDrawable.2
                @Override // com.airbnb.lottie.LottieDrawable.LazyCompositionTask
                public void run(LottieComposition lottieComposition) {
                    LottieDrawable.this.playAnimation();
                }
            });
            return;
        }
        if (this.systemAnimationsEnabled || getRepeatCount() == 0) {
            this.animator.playAnimation();
        }
        if (this.systemAnimationsEnabled) {
            return;
        }
        setFrame((int) (getSpeed() < 0.0f ? getMinFrame() : getMaxFrame()));
        this.animator.endAnimation();
    }

    public void removeAllAnimatorListeners() {
        this.animator.removeAllListeners();
    }

    public void removeAllUpdateListeners() {
        this.animator.removeAllUpdateListeners();
        this.animator.addUpdateListener(this.progressUpdateListener);
    }

    public void removeAnimatorListener(Animator.AnimatorListener animatorListener) {
        this.animator.removeListener(animatorListener);
    }

    @RequiresApi(api = 19)
    public void removeAnimatorPauseListener(Animator.AnimatorPauseListener animatorPauseListener) {
        this.animator.removePauseListener(animatorPauseListener);
    }

    public void removeAnimatorUpdateListener(ValueAnimator.AnimatorUpdateListener animatorUpdateListener) {
        this.animator.removeUpdateListener(animatorUpdateListener);
    }

    public List<KeyPath> resolveKeyPath(KeyPath keyPath) {
        if (this.compositionLayer == null) {
            Logger.warning("Cannot resolve KeyPath. Composition is not set yet.");
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        this.compositionLayer.resolveKeyPath(keyPath, 0, arrayList, new KeyPath(new String[0]));
        return arrayList;
    }

    @MainThread
    public void resumeAnimation() {
        if (this.compositionLayer == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() { // from class: com.airbnb.lottie.LottieDrawable.3
                @Override // com.airbnb.lottie.LottieDrawable.LazyCompositionTask
                public void run(LottieComposition lottieComposition) {
                    LottieDrawable.this.resumeAnimation();
                }
            });
            return;
        }
        if (this.systemAnimationsEnabled || getRepeatCount() == 0) {
            this.animator.resumeAnimation();
        }
        if (this.systemAnimationsEnabled) {
            return;
        }
        setFrame((int) (getSpeed() < 0.0f ? getMinFrame() : getMaxFrame()));
        this.animator.endAnimation();
    }

    public void reverseAnimationSpeed() {
        this.animator.reverseAnimationSpeed();
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void scheduleDrawable(@NonNull Drawable drawable, @NonNull Runnable runnable, long j2) {
        Drawable.Callback callback = getCallback();
        if (callback == null) {
            return;
        }
        callback.scheduleDrawable(this, runnable, j2);
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(@IntRange(from = 0, to = IjkMediaMeta.AV_CH_LAYOUT_7POINT1_WIDE_BACK) int i2) {
        this.alpha = i2;
        invalidateSelf();
    }

    public void setApplyingOpacityToLayersEnabled(boolean z2) {
        this.isApplyingOpacityToLayersEnabled = z2;
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        Logger.warning("Use addColorFilter instead.");
    }

    public boolean setComposition(LottieComposition lottieComposition) {
        if (this.composition == lottieComposition) {
            return false;
        }
        this.isDirty = false;
        clearComposition();
        this.composition = lottieComposition;
        buildCompositionLayer();
        this.animator.setComposition(lottieComposition);
        setProgress(this.animator.getAnimatedFraction());
        setScale(this.scale);
        updateBounds();
        Iterator it = new ArrayList(this.lazyCompositionTasks).iterator();
        while (it.hasNext()) {
            ((LazyCompositionTask) it.next()).run(lottieComposition);
            it.remove();
        }
        this.lazyCompositionTasks.clear();
        lottieComposition.setPerformanceTrackingEnabled(this.performanceTrackingEnabled);
        Drawable.Callback callback = getCallback();
        if (!(callback instanceof ImageView)) {
            return true;
        }
        ImageView imageView = (ImageView) callback;
        imageView.setImageDrawable(null);
        imageView.setImageDrawable(this);
        return true;
    }

    public void setFontAssetDelegate(FontAssetDelegate fontAssetDelegate) {
        this.fontAssetDelegate = fontAssetDelegate;
        FontAssetManager fontAssetManager = this.fontAssetManager;
        if (fontAssetManager != null) {
            fontAssetManager.setDelegate(fontAssetDelegate);
        }
    }

    public void setFrame(final int i2) {
        if (this.composition == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() { // from class: com.airbnb.lottie.LottieDrawable.14
                @Override // com.airbnb.lottie.LottieDrawable.LazyCompositionTask
                public void run(LottieComposition lottieComposition) {
                    LottieDrawable.this.setFrame(i2);
                }
            });
        } else {
            this.animator.setFrame(i2);
        }
    }

    public void setImageAssetDelegate(ImageAssetDelegate imageAssetDelegate) {
        this.imageAssetDelegate = imageAssetDelegate;
        ImageAssetManager imageAssetManager = this.imageAssetManager;
        if (imageAssetManager != null) {
            imageAssetManager.setDelegate(imageAssetDelegate);
        }
    }

    public void setImagesAssetsFolder(@Nullable String str) {
        this.imageAssetsFolder = str;
    }

    public void setMaxFrame(final int i2) {
        if (this.composition == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() { // from class: com.airbnb.lottie.LottieDrawable.6
                @Override // com.airbnb.lottie.LottieDrawable.LazyCompositionTask
                public void run(LottieComposition lottieComposition) {
                    LottieDrawable.this.setMaxFrame(i2);
                }
            });
        } else {
            this.animator.setMaxFrame(i2 + 0.99f);
        }
    }

    public void setMaxProgress(@FloatRange(from = 0.0d, to = 1.0d) final float f2) {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() { // from class: com.airbnb.lottie.LottieDrawable.7
                @Override // com.airbnb.lottie.LottieDrawable.LazyCompositionTask
                public void run(LottieComposition lottieComposition2) {
                    LottieDrawable.this.setMaxProgress(f2);
                }
            });
        } else {
            setMaxFrame((int) MiscUtils.lerp(lottieComposition.getStartFrame(), this.composition.getEndFrame(), f2));
        }
    }

    public void setMinAndMaxFrame(final String str) {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() { // from class: com.airbnb.lottie.LottieDrawable.10
                @Override // com.airbnb.lottie.LottieDrawable.LazyCompositionTask
                public void run(LottieComposition lottieComposition2) {
                    LottieDrawable.this.setMinAndMaxFrame(str);
                }
            });
            return;
        }
        Marker marker = lottieComposition.getMarker(str);
        if (marker != null) {
            int i2 = (int) marker.startFrame;
            setMinAndMaxFrame(i2, ((int) marker.durationFrames) + i2);
        } else {
            throw new IllegalArgumentException("Cannot find marker with name " + str + StrPool.DOT);
        }
    }

    public void setMinAndMaxProgress(@FloatRange(from = 0.0d, to = 1.0d) final float f2, @FloatRange(from = 0.0d, to = 1.0d) final float f3) {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() { // from class: com.airbnb.lottie.LottieDrawable.13
                @Override // com.airbnb.lottie.LottieDrawable.LazyCompositionTask
                public void run(LottieComposition lottieComposition2) {
                    LottieDrawable.this.setMinAndMaxProgress(f2, f3);
                }
            });
        } else {
            setMinAndMaxFrame((int) MiscUtils.lerp(lottieComposition.getStartFrame(), this.composition.getEndFrame(), f2), (int) MiscUtils.lerp(this.composition.getStartFrame(), this.composition.getEndFrame(), f3));
        }
    }

    public void setMinFrame(final int i2) {
        if (this.composition == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() { // from class: com.airbnb.lottie.LottieDrawable.4
                @Override // com.airbnb.lottie.LottieDrawable.LazyCompositionTask
                public void run(LottieComposition lottieComposition) {
                    LottieDrawable.this.setMinFrame(i2);
                }
            });
        } else {
            this.animator.setMinFrame(i2);
        }
    }

    public void setMinProgress(final float f2) {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() { // from class: com.airbnb.lottie.LottieDrawable.5
                @Override // com.airbnb.lottie.LottieDrawable.LazyCompositionTask
                public void run(LottieComposition lottieComposition2) {
                    LottieDrawable.this.setMinProgress(f2);
                }
            });
        } else {
            setMinFrame((int) MiscUtils.lerp(lottieComposition.getStartFrame(), this.composition.getEndFrame(), f2));
        }
    }

    public void setOutlineMasksAndMattes(boolean z2) {
        if (this.outlineMasksAndMattes == z2) {
            return;
        }
        this.outlineMasksAndMattes = z2;
        CompositionLayer compositionLayer = this.compositionLayer;
        if (compositionLayer != null) {
            compositionLayer.setOutlineMasksAndMattes(z2);
        }
    }

    public void setPerformanceTrackingEnabled(boolean z2) {
        this.performanceTrackingEnabled = z2;
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition != null) {
            lottieComposition.setPerformanceTrackingEnabled(z2);
        }
    }

    public void setProgress(@FloatRange(from = 0.0d, to = 1.0d) final float f2) {
        if (this.composition == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() { // from class: com.airbnb.lottie.LottieDrawable.15
                @Override // com.airbnb.lottie.LottieDrawable.LazyCompositionTask
                public void run(LottieComposition lottieComposition) {
                    LottieDrawable.this.setProgress(f2);
                }
            });
            return;
        }
        L.beginSection("Drawable#setProgress");
        this.animator.setFrame(MiscUtils.lerp(this.composition.getStartFrame(), this.composition.getEndFrame(), f2));
        L.endSection("Drawable#setProgress");
    }

    public void setRepeatCount(int i2) {
        this.animator.setRepeatCount(i2);
    }

    public void setRepeatMode(int i2) {
        this.animator.setRepeatMode(i2);
    }

    public void setSafeMode(boolean z2) {
        this.safeMode = z2;
    }

    public void setScale(float f2) {
        this.scale = f2;
        updateBounds();
    }

    public void setScaleType(ImageView.ScaleType scaleType) {
        this.scaleType = scaleType;
    }

    public void setSpeed(float f2) {
        this.animator.setSpeed(f2);
    }

    public void setSystemAnimationsAreEnabled(Boolean bool) {
        this.systemAnimationsEnabled = bool.booleanValue();
    }

    public void setTextDelegate(TextDelegate textDelegate) {
        this.textDelegate = textDelegate;
    }

    @Override // android.graphics.drawable.Animatable
    @MainThread
    public void start() {
        Drawable.Callback callback = getCallback();
        if (!(callback instanceof View) || ((View) callback).isInEditMode()) {
            return;
        }
        playAnimation();
    }

    @Override // android.graphics.drawable.Animatable
    @MainThread
    public void stop() {
        endAnimation();
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void unscheduleDrawable(@NonNull Drawable drawable, @NonNull Runnable runnable) {
        Drawable.Callback callback = getCallback();
        if (callback == null) {
            return;
        }
        callback.unscheduleDrawable(this, runnable);
    }

    @Nullable
    public Bitmap updateBitmap(String str, @Nullable Bitmap bitmap) {
        ImageAssetManager imageAssetManager = getImageAssetManager();
        if (imageAssetManager == null) {
            Logger.warning("Cannot update bitmap. Most likely the drawable is not added to a View which prevents Lottie from getting a Context.");
            return null;
        }
        Bitmap bitmapUpdateBitmap = imageAssetManager.updateBitmap(str, bitmap);
        invalidateSelf();
        return bitmapUpdateBitmap;
    }

    public boolean useTextGlyphs() {
        return this.textDelegate == null && this.composition.getCharacters().size() > 0;
    }

    public void enableMergePathsForKitKatAndAbove(boolean z2) {
        if (this.enableMergePaths == z2) {
            return;
        }
        this.enableMergePaths = z2;
        if (this.composition != null) {
            buildCompositionLayer();
        }
    }

    public void setMaxFrame(final String str) {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() { // from class: com.airbnb.lottie.LottieDrawable.9
                @Override // com.airbnb.lottie.LottieDrawable.LazyCompositionTask
                public void run(LottieComposition lottieComposition2) {
                    LottieDrawable.this.setMaxFrame(str);
                }
            });
            return;
        }
        Marker marker = lottieComposition.getMarker(str);
        if (marker != null) {
            setMaxFrame((int) (marker.startFrame + marker.durationFrames));
            return;
        }
        throw new IllegalArgumentException("Cannot find marker with name " + str + StrPool.DOT);
    }

    public void setMinFrame(final String str) {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() { // from class: com.airbnb.lottie.LottieDrawable.8
                @Override // com.airbnb.lottie.LottieDrawable.LazyCompositionTask
                public void run(LottieComposition lottieComposition2) {
                    LottieDrawable.this.setMinFrame(str);
                }
            });
            return;
        }
        Marker marker = lottieComposition.getMarker(str);
        if (marker != null) {
            setMinFrame((int) marker.startFrame);
            return;
        }
        throw new IllegalArgumentException("Cannot find marker with name " + str + StrPool.DOT);
    }

    public void setMinAndMaxFrame(final String str, final String str2, final boolean z2) {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() { // from class: com.airbnb.lottie.LottieDrawable.11
                @Override // com.airbnb.lottie.LottieDrawable.LazyCompositionTask
                public void run(LottieComposition lottieComposition2) {
                    LottieDrawable.this.setMinAndMaxFrame(str, str2, z2);
                }
            });
            return;
        }
        Marker marker = lottieComposition.getMarker(str);
        if (marker != null) {
            int i2 = (int) marker.startFrame;
            Marker marker2 = this.composition.getMarker(str2);
            if (str2 != null) {
                setMinAndMaxFrame(i2, (int) (marker2.startFrame + (z2 ? 1.0f : 0.0f)));
                return;
            }
            throw new IllegalArgumentException("Cannot find marker with name " + str2 + StrPool.DOT);
        }
        throw new IllegalArgumentException("Cannot find marker with name " + str + StrPool.DOT);
    }

    public <T> void addValueCallback(KeyPath keyPath, T t2, final SimpleLottieValueCallback<T> simpleLottieValueCallback) {
        addValueCallback(keyPath, (KeyPath) t2, (LottieValueCallback<KeyPath>) new LottieValueCallback<T>() { // from class: com.airbnb.lottie.LottieDrawable.17
            @Override // com.airbnb.lottie.value.LottieValueCallback
            public T getValue(LottieFrameInfo<T> lottieFrameInfo) {
                return (T) simpleLottieValueCallback.getValue(lottieFrameInfo);
            }
        });
    }

    public void setMinAndMaxFrame(final int i2, final int i3) {
        if (this.composition == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() { // from class: com.airbnb.lottie.LottieDrawable.12
                @Override // com.airbnb.lottie.LottieDrawable.LazyCompositionTask
                public void run(LottieComposition lottieComposition) {
                    LottieDrawable.this.setMinAndMaxFrame(i2, i3);
                }
            });
        } else {
            this.animator.setMinAndMaxFrames(i2, i3 + 0.99f);
        }
    }
}
