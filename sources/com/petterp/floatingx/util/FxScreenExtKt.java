package com.petterp.floatingx.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Insets;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowMetrics;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.umeng.analytics.pro.d;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt___RangesKt;
import kotlin.text.StringsKt__StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u00000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\f\u001a\u0010\u0010\u001c\u001a\u00020\u00032\u0006\u0010\u001d\u001a\u00020\u0015H\u0002\u001a \u0010\u001e\u001a\u00020\u000e2\u0006\u0010\u001f\u001a\u00020\u000e2\u0006\u0010 \u001a\u00020\u000e2\u0006\u0010\u001d\u001a\u00020\u0015H\u0002\u001a\u001c\u0010!\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000e0\"2\u0006\u0010#\u001a\u00020\u0011H\u0002\u001a\u0010\u0010$\u001a\u00020\u000e2\u0006\u0010\u001d\u001a\u00020\u0015H\u0003\u001a\u0010\u0010%\u001a\u00020\u000e2\u0006\u0010\u001d\u001a\u00020\u0015H\u0002\u001a\u0010\u0010&\u001a\u00020\u000e2\u0006\u0010\u001d\u001a\u00020\u0015H\u0002\u001a\u0010\u0010'\u001a\u00020\u000e2\u0006\u0010\u001d\u001a\u00020\u0015H\u0002\u001a\u0010\u0010(\u001a\u00020\u000e2\u0006\u0010\u001d\u001a\u00020\u0015H\u0002\u001a\u0010\u0010)\u001a\u00020\u000e2\u0006\u0010\u001d\u001a\u00020\u0015H\u0002\u001a\u0010\u0010*\u001a\u00020\u000e2\u0006\u0010\u001d\u001a\u00020\u0015H\u0002\u001a\u0010\u0010+\u001a\u00020\u000e2\u0006\u0010\u001d\u001a\u00020\u0015H\u0002\u001a\u0010\u0010,\u001a\u00020\u000e2\u0006\u0010\u001d\u001a\u00020\u0015H\u0002\u001a\u0010\u0010-\u001a\u00020\u000e2\u0006\u0010\u001d\u001a\u00020\u0015H\u0002\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u0014\u0010\u0002\u001a\u00020\u00038BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0004\"\u0014\u0010\u0005\u001a\u00020\u00038BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0004\"\u0014\u0010\u0006\u001a\u00020\u00038BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0004\"\u0014\u0010\u0007\u001a\u00020\u00038BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0004\"\u0014\u0010\b\u001a\u00020\u00038BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\u0004\"\u0014\u0010\t\u001a\u00020\u00038BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\u0004\"\u0014\u0010\n\u001a\u00020\u00038BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u0004\"\u0014\u0010\u000b\u001a\u00020\u00038BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\u0004\"\u0014\u0010\f\u001a\u00020\u00038BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\u0004\"\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000\"\u000e\u0010\u000f\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000\"\u0018\u0010\u0010\u001a\u00020\u000e*\u00020\u00118@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013\"\u0018\u0010\u0014\u001a\u00020\u000e*\u00020\u00158@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017\"\u0018\u0010\u0018\u001a\u00020\u000e*\u00020\u00158@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u0017\"\u0018\u0010\u001a\u001a\u00020\u000e*\u00020\u00118@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u0013¨\u0006."}, d2 = {"BRAND", "", "isGoogle", "", "()Z", "isHuawei", "isNokia", "isOnePlus", "isOppo", "isSamsung", "isSmarTisan", "isVivo", "isXiaomi", "navigationHeightBf", "", "screenHeightBf", "navigationBarHeight", "Landroid/app/Activity;", "getNavigationBarHeight", "(Landroid/app/Activity;)I", "realScreenHeight", "Landroid/content/Context;", "getRealScreenHeight", "(Landroid/content/Context;)I", "screenHeight", "getScreenHeight", "statusBarHeight", "getStatusBarHeight", "checkNavigationBarShow", d.R, "getNavigationBarHeightFromSystem", "screenSize", "realSize", "getNavigationFromAndroid", "Lkotlin/Pair;", PushConstants.INTENT_ACTIVITY_NAME, "getRealNavHeight", "huaWeiNavigationEnabled", "isNavBarVendorHide", "nokiaNavigationEnabled", "onePlusNavigationEnabled", "oppoNavigationEnabled", "samsungNavigationEnabled", "smartisanNavigationEnabled", "vivoNavigationEnabled", "xiaomiNavigationEnabled", "floatingx_release"}, k = 2, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes4.dex */
public final class FxScreenExtKt {

    @NotNull
    private static final String BRAND;
    private static int navigationHeightBf;
    private static int screenHeightBf;

    static {
        String BRAND2 = Build.BRAND;
        Intrinsics.checkNotNullExpressionValue(BRAND2, "BRAND");
        Locale ROOT = Locale.ROOT;
        Intrinsics.checkNotNullExpressionValue(ROOT, "ROOT");
        String lowerCase = BRAND2.toLowerCase(ROOT);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "(this as java.lang.String).toLowerCase(locale)");
        BRAND = lowerCase;
    }

    private static final boolean checkNavigationBarShow(Context context) throws IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        Object objInvoke;
        Resources resources = context.getResources();
        int identifier = resources.getIdentifier("config_showNavigationBar", "bool", "android");
        boolean z2 = identifier > 0 ? resources.getBoolean(identifier) : false;
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            objInvoke = cls.getMethod("get", String.class).invoke(cls, "qemu.hw.mainkeys");
        } catch (Exception unused) {
        }
        if (objInvoke == null) {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.String");
        }
        String str = (String) objInvoke;
        int i2 = Settings.Global.getInt(context.getContentResolver(), "navigationbar_is_min", 0);
        if (!Intrinsics.areEqual("1", str) && 1 != i2) {
            if (Intrinsics.areEqual("0", str)) {
                return true;
            }
            return z2;
        }
        return false;
    }

    public static final int getNavigationBarHeight(@NotNull Activity activity) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(activity, "<this>");
        int screenHeight = getScreenHeight(activity);
        if (screenHeight == screenHeightBf) {
            return navigationHeightBf;
        }
        screenHeightBf = screenHeight;
        if (Build.VERSION.SDK_INT >= 30) {
            int realNavHeight = getRealNavHeight(activity);
            navigationHeightBf = realNavHeight;
            return realNavHeight;
        }
        Pair<Integer, Integer> navigationFromAndroid = getNavigationFromAndroid(activity);
        int iIntValue = navigationFromAndroid.component1().intValue();
        int iIntValue2 = navigationFromAndroid.component2().intValue();
        int navigationBarHeightFromSystem = 0;
        if (iIntValue == 0) {
            return 0;
        }
        boolean z2 = true;
        if (iIntValue == 1) {
            navigationHeightBf = iIntValue2;
            return iIntValue2;
        }
        if (!checkNavigationBarShow(activity) && isNavBarVendorHide(activity) != 0) {
            z2 = false;
        }
        int realScreenHeight = getRealScreenHeight(activity);
        if (z2 && realScreenHeight != screenHeight) {
            navigationBarHeightFromSystem = getNavigationBarHeightFromSystem(screenHeight, realScreenHeight, activity);
        }
        navigationHeightBf = navigationBarHeightFromSystem;
        return navigationBarHeightFromSystem;
    }

    private static final int getNavigationBarHeightFromSystem(int i2, int i3, Context context) throws Resources.NotFoundException {
        int identifier = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (identifier <= 0) {
            return 0;
        }
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(identifier);
        if (i3 - i2 > dimensionPixelSize - 10) {
            return dimensionPixelSize;
        }
        return 0;
    }

    private static final Pair<Integer, Integer> getNavigationFromAndroid(Activity activity) {
        try {
            View decorView = activity.getWindow().getDecorView();
            ViewGroup viewGroup = decorView instanceof ViewGroup ? (ViewGroup) decorView : null;
            if (viewGroup == null) {
                return TuplesKt.to(-1, 0);
            }
            Iterator<Integer> it = RangesKt___RangesKt.until(0, viewGroup.getChildCount()).iterator();
            while (it.hasNext()) {
                View childAt = viewGroup.getChildAt(((IntIterator) it).nextInt());
                Integer numValueOf = childAt == null ? null : Integer.valueOf(childAt.getId());
                if (numValueOf != null && numValueOf.intValue() == 16908336) {
                    return TuplesKt.to(1, Integer.valueOf(viewGroup.findViewById(numValueOf.intValue()).getHeight()));
                }
            }
            return TuplesKt.to(0, 0);
        } catch (Exception unused) {
            return TuplesKt.to(-1, 0);
        }
    }

    @SuppressLint({"NewApi"})
    private static final int getRealNavHeight(Context context) {
        Object systemService = context.getSystemService("window");
        if (systemService == null) {
            throw new NullPointerException("null cannot be cast to non-null type android.view.WindowManager");
        }
        WindowMetrics currentWindowMetrics = ((WindowManager) systemService).getCurrentWindowMetrics();
        Intrinsics.checkNotNullExpressionValue(currentWindowMetrics, "wm.currentWindowMetrics");
        WindowInsets windowInsets = currentWindowMetrics.getWindowInsets();
        Intrinsics.checkNotNullExpressionValue(windowInsets, "windowMetrics.windowInsets");
        windowInsets.getInsets(WindowInsets.Type.navigationBars());
        Insets insetsIgnoringVisibility = windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.navigationBars() | WindowInsets.Type.displayCutout());
        Intrinsics.checkNotNullExpressionValue(insetsIgnoringVisibility, "windowInsets.getInsetsIgnoringVisibility(typeMask)");
        return insetsIgnoringVisibility.bottom;
    }

    public static final int getRealScreenHeight(@NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        Object systemService = context.getSystemService("window");
        if (systemService == null) {
            throw new NullPointerException("null cannot be cast to non-null type android.view.WindowManager");
        }
        Display defaultDisplay = ((WindowManager) systemService).getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getRealMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public static final int getScreenHeight(@NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        Object systemService = context.getSystemService("window");
        if (systemService == null) {
            throw new NullPointerException("null cannot be cast to non-null type android.view.WindowManager");
        }
        Display defaultDisplay = ((WindowManager) systemService).getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public static final int getStatusBarHeight(@NotNull Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "<this>");
        try {
            if (Build.VERSION.SDK_INT >= 24 && activity.isInMultiWindowMode()) {
                return 0;
            }
            return activity.getResources().getDimensionPixelSize(activity.getResources().getIdentifier("status_bar_height", "dimen", "android"));
        } catch (Exception unused) {
            return 0;
        }
    }

    private static final int huaWeiNavigationEnabled(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), "navigationbar_is_min", 0);
    }

    private static final boolean isGoogle() {
        return StringsKt__StringsKt.contains$default((CharSequence) BRAND, (CharSequence) "google", false, 2, (Object) null);
    }

    private static final boolean isHuawei() {
        String str = BRAND;
        return StringsKt__StringsKt.contains$default((CharSequence) str, (CharSequence) "huawei", false, 2, (Object) null) || StringsKt__StringsKt.contains$default((CharSequence) str, (CharSequence) "honor", false, 2, (Object) null);
    }

    private static final int isNavBarVendorHide(Context context) {
        return isVivo() ? vivoNavigationEnabled(context) : isOppo() ? oppoNavigationEnabled(context) : isXiaomi() ? xiaomiNavigationEnabled(context) : isHuawei() ? huaWeiNavigationEnabled(context) : isOnePlus() ? onePlusNavigationEnabled(context) : isSamsung() ? samsungNavigationEnabled(context) : isSmarTisan() ? smartisanNavigationEnabled(context) : isNokia() ? nokiaNavigationEnabled(context) : isGoogle() ? 0 : -1;
    }

    private static final boolean isNokia() {
        return StringsKt__StringsKt.contains$default((CharSequence) BRAND, (CharSequence) "nokia", false, 2, (Object) null);
    }

    private static final boolean isOnePlus() {
        return StringsKt__StringsKt.contains$default((CharSequence) BRAND, (CharSequence) "oneplus", false, 2, (Object) null);
    }

    private static final boolean isOppo() {
        String str = BRAND;
        return StringsKt__StringsKt.contains$default((CharSequence) str, (CharSequence) "oppo", false, 2, (Object) null) || StringsKt__StringsKt.contains$default((CharSequence) str, (CharSequence) "realme", false, 2, (Object) null);
    }

    private static final boolean isSamsung() {
        return StringsKt__StringsKt.contains$default((CharSequence) BRAND, (CharSequence) "samsung", false, 2, (Object) null);
    }

    private static final boolean isSmarTisan() {
        return StringsKt__StringsKt.contains$default((CharSequence) BRAND, (CharSequence) "smartisan", false, 2, (Object) null);
    }

    private static final boolean isVivo() {
        return StringsKt__StringsKt.contains$default((CharSequence) BRAND, (CharSequence) "vivo", false, 2, (Object) null);
    }

    private static final boolean isXiaomi() {
        String MANUFACTURER = Build.MANUFACTURER;
        Intrinsics.checkNotNullExpressionValue(MANUFACTURER, "MANUFACTURER");
        Locale ROOT = Locale.ROOT;
        Intrinsics.checkNotNullExpressionValue(ROOT, "ROOT");
        String lowerCase = MANUFACTURER.toLowerCase(ROOT);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "(this as java.lang.String).toLowerCase(locale)");
        return Intrinsics.areEqual(lowerCase, "xiaomi");
    }

    private static final int nokiaNavigationEnabled(Context context) {
        return (Settings.Secure.getInt(context.getContentResolver(), "swipe_up_to_switch_apps_enabled", 0) == 0 && Settings.System.getInt(context.getContentResolver(), "navigation_bar_can_hiden", 0) == 0) ? 0 : 1;
    }

    private static final int onePlusNavigationEnabled(Context context) {
        int i2 = Settings.Secure.getInt(context.getContentResolver(), "navigation_mode", 0);
        if (i2 != 2 || Settings.System.getInt(context.getContentResolver(), "buttons_show_on_screen_navkeys", 0) == 0) {
            return i2;
        }
        return 0;
    }

    private static final int oppoNavigationEnabled(Context context) {
        return Settings.Secure.getInt(context.getContentResolver(), "hide_navigationbar_enable", 0);
    }

    private static final int samsungNavigationEnabled(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), "navigationbar_hide_bar_enabled", 0);
    }

    private static final int smartisanNavigationEnabled(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), "navigationbar_trigger_mode", 0);
    }

    private static final int vivoNavigationEnabled(Context context) {
        return Settings.Secure.getInt(context.getContentResolver(), "navigation_gesture_on", 0);
    }

    private static final int xiaomiNavigationEnabled(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), "force_fsg_nav_bar", 0);
    }
}
