package com.psychiatrygarden.widget;

import android.content.Context;
import android.text.Layout;
import android.widget.TextView;

/* loaded from: classes6.dex */
public class TextLayoutUtil {
    public static int dp2px(Context context, float dpValue) {
        return (int) ((dpValue * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int getHysteresisOffset(TextView textView, int x2, int y2, int previousOffset) {
        Layout layout = textView.getLayout();
        if (layout == null) {
            return -1;
        }
        int lineForVertical = layout.getLineForVertical(y2);
        if (isEndOfLineOffset(layout, previousOffset)) {
            int primaryHorizontal = (int) layout.getPrimaryHorizontal(previousOffset - 1);
            int lineRight = (int) layout.getLineRight(lineForVertical);
            if (x2 > lineRight - ((lineRight - primaryHorizontal) / 2)) {
                previousOffset--;
            }
        }
        int lineForOffset = layout.getLineForOffset(previousOffset);
        int lineTop = layout.getLineTop(lineForOffset);
        int lineBottom = layout.getLineBottom(lineForOffset);
        int i2 = (lineBottom - lineTop) / 2;
        if ((lineForVertical == lineForOffset + 1 && y2 - lineBottom < i2) || (lineForVertical == lineForOffset - 1 && lineTop - y2 < i2)) {
            lineForVertical = lineForOffset;
        }
        int offsetForHorizontal = layout.getOffsetForHorizontal(lineForVertical, x2);
        if (offsetForHorizontal >= textView.getText().length() - 1) {
            return offsetForHorizontal;
        }
        int i3 = offsetForHorizontal + 1;
        if (!isEndOfLineOffset(layout, i3)) {
            return offsetForHorizontal;
        }
        int primaryHorizontal2 = (int) layout.getPrimaryHorizontal(offsetForHorizontal);
        int lineRight2 = (int) layout.getLineRight(lineForVertical);
        return x2 > lineRight2 - ((lineRight2 - primaryHorizontal2) / 2) ? i3 : offsetForHorizontal;
    }

    public static int getPreciseOffset(TextView textView, int x2, int y2) {
        Layout layout = textView.getLayout();
        if (layout == null) {
            return -1;
        }
        int offsetForHorizontal = layout.getOffsetForHorizontal(layout.getLineForVertical(y2), x2);
        return ((int) layout.getPrimaryHorizontal(offsetForHorizontal)) > x2 ? layout.getOffsetToLeftOf(offsetForHorizontal) : offsetForHorizontal;
    }

    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    private static boolean isEndOfLineOffset(Layout layout, int offset) {
        return offset > 0 && layout.getLineForOffset(offset) == layout.getLineForOffset(offset - 1) + 1;
    }
}
