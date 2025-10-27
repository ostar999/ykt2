package com.psychiatrygarden.utils;

import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/* loaded from: classes6.dex */
public class URLDrawable extends BitmapDrawable {
    protected Drawable drawable;

    @Override // android.graphics.drawable.BitmapDrawable, android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        Drawable drawable = this.drawable;
        if (drawable != null) {
            drawable.draw(canvas);
        }
    }
}
