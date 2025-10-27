package com.google.android.gms.common.internal;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.Button;
import androidx.core.graphics.drawable.DrawableCompat;
import com.google.android.gms.common.util.DeviceProperties;

/* loaded from: classes3.dex */
public final class SignInButtonImpl extends Button {
    public SignInButtonImpl(Context context) {
        this(context, null);
    }

    private static int zaa(int i2, int i3, int i4, int i5) {
        if (i2 == 0) {
            return i3;
        }
        if (i2 == 1) {
            return i4;
        }
        if (i2 == 2) {
            return i5;
        }
        StringBuilder sb = new StringBuilder(33);
        sb.append("Unknown color scheme: ");
        sb.append(i2);
        throw new IllegalStateException(sb.toString());
    }

    public final void configure(Resources resources, SignInButtonConfig signInButtonConfig) {
        configure(resources, signInButtonConfig.getButtonSize(), signInButtonConfig.getColorScheme());
    }

    public SignInButtonImpl(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, R.attr.buttonStyle);
    }

    public final void configure(Resources resources, int i2, int i3) {
        setTypeface(Typeface.DEFAULT_BOLD);
        setTextSize(14.0f);
        int i4 = (int) ((resources.getDisplayMetrics().density * 48.0f) + 0.5f);
        setMinHeight(i4);
        setMinWidth(i4);
        int i5 = com.google.android.gms.base.R.drawable.common_google_signin_btn_icon_dark;
        int i6 = com.google.android.gms.base.R.drawable.common_google_signin_btn_icon_light;
        int iZaa = zaa(i3, i5, i6, i6);
        int i7 = com.google.android.gms.base.R.drawable.common_google_signin_btn_text_dark;
        int i8 = com.google.android.gms.base.R.drawable.common_google_signin_btn_text_light;
        int iZaa2 = zaa(i3, i7, i8, i8);
        if (i2 == 0 || i2 == 1) {
            iZaa = iZaa2;
        } else if (i2 != 2) {
            StringBuilder sb = new StringBuilder(32);
            sb.append("Unknown button size: ");
            sb.append(i2);
            throw new IllegalStateException(sb.toString());
        }
        Drawable drawableWrap = DrawableCompat.wrap(resources.getDrawable(iZaa));
        DrawableCompat.setTintList(drawableWrap, resources.getColorStateList(com.google.android.gms.base.R.color.common_google_signin_btn_tint));
        DrawableCompat.setTintMode(drawableWrap, PorterDuff.Mode.SRC_ATOP);
        setBackgroundDrawable(drawableWrap);
        int i9 = com.google.android.gms.base.R.color.common_google_signin_btn_text_dark;
        int i10 = com.google.android.gms.base.R.color.common_google_signin_btn_text_light;
        setTextColor((ColorStateList) Preconditions.checkNotNull(resources.getColorStateList(zaa(i3, i9, i10, i10))));
        if (i2 == 0) {
            setText(resources.getString(com.google.android.gms.base.R.string.common_signin_button_text));
        } else if (i2 == 1) {
            setText(resources.getString(com.google.android.gms.base.R.string.common_signin_button_text_long));
        } else {
            if (i2 != 2) {
                StringBuilder sb2 = new StringBuilder(32);
                sb2.append("Unknown button size: ");
                sb2.append(i2);
                throw new IllegalStateException(sb2.toString());
            }
            setText((CharSequence) null);
        }
        setTransformationMethod(null);
        if (DeviceProperties.isWearable(getContext())) {
            setGravity(19);
        }
    }
}
