package splitties.dimensions;

import android.content.Context;
import android.view.View;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0001H\u0086\b\u001a\u0015\u0010\u0000\u001a\u00020\u0004*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0086\b\u001a\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u0001H\u0086\b\u001a\u0015\u0010\u0000\u001a\u00020\u0004*\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u0004H\u0086\b\u001a\u0015\u0010\u0006\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0001H\u0086\b\u001a\u0015\u0010\u0006\u001a\u00020\u0004*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0086\b\u001a\u0015\u0010\u0006\u001a\u00020\u0001*\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u0001H\u0086\b\u001a\u0015\u0010\u0006\u001a\u00020\u0004*\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u0004H\u0086\b¨\u0006\u0007"}, d2 = {"dip", "", "Landroid/content/Context;", "value", "", "Landroid/view/View;", "dp", "splitties-dimensions_release"}, k = 2, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes9.dex */
public final class DimensionsKt {
    public static final int dip(@NotNull Context context, int i2) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        return (int) (i2 * context.getResources().getDisplayMetrics().density);
    }

    public static final int dp(@NotNull View view, int i2) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Context context = view.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        return (int) (i2 * context.getResources().getDisplayMetrics().density);
    }

    public static final float dip(@NotNull Context context, float f2) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        return f2 * context.getResources().getDisplayMetrics().density;
    }

    public static final int dip(@NotNull View view, int i2) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Context context = view.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        return (int) (i2 * context.getResources().getDisplayMetrics().density);
    }

    public static final float dp(@NotNull View view, float f2) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Context context = view.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        return f2 * context.getResources().getDisplayMetrics().density;
    }

    public static final float dip(@NotNull View view, float f2) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Context context = view.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        return f2 * context.getResources().getDisplayMetrics().density;
    }

    public static final int dp(@NotNull Context context, int i2) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        return (int) (i2 * context.getResources().getDisplayMetrics().density);
    }

    public static final float dp(@NotNull Context context, float f2) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        return f2 * context.getResources().getDisplayMetrics().density;
    }
}
