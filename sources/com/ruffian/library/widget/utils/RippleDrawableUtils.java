package com.ruffian.library.widget.utils;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

/* loaded from: classes6.dex */
public class RippleDrawableUtils {
    private Drawable contentDrawable;
    private Drawable maskDrawable;

    public RippleDrawableUtils(Object obj, Object obj2) {
        this.contentDrawable = obj == null ? null : (Drawable) obj;
        this.maskDrawable = obj2 != null ? (Drawable) obj2 : null;
    }

    @RequiresApi(api = 21)
    public RippleDrawable getRippleDrawable(@NonNull ColorStateList colorStateList) {
        return new RippleDrawable(colorStateList, this.contentDrawable, this.maskDrawable);
    }
}
