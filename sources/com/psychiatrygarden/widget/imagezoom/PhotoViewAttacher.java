package com.psychiatrygarden.widget.imagezoom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import java.lang.ref.WeakReference;

/* loaded from: classes6.dex */
public class PhotoViewAttacher implements IPhotoView, View.OnTouchListener, OnGestureListener, ViewTreeObserver.OnGlobalLayoutListener {
    static final int EDGE_BOTH = 2;
    static final int EDGE_LEFT = 0;
    static final int EDGE_NONE = -1;
    static final int EDGE_RIGHT = 1;
    int ZOOM_DURATION;
    private boolean mAllowParentInterceptOnEdge;
    private final Matrix mBaseMatrix;
    private boolean mBlockParentIntercept;
    private FlingRunnable mCurrentFlingRunnable;
    private final RectF mDisplayRect;
    private final Matrix mDrawMatrix;
    private android.view.GestureDetector mGestureDetector;
    private WeakReference<ImageView> mImageView;
    private int mIvBottom;
    private int mIvLeft;
    private int mIvRight;
    private int mIvTop;
    private View.OnLongClickListener mLongClickListener;
    private OnMatrixChangedListener mMatrixChangeListener;
    private final float[] mMatrixValues;
    private float mMaxScale;
    private float mMidScale;
    private float mMinScale;
    private OnPhotoTapListener mPhotoTapListener;
    private OnScaleChangeListener mScaleChangeListener;
    private GestureDetector mScaleDragDetector;
    private ImageView.ScaleType mScaleType;
    private int mScrollEdge;
    private final Matrix mSuppMatrix;
    private OnViewTapListener mViewTapListener;
    private boolean mZoomEnabled;
    private static final String LOG_TAG = "PhotoViewAttacher";
    private static final boolean DEBUG = Log.isLoggable(LOG_TAG, 3);
    static final Interpolator sInterpolator = new AccelerateDecelerateInterpolator();

    /* renamed from: com.psychiatrygarden.widget.imagezoom.PhotoViewAttacher$2, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$android$widget$ImageView$ScaleType;

        static {
            int[] iArr = new int[ImageView.ScaleType.values().length];
            $SwitchMap$android$widget$ImageView$ScaleType = iArr;
            try {
                iArr[ImageView.ScaleType.FIT_START.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.FIT_END.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.FIT_CENTER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.FIT_XY.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public class AnimatedZoomRunnable implements Runnable {
        private final float mFocalX;
        private final float mFocalY;
        private final long mStartTime = System.currentTimeMillis();
        private final float mZoomEnd;
        private final float mZoomStart;

        public AnimatedZoomRunnable(final float currentZoom, final float targetZoom, final float focalX, final float focalY) {
            this.mFocalX = focalX;
            this.mFocalY = focalY;
            this.mZoomStart = currentZoom;
            this.mZoomEnd = targetZoom;
        }

        private float interpolate() {
            return PhotoViewAttacher.sInterpolator.getInterpolation(Math.min(1.0f, ((System.currentTimeMillis() - this.mStartTime) * 1.0f) / PhotoViewAttacher.this.ZOOM_DURATION));
        }

        @Override // java.lang.Runnable
        public void run() {
            ImageView imageView = PhotoViewAttacher.this.getImageView();
            if (imageView == null) {
                return;
            }
            float fInterpolate = interpolate();
            float f2 = this.mZoomStart;
            PhotoViewAttacher.this.onScale((f2 + ((this.mZoomEnd - f2) * fInterpolate)) / PhotoViewAttacher.this.getScale(), this.mFocalX, this.mFocalY);
            if (fInterpolate < 1.0f) {
                Compat.postOnAnimation(imageView, this);
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
            ImageView imageView;
            if (this.mScroller.isFinished() || (imageView = PhotoViewAttacher.this.getImageView()) == null || !this.mScroller.computeScrollOffset()) {
                return;
            }
            int currX = this.mScroller.getCurrX();
            int currY = this.mScroller.getCurrY();
            if (PhotoViewAttacher.DEBUG) {
                Log.d(PhotoViewAttacher.LOG_TAG, "fling run(). CurrentX:" + this.mCurrentX + " CurrentY:" + this.mCurrentY + " NewX:" + currX + " NewY:" + currY);
            }
            PhotoViewAttacher.this.mSuppMatrix.postTranslate(this.mCurrentX - currX, this.mCurrentY - currY);
            PhotoViewAttacher photoViewAttacher = PhotoViewAttacher.this;
            photoViewAttacher.setImageViewMatrix(photoViewAttacher.getDrawMatrix());
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

    public interface OnScaleChangeListener {
        void onScaleChange(float scaleFactor, float focusX, float focusY);
    }

    public interface OnViewTapListener {
        void onViewTap(View view, float x2, float y2);
    }

    public PhotoViewAttacher(ImageView imageView) {
        this(imageView, true);
    }

    private void cancelFling() {
        FlingRunnable flingRunnable = this.mCurrentFlingRunnable;
        if (flingRunnable != null) {
            flingRunnable.cancelFling();
            this.mCurrentFlingRunnable = null;
        }
    }

    private void checkAndDisplayMatrix() {
        if (checkMatrixBounds()) {
            setImageViewMatrix(getDrawMatrix());
        }
    }

    private void checkImageViewScaleType() {
        ImageView imageView = getImageView();
        if (imageView != null && !(imageView instanceof IPhotoView) && !ImageView.ScaleType.MATRIX.equals(imageView.getScaleType())) {
            throw new IllegalStateException("The ImageView's ScaleType has been changed since attaching a PhotoViewAttacher");
        }
    }

    private boolean checkMatrixBounds() {
        RectF displayRect;
        float f2;
        float f3;
        float f4;
        float f5;
        float f6;
        float f7;
        ImageView imageView = getImageView();
        if (imageView == null || (displayRect = getDisplayRect(getDrawMatrix())) == null) {
            return false;
        }
        float fHeight = displayRect.height();
        float fWidth = displayRect.width();
        float imageViewHeight = getImageViewHeight(imageView);
        float f8 = 0.0f;
        if (fHeight <= imageViewHeight) {
            int i2 = AnonymousClass2.$SwitchMap$android$widget$ImageView$ScaleType[this.mScaleType.ordinal()];
            if (i2 != 1) {
                if (i2 != 2) {
                    imageViewHeight = (imageViewHeight - fHeight) / 2.0f;
                    f3 = displayRect.top;
                } else {
                    imageViewHeight -= fHeight;
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
                f4 = f3 < imageViewHeight ? imageViewHeight - f3 : 0.0f;
            }
        }
        float imageViewWidth = getImageViewWidth(imageView);
        if (fWidth <= imageViewWidth) {
            int i3 = AnonymousClass2.$SwitchMap$android$widget$ImageView$ScaleType[this.mScaleType.ordinal()];
            if (i3 != 1) {
                if (i3 != 2) {
                    f6 = (imageViewWidth - fWidth) / 2.0f;
                    f7 = displayRect.left;
                } else {
                    f6 = imageViewWidth - fWidth;
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
                if (f10 < imageViewWidth) {
                    f8 = imageViewWidth - f10;
                    this.mScrollEdge = 1;
                } else {
                    this.mScrollEdge = -1;
                }
            }
        }
        this.mSuppMatrix.postTranslate(f8, f4);
        return true;
    }

    private static void checkZoomLevels(float minZoom, float midZoom, float maxZoom) {
        if (minZoom >= midZoom) {
            throw new IllegalArgumentException("MinZoom has to be less than MidZoom");
        }
        if (midZoom >= maxZoom) {
            throw new IllegalArgumentException("MidZoom has to be less than MaxZoom");
        }
    }

    private int getImageViewHeight(ImageView imageView) {
        if (imageView == null) {
            return 0;
        }
        return (imageView.getHeight() - imageView.getPaddingTop()) - imageView.getPaddingBottom();
    }

    private int getImageViewWidth(ImageView imageView) {
        if (imageView == null) {
            return 0;
        }
        return (imageView.getWidth() - imageView.getPaddingLeft()) - imageView.getPaddingRight();
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
        if (scaleType != ImageView.ScaleType.MATRIX) {
            return true;
        }
        throw new IllegalArgumentException(scaleType.name() + " is not supported in PhotoView");
    }

    private void resetMatrix() {
        this.mSuppMatrix.reset();
        setImageViewMatrix(getDrawMatrix());
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
        if (imageView == null || (imageView instanceof IPhotoView) || ImageView.ScaleType.MATRIX.equals(imageView.getScaleType())) {
            return;
        }
        imageView.setScaleType(ImageView.ScaleType.MATRIX);
    }

    private void updateBaseMatrix(Drawable d3) {
        ImageView imageView = getImageView();
        if (imageView == null || d3 == null) {
            return;
        }
        float imageViewWidth = getImageViewWidth(imageView);
        float imageViewHeight = getImageViewHeight(imageView);
        int intrinsicWidth = d3.getIntrinsicWidth();
        int intrinsicHeight = d3.getIntrinsicHeight();
        this.mBaseMatrix.reset();
        float f2 = intrinsicWidth;
        float f3 = imageViewWidth / f2;
        float f4 = intrinsicHeight;
        float f5 = imageViewHeight / f4;
        ImageView.ScaleType scaleType = this.mScaleType;
        if (scaleType == ImageView.ScaleType.CENTER) {
            this.mBaseMatrix.postTranslate((imageViewWidth - f2) / 2.0f, (imageViewHeight - f4) / 2.0f);
        } else if (scaleType == ImageView.ScaleType.CENTER_CROP) {
            float fMax = Math.max(f3, f5);
            this.mBaseMatrix.postScale(fMax, fMax);
            this.mBaseMatrix.postTranslate((imageViewWidth - (f2 * fMax)) / 2.0f, (imageViewHeight - (f4 * fMax)) / 2.0f);
        } else if (scaleType == ImageView.ScaleType.CENTER_INSIDE) {
            float fMin = Math.min(1.0f, Math.min(f3, f5));
            this.mBaseMatrix.postScale(fMin, fMin);
            this.mBaseMatrix.postTranslate((imageViewWidth - (f2 * fMin)) / 2.0f, (imageViewHeight - (f4 * fMin)) / 2.0f);
        } else {
            RectF rectF = new RectF(0.0f, 0.0f, f2, f4);
            RectF rectF2 = new RectF(0.0f, 0.0f, imageViewWidth, imageViewHeight);
            int i2 = AnonymousClass2.$SwitchMap$android$widget$ImageView$ScaleType[this.mScaleType.ordinal()];
            if (i2 == 1) {
                this.mBaseMatrix.setRectToRect(rectF, rectF2, Matrix.ScaleToFit.START);
            } else if (i2 == 2) {
                this.mBaseMatrix.setRectToRect(rectF, rectF2, Matrix.ScaleToFit.END);
            } else if (i2 == 3) {
                this.mBaseMatrix.setRectToRect(rectF, rectF2, Matrix.ScaleToFit.CENTER);
            } else if (i2 == 4) {
                this.mBaseMatrix.setRectToRect(rectF, rectF2, Matrix.ScaleToFit.FILL);
            }
        }
        resetMatrix();
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    public boolean canZoom() {
        return this.mZoomEnabled;
    }

    public void cleanup() {
        WeakReference<ImageView> weakReference = this.mImageView;
        if (weakReference == null) {
            return;
        }
        ImageView imageView = weakReference.get();
        if (imageView != null) {
            ViewTreeObserver viewTreeObserver = imageView.getViewTreeObserver();
            if (viewTreeObserver != null && viewTreeObserver.isAlive()) {
                viewTreeObserver.removeGlobalOnLayoutListener(this);
            }
            imageView.setOnTouchListener(null);
            cancelFling();
        }
        android.view.GestureDetector gestureDetector = this.mGestureDetector;
        if (gestureDetector != null) {
            gestureDetector.setOnDoubleTapListener(null);
        }
        this.mMatrixChangeListener = null;
        this.mPhotoTapListener = null;
        this.mViewTapListener = null;
        this.mImageView = null;
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    public Matrix getDisplayMatrix() {
        return new Matrix(getDrawMatrix());
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    public RectF getDisplayRect() {
        checkMatrixBounds();
        return getDisplayRect(getDrawMatrix());
    }

    public Matrix getDrawMatrix() {
        this.mDrawMatrix.set(this.mBaseMatrix);
        this.mDrawMatrix.postConcat(this.mSuppMatrix);
        return this.mDrawMatrix;
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    public IPhotoView getIPhotoViewImplementation() {
        return this;
    }

    public ImageView getImageView() {
        WeakReference<ImageView> weakReference = this.mImageView;
        ImageView imageView = weakReference != null ? weakReference.get() : null;
        if (imageView == null) {
            cleanup();
            Log.i(LOG_TAG, "ImageView no longer exists. You should not use this PhotoViewAttacher any more.");
        }
        return imageView;
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    @Deprecated
    public float getMaxScale() {
        return getMaximumScale();
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    public float getMaximumScale() {
        return this.mMaxScale;
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    public float getMediumScale() {
        return this.mMidScale;
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    @Deprecated
    public float getMidScale() {
        return getMediumScale();
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    @Deprecated
    public float getMinScale() {
        return getMinimumScale();
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    public float getMinimumScale() {
        return this.mMinScale;
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    public OnPhotoTapListener getOnPhotoTapListener() {
        return this.mPhotoTapListener;
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    public OnViewTapListener getOnViewTapListener() {
        return this.mViewTapListener;
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    public float getScale() {
        return (float) Math.sqrt(((float) Math.pow(getValue(this.mSuppMatrix, 0), 2.0d)) + ((float) Math.pow(getValue(this.mSuppMatrix, 3), 2.0d)));
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    public ImageView.ScaleType getScaleType() {
        return this.mScaleType;
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    public Bitmap getVisibleRectangleBitmap() {
        ImageView imageView = getImageView();
        if (imageView == null) {
            return null;
        }
        return imageView.getDrawingCache();
    }

    @Override // com.psychiatrygarden.widget.imagezoom.OnGestureListener
    public void onDrag(float dx, float dy) {
        if (this.mScaleDragDetector.isScaling()) {
            return;
        }
        if (DEBUG) {
            Log.d(LOG_TAG, String.format("onDrag: dx: %.2f. dy: %.2f", Float.valueOf(dx), Float.valueOf(dy)));
        }
        ImageView imageView = getImageView();
        this.mSuppMatrix.postTranslate(dx, dy);
        checkAndDisplayMatrix();
        ViewParent parent = imageView.getParent();
        if (!this.mAllowParentInterceptOnEdge || this.mScaleDragDetector.isScaling() || this.mBlockParentIntercept) {
            if (parent != null) {
                parent.requestDisallowInterceptTouchEvent(true);
                return;
            }
            return;
        }
        int i2 = this.mScrollEdge;
        if ((i2 == 2 || ((i2 == 0 && dx >= 1.0f) || (i2 == 1 && dx <= -1.0f))) && parent != null) {
            parent.requestDisallowInterceptTouchEvent(false);
        }
    }

    @Override // com.psychiatrygarden.widget.imagezoom.OnGestureListener
    public void onFling(float startX, float startY, float velocityX, float velocityY) {
        if (DEBUG) {
            Log.d(LOG_TAG, "onFling. sX: " + startX + " sY: " + startY + " Vx: " + velocityX + " Vy: " + velocityY);
        }
        ImageView imageView = getImageView();
        FlingRunnable flingRunnable = new FlingRunnable(imageView.getContext());
        this.mCurrentFlingRunnable = flingRunnable;
        flingRunnable.fling(getImageViewWidth(imageView), getImageViewHeight(imageView), (int) velocityX, (int) velocityY);
        imageView.post(this.mCurrentFlingRunnable);
    }

    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
    public void onGlobalLayout() {
        ImageView imageView = getImageView();
        if (imageView != null) {
            if (!this.mZoomEnabled) {
                updateBaseMatrix(imageView.getDrawable());
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
    }

    @Override // com.psychiatrygarden.widget.imagezoom.OnGestureListener
    public void onScale(float scaleFactor, float focusX, float focusY) {
        if (DEBUG) {
            Log.d(LOG_TAG, String.format("onScale: scale: %.2f. fX: %.2f. fY: %.2f", Float.valueOf(scaleFactor), Float.valueOf(focusX), Float.valueOf(focusY)));
        }
        if (getScale() < this.mMaxScale || scaleFactor < 1.0f) {
            OnScaleChangeListener onScaleChangeListener = this.mScaleChangeListener;
            if (onScaleChangeListener != null) {
                onScaleChangeListener.onScaleChange(scaleFactor, focusX, focusY);
            }
            this.mSuppMatrix.postScale(scaleFactor, scaleFactor, focusX, focusY);
            checkAndDisplayMatrix();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x005e  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0091  */
    @Override // android.view.View.OnTouchListener
    @android.annotation.SuppressLint({"ClickableViewAccessibility"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouch(android.view.View r11, android.view.MotionEvent r12) {
        /*
            r10 = this;
            boolean r0 = r10.mZoomEnabled
            r1 = 0
            if (r0 == 0) goto L9d
            r0 = r11
            android.widget.ImageView r0 = (android.widget.ImageView) r0
            boolean r0 = hasDrawable(r0)
            if (r0 == 0) goto L9d
            android.view.ViewParent r0 = r11.getParent()
            int r2 = r12.getAction()
            r3 = 1
            if (r2 == 0) goto L49
            if (r2 == r3) goto L1f
            r0 = 3
            if (r2 == r0) goto L1f
            goto L59
        L1f:
            float r0 = r10.getScale()
            float r2 = r10.mMinScale
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 >= 0) goto L59
            android.graphics.RectF r0 = r10.getDisplayRect()
            if (r0 == 0) goto L59
            com.psychiatrygarden.widget.imagezoom.PhotoViewAttacher$AnimatedZoomRunnable r2 = new com.psychiatrygarden.widget.imagezoom.PhotoViewAttacher$AnimatedZoomRunnable
            float r6 = r10.getScale()
            float r7 = r10.mMinScale
            float r8 = r0.centerX()
            float r9 = r0.centerY()
            r4 = r2
            r5 = r10
            r4.<init>(r6, r7, r8, r9)
            r11.post(r2)
            r11 = r3
            goto L5a
        L49:
            if (r0 == 0) goto L4f
            r0.requestDisallowInterceptTouchEvent(r3)
            goto L56
        L4f:
            java.lang.String r11 = "PhotoViewAttacher"
            java.lang.String r0 = "onTouch getParent() returned null"
            android.util.Log.i(r11, r0)
        L56:
            r10.cancelFling()
        L59:
            r11 = r1
        L5a:
            com.psychiatrygarden.widget.imagezoom.GestureDetector r0 = r10.mScaleDragDetector
            if (r0 == 0) goto L91
            boolean r11 = r0.isScaling()
            com.psychiatrygarden.widget.imagezoom.GestureDetector r0 = r10.mScaleDragDetector
            boolean r0 = r0.isDragging()
            com.psychiatrygarden.widget.imagezoom.GestureDetector r2 = r10.mScaleDragDetector
            boolean r2 = r2.onTouchEvent(r12)
            if (r11 != 0) goto L7a
            com.psychiatrygarden.widget.imagezoom.GestureDetector r11 = r10.mScaleDragDetector
            boolean r11 = r11.isScaling()
            if (r11 != 0) goto L7a
            r11 = r3
            goto L7b
        L7a:
            r11 = r1
        L7b:
            if (r0 != 0) goto L87
            com.psychiatrygarden.widget.imagezoom.GestureDetector r0 = r10.mScaleDragDetector
            boolean r0 = r0.isDragging()
            if (r0 != 0) goto L87
            r0 = r3
            goto L88
        L87:
            r0 = r1
        L88:
            if (r11 == 0) goto L8d
            if (r0 == 0) goto L8d
            r1 = r3
        L8d:
            r10.mBlockParentIntercept = r1
            r1 = r2
            goto L92
        L91:
            r1 = r11
        L92:
            android.view.GestureDetector r11 = r10.mGestureDetector
            if (r11 == 0) goto L9d
            boolean r11 = r11.onTouchEvent(r12)
            if (r11 == 0) goto L9d
            r1 = r3
        L9d:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.widget.imagezoom.PhotoViewAttacher.onTouch(android.view.View, android.view.MotionEvent):boolean");
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    public void setAllowParentInterceptOnEdge(boolean allow) {
        this.mAllowParentInterceptOnEdge = allow;
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    public boolean setDisplayMatrix(Matrix finalMatrix) {
        if (finalMatrix == null) {
            throw new IllegalArgumentException("Matrix cannot be null");
        }
        ImageView imageView = getImageView();
        if (imageView == null || imageView.getDrawable() == null) {
            return false;
        }
        this.mSuppMatrix.set(finalMatrix);
        setImageViewMatrix(getDrawMatrix());
        checkMatrixBounds();
        return true;
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    @Deprecated
    public void setMaxScale(float maxScale) {
        setMaximumScale(maxScale);
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    public void setMaximumScale(float maximumScale) {
        checkZoomLevels(this.mMinScale, this.mMidScale, maximumScale);
        this.mMaxScale = maximumScale;
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    public void setMediumScale(float mediumScale) {
        checkZoomLevels(this.mMinScale, mediumScale, this.mMaxScale);
        this.mMidScale = mediumScale;
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    @Deprecated
    public void setMidScale(float midScale) {
        setMediumScale(midScale);
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    @Deprecated
    public void setMinScale(float minScale) {
        setMinimumScale(minScale);
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    public void setMinimumScale(float minimumScale) {
        checkZoomLevels(minimumScale, this.mMidScale, this.mMaxScale);
        this.mMinScale = minimumScale;
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener newOnDoubleTapListener) {
        if (newOnDoubleTapListener != null) {
            this.mGestureDetector.setOnDoubleTapListener(newOnDoubleTapListener);
        } else {
            this.mGestureDetector.setOnDoubleTapListener(new DefaultOnDoubleTapListener(this));
        }
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    public void setOnLongClickListener(View.OnLongClickListener listener) {
        this.mLongClickListener = listener;
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    public void setOnMatrixChangeListener(OnMatrixChangedListener listener) {
        this.mMatrixChangeListener = listener;
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    public void setOnPhotoTapListener(OnPhotoTapListener listener) {
        this.mPhotoTapListener = listener;
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    public void setOnScaleChangeListener(OnScaleChangeListener onScaleChangeListener) {
        this.mScaleChangeListener = onScaleChangeListener;
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    public void setOnViewTapListener(OnViewTapListener listener) {
        this.mViewTapListener = listener;
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    public void setPhotoViewRotation(float degrees) {
        this.mSuppMatrix.setRotate(degrees % 360.0f);
        checkAndDisplayMatrix();
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    public void setRotationBy(float degrees) {
        this.mSuppMatrix.postRotate(degrees % 360.0f);
        checkAndDisplayMatrix();
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    public void setRotationTo(float degrees) {
        this.mSuppMatrix.setRotate(degrees % 360.0f);
        checkAndDisplayMatrix();
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    public void setScale(float scale) {
        setScale(scale, false);
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    public void setScaleLevels(float minimumScale, float mediumScale, float maximumScale) {
        checkZoomLevels(minimumScale, mediumScale, maximumScale);
        this.mMinScale = minimumScale;
        this.mMidScale = mediumScale;
        this.mMaxScale = maximumScale;
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    public void setScaleType(ImageView.ScaleType scaleType) {
        if (!isSupportedScaleType(scaleType) || scaleType == this.mScaleType) {
            return;
        }
        this.mScaleType = scaleType;
        update();
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    public void setZoomTransitionDuration(int milliseconds) {
        if (milliseconds < 0) {
            milliseconds = 200;
        }
        this.ZOOM_DURATION = milliseconds;
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    public void setZoomable(boolean zoomable) {
        this.mZoomEnabled = zoomable;
        update();
    }

    public void update() {
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

    public PhotoViewAttacher(ImageView imageView, boolean zoomable) {
        this.ZOOM_DURATION = 200;
        this.mMinScale = 1.0f;
        this.mMidScale = 1.75f;
        this.mMaxScale = 3.0f;
        this.mAllowParentInterceptOnEdge = true;
        this.mBlockParentIntercept = false;
        this.mBaseMatrix = new Matrix();
        this.mDrawMatrix = new Matrix();
        this.mSuppMatrix = new Matrix();
        this.mDisplayRect = new RectF();
        this.mMatrixValues = new float[9];
        this.mScrollEdge = 2;
        this.mScaleType = ImageView.ScaleType.FIT_CENTER;
        this.mImageView = new WeakReference<>(imageView);
        imageView.setDrawingCacheEnabled(true);
        imageView.setOnTouchListener(this);
        ViewTreeObserver viewTreeObserver = imageView.getViewTreeObserver();
        if (viewTreeObserver != null) {
            viewTreeObserver.addOnGlobalLayoutListener(this);
        }
        setImageViewScaleTypeMatrix(imageView);
        if (imageView.isInEditMode()) {
            return;
        }
        this.mScaleDragDetector = VersionedGestureDetector.newInstance(imageView.getContext(), this);
        android.view.GestureDetector gestureDetector = new android.view.GestureDetector(imageView.getContext(), new GestureDetector.SimpleOnGestureListener() { // from class: com.psychiatrygarden.widget.imagezoom.PhotoViewAttacher.1
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public void onLongPress(MotionEvent e2) {
                if (PhotoViewAttacher.this.mLongClickListener != null) {
                    PhotoViewAttacher.this.mLongClickListener.onLongClick(PhotoViewAttacher.this.getImageView());
                }
            }
        });
        this.mGestureDetector = gestureDetector;
        gestureDetector.setOnDoubleTapListener(new DefaultOnDoubleTapListener(this));
        setZoomable(zoomable);
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    public void setScale(float scale, boolean animate) {
        if (getImageView() != null) {
            setScale(scale, r0.getRight() / 2.0f, r0.getBottom() / 2.0f, animate);
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

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    public void setScale(float scale, float focalX, float focalY, boolean animate) {
        ImageView imageView = getImageView();
        if (imageView != null) {
            if (scale < this.mMinScale || scale > this.mMaxScale) {
                Log.i(LOG_TAG, "Scale must be within the range of minScale and maxScale");
            } else if (animate) {
                imageView.post(new AnimatedZoomRunnable(getScale(), scale, focalX, focalY));
            } else {
                this.mSuppMatrix.setScale(scale, scale, focalX, focalY);
                checkAndDisplayMatrix();
            }
        }
    }
}
