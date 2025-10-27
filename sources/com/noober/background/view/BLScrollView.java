package com.noober.background.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;
import com.noober.background.BackgroundFactory;

/* loaded from: classes4.dex */
public class BLScrollView extends ScrollView {
    public BLScrollView(Context context) {
        super(context);
    }

    private void init(Context context, AttributeSet attributeSet) {
        BackgroundFactory.setViewBackground(context, attributeSet, this);
    }

    public BLScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    public BLScrollView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        init(context, attributeSet);
    }
}
