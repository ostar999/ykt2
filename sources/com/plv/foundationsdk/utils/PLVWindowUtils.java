package com.plv.foundationsdk.utils;

import android.app.Activity;
import android.view.WindowManager;
import com.yikaobang.yixue.R2;

/* loaded from: classes4.dex */
public class PLVWindowUtils {
    public static void hideStatusBar(Activity activity) {
        WindowManager.LayoutParams attributes = activity.getWindow().getAttributes();
        attributes.flags |= 1024;
        activity.getWindow().setAttributes(attributes);
        activity.getWindow().addFlags(256);
        activity.getWindow().getDecorView().setSystemUiVisibility(R2.attr.defaultIntentData);
    }

    public static void showStatusBar(Activity activity) {
        WindowManager.LayoutParams attributes = activity.getWindow().getAttributes();
        attributes.flags &= -1025;
        activity.getWindow().setAttributes(attributes);
        activity.getWindow().clearFlags(256);
        activity.getWindow().getDecorView().setSystemUiVisibility(256);
    }
}
