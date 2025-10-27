package com.beizi.fusion.g;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.View;
import com.github.liuyueyi.quick.transfer.dictionary.DictionaryFactory;

/* loaded from: classes2.dex */
public class ap {
    @SuppressLint({"NewApi"})
    public static void a(View view, String str, int i2, String str2, int i3) {
        view.setBackground(a(str, i2, str2, i3));
    }

    private static Drawable a(String str, int i2, String str2, int i3) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(0);
        if (!TextUtils.isEmpty(str) && str.startsWith(DictionaryFactory.SHARP)) {
            gradientDrawable.setColor(Color.parseColor(str));
        } else {
            gradientDrawable.setColor(Color.parseColor("#00000000"));
        }
        if (i3 > 0) {
            gradientDrawable.setCornerRadius(i3);
        }
        if (i2 > 0 && !TextUtils.isEmpty(str2) && str2.startsWith(DictionaryFactory.SHARP)) {
            gradientDrawable.setStroke(i2, Color.parseColor(str2));
        }
        return gradientDrawable;
    }
}
