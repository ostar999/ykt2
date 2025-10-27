package com.google.android.exoplayer2.text.span;

import android.text.Spannable;

/* loaded from: classes3.dex */
public final class SpanUtil {
    private SpanUtil() {
    }

    public static void addOrReplaceSpan(Spannable spannable, Object obj, int i2, int i3, int i4) {
        for (Object obj2 : spannable.getSpans(i2, i3, obj.getClass())) {
            if (spannable.getSpanStart(obj2) == i2 && spannable.getSpanEnd(obj2) == i3 && spannable.getSpanFlags(obj2) == i4) {
                spannable.removeSpan(obj2);
            }
        }
        spannable.setSpan(obj, i2, i3, i4);
    }
}
