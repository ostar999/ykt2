package com.plv.thirdpart.blankj.utilcode.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.AnimRes;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes5.dex */
public final class ActivityUtils {
    private ActivityUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void finishActivity(@NonNull Activity activity) {
        finishActivity(activity, false);
    }

    public static void finishAllActivities() {
        finishAllActivities(false);
    }

    public static void finishOtherActivitiesExceptNewest(@NonNull Class<?> cls) {
        finishOtherActivitiesExceptNewest(cls, false);
    }

    public static boolean finishToActivity(@NonNull Activity activity, boolean z2) {
        return finishToActivity(activity, z2, false);
    }

    public static List<Activity> getActivityList() {
        return Utils.sActivityList;
    }

    private static Context getActivityOrApp() {
        Activity topActivity = getTopActivity();
        return topActivity == null ? Utils.getApp() : topActivity;
    }

    public static String getLauncherActivity() {
        return getLauncherActivity(Utils.getApp().getPackageName());
    }

    private static Bundle getOptionsBundle(Context context, int i2, int i3) {
        return ActivityOptionsCompat.makeCustomAnimation(context, i2, i3).toBundle();
    }

    public static Activity getTopActivity() {
        Activity activity;
        WeakReference<Activity> weakReference = Utils.sTopActivityWeakRef;
        if (weakReference != null && (activity = weakReference.get()) != null) {
            return activity;
        }
        List<Activity> list = Utils.sActivityList;
        int size = list.size();
        if (size > 0) {
            return list.get(size - 1);
        }
        return null;
    }

    public static boolean isActivityExists(@NonNull String str, @NonNull String str2) {
        Intent intent = new Intent();
        intent.setClassName(str, str2);
        return (Utils.getApp().getPackageManager().resolveActivity(intent, 0) == null || intent.resolveActivity(Utils.getApp().getPackageManager()) == null || Utils.getApp().getPackageManager().queryIntentActivities(intent, 0).size() == 0) ? false : true;
    }

    public static boolean isActivityExistsInStack(@NonNull Activity activity) {
        Iterator<Activity> it = Utils.sActivityList.iterator();
        while (it.hasNext()) {
            if (it.next().equals(activity)) {
                return true;
            }
        }
        return false;
    }

    public static void startActivities(@NonNull Intent[] intentArr) {
        startActivities(intentArr, getActivityOrApp(), (Bundle) null);
    }

    public static void startActivity(@NonNull Class<?> cls) {
        Context activityOrApp = getActivityOrApp();
        startActivity(activityOrApp, (Bundle) null, activityOrApp.getPackageName(), cls.getName(), (Bundle) null);
    }

    public static void startHomeActivity() {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        startActivity(intent);
    }

    public static void finishActivity(@NonNull Activity activity, boolean z2) {
        activity.finish();
        if (z2) {
            return;
        }
        activity.overridePendingTransition(0, 0);
    }

    public static void finishAllActivities(boolean z2) {
        List<Activity> list = Utils.sActivityList;
        for (int size = list.size() - 1; size >= 0; size--) {
            Activity activity = list.get(size);
            activity.finish();
            if (!z2) {
                activity.overridePendingTransition(0, 0);
            }
        }
    }

    public static void finishOtherActivitiesExceptNewest(@NonNull Class<?> cls, boolean z2) {
        List<Activity> list = Utils.sActivityList;
        boolean z3 = false;
        for (int size = list.size() - 1; size >= 0; size--) {
            Activity activity = list.get(size);
            if (activity.getClass().equals(cls)) {
                if (z3) {
                    finishActivity(activity, z2);
                } else {
                    z3 = true;
                }
            }
        }
    }

    public static boolean finishToActivity(@NonNull Activity activity, boolean z2, boolean z3) {
        List<Activity> list = Utils.sActivityList;
        for (int size = list.size() - 1; size >= 0; size--) {
            Activity activity2 = list.get(size);
            if (activity2.equals(activity)) {
                if (z2) {
                    finishActivity(activity2, z3);
                }
                return true;
            }
            finishActivity(activity2, z3);
        }
        return false;
    }

    public static String getLauncherActivity(@NonNull String str) {
        Intent intent = new Intent("android.intent.action.MAIN", (Uri) null);
        intent.addCategory("android.intent.category.LAUNCHER");
        intent.addFlags(268435456);
        for (ResolveInfo resolveInfo : Utils.getApp().getPackageManager().queryIntentActivities(intent, 0)) {
            if (resolveInfo.activityInfo.packageName.equals(str)) {
                return resolveInfo.activityInfo.name;
            }
        }
        return "no " + str;
    }

    private static Bundle getOptionsBundle(Activity activity, View[] viewArr) {
        int length = viewArr.length;
        Pair[] pairArr = new Pair[length];
        for (int i2 = 0; i2 < length; i2++) {
            View view = viewArr[i2];
            pairArr[i2] = Pair.create(view, view.getTransitionName());
        }
        return ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pairArr).toBundle();
    }

    public static void startActivities(@NonNull Intent[] intentArr, @NonNull Bundle bundle) {
        startActivities(intentArr, getActivityOrApp(), bundle);
    }

    public static void startActivities(@NonNull Intent[] intentArr, @AnimRes int i2, @AnimRes int i3) {
        Context activityOrApp = getActivityOrApp();
        startActivities(intentArr, activityOrApp, getOptionsBundle(activityOrApp, i2, i3));
    }

    public static void startActivity(@NonNull Class<?> cls, @NonNull Bundle bundle) {
        Context activityOrApp = getActivityOrApp();
        startActivity(activityOrApp, (Bundle) null, activityOrApp.getPackageName(), cls.getName(), bundle);
    }

    public static void finishActivity(@NonNull Activity activity, @AnimRes int i2, @AnimRes int i3) {
        activity.finish();
        activity.overridePendingTransition(i2, i3);
    }

    public static boolean isActivityExistsInStack(@NonNull Class<?> cls) {
        Iterator<Activity> it = Utils.sActivityList.iterator();
        while (it.hasNext()) {
            if (it.next().getClass().equals(cls)) {
                return true;
            }
        }
        return false;
    }

    public static void startActivities(@NonNull Activity activity, @NonNull Intent[] intentArr) {
        startActivities(intentArr, activity, (Bundle) null);
    }

    public static void startActivity(@NonNull Class<?> cls, @AnimRes int i2, @AnimRes int i3) {
        Context activityOrApp = getActivityOrApp();
        startActivity(activityOrApp, (Bundle) null, activityOrApp.getPackageName(), cls.getName(), getOptionsBundle(activityOrApp, i2, i3));
    }

    public static void finishActivity(@NonNull Class<?> cls) {
        finishActivity(cls, false);
    }

    public static void startActivities(@NonNull Activity activity, @NonNull Intent[] intentArr, @NonNull Bundle bundle) {
        startActivities(intentArr, activity, bundle);
    }

    public static void finishActivity(@NonNull Class<?> cls, boolean z2) {
        for (Activity activity : Utils.sActivityList) {
            if (activity.getClass().equals(cls)) {
                activity.finish();
                if (!z2) {
                    activity.overridePendingTransition(0, 0);
                }
            }
        }
    }

    public static void finishAllActivities(@AnimRes int i2, @AnimRes int i3) {
        List<Activity> list = Utils.sActivityList;
        for (int size = list.size() - 1; size >= 0; size--) {
            Activity activity = list.get(size);
            activity.finish();
            activity.overridePendingTransition(i2, i3);
        }
    }

    public static void finishOtherActivitiesExceptNewest(@NonNull Class<?> cls, @AnimRes int i2, @AnimRes int i3) {
        List<Activity> list = Utils.sActivityList;
        boolean z2 = false;
        for (int size = list.size() - 1; size >= 0; size--) {
            Activity activity = list.get(size);
            if (activity.getClass().equals(cls)) {
                if (z2) {
                    finishActivity(activity, i2, i3);
                } else {
                    z2 = true;
                }
            }
        }
    }

    public static void startActivities(@NonNull Activity activity, @NonNull Intent[] intentArr, @AnimRes int i2, @AnimRes int i3) {
        startActivities(intentArr, activity, getOptionsBundle(activity, i2, i3));
    }

    public static boolean finishToActivity(@NonNull Activity activity, boolean z2, @AnimRes int i2, @AnimRes int i3) {
        List<Activity> list = Utils.sActivityList;
        for (int size = list.size() - 1; size >= 0; size--) {
            Activity activity2 = list.get(size);
            if (activity2.equals(activity)) {
                if (z2) {
                    finishActivity(activity2, i2, i3);
                }
                return true;
            }
            finishActivity(activity2, i2, i3);
        }
        return false;
    }

    private static void startActivities(Intent[] intentArr, Context context, Bundle bundle) {
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

    public static void startActivity(@NonNull Activity activity, @NonNull Class<?> cls) {
        startActivity(activity, (Bundle) null, activity.getPackageName(), cls.getName(), (Bundle) null);
    }

    public static void startActivity(@NonNull Activity activity, @NonNull Class<?> cls, @NonNull Bundle bundle) {
        startActivity(activity, (Bundle) null, activity.getPackageName(), cls.getName(), bundle);
    }

    public static void startActivity(@NonNull Activity activity, @NonNull Class<?> cls, @NonNull View... viewArr) {
        startActivity(activity, (Bundle) null, activity.getPackageName(), cls.getName(), getOptionsBundle(activity, viewArr));
    }

    public static void finishActivity(@NonNull Class<?> cls, @AnimRes int i2, @AnimRes int i3) {
        for (Activity activity : Utils.sActivityList) {
            if (activity.getClass().equals(cls)) {
                activity.finish();
                activity.overridePendingTransition(i2, i3);
            }
        }
    }

    public static boolean finishToActivity(@NonNull Class<?> cls, boolean z2) {
        return finishToActivity(cls, z2, false);
    }

    public static void startActivity(@NonNull Activity activity, @NonNull Class<?> cls, @AnimRes int i2, @AnimRes int i3) {
        startActivity(activity, (Bundle) null, activity.getPackageName(), cls.getName(), getOptionsBundle(activity, i2, i3));
    }

    public static boolean finishToActivity(@NonNull Class<?> cls, boolean z2, boolean z3) {
        List<Activity> list = Utils.sActivityList;
        for (int size = list.size() - 1; size >= 0; size--) {
            Activity activity = list.get(size);
            if (activity.getClass().equals(cls)) {
                if (z2) {
                    finishActivity(activity, z3);
                }
                return true;
            }
            finishActivity(activity, z3);
        }
        return false;
    }

    public static void startActivity(@NonNull Bundle bundle, @NonNull Class<?> cls) {
        Context activityOrApp = getActivityOrApp();
        startActivity(activityOrApp, bundle, activityOrApp.getPackageName(), cls.getName(), (Bundle) null);
    }

    public static void startActivity(@NonNull Bundle bundle, @NonNull Class<?> cls, @NonNull Bundle bundle2) {
        Context activityOrApp = getActivityOrApp();
        startActivity(activityOrApp, bundle, activityOrApp.getPackageName(), cls.getName(), bundle2);
    }

    public static boolean finishToActivity(@NonNull Class<?> cls, boolean z2, @AnimRes int i2, @AnimRes int i3) {
        List<Activity> list = Utils.sActivityList;
        for (int size = list.size() - 1; size >= 0; size--) {
            Activity activity = list.get(size);
            if (activity.getClass().equals(cls)) {
                if (z2) {
                    finishActivity(activity, i2, i3);
                }
                return true;
            }
            finishActivity(activity, i2, i3);
        }
        return false;
    }

    public static void startActivity(@NonNull Bundle bundle, @NonNull Class<?> cls, @AnimRes int i2, @AnimRes int i3) {
        Context activityOrApp = getActivityOrApp();
        startActivity(activityOrApp, bundle, activityOrApp.getPackageName(), cls.getName(), getOptionsBundle(activityOrApp, i2, i3));
    }

    public static void startActivity(@NonNull Bundle bundle, @NonNull Activity activity, @NonNull Class<?> cls) {
        startActivity(activity, bundle, activity.getPackageName(), cls.getName(), (Bundle) null);
    }

    public static void startActivity(@NonNull Bundle bundle, @NonNull Activity activity, @NonNull Class<?> cls, @NonNull Bundle bundle2) {
        startActivity(activity, bundle, activity.getPackageName(), cls.getName(), bundle2);
    }

    public static void startActivity(@NonNull Bundle bundle, @NonNull Activity activity, @NonNull Class<?> cls, @NonNull View... viewArr) {
        startActivity(activity, bundle, activity.getPackageName(), cls.getName(), getOptionsBundle(activity, viewArr));
    }

    public static void startActivity(@NonNull Bundle bundle, @NonNull Activity activity, @NonNull Class<?> cls, @AnimRes int i2, @AnimRes int i3) {
        startActivity(activity, bundle, activity.getPackageName(), cls.getName(), getOptionsBundle(activity, i2, i3));
    }

    public static void startActivity(@NonNull String str, @NonNull String str2) {
        startActivity(getActivityOrApp(), (Bundle) null, str, str2, (Bundle) null);
    }

    public static void startActivity(@NonNull String str, @NonNull String str2, @NonNull Bundle bundle) {
        startActivity(getActivityOrApp(), (Bundle) null, str, str2, bundle);
    }

    public static void startActivity(@NonNull String str, @NonNull String str2, @AnimRes int i2, @AnimRes int i3) {
        Context activityOrApp = getActivityOrApp();
        startActivity(activityOrApp, (Bundle) null, str, str2, getOptionsBundle(activityOrApp, i2, i3));
    }

    public static void startActivity(@NonNull Activity activity, @NonNull String str, @NonNull String str2) {
        startActivity(activity, (Bundle) null, str, str2, (Bundle) null);
    }

    public static void startActivity(@NonNull Activity activity, @NonNull String str, @NonNull String str2, @NonNull Bundle bundle) {
        startActivity(activity, (Bundle) null, str, str2, bundle);
    }

    public static void startActivity(@NonNull Activity activity, @NonNull String str, @NonNull String str2, @NonNull View... viewArr) {
        startActivity(activity, (Bundle) null, str, str2, getOptionsBundle(activity, viewArr));
    }

    public static void startActivity(@NonNull Activity activity, @NonNull String str, @NonNull String str2, @AnimRes int i2, @AnimRes int i3) {
        startActivity(activity, (Bundle) null, str, str2, getOptionsBundle(activity, i2, i3));
    }

    public static void startActivity(@NonNull Bundle bundle, @NonNull String str, @NonNull String str2) {
        startActivity(getActivityOrApp(), bundle, str, str2, (Bundle) null);
    }

    public static void startActivity(@NonNull Bundle bundle, @NonNull String str, @NonNull String str2, @NonNull Bundle bundle2) {
        startActivity(getActivityOrApp(), bundle, str, str2, bundle2);
    }

    public static void startActivity(@NonNull Bundle bundle, @NonNull String str, @NonNull String str2, @AnimRes int i2, @AnimRes int i3) {
        Context activityOrApp = getActivityOrApp();
        startActivity(activityOrApp, bundle, str, str2, getOptionsBundle(activityOrApp, i2, i3));
    }

    public static void startActivity(@NonNull Bundle bundle, @NonNull Activity activity, @NonNull String str, @NonNull String str2) {
        startActivity(activity, bundle, str, str2, (Bundle) null);
    }

    public static void startActivity(@NonNull Bundle bundle, @NonNull Activity activity, @NonNull String str, @NonNull String str2, @NonNull Bundle bundle2) {
        startActivity(activity, bundle, str, str2, bundle2);
    }

    public static void startActivity(@NonNull Bundle bundle, @NonNull Activity activity, @NonNull String str, @NonNull String str2, @NonNull View... viewArr) {
        startActivity(activity, bundle, str, str2, getOptionsBundle(activity, viewArr));
    }

    public static void startActivity(@NonNull Bundle bundle, @NonNull Activity activity, @NonNull String str, @NonNull String str2, @AnimRes int i2, @AnimRes int i3) {
        startActivity(activity, bundle, str, str2, getOptionsBundle(activity, i2, i3));
    }

    public static void startActivity(@NonNull Intent intent) {
        startActivity(intent, getActivityOrApp(), (Bundle) null);
    }

    public static void startActivity(@NonNull Intent intent, @NonNull Bundle bundle) {
        startActivity(intent, getActivityOrApp(), bundle);
    }

    public static void startActivity(@NonNull Intent intent, @AnimRes int i2, @AnimRes int i3) {
        Context activityOrApp = getActivityOrApp();
        startActivity(intent, activityOrApp, getOptionsBundle(activityOrApp, i2, i3));
    }

    public static void startActivity(@NonNull Activity activity, @NonNull Intent intent) {
        startActivity(intent, activity, (Bundle) null);
    }

    public static void startActivity(@NonNull Activity activity, @NonNull Intent intent, @NonNull Bundle bundle) {
        startActivity(intent, activity, bundle);
    }

    public static void startActivity(@NonNull Activity activity, @NonNull Intent intent, @NonNull View... viewArr) {
        startActivity(intent, activity, getOptionsBundle(activity, viewArr));
    }

    public static void startActivity(@NonNull Activity activity, @NonNull Intent intent, @AnimRes int i2, @AnimRes int i3) {
        startActivity(intent, activity, getOptionsBundle(activity, i2, i3));
    }

    private static void startActivity(Context context, Bundle bundle, String str, String str2, Bundle bundle2) {
        Intent intent = new Intent("android.intent.action.VIEW");
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        intent.setComponent(new ComponentName(str, str2));
        startActivity(intent, context, bundle2);
    }

    private static void startActivity(Intent intent, Context context, Bundle bundle) {
        if (!(context instanceof Activity)) {
            intent.addFlags(268435456);
        }
        if (bundle != null) {
            context.startActivity(intent, bundle);
        } else {
            context.startActivity(intent);
        }
    }
}
