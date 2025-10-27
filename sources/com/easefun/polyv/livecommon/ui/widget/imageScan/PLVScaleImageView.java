package com.easefun.polyv.livecommon.ui.widget.imageScan;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import com.plv.foundationsdk.log.PLVCommonLog;

/* loaded from: classes3.dex */
public class PLVScaleImageView extends AppCompatImageView {
    private static final String TAG = "PLVScaleImageView";
    private GestureDetector gestureDetector;
    private boolean isCanDrag;
    private boolean isDraged;
    private boolean isDrawablePrepared;
    private boolean isInterceptDrag;
    private int lastPointerCount;
    private float lastX;
    private float lastY;
    private float maxScaleX;
    private float midScaleX;
    private float minScaleX;
    private View.OnClickListener onClickListener;
    private ViewTreeObserver.OnGlobalLayoutListener runnable;
    private ScaleGestureDetector scaleGestureDetector;
    private Matrix scaleMatrix;
    private float scaleX;
    private int touchSlop;

    public PLVScaleImageView(Context context) {
        this(context, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean canScale() {
        return this.isDrawablePrepared && getVisibility() == 0 && getWidth() != 0 && getHeight() != 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkBorderForScale() {
        float fWidth;
        RectF matrixRectF = getMatrixRectF();
        int width = getWidth();
        int height = getHeight();
        float f2 = width;
        if (matrixRectF.width() >= f2) {
            float f3 = matrixRectF.left;
            fWidth = f3 > 0.0f ? -f3 : 0.0f;
            float f4 = matrixRectF.right;
            if (f4 < f2) {
                fWidth = f2 - f4;
            }
        } else {
            fWidth = 0.0f;
        }
        float f5 = height;
        if (matrixRectF.height() >= f5) {
            float f6 = matrixRectF.top;
            height = f6 > 0.0f ? -f6 : 0.0f;
            float f7 = matrixRectF.bottom;
            if (f7 < f5) {
                height = f5 - f7;
            }
        }
        if (matrixRectF.width() < f2) {
            fWidth = (matrixRectF.width() / 2.0f) + ((getWidth() / 2) - matrixRectF.right);
        }
        if (matrixRectF.height() < f5) {
            height = ((getHeight() / 2) - matrixRectF.bottom) + (matrixRectF.height() / 2.0f);
        }
        this.scaleMatrix.postTranslate(fWidth, height);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float getIvScaleX() {
        float[] fArr = new float[9];
        this.scaleMatrix.getValues(fArr);
        return fArr[0];
    }

    private RectF getMatrixRectF() {
        RectF rectF = new RectF();
        if (getDrawable() != null) {
            rectF.set(0.0f, 0.0f, r1.getIntrinsicWidth(), r1.getIntrinsicHeight());
            this.scaleMatrix.mapRect(rectF);
        }
        return rectF;
    }

    private void init() {
        setClickable(true);
        setScaleType(ImageView.ScaleType.MATRIX);
        this.touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        this.scaleMatrix = new Matrix();
        this.gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() { // from class: com.easefun.polyv.livecommon.ui.widget.imageScan.PLVScaleImageView.1
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
            public boolean onDoubleTap(MotionEvent e2) {
                if (PLVScaleImageView.this.canScale()) {
                    if (PLVScaleImageView.this.getIvScaleX() >= PLVScaleImageView.this.scaleX && PLVScaleImageView.this.getIvScaleX() < PLVScaleImageView.this.midScaleX) {
                        PLVScaleImageView.this.scaleMatrix.postScale(PLVScaleImageView.this.midScaleX / PLVScaleImageView.this.getIvScaleX(), PLVScaleImageView.this.midScaleX / PLVScaleImageView.this.getIvScaleX(), PLVScaleImageView.this.getWidth() / 2.0f, PLVScaleImageView.this.getHeight() / 2.0f);
                        PLVScaleImageView pLVScaleImageView = PLVScaleImageView.this;
                        pLVScaleImageView.setImageMatrix(pLVScaleImageView.scaleMatrix);
                    } else {
                        PLVScaleImageView.this.reset();
                    }
                }
                return PLVScaleImageView.this.canScale();
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
            public boolean onSingleTapConfirmed(MotionEvent e2) {
                if (PLVScaleImageView.this.onClickListener != null) {
                    PLVScaleImageView.this.onClickListener.onClick(PLVScaleImageView.this);
                }
                return super.onSingleTapConfirmed(e2);
            }
        });
        this.scaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleGestureDetector.OnScaleGestureListener() { // from class: com.easefun.polyv.livecommon.ui.widget.imageScan.PLVScaleImageView.2
            @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
            public boolean onScale(ScaleGestureDetector detector) {
                if (PLVScaleImageView.this.canScale()) {
                    float ivScaleX = PLVScaleImageView.this.getIvScaleX();
                    float scaleFactor = detector.getScaleFactor();
                    if ((ivScaleX < PLVScaleImageView.this.maxScaleX && scaleFactor > 1.0f) || (ivScaleX > PLVScaleImageView.this.minScaleX && scaleFactor < 1.0f)) {
                        if (ivScaleX * scaleFactor < PLVScaleImageView.this.minScaleX) {
                            scaleFactor = PLVScaleImageView.this.minScaleX / ivScaleX;
                        }
                        if (ivScaleX * scaleFactor > PLVScaleImageView.this.maxScaleX) {
                            scaleFactor = PLVScaleImageView.this.maxScaleX / ivScaleX;
                        }
                        PLVScaleImageView.this.scaleMatrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());
                        PLVScaleImageView.this.checkBorderForScale();
                        PLVScaleImageView pLVScaleImageView = PLVScaleImageView.this;
                        pLVScaleImageView.setImageMatrix(pLVScaleImageView.scaleMatrix);
                    }
                }
                return PLVScaleImageView.this.canScale();
            }

            @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
            public boolean onScaleBegin(ScaleGestureDetector detector) {
                return PLVScaleImageView.this.canScale();
            }

            @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
            public void onScaleEnd(ScaleGestureDetector detector) {
                PLVCommonLog.d(PLVScaleImageView.TAG, " onScaleEnd");
            }
        });
        addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.easefun.polyv.livecommon.ui.widget.imageScan.PLVScaleImageView.3
            @Override // android.view.View.OnLayoutChangeListener
            public void onLayoutChange(View v2, int left, int top2, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (PLVScaleImageView.this.canScale() && oldRight > 0 && right > 0) {
                    if (right > oldRight) {
                        PLVScaleImageView pLVScaleImageView = PLVScaleImageView.this;
                        pLVScaleImageView.resetScaleX(pLVScaleImageView.getDrawable());
                        PLVScaleImageView.this.reset();
                    } else if (right < oldRight) {
                        PLVScaleImageView pLVScaleImageView2 = PLVScaleImageView.this;
                        pLVScaleImageView2.resetScaleX(pLVScaleImageView2.getDrawable());
                        PLVScaleImageView.this.reset();
                    }
                }
            }
        });
    }

    private boolean isMoveAction(float dx, float dy) {
        return Math.sqrt((double) ((dx * dx) + (dy * dy))) > ((double) this.touchSlop);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reset() {
        if (!this.isDrawablePrepared || getDrawable() == null) {
            return;
        }
        Drawable drawable = getDrawable();
        float intrinsicWidth = drawable.getIntrinsicWidth();
        float height = (getHeight() - drawable.getIntrinsicHeight()) / 2.0f;
        this.scaleMatrix.reset();
        this.scaleMatrix.postTranslate((getWidth() - intrinsicWidth) / 2.0f, height);
        Matrix matrix = this.scaleMatrix;
        float f2 = this.scaleX;
        matrix.postScale(f2, f2, getWidth() / 2.0f, getHeight() / 2.0f);
        setImageMatrix(this.scaleMatrix);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetScaleX(Drawable drawable) {
        if (drawable == null) {
            return;
        }
        int width = getWidth();
        int height = getHeight();
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        float f2 = (width * 1.0f) / intrinsicWidth;
        float f3 = (height * 1.0f) / intrinsicHeight;
        if (width > intrinsicWidth && height > intrinsicHeight) {
            this.scaleX = Math.min(f2, f3);
        } else if (width > intrinsicWidth && height < intrinsicHeight) {
            this.scaleX = f3;
        } else if (width < intrinsicWidth && height > intrinsicHeight) {
            this.scaleX = f2;
        } else if (width >= intrinsicWidth || height >= intrinsicHeight) {
            this.scaleX = 1.0f;
        } else {
            this.scaleX = Math.min(f2, f3);
        }
        float f4 = this.scaleX;
        this.minScaleX = 0.5f * f4;
        this.midScaleX = 2.0f * f4;
        this.maxScaleX = f4 * 5.0f;
    }

    public void drawablePrepared(final Drawable drawable) {
        if (drawable == null) {
            super.setImageDrawable(null);
            return;
        }
        setVisibility(4);
        this.isDrawablePrepared = true;
        super.setImageDrawable(drawable);
        getViewTreeObserver().removeOnGlobalLayoutListener(this.runnable);
        this.runnable = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.easefun.polyv.livecommon.ui.widget.imageScan.PLVScaleImageView.4
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                if (PLVScaleImageView.this.getWidth() == 0 || PLVScaleImageView.this.getHeight() == 0) {
                    return;
                }
                PLVScaleImageView.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                if (PLVScaleImageView.this.getDrawable() == null) {
                    return;
                }
                PLVScaleImageView.this.resetScaleX(drawable);
                PLVScaleImageView.this.reset();
                PLVScaleImageView.this.setVisibility(0);
            }
        };
        getViewTreeObserver().addOnGlobalLayoutListener(this.runnable);
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    /* JADX WARN: Removed duplicated region for block: B:70:0x0116  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r14) {
        /*
            Method dump skipped, instructions count: 329
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.easefun.polyv.livecommon.ui.widget.imageScan.PLVScaleImageView.onTouchEvent(android.view.MotionEvent):boolean");
    }

    @Override // androidx.appcompat.widget.AppCompatImageView, android.widget.ImageView
    public void setImageDrawable(@Nullable Drawable drawable) {
        drawablePrepared(drawable);
    }

    @Override // android.view.View
    public void setOnClickListener(@Nullable View.OnClickListener l2) {
        this.onClickListener = l2;
    }

    public PLVScaleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PLVScaleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.scaleX = 1.0f;
        init();
    }
}
