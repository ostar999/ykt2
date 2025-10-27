package com.luck.picture.lib.immersive;

import android.app.Activity;
import android.view.Window;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

/* loaded from: classes4.dex */
public class NavBarUtils {
    public static void setNavBarColor(@NonNull Activity activity, @ColorInt int i2) {
        setNavBarColor(activity.getWindow(), i2);
    }

    public static void setNavBarColor(@NonNull Window window, @ColorInt int i2) {
        window.setNavigationBarColor(i2);
    }
}
