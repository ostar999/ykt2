package com.psychiatrygarden.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import com.psychiatrygarden.widget.glideUtil.GlideImageView;

/* loaded from: classes6.dex */
public class GlideForegroupImageView extends GlideImageView {
    public GlideForegroupImageView(Context context) {
        super(context);
    }

    @Override // android.widget.ImageView, android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(1996488704);
    }

    public GlideForegroupImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GlideForegroupImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
