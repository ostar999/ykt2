package com.psychiatrygarden.widget.imagezoom;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

/* loaded from: classes6.dex */
public class FroyoGestureDetector extends EclairGestureDetector {
    protected final ScaleGestureDetector mDetector;

    public FroyoGestureDetector(Context context) {
        super(context);
        this.mDetector = new ScaleGestureDetector(context, new ScaleGestureDetector.OnScaleGestureListener() { // from class: com.psychiatrygarden.widget.imagezoom.FroyoGestureDetector.1
            @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
            public boolean onScale(ScaleGestureDetector detector) {
                float scaleFactor = detector.getScaleFactor();
                if (Float.isNaN(scaleFactor) || Float.isInfinite(scaleFactor)) {
                    return false;
                }
                FroyoGestureDetector.this.mListener.onScale(scaleFactor, detector.getFocusX(), detector.getFocusY());
                return true;
            }

            @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
            public boolean onScaleBegin(ScaleGestureDetector detector) {
                return true;
            }

            @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
            public void onScaleEnd(ScaleGestureDetector detector) {
            }
        });
    }

    @Override // com.psychiatrygarden.widget.imagezoom.CupcakeGestureDetector, com.psychiatrygarden.widget.imagezoom.GestureDetector
    public boolean isScaling() {
        return this.mDetector.isInProgress();
    }

    @Override // com.psychiatrygarden.widget.imagezoom.EclairGestureDetector, com.psychiatrygarden.widget.imagezoom.CupcakeGestureDetector, com.psychiatrygarden.widget.imagezoom.GestureDetector
    public boolean onTouchEvent(MotionEvent ev) {
        this.mDetector.onTouchEvent(ev);
        return super.onTouchEvent(ev);
    }
}
