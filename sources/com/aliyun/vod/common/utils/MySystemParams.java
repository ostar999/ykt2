package com.aliyun.vod.common.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/* loaded from: classes2.dex */
public class MySystemParams {
    public static final int SCREEN_ORIENTATION_HORIZONTAL = 2;
    public static final int SCREEN_ORIENTATION_VERTICAL = 1;
    private static MySystemParams params;
    private final String TAG = "SystemParams";
    public int densityDpi;
    public float fontScale;
    public float scale;
    public int screenHeight;
    public int screenOrientation;
    public int screenWidth;

    private MySystemParams() {
    }

    public static MySystemParams getInstance() {
        if (params == null) {
            params = new MySystemParams();
        }
        return params;
    }

    public void init(Context context) {
        DisplayMetrics displayMetrics = context.getApplicationContext().getResources().getDisplayMetrics();
        int i2 = displayMetrics.widthPixels;
        this.screenWidth = i2;
        int i3 = displayMetrics.heightPixels;
        this.screenHeight = i3;
        this.densityDpi = displayMetrics.densityDpi;
        this.scale = displayMetrics.density;
        this.fontScale = displayMetrics.scaledDensity;
        if (i3 > i2) {
            this.screenOrientation = 1;
        } else {
            this.screenOrientation = 2;
        }
    }
}
