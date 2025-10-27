package com.bigkoo.pickerview.utils;

import com.bigkoo.pickerview.R;

/* loaded from: classes2.dex */
public class PickerViewAnimateUtil {
    private static final int INVALID = -1;

    public static int getAnimationResource(int i2, boolean z2) {
        if (i2 != 80) {
            return -1;
        }
        return z2 ? R.anim.pickerview_slide_in_bottom : R.anim.pickerview_slide_out_bottom;
    }
}
