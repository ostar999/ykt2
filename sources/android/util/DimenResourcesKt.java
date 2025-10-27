package android.util;

import android.content.AppCtxKt;
import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;
import androidx.annotation.AttrRes;
import androidx.annotation.DimenRes;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.fragment.app.Fragment;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import splitties.mainthread.MainThreadKt;

@Metadata(d1 = {"\u0000*\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a\u0013\u0010\u0000\u001a\u00020\u00012\b\b\u0001\u0010\u0002\u001a\u00020\u0003H\u0086\b\u001a\u0013\u0010\u0004\u001a\u00020\u00032\b\b\u0001\u0010\u0002\u001a\u00020\u0003H\u0086\b\u001a\u0013\u0010\u0005\u001a\u00020\u00032\b\b\u0001\u0010\u0002\u001a\u00020\u0003H\u0086\b\u001a\u0013\u0010\u0006\u001a\u00020\u00012\b\b\u0001\u0010\u0007\u001a\u00020\u0003H\u0086\b\u001a\u0013\u0010\b\u001a\u00020\u00032\b\b\u0001\u0010\u0007\u001a\u00020\u0003H\u0086\b\u001a\u0013\u0010\t\u001a\u00020\u00032\b\b\u0001\u0010\u0007\u001a\u00020\u0003H\u0086\b\u001a\f\u0010\n\u001a\u00020\u000b*\u00020\fH\u0002\u001a\u0017\u0010\r\u001a\u00020\u0001*\u00020\u000e2\b\b\u0001\u0010\u0002\u001a\u00020\u0003H\u0086\b\u001a\u0017\u0010\r\u001a\u00020\u0001*\u00020\u000f2\b\b\u0001\u0010\u0002\u001a\u00020\u0003H\u0086\b\u001a\u0017\u0010\r\u001a\u00020\u0001*\u00020\u00102\b\b\u0001\u0010\u0002\u001a\u00020\u0003H\u0086\b\u001a\u0017\u0010\u0011\u001a\u00020\u0003*\u00020\u000e2\b\b\u0001\u0010\u0002\u001a\u00020\u0003H\u0086\b\u001a\u0017\u0010\u0011\u001a\u00020\u0003*\u00020\u000f2\b\b\u0001\u0010\u0002\u001a\u00020\u0003H\u0086\b\u001a\u0017\u0010\u0011\u001a\u00020\u0003*\u00020\u00102\b\b\u0001\u0010\u0002\u001a\u00020\u0003H\u0086\b\u001a\u0017\u0010\u0012\u001a\u00020\u0003*\u00020\u000e2\b\b\u0001\u0010\u0002\u001a\u00020\u0003H\u0086\b\u001a\u0017\u0010\u0012\u001a\u00020\u0003*\u00020\u000f2\b\b\u0001\u0010\u0002\u001a\u00020\u0003H\u0086\b\u001a\u0017\u0010\u0012\u001a\u00020\u0003*\u00020\u00102\b\b\u0001\u0010\u0002\u001a\u00020\u0003H\u0086\b\u001a\u0014\u0010\u0013\u001a\u00020\u0001*\u00020\u000e2\b\b\u0001\u0010\u0007\u001a\u00020\u0003\u001a\u0017\u0010\u0013\u001a\u00020\u0001*\u00020\u000f2\b\b\u0001\u0010\u0007\u001a\u00020\u0003H\u0086\b\u001a\u0017\u0010\u0013\u001a\u00020\u0001*\u00020\u00102\b\b\u0001\u0010\u0007\u001a\u00020\u0003H\u0086\b\u001a\u0014\u0010\u0014\u001a\u00020\u0003*\u00020\u000e2\b\b\u0001\u0010\u0007\u001a\u00020\u0003\u001a\u0017\u0010\u0014\u001a\u00020\u0003*\u00020\u000f2\b\b\u0001\u0010\u0007\u001a\u00020\u0003H\u0086\b\u001a\u0017\u0010\u0014\u001a\u00020\u0003*\u00020\u00102\b\b\u0001\u0010\u0007\u001a\u00020\u0003H\u0086\b\u001a\u0014\u0010\u0015\u001a\u00020\u0003*\u00020\u000e2\b\b\u0001\u0010\u0007\u001a\u00020\u0003\u001a\u0017\u0010\u0015\u001a\u00020\u0003*\u00020\u000f2\b\b\u0001\u0010\u0007\u001a\u00020\u0003H\u0086\b\u001a\u0017\u0010\u0015\u001a\u00020\u0003*\u00020\u00102\b\b\u0001\u0010\u0007\u001a\u00020\u0003H\u0086\b¨\u0006\u0016"}, d2 = {"appDimen", "", "dimenResId", "", "appDimenPxOffset", "appDimenPxSize", "appStyledDimen", "attr", "appStyledDimenPxOffset", "appStyledDimenPxSize", "checkOfDimensionType", "", "Landroid/util/TypedValue;", "dimen", "Landroid/content/Context;", "Landroid/view/View;", "Landroidx/fragment/app/Fragment;", "dimenPxOffset", "dimenPxSize", "styledDimen", "styledDimenPxOffset", "styledDimenPxSize", "splitties-resources_release"}, k = 2, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes9.dex */
public final class DimenResourcesKt {
    public static final float appDimen(@DimenRes int i2) {
        return AppCtxKt.getAppCtx().getResources().getDimension(i2);
    }

    public static final int appDimenPxOffset(@DimenRes int i2) {
        return AppCtxKt.getAppCtx().getResources().getDimensionPixelOffset(i2);
    }

    public static final int appDimenPxSize(@DimenRes int i2) {
        return AppCtxKt.getAppCtx().getResources().getDimensionPixelSize(i2);
    }

    public static final float appStyledDimen(@AttrRes int i2) {
        return styledDimen(AppCtxKt.getAppCtx(), i2);
    }

    public static final int appStyledDimenPxOffset(@AttrRes int i2) {
        return styledDimenPxOffset(AppCtxKt.getAppCtx(), i2);
    }

    public static final int appStyledDimenPxSize(@AttrRes int i2) {
        return styledDimenPxSize(AppCtxKt.getAppCtx(), i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void checkOfDimensionType(TypedValue typedValue) {
        if (!(typedValue.type == 5)) {
            throw new IllegalArgumentException(StyledAttributesKt.unexpectedThemeAttributeTypeErrorMessage(typedValue, TypedValues.Custom.S_DIMENSION).toString());
        }
    }

    public static final float dimen(@NotNull Context context, @DimenRes int i2) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        return context.getResources().getDimension(i2);
    }

    public static final int dimenPxOffset(@NotNull Context context, @DimenRes int i2) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        return context.getResources().getDimensionPixelOffset(i2);
    }

    public static final int dimenPxSize(@NotNull Context context, @DimenRes int i2) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        return context.getResources().getDimensionPixelSize(i2);
    }

    public static final float styledDimen(@NotNull Fragment fragment, @AttrRes int i2) {
        Intrinsics.checkNotNullParameter(fragment, "<this>");
        Context context = fragment.getContext();
        Intrinsics.checkNotNull(context);
        Intrinsics.checkNotNullExpressionValue(context, "context!!");
        return styledDimen(context, i2);
    }

    public static final int styledDimenPxOffset(@NotNull Fragment fragment, @AttrRes int i2) {
        Intrinsics.checkNotNullParameter(fragment, "<this>");
        Context context = fragment.getContext();
        Intrinsics.checkNotNull(context);
        Intrinsics.checkNotNullExpressionValue(context, "context!!");
        return styledDimenPxOffset(context, i2);
    }

    public static final int styledDimenPxSize(@NotNull Fragment fragment, @AttrRes int i2) {
        Intrinsics.checkNotNullParameter(fragment, "<this>");
        Context context = fragment.getContext();
        Intrinsics.checkNotNull(context);
        Intrinsics.checkNotNullExpressionValue(context, "context!!");
        return styledDimenPxSize(context, i2);
    }

    public static final float dimen(@NotNull Fragment fragment, @DimenRes int i2) {
        Intrinsics.checkNotNullParameter(fragment, "<this>");
        Context context = fragment.getContext();
        Intrinsics.checkNotNull(context);
        Intrinsics.checkNotNullExpressionValue(context, "context!!");
        return context.getResources().getDimension(i2);
    }

    public static final int dimenPxOffset(@NotNull Fragment fragment, @DimenRes int i2) {
        Intrinsics.checkNotNullParameter(fragment, "<this>");
        Context context = fragment.getContext();
        Intrinsics.checkNotNull(context);
        Intrinsics.checkNotNullExpressionValue(context, "context!!");
        return context.getResources().getDimensionPixelOffset(i2);
    }

    public static final int dimenPxSize(@NotNull Fragment fragment, @DimenRes int i2) {
        Intrinsics.checkNotNullParameter(fragment, "<this>");
        Context context = fragment.getContext();
        Intrinsics.checkNotNull(context);
        Intrinsics.checkNotNullExpressionValue(context, "context!!");
        return context.getResources().getDimensionPixelSize(i2);
    }

    public static final float styledDimen(@NotNull View view, @AttrRes int i2) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Context context = view.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        return styledDimen(context, i2);
    }

    public static final int styledDimenPxOffset(@NotNull View view, @AttrRes int i2) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Context context = view.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        return styledDimenPxOffset(context, i2);
    }

    public static final int styledDimenPxSize(@NotNull View view, @AttrRes int i2) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Context context = view.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        return styledDimenPxSize(context, i2);
    }

    public static final float styledDimen(@NotNull Context context, @AttrRes int i2) {
        float fComplexToDimension;
        Intrinsics.checkNotNullParameter(context, "<this>");
        if (MainThreadKt.mainThread == Thread.currentThread()) {
            Resources.Theme theme = context.getTheme();
            TypedValue typedValue = StyledAttributesKt.uiThreadConfinedCachedTypedValue;
            if (theme.resolveAttribute(i2, typedValue, true)) {
                checkOfDimensionType(typedValue);
                return TypedValue.complexToDimension(typedValue.data, context.getResources().getDisplayMetrics());
            }
            throw new Resources.NotFoundException("Couldn't resolve attribute resource #0x" + ((Object) Integer.toHexString(i2)) + " from the theme of this Context.");
        }
        TypedValue typedValue2 = StyledAttributesKt.cachedTypeValue;
        synchronized (typedValue2) {
            if (context.getTheme().resolveAttribute(i2, typedValue2, true)) {
                checkOfDimensionType(typedValue2);
                fComplexToDimension = TypedValue.complexToDimension(typedValue2.data, context.getResources().getDisplayMetrics());
            } else {
                throw new Resources.NotFoundException("Couldn't resolve attribute resource #0x" + ((Object) Integer.toHexString(i2)) + " from the theme of this Context.");
            }
        }
        return fComplexToDimension;
    }

    public static final int styledDimenPxOffset(@NotNull Context context, @AttrRes int i2) {
        int iComplexToDimensionPixelOffset;
        Intrinsics.checkNotNullParameter(context, "<this>");
        if (MainThreadKt.mainThread == Thread.currentThread()) {
            Resources.Theme theme = context.getTheme();
            TypedValue typedValue = StyledAttributesKt.uiThreadConfinedCachedTypedValue;
            if (theme.resolveAttribute(i2, typedValue, true)) {
                checkOfDimensionType(typedValue);
                return TypedValue.complexToDimensionPixelOffset(typedValue.data, context.getResources().getDisplayMetrics());
            }
            throw new Resources.NotFoundException("Couldn't resolve attribute resource #0x" + ((Object) Integer.toHexString(i2)) + " from the theme of this Context.");
        }
        TypedValue typedValue2 = StyledAttributesKt.cachedTypeValue;
        synchronized (typedValue2) {
            if (context.getTheme().resolveAttribute(i2, typedValue2, true)) {
                checkOfDimensionType(typedValue2);
                iComplexToDimensionPixelOffset = TypedValue.complexToDimensionPixelOffset(typedValue2.data, context.getResources().getDisplayMetrics());
            } else {
                throw new Resources.NotFoundException("Couldn't resolve attribute resource #0x" + ((Object) Integer.toHexString(i2)) + " from the theme of this Context.");
            }
        }
        return iComplexToDimensionPixelOffset;
    }

    public static final int styledDimenPxSize(@NotNull Context context, @AttrRes int i2) {
        int iComplexToDimensionPixelSize;
        Intrinsics.checkNotNullParameter(context, "<this>");
        if (MainThreadKt.mainThread == Thread.currentThread()) {
            Resources.Theme theme = context.getTheme();
            TypedValue typedValue = StyledAttributesKt.uiThreadConfinedCachedTypedValue;
            if (theme.resolveAttribute(i2, typedValue, true)) {
                checkOfDimensionType(typedValue);
                return TypedValue.complexToDimensionPixelSize(typedValue.data, context.getResources().getDisplayMetrics());
            }
            throw new Resources.NotFoundException("Couldn't resolve attribute resource #0x" + ((Object) Integer.toHexString(i2)) + " from the theme of this Context.");
        }
        TypedValue typedValue2 = StyledAttributesKt.cachedTypeValue;
        synchronized (typedValue2) {
            if (context.getTheme().resolveAttribute(i2, typedValue2, true)) {
                checkOfDimensionType(typedValue2);
                iComplexToDimensionPixelSize = TypedValue.complexToDimensionPixelSize(typedValue2.data, context.getResources().getDisplayMetrics());
            } else {
                throw new Resources.NotFoundException("Couldn't resolve attribute resource #0x" + ((Object) Integer.toHexString(i2)) + " from the theme of this Context.");
            }
        }
        return iComplexToDimensionPixelSize;
    }

    public static final float dimen(@NotNull View view, @DimenRes int i2) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Context context = view.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        return context.getResources().getDimension(i2);
    }

    public static final int dimenPxOffset(@NotNull View view, @DimenRes int i2) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Context context = view.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        return context.getResources().getDimensionPixelOffset(i2);
    }

    public static final int dimenPxSize(@NotNull View view, @DimenRes int i2) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Context context = view.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        return context.getResources().getDimensionPixelSize(i2);
    }
}
