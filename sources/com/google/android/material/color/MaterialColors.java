package com.google.android.material.color;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import androidx.annotation.AttrRes;
import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import com.easefun.polyv.mediasdk.player.IjkMediaMeta;
import com.google.android.material.R;
import com.google.android.material.resources.MaterialAttributes;

/* loaded from: classes3.dex */
public class MaterialColors {
    public static final float ALPHA_DISABLED = 0.38f;
    public static final float ALPHA_DISABLED_LOW = 0.12f;
    public static final float ALPHA_FULL = 1.0f;
    public static final float ALPHA_LOW = 0.32f;
    public static final float ALPHA_MEDIUM = 0.54f;
    private static final int TONE_ACCENT_CONTAINER_DARK = 20;
    private static final int TONE_ACCENT_CONTAINER_LIGHT = 90;
    private static final int TONE_ACCENT_DARK = 70;
    private static final int TONE_ACCENT_LIGHT = 40;
    private static final int TONE_ON_ACCENT_CONTAINER_DARK = 80;
    private static final int TONE_ON_ACCENT_CONTAINER_LIGHT = 10;
    private static final int TONE_ON_ACCENT_DARK = 10;
    private static final int TONE_ON_ACCENT_LIGHT = 100;

    private MaterialColors() {
    }

    @ColorInt
    public static int compositeARGBWithAlpha(@ColorInt int i2, @IntRange(from = 0, to = IjkMediaMeta.AV_CH_LAYOUT_7POINT1_WIDE_BACK) int i3) {
        return androidx.core.graphics.ColorUtils.setAlphaComponent(i2, (Color.alpha(i2) * i3) / 255);
    }

    @ColorInt
    public static int getColor(@NonNull View view, @AttrRes int i2) {
        return MaterialAttributes.resolveOrThrow(view, i2);
    }

    @ColorInt
    private static int getColorRole(@ColorInt int i2, @IntRange(from = 0, to = 100) int i3) {
        Hct hctFromInt = Hct.fromInt(i2);
        hctFromInt.setTone(i3);
        return hctFromInt.toInt();
    }

    @NonNull
    public static ColorRoles getColorRoles(@NonNull Context context, @ColorInt int i2) {
        return getColorRoles(i2, MaterialAttributes.resolveBoolean(context, R.attr.isLightTheme, true));
    }

    @ColorInt
    public static int harmonize(@ColorInt int i2, @ColorInt int i3) {
        return Blend.harmonize(i2, i3);
    }

    @ColorInt
    public static int harmonizeWithPrimary(@NonNull Context context, @ColorInt int i2) {
        return harmonize(i2, getColor(context, R.attr.colorPrimary, MaterialColors.class.getCanonicalName()));
    }

    public static boolean isColorLight(@ColorInt int i2) {
        return i2 != 0 && androidx.core.graphics.ColorUtils.calculateLuminance(i2) > 0.5d;
    }

    @ColorInt
    public static int layer(@NonNull View view, @AttrRes int i2, @AttrRes int i3) {
        return layer(view, i2, i3, 1.0f);
    }

    @ColorInt
    public static int getColor(Context context, @AttrRes int i2, String str) {
        return MaterialAttributes.resolveOrThrow(context, i2, str);
    }

    @ColorInt
    public static int layer(@NonNull View view, @AttrRes int i2, @AttrRes int i3, @FloatRange(from = 0.0d, to = 1.0d) float f2) {
        return layer(getColor(view, i2), getColor(view, i3), f2);
    }

    @ColorInt
    public static int getColor(@NonNull View view, @AttrRes int i2, @ColorInt int i3) {
        return getColor(view.getContext(), i2, i3);
    }

    @ColorInt
    public static int getColor(@NonNull Context context, @AttrRes int i2, @ColorInt int i3) {
        TypedValue typedValueResolve = MaterialAttributes.resolve(context, i2);
        return typedValueResolve != null ? typedValueResolve.data : i3;
    }

    @NonNull
    public static ColorRoles getColorRoles(@ColorInt int i2, boolean z2) {
        if (z2) {
            return new ColorRoles(getColorRole(i2, 40), getColorRole(i2, 100), getColorRole(i2, 90), getColorRole(i2, 10));
        }
        return new ColorRoles(getColorRole(i2, 70), getColorRole(i2, 10), getColorRole(i2, 20), getColorRole(i2, 80));
    }

    @ColorInt
    public static int layer(@ColorInt int i2, @ColorInt int i3, @FloatRange(from = 0.0d, to = 1.0d) float f2) {
        return layer(i2, androidx.core.graphics.ColorUtils.setAlphaComponent(i3, Math.round(Color.alpha(i3) * f2)));
    }

    @ColorInt
    public static int layer(@ColorInt int i2, @ColorInt int i3) {
        return androidx.core.graphics.ColorUtils.compositeColors(i3, i2);
    }
}
