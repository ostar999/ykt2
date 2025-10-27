package com.psychiatrygarden.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

/* loaded from: classes6.dex */
public class MyNightUtil {
    public static final String PACKAGE_NAME = "com.example.mdpskinres";
    public static final String SKIN_DIR = "/data/data/com.psychiatrygarden/files";
    public static final String SKIN_NAME = "mdp_night_skin.skin";

    public static int getColor(Context context, int resId) {
        return context.getResources().getColor(resId);
    }

    public static Drawable getDrawable(Context context, int resId) {
        return context.getResources().getDrawable(resId);
    }
}
