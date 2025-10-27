package com.psychiatrygarden.widget.imagezoom;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.view.GestureDetector;
import android.view.View;
import android.widget.ImageView;
import com.psychiatrygarden.widget.imagezoom.PhotoViewAttacher;

/* loaded from: classes6.dex */
public interface IPhotoView {
    public static final float DEFAULT_MAX_SCALE = 3.0f;
    public static final float DEFAULT_MID_SCALE = 1.75f;
    public static final float DEFAULT_MIN_SCALE = 1.0f;
    public static final int DEFAULT_ZOOM_DURATION = 200;

    boolean canZoom();

    Matrix getDisplayMatrix();

    RectF getDisplayRect();

    IPhotoView getIPhotoViewImplementation();

    @Deprecated
    float getMaxScale();

    float getMaximumScale();

    float getMediumScale();

    @Deprecated
    float getMidScale();

    @Deprecated
    float getMinScale();

    float getMinimumScale();

    PhotoViewAttacher.OnPhotoTapListener getOnPhotoTapListener();

    PhotoViewAttacher.OnViewTapListener getOnViewTapListener();

    float getScale();

    ImageView.ScaleType getScaleType();

    Bitmap getVisibleRectangleBitmap();

    void setAllowParentInterceptOnEdge(boolean allow);

    boolean setDisplayMatrix(Matrix finalMatrix);

    @Deprecated
    void setMaxScale(float maxScale);

    void setMaximumScale(float maximumScale);

    void setMediumScale(float mediumScale);

    @Deprecated
    void setMidScale(float midScale);

    @Deprecated
    void setMinScale(float minScale);

    void setMinimumScale(float minimumScale);

    void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener newOnDoubleTapListener);

    void setOnLongClickListener(View.OnLongClickListener listener);

    void setOnMatrixChangeListener(PhotoViewAttacher.OnMatrixChangedListener listener);

    void setOnPhotoTapListener(PhotoViewAttacher.OnPhotoTapListener listener);

    void setOnScaleChangeListener(PhotoViewAttacher.OnScaleChangeListener onScaleChangeListener);

    void setOnViewTapListener(PhotoViewAttacher.OnViewTapListener listener);

    void setPhotoViewRotation(float rotationDegree);

    void setRotationBy(float rotationDegree);

    void setRotationTo(float rotationDegree);

    void setScale(float scale);

    void setScale(float scale, float focalX, float focalY, boolean animate);

    void setScale(float scale, boolean animate);

    void setScaleLevels(float minimumScale, float mediumScale, float maximumScale);

    void setScaleType(ImageView.ScaleType scaleType);

    void setZoomTransitionDuration(int milliseconds);

    void setZoomable(boolean zoomable);
}
