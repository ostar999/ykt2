package com.ruffian.library.widget.utils;

import android.graphics.Paint;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;

/* loaded from: classes6.dex */
public class TextViewUtils {
    private static TextViewUtils instance;

    private TextViewUtils() {
    }

    public static TextViewUtils get() {
        if (instance == null) {
            synchronized (TextViewUtils.class) {
                if (instance == null) {
                    instance = new TextViewUtils();
                }
            }
        }
        return instance;
    }

    public float getTextHeight(TextView textView, int i2, int i3, int i4, int i5) {
        if (textView == null) {
            return 0.0f;
        }
        Paint.FontMetrics fontMetrics = textView.getPaint().getFontMetrics();
        float fAbs = Math.abs(fontMetrics.bottom - fontMetrics.top) * textView.getLineCount();
        float height = (((textView.getHeight() - i2) - i3) - i4) - i5;
        return fAbs > height ? height : fAbs;
    }

    public float getTextWidth(TextView textView, int i2, int i3, int i4, int i5) {
        float fMeasureText;
        if (textView == null) {
            return 0.0f;
        }
        String string = textView.getText().toString();
        if (string.contains("\n")) {
            String[] strArrSplit = string.split("\n");
            ArrayList arrayList = new ArrayList(strArrSplit.length);
            for (String str : strArrSplit) {
                arrayList.add(Float.valueOf(textView.getPaint().measureText(str)));
            }
            fMeasureText = ((Float) Collections.max(arrayList)).floatValue();
        } else {
            fMeasureText = textView.getPaint().measureText(string);
        }
        float width = (((textView.getWidth() - i2) - i3) - i4) - i5;
        return fMeasureText > width ? width : fMeasureText;
    }
}
