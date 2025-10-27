package com.easefun.polyv.livecommon.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;
import com.plv.thirdpart.blankj.utilcode.util.ConvertUtils;

/* loaded from: classes3.dex */
public class PLVRewardStrokeTextView extends AppCompatTextView {
    private Context context;
    private TextPaint strokePaint;

    public PLVRewardStrokeTextView(Context context) {
        this(context, null);
    }

    @Override // android.widget.TextView, android.view.View
    public void onDraw(Canvas canvas) {
        if (this.strokePaint == null) {
            this.strokePaint = new TextPaint();
        }
        this.strokePaint.set(getPaint());
        this.strokePaint.setStyle(Paint.Style.STROKE);
        this.strokePaint.setColor(-1);
        this.strokePaint.setStrokeWidth(ConvertUtils.dp2px(3.0f));
        canvas.drawText(getText().toString(), getPaddingLeft(), getBaseline(), this.strokePaint);
        super.onDraw(canvas);
    }

    @Override // androidx.appcompat.widget.AppCompatTextView, android.widget.TextView, android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public PLVRewardStrokeTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PLVRewardStrokeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
