package com.psychiatrygarden.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatImageView;
import com.psychiatrygarden.utils.ScreenUtil;

/* loaded from: classes6.dex */
public class ScaleBgImageView extends AppCompatImageView {
    private final Matrix matrix;

    public ScaleBgImageView(Context context) {
        super(context);
        this.matrix = new Matrix();
    }

    @Override // android.widget.ImageView, android.view.View
    public void onDraw(Canvas canvas) {
        canvas.concat(this.matrix);
        getDrawable().draw(canvas);
    }

    @Override // android.view.View
    public void onLayout(boolean changed, int left, int top2, int right, int bottom) {
        super.onLayout(changed, left, top2, right, bottom);
        float screenWidth = ScreenUtil.getScreenWidth((Activity) getContext()) / getDrawable().getIntrinsicWidth();
        this.matrix.setScale(screenWidth, screenWidth);
    }

    public ScaleBgImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.matrix = new Matrix();
    }

    public ScaleBgImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.matrix = new Matrix();
    }
}
