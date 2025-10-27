package com.easefun.polyv.livecommon.ui.widget.menudrawer.compat;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.lang.reflect.Method;

/* loaded from: classes3.dex */
final class ActionBarHelperCompat {
    private static final String TAG = "ActionBarHelperCompat";

    public static class SetIndicatorInfo {
        public Object mActionBar;
        public Method mHomeAsUpEnabled;
        public ImageView mUpIndicatorView;

        public SetIndicatorInfo(Activity activity) {
            try {
                String packageName = activity.getPackageName();
                try {
                    this.mUpIndicatorView = (ImageView) ((ViewGroup) activity.findViewById(activity.getResources().getIdentifier("abs__home", "id", packageName)).getParent()).findViewById(activity.getResources().getIdentifier("abs__up", "id", packageName));
                } catch (Throwable unused) {
                }
                if (this.mUpIndicatorView == null) {
                    this.mUpIndicatorView = (ImageView) ((ViewGroup) activity.findViewById(activity.getResources().getIdentifier("home", "id", packageName)).getParent()).findViewById(activity.getResources().getIdentifier("up", "id", packageName));
                }
                Object objInvoke = activity.getClass().getMethod("getSupportActionBar", new Class[0]).invoke(activity, null);
                this.mActionBar = objInvoke;
                this.mHomeAsUpEnabled = objInvoke.getClass().getMethod("setDisplayHomeAsUpEnabled", Boolean.TYPE);
            } catch (Throwable unused2) {
            }
        }
    }

    private ActionBarHelperCompat() {
    }

    public static Object getIndicatorInfo(Activity activity) {
        return new SetIndicatorInfo(activity);
    }

    public static Drawable getThemeUpIndicator(Object info) {
        ImageView imageView = ((SetIndicatorInfo) info).mUpIndicatorView;
        if (imageView != null) {
            return imageView.getDrawable();
        }
        return null;
    }

    public static void setActionBarDescription(Object info, Activity activity, int contentDescRes) {
        SetIndicatorInfo setIndicatorInfo = (SetIndicatorInfo) info;
        if (setIndicatorInfo.mUpIndicatorView != null) {
            setIndicatorInfo.mUpIndicatorView.setContentDescription(contentDescRes == 0 ? null : activity.getString(contentDescRes));
        }
    }

    public static void setActionBarUpIndicator(Object info, Activity activity, Drawable drawable, int contentDescRes) {
        SetIndicatorInfo setIndicatorInfo = (SetIndicatorInfo) info;
        ImageView imageView = setIndicatorInfo.mUpIndicatorView;
        if (imageView != null) {
            imageView.setImageDrawable(drawable);
            setIndicatorInfo.mUpIndicatorView.setContentDescription(contentDescRes == 0 ? null : activity.getString(contentDescRes));
        }
    }

    public static void setDisplayHomeAsUpEnabled(Object info, boolean enabled) {
        SetIndicatorInfo setIndicatorInfo = (SetIndicatorInfo) info;
        Method method = setIndicatorInfo.mHomeAsUpEnabled;
        if (method != null) {
            try {
                method.invoke(setIndicatorInfo.mActionBar, Boolean.valueOf(enabled));
            } catch (Throwable unused) {
            }
        }
    }
}
