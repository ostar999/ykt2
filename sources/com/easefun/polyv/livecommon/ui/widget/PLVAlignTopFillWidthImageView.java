package com.easefun.polyv.livecommon.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatImageView;
import com.plv.thirdpart.blankj.utilcode.util.ScreenUtils;

/* loaded from: classes3.dex */
public class PLVAlignTopFillWidthImageView extends AppCompatImageView {
    private final Matrix matrix;

    public PLVAlignTopFillWidthImageView(Context context) {
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
        float screenWidth = ScreenUtils.getScreenWidth() / getDrawable().getIntrinsicWidth();
        this.matrix.setScale(screenWidth, screenWidth);
    }

    public PLVAlignTopFillWidthImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.matrix = new Matrix();
    }

    public PLVAlignTopFillWidthImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.matrix = new Matrix();
    }
}
