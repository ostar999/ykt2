package com.github.chrisbanes.photoview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.View;
import android.widget.ImageView;

/* loaded from: classes3.dex */
public class PhotoView extends ImageView {
    private PhotoViewAttacher attacher;

    public PhotoView(Context context) {
        this(context, null);
    }

    private void init() {
        this.attacher = new PhotoViewAttacher(this);
        super.setScaleType(ImageView.ScaleType.MATRIX);
    }

    public PhotoViewAttacher getAttacher() {
        return this.attacher;
    }

    public void getDisplayMatrix(Matrix matrix) {
        this.attacher.getDisplayMatrix(matrix);
    }

    public RectF getDisplayRect() {
        return this.attacher.getDisplayRect();
    }

    @Override // android.widget.ImageView
    public Matrix getImageMatrix() {
        return this.attacher.getImageMatrix();
    }

    public float getMaximumScale() {
        return this.attacher.getMaximumScale();
    }

    public float getMediumScale() {
        return this.attacher.getMediumScale();
    }

    public float getMinimumScale() {
        return this.attacher.getMinimumScale();
    }

    public float getScale() {
        return this.attacher.getScale();
    }

    @Override // android.widget.ImageView
    public ImageView.ScaleType getScaleType() {
        return this.attacher.getScaleType();
    }

    public boolean isZoomEnabled() {
        return this.attacher.isZoomEnabled();
    }

    public void setAllowParentInterceptOnEdge(boolean z2) {
        this.attacher.setAllowParentInterceptOnEdge(z2);
    }

    public boolean setDisplayMatrix(Matrix matrix) {
        return this.attacher.setDisplayMatrix(matrix);
    }

    @Override // android.widget.ImageView
    public boolean setFrame(int i2, int i3, int i4, int i5) {
        boolean frame = super.setFrame(i2, i3, i4, i5);
        if (frame) {
            this.attacher.update();
        }
        return frame;
    }

    @Override // android.widget.ImageView
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        PhotoViewAttacher photoViewAttacher = this.attacher;
        if (photoViewAttacher != null) {
            photoViewAttacher.update();
        }
    }

    @Override // android.widget.ImageView
    public void setImageResource(int i2) {
        super.setImageResource(i2);
        PhotoViewAttacher photoViewAttacher = this.attacher;
        if (photoViewAttacher != null) {
            photoViewAttacher.update();
        }
    }

    @Override // android.widget.ImageView
    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        PhotoViewAttacher photoViewAttacher = this.attacher;
        if (photoViewAttacher != null) {
            photoViewAttacher.update();
        }
    }

    public void setMaximumScale(float f2) {
        this.attacher.setMaximumScale(f2);
    }

    public void setMediumScale(float f2) {
        this.attacher.setMediumScale(f2);
    }

    public void setMinimumScale(float f2) {
        this.attacher.setMinimumScale(f2);
    }

    @Override // android.view.View
    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.attacher.setOnClickListener(onClickListener);
    }

    public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener onDoubleTapListener) {
        this.attacher.setOnDoubleTapListener(onDoubleTapListener);
    }

    @Override // android.view.View
    public void setOnLongClickListener(View.OnLongClickListener onLongClickListener) {
        this.attacher.setOnLongClickListener(onLongClickListener);
    }

    public void setOnMatrixChangeListener(OnMatrixChangedListener onMatrixChangedListener) {
        this.attacher.setOnMatrixChangeListener(onMatrixChangedListener);
    }

    public void setOnOutsidePhotoTapListener(OnOutsidePhotoTapListener onOutsidePhotoTapListener) {
        this.attacher.setOnOutsidePhotoTapListener(onOutsidePhotoTapListener);
    }

    public void setOnPhotoTapListener(OnPhotoTapListener onPhotoTapListener) {
        this.attacher.setOnPhotoTapListener(onPhotoTapListener);
    }

    public void setOnScaleChangeListener(OnScaleChangedListener onScaleChangedListener) {
        this.attacher.setOnScaleChangeListener(onScaleChangedListener);
    }

    public void setOnSingleFlingListener(OnSingleFlingListener onSingleFlingListener) {
        this.attacher.setOnSingleFlingListener(onSingleFlingListener);
    }

    public void setRotationBy(float f2) {
        this.attacher.setRotationBy(f2);
    }

    public void setRotationTo(float f2) {
        this.attacher.setRotationTo(f2);
    }

    public void setScale(float f2) {
        this.attacher.setScale(f2);
    }

    public void setScaleLevels(float f2, float f3, float f4) {
        this.attacher.setScaleLevels(f2, f3, f4);
    }

    @Override // android.widget.ImageView
    public void setScaleType(ImageView.ScaleType scaleType) {
        PhotoViewAttacher photoViewAttacher = this.attacher;
        if (photoViewAttacher != null) {
            photoViewAttacher.setScaleType(scaleType);
        }
    }

    public void setZoomTransitionDuration(int i2) {
        this.attacher.setZoomTransitionDuration(i2);
    }

    public void setZoomable(boolean z2) {
        this.attacher.setZoomable(z2);
    }

    public PhotoView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public void setScale(float f2, boolean z2) {
        this.attacher.setScale(f2, z2);
    }

    public PhotoView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        init();
    }

    public void setScale(float f2, float f3, float f4, boolean z2) {
        this.attacher.setScale(f2, f3, f4, z2);
    }

    @TargetApi(21)
    public PhotoView(Context context, AttributeSet attributeSet, int i2, int i3) {
        super(context, attributeSet, i2, i3);
        init();
    }
}
