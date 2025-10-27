package com.ykb.ebook.weight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.TypedValue;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import com.ykb.ebook.R;

/* loaded from: classes8.dex */
public class DrawBgTextView extends AppCompatTextView {
    private int bgColor;
    private Paint bgPaint;

    public DrawBgTextView(@NonNull Context context) {
        this(context, null);
    }

    private void init() {
        Paint paint = new Paint();
        this.bgPaint = paint;
        paint.setAntiAlias(true);
        this.bgPaint.setStyle(Paint.Style.FILL);
        this.bgPaint.setColor(getContext().getColor(R.color.color_26ff9da6));
    }

    @Override // android.widget.TextView, android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Layout layout = getLayout();
        if (layout != null) {
            int iApplyDimension = (int) TypedValue.applyDimension(1, 2.0f, getResources().getDisplayMetrics());
            int i2 = 0;
            while (i2 < layout.getLineCount()) {
                canvas.drawRect(0.0f, i2 == 0 ? 0.0f : (getLineHeight() * i2) + iApplyDimension, getWidth(), i2 != 0 ? getLineHeight() : layout.getLineBottom(i2) - iApplyDimension, this.bgPaint);
                i2++;
            }
        }
    }

    public void setBgColor(int i2) {
        this.bgColor = i2;
        this.bgPaint.setColor(i2);
        invalidate();
    }

    public DrawBgTextView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public DrawBgTextView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.bgColor = 0;
        init();
    }
}
