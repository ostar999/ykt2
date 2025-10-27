package com.catchpig.mvvm.ext;

import android.content.Context;
import android.util.TypedValue;
import androidx.annotation.ColorRes;
import com.catchpig.mvvm.R;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0014\u0010\u0002\u001a\u00020\u0003*\u00020\u00042\b\b\u0001\u0010\u0005\u001a\u00020\u0003\u001a\f\u0010\u0006\u001a\u00020\u0003*\u00020\u0004H\u0007\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0007"}, d2 = {"TAG", "", "getColorM", "", "Landroid/content/Context;", "colorRes", "getColorPrimary", "mvvm_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class ContextExtKt {

    @NotNull
    private static final String TAG = "ContextExt";

    public static final int getColorM(@NotNull Context context, @ColorRes int i2) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        return context.getColor(i2);
    }

    @ColorRes
    public static final int getColorPrimary(@NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.resourceId;
    }
}
