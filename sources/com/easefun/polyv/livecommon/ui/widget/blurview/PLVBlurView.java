package com.easefun.polyv.livecommon.ui.widget.blurview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import com.easefun.polyv.livecommon.R;

/* loaded from: classes3.dex */
public class PLVBlurView extends FrameLayout {
    private static final String TAG = "PLVBlurView";
    BlurController blurController;

    @ColorInt
    private int overlayColor;

    public PLVBlurView(Context context) {
        super(context);
        this.blurController = new NoOpController();
        init(null, 0);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attrs, R.styleable.PLVBlurView, defStyleAttr, 0);
        this.overlayColor = typedArrayObtainStyledAttributes.getColor(R.styleable.PLVBlurView_blurOverlayColor, 0);
        typedArrayObtainStyledAttributes.recycle();
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        if (this.blurController.draw(canvas)) {
            super.draw(canvas);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (isHardwareAccelerated()) {
            this.blurController.setBlurAutoUpdate(true);
        } else {
            Log.e(TAG, "BlurView can't be used in not hardware-accelerated window!");
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.blurController.setBlurAutoUpdate(false);
    }

    @Override // android.view.View
    public void onSizeChanged(int w2, int h2, int oldw, int oldh) {
        super.onSizeChanged(w2, h2, oldw, oldh);
        this.blurController.updateBlurViewSize();
    }

    public BlurViewFacade setBlurAutoUpdate(boolean enabled) {
        return this.blurController.setBlurAutoUpdate(enabled);
    }

    public BlurViewFacade setBlurEnabled(boolean enabled) {
        return this.blurController.setBlurEnabled(enabled);
    }

    public BlurViewFacade setBlurRadius(float radius) {
        return this.blurController.setBlurRadius(radius);
    }

    public BlurViewFacade setOverlayColor(@ColorInt int overlayColor) {
        this.overlayColor = overlayColor;
        return this.blurController.setOverlayColor(overlayColor);
    }

    public BlurViewFacade setupWith(@NonNull ViewGroup rootView) {
        BlockingBlurController blockingBlurController = new BlockingBlurController(this, rootView, this.overlayColor);
        this.blurController.destroy();
        this.blurController = blockingBlurController;
        return blockingBlurController;
    }

    public PLVBlurView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.blurController = new NoOpController();
        init(attrs, 0);
    }

    public PLVBlurView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.blurController = new NoOpController();
        init(attrs, defStyleAttr);
    }
}
