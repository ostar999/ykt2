package com.easefun.polyv.livecommon.ui.widget.gif;

import android.os.Build;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextDirectionHeuristics;
import android.widget.TextView;
import androidx.annotation.RequiresApi;

/* loaded from: classes3.dex */
public class TextViewLinesUtils {
    private static StaticLayout getStaticLayout(TextView textView, int width) {
        return new StaticLayout(textView.getText(), 0, textView.getText().length(), textView.getPaint(), width, Layout.Alignment.ALIGN_NORMAL, textView.getLineSpacingMultiplier(), textView.getLineSpacingExtra(), textView.getIncludeFontPadding(), textView.getEllipsize(), width);
    }

    @RequiresApi(api = 23)
    private static StaticLayout getStaticLayout23(TextView textView, int width) {
        StaticLayout.Builder hyphenationFrequency = StaticLayout.Builder.obtain(textView.getText(), 0, textView.getText().length(), textView.getPaint(), width).setAlignment(Layout.Alignment.ALIGN_NORMAL).setTextDirection(TextDirectionHeuristics.FIRSTSTRONG_LTR).setLineSpacing(textView.getLineSpacingExtra(), textView.getLineSpacingMultiplier()).setIncludePad(textView.getIncludeFontPadding()).setBreakStrategy(textView.getBreakStrategy()).setHyphenationFrequency(textView.getHyphenationFrequency());
        if (Build.VERSION.SDK_INT >= 26) {
            hyphenationFrequency.setJustificationMode(textView.getJustificationMode());
        }
        if (textView.getEllipsize() != null && textView.getKeyListener() == null) {
            hyphenationFrequency.setEllipsize(textView.getEllipsize()).setEllipsizedWidth(width);
        }
        return hyphenationFrequency.build();
    }

    public static int getTextViewLines(TextView textView, int textViewWidth) {
        return getStaticLayout23(textView, (textViewWidth - textView.getCompoundPaddingLeft()) - textView.getCompoundPaddingRight()).getLineCount();
    }
}
