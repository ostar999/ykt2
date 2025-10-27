package com.plv.thirdpart.blankj.utilcode.util;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import androidx.annotation.ColorInt;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import com.easefun.polyv.mediasdk.player.IjkMediaMeta;
import com.yikaobang.yixue.R2;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes5.dex */
public final class BarUtils {
    private static final int DEFAULT_ALPHA = 112;
    private static final String TAG_ALPHA = "TAG_ALPHA";
    private static final String TAG_COLOR = "TAG_COLOR";
    private static final int TAG_OFFSET = -123;

    private BarUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void addMarginTopEqualStatusBarHeight(@NonNull View view) {
        Object tag = view.getTag(TAG_OFFSET);
        if (tag == null || !((Boolean) tag).booleanValue()) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            marginLayoutParams.setMargins(marginLayoutParams.leftMargin, marginLayoutParams.topMargin + getStatusBarHeight(), marginLayoutParams.rightMargin, marginLayoutParams.bottomMargin);
            view.setTag(TAG_OFFSET, Boolean.TRUE);
        }
    }

    private static void addStatusBarAlpha(Activity activity, int i2, boolean z2) {
        ViewGroup viewGroup = z2 ? (ViewGroup) activity.getWindow().getDecorView() : (ViewGroup) activity.findViewById(R.id.content);
        View viewFindViewWithTag = viewGroup.findViewWithTag(TAG_ALPHA);
        if (viewFindViewWithTag == null) {
            viewGroup.addView(createAlphaStatusBarView(viewGroup.getContext(), i2));
            return;
        }
        if (viewFindViewWithTag.getVisibility() == 8) {
            viewFindViewWithTag.setVisibility(0);
        }
        viewFindViewWithTag.setBackgroundColor(Color.argb(i2, 0, 0, 0));
    }

    private static void addStatusBarColor(Activity activity, int i2, int i3, boolean z2) {
        ViewGroup viewGroup = z2 ? (ViewGroup) activity.getWindow().getDecorView() : (ViewGroup) activity.findViewById(R.id.content);
        View viewFindViewWithTag = viewGroup.findViewWithTag(TAG_COLOR);
        if (viewFindViewWithTag == null) {
            viewGroup.addView(createColorStatusBarView(viewGroup.getContext(), i2, i3));
            return;
        }
        if (viewFindViewWithTag.getVisibility() == 8) {
            viewFindViewWithTag.setVisibility(0);
        }
        viewFindViewWithTag.setBackgroundColor(getStatusBarColor(i2, i3));
    }

    private static View createAlphaStatusBarView(Context context, int i2) {
        View view = new View(context);
        view.setLayoutParams(new LinearLayout.LayoutParams(-1, getStatusBarHeight()));
        view.setBackgroundColor(Color.argb(i2, 0, 0, 0));
        view.setTag(TAG_ALPHA);
        return view;
    }

    private static View createColorStatusBarView(Context context, int i2, int i3) {
        View view = new View(context);
        view.setLayoutParams(new LinearLayout.LayoutParams(-1, getStatusBarHeight()));
        view.setBackgroundColor(getStatusBarColor(i2, i3));
        view.setTag(TAG_COLOR);
        return view;
    }

    public static int getActionBarHeight(@NonNull Activity activity) {
        TypedValue typedValue = new TypedValue();
        if (activity.getTheme().resolveAttribute(R.attr.actionBarSize, typedValue, true)) {
            return TypedValue.complexToDimensionPixelSize(typedValue.data, activity.getResources().getDisplayMetrics());
        }
        return 0;
    }

    public static int getNavBarHeight() {
        Resources resources = Utils.getApp().getResources();
        int identifier = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (identifier != 0) {
            return resources.getDimensionPixelSize(identifier);
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

    private static int getStatusBarColor(int i2, int i3) {
        if (i3 == 0) {
            return i2;
        }
        float f2 = 1.0f - (i3 / 255.0f);
        return Color.argb(255, (int) ((((i2 >> 16) & 255) * f2) + 0.5d), (int) ((((i2 >> 8) & 255) * f2) + 0.5d), (int) (((i2 & 255) * f2) + 0.5d));
    }

    public static int getStatusBarHeight() {
        Resources resources = Utils.getApp().getResources();
        return resources.getDimensionPixelSize(resources.getIdentifier("status_bar_height", "dimen", "android"));
    }

    private static void hideAlphaView(Activity activity) {
        View viewFindViewWithTag = ((ViewGroup) activity.getWindow().getDecorView()).findViewWithTag(TAG_ALPHA);
        if (viewFindViewWithTag == null) {
            return;
        }
        viewFindViewWithTag.setVisibility(8);
    }

    private static void hideColorView(Activity activity) {
        View viewFindViewWithTag = ((ViewGroup) activity.getWindow().getDecorView()).findViewWithTag(TAG_COLOR);
        if (viewFindViewWithTag == null) {
            return;
        }
        viewFindViewWithTag.setVisibility(8);
    }

    public static void hideNavBar(@NonNull Activity activity) {
        if (getNavBarHeight() > 0) {
            activity.getWindow().getDecorView().setSystemUiVisibility(R2.color.color_qzone);
        }
    }

    public static void hideNotificationBar(@NonNull Context context) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        invokePanels(context, "collapsePanels");
    }

    private static void invokePanels(@NonNull Context context, String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        try {
            Class.forName("android.app.StatusBarManager").getMethod(str, new Class[0]).invoke(context.getSystemService("statusbar"), new Object[0]);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static boolean isNavBarVisible(@NonNull Activity activity) {
        return isNavBarVisible(activity.getWindow());
    }

    public static void setStatusBarAlpha(@NonNull Activity activity) {
        setStatusBarAlpha(activity, 112, false);
    }

    public static void setStatusBarAlpha4Drawer(@NonNull Activity activity, @NonNull DrawerLayout drawerLayout, @NonNull View view, boolean z2) {
        setStatusBarAlpha4Drawer(activity, drawerLayout, view, 112, z2);
    }

    public static void setStatusBarColor(@NonNull Activity activity, @ColorInt int i2) {
        setStatusBarColor(activity, i2, 112, false);
    }

    public static void setStatusBarColor4Drawer(@NonNull Activity activity, @NonNull DrawerLayout drawerLayout, @NonNull View view, @ColorInt int i2, boolean z2) {
        setStatusBarColor4Drawer(activity, drawerLayout, view, i2, 112, z2);
    }

    public static void showNotificationBar(@NonNull Context context, boolean z2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        invokePanels(context, z2 ? "expandSettingsPanel" : "expandNotificationsPanel");
    }

    public static void subtractMarginTopEqualStatusBarHeight(@NonNull View view) {
        Object tag = view.getTag(TAG_OFFSET);
        if (tag == null || !((Boolean) tag).booleanValue()) {
            return;
        }
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        marginLayoutParams.setMargins(marginLayoutParams.leftMargin, marginLayoutParams.topMargin - getStatusBarHeight(), marginLayoutParams.rightMargin, marginLayoutParams.bottomMargin);
        view.setTag(TAG_OFFSET, Boolean.FALSE);
    }

    private static void transparentStatusBar(Activity activity) {
        Window window = activity.getWindow();
        window.getDecorView().setSystemUiVisibility(1280);
        window.setStatusBarColor(0);
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
        if (z2) {
            return (viewGroup.getSystemUiVisibility() & 2) == 0;
        }
        return z2;
    }

    public static void setStatusBarAlpha(@NonNull Activity activity, @IntRange(from = 0, to = IjkMediaMeta.AV_CH_LAYOUT_7POINT1_WIDE_BACK) int i2) {
        setStatusBarAlpha(activity, i2, false);
    }

    public static void setStatusBarAlpha4Drawer(@NonNull Activity activity, @NonNull DrawerLayout drawerLayout, @NonNull View view, @IntRange(from = 0, to = IjkMediaMeta.AV_CH_LAYOUT_7POINT1_WIDE_BACK) int i2, boolean z2) {
        drawerLayout.setFitsSystemWindows(false);
        transparentStatusBar(activity);
        setStatusBarAlpha(view, z2 ? i2 : 0);
        int childCount = drawerLayout.getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            drawerLayout.getChildAt(i3).setFitsSystemWindows(false);
        }
        if (z2) {
            hideAlphaView(activity);
        } else {
            addStatusBarAlpha(activity, i2, false);
        }
    }

    public static void setStatusBarColor(@NonNull Activity activity, @ColorInt int i2, @IntRange(from = 0, to = IjkMediaMeta.AV_CH_LAYOUT_7POINT1_WIDE_BACK) int i3) {
        setStatusBarColor(activity, i2, i3, false);
    }

    public static void setStatusBarColor4Drawer(@NonNull Activity activity, @NonNull DrawerLayout drawerLayout, @NonNull View view, @ColorInt int i2, @IntRange(from = 0, to = IjkMediaMeta.AV_CH_LAYOUT_7POINT1_WIDE_BACK) int i3, boolean z2) {
        drawerLayout.setFitsSystemWindows(false);
        transparentStatusBar(activity);
        setStatusBarColor(view, i2, z2 ? i3 : 0);
        int childCount = drawerLayout.getChildCount();
        for (int i4 = 0; i4 < childCount; i4++) {
            drawerLayout.getChildAt(i4).setFitsSystemWindows(false);
        }
        if (z2) {
            hideAlphaView(activity);
        } else {
            addStatusBarAlpha(activity, i3, false);
        }
    }

    public static void setStatusBarAlpha(@NonNull Activity activity, @IntRange(from = 0, to = IjkMediaMeta.AV_CH_LAYOUT_7POINT1_WIDE_BACK) int i2, boolean z2) {
        hideColorView(activity);
        transparentStatusBar(activity);
        addStatusBarAlpha(activity, i2, z2);
    }

    public static void setStatusBarColor(@NonNull Activity activity, @ColorInt int i2, @IntRange(from = 0, to = IjkMediaMeta.AV_CH_LAYOUT_7POINT1_WIDE_BACK) int i3, boolean z2) {
        hideAlphaView(activity);
        transparentStatusBar(activity);
        addStatusBarColor(activity, i2, i3, z2);
    }

    public static void setStatusBarAlpha(@NonNull View view) {
        setStatusBarAlpha(view, 112);
    }

    public static void setStatusBarColor(@NonNull View view, @ColorInt int i2) {
        setStatusBarColor(view, i2, 112);
    }

    public static void setStatusBarAlpha(@NonNull View view, @IntRange(from = 0, to = IjkMediaMeta.AV_CH_LAYOUT_7POINT1_WIDE_BACK) int i2) {
        view.setVisibility(0);
        transparentStatusBar((Activity) view.getContext());
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = -1;
        layoutParams.height = getStatusBarHeight();
        view.setBackgroundColor(Color.argb(i2, 0, 0, 0));
    }

    public static void setStatusBarColor(@NonNull View view, @ColorInt int i2, @IntRange(from = 0, to = IjkMediaMeta.AV_CH_LAYOUT_7POINT1_WIDE_BACK) int i3) {
        view.setVisibility(0);
        transparentStatusBar((Activity) view.getContext());
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = -1;
        layoutParams.height = getStatusBarHeight();
        view.setBackgroundColor(getStatusBarColor(i2, i3));
    }
}
