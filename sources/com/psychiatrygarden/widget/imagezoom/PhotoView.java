package com.psychiatrygarden.widget.imagezoom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.widget.AppCompatImageView;
import com.psychiatrygarden.widget.imagezoom.PhotoViewAttacher;

/* loaded from: classes6.dex */
public class PhotoView extends AppCompatImageView implements IPhotoView {
    private PhotoViewAttacher mAttacher;
    private ImageView.ScaleType mPendingScaleType;

    public PhotoView(Context context) {
        this(context, null);
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    public boolean canZoom() {
        return this.mAttacher.canZoom();
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    public Matrix getDisplayMatrix() {
        return this.mAttacher.getDisplayMatrix();
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    public RectF getDisplayRect() {
        return this.mAttacher.getDisplayRect();
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    public IPhotoView getIPhotoViewImplementation() {
        return this.mAttacher;
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    @Deprecated
    public float getMaxScale() {
        return getMaximumScale();
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    public float getMaximumScale() {
        return this.mAttacher.getMaximumScale();
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    public float getMediumScale() {
        return this.mAttacher.getMediumScale();
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
        return this.mAttacher.getMinimumScale();
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    public PhotoViewAttacher.OnPhotoTapListener getOnPhotoTapListener() {
        return this.mAttacher.getOnPhotoTapListener();
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    public PhotoViewAttacher.OnViewTapListener getOnViewTapListener() {
        return this.mAttacher.getOnViewTapListener();
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    public float getScale() {
        return this.mAttacher.getScale();
    }

    @Override // android.widget.ImageView, com.psychiatrygarden.widget.imagezoom.IPhotoView
    public ImageView.ScaleType getScaleType() {
        return this.mAttacher.getScaleType();
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    public Bitmap getVisibleRectangleBitmap() {
        return this.mAttacher.getVisibleRectangleBitmap();
    }

    public void init() {
        PhotoViewAttacher photoViewAttacher = this.mAttacher;
        if (photoViewAttacher == null || photoViewAttacher.getImageView() == null) {
            this.mAttacher = new PhotoViewAttacher(this);
        }
        ImageView.ScaleType scaleType = this.mPendingScaleType;
        if (scaleType != null) {
            setScaleType(scaleType);
            this.mPendingScaleType = null;
        }
    }

    @Override // android.widget.ImageView, android.view.View
    public void onAttachedToWindow() {
        init();
        super.onAttachedToWindow();
    }

    @Override // android.widget.ImageView, android.view.View
    public void onDetachedFromWindow() {
        this.mAttacher.cleanup();
        super.onDetachedFromWindow();
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    public void setAllowParentInterceptOnEdge(boolean allow) {
        this.mAttacher.setAllowParentInterceptOnEdge(allow);
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    public boolean setDisplayMatrix(Matrix finalRectangle) {
        return this.mAttacher.setDisplayMatrix(finalRectangle);
    }

    @Override // androidx.appcompat.widget.AppCompatImageView, android.widget.ImageView
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        PhotoViewAttacher photoViewAttacher = this.mAttacher;
        if (photoViewAttacher != null) {
            photoViewAttacher.update();
        }
    }

    @Override // androidx.appcompat.widget.AppCompatImageView, android.widget.ImageView
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        PhotoViewAttacher photoViewAttacher = this.mAttacher;
        if (photoViewAttacher != null) {
            photoViewAttacher.update();
        }
    }

    @Override // androidx.appcompat.widget.AppCompatImageView, android.widget.ImageView
    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        PhotoViewAttacher photoViewAttacher = this.mAttacher;
        if (photoViewAttacher != null) {
            photoViewAttacher.update();
        }
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    @Deprecated
    public void setMaxScale(float maxScale) {
        setMaximumScale(maxScale);
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    public void setMaximumScale(float maximumScale) {
        this.mAttacher.setMaximumScale(maximumScale);
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    public void setMediumScale(float mediumScale) {
        this.mAttacher.setMediumScale(mediumScale);
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
        this.mAttacher.setMinimumScale(minimumScale);
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener newOnDoubleTapListener) {
        this.mAttacher.setOnDoubleTapListener(newOnDoubleTapListener);
    }

    @Override // android.view.View, com.psychiatrygarden.widget.imagezoom.IPhotoView
    public void setOnLongClickListener(View.OnLongClickListener l2) {
        this.mAttacher.setOnLongClickListener(l2);
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    public void setOnMatrixChangeListener(PhotoViewAttacher.OnMatrixChangedListener listener) {
        this.mAttacher.setOnMatrixChangeListener(listener);
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    public void setOnPhotoTapListener(PhotoViewAttacher.OnPhotoTapListener listener) {
        this.mAttacher.setOnPhotoTapListener(listener);
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    public void setOnScaleChangeListener(PhotoViewAttacher.OnScaleChangeListener onScaleChangeListener) {
        this.mAttacher.setOnScaleChangeListener(onScaleChangeListener);
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    public void setOnViewTapListener(PhotoViewAttacher.OnViewTapListener listener) {
        this.mAttacher.setOnViewTapListener(listener);
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    public void setPhotoViewRotation(float rotationDegree) {
        this.mAttacher.setRotationTo(rotationDegree);
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    public void setRotationBy(float rotationDegree) {
        this.mAttacher.setRotationBy(rotationDegree);
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    public void setRotationTo(float rotationDegree) {
        this.mAttacher.setRotationTo(rotationDegree);
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    public void setScale(float scale) {
        this.mAttacher.setScale(scale);
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    public void setScaleLevels(float minimumScale, float mediumScale, float maximumScale) {
        this.mAttacher.setScaleLevels(minimumScale, mediumScale, maximumScale);
    }

    @Override // android.widget.ImageView, com.psychiatrygarden.widget.imagezoom.IPhotoView
    public void setScaleType(ImageView.ScaleType scaleType) {
        PhotoViewAttacher photoViewAttacher = this.mAttacher;
        if (photoViewAttacher != null) {
            photoViewAttacher.setScaleType(scaleType);
        } else {
            this.mPendingScaleType = scaleType;
        }
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    public void setZoomTransitionDuration(int milliseconds) {
        this.mAttacher.setZoomTransitionDuration(milliseconds);
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    public void setZoomable(boolean zoomable) {
        this.mAttacher.setZoomable(zoomable);
    }

    public PhotoView(Context context, AttributeSet attr) {
        this(context, attr, 0);
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    public void setScale(float scale, boolean animate) {
        this.mAttacher.setScale(scale, animate);
    }

    public PhotoView(Context context, AttributeSet attr, int defStyle) {
        super(context, attr, defStyle);
        super.setScaleType(ImageView.ScaleType.MATRIX);
        init();
    }

    @Override // com.psychiatrygarden.widget.imagezoom.IPhotoView
    public void setScale(float scale, float focalX, float focalY, boolean animate) {
        this.mAttacher.setScale(scale, focalX, focalY, animate);
    }
}
