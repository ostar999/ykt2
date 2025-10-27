package com.easefun.polyv.livecommon.ui.util;

import android.view.View;

/* loaded from: classes3.dex */
public class PLVViewUtil {
    public static void showViewForDuration(final View view, final long durationInMillis) {
        if (view == null) {
            return;
        }
        view.setVisibility(0);
        final int i2 = 1680133038;
        view.setTag(1680133038, Long.valueOf((System.currentTimeMillis() + durationInMillis) - 100));
        view.postDelayed(new Runnable() { // from class: com.easefun.polyv.livecommon.ui.util.PLVViewUtil.1
            @Override // java.lang.Runnable
            public void run() {
                Object tag = view.getTag(i2);
                if (!(tag instanceof Long) || ((Long) tag).longValue() <= System.currentTimeMillis()) {
                    view.setVisibility(8);
                }
            }
        }, durationInMillis);
    }
}
