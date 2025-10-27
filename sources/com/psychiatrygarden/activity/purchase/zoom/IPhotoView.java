package com.psychiatrygarden.activity.purchase.zoom;

import android.graphics.RectF;
import android.view.View;
import android.widget.ImageView;
import com.psychiatrygarden.activity.purchase.zoom.PhotoViewAttacher;

/* loaded from: classes5.dex */
public interface IPhotoView {
    boolean canZoom();

    RectF getDisplayRect();

    float getMaxScale();

    float getMidScale();

    float getMinScale();

    float getScale();

    ImageView.ScaleType getScaleType();

    void setAllowParentInterceptOnEdge(boolean allow);

    void setMaxScale(float maxScale);

    void setMidScale(float midScale);

    void setMinScale(float minScale);

    void setOnLongClickListener(View.OnLongClickListener listener);

    void setOnMatrixChangeListener(PhotoViewAttacher.OnMatrixChangedListener listener);

    void setOnPhotoTapListener(PhotoViewAttacher.OnPhotoTapListener listener);

    void setOnViewTapListener(PhotoViewAttacher.OnViewTapListener listener);

    void setScaleType(ImageView.ScaleType scaleType);

    void setZoomable(boolean zoomable);

    void zoomTo(float scale, float focalX, float focalY);
}
