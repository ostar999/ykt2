package com.easefun.polyv.livecommon.ui.widget.blurview;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* loaded from: classes3.dex */
final class BlockingBlurController implements BlurController {
    private static final int ROUNDING_VALUE = 64;

    @ColorInt
    static final int TRANSPARENT = 0;
    final View blurView;

    @Nullable
    private Drawable frameClearDrawable;
    private boolean hasFixedTransformationMatrix;
    private boolean initialized;
    private Bitmap internalBitmap;
    private Canvas internalCanvas;
    private int overlayColor;
    private final ViewGroup rootView;
    private final float scaleFactor = 8.0f;
    private float blurRadius = 16.0f;
    private float roundingWidthScaleFactor = 1.0f;
    private float roundingHeightScaleFactor = 1.0f;
    private final int[] rootLocation = new int[2];
    private final int[] blurViewLocation = new int[2];
    private final ViewTreeObserver.OnPreDrawListener drawListener = new ViewTreeObserver.OnPreDrawListener() { // from class: com.easefun.polyv.livecommon.ui.widget.blurview.BlockingBlurController.1
        @Override // android.view.ViewTreeObserver.OnPreDrawListener
        public boolean onPreDraw() {
            BlockingBlurController.this.updateBlur();
            return true;
        }
    };
    private boolean blurEnabled = true;
    private final Paint paint = new Paint(2);
    private BlurAlgorithm blurAlgorithm = new NoOpBlurAlgorithm();

    public BlockingBlurController(@NonNull View blurView, @NonNull ViewGroup rootView, @ColorInt int overlayColor) {
        this.rootView = rootView;
        this.blurView = blurView;
        this.overlayColor = overlayColor;
        int measuredWidth = blurView.getMeasuredWidth();
        int measuredHeight = blurView.getMeasuredHeight();
        if (isZeroSized(measuredWidth, measuredHeight)) {
            deferBitmapCreation();
        } else {
            init(measuredWidth, measuredHeight);
        }
    }

    private void allocateBitmap(int measuredWidth, int measuredHeight) {
        int iDownScaleSize = downScaleSize(measuredWidth);
        int iDownScaleSize2 = downScaleSize(measuredHeight);
        int iRoundSize = roundSize(iDownScaleSize);
        int iRoundSize2 = roundSize(iDownScaleSize2);
        this.roundingHeightScaleFactor = iDownScaleSize2 / iRoundSize2;
        this.roundingWidthScaleFactor = iDownScaleSize / iRoundSize;
        this.internalBitmap = Bitmap.createBitmap(iRoundSize, iRoundSize2, this.blurAlgorithm.getSupportedBitmapConfig());
    }

    private void blurAndSave() {
        this.internalBitmap = this.blurAlgorithm.blur(this.internalBitmap, this.blurRadius);
        if (this.blurAlgorithm.canModifyBitmap()) {
            return;
        }
        this.internalCanvas.setBitmap(this.internalBitmap);
    }

    private void deferBitmapCreation() {
        this.blurView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.easefun.polyv.livecommon.ui.widget.blurview.BlockingBlurController.2
            public void legacyRemoveOnGlobalLayoutListener() {
                BlockingBlurController.this.blurView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }

            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                BlockingBlurController.this.blurView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                BlockingBlurController.this.init(BlockingBlurController.this.blurView.getMeasuredWidth(), BlockingBlurController.this.blurView.getMeasuredHeight());
            }
        });
    }

    private int downScaleSize(float value) {
        return (int) Math.ceil(value / 8.0f);
    }

    private boolean isZeroSized(int measuredWidth, int measuredHeight) {
        return downScaleSize((float) measuredHeight) == 0 || downScaleSize((float) measuredWidth) == 0;
    }

    private int roundSize(int value) {
        int i2 = value % 64;
        return i2 == 0 ? value : (value - i2) + 64;
    }

    private void setupInternalCanvasMatrix() {
        this.rootView.getLocationOnScreen(this.rootLocation);
        this.blurView.getLocationOnScreen(this.blurViewLocation);
        int[] iArr = this.blurViewLocation;
        int i2 = iArr[0];
        int[] iArr2 = this.rootLocation;
        int i3 = i2 - iArr2[0];
        int i4 = iArr[1] - iArr2[1];
        float f2 = this.roundingWidthScaleFactor * 8.0f;
        float f3 = this.roundingHeightScaleFactor * 8.0f;
        this.internalCanvas.translate((-i3) / f2, (-i4) / f3);
        this.internalCanvas.scale(1.0f / f2, 1.0f / f3);
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.blurview.BlurController
    public void destroy() {
        setBlurAutoUpdate(false);
        this.blurAlgorithm.destroy();
        this.initialized = false;
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.blurview.BlurController
    public boolean draw(Canvas canvas) {
        if (this.blurEnabled && this.initialized) {
            if (canvas == this.internalCanvas) {
                return false;
            }
            updateBlur();
            canvas.save();
            canvas.scale(this.roundingWidthScaleFactor * 8.0f, this.roundingHeightScaleFactor * 8.0f);
            canvas.drawBitmap(this.internalBitmap, 0.0f, 0.0f, this.paint);
            canvas.restore();
            int i2 = this.overlayColor;
            if (i2 != 0) {
                canvas.drawColor(i2);
            }
        }
        return true;
    }

    public void init(int measuredWidth, int measuredHeight) {
        if (isZeroSized(measuredWidth, measuredHeight)) {
            this.blurView.setWillNotDraw(true);
            return;
        }
        this.blurView.setWillNotDraw(false);
        allocateBitmap(measuredWidth, measuredHeight);
        this.internalCanvas = new Canvas(this.internalBitmap);
        this.initialized = true;
        if (this.hasFixedTransformationMatrix) {
            setupInternalCanvasMatrix();
        }
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.blurview.BlurViewFacade
    public BlurViewFacade setBlurAlgorithm(BlurAlgorithm algorithm) {
        this.blurAlgorithm = algorithm;
        return this;
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.blurview.BlurViewFacade
    public BlurViewFacade setBlurAutoUpdate(final boolean enabled) {
        this.blurView.getViewTreeObserver().removeOnPreDrawListener(this.drawListener);
        if (enabled) {
            this.blurView.getViewTreeObserver().addOnPreDrawListener(this.drawListener);
        }
        return this;
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.blurview.BlurViewFacade
    public BlurViewFacade setBlurEnabled(boolean enabled) {
        this.blurEnabled = enabled;
        setBlurAutoUpdate(enabled);
        this.blurView.invalidate();
        return this;
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.blurview.BlurViewFacade
    public BlurViewFacade setBlurRadius(float radius) {
        this.blurRadius = radius;
        return this;
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.blurview.BlurViewFacade
    public BlurViewFacade setFrameClearDrawable(@Nullable Drawable frameClearDrawable) {
        this.frameClearDrawable = frameClearDrawable;
        return this;
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.blurview.BlurViewFacade
    public BlurViewFacade setHasFixedTransformationMatrix(boolean hasFixedTransformationMatrix) {
        this.hasFixedTransformationMatrix = hasFixedTransformationMatrix;
        return this;
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.blurview.BlurViewFacade
    public BlurViewFacade setOverlayColor(int overlayColor) {
        if (this.overlayColor != overlayColor) {
            this.overlayColor = overlayColor;
            this.blurView.invalidate();
        }
        return this;
    }

    public void updateBlur() {
        if (this.blurEnabled && this.initialized) {
            Drawable drawable = this.frameClearDrawable;
            if (drawable == null) {
                this.internalBitmap.eraseColor(0);
            } else {
                drawable.draw(this.internalCanvas);
            }
            if (this.hasFixedTransformationMatrix) {
                this.rootView.draw(this.internalCanvas);
            } else {
                this.internalCanvas.save();
                setupInternalCanvasMatrix();
                this.rootView.draw(this.internalCanvas);
                this.internalCanvas.restore();
            }
            blurAndSave();
        }
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.blurview.BlurController
    public void updateBlurViewSize() {
        init(this.blurView.getMeasuredWidth(), this.blurView.getMeasuredHeight());
    }
}
