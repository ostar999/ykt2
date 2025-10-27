package com.easefun.polyv.livecommon.module.modules.marquee;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.SpannableString;
import android.text.style.ScaleXSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.plv.foundationsdk.utils.PLVScreenUtils;

/* loaded from: classes3.dex */
public class PLVStrokeTextView extends TextView {
    private boolean hasStroke;
    private float spacing;
    private CharSequence srcText;
    private int strokeBlurX;
    private int strokeBlurY;
    private int strokeColor;
    private int strokeWidth;

    public PLVStrokeTextView(Context context) {
        super(context);
        this.strokeBlurX = 0;
        this.strokeBlurY = 0;
    }

    private void applySpacing() {
        if (this.srcText == null) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        int i2 = 0;
        while (i2 < this.srcText.length()) {
            sb.append(this.srcText.charAt(i2));
            i2++;
            if (i2 < this.srcText.length()) {
                sb.append(" ");
            }
        }
        SpannableString spannableString = new SpannableString(sb.toString());
        if (sb.toString().length() > 1) {
            for (int i3 = 1; i3 < sb.toString().length(); i3 += 2) {
                spannableString.setSpan(new ScaleXSpan(this.spacing), i3, i3 + 1, 33);
            }
        }
        super.setText(spannableString, TextView.BufferType.SPANNABLE);
    }

    public static void disableHardwareRendering(View v2) {
        v2.setLayerType(1, null);
    }

    public int getStrokeWidth() {
        return this.strokeWidth;
    }

    @Override // android.widget.TextView, android.view.View
    @SuppressLint({"DrawAllocation"})
    public void onDraw(Canvas canvas) {
        int i2;
        if (this.hasStroke) {
            ColorStateList textColors = getTextColors();
            getPaint().setStyle(Paint.Style.STROKE);
            getPaint().setStrokeWidth(PLVScreenUtils.dip2px(getContext(), this.strokeWidth));
            int i3 = this.strokeBlurX;
            if (i3 > 0 && (i2 = this.strokeBlurY) > 0) {
                int iMax = Math.max(i3, i2);
                disableHardwareRendering(this);
                getPaint().setMaskFilter(new BlurMaskFilter(PLVScreenUtils.dip2px(getContext(), iMax), BlurMaskFilter.Blur.SOLID));
            }
            setTextColor(this.strokeColor);
            super.onDraw(canvas);
            getPaint().setStyle(Paint.Style.FILL);
            setTextColor(textColors);
        }
        super.onDraw(canvas);
    }

    public void setHasStroke(boolean hasStroke) {
        this.hasStroke = hasStroke;
    }

    public void setSpacing(float spacing) {
        this.spacing = spacing;
        if (spacing > 0.0f) {
            applySpacing();
        }
    }

    public void setStrokeBlurX(int strokeBlurX) {
        this.strokeBlurX = strokeBlurX;
    }

    public void setStrokeBlurY(int strokeBlurY) {
        this.strokeBlurY = strokeBlurY;
    }

    public void setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    @Override // android.widget.TextView
    public void setText(CharSequence text, TextView.BufferType type) {
        this.srcText = text;
        if (this.spacing > 0.0f) {
            applySpacing();
        } else {
            super.setText(text, type);
        }
    }

    public PLVStrokeTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.strokeBlurX = 0;
        this.strokeBlurY = 0;
    }

    public PLVStrokeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.strokeBlurX = 0;
        this.strokeBlurY = 0;
    }
}
