package com.yddmi.doctor.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.VideoView;

/* loaded from: classes6.dex */
public class FullVideoView extends VideoView {
    public FullVideoView(Context context) {
        super(context);
    }

    @Override // android.widget.VideoView, android.view.SurfaceView, android.view.View
    public void onMeasure(int i2, int i3) {
        super.onMeasure(i2, i3);
        setMeasuredDimension(View.getDefaultSize(0, i2), View.getDefaultSize(0, i3));
    }

    public FullVideoView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public FullVideoView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
    }

    public FullVideoView(Context context, AttributeSet attributeSet, int i2, int i3) {
        super(context, attributeSet, i2, i3);
    }
}
