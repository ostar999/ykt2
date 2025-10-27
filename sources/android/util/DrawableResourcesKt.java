package android.util;

import android.content.AppCtxKt;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.View;
import androidx.annotation.AttrRes;
import androidx.annotation.DrawableRes;
import androidx.fragment.app.Fragment;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000(\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0015\u0010\u0006\u001a\u0004\u0018\u00010\u00072\b\b\u0001\u0010\b\u001a\u00020\tH\u0086\b\u001a\u0015\u0010\n\u001a\u0004\u0018\u00010\u00072\b\b\u0001\u0010\u000b\u001a\u00020\tH\u0086\b\u001a\u0016\u0010\f\u001a\u0004\u0018\u00010\u0007*\u00020\r2\b\b\u0001\u0010\b\u001a\u00020\t\u001a\u0019\u0010\f\u001a\u0004\u0018\u00010\u0007*\u00020\u000e2\b\b\u0001\u0010\b\u001a\u00020\tH\u0086\b\u001a\u0019\u0010\f\u001a\u0004\u0018\u00010\u0007*\u00020\u000f2\b\b\u0001\u0010\b\u001a\u00020\tH\u0086\b\u001a\u0016\u0010\u0010\u001a\u0004\u0018\u00010\u0007*\u00020\r2\b\b\u0001\u0010\u000b\u001a\u00020\t\u001a\u0019\u0010\u0010\u001a\u0004\u0018\u00010\u0007*\u00020\u000e2\b\b\u0001\u0010\u000b\u001a\u00020\tH\u0086\b\u001a\u0019\u0010\u0010\u001a\u0004\u0018\u00010\u0007*\u00020\u000f2\b\b\u0001\u0010\u000b\u001a\u00020\tH\u0086\b\"\u001b\u0010\u0000\u001a\u00020\u00018BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0002\u0010\u0003¨\u0006\u0011"}, d2 = {"tmpValue", "Landroid/util/TypedValue;", "getTmpValue", "()Landroid/util/TypedValue;", "tmpValue$delegate", "Lkotlin/Lazy;", "appDrawable", "Landroid/graphics/drawable/Drawable;", "drawableResId", "", "appStyledDrawable", "attr", "drawable", "Landroid/content/Context;", "Landroid/view/View;", "Landroidx/fragment/app/Fragment;", "styledDrawable", "splitties-resources_release"}, k = 2, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes9.dex */
public final class DrawableResourcesKt {

    @NotNull
    private static final Lazy tmpValue$delegate = LazyKt__LazyJVMKt.lazy(new Function0<TypedValue>() { // from class: splitties.resources.DrawableResourcesKt$tmpValue$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        @NotNull
        public final android.util.TypedValue invoke() {
            return new android.util.TypedValue();
        }
    });

    @Nullable
    public static final Drawable appDrawable(@DrawableRes int i2) {
        return drawable(AppCtxKt.getAppCtx(), i2);
    }

    @Nullable
    public static final Drawable appStyledDrawable(@AttrRes int i2) {
        return styledDrawable(AppCtxKt.getAppCtx(), i2);
    }

    @Nullable
    public static final Drawable drawable(@NotNull Context context, @DrawableRes int i2) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        return context.getDrawable(i2);
    }

    private static final TypedValue getTmpValue() {
        return (TypedValue) tmpValue$delegate.getValue();
    }

    @Nullable
    public static final Drawable styledDrawable(@NotNull Context context, @AttrRes int i2) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        return drawable(context, StyledAttributesKt.resolveThemeAttribute$default(context, i2, false, 2, null));
    }

    @Nullable
    public static final Drawable drawable(@NotNull Fragment fragment, @DrawableRes int i2) {
        Intrinsics.checkNotNullParameter(fragment, "<this>");
        Context context = fragment.getContext();
        Intrinsics.checkNotNull(context);
        Intrinsics.checkNotNullExpressionValue(context, "context!!");
        return drawable(context, i2);
    }

    @Nullable
    public static final Drawable styledDrawable(@NotNull Fragment fragment, @AttrRes int i2) {
        Intrinsics.checkNotNullParameter(fragment, "<this>");
        Context context = fragment.getContext();
        Intrinsics.checkNotNull(context);
        Intrinsics.checkNotNullExpressionValue(context, "context!!");
        return styledDrawable(context, i2);
    }

    @Nullable
    public static final Drawable drawable(@NotNull View view, @DrawableRes int i2) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Context context = view.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        return drawable(context, i2);
    }

    @Nullable
    public static final Drawable styledDrawable(@NotNull View view, @AttrRes int i2) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Context context = view.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        return styledDrawable(context, i2);
    }
}
