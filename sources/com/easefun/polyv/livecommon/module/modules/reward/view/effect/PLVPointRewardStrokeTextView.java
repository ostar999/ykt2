package com.easefun.polyv.livecommon.module.modules.reward.view.effect;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;
import com.plv.thirdpart.blankj.utilcode.util.ConvertUtils;

/* loaded from: classes3.dex */
public class PLVPointRewardStrokeTextView extends AppCompatTextView {
    private Context context;
    private TextPaint strokePaint;

    public PLVPointRewardStrokeTextView(Context context) {
        this(context, null);
    }

    @Override // android.widget.TextView, android.view.View
    public void onDraw(Canvas canvas) {
        if (this.strokePaint == null) {
            this.strokePaint = new TextPaint();
        }
        TextPaint paint = getPaint();
        this.strokePaint.setTextSize(paint.getTextSize());
        this.strokePaint.setTypeface(getTypeface());
        this.strokePaint.setFlags(paint.getFlags());
        this.strokePaint.setAlpha(paint.getAlpha());
        this.strokePaint.setStyle(Paint.Style.STROKE);
        this.strokePaint.setColor(-1);
        this.strokePaint.setStrokeWidth(ConvertUtils.dp2px(3.0f));
        canvas.drawText(getText().toString(), getPaddingLeft(), getBaseline(), this.strokePaint);
        super.onDraw(canvas);
    }

    @Override // androidx.appcompat.widget.AppCompatTextView, android.widget.TextView, android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth() + 10, getMeasuredHeight());
    }

    public PLVPointRewardStrokeTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PLVPointRewardStrokeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Helvetica-Bold-Oblique_22454.ttf"));
    }
}
