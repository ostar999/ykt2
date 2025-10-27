package com.easefun.polyv.livecommon.module.utils.span;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.widget.TextView;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes3.dex */
public class PLVTextFaceLoader {
    public static void displayTextImage(CharSequence charSequence, TextView textView) {
        textView.setText(messageToSpan(charSequence, (int) textView.getTextSize(), textView.getContext()));
    }

    public static CharSequence messageToSpan(CharSequence charSequence, int size, Context context) {
        return messageToSpan(charSequence, size, context, (List<int[]>) null);
    }

    private static void setSpan(Context context, String group, SpannableStringBuilder span, int size, int start, int end) throws Resources.NotFoundException {
        Drawable drawable = context.getResources().getDrawable(PLVFaceManager.getInstance().getFaceId(group));
        PLVRelativeImageSpan pLVRelativeImageSpan = new PLVRelativeImageSpan(drawable, 3);
        int i2 = (int) (size * 1.5d);
        drawable.setBounds(0, 0, i2, i2);
        span.setSpan(pLVRelativeImageSpan, start, end, 33);
    }

    public static CharSequence messageToSpan(CharSequence charSequence, int size, Context context, List<int[]> spanIndexs) {
        return messageToSpan(charSequence, new int[]{size}, context, spanIndexs)[0];
    }

    public static CharSequence[] messageToSpan(CharSequence charSequence, int[] sizes, Context context) {
        return messageToSpan(charSequence, sizes, context, (List<int[]>) null);
    }

    public static CharSequence[] messageToSpan(CharSequence charSequence, int[] sizes, Context context, List<int[]> spanIndexs) {
        int length = sizes.length;
        SpannableStringBuilder[] spannableStringBuilderArr = new SpannableStringBuilder[length];
        for (int i2 = 0; i2 < sizes.length; i2++) {
            spannableStringBuilderArr[i2] = new SpannableStringBuilder(charSequence);
        }
        Matcher matcher = Pattern.compile("\\[[^\\[]{1,5}\\]").matcher(charSequence);
        while (matcher.find()) {
            int iStart = matcher.start();
            int iEnd = matcher.end();
            String strGroup = matcher.group();
            for (int i3 = 0; i3 < length; i3++) {
                try {
                    setSpan(context, strGroup, spannableStringBuilderArr[i3], sizes[i3], iStart, iEnd);
                } catch (Exception unused) {
                }
            }
            if (spanIndexs != null) {
                spanIndexs.add(new int[]{iStart, iEnd});
            }
        }
        return spannableStringBuilderArr;
    }
}
