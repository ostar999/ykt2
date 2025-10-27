package com.google.android.exoplayer2.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import androidx.annotation.Nullable;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes3.dex */
public final class AspectRatioFrameLayout extends FrameLayout {
    private static final float MAX_ASPECT_RATIO_DEFORMATION_FRACTION = 0.01f;
    public static final int RESIZE_MODE_FILL = 3;
    public static final int RESIZE_MODE_FIT = 0;
    public static final int RESIZE_MODE_FIXED_HEIGHT = 2;
    public static final int RESIZE_MODE_FIXED_WIDTH = 1;
    public static final int RESIZE_MODE_ZOOM = 4;

    @Nullable
    private AspectRatioListener aspectRatioListener;
    private final AspectRatioUpdateDispatcher aspectRatioUpdateDispatcher;
    private int resizeMode;
    private float videoAspectRatio;

    public interface AspectRatioListener {
        void onAspectRatioUpdated(float f2, float f3, boolean z2);
    }

    public final class AspectRatioUpdateDispatcher implements Runnable {
        private boolean aspectRatioMismatch;
        private boolean isScheduled;
        private float naturalAspectRatio;
        private float targetAspectRatio;

        private AspectRatioUpdateDispatcher() {
        }

        @Override // java.lang.Runnable
        public void run() {
            this.isScheduled = false;
            if (AspectRatioFrameLayout.this.aspectRatioListener == null) {
                return;
            }
            AspectRatioFrameLayout.this.aspectRatioListener.onAspectRatioUpdated(this.targetAspectRatio, this.naturalAspectRatio, this.aspectRatioMismatch);
        }

        public void scheduleUpdate(float f2, float f3, boolean z2) {
            this.targetAspectRatio = f2;
            this.naturalAspectRatio = f3;
            this.aspectRatioMismatch = z2;
            if (this.isScheduled) {
                return;
            }
            this.isScheduled = true;
            AspectRatioFrameLayout.this.post(this);
        }
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface ResizeMode {
    }

    public AspectRatioFrameLayout(Context context) {
        this(context, null);
    }

    public int getResizeMode() {
        return this.resizeMode;
    }

    @Override // android.widget.FrameLayout, android.view.View
    public void onMeasure(int i2, int i3) {
        float f2;
        float f3;
        super.onMeasure(i2, i3);
        if (this.videoAspectRatio <= 0.0f) {
            return;
        }
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        float f4 = measuredWidth;
        float f5 = measuredHeight;
        float f6 = f4 / f5;
        float f7 = (this.videoAspectRatio / f6) - 1.0f;
        if (Math.abs(f7) <= MAX_ASPECT_RATIO_DEFORMATION_FRACTION) {
            this.aspectRatioUpdateDispatcher.scheduleUpdate(this.videoAspectRatio, f6, false);
            return;
        }
        int i4 = this.resizeMode;
        if (i4 != 0) {
            if (i4 != 1) {
                if (i4 == 2) {
                    f2 = this.videoAspectRatio;
                } else if (i4 == 4) {
                    if (f7 > 0.0f) {
                        f2 = this.videoAspectRatio;
                    } else {
                        f3 = this.videoAspectRatio;
                    }
                }
                measuredWidth = (int) (f5 * f2);
            } else {
                f3 = this.videoAspectRatio;
            }
            measuredHeight = (int) (f4 / f3);
        } else if (f7 > 0.0f) {
            f3 = this.videoAspectRatio;
            measuredHeight = (int) (f4 / f3);
        } else {
            f2 = this.videoAspectRatio;
            measuredWidth = (int) (f5 * f2);
        }
        this.aspectRatioUpdateDispatcher.scheduleUpdate(this.videoAspectRatio, f6, true);
        super.onMeasure(View.MeasureSpec.makeMeasureSpec(measuredWidth, 1073741824), View.MeasureSpec.makeMeasureSpec(measuredHeight, 1073741824));
    }

    public void setAspectRatio(float f2) {
        if (this.videoAspectRatio != f2) {
            this.videoAspectRatio = f2;
            requestLayout();
        }
    }

    public void setAspectRatioListener(@Nullable AspectRatioListener aspectRatioListener) {
        this.aspectRatioListener = aspectRatioListener;
    }

    public void setResizeMode(int i2) {
        if (this.resizeMode != i2) {
            this.resizeMode = i2;
            requestLayout();
        }
    }

    public AspectRatioFrameLayout(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.resizeMode = 0;
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.AspectRatioFrameLayout, 0, 0);
            try {
                this.resizeMode = typedArrayObtainStyledAttributes.getInt(R.styleable.AspectRatioFrameLayout_resize_mode, 0);
            } finally {
                typedArrayObtainStyledAttributes.recycle();
            }
        }
        this.aspectRatioUpdateDispatcher = new AspectRatioUpdateDispatcher();
    }
}
