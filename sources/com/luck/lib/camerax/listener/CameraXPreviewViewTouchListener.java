package com.luck.lib.camerax.listener;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

/* loaded from: classes4.dex */
public class CameraXPreviewViewTouchListener implements View.OnTouchListener {
    private CustomTouchListener mCustomTouchListener;
    private final GestureDetector mGestureDetector;
    private final ScaleGestureDetector mScaleGestureDetector;
    ScaleGestureDetector.OnScaleGestureListener onScaleGestureListener = new ScaleGestureDetector.SimpleOnScaleGestureListener() { // from class: com.luck.lib.camerax.listener.CameraXPreviewViewTouchListener.1
        @Override // android.view.ScaleGestureDetector.SimpleOnScaleGestureListener, android.view.ScaleGestureDetector.OnScaleGestureListener
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            float scaleFactor = scaleGestureDetector.getScaleFactor();
            if (CameraXPreviewViewTouchListener.this.mCustomTouchListener == null) {
                return true;
            }
            CameraXPreviewViewTouchListener.this.mCustomTouchListener.zoom(scaleFactor);
            return true;
        }
    };
    GestureDetector.SimpleOnGestureListener onGestureListener = new GestureDetector.SimpleOnGestureListener() { // from class: com.luck.lib.camerax.listener.CameraXPreviewViewTouchListener.2
        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
        public boolean onDoubleTap(MotionEvent motionEvent) {
            if (CameraXPreviewViewTouchListener.this.mCustomTouchListener == null) {
                return true;
            }
            CameraXPreviewViewTouchListener.this.mCustomTouchListener.doubleClick(motionEvent.getX(), motionEvent.getY());
            return true;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f2, float f3) {
            return true;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public void onLongPress(MotionEvent motionEvent) {
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
        public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
            if (CameraXPreviewViewTouchListener.this.mCustomTouchListener == null) {
                return true;
            }
            CameraXPreviewViewTouchListener.this.mCustomTouchListener.click(motionEvent.getX(), motionEvent.getY());
            return true;
        }
    };

    public interface CustomTouchListener {
        void click(float f2, float f3);

        void doubleClick(float f2, float f3);

        void zoom(float f2);
    }

    public CameraXPreviewViewTouchListener(Context context) {
        this.mGestureDetector = new GestureDetector(context, this.onGestureListener);
        this.mScaleGestureDetector = new ScaleGestureDetector(context, this.onScaleGestureListener);
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent motionEvent) {
        this.mScaleGestureDetector.onTouchEvent(motionEvent);
        if (this.mScaleGestureDetector.isInProgress()) {
            return true;
        }
        this.mGestureDetector.onTouchEvent(motionEvent);
        return true;
    }

    public void setCustomTouchListener(CustomTouchListener customTouchListener) {
        this.mCustomTouchListener = customTouchListener;
    }
}
