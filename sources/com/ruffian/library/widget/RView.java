package com.ruffian.library.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.ruffian.library.widget.helper.RBaseHelper;
import com.ruffian.library.widget.iface.RHelper;

/* loaded from: classes6.dex */
public class RView extends View implements RHelper<RBaseHelper> {
    private RBaseHelper mHelper;

    public RView(Context context) {
        this(context, null);
    }

    @Override // com.ruffian.library.widget.iface.RHelper
    public RBaseHelper getHelper() {
        return this.mHelper;
    }

    public RView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mHelper = new RBaseHelper(context, this, attributeSet);
    }
}
