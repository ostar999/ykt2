package com.hyphenate.easeui.widget.photoview;

import android.content.Context;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import com.hyphenate.easeui.widget.photoview.PhotoViewAttacher;

/* loaded from: classes4.dex */
public class EasePhotoView extends ImageView implements IPhotoView {
    private final PhotoViewAttacher mAttacher;
    private ImageView.ScaleType mPendingScaleType;

    public EasePhotoView(Context context) {
        this(context, null);
    }

    @Override // com.hyphenate.easeui.widget.photoview.IPhotoView
    public boolean canZoom() {
        return this.mAttacher.canZoom();
    }

    @Override // com.hyphenate.easeui.widget.photoview.IPhotoView
    public RectF getDisplayRect() {
        return this.mAttacher.getDisplayRect();
    }

    @Override // com.hyphenate.easeui.widget.photoview.IPhotoView
    public float getMaxScale() {
        return this.mAttacher.getMaxScale();
    }

    @Override // com.hyphenate.easeui.widget.photoview.IPhotoView
    public float getMidScale() {
        return this.mAttacher.getMidScale();
    }

    @Override // com.hyphenate.easeui.widget.photoview.IPhotoView
    public float getMinScale() {
        return this.mAttacher.getMinScale();
    }

    @Override // com.hyphenate.easeui.widget.photoview.IPhotoView
    public float getScale() {
        return this.mAttacher.getScale();
    }

    @Override // android.widget.ImageView, com.hyphenate.easeui.widget.photoview.IPhotoView
    public ImageView.ScaleType getScaleType() {
        return this.mAttacher.getScaleType();
    }

    @Override // android.widget.ImageView, android.view.View
    public void onDetachedFromWindow() {
        this.mAttacher.cleanup();
        super.onDetachedFromWindow();
    }

    @Override // com.hyphenate.easeui.widget.photoview.IPhotoView
    public void setAllowParentInterceptOnEdge(boolean z2) {
        this.mAttacher.setAllowParentInterceptOnEdge(z2);
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
    public void setImageResource(int i2) {
        super.setImageResource(i2);
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

    @Override // com.hyphenate.easeui.widget.photoview.IPhotoView
    public void setMaxScale(float f2) {
        this.mAttacher.setMaxScale(f2);
    }

    @Override // com.hyphenate.easeui.widget.photoview.IPhotoView
    public void setMidScale(float f2) {
        this.mAttacher.setMidScale(f2);
    }

    @Override // com.hyphenate.easeui.widget.photoview.IPhotoView
    public void setMinScale(float f2) {
        this.mAttacher.setMinScale(f2);
    }

    @Override // android.view.View, com.hyphenate.easeui.widget.photoview.IPhotoView
    public void setOnLongClickListener(View.OnLongClickListener onLongClickListener) {
        this.mAttacher.setOnLongClickListener(onLongClickListener);
    }

    @Override // com.hyphenate.easeui.widget.photoview.IPhotoView
    public void setOnMatrixChangeListener(PhotoViewAttacher.OnMatrixChangedListener onMatrixChangedListener) {
        this.mAttacher.setOnMatrixChangeListener(onMatrixChangedListener);
    }

    @Override // com.hyphenate.easeui.widget.photoview.IPhotoView
    public void setOnPhotoTapListener(PhotoViewAttacher.OnPhotoTapListener onPhotoTapListener) {
        this.mAttacher.setOnPhotoTapListener(onPhotoTapListener);
    }

    @Override // com.hyphenate.easeui.widget.photoview.IPhotoView
    public void setOnViewTapListener(PhotoViewAttacher.OnViewTapListener onViewTapListener) {
        this.mAttacher.setOnViewTapListener(onViewTapListener);
    }

    @Override // android.widget.ImageView, com.hyphenate.easeui.widget.photoview.IPhotoView
    public void setScaleType(ImageView.ScaleType scaleType) {
        PhotoViewAttacher photoViewAttacher = this.mAttacher;
        if (photoViewAttacher != null) {
            photoViewAttacher.setScaleType(scaleType);
        } else {
            this.mPendingScaleType = scaleType;
        }
    }

    @Override // com.hyphenate.easeui.widget.photoview.IPhotoView
    public void setZoomable(boolean z2) {
        this.mAttacher.setZoomable(z2);
    }

    @Override // com.hyphenate.easeui.widget.photoview.IPhotoView
    public void zoomTo(float f2, float f3, float f4) {
        this.mAttacher.zoomTo(f2, f3, f4);
    }

    public EasePhotoView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public EasePhotoView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        super.setScaleType(ImageView.ScaleType.MATRIX);
        this.mAttacher = new PhotoViewAttacher(this);
        ImageView.ScaleType scaleType = this.mPendingScaleType;
        if (scaleType != null) {
            setScaleType(scaleType);
            this.mPendingScaleType = null;
        }
    }
}
