package com.catchpig.utils.ext;

import android.content.Context;
import android.view.View;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0001\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0001\u001a\u0012\u0010\u0005\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0001\u001a\u0012\u0010\u0005\u001a\u00020\u0001*\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0001¨\u0006\u0007"}, d2 = {"dp2px", "", "Landroid/content/Context;", "dp", "Landroid/view/View;", "px2dp", "px", "utils_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class CommonExtKt {
    public static final int dp2px(@NotNull Context context, int i2) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        return (int) ((i2 * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static final int px2dp(@NotNull Context context, int i2) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        return (int) ((i2 / context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static final int dp2px(@NotNull View view, int i2) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        return (int) ((i2 * view.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static final int px2dp(@NotNull View view, int i2) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        return (int) ((i2 / view.getResources().getDisplayMetrics().density) + 0.5f);
    }
}
