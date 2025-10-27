package com.airbnb.lottie;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.AttrRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.FloatRange;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.view.ViewCompat;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.utils.Logger;
import com.airbnb.lottie.utils.Utils;
import com.airbnb.lottie.value.LottieFrameInfo;
import com.airbnb.lottie.value.LottieValueCallback;
import com.airbnb.lottie.value.SimpleLottieValueCallback;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

/* loaded from: classes.dex */
public class LottieAnimationView extends AppCompatImageView {
    private static final LottieListener<Throwable> DEFAULT_FAILURE_LISTENER = new LottieListener<Throwable>() { // from class: com.airbnb.lottie.LottieAnimationView.1
        @Override // com.airbnb.lottie.LottieListener
        public void onResult(Throwable th) {
            if (!Utils.isNetworkException(th)) {
                throw new IllegalStateException("Unable to parse composition", th);
            }
            Logger.warning("Unable to load composition.", th);
        }
    };
    private static final String TAG = "LottieAnimationView";
    private String animationName;

    @RawRes
    private int animationResId;
    private boolean autoPlay;
    private int buildDrawingCacheDepth;
    private boolean cacheComposition;

    @Nullable
    private LottieComposition composition;

    @Nullable
    private LottieTask<LottieComposition> compositionTask;

    @Nullable
    private LottieListener<Throwable> failureListener;

    @DrawableRes
    private int fallbackResource;
    private boolean isInitialized;
    private final LottieListener<LottieComposition> loadedListener;
    private final LottieDrawable lottieDrawable;
    private final Set<LottieOnCompositionLoadedListener> lottieOnCompositionLoadedListeners;
    private boolean playAnimationWhenShown;
    private RenderMode renderMode;
    private boolean wasAnimatingWhenDetached;
    private boolean wasAnimatingWhenNotShown;
    private final LottieListener<Throwable> wrappedFailureListener;

    /* renamed from: com.airbnb.lottie.LottieAnimationView$7, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass7 {
        static final /* synthetic */ int[] $SwitchMap$com$airbnb$lottie$RenderMode;

        static {
            int[] iArr = new int[RenderMode.values().length];
            $SwitchMap$com$airbnb$lottie$RenderMode = iArr;
            try {
                iArr[RenderMode.HARDWARE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$airbnb$lottie$RenderMode[RenderMode.SOFTWARE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$airbnb$lottie$RenderMode[RenderMode.AUTOMATIC.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: com.airbnb.lottie.LottieAnimationView.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int i2) {
                return new SavedState[i2];
            }
        };
        String animationName;
        int animationResId;
        String imageAssetsFolder;
        boolean isAnimating;
        float progress;
        int repeatCount;
        int repeatMode;

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i2) {
            super.writeToParcel(parcel, i2);
            parcel.writeString(this.animationName);
            parcel.writeFloat(this.progress);
            parcel.writeInt(this.isAnimating ? 1 : 0);
            parcel.writeString(this.imageAssetsFolder);
            parcel.writeInt(this.repeatMode);
            parcel.writeInt(this.repeatCount);
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        private SavedState(Parcel parcel) {
            super(parcel);
            this.animationName = parcel.readString();
            this.progress = parcel.readFloat();
            this.isAnimating = parcel.readInt() == 1;
            this.imageAssetsFolder = parcel.readString();
            this.repeatMode = parcel.readInt();
            this.repeatCount = parcel.readInt();
        }
    }

    public LottieAnimationView(Context context) {
        super(context);
        this.loadedListener = new LottieListener<LottieComposition>() { // from class: com.airbnb.lottie.LottieAnimationView.2
            @Override // com.airbnb.lottie.LottieListener
            public void onResult(LottieComposition lottieComposition) {
                LottieAnimationView.this.setComposition(lottieComposition);
            }
        };
        this.wrappedFailureListener = new LottieListener<Throwable>() { // from class: com.airbnb.lottie.LottieAnimationView.3
            @Override // com.airbnb.lottie.LottieListener
            public void onResult(Throwable th) {
                if (LottieAnimationView.this.fallbackResource != 0) {
                    LottieAnimationView lottieAnimationView = LottieAnimationView.this;
                    lottieAnimationView.setImageResource(lottieAnimationView.fallbackResource);
                }
                (LottieAnimationView.this.failureListener == null ? LottieAnimationView.DEFAULT_FAILURE_LISTENER : LottieAnimationView.this.failureListener).onResult(th);
            }
        };
        this.fallbackResource = 0;
        this.lottieDrawable = new LottieDrawable();
        this.playAnimationWhenShown = false;
        this.wasAnimatingWhenNotShown = false;
        this.wasAnimatingWhenDetached = false;
        this.autoPlay = false;
        this.cacheComposition = true;
        this.renderMode = RenderMode.AUTOMATIC;
        this.lottieOnCompositionLoadedListeners = new HashSet();
        this.buildDrawingCacheDepth = 0;
        init(null, R.attr.lottieAnimationViewStyle);
    }

    private void cancelLoaderTask() {
        LottieTask<LottieComposition> lottieTask = this.compositionTask;
        if (lottieTask != null) {
            lottieTask.removeListener(this.loadedListener);
            this.compositionTask.removeFailureListener(this.wrappedFailureListener);
        }
    }

    private void clearComposition() {
        this.composition = null;
        this.lottieDrawable.clearComposition();
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void enableOrDisableHardwareLayer() {
        /*
            r5 = this;
            int[] r0 = com.airbnb.lottie.LottieAnimationView.AnonymousClass7.$SwitchMap$com$airbnb$lottie$RenderMode
            com.airbnb.lottie.RenderMode r1 = r5.renderMode
            int r1 = r1.ordinal()
            r0 = r0[r1]
            r1 = 2
            r2 = 1
            if (r0 == r2) goto L41
            if (r0 == r1) goto L13
            r3 = 3
            if (r0 == r3) goto L15
        L13:
            r1 = r2
            goto L41
        L15:
            com.airbnb.lottie.LottieComposition r0 = r5.composition
            r3 = 0
            if (r0 == 0) goto L27
            boolean r0 = r0.hasDashPattern()
            if (r0 == 0) goto L27
            int r0 = android.os.Build.VERSION.SDK_INT
            r4 = 28
            if (r0 >= r4) goto L27
            goto L3f
        L27:
            com.airbnb.lottie.LottieComposition r0 = r5.composition
            if (r0 == 0) goto L33
            int r0 = r0.getMaskAndMatteCount()
            r4 = 4
            if (r0 <= r4) goto L33
            goto L3f
        L33:
            int r0 = android.os.Build.VERSION.SDK_INT
            r4 = 24
            if (r0 == r4) goto L3f
            r4 = 25
            if (r0 != r4) goto L3e
            goto L3f
        L3e:
            r3 = r2
        L3f:
            if (r3 == 0) goto L13
        L41:
            int r0 = r5.getLayerType()
            if (r1 == r0) goto L4b
            r0 = 0
            r5.setLayerType(r1, r0)
        L4b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.LottieAnimationView.enableOrDisableHardwareLayer():void");
    }

    private LottieTask<LottieComposition> fromAssets(final String str) {
        return isInEditMode() ? new LottieTask<>(new Callable<LottieResult<LottieComposition>>() { // from class: com.airbnb.lottie.LottieAnimationView.5
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public LottieResult<LottieComposition> call() {
                return LottieAnimationView.this.cacheComposition ? LottieCompositionFactory.fromAssetSync(LottieAnimationView.this.getContext(), str) : LottieCompositionFactory.fromAssetSync(LottieAnimationView.this.getContext(), str, null);
            }
        }, true) : this.cacheComposition ? LottieCompositionFactory.fromAsset(getContext(), str) : LottieCompositionFactory.fromAsset(getContext(), str, null);
    }

    private LottieTask<LottieComposition> fromRawRes(@RawRes final int i2) {
        return isInEditMode() ? new LottieTask<>(new Callable<LottieResult<LottieComposition>>() { // from class: com.airbnb.lottie.LottieAnimationView.4
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public LottieResult<LottieComposition> call() {
                return LottieAnimationView.this.cacheComposition ? LottieCompositionFactory.fromRawResSync(LottieAnimationView.this.getContext(), i2) : LottieCompositionFactory.fromRawResSync(LottieAnimationView.this.getContext(), i2, null);
            }
        }, true) : this.cacheComposition ? LottieCompositionFactory.fromRawRes(getContext(), i2) : LottieCompositionFactory.fromRawRes(getContext(), i2, null);
    }

    private void init(@Nullable AttributeSet attributeSet, @AttrRes int i2) {
        String string;
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.LottieAnimationView, i2, 0);
        this.cacheComposition = typedArrayObtainStyledAttributes.getBoolean(R.styleable.LottieAnimationView_lottie_cacheComposition, true);
        int i3 = R.styleable.LottieAnimationView_lottie_rawRes;
        boolean zHasValue = typedArrayObtainStyledAttributes.hasValue(i3);
        int i4 = R.styleable.LottieAnimationView_lottie_fileName;
        boolean zHasValue2 = typedArrayObtainStyledAttributes.hasValue(i4);
        int i5 = R.styleable.LottieAnimationView_lottie_url;
        boolean zHasValue3 = typedArrayObtainStyledAttributes.hasValue(i5);
        if (zHasValue && zHasValue2) {
            throw new IllegalArgumentException("lottie_rawRes and lottie_fileName cannot be used at the same time. Please use only one at once.");
        }
        if (zHasValue) {
            int resourceId = typedArrayObtainStyledAttributes.getResourceId(i3, 0);
            if (resourceId != 0) {
                setAnimation(resourceId);
            }
        } else if (zHasValue2) {
            String string2 = typedArrayObtainStyledAttributes.getString(i4);
            if (string2 != null) {
                setAnimation(string2);
            }
        } else if (zHasValue3 && (string = typedArrayObtainStyledAttributes.getString(i5)) != null) {
            setAnimationFromUrl(string);
        }
        setFallbackResource(typedArrayObtainStyledAttributes.getResourceId(R.styleable.LottieAnimationView_lottie_fallbackRes, 0));
        if (typedArrayObtainStyledAttributes.getBoolean(R.styleable.LottieAnimationView_lottie_autoPlay, false)) {
            this.wasAnimatingWhenDetached = true;
            this.autoPlay = true;
        }
        if (typedArrayObtainStyledAttributes.getBoolean(R.styleable.LottieAnimationView_lottie_loop, false)) {
            this.lottieDrawable.setRepeatCount(-1);
        }
        int i6 = R.styleable.LottieAnimationView_lottie_repeatMode;
        if (typedArrayObtainStyledAttributes.hasValue(i6)) {
            setRepeatMode(typedArrayObtainStyledAttributes.getInt(i6, 1));
        }
        int i7 = R.styleable.LottieAnimationView_lottie_repeatCount;
        if (typedArrayObtainStyledAttributes.hasValue(i7)) {
            setRepeatCount(typedArrayObtainStyledAttributes.getInt(i7, -1));
        }
        int i8 = R.styleable.LottieAnimationView_lottie_speed;
        if (typedArrayObtainStyledAttributes.hasValue(i8)) {
            setSpeed(typedArrayObtainStyledAttributes.getFloat(i8, 1.0f));
        }
        setImageAssetsFolder(typedArrayObtainStyledAttributes.getString(R.styleable.LottieAnimationView_lottie_imageAssetsFolder));
        setProgress(typedArrayObtainStyledAttributes.getFloat(R.styleable.LottieAnimationView_lottie_progress, 0.0f));
        enableMergePathsForKitKatAndAbove(typedArrayObtainStyledAttributes.getBoolean(R.styleable.LottieAnimationView_lottie_enableMergePathsForKitKatAndAbove, false));
        int i9 = R.styleable.LottieAnimationView_lottie_colorFilter;
        if (typedArrayObtainStyledAttributes.hasValue(i9)) {
            addValueCallback(new KeyPath("**"), (KeyPath) LottieProperty.COLOR_FILTER, (LottieValueCallback<KeyPath>) new LottieValueCallback(new SimpleColorFilter(typedArrayObtainStyledAttributes.getColor(i9, 0))));
        }
        int i10 = R.styleable.LottieAnimationView_lottie_scale;
        if (typedArrayObtainStyledAttributes.hasValue(i10)) {
            this.lottieDrawable.setScale(typedArrayObtainStyledAttributes.getFloat(i10, 1.0f));
        }
        int i11 = R.styleable.LottieAnimationView_lottie_renderMode;
        if (typedArrayObtainStyledAttributes.hasValue(i11)) {
            RenderMode renderMode = RenderMode.AUTOMATIC;
            int iOrdinal = typedArrayObtainStyledAttributes.getInt(i11, renderMode.ordinal());
            if (iOrdinal >= RenderMode.values().length) {
                iOrdinal = renderMode.ordinal();
            }
            setRenderMode(RenderMode.values()[iOrdinal]);
        }
        if (getScaleType() != null) {
            this.lottieDrawable.setScaleType(getScaleType());
        }
        typedArrayObtainStyledAttributes.recycle();
        this.lottieDrawable.setSystemAnimationsAreEnabled(Boolean.valueOf(Utils.getAnimationScale(getContext()) != 0.0f));
        enableOrDisableHardwareLayer();
        this.isInitialized = true;
    }

    private void setCompositionTask(LottieTask<LottieComposition> lottieTask) {
        clearComposition();
        cancelLoaderTask();
        this.compositionTask = lottieTask.addListener(this.loadedListener).addFailureListener(this.wrappedFailureListener);
    }

    public void addAnimatorListener(Animator.AnimatorListener animatorListener) {
        this.lottieDrawable.addAnimatorListener(animatorListener);
    }

    @RequiresApi(api = 19)
    public void addAnimatorPauseListener(Animator.AnimatorPauseListener animatorPauseListener) {
        this.lottieDrawable.addAnimatorPauseListener(animatorPauseListener);
    }

    public void addAnimatorUpdateListener(ValueAnimator.AnimatorUpdateListener animatorUpdateListener) {
        this.lottieDrawable.addAnimatorUpdateListener(animatorUpdateListener);
    }

    public boolean addLottieOnCompositionLoadedListener(@NonNull LottieOnCompositionLoadedListener lottieOnCompositionLoadedListener) {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition != null) {
            lottieOnCompositionLoadedListener.onCompositionLoaded(lottieComposition);
        }
        return this.lottieOnCompositionLoadedListeners.add(lottieOnCompositionLoadedListener);
    }

    public <T> void addValueCallback(KeyPath keyPath, T t2, LottieValueCallback<T> lottieValueCallback) {
        this.lottieDrawable.addValueCallback(keyPath, (KeyPath) t2, (LottieValueCallback<KeyPath>) lottieValueCallback);
    }

    @Override // android.view.View
    public void buildDrawingCache(boolean z2) {
        L.beginSection("buildDrawingCache");
        this.buildDrawingCacheDepth++;
        super.buildDrawingCache(z2);
        if (this.buildDrawingCacheDepth == 1 && getWidth() > 0 && getHeight() > 0 && getLayerType() == 1 && getDrawingCache(z2) == null) {
            setRenderMode(RenderMode.HARDWARE);
        }
        this.buildDrawingCacheDepth--;
        L.endSection("buildDrawingCache");
    }

    @MainThread
    public void cancelAnimation() {
        this.wasAnimatingWhenDetached = false;
        this.wasAnimatingWhenNotShown = false;
        this.playAnimationWhenShown = false;
        this.lottieDrawable.cancelAnimation();
        enableOrDisableHardwareLayer();
    }

    public void disableExtraScaleModeInFitXY() {
        this.lottieDrawable.disableExtraScaleModeInFitXY();
    }

    public void enableMergePathsForKitKatAndAbove(boolean z2) {
        this.lottieDrawable.enableMergePathsForKitKatAndAbove(z2);
    }

    @Nullable
    public LottieComposition getComposition() {
        return this.composition;
    }

    public long getDuration() {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition != null) {
            return (long) lottieComposition.getDuration();
        }
        return 0L;
    }

    public int getFrame() {
        return this.lottieDrawable.getFrame();
    }

    @Nullable
    public String getImageAssetsFolder() {
        return this.lottieDrawable.getImageAssetsFolder();
    }

    public float getMaxFrame() {
        return this.lottieDrawable.getMaxFrame();
    }

    public float getMinFrame() {
        return this.lottieDrawable.getMinFrame();
    }

    @Nullable
    public PerformanceTracker getPerformanceTracker() {
        return this.lottieDrawable.getPerformanceTracker();
    }

    @FloatRange(from = 0.0d, to = 1.0d)
    public float getProgress() {
        return this.lottieDrawable.getProgress();
    }

    public int getRepeatCount() {
        return this.lottieDrawable.getRepeatCount();
    }

    public int getRepeatMode() {
        return this.lottieDrawable.getRepeatMode();
    }

    public float getScale() {
        return this.lottieDrawable.getScale();
    }

    public float getSpeed() {
        return this.lottieDrawable.getSpeed();
    }

    public boolean hasMasks() {
        return this.lottieDrawable.hasMasks();
    }

    public boolean hasMatte() {
        return this.lottieDrawable.hasMatte();
    }

    @Override // android.widget.ImageView, android.view.View, android.graphics.drawable.Drawable.Callback
    public void invalidateDrawable(@NonNull Drawable drawable) {
        Drawable drawable2 = getDrawable();
        LottieDrawable lottieDrawable = this.lottieDrawable;
        if (drawable2 == lottieDrawable) {
            super.invalidateDrawable(lottieDrawable);
        } else {
            super.invalidateDrawable(drawable);
        }
    }

    public boolean isAnimating() {
        return this.lottieDrawable.isAnimating();
    }

    public boolean isMergePathsEnabledForKitKatAndAbove() {
        return this.lottieDrawable.isMergePathsEnabledForKitKatAndAbove();
    }

    @Deprecated
    public void loop(boolean z2) {
        this.lottieDrawable.setRepeatCount(z2 ? -1 : 0);
    }

    @Override // android.widget.ImageView, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (isInEditMode()) {
            return;
        }
        if (this.autoPlay || this.wasAnimatingWhenDetached) {
            playAnimation();
            this.autoPlay = false;
            this.wasAnimatingWhenDetached = false;
        }
    }

    @Override // android.widget.ImageView, android.view.View
    public void onDetachedFromWindow() {
        if (isAnimating()) {
            cancelAnimation();
            this.wasAnimatingWhenDetached = true;
        }
        super.onDetachedFromWindow();
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        String str = savedState.animationName;
        this.animationName = str;
        if (!TextUtils.isEmpty(str)) {
            setAnimation(this.animationName);
        }
        int i2 = savedState.animationResId;
        this.animationResId = i2;
        if (i2 != 0) {
            setAnimation(i2);
        }
        setProgress(savedState.progress);
        if (savedState.isAnimating) {
            playAnimation();
        }
        this.lottieDrawable.setImagesAssetsFolder(savedState.imageAssetsFolder);
        setRepeatMode(savedState.repeatMode);
        setRepeatCount(savedState.repeatCount);
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.animationName = this.animationName;
        savedState.animationResId = this.animationResId;
        savedState.progress = this.lottieDrawable.getProgress();
        savedState.isAnimating = this.lottieDrawable.isAnimating() || (!ViewCompat.isAttachedToWindow(this) && this.wasAnimatingWhenDetached);
        savedState.imageAssetsFolder = this.lottieDrawable.getImageAssetsFolder();
        savedState.repeatMode = this.lottieDrawable.getRepeatMode();
        savedState.repeatCount = this.lottieDrawable.getRepeatCount();
        return savedState;
    }

    @Override // android.view.View
    public void onVisibilityChanged(@NonNull View view, int i2) {
        if (this.isInitialized) {
            if (!isShown()) {
                if (isAnimating()) {
                    pauseAnimation();
                    this.wasAnimatingWhenNotShown = true;
                    return;
                }
                return;
            }
            if (this.wasAnimatingWhenNotShown) {
                resumeAnimation();
            } else if (this.playAnimationWhenShown) {
                playAnimation();
            }
            this.wasAnimatingWhenNotShown = false;
            this.playAnimationWhenShown = false;
        }
    }

    @MainThread
    public void pauseAnimation() {
        this.autoPlay = false;
        this.wasAnimatingWhenDetached = false;
        this.wasAnimatingWhenNotShown = false;
        this.playAnimationWhenShown = false;
        this.lottieDrawable.pauseAnimation();
        enableOrDisableHardwareLayer();
    }

    @MainThread
    public void playAnimation() {
        if (!isShown()) {
            this.playAnimationWhenShown = true;
        } else {
            this.lottieDrawable.playAnimation();
            enableOrDisableHardwareLayer();
        }
    }

    public void removeAllAnimatorListeners() {
        this.lottieDrawable.removeAllAnimatorListeners();
    }

    public void removeAllLottieOnCompositionLoadedListener() {
        this.lottieOnCompositionLoadedListeners.clear();
    }

    public void removeAllUpdateListeners() {
        this.lottieDrawable.removeAllUpdateListeners();
    }

    public void removeAnimatorListener(Animator.AnimatorListener animatorListener) {
        this.lottieDrawable.removeAnimatorListener(animatorListener);
    }

    @RequiresApi(api = 19)
    public void removeAnimatorPauseListener(Animator.AnimatorPauseListener animatorPauseListener) {
        this.lottieDrawable.removeAnimatorPauseListener(animatorPauseListener);
    }

    public boolean removeLottieOnCompositionLoadedListener(@NonNull LottieOnCompositionLoadedListener lottieOnCompositionLoadedListener) {
        return this.lottieOnCompositionLoadedListeners.remove(lottieOnCompositionLoadedListener);
    }

    public void removeUpdateListener(ValueAnimator.AnimatorUpdateListener animatorUpdateListener) {
        this.lottieDrawable.removeAnimatorUpdateListener(animatorUpdateListener);
    }

    public List<KeyPath> resolveKeyPath(KeyPath keyPath) {
        return this.lottieDrawable.resolveKeyPath(keyPath);
    }

    @MainThread
    public void resumeAnimation() {
        if (isShown()) {
            this.lottieDrawable.resumeAnimation();
            enableOrDisableHardwareLayer();
        } else {
            this.playAnimationWhenShown = false;
            this.wasAnimatingWhenNotShown = true;
        }
    }

    public void reverseAnimationSpeed() {
        this.lottieDrawable.reverseAnimationSpeed();
    }

    public void setAnimation(@RawRes int i2) {
        this.animationResId = i2;
        this.animationName = null;
        setCompositionTask(fromRawRes(i2));
    }

    @Deprecated
    public void setAnimationFromJson(String str) {
        setAnimationFromJson(str, null);
    }

    public void setAnimationFromUrl(String str) {
        setCompositionTask(this.cacheComposition ? LottieCompositionFactory.fromUrl(getContext(), str) : LottieCompositionFactory.fromUrl(getContext(), str, null));
    }

    public void setApplyingOpacityToLayersEnabled(boolean z2) {
        this.lottieDrawable.setApplyingOpacityToLayersEnabled(z2);
    }

    public void setCacheComposition(boolean z2) {
        this.cacheComposition = z2;
    }

    public void setComposition(@NonNull LottieComposition lottieComposition) {
        if (L.DBG) {
            Log.v(TAG, "Set Composition \n" + lottieComposition);
        }
        this.lottieDrawable.setCallback(this);
        this.composition = lottieComposition;
        boolean composition = this.lottieDrawable.setComposition(lottieComposition);
        enableOrDisableHardwareLayer();
        if (getDrawable() != this.lottieDrawable || composition) {
            onVisibilityChanged(this, getVisibility());
            requestLayout();
            Iterator<LottieOnCompositionLoadedListener> it = this.lottieOnCompositionLoadedListeners.iterator();
            while (it.hasNext()) {
                it.next().onCompositionLoaded(lottieComposition);
            }
        }
    }

    public void setFailureListener(@Nullable LottieListener<Throwable> lottieListener) {
        this.failureListener = lottieListener;
    }

    public void setFallbackResource(@DrawableRes int i2) {
        this.fallbackResource = i2;
    }

    public void setFontAssetDelegate(FontAssetDelegate fontAssetDelegate) {
        this.lottieDrawable.setFontAssetDelegate(fontAssetDelegate);
    }

    public void setFrame(int i2) {
        this.lottieDrawable.setFrame(i2);
    }

    public void setImageAssetDelegate(ImageAssetDelegate imageAssetDelegate) {
        this.lottieDrawable.setImageAssetDelegate(imageAssetDelegate);
    }

    public void setImageAssetsFolder(String str) {
        this.lottieDrawable.setImagesAssetsFolder(str);
    }

    @Override // androidx.appcompat.widget.AppCompatImageView, android.widget.ImageView
    public void setImageBitmap(Bitmap bitmap) {
        cancelLoaderTask();
        super.setImageBitmap(bitmap);
    }

    @Override // androidx.appcompat.widget.AppCompatImageView, android.widget.ImageView
    public void setImageDrawable(Drawable drawable) {
        cancelLoaderTask();
        super.setImageDrawable(drawable);
    }

    @Override // androidx.appcompat.widget.AppCompatImageView, android.widget.ImageView
    public void setImageResource(int i2) {
        cancelLoaderTask();
        super.setImageResource(i2);
    }

    public void setMaxFrame(int i2) {
        this.lottieDrawable.setMaxFrame(i2);
    }

    public void setMaxProgress(@FloatRange(from = 0.0d, to = 1.0d) float f2) {
        this.lottieDrawable.setMaxProgress(f2);
    }

    public void setMinAndMaxFrame(String str) {
        this.lottieDrawable.setMinAndMaxFrame(str);
    }

    public void setMinAndMaxProgress(@FloatRange(from = 0.0d, to = 1.0d) float f2, @FloatRange(from = 0.0d, to = 1.0d) float f3) {
        this.lottieDrawable.setMinAndMaxProgress(f2, f3);
    }

    public void setMinFrame(int i2) {
        this.lottieDrawable.setMinFrame(i2);
    }

    public void setMinProgress(float f2) {
        this.lottieDrawable.setMinProgress(f2);
    }

    public void setOutlineMasksAndMattes(boolean z2) {
        this.lottieDrawable.setOutlineMasksAndMattes(z2);
    }

    public void setPerformanceTrackingEnabled(boolean z2) {
        this.lottieDrawable.setPerformanceTrackingEnabled(z2);
    }

    public void setProgress(@FloatRange(from = 0.0d, to = 1.0d) float f2) {
        this.lottieDrawable.setProgress(f2);
    }

    public void setRenderMode(RenderMode renderMode) {
        this.renderMode = renderMode;
        enableOrDisableHardwareLayer();
    }

    public void setRepeatCount(int i2) {
        this.lottieDrawable.setRepeatCount(i2);
    }

    public void setRepeatMode(int i2) {
        this.lottieDrawable.setRepeatMode(i2);
    }

    public void setSafeMode(boolean z2) {
        this.lottieDrawable.setSafeMode(z2);
    }

    public void setScale(float f2) {
        this.lottieDrawable.setScale(f2);
        if (getDrawable() == this.lottieDrawable) {
            setImageDrawable(null);
            setImageDrawable(this.lottieDrawable);
        }
    }

    @Override // android.widget.ImageView
    public void setScaleType(ImageView.ScaleType scaleType) {
        super.setScaleType(scaleType);
        LottieDrawable lottieDrawable = this.lottieDrawable;
        if (lottieDrawable != null) {
            lottieDrawable.setScaleType(scaleType);
        }
    }

    public void setSpeed(float f2) {
        this.lottieDrawable.setSpeed(f2);
    }

    public void setTextDelegate(TextDelegate textDelegate) {
        this.lottieDrawable.setTextDelegate(textDelegate);
    }

    @Nullable
    public Bitmap updateBitmap(String str, @Nullable Bitmap bitmap) {
        return this.lottieDrawable.updateBitmap(str, bitmap);
    }

    public <T> void addValueCallback(KeyPath keyPath, T t2, final SimpleLottieValueCallback<T> simpleLottieValueCallback) {
        this.lottieDrawable.addValueCallback(keyPath, (KeyPath) t2, (LottieValueCallback<KeyPath>) new LottieValueCallback<T>() { // from class: com.airbnb.lottie.LottieAnimationView.6
            @Override // com.airbnb.lottie.value.LottieValueCallback
            public T getValue(LottieFrameInfo<T> lottieFrameInfo) {
                return (T) simpleLottieValueCallback.getValue(lottieFrameInfo);
            }
        });
    }

    public void setAnimationFromJson(String str, @Nullable String str2) {
        setAnimation(new ByteArrayInputStream(str.getBytes()), str2);
    }

    public void setMaxFrame(String str) {
        this.lottieDrawable.setMaxFrame(str);
    }

    public void setMinAndMaxFrame(String str, String str2, boolean z2) {
        this.lottieDrawable.setMinAndMaxFrame(str, str2, z2);
    }

    public void setMinFrame(String str) {
        this.lottieDrawable.setMinFrame(str);
    }

    public void setMinAndMaxFrame(int i2, int i3) {
        this.lottieDrawable.setMinAndMaxFrame(i2, i3);
    }

    public void setAnimation(String str) {
        this.animationName = str;
        this.animationResId = 0;
        setCompositionTask(fromAssets(str));
    }

    public void setAnimationFromUrl(String str, @Nullable String str2) {
        setCompositionTask(LottieCompositionFactory.fromUrl(getContext(), str, str2));
    }

    public void setAnimation(InputStream inputStream, @Nullable String str) {
        setCompositionTask(LottieCompositionFactory.fromJsonInputStream(inputStream, str));
    }

    public LottieAnimationView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.loadedListener = new LottieListener<LottieComposition>() { // from class: com.airbnb.lottie.LottieAnimationView.2
            @Override // com.airbnb.lottie.LottieListener
            public void onResult(LottieComposition lottieComposition) {
                LottieAnimationView.this.setComposition(lottieComposition);
            }
        };
        this.wrappedFailureListener = new LottieListener<Throwable>() { // from class: com.airbnb.lottie.LottieAnimationView.3
            @Override // com.airbnb.lottie.LottieListener
            public void onResult(Throwable th) {
                if (LottieAnimationView.this.fallbackResource != 0) {
                    LottieAnimationView lottieAnimationView = LottieAnimationView.this;
                    lottieAnimationView.setImageResource(lottieAnimationView.fallbackResource);
                }
                (LottieAnimationView.this.failureListener == null ? LottieAnimationView.DEFAULT_FAILURE_LISTENER : LottieAnimationView.this.failureListener).onResult(th);
            }
        };
        this.fallbackResource = 0;
        this.lottieDrawable = new LottieDrawable();
        this.playAnimationWhenShown = false;
        this.wasAnimatingWhenNotShown = false;
        this.wasAnimatingWhenDetached = false;
        this.autoPlay = false;
        this.cacheComposition = true;
        this.renderMode = RenderMode.AUTOMATIC;
        this.lottieOnCompositionLoadedListeners = new HashSet();
        this.buildDrawingCacheDepth = 0;
        init(attributeSet, R.attr.lottieAnimationViewStyle);
    }

    public LottieAnimationView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.loadedListener = new LottieListener<LottieComposition>() { // from class: com.airbnb.lottie.LottieAnimationView.2
            @Override // com.airbnb.lottie.LottieListener
            public void onResult(LottieComposition lottieComposition) {
                LottieAnimationView.this.setComposition(lottieComposition);
            }
        };
        this.wrappedFailureListener = new LottieListener<Throwable>() { // from class: com.airbnb.lottie.LottieAnimationView.3
            @Override // com.airbnb.lottie.LottieListener
            public void onResult(Throwable th) {
                if (LottieAnimationView.this.fallbackResource != 0) {
                    LottieAnimationView lottieAnimationView = LottieAnimationView.this;
                    lottieAnimationView.setImageResource(lottieAnimationView.fallbackResource);
                }
                (LottieAnimationView.this.failureListener == null ? LottieAnimationView.DEFAULT_FAILURE_LISTENER : LottieAnimationView.this.failureListener).onResult(th);
            }
        };
        this.fallbackResource = 0;
        this.lottieDrawable = new LottieDrawable();
        this.playAnimationWhenShown = false;
        this.wasAnimatingWhenNotShown = false;
        this.wasAnimatingWhenDetached = false;
        this.autoPlay = false;
        this.cacheComposition = true;
        this.renderMode = RenderMode.AUTOMATIC;
        this.lottieOnCompositionLoadedListeners = new HashSet();
        this.buildDrawingCacheDepth = 0;
        init(attributeSet, i2);
    }
}
