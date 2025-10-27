package com.psychiatrygarden.widget.imagezoom;

import android.graphics.RectF;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;

/* loaded from: classes6.dex */
public class DefaultOnDoubleTapListener implements GestureDetector.OnDoubleTapListener {
    private PhotoViewAttacher photoViewAttacher;

    public DefaultOnDoubleTapListener(PhotoViewAttacher photoViewAttacher) {
        setPhotoViewAttacher(photoViewAttacher);
    }

    @Override // android.view.GestureDetector.OnDoubleTapListener
    public boolean onDoubleTap(MotionEvent ev) {
        PhotoViewAttacher photoViewAttacher = this.photoViewAttacher;
        if (photoViewAttacher == null) {
            return false;
        }
        try {
            float scale = photoViewAttacher.getScale();
            float x2 = ev.getX();
            float y2 = ev.getY();
            if (scale < this.photoViewAttacher.getMediumScale()) {
                PhotoViewAttacher photoViewAttacher2 = this.photoViewAttacher;
                photoViewAttacher2.setScale(photoViewAttacher2.getMediumScale(), x2, y2, true);
            } else if (scale < this.photoViewAttacher.getMediumScale() || scale >= this.photoViewAttacher.getMaximumScale()) {
                PhotoViewAttacher photoViewAttacher3 = this.photoViewAttacher;
                photoViewAttacher3.setScale(photoViewAttacher3.getMinimumScale(), x2, y2, true);
            } else {
                PhotoViewAttacher photoViewAttacher4 = this.photoViewAttacher;
                photoViewAttacher4.setScale(photoViewAttacher4.getMaximumScale(), x2, y2, true);
            }
        } catch (ArrayIndexOutOfBoundsException unused) {
        }
        return true;
    }

    @Override // android.view.GestureDetector.OnDoubleTapListener
    public boolean onDoubleTapEvent(MotionEvent e2) {
        return false;
    }

    @Override // android.view.GestureDetector.OnDoubleTapListener
    public boolean onSingleTapConfirmed(MotionEvent e2) {
        RectF displayRect;
        PhotoViewAttacher photoViewAttacher = this.photoViewAttacher;
        if (photoViewAttacher == null) {
            return false;
        }
        ImageView imageView = photoViewAttacher.getImageView();
        if (this.photoViewAttacher.getOnPhotoTapListener() != null && (displayRect = this.photoViewAttacher.getDisplayRect()) != null) {
            float x2 = e2.getX();
            float y2 = e2.getY();
            if (displayRect.contains(x2, y2)) {
                this.photoViewAttacher.getOnPhotoTapListener().onPhotoTap(imageView, (x2 - displayRect.left) / displayRect.width(), (y2 - displayRect.top) / displayRect.height());
                return true;
            }
        }
        if (this.photoViewAttacher.getOnViewTapListener() != null) {
            this.photoViewAttacher.getOnViewTapListener().onViewTap(imageView, e2.getX(), e2.getY());
        }
        return false;
    }

    public void setPhotoViewAttacher(PhotoViewAttacher newPhotoViewAttacher) {
        this.photoViewAttacher = newPhotoViewAttacher;
    }
}
