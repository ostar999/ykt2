package cn.lightsky.infiniteindicator;

import android.content.Context;
import android.view.WindowManager;

/* loaded from: classes.dex */
public class ViewUtils {
    public static int dip2px(Context context, float f2) {
        return context == null ? (int) f2 : (int) ((f2 * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int getScreenWidth(Context context) {
        return ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getWidth();
    }

    public static int px2dip(Context context, float f2) {
        return context == null ? (int) f2 : (int) ((f2 / context.getResources().getDisplayMetrics().density) + 0.5f);
    }
}
