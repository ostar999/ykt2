package com.noober.background.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.noober.background.BackgroundFactory;

/* loaded from: classes4.dex */
public class BLFrameLayout extends FrameLayout {
    public BLFrameLayout(Context context) {
        super(context);
    }

    private void init(Context context, AttributeSet attributeSet) {
        BackgroundFactory.setViewBackground(context, attributeSet, this);
    }

    public BLFrameLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    public BLFrameLayout(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        init(context, attributeSet);
    }
}
