package android.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.CheckResult;
import androidx.annotation.LayoutRes;
import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u00000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\u001a&\u0010\u0000\u001a\u0002H\u0001\"\b\b\u0000\u0010\u0001*\u00020\u0002*\u00020\u00032\b\b\u0001\u0010\u0004\u001a\u00020\u0005H\u0087\b¢\u0006\u0002\u0010\u0006\u001a&\u0010\u0000\u001a\u0002H\u0001\"\b\b\u0000\u0010\u0001*\u00020\u0002*\u00020\u00072\b\b\u0001\u0010\u0004\u001a\u00020\u0005H\u0087\b¢\u0006\u0002\u0010\b\u001a.\u0010\u0000\u001a\u0002H\u0001\"\b\b\u0000\u0010\u0001*\u00020\u0002*\u00020\t2\b\b\u0001\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\fH\u0086\b¢\u0006\u0002\u0010\r\u001a\u0017\u0010\u000e\u001a\u00020\u000f*\u00020\t2\b\b\u0001\u0010\n\u001a\u00020\u0005H\u0086\b¨\u0006\u0010"}, d2 = {"inflate", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "Landroid/view/View;", "Landroid/content/Context;", "layoutResId", "", "(Landroid/content/Context;I)Landroid/view/View;", "Landroid/view/LayoutInflater;", "(Landroid/view/LayoutInflater;I)Landroid/view/View;", "Landroid/view/ViewGroup;", "layoutRes", "attachToRoot", "", "(Landroid/view/ViewGroup;IZ)Landroid/view/View;", "inflateAndAttach", "", "splitties-views_release"}, k = 2, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes9.dex */
public final class LayoutInflaterKt {
    @CheckResult
    @NotNull
    public static final <V extends View> V inflate(@NotNull LayoutInflater layoutInflater, @LayoutRes int i2) {
        Intrinsics.checkNotNullParameter(layoutInflater, "<this>");
        V v2 = (V) layoutInflater.inflate(i2, (ViewGroup) null, false);
        if (v2 != null) {
            return v2;
        }
        throw new NullPointerException("null cannot be cast to non-null type V of splitties.views.LayoutInflaterKt.inflate");
    }

    public static final void inflateAndAttach(@NotNull ViewGroup viewGroup, @LayoutRes int i2) {
        Intrinsics.checkNotNullParameter(viewGroup, "<this>");
        Context context = viewGroup.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        Object systemService = context.getSystemService("layout_inflater");
        if (systemService == null) {
            throw new NullPointerException("null cannot be cast to non-null type android.view.LayoutInflater");
        }
        ((LayoutInflater) systemService).inflate(i2, viewGroup, true);
    }

    @CheckResult
    @NotNull
    public static final <V extends View> V inflate(@NotNull Context context, @LayoutRes int i2) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        Object systemService = context.getSystemService("layout_inflater");
        if (systemService == null) {
            throw new NullPointerException("null cannot be cast to non-null type android.view.LayoutInflater");
        }
        V v2 = (V) ((LayoutInflater) systemService).inflate(i2, (ViewGroup) null, false);
        if (v2 != null) {
            return v2;
        }
        throw new NullPointerException("null cannot be cast to non-null type V of splitties.views.LayoutInflaterKt.inflate");
    }

    @NotNull
    public static final <V extends View> V inflate(@NotNull ViewGroup viewGroup, @LayoutRes int i2, boolean z2) {
        Intrinsics.checkNotNullParameter(viewGroup, "<this>");
        Context context = viewGroup.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        Object systemService = context.getSystemService("layout_inflater");
        if (systemService != null) {
            V v2 = (V) ((LayoutInflater) systemService).inflate(i2, viewGroup, z2);
            if (v2 != null) {
                return v2;
            }
            throw new NullPointerException("null cannot be cast to non-null type V of splitties.views.LayoutInflaterKt.inflate");
        }
        throw new NullPointerException("null cannot be cast to non-null type android.view.LayoutInflater");
    }
}
