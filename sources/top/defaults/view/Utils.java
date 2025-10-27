package top.defaults.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;

/* loaded from: classes9.dex */
class Utils {
    private static final Object sLock = new Object();
    private static TypedValue sTempValue;

    public static <T> T checkNotNull(T t2, String str) {
        if (t2 != null) {
            return t2;
        }
        throw new NullPointerException(str);
    }

    public static Drawable getDrawable(Context context, int i2) {
        return context.getDrawable(i2);
    }

    public static int pixelOfDp(Context context, int i2) {
        return (int) TypedValue.applyDimension(1, i2, context.getResources().getDisplayMetrics());
    }

    public static int pixelOfScaled(Context context, int i2) {
        return (int) TypedValue.applyDimension(2, i2, context.getResources().getDisplayMetrics());
    }
}
