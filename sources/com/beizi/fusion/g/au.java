package com.beizi.fusion.g;

import android.graphics.Rect;
import android.view.View;

/* loaded from: classes2.dex */
public class au {
    public static boolean a(View view) {
        if (view == null || view.getVisibility() != 0 || view.getParent() == null || !view.hasWindowFocus()) {
            return false;
        }
        Rect rect = new Rect();
        if (!view.getGlobalVisibleRect(rect)) {
            return false;
        }
        int iHeight = rect.height() * rect.width();
        int height = view.getHeight() * view.getWidth();
        return height > 0 && iHeight >= height;
    }

    public static boolean b(View view) {
        if (view != null) {
            try {
                if (view.getVisibility() == 0 && view.getParent() != null && view.hasWindowFocus()) {
                    Rect rect = new Rect();
                    if (view.getGlobalVisibleRect(rect)) {
                        return rect.height() * rect.width() > 0;
                    }
                    return false;
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                return true;
            }
        }
        return false;
    }
}
