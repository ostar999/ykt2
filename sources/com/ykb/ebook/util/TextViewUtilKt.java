package com.ykb.ebook.util;

import android.text.Layout;
import android.widget.TextView;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002¨\u0006\u0003"}, d2 = {"isTextFullyDisplayed", "", "Landroid/widget/TextView;", "ebook_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class TextViewUtilKt {
    public static final boolean isTextFullyDisplayed(@NotNull TextView textView) {
        Intrinsics.checkNotNullParameter(textView, "<this>");
        Layout layout = textView.getLayout();
        if (layout == null) {
            return false;
        }
        int lineCount = layout.getLineCount();
        return (lineCount >= 0 && lineCount < textView.getMaxLines()) || layout.getEllipsisCount(lineCount - 1) <= 0;
    }
}
