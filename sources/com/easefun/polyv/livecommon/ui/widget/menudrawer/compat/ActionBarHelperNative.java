package com.easefun.polyv.livecommon.ui.widget.menudrawer.compat;

import android.R;
import android.app.ActionBar;
import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.lang.reflect.Method;

/* loaded from: classes3.dex */
final class ActionBarHelperNative {
    private static final String TAG = "ActionBarHelperNative";
    private static final int[] THEME_ATTRS = {R.attr.homeAsUpIndicator};

    public static class SetIndicatorInfo {
        public Method setHomeActionContentDescription;
        public Method setHomeAsUpIndicator;
        public ImageView upIndicatorView;

        public SetIndicatorInfo(Activity activity) {
            try {
                this.setHomeAsUpIndicator = ActionBar.class.getDeclaredMethod("setHomeAsUpIndicator", Drawable.class);
                this.setHomeActionContentDescription = ActionBar.class.getDeclaredMethod("setHomeActionContentDescription", Integer.TYPE);
            } catch (Throwable unused) {
                View viewFindViewById = activity.findViewById(R.id.home);
                if (viewFindViewById == null) {
                    return;
                }
                ViewGroup viewGroup = (ViewGroup) viewFindViewById.getParent();
                if (viewGroup.getChildCount() != 2) {
                    return;
                }
                View childAt = viewGroup.getChildAt(0);
                childAt = childAt.getId() == 16908332 ? viewGroup.getChildAt(1) : childAt;
                if (childAt instanceof ImageView) {
                    this.upIndicatorView = (ImageView) childAt;
                }
            }
        }
    }

    private ActionBarHelperNative() {
    }

    public static Object getIndicatorInfo(Activity activity) {
        return new SetIndicatorInfo(activity);
    }

    public static Drawable getThemeUpIndicator(Object info, Activity activity) {
        TypedArray typedArrayObtainStyledAttributes = activity.obtainStyledAttributes(THEME_ATTRS);
        Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(0);
        typedArrayObtainStyledAttributes.recycle();
        return drawable;
    }

    public static void setActionBarDescription(Object info, Activity activity, int contentDescRes) {
        SetIndicatorInfo setIndicatorInfo = (SetIndicatorInfo) info;
        if (setIndicatorInfo.setHomeAsUpIndicator != null) {
            try {
                setIndicatorInfo.setHomeActionContentDescription.invoke(activity.getActionBar(), Integer.valueOf(contentDescRes));
            } catch (Throwable unused) {
            }
        }
    }

    public static void setActionBarUpIndicator(Object info, Activity activity, Drawable drawable, int contentDescRes) {
        SetIndicatorInfo setIndicatorInfo = (SetIndicatorInfo) info;
        if (setIndicatorInfo.setHomeAsUpIndicator != null) {
            try {
                ActionBar actionBar = activity.getActionBar();
                setIndicatorInfo.setHomeAsUpIndicator.invoke(actionBar, drawable);
                setIndicatorInfo.setHomeActionContentDescription.invoke(actionBar, Integer.valueOf(contentDescRes));
                return;
            } catch (Throwable unused) {
                return;
            }
        }
        ImageView imageView = setIndicatorInfo.upIndicatorView;
        if (imageView != null) {
            imageView.setImageDrawable(drawable);
        }
    }

    public static void setDisplayHomeAsUpEnabled(Activity activity, boolean b3) {
        ActionBar actionBar = activity.getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(b3);
        }
    }
}
