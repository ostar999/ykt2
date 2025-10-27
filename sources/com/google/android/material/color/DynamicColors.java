package com.google.android.material.color;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import androidx.annotation.ChecksSdkIntAtLeast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import com.google.android.material.R;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public class DynamicColors {
    private static final Precondition ALWAYS_ALLOW;
    private static final DeviceSupportCondition DEFAULT_DEVICE_SUPPORT_CONDITION;
    private static final Map<String, DeviceSupportCondition> DYNAMIC_COLOR_SUPPORTED_BRANDS;
    private static final Map<String, DeviceSupportCondition> DYNAMIC_COLOR_SUPPORTED_MANUFACTURERS;
    private static final int[] DYNAMIC_COLOR_THEME_OVERLAY_ATTRIBUTE = {R.attr.dynamicColorThemeOverlay};

    @SuppressLint({"PrivateApi"})
    private static final DeviceSupportCondition SAMSUNG_DEVICE_SUPPORT_CONDITION;
    private static final int USE_DEFAULT_THEME_OVERLAY = 0;

    public interface DeviceSupportCondition {
        boolean isSupported();
    }

    public static class DynamicColorsActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
        private final int dynamicColorThemeOverlay;
        private final Precondition precondition;

        public DynamicColorsActivityLifecycleCallbacks(@StyleRes int i2, @NonNull Precondition precondition) {
            this.dynamicColorThemeOverlay = i2;
            this.precondition = precondition;
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityDestroyed(@NonNull Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPaused(@NonNull Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPreCreated(@NonNull Activity activity, @Nullable Bundle bundle) {
            DynamicColors.applyIfAvailable(activity, this.dynamicColorThemeOverlay, this.precondition);
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityResumed(@NonNull Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStarted(@NonNull Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStopped(@NonNull Activity activity) {
        }
    }

    public interface Precondition {
        boolean shouldApplyDynamicColors(@NonNull Activity activity, @StyleRes int i2);
    }

    static {
        DeviceSupportCondition deviceSupportCondition = new DeviceSupportCondition() { // from class: com.google.android.material.color.DynamicColors.1
            @Override // com.google.android.material.color.DynamicColors.DeviceSupportCondition
            public boolean isSupported() {
                return true;
            }
        };
        DEFAULT_DEVICE_SUPPORT_CONDITION = deviceSupportCondition;
        DeviceSupportCondition deviceSupportCondition2 = new DeviceSupportCondition() { // from class: com.google.android.material.color.DynamicColors.2
            private Long version;

            @Override // com.google.android.material.color.DynamicColors.DeviceSupportCondition
            public boolean isSupported() throws NoSuchMethodException, SecurityException {
                if (this.version == null) {
                    try {
                        Method declaredMethod = Build.class.getDeclaredMethod("getLong", String.class);
                        declaredMethod.setAccessible(true);
                        this.version = Long.valueOf(((Long) declaredMethod.invoke(null, "ro.build.version.oneui")).longValue());
                    } catch (Exception unused) {
                        this.version = -1L;
                    }
                }
                return this.version.longValue() >= 40100;
            }
        };
        SAMSUNG_DEVICE_SUPPORT_CONDITION = deviceSupportCondition2;
        HashMap map = new HashMap();
        map.put("oppo", deviceSupportCondition);
        map.put("realme", deviceSupportCondition);
        map.put("oneplus", deviceSupportCondition);
        map.put("vivo", deviceSupportCondition);
        map.put("xiaomi", deviceSupportCondition);
        map.put("motorola", deviceSupportCondition);
        map.put("itel", deviceSupportCondition);
        map.put("tecno mobile limited", deviceSupportCondition);
        map.put("infinix mobility limited", deviceSupportCondition);
        map.put("hmd global", deviceSupportCondition);
        map.put("sharp", deviceSupportCondition);
        map.put("sony", deviceSupportCondition);
        map.put("tcl", deviceSupportCondition);
        map.put("lenovo", deviceSupportCondition);
        map.put("lge", deviceSupportCondition);
        map.put("google", deviceSupportCondition);
        map.put("robolectric", deviceSupportCondition);
        map.put("samsung", deviceSupportCondition2);
        DYNAMIC_COLOR_SUPPORTED_MANUFACTURERS = Collections.unmodifiableMap(map);
        HashMap map2 = new HashMap();
        map2.put("asus", deviceSupportCondition);
        map2.put("jio", deviceSupportCondition);
        DYNAMIC_COLOR_SUPPORTED_BRANDS = Collections.unmodifiableMap(map2);
        ALWAYS_ALLOW = new Precondition() { // from class: com.google.android.material.color.DynamicColors.3
            @Override // com.google.android.material.color.DynamicColors.Precondition
            public boolean shouldApplyDynamicColors(@NonNull Activity activity, int i2) {
                return true;
            }
        };
    }

    private DynamicColors() {
    }

    public static void applyIfAvailable(@NonNull Activity activity) {
        applyIfAvailable(activity, 0);
    }

    public static void applyToActivitiesIfAvailable(@NonNull Application application) {
        applyToActivitiesIfAvailable(application, 0);
    }

    private static int getDefaultThemeOverlay(@NonNull Context context) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(DYNAMIC_COLOR_THEME_OVERLAY_ATTRIBUTE);
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(0, 0);
        typedArrayObtainStyledAttributes.recycle();
        return resourceId;
    }

    @ChecksSdkIntAtLeast(api = 31)
    @SuppressLint({"DefaultLocale"})
    public static boolean isDynamicColorAvailable() {
        if (Build.VERSION.SDK_INT < 31) {
            return false;
        }
        DeviceSupportCondition deviceSupportCondition = DYNAMIC_COLOR_SUPPORTED_MANUFACTURERS.get(Build.MANUFACTURER.toLowerCase());
        if (deviceSupportCondition == null) {
            deviceSupportCondition = DYNAMIC_COLOR_SUPPORTED_BRANDS.get(Build.BRAND.toLowerCase());
        }
        return deviceSupportCondition != null && deviceSupportCondition.isSupported();
    }

    @NonNull
    public static Context wrapContextIfAvailable(@NonNull Context context) {
        return wrapContextIfAvailable(context, 0);
    }

    public static void applyIfAvailable(@NonNull Activity activity, @StyleRes int i2) {
        applyIfAvailable(activity, i2, ALWAYS_ALLOW);
    }

    public static void applyToActivitiesIfAvailable(@NonNull Application application, @StyleRes int i2) {
        applyToActivitiesIfAvailable(application, i2, ALWAYS_ALLOW);
    }

    @NonNull
    public static Context wrapContextIfAvailable(@NonNull Context context, @StyleRes int i2) {
        if (!isDynamicColorAvailable()) {
            return context;
        }
        if (i2 == 0) {
            i2 = getDefaultThemeOverlay(context);
        }
        return i2 == 0 ? context : new ContextThemeWrapper(context, i2);
    }

    public static void applyIfAvailable(@NonNull Activity activity, @NonNull Precondition precondition) {
        applyIfAvailable(activity, 0, precondition);
    }

    public static void applyToActivitiesIfAvailable(@NonNull Application application, @NonNull Precondition precondition) {
        applyToActivitiesIfAvailable(application, 0, precondition);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void applyIfAvailable(@NonNull Activity activity, @StyleRes int i2, @NonNull Precondition precondition) {
        if (isDynamicColorAvailable()) {
            if (i2 == 0) {
                i2 = getDefaultThemeOverlay(activity);
            }
            if (i2 == 0 || !precondition.shouldApplyDynamicColors(activity, i2)) {
                return;
            }
            activity.setTheme(i2);
        }
    }

    public static void applyToActivitiesIfAvailable(@NonNull Application application, @StyleRes int i2, @NonNull Precondition precondition) {
        application.registerActivityLifecycleCallbacks(new DynamicColorsActivityLifecycleCallbacks(i2, precondition));
    }
}
