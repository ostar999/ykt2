package com.lxj.xpopup.photoview;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.OverScroller;

/* loaded from: classes4.dex */
public class PhotoViewAttacher implements View.OnTouchListener, View.OnLayoutChangeListener {
    private static float DEFAULT_MAX_SCALE = 4.0f;
    private static float DEFAULT_MID_SCALE = 2.5f;
    private static float DEFAULT_MIN_SCALE = 1.0f;
    private static int DEFAULT_ZOOM_DURATION = 200;
    private static final int HORIZONTAL_EDGE_BOTH = 2;
    private static final int HORIZONTAL_EDGE_LEFT = 0;
    private static final int HORIZONTAL_EDGE_NONE = -1;
    private static final int HORIZONTAL_EDGE_RIGHT = 1;
    private static int SINGLE_TOUCH = 1;
    private static final int VERTICAL_EDGE_BOTH = 2;
    private static final int VERTICAL_EDGE_BOTTOM = 1;
    private static final int VERTICAL_EDGE_NONE = -1;
    private static final int VERTICAL_EDGE_TOP = 0;
    public boolean isBottomEnd;
    public boolean isHorizontal;
    public boolean isLeftEnd;
    public boolean isTopEnd;
    public boolean isVertical;
    private float mBaseRotation;
    private FlingRunnable mCurrentFlingRunnable;
    private GestureDetector mGestureDetector;
    private ImageView mImageView;
    private View.OnLongClickListener mLongClickListener;
    private OnMatrixChangedListener mMatrixChangeListener;
    private View.OnClickListener mOnClickListener;
    private OnViewDragListener mOnViewDragListener;
    private OnOutsidePhotoTapListener mOutsidePhotoTapListener;
    private OnPhotoTapListener mPhotoTapListener;
    private OnScaleChangedListener mScaleChangeListener;
    private CustomGestureDetector mScaleDragDetector;
    private OnSingleFlingListener mSingleFlingListener;
    private OnViewTapListener mViewTapListener;

    /* renamed from: x, reason: collision with root package name */
    float f8902x;

    /* renamed from: y, reason: collision with root package name */
    float f8903y;
    private Interpolator mInterpolator = new AccelerateDecelerateInterpolator();
    private int mZoomDuration = DEFAULT_ZOOM_DURATION;
    private float mMinScale = DEFAULT_MIN_SCALE;
    private float mMidScale = DEFAULT_MID_SCALE;
    private float mMaxScale = DEFAULT_MAX_SCALE;
    private boolean mAllowParentInterceptOnEdge = true;
    private boolean mBlockParentIntercept = false;
    private final Matrix mBaseMatrix = new Matrix();
    private final Matrix mDrawMatrix = new Matrix();
    private final Matrix mSuppMatrix = new Matrix();
    private final RectF mDisplayRect = new RectF();
    private final float[] mMatrixValues = new float[9];
    private int mHorizontalScrollEdge = 2;
    private int mVerticalScrollEdge = 2;
    public boolean isRightEnd = false;
    private boolean mZoomEnabled = true;
    private boolean isLongImage = false;
    private ImageView.ScaleType mScaleType = ImageView.ScaleType.FIT_CENTER;
    private OnGestureListener onGestureListener = new OnGestureListener() { // from class: com.lxj.xpopup.photoview.PhotoViewAttacher.1
        @Override // com.lxj.xpopup.photoview.OnGestureListener
        public void onDrag(float f2, float f3) {
            if (PhotoViewAttacher.this.mScaleDragDetector.isScaling()) {
                return;
            }
            if (PhotoViewAttacher.this.mOnViewDragListener != null) {
                PhotoViewAttacher.this.mOnViewDragListener.onDrag(f2, f3);
            }
            PhotoViewAttacher.this.mSuppMatrix.postTranslate(f2, f3);
            PhotoViewAttacher.this.checkAndDisplayMatrix();
            PhotoViewAttacher photoViewAttacher = PhotoViewAttacher.this;
            photoViewAttacher.isTopEnd = photoViewAttacher.mVerticalScrollEdge == 0 && PhotoViewAttacher.this.getScale() != 1.0f;
            PhotoViewAttacher photoViewAttacher2 = PhotoViewAttacher.this;
            photoViewAttacher2.isBottomEnd = photoViewAttacher2.mVerticalScrollEdge == 1 && PhotoViewAttacher.this.getScale() != 1.0f;
            PhotoViewAttacher photoViewAttacher3 = PhotoViewAttacher.this;
            photoViewAttacher3.isLeftEnd = photoViewAttacher3.mHorizontalScrollEdge == 0 && PhotoViewAttacher.this.getScale() != 1.0f;
            PhotoViewAttacher photoViewAttacher4 = PhotoViewAttacher.this;
            photoViewAttacher4.isRightEnd = photoViewAttacher4.mHorizontalScrollEdge == 1 && PhotoViewAttacher.this.getScale() != 1.0f;
            ViewParent parent = PhotoViewAttacher.this.mImageView.getParent();
            if (parent == null) {
                return;
            }
            if (!PhotoViewAttacher.this.mAllowParentInterceptOnEdge || PhotoViewAttacher.this.mScaleDragDetector.isScaling() || PhotoViewAttacher.this.mBlockParentIntercept) {
                if (PhotoViewAttacher.this.mHorizontalScrollEdge == 2 && PhotoViewAttacher.this.isLongImage && PhotoViewAttacher.this.isHorizontal) {
                    parent.requestDisallowInterceptTouchEvent(false);
                    return;
                }
                if ((PhotoViewAttacher.this.mHorizontalScrollEdge != 1 && PhotoViewAttacher.this.mHorizontalScrollEdge != 0) || PhotoViewAttacher.this.isLongImage || PhotoViewAttacher.this.isHorizontal) {
                    parent.requestDisallowInterceptTouchEvent(true);
                    return;
                } else {
                    parent.requestDisallowInterceptTouchEvent(false);
                    return;
                }
            }
            if ((PhotoViewAttacher.this.mHorizontalScrollEdge == 2 && !PhotoViewAttacher.this.isLongImage) || ((PhotoViewAttacher.this.mHorizontalScrollEdge == 0 && f2 >= 0.0f && PhotoViewAttacher.this.isHorizontal) || (PhotoViewAttacher.this.mHorizontalScrollEdge == 1 && f2 <= -0.0f && PhotoViewAttacher.this.isHorizontal))) {
                parent.requestDisallowInterceptTouchEvent(false);
                return;
            }
            if (PhotoViewAttacher.this.mVerticalScrollEdge != 2 || !PhotoViewAttacher.this.isVertical) {
                PhotoViewAttacher photoViewAttacher5 = PhotoViewAttacher.this;
                if ((!photoViewAttacher5.isTopEnd || f3 <= 0.0f || !photoViewAttacher5.isVertical) && (!photoViewAttacher5.isBottomEnd || f3 >= 0.0f || !photoViewAttacher5.isVertical)) {
                    if (photoViewAttacher5.isLongImage) {
                        if ((PhotoViewAttacher.this.mVerticalScrollEdge == 0 && f3 > 0.0f && PhotoViewAttacher.this.isVertical) || (PhotoViewAttacher.this.mVerticalScrollEdge == 1 && f3 < 0.0f && PhotoViewAttacher.this.isVertical)) {
                            parent.requestDisallowInterceptTouchEvent(false);
                            return;
                        }
                        return;
                    }
                    return;
                }
            }
            parent.requestDisallowInterceptTouchEvent(false);
        }

        @Override // com.lxj.xpopup.photoview.OnGestureListener
        public void onFling(float f2, float f3, float f4, float f5) {
            PhotoViewAttacher photoViewAttacher = PhotoViewAttacher.this;
            photoViewAttacher.mCurrentFlingRunnable = photoViewAttacher.new FlingRunnable(photoViewAttacher.mImageView.getContext());
            FlingRunnable flingRunnable = PhotoViewAttacher.this.mCurrentFlingRunnable;
            PhotoViewAttacher photoViewAttacher2 = PhotoViewAttacher.this;
            int imageViewWidth = photoViewAttacher2.getImageViewWidth(photoViewAttacher2.mImageView);
            PhotoViewAttacher photoViewAttacher3 = PhotoViewAttacher.this;
            flingRunnable.fling(imageViewWidth, photoViewAttacher3.getImageViewHeight(photoViewAttacher3.mImageView), (int) f4, (int) f5);
            PhotoViewAttacher.this.mImageView.post(PhotoViewAttacher.this.mCurrentFlingRunnable);
        }

        @Override // com.lxj.xpopup.photoview.OnGestureListener
        public void onScale(float f2, float f3, float f4) {
            if (PhotoViewAttacher.this.getScale() < PhotoViewAttacher.this.mMaxScale || f2 < 1.0f) {
                if (PhotoViewAttacher.this.mScaleChangeListener != null) {
                    PhotoViewAttacher.this.mScaleChangeListener.onScaleChange(f2, f3, f4);
                }
                PhotoViewAttacher.this.mSuppMatrix.postScale(f2, f2, f3, f4);
                PhotoViewAttacher.this.checkAndDisplayMatrix();
            }
        }
    };

    /* renamed from: com.lxj.xpopup.photoview.PhotoViewAttacher$4, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass4 {
        static final /* synthetic */ int[] $SwitchMap$android$widget$ImageView$ScaleType;

        static {
            int[] iArr = new int[ImageView.ScaleType.values().length];
            $SwitchMap$android$widget$ImageView$ScaleType = iArr;
            try {
                iArr[ImageView.ScaleType.FIT_CENTER.ordinal()] = 1;
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

        public AnimatedZoomRunnable(float f2, float f3, float f4, float f5) {
            this.mFocalX = f4;
            this.mFocalY = f5;
            this.mZoomStart = f2;
            this.mZoomEnd = f3;
        }

        private float interpolate() {
            return PhotoViewAttacher.this.mInterpolator.getInterpolation(Math.min(1.0f, ((System.currentTimeMillis() - this.mStartTime) * 1.0f) / PhotoViewAttacher.this.mZoomDuration));
        }

        @Override // java.lang.Runnable
        public void run() {
            float fInterpolate = interpolate();
            float f2 = this.mZoomStart;
            PhotoViewAttacher.this.onGestureListener.onScale((f2 + ((this.mZoomEnd - f2) * fInterpolate)) / PhotoViewAttacher.this.getScale(), this.mFocalX, this.mFocalY);
            if (fInterpolate < 1.0f) {
                Compat.postOnAnimation(PhotoViewAttacher.this.mImageView, this);
            }
        }
    }

    public class FlingRunnable implements Runnable {
        private int mCurrentX;
        private int mCurrentY;
        private final OverScroller mScroller;

        public FlingRunnable(Context context) {
            this.mScroller = new OverScroller(context);
        }

        public void cancelFling() {
            this.mScroller.forceFinished(true);
        }

        public void fling(int i2, int i3, int i4, int i5) {
            int i6;
            int iRound;
            int i7;
            int iRound2;
            RectF displayRect = PhotoViewAttacher.this.getDisplayRect();
            if (displayRect == null) {
                return;
            }
            int iRound3 = Math.round(-displayRect.left);
            float f2 = i2;
            if (f2 < displayRect.width()) {
                iRound = Math.round(displayRect.width() - f2);
                i6 = 0;
            } else {
                i6 = iRound3;
                iRound = i6;
            }
            int iRound4 = Math.round(-displayRect.top);
            float f3 = i3;
            if (f3 < displayRect.height()) {
                iRound2 = Math.round(displayRect.height() - f3);
                i7 = 0;
            } else {
                i7 = iRound4;
                iRound2 = i7;
            }
            this.mCurrentX = iRound3;
            this.mCurrentY = iRound4;
            if (iRound3 == iRound && iRound4 == iRound2) {
                return;
            }
            this.mScroller.fling(iRound3, iRound4, i4, i5, i6, iRound, i7, iRound2, 0, 0);
        }

        @Override // java.lang.Runnable
        public void run() {
            if (!this.mScroller.isFinished() && this.mScroller.computeScrollOffset()) {
                int currX = this.mScroller.getCurrX();
                int currY = this.mScroller.getCurrY();
                PhotoViewAttacher.this.mSuppMatrix.postTranslate(this.mCurrentX - currX, this.mCurrentY - currY);
                PhotoViewAttacher.this.checkAndDisplayMatrix();
                this.mCurrentX = currX;
                this.mCurrentY = currY;
                Compat.postOnAnimation(PhotoViewAttacher.this.mImageView, this);
            }
        }
    }

    public PhotoViewAttacher(ImageView imageView) {
        this.mImageView = imageView;
        imageView.setOnTouchListener(this);
        imageView.addOnLayoutChangeListener(this);
        if (imageView.isInEditMode()) {
            return;
        }
        this.mBaseRotation = 0.0f;
        this.mScaleDragDetector = new CustomGestureDetector(imageView.getContext(), this.onGestureListener);
        GestureDetector gestureDetector = new GestureDetector(imageView.getContext(), new GestureDetector.SimpleOnGestureListener() { // from class: com.lxj.xpopup.photoview.PhotoViewAttacher.2
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f2, float f3) {
                if (PhotoViewAttacher.this.mSingleFlingListener == null || PhotoViewAttacher.this.getScale() > PhotoViewAttacher.DEFAULT_MIN_SCALE || motionEvent.getPointerCount() > PhotoViewAttacher.SINGLE_TOUCH || motionEvent2.getPointerCount() > PhotoViewAttacher.SINGLE_TOUCH) {
                    return false;
                }
                return PhotoViewAttacher.this.mSingleFlingListener.onFling(motionEvent, motionEvent2, f2, f3);
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public void onLongPress(MotionEvent motionEvent) {
                if (PhotoViewAttacher.this.mLongClickListener != null) {
                    PhotoViewAttacher.this.mLongClickListener.onLongClick(PhotoViewAttacher.this.mImageView);
                }
            }
        });
        this.mGestureDetector = gestureDetector;
        gestureDetector.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() { // from class: com.lxj.xpopup.photoview.PhotoViewAttacher.3
            @Override // android.view.GestureDetector.OnDoubleTapListener
            public boolean onDoubleTap(MotionEvent motionEvent) {
                try {
                    float scale = PhotoViewAttacher.this.getScale();
                    float x2 = motionEvent.getX();
                    float y2 = motionEvent.getY();
                    if (scale < PhotoViewAttacher.this.getMediumScale()) {
                        PhotoViewAttacher photoViewAttacher = PhotoViewAttacher.this;
                        photoViewAttacher.setScale(photoViewAttacher.getMediumScale(), x2, y2, true);
                    } else if (scale < PhotoViewAttacher.this.getMediumScale() || scale >= PhotoViewAttacher.this.getMaximumScale()) {
                        PhotoViewAttacher photoViewAttacher2 = PhotoViewAttacher.this;
                        photoViewAttacher2.setScale(photoViewAttacher2.getMinimumScale(), x2, y2, true);
                    } else {
                        PhotoViewAttacher photoViewAttacher3 = PhotoViewAttacher.this;
                        photoViewAttacher3.setScale(photoViewAttacher3.getMaximumScale(), x2, y2, true);
                    }
                } catch (ArrayIndexOutOfBoundsException unused) {
                }
                return true;
            }

            @Override // android.view.GestureDetector.OnDoubleTapListener
            public boolean onDoubleTapEvent(MotionEvent motionEvent) {
                return true;
            }

            @Override // android.view.GestureDetector.OnDoubleTapListener
            public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
                if (PhotoViewAttacher.this.mOnClickListener != null) {
                    PhotoViewAttacher.this.mOnClickListener.onClick(PhotoViewAttacher.this.mImageView);
                }
                RectF displayRect = PhotoViewAttacher.this.getDisplayRect();
                float x2 = motionEvent.getX();
                float y2 = motionEvent.getY();
                if (PhotoViewAttacher.this.mViewTapListener != null) {
                    PhotoViewAttacher.this.mViewTapListener.onViewTap(PhotoViewAttacher.this.mImageView, x2, y2);
                }
                if (displayRect == null) {
                    return false;
                }
                if (!displayRect.contains(x2, y2)) {
                    if (PhotoViewAttacher.this.mOutsidePhotoTapListener == null) {
                        return false;
                    }
                    PhotoViewAttacher.this.mOutsidePhotoTapListener.onOutsidePhotoTap(PhotoViewAttacher.this.mImageView);
                    return false;
                }
                float fWidth = (x2 - displayRect.left) / displayRect.width();
                float fHeight = (y2 - displayRect.top) / displayRect.height();
                if (PhotoViewAttacher.this.mPhotoTapListener == null) {
                    return true;
                }
                PhotoViewAttacher.this.mPhotoTapListener.onPhotoTap(PhotoViewAttacher.this.mImageView, fWidth, fHeight);
                return true;
            }
        });
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
        if (checkMatrixBounds()) {
            setImageViewMatrix(getDrawMatrix());
        }
    }

    private boolean checkMatrixBounds() {
        float f2;
        float f3;
        float f4;
        float f5;
        float f6;
        RectF displayRect = getDisplayRect(getDrawMatrix());
        if (displayRect == null) {
            return false;
        }
        float fHeight = displayRect.height();
        float fWidth = displayRect.width();
        float imageViewHeight = getImageViewHeight(this.mImageView);
        float f7 = 0.0f;
        if (fHeight > imageViewHeight || displayRect.top < 0.0f) {
            float f8 = displayRect.top;
            if (f8 >= 0.0f) {
                this.mVerticalScrollEdge = 0;
                f2 = -f8;
            } else {
                float f9 = displayRect.bottom;
                if (f9 <= imageViewHeight) {
                    this.mVerticalScrollEdge = 1;
                    f2 = imageViewHeight - f9;
                } else {
                    this.mVerticalScrollEdge = -1;
                    f2 = 0.0f;
                }
            }
        } else {
            int i2 = AnonymousClass4.$SwitchMap$android$widget$ImageView$ScaleType[this.mScaleType.ordinal()];
            if (i2 != 2) {
                if (i2 != 3) {
                    f5 = (imageViewHeight - fHeight) / 2.0f;
                    f6 = displayRect.top;
                } else {
                    f5 = imageViewHeight - fHeight;
                    f6 = displayRect.top;
                }
                f2 = f5 - f6;
            } else {
                f2 = -displayRect.top;
            }
            this.mVerticalScrollEdge = 2;
        }
        float imageViewWidth = getImageViewWidth(this.mImageView);
        if (fWidth > imageViewWidth || displayRect.left < 0.0f) {
            float f10 = displayRect.left;
            if (f10 >= 0.0f) {
                this.mHorizontalScrollEdge = 0;
                f7 = -f10;
            } else {
                float f11 = displayRect.right;
                if (f11 <= imageViewWidth) {
                    f7 = imageViewWidth - f11;
                    this.mHorizontalScrollEdge = 1;
                } else {
                    this.mHorizontalScrollEdge = -1;
                }
            }
        } else {
            int i3 = AnonymousClass4.$SwitchMap$android$widget$ImageView$ScaleType[this.mScaleType.ordinal()];
            if (i3 != 2) {
                if (i3 != 3) {
                    f3 = (imageViewWidth - fWidth) / 2.0f;
                    f4 = displayRect.left;
                } else {
                    f3 = imageViewWidth - fWidth;
                    f4 = displayRect.left;
                }
                f7 = f3 - f4;
            } else {
                f7 = -displayRect.left;
            }
            this.mHorizontalScrollEdge = 2;
        }
        this.mSuppMatrix.postTranslate(f7, f2);
        return true;
    }

    private Matrix getDrawMatrix() {
        this.mDrawMatrix.set(this.mBaseMatrix);
        this.mDrawMatrix.postConcat(this.mSuppMatrix);
        return this.mDrawMatrix;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getImageViewHeight(ImageView imageView) {
        return (imageView.getHeight() - imageView.getPaddingTop()) - imageView.getPaddingBottom();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getImageViewWidth(ImageView imageView) {
        return (imageView.getWidth() - imageView.getPaddingLeft()) - imageView.getPaddingRight();
    }

    private void resetMatrix() {
        this.mSuppMatrix.reset();
        setRotationBy(this.mBaseRotation);
        setImageViewMatrix(getDrawMatrix());
        checkMatrixBounds();
    }

    private void setImageViewMatrix(Matrix matrix) {
        RectF displayRect;
        this.mImageView.setImageMatrix(matrix);
        if (this.mMatrixChangeListener == null || (displayRect = getDisplayRect(matrix)) == null) {
            return;
        }
        this.mMatrixChangeListener.onMatrixChanged(displayRect);
    }

    private void updateBaseMatrix(Drawable drawable) {
        if (drawable == null) {
            return;
        }
        float imageViewWidth = getImageViewWidth(this.mImageView);
        float imageViewHeight = getImageViewHeight(this.mImageView);
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
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
            if (((int) this.mBaseRotation) % 180 != 0) {
                rectF = new RectF(0.0f, 0.0f, f4, f2);
            }
            int i2 = AnonymousClass4.$SwitchMap$android$widget$ImageView$ScaleType[this.mScaleType.ordinal()];
            if (i2 != 1) {
                if (i2 == 2) {
                    this.mBaseMatrix.setRectToRect(rectF, rectF2, Matrix.ScaleToFit.START);
                } else if (i2 == 3) {
                    this.mBaseMatrix.setRectToRect(rectF, rectF2, Matrix.ScaleToFit.END);
                } else if (i2 == 4) {
                    this.mBaseMatrix.setRectToRect(rectF, rectF2, Matrix.ScaleToFit.FILL);
                }
            } else if ((f4 * 1.0f) / f2 > (imageViewHeight * 1.0f) / imageViewWidth) {
                this.isLongImage = true;
                this.mBaseMatrix.setRectToRect(rectF, new RectF(0.0f, 0.0f, imageViewWidth, f4 * f3), Matrix.ScaleToFit.START);
            } else {
                this.mBaseMatrix.setRectToRect(rectF, rectF2, Matrix.ScaleToFit.CENTER);
            }
        }
        resetMatrix();
    }

    public void getDisplayMatrix(Matrix matrix) {
        matrix.set(getDrawMatrix());
    }

    public RectF getDisplayRect() {
        checkMatrixBounds();
        return getDisplayRect(getDrawMatrix());
    }

    public Matrix getImageMatrix() {
        return this.mDrawMatrix;
    }

    public float getMaximumScale() {
        return this.mMaxScale;
    }

    public float getMediumScale() {
        return this.mMidScale;
    }

    public float getMinimumScale() {
        return this.mMinScale;
    }

    public float getScale() {
        return (float) Math.sqrt(((float) Math.pow(getValue(this.mSuppMatrix, 0), 2.0d)) + ((float) Math.pow(getValue(this.mSuppMatrix, 3), 2.0d)));
    }

    public ImageView.ScaleType getScaleType() {
        return this.mScaleType;
    }

    public void getSuppMatrix(Matrix matrix) {
        matrix.set(this.mSuppMatrix);
    }

    public float getValue(Matrix matrix, int i2) {
        matrix.getValues(this.mMatrixValues);
        return this.mMatrixValues[i2];
    }

    @Deprecated
    public boolean isZoomEnabled() {
        return this.mZoomEnabled;
    }

    public boolean isZoomable() {
        return this.mZoomEnabled;
    }

    @Override // android.view.View.OnLayoutChangeListener
    public void onLayoutChange(View view, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
        if (i2 == i6 && i3 == i7 && i4 == i8 && i5 == i9) {
            return;
        }
        updateBaseMatrix(this.mImageView.getDrawable());
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x0079  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00eb  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x011e  */
    @Override // android.view.View.OnTouchListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouch(android.view.View r11, android.view.MotionEvent r12) {
        /*
            Method dump skipped, instructions count: 299
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lxj.xpopup.photoview.PhotoViewAttacher.onTouch(android.view.View, android.view.MotionEvent):boolean");
    }

    public void setAllowParentInterceptOnEdge(boolean z2) {
        this.mAllowParentInterceptOnEdge = z2;
    }

    public void setBaseRotation(float f2) {
        this.mBaseRotation = f2 % 360.0f;
        update();
        setRotationBy(this.mBaseRotation);
        checkAndDisplayMatrix();
    }

    public boolean setDisplayMatrix(Matrix matrix) {
        if (matrix == null) {
            throw new IllegalArgumentException("Matrix cannot be null");
        }
        if (this.mImageView.getDrawable() == null) {
            return false;
        }
        this.mSuppMatrix.set(matrix);
        checkAndDisplayMatrix();
        return true;
    }

    public void setMaximumScale(float f2) {
        Util.checkZoomLevels(this.mMinScale, this.mMidScale, f2);
        this.mMaxScale = f2;
    }

    public void setMediumScale(float f2) {
        Util.checkZoomLevels(this.mMinScale, f2, this.mMaxScale);
        this.mMidScale = f2;
    }

    public void setMinimumScale(float f2) {
        Util.checkZoomLevels(f2, this.mMidScale, this.mMaxScale);
        this.mMinScale = f2;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener onDoubleTapListener) {
        this.mGestureDetector.setOnDoubleTapListener(onDoubleTapListener);
    }

    public void setOnLongClickListener(View.OnLongClickListener onLongClickListener) {
        this.mLongClickListener = onLongClickListener;
    }

    public void setOnMatrixChangeListener(OnMatrixChangedListener onMatrixChangedListener) {
        this.mMatrixChangeListener = onMatrixChangedListener;
    }

    public void setOnOutsidePhotoTapListener(OnOutsidePhotoTapListener onOutsidePhotoTapListener) {
        this.mOutsidePhotoTapListener = onOutsidePhotoTapListener;
    }

    public void setOnPhotoTapListener(OnPhotoTapListener onPhotoTapListener) {
        this.mPhotoTapListener = onPhotoTapListener;
    }

    public void setOnScaleChangeListener(OnScaleChangedListener onScaleChangedListener) {
        this.mScaleChangeListener = onScaleChangedListener;
    }

    public void setOnSingleFlingListener(OnSingleFlingListener onSingleFlingListener) {
        this.mSingleFlingListener = onSingleFlingListener;
    }

    public void setOnViewDragListener(OnViewDragListener onViewDragListener) {
        this.mOnViewDragListener = onViewDragListener;
    }

    public void setOnViewTapListener(OnViewTapListener onViewTapListener) {
        this.mViewTapListener = onViewTapListener;
    }

    public void setRotationBy(float f2) {
        this.mSuppMatrix.postRotate(f2 % 360.0f);
        checkAndDisplayMatrix();
    }

    public void setRotationTo(float f2) {
        this.mSuppMatrix.setRotate(f2 % 360.0f);
        checkAndDisplayMatrix();
    }

    public void setScale(float f2) {
        setScale(f2, false);
    }

    public void setScaleLevels(float f2, float f3, float f4) {
        Util.checkZoomLevels(f2, f3, f4);
        this.mMinScale = f2;
        this.mMidScale = f3;
        this.mMaxScale = f4;
    }

    public void setScaleType(ImageView.ScaleType scaleType) {
        if (!Util.isSupportedScaleType(scaleType) || scaleType == this.mScaleType) {
            return;
        }
        this.mScaleType = scaleType;
        update();
    }

    public void setZoomInterpolator(Interpolator interpolator) {
        this.mInterpolator = interpolator;
    }

    public void setZoomTransitionDuration(int i2) {
        this.mZoomDuration = i2;
    }

    public void setZoomable(boolean z2) {
        this.mZoomEnabled = z2;
        update();
    }

    public void update() {
        if (this.mZoomEnabled) {
            updateBaseMatrix(this.mImageView.getDrawable());
        } else {
            resetMatrix();
        }
    }

    public void setScale(float f2, boolean z2) {
        setScale(f2, this.mImageView.getRight() / 2, this.mImageView.getBottom() / 2, z2);
    }

    private RectF getDisplayRect(Matrix matrix) {
        if (this.mImageView.getDrawable() == null) {
            return null;
        }
        this.mDisplayRect.set(0.0f, 0.0f, r0.getIntrinsicWidth(), r0.getIntrinsicHeight());
        matrix.mapRect(this.mDisplayRect);
        return this.mDisplayRect;
    }

    public void setScale(float f2, float f3, float f4, boolean z2) {
        if (z2) {
            this.mImageView.post(new AnimatedZoomRunnable(getScale(), f2, f3, f4));
        } else {
            this.mSuppMatrix.setScale(f2, f2, f3, f4);
            checkAndDisplayMatrix();
        }
    }
}
