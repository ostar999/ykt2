package c;

import android.content.Context;

/* loaded from: classes.dex */
public class l {
    public static int a(Context context, float f2) {
        return (int) ((f2 * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int b(Context context, float f2) {
        return (int) ((f2 / context.getResources().getDisplayMetrics().scaledDensity) + 0.5f);
    }

    public static int c(Context context, float f2) {
        return (int) ((f2 / context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int d(Context context, float f2) {
        return (int) ((f2 * context.getResources().getDisplayMetrics().scaledDensity) + 0.5f);
    }

    public static int a(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static int b(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }
}
