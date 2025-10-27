package com.luck.lib.camerax.listener;

import android.content.Context;
import android.view.OrientationEventListener;

/* loaded from: classes4.dex */
public class CameraXOrientationEventListener extends OrientationEventListener {
    private OnOrientationChangedListener changedListener;
    private int mRotation;

    public interface OnOrientationChangedListener {
        void onOrientationChanged(int i2);
    }

    public CameraXOrientationEventListener(Context context, OnOrientationChangedListener onOrientationChangedListener) {
        super(context);
        this.mRotation = 0;
        this.changedListener = onOrientationChangedListener;
    }

    @Override // android.view.OrientationEventListener
    public void onOrientationChanged(int i2) {
        if (i2 == -1) {
            return;
        }
        int i3 = (i2 <= 80 || i2 >= 100) ? (i2 <= 170 || i2 >= 190) ? (i2 <= 260 || i2 >= 280) ? 0 : 1 : 2 : 3;
        if (this.mRotation != i3) {
            this.mRotation = i3;
            OnOrientationChangedListener onOrientationChangedListener = this.changedListener;
            if (onOrientationChangedListener != null) {
                onOrientationChangedListener.onOrientationChanged(i3);
            }
        }
    }

    public void star() {
        enable();
    }

    public void stop() {
        disable();
    }
}
