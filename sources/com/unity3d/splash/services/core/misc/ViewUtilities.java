package com.unity3d.splash.services.core.misc;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import com.unity3d.splash.services.core.log.DeviceLog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes6.dex */
public class ViewUtilities {
    public static void removeViewFromParent(View view) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (view == null || view.getParent() == null) {
            return;
        }
        try {
            ((ViewGroup) view.getParent()).removeView(view);
        } catch (Exception e2) {
            DeviceLog.exception("Error while removing view from it's parent", e2);
        }
    }

    public static void setBackground(View view, Drawable drawable) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        try {
            View.class.getMethod("setBackground", Drawable.class).invoke(view, drawable);
        } catch (Exception e2) {
            DeviceLog.exception("Couldn't runsetBackground", e2);
        }
    }
}
