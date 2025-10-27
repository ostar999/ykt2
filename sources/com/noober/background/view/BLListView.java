package com.noober.background.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;
import com.noober.background.BackgroundFactory;

/* loaded from: classes4.dex */
public class BLListView extends ListView {
    public BLListView(Context context) {
        super(context);
    }

    private void init(Context context, AttributeSet attributeSet) {
        BackgroundFactory.setViewBackground(context, attributeSet, this);
    }

    public BLListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    public BLListView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        init(context, attributeSet);
    }
}
