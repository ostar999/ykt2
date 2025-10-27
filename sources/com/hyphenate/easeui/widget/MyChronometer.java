package com.hyphenate.easeui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Chronometer;

/* loaded from: classes4.dex */
public class MyChronometer extends Chronometer {
    public MyChronometer(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
    }

    @Override // android.widget.Chronometer, android.view.View
    public void onWindowVisibilityChanged(int i2) {
        super.onWindowVisibilityChanged(0);
    }

    public MyChronometer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public MyChronometer(Context context) {
        super(context);
    }
}
