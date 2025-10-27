package com.ruffian.library.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.ruffian.library.widget.helper.RBaseHelper;
import com.ruffian.library.widget.iface.RHelper;

/* loaded from: classes6.dex */
public class RFrameLayout extends FrameLayout implements RHelper<RBaseHelper> {
    private RBaseHelper mHelper;

    public RFrameLayout(Context context) {
        this(context, null);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        this.mHelper.dispatchDraw(canvas);
    }

    @Override // com.ruffian.library.widget.iface.RHelper
    public RBaseHelper getHelper() {
        return this.mHelper;
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        super.onLayout(z2, i2, i3, i4, i5);
        this.mHelper.onLayout(z2, i2, i3, i4, i5);
    }

    public RFrameLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RFrameLayout(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mHelper = new RBaseHelper(context, this, attributeSet);
    }
}
