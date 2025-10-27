package com.pizidea.imagepicker.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.OverScroller;
import android.widget.Scroller;
import androidx.appcompat.widget.AppCompatImageView;

/* loaded from: classes4.dex */
public class TouchImageView extends AppCompatImageView {
    private static final String DEBUG = "DEBUG";
    private static final float SUPER_MAX_MULTIPLIER = 1.25f;
    private static final float SUPER_MIN_MULTIPLIER = 0.75f;
    private Context context;
    private ZoomVariables delayedZoomVariables;
    private GestureDetector.OnDoubleTapListener doubleTapListener;
    private Fling fling;
    private boolean imageRenderedAtLeastOnce;

    /* renamed from: m, reason: collision with root package name */
    private float[] f10728m;
    private GestureDetector mGestureDetector;
    private ScaleGestureDetector mScaleDetector;
    private ImageView.ScaleType mScaleType;
    private float matchViewHeight;
    private float matchViewWidth;
    private Matrix matrix;
    private float maxScale;
    private float minScale;
    private float normalizedScale;
    private boolean onDrawReady;
    private float prevMatchViewHeight;
    private float prevMatchViewWidth;
    private Matrix prevMatrix;
    private int prevViewHeight;
    private int prevViewWidth;
    private State state;
    private float superMaxScale;
    private float superMinScale;
    private OnTouchImageViewListener touchImageViewListener;
    private View.OnTouchListener userTouchListener;
    private int viewHeight;
    private int viewWidth;

    /* renamed from: com.pizidea.imagepicker.widget.TouchImageView$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$android$widget$ImageView$ScaleType;

        static {
            int[] iArr = new int[ImageView.ScaleType.values().length];
            $SwitchMap$android$widget$ImageView$ScaleType = iArr;
            try {
                iArr[ImageView.ScaleType.CENTER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.CENTER_CROP.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.CENTER_INSIDE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.FIT_CENTER.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.FIT_XY.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    @TargetApi(9)
    public class CompatScroller {
        boolean isPreGingerbread = false;
        OverScroller overScroller;
        Scroller scroller;

        public CompatScroller(Context context) {
            this.overScroller = new OverScroller(context);
        }

        public boolean computeScrollOffset() {
            if (this.isPreGingerbread) {
                return this.scroller.computeScrollOffset();
            }
            this.overScroller.computeScrollOffset();
            return this.overScroller.computeScrollOffset();
        }

        public void fling(int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
            if (this.isPreGingerbread) {
                this.scroller.fling(i2, i3, i4, i5, i6, i7, i8, i9);
            } else {
                this.overScroller.fling(i2, i3, i4, i5, i6, i7, i8, i9);
            }
        }

        public void forceFinished(boolean z2) {
            if (this.isPreGingerbread) {
                this.scroller.forceFinished(z2);
            } else {
                this.overScroller.forceFinished(z2);
            }
        }

        public int getCurrX() {
            return this.isPreGingerbread ? this.scroller.getCurrX() : this.overScroller.getCurrX();
        }

        public int getCurrY() {
            return this.isPreGingerbread ? this.scroller.getCurrY() : this.overScroller.getCurrY();
        }

        public boolean isFinished() {
            return this.isPreGingerbread ? this.scroller.isFinished() : this.overScroller.isFinished();
        }
    }

    public class DoubleTapZoom implements Runnable {
        private static final float ZOOM_TIME = 500.0f;
        private float bitmapX;
        private float bitmapY;
        private PointF endTouch;
        private AccelerateDecelerateInterpolator interpolator = new AccelerateDecelerateInterpolator();
        private long startTime;
        private PointF startTouch;
        private float startZoom;
        private boolean stretchImageToSuper;
        private float targetZoom;

        public DoubleTapZoom(float f2, float f3, float f4, boolean z2) {
            TouchImageView.this.setState(State.ANIMATE_ZOOM);
            this.startTime = System.currentTimeMillis();
            this.startZoom = TouchImageView.this.normalizedScale;
            this.targetZoom = f2;
            this.stretchImageToSuper = z2;
            PointF pointFTransformCoordTouchToBitmap = TouchImageView.this.transformCoordTouchToBitmap(f3, f4, false);
            float f5 = pointFTransformCoordTouchToBitmap.x;
            this.bitmapX = f5;
            float f6 = pointFTransformCoordTouchToBitmap.y;
            this.bitmapY = f6;
            this.startTouch = TouchImageView.this.transformCoordBitmapToTouch(f5, f6);
            this.endTouch = new PointF(TouchImageView.this.viewWidth / 2, TouchImageView.this.viewHeight / 2);
        }

        private double calculateDeltaScale(float f2) {
            float f3 = this.startZoom;
            return (f3 + (f2 * (this.targetZoom - f3))) / TouchImageView.this.normalizedScale;
        }

        private float interpolate() {
            return this.interpolator.getInterpolation(Math.min(1.0f, (System.currentTimeMillis() - this.startTime) / ZOOM_TIME));
        }

        private void translateImageToCenterTouchPosition(float f2) {
            PointF pointF = this.startTouch;
            float f3 = pointF.x;
            PointF pointF2 = this.endTouch;
            float f4 = f3 + ((pointF2.x - f3) * f2);
            float f5 = pointF.y;
            float f6 = f5 + (f2 * (pointF2.y - f5));
            PointF pointFTransformCoordBitmapToTouch = TouchImageView.this.transformCoordBitmapToTouch(this.bitmapX, this.bitmapY);
            TouchImageView.this.matrix.postTranslate(f4 - pointFTransformCoordBitmapToTouch.x, f6 - pointFTransformCoordBitmapToTouch.y);
        }

        @Override // java.lang.Runnable
        public void run() {
            float fInterpolate = interpolate();
            TouchImageView.this.scaleImage(calculateDeltaScale(fInterpolate), this.bitmapX, this.bitmapY, this.stretchImageToSuper);
            translateImageToCenterTouchPosition(fInterpolate);
            TouchImageView.this.fixScaleTrans();
            TouchImageView touchImageView = TouchImageView.this;
            touchImageView.setImageMatrix(touchImageView.matrix);
            if (TouchImageView.this.touchImageViewListener != null) {
                TouchImageView.this.touchImageViewListener.onMove();
            }
            if (fInterpolate < 1.0f) {
                TouchImageView.this.compatPostOnAnimation(this);
            } else {
                TouchImageView.this.setState(State.NONE);
            }
        }
    }

    public class Fling implements Runnable {
        int currX;
        int currY;
        CompatScroller scroller;

        public Fling(int i2, int i3) {
            int imageWidth;
            int i4;
            int imageHeight;
            int i5;
            TouchImageView.this.setState(State.FLING);
            this.scroller = TouchImageView.this.new CompatScroller(TouchImageView.this.context);
            TouchImageView.this.matrix.getValues(TouchImageView.this.f10728m);
            int i6 = (int) TouchImageView.this.f10728m[2];
            int i7 = (int) TouchImageView.this.f10728m[5];
            if (TouchImageView.this.getImageWidth() > TouchImageView.this.viewWidth) {
                imageWidth = TouchImageView.this.viewWidth - ((int) TouchImageView.this.getImageWidth());
                i4 = 0;
            } else {
                imageWidth = i6;
                i4 = imageWidth;
            }
            if (TouchImageView.this.getImageHeight() > TouchImageView.this.viewHeight) {
                imageHeight = TouchImageView.this.viewHeight - ((int) TouchImageView.this.getImageHeight());
                i5 = 0;
            } else {
                imageHeight = i7;
                i5 = imageHeight;
            }
            this.scroller.fling(i6, i7, i2, i3, imageWidth, i4, imageHeight, i5);
            this.currX = i6;
            this.currY = i7;
        }

        public void cancelFling() {
            if (this.scroller != null) {
                TouchImageView.this.setState(State.NONE);
                this.scroller.forceFinished(true);
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            if (TouchImageView.this.touchImageViewListener != null) {
                TouchImageView.this.touchImageViewListener.onMove();
            }
            if (this.scroller.isFinished()) {
                this.scroller = null;
                return;
            }
            if (this.scroller.computeScrollOffset()) {
                int currX = this.scroller.getCurrX();
                int currY = this.scroller.getCurrY();
                int i2 = currX - this.currX;
                int i3 = currY - this.currY;
                this.currX = currX;
                this.currY = currY;
                TouchImageView.this.matrix.postTranslate(i2, i3);
                TouchImageView.this.fixTrans();
                TouchImageView touchImageView = TouchImageView.this;
                touchImageView.setImageMatrix(touchImageView.matrix);
                TouchImageView.this.compatPostOnAnimation(this);
            }
        }
    }

    public class GestureListener extends GestureDetector.SimpleOnGestureListener {
        private GestureListener() {
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
        public boolean onDoubleTap(MotionEvent motionEvent) {
            boolean zOnDoubleTap = TouchImageView.this.doubleTapListener != null ? TouchImageView.this.doubleTapListener.onDoubleTap(motionEvent) : false;
            if (TouchImageView.this.state != State.NONE) {
                return zOnDoubleTap;
            }
            TouchImageView.this.compatPostOnAnimation(TouchImageView.this.new DoubleTapZoom(TouchImageView.this.normalizedScale == TouchImageView.this.minScale ? TouchImageView.this.maxScale : TouchImageView.this.minScale, motionEvent.getX(), motionEvent.getY(), false));
            return true;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
        public boolean onDoubleTapEvent(MotionEvent motionEvent) {
            if (TouchImageView.this.doubleTapListener != null) {
                return TouchImageView.this.doubleTapListener.onDoubleTapEvent(motionEvent);
            }
            return false;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f2, float f3) {
            if (TouchImageView.this.fling != null) {
                TouchImageView.this.fling.cancelFling();
            }
            TouchImageView touchImageView = TouchImageView.this;
            touchImageView.fling = touchImageView.new Fling((int) f2, (int) f3);
            TouchImageView touchImageView2 = TouchImageView.this;
            touchImageView2.compatPostOnAnimation(touchImageView2.fling);
            return super.onFling(motionEvent, motionEvent2, f2, f3);
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public void onLongPress(MotionEvent motionEvent) {
            TouchImageView.this.performLongClick();
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
        public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
            return TouchImageView.this.doubleTapListener != null ? TouchImageView.this.doubleTapListener.onSingleTapConfirmed(motionEvent) : TouchImageView.this.performClick();
        }

        public /* synthetic */ GestureListener(TouchImageView touchImageView, AnonymousClass1 anonymousClass1) {
            this();
        }
    }

    public interface OnTouchImageViewListener {
        void onMove();
    }

    public class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        private ScaleListener() {
        }

        @Override // android.view.ScaleGestureDetector.SimpleOnScaleGestureListener, android.view.ScaleGestureDetector.OnScaleGestureListener
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            TouchImageView.this.scaleImage(scaleGestureDetector.getScaleFactor(), scaleGestureDetector.getFocusX(), scaleGestureDetector.getFocusY(), true);
            if (TouchImageView.this.touchImageViewListener == null) {
                return true;
            }
            TouchImageView.this.touchImageViewListener.onMove();
            return true;
        }

        @Override // android.view.ScaleGestureDetector.SimpleOnScaleGestureListener, android.view.ScaleGestureDetector.OnScaleGestureListener
        public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
            TouchImageView.this.setState(State.ZOOM);
            return true;
        }

        @Override // android.view.ScaleGestureDetector.SimpleOnScaleGestureListener, android.view.ScaleGestureDetector.OnScaleGestureListener
        public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
            super.onScaleEnd(scaleGestureDetector);
            TouchImageView.this.setState(State.NONE);
            float f2 = TouchImageView.this.normalizedScale;
            boolean z2 = true;
            if (TouchImageView.this.normalizedScale > TouchImageView.this.maxScale) {
                f2 = TouchImageView.this.maxScale;
            } else if (TouchImageView.this.normalizedScale < TouchImageView.this.minScale) {
                f2 = TouchImageView.this.minScale;
            } else {
                z2 = false;
            }
            float f3 = f2;
            if (z2) {
                TouchImageView.this.compatPostOnAnimation(TouchImageView.this.new DoubleTapZoom(f3, r3.viewWidth / 2, TouchImageView.this.viewHeight / 2, true));
            }
        }

        public /* synthetic */ ScaleListener(TouchImageView touchImageView, AnonymousClass1 anonymousClass1) {
            this();
        }
    }

    public enum State {
        NONE,
        DRAG,
        ZOOM,
        FLING,
        ANIMATE_ZOOM
    }

    public class ZoomVariables {
        public float focusX;
        public float focusY;
        public float scale;
        public ImageView.ScaleType scaleType;

        public ZoomVariables(float f2, float f3, float f4, ImageView.ScaleType scaleType) {
            this.scale = f2;
            this.focusX = f3;
            this.focusY = f4;
            this.scaleType = scaleType;
        }
    }

    public TouchImageView(Context context) {
        super(context);
        this.doubleTapListener = null;
        this.userTouchListener = null;
        this.touchImageViewListener = null;
        sharedConstructing(context);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @TargetApi(16)
    public void compatPostOnAnimation(Runnable runnable) {
        postOnAnimation(runnable);
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x0099  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00a6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void fitImageToView() {
        /*
            Method dump skipped, instructions count: 251
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.pizidea.imagepicker.widget.TouchImageView.fitImageToView():void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void fixScaleTrans() {
        fixTrans();
        this.matrix.getValues(this.f10728m);
        float imageWidth = getImageWidth();
        int i2 = this.viewWidth;
        if (imageWidth < i2) {
            this.f10728m[2] = (i2 - getImageWidth()) / 2.0f;
        }
        float imageHeight = getImageHeight();
        int i3 = this.viewHeight;
        if (imageHeight < i3) {
            this.f10728m[5] = (i3 - getImageHeight()) / 2.0f;
        }
        this.matrix.setValues(this.f10728m);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void fixTrans() {
        this.matrix.getValues(this.f10728m);
        float[] fArr = this.f10728m;
        float f2 = fArr[2];
        float f3 = fArr[5];
        float fixTrans = getFixTrans(f2, this.viewWidth - 30, getImageWidth());
        float fixTrans2 = getFixTrans(f3, this.viewHeight, getImageHeight());
        if (fixTrans == 0.0f && fixTrans2 == 0.0f) {
            return;
        }
        this.matrix.postTranslate(fixTrans, fixTrans2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float getFixDragTrans(float f2, float f3, float f4) {
        if (f4 <= f3) {
            return 0.0f;
        }
        return f2;
    }

    private float getFixTrans(float f2, float f3, float f4) {
        float f5;
        float f6;
        if (f4 <= f3) {
            f6 = f3 - f4;
            f5 = 0.0f;
        } else {
            f5 = f3 - f4;
            f6 = 0.0f;
        }
        if (f2 < f5) {
            return (-f2) + f5;
        }
        if (f2 > f6) {
            return (-f2) + f6;
        }
        return 0.0f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float getImageHeight() {
        return this.matchViewHeight * this.normalizedScale;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float getImageWidth() {
        return this.matchViewWidth * this.normalizedScale;
    }

    private void printMatrixInfo() {
        float[] fArr = new float[9];
        this.matrix.getValues(fArr);
        Log.d(DEBUG, "Scale: " + fArr[0] + " TransX: " + fArr[2] + " TransY: " + fArr[5]);
    }

    private void savePreviousImageValues() {
        Matrix matrix = this.matrix;
        if (matrix == null || this.viewHeight == 0 || this.viewWidth == 0) {
            return;
        }
        matrix.getValues(this.f10728m);
        this.prevMatrix.setValues(this.f10728m);
        this.prevMatchViewHeight = this.matchViewHeight;
        this.prevMatchViewWidth = this.matchViewWidth;
        this.prevViewHeight = this.viewHeight;
        this.prevViewWidth = this.viewWidth;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scaleImage(double d3, float f2, float f3, boolean z2) {
        float f4;
        float f5;
        if (z2) {
            f4 = this.superMinScale;
            f5 = this.superMaxScale;
        } else {
            f4 = this.minScale;
            f5 = this.maxScale;
        }
        float f6 = this.normalizedScale;
        float f7 = (float) (f6 * d3);
        this.normalizedScale = f7;
        if (f7 > f5) {
            this.normalizedScale = f5;
            d3 = f5 / f6;
        } else if (f7 < f4) {
            this.normalizedScale = f4;
            d3 = f4 / f6;
        }
        float f8 = (float) d3;
        this.matrix.postScale(f8, f8, f2, f3);
        fixScaleTrans();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setState(State state) {
        this.state = state;
    }

    private int setViewSize(int i2, int i3, int i4) {
        return i2 != Integer.MIN_VALUE ? i2 != 0 ? i3 : i4 : Math.min(i4, i3);
    }

    private void sharedConstructing(Context context) {
        super.setClickable(true);
        this.context = context;
        AnonymousClass1 anonymousClass1 = null;
        this.mScaleDetector = new ScaleGestureDetector(context, new ScaleListener(this, anonymousClass1));
        this.mGestureDetector = new GestureDetector(context, new GestureListener(this, anonymousClass1));
        this.matrix = new Matrix();
        this.prevMatrix = new Matrix();
        this.f10728m = new float[9];
        this.normalizedScale = 1.0f;
        if (this.mScaleType == null) {
            this.mScaleType = ImageView.ScaleType.FIT_CENTER;
        }
        this.minScale = 1.0f;
        this.maxScale = 3.0f;
        this.superMinScale = 1.0f * 0.75f;
        this.superMaxScale = 3.0f * SUPER_MAX_MULTIPLIER;
        setImageMatrix(this.matrix);
        setScaleType(ImageView.ScaleType.MATRIX);
        setState(State.NONE);
        this.onDrawReady = false;
        super.setOnTouchListener(new PrivateOnTouchListener(this, anonymousClass1));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public PointF transformCoordBitmapToTouch(float f2, float f3) {
        this.matrix.getValues(this.f10728m);
        return new PointF(this.f10728m[2] + (getImageWidth() * (f2 / getDrawable().getIntrinsicWidth())), this.f10728m[5] + (getImageHeight() * (f3 / getDrawable().getIntrinsicHeight())));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public PointF transformCoordTouchToBitmap(float f2, float f3, boolean z2) {
        this.matrix.getValues(this.f10728m);
        float intrinsicWidth = getDrawable().getIntrinsicWidth();
        float intrinsicHeight = getDrawable().getIntrinsicHeight();
        float[] fArr = this.f10728m;
        float f4 = fArr[2];
        float f5 = fArr[5];
        float imageWidth = ((f2 - f4) * intrinsicWidth) / getImageWidth();
        float imageHeight = ((f3 - f5) * intrinsicHeight) / getImageHeight();
        if (z2) {
            imageWidth = Math.min(Math.max(imageWidth, 0.0f), intrinsicWidth);
            imageHeight = Math.min(Math.max(imageHeight, 0.0f), intrinsicHeight);
        }
        return new PointF(imageWidth, imageHeight);
    }

    private void translateMatrixAfterRotate(int i2, float f2, float f3, float f4, int i3, int i4, int i5) {
        float f5 = i4;
        if (f4 < f5) {
            float[] fArr = this.f10728m;
            fArr[i2] = (f5 - (i5 * fArr[0])) * 0.5f;
        } else if (f2 > 0.0f) {
            this.f10728m[i2] = -((f4 - f5) * 0.5f);
        } else {
            this.f10728m[i2] = -((((Math.abs(f2) + (i3 * 0.5f)) / f3) * f4) - (f5 * 0.5f));
        }
    }

    @Override // android.view.View
    public boolean canScrollHorizontally(int i2) {
        this.matrix.getValues(this.f10728m);
        float f2 = this.f10728m[2];
        if (getImageWidth() < this.viewWidth) {
            return false;
        }
        if (f2 < -1.0f || i2 >= 0) {
            return (Math.abs(f2) + ((float) this.viewWidth)) + 1.0f < getImageWidth() || i2 <= 0;
        }
        return false;
    }

    public boolean canScrollHorizontallyFroyo(int i2) {
        return canScrollHorizontally(i2);
    }

    public float getCurrentZoom() {
        return this.normalizedScale;
    }

    public float getMaxZoom() {
        return this.maxScale;
    }

    public float getMinZoom() {
        return this.minScale;
    }

    @Override // android.widget.ImageView
    public ImageView.ScaleType getScaleType() {
        return this.mScaleType;
    }

    public PointF getScrollPosition() {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return null;
        }
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        PointF pointFTransformCoordTouchToBitmap = transformCoordTouchToBitmap(this.viewWidth / 2, this.viewHeight / 2, true);
        pointFTransformCoordTouchToBitmap.x /= intrinsicWidth;
        pointFTransformCoordTouchToBitmap.y /= intrinsicHeight;
        return pointFTransformCoordTouchToBitmap;
    }

    public RectF getZoomedRect() {
        if (this.mScaleType == ImageView.ScaleType.FIT_XY) {
            throw new UnsupportedOperationException("getZoomedRect() not supported with FIT_XY");
        }
        PointF pointFTransformCoordTouchToBitmap = transformCoordTouchToBitmap(0.0f, 0.0f, true);
        PointF pointFTransformCoordTouchToBitmap2 = transformCoordTouchToBitmap(this.viewWidth, this.viewHeight, true);
        float intrinsicWidth = getDrawable().getIntrinsicWidth();
        float intrinsicHeight = getDrawable().getIntrinsicHeight();
        return new RectF(pointFTransformCoordTouchToBitmap.x / intrinsicWidth, pointFTransformCoordTouchToBitmap.y / intrinsicHeight, pointFTransformCoordTouchToBitmap2.x / intrinsicWidth, pointFTransformCoordTouchToBitmap2.y / intrinsicHeight);
    }

    public boolean isZoomed() {
        return this.normalizedScale != 1.0f;
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        savePreviousImageValues();
    }

    @Override // android.widget.ImageView, android.view.View
    public void onDraw(Canvas canvas) {
        this.onDrawReady = true;
        this.imageRenderedAtLeastOnce = true;
        ZoomVariables zoomVariables = this.delayedZoomVariables;
        if (zoomVariables != null) {
            setZoom(zoomVariables.scale, zoomVariables.focusX, zoomVariables.focusY, zoomVariables.scaleType);
            this.delayedZoomVariables = null;
        }
        super.onDraw(canvas);
    }

    @Override // android.widget.ImageView, android.view.View
    public void onMeasure(int i2, int i3) {
        Drawable drawable = getDrawable();
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        int size = View.MeasureSpec.getSize(i2);
        int mode = View.MeasureSpec.getMode(i2);
        int size2 = View.MeasureSpec.getSize(i3);
        int mode2 = View.MeasureSpec.getMode(i3);
        this.viewWidth = setViewSize(mode, size, intrinsicWidth);
        int viewSize = setViewSize(mode2, size2, intrinsicHeight);
        this.viewHeight = viewSize;
        setMeasuredDimension(this.viewWidth, viewSize);
        fitImageToView();
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof Bundle)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        Bundle bundle = (Bundle) parcelable;
        this.normalizedScale = bundle.getFloat("saveScale");
        float[] floatArray = bundle.getFloatArray("matrix");
        this.f10728m = floatArray;
        this.prevMatrix.setValues(floatArray);
        this.prevMatchViewHeight = bundle.getFloat("matchViewHeight");
        this.prevMatchViewWidth = bundle.getFloat("matchViewWidth");
        this.prevViewHeight = bundle.getInt("viewHeight");
        this.prevViewWidth = bundle.getInt("viewWidth");
        this.imageRenderedAtLeastOnce = bundle.getBoolean("imageRendered");
        super.onRestoreInstanceState(bundle.getParcelable("instanceState"));
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("instanceState", super.onSaveInstanceState());
        bundle.putFloat("saveScale", this.normalizedScale);
        bundle.putFloat("matchViewHeight", this.matchViewHeight);
        bundle.putFloat("matchViewWidth", this.matchViewWidth);
        bundle.putInt("viewWidth", this.viewWidth);
        bundle.putInt("viewHeight", this.viewHeight);
        this.matrix.getValues(this.f10728m);
        bundle.putFloatArray("matrix", this.f10728m);
        bundle.putBoolean("imageRendered", this.imageRenderedAtLeastOnce);
        return bundle;
    }

    public void resetZoom() {
        this.normalizedScale = 1.0f;
        fitImageToView();
    }

    @Override // androidx.appcompat.widget.AppCompatImageView, android.widget.ImageView
    public void setImageBitmap(Bitmap bitmap) {
        super.setImageBitmap(bitmap);
        savePreviousImageValues();
        fitImageToView();
    }

    @Override // androidx.appcompat.widget.AppCompatImageView, android.widget.ImageView
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        savePreviousImageValues();
        fitImageToView();
    }

    @Override // androidx.appcompat.widget.AppCompatImageView, android.widget.ImageView
    public void setImageResource(int i2) {
        super.setImageResource(i2);
        savePreviousImageValues();
        fitImageToView();
    }

    @Override // androidx.appcompat.widget.AppCompatImageView, android.widget.ImageView
    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        savePreviousImageValues();
        fitImageToView();
    }

    public void setMaxZoom(float f2) {
        this.maxScale = f2;
        this.superMaxScale = f2 * SUPER_MAX_MULTIPLIER;
    }

    public void setMinZoom(float f2) {
        this.minScale = f2;
        this.superMinScale = f2 * 0.75f;
    }

    public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener onDoubleTapListener) {
        this.doubleTapListener = onDoubleTapListener;
    }

    public void setOnTouchImageViewListener(OnTouchImageViewListener onTouchImageViewListener) {
        this.touchImageViewListener = onTouchImageViewListener;
    }

    @Override // android.view.View
    public void setOnTouchListener(View.OnTouchListener onTouchListener) {
        this.userTouchListener = onTouchListener;
    }

    @Override // android.widget.ImageView
    public void setScaleType(ImageView.ScaleType scaleType) {
        if (scaleType == ImageView.ScaleType.FIT_START || scaleType == ImageView.ScaleType.FIT_END) {
            throw new UnsupportedOperationException("TouchImageView does not support FIT_START or FIT_END");
        }
        ImageView.ScaleType scaleType2 = ImageView.ScaleType.MATRIX;
        if (scaleType == scaleType2) {
            super.setScaleType(scaleType2);
            return;
        }
        this.mScaleType = scaleType;
        if (this.onDrawReady) {
            setZoom(this);
        }
    }

    public void setScrollPosition(float f2, float f3) {
        setZoom(this.normalizedScale, f2, f3);
    }

    public void setZoom(float f2) {
        setZoom(f2, 0.5f, 0.5f);
    }

    public class PrivateOnTouchListener implements View.OnTouchListener {
        private PointF last;

        private PrivateOnTouchListener() {
            this.last = new PointF();
        }

        /* JADX WARN: Removed duplicated region for block: B:19:0x009d  */
        @Override // android.view.View.OnTouchListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public boolean onTouch(android.view.View r8, android.view.MotionEvent r9) {
            /*
                r7 = this;
                com.pizidea.imagepicker.widget.TouchImageView r0 = com.pizidea.imagepicker.widget.TouchImageView.this
                android.view.ScaleGestureDetector r0 = com.pizidea.imagepicker.widget.TouchImageView.access$1000(r0)
                r0.onTouchEvent(r9)
                com.pizidea.imagepicker.widget.TouchImageView r0 = com.pizidea.imagepicker.widget.TouchImageView.this
                android.view.GestureDetector r0 = com.pizidea.imagepicker.widget.TouchImageView.access$1100(r0)
                r0.onTouchEvent(r9)
                android.graphics.PointF r0 = new android.graphics.PointF
                float r1 = r9.getX()
                float r2 = r9.getY()
                r0.<init>(r1, r2)
                com.pizidea.imagepicker.widget.TouchImageView r1 = com.pizidea.imagepicker.widget.TouchImageView.this
                com.pizidea.imagepicker.widget.TouchImageView$State r1 = com.pizidea.imagepicker.widget.TouchImageView.access$600(r1)
                com.pizidea.imagepicker.widget.TouchImageView$State r2 = com.pizidea.imagepicker.widget.TouchImageView.State.NONE
                r3 = 1
                if (r1 == r2) goto L3e
                com.pizidea.imagepicker.widget.TouchImageView r1 = com.pizidea.imagepicker.widget.TouchImageView.this
                com.pizidea.imagepicker.widget.TouchImageView$State r1 = com.pizidea.imagepicker.widget.TouchImageView.access$600(r1)
                com.pizidea.imagepicker.widget.TouchImageView$State r4 = com.pizidea.imagepicker.widget.TouchImageView.State.DRAG
                if (r1 == r4) goto L3e
                com.pizidea.imagepicker.widget.TouchImageView r1 = com.pizidea.imagepicker.widget.TouchImageView.this
                com.pizidea.imagepicker.widget.TouchImageView$State r1 = com.pizidea.imagepicker.widget.TouchImageView.access$600(r1)
                com.pizidea.imagepicker.widget.TouchImageView$State r4 = com.pizidea.imagepicker.widget.TouchImageView.State.FLING
                if (r1 != r4) goto Lc0
            L3e:
                int r1 = r9.getAction()
                if (r1 == 0) goto La3
                if (r1 == r3) goto L9d
                r4 = 2
                if (r1 == r4) goto L4d
                r0 = 6
                if (r1 == r0) goto L9d
                goto Lc0
            L4d:
                com.pizidea.imagepicker.widget.TouchImageView r1 = com.pizidea.imagepicker.widget.TouchImageView.this
                com.pizidea.imagepicker.widget.TouchImageView$State r1 = com.pizidea.imagepicker.widget.TouchImageView.access$600(r1)
                com.pizidea.imagepicker.widget.TouchImageView$State r2 = com.pizidea.imagepicker.widget.TouchImageView.State.DRAG
                if (r1 != r2) goto Lc0
                float r1 = r0.x
                android.graphics.PointF r2 = r7.last
                float r4 = r2.x
                float r1 = r1 - r4
                float r4 = r0.y
                float r2 = r2.y
                float r4 = r4 - r2
                com.pizidea.imagepicker.widget.TouchImageView r2 = com.pizidea.imagepicker.widget.TouchImageView.this
                int r5 = com.pizidea.imagepicker.widget.TouchImageView.access$1300(r2)
                float r5 = (float) r5
                com.pizidea.imagepicker.widget.TouchImageView r6 = com.pizidea.imagepicker.widget.TouchImageView.this
                float r6 = com.pizidea.imagepicker.widget.TouchImageView.access$1400(r6)
                float r1 = com.pizidea.imagepicker.widget.TouchImageView.access$1500(r2, r1, r5, r6)
                com.pizidea.imagepicker.widget.TouchImageView r2 = com.pizidea.imagepicker.widget.TouchImageView.this
                int r5 = com.pizidea.imagepicker.widget.TouchImageView.access$1600(r2)
                float r5 = (float) r5
                com.pizidea.imagepicker.widget.TouchImageView r6 = com.pizidea.imagepicker.widget.TouchImageView.this
                float r6 = com.pizidea.imagepicker.widget.TouchImageView.access$1700(r6)
                float r2 = com.pizidea.imagepicker.widget.TouchImageView.access$1500(r2, r4, r5, r6)
                com.pizidea.imagepicker.widget.TouchImageView r4 = com.pizidea.imagepicker.widget.TouchImageView.this
                android.graphics.Matrix r4 = com.pizidea.imagepicker.widget.TouchImageView.access$1800(r4)
                r4.postTranslate(r1, r2)
                com.pizidea.imagepicker.widget.TouchImageView r1 = com.pizidea.imagepicker.widget.TouchImageView.this
                com.pizidea.imagepicker.widget.TouchImageView.access$1900(r1)
                android.graphics.PointF r1 = r7.last
                float r2 = r0.x
                float r0 = r0.y
                r1.set(r2, r0)
                goto Lc0
            L9d:
                com.pizidea.imagepicker.widget.TouchImageView r0 = com.pizidea.imagepicker.widget.TouchImageView.this
                com.pizidea.imagepicker.widget.TouchImageView.access$1200(r0, r2)
                goto Lc0
            La3:
                android.graphics.PointF r1 = r7.last
                r1.set(r0)
                com.pizidea.imagepicker.widget.TouchImageView r0 = com.pizidea.imagepicker.widget.TouchImageView.this
                com.pizidea.imagepicker.widget.TouchImageView$Fling r0 = com.pizidea.imagepicker.widget.TouchImageView.access$400(r0)
                if (r0 == 0) goto Lb9
                com.pizidea.imagepicker.widget.TouchImageView r0 = com.pizidea.imagepicker.widget.TouchImageView.this
                com.pizidea.imagepicker.widget.TouchImageView$Fling r0 = com.pizidea.imagepicker.widget.TouchImageView.access$400(r0)
                r0.cancelFling()
            Lb9:
                com.pizidea.imagepicker.widget.TouchImageView r0 = com.pizidea.imagepicker.widget.TouchImageView.this
                com.pizidea.imagepicker.widget.TouchImageView$State r1 = com.pizidea.imagepicker.widget.TouchImageView.State.DRAG
                com.pizidea.imagepicker.widget.TouchImageView.access$1200(r0, r1)
            Lc0:
                com.pizidea.imagepicker.widget.TouchImageView r0 = com.pizidea.imagepicker.widget.TouchImageView.this
                android.graphics.Matrix r1 = com.pizidea.imagepicker.widget.TouchImageView.access$1800(r0)
                r0.setImageMatrix(r1)
                com.pizidea.imagepicker.widget.TouchImageView r0 = com.pizidea.imagepicker.widget.TouchImageView.this
                android.view.View$OnTouchListener r0 = com.pizidea.imagepicker.widget.TouchImageView.access$2000(r0)
                if (r0 == 0) goto Lda
                com.pizidea.imagepicker.widget.TouchImageView r0 = com.pizidea.imagepicker.widget.TouchImageView.this
                android.view.View$OnTouchListener r0 = com.pizidea.imagepicker.widget.TouchImageView.access$2000(r0)
                r0.onTouch(r8, r9)
            Lda:
                com.pizidea.imagepicker.widget.TouchImageView r8 = com.pizidea.imagepicker.widget.TouchImageView.this
                com.pizidea.imagepicker.widget.TouchImageView$OnTouchImageViewListener r8 = com.pizidea.imagepicker.widget.TouchImageView.access$2100(r8)
                if (r8 == 0) goto Leb
                com.pizidea.imagepicker.widget.TouchImageView r8 = com.pizidea.imagepicker.widget.TouchImageView.this
                com.pizidea.imagepicker.widget.TouchImageView$OnTouchImageViewListener r8 = com.pizidea.imagepicker.widget.TouchImageView.access$2100(r8)
                r8.onMove()
            Leb:
                return r3
            */
            throw new UnsupportedOperationException("Method not decompiled: com.pizidea.imagepicker.widget.TouchImageView.PrivateOnTouchListener.onTouch(android.view.View, android.view.MotionEvent):boolean");
        }

        public /* synthetic */ PrivateOnTouchListener(TouchImageView touchImageView, AnonymousClass1 anonymousClass1) {
            this();
        }
    }

    public void setZoom(float f2, float f3, float f4) {
        setZoom(f2, f3, f4, this.mScaleType);
    }

    public void setZoom(float f2, float f3, float f4, ImageView.ScaleType scaleType) {
        if (!this.onDrawReady) {
            this.delayedZoomVariables = new ZoomVariables(f2, f3, f4, scaleType);
            return;
        }
        if (scaleType != this.mScaleType) {
            setScaleType(scaleType);
        }
        resetZoom();
        scaleImage(f2, this.viewWidth / 2, this.viewHeight / 2, true);
        this.matrix.getValues(this.f10728m);
        this.f10728m[2] = -((f3 * getImageWidth()) - (this.viewWidth * 0.5f));
        this.f10728m[5] = -((f4 * getImageHeight()) - (this.viewHeight * 0.5f));
        this.matrix.setValues(this.f10728m);
        fixTrans();
        setImageMatrix(this.matrix);
    }

    public TouchImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.doubleTapListener = null;
        this.userTouchListener = null;
        this.touchImageViewListener = null;
        sharedConstructing(context);
    }

    public TouchImageView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.doubleTapListener = null;
        this.userTouchListener = null;
        this.touchImageViewListener = null;
        sharedConstructing(context);
    }

    public void setZoom(TouchImageView touchImageView) {
        PointF scrollPosition = touchImageView.getScrollPosition();
        setZoom(touchImageView.getCurrentZoom(), scrollPosition.x, scrollPosition.y, touchImageView.getScaleType());
    }
}
