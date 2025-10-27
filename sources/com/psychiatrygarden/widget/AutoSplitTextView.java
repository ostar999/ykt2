package com.psychiatrygarden.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import cn.hutool.core.text.StrPool;

@SuppressLint({"AppCompatCustomView"})
/* loaded from: classes6.dex */
public class AutoSplitTextView extends TextView {
    private boolean mEnabled;

    public AutoSplitTextView(Context context) {
        super(context);
        this.mEnabled = true;
    }

    private String autoSplitText(final TextView tv2) {
        String string = tv2.getText().toString();
        TextPaint paint = tv2.getPaint();
        float width = (tv2.getWidth() - tv2.getPaddingLeft()) - tv2.getPaddingRight();
        String[] strArrSplit = string.replaceAll(StrPool.CR, "").split("\n");
        StringBuilder sb = new StringBuilder();
        for (String str : strArrSplit) {
            if (paint.measureText(str) <= width) {
                sb.append(str);
            } else {
                int i2 = 0;
                float fMeasureText = 0.0f;
                while (i2 != str.length()) {
                    char cCharAt = str.charAt(i2);
                    fMeasureText += paint.measureText(String.valueOf(cCharAt));
                    if (fMeasureText <= width) {
                        sb.append(cCharAt);
                    } else {
                        sb.append("\n");
                        i2--;
                        fMeasureText = 0.0f;
                    }
                    i2++;
                }
            }
            sb.append("\n");
        }
        if (!string.endsWith("\n")) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    @Override // android.widget.TextView, android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (View.MeasureSpec.getMode(widthMeasureSpec) == 1073741824 && View.MeasureSpec.getMode(heightMeasureSpec) == 1073741824 && getWidth() > 0 && getHeight() > 0 && this.mEnabled) {
            String strAutoSplitText = autoSplitText(this);
            if (!TextUtils.isEmpty(strAutoSplitText)) {
                setText(strAutoSplitText);
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setAutoSplitEnabled(boolean enabled) {
        this.mEnabled = enabled;
    }

    public AutoSplitTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mEnabled = true;
    }

    public AutoSplitTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mEnabled = true;
    }
}
