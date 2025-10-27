package com.uuzuche.lib_zxing.activity;

import android.content.Context;
import android.util.DisplayMetrics;
import com.uuzuche.lib_zxing.DisplayUtil;

/* loaded from: classes6.dex */
public class ZXingLibrary {
    public static void initDisplayOpinion(Context context) {
        if (context == null) {
            return;
        }
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        DisplayUtil.density = displayMetrics.density;
        DisplayUtil.densityDPI = displayMetrics.densityDpi;
        DisplayUtil.screenWidthPx = displayMetrics.widthPixels;
        DisplayUtil.screenhightPx = displayMetrics.heightPixels;
        DisplayUtil.screenWidthDip = DisplayUtil.px2dip(context, r1);
        DisplayUtil.screenHightDip = DisplayUtil.px2dip(context, displayMetrics.heightPixels);
    }
}
