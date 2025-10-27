package com.hyphenate.easeui.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import androidx.core.content.ContextCompat;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.SdkConstant;

/* loaded from: classes4.dex */
public class DarkModeHelper {
    public static boolean isDarkMode(Context context) {
        return context.getSharedPreferences(SdkConstant.UMENG_ALIS, 0).getInt(CommonParameter.SkinMananer, 0) == 1;
    }

    public static void setDarkModeDrawable(ImageView imageView, int i2) {
        Drawable drawable = ContextCompat.getDrawable(imageView.getContext(), i2);
        if (drawable != null) {
            drawable.setColorFilter(Color.parseColor("#7380A9"), PorterDuff.Mode.SRC_IN);
            imageView.setImageDrawable(drawable);
        }
    }

    public static Drawable transform2DarkModeDrawable(Context context, int i2) {
        Drawable drawable = ContextCompat.getDrawable(context, i2);
        drawable.setColorFilter(Color.parseColor("#7380A9"), PorterDuff.Mode.SRC_IN);
        return drawable;
    }
}
