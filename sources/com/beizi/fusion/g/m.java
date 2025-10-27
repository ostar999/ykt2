package com.beizi.fusion.g;

import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class m {
    public static void a(View view, float f2, float f3) {
        MotionEvent motionEventObtain = MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), 0, f2, f3, 0);
        view.dispatchTouchEvent(motionEventObtain);
        MotionEvent motionEventObtain2 = MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis() + 50, 1, f2, f3, 0);
        view.dispatchTouchEvent(motionEventObtain2);
        motionEventObtain.recycle();
        motionEventObtain2.recycle();
    }

    public static List<View> b(View view) {
        ArrayList arrayList = new ArrayList();
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i2 = 0; i2 < viewGroup.getChildCount(); i2++) {
                View childAt = viewGroup.getChildAt(i2);
                arrayList.add(childAt);
                arrayList.addAll(b(childAt));
            }
        }
        return arrayList;
    }

    public static void a(View view) {
        if (view != null) {
            view.performClick();
        }
    }
}
