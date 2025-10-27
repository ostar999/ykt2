package com.psychiatrygarden.utils;

import android.R;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import androidx.annotation.ColorInt;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import com.easefun.polyv.mediasdk.player.IjkMediaMeta;
import com.yikaobang.yixue.R2;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* loaded from: classes6.dex */
public class StatusBarUtil {
    public static final int DEFAULT_STATUS_BAR_ALPHA = 112;
    private static final int FAKE_STATUS_BAR_VIEW_ID = 2131367062;
    private static final int FAKE_TRANSLUCENT_VIEW_ID = 2131367063;
    private static final int TAG_KEY_HAVE_SET_OFFSET = -123;

    /* renamed from: com.psychiatrygarden.utils.StatusBarUtil$1, reason: invalid class name */
    public class AnonymousClass1 implements Runnable {
        final /* synthetic */ CoordinatorLayout val$coordinatorLayout;

        public AnonymousClass1(final CoordinatorLayout val$coordinatorLayout) {
            this.val$coordinatorLayout = val$coordinatorLayout;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.val$coordinatorLayout.requestLayout();
        }
    }

    private static void addTranslucentView(Activity activity, @IntRange(from = 0, to = IjkMediaMeta.AV_CH_LAYOUT_7POINT1_WIDE_BACK) int statusBarAlpha) {
        ViewGroup viewGroup = (ViewGroup) activity.findViewById(R.id.content);
        View viewFindViewById = viewGroup.findViewById(com.yikaobang.yixue.R.id.statusbarutil_translucent_view);
        if (viewFindViewById == null) {
            viewGroup.addView(createTranslucentStatusBarView(activity, statusBarAlpha));
            return;
        }
        if (viewFindViewById.getVisibility() == 8) {
            viewFindViewById.setVisibility(0);
        }
        viewFindViewById.setBackgroundColor(Color.argb(statusBarAlpha, 0, 0, 0));
    }

    private static int calculateStatusColor(@ColorInt int color, int alpha) {
        if (alpha == 0) {
            return color;
        }
        float f2 = 1.0f - (alpha / 255.0f);
        return ((int) (((color & 255) * f2) + 0.5d)) | (((int) ((((color >> 16) & 255) * f2) + 0.5d)) << 16) | (-16777216) | (((int) ((((color >> 8) & 255) * f2) + 0.5d)) << 8);
    }

    @TargetApi(19)
    private static void clearPreviousSetting(Activity activity) {
        ViewGroup viewGroup = (ViewGroup) activity.getWindow().getDecorView();
        View viewFindViewById = viewGroup.findViewById(com.yikaobang.yixue.R.id.statusbarutil_fake_status_bar_view);
        if (viewFindViewById != null) {
            viewGroup.removeView(viewFindViewById);
            ((ViewGroup) ((ViewGroup) activity.findViewById(R.id.content)).getChildAt(0)).setPadding(0, 0, 0, 0);
        }
    }

    private static View createStatusBarView(Activity activity, @ColorInt int color) {
        return createStatusBarView(activity, color, 0);
    }

    private static View createTranslucentStatusBarView(Activity activity, int alpha) {
        View view = new View(activity);
        view.setLayoutParams(new LinearLayout.LayoutParams(-1, getStatusBarHeight(activity)));
        view.setBackgroundColor(Color.argb(alpha, 0, 0, 0));
        view.setId(com.yikaobang.yixue.R.id.statusbarutil_translucent_view);
        return view;
    }

    public static int getStatusBarHeight(Context context) {
        return context.getResources().getDimensionPixelSize(context.getResources().getIdentifier("status_bar_height", "dimen", "android"));
    }

    public static void hideFakeStatusBarView(Activity activity) {
        ViewGroup viewGroup = (ViewGroup) activity.getWindow().getDecorView();
        View viewFindViewById = viewGroup.findViewById(com.yikaobang.yixue.R.id.statusbarutil_fake_status_bar_view);
        if (viewFindViewById != null) {
            viewFindViewById.setVisibility(8);
        }
        View viewFindViewById2 = viewGroup.findViewById(com.yikaobang.yixue.R.id.statusbarutil_translucent_view);
        if (viewFindViewById2 != null) {
            viewFindViewById2.setVisibility(8);
        }
    }

    public static boolean isMeizu() {
        try {
            return Build.class.getMethod("hasSmartBar", new Class[0]) != null;
        } catch (NoSuchMethodException unused) {
            return false;
        }
    }

    public static boolean isXiaomi() {
        return "Xiaomi".equals(Build.MANUFACTURER);
    }

    public static void setColor(Activity activity, @ColorInt int color) {
        setColor(activity, color, 112);
    }

    @Deprecated
    public static void setColorDiff(Activity activity, @ColorInt int color) {
        transparentStatusBar(activity);
        ViewGroup viewGroup = (ViewGroup) activity.findViewById(R.id.content);
        View viewFindViewById = viewGroup.findViewById(com.yikaobang.yixue.R.id.statusbarutil_fake_status_bar_view);
        if (viewFindViewById != null) {
            if (viewFindViewById.getVisibility() == 8) {
                viewFindViewById.setVisibility(0);
            }
            viewFindViewById.setBackgroundColor(color);
        } else {
            viewGroup.addView(createStatusBarView(activity, color));
        }
        setRootView(activity);
    }

    public static void setColorForDrawerLayout(Activity activity, DrawerLayout drawerLayout, @ColorInt int color) {
        setColorForDrawerLayout(activity, drawerLayout, color, 112);
    }

    @Deprecated
    public static void setColorForDrawerLayoutDiff(Activity activity, DrawerLayout drawerLayout, @ColorInt int color) {
        activity.getWindow().addFlags(67108864);
        ViewGroup viewGroup = (ViewGroup) drawerLayout.getChildAt(0);
        View viewFindViewById = viewGroup.findViewById(com.yikaobang.yixue.R.id.statusbarutil_fake_status_bar_view);
        if (viewFindViewById != null) {
            if (viewFindViewById.getVisibility() == 8) {
                viewFindViewById.setVisibility(0);
            }
            viewFindViewById.setBackgroundColor(calculateStatusColor(color, 112));
        } else {
            viewGroup.addView(createStatusBarView(activity, color), 0);
        }
        if (!(viewGroup instanceof LinearLayout) && viewGroup.getChildAt(1) != null) {
            viewGroup.getChildAt(1).setPadding(0, getStatusBarHeight(activity), 0, 0);
        }
        setDrawerLayoutProperty(drawerLayout, viewGroup);
    }

    public static void setColorForSwipeBack(Activity activity, int color) {
        setColorForSwipeBack(activity, color, 112);
    }

    public static void setColorNoTranslucent(Activity activity, @ColorInt int color) {
        setColor(activity, color, 0);
    }

    public static void setColorNoTranslucentForDrawerLayout(Activity activity, DrawerLayout drawerLayout, @ColorInt int color) {
        setColorForDrawerLayout(activity, drawerLayout, color, 0);
    }

    @TargetApi(23)
    public static void setDarkMode(Activity activity) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        setMIUIStatusBarDarkIcon(activity, false);
        setMeizuStatusBarDarkIcon(activity, false);
        activity.getWindow().getDecorView().setSystemUiVisibility(256);
    }

    private static void setDrawerLayoutProperty(DrawerLayout drawerLayout, ViewGroup drawerLayoutContentLayout) {
        ViewGroup viewGroup = (ViewGroup) drawerLayout.getChildAt(1);
        drawerLayout.setFitsSystemWindows(false);
        drawerLayoutContentLayout.setFitsSystemWindows(false);
        drawerLayoutContentLayout.setClipToPadding(true);
        viewGroup.setFitsSystemWindows(false);
    }

    @TargetApi(23)
    public static void setLightMode(Activity activity) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        setMIUIStatusBarDarkIcon(activity, true);
        setMeizuStatusBarDarkIcon(activity, true);
        activity.getWindow().getDecorView().setSystemUiVisibility(R2.drawable.ddbq);
    }

    private static void setMIUIStatusBarDarkIcon(@NonNull Activity activity, boolean darkIcon) throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Class<?> cls = activity.getWindow().getClass();
        try {
            Class<?> cls2 = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            int i2 = cls2.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE").getInt(cls2);
            Class<?> cls3 = Integer.TYPE;
            Method method = cls.getMethod("setExtraFlags", cls3, cls3);
            Window window = activity.getWindow();
            Object[] objArr = new Object[2];
            objArr[0] = Integer.valueOf(darkIcon ? i2 : 0);
            objArr[1] = Integer.valueOf(i2);
            method.invoke(window, objArr);
        } catch (Exception unused) {
        }
    }

    public static void setMeizuStatusBar(Window window, boolean isLightStatusBar) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        WindowManager.LayoutParams attributes = window.getAttributes();
        try {
            Field declaredField = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            Field declaredField2 = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
            declaredField.setAccessible(true);
            declaredField2.setAccessible(true);
            int i2 = declaredField.getInt(null);
            int i3 = declaredField2.getInt(attributes);
            declaredField2.setInt(attributes, isLightStatusBar ? i3 | i2 : (~i2) & i3);
            window.setAttributes(attributes);
            declaredField.setAccessible(false);
            declaredField2.setAccessible(false);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private static void setMeizuStatusBarDarkIcon(@NonNull Activity activity, boolean darkIcon) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        try {
            WindowManager.LayoutParams attributes = activity.getWindow().getAttributes();
            Field declaredField = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            Field declaredField2 = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
            declaredField.setAccessible(true);
            declaredField2.setAccessible(true);
            int i2 = declaredField.getInt(null);
            int i3 = declaredField2.getInt(attributes);
            declaredField2.setInt(attributes, darkIcon ? i3 | i2 : (~i2) & i3);
            activity.getWindow().setAttributes(attributes);
        } catch (Exception unused) {
        }
    }

    private static void setRootView(Activity activity) {
        ViewGroup viewGroup = (ViewGroup) activity.findViewById(R.id.content);
        int childCount = viewGroup.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = viewGroup.getChildAt(i2);
            if (childAt instanceof ViewGroup) {
                childAt.setFitsSystemWindows(true);
                ((ViewGroup) childAt).setClipToPadding(true);
            }
        }
    }

    public static void setStatusBarTranslucent(Activity activity, boolean isLightStatusBar) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (activity == null) {
            return;
        }
        Window window = activity.getWindow();
        window.clearFlags(67108864);
        window.getDecorView().setSystemUiVisibility(1280);
        window.addFlags(Integer.MIN_VALUE);
        window.setStatusBarColor(0);
        if (isXiaomi()) {
            setXiaomiStatusBar(window, isLightStatusBar);
        } else if (isMeizu()) {
            setMeizuStatusBar(window, isLightStatusBar);
        }
    }

    public static void setTranslucent(Activity activity) {
        setTranslucent(activity, 112);
    }

    @Deprecated
    public static void setTranslucentDiff(Activity activity) {
        activity.getWindow().addFlags(67108864);
        setRootView(activity);
    }

    public static void setTranslucentForCoordinatorLayout(Activity activity, @IntRange(from = 0, to = IjkMediaMeta.AV_CH_LAYOUT_7POINT1_WIDE_BACK) int statusBarAlpha) {
        transparentStatusBar(activity);
        addTranslucentView(activity, statusBarAlpha);
    }

    public static void setTranslucentForDrawerLayout(Activity activity, DrawerLayout drawerLayout) {
        setTranslucentForDrawerLayout(activity, drawerLayout, 112);
    }

    @Deprecated
    public static void setTranslucentForDrawerLayoutDiff(Activity activity, DrawerLayout drawerLayout) {
        activity.getWindow().addFlags(67108864);
        ViewGroup viewGroup = (ViewGroup) drawerLayout.getChildAt(0);
        viewGroup.setFitsSystemWindows(true);
        viewGroup.setClipToPadding(true);
        ((ViewGroup) drawerLayout.getChildAt(1)).setFitsSystemWindows(false);
        drawerLayout.setFitsSystemWindows(false);
    }

    public static void setTranslucentForImageView(Activity activity, View needOffsetView) {
        setTranslucentForImageView(activity, 112, needOffsetView);
    }

    public static void setTranslucentForImageViewInFragment(Activity activity, View needOffsetView) {
        setTranslucentForImageViewInFragment(activity, 112, needOffsetView);
    }

    public static void setTransparent(Activity activity) {
        transparentStatusBar(activity);
        setRootView(activity);
    }

    public static void setTransparentForDrawerLayout(Activity activity, DrawerLayout drawerLayout) {
        activity.getWindow().addFlags(Integer.MIN_VALUE);
        activity.getWindow().clearFlags(67108864);
        activity.getWindow().setStatusBarColor(0);
        ViewGroup viewGroup = (ViewGroup) drawerLayout.getChildAt(0);
        if (!(viewGroup instanceof LinearLayout) && viewGroup.getChildAt(1) != null) {
            viewGroup.getChildAt(1).setPadding(0, getStatusBarHeight(activity), 0, 0);
        }
        setDrawerLayoutProperty(drawerLayout, viewGroup);
    }

    public static void setTransparentForImageView(Activity activity, View needOffsetView) {
        setTranslucentForImageView(activity, 0, needOffsetView);
    }

    public static void setTransparentForImageViewInFragment(Activity activity, View needOffsetView) {
        setTranslucentForImageViewInFragment(activity, 0, needOffsetView);
    }

    private static void setTransparentForWindow(Activity activity) {
        activity.getWindow().setStatusBarColor(0);
        activity.getWindow().getDecorView().setSystemUiVisibility(1280);
    }

    public static void setXiaomiStatusBar(Window window, boolean isLightStatusBar) throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Class<?> cls = window.getClass();
        try {
            Class<?> cls2 = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            int i2 = cls2.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE").getInt(cls2);
            Class<?> cls3 = Integer.TYPE;
            Method method = cls.getMethod("setExtraFlags", cls3, cls3);
            Object[] objArr = new Object[2];
            objArr[0] = Integer.valueOf(isLightStatusBar ? i2 : 0);
            objArr[1] = Integer.valueOf(i2);
            method.invoke(window, objArr);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @TargetApi(19)
    private static void transparentStatusBar(Activity activity) {
        activity.getWindow().addFlags(Integer.MIN_VALUE);
        activity.getWindow().clearFlags(67108864);
        activity.getWindow().addFlags(134217728);
        activity.getWindow().setStatusBarColor(0);
    }

    private static View createStatusBarView(Activity activity, @ColorInt int color, int alpha) {
        View view = new View(activity);
        view.setLayoutParams(new LinearLayout.LayoutParams(-1, getStatusBarHeight(activity)));
        view.setBackgroundColor(calculateStatusColor(color, alpha));
        view.setId(com.yikaobang.yixue.R.id.statusbarutil_fake_status_bar_view);
        return view;
    }

    public static void setColor(Activity activity, @ColorInt int color, @IntRange(from = 0, to = IjkMediaMeta.AV_CH_LAYOUT_7POINT1_WIDE_BACK) int statusBarAlpha) {
        activity.getWindow().addFlags(Integer.MIN_VALUE);
        activity.getWindow().clearFlags(67108864);
        activity.getWindow().setStatusBarColor(calculateStatusColor(color, statusBarAlpha));
    }

    public static void setColorForDrawerLayout(Activity activity, DrawerLayout drawerLayout, @ColorInt int color, @IntRange(from = 0, to = IjkMediaMeta.AV_CH_LAYOUT_7POINT1_WIDE_BACK) int statusBarAlpha) {
        activity.getWindow().addFlags(Integer.MIN_VALUE);
        activity.getWindow().clearFlags(67108864);
        activity.getWindow().setStatusBarColor(0);
        ViewGroup viewGroup = (ViewGroup) drawerLayout.getChildAt(0);
        View viewFindViewById = viewGroup.findViewById(com.yikaobang.yixue.R.id.statusbarutil_fake_status_bar_view);
        if (viewFindViewById != null) {
            if (viewFindViewById.getVisibility() == 8) {
                viewFindViewById.setVisibility(0);
            }
            viewFindViewById.setBackgroundColor(color);
        } else {
            viewGroup.addView(createStatusBarView(activity, color), 0);
        }
        if (!(viewGroup instanceof LinearLayout) && viewGroup.getChildAt(1) != null) {
            viewGroup.getChildAt(1).setPadding(viewGroup.getPaddingLeft(), getStatusBarHeight(activity) + viewGroup.getPaddingTop(), viewGroup.getPaddingRight(), viewGroup.getPaddingBottom());
        }
        setDrawerLayoutProperty(drawerLayout, viewGroup);
        addTranslucentView(activity, statusBarAlpha);
    }

    public static void setColorForSwipeBack(Activity activity, @ColorInt int color, @IntRange(from = 0, to = IjkMediaMeta.AV_CH_LAYOUT_7POINT1_WIDE_BACK) int statusBarAlpha) {
        ViewGroup viewGroup = (ViewGroup) activity.findViewById(R.id.content);
        View childAt = viewGroup.getChildAt(0);
        int statusBarHeight = getStatusBarHeight(activity);
        if (childAt == null || !(childAt instanceof CoordinatorLayout)) {
            viewGroup.setPadding(0, statusBarHeight, 0, 0);
            viewGroup.setBackgroundColor(calculateStatusColor(color, statusBarAlpha));
        } else {
            ((CoordinatorLayout) childAt).setStatusBarBackgroundColor(calculateStatusColor(color, statusBarAlpha));
        }
        setTransparentForWindow(activity);
    }

    public static void setTranslucent(Activity activity, @IntRange(from = 0, to = IjkMediaMeta.AV_CH_LAYOUT_7POINT1_WIDE_BACK) int statusBarAlpha) {
        setTransparent(activity);
        addTranslucentView(activity, statusBarAlpha);
    }

    public static void setTranslucentForDrawerLayout(Activity activity, DrawerLayout drawerLayout, @IntRange(from = 0, to = IjkMediaMeta.AV_CH_LAYOUT_7POINT1_WIDE_BACK) int statusBarAlpha) {
        setTransparentForDrawerLayout(activity, drawerLayout);
        addTranslucentView(activity, statusBarAlpha);
    }

    public static void setTranslucentForImageView(Activity activity, @IntRange(from = 0, to = IjkMediaMeta.AV_CH_LAYOUT_7POINT1_WIDE_BACK) int statusBarAlpha, View needOffsetView) {
        setTransparentForWindow(activity);
        addTranslucentView(activity, statusBarAlpha);
        if (needOffsetView != null) {
            Object tag = needOffsetView.getTag(TAG_KEY_HAVE_SET_OFFSET);
            if (tag == null || !((Boolean) tag).booleanValue()) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) needOffsetView.getLayoutParams();
                marginLayoutParams.setMargins(marginLayoutParams.leftMargin, marginLayoutParams.topMargin + getStatusBarHeight(activity), marginLayoutParams.rightMargin, marginLayoutParams.bottomMargin);
                needOffsetView.setTag(TAG_KEY_HAVE_SET_OFFSET, Boolean.TRUE);
            }
        }
    }

    public static void setTranslucentForImageViewInFragment(Activity activity, @IntRange(from = 0, to = IjkMediaMeta.AV_CH_LAYOUT_7POINT1_WIDE_BACK) int statusBarAlpha, View needOffsetView) {
        setTranslucentForImageView(activity, statusBarAlpha, needOffsetView);
    }
}
