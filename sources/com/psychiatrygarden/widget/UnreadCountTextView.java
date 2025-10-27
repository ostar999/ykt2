package com.psychiatrygarden.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;
import com.psychiatrygarden.utils.ScreenUtil;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class UnreadCountTextView extends AppCompatTextView {
    private int mNormalSize;
    private Paint mPaint;

    public UnreadCountTextView(Context context) {
        super(context);
        this.mNormalSize = ScreenUtil.getPxByDp(getContext(), 16);
        init();
    }

    private void init() {
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setColor(getResources().getColor(R.color.app_theme_red));
        setTextColor(-1);
        setTextSize(2, 10.0f);
    }

    @Override // android.widget.TextView, android.view.View
    public void onDraw(Canvas canvas) {
        if (getText().length() == 1) {
            int i2 = this.mNormalSize;
            canvas.drawOval(new RectF(0.0f, 0.0f, i2, i2), this.mPaint);
        } else if (getText().length() > 1) {
            canvas.drawRoundRect(new RectF(0.0f, 0.0f, getMeasuredWidth(), getMeasuredHeight()), getMeasuredHeight() / 3, getMeasuredHeight() / 3, this.mPaint);
        }
        super.onDraw(canvas);
    }

    @Override // androidx.appcompat.widget.AppCompatTextView, android.widget.TextView, android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int i2 = this.mNormalSize;
        setMeasuredDimension(getText().length() > 1 ? this.mNormalSize + ScreenUtil.getPxByDp(getContext(), (getText().length() - 1) * 10) : i2, i2);
    }

    public UnreadCountTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mNormalSize = ScreenUtil.getPxByDp(getContext(), 16);
        init();
    }

    public UnreadCountTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mNormalSize = ScreenUtil.getPxByDp(getContext(), 16);
        init();
    }
}
