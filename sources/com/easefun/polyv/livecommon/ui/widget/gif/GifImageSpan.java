package com.easefun.polyv.livecommon.ui.widget.gif;

import android.graphics.drawable.Drawable;

/* loaded from: classes3.dex */
public class GifImageSpan extends RelativeImageSpan {
    private Drawable mDrawable;

    public GifImageSpan(Drawable d3) {
        super(d3);
        this.mDrawable = d3;
    }

    @Override // android.text.style.ImageSpan, android.text.style.DynamicDrawableSpan
    public Drawable getDrawable() {
        return this.mDrawable;
    }

    public GifImageSpan(Drawable d3, int verticalAlignment) {
        super(d3, verticalAlignment);
        this.mDrawable = d3;
    }

    public GifImageSpan(Drawable d3, int verticalAlignment, float spacingScale) {
        super(d3, verticalAlignment, spacingScale);
        this.mDrawable = d3;
    }
}
