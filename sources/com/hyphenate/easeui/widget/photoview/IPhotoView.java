package com.hyphenate.easeui.widget.photoview;

import android.graphics.RectF;
import android.view.View;
import android.widget.ImageView;
import com.hyphenate.easeui.widget.photoview.PhotoViewAttacher;

/* loaded from: classes4.dex */
public interface IPhotoView {
    boolean canZoom();

    RectF getDisplayRect();

    float getMaxScale();

    float getMidScale();

    float getMinScale();

    float getScale();

    ImageView.ScaleType getScaleType();

    void setAllowParentInterceptOnEdge(boolean z2);

    void setMaxScale(float f2);

    void setMidScale(float f2);

    void setMinScale(float f2);

    void setOnLongClickListener(View.OnLongClickListener onLongClickListener);

    void setOnMatrixChangeListener(PhotoViewAttacher.OnMatrixChangedListener onMatrixChangedListener);

    void setOnPhotoTapListener(PhotoViewAttacher.OnPhotoTapListener onPhotoTapListener);

    void setOnViewTapListener(PhotoViewAttacher.OnViewTapListener onViewTapListener);

    void setScaleType(ImageView.ScaleType scaleType);

    void setZoomable(boolean z2);

    void zoomTo(float f2, float f3, float f4);
}
