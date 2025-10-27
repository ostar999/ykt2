package com.luck.picture.lib.utils;

import android.app.Activity;
import androidx.fragment.app.FragmentActivity;

/* loaded from: classes4.dex */
public class ActivityCompatHelper {
    private static final int MIN_FRAGMENT_COUNT = 1;

    public static boolean checkFragmentNonExits(FragmentActivity fragmentActivity, String str) {
        return !isDestroy(fragmentActivity) && fragmentActivity.getSupportFragmentManager().findFragmentByTag(str) == null;
    }

    public static boolean checkRootFragment(FragmentActivity fragmentActivity) {
        return !isDestroy(fragmentActivity) && fragmentActivity.getSupportFragmentManager().getBackStackEntryCount() == 1;
    }

    public static boolean isDestroy(Activity activity) {
        return activity == null || activity.isFinishing() || activity.isDestroyed();
    }
}
