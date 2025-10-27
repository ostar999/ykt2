package com.ykb.ebook.util;

import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import androidx.annotation.ColorInt;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\u001a \u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"getSpannableColor", "Landroid/text/SpannableStringBuilder;", "contentStr", "", "targetString", "targetColor", "", "ebook_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class SpannableStringBuilderUtilKt {
    @NotNull
    public static final SpannableStringBuilder getSpannableColor(@NotNull String contentStr, @NotNull String targetString, @ColorInt int i2) {
        Intrinsics.checkNotNullParameter(contentStr, "contentStr");
        Intrinsics.checkNotNullParameter(targetString, "targetString");
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(contentStr);
        int iIndexOf$default = StringsKt__StringsKt.indexOf$default((CharSequence) contentStr, targetString, 0, false, 6, (Object) null);
        spannableStringBuilder.setSpan(new ForegroundColorSpan(i2), iIndexOf$default, targetString.length() + iIndexOf$default, 17);
        return spannableStringBuilder;
    }
}
