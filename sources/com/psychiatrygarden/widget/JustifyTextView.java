package com.psychiatrygarden.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint({"AppCompatCustomView"})
/* loaded from: classes6.dex */
public class JustifyTextView extends TextView {
    private int mLineY;
    private int mViewWidth;

    public JustifyTextView(Context context) {
        super(context);
    }

    private void drawScaledText(Canvas canvas, int lineStart, String line, float lineWidth) {
        float desiredWidth = 0.0f;
        if (isFirstLineOfParagraph(lineStart, line)) {
            canvas.drawText("  ", 0.0f, this.mLineY, getPaint());
            desiredWidth = 0.0f + Layout.getDesiredWidth("  ", getPaint());
            line = line.substring(3);
        }
        int length = line.length() - 1;
        int i2 = 2;
        if (line.length() > 2 && line.charAt(0) == 12288 && line.charAt(1) == 12288) {
            String strSubstring = line.substring(0, 2);
            float desiredWidth2 = Layout.getDesiredWidth(strSubstring, getPaint());
            canvas.drawText(strSubstring, desiredWidth, this.mLineY, getPaint());
            desiredWidth += desiredWidth2;
        } else {
            i2 = 0;
        }
        float f2 = (this.mViewWidth - lineWidth) / length;
        while (i2 < line.length()) {
            String strValueOf = String.valueOf(line.charAt(i2));
            float desiredWidth3 = Layout.getDesiredWidth(strValueOf, getPaint());
            canvas.drawText(strValueOf, desiredWidth, this.mLineY, getPaint());
            desiredWidth += desiredWidth3 + f2;
            i2++;
        }
    }

    private boolean isFirstLineOfParagraph(int lineStart, String line) {
        return line.length() > 3 && line.charAt(0) == ' ' && line.charAt(1) == ' ';
    }

    private boolean needScale(String line) {
        return (line.length() == 0 || line.charAt(line.length() - 1) == '\n') ? false : true;
    }

    @Override // android.widget.TextView, android.view.View
    public void onDraw(Canvas canvas) {
        TextPaint paint = getPaint();
        paint.setColor(getCurrentTextColor());
        paint.drawableState = getDrawableState();
        this.mViewWidth = getMeasuredWidth();
        String string = getText().toString();
        this.mLineY = 0;
        this.mLineY = (int) (0 + getTextSize());
        Layout layout = getLayout();
        if (layout == null) {
            return;
        }
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        int iCeil = (int) ((((int) Math.ceil(fontMetrics.descent - fontMetrics.ascent)) * layout.getSpacingMultiplier()) + layout.getSpacingAdd());
        for (int i2 = 0; i2 < layout.getLineCount(); i2++) {
            int lineStart = layout.getLineStart(i2);
            int lineEnd = layout.getLineEnd(i2);
            float desiredWidth = Layout.getDesiredWidth(string, lineStart, lineEnd, getPaint());
            String strSubstring = string.substring(lineStart, lineEnd);
            if (strSubstring.equals("")) {
                return;
            }
            if (i2 >= layout.getLineCount() - 1) {
                canvas.drawText(strSubstring, 0.0f, this.mLineY, paint);
            } else if (needScale(strSubstring)) {
                drawScaledText(canvas, lineStart, strSubstring, desiredWidth);
            } else {
                canvas.drawText(strSubstring, 0.0f, this.mLineY, paint);
            }
            this.mLineY += iCeil;
        }
    }

    @Override // android.widget.TextView, android.view.View
    public void onLayout(boolean changed, int left, int top2, int right, int bottom) {
        super.onLayout(changed, left, top2, right, bottom);
    }

    public JustifyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
