package android.util;

import android.content.AppCtxKt;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;
import androidx.annotation.AttrRes;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.fragment.app.Fragment;
import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;
import org.jetbrains.annotations.NotNull;
import splitties.exceptions.ExceptionsKt;
import splitties.mainthread.MainThreadKt;

@Metadata(d1 = {"\u0000\"\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\u0013\u0010\u0000\u001a\u00020\u00012\b\b\u0001\u0010\u0002\u001a\u00020\u0001H\u0086\b\u001a\u0013\u0010\u0003\u001a\u00020\u00042\b\b\u0001\u0010\u0002\u001a\u00020\u0001H\u0086\b\u001a\u0013\u0010\u0005\u001a\u00020\u00012\b\b\u0001\u0010\u0006\u001a\u00020\u0001H\u0086\b\u001a\u0013\u0010\u0007\u001a\u00020\u00042\b\b\u0001\u0010\u0006\u001a\u00020\u0001H\u0086\b\u001a\u0016\u0010\b\u001a\u00020\u0001*\u00020\t2\b\b\u0001\u0010\u0002\u001a\u00020\u0001H\u0007\u001a\u0017\u0010\b\u001a\u00020\u0001*\u00020\n2\b\b\u0001\u0010\u0002\u001a\u00020\u0001H\u0086\b\u001a\u0017\u0010\b\u001a\u00020\u0001*\u00020\u000b2\b\b\u0001\u0010\u0002\u001a\u00020\u0001H\u0086\b\u001a\u0014\u0010\f\u001a\u00020\u0004*\u00020\t2\b\b\u0001\u0010\u0002\u001a\u00020\u0001\u001a\u0017\u0010\f\u001a\u00020\u0004*\u00020\n2\b\b\u0001\u0010\u0002\u001a\u00020\u0001H\u0086\b\u001a\u0017\u0010\f\u001a\u00020\u0004*\u00020\u000b2\b\b\u0001\u0010\u0002\u001a\u00020\u0001H\u0086\b\u001a\u0016\u0010\r\u001a\u00020\u0001*\u00020\t2\b\b\u0001\u0010\u0006\u001a\u00020\u0001H\u0007\u001a\u0017\u0010\r\u001a\u00020\u0001*\u00020\n2\b\b\u0001\u0010\u0006\u001a\u00020\u0001H\u0086\b\u001a\u0017\u0010\r\u001a\u00020\u0001*\u00020\u000b2\b\b\u0001\u0010\u0006\u001a\u00020\u0001H\u0086\b\u001a\u0014\u0010\u000e\u001a\u00020\u0004*\u00020\t2\b\b\u0001\u0010\u0006\u001a\u00020\u0001\u001a\u0017\u0010\u000e\u001a\u00020\u0004*\u00020\n2\b\b\u0001\u0010\u0006\u001a\u00020\u0001H\u0086\b\u001a\u0017\u0010\u000e\u001a\u00020\u0004*\u00020\u000b2\b\b\u0001\u0010\u0006\u001a\u00020\u0001H\u0086\b¨\u0006\u000f"}, d2 = {"appColor", "", "colorRes", "appColorSL", "Landroid/content/res/ColorStateList;", "appStyledColor", "attr", "appStyledColorSL", "color", "Landroid/content/Context;", "Landroid/view/View;", "Landroidx/fragment/app/Fragment;", "colorSL", "styledColor", "styledColorSL", "splitties-resources_release"}, k = 2, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes9.dex */
public final class ColorResourcesKt {
    public static final int appColor(@ColorRes int i2) {
        return color(AppCtxKt.getAppCtx(), i2);
    }

    @NotNull
    public static final ColorStateList appColorSL(@ColorRes int i2) {
        return colorSL(AppCtxKt.getAppCtx(), i2);
    }

    public static final int appStyledColor(@AttrRes int i2) {
        return styledColor(AppCtxKt.getAppCtx(), i2);
    }

    @NotNull
    public static final ColorStateList appStyledColorSL(@AttrRes int i2) {
        return styledColorSL(AppCtxKt.getAppCtx(), i2);
    }

    @ColorInt
    public static final int color(@NotNull Context context, @ColorRes int i2) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        return context.getColor(i2);
    }

    @NotNull
    public static final ColorStateList colorSL(@NotNull Context context, @ColorRes int i2) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        ColorStateList colorStateList = context.getColorStateList(i2);
        Intrinsics.checkNotNullExpressionValue(colorStateList, "getColorStateList(colorRes)");
        return colorStateList;
    }

    public static final int styledColor(@NotNull Fragment fragment, @AttrRes int i2) {
        Intrinsics.checkNotNullParameter(fragment, "<this>");
        Context context = fragment.getContext();
        Intrinsics.checkNotNull(context);
        Intrinsics.checkNotNullExpressionValue(context, "context!!");
        return styledColor(context, i2);
    }

    @NotNull
    public static final ColorStateList styledColorSL(@NotNull Fragment fragment, @AttrRes int i2) {
        Intrinsics.checkNotNullParameter(fragment, "<this>");
        Context context = fragment.getContext();
        Intrinsics.checkNotNull(context);
        Intrinsics.checkNotNullExpressionValue(context, "context!!");
        return styledColorSL(context, i2);
    }

    public static final int color(@NotNull Fragment fragment, @ColorRes int i2) {
        Intrinsics.checkNotNullParameter(fragment, "<this>");
        Context context = fragment.getContext();
        Intrinsics.checkNotNull(context);
        Intrinsics.checkNotNullExpressionValue(context, "context!!");
        return color(context, i2);
    }

    @NotNull
    public static final ColorStateList colorSL(@NotNull Fragment fragment, @ColorRes int i2) {
        Intrinsics.checkNotNullParameter(fragment, "<this>");
        Context context = fragment.getContext();
        Intrinsics.checkNotNull(context);
        Intrinsics.checkNotNullExpressionValue(context, "context!!");
        return colorSL(context, i2);
    }

    public static final int styledColor(@NotNull View view, @AttrRes int i2) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Context context = view.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        return styledColor(context, i2);
    }

    @NotNull
    public static final ColorStateList styledColorSL(@NotNull View view, @AttrRes int i2) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Context context = view.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        return styledColorSL(context, i2);
    }

    public static final int color(@NotNull View view, @ColorRes int i2) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Context context = view.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        return color(context, i2);
    }

    @NotNull
    public static final ColorStateList colorSL(@NotNull View view, @ColorRes int i2) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Context context = view.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        return colorSL(context, i2);
    }

    @ColorInt
    public static final int styledColor(@NotNull Context context, @AttrRes int i2) {
        int iColor;
        Intrinsics.checkNotNullParameter(context, "<this>");
        if (MainThreadKt.mainThread == Thread.currentThread()) {
            Resources.Theme theme = context.getTheme();
            TypedValue typedValue = StyledAttributesKt.uiThreadConfinedCachedTypedValue;
            if (theme.resolveAttribute(i2, typedValue, true)) {
                int i3 = typedValue.type;
                if (28 <= i3 && i3 <= 31) {
                    return typedValue.data;
                }
                if (i3 == 3) {
                    CharSequence string = typedValue.string;
                    Intrinsics.checkNotNullExpressionValue(string, "string");
                    if (StringsKt__StringsKt.startsWith$default(string, (CharSequence) "res/color/", false, 2, (Object) null)) {
                        return color(context, typedValue.resourceId);
                    }
                }
                ExceptionsKt.illegalArg(StyledAttributesKt.unexpectedThemeAttributeTypeErrorMessage(typedValue, "color"));
                throw new KotlinNothingValueException();
            }
            throw new Resources.NotFoundException("Couldn't resolve attribute resource #0x" + ((Object) Integer.toHexString(i2)) + " from the theme of this Context.");
        }
        TypedValue typedValue2 = StyledAttributesKt.cachedTypeValue;
        synchronized (typedValue2) {
            if (context.getTheme().resolveAttribute(i2, typedValue2, true)) {
                int i4 = typedValue2.type;
                if (28 > i4 || i4 > 31) {
                    z = false;
                }
                if (!z) {
                    if (i4 == 3) {
                        CharSequence string2 = typedValue2.string;
                        Intrinsics.checkNotNullExpressionValue(string2, "string");
                        if (StringsKt__StringsKt.startsWith$default(string2, (CharSequence) "res/color/", false, 2, (Object) null)) {
                            iColor = color(context, typedValue2.resourceId);
                        }
                    }
                    ExceptionsKt.illegalArg(StyledAttributesKt.unexpectedThemeAttributeTypeErrorMessage(typedValue2, "color"));
                    throw new KotlinNothingValueException();
                }
                iColor = typedValue2.data;
            } else {
                throw new Resources.NotFoundException("Couldn't resolve attribute resource #0x" + ((Object) Integer.toHexString(i2)) + " from the theme of this Context.");
            }
        }
        return iColor;
    }

    @NotNull
    public static final ColorStateList styledColorSL(@NotNull Context context, @AttrRes int i2) {
        ColorStateList colorStateListColorSL;
        Intrinsics.checkNotNullParameter(context, "<this>");
        if (MainThreadKt.mainThread == Thread.currentThread()) {
            Resources.Theme theme = context.getTheme();
            TypedValue typedValue = StyledAttributesKt.uiThreadConfinedCachedTypedValue;
            if (theme.resolveAttribute(i2, typedValue, true)) {
                int i3 = typedValue.type;
                if (28 <= i3 && i3 <= 31) {
                    colorStateListColorSL = ColorStateList.valueOf(typedValue.data);
                } else {
                    if (i3 == 3) {
                        CharSequence string = typedValue.string;
                        Intrinsics.checkNotNullExpressionValue(string, "string");
                        if (StringsKt__StringsKt.startsWith$default(string, (CharSequence) "res/color/", false, 2, (Object) null)) {
                            colorStateListColorSL = colorSL(context, typedValue.resourceId);
                        }
                    }
                    ExceptionsKt.illegalArg(StyledAttributesKt.unexpectedThemeAttributeTypeErrorMessage(typedValue, "color"));
                    throw new KotlinNothingValueException();
                }
            } else {
                throw new Resources.NotFoundException("Couldn't resolve attribute resource #0x" + ((Object) Integer.toHexString(i2)) + " from the theme of this Context.");
            }
        } else {
            TypedValue typedValue2 = StyledAttributesKt.cachedTypeValue;
            synchronized (typedValue2) {
                if (context.getTheme().resolveAttribute(i2, typedValue2, true)) {
                    int i4 = typedValue2.type;
                    if (28 > i4 || i4 > 31) {
                        z = false;
                    }
                    if (z) {
                        colorStateListColorSL = ColorStateList.valueOf(typedValue2.data);
                    } else {
                        if (i4 == 3) {
                            CharSequence string2 = typedValue2.string;
                            Intrinsics.checkNotNullExpressionValue(string2, "string");
                            if (StringsKt__StringsKt.startsWith$default(string2, (CharSequence) "res/color/", false, 2, (Object) null)) {
                                colorStateListColorSL = colorSL(context, typedValue2.resourceId);
                            }
                        }
                        ExceptionsKt.illegalArg(StyledAttributesKt.unexpectedThemeAttributeTypeErrorMessage(typedValue2, "color"));
                        throw new KotlinNothingValueException();
                    }
                } else {
                    throw new Resources.NotFoundException("Couldn't resolve attribute resource #0x" + ((Object) Integer.toHexString(i2)) + " from the theme of this Context.");
                }
            }
        }
        Intrinsics.checkNotNullExpressionValue(colorStateListColorSL, "withResolvedThemeAttribute(attr) {\n    if (type in TypedValue.TYPE_FIRST_COLOR_INT..TypedValue.TYPE_LAST_COLOR_INT) {\n        ColorStateList.valueOf(data)\n    } else if (type == TypedValue.TYPE_STRING && string.startsWith(\"res/color/\")) {\n        colorSL(resourceId)\n    } else {\n        illegalArg(errorMessage = unexpectedThemeAttributeTypeErrorMessage(expectedKind = \"color\"))\n    }\n}");
        return colorStateListColorSL;
    }
}
