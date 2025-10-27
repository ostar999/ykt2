package com.easefun.polyv.livecommon.ui.widget.menudrawer;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/* loaded from: classes3.dex */
class BuildLayerFrameLayout extends FrameLayout {
    private boolean mAttached;
    private boolean mChanged;
    private boolean mFirst;
    private boolean mHardwareLayersEnabled;

    public BuildLayerFrameLayout(Context context) {
        super(context);
        this.mHardwareLayersEnabled = true;
        this.mFirst = true;
        if (PLVMenuDrawer.USE_TRANSLATIONS) {
            setLayerType(2, null);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (this.mChanged && PLVMenuDrawer.USE_TRANSLATIONS) {
            post(new Runnable() { // from class: com.easefun.polyv.livecommon.ui.widget.menudrawer.BuildLayerFrameLayout.2
                @Override // java.lang.Runnable
                public void run() {
                    if (BuildLayerFrameLayout.this.mAttached) {
                        if (BuildLayerFrameLayout.this.getLayerType() != 2 || BuildLayerFrameLayout.this.mFirst) {
                            BuildLayerFrameLayout.this.mFirst = false;
                            BuildLayerFrameLayout.this.setLayerType(2, null);
                            BuildLayerFrameLayout.this.buildLayer();
                            BuildLayerFrameLayout.this.setLayerType(0, null);
                        }
                    }
                }
            });
            this.mChanged = false;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mAttached = true;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mAttached = false;
    }

    @Override // android.view.View
    public void onSizeChanged(int w2, int h2, int oldw, int oldh) {
        super.onSizeChanged(w2, h2, oldw, oldh);
        if (PLVMenuDrawer.USE_TRANSLATIONS && this.mHardwareLayersEnabled) {
            post(new Runnable() { // from class: com.easefun.polyv.livecommon.ui.widget.menudrawer.BuildLayerFrameLayout.1
                @Override // java.lang.Runnable
                public void run() {
                    BuildLayerFrameLayout.this.mChanged = true;
                    BuildLayerFrameLayout.this.invalidate();
                }
            });
        }
    }

    public void setHardwareLayersEnabled(boolean enabled) {
        this.mHardwareLayersEnabled = enabled;
    }

    public BuildLayerFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mHardwareLayersEnabled = true;
        this.mFirst = true;
        if (PLVMenuDrawer.USE_TRANSLATIONS) {
            setLayerType(2, null);
        }
    }

    public BuildLayerFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mHardwareLayersEnabled = true;
        this.mFirst = true;
        if (PLVMenuDrawer.USE_TRANSLATIONS) {
            setLayerType(2, null);
        }
    }
}
