package com.catchpig.utils.ext;

import android.widget.TextView;
import androidx.annotation.ColorRes;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\u001a\u0014\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\b\u0001\u0010\u0003\u001a\u00020\u0004¨\u0006\u0005"}, d2 = {"setTextColorRes", "", "Landroid/widget/TextView;", "textColor", "", "utils_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class TextViewExtKt {
    public static final void setTextColorRes(@NotNull TextView textView, @ColorRes int i2) {
        Intrinsics.checkNotNullParameter(textView, "<this>");
        try {
            textView.setTextColor(textView.getContext().getColor(i2));
        } catch (Throwable th) {
            LogExtKt.loge("颜色异常 " + th, "TextViewExt");
        }
    }
}
