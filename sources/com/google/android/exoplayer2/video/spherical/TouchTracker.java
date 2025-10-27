package com.google.android.exoplayer2.video.spherical;

import android.content.Context;
import android.graphics.PointF;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.BinderThread;
import com.google.android.exoplayer2.video.spherical.OrientationListener;

/* loaded from: classes3.dex */
final class TouchTracker extends GestureDetector.SimpleOnGestureListener implements View.OnTouchListener, OrientationListener.Listener {
    static final float MAX_PITCH_DEGREES = 45.0f;
    private final GestureDetector gestureDetector;
    private final Listener listener;
    private final float pxPerDegrees;
    private final PointF previousTouchPointPx = new PointF();
    private final PointF accumulatedTouchOffsetDegrees = new PointF();
    private volatile float roll = 3.1415927f;

    public interface Listener {
        void onScrollChange(PointF pointF);

        boolean onSingleTapUp(MotionEvent motionEvent);
    }

    public TouchTracker(Context context, Listener listener, float f2) {
        this.listener = listener;
        this.pxPerDegrees = f2;
        this.gestureDetector = new GestureDetector(context, this);
    }

    @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
    public boolean onDown(MotionEvent motionEvent) {
        this.previousTouchPointPx.set(motionEvent.getX(), motionEvent.getY());
        return true;
    }

    @Override // com.google.android.exoplayer2.video.spherical.OrientationListener.Listener
    @BinderThread
    public void onOrientationChange(float[] fArr, float f2) {
        this.roll = -f2;
    }

    @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f2, float f3) {
        float x2 = (motionEvent2.getX() - this.previousTouchPointPx.x) / this.pxPerDegrees;
        float y2 = motionEvent2.getY();
        PointF pointF = this.previousTouchPointPx;
        float f4 = (y2 - pointF.y) / this.pxPerDegrees;
        pointF.set(motionEvent2.getX(), motionEvent2.getY());
        double d3 = this.roll;
        float fCos = (float) Math.cos(d3);
        float fSin = (float) Math.sin(d3);
        PointF pointF2 = this.accumulatedTouchOffsetDegrees;
        pointF2.x -= (fCos * x2) - (fSin * f4);
        float f5 = pointF2.y + (fSin * x2) + (fCos * f4);
        pointF2.y = f5;
        pointF2.y = Math.max(-45.0f, Math.min(MAX_PITCH_DEGREES, f5));
        this.listener.onScrollChange(this.accumulatedTouchOffsetDegrees);
        return true;
    }

    @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return this.listener.onSingleTapUp(motionEvent);
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return this.gestureDetector.onTouchEvent(motionEvent);
    }
}
