package com.blankj.utilcode.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import androidx.annotation.AnimRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import com.blankj.utilcode.util.Utils;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public final class ActivityUtils {
    private ActivityUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void addActivityLifecycleCallbacks(@Nullable Utils.ActivityLifecycleCallbacks activityLifecycleCallbacks) {
        UtilsBridge.addActivityLifecycleCallbacks(activityLifecycleCallbacks);
    }

    public static void finishActivity(@NonNull Activity activity) {
        finishActivity(activity, false);
    }

    public static void finishAllActivities() {
        finishAllActivities(false);
    }

    public static void finishAllActivitiesExceptNewest() {
        finishAllActivitiesExceptNewest(false);
    }

    public static void finishOtherActivities(@NonNull Class<? extends Activity> cls) {
        finishOtherActivities(cls, false);
    }

    public static boolean finishToActivity(@NonNull Activity activity, boolean z2) {
        return finishToActivity(activity, z2, false);
    }

    @Nullable
    public static Activity getActivityByContext(@Nullable Context context) throws NoSuchFieldException, SecurityException {
        if (context == null) {
            return null;
        }
        Activity activityByContextInner = getActivityByContextInner(context);
        if (isActivityAlive(activityByContextInner)) {
            return activityByContextInner;
        }
        return null;
    }

    @Nullable
    private static Activity getActivityByContextInner(@Nullable Context context) throws NoSuchFieldException, SecurityException {
        if (context == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        while (context instanceof ContextWrapper) {
            if (!(context instanceof Activity)) {
                Activity activityFromDecorContext = getActivityFromDecorContext(context);
                if (activityFromDecorContext == null) {
                    arrayList.add(context);
                    context = ((ContextWrapper) context).getBaseContext();
                    if (context == null || arrayList.contains(context)) {
                        break;
                    }
                } else {
                    return activityFromDecorContext;
                }
            } else {
                return (Activity) context;
            }
        }
        return null;
    }

    @Nullable
    private static Activity getActivityFromDecorContext(@Nullable Context context) throws NoSuchFieldException, SecurityException {
        if (context != null && context.getClass().getName().equals("com.android.internal.policy.DecorContext")) {
            try {
                Field declaredField = context.getClass().getDeclaredField("mActivityContext");
                declaredField.setAccessible(true);
                return (Activity) ((WeakReference) declaredField.get(context)).get();
            } catch (Exception unused) {
            }
        }
        return null;
    }

    @Nullable
    public static Drawable getActivityIcon(@NonNull Activity activity) {
        return getActivityIcon(activity.getComponentName());
    }

    public static List<Activity> getActivityList() {
        return UtilsBridge.getActivityList();
    }

    @Nullable
    public static Drawable getActivityLogo(@NonNull Activity activity) {
        return getActivityLogo(activity.getComponentName());
    }

    public static String getLauncherActivity() {
        return getLauncherActivity(Utils.getApp().getPackageName());
    }

    public static List<String> getMainActivities() {
        return getMainActivities(Utils.getApp().getPackageName());
    }

    private static Bundle getOptionsBundle(Fragment fragment, int i2, int i3) {
        FragmentActivity activity = fragment.getActivity();
        if (activity == null) {
            return null;
        }
        return ActivityOptionsCompat.makeCustomAnimation(activity, i2, i3).toBundle();
    }

    public static Activity getTopActivity() {
        return UtilsBridge.getTopActivity();
    }

    private static Context getTopActivityOrApp() {
        if (!UtilsBridge.isAppForeground()) {
            return Utils.getApp();
        }
        Activity topActivity = getTopActivity();
        return topActivity == null ? Utils.getApp() : topActivity;
    }

    public static boolean isActivityAlive(Context context) {
        return isActivityAlive(getActivityByContext(context));
    }

    public static boolean isActivityExists(@NonNull String str, @NonNull String str2) {
        Intent intent = new Intent();
        intent.setClassName(str, str2);
        PackageManager packageManager = Utils.getApp().getPackageManager();
        return (packageManager.resolveActivity(intent, 0) == null || intent.resolveActivity(packageManager) == null || packageManager.queryIntentActivities(intent, 0).size() == 0) ? false : true;
    }

    public static boolean isActivityExistsInStack(@NonNull Activity activity) {
        Iterator<Activity> it = UtilsBridge.getActivityList().iterator();
        while (it.hasNext()) {
            if (it.next().equals(activity)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isIntentAvailable(Intent intent) {
        return true;
    }

    public static void removeActivityLifecycleCallbacks(@Nullable Utils.ActivityLifecycleCallbacks activityLifecycleCallbacks) {
        UtilsBridge.removeActivityLifecycleCallbacks(activityLifecycleCallbacks);
    }

    public static void startActivities(@NonNull Intent[] intentArr) {
        startActivities(intentArr, getTopActivityOrApp(), (Bundle) null);
    }

    public static void startActivity(@NonNull Class<? extends Activity> cls) {
        Context topActivityOrApp = getTopActivityOrApp();
        startActivity(topActivityOrApp, (Bundle) null, topActivityOrApp.getPackageName(), cls.getName(), (Bundle) null);
    }

    public static void startActivityForResult(@NonNull Activity activity, @NonNull Class<? extends Activity> cls, int i2) {
        startActivityForResult(activity, (Bundle) null, activity.getPackageName(), cls.getName(), i2, (Bundle) null);
    }

    public static void startHomeActivity() {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        intent.setFlags(268435456);
        startActivity(intent);
    }

    public static void startLauncherActivity() {
        startLauncherActivity(Utils.getApp().getPackageName());
    }

    public static void addActivityLifecycleCallbacks(@Nullable Activity activity, @Nullable Utils.ActivityLifecycleCallbacks activityLifecycleCallbacks) {
        UtilsBridge.addActivityLifecycleCallbacks(activity, activityLifecycleCallbacks);
    }

    public static void finishActivity(@NonNull Activity activity, boolean z2) {
        activity.finish();
        if (z2) {
            return;
        }
        activity.overridePendingTransition(0, 0);
    }

    public static void finishAllActivities(boolean z2) {
        for (Activity activity : UtilsBridge.getActivityList()) {
            activity.finish();
            if (!z2) {
                activity.overridePendingTransition(0, 0);
            }
        }
    }

    public static void finishAllActivitiesExceptNewest(boolean z2) {
        List<Activity> activityList = UtilsBridge.getActivityList();
        for (int i2 = 1; i2 < activityList.size(); i2++) {
            finishActivity(activityList.get(i2), z2);
        }
    }

    public static void finishOtherActivities(@NonNull Class<? extends Activity> cls, boolean z2) {
        for (Activity activity : UtilsBridge.getActivityList()) {
            if (!activity.getClass().equals(cls)) {
                finishActivity(activity, z2);
            }
        }
    }

    public static boolean finishToActivity(@NonNull Activity activity, boolean z2, boolean z3) {
        for (Activity activity2 : UtilsBridge.getActivityList()) {
            if (activity2.equals(activity)) {
                if (!z2) {
                    return true;
                }
                finishActivity(activity2, z3);
                return true;
            }
            finishActivity(activity2, z3);
        }
        return false;
    }

    @Nullable
    public static Drawable getActivityIcon(@NonNull Class<? extends Activity> cls) {
        return getActivityIcon(new ComponentName(Utils.getApp(), cls));
    }

    @Nullable
    public static Drawable getActivityLogo(@NonNull Class<? extends Activity> cls) {
        return getActivityLogo(new ComponentName(Utils.getApp(), cls));
    }

    public static String getLauncherActivity(@NonNull String str) {
        if (UtilsBridge.isSpace(str)) {
            return "";
        }
        Intent intent = new Intent("android.intent.action.MAIN", (Uri) null);
        intent.addCategory("android.intent.category.LAUNCHER");
        intent.setPackage(str);
        List<ResolveInfo> listQueryIntentActivities = Utils.getApp().getPackageManager().queryIntentActivities(intent, 0);
        return (listQueryIntentActivities == null || listQueryIntentActivities.size() == 0) ? "" : listQueryIntentActivities.get(0).activityInfo.name;
    }

    public static List<String> getMainActivities(@NonNull String str) {
        ArrayList arrayList = new ArrayList();
        Intent intent = new Intent("android.intent.action.MAIN", (Uri) null);
        intent.setPackage(str);
        List<ResolveInfo> listQueryIntentActivities = Utils.getApp().getPackageManager().queryIntentActivities(intent, 0);
        int size = listQueryIntentActivities.size();
        if (size == 0) {
            return arrayList;
        }
        for (int i2 = 0; i2 < size; i2++) {
            ResolveInfo resolveInfo = listQueryIntentActivities.get(i2);
            if (resolveInfo.activityInfo.processName.equals(str)) {
                arrayList.add(resolveInfo.activityInfo.name);
            }
        }
        return arrayList;
    }

    public static boolean isActivityAlive(Activity activity) {
        return (activity == null || activity.isFinishing() || activity.isDestroyed()) ? false : true;
    }

    public static void removeActivityLifecycleCallbacks(@Nullable Activity activity) {
        UtilsBridge.removeActivityLifecycleCallbacks(activity);
    }

    public static void startActivities(@NonNull Intent[] intentArr, @Nullable Bundle bundle) {
        startActivities(intentArr, getTopActivityOrApp(), bundle);
    }

    public static void startActivityForResult(@NonNull Activity activity, @NonNull Class<? extends Activity> cls, int i2, @Nullable Bundle bundle) {
        startActivityForResult(activity, (Bundle) null, activity.getPackageName(), cls.getName(), i2, bundle);
    }

    public static void startLauncherActivity(@NonNull String str) {
        String launcherActivity = getLauncherActivity(str);
        if (TextUtils.isEmpty(launcherActivity)) {
            return;
        }
        startActivity(str, launcherActivity);
    }

    @Nullable
    public static Drawable getActivityIcon(@NonNull ComponentName componentName) {
        try {
            return Utils.getApp().getPackageManager().getActivityIcon(componentName);
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    @Nullable
    public static Drawable getActivityLogo(@NonNull ComponentName componentName) {
        try {
            return Utils.getApp().getPackageManager().getActivityLogo(componentName);
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    private static Bundle getOptionsBundle(Context context, int i2, int i3) {
        return ActivityOptionsCompat.makeCustomAnimation(context, i2, i3).toBundle();
    }

    public static void removeActivityLifecycleCallbacks(@Nullable Activity activity, @Nullable Utils.ActivityLifecycleCallbacks activityLifecycleCallbacks) {
        UtilsBridge.removeActivityLifecycleCallbacks(activity, activityLifecycleCallbacks);
    }

    public static void startActivities(@NonNull Intent[] intentArr, @AnimRes int i2, @AnimRes int i3) {
        Context topActivityOrApp = getTopActivityOrApp();
        startActivities(intentArr, topActivityOrApp, getOptionsBundle(topActivityOrApp, i2, i3));
    }

    public static void startActivity(@NonNull Class<? extends Activity> cls, @Nullable Bundle bundle) {
        Context topActivityOrApp = getTopActivityOrApp();
        startActivity(topActivityOrApp, (Bundle) null, topActivityOrApp.getPackageName(), cls.getName(), bundle);
    }

    public static void startActivityForResult(@NonNull Activity activity, @NonNull Class<? extends Activity> cls, int i2, View... viewArr) {
        startActivityForResult(activity, (Bundle) null, activity.getPackageName(), cls.getName(), i2, getOptionsBundle(activity, viewArr));
    }

    public static void finishActivity(@NonNull Activity activity, @AnimRes int i2, @AnimRes int i3) {
        activity.finish();
        activity.overridePendingTransition(i2, i3);
    }

    private static Bundle getOptionsBundle(Fragment fragment, View[] viewArr) {
        FragmentActivity activity = fragment.getActivity();
        if (activity == null) {
            return null;
        }
        return getOptionsBundle(activity, viewArr);
    }

    public static boolean isActivityExistsInStack(@NonNull Class<? extends Activity> cls) {
        Iterator<Activity> it = UtilsBridge.getActivityList().iterator();
        while (it.hasNext()) {
            if (it.next().getClass().equals(cls)) {
                return true;
            }
        }
        return false;
    }

    public static void finishAllActivitiesExceptNewest(@AnimRes int i2, @AnimRes int i3) {
        List<Activity> activityList = UtilsBridge.getActivityList();
        for (int i4 = 1; i4 < activityList.size(); i4++) {
            finishActivity(activityList.get(i4), i2, i3);
        }
    }

    public static void startActivities(@NonNull Activity activity, @NonNull Intent[] intentArr) {
        startActivities(intentArr, activity, (Bundle) null);
    }

    public static void startActivity(@NonNull Class<? extends Activity> cls, @AnimRes int i2, @AnimRes int i3) {
        Context topActivityOrApp = getTopActivityOrApp();
        startActivity(topActivityOrApp, (Bundle) null, topActivityOrApp.getPackageName(), cls.getName(), getOptionsBundle(topActivityOrApp, i2, i3));
    }

    public static void finishActivity(@NonNull Class<? extends Activity> cls) {
        finishActivity(cls, false);
    }

    public static void finishAllActivities(@AnimRes int i2, @AnimRes int i3) {
        for (Activity activity : UtilsBridge.getActivityList()) {
            activity.finish();
            activity.overridePendingTransition(i2, i3);
        }
    }

    public static void finishOtherActivities(@NonNull Class<? extends Activity> cls, @AnimRes int i2, @AnimRes int i3) {
        for (Activity activity : UtilsBridge.getActivityList()) {
            if (!activity.getClass().equals(cls)) {
                finishActivity(activity, i2, i3);
            }
        }
    }

    private static Bundle getOptionsBundle(Activity activity, View[] viewArr) {
        int length;
        if (viewArr == null || (length = viewArr.length) <= 0) {
            return null;
        }
        Pair[] pairArr = new Pair[length];
        for (int i2 = 0; i2 < length; i2++) {
            View view = viewArr[i2];
            pairArr[i2] = Pair.create(view, view.getTransitionName());
        }
        return ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pairArr).toBundle();
    }

    public static void startActivities(@NonNull Activity activity, @NonNull Intent[] intentArr, @Nullable Bundle bundle) {
        startActivities(intentArr, activity, bundle);
    }

    public static void startActivityForResult(@NonNull Activity activity, @NonNull Class<? extends Activity> cls, int i2, @AnimRes int i3, @AnimRes int i4) {
        startActivityForResult(activity, (Bundle) null, activity.getPackageName(), cls.getName(), i2, getOptionsBundle(activity, i3, i4));
    }

    public static void finishActivity(@NonNull Class<? extends Activity> cls, boolean z2) {
        for (Activity activity : UtilsBridge.getActivityList()) {
            if (activity.getClass().equals(cls)) {
                activity.finish();
                if (!z2) {
                    activity.overridePendingTransition(0, 0);
                }
            }
        }
    }

    public static boolean finishToActivity(@NonNull Activity activity, boolean z2, @AnimRes int i2, @AnimRes int i3) {
        for (Activity activity2 : UtilsBridge.getActivityList()) {
            if (activity2.equals(activity)) {
                if (!z2) {
                    return true;
                }
                finishActivity(activity2, i2, i3);
                return true;
            }
            finishActivity(activity2, i2, i3);
        }
        return false;
    }

    public static void startActivities(@NonNull Activity activity, @NonNull Intent[] intentArr, @AnimRes int i2, @AnimRes int i3) {
        startActivities(intentArr, activity, getOptionsBundle(activity, i2, i3));
    }

    private static void startActivities(Intent[] intentArr, Context context, @Nullable Bundle bundle) {
        if (!(context instanceof Activity)) {
            for (Intent intent : intentArr) {
                intent.addFlags(268435456);
            }
        }
        if (bundle != null) {
            context.startActivities(intentArr, bundle);
        } else {
            context.startActivities(intentArr);
        }
    }

    public static void startActivity(@NonNull Activity activity, @NonNull Class<? extends Activity> cls) {
        startActivity(activity, (Bundle) null, activity.getPackageName(), cls.getName(), (Bundle) null);
    }

    public static void startActivityForResult(@NonNull Bundle bundle, @NonNull Activity activity, @NonNull Class<? extends Activity> cls, int i2) {
        startActivityForResult(activity, bundle, activity.getPackageName(), cls.getName(), i2, (Bundle) null);
    }

    public static void startActivity(@NonNull Activity activity, @NonNull Class<? extends Activity> cls, @Nullable Bundle bundle) {
        startActivity(activity, (Bundle) null, activity.getPackageName(), cls.getName(), bundle);
    }

    public static void startActivityForResult(@NonNull Bundle bundle, @NonNull Activity activity, @NonNull Class<? extends Activity> cls, int i2, @Nullable Bundle bundle2) {
        startActivityForResult(activity, bundle, activity.getPackageName(), cls.getName(), i2, bundle2);
    }

    public static void startActivity(@NonNull Activity activity, @NonNull Class<? extends Activity> cls, View... viewArr) {
        startActivity(activity, (Bundle) null, activity.getPackageName(), cls.getName(), getOptionsBundle(activity, viewArr));
    }

    public static void startActivityForResult(@NonNull Bundle bundle, @NonNull Activity activity, @NonNull Class<? extends Activity> cls, int i2, View... viewArr) {
        startActivityForResult(activity, bundle, activity.getPackageName(), cls.getName(), i2, getOptionsBundle(activity, viewArr));
    }

    public static void finishActivity(@NonNull Class<? extends Activity> cls, @AnimRes int i2, @AnimRes int i3) {
        for (Activity activity : UtilsBridge.getActivityList()) {
            if (activity.getClass().equals(cls)) {
                activity.finish();
                activity.overridePendingTransition(i2, i3);
            }
        }
    }

    public static boolean finishToActivity(@NonNull Class<? extends Activity> cls, boolean z2) {
        return finishToActivity(cls, z2, false);
    }

    public static boolean finishToActivity(@NonNull Class<? extends Activity> cls, boolean z2, boolean z3) {
        for (Activity activity : UtilsBridge.getActivityList()) {
            if (activity.getClass().equals(cls)) {
                if (!z2) {
                    return true;
                }
                finishActivity(activity, z3);
                return true;
            }
            finishActivity(activity, z3);
        }
        return false;
    }

    public static void startActivity(@NonNull Activity activity, @NonNull Class<? extends Activity> cls, @AnimRes int i2, @AnimRes int i3) {
        startActivity(activity, (Bundle) null, activity.getPackageName(), cls.getName(), getOptionsBundle(activity, i2, i3));
    }

    public static void startActivityForResult(@NonNull Bundle bundle, @NonNull Activity activity, @NonNull Class<? extends Activity> cls, int i2, @AnimRes int i3, @AnimRes int i4) {
        startActivityForResult(activity, bundle, activity.getPackageName(), cls.getName(), i2, getOptionsBundle(activity, i3, i4));
    }

    public static void startActivity(@NonNull Bundle bundle, @NonNull Class<? extends Activity> cls) {
        Context topActivityOrApp = getTopActivityOrApp();
        startActivity(topActivityOrApp, bundle, topActivityOrApp.getPackageName(), cls.getName(), (Bundle) null);
    }

    public static void startActivityForResult(@NonNull Bundle bundle, @NonNull Activity activity, @NonNull String str, @NonNull String str2, int i2) {
        startActivityForResult(activity, bundle, str, str2, i2, (Bundle) null);
    }

    public static boolean finishToActivity(@NonNull Class<? extends Activity> cls, boolean z2, @AnimRes int i2, @AnimRes int i3) {
        for (Activity activity : UtilsBridge.getActivityList()) {
            if (activity.getClass().equals(cls)) {
                if (!z2) {
                    return true;
                }
                finishActivity(activity, i2, i3);
                return true;
            }
            finishActivity(activity, i2, i3);
        }
        return false;
    }

    public static void startActivityForResult(@NonNull Bundle bundle, @NonNull Activity activity, @NonNull String str, @NonNull String str2, int i2, @Nullable Bundle bundle2) {
        startActivityForResult(activity, bundle, str, str2, i2, bundle2);
    }

    public static void startActivity(@NonNull Bundle bundle, @NonNull Class<? extends Activity> cls, @Nullable Bundle bundle2) {
        Context topActivityOrApp = getTopActivityOrApp();
        startActivity(topActivityOrApp, bundle, topActivityOrApp.getPackageName(), cls.getName(), bundle2);
    }

    public static void startActivityForResult(@NonNull Bundle bundle, @NonNull Activity activity, @NonNull String str, @NonNull String str2, int i2, View... viewArr) {
        startActivityForResult(activity, bundle, str, str2, i2, getOptionsBundle(activity, viewArr));
    }

    public static void startActivity(@NonNull Bundle bundle, @NonNull Class<? extends Activity> cls, @AnimRes int i2, @AnimRes int i3) {
        Context topActivityOrApp = getTopActivityOrApp();
        startActivity(topActivityOrApp, bundle, topActivityOrApp.getPackageName(), cls.getName(), getOptionsBundle(topActivityOrApp, i2, i3));
    }

    public static void startActivityForResult(@NonNull Bundle bundle, @NonNull Activity activity, @NonNull String str, @NonNull String str2, int i2, @AnimRes int i3, @AnimRes int i4) {
        startActivityForResult(activity, bundle, str, str2, i2, getOptionsBundle(activity, i3, i4));
    }

    public static void startActivityForResult(@NonNull Activity activity, @NonNull Intent intent, int i2) {
        startActivityForResult(intent, activity, i2, (Bundle) null);
    }

    public static void startActivityForResult(@NonNull Activity activity, @NonNull Intent intent, int i2, @Nullable Bundle bundle) {
        startActivityForResult(intent, activity, i2, bundle);
    }

    public static void startActivity(@NonNull Bundle bundle, @NonNull Activity activity, @NonNull Class<? extends Activity> cls) {
        startActivity(activity, bundle, activity.getPackageName(), cls.getName(), (Bundle) null);
    }

    public static void startActivityForResult(@NonNull Activity activity, @NonNull Intent intent, int i2, View... viewArr) {
        startActivityForResult(intent, activity, i2, getOptionsBundle(activity, viewArr));
    }

    public static void startActivity(@NonNull Bundle bundle, @NonNull Activity activity, @NonNull Class<? extends Activity> cls, @Nullable Bundle bundle2) {
        startActivity(activity, bundle, activity.getPackageName(), cls.getName(), bundle2);
    }

    public static void startActivity(@NonNull Bundle bundle, @NonNull Activity activity, @NonNull Class<? extends Activity> cls, View... viewArr) {
        startActivity(activity, bundle, activity.getPackageName(), cls.getName(), getOptionsBundle(activity, viewArr));
    }

    public static void startActivityForResult(@NonNull Activity activity, @NonNull Intent intent, int i2, @AnimRes int i3, @AnimRes int i4) {
        startActivityForResult(intent, activity, i2, getOptionsBundle(activity, i3, i4));
    }

    public static void startActivityForResult(@NonNull Fragment fragment, @NonNull Class<? extends Activity> cls, int i2) {
        startActivityForResult(fragment, (Bundle) null, Utils.getApp().getPackageName(), cls.getName(), i2, (Bundle) null);
    }

    public static void startActivity(@NonNull Bundle bundle, @NonNull Activity activity, @NonNull Class<? extends Activity> cls, @AnimRes int i2, @AnimRes int i3) {
        startActivity(activity, bundle, activity.getPackageName(), cls.getName(), getOptionsBundle(activity, i2, i3));
    }

    public static void startActivityForResult(@NonNull Fragment fragment, @NonNull Class<? extends Activity> cls, int i2, @Nullable Bundle bundle) {
        startActivityForResult(fragment, (Bundle) null, Utils.getApp().getPackageName(), cls.getName(), i2, bundle);
    }

    public static void startActivityForResult(@NonNull Fragment fragment, @NonNull Class<? extends Activity> cls, int i2, View... viewArr) {
        startActivityForResult(fragment, (Bundle) null, Utils.getApp().getPackageName(), cls.getName(), i2, getOptionsBundle(fragment, viewArr));
    }

    public static void startActivity(@NonNull String str, @NonNull String str2) {
        startActivity(getTopActivityOrApp(), (Bundle) null, str, str2, (Bundle) null);
    }

    public static void startActivity(@NonNull String str, @NonNull String str2, @Nullable Bundle bundle) {
        startActivity(getTopActivityOrApp(), (Bundle) null, str, str2, bundle);
    }

    public static void startActivityForResult(@NonNull Fragment fragment, @NonNull Class<? extends Activity> cls, int i2, @AnimRes int i3, @AnimRes int i4) {
        startActivityForResult(fragment, (Bundle) null, Utils.getApp().getPackageName(), cls.getName(), i2, getOptionsBundle(fragment, i3, i4));
    }

    public static void startActivity(@NonNull String str, @NonNull String str2, @AnimRes int i2, @AnimRes int i3) {
        Context topActivityOrApp = getTopActivityOrApp();
        startActivity(topActivityOrApp, (Bundle) null, str, str2, getOptionsBundle(topActivityOrApp, i2, i3));
    }

    public static void startActivity(@NonNull Activity activity, @NonNull String str, @NonNull String str2) {
        startActivity(activity, (Bundle) null, str, str2, (Bundle) null);
    }

    public static void startActivityForResult(@NonNull Bundle bundle, @NonNull Fragment fragment, @NonNull Class<? extends Activity> cls, int i2) {
        startActivityForResult(fragment, bundle, Utils.getApp().getPackageName(), cls.getName(), i2, (Bundle) null);
    }

    public static void startActivity(@NonNull Activity activity, @NonNull String str, @NonNull String str2, @Nullable Bundle bundle) {
        startActivity(activity, (Bundle) null, str, str2, bundle);
    }

    public static void startActivityForResult(@NonNull Bundle bundle, @NonNull Fragment fragment, @NonNull Class<? extends Activity> cls, int i2, @Nullable Bundle bundle2) {
        startActivityForResult(fragment, bundle, Utils.getApp().getPackageName(), cls.getName(), i2, bundle2);
    }

    public static void startActivity(@NonNull Activity activity, @NonNull String str, @NonNull String str2, View... viewArr) {
        startActivity(activity, (Bundle) null, str, str2, getOptionsBundle(activity, viewArr));
    }

    public static void startActivityForResult(@NonNull Bundle bundle, @NonNull Fragment fragment, @NonNull Class<? extends Activity> cls, int i2, View... viewArr) {
        startActivityForResult(fragment, bundle, Utils.getApp().getPackageName(), cls.getName(), i2, getOptionsBundle(fragment, viewArr));
    }

    public static void startActivity(@NonNull Activity activity, @NonNull String str, @NonNull String str2, @AnimRes int i2, @AnimRes int i3) {
        startActivity(activity, (Bundle) null, str, str2, getOptionsBundle(activity, i2, i3));
    }

    public static void startActivity(@NonNull Bundle bundle, @NonNull String str, @NonNull String str2) {
        startActivity(getTopActivityOrApp(), bundle, str, str2, (Bundle) null);
    }

    public static void startActivity(@NonNull Bundle bundle, @NonNull String str, @NonNull String str2, @Nullable Bundle bundle2) {
        startActivity(getTopActivityOrApp(), bundle, str, str2, bundle2);
    }

    public static void startActivityForResult(@NonNull Bundle bundle, @NonNull Fragment fragment, @NonNull Class<? extends Activity> cls, int i2, @AnimRes int i3, @AnimRes int i4) {
        startActivityForResult(fragment, bundle, Utils.getApp().getPackageName(), cls.getName(), i2, getOptionsBundle(fragment, i3, i4));
    }

    public static void startActivity(@NonNull Bundle bundle, @NonNull String str, @NonNull String str2, @AnimRes int i2, @AnimRes int i3) {
        Context topActivityOrApp = getTopActivityOrApp();
        startActivity(topActivityOrApp, bundle, str, str2, getOptionsBundle(topActivityOrApp, i2, i3));
    }

    public static void startActivity(@NonNull Bundle bundle, @NonNull Activity activity, @NonNull String str, @NonNull String str2) {
        startActivity(activity, bundle, str, str2, (Bundle) null);
    }

    public static void startActivityForResult(@NonNull Bundle bundle, @NonNull Fragment fragment, @NonNull String str, @NonNull String str2, int i2) {
        startActivityForResult(fragment, bundle, str, str2, i2, (Bundle) null);
    }

    public static void startActivity(@NonNull Bundle bundle, @NonNull Activity activity, @NonNull String str, @NonNull String str2, @Nullable Bundle bundle2) {
        startActivity(activity, bundle, str, str2, bundle2);
    }

    public static void startActivityForResult(@NonNull Bundle bundle, @NonNull Fragment fragment, @NonNull String str, @NonNull String str2, int i2, @Nullable Bundle bundle2) {
        startActivityForResult(fragment, bundle, str, str2, i2, bundle2);
    }

    public static void startActivity(@NonNull Bundle bundle, @NonNull Activity activity, @NonNull String str, @NonNull String str2, View... viewArr) {
        startActivity(activity, bundle, str, str2, getOptionsBundle(activity, viewArr));
    }

    public static void startActivityForResult(@NonNull Bundle bundle, @NonNull Fragment fragment, @NonNull String str, @NonNull String str2, int i2, View... viewArr) {
        startActivityForResult(fragment, bundle, str, str2, i2, getOptionsBundle(fragment, viewArr));
    }

    public static void startActivity(@NonNull Bundle bundle, @NonNull Activity activity, @NonNull String str, @NonNull String str2, @AnimRes int i2, @AnimRes int i3) {
        startActivity(activity, bundle, str, str2, getOptionsBundle(activity, i2, i3));
    }

    public static boolean startActivity(@NonNull Intent intent) {
        return startActivity(intent, getTopActivityOrApp(), (Bundle) null);
    }

    public static void startActivityForResult(@NonNull Bundle bundle, @NonNull Fragment fragment, @NonNull String str, @NonNull String str2, int i2, @AnimRes int i3, @AnimRes int i4) {
        startActivityForResult(fragment, bundle, str, str2, i2, getOptionsBundle(fragment, i3, i4));
    }

    public static boolean startActivity(@NonNull Intent intent, @Nullable Bundle bundle) {
        return startActivity(intent, getTopActivityOrApp(), bundle);
    }

    public static boolean startActivity(@NonNull Intent intent, @AnimRes int i2, @AnimRes int i3) {
        Context topActivityOrApp = getTopActivityOrApp();
        return startActivity(intent, topActivityOrApp, getOptionsBundle(topActivityOrApp, i2, i3));
    }

    public static void startActivityForResult(@NonNull Fragment fragment, @NonNull Intent intent, int i2) {
        startActivityForResult(intent, fragment, i2, (Bundle) null);
    }

    public static void startActivityForResult(@NonNull Fragment fragment, @NonNull Intent intent, int i2, @Nullable Bundle bundle) {
        startActivityForResult(intent, fragment, i2, bundle);
    }

    public static void startActivity(@NonNull Activity activity, @NonNull Intent intent) {
        startActivity(intent, activity, (Bundle) null);
    }

    public static void startActivityForResult(@NonNull Fragment fragment, @NonNull Intent intent, int i2, View... viewArr) {
        startActivityForResult(intent, fragment, i2, getOptionsBundle(fragment, viewArr));
    }

    public static void startActivity(@NonNull Activity activity, @NonNull Intent intent, @Nullable Bundle bundle) {
        startActivity(intent, activity, bundle);
    }

    public static void startActivity(@NonNull Activity activity, @NonNull Intent intent, View... viewArr) {
        startActivity(intent, activity, getOptionsBundle(activity, viewArr));
    }

    public static void startActivityForResult(@NonNull Fragment fragment, @NonNull Intent intent, int i2, @AnimRes int i3, @AnimRes int i4) {
        startActivityForResult(intent, fragment, i2, getOptionsBundle(fragment, i3, i4));
    }

    public static void startActivity(@NonNull Activity activity, @NonNull Intent intent, @AnimRes int i2, @AnimRes int i3) {
        startActivity(intent, activity, getOptionsBundle(activity, i2, i3));
    }

    private static void startActivity(Context context, Bundle bundle, String str, String str2, @Nullable Bundle bundle2) {
        Intent intent = new Intent();
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        intent.setComponent(new ComponentName(str, str2));
        startActivity(intent, context, bundle2);
    }

    private static boolean startActivityForResult(Activity activity, Bundle bundle, String str, String str2, int i2, @Nullable Bundle bundle2) {
        Intent intent = new Intent();
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        intent.setComponent(new ComponentName(str, str2));
        return startActivityForResult(intent, activity, i2, bundle2);
    }

    private static boolean startActivity(Intent intent, Context context, Bundle bundle) {
        if (!isIntentAvailable(intent)) {
            Log.e("ActivityUtils", "intent is unavailable");
            return false;
        }
        if (!(context instanceof Activity)) {
            intent.addFlags(268435456);
        }
        if (bundle != null) {
            context.startActivity(intent, bundle);
            return true;
        }
        context.startActivity(intent);
        return true;
    }

    private static boolean startActivityForResult(Intent intent, Activity activity, int i2, @Nullable Bundle bundle) {
        if (!isIntentAvailable(intent)) {
            Log.e("ActivityUtils", "intent is unavailable");
            return false;
        }
        if (bundle != null) {
            activity.startActivityForResult(intent, i2, bundle);
            return true;
        }
        activity.startActivityForResult(intent, i2);
        return true;
    }

    private static boolean startActivityForResult(Fragment fragment, Bundle bundle, String str, String str2, int i2, @Nullable Bundle bundle2) {
        Intent intent = new Intent();
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        intent.setComponent(new ComponentName(str, str2));
        return startActivityForResult(intent, fragment, i2, bundle2);
    }

    private static boolean startActivityForResult(Intent intent, Fragment fragment, int i2, @Nullable Bundle bundle) {
        if (!isIntentAvailable(intent)) {
            Log.e("ActivityUtils", "intent is unavailable");
            return false;
        }
        if (fragment.getActivity() != null) {
            if (bundle != null) {
                fragment.startActivityForResult(intent, i2, bundle);
                return true;
            }
            fragment.startActivityForResult(intent, i2);
            return true;
        }
        Log.e("ActivityUtils", "Fragment " + fragment + " not attached to Activity");
        return false;
    }
}
