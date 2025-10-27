package com.noober.background.common;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.github.liuyueyi.quick.transfer.dictionary.DictionaryFactory;

/* loaded from: classes4.dex */
public class ResourceUtils {
    public static int getColor(Context context, String str) {
        Resources resources = context.getResources();
        if (str.startsWith(DictionaryFactory.SHARP)) {
            return Color.parseColor(str);
        }
        int identifier = resources.getIdentifier(str, "color", context.getPackageName());
        if (identifier == 0) {
            return -1;
        }
        return ContextCompat.getColor(context, identifier);
    }

    @Nullable
    public static Drawable getDrawable(Context context, String str) {
        Resources resources = context.getResources();
        if (str.startsWith(DictionaryFactory.SHARP)) {
            return new ColorDrawable(Color.parseColor(str));
        }
        int identifier = resources.getIdentifier(str, "drawable", context.getPackageName());
        if (identifier == 0) {
            identifier = resources.getIdentifier(str, "mipmap", context.getPackageName());
        }
        if (identifier == 0) {
            identifier = resources.getIdentifier(str, "color", context.getPackageName());
        }
        if (identifier == 0) {
            return null;
        }
        return ContextCompat.getDrawable(context, identifier);
    }
}
