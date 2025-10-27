package com.plv.thirdpart.blankj.utilcode.util;

import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

/* loaded from: classes5.dex */
public final class SizeUtils {

    public interface onGetSizeListener {
        void onGetSize(View view);
    }

    private SizeUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static float applyDimension(int i2, float f2, DisplayMetrics displayMetrics) {
        float f3;
        if (i2 == 0) {
            return f2;
        }
        if (i2 == 1) {
            f3 = displayMetrics.density;
        } else if (i2 == 2) {
            f3 = displayMetrics.scaledDensity;
        } else if (i2 == 3) {
            f2 *= displayMetrics.xdpi;
            f3 = 0.013888889f;
        } else if (i2 == 4) {
            f3 = displayMetrics.xdpi;
        } else {
            if (i2 != 5) {
                return 0.0f;
            }
            f2 *= displayMetrics.xdpi;
            f3 = 0.03937008f;
        }
        return f2 * f3;
    }

    public static int dp2px(float f2) {
        return (int) ((f2 * Utils.getApp().getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static void forceGetViewSize(final View view, final onGetSizeListener ongetsizelistener) {
        view.post(new Runnable() { // from class: com.plv.thirdpart.blankj.utilcode.util.SizeUtils.1
            @Override // java.lang.Runnable
            public void run() {
                onGetSizeListener ongetsizelistener2 = ongetsizelistener;
                if (ongetsizelistener2 != null) {
                    ongetsizelistener2.onGetSize(view);
                }
            }
        });
    }

    public static int getMeasuredHeight(View view) {
        return measureView(view)[1];
    }

    public static int getMeasuredWidth(View view) {
        return measureView(view)[0];
    }

    public static int[] measureView(View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new ViewGroup.LayoutParams(-1, -2);
        }
        int childMeasureSpec = ViewGroup.getChildMeasureSpec(0, 0, layoutParams.width);
        int i2 = layoutParams.height;
        view.measure(childMeasureSpec, i2 > 0 ? View.MeasureSpec.makeMeasureSpec(i2, 1073741824) : View.MeasureSpec.makeMeasureSpec(0, 0));
        return new int[]{view.getMeasuredWidth(), view.getMeasuredHeight()};
    }

    public static int px2dp(float f2) {
        return (int) ((f2 / Utils.getApp().getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int px2sp(float f2) {
        return (int) ((f2 / Utils.getApp().getResources().getDisplayMetrics().scaledDensity) + 0.5f);
    }

    public static int sp2px(float f2) {
        return (int) ((f2 * Utils.getApp().getResources().getDisplayMetrics().scaledDensity) + 0.5f);
    }
}
