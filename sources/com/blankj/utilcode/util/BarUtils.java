package com.blankj.utilcode.util;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.provider.Settings;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;
import androidx.drawerlayout.widget.DrawerLayout;
import com.yikaobang.yixue.R2;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public final class BarUtils {
    private static final int KEY_OFFSET = -123;
    private static final String TAG_OFFSET = "TAG_OFFSET";
    private static final String TAG_STATUS_BAR = "TAG_STATUS_BAR";

    private BarUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void addMarginTopEqualStatusBarHeight(@NonNull View view) {
        view.setTag(TAG_OFFSET);
        Object tag = view.getTag(KEY_OFFSET);
        if (tag == null || !((Boolean) tag).booleanValue()) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            marginLayoutParams.setMargins(marginLayoutParams.leftMargin, marginLayoutParams.topMargin + getStatusBarHeight(), marginLayoutParams.rightMargin, marginLayoutParams.bottomMargin);
            view.setTag(KEY_OFFSET, Boolean.TRUE);
        }
    }

    private static View applyStatusBarColor(@NonNull Activity activity, int i2, boolean z2) {
        return applyStatusBarColor(activity.getWindow(), i2, z2);
    }

    private static View createStatusBarView(@NonNull Context context, int i2) {
        View view = new View(context);
        view.setLayoutParams(new ViewGroup.LayoutParams(-1, getStatusBarHeight()));
        view.setBackgroundColor(i2);
        view.setTag(TAG_STATUS_BAR);
        return view;
    }

    public static int getActionBarHeight() {
        TypedValue typedValue = new TypedValue();
        if (Utils.getApp().getTheme().resolveAttribute(R.attr.actionBarSize, typedValue, true)) {
            return TypedValue.complexToDimensionPixelSize(typedValue.data, Utils.getApp().getResources().getDisplayMetrics());
        }
        return 0;
    }

    @RequiresApi(21)
    public static int getNavBarColor(@NonNull Activity activity) {
        return getNavBarColor(activity.getWindow());
    }

    public static int getNavBarHeight() {
        Resources system = Resources.getSystem();
        int identifier = system.getIdentifier("navigation_bar_height", "dimen", "android");
        if (identifier != 0) {
            return system.getDimensionPixelSize(identifier);
        }
        return 0;
    }

    private static String getResNameById(int i2) {
        try {
            return Utils.getApp().getResources().getResourceEntryName(i2);
        } catch (Exception unused) {
            return "";
        }
    }

    public static int getStatusBarHeight() {
        Resources system = Resources.getSystem();
        return system.getDimensionPixelSize(system.getIdentifier("status_bar_height", "dimen", "android"));
    }

    private static void hideStatusBarView(@NonNull Activity activity) {
        hideStatusBarView(activity.getWindow());
    }

    private static void invokePanels(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        try {
            Class.forName("android.app.StatusBarManager").getMethod(str, new Class[0]).invoke(Utils.getApp().getSystemService("statusbar"), new Object[0]);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static boolean isNavBarLightMode(@NonNull Activity activity) {
        return isNavBarLightMode(activity.getWindow());
    }

    public static boolean isNavBarVisible(@NonNull Activity activity) {
        return isNavBarVisible(activity.getWindow());
    }

    public static boolean isStatusBarLightMode(@NonNull Activity activity) {
        return isStatusBarLightMode(activity.getWindow());
    }

    public static boolean isStatusBarVisible(@NonNull Activity activity) {
        return (activity.getWindow().getAttributes().flags & 1024) == 0;
    }

    public static boolean isSupportNavBar() {
        WindowManager windowManager = (WindowManager) Utils.getApp().getSystemService("window");
        if (windowManager == null) {
            return false;
        }
        Display defaultDisplay = windowManager.getDefaultDisplay();
        Point point = new Point();
        Point point2 = new Point();
        defaultDisplay.getSize(point);
        defaultDisplay.getRealSize(point2);
        return (point2.y == point.y && point2.x == point.x) ? false : true;
    }

    @RequiresApi(21)
    public static void setNavBarColor(@NonNull Activity activity, @ColorInt int i2) {
        setNavBarColor(activity.getWindow(), i2);
    }

    public static void setNavBarLightMode(@NonNull Activity activity, boolean z2) {
        setNavBarLightMode(activity.getWindow(), z2);
    }

    public static void setNavBarVisibility(@NonNull Activity activity, boolean z2) {
        setNavBarVisibility(activity.getWindow(), z2);
    }

    @RequiresPermission("android.permission.EXPAND_STATUS_BAR")
    public static void setNotificationBarVisibility(boolean z2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        invokePanels(z2 ? "expandNotificationsPanel" : "collapsePanels");
    }

    public static View setStatusBarColor(@NonNull Activity activity, @ColorInt int i2) {
        return setStatusBarColor(activity, i2, false);
    }

    public static void setStatusBarColor4Drawer(@NonNull DrawerLayout drawerLayout, @NonNull View view, @ColorInt int i2) {
        setStatusBarColor4Drawer(drawerLayout, view, i2, false);
    }

    public static void setStatusBarCustom(@NonNull View view) {
        Activity activityByContext = UtilsBridge.getActivityByContext(view.getContext());
        if (activityByContext == null) {
            return;
        }
        transparentStatusBar(activityByContext);
        view.setVisibility(0);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            view.setLayoutParams(new ViewGroup.LayoutParams(-1, getStatusBarHeight()));
        } else {
            layoutParams.width = -1;
            layoutParams.height = getStatusBarHeight();
        }
    }

    public static void setStatusBarLightMode(@NonNull Activity activity, boolean z2) {
        setStatusBarLightMode(activity.getWindow(), z2);
    }

    public static void setStatusBarVisibility(@NonNull Activity activity, boolean z2) {
        setStatusBarVisibility(activity.getWindow(), z2);
    }

    private static void showStatusBarView(@NonNull Window window) {
        View viewFindViewWithTag = ((ViewGroup) window.getDecorView()).findViewWithTag(TAG_STATUS_BAR);
        if (viewFindViewWithTag == null) {
            return;
        }
        viewFindViewWithTag.setVisibility(0);
    }

    public static void subtractMarginTopEqualStatusBarHeight(@NonNull View view) {
        Object tag = view.getTag(KEY_OFFSET);
        if (tag == null || !((Boolean) tag).booleanValue()) {
            return;
        }
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        marginLayoutParams.setMargins(marginLayoutParams.leftMargin, marginLayoutParams.topMargin - getStatusBarHeight(), marginLayoutParams.rightMargin, marginLayoutParams.bottomMargin);
        view.setTag(KEY_OFFSET, Boolean.FALSE);
    }

    public static void transparentNavBar(@NonNull Activity activity) {
        transparentNavBar(activity.getWindow());
    }

    public static void transparentStatusBar(@NonNull Activity activity) {
        transparentStatusBar(activity.getWindow());
    }

    private static View applyStatusBarColor(@NonNull Window window, int i2, boolean z2) {
        ViewGroup viewGroup = z2 ? (ViewGroup) window.getDecorView() : (ViewGroup) window.findViewById(R.id.content);
        View viewFindViewWithTag = viewGroup.findViewWithTag(TAG_STATUS_BAR);
        if (viewFindViewWithTag == null) {
            View viewCreateStatusBarView = createStatusBarView(window.getContext(), i2);
            viewGroup.addView(viewCreateStatusBarView);
            return viewCreateStatusBarView;
        }
        if (viewFindViewWithTag.getVisibility() == 8) {
            viewFindViewWithTag.setVisibility(0);
        }
        viewFindViewWithTag.setBackgroundColor(i2);
        return viewFindViewWithTag;
    }

    @RequiresApi(21)
    public static int getNavBarColor(@NonNull Window window) {
        return window.getNavigationBarColor();
    }

    private static void hideStatusBarView(@NonNull Window window) {
        View viewFindViewWithTag = ((ViewGroup) window.getDecorView()).findViewWithTag(TAG_STATUS_BAR);
        if (viewFindViewWithTag == null) {
            return;
        }
        viewFindViewWithTag.setVisibility(8);
    }

    public static boolean isNavBarLightMode(@NonNull Window window) {
        return Build.VERSION.SDK_INT >= 26 && (window.getDecorView().getSystemUiVisibility() & 16) != 0;
    }

    public static boolean isNavBarVisible(@NonNull Window window) {
        boolean z2;
        ViewGroup viewGroup = (ViewGroup) window.getDecorView();
        int childCount = viewGroup.getChildCount();
        int i2 = 0;
        while (true) {
            if (i2 >= childCount) {
                z2 = false;
                break;
            }
            View childAt = viewGroup.getChildAt(i2);
            int id = childAt.getId();
            if (id != -1 && "navigationBarBackground".equals(getResNameById(id)) && childAt.getVisibility() == 0) {
                z2 = true;
                break;
            }
            i2++;
        }
        if (!z2) {
            return z2;
        }
        if (UtilsBridge.isSamsung() && Build.VERSION.SDK_INT < 29) {
            try {
                return Settings.Global.getInt(Utils.getApp().getContentResolver(), "navigationbar_hide_bar_enabled") == 0;
            } catch (Exception unused) {
            }
        }
        return (viewGroup.getSystemUiVisibility() & 2) == 0;
    }

    public static boolean isStatusBarLightMode(@NonNull Window window) {
        return (window.getDecorView().getSystemUiVisibility() & 8192) != 0;
    }

    @RequiresApi(21)
    public static void setNavBarColor(@NonNull Window window, @ColorInt int i2) {
        window.addFlags(Integer.MIN_VALUE);
        window.setNavigationBarColor(i2);
    }

    public static void setNavBarLightMode(@NonNull Window window, boolean z2) {
        if (Build.VERSION.SDK_INT >= 26) {
            View decorView = window.getDecorView();
            int systemUiVisibility = decorView.getSystemUiVisibility();
            decorView.setSystemUiVisibility(z2 ? systemUiVisibility | 16 : systemUiVisibility & (-17));
        }
    }

    public static void setNavBarVisibility(@NonNull Window window, boolean z2) {
        ViewGroup viewGroup = (ViewGroup) window.getDecorView();
        int childCount = viewGroup.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = viewGroup.getChildAt(i2);
            int id = childAt.getId();
            if (id != -1 && "navigationBarBackground".equals(getResNameById(id))) {
                childAt.setVisibility(z2 ? 0 : 4);
            }
        }
        if (z2) {
            viewGroup.setSystemUiVisibility(viewGroup.getSystemUiVisibility() & (-4611));
        } else {
            viewGroup.setSystemUiVisibility(viewGroup.getSystemUiVisibility() | R2.color.color_qzone);
        }
    }

    public static View setStatusBarColor(@NonNull Activity activity, @ColorInt int i2, boolean z2) {
        transparentStatusBar(activity);
        return applyStatusBarColor(activity, i2, z2);
    }

    public static void setStatusBarColor4Drawer(@NonNull DrawerLayout drawerLayout, @NonNull View view, @ColorInt int i2, boolean z2) {
        Activity activityByContext = UtilsBridge.getActivityByContext(view.getContext());
        if (activityByContext == null) {
            return;
        }
        transparentStatusBar(activityByContext);
        drawerLayout.setFitsSystemWindows(false);
        setStatusBarColor(view, i2);
        int childCount = drawerLayout.getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            drawerLayout.getChildAt(i3).setFitsSystemWindows(false);
        }
        if (z2) {
            hideStatusBarView(activityByContext);
        } else {
            setStatusBarColor(activityByContext, i2, false);
        }
    }

    public static void setStatusBarLightMode(@NonNull Window window, boolean z2) {
        View decorView = window.getDecorView();
        int systemUiVisibility = decorView.getSystemUiVisibility();
        decorView.setSystemUiVisibility(z2 ? systemUiVisibility | 8192 : systemUiVisibility & (-8193));
    }

    public static void setStatusBarVisibility(@NonNull Window window, boolean z2) {
        if (z2) {
            window.clearFlags(1024);
            showStatusBarView(window);
            addMarginTopEqualStatusBarHeight(window);
        } else {
            window.addFlags(1024);
            hideStatusBarView(window);
            subtractMarginTopEqualStatusBarHeight(window);
        }
    }

    public static void transparentNavBar(@NonNull Window window) {
        if (Build.VERSION.SDK_INT >= 29) {
            window.setNavigationBarContrastEnforced(false);
        }
        window.setNavigationBarColor(0);
        View decorView = window.getDecorView();
        decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() | R2.attr.ic_info_desc_had_collection);
    }

    public static void transparentStatusBar(@NonNull Window window) {
        window.clearFlags(67108864);
        window.addFlags(Integer.MIN_VALUE);
        window.getDecorView().setSystemUiVisibility(window.getDecorView().getSystemUiVisibility() | 1280);
        window.setStatusBarColor(0);
    }

    public static View setStatusBarColor(@NonNull Window window, @ColorInt int i2) {
        return setStatusBarColor(window, i2, false);
    }

    public static View setStatusBarColor(@NonNull Window window, @ColorInt int i2, boolean z2) {
        transparentStatusBar(window);
        return applyStatusBarColor(window, i2, z2);
    }

    public static void setStatusBarColor(@NonNull View view, @ColorInt int i2) {
        Activity activityByContext = UtilsBridge.getActivityByContext(view.getContext());
        if (activityByContext == null) {
            return;
        }
        transparentStatusBar(activityByContext);
        view.setVisibility(0);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = -1;
        layoutParams.height = getStatusBarHeight();
        view.setBackgroundColor(i2);
    }

    private static void subtractMarginTopEqualStatusBarHeight(@NonNull Window window) {
        View viewFindViewWithTag = window.getDecorView().findViewWithTag(TAG_OFFSET);
        if (viewFindViewWithTag == null) {
            return;
        }
        subtractMarginTopEqualStatusBarHeight(viewFindViewWithTag);
    }

    private static void addMarginTopEqualStatusBarHeight(@NonNull Window window) {
        View viewFindViewWithTag = window.getDecorView().findViewWithTag(TAG_OFFSET);
        if (viewFindViewWithTag == null) {
            return;
        }
        addMarginTopEqualStatusBarHeight(viewFindViewWithTag);
    }
}
