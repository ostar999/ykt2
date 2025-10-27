package com.hyphenate.easeui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;
import androidx.annotation.Nullable;

/* loaded from: classes4.dex */
public class EImageView extends ImageView {
    public EImageView(Context context) {
        super(context);
    }

    @Override // android.widget.ImageView, android.view.View
    public void onDraw(Canvas canvas) {
        try {
            super.onDraw(canvas);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public EImageView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public EImageView(Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
    }
}
