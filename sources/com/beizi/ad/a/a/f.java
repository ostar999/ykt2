package com.beizi.ad.a.a;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import com.yikaobang.yixue.R2;

/* loaded from: classes2.dex */
public class f {
    public static View a(int i2, View view, View view2, String str) {
        if (view != null) {
            return a(i2, view2, view, str, true);
        }
        return null;
    }

    private static int b(Context context) {
        return context instanceof Activity ? ((Activity) context).getResources().getDisplayMetrics().heightPixels : R2.attr.iconTint;
    }

    private static int c(Context context) throws Resources.NotFoundException {
        if (!(context instanceof Activity)) {
            return 0;
        }
        Resources resources = ((Activity) context).getResources();
        int dimensionPixelSize = resources.getDimensionPixelSize(resources.getIdentifier("status_bar_height", "dimen", "android"));
        i.c("BeiZisAd", "Status height:" + dimensionPixelSize);
        return dimensionPixelSize;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:70:0x036a  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x036d  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x037b  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0381  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x03bb  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.view.View a(int r23, android.view.View r24, final android.view.View r25, java.lang.String r26, boolean r27) {
        /*
            Method dump skipped, instructions count: 1144
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.beizi.ad.a.a.f.a(int, android.view.View, android.view.View, java.lang.String, boolean):android.view.View");
    }

    private static int a(Context context) {
        return context instanceof Activity ? ((Activity) context).getResources().getDisplayMetrics().widthPixels : R2.attr.color_hot_circle_one_end;
    }

    public static void a(View view, float f2, float f3) {
        MotionEvent motionEventObtain = MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), 0, f2, f3, 0);
        view.dispatchTouchEvent(motionEventObtain);
        MotionEvent motionEventObtain2 = MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis() + 50, 1, f2, f3, 0);
        view.dispatchTouchEvent(motionEventObtain2);
        motionEventObtain.recycle();
        motionEventObtain2.recycle();
    }
}
