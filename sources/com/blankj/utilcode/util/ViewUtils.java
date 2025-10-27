package com.blankj.utilcode.util;

import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.LayoutRes;

/* loaded from: classes2.dex */
public class ViewUtils {
    public static void fixScrollViewTopping(View view) {
        view.setFocusable(false);
        ViewGroup viewGroup = view instanceof ViewGroup ? (ViewGroup) view : null;
        if (viewGroup == null) {
            return;
        }
        int childCount = viewGroup.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = viewGroup.getChildAt(i2);
            childAt.setFocusable(false);
            if (childAt instanceof ViewGroup) {
                fixScrollViewTopping(childAt);
            }
        }
    }

    public static boolean isLayoutRtl() {
        return TextUtils.getLayoutDirectionFromLocale(Build.VERSION.SDK_INT >= 24 ? Utils.getApp().getResources().getConfiguration().getLocales().get(0) : Utils.getApp().getResources().getConfiguration().locale) == 1;
    }

    public static View layoutId2View(@LayoutRes int i2) {
        return ((LayoutInflater) Utils.getApp().getSystemService("layout_inflater")).inflate(i2, (ViewGroup) null);
    }

    public static void runOnUiThread(Runnable runnable) {
        UtilsBridge.runOnUiThread(runnable);
    }

    public static void runOnUiThreadDelayed(Runnable runnable, long j2) {
        UtilsBridge.runOnUiThreadDelayed(runnable, j2);
    }

    public static void setViewEnabled(View view, boolean z2) {
        setViewEnabled(view, z2, null);
    }

    public static void setViewEnabled(View view, boolean z2, View... viewArr) {
        if (view == null) {
            return;
        }
        if (viewArr != null) {
            for (View view2 : viewArr) {
                if (view == view2) {
                    return;
                }
            }
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                setViewEnabled(viewGroup.getChildAt(i2), z2, viewArr);
            }
        }
        view.setEnabled(z2);
    }
}
