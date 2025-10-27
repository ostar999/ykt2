package com.actionbarsherlock.widget.swipeback;

import android.app.Activity;
import android.app.ActivityOptions;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
public class Utils {
    private Utils() {
    }

    public static void convertActivityFromTranslucent(Activity activity) {
        try {
            Method declaredMethod = Activity.class.getDeclaredMethod("convertFromTranslucent", new Class[0]);
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(activity, new Object[0]);
        } catch (Throwable unused) {
        }
    }

    public static void convertActivityToTranslucent(Activity activity) {
        Class<?> cls;
        try {
            Class<?>[] declaredClasses = Activity.class.getDeclaredClasses();
            int length = declaredClasses.length;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    cls = null;
                    break;
                }
                cls = declaredClasses[i2];
                if (cls.getSimpleName().contains("TranslucentConversionListener")) {
                    break;
                } else {
                    i2++;
                }
            }
            Method declaredMethod = Activity.class.getDeclaredMethod("convertToTranslucent", cls, ActivityOptions.class);
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(activity, null, null);
        } catch (Throwable unused) {
        }
    }
}
