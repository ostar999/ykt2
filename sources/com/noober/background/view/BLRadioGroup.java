package com.noober.background.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioGroup;
import com.noober.background.BackgroundFactory;

/* loaded from: classes4.dex */
public class BLRadioGroup extends RadioGroup {
    public BLRadioGroup(Context context) {
        super(context);
    }

    private void init(Context context, AttributeSet attributeSet) {
        BackgroundFactory.setViewBackground(context, attributeSet, this);
    }

    public BLRadioGroup(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }
}
