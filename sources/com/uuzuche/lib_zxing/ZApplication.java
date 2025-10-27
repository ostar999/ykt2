package com.uuzuche.lib_zxing;

import android.app.Application;
import android.util.DisplayMetrics;

/* loaded from: classes6.dex */
public class ZApplication extends Application {
    private void initDisplayOpinion() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        DisplayUtil.density = displayMetrics.density;
        DisplayUtil.densityDPI = displayMetrics.densityDpi;
        DisplayUtil.screenWidthPx = displayMetrics.widthPixels;
        DisplayUtil.screenhightPx = displayMetrics.heightPixels;
        DisplayUtil.screenWidthDip = DisplayUtil.px2dip(getApplicationContext(), displayMetrics.widthPixels);
        DisplayUtil.screenHightDip = DisplayUtil.px2dip(getApplicationContext(), displayMetrics.heightPixels);
    }

    @Override // android.app.Application
    public void onCreate() {
        super.onCreate();
        initDisplayOpinion();
    }
}
