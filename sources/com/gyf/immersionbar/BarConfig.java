package com.gyf.immersionbar;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import androidx.annotation.NonNull;
import com.gyf.immersionbar.GestureUtils;

/* loaded from: classes4.dex */
class BarConfig {
    private final int mActionBarHeight;
    private final boolean mHasNavigationBar;
    private final boolean mInPortrait;
    private final int mNavigationBarHeight;
    private final int mNavigationBarWidth;
    private final float mSmallestWidthDp;
    private final int mStatusBarHeight;

    public BarConfig(Activity activity) {
        this.mInPortrait = activity.getResources().getConfiguration().orientation == 1;
        this.mSmallestWidthDp = getSmallestWidthDp(activity);
        this.mStatusBarHeight = getInternalDimensionSize(activity, "status_bar_height");
        this.mActionBarHeight = getActionBarHeight(activity);
        int navigationBarHeight = getNavigationBarHeight(activity);
        this.mNavigationBarHeight = navigationBarHeight;
        this.mNavigationBarWidth = getNavigationBarWidth(activity);
        this.mHasNavigationBar = navigationBarHeight > 0;
    }

    @TargetApi(14)
    private int getActionBarHeight(Activity activity) {
        View viewFindViewById = activity.getWindow().findViewById(R.id.action_bar_container);
        int measuredHeight = viewFindViewById != null ? viewFindViewById.getMeasuredHeight() : 0;
        if (measuredHeight != 0) {
            return measuredHeight;
        }
        TypedValue typedValue = new TypedValue();
        activity.getTheme().resolveAttribute(android.R.attr.actionBarSize, typedValue, true);
        return TypedValue.complexToDimensionPixelSize(typedValue.data, activity.getResources().getDisplayMetrics());
    }

    public static int getInternalDimensionSize(Context context, String str) throws Resources.NotFoundException {
        try {
            int identifier = Resources.getSystem().getIdentifier(str, "dimen", "android");
            if (identifier > 0) {
                int dimensionPixelSize = context.getResources().getDimensionPixelSize(identifier);
                int dimensionPixelSize2 = Resources.getSystem().getDimensionPixelSize(identifier);
                if (dimensionPixelSize2 >= dimensionPixelSize && (Build.VERSION.SDK_INT < 29 || str.equals("status_bar_height"))) {
                    return dimensionPixelSize2;
                }
                float f2 = (dimensionPixelSize * Resources.getSystem().getDisplayMetrics().density) / context.getResources().getDisplayMetrics().density;
                return (int) (f2 >= 0.0f ? f2 + 0.5f : f2 - 0.5f);
            }
        } catch (Resources.NotFoundException unused) {
        }
        return 0;
    }

    @TargetApi(14)
    private int getNavigationBarHeight(Context context) {
        if (hasNavBar((Activity) context)) {
            return getNavigationBarHeightInternal(context);
        }
        return 0;
    }

    public static int getNavigationBarHeightInternal(@NonNull Context context) {
        return getInternalDimensionSize(context, context.getResources().getConfiguration().orientation == 1 ? "navigation_bar_height" : "navigation_bar_height_landscape");
    }

    @TargetApi(14)
    private int getNavigationBarWidth(Context context) {
        if (hasNavBar((Activity) context)) {
            return getInternalDimensionSize(context, "navigation_bar_width");
        }
        return 0;
    }

    public static int getNavigationBarWidthInternal(@NonNull Context context) {
        return getInternalDimensionSize(context, "navigation_bar_width");
    }

    @SuppressLint({"NewApi"})
    private float getSmallestWidthDp(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
        float f2 = displayMetrics.widthPixels;
        float f3 = displayMetrics.density;
        return Math.min(f2 / f3, displayMetrics.heightPixels / f3);
    }

    @TargetApi(14)
    private boolean hasNavBar(Activity activity) {
        GestureUtils.GestureBean gestureBean = GestureUtils.getGestureBean(activity);
        if (!gestureBean.checkNavigation && gestureBean.isGesture) {
            return false;
        }
        Display defaultDisplay = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getRealMetrics(displayMetrics);
        int i2 = displayMetrics.heightPixels;
        int i3 = displayMetrics.widthPixels;
        DisplayMetrics displayMetrics2 = new DisplayMetrics();
        defaultDisplay.getMetrics(displayMetrics2);
        return i3 - displayMetrics2.widthPixels > 0 || i2 - displayMetrics2.heightPixels > 0;
    }

    public int getStatusBarHeight() {
        return this.mStatusBarHeight;
    }

    public boolean hasNavigationBar() {
        return this.mHasNavigationBar;
    }

    public boolean isNavigationAtBottom() {
        return this.mSmallestWidthDp >= 600.0f || this.mInPortrait;
    }

    public int getNavigationBarHeight() {
        return this.mNavigationBarHeight;
    }

    public int getNavigationBarWidth() {
        return this.mNavigationBarWidth;
    }

    public int getActionBarHeight() {
        return this.mActionBarHeight;
    }
}
