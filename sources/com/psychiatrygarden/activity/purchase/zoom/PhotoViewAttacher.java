package com.psychiatrygarden.activity.purchase.zoom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import com.psychiatrygarden.activity.purchase.zoom.VersionedGestureDetector;
import java.lang.ref.WeakReference;

/* loaded from: classes5.dex */
public class PhotoViewAttacher implements IPhotoView, View.OnTouchListener, VersionedGestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener, ViewTreeObserver.OnGlobalLayoutListener {
    public static final float DEFAULT_MAX_SCALE = 3.0f;
    public static final float DEFAULT_MID_SCALE = 1.75f;
    public static final float DEFAULT_MIN_SCALE = 1.0f;
    static final int EDGE_BOTH = 2;
    static final int EDGE_LEFT = 0;
    static final int EDGE_NONE = -1;
    static final int EDGE_RIGHT = 1;
    private FlingRunnable mCurrentFlingRunnable;
    private GestureDetector mGestureDetector;
    private WeakReference<ImageView> mImageView;
    private int mIvBottom;
    private int mIvLeft;
    private int mIvRight;
    private int mIvTop;
    private View.OnLongClickListener mLongClickListener;
    private OnMatrixChangedListener mMatrixChangeListener;
    private OnPhotoTapListener mPhotoTapListener;
    private VersionedGestureDetector mScaleDragDetector;
    private OnViewTapListener mViewTapListener;
    private ViewTreeObserver mViewTreeObserver;
    private boolean mZoomEnabled;
    static final String LOG_TAG = "PhotoViewAttacher";
    static final boolean DEBUG = Log.isLoggable(LOG_TAG, 3);
    private float mMinScale = 1.0f;
    private float mMidScale = 1.75f;
    private float mMaxScale = 3.0f;
    private boolean mAllowParentInterceptOnEdge = true;
    private final Matrix mBaseMatrix = new Matrix();
    private final Matrix mDrawMatrix = new Matrix();
    private final Matrix mSuppMatrix = new Matrix();
    private final RectF mDisplayRect = new RectF();
    private final float[] mMatrixValues = new float[9];
    private int mScrollEdge = 2;
    private ImageView.ScaleType mScaleType = ImageView.ScaleType.FIT_CENTER;

    /* renamed from: com.psychiatrygarden.activity.purchase.zoom.PhotoViewAttacher$2, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$android$widget$ImageView$ScaleType;

        static {
            int[] iArr = new int[ImageView.ScaleType.values().length];
            $SwitchMap$android$widget$ImageView$ScaleType = iArr;
            try {
                iArr[ImageView.ScaleType.MATRIX.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.FIT_START.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.FIT_END.ordinal()] = 3;
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

    public class AnimatedZoomRunnable implements Runnable {
        static final float ANIMATION_SCALE_PER_ITERATION_IN = 1.07f;
        static final float ANIMATION_SCALE_PER_ITERATION_OUT = 0.93f;
        private final float mDeltaScale;
        private final float mFocalX;
        private final float mFocalY;
        private final float mTargetZoom;

        public AnimatedZoomRunnable(final float currentZoom, final float targetZoom, final float focalX, final float focalY) {
            this.mTargetZoom = targetZoom;
            this.mFocalX = focalX;
            this.mFocalY = focalY;
            if (currentZoom < targetZoom) {
                this.mDeltaScale = ANIMATION_SCALE_PER_ITERATION_IN;
            } else {
                this.mDeltaScale = ANIMATION_SCALE_PER_ITERATION_OUT;
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            ImageView imageView = PhotoViewAttacher.this.getImageView();
            if (imageView != null) {
                Matrix matrix = PhotoViewAttacher.this.mSuppMatrix;
                float f2 = this.mDeltaScale;
                matrix.postScale(f2, f2, this.mFocalX, this.mFocalY);
                PhotoViewAttacher.this.checkAndDisplayMatrix();
                float scale = PhotoViewAttacher.this.getScale();
                float f3 = this.mDeltaScale;
                if ((f3 > 1.0f && scale < this.mTargetZoom) || (f3 < 1.0f && this.mTargetZoom < scale)) {
                    Compat.postOnAnimation(imageView, this);
                    return;
                }
                float f4 = this.mTargetZoom / scale;
                PhotoViewAttacher.this.mSuppMatrix.postScale(f4, f4, this.mFocalX, this.mFocalY);
                PhotoViewAttacher.this.checkAndDisplayMatrix();
            }
        }
    }

    public class FlingRunnable implements Runnable {
        private int mCurrentX;
        private int mCurrentY;
        private final ScrollerProxy mScroller;

        public FlingRunnable(Context context) {
            this.mScroller = ScrollerProxy.getScroller(context);
        }

        public void cancelFling() {
            if (PhotoViewAttacher.DEBUG) {
                Log.d(PhotoViewAttacher.LOG_TAG, "Cancel Fling");
            }
            this.mScroller.forceFinished(true);
        }

        public void fling(int viewWidth, int viewHeight, int velocityX, int velocityY) {
            int i2;
            int iRound;
            int i3;
            int iRound2;
            RectF displayRect = PhotoViewAttacher.this.getDisplayRect();
            if (displayRect == null) {
                return;
            }
            int iRound3 = Math.round(-displayRect.left);
            float f2 = viewWidth;
            if (f2 < displayRect.width()) {
                iRound = Math.round(displayRect.width() - f2);
                i2 = 0;
            } else {
                i2 = iRound3;
                iRound = i2;
            }
            int iRound4 = Math.round(-displayRect.top);
            float f3 = viewHeight;
            if (f3 < displayRect.height()) {
                iRound2 = Math.round(displayRect.height() - f3);
                i3 = 0;
            } else {
                i3 = iRound4;
                iRound2 = i3;
            }
            this.mCurrentX = iRound3;
            this.mCurrentY = iRound4;
            if (PhotoViewAttacher.DEBUG) {
                Log.d(PhotoViewAttacher.LOG_TAG, "fling. StartX:" + iRound3 + " StartY:" + iRound4 + " MaxX:" + iRound + " MaxY:" + iRound2);
            }
            if (iRound3 == iRound && iRound4 == iRound2) {
                return;
            }
            this.mScroller.fling(iRound3, iRound4, velocityX, velocityY, i2, iRound, i3, iRound2, 0, 0);
        }

        @Override // java.lang.Runnable
        public void run() {
            ImageView imageView = PhotoViewAttacher.this.getImageView();
            if (imageView == null || !this.mScroller.computeScrollOffset()) {
                return;
            }
            int currX = this.mScroller.getCurrX();
            int currY = this.mScroller.getCurrY();
            if (PhotoViewAttacher.DEBUG) {
                Log.d(PhotoViewAttacher.LOG_TAG, "fling run(). CurrentX:" + this.mCurrentX + " CurrentY:" + this.mCurrentY + " NewX:" + currX + " NewY:" + currY);
            }
            PhotoViewAttacher.this.mSuppMatrix.postTranslate(this.mCurrentX - currX, this.mCurrentY - currY);
            PhotoViewAttacher photoViewAttacher = PhotoViewAttacher.this;
            photoViewAttacher.setImageViewMatrix(photoViewAttacher.getDisplayMatrix());
            this.mCurrentX = currX;
            this.mCurrentY = currY;
            Compat.postOnAnimation(imageView, this);
        }
    }

    public interface OnMatrixChangedListener {
        void onMatrixChanged(RectF rect);
    }

    public interface OnPhotoTapListener {
        void onPhotoTap(View view, float x2, float y2);
    }

    public interface OnViewTapListener {
        void onViewTap(View view, float x2, float y2);
    }

    public PhotoViewAttacher(ImageView imageView) {
        this.mImageView = new WeakReference<>(imageView);
        imageView.setOnTouchListener(this);
        ViewTreeObserver viewTreeObserver = imageView.getViewTreeObserver();
        this.mViewTreeObserver = viewTreeObserver;
        viewTreeObserver.addOnGlobalLayoutListener(this);
        setImageViewScaleTypeMatrix(imageView);
        if (imageView.isInEditMode()) {
            return;
        }
        this.mScaleDragDetector = VersionedGestureDetector.newInstance(imageView.getContext(), this);
        GestureDetector gestureDetector = new GestureDetector(imageView.getContext(), new GestureDetector.SimpleOnGestureListener() { // from class: com.psychiatrygarden.activity.purchase.zoom.PhotoViewAttacher.1
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public void onLongPress(MotionEvent e2) {
                if (PhotoViewAttacher.this.mLongClickListener != null) {
                    PhotoViewAttacher.this.mLongClickListener.onLongClick((View) PhotoViewAttacher.this.mImageView.get());
                }
            }
        });
        this.mGestureDetector = gestureDetector;
        gestureDetector.setOnDoubleTapListener(this);
        setZoomable(true);
    }

    private void cancelFling() {
        FlingRunnable flingRunnable = this.mCurrentFlingRunnable;
        if (flingRunnable != null) {
            flingRunnable.cancelFling();
            this.mCurrentFlingRunnable = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkAndDisplayMatrix() {
        checkMatrixBounds();
        setImageViewMatrix(getDisplayMatrix());
    }

    private void checkImageViewScaleType() {
        ImageView imageView = getImageView();
        if (imageView != null && !(imageView instanceof PhotoView) && imageView.getScaleType() != ImageView.ScaleType.MATRIX) {
            throw new IllegalStateException("The ImageView's ScaleType has been changed since attaching a PhotoViewAttacher");
        }
    }

    private void checkMatrixBounds() {
        RectF displayRect;
        float f2;
        float f3;
        float f4;
        float f5;
        float f6;
        float f7;
        ImageView imageView = getImageView();
        if (imageView == null || (displayRect = getDisplayRect(getDisplayMatrix())) == null) {
            return;
        }
        float fHeight = displayRect.height();
        float fWidth = displayRect.width();
        float height = imageView.getHeight();
        float f8 = 0.0f;
        if (fHeight <= height) {
            int i2 = AnonymousClass2.$SwitchMap$android$widget$ImageView$ScaleType[this.mScaleType.ordinal()];
            if (i2 != 2) {
                if (i2 != 3) {
                    height = (height - fHeight) / 2.0f;
                    f3 = displayRect.top;
                } else {
                    height -= fHeight;
                    f3 = displayRect.top;
                }
            } else {
                f2 = displayRect.top;
                f4 = -f2;
            }
        } else {
            f2 = displayRect.top;
            if (f2 > 0.0f) {
                f4 = -f2;
            } else {
                f3 = displayRect.bottom;
                f4 = f3 < height ? height - f3 : 0.0f;
            }
        }
        float width = imageView.getWidth();
        if (fWidth <= width) {
            int i3 = AnonymousClass2.$SwitchMap$android$widget$ImageView$ScaleType[this.mScaleType.ordinal()];
            if (i3 != 2) {
                if (i3 != 3) {
                    f6 = (width - fWidth) / 2.0f;
                    f7 = displayRect.left;
                } else {
                    f6 = width - fWidth;
                    f7 = displayRect.left;
                }
                f5 = f6 - f7;
            } else {
                f5 = -displayRect.left;
            }
            f8 = f5;
            this.mScrollEdge = 2;
        } else {
            float f9 = displayRect.left;
            if (f9 > 0.0f) {
                this.mScrollEdge = 0;
                f8 = -f9;
            } else {
                float f10 = displayRect.right;
                if (f10 < width) {
                    f8 = width - f10;
                    this.mScrollEdge = 1;
                } else {
                    this.mScrollEdge = -1;
                }
            }
        }
        this.mSuppMatrix.postTranslate(f8, f4);
    }

    private static void checkZoomLevels(float minZoom, float midZoom, float maxZoom) {
        if (minZoom >= midZoom) {
            throw new IllegalArgumentException("MinZoom should be less than MidZoom");
        }
        if (midZoom >= maxZoom) {
            throw new IllegalArgumentException("MidZoom should be less than MaxZoom");
        }
    }

    private float getValue(Matrix matrix, int whichValue) {
        matrix.getValues(this.mMatrixValues);
        return this.mMatrixValues[whichValue];
    }

    private static boolean hasDrawable(ImageView imageView) {
        return (imageView == null || imageView.getDrawable() == null) ? false : true;
    }

    private static boolean isSupportedScaleType(final ImageView.ScaleType scaleType) {
        if (scaleType == null) {
            return false;
        }
        if (AnonymousClass2.$SwitchMap$android$widget$ImageView$ScaleType[scaleType.ordinal()] != 1) {
            return true;
        }
        throw new IllegalArgumentException(scaleType.name() + " is not supported in PhotoView");
    }

    private void resetMatrix() {
        this.mSuppMatrix.reset();
        setImageViewMatrix(getDisplayMatrix());
        checkMatrixBounds();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setImageViewMatrix(Matrix matrix) {
        RectF displayRect;
        ImageView imageView = getImageView();
        if (imageView != null) {
            checkImageViewScaleType();
            imageView.setImageMatrix(matrix);
            if (this.mMatrixChangeListener == null || (displayRect = getDisplayRect(matrix)) == null) {
                return;
            }
            this.mMatrixChangeListener.onMatrixChanged(displayRect);
        }
    }

    private static void setImageViewScaleTypeMatrix(ImageView imageView) {
        if (imageView == null || (imageView instanceof PhotoView)) {
            return;
        }
        imageView.setScaleType(ImageView.ScaleType.MATRIX);
    }

    private void updateBaseMatrix(Drawable d3) {
        ImageView imageView = getImageView();
        if (imageView == null || d3 == null) {
            return;
        }
        float width = imageView.getWidth();
        float height = imageView.getHeight();
        int intrinsicWidth = d3.getIntrinsicWidth();
        int intrinsicHeight = d3.getIntrinsicHeight();
        this.mBaseMatrix.reset();
        float f2 = intrinsicWidth;
        float f3 = width / f2;
        float f4 = intrinsicHeight;
        float f5 = height / f4;
        ImageView.ScaleType scaleType = this.mScaleType;
        if (scaleType == ImageView.ScaleType.CENTER) {
            this.mBaseMatrix.postTranslate((width - f2) / 2.0f, (height - f4) / 2.0f);
        } else if (scaleType == ImageView.ScaleType.CENTER_CROP) {
            float fMax = Math.max(f3, f5);
            this.mBaseMatrix.postScale(fMax, fMax);
            this.mBaseMatrix.postTranslate((width - (f2 * fMax)) / 2.0f, (height - (f4 * fMax)) / 2.0f);
        } else if (scaleType == ImageView.ScaleType.CENTER_INSIDE) {
            float fMin = Math.min(1.0f, Math.min(f3, f5));
            this.mBaseMatrix.postScale(fMin, fMin);
            this.mBaseMatrix.postTranslate((width - (f2 * fMin)) / 2.0f, (height - (f4 * fMin)) / 2.0f);
        } else {
            RectF rectF = new RectF(0.0f, 0.0f, f2, f4);
            RectF rectF2 = new RectF(0.0f, 0.0f, width, height);
            int i2 = AnonymousClass2.$SwitchMap$android$widget$ImageView$ScaleType[this.mScaleType.ordinal()];
            if (i2 == 2) {
                this.mBaseMatrix.setRectToRect(rectF, rectF2, Matrix.ScaleToFit.START);
            } else if (i2 == 3) {
                this.mBaseMatrix.setRectToRect(rectF, rectF2, Matrix.ScaleToFit.END);
            } else if (i2 == 4) {
                this.mBaseMatrix.setRectToRect(rectF, rectF2, Matrix.ScaleToFit.CENTER);
            } else if (i2 == 5) {
                this.mBaseMatrix.setRectToRect(rectF, rectF2, Matrix.ScaleToFit.FILL);
            }
        }
        resetMatrix();
    }

    @Override // com.psychiatrygarden.activity.purchase.zoom.IPhotoView
    public final boolean canZoom() {
        return this.mZoomEnabled;
    }

    @SuppressLint({"NewApi"})
    public final void cleanup() {
        WeakReference<ImageView> weakReference = this.mImageView;
        if (weakReference != null) {
            weakReference.get().getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
        ViewTreeObserver viewTreeObserver = this.mViewTreeObserver;
        if (viewTreeObserver == null || !viewTreeObserver.isAlive()) {
            return;
        }
        this.mViewTreeObserver.removeOnGlobalLayoutListener(this);
        this.mViewTreeObserver = null;
        this.mMatrixChangeListener = null;
        this.mPhotoTapListener = null;
        this.mViewTapListener = null;
        this.mImageView = null;
    }

    public Matrix getDisplayMatrix() {
        this.mDrawMatrix.set(this.mBaseMatrix);
        this.mDrawMatrix.postConcat(this.mSuppMatrix);
        return this.mDrawMatrix;
    }

    @Override // com.psychiatrygarden.activity.purchase.zoom.IPhotoView
    public final RectF getDisplayRect() {
        checkMatrixBounds();
        return getDisplayRect(getDisplayMatrix());
    }

    public final ImageView getImageView() {
        WeakReference<ImageView> weakReference = this.mImageView;
        ImageView imageView = weakReference != null ? weakReference.get() : null;
        if (imageView != null) {
            return imageView;
        }
        cleanup();
        throw new IllegalStateException("ImageView no longer exists. You should not use this PhotoViewAttacher any more.");
    }

    @Override // com.psychiatrygarden.activity.purchase.zoom.IPhotoView
    public float getMaxScale() {
        return this.mMaxScale;
    }

    @Override // com.psychiatrygarden.activity.purchase.zoom.IPhotoView
    public float getMidScale() {
        return this.mMidScale;
    }

    @Override // com.psychiatrygarden.activity.purchase.zoom.IPhotoView
    public float getMinScale() {
        return this.mMinScale;
    }

    @Override // com.psychiatrygarden.activity.purchase.zoom.IPhotoView
    public final float getScale() {
        return getValue(this.mSuppMatrix, 0);
    }

    @Override // com.psychiatrygarden.activity.purchase.zoom.IPhotoView
    public final ImageView.ScaleType getScaleType() {
        return this.mScaleType;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0024 A[Catch: ArrayIndexOutOfBoundsException -> 0x0029, TRY_LEAVE, TryCatch #0 {ArrayIndexOutOfBoundsException -> 0x0029, blocks: (B:2:0x0000, B:4:0x0012, B:7:0x001a, B:9:0x0020, B:10:0x0024), top: B:15:0x0000 }] */
    @Override // android.view.GestureDetector.OnDoubleTapListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean onDoubleTap(android.view.MotionEvent r5) {
        /*
            r4 = this;
            float r0 = r4.getScale()     // Catch: java.lang.ArrayIndexOutOfBoundsException -> L29
            float r1 = r5.getX()     // Catch: java.lang.ArrayIndexOutOfBoundsException -> L29
            float r5 = r5.getY()     // Catch: java.lang.ArrayIndexOutOfBoundsException -> L29
            float r2 = r4.mMidScale     // Catch: java.lang.ArrayIndexOutOfBoundsException -> L29
            int r3 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r3 >= 0) goto L16
            r4.zoomTo(r2, r1, r5)     // Catch: java.lang.ArrayIndexOutOfBoundsException -> L29
            goto L29
        L16:
            int r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r2 < 0) goto L24
            float r2 = r4.mMaxScale     // Catch: java.lang.ArrayIndexOutOfBoundsException -> L29
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 >= 0) goto L24
            r4.zoomTo(r2, r1, r5)     // Catch: java.lang.ArrayIndexOutOfBoundsException -> L29
            goto L29
        L24:
            float r0 = r4.mMinScale     // Catch: java.lang.ArrayIndexOutOfBoundsException -> L29
            r4.zoomTo(r0, r1, r5)     // Catch: java.lang.ArrayIndexOutOfBoundsException -> L29
        L29:
            r5 = 1
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.activity.purchase.zoom.PhotoViewAttacher.onDoubleTap(android.view.MotionEvent):boolean");
    }

    @Override // android.view.GestureDetector.OnDoubleTapListener
    public final boolean onDoubleTapEvent(MotionEvent e2) {
        return false;
    }

    @Override // com.psychiatrygarden.activity.purchase.zoom.VersionedGestureDetector.OnGestureListener
    public final void onDrag(float dx, float dy) {
        if (DEBUG) {
            Log.d(LOG_TAG, String.format("onDrag: dx: %.2f. dy: %.2f", Float.valueOf(dx), Float.valueOf(dy)));
        }
        ImageView imageView = getImageView();
        if (imageView == null || !hasDrawable(imageView)) {
            return;
        }
        this.mSuppMatrix.postTranslate(dx, dy);
        checkAndDisplayMatrix();
        if (!this.mAllowParentInterceptOnEdge || this.mScaleDragDetector.isScaling()) {
            return;
        }
        int i2 = this.mScrollEdge;
        if (i2 == 2 || ((i2 == 0 && dx >= 1.0f) || (i2 == 1 && dx <= -1.0f))) {
            imageView.getParent().requestDisallowInterceptTouchEvent(false);
        }
    }

    @Override // com.psychiatrygarden.activity.purchase.zoom.VersionedGestureDetector.OnGestureListener
    public final void onFling(float startX, float startY, float velocityX, float velocityY) {
        if (DEBUG) {
            Log.d(LOG_TAG, "onFling. sX: " + startX + " sY: " + startY + " Vx: " + velocityX + " Vy: " + velocityY);
        }
        ImageView imageView = getImageView();
        if (hasDrawable(imageView)) {
            FlingRunnable flingRunnable = new FlingRunnable(imageView.getContext());
            this.mCurrentFlingRunnable = flingRunnable;
            flingRunnable.fling(imageView.getWidth(), imageView.getHeight(), (int) velocityX, (int) velocityY);
            imageView.post(this.mCurrentFlingRunnable);
        }
    }

    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
    public final void onGlobalLayout() {
        ImageView imageView = getImageView();
        if (imageView == null || !this.mZoomEnabled) {
            return;
        }
        int top2 = imageView.getTop();
        int right = imageView.getRight();
        int bottom = imageView.getBottom();
        int left = imageView.getLeft();
        if (top2 == this.mIvTop && bottom == this.mIvBottom && left == this.mIvLeft && right == this.mIvRight) {
            return;
        }
        updateBaseMatrix(imageView.getDrawable());
        this.mIvTop = top2;
        this.mIvRight = right;
        this.mIvBottom = bottom;
        this.mIvLeft = left;
    }

    @Override // com.psychiatrygarden.activity.purchase.zoom.VersionedGestureDetector.OnGestureListener
    public final void onScale(float scaleFactor, float focusX, float focusY) {
        if (DEBUG) {
            Log.d(LOG_TAG, String.format("onScale: scale: %.2f. fX: %.2f. fY: %.2f", Float.valueOf(scaleFactor), Float.valueOf(focusX), Float.valueOf(focusY)));
        }
        if (hasDrawable(getImageView())) {
            if (getScale() < this.mMaxScale || scaleFactor < 1.0f) {
                this.mSuppMatrix.postScale(scaleFactor, scaleFactor, focusX, focusY);
                checkAndDisplayMatrix();
            }
        }
    }

    @Override // android.view.GestureDetector.OnDoubleTapListener
    public final boolean onSingleTapConfirmed(MotionEvent e2) {
        RectF displayRect;
        ImageView imageView = getImageView();
        if (imageView == null) {
            return false;
        }
        if (this.mPhotoTapListener != null && (displayRect = getDisplayRect()) != null) {
            float x2 = e2.getX();
            float y2 = e2.getY();
            if (displayRect.contains(x2, y2)) {
                this.mPhotoTapListener.onPhotoTap(imageView, (x2 - displayRect.left) / displayRect.width(), (y2 - displayRect.top) / displayRect.height());
                return true;
            }
        }
        OnViewTapListener onViewTapListener = this.mViewTapListener;
        if (onViewTapListener == null) {
            return false;
        }
        onViewTapListener.onViewTap(imageView, e2.getX(), e2.getY());
        return false;
    }

    @Override // android.view.View.OnTouchListener
    public final boolean onTouch(View v2, MotionEvent ev) {
        RectF displayRect;
        boolean z2 = false;
        if (!this.mZoomEnabled) {
            return false;
        }
        int action = ev.getAction();
        if (action == 0) {
            v2.getParent().requestDisallowInterceptTouchEvent(true);
            cancelFling();
        } else if ((action == 1 || action == 3) && getScale() < this.mMinScale && (displayRect = getDisplayRect()) != null) {
            v2.post(new AnimatedZoomRunnable(getScale(), this.mMinScale, displayRect.centerX(), displayRect.centerY()));
            z2 = true;
        }
        GestureDetector gestureDetector = this.mGestureDetector;
        if (gestureDetector != null && gestureDetector.onTouchEvent(ev)) {
            z2 = true;
        }
        VersionedGestureDetector versionedGestureDetector = this.mScaleDragDetector;
        if (versionedGestureDetector == null || !versionedGestureDetector.onTouchEvent(ev)) {
            return z2;
        }
        return true;
    }

    @Override // com.psychiatrygarden.activity.purchase.zoom.IPhotoView
    public void setAllowParentInterceptOnEdge(boolean allow) {
        this.mAllowParentInterceptOnEdge = allow;
    }

    @Override // com.psychiatrygarden.activity.purchase.zoom.IPhotoView
    public void setMaxScale(float maxScale) {
        checkZoomLevels(this.mMinScale, this.mMidScale, maxScale);
        this.mMaxScale = maxScale;
    }

    @Override // com.psychiatrygarden.activity.purchase.zoom.IPhotoView
    public void setMidScale(float midScale) {
        checkZoomLevels(this.mMinScale, midScale, this.mMaxScale);
        this.mMidScale = midScale;
    }

    @Override // com.psychiatrygarden.activity.purchase.zoom.IPhotoView
    public void setMinScale(float minScale) {
        checkZoomLevels(minScale, this.mMidScale, this.mMaxScale);
        this.mMinScale = minScale;
    }

    @Override // com.psychiatrygarden.activity.purchase.zoom.IPhotoView
    public final void setOnLongClickListener(View.OnLongClickListener listener) {
        this.mLongClickListener = listener;
    }

    @Override // com.psychiatrygarden.activity.purchase.zoom.IPhotoView
    public final void setOnMatrixChangeListener(OnMatrixChangedListener listener) {
        this.mMatrixChangeListener = listener;
    }

    @Override // com.psychiatrygarden.activity.purchase.zoom.IPhotoView
    public final void setOnPhotoTapListener(OnPhotoTapListener listener) {
        this.mPhotoTapListener = listener;
    }

    @Override // com.psychiatrygarden.activity.purchase.zoom.IPhotoView
    public final void setOnViewTapListener(OnViewTapListener listener) {
        this.mViewTapListener = listener;
    }

    @Override // com.psychiatrygarden.activity.purchase.zoom.IPhotoView
    public final void setScaleType(ImageView.ScaleType scaleType) {
        if (!isSupportedScaleType(scaleType) || scaleType == this.mScaleType) {
            return;
        }
        this.mScaleType = scaleType;
        update();
    }

    @Override // com.psychiatrygarden.activity.purchase.zoom.IPhotoView
    public final void setZoomable(boolean zoomable) {
        this.mZoomEnabled = zoomable;
        update();
    }

    public final void update() {
        ImageView imageView = getImageView();
        if (imageView != null) {
            if (!this.mZoomEnabled) {
                resetMatrix();
            } else {
                setImageViewScaleTypeMatrix(imageView);
                updateBaseMatrix(imageView.getDrawable());
            }
        }
    }

    @Override // com.psychiatrygarden.activity.purchase.zoom.IPhotoView
    public final void zoomTo(float scale, float focalX, float focalY) {
        ImageView imageView = getImageView();
        if (imageView != null) {
            imageView.post(new AnimatedZoomRunnable(getScale(), scale, focalX, focalY));
        }
    }

    private RectF getDisplayRect(Matrix matrix) {
        Drawable drawable;
        ImageView imageView = getImageView();
        if (imageView == null || (drawable = imageView.getDrawable()) == null) {
            return null;
        }
        this.mDisplayRect.set(0.0f, 0.0f, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        matrix.mapRect(this.mDisplayRect);
        return this.mDisplayRect;
    }
}
