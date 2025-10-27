package com.psychiatrygarden.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class FontTextView extends AppCompatTextView {
    private static final int TYPE_BOLD = 2;
    private static final int TYPE_MEDIUM = 1;
    private static final int TYPE_NORMAL = 0;
    private float strokeWidth;

    public FontTextView(@NonNull Context context) {
        this(context, null);
    }

    private void init(AttributeSet attrs) {
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attrs, R.styleable.my_textview_font);
        this.strokeWidth = typedArrayObtainStyledAttributes.getFloat(0, 0.0f);
        typedArrayObtainStyledAttributes.recycle();
        invalidate();
    }

    @Override // android.widget.TextView, android.view.View
    public void onDraw(Canvas canvas) {
        TextPaint paint = getPaint();
        paint.setStrokeWidth(this.strokeWidth);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        super.onDraw(canvas);
    }

    public FontTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FontTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.strokeWidth = 0.0f;
        init(attrs);
    }
}
