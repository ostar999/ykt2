package android.util;

import android.content.AppCtxKt;
import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;
import androidx.annotation.ArrayRes;
import androidx.annotation.AttrRes;
import androidx.annotation.BoolRes;
import androidx.annotation.IntegerRes;
import androidx.fragment.app.Fragment;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import splitties.mainthread.MainThreadKt;

@Metadata(d1 = {"\u0000(\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0015\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a\u0013\u0010\u0000\u001a\u00020\u00012\b\b\u0001\u0010\u0002\u001a\u00020\u0003H\u0086\b\u001a\u0013\u0010\u0004\u001a\u00020\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0003H\u0086\b\u001a\u0013\u0010\u0006\u001a\u00020\u00072\b\b\u0001\u0010\b\u001a\u00020\u0003H\u0086\b\u001a\u0013\u0010\t\u001a\u00020\u00012\b\b\u0001\u0010\n\u001a\u00020\u0003H\u0086\b\u001a\u0013\u0010\u000b\u001a\u00020\u00032\b\b\u0001\u0010\n\u001a\u00020\u0003H\u0086\b\u001a\u0017\u0010\f\u001a\u00020\u0001*\u00020\r2\b\b\u0001\u0010\u0002\u001a\u00020\u0003H\u0086\b\u001a\u0017\u0010\f\u001a\u00020\u0001*\u00020\u000e2\b\b\u0001\u0010\u0002\u001a\u00020\u0003H\u0086\b\u001a\u0017\u0010\f\u001a\u00020\u0001*\u00020\u000f2\b\b\u0001\u0010\u0002\u001a\u00020\u0003H\u0086\b\u001a\u0017\u0010\u0010\u001a\u00020\u0003*\u00020\r2\b\b\u0001\u0010\u0005\u001a\u00020\u0003H\u0086\b\u001a\u0017\u0010\u0010\u001a\u00020\u0003*\u00020\u000e2\b\b\u0001\u0010\u0005\u001a\u00020\u0003H\u0086\b\u001a\u0017\u0010\u0010\u001a\u00020\u0003*\u00020\u000f2\b\b\u0001\u0010\u0005\u001a\u00020\u0003H\u0086\b\u001a\u0017\u0010\u0011\u001a\u00020\u0007*\u00020\r2\b\b\u0001\u0010\b\u001a\u00020\u0003H\u0086\b\u001a\u0017\u0010\u0011\u001a\u00020\u0007*\u00020\u000e2\b\b\u0001\u0010\b\u001a\u00020\u0003H\u0086\b\u001a\u0017\u0010\u0011\u001a\u00020\u0007*\u00020\u000f2\b\b\u0001\u0010\b\u001a\u00020\u0003H\u0086\b\u001a\u0014\u0010\u0012\u001a\u00020\u0001*\u00020\r2\b\b\u0001\u0010\n\u001a\u00020\u0003\u001a\u0017\u0010\u0012\u001a\u00020\u0001*\u00020\u000e2\b\b\u0001\u0010\n\u001a\u00020\u0003H\u0086\b\u001a\u0017\u0010\u0012\u001a\u00020\u0001*\u00020\u000f2\b\b\u0001\u0010\n\u001a\u00020\u0003H\u0086\b\u001a\u0014\u0010\u0013\u001a\u00020\u0003*\u00020\r2\b\b\u0001\u0010\n\u001a\u00020\u0003\u001a\u0017\u0010\u0013\u001a\u00020\u0003*\u00020\u000e2\b\b\u0001\u0010\n\u001a\u00020\u0003H\u0086\b\u001a\u0017\u0010\u0013\u001a\u00020\u0003*\u00020\u000f2\b\b\u0001\u0010\n\u001a\u00020\u0003H\u0086\b¨\u0006\u0014"}, d2 = {"appBool", "", "boolResId", "", "appInt", "intResId", "appIntArray", "", "intArrayResId", "appStyledBool", "attr", "appStyledInt", "bool", "Landroid/content/Context;", "Landroid/view/View;", "Landroidx/fragment/app/Fragment;", "int", "intArray", "styledBool", "styledInt", "splitties-resources_release"}, k = 2, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes9.dex */
public final class PrimitiveResourcesKt {
    public static final boolean appBool(@BoolRes int i2) {
        return AppCtxKt.getAppCtx().getResources().getBoolean(i2);
    }

    public static final int appInt(@IntegerRes int i2) {
        return AppCtxKt.getAppCtx().getResources().getInteger(i2);
    }

    @NotNull
    public static final int[] appIntArray(@ArrayRes int i2) throws Resources.NotFoundException {
        int[] intArray = AppCtxKt.getAppCtx().getResources().getIntArray(i2);
        Intrinsics.checkNotNullExpressionValue(intArray, "resources.getIntArray(intArrayResId)");
        return intArray;
    }

    public static final boolean appStyledBool(@AttrRes int i2) {
        return styledBool(AppCtxKt.getAppCtx(), i2);
    }

    public static final int appStyledInt(@AttrRes int i2) {
        return styledInt(AppCtxKt.getAppCtx(), i2);
    }

    public static final boolean bool(@NotNull Context context, @BoolRes int i2) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        return context.getResources().getBoolean(i2);
    }

    /* renamed from: int, reason: not valid java name */
    public static final int m2582int(@NotNull Context context, @IntegerRes int i2) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        return context.getResources().getInteger(i2);
    }

    @NotNull
    public static final int[] intArray(@NotNull Context context, @ArrayRes int i2) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(context, "<this>");
        int[] intArray = context.getResources().getIntArray(i2);
        Intrinsics.checkNotNullExpressionValue(intArray, "resources.getIntArray(intArrayResId)");
        return intArray;
    }

    public static final boolean styledBool(@NotNull Fragment fragment, @AttrRes int i2) {
        Intrinsics.checkNotNullParameter(fragment, "<this>");
        Context context = fragment.getContext();
        Intrinsics.checkNotNull(context);
        Intrinsics.checkNotNullExpressionValue(context, "context!!");
        return styledBool(context, i2);
    }

    public static final int styledInt(@NotNull Fragment fragment, @AttrRes int i2) {
        Intrinsics.checkNotNullParameter(fragment, "<this>");
        Context context = fragment.getContext();
        Intrinsics.checkNotNull(context);
        Intrinsics.checkNotNullExpressionValue(context, "context!!");
        return styledInt(context, i2);
    }

    public static final boolean bool(@NotNull Fragment fragment, @BoolRes int i2) {
        Intrinsics.checkNotNullParameter(fragment, "<this>");
        Context context = fragment.getContext();
        Intrinsics.checkNotNull(context);
        Intrinsics.checkNotNullExpressionValue(context, "context!!");
        return context.getResources().getBoolean(i2);
    }

    /* renamed from: int, reason: not valid java name */
    public static final int m2584int(@NotNull Fragment fragment, @IntegerRes int i2) {
        Intrinsics.checkNotNullParameter(fragment, "<this>");
        Context context = fragment.getContext();
        Intrinsics.checkNotNull(context);
        Intrinsics.checkNotNullExpressionValue(context, "context!!");
        return context.getResources().getInteger(i2);
    }

    @NotNull
    public static final int[] intArray(@NotNull Fragment fragment, @ArrayRes int i2) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(fragment, "<this>");
        Context context = fragment.getContext();
        Intrinsics.checkNotNull(context);
        Intrinsics.checkNotNullExpressionValue(context, "context!!");
        int[] intArray = context.getResources().getIntArray(i2);
        Intrinsics.checkNotNullExpressionValue(intArray, "resources.getIntArray(intArrayResId)");
        return intArray;
    }

    public static final boolean styledBool(@NotNull View view, @AttrRes int i2) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Context context = view.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        return styledBool(context, i2);
    }

    public static final int styledInt(@NotNull View view, @AttrRes int i2) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Context context = view.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        return styledInt(context, i2);
    }

    public static final boolean styledBool(@NotNull Context context, @AttrRes int i2) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        boolean z2 = false;
        if (MainThreadKt.mainThread == Thread.currentThread()) {
            Resources.Theme theme = context.getTheme();
            TypedValue typedValue = StyledAttributesKt.uiThreadConfinedCachedTypedValue;
            if (theme.resolveAttribute(i2, typedValue, true)) {
                if (typedValue.type == 18) {
                    int i3 = typedValue.data;
                    if (i3 == 0) {
                        return false;
                    }
                    if (i3 == 1) {
                        return true;
                    }
                    throw new IllegalStateException(Intrinsics.stringPlus("Expected 0 or 1 but got ", Integer.valueOf(i3)).toString());
                }
                throw new IllegalArgumentException(StyledAttributesKt.unexpectedThemeAttributeTypeErrorMessage(typedValue, "bool").toString());
            }
            throw new Resources.NotFoundException("Couldn't resolve attribute resource #0x" + ((Object) Integer.toHexString(i2)) + " from the theme of this Context.");
        }
        TypedValue typedValue2 = StyledAttributesKt.cachedTypeValue;
        synchronized (typedValue2) {
            if (context.getTheme().resolveAttribute(i2, typedValue2, true)) {
                if (typedValue2.type == 18) {
                    int i4 = typedValue2.data;
                    if (i4 != 0) {
                        if (i4 != 1) {
                            throw new IllegalStateException(Intrinsics.stringPlus("Expected 0 or 1 but got ", Integer.valueOf(i4)).toString());
                        }
                        z2 = true;
                    }
                } else {
                    throw new IllegalArgumentException(StyledAttributesKt.unexpectedThemeAttributeTypeErrorMessage(typedValue2, "bool").toString());
                }
            } else {
                throw new Resources.NotFoundException("Couldn't resolve attribute resource #0x" + ((Object) Integer.toHexString(i2)) + " from the theme of this Context.");
            }
        }
        return z2;
    }

    public static final int styledInt(@NotNull Context context, @AttrRes int i2) {
        int i3;
        Intrinsics.checkNotNullParameter(context, "<this>");
        if (MainThreadKt.mainThread == Thread.currentThread()) {
            Resources.Theme theme = context.getTheme();
            TypedValue typedValue = StyledAttributesKt.uiThreadConfinedCachedTypedValue;
            if (theme.resolveAttribute(i2, typedValue, true)) {
                int i4 = typedValue.type;
                if (i4 == 16 || i4 == 17) {
                    return typedValue.data;
                }
                throw new IllegalArgumentException(StyledAttributesKt.unexpectedThemeAttributeTypeErrorMessage(typedValue, "int").toString());
            }
            throw new Resources.NotFoundException("Couldn't resolve attribute resource #0x" + ((Object) Integer.toHexString(i2)) + " from the theme of this Context.");
        }
        TypedValue typedValue2 = StyledAttributesKt.cachedTypeValue;
        synchronized (typedValue2) {
            if (context.getTheme().resolveAttribute(i2, typedValue2, true)) {
                int i5 = typedValue2.type;
                if (i5 == 16 || i5 == 17) {
                    i3 = typedValue2.data;
                } else {
                    throw new IllegalArgumentException(StyledAttributesKt.unexpectedThemeAttributeTypeErrorMessage(typedValue2, "int").toString());
                }
            } else {
                throw new Resources.NotFoundException("Couldn't resolve attribute resource #0x" + ((Object) Integer.toHexString(i2)) + " from the theme of this Context.");
            }
        }
        return i3;
    }

    public static final boolean bool(@NotNull View view, @BoolRes int i2) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Context context = view.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        return context.getResources().getBoolean(i2);
    }

    /* renamed from: int, reason: not valid java name */
    public static final int m2583int(@NotNull View view, @IntegerRes int i2) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Context context = view.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        return context.getResources().getInteger(i2);
    }

    @NotNull
    public static final int[] intArray(@NotNull View view, @ArrayRes int i2) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Context context = view.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        int[] intArray = context.getResources().getIntArray(i2);
        Intrinsics.checkNotNullExpressionValue(intArray, "resources.getIntArray(intArrayResId)");
        return intArray;
    }
}
