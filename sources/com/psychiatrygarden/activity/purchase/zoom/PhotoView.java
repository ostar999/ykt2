package com.psychiatrygarden.activity.purchase.zoom;

import android.content.Context;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import com.psychiatrygarden.activity.purchase.zoom.PhotoViewAttacher;

/* loaded from: classes5.dex */
public class PhotoView extends ImageView implements IPhotoView {
    private final PhotoViewAttacher mAttacher;
    private ImageView.ScaleType mPendingScaleType;

    public PhotoView(Context context) {
        super(context);
        super.setScaleType(ImageView.ScaleType.MATRIX);
        this.mAttacher = new PhotoViewAttacher(this);
        ImageView.ScaleType scaleType = this.mPendingScaleType;
        if (scaleType != null) {
            setScaleType(scaleType);
            this.mPendingScaleType = null;
        }
    }

    @Override // com.psychiatrygarden.activity.purchase.zoom.IPhotoView
    public boolean canZoom() {
        return this.mAttacher.canZoom();
    }

    @Override // com.psychiatrygarden.activity.purchase.zoom.IPhotoView
    public RectF getDisplayRect() {
        return this.mAttacher.getDisplayRect();
    }

    @Override // com.psychiatrygarden.activity.purchase.zoom.IPhotoView
    public float getMaxScale() {
        return this.mAttacher.getMaxScale();
    }

    @Override // com.psychiatrygarden.activity.purchase.zoom.IPhotoView
    public float getMidScale() {
        return this.mAttacher.getMidScale();
    }

    @Override // com.psychiatrygarden.activity.purchase.zoom.IPhotoView
    public float getMinScale() {
        return this.mAttacher.getMinScale();
    }

    @Override // com.psychiatrygarden.activity.purchase.zoom.IPhotoView
    public float getScale() {
        return this.mAttacher.getScale();
    }

    @Override // android.widget.ImageView, com.psychiatrygarden.activity.purchase.zoom.IPhotoView
    public ImageView.ScaleType getScaleType() {
        return this.mAttacher.getScaleType();
    }

    @Override // android.widget.ImageView, android.view.View
    public void onDetachedFromWindow() {
        this.mAttacher.cleanup();
        super.onDetachedFromWindow();
    }

    @Override // com.psychiatrygarden.activity.purchase.zoom.IPhotoView
    public void setAllowParentInterceptOnEdge(boolean allow) {
        this.mAttacher.setAllowParentInterceptOnEdge(allow);
    }

    @Override // android.widget.ImageView
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        PhotoViewAttacher photoViewAttacher = this.mAttacher;
        if (photoViewAttacher != null) {
            photoViewAttacher.update();
        }
    }

    @Override // android.widget.ImageView
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        PhotoViewAttacher photoViewAttacher = this.mAttacher;
        if (photoViewAttacher != null) {
            photoViewAttacher.update();
        }
    }

    @Override // android.widget.ImageView
    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        PhotoViewAttacher photoViewAttacher = this.mAttacher;
        if (photoViewAttacher != null) {
            photoViewAttacher.update();
        }
    }

    @Override // com.psychiatrygarden.activity.purchase.zoom.IPhotoView
    public void setMaxScale(float maxScale) {
        this.mAttacher.setMaxScale(maxScale);
    }

    @Override // com.psychiatrygarden.activity.purchase.zoom.IPhotoView
    public void setMidScale(float midScale) {
        this.mAttacher.setMidScale(midScale);
    }

    @Override // com.psychiatrygarden.activity.purchase.zoom.IPhotoView
    public void setMinScale(float minScale) {
        this.mAttacher.setMinScale(minScale);
    }

    @Override // android.view.View, com.psychiatrygarden.activity.purchase.zoom.IPhotoView
    public void setOnLongClickListener(View.OnLongClickListener l2) {
        this.mAttacher.setOnLongClickListener(l2);
    }

    @Override // com.psychiatrygarden.activity.purchase.zoom.IPhotoView
    public void setOnMatrixChangeListener(PhotoViewAttacher.OnMatrixChangedListener listener) {
        this.mAttacher.setOnMatrixChangeListener(listener);
    }

    @Override // com.psychiatrygarden.activity.purchase.zoom.IPhotoView
    public void setOnPhotoTapListener(PhotoViewAttacher.OnPhotoTapListener listener) {
        this.mAttacher.setOnPhotoTapListener(listener);
    }

    @Override // com.psychiatrygarden.activity.purchase.zoom.IPhotoView
    public void setOnViewTapListener(PhotoViewAttacher.OnViewTapListener listener) {
        this.mAttacher.setOnViewTapListener(listener);
    }

    @Override // android.widget.ImageView, com.psychiatrygarden.activity.purchase.zoom.IPhotoView
    public void setScaleType(ImageView.ScaleType scaleType) {
        PhotoViewAttacher photoViewAttacher = this.mAttacher;
        if (photoViewAttacher != null) {
            photoViewAttacher.setScaleType(scaleType);
        } else {
            this.mPendingScaleType = scaleType;
        }
    }

    @Override // com.psychiatrygarden.activity.purchase.zoom.IPhotoView
    public void setZoomable(boolean zoomable) {
        this.mAttacher.setZoomable(zoomable);
    }

    @Override // com.psychiatrygarden.activity.purchase.zoom.IPhotoView
    public void zoomTo(float scale, float focalX, float focalY) {
        this.mAttacher.zoomTo(scale, focalX, focalY);
    }

    public PhotoView(Context context, AttributeSet attr) {
        super(context, attr);
        super.setScaleType(ImageView.ScaleType.MATRIX);
        this.mAttacher = new PhotoViewAttacher(this);
        ImageView.ScaleType scaleType = this.mPendingScaleType;
        if (scaleType != null) {
            setScaleType(scaleType);
            this.mPendingScaleType = null;
        }
    }

    public PhotoView(Context context, AttributeSet attr, int defStyle) {
        super(context, attr, defStyle);
        super.setScaleType(ImageView.ScaleType.MATRIX);
        this.mAttacher = new PhotoViewAttacher(this);
        ImageView.ScaleType scaleType = this.mPendingScaleType;
        if (scaleType != null) {
            setScaleType(scaleType);
            this.mPendingScaleType = null;
        }
    }
}
